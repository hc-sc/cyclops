create or replace force view
v_assessment_dose_duration
as
select  x.duration_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.duration_code ,
        sp.duration_decode_eng,
        sp.duration_decode_fr,
        sp.min_duration,
        sp.max_duration,
        sp.unit_id,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose ad,
       x$assessment_dose_duration x,
       x$duration_xref sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.duration_id = sp.duration_id
   and ad.monograph_id = m.mono_id
;

