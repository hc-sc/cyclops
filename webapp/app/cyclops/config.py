#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Cyclops app configuration
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------
from cyclops import app

# # SFE-DEV server prefix, TODO: subdomain
# app.config['APPLICATION_ROOT'] = '/apps/cyclops'

app.config['SECRET_KEY']    = '5791628bb0b13ce0c676dfde280ba245'
app.config['UPLOADED_PATH'] = "/uploads"
app.config['SESSION_TYPE']  = 'filesystem'

#moved to environment variable MAPI_URL
#it will be declared on docker-compose.yml
#app.config['MAPI_URL'] =  "http://mapi:9000"


# Flask-Dropzone config:
app.config['DROPZONE_ALLOWED_FILE_CUSTOM'] = True
app.config['DROPZONE_ALLOWED_FILE_TYPE'] = 'image/*, application/*'
app.config['DROPZONE_MAX_FILE_SIZE']     = 10
app.config['DROPZONE_MAX_FILES']         = 5
app.config['DROPZONE_UPLOAD_MULTIPLE']   = True
app.config['DROPZONE_PARALLEL_UPLOADS']  = 3
app.config['DROPZONE_REDIRECT_VIEW']     = 'product'
app.config['DROPZONE_DEFAULT_MESSAGE']   = 'Drop images here or click to upload'
