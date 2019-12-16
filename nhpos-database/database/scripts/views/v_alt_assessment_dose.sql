create or replace force view 
v_alt_assessment_dose 
as
select a.mi_dose_code, 
       a.mi_dose_code_ref, 
       a.mi_dose_type,
       d1.mono_code, 
       d1.monograph_id,
       d1.ingredient_id, 
       d1.sub_ingredient_id, 
       d1.source_ingredient_id, 
       d1.assessment_dose_id,
       d1.qdu_min, 
       d1.qdu_max, 
       d1.qie_min, 
       d1.qie_max, 
       d1.quantity_unit_code,
       d1.min_freq, 
       d1.max_freq, 
       d1.frequency_unit_code, 
       d1.ratio_min, 
       d1.ratio_max,
       d2.ingredient_id alt_ingredient_id, 
       d2.sub_ingredient_id alt_sub_ingredient_id, 
       d2.source_ingredient_id alt_source_ingredient_id, 
       d2.assessment_dose_id alt_assessment_dose_id,
       d2.qdu_min alt_qdu_min, 
       d2.qdu_max alt_qdu_max, 
       d2.qie_min alt_qie_min, 
       d2.qie_max alt_qie_max, 
       d2.quantity_unit_code alt_quantity_unit_code,
       d2.min_freq alt_min_freq, 
       d2.max_freq alt_max_freq, 
       d2.frequency_unit_code alt_frequency_unit_code, 
       d2.ratio_min alt_ratio_min, 
       d2.ratio_max alt_ratio_max
  from x$alt_assessment_dose a, v_assessment_doses d1, v_assessment_doses d2
 where a.assessment_dose_id = d1.assessment_dose_id 
   and a.alt_assessment_dose_id = d2.assessment_dose_id
;
