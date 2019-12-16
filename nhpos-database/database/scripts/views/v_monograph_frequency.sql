create or replace force view
v_monograph_frequency
as
select distinct 
       m.code mono_code, ad.product_mono_code, ad.single_mono_code,
       u.unit_code, u.unit_name_eng, u.unit_name_fr, u.unit_id
  from x$assessment_dose ad, MONOGRAPHS m, v_units_of_measure u
 where ad.monograph_id = m.mono_id and ad.frequency_unit_id = u.unit_id(+)
;

create or replace public synonym v_monograph_frequency for v_monograph_frequency;
grant select on v_monograph_frequency to public;
