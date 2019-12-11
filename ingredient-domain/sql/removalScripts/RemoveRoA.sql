-- Removes a Route of Administration and the related dose forms.
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/11

-- 1. Chose the RoA to remove - Transdermal
--    a. get the id
select * from adminstration_routes where adminrt_code = 'TDERM';
--    TDERM	Applied to...	Appliqué à la...	14	Transdermal	Transdermique
--    b. Find the dose forms related
select * from dosage_form_admin_routes where adminrt_id = 14;
--    c. delete the records from the dose form - admin routes mapping table
delete * rom dosage_form_admin_routes where adminrt_id = 14;
--    d. delete the roa record
delete from administration_routes where adminrt_code = 'TDERM';
commit;