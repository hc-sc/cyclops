#!/usr/bin/env python
# -*- coding: utf-8 -*-
# -----------------------------------------------------------
# Cyclops app views/routes
#
# (C) 2020 Statistics Canada
# Author: Andres Solis Montero
# -----------------------------------------------------------

from flask   import  render_template, request, url_for, redirect, session, flash
from cyclops import app, dropzone
from cyclops.utils import init_session, clear_session, clear_dangling, abs_filename, isempty_session_tmpdir
from cyclops.utils import APP, FILES, PROPOSAL, ID,MSSG_NO_PRODUCT_SELECTED
from cyclops.utils import FLAG_NEW_IMAGE


@app.route("/")
@app.route("/upload", methods=['GET','POST'])
def upload():
    init_session()
    if request.method == 'POST':
        for key, f in request.files.items():
            if key.startswith('file'):
                session[APP][FILES].add(f.filename)
                session[APP][FLAG_NEW_IMAGE] = True
                f.save(abs_filename(f.filename))
    return render_template('upload.html', title='Upload')

@app.route('/clear')
def clear():
    clear_session()
    clear_dangling()
    return redirect(url_for('upload'))

@app.route('/testing')
def testing():
    init_session()
    return render_template('testing.html',
                          title='Testing',
                          uploaded=session[APP][FILES])

@app.route('/product')
def product():
    init_session()
    
    if (isempty_session_tmpdir()):
        return redirect(url_for('upload'))

    return render_template('product.html', 
                           title='Product Identification', 
                           uploaded=session[APP][FILES])

@app.route('/ingredients')
def ingredients():
    init_session()
    
    if (isempty_session_tmpdir()):
        return redirect(url_for('upload'))
    if not PROPOSAL in session[APP]:
        return redirect(url_for('upload'))
    if not session[APP][PROPOSAL][ID]:
        flash(MSSG_NO_PRODUCT_SELECTED, 'warning')

    return render_template('ingredients.html', 
                           title='Ingredients',
                           uploaded=session[APP][FILES])

@app.route('/claims')
def claims():
    init_session()
    
    if (isempty_session_tmpdir()):
        return redirect(url_for('upload'))
    if not PROPOSAL in session[APP]:
        return redirect(url_for('upload'))
    if not session[APP][PROPOSAL][ID]:
        flash(MSSG_NO_PRODUCT_SELECTED, 'warning')

    return render_template('claims.html', 
                            title='Claims', 
                            uploaded=session[APP][FILES])

@app.route('/flags')
def flags():
    init_session()
    
    if (isempty_session_tmpdir()):
        return redirect(url_for('upload'))
    if not PROPOSAL in session[APP]:
        return redirect(url_for('upload'))
    if not session[APP][PROPOSAL][ID]:
        flash(MSSG_NO_PRODUCT_SELECTED, 'warning')

    return render_template('flags.html', 
                            title='Flags', 
                            uploaded=session[APP][FILES])

@app.route('/report')
def report():
    init_session()
    
    if (isempty_session_tmpdir()):
        return redirect(url_for('upload'))
    if not PROPOSAL in session[APP]:
        return redirect(url_for('upload'))
    if not session[APP][PROPOSAL][ID]:
        flash(MSSG_NO_PRODUCT_SELECTED, 'warning')

    return render_template('report.html', 
                            title='Report', 
                            uploaded=session[APP][FILES])



    

