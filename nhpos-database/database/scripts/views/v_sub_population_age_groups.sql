create or replace force view 
v_sub_population_age_groups 
as
select t.age_group_id,
       t.age_group,
       t.age_group_code,
       t.age_min,
       t.age_max,
       u.unit_code age_unit_code,
       u.unit_name_eng,
       u.unit_name_fr
       --gg.group_id
  from X$SUB_POP_AGE_GROUPS t,
       --X$SUB_POP_GROUP_AGE_GROUPS gg,
       v_units_of_measure u
 where 1=1--t.age_group_id = gg.age_group_id
   and t.age_unit_id = u.unit_id(+)
;

--grant select on v_sub_population_age_groups to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_sub_population_age_groups for nhpdweb_dev.v_sub_population_age_groups;
