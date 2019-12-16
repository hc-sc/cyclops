create or replace force view 
v_assessment_dose_dose_cat 
as
select  x.dose_cat_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.dose_cat,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_dose_cat x,
       x$assessment_dose ad,
       x$dose_cat_xref sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.dose_cat_id = sp.dose_cat_id
   and ad.monograph_id = m.mono_id
;
