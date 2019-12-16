create or replace force view
v_assessment_dose_sub_pop
as
select  x.sub_pop_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.sub_pop_code,
        sp.sub_pop_desc_eng sub_pop_desc_eng,
        sp.sub_pop_desc_fr sub_pop_desc_fr,
        sp.age_group,
        sp.age_min,
        sp.age_max,
        sp.age_unit_code,
        sp.sex_desc_eng sex,
        sp.sub_pop_condition_desc_eng condition,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_sub_pop x,
       x$assessment_dose ad,
       v_sub_population sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.sub_pop_id = sp.sub_pop_id
   and ad.monograph_id= m.mono_id
;
