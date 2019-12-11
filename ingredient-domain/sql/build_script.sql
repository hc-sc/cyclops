-- ****************************************************************************
-- Upgrade Script for Ingredient Domain version 2.8.0
--
-- This script is executed once by the schema owner during an upgrade of the
-- schema.  Change the above version number and the version number at the end
-- of the script as well.  Change the Release number in the spool output file.
-- This script assumes that it is executed in SQLplus
-- This script assumes that the existing release schema exists.
-- ****************************************************************************

-- Set reporting/listing environment variables
SET NEWPAGE 0
SET SPACE 0
SET LINESIZE 80
SET PAGESIZE 0
SET FEEDBACK OFF
SET HEADING OFF
SET MARKUP HTML OFF
SET ESCAPE \

-- Spooling has been enabled for error trapping purposes.  When this script is
-- called by the Ant script, the file will be renamed with a date/time stamp.
SET ECHO ON
SET FEEDBACK ON

set autocommit on

-- Delete materialized views. (Required to delete tables.)
drop materialized view ingredient_name_search_mv;
drop materialized view ingredient_name_search_ws_mv;
drop materialized view ingredient_organism_mv;
drop materialized view ingredientsources_mv;
drop materialized view ingredient_synonyms_mv;
drop materialized view monograph_synonyms_mv;
drop materialized view organism_constituent_mv;

-- These materialized views are used to enable fast Ingredients Web Service
drop materialized view orgpart_parttype_mv;
drop materialized view orgpart_groupname_mv;
drop materialized view orgpart_constituent_mv;
drop materialized view orgpart_constituents_mv;
drop materialized view orgpart_organism_mv;
drop materialized view ingredient_orgpart_mv;
drop materialized view sourceOrganismParts_mv;
drop materialized view relatedSourceIngredients_mv;

-- These materialized views are used by the ingredient edit pages
drop materialized view SUBINGREDIENT_MV;
drop materialized view QUALIFIEDSYNONYM_REFERENCES_MV;
drop materialized view SPECIES_MV;
drop materialized view SUBTAXA_MV;
drop materialized view REFERENCES_MV;

-- Find and Delete all existing tables in the current schema.
SPOOL dropTables.sql
select 'drop table ', table_name, ' cascade constraints \;' 
from 
  user_tables
where 
  table_name!='ERROR_LOGS' and 
  table_name!='LOGS' and 
  table_name!='LOG_DETAILS' and 
  table_name!='NHPSAS_INGREDIENT_SYNC';
SPOOL OFF
PROMPT Dropping tables...
@dropTables.sql

-- Drop the single remaining table (not previously deleted due to the size of the name)
drop table ORGANISM_TYPE_GRP_ORGPARTTYPES;

SPOOL dropSequences.sql
select 'drop sequence ', sequence_name, ' \;' from user_sequences where sequence_name != 'ols_tracking_no_seq';
SPOOL OFF
PROMPT Dropping sequences...
@dropSequences.sql

-- Spooling has been enabled for error trapping purposes.  When this script is
-- called by the Ant script, the file will be renamed with a date/time stamp.
SET ECHO ON
SET FEEDBACK ON
SPOOL schema_load_report.sql

-- Call the individual scripts to recreate everything
@createScripts\\createBaseTables.sql
@createScripts\\createJoinTables.sql
@createScripts\\createForeignKeyConstraints.sql
@createScripts\\createSequences.sql
@createScripts\\createTriggers.sql
@createScripts\\createFunctions.sql
@createScripts\\createMaterializedViews.sql
@createScripts\\createOrgPartsFunctions.sql
@createScripts\\createProcedures.sql
@createScripts\\createOrgPartsMvs.sql
@createScripts\\createTrackingTables.sql
@createScripts\\createNHPSAStracker.sql

-- Populate the initial contents: user ids, countries, ingredient sources, 
-- organism types, ingredient roles.
-- SET FEEDBACK OFF
-- PROMPT -------------------------------------------------
-- PROMPT Populating (static) content tables...
-- PROMPT -------------------------------------------------
-- Due to environment changes, the following script is now loaded via the loader.
-- @oracle_content_2008_11_10.sql
-- Create the initial account.  This is leveraged by hibernate configuration.
INSERT INTO user_accounts (useracc_id,useracc_account_name,useracc_first_name,useracc_last_name,useracc_email,useracc_creation_date,useracc_lastupdate_date,useracc_useracc_id) VALUES (1,'uploader','System','Uploader','sysUploader2@hc.ca',SYSDATE ,SYSDATE ,0);
commit;

-- Stamp Database Schema Version and todays date into the APPLICATION_PROPERTIES table. 
INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('schema.version', '2.1.1');
INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('schema.date', TO_CHAR(SYSDATE, 'YYYY-MM-DD'));

commit;

SPOOL NHPOS_R4.1_schema_upgrade_report.sql

@createScripts\\ePLA20.sql
commit;

-- Add the stored procedures used by the CRON job to truncate the tables.
@stagingScripts\\configuration.sql
@stagingScripts\\preLoader.sql
@stagingScripts\\postLoader.sql
@stagingScripts\\handShake.sql
commit;

-- Release 4.1.0 upgrade scripts
@createScripts\\upgrade_R4_1_0_DDL.sql
commit;

-- Stamp Database Schema Version and todays date into the APPLICATION_PROPERTIES table. 
UPDATE APPLICATION_PROPERTIES SET DATA='2.8.1' WHERE KEY='schema.version';
UPDATE APPLICATION_PROPERTIES SET DATA=TO_CHAR(SYSDATE, 'YYYY-MM-DD') WHERE KEY='schema.date';

commit;

SPOOL OFF;