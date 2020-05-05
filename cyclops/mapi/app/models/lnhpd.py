# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Model connecting to the LNHPD database
#
# Actions:
#
#   top_company_search_matches    <query> 
#                   returns top company matches from query
#   top_npn_search_matches <query>
#                   returns top npn matches from query
#.  top_product_search_matches <query>
#                   returns top product matches from query
#   best_product_matches <npn>, <company_name>, <product_name>
#                    Identifies possible products based in the predicted
#                   values of npn, company_name, product_name
#
#   get_ingredients_data      <lnhpd_id> 
#                   Retrieves ingredients data from lnhpd_id
#   get_claim_data      <lnhpd_id> 
#                   Retrieves claim data  from lnhpd_id
#   
#   match_ingredients <ingredientA> <ingredientB>
#                   Compares ingredientA to ingredientB, returns A^B, A-B, B-A
#                       
#
#   validate_md < ingredientA >
#                   Validate the ingredient of < ingredientA >
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------

"""
Modified on Mon Feb 24 09:20:35 2020
@author: nick
"""

"""
Modified on Frid Mar 3 11:40:00 2020
Moved all functionalities to sqlite3 database. 

@autor: Andres Solis Montero
"""


import pandas as pd
import os, re, json, requests
import numpy as np
import pickle
import glob
from os.path import join, exists
from sqlite_utils    import Database
from sqlite_utils.db import NotFoundError
from unidecode import unidecode
import sqlite3

# from models.main import *
# TODO confirm with Irshad that NHPID dataset is equivalent
#  to the alias dataset... so if it is in there, we don't
#  need to check if it is banned...

class LNHPD(object):
    """Model for LNHPD database queries"""
    
    MODEL_FOLDER = '/data/'
    #MODEL_FOLDER = './data/'
    
    SQLITE_FILE  = 'lnhpd.db'
    ALIAS_FILE   = "synonyms.csv"
    CDSA_FILE    = "cdsa.csv"
    PDL_FILE     = "pdl.csv"
    SCH_A_FILE   = "scha.csv"

    @staticmethod
    def __assoc_query__(db, query, params):
        with db.conn:
            c = db.conn.cursor()
            c.execute(query, params)
            keys = [_[0] for _ in c.description]
            values = c.fetchall()
            return [dict(zip(keys, row)) for row in values]
        return []

    @staticmethod
    def top_company_search_matches(query_company, top_output=10):
        """ Returns a list of the the top_output company names (strings), 
            given a query_company string that the user is filling out from 
            the webapp form.
            
            Parameters
            ----------
            query_company: str
                    User typed (or clicked from proposal suggestion) company name
           
            top_output: int
                    number of top hits returned
                    
            Returns
            -------
            list of dictionaries (company names)
            
            output -> [<company>]
            <company> -> {"company_name": str}
        """
        db      = Database(join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE))
        query_company = LNHPD.__token_removal__(query_company)
        query  =  (
            f"SELECT DISTINCT company_name FROM search "
            f"WHERE search "
            f"MATCH 'company_name:\"{query_company}\"*' "
            f"ORDER BY rank, company_name LIMIT ?"
        )
        return LNHPD.__assoc_query__(db, query, [top_output])
       
    @staticmethod
    def top_npn_search_matches(query_npn, top_output=10):
        """ Returns a list of the the top_output npn (strings), 
            given a query_npn string that the user is filling out from 
            the webapp form.
            
            Parameters
            ----------
            query_npn: str
                    User typed (or clicked from proposal suggestion) npn
           
            top_output: int
                    number of top hits returned
                    
            Returns
            -------
            list of dictionaries (npn)
            
            output -> [<npn>]
            <npn> -> {"npn": str}
        """
        db      = Database(join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE))
        query_npn = LNHPD.__token_removal__(query_npn)
        query  =  (
            f"SELECT DISTINCT npn FROM search "
            f"WHERE search "
            f"MATCH 'npn:\"{query_npn}\"*' "
            f"ORDER BY rank, npn LIMIT ?"
        )
        return LNHPD.__assoc_query__(db, query, [top_output])

    @staticmethod
    def top_product_search_matches(query_product, top_output=10):
        """ Returns a list of the the top_output product names (strings), 
            given a query_product string that the user is filling out from 
            the webapp form.
            
            Parameters
            ----------
            query_product: str
                    User typed (or clicked from proposal suggestion) product name
            top_output: int
                    number of top hits returned
                    
            Returns
            -------
            list of dictionaries (product names)
            
            output -> [<product>,...]
            <product>-> {"product_name": str}
        """
        db      = Database(join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE))
        query_product = LNHPD.__token_removal__(query_product)
        query  =  (
            f"SELECT DISTINCT product_name FROM search "
            f"WHERE product_name "
            f"MATCH 'product_name:\"{query_product}\"*' "
            f"ORDER BY rank, product_name LIMIT ?"
        )
        return LNHPD.__assoc_query__(db, query, [ top_output])
    
    @staticmethod
    def best_product_matches(npn, company_name, product_name, top_output=10):
        """ Returns a list of possible products based on the
            proposed NPN, Company Name, Product Name. Products
            are returned sorted by ranking. 

            Parameters
            ----------
            npn: str
                Natural Health Product Number
            company_name: str
                Company Name
            product_name:
                Product Name
            
            Returns
            -------
            list of dict 
                
                List of product with the following format
                
                output   -> [<product>, ...] #list of product

                <product> -> {
                    "product_name": str,       # Product Name  
                    "npn":          str,       # NPN   
                    "lnhpd_id":     str,       # LNHPD ID number
                    "company_name": str,       # Company Name List
                    "medical_data": [<ingredient>, ...]  # Ingredient List
                    "claim_data:    [<claim>, ...]       # Claim List
                }
                <ingredient> -> {"ingredient_name": str}
                <claim>      -> {"purpose": str}
           
        """
        db   = Database(join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE))
        product_name = LNHPD.__token_removal__(product_name)
        company_name = LNHPD.__token_removal__(company_name)
        npn          = LNHPD.__token_removal__(npn)
        query = """
            SELECT * FROM search WHERE search MATCH ? ORDER BY rank LIMIT ?
        """
        where = (
            f" search MATCH"
            f" 'product_name:\"{product_name}\"* "
            f"  company_name:\"{company_name}\"* "
            f"  npn:\"{npn}\"* ' "
            f" ORDER by rank"
            f" LIMIT {top_output}"
        )
        # param0 = f" product_name:\"{product_name}\"* npn:\"{npn}\"* company_name:\"{company_name}\"*"
        return [r for r in db['search'].rows_where(where)]
        # return LNHPD.__assoc_query__(db, query, [param0, top_output])

    @staticmethod 
    def __token_removal__(string):
        return string.replace("'",' ').replace('"',' ').replace('*','')
    
    @staticmethod
    def __hash__(word):
        return unidecode(word).replace(' ', '').lower()
    
    @staticmethod
    def __sqlite_alias__():
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        path      = join(LNHPD.MODEL_FOLDER, LNHPD.ALIAS_FILE)
        ing       = pd.read_csv( path , encoding = "UTF-8")
        columns = [
            ('ENGLISH_NAME'        , ''),
            ('FRENCH_NAME'         , ''),
            ('ENGLISH_SEARCHTERM'  , '~'),
            ('FRENCH_SEARCHTERM'   , '~'),
            ('ENGLISH_SYNONYMS'    , '<br/>'),
            ('FRENCH_SYNONYMS'     , '<br/>'),
            ('ENGLISH_PROPER_NAMES', '<br/>'),
            ('FRENCH_PROPER_NAMES' , '<br/>'),
            ('ENGLISH_COMMON_NAMES', '<br/>'),
            ('FRENCH_COMMON_NAMES' , '<br/>')
        ]
        role_column  = 'ROLE_NAME'

        hash2code   = {}
        code2words  = {}
        code2role   = {}

        for (idx, r) in ing.iterrows():
            words_row = set([])
            #for each row iterate all the specified columns
            # and separate by separator sep
            for col, sep in columns:
                word = r[col]
                #only if the cell is a string 
                #separate by its sep if any
                #and add words to the words_row set
                if isinstance(word, str):
                    words = word.split(sep) if sep else [word]
                    words_row.update([w for w in words if w])
            #compute all the 'unique hashs' for words_row
            words_hashes = { LNHPD.__hash__(w) for w in words_row }

            role = r[role_column]
            #compute all connected components 

            #compute all the hash codes belonging to this wor if they exist
            #if they don't then idx is their code
            codes = [hash2code[h] if h in hash2code else idx for h in words_hashes]

            #merge all the conected components with the codes
            #and remove them from code2words dict
            for c in codes:
                if c in code2words:
                    words_row.update(code2words[c])
                    del code2words[c]
                    if c in code2role:
                        del code2role[c]
            #select the minimum connected component idx
            #and update the connected component set 
            code2words[idx] = words_row
            code2role[idx]  = role
            for word in words_row:
                hash2code[LNHPD.__hash__(word)] = idx
        
        db['ingredients_alias'].insert_all([
            {
                'hash':h, 
                'code':hash2code[h]
            } 
            for h in hash2code 
        ], pk='hash')

        db['ingredients'].insert_all([
            {
                'code':c,
                'ingredient_name': w ,
                'role': code2role[c]
            }
            for c in code2words for w in code2words[c] 
        ])

    @staticmethod
    def __sqlite_cdsa__():
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        data = pd.read_csv(join(LNHPD.MODEL_FOLDER, LNHPD.CDSA_FILE), encoding = "UTF-8")
        db['cdsa'].insert_all([
            {
                "hash":LNHPD.__hash__(w),
                "ingredient_name":w 
            } 
            for w in data['CDSA'].values.tolist()
        ], pk='hash', replace=True)
    
    @staticmethod
    def __sqlite_pdl__():
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        data = pd.read_csv(join(LNHPD.MODEL_FOLDER, LNHPD.PDL_FILE), encoding='UTF-8')
        data.columns = ['Drugs', 'Including_Not_Limited', 'Qualifier']
        data = data[pd.isnull(data['Qualifier'])]
        db['pdl'].insert_all([
            {
                "hash":LNHPD.__hash__(w),
                "ingredient_name":w
            }
            for w in data['Drugs'].values.tolist()
        ], pk='hash', replace=True)
    
    @staticmethod
    def __sqlite_schedule_a__():
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        data         = pd.read_csv(join(LNHPD.MODEL_FOLDER, LNHPD.SCH_A_FILE), encoding = "UTF-8")
        words        = data['List'].values.tolist()
        extra_words  = ['cures','cure', 'diagnosis', 'treatment', 'mitigation', 
                        'mitigate', 'mitigating', 'prevention', 'preventing', 
                        'preventive', 'prevent', 'prevents']
        words.extend(extra_words)
        db['schedule_a'].insert_all([
            {
                "hash":LNHPD.__hash__(w),
                "ingredient_name":w
            }
            for w in words
        ], pk='hash', replace=True)

    @staticmethod
    def __sqlite_injectable__():
        ##INJECTABLE
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        injectable_list = ['injectable','injection','inject','syringe','needle',
                            'intravenous','intramuscular','subcutaneous','pierce',
                            'bloodstream','solution','vial','parenteral','infusion',
                            'iv','vein','ampoule']
        db['injectable'].insert_all([
            {
                "hash":LNHPD.__hash__(w)
            }
            for w in injectable_list
        ], pk='hash', replace=True)

    @staticmethod
    def __sqlite_initialize__(data):
        filename = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)        
        with open(join(LNHPD.MODEL_FOLDER, 'lnhpd.json'),'w', encoding='utf8') as w:
            json.dump(data, w, separators=(',', ':'), indent=4, ensure_ascii=False) 
        # with open(join(LNHPD.MODEL_FOLDER, 'lnhpd.json'),'r', encoding='utf8') as r:
        #     data = json.load(r) 

        if exists(filename):
            os.remove(filename)   
        #AUTOCOMPLETE NPN, COMPANY NAME, PRODUCT_NAME
        db   = Database(filename)
        db["products"].insert_all(data) 
        c = db.conn.cursor()
        c.execute('''CREATE VIRTUAL TABLE search USING fts5(product_name, company_name, npn, lnhpd_id);''')
        c.execute("""
                INSERT INTO search (product_name, company_name, npn, lnhpd_id)
                SELECT product_name, company_name, licence_number npn, lnhpd_id FROM products """)
        db.conn.commit()
        db.conn.close()
        
    @staticmethod
    def __is_pdl__(db, ingredient_name):
        try:
            db["pdl"].get(LNHPD.__hash__(ingredient_name))
        except NotFoundError:
            return False
        return True

    @staticmethod
    def __is_cdsa__(db, ingredient_name):
        try:
            db["cdsa"].get(LNHPD.__hash__(ingredient_name))
        except NotFoundError:
            return False
        return True

    @staticmethod
    def __is_schedule_a__(db, words):
        try:
            db["schedule_a"].get(LNHPD.__hash__(words))
        except NotFoundError:
            return False
        return True

    @staticmethod
    def __is_injectable__(db, words):
        try:
            db["injectable"].get(LNHPD.__hash__(words))
        except NotFoundError:
            return False
        return True

    @staticmethod
    def __get_alias_code__(db, ingredient_name):
        """Returns False if there is no alias code associated to the ingredient_name
        """
        try:
            return db["ingredients_alias"].get(LNHPD.__hash__(ingredient_name))
        except NotFoundError:
            return False

    @staticmethod
    def __initialize_lnhpd__():
        """ Queries the LNHPD Product Licence database
            Runs the preprocess_product_licence function,
                save_licence_dataframe_lnhpd_all function,
                save_licence_dataframe_lnhpd_cn_pn function, and
                save_licence_dataframe_lnhpd_grouped function
            
            Parameters
            ----------
            None
           
            Returns
            -------
            Boolean if process was successful 
            
        """
        data = requests.get("https://health-products.canada.ca/api/natural-licences/productlicence/?type=json").json()
        LNHPD.__sqlite_initialize__(data)
        LNHPD.__sqlite_alias__()
        LNHPD.__sqlite_cdsa__()
        LNHPD.__sqlite_pdl__()
        LNHPD.__sqlite_schedule_a__()
        LNHPD.__sqlite_injectable__()

        return True


    @staticmethod
    def get_ingredients_data(lnhpd_id):
        """ Retrieves ingredients data associated with the lnhpd_id

            Parameters
            ----------
            lnhpd_id: str
                Unique identifier of a product in the 
                LNHPD database
            
            Returns
            -------
            list of dict 
                List of medical data with the following format

                output     -> [<ingredient>, ...] 

                <ingredient> -> {
                    "ingredient_name": str,
                    "_ingredient_class": str,   (either medicinal or nonmedicinal)
                    ...
                }
        
        example of return        
        [
                {"ingredient_name": "Menthol"}, 
                {"ingredient_name": "Vitamin A"}, 
                {"ingredient_name": "Vitamin B"}, 
        ]

        """
        if not lnhpd_id:
            return []

        parameters       = {}
        parameters['id'] = int(lnhpd_id)
        categories       = ['medicinalingredient', 'nonmedicinalingredient']
        all_ingredients  = []

        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)

        unique = set([])
        for ingredient_type in categories:
           
            ingredient_response = requests.get("https://health-products.canada.ca/api/natural-licences/{}/?lang=en&type=json".format(ingredient_type),  params=parameters).json()
            if isinstance(ingredient_response, dict):
                ingredient_response = ingredient_response['data']  # take data only, no api metadata needed

            for ingredient in ingredient_response:
                name  = ingredient['ingredient_name']
                _hash = LNHPD.__hash__(name)
               
                if _hash in unique:
                    continue
                else:
                    unique.add(_hash)

                tmp = {'ingredient_name': name,
                          '_medicinal'  : (ingredient_type == 'medicinalingredient'),
                          '_pdl'        : LNHPD.__is_pdl__(db, name),
                          '_cdsa'       : LNHPD.__is_cdsa__(db, name)
                }
                all_ingredients.append(tmp)
                
        return all_ingredients
        
    @staticmethod
    def get_claim_data(lnhpd_id):
        """ Retrieves claim data associated with the lnhphd_id
            
            Parameters
            ----------
            lnhpd_id: str
                Unique identifier of a product in the 
                LNHPD database
            
            Returns
            -------
            list of dict 
                List of claim data with the following format

                output -> [<claim>, ...]
                <claim>  -> [
                    "purpose": str,
                    ...
                ]
                
         **************** May be hard to split up claims..... ********************
        """

        if not lnhpd_id:
            return []

        parameters = {}
        parameters['id'] = int(lnhpd_id)
        response = requests.get("https://health-products.canada.ca/api/natural-licences/productpurpose/?lang=en&type=json", params=parameters).json()
        
        if isinstance(response, dict):
            claims = response['data']
        
        claims_list = []
        for claim in claims:
            claims_list.append({
                'purpose'   : claim['purpose'],
                '_compliant': True
            })
            
        return claims_list
        
    #todo: keep cdsa/pdl flag
    @staticmethod
    def match_ingredients(user_medical_data, lnhpd_medical_data):
        """
            Compares the user_medical_data dict against the medical data
            associated to the lnhpd_id. lnhpd_medical_data

            Parameters
            ----------
            user_medical_data: list of <ingredients>
                Medical data information, a list of ingredients.
                [
                    {"ingredient_name":"name A"},
                    {"ingredient_name":"name B"},
                    ...
                ]


            lnhpd_medical_data: list of <ingredients>
                [
                    {"ingredient_name":"name A"},
                    {"ingredient_name":"name B"},
                    ...
                ]
            
            Returns
            -------
            (intersection, listA, listB, AB_equivalence_dict)
                Returns a tuple containing 4 lists.
                intersection ingredients present in user_medical_data and lnhpd_medical_data
                listA  ingredients  present only in user_medical_data
                listB  ingredients present only in lnhpd_medical_data
                AB_equivalence_dict    a dictionary mapping the ingredient in user_medical_data that maps 
                                        to the ingredient in lnhpd_medical_data        
                
                output     -> ([<ingredient>, ...],  
                               [<ingredient>, ...], 
                               [<ingredient>, ...], 
                               {'vitamin a':'24-6,3-hydroxymethyl...'})
                <ingredient> -> {"ingredient_name": str}
        """

        AB_equivalence_dict = {}
        intersection = []
        listA = []
        listB = []

        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)

        user_hashes = []
        user_codes  = []

        for ingredient in user_medical_data:
            name = ingredient['ingredient_name']
            _hash = LNHPD.__hash__(name)
            _code = LNHPD.__get_alias_code__(db, name)
            
            if _code:
                user_codes.append(_code)
            user_hashes.append(_hash)

        lnhpd_hashes = []
        lnhpd_codes  = []
        for ingredient in lnhpd_medical_data:
            name = ingredient['ingredient_name']
            _hash = LNHPD.__hash__(name)
            _code = LNHPD.__get_alias_code__(db, name)

            if _code:
                lnhpd_codes.append(_code)
            lnhpd_hashes.append(_hash)
        
        for ingredient in user_medical_data:
            name = ingredient['ingredient_name']
            _hash = LNHPD.__hash__(name)
            _code = LNHPD.__get_alias_code__(db, name)

            if (_hash not in lnhpd_hashes) and (_code not in lnhpd_codes):
                listA.append(ingredient)

        for ingredient in lnhpd_medical_data:
            name = ingredient['ingredient_name']
            _hash = LNHPD.__hash__(name)
            _code = LNHPD.__get_alias_code__(db, name)
            if (_hash in user_hashes) or (_code in user_codes):
                intersection.append(ingredient)
            else:
                listB.append(ingredient)
                
        listA = LNHPD.validate_md(listA)

        return (intersection, listA, listB, AB_equivalence_dict)

    @staticmethod
    def validate_md(user_medical_data):
        """ Validate all ingredient inside a user_medical_data dict. 
            
            Parameters
            ----------
            user_medical_data: list of <ingredients>
                Medical data information, a list of ingredients.
                [
                    {"ingredient_name":"name A"},
                    {"ingredient_name":"name B"},
                    ...
                ]
           
            Returns
            -------
            list
                Returns a medical_data list by flagging ingridients
                that are : compliant, nhp, banned sustances. 

                output     -> [<ingredient> ...]
                <ingredient> -> {
                    "ingredient":    str,
                    "_pdl":    boolean,
                    "_cdsa":   boolean}
        """
        
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        for ingredient in user_medical_data:
            ingredient['_pdl']  = LNHPD.__is_pdl__(db, ingredient['ingredient_name'])
            ingredient['_cdsa'] = LNHPD.__is_cdsa__(db, ingredient['ingredient_name'])
        return user_medical_data


    # TODO: TEST THIS FUNCTION
    @staticmethod
    def validate_flagged_words(ocr_words):
        
        """ Iterates through each OCR word and adds a boolean dictionary to the input
                indicating if the corresponding word matches a word in the FDA Schedule A.1 list
                as well as other words, such as mitigate and treatment
            
            Parameters
            ----------
            ocr_words : [ocr_word, ...]


            <ocr_word>    -> {
                         "raw_ocr_word":   str,
                         "ocr_word":       str,
                         "coordinates":    list([float,float]),
                         "filename":       str,
                         "classifier_class":     str,
                         "ocr_match_class'":     list(str),
                         "symspell_match_class": list(str),
                         "_schedule_a": boolean,
                         "_injectable": boolean,
            }
                    
            Returns
            -------
            [ocr_word,..]: list of ocr_word with 
                The _schedule_a  and _injectable updated metadata is updated.
            
           
           
        """
        #TODO: PUNCT/LOWER FOR SCH_A
        filename  = join(LNHPD.MODEL_FOLDER, LNHPD.SQLITE_FILE)
        db        = Database(filename)
        for word in ocr_words:
            word['_schedule_a'] = LNHPD.__is_schedule_a__(db, word['ocr_word'])
            word['_injectable'] = LNHPD.__is_injectable__(db, word['ocr_word'])
        return ocr_words
