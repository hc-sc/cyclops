--drop table x$assessment_dose_use;
--create table x$assessment_dose_use as
truncate table x$assessment_dose_use;
insert into x$assessment_dose_use
(assessment_dose_id, use_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.use_id
       ,0, trunc(sysdate)
  from x$use_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.use_code)) = lower(trim(xr.use_code))
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_USE
  add constraint X$ASSESSMENT_DOSE_USE_PK primary key (USE_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_USE
  add constraint X$ASSESSMENT_DOSE_USE_FK1 foreign key (USE_ID)
  references x$use_xref (USE_ID);
alter table X$ASSESSMENT_DOSE_USE
  add constraint X$ASSESSMENT_DOSE_USE_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
