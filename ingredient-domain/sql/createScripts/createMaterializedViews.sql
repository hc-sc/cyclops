-- ****************************************************************************
-- Create Materialized Views.  Please maintain alphabetic order
-- ****************************************************************************

drop materialized view ingredient_name_search_mv;
drop materialized view ingredient_name_search_ws_mv;
drop materialized view INGREDIENT_SYNONYMS_MV;
drop materialized view MONOGRAPH_SYNONYMS_MV;
drop materialized view subingredient_mv;
drop materialized view subtaxa_mv;
drop materialized view species_mv;
drop materialized view qualifiedsynonym_references_mv;
drop materialized view references_mv;

---------------------------------------------------------------------
-- ingredient_name_search_mv
---------------------------------------------------------------------
CREATE MATERIALIZED VIEW ingredient_name_search_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
SELECT
  i.ingred_id as ingred_id, 
  i.ingred_authorized_name_eng as english_name,
  i.ingred_authorized_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id
UNION
SELECT
  i.ingred_id as ingred_id,
  syn.synonym_name_eng as english_name,
  syn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM 
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,     
  synonyms syn,
  qualified_synonyms qsyn,
  ingredient_qualsyno_cns cns
WHERE
  i.ingred_id=cns.ingred_id and
  cns.qualsyno_id=qsyn.qualsyno_id and
  syn.synonym_id=qsyn.synonym_id
UNION
SELECT
  i.ingred_id as ingred_id,
  syn.synonym_name_eng as english_name,
  syn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM 
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,     
  synonyms syn,
  qualified_synonyms qsyn,
  ingredient_qualsyno_pns pns
WHERE
  i.ingred_id=pns.ingred_id and
  pns.qualsyno_id=qsyn.qualsyno_id and
  syn.synonym_id=qsyn.synonym_id
UNION
SELECT
  i.ingred_id as ingred_id,
  syn.synonym_name_eng as english_name,
  syn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM 
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,     
  synonyms syn,
  qualified_synonyms qsyn,
  ingredient_qualsyno_qss qss
WHERE
  i.ingred_id=qss.ingred_id and
  qss.qualsyno_id=qsyn.qualsyno_id and
  syn.synonym_id=qsyn.synonym_id
UNION
SELECT 
  i.ingred_id as ingred_id, 
  re.regentry_code as english_name,
  re.regentry_code as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  ingredient_reg_entries i_re,
  registry_entries re
WHERE
  i.ingred_id = i_re.ingred_id and    
  i_re.regentry_id = re.regentry_id
UNION
SELECT
  i.ingred_id as ingred_id,
  re.regentry_code as english_name,
  re.regentry_code as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  registry_entries re
WHERE
  i.regentry_id = re.regentry_id
UNION
SELECT
  i.ingred_id as ingred_id,
  org_grp.org_grp_name_eng as english_name,
  org_grp.org_grp_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  organism_groups org_grp
WHERE
  i.org_grp_id = org_grp.org_grp_id
UNION
SELECT
  i.ingred_id as ingred_id,
  org.organism_name as english_name,
  org.organism_name as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  in_org_grp_organism org_grps,
  organisms org
WHERE
  i.org_grp_id = org_grps.org_grp_id and
  org_grps.organism_id = org.organism_id
UNION
SELECT
  i.ingred_id as ingred_id,
  syn.synonym_name_eng as english_name,
  syn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  qualified_synonyms qsyn,
  synonyms syn, 
  ingredients ing, 
  in_org_grp_organism iogo, 
  organisms org, 
  organism_qualifiedsynonyms orgqsyn 
where 
  i.org_grp_id = iogo.org_grp_id and
  ing.org_grp_id = iogo.org_grp_id and 
  iogo.organism_id = org.organism_id and 
  -- ing.ingred_authorized_name_eng = org.organism_name and 
  orgqsyn.organism_id = org.organism_id and 
  orgqsyn.qualsyno_id = qsyn.qualsyno_id and
  qsyn.synonym_id = syn.synonym_id
UNION
SELECT
  i.ingred_id as ingred_id,
  tFrmGrp.taxon_name as english_name,
  tFrmGrp.taxon_name as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  in_org_grp_organism org_grps,
  taxa tFrmGrp
WHERE
  i.org_grp_id = org_grps.org_grp_id and
  org_grps.organism_id = tFrmGrp.organism_id
UNION
SELECT 
  i.ingred_id as ingred_id,
  org.organism_name as english_name,
  org.organism_name as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  organisms org
WHERE
  i.organism_id = org.organism_id
UNION
SELECT 
  i.ingred_id as ingred_id,
  orgSyn.synonym_name_eng as english_name,
  orgSyn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  organism_synonyms o_syn,
  synonyms orgSyn
WHERE
  i.organism_id = o_syn.organism_id and
  o_syn.synonym_id = orgSyn.synonym_id
UNION  
SELECT 
  i.ingred_id as ingred_id,
  hgt.homeogentext_name_eng as english_name,
  hgt.homeogentext_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  homeopathic_proper_name_hgts hpn,
  homeopathic_generic_texts hgt
WHERE
  i.ingred_id = hpn.ingred_id AND 
  hpn.homeogentext_id = hgt.homeogentext_id
UNION
SELECT 
  i.ingred_id as ingred_id,
  hgt.homeogentext_name_eng as english_name,
  hgt.homeogentext_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  homeopathic_common_name_hgts hcn,
  homeopathic_generic_texts hgt
WHERE
  i.ingred_id = hcn.ingred_id AND 
  hcn.homeogentext_id = hgt.homeogentext_id;

CREATE INDEX  ingredient_name_search_IDX1 
    ON  ingredient_name_search_mv (ingred_id);
CREATE INDEX  ingredient_name_search_IDX2 
    ON  ingredient_name_search_mv (english_name);
CREATE INDEX  ingredient_name_search_IDX3 
    ON  ingredient_name_search_mv (french_name);
CREATE INDEX  ingredient_name_search_IDX4 
    ON  ingredient_name_search_mv (role_name);

---------------------------------------------------------------------
-- ingredient_name_search_ws_mv 
---------------------------------------------------------------------
CREATE MATERIALIZED VIEW ingredient_name_search_ws_mv
BUILD IMMEDIATE AS
SELECT
  m.ingred_id as ingred_id,
  m.english_name as english_name,
  m.french_name as french_name,
  m.role_name as role_name,
  c.ingredcat_code as category_code
FROM
  ingredient_name_search_mv m,
  ingredients i,
  ingredient_categories c
where
  m.ingred_id = i.ingred_id and
  i.ingredcat_id = c.ingredcat_id;

CREATE INDEX  ingredient_name_search_ws_IDX1 
    ON  ingredient_name_search_ws_mv (ingred_id);
CREATE INDEX  ingredient_name_search_ws_IDX2 
    ON  ingredient_name_search_ws_mv (english_name);
CREATE INDEX  ingredient_name_search_ws_IDX3 
    ON  ingredient_name_search_ws_mv (french_name);
CREATE INDEX  ingredient_name_search_ws_IDX4 
    ON  ingredient_name_search_ws_mv (role_name);
CREATE INDEX  ingredient_name_search_ws_IDX5 
    ON  ingredient_name_search_ws_mv (category_code);



---------------------------------------------------------------------
-- INGREDIENT_SYNONYMS_MV - used by both Ingredients Web App and
-- Ingredients Web Service
---------------------------------------------------------------------
CREATE MATERIALIZED VIEW INGREDIENT_SYNONYMS_MV
  BUILD IMMEDIATE
  USING INDEX 
  REFRESH FORCE ON DEMAND
  WITH ROWID USING DEFAULT LOCAL ROLLBACK SEGMENT
  DISABLE QUERY REWRITE
  AS 
SELECT
  i.ingred_id as ingred_id,
  i.ingred_authorized_name_eng as english_name,
  i.ingred_authorized_name_fr as french_name,
  i.ingredspec_class_name as class_name,
  r.rolespec_class_name as role_name,
  c.ingredcat_code as category_code,
  getSearchTerms('ENG', '~', i.ingred_id) as english_searchterm,
  getSearchTerms('FR', '~', i.ingred_id) as french_searchterm,
  getSynonyms('ENG', '<br/>', i.ingred_id) as english_synonyms,
  getSynonyms('FR', '<br/>', i.ingred_id) as french_synonyms,
  getProperNames('ENG', '<br/>', i.ingred_id) as english_proper_names,
  getProperNames('FR', '<br/>', i.ingred_id) as french_proper_names,
  getCommonNames('ENG', '<br/>', i.ingred_id) as english_common_names,
  getCommonNames('FR', '<br/>', i.ingred_id) as french_common_names,
  getPreClearedUrls('ENG', '~','<br/>', i.ingred_id) as english_precleared_urls,
  getPreClearedUrls('FR', '~','<br/>', i.ingred_id) as french_precleared_urls
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id
  LEFT JOIN ingredient_categories c ON i.ingredcat_id = c.ingredcat_id;

CREATE INDEX  ingredient_synonyms_mv_IDX1 
    ON  ingredient_synonyms_mv (ingred_id);
CREATE INDEX  ingredient_synonyms_mv_IDX2 
    ON  ingredient_synonyms_mv (english_name);
CREATE INDEX  ingredient_synonyms_mv_IDX3 
    ON  ingredient_synonyms_mv (french_name);
CREATE INDEX  ingredient_synonyms_mv_IDX4 
    ON  ingredient_synonyms_mv (class_name);
CREATE INDEX  ingredient_synonyms_mv_IDX5 
    ON  ingredient_synonyms_mv (role_name);
CREATE INDEX  ingredient_synonyms_mv_IDX6 
    ON  ingredient_synonyms_mv (category_code);

-- Not currently used with the contains function.  Need access to update the indexes.
CREATE INDEX  ingredient_snnms_mv_IDX7 
    ON ingredient_synonyms_mv(english_searchterm) INDEXTYPE IS CTXSYS.CONTEXT;

-- Not currently used with the contains function.  Need access to update the indexes.
CREATE INDEX  ingredient_snnms_mv_IDX8 
    ON ingredient_synonyms_mv(french_searchterm) INDEXTYPE IS CTXSYS.CONTEXT;


---------------------------------------------------------------------
-- MONOGRAPH_SYNONYMS_MV - used by Monograph Web Service
-- Monograph Refactoring: Monographs table now only contains
-- "Generated" monographs - where clause removed. 
---------------------------------------------------------------------
CREATE MATERIALIZED VIEW MONOGRAPH_SYNONYMS_MV
   BUILD IMMEDIATE
   USING INDEX 
   REFRESH FORCE ON DEMAND
   WITH ROWID USING DEFAULT LOCAL ROLLBACK SEGMENT
   DISABLE QUERY REWRITE
   AS 
SELECT DISTINCT
   m.mono_id as mono_id,
   m.mono_name_eng as mono_name_eng,
   m.mono_name_fr as mono_name_fr,
   m.mono_publish_date as mono_publish_date,
   m.mono_status as mono_status,
   m.mono_type as mono_type,
   m.monospec_class_name as monospec_class_name,
   me.ingred_id,
   getSearchTerms('ENG', '~', me.ingred_id) as english_searchterm,
   getSearchTerms('FR', '~', me.ingred_id) as french_searchterm,
   getSynonyms('ENG', '<br/>', me.ingred_id) as english_synonyms,
   getSynonyms('FR', '<br/>', me.ingred_id) as french_synonyms
 FROM
   monographs m
   INNER JOIN monograph_entries me ON m.mono_id = me.mono_id;
--   WHERE  (EXISTS (select mono_id from monograph_roa_shrd_monographs smr where smr.mono_id = m.mono_id)
--       OR  EXISTS (select mono_id from monograph_roa_dist_monographs smr where smr.mono_id = m.mono_id))
--   AND me.MONOENT_PRIMARY_INGREDIENT = 'Y';

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX1 
    ON  MONOGRAPH_SYNONYMS_MV (mono_id);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX2 
    ON  MONOGRAPH_SYNONYMS_MV (mono_name_eng);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX3 
    ON  MONOGRAPH_SYNONYMS_MV (mono_name_fr);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX4 
    ON  MONOGRAPH_SYNONYMS_MV (ingred_id);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX5 
    ON  MONOGRAPH_SYNONYMS_MV (english_searchterm);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX6 
    ON  MONOGRAPH_SYNONYMS_MV (french_searchterm);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX7 
    ON  MONOGRAPH_SYNONYMS_MV (english_synonyms);

CREATE INDEX  MONOGRAPH_SYNONYMS_IDX8 
    ON  MONOGRAPH_SYNONYMS_MV (french_synonyms);


---------------------------------------------------------------------
--                       subingredient_mv                          --
---------------------------------------------------------------------
-- Used by the Ingredient Editor to accellerate access to          --
-- subingredients.                                                 --
---------------------------------------------------------------------
CREATE MATERIALIZED VIEW subingredient_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select 
  si.subingred_id as id,
  i.ingred_authorized_name_eng || ' [' ||
    decode(pi.ingred_authorized_name_eng, null, 
      opt.orgparttype_name_eng || ' - (' || o.organism_name || ')',
      pi.ingred_authorized_name_eng) || ']' as shortDescriptionEng,
  i.ingred_authorized_name_fr || ' [' ||
    decode(pi.ingred_authorized_name_fr, null, 
      opt.orgparttype_name_fr || ' - (' || o.organism_name || ')',
      pi.ingred_authorized_name_fr) || ']' as shortDescriptionFr
from
  ingredients i,
  subingredients si,
  ingredients pi,
  ingredient_subingredients isu,
  organism_part_subingredients ops,
  organism_parts op,
  organism_groups og,
  organisms o,
  organism_part_types opt
where
  i.ingred_id=si.ingred_id and
  si.subingred_id=isu.subingred_id (+) and
  isu.ingred_id=pi.ingred_id (+) and
  si.subingred_id=ops.subingred_id (+) and
  ops.orgpart_id=op.orgpart_id (+) and
  op.organism_id=o.organism_id (+) and
  op.org_grp_id=og.org_grp_id (+) and
  op.orgparttype_id=opt.orgparttype_id (+);
    
------------------------------------------------------------
--                   subingredient_mv                     --
------------------------------------------------------------
-- Used by the Ingredient Editor for speed.               --
------------------------------------------------------------
CREATE MATERIALIZED VIEW qualifiedsynonym_references_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select 
  qs.qualsyno_id as id,
  s.synonym_name_eng || ' [' || GetQualifiedSynonymReferences(', ', qs.qualsyno_id) || ']' as shortDescriptionEng,
  s.synonym_name_fr || ' [' || GetQualifiedSynonymReferences(', ', qs.qualsyno_id) || ']' as shortDescriptionFr
from
  qualified_synonyms qs,
  synonyms s
where
  qs.synonym_id=s.synonym_id;
-- Note - this took about 1 minute - 2 minutes to do.

------------------------------------------------------------
--                       species_mv                       --
------------------------------------------------------------
-- Used by the Ingredient Editor for speed.               --
------------------------------------------------------------
CREATE MATERIALIZED VIEW species_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select
  sp.species_id as id,
  fa.family_name || ' ' || ge.genus_name || ' ' || sp.species_name as shortDescriptionEng,
  fa.family_name || ' ' || ge.genus_name || ' ' || sp.species_name as shortDescriptionFr
from
  species sp,
  genera ge,
  families fa
where
  sp.genus_id = ge.genus_id and
  ge.family_id = fa.family_id
order by
  fa.family_name, ge.genus_name, sp.species_name;

------------------------------------------------------------
--                       subtaxa_mv                       --
------------------------------------------------------------
-- Used by the Ingredient Editor for speed.               --
------------------------------------------------------------
CREATE MATERIALIZED VIEW subtaxa_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select
  sp.species_id as id,
  fa.family_name || ' ' || ge.genus_name || ' ' || sp.species_name || ' ' || st.subtaxa_name as shortDescriptionEng,
  fa.family_name || ' ' || ge.genus_name || ' ' || sp.species_name || ' ' || st.subtaxa_name as shortDescriptionFr
from
  subtaxa st,
  species sp,
  genera ge,
  families fa
where
  st.species_id = sp.species_id and
  sp.genus_id = ge.genus_id and
  ge.family_id = fa.family_id
order by
  fa.family_name, ge.genus_name, sp.species_name, st.subtaxa_name;

------------------------------------------------------------
--                     references_mv                       --
------------------------------------------------------------
-- Used by the Ingredient Editor for speed.               --
------------------------------------------------------------
CREATE MATERIALIZED VIEW references_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select
  id, shortDescriptionEng, shortDescriptionFr
from(
select
  ref.ref_id as id,
  decode(ref.ref_code, null, '', '[' || ref.ref_code || '] ') || ref_name_eng as shortDescriptionEng,
  decode(ref.ref_code, null, '', '[' || ref.ref_code || '] ') || ref_name_eng as shortDescriptionFr
from
  refs ref);    

--------------------------------------------------------------------------------
-- This Materialized view creates an optimized way to find organisms for any
-- given ingredient.  It uses the organism group to find out what ingredients
-- belong to the given organism.
--------------------------------------------------------------------------------
-- Used by the NHPSAS Tracker
--------------------------------------------------------------------------------
CREATE MATERIALIZED VIEW ingredient_organism_mv
BUILD IMMEDIATE AS
SELECT 
  ing.ingred_id,
  org.organism_id
FROM
  ingredients ing,
  in_org_grp_organism iogo,
  organisms org
WHERE
  ing.org_grp_id = iogo.org_grp_id and
  iogo.organism_id = org.organism_id;

CREATE INDEX ingredient_organism_mv_IDX1 
    ON ingredient_organism_mv (ingred_id);
CREATE INDEX ingredient_organism_mv_IDX2 
    ON ingredient_organism_mv (organism_id);
CREATE INDEX ingredient_organism_mv_IDX3 
    ON ingredient_organism_mv (ingred_id, organism_id);

--------------------------------------------------------------------------------
-- This Materialized view creates an optimized way to find constituents 
-- for any given organism.
--------------------------------------------------------------------------------
-- Used by the NHPSAS Tracker
--------------------------------------------------------------------------------
CREATE MATERIALIZED VIEW organism_constituent_mv
BUILD IMMEDIATE AS
SELECT
  op.organism_id,
  su.ingred_id
FROM
  organism_parts op,
  organism_part_types opt,
  organism_part_subingredients ops,
  subingredients su
WHERE
  op.orgpart_id = ops.orgpart_id and
  op.orgparttype_id = opt.orgparttype_id and
  su.subingred_id = ops.subingred_id;

CREATE INDEX organism_constituent_mv_IDX1 
    ON organism_constituent_mv (organism_id);
CREATE INDEX organism_constituent_mv_IDX2 
    ON organism_constituent_mv (ingred_id);
CREATE INDEX organism_constituent_mv_IDX3 
    ON organism_constituent_mv (organism_id, ingred_id);  
  
  
  