-------------------------------------------------------------------------------
--                               CONFIGURATION.SQL                           --
-------------------------------------------------------------------------------
-- Creation of configuration tables used in the truncating, sequence  reset  --
-- and refreshing materialized views.                                        --
-------------------------------------------------------------------------------
-- Author:   Germain Brunet                                                  --
-- Date:     2011-12-30                                                      --
-------------------------------------------------------------------------------

drop table NHP_STAGE_TABLES;
drop table NHP_STAGE_SEQUENCES;
drop table NHP_STAGE_MAT_VIEWS;

-------------------------------------------------------------------------------
--                               NHP_STATE_TABLES                            --
-------------------------------------------------------------------------------
-- This is the list of tables that should be truncated as part of the        --
-- pre-loading process.  Note that we do not have the complete table list as --
-- as some tables must be retained for configuration, logging and NHPSAS     --
-- synchronization purpose.  To ensure that this configuration information   --
-- can be extended, we added the 'table_truncate' column.  A 'Y' indicates   --
-- that this is a table that should be truncated.                            --
-- Note:  The PRE_LOADER_PKG located in the preLoader.sql file makes         --
--        reference to this information.                                     --
-------------------------------------------------------------------------------
create table NHP_STAGE_TABLES (
  table_id       INTEGER default null NOT NULL,
  table_name     VARCHAR2(100) default null NOT NULL,
  table_truncate VARCHAR2(1) default null NOT NULL
);
ALTER TABLE  NHP_STAGE_TABLES ADD (
    CONSTRAINT  NHP_STAGE_TABLES_PK 
    PRIMARY KEY ( table_id ) 
    USING INDEX 
); 
ALTER TABLE  NHP_STAGE_TABLES
    ADD CONSTRAINT  NHP_STAGE_TABLES_UK1 
    UNIQUE (table_name);

insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (1, 'ADMINISTRATION_ROUTES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (2, 'ADMINRTE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (3, 'APPLICATION_TEXT', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (4, 'APPLICATION_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (5, 'ATTACHMENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (6, 'AUDIT_ENTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (7, 'BIOLOGICAL_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (8, 'CHEM_CLASSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (9, 'CHEM_SUBCLASSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (10, 'CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (11, 'CITATION_SPECIALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (12, 'CODED_RISKS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (13, 'CODED_RISKS_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (14, 'COMMON_TERMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (15, 'COMMON_TERM_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (16, 'CONSENCIT_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (17, 'CONTEXT_SENSITIVE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (18, 'COUNTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (19, 'DIETARY_ALLOWANCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (20, 'DOSAGEFORM_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (21, 'DOSAGEFORM_DOSAGEFORMGROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (22, 'DOSAGEFORM_DOSAGEUNITS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (23, 'DOSAGEFORM_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (24, 'DOSAGE_FORMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (25, 'DOSAGE_FORM_ADMIN_ROUTES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (26, 'DOSAGE_FORM_GROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (27, 'DOSAGE_FORM_GROUP_TEST_SPECS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (28, 'DOSAGE_FORM_TEST_SPECS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (29, 'DOSAGE_RANGES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (30, 'DOSAGE_UNITS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (31, 'DR$INGREDIENT_SNNMS_MV_IDX7$I', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (32, 'DR$INGREDIENT_SNNMS_MV_IDX7$K', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (33, 'DR$INGREDIENT_SNNMS_MV_IDX7$N', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (34, 'DR$INGREDIENT_SNNMS_MV_IDX7$R', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (35, 'DR$INGREDIENT_SNNMS_MV_IDX8$I', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (36, 'DR$INGREDIENT_SNNMS_MV_IDX8$K', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (37, 'DR$INGREDIENT_SNNMS_MV_IDX8$N', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (38, 'DR$INGREDIENT_SNNMS_MV_IDX8$R', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (39, 'ENDANG_ORGANISM_COUNTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (40, 'EVIDENCE_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (41, 'EX_ORG_GRP_FAMILIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (42, 'EX_ORG_GRP_GENERA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (43, 'EX_ORG_GRP_ORGANISM', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (44, 'EX_ORG_GRP_SPECIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (45, 'EX_ORG_GRP_SUBTAXA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (46, 'FAMILIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (47, 'GENERA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (48, 'GENERIC_TEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (49, 'GENERIC_TEXTS_ENG_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (50, 'GENERIC_TEXTS_FR_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (51, 'GEOGRAPHIC_AREAS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (52, 'HOMEOPATHIC_COMMON_NAME_HGTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (53, 'HOMEOPATHIC_DILUTIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (54, 'HOMEOPATHIC_FORMULAS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (55, 'HOMEOPATHIC_GENERIC_TEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (56, 'HOMEOPATHIC_METHOFPREPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (57, 'HOMEOPATHIC_PREPARATION_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (58, 'HOMEOPATHIC_PROPER_NAME_HGTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (59, 'HOMEOPATHIC_SOURCE_MAT_HGTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (60, 'ICH_CLASSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (61, 'ICH_CLASS_TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (62, 'IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (63, 'IMPURITY_SPECIALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (64, 'IMPURITY_TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (65, 'INDICATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (66, 'INDICATION_ADMIN_ROUTES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (67, 'INDICATION_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (68, 'INGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (69, 'INGREDIENT_BIO_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (70, 'INGREDIENT_CATEGORIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (71, 'INGREDIENT_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (72, 'INGREDIENT_INGRED_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (73, 'INGREDIENT_NAME_SEARCH_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (74, 'INGREDIENT_NAME_SEARCH_WS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (75, 'INGREDIENT_NHPCLASSIFICATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (76, 'INGREDIENT_ORGANISM_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (77, 'INGREDIENT_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (78, 'INGREDIENT_ORGPART_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (79, 'INGREDIENT_PREPTYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (80, 'INGREDIENT_QUALSYNO_CNS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (81, 'INGREDIENT_QUALSYNO_PNS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (82, 'INGREDIENT_QUALSYNO_QSS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (83, 'INGREDIENT_REG_ENTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (84, 'INGREDIENT_ROLES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (85, 'INGREDIENT_ROLE_PURPOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (86, 'INGREDIENT_SOURCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (87, 'INGREDIENT_SPECIALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (88, 'INGREDIENT_STD_GRADE_REFS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (89, 'INGREDIENT_SUBINGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (90, 'INGREDIENT_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (91, 'INGREDIENT_SYNONYMS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (92, 'INGREDIENT_TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (93, 'INGREDIENT_TEST_SPECS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (94, 'IN_ORG_GRP_FAMILIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (95, 'IN_ORG_GRP_GENERA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (96, 'IN_ORG_GRP_ORGANISM', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (97, 'IN_ORG_GRP_SPECIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (98, 'IN_ORG_GRP_SUBTAXA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (99, 'MANUFACT_PROCESSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (100, 'MANUFACT_PROCESS_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (101, 'MANUFACT_PROCESS_INGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (102, 'MANUFACT_PROC_BIO_IMPURTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (103, 'MEDDRA_HLGTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (104, 'MEDDRA_HLGT_HLTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (105, 'MEDDRA_HLTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (106, 'MEDDRA_HLT_PTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (107, 'MEDDRA_LLTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (108, 'MEDDRA_PREF_TERMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (109, 'MEDDRA_PTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (110, 'MEDDRA_SOCS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (111, 'MEDDRA_SOC_HLGTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (112, 'MONODOSECOMB_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (113, 'MONODOSECOMB_MONOUSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (114, 'MONODOSE_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (115, 'MONODOSE_MONOSUBIDOSECOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (116, 'MONODRFRUSE_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (117, 'MONODURATION_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (118, 'MONOGRAPHS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (119, 'MONOGRAPH_CITATION_JOINS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (120, 'MONOGRAPH_DIRECTIONS_FOR_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (121, 'MONOGRAPH_DOSECOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (122, 'MONOGRAPH_DOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (123, 'MONOGRAPH_DOSE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (124, 'MONOGRAPH_DOSE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (125, 'MONOGRAPH_DOSE_MONO_DFUS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (126, 'MONOGRAPH_DOSE_MONO_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (127, 'MONOGRAPH_DOSE_NOTE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (128, 'MONOGRAPH_DOSE_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (129, 'MONOGRAPH_DRFRUSE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (130, 'MONOGRAPH_DRFRUSE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (131, 'MONOGRAPH_DRFRUSE_MONO_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (132, 'MONOGRAPH_DRFRUSE_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (133, 'MONOGRAPH_DURATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (134, 'MONOGRAPH_DURATION_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (135, 'MONOGRAPH_DURATION_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (136, 'MONOGRAPH_DURATION_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (137, 'MONOGRAPH_DUR_MONOGRAPHS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (138, 'MONOGRAPH_DUR_MONO_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (139, 'MONOGRAPH_ENTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (140, 'MONOGRAPH_ENTRIES_COMMONNAMES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (141, 'MONOGRAPH_ENTRIES_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (142, 'MONOGRAPH_ENTRIES_PREPTYPEGRPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (143, 'MONOGRAPH_ENTRIES_PREPTYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (144, 'MONOGRAPH_ENTRIES_SRC_INGREDS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (145, 'MONOGRAPH_ENTRY_GENERIC_TEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (146, 'MONOGRAPH_ENTRY_QUALSYNO_CNS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (147, 'MONOGRAPH_ENTRY_QUALSYNO_PNS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (148, 'MONOGRAPH_GENERIC_TEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (149, 'MONOGRAPH_GEN_NOTE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (150, 'MONOGRAPH_GROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (151, 'MONOGRAPH_MONOGRAPH_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (152, 'MONOGRAPH_NMI_NOTE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (153, 'MONOGRAPH_PHASES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (154, 'MONOGRAPH_PHASE_MONOGRAPHS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (155, 'MONOGRAPH_PHASE_MONO_DURATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (156, 'MONOGRAPH_PREPARATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (157, 'MONOGRAPH_RISKSTMT_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (158, 'MONOGRAPH_RISKSTMT_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (159, 'MONOGRAPH_RISKSTMT_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (160, 'MONOGRAPH_RISK_MONOGRAPHS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (161, 'MONOGRAPH_RISK_NOTE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (162, 'MONOGRAPH_RISK_SRC_INGREDS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (163, 'MONOGRAPH_RISK_STMTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (164, 'MONOGRAPH_ROA_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (165, 'MONOGRAPH_ROA_DOSAGE_FORMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (166, 'MONOGRAPH_ROA_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (167, 'MONOGRAPH_ROUTEOFADMINS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (168, 'MONOGRAPH_RSKSTMT_MONO_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (169, 'MONOGRAPH_SPEC_INFO_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (170, 'MONOGRAPH_STOR_COND_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (171, 'MONOGRAPH_SUBINGCOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (172, 'MONOGRAPH_SUBINGDOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (173, 'MONOGRAPH_SUBING_DOSECOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (174, 'MONOGRAPH_SUB_POPULATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (175, 'MONOGRAPH_SYNONYMS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (176, 'MONOGRAPH_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (177, 'MONOGRAPH_USES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (178, 'MONOGRAPH_USES_MONOGRAPHS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (179, 'MONOGRAPH_USE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (180, 'MONOGRAPH_USE_GENTEXTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (181, 'MONOGRAPH_USE_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (182, 'MONORISK_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (183, 'MONOSBINGDCMBS_MONOSBINGDSS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (184, 'MONOUSE_DOSAGEFORMGROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (185, 'MONOUSE_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (186, 'MONO_DOSECOMB_MONO_DOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (187, 'MONO_ENTRIES_TXT_MONOSOURCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (188, 'MONO_ENTRY_MONOSRCINGREDS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (189, 'MONO_ENTRY_MONOSRCORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (190, 'MONO_ENTRY_MONO_DOSECOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (191, 'MONO_PHASE_MONO_DOSECOMBS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (192, 'MONO_SOURCE_INGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (193, 'MONO_SOURCE_ORGPARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (194, 'MONO_SRCINGRED_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (195, 'MONO_SRCORGPART_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (196, 'NHP_CLASSIFICATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (197, 'NHP_CLASS_BIO_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (198, 'NHP_CLASS_IDNT_TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (199, 'NHP_CLASS_IMPURITIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (200, 'NHP_CLASS_INGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (201, 'NHP_CLASS_QTY_TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (202, 'ORGANISMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (203, 'ORGANISM_CONSTITUENT_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (204, 'ORGANISM_COUNTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (205, 'ORGANISM_GEOGRAPHIC_AREAS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (206, 'ORGANISM_GROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (207, 'ORGANISM_PARTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (208, 'ORGANISM_PART_SUBINGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (209, 'ORGANISM_PART_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (210, 'ORGANISM_QUALIFIEDSYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (211, 'ORGANISM_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (212, 'ORGANISM_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (213, 'ORGANISM_TYPE_GROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (214, 'ORGANISM_TYPE_GRP_ORGPARTTYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (215, 'ORGPARTTYPE_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (216, 'ORGPART_CONSTITUENTS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (217, 'ORGPART_CONSTITUENT_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (218, 'ORGPART_GROUPNAME_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (219, 'ORGPART_ORGANISM_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (220, 'ORGPART_PARTTYPE_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (221, 'PERMISSIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (222, 'PREPARATION_RULES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (223, 'PREPARATION_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (224, 'PREPARATION_TYPE_GROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (225, 'PREPRULES_SOLVENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (226, 'PREPTYPES_SOLVENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (227, 'PREPTYPE_PREPTYPEGROUPS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (228, 'PRODUCT_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (229, 'PROVINCES_STATES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (230, 'PUBLISHED_JOURNALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (231, 'PURPOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (232, 'PURPOSE_ADMINRTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (233, 'PURPOSE_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (234, 'PURPOSE_TEST_SPECS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (235, 'QUALIFIEDSYNONYM_REFERENCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (236, 'QUALIFIEDSYNONYM_REFERENCES_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (237, 'QUALIFIED_SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (238, 'QUALITY_TOLERANCE_MODELS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (239, 'RATIONALES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (240, 'RECOMMENDED_DIETARY_ALLOWANCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (241, 'REFERENCES_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (242, 'REFS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (243, 'REGISTRY_ENTRIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (244, 'RELATEDSOURCEINGREDIENTS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (245, 'RESTRICTIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (246, 'RESTRICTION_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (247, 'RESTRICTION_PRODUCT_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (248, 'RESTRICTION_PURPOSES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (249, 'RESTRICTION_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (250, 'RISKS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (251, 'RISK_STMTS_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (252, 'RISK_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (253, 'ROLE_SPECIALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (254, 'SOLVENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (255, 'SOURCEORGANISMPARTS_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (256, 'SOURCE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (257, 'SPECIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (258, 'SPECIES_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (259, 'STANDARD_GRADE_REFERENCES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (260, 'SUBINGREDIENTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (261, 'SUBINGREDIENT_ADMIN_ROUTES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (262, 'SUBINGREDIENT_DOSAGE_FORMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (263, 'SUBINGREDIENT_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (264, 'SUBINGREDIENT_REFS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (265, 'SUBTAXA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (266, 'SUBTAXA_MV', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (267, 'SUB_POPULATION', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (268, 'SYNONYMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (269, 'SYNONYM_REFS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (270, 'SYNONYM_TEXTS_ENG_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (271, 'SYNONYM_TEXTS_FR_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (272, 'TAXA', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (273, 'TEST_CATEGORIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (274, 'TEST_METHODS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (275, 'TEST_METHOD_CATEGORIES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (276, 'TEST_METHOD_SUBTYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (277, 'TEST_METHOD_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (278, 'TEST_SPECIFICATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (279, 'TEST_SPEC_TST_MTDS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (280, 'TEXT_MONO_SRCMATERIALS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (281, 'TRAD_PARADIGMS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (282, 'TXTMONOSRCMAT_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (283, 'UNITS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (284, 'UNITS_TYPES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (285, 'USERACCOUNT_USERROLES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (286, 'USERROLE_PERMISSIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (287, 'USER_ACCOUNTS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (288, 'USER_ROLES', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (289, 'USE_CITATIONS', 'Y');
insert into NHP_STAGE_TABLES (table_id, table_name, table_truncate) values (290, 'USE_TYPES', 'Y');
COMMIT;

-------------------------------------------------------------------------------
--                              NHP_STATE_SEQUENCES                          --
-------------------------------------------------------------------------------
-- This is the list of sequences that should have their value reset when we  --
-- are preparing for a new data load.  This should be done before the        --
-- loading process is started.  In one of the cases, we specify a number     --
-- other than 1.  This is because the published journals sequence needs to   --
-- start at a higher number because the table is loaded via two differente   --
-- sources of information.                                                   --
-- Note:  The PRE_LOADER_PKG located in the preLoader.sql file makes         --
--        reference to this information.                                     --
-------------------------------------------------------------------------------
create table NHP_STAGE_SEQUENCES (
  sequence_id       INTEGER default null NOT NULL,
  sequence_name     VARCHAR2(100) default null NOT NULL,
  sequence_start_number INTEGER default 1 NOT NULL
);
ALTER TABLE  NHP_STAGE_SEQUENCES ADD (
    CONSTRAINT  NHP_STAGE_SEQUENCES_PK 
    PRIMARY KEY ( sequence_id ) 
    USING INDEX 
); 
ALTER TABLE  NHP_STAGE_SEQUENCES
    ADD CONSTRAINT  NHP_STAGE_SEQUENCES_UK1 
    UNIQUE (sequence_name);

insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (1, 'ADMINISTRATION_ROUTES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (2, 'APPLICATION_TEXT_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (3, 'ATTACHMENTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (4, 'AUDIT_ENTRIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (5, 'BIOLOGICAL_IMPURITIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (6, 'CHEM_CLASSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (7, 'CHEM_SUBCLASSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (8, 'CODED_RISK_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (9, 'CITATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (10, 'CITATION_SPECIALS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (11, 'CONTEXT_SENSTVE_CITATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (12, 'COUNTRIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (13, 'DIETARY_ALLOWANCES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (14, 'DOSAGE_FORMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (15, 'DOSAGE_FORM_GROUPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (16, 'DOSAGE_RANGE_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (17, 'EVIDENCE_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (18, 'EXTERNAL_MONOGRAPHS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (19, 'FAMILIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (20, 'GENERA_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (21, 'GENERIC_TEXT_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (22, 'GEOGRAPHIC_AREAS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (23, 'HOMEOPATHIC_DILUTIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (24, 'HOMEOPATHIC_GENERIC_TEXTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (25, 'HOMEOPATHIC_FORMULAS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (26, 'HOMEOPATHIC_METHOFPREPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (27, 'HOMEOPATHIC_PREP_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (28, 'ICH_CLASSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (29, 'IMPURITIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (30, 'INDICATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (31, 'INDICATION_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (32, 'INGREDIENTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (33, 'INGREDIENT_CATEGORIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (34, 'INGREDIENT_ROLES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (35, 'INGREDIENT_SOURCES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (36, 'INGREDIENT_SPECIALS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (37, 'MANUFACT_PROCESSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (38, 'MEDDRA_PREF_TERMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (39, 'MONOGRAPHS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (40, 'MONOGRAPH_GROUPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (41, 'MONOGRAPH_DIRECTS_FOR_USES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (42, 'MONOGRAPH_DOSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (43, 'MONOGRAPH_DURATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (44, 'MONOGRAPH_ENTRIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (45, 'MONOGRAPH_DOSECOMBS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (46, 'MONOGRAPH_PHASES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (47, 'MONOGRAPH_PREPARATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (48, 'MONOGRAPH_RISK_STMTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (49, 'MONOGRAPH_ROUTEOFADMINS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (50, 'MONOGRAPH_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (51, 'MONO_SOURCE_INGREDIENTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (52, 'MONO_SOURCE_ORGPARTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (53, 'MONOGRAPH_SUBINGCOMBS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (54, 'MONOGRAPH_SUBING_DOSECOMBS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (55, 'MONOGRAPH_SUBINGDOSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (56, 'MONOGRAPH_USES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (57, 'NHP_CLASSIFICATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (58, 'ORGANISMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (59, 'ORGANISM_GROUPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (60, 'ORGANISM_PARTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (61, 'ORGANISM_PART_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (62, 'ORGANISM_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (63, 'ORGANISM_TYPE_GROUPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (64, 'PERMISSIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (65, 'PREPARATION_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (66, 'PREPARATION_TYPE_GROUPS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (67, 'PRODUCT_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (68, 'PUBLISHED_JOURNALS_SEQ', 25000);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (69, 'PURPOSES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (70, 'QUALIFIED_SYNONYMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (71, 'QUALITY_TOLERANCE_MODELS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (72, 'RATIONALES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (73, 'RECOMMENDED_DIETARY_ALLO_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (74, 'REFS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (75, 'REGISTRY_ENTRIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (76, 'RESTRICTIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (77, 'RESTRICTION_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (78, 'RISKS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (79, 'RISK_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (80, 'ROLE_SPECIALS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (81, 'SOLVENTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (82, 'SPECIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (83, 'SUBINGREDIENTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (84, 'SUBTAXA_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (85, 'SUB_POPULATION_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (86, 'SYNONYMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (87, 'TAXA_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (88, 'TEST_CATEGORIES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (89, 'TEST_METHODS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (90, 'TEST_METHOD_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (91, 'TEST_METHOD_SUBTYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (92, 'TEST_SPECIFICATIONS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (93, 'TEXT_MONO_SRCMATERIALS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (94, 'TRAD_PARADIGMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (95, 'UNITS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (96, 'UNITS_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (97, 'USER_ACCOUNTS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (98, 'USE_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (99, 'USER_ROLES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (100, 'PREPARATION_RULES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (101, 'APPLICATION_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (102, 'DOSAGE_UNITS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (103, 'PROVINCES_STATES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (104, 'COMMON_TERMS_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (105, 'COMMON_TERM_TYPES_SEQ', 1);
insert into NHP_STAGE_SEQUENCES (sequence_id, sequence_name, sequence_start_number) values (106, 'STANDARD_GRADE_REFS_SEQ', 1);
COMMIT;

-------------------------------------------------------------------------------
--                         NHP_STATE_MATERIALIZED_VIEWS                      --
-------------------------------------------------------------------------------
-- This is the list of materialized views that need to be refreshed once the --
-- loading process is completed.  Note that the mat_view_refresh_order       --
-- column controls the order in which the views are refreshed due to         -- 
-- dependencies between the views.                                           --
-- Note:  The POST_LOADER_PKG located in the postLoader.sql file makes       --
--        reference to this information.                                     --
-------------------------------------------------------------------------------
create table NHP_STAGE_MAT_VIEWS (
  mat_view_id            INTEGER default null NOT NULL,
  mat_view_name          VARCHAR2(100) default null NOT NULL,
  mat_view_refresh_order INTEGER default null NOT NULL
);
ALTER TABLE  NHP_STAGE_MAT_VIEWS ADD (
    CONSTRAINT  NHP_STAGE_MAT_VIEWS_PK 
    PRIMARY KEY ( mat_view_id ) 
    USING INDEX 
); 
ALTER TABLE  NHP_STAGE_MAT_VIEWS
    ADD CONSTRAINT  NHP_STAGE_MAT_VIEWS_UK1 
    UNIQUE (mat_view_name);

insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (1, 'ingredient_name_search_mv', 1);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (2, 'ingredient_name_search_ws_mv', 2);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (3, 'ingredient_synonyms_mv', 3);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (4, 'monograph_synonyms_mv', 4);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (5, 'organism_constituent_mv', 5);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (6, 'orgpart_constituent_mv', 6);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (7, 'orgpart_parttype_mv', 7);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (8, 'orgpart_constituents_mv', 8);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (9, 'orgpart_organism_mv', 9);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (10, 'orgpart_groupname_mv', 10);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (11, 'RelatedSourceIngredients_mv', 11);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (12, 'ingredient_organism_mv', 12);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (13, 'ingredient_orgpart_mv', 13);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (14, 'SourceOrganismParts_mv', 14);

-- Extra rows added for the missing materialized views - GB 2012/02/20
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (15, 'references_mv', 15);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (16, 'qualifiedsynonym_references_mv', 16);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (17, 'species_mv', 17);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (18, 'subingredient_mv', 18);
insert into NHP_STAGE_MAT_VIEWS (mat_view_id, mat_view_name, mat_view_refresh_order) values (19, 'subtaxa_mv', 19);


COMMIT;