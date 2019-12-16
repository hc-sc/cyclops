--drop table x$assessment_dose_mop;
--create table x$assessment_dose_mop as
truncate table x$assessment_dose_mop;
insert into x$assessment_dose_mop
(assessment_dose_id, prep_method_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.prep_method_id
       ,0, trunc(sysdate)
  from x$preparation_methods_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.prep_group_code)) = lower(trim(xr.prep_group_code))
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_MOP
  add constraint X$ASSESSMENT_DOSE_MOP_PK primary key (prep_method_id, assessment_dose_id);
alter table X$ASSESSMENT_DOSE_MOP
  add constraint X$ASSESSMENT_DOSE_MOP_FK1 foreign key (prep_method_id)
  references x$preparation_methods_xref (PREP_METHOD_ID);
alter table X$ASSESSMENT_DOSE_MOP
  add constraint X$ASSESSMENT_DOSE_MOP_FK2 foreign key (assessment_dose_id)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
