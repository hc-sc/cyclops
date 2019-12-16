--drop table x$assessment_dose_dfu;
--create table x$assessment_dose_dfu as
truncate table x$assessment_dose_dfu;
insert into x$assessment_dose_dfu
(assessment_dose_id, dfu_id, useracc_id, creation_date)
select unique 
       xa.assessment_dose_id, xr.dfu_id
       ,0, trunc(sysdate)
  from x$dfu_xref xr,
       x$assessment_dose_xref xa
 where lower(trim(xa.dfu_group_code)) = lower(trim(xr.dfu_group_code))
;

commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_DFU
  add constraint X$ASSESSMENT_DOSE_DFU_PK primary key (DFU_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_DFU
  add constraint X$ASSESSMENT_DOSE_DFU_FK1 foreign key (DFU_ID)
  references x$dfu_xref (DFU_ID);
alter table X$ASSESSMENT_DOSE_DFU
  add constraint X$ASSESSMENT_DOSE_DFU_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
