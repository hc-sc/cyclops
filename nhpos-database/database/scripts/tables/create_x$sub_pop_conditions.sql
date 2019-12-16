--drop table x$sub_pop_conditions cascade constraints;
--create table x$sub_pop_conditions as
truncate table x$sub_pop_conditions;
insert into x$sub_pop_conditions
(condition_id, condition_desc_eng, condition_desc_fr, 
 condition_code, display,useracc_id, creation_date)
select unique
       dense_rank() over (order by t.sub_pop_condition) condition_id,
       t.sub_pop_condition condition_desc_eng, 
       t.sub_pop_condition_fr condition_desc_fr,
       case when lower(t.sub_pop_condition) like 'br%' then 'BF'        --Breastfeeding
            when lower(t.sub_pop_condition) like 'men%post%' then 'MPSM' --Menopausal and postmenopausal
            when lower(t.sub_pop_condition) like 'men%' then 'MP'        --Menopausal
            when lower(t.sub_pop_condition) like 'per%post%' then 'PPM'  --Peri or postmenopausal
            when lower(t.sub_pop_condition) like 'post%' then 'PSM'      --Postmenopausal
            when lower(t.sub_pop_condition) like 'prem%' then 'PRM'      --Premenopausal
            when lower(t.sub_pop_condition) like 'preg%br%per%post%' then 'PBPP' --Pregnant, breastfeeding, and peri or postmenopausal
            when lower(t.sub_pop_condition) like 'preg%br%' then 'PGBF'  --Pregnant and breastfeeding
            when lower(t.sub_pop_condition) like 'preg%' then 'PG'       --Pregnant
       end condition_code,
       case when regexp_instr(t.sub_pop_condition,' and | or |,') > 0 then null else 1 end display,
       0, trunc(sysdate)
  from x$sub_population_xref t
 where t.sub_pop_condition is not null
;
commit;

--delete from x$sub_pop_conditions t where t.condition_id = 1 and t.condition_desc_fr not in ('Enceintes'); 
--alter table x$sub_pop_conditions add constraint x$sub_pop_conditions_pk primary key (condition_id);
