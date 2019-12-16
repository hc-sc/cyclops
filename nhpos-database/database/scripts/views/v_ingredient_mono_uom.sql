create or replace force view
v_ingredient_mono_uom
as
select unique --716
       i.ingred_id, i.ingredspec_class_name,--16816
       m.mono_id, m.code mono_code,
       vu.unit_type_code, vu.unit_name_eng, vu.unit_name_fr, vu.unit_code
  from x$assessment_dose xd,
       INGREDIENTS i, 
       MONOGRAPHS m,
       v_units_of_measure vu
 where xd.ingredient_id = i.ingred_id
   and xd.monograph_id = m.mono_id(+)
   and xd.quantity_unit_id = vu.unit_id
;

--grant select on v_ingredient_mono_uom to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_ingredient_mono_uom for nhpdweb_dev.v_ingredient_mono_uom;
