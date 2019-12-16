create or replace force view
v_assessment_dose_stor_cond
as
select  x.storage_cond_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.storage_cond_code,
        sp.storage_cond_desc_eng storage_cond_decode_eng,
        sp.storage_cond_desc_fr storage_cond_decode_fr,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_stor_cond x, 
       x$assessment_dose ad, 
       x$storage_conditions_xref sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.storage_cond_id = sp.storage_cond_id
   and ad.monograph_id = m.mono_id
;
