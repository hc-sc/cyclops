create or replace force view 
v_sub_population_conditions 
as
select t.condition_id,
       t.condition_desc_eng,
       t.condition_desc_fr,
       t.condition_code
  from X$SUB_POP_CONDITIONS t
 where t.display = 1
;

--grant select on v_sub_population_conditions to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_sub_population_conditions for nhpdweb_dev.v_sub_population_conditions;
