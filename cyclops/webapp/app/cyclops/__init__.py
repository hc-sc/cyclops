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

app      = Flask(__name__)

from cyclops import config

dropzone = Dropzone(app)
Session(app)

from cyclops import views 
from cyclops import controller


