#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Cyclops app controller
# all AJAX REST api call are controlled here
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------
import requests
from flask   import send_file, request, flash,jsonify, session, send_from_directory
from cyclops import app
from cyclops.utils import *
from os.path import join, exists

if not mapi_url('/'):
    from models.lnhpd import LNHPD


#AUTOCOMPLETE CALLS
@app.route("/search_npn", methods=['GET'])
def search_npn():
    q        = request.args.get('q', default='', type=str)
    url      = mapi_url('top_npn_search_matches')
    if (url):
        response = requests.get(url, params={'q': q}).json()
    else:
        response = LNHPD.top_npn_search_matches(q)

    return jsonify(response)

@app.route("/search_cn", methods=['GET'])
def search_cn():
    q        = request.args.get('q', default='', type=str)
    url      = mapi_url('top_company_search_matches')
    if (url):
        response = requests.get(url, params={'q': q}).json()
    else:
        response = LNHPD.top_company_search_matches(q)

    return jsonify(response)

@app.route("/search_pn", methods=['GET'])
def search_pn():
    q        = request.args.get('q', default='', type=str)
    url      = mapi_url('top_product_search_matches')
    if (url):
        response = requests.get(url, params={'q': q}).json()
    else:
        response = LNHPD.top_product_search_matches(q)

    return jsonify(response)

@app.route("/best_product_matches", methods=['GET'])
def best_product_matches():
    npn          = request.args.get('npn', default='', type=str)
    company_name = request.args.get('company_name', default='', type=str)
    product_name = request.args.get('product_name', default='', type=str)

    url          = mapi_url('best_product_matches')

    if (url):
        response     = requests.post(url, json={
                                'npn': npn,
                                'company_name': company_name,
                                'product_name': product_name
                                }).json()
    else:
        response      = LNHPD.best_product_matches(npn, 
                                    company_name, product_name)
    return jsonify(response)
    

 
#PRODUCT_PROPOSAL
@app.route("/get_product_info", methods=['GET'])
def get_product_info():
    if ( not PROPOSAL in session[APP] ) or \
       ( session[APP][FLAG_NEW_IMAGE] ):

        proposal = product_proposal_skeleton()

        # url      = mapi_url('extract')
        # files    = [join(session.sid, f) for f in session[APP][FILES]]
        # response = requests.post(url, json={'filenames': files}).json()
        
        url      = mapi_url('upload')

        if (url):
            files    = [ 
                    ('file', (name, open(abs_filename(name), 'rb'))) 
                    for name in session[APP][FILES]
            ]
            response = requests.post(url, files=files).json()
        else:
            response = product_proposal_example()
        
        proposal.update(response)

        session[APP][PROPOSAL]      = proposal
        session[APP][FLAG_NEW_IMAGE] = False

    return jsonify(session[APP][PROPOSAL])

@app.route('/get_ingredient_info', methods=['GET'])
def get_ingredient_info():
    if ( not MEDICAL_DIFF in session[APP][PROPOSAL] ):

        A   = session[APP][PROPOSAL][MEDICAL_DATA]

        url = mapi_url('get_ingredients_data')
        if url:
            lnhdp_id = session[APP][PROPOSAL][ID] if session[APP][PROPOSAL][ID] else ''
            B   = requests.post(url, json={
                'lnhpd_id': lnhdp_id
            }).json()
           
            url  = mapi_url('match_ingredients')
            matched, found, missing, _  = requests.post(url, json={
                'medical_data': A, 
                'lnhpd_data'  : B
            }).json()
        else:
            B = LNHPD.get_ingredients_data(session[APP][PROPOSAL][ID])
            matched, found, missing, _  = LNHPD.match_ingredients(A,B)
        
        
        session[APP][PROPOSAL][MEDICAL_DIFF] = {
            "found"  :found,
            "missing":missing,
            "matched":matched
        }
    
    return jsonify(session[APP][PROPOSAL])

@app.route('/get_claims_info', methods=['GET'])
def get_claims_info():
    if ( not CLAIM_DIFF in session[APP][PROPOSAL] ):

        matched, found, missing  = [], session[APP][PROPOSAL][CLAIM_DATA], []
        
        url       = mapi_url('get_claim_data')
        if url:
            lnhdp_id = session[APP][PROPOSAL][ID] if session[APP][PROPOSAL][ID] else ''
            missing   = requests.post(url, json={
                'lnhpd_id': lnhdp_id
            }).json()
        else:
            missing   = LNHPD.get_claim_data(session[APP][PROPOSAL][ID])
        
        session[APP][PROPOSAL][CLAIM_DIFF] = {
                "found"  :found,
                "missing":missing,
                "matched":matched
        }

    return jsonify(session[APP][PROPOSAL])

@app.route('/get_flags_info', methods=['GET'])
def get_flags_info():
    warnings = session[APP][PROPOSAL][WARNINGS]
    url      = mapi_url('validate_flagged_words')
    if url:
        session[APP][PROPOSAL][WARNINGS] = requests.post(url, warnings).json()
    else:
        session[APP][PROPOSAL][WARNINGS] = LNHPD.validate_flagged_words(warnings)
    return jsonify(session[APP][PROPOSAL])

@app.route('/get_report_info', methods=['GET'])
def get_report_info():
    report           = {}
    report[FILES]    = list(session[APP][FILES])
    report[PROPOSAL] = session[APP][PROPOSAL]
    return jsonify(report)

@app.route("/update_product",     methods=['POST'])
def update_product():
    product = request.get_json()

    for k in [PRODUCT_NAME, ID, COMPANY_NAME, NPN]:
        #Flags if a new product was selected
        if (session[APP][PROPOSAL][k] != product[k]):
            if MEDICAL_DIFF in session[APP][PROPOSAL]:
                del session[APP][PROPOSAL][MEDICAL_DIFF]
            if CLAIM_DIFF   in session[APP][PROPOSAL]:
                del session[APP][PROPOSAL][CLAIM_DIFF]
        #update product data
        session[APP][PROPOSAL][k]     = product[k]

    return jsonify(session[APP][PROPOSAL])

@app.route("/update_ingredients", methods=['POST'])
def update_ingredients():
    product = request.get_json()

    matched = product[MEDICAL_DIFF][MATCHED]
    missing = product[MEDICAL_DIFF][MISSING]
    found   = product[MEDICAL_DIFF][FOUND]

    A       = found   + matched
    B       = missing + matched

    url = mapi_url('match_ingredients')
    if url:
        matched, found, missing, _  = requests.post(url, json={
            'medical_data': A, 
            'lnhpd_data'  : B
        }).json()
    else:
        matched, found, missing, _  = LNHPD.match_ingredients(A,B)

    session[APP][PROPOSAL][MEDICAL_DIFF][FOUND]   = found
    session[APP][PROPOSAL][MEDICAL_DIFF][MISSING] = missing
    session[APP][PROPOSAL][MEDICAL_DIFF][MATCHED] = matched

    return jsonify(session[APP][PROPOSAL])

@app.route("/update_claims",      methods=['POST'])
def update_claims():
    product                                      = request.get_json()
    session[APP][PROPOSAL][CLAIM_DIFF][FOUND]    = product[CLAIM_DIFF][FOUND]
    session[APP][PROPOSAL][CLAIM_DIFF][MISSING]  = product[CLAIM_DIFF][MISSING]
    session[APP][PROPOSAL][CLAIM_DIFF][MATCHED]  = product[CLAIM_DIFF][MATCHED]
    return jsonify(session[APP][PROPOSAL])

@app.route("/update_flags",    methods=['POST'])
def update_flags():
    product = request.get_json()
    url     = mapi_url('validate_flagged_words')
    
    if url:
        session[APP][PROPOSAL][WARNINGS] = requests.post(url, json=product[WARNINGS]).json()
    else:
        session[APP][PROPOSAL][WARNINGS] = LNHPD.validate_flagged_words(product[WARNINGS])

    return jsonify(session[APP][PROPOSAL])

@app.route("/image")
def image():
    init_session()
    filename       = request.args.get('img', default='', type=str)
    full_path      = abs_filename(filename)
    if exists(full_path):
        return send_file(full_path)
    return ''


@app.route('/favicon.ico')
def favicon():
    return send_from_directory(join(app.root_path, 'static'),
                               'favicon.ico', mimetype='image/vnd.microsoft.icon')
