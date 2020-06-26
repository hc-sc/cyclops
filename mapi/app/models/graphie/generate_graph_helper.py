# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Chunks out the words in an image and supplements embedding
#
# Actions:
#
#   covert_ocr_to_df        <ocr dictionary>
#                   returns dataframe with a row per each ocr extraction
#
#   generate_graph        <dataframe of OCR result>
#                   returns graph IE representation
#
#   create_subgraphs        <graph>
#                   returns dataframe with a row per each subgraph
#
#   sort_by_center_x        <tuple of word, position, x coordinates, y coordinates>
#                   returns the same input sorted first by y coordinates range and then by x coordinates
#
#   separate_by_sentence    <word list>, <positions>, <x y coordinates>
#                   returns the input grouped by words in the same area
#
#   custom_dataset        <graph>, <use spell checked words?>
#                   returns dataframe with embedding
#
# (C) 2020 Statistics Canada
# Author: Anurag Bejju, Joanne Yoon
# -----------------------------------------------------------

import numpy as np
import pandas as pd
from wand.image import Image as WImage
from wand.drawing import Drawing
from wand.color import Color
from PIL import ImageFont
import pickle
import time
import networkx as nx
from itertools import chain

from pathlib import Path

DATA_DIR = "/data"
FONT = f"{DATA_DIR}/font/Arial.ttf"

def preprocess_images(*img_filenames):
    for file in img_filenames:
        img = WImage(filename=file)
        img.strip()
        if img.width > img.height:
            img.rotate(90)
        img.save(filename=file)


def calulate_function_time(func, *args):
    start = time.time()
    output = func(*args)
    end = time.time()
    execution_time = end - start
    return output, execution_time


def covert_ocr_to_df(ocr_output):
    rows = []
    for image_name, image_val in ocr_output.items():
        for token_dict in image_val['phrase']:
            rows.append([image_name, token_dict['text'],
                         np.array(token_dict['all_x']).astype(np.float).tolist(),
                         np.array(token_dict['all_y']).astype(np.float).tolist()])
    ocr_df = pd.DataFrame(rows, columns=['file_name', 'token', 'x', 'y'])
    ocr_df['x_y'] = ocr_df[['x', 'y']].apply(lambda x: [(x[0][0], x[1][0]),
                                                        (x[0][1], x[1][1]),
                                                        (x[0][2], x[1][2]),
                                                        (x[0][3], x[1][3])]
                                             , axis=1)

    return ocr_df


def generate_graph(ocr_df, visualize_graph=True):
    v_overlap = 0
    v_margin = 0.2
    h_overlap = 0.5

    class_color = {'productname': 'red',
                   'claim': 'fuchsia',
                   'dosage': 'blue',
                   'ingredients': 'pink',
                   'companyname': 'teal',
                   'npn_number': 'purple',
                   'unknown': 'aqua',
                   'name': 'lime'
                   }
    font = ImageFont.truetype(FONT, 10)

    def overlap(x1, x2, y1, y2):
        res = x2 - x1 + y2 - y1 - (max(y2, x2) - min(y1, x1))
        return max(res, 0)

    def check_vertical_edge(pos1, pos2):
        a_x1, a_y1, a_x2, a_y2 = pos1
        b_x1, b_y1, b_x2, b_y2 = pos2
        if b_y1 > a_y1 and overlap(a_x1, a_x2, b_x1, b_x2) > min(a_x2 - a_x1, b_x2 - b_x1) * v_overlap:
            d = b_y1 - a_y2
            if d < max(a_y2 - a_y1, b_y2 - b_y1) * v_margin:
                return d
            return None

    def check_horizontal_edge(pos1, pos2):
        a_x1, a_y1, a_x2, a_y2 = pos1
        b_x1, b_y1, b_x2, b_y2 = pos2
        if b_x1 > a_x1 and overlap(a_y1, a_y2, b_y1, b_y2) > min(a_y2 - a_y1, b_y2 - b_y1) * h_overlap:
            return b_x1 - a_x2
        return None

    def v_edge(words, u, v, img, draw):
        y1 = words[u][1]
        y2 = words[v][3]
        x1 = max(words[u][0], words[v][0])
        x2 = min(words[u][2], words[v][2])
        x1 = x2 = (x1 + x2) / 2
        draw.line((x1, y1), (x2, y2))

    def h_edge(words, u, v, img, draw):
        x1 = words[u][2]
        x2 = words[v][0]
        y1 = max(words[u][1], words[v][1])
        y2 = min(words[u][3], words[v][3])

        y1 = y2 = (y1 + y2) / 2
        draw.line((x1, y1), (x2, y2))

    result = []

    final_op = {}

    for a, b in ocr_df.groupby('file_name'):
        final_op[a] = {}

        final_op[a]['img'] = a
        img_1 = WImage(filename=a)
        img_1.strip()
        final_op[a]['width'] = width = img_1.width
        final_op[a]['height'] = height = img_1.height
        N = len(b)
        final_op[a]['nodes_count'] = N
        graph = final_op[a]
        final_op[a]['tokens'] = []
        final_op[a]['pos'] = []
        final_op[a]['act_pos'] = []
        final_op[a]['x_y'] = []
        final_op[a]['edge'] = {'v': [], 'h': []}
        with Drawing() as draw:
            draw.font_color = Color('white')
            draw.font = "models/font/Arial.ttf"
            if img_1.width > 750:
                draw.font_size = 30
                font = ImageFont.truetype(FONT, 30)
            else:
                draw.font_size = 6
                font = ImageFont.truetype(FONT, 6)
            for c, d in b[['token', 'x_y']].values:
                final_op[a]['tokens'].append(c)
                x1, y1, x2, y2 = d[0][0], d[0][1], d[2][0], d[2][1]
                final_op[a]['act_pos'].append([x1, y1, x2, y2])
                final_op[a]['x_y'].append(d)
                x1, x2 = x1 / width, x2 / width
                y1, y2 = 1 - y2 / height, 1 - y1 / height
                final_op[a]['pos'].append([x1, y1, x2, y2])
                # draw.rectangle((d[0],d[2]), outline=class_color[e],width=2)

                draw.stroke_width = 3
                draw.stroke_color = Color("red")
                draw.fill_color = Color("red")
                draw.fill_opacity = 0.2
                draw.polygon(d)
                draw.push()
                draw.stroke_color = Color('black')
                draw.fill_color = Color('black')
                draw.fill_opacity = 1

                text = c + " : "
                w, h = font.getsize(text)
                draw.rectangle(left=d[2][0], top=int(d[2][1] - h),
                               width=w, height=h)

                draw.pop()
                draw.push()
                draw.stroke_color = Color('white')
                draw.fill_color = Color('white')

                draw.fill_opacity = 1
                draw.text(x=int(d[2][0]),
                          y=int(d[2][1] - h / 4),
                          body=text)
                draw.pop()
            draw.push()
            draw.fill_color = Color('green')
            draw.stroke_color = Color('green')
            draw.stroke_width = 3

            for u in range(N):
                v, min_d = None, 1
                for _v in range(N):
                    d = check_vertical_edge(graph['pos'][u], graph['pos'][_v])
                    if d is not None and d < min_d:
                        min_d, v = d, _v
                if v is not None:
                    final_op[a]['edge']['v'].append([u, v])
                    v_edge(final_op[a]['act_pos'], u, v, img_1, draw)

            lines = [[u] for u in range(N)]
            father = [u for u in range(N)]

            def get_father(u):
                if father[u] == u:
                    return u
                return get_father(father[u])

            for u in range(N):
                v, min_d = None, 1
                for _v in range(N):
                    d = check_horizontal_edge(final_op[a]['pos'][u], final_op[a]['pos'][_v])
                    if d is not None and d < min_d:
                        min_d, v = d, _v
                        # merge a line
                if v is not None:
                    final_op[a]['edge']['h'].append([u, v])
                    h_edge(final_op[a]['act_pos'], u, v, img_1, draw)
                    fu, fv = get_father(u), get_father(v)
                    lines[fu] = lines[fu] + lines[fv]
                    lines[fv] = []
                    father[fv] = fu
            draw.pop()
            draw(img_1)
        if visualize_graph == True:
            f_name = a.split('/')[-1]
            Path(f"{DATA_DIR}/graph").mkdir(parents=True, exist_ok=True)
            img_1.save(filename=f"{DATA_DIR}/graph/" + f_name)
    return final_op


def get_height(y_cords):
    average_height = ((y_cords[3] - y_cords[0]) + (y_cords[2] - y_cords[1])) / 2
    assert (average_height > 0)
    return average_height


def sort_by_center_x(batch):
    # A list of x_line_snippets. Each element is in different y line
    xy_sorted_snippets = []

    # A list of words under the same x line
    same_x_line_snippets = []
    bottom_ys = []
    for token, pos, xs, ys in batch:
        center_y = sum(ys) / len(ys)

        # If the new word's center y point is below the average y point
        if (len(bottom_ys) > 0 and (center_y > sum(bottom_ys) / len(bottom_ys))):
            bottom_ys = []
            # Sort the words within the y line by the x value
            xy_sorted_snippets.append(sorted(same_x_line_snippets,
                                             key=lambda tpxy: sum(tpxy[2]) / len(tpxy[2])))
            same_x_line_snippets = []

        bottom_ys.append((ys[2] + ys[3]) / 2)
        same_x_line_snippets.append((token, pos, xs, ys))

    # Fill the remaining words
    if (len(same_x_line_snippets) > 0):
        xy_sorted_snippets.append(sorted(same_x_line_snippets,
                                         key=lambda tpxy: sum(tpxy[2]) / len(tpxy[2])))

    return list(zip(*chain(*xy_sorted_snippets)))


def separate_by_sentence(token_list, pos_list, x_y_list):
    assert (len(token_list) == len(pos_list))
    batches = []
    expected_next_y = None
    expected_next_size = (None, None)

    # Separate X and Y coordinates for easier calculation
    x_lists = []
    y_lists = []
    for x_ys in x_y_list:
        xs = []
        ys = []
        for x, y in x_ys:
            xs.append(x)
            ys.append(y)
        x_lists.append(xs)
        y_lists.append(ys)

    # Sort the ocr words by ascending y coordinates
    sorted_ocr_details = sorted(zip(token_list, pos_list, x_lists, y_lists),
                                key=lambda tpxy: sum(tpxy[3]) / len(tpxy[3]))

    # Loop and further separate the ocr words if there is a large margin,
    # large font difference, or sentence split
    current_batch = []
    for token, unused_pos, xs, ys in sorted_ocr_details:
        current_batch.append((token, unused_pos, xs, ys))
        current_height = get_height(ys)

        is_big_margin = expected_next_y is not None and expected_next_y < ys[0]
        is_big_font_diff = expected_next_size[0] is not None and (
                expected_next_size[0] > current_height or expected_next_size[1] < current_height)
        is_end_of_sentence = token.endswith('.') and any([x.isalpha() for x in token])

        # Record the old batch and Create a new batch
        if (is_big_margin or is_big_font_diff):
            batches.append(sort_by_center_x(current_batch[:-1]))
            current_batch = current_batch[-1:]
            assert (len(current_batch) == 1)
        elif (is_end_of_sentence):
            batches.append(sort_by_center_x(current_batch))
            current_batch = []

        # Update expected y coordinates
        expected_next_y = ys[3] + (current_height * 0.75)
        expected_next_size = (current_height * 0.5, current_height * 1.5)

    # Record the remaining batch
    if (len(current_batch) > 0):
        batches.append(sort_by_center_x(current_batch))

    # Format the output to match the original input format.
    final_batches = []
    for batch in batches:
        if (len(batch) != 4):
            continue
        t, p, multi_xs, multi_ys = batch
        temp_x_ys = [list(zip(single_xs, single_ys)) for single_xs, single_ys in zip(multi_xs, multi_ys)]
        final_batches.append((t, p, temp_x_ys))

    return final_batches


def create_subgraphs(graph):
    graph_op = []

    def connected_component_subgraphs(G):
        for c in nx.connected_components(G):
            yield G.subgraph(c)

    graph_dict = {}

    def token_class_list(k, labeldict):
        tokens = (labeldict[k][0]).lower().split(' ')
        pos = [labeldict[k][1]] * len(tokens)
        x_y = [labeldict[k][2]] * len(tokens)
        return [tokens, pos, x_y]

    for c, d in graph.items():
        graph_dict[c] = {}
        labeldict = {}
        idx = 0
        for idx, value in enumerate(zip(d["tokens"], d['act_pos'], d['x_y'])):
            labeldict[idx] = [value[0], value[1], value[2]]
        G = nx.Graph()
        for i in range(d['nodes_count']):
            G.add_node(i)
        G.add_edges_from(d["edge"]["h"])
        G.add_edges_from(d["edge"]["v"])
        color_for_graph = {}
        colors = ['red', 'green', 'blue', 'yellow', 'black', 'pink', 'red', 'green', 'blue', 'yellow', 'black', 'pink']
        graphs = list(connected_component_subgraphs(G))
        for id_val, graph in enumerate(graphs):
            token_list = [token_class_list(k, labeldict)[0] for k in sorted(list(graph.nodes()))]
            token_list = list(chain(*token_list))
            pos_list = [token_class_list(k, labeldict)[1] for k in sorted(list(graph.nodes()))]
            pos_list = list(chain(*pos_list))
            x_y_list = [token_class_list(k, labeldict)[2] for k in sorted(list(graph.nodes()))]
            x_y_list = list(chain(*x_y_list))

            separated_sentences = separate_by_sentence(token_list, pos_list, x_y_list)
            if (len(separated_sentences) > 0):
                for sentence in separated_sentences:
                    if (len(sentence) != 3):
                        continue
                    t, p, xs_and_ys = sentence
                    graph_op.append([c, t, p, xs_and_ys])

    graph_IE_df = pd.DataFrame(graph_op, columns=['file_name', 'tokens', 'position', 'x_y'])
    return graph_IE_df


def custom_dataset(sub_graph_df, sym):
    new_target_dict = {'productname': 7,
                       'claim': 4,
                       'dosage': 3,
                       'ingredients': 2,
                       'companyname': 5,
                       'npn_number': 8,
                       'unknown': 1,
                       'name': 1
                       }

    # Mapping word to index
    def return_word_emb(tokens, word_dict):

        def find_word_in_dict(word):
            if word in word_dict.keys():
                return word_dict[word]
            else:
                return word_dict['<UNK>']

        word_emb = [[find_word_in_dict(row[i]) for i in np.arange(len(row))]
                    for index, row in enumerate(tokens)]
        return word_emb

    # Mapping char to index

    def return_char_emb(tokens, char_dict):
        def find_char_in_dict(char):
            if char in char_dict.keys():
                return char_dict[char]
            else:

                return char_dict['<UNK>']

        def create_char_emb(arr):
            return [[find_char_in_dict(row[i]) for i in np.arange(len(row))]
                    for index, row in enumerate(arr)]

        char_emb = [create_char_emb([row[i] for i in np.arange(len(row))])
                    for index, row in enumerate(tokens)]
        return char_emb

    def create_custom_dataset(X, pd_word_to_ind_dictionary, char_index, new_target_dict, datset_type):
        # mapping word tokens to idx
        word_emb = return_word_emb(X.tolist(), pd_word_to_ind_dictionary)
        # mapping chars inside tokens to idx
        char_emb = return_char_emb(X.tolist(), char_index)
        # creating ",datset_type," dataset
        dataset = {}
        for i in range(len(X)):
            dataset[i] = [char_emb[i], word_emb[i], X.tolist()[i]]

        return dataset

    with open(f'{DATA_DIR}/partitioned_final_data/primary_word_data/pd_word_to_ind_dictionary.pickle',
              'rb') as handle:
        pd_word_to_ind_dictionary = pickle.load(handle)
    with open(f'{DATA_DIR}/partitioned_final_data/character_dict/char_index.pickle', 'rb') as handle:
        char_index = pickle.load(handle)

    # Cannot do anything if there are no words to investigate
    if sub_graph_df.empty:
        return sub_graph_df

    if not sym:
        custom_result = create_custom_dataset(sub_graph_df.tokens.values,
                                              pd_word_to_ind_dictionary, char_index, new_target_dict, 'train')
    else:
        custom_result = create_custom_dataset(sub_graph_df.symspelled_tokens.values,
                                              pd_word_to_ind_dictionary, char_index, new_target_dict, 'train')
    return custom_result
