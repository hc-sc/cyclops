------------------------------------------------------------------
-- Create Functions.  Please maintain alphabetic order.
------------------------------------------------------------------

CREATE OR REPLACE FUNCTION GetCommonNames(
  lang         VARCHAR2,
  concatenator VARCHAR2,
  ingred_id    INTEGER
) RETURN VARCHAR2
AS
  TYPE common_name_cursor_type IS REF CURSOR;
  common_name_cursor common_name_cursor_type;
  TYPE common_name_type IS RECORD (
    common_name_eng VARCHAR2(1000),
    common_name_fr VARCHAR2(1000)
  );
  common_name_rec   common_name_type;
  common_name_list  VARCHAR2(8000);
  common_name       VARCHAR2(1000);
  sql_query VARCHAR2(8000);
BEGIN
  -- Define an empty common_name list.
  common_name_list := '';

  -- Build query which gets all common names for the ingredient
  sql_query := sql_query || 'SELECT';
  sql_query := sql_query || '  syn.synonym_name_eng, ';
  sql_query := sql_query || '  syn.synonym_name_fr ';
  sql_query := sql_query || 'FROM';
  sql_query := sql_query || '  ingredient_qualsyno_cns cns';
  sql_query := sql_query || '  INNER JOIN qualified_synonyms qs ON cns.qualsyno_id = qs.qualsyno_id';
  sql_query := sql_query || '  INNER JOIN synonyms syn ON syn.synonym_id = qs.synonym_id ';
  sql_query := sql_query || 'WHERE';
  sql_query := sql_query || '  cns.ingred_id = ' || ingred_id;
  sql_query := sql_query || 'UNION ';
  sql_query := sql_query || 'select  ';
  sql_query := sql_query || '  hgt.homeogentext_name_eng, ';
  sql_query := sql_query || '  hgt.homeogentext_name_fr ';
  sql_query := sql_query || 'from  ';
  sql_query := sql_query || '  homeopathic_generic_texts hgt, ';
  sql_query := sql_query || '  homeopathic_common_name_hgts hcn ';
  sql_query := sql_query || 'where ';
  sql_query := sql_query || '  hgt.homeogentext_id=hcn.homeogentext_id and ';
  sql_query := sql_query || '  hcn.ingred_id = ' || ingred_id;
  
  -- Walk through the result set.
  OPEN common_name_cursor FOR sql_query;
  LOOP
    FETCH common_name_cursor INTO common_name_rec;
    IF common_name_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        common_name := common_name_rec.common_name_eng;
      ELSE
        common_name := common_name_rec.common_name_fr;
      END IF;

      IF (common_name IS NOT NULL AND common_name <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(common_name_list) IS NOT NULL THEN
          common_name_list := common_name_list || concatenator;
        END IF;
        -- concatenate the Synonym
        common_name_list := common_name_list || common_name;
      END IF;
    END IF;

  END LOOP;

  CLOSE common_name_cursor;
  RETURN common_name_list;
END GetcommonNames;
/

------------------------------------------------------------------
create or replace FUNCTION getMonographTypes (
  concat_item  VARCHAR2,    -- concatenator between types
  mono_id    INTEGER
) RETURN       VARCHAR2
AS
  TYPE monographtype_type IS REF CURSOR;
  monographtype_cursor monographtype_type;

  TYPE monograph_type_record IS RECORD (
    monotype_code   VARCHAR2(10)
  );
  monograph_type_rec   monograph_type_record;
  monograph_type_list  VARCHAR2(100);
  sql_query            VARCHAR2(2000);
BEGIN
  -- Define an empty common_name list.
  monograph_type_list := '';
  -- ----------------------------------------------------------------
  -- Query which gets all tye monograph types for the monograph.
  -- ----------------------------------------------------------------
  sql_query :=              'select ';
  sql_query := sql_query || '  mt.monotype_code as monotype_code ';
  sql_query := sql_query || 'from ';
  sql_query := sql_query || '  monographs m, ';
  sql_query := sql_query || '  monograph_monograph_types mmt, ';
  sql_query := sql_query || '  monograph_types mt ';
  sql_query := sql_query || 'where ';
  sql_query := sql_query || '  mmt.monotype_id=mt.monotype_id and ';
  sql_query := sql_query || '  mmt.mono_id=m.mono_id and ';
  sql_query := sql_query || '  m.mono_id=' || mono_id;
  -- Walk through the result set.
  OPEN monographtype_cursor FOR sql_query;
  LOOP
    FETCH monographtype_cursor INTO monograph_type_rec;
    IF monographtype_cursor%NOTFOUND THEN
      EXIT;
    ELSE
      IF LENGTH(monograph_type_list) IS NOT NULL THEN
          monograph_type_list := monograph_type_list || concat_item;
      END IF;
      monograph_type_list := monograph_type_list || monograph_type_rec.monotype_code;
    END IF;
  END LOOP;
  CLOSE monographtype_cursor;
  RETURN monograph_type_list;
END getMonographTypes;
/

------------------------------------------------------------------
create or replace FUNCTION GetPreClearedUrls (
  lang         VARCHAR2,
  concat_item  VARCHAR2,    --concatenator between name and url
  concat_pair  VARCHAR2,    --concatenator between name/url pairs
  ingred_id    INTEGER
) RETURN       VARCHAR2
AS
  TYPE precleared_url_cursor_type IS REF CURSOR;
  precleared_url_cursor precleared_url_cursor_type;

  TYPE precleared_url_type IS RECORD (
      result   VARCHAR2(1000)
  );
  precleared_url_rec   precleared_url_type;
  precleared_url_list  VARCHAR2(12000);
  sql_query            VARCHAR2(2000);
BEGIN
  -- Define an empty common_name list.
  precleared_url_list := '';

  -- ----------------------------------------------------------------
  -- Query which gets all Pre-Cleared Info urls for the ingredient
  -- and distinguishes between Generated and Internal ABLS Monographs.
  -- Let's marvel at the syntactic crud that is PL-SQL generating a
  -- query string containing quotes...
  -- ----------------------------------------------------------------
  sql_query :=              'select unique ';
  IF LANG='ENG' THEN
      sql_query := sql_query || '    monos.mono_name_eng || ''' || concat_item || ''' || ';
  ELSE
      sql_query := sql_query || '    monos.mono_name_fr || ''' || concat_item || ''' || ';
  END IF;
  sql_query := sql_query || '    getMonographTypes('';'', monos.mono_id) || ''' || concat_item || ''' || ';
  sql_query := sql_query || '    substr(monos.monospec_class_name, 1, 1) || ''' || concat_item || ''' || ';
  IF LANG='ENG' THEN
      sql_query := sql_query || '    monos.mono_url_eng || ''' || concat_item || ''' || ';
  ELSE
      sql_query := sql_query || '    monos.mono_url_fr || ''' || concat_item || ''' || ';
  END IF;
  sql_query := sql_query || '    monos.mono_id as result ';
  sql_query := sql_query || 'from ';
  sql_query := sql_query || '    monographs monos, ';
  sql_query := sql_query || '    monograph_entries monoents ';
  sql_query := sql_query || 'left outer join monograph_monograph_types mmts on ';
  sql_query := sql_query || '    mmts.mono_id = monoents.mono_id ';
  sql_query := sql_query || 'where ';
  sql_query := sql_query || '     monos.mono_id = monoents.mono_id AND ';
  sql_query := sql_query || 'monoents.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN precleared_url_cursor FOR sql_query;
  LOOP
    FETCH precleared_url_cursor INTO precleared_url_rec;
    IF precleared_url_cursor%NOTFOUND THEN
      EXIT;
    ELSE
      IF LENGTH(precleared_url_list) IS NOT NULL THEN
        precleared_url_list := precleared_url_list || concat_pair;
      END IF;
      precleared_url_list := precleared_url_list || precleared_url_rec.result;
    END IF;

  END LOOP;

  CLOSE precleared_url_cursor;

  RETURN precleared_url_list;

END GetPreClearedUrls;
/

------------------------------------------------------------------
CREATE OR REPLACE FUNCTION GetProperNames(
  lang         VARCHAR2,
  concatenator VARCHAR2,
  ingred_id    INTEGER
) RETURN VARCHAR2
AS
  TYPE proper_name_cursor_type IS REF CURSOR;
  proper_name_cursor proper_name_cursor_type;
  TYPE proper_name_type IS RECORD (
    proper_name_eng VARCHAR2(1000),
    proper_name_fr VARCHAR2(1000)
  );
  proper_name_rec   proper_name_type;
  proper_name_list  VARCHAR2(8000);
  proper_name       VARCHAR2(1000);
  sql_query VARCHAR2(8000);
BEGIN
  -- Define an empty proper_name list.
  proper_name_list := '';

  -- Build query which gets all proper names for the ingredient
  sql_query := sql_query || 'SELECT';
  sql_query := sql_query || '  syn.synonym_name_eng, ';
  sql_query := sql_query || '  syn.synonym_name_fr ';
  sql_query := sql_query || 'FROM';
  sql_query := sql_query || '  ingredient_qualsyno_pns pns';
  sql_query := sql_query || '  INNER JOIN qualified_synonyms qs ON pns.qualsyno_id = qs.qualsyno_id';
  sql_query := sql_query || '  INNER JOIN synonyms syn ON syn.synonym_id = qs.synonym_id ';
  sql_query := sql_query || 'WHERE';
  sql_query := sql_query || '  pns.ingred_id = ' || ingred_id;
  sql_query := sql_query || 'UNION ';
  sql_query := sql_query || 'select  ';
  sql_query := sql_query || '  hgt.homeogentext_name_eng, ';
  sql_query := sql_query || '  hgt.homeogentext_name_fr ';
  sql_query := sql_query || 'from  ';
  sql_query := sql_query || '  homeopathic_generic_texts hgt, ';
  sql_query := sql_query || '  homeopathic_proper_name_hgts hpn ';
  sql_query := sql_query || 'where ';
  sql_query := sql_query || '  hgt.homeogentext_id=hpn.homeogentext_id and ';
  sql_query := sql_query || '  hpn.ingred_id = ' || ingred_id;
  
  -- Walk through the result set.
  OPEN proper_name_cursor FOR sql_query;
  LOOP
    FETCH proper_name_cursor INTO proper_name_rec;
    IF proper_name_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE

      IF LANG='ENG' THEN
        proper_name := proper_name_rec.proper_name_eng;
      ELSE
        proper_name := proper_name_rec.proper_name_fr;
      END IF;

      IF (proper_name IS NOT NULL AND proper_name <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF LENGTH(proper_name_list) IS NOT NULL THEN
          proper_name_list := proper_name_list || concatenator;
        END IF;
        -- concatenate the Synonym
        proper_name_list := proper_name_list || proper_name;
      END IF;
    END IF;

  END LOOP;

  CLOSE proper_name_cursor;
  RETURN proper_name_list;
END GetProperNames;
/

------------------------------------------------------------------
CREATE OR REPLACE FUNCTION GetSearchTerms(
  lang         VARCHAR2,
  concatenator VARCHAR2,
  ingred_id    INTEGER
) RETURN VARCHAR2
IS
  TYPE searchterm_cursor_type IS REF CURSOR;
  searchterm_cursor searchterm_cursor_type;
  TYPE SearchTerm_Type is record (
    SearchTerm_eng VARCHAR2(1000),
    SearchTerm_fr VARCHAR2(1000)
  );
  SearchTerm_Rec    SearchTerm_Type;
  SearchTerm_List   VARCHAR2(8000);
  SearchTerm        VARCHAR2(1000);
  SQLQ VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  SearchTerm_List := '';
  
  -- Build the select statement. 
  SQLQ :=         'select ';
  SQLQ := SQLQ || '  ins.english_name, ';
  SQLQ := SQLQ || '  ins.french_name ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredient_name_search_mv ins ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  ins.ingred_id=' || ingred_id;

  -- Walk through the result set.
  OPEN SearchTerm_cursor FOR SQLQ;
  LOOP
    
    Fetch SearchTerm_cursor INTO SearchTerm_Rec;
    IF SearchTerm_cursor%NOTFOUND THEN
      EXIT;
    ELSE  
      
      IF LANG='ENG' THEN 
        SearchTerm := SearchTerm_Rec.SearchTerm_eng;
      ELSE
        SearchTerm := SearchTerm_Rec.SearchTerm_fr;
      END IF;
      
      IF (SearchTerm is not null AND SearchTerm <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF length(SearchTerm_List) is not null THEN 
          SearchTerm_List := SearchTerm_List || concatenator;
        END IF;
        -- concatenate the search term
        SearchTerm_List := SearchTerm_List || SearchTerm;      
      END IF;  
    END IF;
    
  END LOOP;
  
  CLOSE SearchTerm_cursor;
  IF concatenator = '~' THEN
    SearchTerm_List := concatenator || TRIM(both '~' from SearchTerm_List) || concatenator;
  END IF;
  RETURN SearchTerm_List;
END GetSearchTerms;
/
------------------------------------------------------------------

CREATE OR REPLACE FUNCTION GETSYNONYMS(
  lang         VARCHAR2,
  concatenator VARCHAR2,
  ingred_id    INTEGER
) RETURN VARCHAR2
IS
  TYPE synonym_cursor_type IS REF CURSOR;
  synonym_cursor synonym_cursor_type;
  TYPE Syn_Type is record (
    Synonym_name_eng VARCHAR2(1000),
    Synonym_name_fr VARCHAR2(1000)
  );
  Syn_Rec       Syn_Type;
  Synonym_List  VARCHAR2(8000);
  Synonym       VARCHAR2(1000);
  SQLQ VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  Synonym_List := '';

  -- Build the select statement. 
  -- 1. get the synonyms from the ingredient common names and proper names
  -- 2. get the synonyms from the organism when ingredient.name = organism.name

  SQLQ :=         'select ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  ingredient_qualsyno_cns cns ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  cns.qualsyno_id=qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  syn.synonym_id=qsyn.synonym_id and ';
  SQLQ := SQLQ || '  cns.ingred_id = ' || ingred_id;
  SQLQ := SQLQ || 'UNION ';
  SQLQ := SQLQ || 'select  ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  ingredient_qualsyno_pns pns ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  pns.qualsyno_id=qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  syn.synonym_id=qsyn.synonym_id and ';
  SQLQ := SQLQ || '  pns.ingred_id = ' || ingred_id;
  SQLQ := SQLQ || 'UNION ';
  SQLQ := SQLQ || 'select  ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  ingredient_qualsyno_qss qss ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  qss.qualsyno_id=qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  syn.synonym_id=qsyn.synonym_id and ';
  SQLQ := SQLQ || '  qss.ingred_id = ' || ingred_id;
  -- Added Homeopathic Common Names MR Sep 24.  (Modified by GB)  
  -- NB. there are no French translations for Homeopathic names yet.
  SQLQ := SQLQ || 'UNION ';
  SQLQ := SQLQ || 'select  ';
  SQLQ := SQLQ || '  hgt.homeogentext_name_eng, ';
  SQLQ := SQLQ || '  hgt.homeogentext_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  homeopathic_generic_texts hgt, ';
  SQLQ := SQLQ || '  homeopathic_common_name_hgts hcn ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  hgt.homeogentext_id=hcn.homeogentext_id and ';
  SQLQ := SQLQ || '  hcn.ingred_id = ' || ingred_id;
 
  SQLQ := SQLQ || 'UNION ';
  SQLQ := SQLQ || 'select  ';
  SQLQ := SQLQ || '  syn.synonym_name_eng,  ';
  SQLQ := SQLQ || '  syn.synonym_name_fr  ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  synonyms syn,  ';
  SQLQ := SQLQ || '  ingredients ing,  ';
  SQLQ := SQLQ || '  in_org_grp_organism iogo,  ';
  SQLQ := SQLQ || '  organisms org,  ';
  SQLQ := SQLQ || '  organism_qualifiedsynonyms orgqsyn  ';
  SQLQ := SQLQ || 'where  ';
  SQLQ := SQLQ || '  ing.org_grp_id = iogo.org_grp_id and  ';
  SQLQ := SQLQ || '  iogo.organism_id = org.organism_id and  ';
  -- SQLQ := SQLQ || '  ing.ingred_authorized_name_eng = org.organism_name and  ';
  SQLQ := SQLQ || '  orgqsyn.organism_id = org.organism_id and  ';
  SQLQ := SQLQ || '  orgqsyn.qualsyno_id = qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  qsyn.synonym_id = syn.synonym_id and  ';
  SQLQ := SQLQ || '  ing.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN synonym_cursor FOR SQLQ;
  LOOP
    Fetch Synonym_cursor INTO Syn_Rec;
    IF Synonym_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE
    
      IF LANG='ENG' THEN 
        Synonym := Syn_Rec.synonym_name_eng;
      ELSE
        Synonym := Syn_Rec.synonym_name_fr;
      END IF;
      
      IF (Synonym is not null AND Synonym <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF length(Synonym_List) is not null THEN 
          Synonym_List := Synonym_List || concatenator;
        END IF;
        -- concatenate the Synonym
        Synonym_List := Synonym_List || Synonym;      
      END IF;  
    END IF;
    
  END LOOP;
  
  CLOSE synonym_cursor;
  RETURN synonym_List;
END GETSYNONYMS;
/


------------------------------------------------------------
--              GetQualifiedSynonymReferences             --
------------------------------------------------------------
-- Used by the Ingredient Editor to accellerate the       --
-- editing.                                               --  
------------------------------------------------------------

CREATE OR REPLACE FUNCTION GetQualifiedSynonymReferences(
  concatenator VARCHAR2,
  qualsyno_id    INTEGER
) RETURN VARCHAR2
IS
  TYPE qualsynoref_cursor_type IS REF CURSOR;
  qualsynoref_cursor qualsynoref_cursor_type;
  TYPE qualsynoref_Type is record (
    qualsynoref VARCHAR2(1000)
  );
  qualsynoref_Rec    qualsynoref_Type;
  qualsynoref_List   VARCHAR2(8000);
  code               VARCHAR2(1000);
  SQLQ               VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  qualsynoref_List := '';
  
  -- Build the select statement. 
  SQLQ :=         'select ';
  SQLQ := SQLQ || '  r.ref_code ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  refs r, ';
  SQLQ := SQLQ || '  qualifiedsynonym_references qsr, ';
  SQLQ := SQLQ || '  qualified_synonyms qs ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  qsr.ref_id=r.ref_id and ';
  SQLQ := SQLQ || '  qsr.qualsyno_id=qs.qualsyno_id and ';
  SQLQ := SQLQ || '  qs.qualsyno_id=' || qualsyno_id;

  -- Walk through the result set.
  OPEN qualsynoref_cursor FOR SQLQ;
  LOOP
    
    Fetch qualsynoref_cursor INTO qualsynoref_Rec;
    IF qualsynoref_cursor%NOTFOUND THEN
      EXIT;
    ELSE  
      
      code := qualsynoref_Rec.qualsynoref;
      
      IF (code is not null AND code <> 'NA') THEN
        -- Add a concatenator if the list already contains data.
        IF length(qualsynoref_List) is not null THEN 
          qualsynoref_List := qualsynoref_List || concatenator;
        END IF;
        -- concatenate the search term
        qualsynoref_List := qualsynoref_List || code;      

      END IF;  
    END IF;
    
  END LOOP;
  
  CLOSE qualsynoref_cursor;
  RETURN qualsynoref_List;
END GetQualifiedSynonymReferences;
/

