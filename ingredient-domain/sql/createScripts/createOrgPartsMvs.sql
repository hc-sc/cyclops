
------------------------------------------------------------------------------
-- Materialized view containing flattened organism part types and synonyms 
-- Build Time - about 10 seconds.
------------------------------------------------------------------------------
-- DROP MATERIALIZED VIEW orgpart_parttype_mv;
CREATE MATERIALIZED VIEW orgpart_parttype_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select 
  op.orgpart_id as organismPartId,
  opt.orgparttype_code as partTypeCode,
  opt.orgparttype_name_eng as partTypeNameEnglish,
  opt.orgparttype_name_fr as partTypeNameFrench,
  getOrgPartTypeSynonyms('ENG', '~', opt.orgparttype_id) as partTypeSynonymsEnglish,
  getOrgPartTypeSynonyms('FR', '~', opt.orgparttype_id) as partTypeSynonymsFrench
from
  organism_parts op, 
  organism_part_types opt
where
  op.orgparttype_id = opt.orgparttype_id;

CREATE INDEX orgpart_parttype_mv_IDX1 
    ON orgpart_parttype_mv (organismPartId);
commit;  


----------------------------------------------------------------------------------
-- Get CONSTITUENTS
-- Takes about 60 seconds
----------------------------------------------------------------------------------
-- DROP MATERIALIZED VIEW orgpart_constituents_mv;
CREATE MATERIALIZED VIEW orgpart_constituents_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select 
  op.orgpart_id as organismPartId,
  getConstituents('ENG', '~', op.orgpart_id) as constituentsEnglish,
  getConstituents('FR', '~', op.orgpart_id) as constituentsFrench
from
  organism_parts op;

CREATE INDEX orgpart_constituents_mv_IDX1 
    ON orgpart_constituents_mv (organismPartId);

commit; 
-- TEST
-- select getOrganismSynonyms('ENG', '~', 1) from dual;
-- Takes about 60 seconds
-- Took about 10 seconds.
-- select max(length(constituentsEnglish)) from orgpart_constituents_mv;
-- Max 3392
-- select max(length(constituentsFrench)) from orgpart_constituents_mv;
-- Max 3562

    
----------------------------------------------------------------------------------
-- Get CONSTITUENTS
-- Takes about 60 seconds
----------------------------------------------------------------------------------    
-- DROP MATERIALIZED VIEW orgpart_organism_mv;
CREATE MATERIALIZED VIEW orgpart_organism_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select 
  op.orgpart_id as organismPartId,
  o.organism_name as organism,
  getOrganismSynonyms('ENG', '~', o.organism_id) as organismSynonymsEnglish,
  getOrganismSynonyms('FR', '~', o.organism_id) as organismSynonymsFrench
from
  organism_parts op, 
  organisms o
where
  op.organism_id = o.organism_id;

CREATE INDEX orgpart_organism_mv_IDX1 
    ON orgpart_organism_mv (organismPartId);
commit;  

----------------------------------------------------------------------------------
-- Go from the Ingredient ID to its source organism parts.
-- Takes about 60 seconds
----------------------------------------------------------------------------------  
-- DROP MATERIALIZED VIEW ingredient_orgpart_mv;
CREATE MATERIALIZED VIEW ingredient_orgpart_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select unique ingredientId, organismPartId from (
select
  si.ingred_id as ingredientId,
  ops.orgpart_id as organismPartId
from
  subingredients si,
  organism_part_subingredients ops
where
  si.subingred_id = ops.subingred_id
UNION  
select
  i.ingred_id as ingredientId,
  op.orgpart_id as organismPartId
from
  ingredients i,
  in_org_grp_organism iogo,
  organism_parts op
where
  i.org_grp_id = iogo.org_grp_id and
  iogo.organism_id = op.organism_id);
  
CREATE INDEX ingredient_orgpart_mv_IDX1 
    ON ingredient_orgpart_mv (ingredientId);

CREATE INDEX ingredient_orgpart_mv_IDX2 
    ON ingredient_orgpart_mv (organismPartId);
commit;  

----------------------------------------------------------------------------------
-- Create Organism Group MV.  This is used to provide group name information
-- to resolve the issue 2077
----------------------------------------------------------------------------------  
-- Create the combining MV.
-- DROP MATERIALIZED VIEW orgpart_groupname_mv;
CREATE MATERIALIZED VIEW orgpart_groupname_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select
  og.org_grp_id as org_grp_id,
  og.org_grp_name_eng as org_grp_name_eng,
  og.org_grp_name_fr as org_grp_name_fr,
  op.orgpart_id as organismPartId
from
  organism_groups og,
  organism_parts op
where
  op.org_grp_id = og.org_grp_id;

CREATE INDEX orgpart_groupname_mv_IDX1 
    ON orgpart_groupname_mv (organismPartId);
commit;

----------------------------------------------------------------------------------
-- Create Source Organism Parts MV.  Uses content from the 4 other MVs. 
-- Takes about 60 seconds
----------------------------------------------------------------------------------  
-- Create the combining MV.
-- DROP MATERIALIZED VIEW SourceOrganismParts_mv;
CREATE MATERIALIZED VIEW SourceOrganismParts_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select
  io_mv.ingredientId as ingred_id,
  io_mv.organismPartId as orgpart_Id,
  oc_mv.constituentsEnglish as constituents_eng,
  oc_mv.constituentsFrench as constituents_fr,
  oo_mv.organism as organism_name,
  og_mv.org_grp_name_eng as organism_group_name_eng,
  og_mv.org_grp_name_fr as organism_group_name_fr,
  oo_mv.organismSynonymsEnglish as organism_synonyms_eng,
  oo_mv.organismSynonymsFrench as organism_synonyms_fr,
  op_mv.partTypeCode as orgparttype_code,
  op_mv.partTypeNameEnglish as parttype_name_eng,
  op_mv.partTypeNameFrench as parttype_name_fr,
  op_mv.partTypeSynonymsEnglish as parttype_synonyms_eng,
  op_mv.partTypeSynonymsFrench as parttype_synonyms_fr
from
  ingredient_orgpart_mv io_mv,
  orgpart_organism_mv oo_mv,
  orgpart_constituents_mv oc_mv,
  orgpart_parttype_mv op_mv,
  orgpart_groupname_mv og_mv
where
  io_mv.organismPartId = oo_mv.organismPartId (+) and
  io_mv.organismPartId = oc_mv.organismPartId (+) and
  io_mv.organismPartId = op_mv.organismPartId (+) and
  io_mv.organismPartId = og_mv.organismPartId (+);

CREATE INDEX SourceOrganismParts_mv_IDX1 
    ON SourceOrganismParts_mv (ingred_id);
CREATE INDEX SourceOrganismParts_mv_IDX2 
    ON SourceOrganismParts_mv (orgpart_id);
commit;    

----------------------------------------------------------------------------------
-- Create RelatedSourceIngredients MV.  These are the parents of the 
-- subingredients which are associated with the ingredient. 
----------------------------------------------------------------------------------  
-- DROP MATERIALIZED VIEW RelatedSourceIngredients_mv;
CREATE MATERIALIZED VIEW RelatedSourceIngredients_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
 SELECT
   subs.ingred_id as ingred_id,
   ing.ingred_id as related_ingred_id,
   ingred_authorized_name_eng as related_ingred_authname_eng,
   ingred_authorized_name_fr as related_ingred_authname_fr,
   getSynonyms('ENG', '<br/>', ing.ingred_id) as english_synonyms,
   getSynonyms('FR', '<br/>', ing.ingred_id) as french_synonyms
 FROM
   ingredients ing,
   ingredient_subingredients ingsub,
   subingredients subs 
 WHERE
   ing.ingred_id = ingsub.ingred_id AND 
   ingsub.subingred_id = subs.subingred_id AND 
   ing.INGREDSPEC_CLASS_NAME = 'ChemicalSubstance'
 ORDER BY subs.ingred_id;


CREATE INDEX RelatedSourceIngreds_mv_IDX1 
    ON RelatedSourceIngredients_mv (ingred_id);
commit;

