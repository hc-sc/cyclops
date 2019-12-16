--drop table x$sub_pop_groups;
/*
create table x$sub_pop_groups
(group_id number not null,
 group_desc_eng varchar2(255) not null,
 group_desc_fr  varchar2(255),
 group_min_age  number,
 group_max_age  number,
 group_age_unit_id  number,
 constraint x$sub_pop_groups_pk primary key (group_id)
);
*/
truncate table x$sub_pop_groups;
insert into x$sub_pop_groups
(group_id, group_desc_eng, group_desc_fr, 
group_min_age, group_max_age, group_age_unit_id,
useracc_id, creation_date)
select q.*, 0, trunc(sysdate) from (
select 1 group_id,'Neonates' group_desc_eng,'Neonates' group_desc_fr,0 group_min_age,4 group_max_age,167 group_age_unit_id from dual union all
select 2,'Infants','Infants',0,12,121 from dual union all
select 3,'Children','Children',0,12,169 from dual union all
select 4,'Adolescents','Adolescents',9,19,169 from dual union all
select 5,'Adults','Adults',18,null,169 from dual 
) q;
commit;

-- Create/Recreate primary, unique and foreign key constraints 
--alter table X$SUB_POP_GROUPS add constraint X$SUB_POP_GROUPS_FK1 foreign key (GROUP_AGE_UNIT_ID) references units (UNITs_ID);
