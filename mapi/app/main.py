#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# mlapi entry point 
# based on: https://github.com/blairdrummond/docker-workshop
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------

import os
import shutil
from typing  import Set, List, Dict, Tuple
from fastapi import Body, FastAPI, Query, File, UploadFile
from models.aicore  import AICore
from models.lnhpd   import LNHPD
from pydantic import BaseModel
from wand.image import Image

from tempfile import NamedTemporaryFile
from pathlib  import Path

# #production
# app = FastAPI(docs_url=None)

app = FastAPI( debug=True)

UPLOAD_FILE = '/uploads'

class Images(BaseModel):
    filenames: Set[str]
class Claim(BaseModel):
    purpose: str
class Ingredient(BaseModel):
    ingredient_name: str
    pdl            : bool = None
    cdsa           : bool = None
    medicinal      : bool = None
    class Config:
        fields = {
            'pdl'      : '_pdl',
            'cdsa'     : '_cdsa', 
            'medicinal': '_medicinal'
        }

class OCR_word(BaseModel):
    raw_ocr_word    : str
    ocr_word        : str
    filename        : str
    classifier_class: int  
    schedule_a      : bool = None
    injectable      : bool = None
    ocr_match_class : List[int]     = []
    symspell_match_class: List[int] = []
    coordinates         : List[int] = []
    bigram_match_class  : List[Tuple[int, str]] = []

    class Config:
        fields = {
            'schedule_a' : '_schedule_a',
            'injectable' : '_injectable'
        }

class Product(BaseModel):
    npn: str
    company_name: str
    product_name: str

@app.post("/upload")
async def upload(file: List[UploadFile] = File(...)):
    paths =[]
    for f in file:
        suffix = f.filename
        if f.content_type == "application/pdf":
            images = Image(file=f.file, resolution=600)
            for i, page in enumerate(images.sequence):
                with Image(page) as img:
                    with NamedTemporaryFile(delete=False, suffix=f"_{i}-{suffix}") as tmp:
                        img.save(tmp)
        else:
            try:
                with NamedTemporaryFile(delete=False, suffix=f"_{suffix}") as tmp:
                    shutil.copyfileobj(f.file, tmp)
                    paths.append(tmp.name)
            finally:
                f.file.close()
    
    proposal = AICore.extract(*paths)
    
    for p in paths:
        if os.path.exists(p):
            os.remove(p)

    return proposal

@app.post("/extract")
async def extract(imgs: Images): 
    """ See AICore.process for documentation """
    abs_path = [
        os.path.join(UPLOAD_FILE, i)
        for i in imgs.filenames
    ]
    return AICore.extract(*abs_path)

@app.post("/get_ingredients_data")
async def get_ingredients_data(lnhpd_id: str = Body(...,embed=True)):
    """ See LNHPD.get_ingredients_data for documentation """
    return LNHPD.get_ingredients_data(lnhpd_id)

@app.post("/get_claim_data")
async def get_claim_data(lnhpd_id: str = Body(..., embed=True)):
    """ See LNHPD.claim_data for documentation """
    return LNHPD.get_claim_data(lnhpd_id)

@app.post("/best_product_matches")
async def best_product_matches(p: Product):
    """ See LNHPD.best_product_matches for documentation """
    return LNHPD.best_product_matches(p.npn, p.company_name, p.product_name)

@app.post("/match_ingredients")
async def match_ingredients(medical_data: List[Ingredient] = [], lnhpd_data: List[Ingredient] = []):
    """ See LNHPD.match_ingredients for documentation """
    md = [l.dict(by_alias=True) for l in medical_data]
    ld = [l.dict(by_alias=True) for l in lnhpd_data]
    return LNHPD.match_ingredients(md, ld)

@app.post("/validate_md")
async def validate_md(medical_data: List[Ingredient] = []):
    """ See LNHPD.validate_md for documentation """
    md = [l.dict(by_alias=True) for l in medical_data]
    return LNHPD.validate_md(md)

@app.post("/validate_flagged_words")
async def validate_flagged_words(ocr_words: List[OCR_word] = []):
    """ See LNHPD.validate_flagged_words for documentation """
    md = [l.dict(by_alias=True) for l in ocr_words]
    return LNHPD.validate_flagged_words(md)


###
#
#    GET METHODS USED IN AUTOCOMPLETION 
#    
#
###
@app.get("/top_product_search_matches")
async def top_product_search_matches(q: str):
    return LNHPD.top_product_search_matches(q)

@app.get("/top_company_search_matches")
async def top_company_search_matches(q: str):
    return LNHPD.top_company_search_matches(q)

@app.get("/top_npn_search_matches")
async def top_npn_search_matches(q: str):
    return LNHPD.top_npn_search_matches(q)