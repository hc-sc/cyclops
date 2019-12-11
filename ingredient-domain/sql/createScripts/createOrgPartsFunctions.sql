------------------------------------------------------------------------------
-- Retrieves concatenated lists of synonyms for Organism Part Types. 
------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION getOrgPartTypeSynonyms(
  lang           VARCHAR2,
  concatenator   VARCHAR2,
  orgparttype_id INTEGER
) RETURN VARCHAR2
AS
  TYPE synonym_cursor_type IS REF CURSOR;
  synonym_cursor synonym_cursor_type;
  TYPE synonym_type IS RECORD (
    synonym_eng VARCHAR2(1000),
    synonym_fr VARCHAR2(1000)
  );
  synonym_rec   synonym_type;
  synonym_list  VARCHAR2(8000);
  synonym       VARCHAR2(1000);
  sql_query VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  synonym_list := '';

  -- Build query which gets all common names for the ingredient
  sql_query := sql_query || 'SELECT';
  sql_query := sql_query || '  syn.synonym_name_eng, ';
  sql_query := sql_query || '  syn.synonym_name_fr ';
  sql_query := sql_query || 'FROM';
  sql_query := sql_query || '  orgparttype_synonyms os';
  sql_query := sql_query || '  INNER JOIN synonyms syn ON syn.synonym_id = os.synonym_id ';
  sql_query := sql_query || 'WHERE';
  sql_query := sql_query || '  os.orgparttype_id = ' || orgparttype_id;
  
  -- Walk through the result set.
  OPEN synonym_cursor FOR sql_query;
  LOOP
    FETCH synonym_cursor INTO synonym_rec;
    IF synonym_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        synonym := synonym_rec.synonym_eng;
      ELSE
        synonym := synonym_rec.synonym_fr;
      END IF;

      IF (synonym IS NOT NULL AND synonym <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(synonym_list) IS NOT NULL THEN
          synonym_list := synonym_list || concatenator;
        END IF;
        -- concatenate the Synonym
        synonym_list := synonym_list || synonym;
      END IF;
    END IF;

  END LOOP;

  CLOSE synonym_cursor;
  RETURN synonym_list;
END getOrgPartTypeSynonyms;
/

------------------------------------------------------------------------------
-- Retrieves concatenated lists of synonyms for Organisms. 
------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION getOrganismSynonyms(
  lang           VARCHAR2,
  concatenator   VARCHAR2,
  organism_id    INTEGER
) RETURN VARCHAR2
AS
  TYPE synonym_cursor_type IS REF CURSOR;
  synonym_cursor synonym_cursor_type;
  TYPE synonym_type IS RECORD (
    synonym_eng VARCHAR2(1000),
    synonym_fr VARCHAR2(1000)
  );
  synonym_rec   synonym_type;
  synonym_list  VARCHAR2(8000);
  synonym       VARCHAR2(1000);
  sql_query VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  synonym_list := '';

  -- Build query which gets all common names for the ingredient
  sql_query := sql_query || 'SELECT';
  sql_query := sql_query || '  syn.synonym_name_eng, ';
  sql_query := sql_query || '  syn.synonym_name_fr ';
  sql_query := sql_query || 'FROM';
  sql_query := sql_query || '  organism_qualifiedsynonyms oq';
  sql_query := sql_query || '  INNER JOIN qualified_synonyms qs ON oq.qualsyno_id = qs.qualsyno_id ';
  sql_query := sql_query || '  INNER JOIN synonyms syn ON syn.synonym_id = qs.synonym_id ';
  sql_query := sql_query || 'WHERE';
  sql_query := sql_query || '  oq.organism_id = ' || organism_id;
  
  -- Walk through the result set.
  OPEN synonym_cursor FOR sql_query;
  LOOP
    FETCH synonym_cursor INTO synonym_rec;
    IF synonym_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        synonym := synonym_rec.synonym_eng;
      ELSE
        synonym := synonym_rec.synonym_fr;
      END IF;

      IF (synonym IS NOT NULL AND synonym <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(synonym_list) IS NOT NULL THEN
          synonym_list := synonym_list || concatenator;
        END IF;
        -- concatenate the Synonym
        synonym_list := synonym_list || synonym;
      END IF;
    END IF;

  END LOOP;

  CLOSE synonym_cursor;
  RETURN synonym_list;
END getOrganismSynonyms;
/



------------------------------------------------------------------------------
-- Retrieves concatenated list of subingredients names for an org part type 
------------------------------------------------------------------------------
-- Build Time - about 5 seconds.
-- DROP MATERIALIZED VIEW orgpart_constituent_mv;
CREATE MATERIALIZED VIEW orgpart_constituent_mv
NOLOGGING PARALLEL 
BUILD IMMEDIATE AS
select distinct 
  op.orgpart_id as organismPartId,
  '<id>' || ism.ingred_id            || '</id>' ||
  '<tp>' || isp.ingredspec_name_eng  || '</tp>' ||
  '<ap>' || ism.english_name         || '</ap>' as ingredientNamesEnglish,
  '<id>' || ism.ingred_id            || '</id>' ||
  '<tp>' || isp.ingredspec_name_fr   || '</tp>' ||
  '<ap>' || ism.french_name          || '</ap>' as ingredientNamesFrench
from
  organism_parts op,
  organism_part_subingredients ops,
  subingredients s,
  ingredient_synonyms_mv ism,
  ingredients i,
  ingredient_specials isp
where
  op.orgpart_id = ops.orgpart_id and
  ops.subingred_id = s.subingred_id and
  s.ingred_id = ism.ingred_id and
  ism.ingred_id = i.ingred_id and
  i.ingredspec_class_name = isp.ingredspec_class_name;

CREATE INDEX orgpart_constituent_mv_IDX1 
    ON orgpart_constituent_mv (organismPartId);

-- Test 
-- select ingredientNamesEnglish from orgpart_constituent_mv where organismPartId=2644;
-- select max(length(ingredientNamesEnglish)) from orgpart_constituent_mv;
-- max English = 722.  Ingreasing function limit to 2k;
-- select max(length(ingredientNamesFrench)) from orgpart_constituent_mv;
-- max English = 749.  Ingreasing function limit to 2k;
-- select count(*) from orgpart_constituent_mv where organismPartId=382;
-- Should return 3.

------------------------------------------------------------------------------
-- Retrieves concatenated list of subingredients names for an org part type 
-- Uses the orgpart_constituent_mv defined above.
------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION getConstituents(
  lang           VARCHAR2,
  concatenator   VARCHAR2,
  orgparttype_id INTEGER
) RETURN VARCHAR2
AS
  TYPE constituent_cursor_type IS REF CURSOR;
  constituent_cursor constituent_cursor_type;
  TYPE constituent_type IS RECORD (
    constituent_eng VARCHAR2(8000),
    constituent_fr VARCHAR2(8000)
  );
  constituent_rec   constituent_type;
  constituent_list  VARCHAR2(32767);
  constituent       VARCHAR2(2000);
  sql_query VARCHAR2(8000);
BEGIN
  -- Define an empty constituent list.
  constituent_list := '';

  -- Build query which gets all common names for the ingredient
  sql_query := sql_query || 'SELECT';
  sql_query := sql_query || '  oc_mv.ingredientNamesEnglish, ';
  sql_query := sql_query || '  oc_mv.ingredientNamesFrench ';
  sql_query := sql_query || 'FROM';
  sql_query := sql_query || '  orgpart_constituent_mv oc_mv ';
  sql_query := sql_query || 'WHERE';
  sql_query := sql_query || '  oc_mv.organismPartId = ' || orgparttype_id;
  
  
  -- Walk through the result set.
  OPEN constituent_cursor FOR sql_query;
  LOOP
    FETCH constituent_cursor INTO constituent_rec;
    IF constituent_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        constituent := constituent_rec.constituent_eng;
      ELSE
        constituent := constituent_rec.constituent_fr;
      END IF;

      IF (constituent IS NOT NULL AND constituent <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(constituent_list) IS NOT NULL THEN
          constituent_list := constituent_list || concatenator;
        END IF;
        -- concatenate the constituent
        constituent_list := constituent_list || constituent;
      END IF;
    END IF;

  END LOOP;

  CLOSE constituent_cursor;
  RETURN constituent_list;
END getConstituents;
/

-- select getConstituents('ENG', '~', 840) from dual

------------------------------------------------------------------------------
-- Retrieves related source ingredients for an ingredient. 
-- Used by MV: [todo]  
------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION getRelatedSourceIngredients (
  lang           VARCHAR2,
  concatenator   VARCHAR2,
  ingredient_id INTEGER
) RETURN VARCHAR2
AS
  TYPE sourceingred_cursor_type IS REF CURSOR;
  sourceingred_cursor sourceingred_cursor_type;
  TYPE sourceingred_type IS RECORD (
    sourceingred_eng VARCHAR2(1000),
    sourceingred_fr  VARCHAR2(1000)
  );
  sourceingred_rec   sourceingred_type;
  sourceingred_list  VARCHAR2(8000);
  sourceingred       VARCHAR2(1000);
  sql_query 		 VARCHAR2(8000);
BEGIN
  -- Define an empty source ingredients list.
  sourceingred_list := '';
  
  -- Retrieve related source ingredients for the specified ingredient.
  -- Navigates from Ingredient -> SubIngredient -> Parent Ingredient
  sql_query := sql_query || 'select';
  sql_query := sql_query || ' ingred_authorized_name_eng, ';
  sql_query := sql_query || ' ingred_authorized_name_fr ';      
  sql_query := sql_query || 'from';
  sql_query := sql_query || '  ingredients ing, ';
  sql_query := sql_query || '  ingredient_subingredients ingsub, ';
  sql_query := sql_query || '  subingredients subs ';
  sql_query := sql_query || 'where';
  sql_query := sql_query || '  ing.ingred_id = ingsub.ingred_id and ';
  sql_query := sql_query || '  ingsub.subingred_id = subs.subingred_id and ';
  sql_query := sql_query || '  subs.ingred_id = ' || ingredient_id; 
  IF LANG='ENG' THEN
    sql_query := sql_query || '  order by ingred_authorized_name_eng '; 
  ELSE
    sql_query := sql_query || '  order by ingred_authorized_name_fr ';
  END IF;    

  -- Walk through the result set.
  OPEN sourceingred_cursor FOR sql_query;
  LOOP
    FETCH sourceingred_cursor INTO sourceingred_rec;
    IF sourceingred_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        sourceingred := sourceingred_rec.sourceingred_eng;
      ELSE
        sourceingred := sourceingred_rec.sourceingred_fr;
      END IF;

      IF (sourceingred IS NOT NULL) THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(sourceingred_list) IS NOT NULL THEN
          sourceingred_list := sourceingred_list || concatenator;
        END IF;
        -- concatenate the source ingredient name
        --DBMS_OUTPUT.PUT_LINE(sourceingred);
        sourceingred_list := sourceingred_list || sourceingred;
      END IF;
    END IF;

  END LOOP;

  CLOSE sourceingred_cursor;
  RETURN sourceingred_list;
END getRelatedSourceIngredients;
/


