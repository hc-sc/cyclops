#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# cyclops module init
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------

from flask import Flask
from flask_dropzone import Dropzone
from flask_session import Session
from flask_reverse_proxy_fix.middleware import ReverseProxyPrefixFix

app      = Flask(__name__)

# Reverse proxy fix for SFE-DEV environment: TODO: Remove if not behind recerse proxy
# https://pypi.org/project/flask-reverse-proxy-fix/
app.config['REVERSE_PROXY_PATH'] = '/apps/cyclops'
ReverseProxyPrefixFix(app)

from cyclops import config

dropzone = Dropzone(app)
Session(app)

from cyclops import views 
from cyclops import controller


