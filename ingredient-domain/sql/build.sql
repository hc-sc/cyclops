-- Description:
-- This script is to be executed in SQL Plus and will delete and recreate the 
-- ingredient database schema and base values.

-- Delete the normalized view. (Required to delete tables.)
drop materialized view ingredient_name_search_mv;
drop materialized view ingredient_name_search_ws_mv;
drop materialized view ingredient_synonyms_mv;
drop materialized view monograph_synonyms_MV;

-- Find and Delete all existing tables in the current schema.
SET NEWPAGE 0
SET SPACE 0
SET LINESIZE 80
SET PAGESIZE 0
SET ECHO OFF
SET FEEDBACK OFF
SET HEADING OFF
SET MARKUP HTML OFF
SET ESCAPE \
SPOOL DELETEME.SQL
select 'drop table ', table_name, ' cascade constraints \;' from user_tables;
SPOOL OFF
@DELETEME

-- Drop the single remaining table (not previously deleted due to the size of the name)
drop table ORGANISM_TYPE_GRP_ORGPARTTYPES;

SPOOL DELETEME.SQL
select 'drop sequence ', sequence_name, ' \;' from user_sequences;
SPOOL OFF
@DELETEME

-- Spooling has been enabled for error trapping purposes, and can be turned off once development is completed.
set echo on
spool schema_load.sql

-- Recreate all the tables
@c:\\oracle_schema.sql

spool off

-- Populate the initial contents
@c:\\oracle_content.sql

-- Stamp Database Schema Version and todays date into the APPLICATION_PROPERTIES table. 
INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('schema.version', '1.4.2 (alpha)');
INSERT INTO APPLICATION_PROPERTIES (KEY, DATA) values ('schema.date3', TO_CHAR(SYSDATE, 'YYYY-MM-DD'));

-- Commit all changes.
Commit;
