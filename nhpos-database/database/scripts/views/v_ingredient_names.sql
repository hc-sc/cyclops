create or replace force view 
v_ingredient_names
as 
select --unique
       i.ingred_id,
       i.ingred_authorized_name_eng nhpid_name_eng,
       i.ingred_authorized_name_fr nhpid_name_fr,
       x.common_name common_name_eng, x.common_name_fr,
       x.proper_name proper_name_eng, x.proper_name_fr,
       m.code mono_code, m.mono_name_eng, m.mono_name_fr,
       x.product_mono_code, x.single_mono_code,
       nc.ingred_name_type_code,
       nc.ingred_type_code, nc.ingred_type_name_eng, nc.ingred_type_name_fr,
       rl.rolespec_class_name ingred_role_code,
       rl.rolespec_name_eng ingred_role_name_eng, rl.rolespec_name_fr ingred_role_name_fr,
       nc.nhp_class_code, nc.nhp_class_name_eng, nc.nhp_class_name_fr,
       m.mono_id, 
       x.cn_syn_id common_name_id, -- qualified synonym IDs
       x.pn_syn_id proper_name_id, -- qualifies synonym IDs
       nc.ingred_name_type_id, nc.ingred_type_id, rl.ingredrole_id ingred_role_id, nc.nhp_class_id
  from x$monograph_ingred_name_xref x,
       INGREDIENTS i,
       MONOGRAPHS m,
       v_ingredient_role rl,
       v_ingredient_type nc
 where x.mono_id = m.mono_id
   and x.ingred_id = i.ingred_id
   and i.ingred_id = rl.ingred_id(+) 
   and nvl(rl.rolespec_class_name(+),'x') <> 'NonNHPRole' -- Ramy exclude non-nhp
   and i.ingred_id = nc.ingred_id(+)
union --all
-- non-compendial inngredients
select --unique
       i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
       cs.synonym_name_eng common_name_eng, cs.synonym_name_fr common_name_fr,
       ps.synonym_name_eng proper_name_eng, ps.synonym_name_fr proper_name_fr,
       null, null, null,
       null, null,
       nc.ingred_name_type_code,
       nc.ingred_type_code, nc.ingred_type_name_eng, nc.ingred_type_name_fr,
       rl.rolespec_class_name ingred_role_code,
       rl.rolespec_name_eng ingred_role_name_eng, rl.rolespec_name_fr ingred_role_name_fr,
       nc.nhp_class_code, nc.nhp_class_name_eng, nc.nhp_class_name_fr,
       null mono_id, cs.synonym_id common_name_id, ps.synonym_id proper_name_id,
       nc.ingred_name_type_id, nc.ingred_type_id, rl.ingredrole_id ingred_role_id, nc.nhp_class_id
  from INGREDIENTS i,
       INGREDIENT_QUALSYNO_CNS cn,
       QUALIFIED_SYNONYMS cq,
       SYNONYMS cs,
       INGREDIENT_QUALSYNO_PNS pn,
       QUALIFIED_SYNONYMS pq,
       SYNONYMS ps,
       v_ingredient_role rl,
       v_ingredient_type nc
where i.ingred_id = cn.ingred_id(+)
   and cn.qualsyno_id = cq.qualsyno_id(+)
   and cq.synonym_id = cs.synonym_id(+)
   and i.ingred_id = pn.ingred_id(+)
   and pn.qualsyno_id = pq.qualsyno_id(+)
   and pq.synonym_id = ps.synonym_id(+)
   and i.ingred_id = rl.ingred_id(+) 
   and nvl(rl.rolespec_class_name(+),'x') <> 'NonNHPRole' -- Ramy exclude non-nhp
   and i.ingred_id = nc.ingred_id(+)
   and (cs.synonym_name_eng is not null or ps.synonym_name_eng is not null)
union all
-- homeopathic ingredients
select i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
       cg.homeogentext_name_eng common_name_eng, cg.homeogentext_name_fr common_name_fr,
       pg.homeogentext_name_eng proper_name_eng, pg.homeogentext_name_fr proper_name_fr,
       null, null, null,
       null, null,
       nc.ingred_name_type_code,
       nc.ingred_type_code, nc.ingred_type_name_eng, nc.ingred_type_name_fr,
       --rl.rolespec_class_name 
       null ingred_role_code,
       --rl.rolespec_name_eng 
       null ingred_role_name_eng, 
       --rl.rolespec_name_fr 
       null ingred_role_name_fr,
       null, null, null,
       null mono_id, cg.homeogentext_id common_name_id, pg.homeogentext_id proper_name_id,
       nc.ingred_name_type_id, nc.ingred_type_id, 
       --rl.ingredrole_id 
       null ingred_role_id, 
       null
  from INGREDIENTS i,
       HOMEOPATHIC_COMMON_NAME_HGTS cn,
       HOMEOPATHIC_GENERIC_TEXTS cg,
       HOMEOPATHIC_PROPER_NAME_HGTS pn,
       HOMEOPATHIC_GENERIC_TEXTS pg,
       v_ingredient_type nc
--       v_ingredient_role rl
where i.ingred_id = cn.ingred_id(+)
   and cn.homeogentext_id = cg.homeogentext_id(+)
   and i.ingred_id = pn.ingred_id(+)
   and pn.homeogentext_id = pg.homeogentext_id(+)
   and i.ingred_id = nc.ingred_id
--   and i.ingred_id = rl.ingred_id(+) 
--   and rl.rolespec_class_name(+) <> 'NonNHPRole' -- Ramy exclude non-nhp
   and nc.ingred_type_code = 'HomeopathicSubstance'
   and (cg.homeogentext_name_eng is not null or pg.homeogentext_name_eng is not null)
;
