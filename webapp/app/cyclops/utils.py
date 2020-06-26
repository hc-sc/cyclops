#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Cyclops app utils functions
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------
from cyclops import app
from os import environ
from flask import session, request
from os import makedirs, walk, remove, listdir, getenv
from os.path import join, exists, getmtime, isfile, isdir
from shutil import rmtree
from urllib.parse import urljoin
from datetime import timedelta
import datetime
from filelock import Timeout, FileLock
from glob import glob


#import threading
#from io import StringIO
#
#from minio import Minio
#from minio.error import ResponseError

#S3_HOST = getenv('S3_HOST')
#S3_ACCESS_KEY = getenv('S3_ACCESS_KEY')
#S3_SECRET_KEY = getenv('S3_SECRET_KEY')
#
## Minio is compatible with other s3 APIs
#minioClient = Minio(S3_HOST,
#                  access_key=S3_ACCESS_KEY,
#                  secret_key=S3_SECRET_KEY,
#                  secure=True)


#Datastructure keys 
APP          = 'app'
FILES        = 'files'

PROPOSAL     = 'product_proposal'
NPN          = 'npn'
COMPANY_NAME = 'company_name'
PRODUCT_NAME = 'product_name'
ID           = 'lnhpd_id'
MEDICAL_DATA = 'medical_data'
CLAIM_DATA   = 'claim_data'
MEDICAL_DIFF = 'medical_data_diff'
CLAIM_DIFF   = 'claim_data_diff'
WARNINGS     = 'warnings'
FOUND        = 'found'
MISSING      = 'missing' 
MATCHED      = 'matched'
#State Flags keys

FLAG_NEW_IMAGE   = 'flag_new_image'

#Messages
MSSG_NO_PRODUCT_SELECTED = "The product wasn't selected from database."

#Cleaning up procedure
LOCKFILE_NAME            = '.cleanup.lock'
WAY_TOO_OLD              = 24 * 2 # hours



def isempty_session_tmpdir():
    _, _, files = next(walk(session_tmpdir(), session.sid))
    return not len(files)

def session_tmpdir():
    """ Returns the default upload folder for the current session id (session.sid)"""
    tmpdir = join(app.config['UPLOADED_PATH'], session.sid)
    if not exists(tmpdir):
        makedirs(tmpdir)
    return tmpdir

def init_session():
    """ Initializes the session variables if not set"""
    if APP not in session:
        session.permanent              = True
        app.permanent_session_lifetime = timedelta(days=1)
        session[APP] = {
            FILES           :  set([]),
            FLAG_NEW_IMAGE  :  True
        }
    session_tmpdir()
        
def clear_session():
    """ removes the temp session folder and clears the session variables"""
    if exists(session_tmpdir()):
        rmtree(session_tmpdir())
    session.clear()


def clear_dangling():
    """ Find and remove any old sessions that got abandoned """
    lockfile = join(app.config['UPLOADED_PATH'], LOCKFILE_NAME)
    fromtimestamp = datetime.datetime.fromtimestamp
    now = datetime.datetime.now

    if exists(lockfile):
        try:
            # if there is an old lockfile, kill it
            lock_time = getmtime(lockfile)
            modtime = fromtimestamp(lock_time)
            if (now() - modtime) > timedelta(minutes=30):
                remove(lockfile)
            else:
                print("Lockfile found. Skipping cleanup for now.")
                # We'll play it safe and leave it alone for now.
                return
        except FileNotFoundError:
            # Someone probably just finished cleaning up
            # This will probably never happen.
            return

    try:
        # We land here only if the lockfile didn't exist or was old
        with FileLock(lockfile, timeout=1):
            print("Acquired a lock.")

            # uploads/sessions/
            for d in glob(join(app.config['UPLOADED_PATH'], '*')):
                if isdir(d):
                    # directory is empty and old, remove it
                    modtime = fromtimestamp(getmtime(d))
                    if (now() - modtime) > timedelta(hours=WAY_TOO_OLD):
                        rmtree(d)

        # For some reason, the lock isn't deleted...
        # But the above logic manages that, so whatever, I guess
        print("Lock released.")

    except Timeout:
        print("Someone else holds a lock. Skipping.")
        # Someone else beat us to it.
        pass


def abs_filename(filename):
    """ creates an absolute filanme but appending the filename to the session
    upload temp folder"""
    return join(session_tmpdir(), filename)

def mapi_url(path):
    url =  urljoin(environ.get('MAPI_URL'), path)
    return url if url != path else False



def product_proposal_skeleton():
    product_proposal = {
        "product_name": None,
        "company_name": None,
        "npn":          None,
        "lnhpd_id":     None,
        "prod_prpsl":     [],
        "npn_prpsl":      [],  
        "comp_prpsl":     [],  
        "medical_data":   [],  
        "claim_data":     [],
        "warnings":       []
    }
    return product_proposal


def product_proposal_example():
    ingredient_lst = [
        {
        "ingredient_name": "Coenzyme Q10",
        "_ingredient_class": "medicinal",
        "_cdsa": True,
        "_pdl": False,
        "_medicinal": True
        },
        {
        "ingredient_name": "Citric acid",
        "_ingredient_class": "nonmedicinal",
        "_cdsa": False,
        "_pdl": True,
        "_medicinal": False
        },
        {
        "ingredient_name": "Medium-chain Triglycerides",
        "_ingredient_class": "nonmedicinal",
        "_cdsa": True,
        "_pdl": False,
        "_medicinal": True
        }
    ]
    claim_lst = [
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        },
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        },
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        }
    ]
    warnings =     [{
        "raw_ocr_word": "cancer",
        "ocr_word":    'cancer',
        "coordinates": [20,50,20,51, 123,133,124,144],     
        "filename":    "P00123.jpg",
        "classifier_class": 0,                
        "ocr_match_class": [1,2,3], 
        "symspell_match_class":    [1,2,3],
        "bigram_match_class": [(1,'de la')],
        "_schedule_a": False, 
        "_injectable": True}
    ]

    product_proposal = {
    "product_name": None,
    "company_name": None,
    "npn":          None,
    "lnhpd_id":     None,
    "prod_prpsl":       [
        {"product_name":"Tylenol"},
        {"product_name":"Grucadol Krill"},
        {"product_name":"Band-AID"}
    ],
    "npn_prpsl":        [
        {"npn":"12345678"},
        {"npn":"12342"},
        {"npn":"893839938"}, 
        {"npn":"3434333"}
    ],  
    "comp_prpsl":       [
        {"company_name":"F Hoffmann-La Roche Ltd "},
        {"company_name":"Merck & Co Inc"}, 
        {"company_name":"GlaxoSmithKline Plc"},
        {"company_name":"Sanofi"},
        {"company_name":"Bayer AG"}
    ],  
    "medical_data":     ingredient_lst,  
    "claim_data":       claim_lst,
    "warnings":         warnings
    }
    return product_proposal

def medical_diff_example():
    ingredient_lst = [
        {
        "ingredient_name": "Coenzyme Q10",
        "_ingredient_class": "medicinal",
        "_cdsa": True,
        "_pdl": False,
        "_medicinal": True
        },
        {
        "ingredient_name": "Citric acid",
        "_ingredient_class": "nonmedicinal",
        "_cdsa": False,
        "_pdl": True,
        "_medicinal": False
        },
        {
        "ingredient_name": "Medium-chain Triglycerides",
        "_ingredient_class": "nonmedicinal",
        "_cdsa": True,
        "_pdl": False,
        "_medicinal": True
        }
    ]

    medical_data_diff = {
        "found"  :ingredient_lst,
        "missing":ingredient_lst,
        "matched":ingredient_lst
    }
    return medical_data_diff

def claim_diff_example():
    claim_lst = [
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        },
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        },
        {
        "purpose": "Temporarily relieves minor aches and pains due to: the common cold,",
        "_compliant": True
        }
    ]
    claim_data_diff = {
        "found"  : claim_lst,
        "missing": claim_lst,
        "matched": claim_lst
    }
    return claim_data_diff


######################################
#  Don't delete this! Will need soon #
######################################
#   def in_the_backgroud(f):
#       """ Use as a decorator. Executes f on its own thread. """
#       def __in_the_background__(*args):
#           t = threading.Thread(target=f, args=args)
#           t.start()
#           return None
#       return __in_the_background__
#   
#   @in_the_background    
#   def push_file_to_s3(infile, outpath, bucket):
#       """ Send a file (absolute path) to the s3 bucket using minio. """
#      
#       # Put a file with default content-type,
#       # upon success prints the etag identifier computed by server.
#       try:
#           with open(infile, 'rb') as file_data:
#               file_stat = os.stat(infile)
#               print(minioClient.put_object(bucket, outpath,
#                                      file_data, file_stat.st_size))
#   
#       except ResponseError as err:
#           print(err)
#   
#   
#   @in_the_background
#   def push_json_to_s3(j, outpath):
#       """ Push an object to s3 as json """
#   
#       # Put a file with default content-type, upon success prints the etag identifier computed by server.
#       try:
#           with open('my-testfile', 'rb') as file_data:
#               file_stat = os.stat('my-testfile')
#               print(minioClient.put_object('mybucket', 'myobject',
#                                      file_data, file_stat.st_size))
#       except ResponseError as err:
#           print(err)
