truncate table x$monograph_ingred_name_xref;
insert into  x$monograph_ingred_name_xref
(product_mono_code, single_mono_code, mono_code, mono_id, mono_name, 
 ingred_id, proper_name, proper_name_fr, pn_syn_id, common_name, common_name_fr, cn_syn_id,
 useracc_id, creation_date, row_id)
with cn as (
select i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, cq.qualsyno_id,
       cs.synonym_name_eng common_name_eng, cs.synonym_name_fr common_name_fr
  from INGREDIENTS i, INGREDIENT_QUALSYNO_CNS cn, QUALIFIED_SYNONYMS cq, SYNONYMS cs
where i.ingred_id = cn.ingred_id
   and cn.qualsyno_id = cq.qualsyno_id
   and cq.synonym_id = cs.synonym_id
)
,pn as (
select i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, pq.qualsyno_id,
       ps.synonym_name_eng proper_name_eng, ps.synonym_name_fr proper_name_fr
  from INGREDIENTS i,
       INGREDIENT_QUALSYNO_PNS pn,
       QUALIFIED_SYNONYMS pq,
       SYNONYMS ps
where i.ingred_id = pn.ingred_id(+)
   and pn.qualsyno_id = pq.qualsyno_id(+)
   and pq.synonym_id = ps.synonym_id(+)
)
,m as (
select to_number(m.ingred_id_nhpid) ingred_id, m.common_name, m.proper_name,
       x.mono_id, x.mono_code, x.product_mono_code, x.single_mono_code
  from x_monograph m, x$monograph_xref x
 where m.ingred_id_nhpid is not null
   and nvl(m.product_mono_code,'x') = nvl(x.product_mono_code,'x') and m.mono_code = x.single_mono_code
)
select q.*, 0 useracc_id, trunc(sysdate) creation_date, rownum row_id from (
select m.product_mono_code, m.single_mono_code, m.mono_code, m.mono_id, null mono_name, cn.ingred_id,
       null proper_name_eng, null proper_name_fr, null pn_qs_id,
       cn.common_name_eng, cn.common_name_fr, cn.qualsyno_id cn_qs_id
  from m,cn
 where m.ingred_id = cn.ingred_id
   and lower(trim(m.common_name)) = lower(trim(cn.common_name_eng))
union all
select unique 
       m.product_mono_code, m.single_mono_code, m.mono_code, m.mono_id, null mono_name, pn.ingred_id, 
       pn.proper_name_eng, pn.proper_name_fr, pn.qualsyno_id pn_qs_id,
       null common_name_eng, null common_name_fr, null cn_qs_id
  from m,pn
 where m.ingred_id = pn.ingred_id
   and lower(trim(m.proper_name)) = lower(trim(pn.proper_name_eng))
) q
;

commit;

