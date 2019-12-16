--drop table x$assessment_dose_dosage_form cascade constraints;
--create table x$assessment_dose_dosage_form as
truncate table x$assessment_dose_dosage_form;
alter table x$assessment_dose_dosage_form nologging;
insert /*+ parallel(4) */ into x$assessment_dose_dosage_form
(dosage_form_id,assessment_dose_id, useracc_id, creation_date)
select unique 
       t.dosefrm_id dosage_form_id,
       r.assessment_dose_id,
       0, trunc(sysdate)
from x_dosage_form x, DOSAGE_FORMS t, x$assessment_dose_xref r
where t.dosefrm_name_eng = x.dosage_form_decode
and x.dosage_form_group_code = r.dosage_form_group
;
commit;
alter table x$assessment_dose_dosage_form logging;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE_DOSAGE_FORM
  add constraint X$ASSESS_DOSE_DOSAGE_FORM_PK primary key (DOSAGE_FORM_ID, ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE_DOSAGE_FORM
  add constraint X$ASSESS_DOSE_DOSAGE_FORM_FK1 foreign key (DOSAGE_FORM_ID)
  references DOSAGE_FORMS (DOSEFRM_ID);
alter table X$ASSESSMENT_DOSE_DOSAGE_FORM
  add constraint X$ASSESS_DOSE_DOSAGE_FORM_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
*/
