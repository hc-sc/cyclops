--drop table x$assessment_dose_dose_cat;
--create table x$assessment_dose_dose_cat as
truncate table x$assessment_dose_dose_cat;
insert into x$assessment_dose_dose_cat
(assessment_dose_id, dose_cat_id, useracc_id, creation_date)
select unique xa.assessment_dose_id, x.dose_cat_id,
       0, trunc(sysdate)
  from x$assessment_dose_xref xa, x$dose_cat_xref x
 where x.dose_cat = xa.dose_cat
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_DOSE_CAT
  add constraint X$ASSESSMENT_DOSE_DOSE_CAT_PK primary key (DOSE_CAT_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_DOSE_CAT
  add constraint X$ASSESSMENT_DOSE_DOSE_CAT_FK1 foreign key (DOSE_CAT_ID)
  references x$dose_cat_xref (DOSE_CAT_ID);
alter table X$ASSESSMENT_DOSE_DOSE_CAT
  add constraint X$ASSESSMENT_DOSE_DOSE_CAT_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
