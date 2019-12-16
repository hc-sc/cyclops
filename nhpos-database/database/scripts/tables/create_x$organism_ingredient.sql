truncate table x$organism_ingredient;
insert into x$organism_ingredient
(org_ingred_id, ingred_id, organism_id, product_mono_code, mono_code, 
source_material_part, orgparttype_id, source_material_strain, orgtype_id,
source_id, source_material_org_type, useracc_id, creation_date)
with i as (
select --+ materialize
       m.product_mono_code, m.mono_code, m.source_code, si.subing_id_nhpid ingred_id
  from x_monograph m,
       x_sub_ingredient_matrix si
 where nvl(m.product_mono_code,'x') <> 'HOMEO'
   and m.mono_code = si.mono_code
   and nvl(m.product_mono_code, 'x') = nvl(si.product_mono_code, 'x')
union
select m.product_mono_code, m.mono_code, m.source_code, to_number(m.ingred_id_nhpid)
  from x_monograph m
 where nvl(m.product_mono_code,'x')  <> 'HOMEO'
   and m.source_code is not null
)
,m as ( -- mono-source-ingred add org types and org part types
select i.product_mono_code, i.mono_code, i.ingred_id, 
       to_number(s.source_ingred_id_nhpid) source_id, 
       s.source_material_part, s.source_material_strain, s.organism_type_code_nhpid,
       ot.orgtype_id, pt.orgparttype_id, source_material_org_type,
       s.source_nhpid_name, s.organism_name
  from i, x_sources s,
       ORGANISM_PART_TYPES pt,
       ORGANISM_TYPES ot
 where i.source_code = s.source_rule_code
   and s.organism_type_code_nhpid = ot.orgtype_code(+)
   and lower(trim(s.source_material_part)) = lower(trim(pt.orgparttype_name_eng(+)))
)
,q as (-- mono-source-ingred add organism
select m.ingred_id,
       m.source_id, 
       o.organism_id,
       m.product_mono_code, m.mono_code,
       m.source_material_part,
       m.orgparttype_id,
       m.source_material_strain,
       m.orgtype_id,
       m.source_material_org_type
  from m, ORGANISMS o
 where lower(trim(m.organism_name)) = lower(trim(o.organism_name(+)))
 union -- where organism is ingredient
select q.ingred_id,
       m.source_id, 
       q.organism_id,
       m.product_mono_code, m.mono_code,
       m.source_material_part,
       m.orgparttype_id,
       m.source_material_strain,
       m.orgtype_id,
       m.source_material_org_type
  from (select i.ingred_id, o.organism_id, o.organism_name
          from INGREDIENTS i, ORGANISMS o
         where lower(trim(i.ingred_authorized_name_eng)) = lower(trim(o.organism_name))
       ) q, m
 where q.ingred_id = m.ingred_id(+)
   and lower(trim(q.organism_name)) = lower(trim(m.organism_name(+)))
 union -- where organism is group
select q.ingred_id,
       m.source_id, 
       q.organism_id,
       m.product_mono_code, m.mono_code,
       m.source_material_part,
       m.orgparttype_id,
       m.source_material_strain,
       m.orgtype_id,
       m.source_material_org_type
  from (select i.ingred_id, og.organism_id, o.organism_name
          from INGREDIENTS i, 
               IN_ORG_GRP_ORGANISM og,
               ORGANISMS o
         where i.org_grp_id = og.org_grp_id
           and og.organism_id = o.organism_id) q, m
 where q.ingred_id = m.ingred_id(+)
   and lower(trim(q.organism_name)) = lower(trim(m.organism_name(+)))
 union -- where organism is subingred (source???)
select q.ingred_id,
       m.source_id, 
       null organism_id,
       m.product_mono_code, m.mono_code,
       m.source_material_part,
       q.orgparttype_id,
       m.source_material_strain,
       m.orgtype_id,
       m.source_material_org_type
  from (select s.ingred_id, op.orgparttype_id
          from SUBINGREDIENTS s,
               ORGANISM_PART_SUBINGREDIENTS ps,
               ORGANISM_PARTS op
         where s.subingred_id = ps.subingred_id
           and ps.orgpart_id = op.orgpart_id) q, m
 where q.ingred_id = m.ingred_id(+)
   and q.orgparttype_id = m.orgparttype_id(+)
)
select rownum org_ingred_id, 
       ingred_id,
       organism_id,
       product_mono_code,
       mono_code,
       source_material_part,
       orgparttype_id,
       source_material_strain,
       orgtype_id,
       source_id,
       source_material_org_type,
       0 useracc_id,
       trunc(sysdate) creation_date
from q
 where ingred_id is not null
   and (organism_id is not null
    or product_mono_code is not null
    or mono_code is not null
    or source_material_part is not null
    or orgparttype_id is not null
    or source_material_org_type is not null
    or source_material_strain is not null)
;
commit;
