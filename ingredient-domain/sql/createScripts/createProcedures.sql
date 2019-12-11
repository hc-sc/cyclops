-------------------------------------------------------------------------------
-- Create Procedures.  Please maintain alpha order
-------------------------------------------------------------------------------

---------------------------------------------------------------------
-- Create procedure which stamps a load.
---------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE StampCurrentLoad(
  loader_version in varchar2
)
IS
  INGREDIENT_COUNT integer;
  INGRED_COUNT_STRING varchar2(10);
BEGIN
  select CAST(count(*) as varchar2(10)) into ingred_count_string from ingredients;
  DELETE FROM APPLICATION_PROPERTIES WHERE key='loader.ingredients_loaded';
  INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('loader.ingredients_loaded', ingred_count_string);
  DELETE FROM APPLICATION_PROPERTIES WHERE key='loader.load_date';
  INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('loader.load_date', to_char(sysdate, 'YYYY-MM-DD'));
  DELETE FROM APPLICATION_PROPERTIES WHERE key='loader.version';
  INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('loader.version', loader_version);
END StampCurrentLoad;
/

---------------------------------------------------------------------
-- Procedure to refresh materialized views.
---------------------------------------------------------------------
Create or Replace PROCEDURE RefreshMaterializedView(
  viewName VARCHAR2)
IS
BEGIN
  DBMS_SNAPSHOT.REFRESH(viewName,'C');
END RefreshMaterializedView;
/

------------------------------------------------------------
--                         AddMIRoles                     --
------------------------------------------------------------
-- The series of AddMIRoles procedures are used to add
-- MI roles to ingredients related to other Ingredients
-- that have a medicinal role.  
--
-- Example:  Garlic Power is a Medicinal Ingredient because
-- Allium Sativum is a Medicinal Ingredient and that they 
-- share the Allium Sativum organism.
------------------------------------------------------------

-- The following set of 3 functions "ADDMIROLES3", "ADDMIROLES2"
-- and "ADDMIROLES" are related.
CREATE OR REPLACE PROCEDURE ADDMIROLES3 (
  in_ingred_id integer 
) IS
  medicinal_role_count integer;
BEGIN
  -- Build the select statement. 
  -- 1. Add a Medicinal role to this ingredient if it does not have one.
  select 
    count(*) into medicinal_role_count
  from 
    ingredient_roles ir
  where
    ir.rolespec_class_name='MedicinalRole' and
    ir.ingred_id = in_ingred_id;

  if medicinal_role_count!=1 then
    insert into ingredient_roles (ingred_id, ingredrole_id, rolespec_class_name, ingredrole_creation_date, 
ingredrole_lastupdate_date, useracc_id) values (
      in_ingred_id, INGREDIENT_ROLES_SEQ.nextval, 'MedicinalRole', sysdate, sysdate, 1);
  end if;
END ADDMIROLES3;
/

-- The following set of 3 functions "ADDMIROLES3", "ADDMIROLES2"
-- and "ADDMIROLES" are related.
CREATE OR REPLACE PROCEDURE ADDMIROLES2 (
  in_organism_id integer 
) IS
  TYPE ingredient_cursor_type IS REF CURSOR;
  ingredient_cursor ingredient_cursor_type;
  TYPE ingredient_Type is record (
    ingred_id integer
  );
  ingredient_Rec ingredient_Type;
  SQLQ VARCHAR2(8000);
BEGIN
  -- 1. Find all ingredients that have the specified organism.
  SQLQ :=         'select distinct ';
  SQLQ := SQLQ || '    ing.ingred_id ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredients ing, ';
  SQLQ := SQLQ || '  organisms org, ';
  SQLQ := SQLQ || '  in_org_grp_organism orggrp ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  org.organism_id =' || in_organism_id || ' and ';
  SQLQ := SQLQ || '  org.organism_id = orggrp.organism_id and ';
  SQLQ := SQLQ || '  ing.org_grp_id = orggrp.org_grp_id ';
  -- Walk through the result set.
  OPEN ingredient_cursor FOR SQLQ;
  LOOP
    Fetch ingredient_cursor INTO ingredient_Rec;
    IF ingredient_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      -- Add a concatinator if the list contains data.
      ADDMIROLES3(ingredient_rec.ingred_id);
    END IF;
  END LOOP;
  CLOSE ingredient_cursor;
END ADDMIROLES2;
/

-- The following set of 3 functions "ADDMIROLES3", "ADDMIROLES2"
-- and "ADDMIROLES" are related.
CREATE OR REPLACE PROCEDURE ADDMIROLES 
IS
  TYPE organism_cursor_type IS REF CURSOR;
  organism_cursor organism_cursor_type;
  TYPE Organism_Type is record (
    organism_id integer
  );
  organism_Rec organism_Type;
  SQLQ VARCHAR2(8000);
BEGIN
  -- 1. Find all organisms with matching ingredient names.
  -- 2. If the Ingredient has a medicinal role, then return the organism.
  SQLQ :=         'select ';
  SQLQ := SQLQ || '  org.organism_id ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredients ing, ';
  SQLQ := SQLQ || '  organisms org, ';
  SQLQ := SQLQ || '  in_org_grp_organism orggrp, ';
  SQLQ := SQLQ || '  ingredient_roles ro ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  ing.org_grp_id = orggrp.org_grp_id and ';
  SQLQ := SQLQ || '  org.organism_id = orggrp.organism_id and ';
  SQLQ := SQLQ || '  ing.ingred_authorized_name_eng = org.organism_name and ';
  SQLQ := SQLQ || '  ro.ingred_id = ing.ingred_id and ';
  SQLQ := SQLQ || '  ro.rolespec_class_name=''MedicinalRole'' ';
  SQLQ := SQLQ || 'order by ';
  SQLQ := SQLQ || '  ing.ingred_authorized_name_eng ';

  -- Walk through the result set.
  OPEN organism_cursor FOR SQLQ;
  LOOP
    Fetch organism_cursor INTO organism_Rec;
    IF organism_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      -- Add a concatinator if the list contains data.
      ADDMIROLES2(organism_rec.organism_id);
      COMMIT;
    END IF;
  END LOOP;
  CLOSE organism_cursor;
END ADDMIROLES;
/

