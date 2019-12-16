create or replace view 
v_combo_assessment_dose
as
select a.combo_dose_code,
       d1.mono_code, d1.monograph_id, 
       d1.ingredient_id, d1.sub_ingredient_id, d1.source_ingredient_id, d1.assessment_dose_id, 
       d1.qdu_min, d1.qdu_max, d1.qie_min, d1.qie_max, d1.quantity_unit_code, 
       d1.min_freq, d1.max_freq, d1.frequency_unit_code, d1.ratio_min, d1.ratio_max
from x$combo_doses a, v_assessment_doses d1
where a.assessment_dose_id = d1.assessment_dose_id
;
create or replace public synonym v_combo_assessment_dose for v_combo_assessment_dose;
grant select on v_combo_assessment_dose to public;

