create or replace force view
v_assessment_dose_use
as
select  x.use_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.use_code ,
        sp.use_decode_eng,
        sp.use_decode_fr,
        decode(count(unique ad.ingredient_id) over (partition by ad.monograph_id, sp.use_code),1,'Specific','General') use_type,
        ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_use x, 
       x$assessment_dose ad,
       x$use_xref sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.use_id = sp.use_id
   and ad.monograph_id = m.mono_id
;

