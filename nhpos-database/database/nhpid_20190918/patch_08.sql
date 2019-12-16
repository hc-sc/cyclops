-----------------------------------
--patch #08
-- date: 2019/09/20
-- fixes: TCM monograph data set
-- log: patch_08.log
----------------------------------
spool patch_08.log
prompt Patch 08
prompt Updating table X_MONOGRAPH...
set feedback off
set define off
insert into X_MONOGRAPH (PRODUCT_MONO_CODE, MONO_CODE, MONO_TYPE, NHPID_MONO_CODE, MONO_NAME, STERILE, ROW_ID, USERACC_ID, CREATION_DATE, LASTUPDATE_DATE, PROD_MONO_ID)
values ('TCM', null, 'Product Monograph', 'TCM', 'Traditional Chinese Medicine Ingredients', 'NO', x_monograph_seq.nextval, 0, sysdate, sysdate, 1250);
prompt Updating table X$MONOGRAPH_XREF...
update X$MONOGRAPH_XREF 
set MONO_ID = 2129, 
    MONO_CODE = 'TCM', 
    MONO_NAME_ENG = '', 
    MONO_NAME_FR = '', 
    PRODUCT_MONO_CODE = 'TCM', 
    SINGLE_MONO_CODE = null
where mid = 1902
;
commit;
prompt Done.
spool off

