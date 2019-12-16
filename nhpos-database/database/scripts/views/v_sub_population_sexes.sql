create or replace force view 
v_sub_population_sexes
as
select t.sex_id,
       t.sex_code,
       t.sex_desc_eng,
       t.sex_desc_fr
  from X$SUB_POP_SEXES t
;

--grant select on v_sub_population_sexes to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_sub_population_sexes for nhpdweb_dev.v_sub_population_sexes;
