--drop table x$sub_population;
--create table x$sub_population as
--alter table X$ASSESSMENT_DOSE_SUB_POP drop constraint X$ASSESSMENT_DOSE_SUB_POP_FK1;
truncate table x$sub_population;
insert into x$sub_population
select t.sub_pop_id,
       t.sub_pop_code,
       t.nhpid_subpop_id,
--       t.nhpid_code,
--       t.age_group,
--       t.age_min,
--       t.age_max,
--       u.units_id age_unit_id, 
       t.sex_id,
--       t.sub_pop_condition,
--       t.sub_pop_condition_fr,
       t.sub_pop_decode,
       t.sub_pop_decode_fr,
       decode(t.sub_pop_preferred,'y','y',null)  preferred,
       ag.age_group_id,
       c.condition_id,
       0, trunc(sysdate), null
  from x$sub_population_xref t, 
       units u,
       x$sub_pop_age_groups ag, 
       x$sub_pop_conditions c
 where case when t.age_min like '0.%' or t.age_max like '0.%' then 'month(s)' else t.age_unit end = lower(u.units_name_eng)||'(s)' 
   and u.unitstype_id = 4--'TIME'
   and lower(t.age_group) = lower(ag.age_group)
   and t.age_min = ag.age_min
   and nvl(t.age_max,-1) = nvl(ag.age_max,-1)
   and u.units_id = ag.age_unit_id
   and t.sub_pop_condition = c.condition_desc_eng(+)
   order by 1
;
commit;

/*
with x as (
select t.sub_pop_id,
       nvl(t.nhpid_code,
       case when lower(t.age_group) like 'ado%adu%' then 'ADU'
            when lower(t.age_group) like 'ado%' then 'AD'
            when lower(t.age_group) like 'adu%' then 'A'
            when lower(t.age_group) like 'chi%ado%adu%' then 'CHADU'
            when lower(t.age_group) like 'chi%ado%' then 'CHAD'
            when lower(t.age_group) like 'chi%' then 'CH'
            when lower(t.age_group) like 'inf%chi%ado%adu%' then 'INFCHADU'
            when lower(t.age_group) like 'inf%chi%' then 'INFCH'
            when lower(t.age_group) like 'inf%' then 'INF'
            else '???' end ||
       case when t.age_min like '0.%' then to_char(round(to_number(t.age_min) * 12,0)) else decode(t.age_min,'0','_',t.age_min) end ||
       case when t.age_max like '0.%' then to_char(round(to_number(t.age_max) * 12,0)) else decode(t.age_max,'and older',null,t.age_max) ||
       decode(t.sex,1,'M',2,'F') ||
       case when lower(t.sub_pop_condition) like 'prem%' then 'PRM'
            when lower(t.sub_pop_condition) like 'postm%' then 'PSM'
            when lower(t.sub_pop_condition) like 'peri%' then 'PP'
            when lower(t.sub_pop_condition) like 'preg%bre%peri%postm%' then 'PBPPPSM'
            when lower(t.sub_pop_condition) like 'preg%bre%' then 'PB'
            when lower(t.sub_pop_condition) like 'meno%postm%' then 'MPSM'
            else substr(t.sub_pop_condition,1,1) end
       end) sub_pop_code,
      t.sub_pop_code sub_pop_code_x,
      t.nhpid_code,
      t.age_group,
      case when t.age_min like '0.%' then round(to_number(t.age_min) * 12) 
           else to_number(t.age_min) end age_min,
      case when t.age_min like '0.%' then to_number(decode(t.age_max,'and older',null,t.age_max)) * 12
           when t.age_max like '0.%' then round(to_number(decode(t.age_max,'and older',null,t.age_max)) * 12)
           when t.age_max like '%older' then null
           else to_number(t.age_max) end age_max,
      u.units_id age_unit_id, 
      to_number(nvl(t.sex,'0')) sex_id,
      t.sub_pop_condition,
      t.sub_pop_decode,
      t.sub_pop_decode_fr
  from x$sub_population_xref t, units u
 where case when t.age_min like '0.%' or t.age_max like '0.%' then 'month(s)' else t.age_unit end = lower(u.units_name_eng)||'(s)' 
   and u.unitstype_id = 4--'TIME'
   and not regexp_like(t.sub_pop_code,'SP[0-9]+[x|y]') 
)
select x.sub_pop_id, x.sub_pop_code, x.sub_pop_code_x, x.nhpid_code,
       ag.age_group_id, x.sex_id, c.condition_id, 
       x.sub_pop_decode sub_pop_decode_eng, x.sub_pop_decode_fr
  from x, x$sub_pop_age_groups ag, x$sub_pop_conditions c
 where 1=1
   and lower(x.age_group) = lower(ag.age_group)
   and x.age_min = ag.age_min
   and nvl(x.age_max,999) = nvl(ag.age_max,999)
   and x.age_unit_id = ag.age_unit_id
   and x.sub_pop_condition = c.condition_desc_eng(+)
 order by 3 nulls first
;
*/
/*
-- Add/modify columns 
alter table X$SUB_POPULATION modify sub_pop_id not null;
alter table X$SUB_POPULATION modify sub_pop_code not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_PK primary key (sub_pop_id);
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_UK unique (sub_pop_code);
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_FK2 foreign key (age_group_id )
  references x$sub_pop_age_groups (AGE_GROUP_ID);
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_FK3 foreign key (sex_id )
  references x$sub_pop_sexes (SEX_ID);
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_FK4 foreign key (condition_id )
  references x$sub_pop_conditions (CONDITION_ID);
alter table X$SUB_POPULATION
  add constraint X$SUB_POPULATION_FK5 foreign key (sub_pop_id)
  references SUB_POPULATION (SUBPOP_ID) novalidate;
--
alter table X$ASSESSMENT_DOSE_SUB_POP
  add constraint X$ASSESSMENT_DOSE_SUB_POP_FK1 foreign key (SUB_POP_ID)
  references x$sub_population (SUB_POP_ID);-- novalidate;
*/

