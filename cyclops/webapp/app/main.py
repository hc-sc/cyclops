#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Cyclops entry point 
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------
from cyclops import app

if __name__ == '__main__':
    app.run(host= '0.0.0.0', port=8000, debug=True)