create or replace force view
v_dosage_forms
as
with x as (
select df.dosefrm_code dosage_form_code,
       df.dosefrm_name_eng dosage_form_name_eng, df.dosefrm_name_fr dosage_form_name_fr,
       decode(xt.dosage_form_type,'Discrete','y','Non-discrete','n',null) discrete,
       xt.valid_for_compendial,
       xt.allow_ingredient_units,
       df.dosefrm_id,
       df.units_id
  from DOSAGE_FORMS df,
       x$dose_form_types xt
 where df.dosefrm_id = xt.dosefrm_id(+)
)
select dosage_form_code||nvl2(synonym_code,'-',null)||synonym_code dosage_form_syn_code,
       nvl(synonym_code, dosage_form_code) dosage_form_code,
       nvl2(synonym_code, synonym_name_eng, dosage_form_name_eng) dosage_form_name_eng,
       nvl2(synonym_code, synonym_name_fr, dosage_form_name_fr) dosage_form_name_fr,
       discrete,
       valid_for_compendial,
       allow_ingredient_units,
       dosefrm_id,
       synonym_id
       --units_id
  from (select x.*, ds.synonym_code, ds.synonym_id, ds.synonym_name_eng, ds.synonym_name_fr 
          from x, V_DOSAGE_FORM_SYNONYMS ds
         where x.dosefrm_id = ds.dosefrm_id
         union all
        select x.*, null, null, null, null from x)
;

--grant select on v_dosage_forms to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_dosage_forms for nhpdweb_dev.v_dosage_forms;
