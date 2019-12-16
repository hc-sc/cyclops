create or replace force view
v_units_of_measure
as
select u.units_code unit_code, u.units_name_eng unit_name_eng, u.units_name_fr unit_name_fr,
       ut.unitstype_code unit_type_code, ut.unitstype_name_eng unit_type_name_eng, ut.unitstype_name_fr unit_type_name_fr, 
       u.units_id unit_id, ut.unitstype_id unit_type_id,
       null unit_subtype, 
       u.units_equivalent unit_equivalent, 
       --u.units_preferred preferred, 
       case when ut.unitstype_code = 'TIME' and u.units_code = 'd' then 'y'
            when ut.unitstype_code = 'TIME' and u.units_code = 'h' then 'n'
            when ut.unitstype_code = 'TIME' and u.units_code = 'min' then 'n'
            when ut.unitstype_code = 'TIME' and u.units_code = 'mnths' then 'y'
            when ut.unitstype_code = 'TIME' and u.units_code = 's' then 'n'
            when ut.unitstype_code = 'TIME' and u.units_code = 'wk' then 'y'
            when ut.unitstype_code = 'TIME' and u.units_code = 'yr' then 'y'
            else u.units_preferred end preferred, 
       --u.units_ratio_to_base ratio_to_base,
       --decode(u.units_code,'d',1,'s',-1,u.units_ratio_to_base) ratio_to_base,
       case when ut.unitstype_code = 'TIME' and u.units_code = 'd' then 1
            when ut.unitstype_code = 'TIME' and u.units_code = 'h' then 0.0416
            when ut.unitstype_code = 'TIME' and u.units_code = 'min' then -1
            when ut.unitstype_code = 'TIME' and u.units_code = 'mnths' then 30
            when ut.unitstype_code = 'TIME' and u.units_code = 's' then -1
            when ut.unitstype_code = 'TIME' and u.units_code = 'wk' then 7
            when ut.unitstype_code = 'TIME' and u.units_code = 'yr' then 365
            else u.units_ratio_to_base end ratio_to_base, 
       null is_diluted
  from units u, units_types ut
 where u.unitstype_id = ut.unitstype_id
;
---- 
--  select x.unit_code,
--       x.unit_name_eng,
--       x.unit_name_fr,
--       x.unit_type_code,
--       x.unit_type_name_eng,
--       x.unit_type_name_fr,
--       x.unit_id,
--       x.unit_type_id,
--       x.unit_subtype,
--       x.unit_equivalent,
--       x.preferred,
--       case
--        when x.unit_code = 'd' then 1
--        when x.unit_code = 's' then -1
--        else x.ratio_to_base
--        end as ratio_to_base,
--       x.is_diluted
--  from x$units_of_measure x;
--;
--grant select on v_units_of_measure to nhpdweb_user;
--create or replace public synonym v_units_of_measure for nhpdweb_dev.v_units_of_measure;
