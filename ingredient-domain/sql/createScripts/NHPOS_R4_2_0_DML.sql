-- Created on 2013-01-18
-- The following lines update the schema data.  Pre-4.0, this was done
-- in the loader because the schema was recreated every time.  This is no
-- longer the case, so it is done at release time.
UPDATE APPLICATION_PROPERTIES SET DATA='2.9.0' WHERE KEY='schema.version';
UPDATE APPLICATION_PROPERTIES SET DATA='2012-07-19' WHERE KEY='schema.date';
UPDATE APPLICATION_PROPERTIES SET DATA='IngredientLoader_V2.9.1.B1_20130211' 
       WHERE KEY='loader.version';
COMMIT;
-- Sent to SADATE on 2013-03-06.