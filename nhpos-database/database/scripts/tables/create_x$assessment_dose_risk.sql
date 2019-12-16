--drop table x$assessment_dose_risk;
--create table x$assessment_dose_risk as
truncate table x$assessment_dose_risk;
insert into x$assessment_dose_risk
(assessment_dose_id, risk_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.risk_id
       ,0, trunc(sysdate)
  from x$risk_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.risk_code)) = lower(trim(xr.risk_code))
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_RISK
  add constraint X$ASSESSMENT_DOSE_RISK_PK primary key (RISK_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_RISK
  add constraint X$ASSESSMENT_DOSE_RISK_FK1 foreign key (RISK_ID)
  references x$risk_xref (RISK_ID);
alter table X$ASSESSMENT_DOSE_RISK
  add constraint X$ASSESSMENT_DOSE_RISK_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
