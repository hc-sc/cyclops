--drop table x$assessment_dose_duration;
--create table x$assessment_dose_duration as
truncate table x$assessment_dose_duration;
insert into x$assessment_dose_duration
(assessment_dose_id, duration_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.duration_id,
       0, trunc(sysdate)
  from x$duration_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.duration_code)) = lower(trim(xr.duration_code))
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_DURATION
  add constraint X$ASSESSMENT_DOSE_DURATION_PK primary key (DURATION_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_DURATION
  add constraint X$ASSESSMENT_DOSE_DURATION_FK1 foreign key (DURATION_ID)
  references x$duration_xref (DURATION_ID);
alter table X$ASSESSMENT_DOSE_DURATION
  add constraint X$ASSESSMENT_DOSE_DURATION_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
