--drop table x$sub_population_xref;
--create table x$sub_population_xref as
truncate table x$sub_population_xref;
insert into x$sub_population_xref
(sub_pop_id, sub_pop_code, nhpid_subpop_id, nhpid_code, age_group, age_min, age_max, age_unit, 
 sex, sex_id, nhpid_condition, sub_pop_condition, sub_pop_condition_fr, sub_pop_decode, 
 sub_pop_decode_fr, sub_pop_max_age, sub_pop_min_age, units_id_max, units_id_min, sub_pop_preferred,
 useracc_id, creation_date)
with q as (
select SP,
       COND,
       GENDER,
       min(SUBPOP_ID) subpop_id,
       min(CODE) code,
       SUBPOP_MAX_AGE,
       SUBPOP_MIN_AGE,
       UNITS_ID_MAX,
       UNITS_ID_MIN,
       SUBPOP_PREFERRED
  from (
select --unique
       --regexp_substr(subpop_name_eng,'[0-9]{1,2}',1,1) mn, 
       --regexp_substr(subpop_name_eng,'[0-9]{1,2}|older|over',1,2) mx,
       case when subpop_name_eng like 'Adults, adolescents and children%' 
            then 'Children, adolescents, and adults'
            when subpop_name_eng like 'Adolescents and adults%' or subpop_name_eng like 'Adults and adolescents%' 
            then 'Adolescents and adults'
            when subpop_name_eng like 'Adolescents%' 
            then 'Adolescents'
            when subpop_name_eng like 'Children%' 
            then 'Children'
            when subpop_name_eng like 'Infants%' 
            then 'Infants'
            when subpop_name_eng like 'Adult%' or subpop_name_eng like 'Breastfeeding%' 
              or lower(subpop_name_eng) like '%males%' or subpop_name_eng like 'Pregnant%' 
              or lower(subpop_name_eng) like '%menopausal%'
            then 'Adults'
            else '???' end sp,
       case when code like 'BRF%' then 'Breastfeeding'
            when code like 'PRG%' then 'Pregnant'
            when code like 'MPMW%' then 'Menopausal and postmenopausal'
            when code like 'PMW%' then 'Postmenopausal'
            else null end cond,
       case when lower(subpop_in_crit_eng) like '%female%' then 'Females'
            when lower(code) like '%women%' then  'Females'
            when lower(subpop_in_crit_eng) like '%male%' then 'Males'
            else null end gender,
       subpop_id, 
       code,  
       --subpop_name_eng,  subpop_in_crit_eng,  
       subpop_max_age,  
       subpop_min_age,  
       units_id_max, 
       units_id_min,
       subpop_preferred
  from SUB_POPULATION t
) group by SP,
       COND,
       GENDER,
       --SUBPOP_ID,
       --CODE,
       SUBPOP_MAX_AGE,
       SUBPOP_MIN_AGE,
       UNITS_ID_MAX,
       UNITS_ID_MIN,
       SUBPOP_PREFERRED
)
,t as (
select q.subpop_id  nhpid_subpop_id,
      x.sub_pop_code, 
      q.code nhpid_code,
      x.age_group, 
--      x.age_min, 
--      x.age_max, 
--      case when x.age_min like '0.%' then round(to_number(x.age_min) * 12) 
--           else to_number(x.age_min) end age_min,
      to_number(x.age_min) age_min,
--      case when x.age_min like '0.%' then to_number(decode(x.age_max,'and older',null,x.age_max)) * 12
--           when x.age_max like '0.%' then round(to_number(decode(x.age_max,'and older',null,x.age_max)) * 12)
--           when x.age_max like '%older' then null
--           else to_number(x.age_max) end age_max,
      case when x.age_max like '%older' then null
           else to_number(x.age_max) end age_max,
      x.age_unit, 
      x.sex,
      to_number(nvl(x.sex,'0')) sex_id,
      q.cond nhpid_condition,
      x.sub_pop_condition, 
      x.sub_pop_condition_fr, 
      --x.sex_decode, 
      --x.sex_decode_fr, 
      x.sub_pop_decode, 
      x.sub_pop_decode_fr,
      q.code, 
      --q.subpop_name_eng sub_pop_name_eng, 
      --q.subpop_name_fr sub_pop_name_fr, 
      --q.subpop_comment_eng sub_pop_comment_eng, 
      --q.subpop_comment_fr sub_pop_comment_fr, 
      --q.subpop_desc_eng sub_pop_desc_eng, 
      --q.subpop_desc_fr sub_pop_desc_fr, 
      --q.subpop_ex_crit_eng sub_pop_ex_crit_eng, 
      --q.subpop_ex_crit_fr sub_pop_ex_crit_fr, 
      --q.subpop_in_crit_eng sub_pop_in_crit_eng, 
      --q.subpop_in_crit_fr sub_pop_in_crit_fr, 
      q.subpop_max_age sub_pop_max_age, 
      q.subpop_min_age sub_pop_min_age, 
      q.units_id_max, 
      q.units_id_min, 
      --q.subpop_creation_date, 
      --q.subpop_lastupdate_date, 
      --q.useracc_id, 
      q.subpop_preferred sub_pop_preferred
  from x_sub_pop x, q
 where x.age_group = q.sp(+)
   and to_number(x.age_min) = q.subpop_min_age(+)--q.mn(+)
   and to_number(decode(x.age_max,'and older','0',x.age_max)) = q.subpop_max_age(+)--decode(q.mx(+),'over','and older','older','and older',q.mx(+))
   and nvl(x.sub_pop_condition,'x') = nvl(q.cond(+),'x')
   and nvl(x.sex_decode,'x') = nvl(q.gender(+),'x')
   and x.sub_pop_code is not null
 order by --q.subpop_id nulls last, 
          x.age_group, x.age_min, x.age_max
) --select * from t
select --nque nvl(t.subpop_id, rownum + xx.mx) sub_pop_id,
      rownum sub_pop_id,
      t.sub_pop_code,
      t.nhpid_subpop_id, 
      t.code nhpid_code,
      t.age_group, 
      nvl(t.age_min,0) age_min, 
      t.age_max age_max, 
      t.age_unit, 
      t.sex, 
      t.sex_id,
      t.nhpid_condition,
      t.sub_pop_condition, 
      t.sub_pop_condition_fr, 
      --t.sex_decode, 
      --t.sex_decode_fr, 
      --t.code, 
      t.sub_pop_decode, 
      t.sub_pop_decode_fr,
      --t.sub_pop_name_eng, 
      --t.sub_pop_name_fr, 
      --t.sub_pop_comment_eng, 
      --t.sub_pop_comment_fr, 
      --t.sub_pop_desc_eng, 
      --t.sub_pop_desc_fr, 
      --t.subpop_ex_crit_eng sub_pop_ex_crit_eng, 
      --t.subpop_ex_crit_fr sub_pop_ex_crit_fr, 
      --t.sub_pop_in_crit_eng, 
      --t.sub_pop_in_crit_fr, 
      t.sub_pop_max_age, 
      t.sub_pop_min_age, 
      t.units_id_max, 
      t.units_id_min, 
      --t.subpop_creation_date, 
      --t.subpop_lastupdate_date, 
      --t.useracc_id, 
      t.sub_pop_preferred,
      0, trunc(sysdate)
  from t --, (select max(s.subpop_id) mx from sub_population s) xx
;
commit;

/*
drop table x$sub_population_xref;
create table x$sub_population_xref as
with q as (
select regexp_substr(subpop_name_eng,'[0-9]{1,2}',1,1) mn, regexp_substr(subpop_name_eng,'[0-9]{1,2}|older|over',1,2) mx,
       case when subpop_name_eng like 'Adults, adolescents and children%' 
            then 'Children, adolescents, and adults'
            when subpop_name_eng like 'Adolescents and adults%' or subpop_name_eng like 'Adults and adolescents%' 
            then 'Adolescents and adults'
            when subpop_name_eng like 'Adolescents%' 
            then 'Adolescents'
            when subpop_name_eng like 'Children%' 
            then 'Children'
            when subpop_name_eng like 'Infants%' 
            then 'Infants'
            when subpop_name_eng like 'Adult%' or subpop_name_eng like 'Breastfeeding%' 
              or lower(subpop_name_eng) like '%males%' or subpop_name_eng like 'Pregnant%' 
              or lower(subpop_name_eng) like '%menopausal%'
            then 'Adults'
            else '???' end sp,
       case when code like 'BRF%' then 'Breastfeeding'
            when code like 'PRG%' then 'Pregnant'
            when code like 'MPMW%' then 'Menopausal'
            when code like 'PMW%' then 'Postmenopausal'
            else null end cond,
       case when lower(subpop_in_crit_eng) like '%female%' then 'Females'
            when lower(code) like '%women%' then  'Females'
            when lower(subpop_in_crit_eng) like '%male%' then 'Males'
            else null end gender,
       t.*--subpop_id, code,  subpop_name_eng,  subpop_in_crit_eng,  subpop_max_age,  subpop_min_age,  units_id_max, subpop_preferred, units_id_min
  from SUB_POPULATION t
)
,t as (
select q.subpop_id,
      x.sub_pop_code, 
      q.code nhpid_code,
      x.age_group, 
--      x.age_min, 
--      x.age_max, 
      case when x.age_min like '0.%' then round(to_number(x.age_min) * 12) 
           else to_number(x.age_min) end age_min,
      case when x.age_min like '0.%' then to_number(decode(x.age_max,'and older',null,x.age_max)) * 12
           when x.age_max like '0.%' then round(to_number(decode(x.age_max,'and older',null,x.age_max)) * 12)
           when x.age_max like '%older' then null
           else to_number(x.age_max) end age_max,
      x.age_unit, 
      x.sex,
      to_number(nvl(x.sex,'0')) sex_id,
      x.sub_pop_condition, 
      x.sub_pop_condition_fr, 
      x.sex_decode, 
      x.sex_decode_fr, 
      x.sub_pop_decode, 
      x.sub_pop_decode_fr,
      q.code, 
      q.subpop_name_eng sub_pop_name_eng, 
      q.subpop_name_fr sub_pop_name_fr, 
      q.subpop_comment_eng sub_pop_comment_eng, 
      q.subpop_comment_fr sub_pop_comment_fr, 
      q.subpop_desc_eng sub_pop_desc_eng, 
      q.subpop_desc_fr sub_pop_desc_fr, 
      --q.subpop_ex_crit_eng sub_pop_ex_crit_eng, 
      --q.subpop_ex_crit_fr sub_pop_ex_crit_fr, 
      q.subpop_in_crit_eng sub_pop_in_crit_eng, 
      q.subpop_in_crit_fr sub_pop_in_crit_fr, 
      q.subpop_max_age sub_pop_max_age, 
      q.subpop_min_age sub_pop_min_age, 
      q.units_id_max, 
      q.units_id_min, 
      --q.subpop_creation_date, 
      --q.subpop_lastupdate_date, 
      --q.useracc_id, 
      q.subpop_preferred sub_pop_preferred
  from x_sub_pop x, q
 where x.age_group = q.sp(+)
   and x.age_min = q.mn(+)
   and x.age_max = decode(q.mx(+),'over','and older','older','and older',q.mx(+))
   and nvl(x.sub_pop_condition,'x') = nvl(q.cond(+),'x')
   and nvl(x.sex_decode,'x') = nvl(q.gender(+),'x')
   and x.sub_pop_code is not null
 order by q.subpop_id nulls first, x.age_group, x.age_min, x.age_max
)
select nvl(t.subpop_id, rownum + xx.mx) sub_pop_id,
      t.sub_pop_code, 
      t.code nhpid_code,
      t.age_group, 
      t.age_min, 
      t.age_max, 
      t.age_unit, 
      t.sex, 
      t.sub_pop_condition, 
      t.sub_pop_condition_fr, 
      t.sex_decode, 
      t.sex_decode_fr, 
      t.sub_pop_decode, 
      t.sub_pop_decode_fr,
      t.code, 
      t.sub_pop_name_eng, 
      t.sub_pop_name_fr, 
      t.sub_pop_comment_eng, 
      t.sub_pop_comment_fr, 
      t.sub_pop_desc_eng, 
      t.sub_pop_desc_fr, 
      --t.subpop_ex_crit_eng sub_pop_ex_crit_eng, 
      --t.subpop_ex_crit_fr sub_pop_ex_crit_fr, 
      t.sub_pop_in_crit_eng, 
      t.sub_pop_in_crit_fr, 
      t.sub_pop_max_age, 
      t.sub_pop_min_age, 
      t.units_id_max, 
      t.units_id_min, 
      --t.subpop_creation_date, 
      --t.subpop_lastupdate_date, 
      --t.useracc_id, 
      t.sub_pop_preferred
  from t, (select max(s.subpop_id) mx from sub_population s) xx
  order by 1;
*/

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SUB_POPULATION_XREF
  add constraint X$SUB_POPULATION_XREF_PK primary key (SUB_POP_ID);
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SUB_POPULATION_XREF
  add constraint X$SUB_POPULATION_XREF_FK1 foreign key (UNITS_ID_MAX)
  references units (UNITS_ID);
alter table X$SUB_POPULATION_XREF
  add constraint X$SUB_POPULATION_XREF_FK2 foreign key (SUB_POP_ID)
  references sub_population (SUBPOP_ID) novalidate;
*/

