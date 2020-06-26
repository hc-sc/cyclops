# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# AICore model
#
# Actions:
#    extract   [<images_filenames>]
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero, Joanne Yoon, Anurag Bejju, Blair Drummond
# -----------------------------------------------------------
from models.OCR.ocr_helper import ocr_images, OCR_Args, OCR_Model
import models.graphie.generate_graph_helper as gh
import models.classifier.classifier_helper as ch
import models.symspell.sc_helper as sc
import models.proposal.proposal_helper as ph
from loguru import logger
import os
import shutil
import pickle
import datetime
from hashlib import sha256
import traceback

#############################################
#############################################
###
### Use MongoDB to cache already computed images.
### Proposed schema:
###
### {
###     sha256: str,      <- main key
###     ocr_result: json,
###     file_raw: bytes,  <- store the whole file
###     date: str,
###     user:str          <- in the future we might have mutli-user
### }
###
### Interact with the `image_cache` object!
###
MONGO = os.getenv('MONGO_URL')
if MONGO:
    MONGO_MAPI_USER = os.getenv('MONGO_MAPI_USER')
    MONGO_MAPI_DB = os.getenv('MONGO_MAPI_DB')
    MONGO_MAPI_PASS = os.getenv('MONGO_MAPI_PASS')

    assert MONGO_MAPI_PASS

    import pymongo

    mongo = pymongo.MongoClient(
        f"mongodb://{MONGO}/{MONGO_MAPI_DB}",
        username=MONGO_MAPI_USER,
        password=MONGO_MAPI_PASS
    )
    image_cache = mongo[MONGO_MAPI_DB].images


def sha_from_file(filename):
    """ The for loop computes the sha in 4096 byte blocks to avoid
    reading the whole file into memory. """

    logger.info(f"Computing {filename} sha256")
    sha256_hash = sha256()
    with open(filename, "rb") as f:
        # Read and update hash string value in blocks of 4K
        for byte_block in iter(lambda: f.read(4096), b""):
            sha256_hash.update(byte_block)
        return (sha256_hash.hexdigest())


###
#############################################


class AICore(object):
    """AICore model connects to OCR, SpellCheck, Graph,
    and Classification modules"""

    # static variable
    ocr_args = OCR_Args()
    ocr_model = OCR_Model(ocr_args)

    @staticmethod
    def recognize_text(*img_filenames):
        """ Takes a list of image filenames and returns
            the extracted words
            (i.e., npn, company name, product name, ...)

            Uses a mongodb cache to fetch previously computed images

        Parameters
        ----------
        img_filenames: list of str
            List containing image filenames. The path are absolute.
            No need to join/concatenate

        Outputs
        --------
        dict
            OCR

            OCR -> {filename: {
                "filename":       filename,
                "all_text":       [text, ...],
                "phrase":         [phrase_with_coordinates, ...]
            }}

            <phrase_with_coordinates> -> {"text": str,                    # Extracted OCRs
                                          "all_x": list of number in str, # Extracted OCR's x coordinates clockwise
                                          "all_y": list of number in str} # Extracted OCR's y coordinates clockwise

        return {
            {"awesomeworks-isolate-6lb_1.jpg": {
                "filename": "awesomeworks-isolate-6lb_1.jpg",
                "all_text": ["whey", "protein"],
                "phrase" : [
                    {"text":"whey",
                     "all_x": ["57.54903", "155.188", "154.43016", "56.791172"],
                     "all_y": ["62.038715", "64.4797", "94.79409", "92.35311"]},
                    {"text": "protein",
                     "all_x": ["57.333332", "153.33333", "153.33333", "57.333332"],
                     "all_y": ["92.0", "92.0", "106.666664", "106.666664"]}
            }}
        }
        """

        if not MONGO:
            logger.info("No mongo; not using a cache")
            return ocr_images(img_filenames, AICore.ocr_model, AICore.ocr_args)

        else:
            now = str(datetime.datetime.utcnow())

            def lookup(sha):
                res = image_cache.find_one({"sha256": sha}, {"_id": 0, 'image_raw': 0})
                if res is None or 'ocr_result' not in res:
                    return None
                else:
                    # Note the need to unpickle
                    return pickle.loads(res['ocr_result'])

            # Get images from cache if we can
            imgs = {}
            for img in img_filenames:
                sha = sha_from_file(img)
                imgs[img] = {
                    "sha256": sha,
                    "ocr_result": lookup(sha)
                }
                if imgs[img]['ocr_result'] is not None:
                    logger.info(f"Using Mongo cache for {img} with sha {sha} --- skipping OCR")

            # Process anything not in mongo
            remaining = [
                img for img in imgs
                if imgs[img]['ocr_result'] is None
            ]
            logger.info("Images left to process " + str(remaining))

            # TODO Add user authentication and metadata
            # Currently hardcoded to "default"
            newly_processed = ocr_images(remaining, AICore.ocr_model, AICore.ocr_args)
            for img in newly_processed:
                # Add the result to the imgs dict
                imgs[img]['ocr_result'] = newly_processed[img]

                # Upload new things to mongo
                with open(img, 'rb') as f:
                    # img_bytes = f.read()
                    d = {
                        "sha256": imgs[img]['sha256'],
                        "date": now,
                        "user": "default",
                        "ocr_result": pickle.dumps(newly_processed[img])
                    }
                    #   "image_raw" : pickle.dumps(img_bytes),
                    image_cache.insert_one(d)

            # We processed everything, right?
            assert all(imgs[img]["ocr_result"] is not None for img in imgs)

            # We don't need to track the sha anymore. Conform to the api.
            return {img: imgs[img]['ocr_result'] for img in imgs}

    @staticmethod
    def extract(*img_filenames):
        """ Takes a list of image filenames and returns a
            product proposal containing the extracted words and classifications
            (i.e., npn, company name, product name, ...)

        Parameters
        ----------
        img_filenames: list of str
            List containing image filenames. The path are absolute.
            No need to join/concatenate

        Outputs
        --------
        dict
            Product proposal

            Product_Proposal -> {
                "prod_prpsl":       [<name>, ...],       # Product Name List
                "npn_prpsl":        [<npn>, ...],        # NPN List
                "comp_prpsl":       [<company>, ...],    # Company Name List
                "medical_data":     [<ingredient>, ...]  # Ingredient List
                "claim_data:        [<claim>, ...]       # Claim List
                ]
            }

            <name>       -> {"product_name": str}        # Product Name
            <npn>        -> {"npn":          str}        # NPN
            <company>    -> {"company_name": str}        # Company Name
            <ingredient> -> {"ingredient_name": str}     # Ingredient
            <claim>      -> {"purpose": str}             # Claim


            All dict keys are in lowercase.
            Keys with multiple words should be separated by '_'.

            Different keys not mentioned in the Documentation should
            start with the underscore character '_'.


        Internal Calls
        --------
        """

        total_time = 0
        logger.add("cyclops_function_call.log")
        logger.info("{filename} : Started AICore Pipeline!", filename=img_filenames[0])
        try:
            # Preprocessing modifies the files in-place.
            # This causes some problems. Therefore save the old images.
            error_msg = "Unable to move files!"
            tmp_imgs = ['/tmp' + f for f in img_filenames]
            logger.info("Moving {files} to {newfiles}", files=str(img_filenames), newfiles=str(tmp_imgs))
            for (src, dest) in zip(img_filenames, tmp_imgs):
                os.makedirs(os.path.dirname(dest), exist_ok=True)
                shutil.copyfile(src, dest)
            img_filenames = tmp_imgs

            error_msg = "Unable to preprocess_images!"
            output, execution_time = gh.calulate_function_time(gh.preprocess_images, *img_filenames)

            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Preprocessing",
                        exect=round(execution_time, 2))

            total_time = total_time + execution_time
            error_msg = "Unable to recognize_text in image!"
            output, execution_time = gh.calulate_function_time(AICore.recognize_text, *img_filenames)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="OCR",
                        exect=round(execution_time, 2))

            len_status = False
            for k, v in output.items():
                if len(v['all_text']) == 0:
                    len_status = True
                    error_msg = "Image: " + k + " has no text!"
                    break
            if not len_status:
                error_msg = "Unable to convert ocr to dataframe!"

            total_time = total_time + execution_time
            output, execution_time = gh.calulate_function_time(gh.covert_ocr_to_df, output)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Dataframe Conversion",
                        exect=round(execution_time, 2))

            total_time = total_time + execution_time
            error_msg = "Unable to generate_graph!"
            output, execution_time = gh.calulate_function_time(gh.generate_graph, output, True)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Graph Coversion",
                        exect=round(execution_time, 2))

            total_time = total_time + execution_time
            error_msg = "Unable to create_subgraphs!"
            output, execution_time = gh.calulate_function_time(gh.create_subgraphs, output)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Sub-Graph Creation",
                        exect=round(execution_time, 2))
            # print ("Sub-Graph Creation done in %.2f seconds" %execution_time)
            total_time = total_time + execution_time
            error_msg = "Unable to spellcheck words!"
            graph_ie_df, execution_time = gh.calulate_function_time(sc.spellcheck_words, output)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Spell check",
                        exect=round(execution_time, 2))
            # print ("Spell check done in %.2f seconds" %execution_time)
            total_time = total_time + execution_time
            error_msg = "Unable to build custom_dataset!"
            output, execution_time = gh.calulate_function_time(gh.custom_dataset, graph_ie_df, True)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Custom dataset for ML Model",
                        exect=round(execution_time, 2))

            total_time = total_time + execution_time
            error_msg = "Unable to classify images!"
            output, execution_time = gh.calulate_function_time(ch.classify_images, output, graph_ie_df)
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Classifier using ML Model",
                        exect=round(execution_time, 2))

            error_msg = "Unable to propose class!"
            output, execution_time = gh.calulate_function_time(ph.propose_class, output)
            total_time = total_time + execution_time
            logger.info("{filename} : {process} process took {exect} seconds!", filename=img_filenames[0],
                        process="Total execution",
                        exect=round(total_time, 2))
        except:
            output = ph.get_empty_proposal()
            output['_message'] = error_msg
            output['_stderr'] = traceback.format_exc()
        finally:
            return output
