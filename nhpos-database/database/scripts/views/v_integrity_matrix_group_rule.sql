create or replace view v_integrity_matrix_group_rule as
with i as (
select t.*, rownum row_id from (
select --unique
       i.product_mono_code,
       i.mono_code,
       i.group_rule_group_id,
       i.rule_group_id,
       to_number(i.ingred_id_nhpid) ingred_id_nhpid,
       null subing_id_nhpid
  from x_ingredient_matrix i
 where i.group_rule_group_id||i.rule_group_id is not null
 union
select --unique
       i.product_mono_code,
       i.mono_code,
       i.group_rule_group_id,
       i.rule_group_id,
       to_number(i.ingred_id_nhpid),
       to_number(subing_id_nhpid)
 from x_sub_ingredient_matrix i
 where i.group_rule_group_id||i.rule_group_id is not null
) t)
,g as (
select unique
       g.product_mono_code g_product_mono_code,
       g.mono_code g_mono_code,
       g.group_group_id g_group_group_id,
       g.group_id g_group_id,
       to_number(g.nhpid_ingred_id) g_ingred_id_nhpid,
       to_number(g.sub_ingred_id_nhpid) g_subing_id_nhpid,
       row_id
  from x_ingredient_groups g
)
,r as (
select unique
       g.product_mono_code g_product_mono_code,
       g.mono_code g_mono_code,
       g.groupgroup_id g_group_group_id,
       g.group_id g_group_id,
       null,null,
       row_id
  from x_mono_rules g
where g.product_mono_code||g.mono_code||g.groupgroup_id||g.group_id is not null
)
select 'INGRED-GROUP' diff,
       i.product_mono_code, i.mono_code, i.group_rule_group_id, i.rule_group_id, i.ingred_id_nhpid, i.subing_id_nhpid,
       g.g_product_mono_code, g.g_mono_code, g.g_group_group_id, g.g_group_id, g.g_ingred_id_nhpid, g.g_subing_id_nhpid
from i full join g
  on (nvl(i.product_mono_code,'x') = nvl(g.g_product_mono_code,'x')
    and nvl(i.mono_code,'x') = nvl(g.g_mono_code,'x')
    and nvl(i.group_rule_group_id,'x') = nvl(g.g_group_group_id,'x')
    and nvl(i.rule_group_id,'x') = nvl(g.g_group_id,'x')
    and nvl(i.ingred_id_nhpid,0) = nvl(g.g_ingred_id_nhpid,0)
    and nvl(i.subing_id_nhpid,0) = nvl(g.g_subing_id_nhpid,0))
 where i.row_id is null or g.row_id is null
 union all
select unique 'INGRED-RULES' diff,
       i.product_mono_code,i.mono_code,i.group_rule_group_id,i.rule_group_id, null ingred_id_nhpid, null subing_id_nhpid,
       r.g_product_mono_code,r.g_mono_code,r.g_group_group_id,r.g_group_id, null g_ingred_id_nhpid, null g_subing_id_nhpid
from i full join r
  on (nvl(i.product_mono_code,'x') = nvl(r.g_product_mono_code,'x')
    and nvl(i.mono_code,'x') = nvl(r.g_mono_code,'x')
    and nvl(i.group_rule_group_id,'x') = nvl(r.g_group_group_id,'x')
    and nvl(i.rule_group_id,'x') = nvl(r.g_group_id,'x'))
--    and nvl(i.ingred_id_nhpid,0) = nvl(r.g_ingred_id_nhpid,0)
--    and nvl(i.subing_id_nhpid,0) = nvl(r.g_subing_id_nhpid,0)
 where i.row_id is null or r.row_id is null
 union all
select unique 'RULES-GROUP' diff,
       r.g_product_mono_code,r.g_mono_code,r.g_group_group_id,r.g_group_id, null g_ingred_id_nhpid, null g_subing_id_nhpid,
       g.g_product_mono_code, g.g_mono_code, g.g_group_group_id, g.g_group_id, null g_ingred_id_nhpid, null g_subing_id_nhpid
from r full join g
  on (nvl(r.g_product_mono_code,'x') = nvl(g.g_product_mono_code,'x')
    and nvl(r.g_mono_code,'x') = nvl(g.g_mono_code,'x')
    and nvl(r.g_group_group_id,'x') = nvl(g.g_group_group_id,'x')
    and nvl(r.g_group_id,'x') = nvl(g.g_group_id,'x'))
 where r.row_id is null or g.row_id is null
 order by 1,2 nulls last,3,4,5,6,7,8,9
;
