create or replace view v_sub_population as
select q.sub_pop_code,
       q.sub_pop_id,
--       q.sub_pop_code_x,
       sp.code nhpid_code, --???
       g.age_group,
       g.age_group_code,
       g.age_min,
       g.age_max,
--       u.unit_code age_unit_code,
--       u.unit_name_eng,
--       u.unit_name_fr,
       u.units_code age_unit_code,
       u.units_name_eng unit_name_eng,
       u.units_name_fr unit_name_fr,
       s.sex_code,
       s.sex_desc_eng,
       s.sex_desc_fr,
       c.condition_code sub_pop_condition_code,
       c.condition_desc_eng sub_pop_condition_desc_eng,
       c.condition_desc_fr sub_pop_condition_desc_fr,
       --q.sub_pop_decode sub_pop_desc_eng,
       --q.sub_pop_decode_fr sub_pop_desc_fr
       trim(c.condition_desc_eng||' '||g.age_group||' '||
       decode(s.sex_code,'N',null,s.sex_desc_eng)||' '||
       to_char(g.age_min)||' - '||to_char(g.age_max)||' '||
       lower(u.units_name_eng)) sub_pop_desc_eng,
       --q.sub_pop_decode_fr sub_pop_desc_fr
       trim(c.condition_desc_fr||' '||g.age_group_fr||' '||
       decode(s.sex_code,'N',null,s.sex_desc_fr)||' '||
       to_char(g.age_min)||' - '||to_char(g.age_max)||' '||
       lower(u.units_name_fr)) sub_pop_desc_fr
  from x$sub_population q,
       UNITS u, --v_units_of_measure u,
       x$sub_pop_sexes s,
       x$sub_pop_conditions c,
       x$sub_pop_age_groups g,
       SUB_POPULATION sp
 where 1=1
   and q.sex_id = s.sex_id(+)
   and q.condition_id = c.condition_id(+)
   and q.age_group_id = g.age_group_id(+)
   and g.age_unit_id = u.units_id(+)
   and q.nhpid_subpop_id = sp.subpop_id(+)
;
