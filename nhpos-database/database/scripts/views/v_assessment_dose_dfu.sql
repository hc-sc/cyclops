create or replace force view 
v_assessment_dose_dfu
as
select distinct x.dfu_id,
       ad.assessment_dose_id,
       m.mono_code,
       m.product_mono_code,
       m.single_mono_code,
       --
       sp.dfu_group_code,
       case when sp.unique_dfu_code is null
            then sp.dfu_group_code
            else sp.unique_dfu_code
       end as unique_dfu_code,
       case when sp.unique_dfu_decode is null
            then sp.dfu_group_decode_eng
            else sp.unique_dfu_decode
       end as dfu_group_decode_eng,
       case when sp.unique_dfu_decode_fr is null
            then sp.dfu_group_decode_fr
            else sp.unique_dfu_decode_fr
       end as dfu_group_decode_fr,
       sp.dfu_type,
       ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_dfu x,
       x$assessment_dose ad,
       x$dfu_xref sp,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.dfu_id = sp.dfu_id
   and ad.monograph_id = m.mono_id
;
