--drop table x$assessment_dose_sub_pop;
--create table x$assessment_dose_sub_pop as
truncate table x$assessment_dose_sub_pop;
insert into x$assessment_dose_sub_pop
(assessment_dose_id, sub_pop_id, useracc_id, creation_date)
select unique xad.assessment_dose_id, xsp.sub_pop_id 
       ,0, trunc(sysdate)
  from x$sub_population_xref xsp, 
       x$assessment_dose_xref xad
 where xad.sub_pop_code = xsp.sub_pop_code
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_SUB_POP
  add constraint X$ASSESSMENT_DOSE_SUB_POP_PK primary key (SUB_POP_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_SUB_POP
  add constraint X$ASSESSMENT_DOSE_SUB_POP_FK1 foreign key (SUB_POP_ID)
  references x$sub_population (SUB_POP_ID) novalidate;
alter table X$ASSESSMENT_DOSE_SUB_POP
  add constraint X$ASSESSMENT_DOSE_SUB_POP_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
