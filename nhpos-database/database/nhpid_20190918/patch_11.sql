-----------------------------------
--patch #11
-- date: 2019/09/25
-- fixes: V_ALT_ASSESSMENT_DOSE
-- log: patch_11.log
----------------------------------
spool patch_11.log
prompt Patch 11
prompt update view V_ALT_ASSESSMENT_DOSE

create or replace view
v_alt_assessment_dose
as
select a.assessment_dose_id, a.alt_assessment_dose_id,
       x.mi_dose_code, x.mi_dose_code_ref, x.mi_dose_type
  from x$alt_assessment_dose a, 
       x$assessment_dose_xref x, 
       x$assessment_dose_xref xa 
 where a.assessment_dose_id = x.assessment_dose_id 
   and a.alt_assessment_dose_id = xa.assessment_dose_id
;

