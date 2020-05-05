# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# OCR Model to detect and recognize text in input image
#
# Actions:
#
#   ocr_images          <image paths>, <ocr model>, <ocr arguments>
#       returns dictionary of text extractions
#
#   crop_rect           <image>, <rectangle details to crop the image>
#       returns image cropped according to the rectangle details
#
#   correct_skewness    <image, points to rotate>
#       returns a rotated rectangle of the minimum area enclosing the input 2D point set.
#
#   tesseract_recognize_text    <list of interim proposal per page>
#       returns tesseract's image recognition
#
#   arbitrary_match     <list chosen chars>, <dtr recognized text>, <tesseract recognized text>,
#                       <current dtr index>,  <dtr text length>,
#                       <current tesseract index>,  <tesseract text length>
#       fill in list chosen chars with tesseract char if it is a space or symbol. Take clover otherwise
#       returns current dtr index, current tesseract index
#
#   combine_clov_and_tesseract_results  <dtr recognized text>, <tesseract recognized text>
#       returns the final recognized text based on dtr and tesseract results
#
#   group_with_prev_text        <image>, <currently cropped word>,
#                               <tesseract end point>, <mapping of tesseract idx to clov idx>
#       merge images if they are close. Update tesseract end points and mappings accordingly
#       returns if the currently cropped word was merged to another one
#
#   detect_text         <image>, <ocr model>, <ocr arguments>
#       returns detected image text snippets
#       (this includes, the text coordinates, snippet list for dtr,
#       snippet list for tesseract, mapping of tesseract idx to clov idx)
#
#   recognizing_rotated_text    <list of index to rotate>, <snippet list for tesseract>, <snippet list for dtr>
#                               <mapping of tesseract idx to clov idx>, <ocr model>, <ocr arguments>
#       returns images rotated to be upright
#
#   extract_text        <snippet list for tesseract>, <snippet list for dtr>, <interim proposal>,
#                       <index of dtr img to extract>, <index of tesseract img to extract>
#                       <mapping of tesseract idx to clov idx>, <old mapping of tesseract idx to clov idx>,
#                       <ocr model>, <ocr arguments>
#       returns the recognized dtr text and tesseract text
#
#   get_text_in_image   <image path>, <ocr model>, <ocr arguments>
#       returns the recognize text from the image in the image path
#
#   iterate_overlap     <column to look for overlap>, <list of image content>, <original word column>
#       returns the most commonly appearing category AND consistently appearing category
#            AND consistently appearing category for bigram words AND words in the image content
#
# (C) 2020 Statistics Canada
# Author: Joanne Yoon
# -----------------------------------------------------------
import multiprocessing as mp
import cv2
import numpy as np
from math import sqrt
import pytesseract
from difflib import SequenceMatcher
from collections import defaultdict
from shapely.geometry.polygon import Polygon

# Code that was mostly, already written by Clova AI
from models.OCR.RECOG_pkg.worker import *
from models.OCR.CRAFT_pkg.worker import *


DATA_DIR = '/data'


class OCR_Args:
    def __init__(self):
        self.detection_model = f'{DATA_DIR}/OCR/CRAFT_pkg/craft_mlt_25k.pth'
        self.refiner_model = f'{DATA_DIR}/OCR/CRAFT_pkg/craft_refiner_CTW1500.pth'
        self.Transformation = 'TPS'
        self.FeatureExtraction = 'ResNet'
        self.SequenceModeling = 'BiLSTM'
        self.Prediction = 'Attn'
        self.hidden_size = 256
        self.character = '0123456789abcdefghijklmnopqrstuvwxyz'
        self.recognition_model = f'{DATA_DIR}/OCR/RECOG_pkg/TPS-ResNet-BiLSTM-Attn.pth'
        # Other recognition model options are:
        # TPS-ResNet-BiLSTM-Attn
        # TPS-ResNet-BiLSTM-CTC
        # None-VGG-BiLSTM-CTC
        # None-ResNet-None-CTC
        self.show_time = False
        self.poly = False
        self.refine = False
        self.sensitive = False
        self.PAD = False
        self.input_channel = 1
        self.poly = False
        self.poly = False
        self.mag_ratio = 1.5
        self.canvas_size = 1280
        self.cuda = False
        self.link_threshold = 0.4
        self.low_text = 0.4
        self.text_threshold = 0.5
        self.workers = int(mp.cpu_count() / 2)
        self.batch_size = 192
        self.batch_max_length = 25
        self.imgH = 32
        self.imgW = 100
        self.rgb = False
        self.num_fiducial = 20
        self.output_channel = 512


class OCR_Model:
    def __init__(self, args):
        self.craft_net, self.refine_net = load_CRAFT_net(args)
        self.recog_model, self.recog_converter = load_recognition_model(args)
        self.recog_model.eval()


def ocr_images(image_paths, ocr_model, args):
    all_regions = {image_path: get_text_in_image(image_path, ocr_model, args)
                   for image_path in image_paths}
    return all_regions


def get_text_in_image(image_path, ocr_model, args):
    try:
        image = imgproc.loadImage(image_path)
    except:
        return (None, None, None)

    # Detect the text within the image
    detected_tesseract_coordinates, clov_imgs, tesseract_imgs, tesseract_to_clov_idxes = detect_text(
        image, ocr_model, args)
    del image

    all_pred_texts, all_coordinates = get_region_content(detected_tesseract_coordinates, clov_imgs,
                                                         tesseract_imgs, tesseract_to_clov_idxes, ocr_model, args, None)

    # Record the results
    phrase_coordinates = []
    for current_coordinates, pred_text in zip(all_coordinates, all_pred_texts):
        all_x, all_y = zip(*current_coordinates)
        phrase_coordinate = {
            'text': pred_text,
            'all_x': list(all_x),
            'all_y': list(all_y)
        }
        phrase_coordinates.append(phrase_coordinate)

    all_content = ({
        'filename': image_path,
        'all_text': all_pred_texts,
        'phrase': phrase_coordinates
    })

    del detected_tesseract_coordinates, clov_imgs, tesseract_imgs, tesseract_to_clov_idxes
    return all_content


def detect_text(image, ocr_model, args):
    bboxes, _, _ = test_CRAFT_net(ocr_model.craft_net, image, args.text_threshold,
                                  args.link_threshold, args.low_text, args.cuda, args.poly, args, ocr_model.refine_net)

    # One to many relationship between tess to clov
    tesseract_to_clov_idx = defaultdict(list)
    all_clov_end_pts = []
    all_tesseract_end_pts = []

    for i, b in enumerate(bboxes):
        all_clov_end_pts.append(b)

        # Add the first tesseract image without looking at the other ones
        if (len(all_tesseract_end_pts) == 0):
            all_tesseract_end_pts.append(b)
            tesseract_to_clov_idx[len(all_tesseract_end_pts) - 1].append(i)
        else:
            orig_tess_len = len(all_tesseract_end_pts)

            grouped_to_prev = group_with_prev_text(b, i, all_tesseract_end_pts, tesseract_to_clov_idx)
            if (not grouped_to_prev):
                all_tesseract_end_pts.append(b)
                tesseract_to_clov_idx[len(all_tesseract_end_pts) - 1].append(i)
            else:
                # Make sure not making new images when grouped to previous
                assert (orig_tess_len >= len(all_tesseract_end_pts))
                assert (len(tesseract_to_clov_idx) == len(all_tesseract_end_pts))
                assert (sum([len(x) for x in tesseract_to_clov_idx.values()]) == len(all_clov_end_pts))

    del bboxes

    # Add last image
    pool = mp.Pool(processes=args.workers)
    tesseract_imgs = pool.map(correct_skewness, [(image, pt) for pt in all_tesseract_end_pts])
    pool.close()
    pool.join()

    pool = mp.Pool(processes=args.workers)
    clov_imgs = pool.map(correct_skewness, [(image, pt) for pt in all_clov_end_pts])
    pool.close()
    pool.join()

    del all_clov_end_pts
    return all_tesseract_end_pts, clov_imgs, tesseract_imgs, tesseract_to_clov_idx


def get_region_content(detected_tesseract_coordinates, clov_imgs, tesseract_imgs,
                       tesseract_to_clov_idxes, ocr_model, args, region_polygon=None):
    region_coordinates = []

    # Find relevant regions
    if (region_polygon is not None):
        # Get relevant detected sections
        region_tesseract_imgs_idxes = []
        region_clov_imgs_idxes = []
        region_tesseract_to_old_clov_idxes = {}
        region_tesseract_to_new_clov_idxes = {}

        for old_t_index, (coordinates, tesseract_img) in enumerate(zip(
                detected_tesseract_coordinates, tesseract_imgs)):
            word_polygon = Polygon(coordinates)
            # Save relevant region
            if (region_polygon.intersects(word_polygon)):
                region_coordinates.append(coordinates)
                region_tesseract_imgs_idxes.append(old_t_index)

                # Get the matching clov text
                region_tesseract_to_old_clov_idxes[old_t_index] = []
                region_tesseract_to_new_clov_idxes[old_t_index] = []
                for c in tesseract_to_clov_idxes[old_t_index]:
                    region_tesseract_to_new_clov_idxes[old_t_index].append(len(region_clov_imgs_idxes))
                    region_tesseract_to_old_clov_idxes[old_t_index].append(c)
                    region_clov_imgs_idxes.append(c)

        # Recognize text in relevant sections
        clov_pred_text_parts, tess_pred_texts = extract_text(clov_imgs, tesseract_imgs,
                                                             region_clov_imgs_idxes, region_tesseract_imgs_idxes,
                                                             region_tesseract_to_new_clov_idxes,
                                                             region_tesseract_to_old_clov_idxes, ocr_model, args)

        assert (len(clov_pred_text_parts) == len(region_tesseract_imgs_idxes))
        del region_clov_imgs_idxes, region_tesseract_imgs_idxes,
        del region_tesseract_to_new_clov_idxes, region_tesseract_to_old_clov_idxes

    else:
        # Recognize text in relevant sections
        clov_pred_text_parts, tess_pred_texts = extract_text(
            clov_imgs, tesseract_imgs, None, None,
            tesseract_to_clov_idxes, tesseract_to_clov_idxes, ocr_model, args)
        region_coordinates = detected_tesseract_coordinates

    # Join two OCR
    region_pred_texts = [combine_clov_and_tesseract_results(clov_parts, tess_text)
                         for clov_parts, tess_text in zip(clov_pred_text_parts, tess_pred_texts)]
    del clov_pred_text_parts, tess_pred_texts
    return (region_pred_texts, region_coordinates)


def crop_rect(img, rect):
    # get the parameter of the small rectangle
    center, size, angle = rect[0], rect[1], rect[2]
    center, size = tuple(map(int, center)), tuple(map(int, size))

    # get row and col num in img
    height, width = img.shape[0], img.shape[1]

    # calculate the rotation matrix
    M = cv2.getRotationMatrix2D(center, angle, 1)
    # rotate the original image
    img_rot = cv2.warpAffine(img, M, (width, height))
    img_crop = cv2.getRectSubPix(img_rot, size, center)

    return img_crop


def correct_skewness(img_and_cnt):
    img, cnt = img_and_cnt
    rect = cv2.minAreaRect(cnt)
    assert (-100 < rect[2])

    # get the parameter of the small rectangle
    center, size, angle = rect[0], rect[1], rect[2]
    if (angle < -45):
        size = (size[1], size[0])
        angle += 90
    center, size = tuple(map(int, center)), tuple(map(int, size))

    # Rotate if needed
    img_rot = img
    if (not (round(angle) == -90 or round(angle) == 0)):
        # calculate the rotation matrix
        M = cv2.getRotationMatrix2D(center, angle, 1)
        # rotate the original image
        img_rot = cv2.warpAffine(img, M, (img.shape[1], img.shape[0]))

    img_crop = cv2.getRectSubPix(img_rot, size, center)
    return img_crop


def euclidean_dist(x1, x2, y1, y2):
    y_dist = y2 - y1
    x_dist = x2 - x1
    return sqrt(y_dist ** 2 + x_dist ** 2)


def tesseract_recognize_text(img):
    # Get verbose data including boxes, confidences and line
    tesseract_data = pytesseract.image_to_data(img, config=r'--psm 13 --oem 1', output_type='dict', lang='eng+fra')

    tesseract_text = []
    for conf, text in zip(tesseract_data['conf'], tesseract_data['text']):
        if (int(conf) > 10):
            tesseract_text.append(text)

    tesseract_text = ' '.join(tesseract_text)

    # Find if the text is different if we ask tesseract to look for digits and puncts only
    tesseract_text_digit_only = [c for c in tesseract_text if (c in '0123456789')]
    tesseract_text_digit_punct_only = [c for c in tesseract_text if (c in '0123456789+,-.')]
    if (len(tesseract_text_digit_only) > 0):
        tesseract_num_punct = pytesseract.image_to_string(img,
                                                          config='--psm 13 --oem 1 -c tessedit_char_whitelist=0123456789+,-.',
                                                          lang='eng+fra')

        # Add punctuations to tesseract text if needed
        if (tesseract_text_digit_punct_only != tesseract_num_punct
                and not set(tesseract_num_punct).isdisjoint('+,-.')):
            breaker_index = -1
            for cur_tesseract_num_punct in tesseract_num_punct:
                if (not cur_tesseract_num_punct.isdigit() and cur_tesseract_num_punct not in tesseract_text):
                    tesseract_text = (tesseract_text[:breaker_index + 1]
                                      + cur_tesseract_num_punct + tesseract_text[breaker_index + 1:])
                elif (cur_tesseract_num_punct in tesseract_text):
                    breaker_index = tesseract_text.index(cur_tesseract_num_punct)

    return tesseract_text.lower()


def arbitrary_match(result_chars, clov_text, tesseract_text, c_idx, c_idx_end, t_idx, t_idx_end):
    while (c_idx < c_idx_end and t_idx < t_idx_end):
        # Take tesseract if it is a space or symbol. Take clover otherwise
        if (tesseract_text[t_idx] == ' '):
            result_chars.append(tesseract_text[t_idx])
            t_idx += 1
        elif (not tesseract_text[t_idx].isalnum()):
            result_chars.append(tesseract_text[t_idx])
            t_idx += 1

            if (t_idx + 1 < t_idx_end and
                    not (tesseract_text[t_idx + 1].isalnum() and clov_text[c_idx].isalnum())):
                c_idx += 1
        else:
            result_chars.append(clov_text[c_idx])
            t_idx += 1
            c_idx += 1

    # Use remaining text from each side if they are relevant
    if (c_idx < c_idx_end):
        if (len(result_chars) > 0 and result_chars[-1].isalnum()):
            result_chars += clov_text[c_idx:c_idx_end]
        c_idx += c_idx_end - c_idx

    if (t_idx < t_idx_end):
        if (any([not x.isalnum() for x in tesseract_text[t_idx:t_idx_end]])):
            result_chars += tesseract_text[t_idx:t_idx_end]
        t_idx += t_idx_end - t_idx
    return (c_idx, t_idx)


def combine_clov_and_tesseract_results(clov_text_parts, tesseract_text):
    clov_text = ''.join(clov_text_parts)
    max_text_len = max(len(clov_text), len(tesseract_text))
    num_match = len(
        list(filter(lambda x: x, [a == b for a, b in zip(clov_text[:max_text_len], tesseract_text[:max_text_len])])))

    # If they are identical or very different, just use clover text
    if (clov_text == tesseract_text or
            num_match < max_text_len / 5):
        return (' '.join(clov_text_parts))

    # Find all the matches
    matcher = SequenceMatcher(None, clov_text, tesseract_text)
    matches = matcher.get_matching_blocks()

    t_idx = 0
    c_idx = 0
    result_chars = []

    for i, match in enumerate(matches):
        # Arbitrarily match the conflicting character that preceded the matching characters
        c_idx, t_idx = arbitrary_match(result_chars, clov_text, tesseract_text, c_idx, match.a, t_idx, match.b)

        assert (t_idx == match.b)
        assert (c_idx == match.a)
        assert (clov_text[c_idx: match.a + match.size] == tesseract_text[t_idx:match.b + match.size])

        # Joing matching text
        result_chars += clov_text[match.a: match.a + match.size]
        c_idx += match.size
        t_idx += match.size

    arbitrary_match(result_chars, clov_text, tesseract_text, c_idx, len(clov_text), t_idx, len(tesseract_text))
    assert (t_idx == len(tesseract_text))
    assert (c_idx == len(clov_text))

    return ''.join(result_chars)


def group_with_prev_text(current_image, current_image_idx,
                         all_tesseract_end_pts, tesseract_to_clov_idx):
    grouped_to_prev = False
    curr_tess_to_clov_idx = None

    # While we find a match
    while (True):
        grouped_to_prev_this_loop = False

        for t in range(len(all_tesseract_end_pts) - 1, -1, -1):
            if (np.array_equal(all_tesseract_end_pts[t], current_image)):
                continue

            # True if the new image is to the right of existing
            in_order = abs(all_tesseract_end_pts[t][1][0] - current_image[0][0]) < abs(
                all_tesseract_end_pts[t][0][0] - current_image[1][0])
            left, right = ((all_tesseract_end_pts[t], current_image)
                           if (in_order) else (current_image, all_tesseract_end_pts[t]))

            right_height = right[2][1] - right[1][1]
            left_height = left[2][1] - left[1][1]
            right_length = right[1][0] - right[1][0]
            left_length = left[1][0] - left[1][0]

            # Two images may be close diagonally or horizontally. Use the euclidean distance
            distance_from_prev = euclidean_dist(left[1][0],
                                                right[0][0], left[1][1], right[0][1])
            dist_is_short = distance_from_prev < 10

            # If distance is long, then see if it due to horizontal overlap or mainly from vertical overlap
            if (not dist_is_short):
                # If two images are far away (not vertical text) and not side by side, then skip
                top_y = max(right[0][1], left[1][1])
                bot_y = min(right[3][1], left[2][1])
                if (top_y > bot_y):
                    continue

                horz_distance_from_prev = abs(right[0][0] - left[1][0])
                vert_distance_from_prev = abs(right[0][1] - left[1][1])

                horz_overlap = (left[0][0] <= right[0][0] <= left[1][0]
                                and horz_distance_from_prev < right_length / 2 and horz_distance_from_prev < left_length / 2
                                ) or (horz_distance_from_prev < 4)
                # the distance should not exceed half of either texts
                enough_vert_overlap = vert_distance_from_prev < (min(left_height, right_height) / 2)
                mainly_horz_dist = horz_overlap and enough_vert_overlap

            else:
                mainly_horz_dist = False

            # Do not join two text if they are far away
            if (not dist_is_short and not mainly_horz_dist):
                continue

            # Join the two images
            all_tesseract_end_pts[t] = (np.array([
                [left[0][0], right[0][1]],
                right[1],
                right[2],
                [left[3][0], right[2][1]],
            ]) if (left_height < right_height / 2)
                                        else np.array([
                left[0],
                [right[1][0], left[1][1]],
                [right[2][0], left[2][1]],
                left[3],
            ]) if (right_height < left_height / 2)
            else np.array([
                left[0],
                right[1],
                right[2],
                left[3],
            ])
                                        )

            # Record/Update with clover images to append
            if (in_order):
                if (curr_tess_to_clov_idx):
                    tesseract_to_clov_idx[t] = (tesseract_to_clov_idx[t]
                                                + tesseract_to_clov_idx[curr_tess_to_clov_idx])
                    tesseract_to_clov_idx.pop(curr_tess_to_clov_idx)
                    for i in list(tesseract_to_clov_idx.keys()):
                        if (i > curr_tess_to_clov_idx):
                            tesseract_to_clov_idx[i - 1] = tesseract_to_clov_idx.pop(i)

                    all_tesseract_end_pts.pop(curr_tess_to_clov_idx)
                else:
                    tesseract_to_clov_idx[t].append(current_image_idx)
            else:
                if (curr_tess_to_clov_idx):
                    tesseract_to_clov_idx[t] = (tesseract_to_clov_idx[curr_tess_to_clov_idx]
                                                + tesseract_to_clov_idx[t])
                    tesseract_to_clov_idx.pop(curr_tess_to_clov_idx)
                    for i in list(tesseract_to_clov_idx.keys()):
                        if (i > curr_tess_to_clov_idx):
                            tesseract_to_clov_idx[i - 1] = tesseract_to_clov_idx.pop(i)

                    all_tesseract_end_pts.pop(curr_tess_to_clov_idx)

                else:
                    tesseract_to_clov_idx[t] = [current_image_idx] + tesseract_to_clov_idx[t]

            grouped_to_prev = True
            grouped_to_prev_this_loop = True
            curr_tess_to_clov_idx = t

            if (t == len(all_tesseract_end_pts)):
                t -= 1
            current_image = all_tesseract_end_pts[t]

            break

        # Leave if there was no changes made this loop
        if (not grouped_to_prev_this_loop):
            break

    return grouped_to_prev


def recognizing_rotated_text(idxes_to_rotate, tesseract_imgs, clov_imgs,
                             tess_to_clov_idxes, ocr_model, args):
    best_text_orientations = []
    upright_texts = [tesseract_imgs[i] for i in idxes_to_rotate]
    upright_texts = recognize_text_in_imgs(upright_texts, ocr_model.recog_model,
                                           ocr_model.recog_converter, args)

    # Get the images for clock wise. Allow multiple clov imgs to get spaces
    rotated_tess_to_clov_idxes = defaultdict(list)
    cw_rotated_imgs = []
    for tess_i in idxes_to_rotate:
        for clov_i in tess_to_clov_idxes[tess_i]:
            rotated_tess_to_clov_idxes[tess_i].append(len(cw_rotated_imgs))
            cw_rotated_imgs.append(cv2.rotate(clov_imgs[clov_i], cv2.ROTATE_90_CLOCKWISE))

    cw_rotated_texts = recognize_text_in_imgs(cw_rotated_imgs, ocr_model.recog_model, ocr_model.recog_converter, args)
    for i, upright_text in zip(idxes_to_rotate, upright_texts):
        cw_rotated_text = ' '.join([cw_rotated_texts[c] for c in rotated_tess_to_clov_idxes[i]])
        best_text_orientation = (upright_text, None)

        if (len(cw_rotated_text) > len(upright_text) * 2 or len(upright_text) == 0):
            best_text_orientation = (cw_rotated_text, cv2.ROTATE_90_CLOCKWISE)
            # print(i,"ROTATE_90_CLOCKWISE", cw_rotated_text, 'vs', upright_text)

            # Try counter clockwise
            if (len(cw_rotated_text) == 0 or 'ndn' in cw_rotated_text or 'udu' in cw_rotated_text):
                ccw_rotated_text = recognize_text_in_imgs(
                    [cv2.rotate(tesseract_imgs[i], cv2.ROTATE_90_COUNTERCLOCKWISE)],
                    ocr_model.recog_model, ocr_model.recog_converter, args)
                # print(i,"ROTATE_90_COUNTERCLOCKWISE", ccw_rotated_text, 'vs', upright_text)
                if (len(ccw_rotated_text) > 0):
                    best_text_orientation = (ccw_rotated_text, cv2.ROTATE_90_COUNTERCLOCKWISE)

        best_text_orientations.append(best_text_orientation)
    return best_text_orientations


def extract_text(clov_imgs, tesseract_imgs, clov_imgs_idxes,
                 tesseract_imgs_idxes, tesseract_to_new_clov_idxes,
                 tesseract_to_old_clov_idxes, ocr_model, args):
    # Predict via tesseract and rotate images when needed
    tesseract_imgs_to_test = []
    tess_idxes_to_rotate = []
    for i, tess_img in enumerate(tesseract_imgs):
        if (tesseract_imgs_idxes is not None and i not in tesseract_imgs_idxes):
            continue
        tesseract_imgs_to_test.append(tess_img)

        # If width is longer than the length, it is unlikely rotated vertically
        if (tess_img.shape[1] < tess_img.shape[0] * 1.5):
            tess_idxes_to_rotate.append(i)

    pool = mp.Pool(processes=args.workers)
    tess_pred_texts = pool.map(tesseract_recognize_text, tesseract_imgs_to_test)
    pool.close()
    pool.join()

    # Rotate words as needed
    best_text_orientations = recognizing_rotated_text(tess_idxes_to_rotate, tesseract_imgs, clov_imgs,
                                                      tesseract_to_old_clov_idxes, ocr_model, args)

    for i, (best_text, best_orientation) in zip(tess_idxes_to_rotate, best_text_orientations):
        if (best_orientation is not None):
            # Edit the word
            tess_pred_texts[i] = best_text

            # Rotate the clover images as well to be consistent
            for c in tesseract_to_old_clov_idxes[i]:
                clov_imgs[c] = cv2.rotate(clov_imgs[c], best_orientation)
    del tess_idxes_to_rotate, best_text_orientations

    # Get Text from clov images
    relevant_clov_imgs = [clov_imgs[c] for c in clov_imgs_idxes] if (clov_imgs_idxes is not None) else clov_imgs
    clov_indi_texts = recognize_text_in_imgs(relevant_clov_imgs, ocr_model.recog_model, ocr_model.recog_converter, args)

    # Group text to match how it was in tesseract
    clov_pred_text_parts = [[clov_indi_texts[c] for c in cs] for t, cs in tesseract_to_new_clov_idxes.items()]
    return clov_pred_text_parts, tess_pred_texts


"""
# import json
def default_json_print(obj):
    if (isinstance(obj, np.float32)):
        return str(obj)
    raise TypeError('Object of type ' + str(type(obj)) +
                    'is not JSON serializable')

#Save Results
all_products_json = defaultdict(list)
for product_identifier, extracted_pages in all_regions:
    if(not product_identifier):
        continue
    all_products_json[product_identifier].append(extracted_pages)

with open(full_text_out_filename, 'w') as outfile:
    json.dump(all_products_json, outfile, default=default_json_print)
"""
