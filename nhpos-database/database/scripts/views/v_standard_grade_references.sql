create or replace force view
v_standard_grade_references
as
select r.ref_code, r.ref_name_eng, r.ref_name_fr, 
       sgr.standgraderef_ishomeopathic stand_grade_ref_is_homeopathic,
       sgr.standgraderef_min_dil_amount stand_grade_ref_min_dil_amount,
       r.ref_id,sgr.standgraderef_id, r.citation_id
  from STANDARD_GRADE_REFERENCES sgr, REFS r
 where sgr.ref_id = r.ref_id
;

--grant select on v_standard_grade_references to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_standard_grade_references for nhpdweb_dev.v_standard_grade_references;

