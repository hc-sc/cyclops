# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Corrects spelling and Matches the words to class that share the same word in its vocab.
#
# Actions:
#
#   spellcheck_words            <dataframe>
#                   returns the input dataframe with 4 new columns added
#                           1. symspelled_tokens: list of corrected word
#                           2. ocr_word_match: the classes that had the original word in its vocabulary
#                           3. symspell_word_match: the classes that had the corrected word in its vocabulary
#                           3. bigram_match_class: the classes that had the corrected bigram in its vocabulary
#
#   remove_junk                  <word>
#                   returns the word without unwanted symbols
#
#   symspell_tokens             <list of words>
#                   returns 1. list of corrected word
#                           2. the classes that had the original word in its vocabulary
#                           3. the classes that had the corrected word in its vocabulary
#
#   create_dictionary           <list of words>, <pickle path to save spell checker>,
#                               <num spell error allowed>, <prefixe length>
#                   returns spell checker
#
#   load_ingredients
#                   returns set of unique ingredient entries
#
#   load_spellchecker_and_vocab <pickle path to save spell checker>
#                  returns  1. spell checker
#                           2. combined vocabulary
#                           2. dictionary linking the class code to its vocabulary
#                           2. dictionary linking the class code to its bigram vocabulary
#
#   find_bigram                 <first word>, <second word>
#                  returns the list of which classes contain the bigram and what the words are
#
#   find_bigram_in_list         <list of words>
#                   returns the list of bigrams found
# (C) 2020 Statistics Canada
# Author: Joanne Yoon
# -----------------------------------------------------------

import re
from symspellpy.symspellpy import SymSpell, Verbosity  # import the module
import os
from pathlib import Path
from collections import Counter

VOCAB_FILENAME_TO_CODE = {
    'medicinal': 2,
    'claims': 4,
    'companyaddress': 5,
    'companynames': 5,
    'productnames': 7,
    'npn': 8,
}
PROPOSAL_CODES = [2, 4, 5, 7, 8]


DATA_DIR = "/data"


def spellcheck_words(sub_graph_df):
    # Cannot do any spell checking if there are no words
    if(sub_graph_df.empty):
        return sub_graph_df

    (sub_graph_df['symspelled_tokens'], sub_graph_df['ocr_word_match'],
     sub_graph_df['symspell_word_match']) = zip(*
                                                sub_graph_df.tokens.apply(symspell_tokens))
    sub_graph_df['bigram_match_class'] = sub_graph_df['symspelled_tokens'].apply(find_bigram_in_list)
    return sub_graph_df


# Text is array of words
def remove_junk(word):
    word = word.replace('(s)', '')
    word = re.sub(r'[?|$|.|!|"|\"|,|;|:|*|#|%|+|/|(|)|{|}]', r'', word.lower())
    return word


def symspell_tokens(list_of_words):
    corrected_words = []
    ocr_match_classes = []
    symspell_match_classes = []

    for input_word in list_of_words:
        word = remove_junk(input_word)
        corrected_word = word
        ocr_match_class = []

        if (len(word) <= 0):
            continue

        # If the word is in the vocabulary, do not fix its spelling
        word_is_in_vocab = word in VOCABULARY
        if (word_is_in_vocab):
            #Find which class the vocab is in
            for target_vocab_type, target_vocab in TARGET_VOCAB_DICT.items():
                if (word in target_vocab):
                    ocr_match_class.append(target_vocab_type)
        elif (any([a.isalpha() for a in input_word]) or
              (input_word.isdigit() and (10 > len(input_word) > 5))):
            #Try to fix the spelling of npn-likely numbers and words with an alphabet
            try:
                suggested_words = SYM_SPELL.lookup(word, Verbosity.ALL, 1, include_unknown=False)
                if (len(suggested_words) > 0):
                    corrected_word = suggested_words[0].term
                else:
                    suggested_words = SYM_SPELL.lookup(word, Verbosity.ALL, 2, include_unknown=False)
                    if (len(suggested_words) > 0):
                        corrected_word = suggested_words[0].term
            except IndexError:
                print("Error In Spell Checking... Returning original word: ", word)

        symspell_match_class = []
        if (word_is_in_vocab):
            #If spelling was not fixed, use the existing list of matched words
            symspell_match_class = ocr_match_class
        else:
            #Find which class the corrected vocab is in
            for target_vocab_type, target_vocab in TARGET_VOCAB_DICT.items():
                if (corrected_word in target_vocab):
                    symspell_match_class.append(target_vocab_type)

        corrected_words.append(corrected_word)
        ocr_match_classes.append(ocr_match_class)
        symspell_match_classes.append(symspell_match_class)

    return corrected_words, ocr_match_classes, symspell_match_classes


def create_dictionary(well_spelled_words, spell_check_pickle_filename,
                      max_edit_distance_lookup, prefix_length):
    SYM_SPELL = SymSpell(max_edit_distance_lookup, prefix_length)

    # Format vocab file for the sym spell
    symspell_vocab_file = 'temp_vocab_file.txt'
    with open(symspell_vocab_file, 'w', encoding='latin-1') as output:
        for key, count in Counter(well_spelled_words).items():
            output.write("{} {}\n".format(key, count))

    # load dictionary with the frequencies
    if not SYM_SPELL.load_dictionary(symspell_vocab_file, term_index=0,
                                     count_index=1, encoding='latin-1'):
        print("Dictionary file not found")
        return None

    SYM_SPELL.save_pickle(spell_check_pickle_filename)

    return SYM_SPELL

def load_ingredients():
    ingred_path = f'{DATA_DIR}/symspell/Vocabulary/extracted_medicinal.txt'
    with open(ingred_path, 'r', encoding='latin-1') as input:
        input_texts = [remove_junk(i) for i in input.read().split('\n')]
    return set(input_texts)

# for claim proposal
def load_all_ingredients():
    all_ingredients = []
    medicinal_ingredients = load_ingredients()
    nonmedicinal_ingred_path = f'{DATA_DIR}/symspell/Vocabulary/extracted_nonmedicinal.txt'
    with open(nonmedicinal_ingred_path, 'r', encoding='latin-1') as input:
        nonmedicinal_ingredients = [remove_junk(i) for i in input.read().split('\n')]
    all_ingredients.extend(medicinal_ingredients)
    all_ingredients.extend(nonmedicinal_ingredients)
    return set(all_ingredients)


def load_spellchecker_and_vocab(input_spell_check_pickle_filename):
    class_vocab_path = f'{DATA_DIR}/symspell/Vocabulary'

    vocab = set()
    target_vocab_dict = {}
    target_bigram_dict = {}
    # Read each vocab file and get the words
    # vocab: key is the class and value is the words in such class
    for class_vocab_file in Path(class_vocab_path).rglob("*.txt"):
        with open(class_vocab_file, 'r', encoding='latin-1') as input:
            filename = os.path.splitext(os.path.basename(class_vocab_file))[0]
            filename = filename.replace('extracted_', '')
            if (filename not in VOCAB_FILENAME_TO_CODE):
                continue

            # Don't include digits that are not npns
            input_text = remove_junk(input.read())
            all_lines = input_text.split('\n')
            all_words = input_text.split()
            if ('npn' not in filename):
                all_words = [w for w in all_words if (not w.isdigit())]
            all_words = set(all_words)
            vocab |= all_words
            target_vocab_dict[VOCAB_FILENAME_TO_CODE[filename]] = all_words

            # Create bigrams based on the input vocabulary
            bigram = [" ".join(b) for line in all_lines
                      for b in zip(line.split(" ")[:-1], line.split(" ")[1:])]
            target_bigram_dict[VOCAB_FILENAME_TO_CODE[filename]] = set(bigram)

    # Load the Sym spell made up of the words in the vocab_path
    max_edit_distance_lookup = 2
    prefix_length = 7
    SYM_SPELL = SymSpell()
    if (os.path.exists(input_spell_check_pickle_filename)):
        SYM_SPELL.load_pickle(filename=input_spell_check_pickle_filename)
    else:
        SYM_SPELL = create_dictionary(vocab, input_spell_check_pickle_filename,
                                      max_edit_distance_lookup, prefix_length)

    assert (len(target_vocab_dict) == 5)
    return SYM_SPELL, vocab, target_vocab_dict, target_bigram_dict


spell_check_pickle_filename = f'{DATA_DIR}/symspell/all_class_spell_checker_without_non_npn_digits.pickle'
SYM_SPELL, VOCABULARY, TARGET_VOCAB_DICT, TARGET_BIGRAM_DICT = (
    load_spellchecker_and_vocab(spell_check_pickle_filename))


def find_bigram(corrected_word, next_corrected_word):
    if (corrected_word is None or next_corrected_word is None):
        return []
    # If the bigram is of short words, then not as valuable
    if (len(corrected_word) < 4 and len(next_corrected_word) < 4):
        return []

    current_bigram = corrected_word + ' ' + next_corrected_word
    bigram_match_classes = []
    if (next_corrected_word is not None):
        for target_bigram_type, target_bigram in TARGET_BIGRAM_DICT.items():
            if (current_bigram in target_bigram):
                bigram_match_classes.append((target_bigram_type, current_bigram))
    return bigram_match_classes


def find_bigram_in_list(tokens):
    # Check for bigram match with the next word
    bigram_match_classes = []
    for current_word in tokens:
        current_match = []
        for next_word in tokens:
            if (current_word == next_word):
                continue
            current_match += find_bigram(current_word, next_word)

        bigram_match_classes.append(current_match)

    assert (len(bigram_match_classes) == len(tokens))
    return bigram_match_classes
