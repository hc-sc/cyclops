--drop table x$sub_pop_group_age_groups cascade constraints;
--drop table x$sub_pop_age_groups cascade constraints;
--create table x$sub_pop_age_groups as
truncate table x$sub_pop_age_groups;
insert into x$sub_pop_age_groups
(age_group_id, age_group, age_group_fr, age_group_code, 
 age_min, age_max, age_unit_id,useracc_id, creation_date)
with q as (
select --unique --91
       --t.sub_pop_code,
       --t.sub_pop_id,
       t.age_group,
       t.age_group||' (f)' age_group_fr,
       case when lower(t.age_group) like 'ado%adu%' then 'ADU' --Adolescents and adults
            when lower(t.age_group) like 'ado%' then 'AD'      --Adolescents
            when lower(t.age_group) like 'adu%' then 'AU'      --Adults
            when lower(t.age_group) like 'ch%ado%adu%' then 'CADU' --Children, adolescents, and adults
            when lower(t.age_group) like 'ch%ado%' then 'CHAD' --Children and adolescents
            when lower(t.age_group) like 'ch%' then 'CH'       --Children
            when lower(t.age_group) like 'in%ch%ado%adu%' then 'ALL' --Infants, children, adolescents, and adults
            when lower(t.age_group) like 'in%ch%' then 'IFCH'  --Infants and children
            when lower(t.age_group) like 'in%' then 'IF'       --Infants
            else regexp_replace(t.sub_pop_code,'[0-9]','')
       end age_group_code,
--       t.age_min,
       case when t.age_min like '0.%' then round(to_number(t.age_min) * 12) 
            else to_number(t.age_min) end age_min,
--       t.age_max,
       case when t.age_min like '0.%' then to_number(decode(t.age_max,'and older',null,t.age_max)) * 12
            when t.age_max like '0.%' then round(to_number(decode(t.age_max,'and older',null,t.age_max)) * 12)
            when t.age_max like '%older' then null
            else to_number(t.age_max) end age_max,
       --t.age_unit,
       u.units_id age_unit_id
       --t.sub_pop_group_id
  from x$sub_population_xref t, units u --156
 where case when t.age_min like '0.%' or t.age_max like '0.%' then 'month'
            else replace(lower(age_unit),'(s)') end = lower(u.units_name_eng(+))
 union
select 'Neonates','Neonates','NNT',0,4,167 from dual
 order by 1,3
)
select rownum as age_group_id, q.*, 0, trunc(sysdate) from q
;
commit;

-- Add/modify columns 
--alter table X$SUB_POP_AGE_GROUPS modify age_group_code VARCHAR2(20);
-- Create/Recreate primary, unique and foreign key constraints 
--alter table x$sub_pop_age_groups add constraint x$sub_pop_age_groups_pk primary key (age_group_id);

--create table X$SUB_POP_GROUP_AGE_GROUPS as
truncate table X$SUB_POP_GROUP_AGE_GROUPS;
insert into X$SUB_POP_GROUP_AGE_GROUPS
(age_group_id, group_id)
select age_group_id, group_id
  from (select ag.age_group_id, 
               case when instr(lower(ag.age_group),lower(g.group_desc_eng)) > 0 
                    then g.group_id else 0 end group_id
               --,ag.age_group,g.group_desc_eng
          from X$SUB_POP_AGE_GROUPS ag, X$SUB_POP_GROUPS g)
 where group_id > 0
;
commit;

/*
alter table X$SUB_POP_GROUP_AGE_GROUPS 
  add constraint X$SUB_POP_GRP_AGE_GRPS_PK primary key (age_group_id, group_id);
alter table X$SUB_POP_GROUP_AGE_GROUPS 
  add constraint X$SUB_POP_GRP_AGE_GRPS_FK1 foreign key (age_group_id)
  references X$SUB_POP_AGE_GROUPS (age_group_id);
alter table X$SUB_POP_GROUP_AGE_GROUPS 
  add constraint X$SUB_POP_GRP_AGE_GRPS_FK2 foreign key (group_id)
  references X$SUB_POP_GROUPS (group_id);
alter table X$SUB_POP_AGE_GROUPS
  add constraint X$SUB_POP_AGE_GROUPS_FK1 foreign key (AGE_UNIT_ID)
  references UNITS (UNITS_ID);
*/

