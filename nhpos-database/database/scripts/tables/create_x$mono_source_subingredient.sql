--drop table X$MONO_SOURCE_SUB_INGREDIENT;
--create table X$MONO_SOURCE_SUB_INGREDIENT as
truncate table X$MONO_SOURCE_SUB_INGREDIENT;
insert into X$MONO_SOURCE_SUB_INGREDIENT
(mono_src_sub_ingred_id, monograph_id, product_mono_code, mono_code, mono_code_nhpid, 
 source_id, source_name, sub_ingredient_id, ingred_name,
 useracc_id, creation_date)
with m as (-- map mono name to nhpid mono_id
select /*+ materialize */
       unique --4554
       m.mono_id, m.code, m.mono_name_eng,
       q.product_mono_code,q.mono_code,
       q.source_code 
  from MONOGRAPHS m, x_monograph q
 where q.nhpid_mono_code = m.code
    or lower(trim(q.mono_name)) = lower(trim(m.mono_name_eng))
)
,x as (-- map (sub)ingred name to nhpid ingred
select /*+ materialize */
       unique
       i.ingred_id, s.subing_name_nhpid ingred_name, 
       s.product_mono_code, s.mono_code, 
       s.source_group_code
  from INGREDIENTS i, x_sub_ingredient_matrix s
where (i.ingred_id = to_number(s.subing_id_nhpid) 
   or  lower(trim(i.ingred_authorized_name_eng)) = lower(trim(s.subing_name_nhpid)))
   and s.source_group_code is not null
)
,s as ( -- map excel.sources to nhpid.ingredients
select /*+ materialize */
       unique --3772
       i.ingred_id source_id, i.ingred_authorized_name_eng source_name, 
       s.product_mono_code, s.mono_code,
       s.source_group_code, s.source_rule_code
  from INGREDIENTS i, x_sources s
 where to_number(decode(s.source_ingred_id_nhpid,'#N/A',null,s.source_ingred_id_nhpid)) = i.ingred_id(+) -- not all sources are ingredients, we need a source_group/rule_code
   --or  lower(trim(i.ingred_authorized_name_eng)) =  lower(trim(nvl(nvl(s.source_nhpid_name, s.source_proper_name), s.source_common_name)))
)
select rownum mono_src_sub_ingred_id, q.*,
       0, trunc(sysdate)
from (
select unique --2792
       m.mono_id monograph_id, m.product_mono_code, m.mono_code, m.code mono_code_nhpid,
       s.source_id, s.source_name,
       x.ingred_id sub_ingredient_id, x.ingred_name
  from m, s, x
 where 1=1
   and nvl(m.product_mono_code, 'x') = nvl(s.product_mono_code, 'x')
   and m.mono_code = s.mono_code
   and m.source_code = s.source_rule_code
   and nvl(s.product_mono_code,'x') = nvl(x.product_mono_code, 'x')
   and s.mono_code = x.mono_code
   and s.source_group_code = x.source_group_code
) q;

commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$MONO_SOURCE_SUB_INGREDIENT
  add constraint X$MONO_SOURCE_INGRED_PK primary key (mono_src_sub_ingred_id);
alter table X$MONO_SOURCE_SUB_INGREDIENT
  add constraint X$MONO_SOURCE_INGRED_FK1 foreign key (SUB_INGREDIENT_ID)
  references ingredients (INGRED_ID);
alter table X$MONO_SOURCE_SUB_INGREDIENT
  add constraint X$MONO_SOURCE_INGRED_FK2 foreign key (SOURCE_ID)
  references ingredients (INGRED_ID);
alter table X$MONO_SOURCE_SUB_INGREDIENT
  add constraint X$MONO_SOURCE_INGRED_FK3 foreign key (MONOGRAPH_ID)
  references monographs (MONO_ID);
*/
