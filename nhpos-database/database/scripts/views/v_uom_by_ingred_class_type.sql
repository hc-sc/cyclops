create or replace force view 
v_uom_by_ingred_class_type 
as
with vu as ( -- "Unit of Measure Types by Ingredient Class and Type" document by Roxane
select nc.nhpclass_id nhp_class_id,
       nc.nhpclass_code nhp_class_code,
       vu.unit_id, vu.unit_code, vu.unit_name_eng, vu.unit_name_fr,
       vu.unit_type_id, vu.unit_type_code,
       null role_spec_id, null role_spec_name, vu.preferred
  from NHP_CLASSIFICATIONS nc,
       v_units_of_measure vu
 where vu.unit_type_code in ('IMPD','MASS','PERC','VOLUME')
    or (vu.unit_type_code = 'BIOU' and nc.nhpclass_code in ('EXT','NHANML','PROB','BACT','ISO','EXT'))
    or (vu.unit_type_code in ('EQUV','EQUW','MOLE','WGHT') and nc.nhpclass_code = 'MIN')
    or (vu.unit_type_code = 'INTU' and nc.nhpclass_code in ('VIT','AA','ISO','EXT'))
    or (vu.unit_type_code = 'MICC' and nc.nhpclass_code in ('FUNG','PROB','BACT'))
    or (vu.unit_type_code = 'QUAS' and nc.nhpclass_code = 'NMI')
union all
select null nhp_class_id, null nhp_class_code,
       vu.unit_id, vu.unit_code, vu.unit_name_eng, vu.unit_name_fr,
       vu.unit_type_id, vu.unit_type_code,
       rs.rolespec_id role_spec_id, rs.rolespec_name_eng role_spec_name,
       vu.preferred
  from ROLE_SPECIALS rs,
       v_units_of_measure vu
 where rs.rolespec_name_eng = 'Non-medicinal'
   and vu.unit_type_code in ('IMPD','MASS','PERC','VOLUME','QUAS')
)
--,q as (
---- all compendial
--select i.ingred_id,
--       m.mono_id, m.code mono_code,
--       null restrict_id, xd.quantity_unit_id
--  from x$assessment_dose xd,
--       INGREDIENTS i,
--       MONOGRAPHS m
-- where xd.ingredient_id = i.ingred_id
--   and xd.monograph_id = m.mono_id(+)
-- union
--select i.ingred_id,
--       null mono_id, null mono_code,
--       null restrict_id, null quantity_unit_id
--  from INGREDIENTS i
--)
select unique
       nc.ingred_type_code, -- vp 2019-07-15
       nc.nhp_class_code, nc.nhp_class_name_eng, nc.nhp_class_name_fr,
       vu.unit_type_code, vu.unit_code, vu.unit_name_eng, vu.unit_name_fr, vu.preferred,
       -- DB-222
       case when nc.nhp_class_code = 'MIN' and vu.unit_type_code in ('EQUV','EQUW','MOLE','WGHT') then 'y'
            when vu.unit_type_code = 'INTU' and nc.nhp_class_code in ('VIT','AA','ISO','EXT') then 'y'
            else 'n' end additional_unit
       --q.mono_id, q.mono_code,
       --null restrict_id
  from vu, v_ingredient_type nc --, q
 where 1=1
--   and (vu.unit_id = q.quantity_unit_id or q.quantity_unit_id is null)
--   and q.ingred_id = nc.ingred_id
   and vu.nhp_class_code = nc.nhp_class_code
;
