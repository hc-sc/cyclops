-- This is the DDL database upgrade script for NHPOS Release 4.1.0.
-- The script is required for creating a new database, or upgrading from a previous schema.
-- 
-- Release: 4.1.0
-- Maintainer: Mathieu Dugré
-- Last update: 2012-05-18 / TQA: 2012-07-19
--

-- New sub population flag for ITS #8730 - User defined subpopulation lists in the ePLA
ALTER TABLE "SUB_POPULATION" 
   ADD SUBPOP_PREFERRED VARCHAR2(1) DEFAULT 'n' NOT NULL;
   
-- Submitted to TQA_DB-BD / SSC-SADATE_RDBMS on 2012-07-19.
   