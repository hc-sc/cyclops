-- --------------------
-- Data Removal Scripts 
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- Updated: 2012-12-07 by Jeff Haydt
-- --------------------

- The scripts in this folder are meant for ePLA data removal tests only, do not
  use them to try to load or modify ingredients.
- Be sure to save your transactions in case you want to put stuff back.
- Only NHPDWEB_OWNER/tqa_1836@SDV101 can run these scripts, we do not have
  privs to change external db data in TEST, CATE, or PROD.  Therefore you need
  to run these tests using a DEV version ePLA.