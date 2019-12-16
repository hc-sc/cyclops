--drop table x$assessment_dose_stor_cond;
--create table x$assessment_dose_stor_cond as
truncate table x$assessment_dose_stor_cond;
insert into x$assessment_dose_stor_cond
(assessment_dose_id, storage_cond_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.storage_cond_id
       ,0, trunc(sysdate)
  from x$storage_conditions_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.storage_conditions_group_code)) = lower(trim(xr.storage_cond_code))--lower(trim(xr.storage_cond_group_code))
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_STOR_COND
  add constraint X$ASSESSMENT_DOSE_STOR_COND_PK primary key (STORAGE_COND_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_STOR_COND
  add constraint X$ASSESSMENT_DOSE_STORCOND_FK1 foreign key (STORAGE_COND_ID)
  references x$storage_conditions_xref (STORAGE_COND_ID);
alter table X$ASSESSMENT_DOSE_STOR_COND
  add constraint X$ASSESSMENT_DOSE_STORCOND_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
