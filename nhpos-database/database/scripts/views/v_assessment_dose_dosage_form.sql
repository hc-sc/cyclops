create or replace force view
v_assessment_dose_dosage_form
as
select  x.dosage_form_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        df.dosefrm_code,
        sp.dosage_form_type,
        df.dosefrm_name_eng,
        df.dosefrm_name_fr,
        sp.valid_for_compendial,
        sp.allow_ingredient_units,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose ad, 
       x$assessment_dose_dosage_form x, 
       x$dose_form_types sp,
       DOSAGE_FORMS df,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.dosage_form_id = sp.dosefrm_id
   and x.dosage_form_id = df.dosefrm_id
   and ad.monograph_id = m.mono_id
;
