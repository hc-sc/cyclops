# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Model to suggest product name, company name, NPN and ingredients
#
# Actions:
#
#   propose_class       <image_texts>
#                   returns filled PRODUCT_PROPOSAL based on texts in input
#
#   get_empty_product_info
#                   returns empty product proposal dictionary
#
#   summarize_datasets  <list of interim proposal per image>
#                   returns final proposal after comparing page to page results
#
#   find_all_in_colname <list of extracted page content>, <column to extract>, <words to remove>
#                   returns all content under each dataset's certain column
#
#   find_ingredients    <list of interim proposal per page>
#                   returns final medicinal ingredient suggestion
#
#   find_product_name   <list of interim proposal per image>
#                   returns final product name suggestion
#
#   find_claims_data    <list of image content>
#                   returns final claim suggestions
#
#   suggest_type_worker <image content>, <look for ingredient?>
#                  returns temp proposal per page
#
#   suggest_type        <list of image content>, <interim proposal>
#                  returns temp proposal for all input pages
#
#   find_measurement    <image content>
#                   returns number of measurements found
#
#   add_npn_to_dataset  <interim proposal>, <partial image content>
#                   fills in interim proposal and
#                   returns unassigned image content
#
#   assign_remaining_ocrs <list of image content>, <interim proposal>,
#                         <original word column>, <prediction column>, <exact match column>,
#                         <commonly predicted class>, <commonly exactly matched class>, <look for ingredient?>
#                   proposes rest of the image content and puts them into interim proposal
#
#   find_common_category <list of image content>, <original word column>, <column to look for overlap>,
#                       <interim proposal>, <look for ingredient?>
#                   returns Found consistently appearing category? AND set of the most commonly appearing category
#
#   iterate_overlap     <column to look for overlap>, <list of image content>, <original word column>
#                   returns the most commonly appearing category AND consistently appearing category
#                   AND consistently appearing category for bigram words AND words in the image content
#
# (C) 2020 Statistics Canada
# Author: Joanne Yoon
# -----------------------------------------------------------
from models.symspell.sc_helper import load_ingredients, load_all_ingredients
from collections import Counter
import re
from itertools import chain
import copy

POSSIBLE_MEASUREMENTS = set(["grams", "g", "mg", "mcg", "iu", "ug", "ng"])

PROPOSAL_CODE_NAME = {
    7: 'prod_prpsl',
    4: 'claim_data',
    2: 'medical_data',
    5: 'comp_prpsl'
}
PROPOSAL_CODES = set(PROPOSAL_CODE_NAME.keys())
PRODUCT_CODE = 7
MED_CODE = 2
CLAIM_CODE = 4

PROPOSAL_CODE_TO_NAME_DICT = {
    7: 'product_name',
    4: 'claim_data',
    2: 'medical_data',
    5: 'comp_prpsl',
}

PROPOSAL_NAME_TO_TITLE = {
    'prod_prpsl': 'product_name',
    'claim_data': 'purpose',
    'medical_data': 'ingredient_name',
    'comp_prpsl': 'company_name',
    'npn_prpsl': 'npn',
}
PROPOSAL_NAMES = set(PROPOSAL_NAME_TO_TITLE.keys())

EMPTY_PRODUCT_PROPOSAL = {
    'prod_prpsl': [],
    'npn_prpsl': [],
    'comp_prpsl': [],
    'medical_data': [],
    'claim_data': [],
    'warnings': [],
    '_message': '',
    '_stderr': ''
}

EMPTY_PRODUCT_INFO = {
    "prod_prpsl": [],
    "prod_prpsl_size": [],
    "npn_prpsl": [],
    "comp_prpsl": [],
    "medical_data": [],
    "known_ingred": [],
    "claim_data": [],
    "num_measurements": 0,
}


def get_empty_proposal():
    return copy.deepcopy(EMPTY_PRODUCT_PROPOSAL)


def get_empty_product_info():
    return copy.deepcopy(EMPTY_PRODUCT_INFO)


def propose_class(image_texts):
    texts_per_image = []
    # For a list of texts in an image
    for ocr_in_one_image in image_texts:
        # Set up an empty dict to hold texts for a text
        json_dataset = get_empty_product_info()
        for ocr_graph in ocr_in_one_image:
            # Suggest based on one section at a time
            suggest_type(ocr_graph, json_dataset)
            json_dataset['claim_data'] += find_claims_data(ocr_graph)
        texts_per_image.append(json_dataset)

    # Compare page to page results to finalize the proposals
    final_json_dataset = summarize_datasets(texts_per_image)
    return final_json_dataset


def summarize_datasets(image_datasets):
    # Format the results into a proposal
    final_json_dataset = get_empty_proposal()
    final_json_dataset["prod_prpsl"] = [{PROPOSAL_NAME_TO_TITLE["prod_prpsl"]: x}
                                        for x in find_product_name(image_datasets)]
    final_json_dataset["npn_prpsl"] = [{PROPOSAL_NAME_TO_TITLE["npn_prpsl"]: x}
                                       for x in find_all_in_colname(image_datasets, "npn_prpsl",
                                                                    subwords_to_remove=set(['npn']))]
    final_json_dataset["comp_prpsl"] = [{PROPOSAL_NAME_TO_TITLE["comp_prpsl"]: comp}
                                        for comp in chain(*[x["comp_prpsl"] for x in image_datasets])]
    final_json_dataset["medical_data"] = [{PROPOSAL_NAME_TO_TITLE["medical_data"]: x,
                                           "_pdl": None,
                                           "_cdsa": None,
                                           "_medicinal": None}
                                          for x in find_ingredients(image_datasets)]
    final_json_dataset["claim_data"] = [{PROPOSAL_NAME_TO_TITLE["claim_data"]: claim,
                                          "_compliant": None}
                                         for claim in list(set(chain(*[x["claim_data"] for x in image_datasets])))]
    return final_json_dataset


def find_all_in_colname(json_datasets, colname, words_to_remove=set(), subwords_to_remove=set()):
    seen = set()
    # Merge all content under each dataset's certain column
    single_list = chain(*[data[colname] for data in json_datasets])
    single_list = chain(*[s.split() for s in single_list])
    # Skip words if any was specified in the parameter
    single_list = [data for data in single_list
                   if not (data in words_to_remove or data in seen or seen.add(data))]

    # Remove subwords from the suggestion
    for subword in subwords_to_remove:
        single_list = [i.replace(subword, '') for i in single_list]
    return [i for i in single_list if(len(i)>0)]


# Known ingredients to suggest to user
INGREDIENTS = load_ingredients()
INGREDIENTS = [' ' + i + ' ' for i in INGREDIENTS]
INGREDIENTS_all = load_all_ingredients() # remove claim proposals that consist of an ingredient (medicinal or nonmedicinal)
INGREDIENTS_all = [x.strip() for x in INGREDIENTS_all]

def find_ingredients(json_datasets):
    most_ingred_content = []
    known_ingred = []
    # Select page with the most medical words
    for d in json_datasets:
        if (len(list(chain(*[x.split() for x in d["medical_data"]]))) > len(most_ingred_content)):
            most_ingred_content = d["medical_data"]
            known_ingred = d["known_ingred"]

    # Loop through each ingredient proposal
    final_nhp_ingreds = []
    for input_m, is_known_list in zip(most_ingred_content, known_ingred):
        med_words = []
        is_known_med = []
        measurement_exists = False
        assert (len(input_m.split()) == len(is_known_list))
        # Find a measurement unit and clean text from numbers
        for w, ingred_is_known in zip(input_m.split(), is_known_list):
            # Filter out numbers and measurements
            word = re.sub(r"[0-9\.,]+", "", w)
            if (word in POSSIBLE_MEASUREMENTS):
                measurement_exists = True
            elif (len(word) > 0):
                med_words.append(word)
                is_known_med.append(ingred_is_known)

        # Filter out to known medical words if measurement is not in the sentence
        if (not measurement_exists):
            med_words = [word for word, ingred_is_known in zip(med_words, is_known_med) if (ingred_is_known)]
        if (len(med_words) > 0):
            med_content = ' ' + ' '.join(med_words) + ' '
            # Do word-wise match
            final_nhp_ingreds += [i.strip() for i in INGREDIENTS if (i in med_content)]

    # Prefer larger matches over smaller matches
    final_nhp_ingreds = set(final_nhp_ingreds)
    final_nhp_ingreds = [a for a in final_nhp_ingreds if (not any((a is not b and a in b) for b in final_nhp_ingreds))]
    return final_nhp_ingreds


def find_product_name(json_datasets):
    # Product name is likely in the same page as the npn
    prod_details = set()
    dataset_with_npn = [d for d in json_datasets if (len(d["npn_prpsl"]) > 0 and len(d["prod_prpsl"]) > 0)]
    dataset_to_search_for_prod_name = dataset_with_npn if (len(dataset_with_npn) > 0) else json_datasets

    # For each page where the product can be in
    for data in dataset_to_search_for_prod_name:
        assert (len(data["prod_prpsl_size"]) == len(data["prod_prpsl"]))

        # Find large sized text
        text_size_limit_for_prod = max(data["prod_prpsl_size"]) * 0.5
        for prod_text, prod_size in zip(data["prod_prpsl"], data["prod_prpsl_size"]):
            if (prod_size > text_size_limit_for_prod):
                prod_details.add(prod_text)
                # print(prod_text, "Good Size", prod_size)
            # else:
            #    print(prod_text, "Too Small", prod_size)
    return prod_details


def find_claims_data(ocr_details):
    """ Creates claim proposals and appends to empty claim_data section
        of the product proposal dictionary
        
    Parameters
    ------
    ocr_details: list of image content
        
    Returns
    -----
    claim_instances_sent: list of claim proposals
        Each element in the list is a different claim proposal
    """
    claim_prod = []
    claim_found = False
    graph_claim = []
    claim_instances = []
    count = 0
    for idx1, graph in enumerate(ocr_details):
        count += 1
        if claim_found:
            word = graph['corrected_ocr_word']
            if CLAIM_CODE == graph['classifier_class'] or CLAIM_CODE in graph['ocr_match_class'] or \
                CLAIM_CODE in graph['symspell_match_class'] or CLAIM_CODE in graph['bigram_match_class']:
                graph_claim.append(word)
                if (count == len(ocr_details)):
                    claim_instances.append(graph_claim)
                    graph_claim = []
            else:
                claim_found = False
                claim_instances.append(graph_claim)
                graph_claim = []
        else:
            if CLAIM_CODE == graph['classifier_class'] or CLAIM_CODE in graph['ocr_match_class'] or \
                CLAIM_CODE in graph['symspell_match_class'] or CLAIM_CODE in graph['bigram_match_class']:
                word = graph['corrected_ocr_word']
                graph_claim.append(word)
                if (count == len(ocr_details)):
                    claim_instances.append(graph_claim)
                    graph_claim = []
                claim_found = True
    if len(claim_instances) > 0:
        graph_claim.append(claim_instances)
    claim_prod.extend(claim_instances)
    claim_instances_sent = list(set([' '.join(x) for x in claim_prod]))
    claim_instances_sent = [x for x in claim_instances_sent
                            if len(x.split()) > 1 and
                            x.split() != POSSIBLE_MEASUREMENTS and
                            len(x) > 5 and x not in INGREDIENTS_all]

    return claim_instances_sent


def get_height(ocr_detail):
    xy1, xy2, xy3, xy4 = ocr_detail["coordinates"]
    average_height = ((xy4[1] - xy1[1]) + (xy3[1] - xy2[1])) / 2
    return average_height

def get_width(ocr_detail):
    xy1, xy2, xy3, xy4 = ocr_detail["coordinates"]
    average_width = ((xy2[0] - xy1[0]) + (xy3[0] - xy4[0])) / 2
    return average_width

def suggest_type_worker(ocr_details, look_for_ingred):
    new_entry = get_empty_product_info()

    if (len(ocr_details) == 0):
        return new_entry

    # Pick out the NPNs first
    ocr_details_without_npn = add_npn_to_dataset(new_entry, ocr_details)
    if (len(ocr_details_without_npn) == 0):
        return new_entry

    found_common_prediction, common_prediction = find_common_category(
        ocr_details_without_npn, 'corrected_ocr_word',
        'classifier_class', new_entry, look_for_ingred)
    if (found_common_prediction):
        return new_entry

    found_common_exact_match, common_exact_match = find_common_category(
        ocr_details_without_npn, 'corrected_ocr_word',
        'symspell_match_class', new_entry, look_for_ingred)
    if (found_common_exact_match):
        return new_entry

    # If batch ends with a period and no uniform class was predicted
    # Then it is probably a description rather than a class
    if (not look_for_ingred
            and ocr_details[-1]['raw_ocr_word'].endswith('.')
            and any([x.isalpha() for x in ocr_details[-1]['raw_ocr_word']])
            and len(common_prediction) == 0):
        return new_entry

    assign_remaining_ocrs(ocr_details_without_npn, new_entry,
                          'corrected_ocr_word', 'classifier_class',
                          'symspell_match_class',
                          common_prediction, common_exact_match, look_for_ingred)

    assert (len(new_entry["prod_prpsl_size"]) == len(new_entry["prod_prpsl"]))
    return new_entry


def suggest_type(ocr_details, json_dataset):
    num_ingred_measurements = find_measurement(ocr_details)

    look_for_ingred = num_ingred_measurements > 0

    # Put the ocr words into the right category under json_dataset
    # If irrelevant, the ocr word will be discarded
    new_entry = suggest_type_worker(ocr_details, look_for_ingred)

    # Skip if no valuable information found
    if (not any([len(new_entry[name]) > 0 for name in PROPOSAL_NAMES])):
        return json_dataset

    # Join the words within the same batch
    # And record it in input json_dataset
    for name in PROPOSAL_NAMES:
        if (len(new_entry[name]) > 0):
            json_dataset[name].append(' '.join(new_entry[name]))

    # Find the average size
    if (len(new_entry["prod_prpsl_size"]) > 0):
        json_dataset["prod_prpsl_size"].append(
            sum(new_entry["prod_prpsl_size"]) / len(new_entry["prod_prpsl_size"]))

    if (len(new_entry["known_ingred"]) > 0):
        json_dataset["known_ingred"].append(new_entry["known_ingred"])

    return json_dataset


def sort_by_center_x(snippets):
    xy_sorted_snippets = []

    same_x_line_snippets = []
    bottom_ys = []
    for s in snippets:
        center_y = sum([y for x, y in s['coordinates']]) / len(s['coordinates'])

        if (len(bottom_ys) > 0 and center_y > sum(bottom_ys) / len(bottom_ys)):
            bottom_ys = []
            xy_sorted_snippets.append(sorted(same_x_line_snippets,
                                             key=lambda k: (sum([x for x, y in k['coordinates']])) / len(
                                                 k['coordinates'])))
            same_x_line_snippets = []

        bottom_ys.append((s['coordinates'][2][1] + s['coordinates'][3][1]) / 2)
        same_x_line_snippets.append(s)

    # Fill the remaining
    xy_sorted_snippets.append(sorted(same_x_line_snippets,
                                     key=lambda k: (sum([x for x, y in k['coordinates']])) / len(k['coordinates'])))
    return list(chain(*xy_sorted_snippets))


def find_measurement(all_ocr_details):
    num_measurements = 0
    digit_exists = False

    for o in all_ocr_details:
        # once true, do not change to false
        # If measurement is in the ocr word
        if (re.sub(r"[0-9\., ]+", "", o['corrected_ocr_word']) in POSSIBLE_MEASUREMENTS):
            num_measurements += 1
        if (not digit_exists):
            digit_exists = any([c.isdigit() for c in o['raw_ocr_word']])

    return (num_measurements if (digit_exists) else 0)


def is_npn(ocr_word):
    return 'npn' in ocr_word['raw_ocr_word'] or (ocr_word['corrected_ocr_word'].isnumeric()
                                                 and len(ocr_word['corrected_ocr_word']) > 6 and len(
                ocr_word['corrected_ocr_word']) < 10)


# Take out the NPN words and put them into json dataset
def add_npn_to_dataset(entry, ocr_details):
    ocr_details_without_npn = []
    for ocr_word in ocr_details:
        # print(ocr_word['corrected_ocr_word'], ocr_word['raw_ocr_word'], '\t>', ocr_word['classifier_class'],
        #      '\t+', ocr_word['symspell_match_class'])

        if (is_npn(ocr_word)):
            entry["npn_prpsl"].append(ocr_word['corrected_ocr_word'])
        else:
            ocr_details_without_npn.append(ocr_word)

    return (ocr_details_without_npn)


# Fill the json with the class that the ocr word belongs to
def assign_remaining_ocrs(ocr_details, entry,
                          word_colname, predicted_class_colname, exact_class_colname,
                          common_predicted, common_exact, look_for_ingred):
    if (not isinstance(common_exact, set)):
        common_exact = set(common_exact)

    for ocr_word in ocr_details:
        word = ocr_word[word_colname]

        # Empty strings cannot have a category
        if (len(ocr_word[exact_class_colname]) == 0):
            continue

        # Compare the class that the word was classified to (single)
        # vs. where it existed in the database (can be multiple)
        prediction = ocr_word[predicted_class_colname]
        is_pred_valid = prediction in PROPOSAL_CODES
        exact_match_set = set(ocr_word[exact_class_colname])
        chosen_classes = set()
        if (look_for_ingred
                and (MED_CODE == prediction or MED_CODE in exact_match_set)):
            chosen_classes.add(MED_CODE)
        elif (prediction in common_predicted and is_pred_valid):
            chosen_classes.add(prediction)
        elif (not exact_match_set.isdisjoint(common_exact)):
            chosen_classes = exact_match_set & common_exact
        elif (prediction in exact_match_set or is_pred_valid):
            chosen_classes.add(prediction)
        elif (len(ocr_details) == 1 and 0 < len(exact_match_set) < MAX_POSSIBLE_CATEGORY):
            chosen_classes = exact_match_set
        else:
            continue

        for c in chosen_classes:
            entry[PROPOSAL_CODE_NAME[c]].append(word)

        if (PRODUCT_CODE in chosen_classes):
            entry["prod_prpsl_size"].append(get_prod_height(ocr_word))

        if (MED_CODE in chosen_classes):
            entry["known_ingred"].append(MED_CODE in ocr_word[exact_class_colname])
    return None

#If height is bigger than width, unlikely to be product
def get_prod_height(ocr_word):
    height = get_height(ocr_word)
    width = get_width(ocr_word)
    return height if (height<width) else 0



MAX_POSSIBLE_CATEGORY = 3


# Which class is commonly appearing in the list ocr words
def find_common_category(ocr_details, word_colname, class_colname, entry, look_for_ingred):
    if (len(ocr_details) < 2):
        return (False, set())

    most_common_categories, overlapping_category, bigram_categories, words = (
        iterate_overlap(class_colname, ocr_details, word_colname))

    # If no words were valid to find overlap
    if (overlapping_category is None):
        return (False, set())

    # If all the words had a category in common, assign the words to category
    # Otherwise, do nothing
    if (len(overlapping_category) > 0):
        if (len(overlapping_category) > 1):
            likely_category = overlapping_category
            if (look_for_ingred and MED_CODE in overlapping_category):
                overlapping_category = {MED_CODE}
            elif (len(bigram_categories) > 0):
                # Filter down multiple candidates via bigrams, if possible
                bigram_counts = Counter(bigram_categories).most_common()
                bigram_common, _ = zip(*filter(
                    lambda t: t[1] >= bigram_counts[0][1], bigram_counts))
                category_duplicates = set(bigram_common) & overlapping_category
                if (len(category_duplicates) > 0):
                    likely_category = category_duplicates

                overlapping_category = likely_category

        # Fill the current words under the overlapping category
        for c in overlapping_category:
            entry[PROPOSAL_CODE_NAME[c]] = words

        # Record extra information depending on which category was filled
        if (PRODUCT_CODE in overlapping_category):
            entry["prod_prpsl_size"] = [get_prod_height(o) for o in ocr_details]
        if (MED_CODE in overlapping_category):
            entry["known_ingred"] = [MED_CODE in o['symspell_match_class'] for o in ocr_details]
        # print("\t", class_colname, "OVERLAP FOUND:", overlapping_category, words)
        assert (len(entry["medical_data"]) == len(entry["known_ingred"]))
        return (True, overlapping_category)

    return (False, set(most_common_categories))


# Check if classifier predicted the same category across the words
def iterate_overlap(class_colname, ocr_details, word_colname):
    overlapping_category = None
    all_categories = []
    words = []
    bigram_categories = []
    valid_cases = 0
    for ocr_word in ocr_details:
        # Represent the category as a set
        categories = (set(ocr_word[class_colname])
                      if (isinstance(ocr_word[class_colname], set)
                          or isinstance(ocr_word[class_colname], list))
                      else set([ocr_word[class_colname]]))

        words.append(ocr_word[word_colname])

        # Skip if the word has no class or too many classes
        if (ocr_word[word_colname].isnumeric()
                or len(categories) == 0
                or len(categories) > MAX_POSSIBLE_CATEGORY):
            continue

        all_categories += categories
        # check if the past category reappears
        if (overlapping_category is None):
            overlapping_category = categories
        else:
            overlapping_category = overlapping_category & categories

        # Record common bigrams
        if (len(ocr_word["bigram_match_class"]) > 0):
            bigram_classes, _ = zip(*ocr_word["bigram_match_class"])
            bigram_categories += bigram_classes

        valid_cases += 1

    if (valid_cases < 2):
        overlapping_category = None
    else:
        overlapping_category = set([c for c in overlapping_category if (c in PROPOSAL_CODE_NAME)])

    # Return the most common category as an FYI
    most_common_categories = set()
    if (len(all_categories) > 0):
        all_categories_counts = Counter(all_categories).most_common()
        most_common_categories, _ = zip(*filter(
            lambda t: t[1] >= all_categories_counts[0][1], all_categories_counts))

    return most_common_categories, overlapping_category, bigram_categories, words
