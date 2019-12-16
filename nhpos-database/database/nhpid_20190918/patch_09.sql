-----------------------------------
--patch #09
-- date: 2019/09/25
-- fixes: V_INGREDIENT_NAMES
-- log: patch_09.log
----------------------------------
spool patch_09.log
prompt Patch 09
prompt update view V_INGREDIENT_NAMES

CREATE OR REPLACE FORCE VIEW 
V_INGREDIENT_NAMES 
--("INGRED_ID", "NHPID_NAME_ENG", "NHPID_NAME_FR", "COMMON_NAME_ENG", "COMMON_NAME_FR", "PROPER_NAME_ENG", "PROPER_NAME_FR", "MONO_CODE", "MONO_NAME_ENG", "MONO_NAME_FR", "PRODUCT_MONO_CODE", "SINGLE_MONO_CODE", "INGRED_NAME_TYPE_CODE", "INGRED_TYPE_CODE", "INGRED_TYPE_NAME_ENG", "INGRED_TYPE_NAME_FR", "INGRED_ROLE_CODE", "INGRED_ROLE_NAME_ENG", "INGRED_ROLE_NAME_FR", "NHP_CLASS_CODE", "NHP_CLASS_NAME_ENG", "NHP_CLASS_NAME_FR", "MONO_ID", "COMMON_NAME_ID", "PROPER_NAME_ID", "INGRED_NAME_TYPE_ID", "INGRED_TYPE_ID", "INGRED_ROLE_ID", "NHP_CLASS_ID")
AS 
  select i.ingred_id,
       i.ingred_authorized_name_eng nhpid_name_eng,
       i.ingred_authorized_name_fr nhpid_name_fr,
       DECODE(cs.synonym_id, NULL, x.common_name, cs.synonym_name_eng) common_name_eng,
       DECODE(cs.synonym_id, NULL, x.common_name_fr, cs.synonym_name_fr) common_name_fr,
       DECODE(ps.synonym_id, NULL, x.proper_name, ps.synonym_name_eng) proper_name_eng,
       DECODE(ps.synonym_id, NULL, x.proper_name_fr, ps.synonym_name_fr) proper_name_fr,
        m.code mono_code, m.mono_name_eng, m.mono_name_fr,
       x.product_mono_code, x.single_mono_code,
       nc.ingred_name_type_code,
       nc.ingred_type_code, nc.ingred_type_name_eng, nc.ingred_type_name_fr,
       rl.rolespec_class_name ingred_role_code,
       rl.rolespec_name_eng ingred_role_name_eng, rl.rolespec_name_fr ingred_role_name_fr,
       nc.nhp_class_code, nc.nhp_class_name_eng, nc.nhp_class_name_fr,
       me.mono_id, cs.synonym_id common_name_id, ps.synonym_id proper_name_id,
       nc.ingred_name_type_id, nc.ingred_type_id, rl.ingredrole_id ingred_role_id, nc.nhp_class_id
  from MONOGRAPH_ENTRIES me,
      INGREDIENTS i,
       MONOGRAPHS m,
       MONOGRAPH_ENTRY_QUALSYNO_CNS cqc,
       QUALIFIED_SYNONYMS cqs,
       SYNONYMS cs,
       MONOGRAPH_ENTRY_QUALSYNO_PNS pqc,
       QUALIFIED_SYNONYMS pqs,
       SYNONYMS ps,
       v_ingredient_role rl,
      v_ingredient_type nc,
       x$monograph_ingred_name_xref x
where me.mono_id = m.mono_id
   and me.ingred_id = i.ingred_id
   and me.monoent_id = cqc.monoent_id(+)
   and cqc.qualsyno_id = cqs.qualsyno_id(+)
   and cqs.synonym_id = cs.synonym_id(+)
   and me.monoent_id = pqc.monoent_id(+)
   and pqc.qualsyno_id = pqs.qualsyno_id(+)
   and pqs.synonym_id = ps.synonym_id(+)
   and i.ingred_id = rl.ingred_id(+)
   and i.ingred_id = nc.ingred_id(+)
   and me.mono_id = x.mono_id(+)
   and me.ingred_id = x.ingred_id(+)
union all
-- non-compendial inngredients
select i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
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
       rl.rolespec_class_name ingred_role_code,
       rl.rolespec_name_eng ingred_role_name_eng, rl.rolespec_name_fr ingred_role_name_fr,
       null, null, null,
       null mono_id, cg.homeogentext_id common_name_id, pg.homeogentext_id proper_name_id,
       nc.ingred_name_type_id, nc.ingred_type_id, rl.ingredrole_id ingred_role_id, null
  from INGREDIENTS i,
       HOMEOPATHIC_COMMON_NAME_HGTS cn,
       HOMEOPATHIC_GENERIC_TEXTS cg,
       HOMEOPATHIC_PROPER_NAME_HGTS pn,
       HOMEOPATHIC_GENERIC_TEXTS pg,
       v_ingredient_type nc,
       v_ingredient_role rl
where i.ingred_id = cn.ingred_id(+)
   and cn.homeogentext_id = cg.homeogentext_id(+)
   and i.ingred_id = pn.ingred_id(+)
   and pn.homeogentext_id = pg.homeogentext_id(+)
   and i.ingred_id = nc.ingred_id
   and i.ingred_id = rl.ingred_id(+)
   and nc.ingred_type_code = 'HomeopathicSubstance'
   and (cg.homeogentext_name_eng is not null or pg.homeogentext_name_eng is not null)
;

spool off

