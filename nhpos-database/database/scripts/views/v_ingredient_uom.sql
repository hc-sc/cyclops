create or replace force view
v_ingredient_uom
as
with c as (-- ingredient nhp class
select nc.nhpclass_code nhp_class_code, nc.nhpclass_name_eng nhp_class_name_eng, nc.nhpclass_name_fr nhp_class_name_fr,
       inc.ingred_id, inc.nhpclass_id
  from ingredient_nhpclassifications inc,
       nhp_classifications nc
where inc.nhpclass_id = nc.nhpclass_id
)
-- all compendial
select unique
       --i.ingred_id,
       --i.ingredspec_class_name,
       isp.ingredspec_class_name ingredient_type,
       c.nhp_class_code, c.nhp_class_name_eng, c.nhp_class_name_fr,
       vu.unit_type_code, vu.unit_name_eng, vu.unit_name_fr,
       m.mono_id, m.code mono_code,
       null restrict_id
  from x$assessment_dose xd,
       ingredients i,
       ingredient_specials isp,
       ingredient_categories ic,
       monographs m,
       v_units_of_measure vu, c
where xd.ingredient_id = i.ingred_id
   and xd.quantity_unit_id = vu.unit_id
   and xd.monograph_id = m.mono_id(+)
   and i.ingred_id = c.ingred_id
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
union --all
select unique
       --i.ingred_id,
       --i.ingredspec_class_name,
       isp.ingredspec_class_name ingredient_type,
       c.nhp_class_code, c.nhp_class_name_eng, c.nhp_class_name_fr,
       vu.unit_type_code, vu.unit_name_eng, vu.unit_name_fr,
       null, null mono_code,
       null restrict_id
  from x$assessment_dose xd,
       ingredients i,
       ingredient_specials isp,
       ingredient_categories ic,
       v_units_of_measure vu, c
where xd.ingredient_id = i.ingred_id
   and xd.quantity_unit_id = vu.unit_id
   and i.ingred_id = c.ingred_id
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
union --all
-- with roa restriction
select --i.ingred_id,
       --i.ingredspec_class_name,
       isp.ingredspec_class_name ingredient_type,
       c.nhp_class_code, c.nhp_class_name_eng, c.nhp_class_name_fr,
       vu.unit_type_code, vu.unit_name_eng, vu.unit_name_fr,
       null, null mono_code,
       r.restrict_id
  from ingredients i,
       restrictions r,
       ingredient_specials isp,
       ingredient_categories ic,
       v_units_of_measure vu, c
where i.restrict_id = r.restrict_id
   and r.units_id =  vu.unit_id
   and r.units_id is not null
   and i.ingred_id = c.ingred_id
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
union --all
-- subingredient yeld
select --i.ingred_id,
       --i.ingredspec_class_name,
       isp.ingredspec_class_name  ingredient_type,
       c.nhp_class_code, c.nhp_class_name_eng, c.nhp_class_name_fr,
       vu.unit_type_code, vu.unit_name_eng, vu.unit_name_fr,
       null, null mono_code,
       null restrict_id
  from subingredients s,
       ingredients i,
       ingredient_specials isp,
       ingredient_categories ic,
       v_units_of_measure vu, c
where s.ingred_id = i.ingred_id
   and s.units_id =  vu.unit_id
   and s.units_id is not null
   and i.ingred_id = c.ingred_id
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
;

--grant select on v_ingredient_mono_uom to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_ingredient_mono_uom for nhpdweb_dev.v_ingredient_mono_uom;
