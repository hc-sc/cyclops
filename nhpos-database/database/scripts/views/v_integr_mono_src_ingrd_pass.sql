create or replace view
v_integr_mono_src_ingrd_pass
as
with i as (
select t.*, rownum row_id from (
select --unique
       i.product_mono_code i_product_mono_code,
       i.mono_code i_mono_code,
       i.source_group_code i_source_group_code
  from x_ingredient_matrix i
 where i.source_group_code is not null
 union
select --unique
       i.product_mono_code,
       i.mono_code,
       i.source_group_code
 from x_sub_ingredient_matrix i
 where i.source_group_code is not null
) t)
,m as (
select unique
       m.product_mono_code m_product_mono_code,
       m.mono_code m_mono_code,
       m.source_code m_source_code,
       row_id
  from x_monograph m
 where m.source_code is not null
)
,s as (
select unique
       s.product_mono_code s_product_mono_code,
       s.mono_code s_mono_code,
       s.source_group_code s_source_group_code,
       s.source_rule_code s_source_rule_code,
       row_id
  from x_sources s
where s.source_group_code||s.source_rule_code is not null
)
, ms as (
select unique
       m.m_product_mono_code, m.m_mono_code, m.m_source_code,
       s.s_product_mono_code, s.s_mono_code, s.s_source_rule_code,
       m.row_id m_row_id, s.row_id s_row_id
from m full join s
  on (nvl(m.m_product_mono_code,'x') = nvl(s.s_product_mono_code,'x')
    and nvl(m.m_mono_code,'x') = nvl(s.s_mono_code,'x')
    and m.m_source_code = s.s_source_rule_code)
)
, si as (
select unique
       i.i_product_mono_code, i.i_mono_code, i.i_source_group_code,
       s.s_product_mono_code, s.s_mono_code, s.s_source_group_code, s.s_source_rule_code,
       i.row_id i_row_id, s.row_id s_row_id
from i full join s
  on (nvl(i_product_mono_code,'x') = nvl(s.s_product_mono_code,'x')
    and nvl(i_mono_code,'x') = nvl(s.s_mono_code,'x')
    and i_source_group_code = s.s_source_group_code)
)
select unique
       m_product_mono_code product_mono_code, m_mono_code mono_code, x.mono_name_eng
--       ms.s_product_mono_code ms_product_mono_code, ms.s_mono_code ms_mono_code, ms.s_source_rule_code ms_source_rule_code, 
--       si.s_product_mono_code, si.s_mono_code, si.s_source_group_code, si.s_source_rule_code, 
--       i_product_mono_code, i_mono_code, i_source_group_code
  from ms, si, x$monograph_xref x
 where 1=1
   and nvl(ms.m_product_mono_code,'x') = nvl(si.s_product_mono_code,'x')
   and ms.m_mono_code = si.s_mono_code
   and ms.m_source_code = si.s_source_rule_code
   and nvl(ms.m_product_mono_code,'x') = nvl(x.product_mono_code(+),'x')
   and ms.m_mono_code = x.single_mono_code(+)
   and (si.i_mono_code is not null and si.s_mono_code is not null and ms.s_mono_code is not null and ms.m_mono_code is not null)
 order by 1 nulls last,2,3
;
