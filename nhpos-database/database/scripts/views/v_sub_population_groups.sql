create or replace force view
v_sub_population_groups
as
with q as (
select unique t.age_group_code, gg.group_id
  from X$SUB_POP_AGE_GROUPS t, 
       X$SUB_POP_GROUP_AGE_GROUPS gg
 where t.age_group_id = gg.age_group_id
   and not regexp_like(t.age_group,'( and )|( or )|(,)') 
)
select t.group_id, q.age_group_code group_code, t.group_desc_eng, t.group_desc_fr, 
       t.group_min_age, t.group_max_age,
       u.unit_code group_age_unit_code, u.unit_name_eng, u.unit_name_fr
  from X$SUB_POP_GROUPS t,
       v_units_of_measure u, q
 where t.group_age_unit_id = u.unit_id(+)
   and t.group_id = q.group_id
 order by t.group_min_age
;

--grant select on v_sub_population_groups to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_sub_population_groups for nhpdweb_dev.v_sub_population_groups;

