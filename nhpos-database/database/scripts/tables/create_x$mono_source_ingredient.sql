--drop table X$MONO_SOURCE_INGREDIENT;
--create table X$MONO_SOURCE_INGREDIENT as
truncate  table X$MONO_SOURCE_INGREDIENT;
insert into X$MONO_SOURCE_INGREDIENT 
(mono_src_ingred_id, monograph_id, product_mono_code, mono_code, mono_code_nhpid,
 source_id, source_name, ingredient_id, ingred_name,
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
       --unique --1732
       i.ingred_id, ix.nhpid_name ingred_name, 
       ix.product_mono_code, ix.mono_code, 
       ix.source_group_code
  from INGREDIENTS i, x_ingredient_matrix ix
where (i.ingred_id = to_number(ix.ingred_id_nhpid) 
   or  lower(trim(i.ingred_authorized_name_eng)) = lower(trim(ix.nhpid_name)))
   and ix.source_group_code is not null
 union
select --unique --281
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
select rownum mono_src_ingred_id, q.*,
       0, trunc(sysdate)
from (
select unique --2792
       m.mono_id monograph_id, m.product_mono_code, m.mono_code, m.code mono_code_nhpid,
       s.source_id, s.source_name,
       x.ingred_id ingredient_id, x.ingred_name
  from m, s, x
 where 1=1
   and nvl(m.product_mono_code, 'x') = nvl(s.product_mono_code, 'x')
   and nvl(m.mono_code,'x') = nvl(s.mono_code, 'x') -- add HOMEO
   and m.source_code = s.source_rule_code
   and nvl(s.product_mono_code,'x') = nvl(x.product_mono_code, 'x')
   and nvl(s.mono_code,'x') = nvl(x.mono_code, 'x') -- add HOMEO
   and s.source_group_code = x.source_group_code
) q;

commit;
/*-- uncomment when changing HOMEO 
-- Add artificial source for HOMEO ingredients to fit it into X$ASSESSMENT_DOSE table
prompt Insert 0 for HOMEO
insert into X$MONO_SOURCE_INGREDIENT 
(mono_src_ingred_id, monograph_id, product_mono_code, mono_code, mono_code_nhpid,
 source_id, source_name, ingredient_id, ingred_name, useracc_id, creation_date)
select unique
       0 mono_src_ingred_id, 
       t.mono_id monograph_id, 
       m.product_mono_code, 
       m.mono_code, 
       m.nhpid_mono_code mono_code_nhpid,
       x.ingred_id_nhpid source_id, 
       x.nhpid_name source_name, 
       x.ingred_id_nhpid ingredient_id, 
       x.nhpid_name ingred_name, 
       0 useracc_id, trunc(sysdate) creation_date
  from x_monograph m, MONOGRAPHS t, x_ingredient_matrix x
 where m.product_mono_code = 'HOMEO' 
   and m.nhpid_mono_code = t.code
   and m.product_mono_code = x.product_mono_code
   and m.ingred_id_nhpid = x.ingred_id_nhpid
;
commit;
*/

