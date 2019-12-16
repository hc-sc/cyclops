create or replace force view
v_dosage_form_units
as
select df.dosage_form_syn_code,
       df.dosage_form_code, 
       df.dosage_form_name_eng, df.dosage_form_name_fr, 
       df.allow_ingredient_units,
       du.dosageunit_code dosage_unit_code, 
       du.dosageunit_name_eng dosage_unit_name_eng, du.dosageunit_name_fr dosage_unit_name_fr,
       df.dosefrm_id, du.dosageunit_id,
       u.unit_type_code dosage_unit_type_code
  from DOSAGEFORM_DOSAGEUNITS dd, 
       v_dosage_forms df, 
       DOSAGE_UNITS du,
       v_units_of_measure u
 where dd.dosageunit_id = du.dosageunit_id
   and dd.dosefrm_id = df.dosefrm_id
   and du.dosageunit_code = u.unit_code(+)
;

--grant select on v_dosage_form_units to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_dosage_form_units for nhpdweb_dev.v_dosage_form_units;
