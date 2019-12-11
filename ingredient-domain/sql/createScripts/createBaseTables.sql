-------------------------------------------------------------------------------
-- Create Base tables.  NB. Join tables are created in the createJoinTables.sql
-- script.  Please maintain alpha order.
-------------------------------------------------------------------------------

CREATE TABLE  administration_routes (
    adminrt_code  Varchar2(20) default null NOT NULL, 
    adminrt_comment_eng  Varchar2(1000) default NULL , 
    adminrt_comment_fr  Varchar2(1000) default NULL , 
    adminrt_creation_date Date NOT NULL, 
    adminrt_id Integer default null NOT NULL, 
    adminrt_lastupdate_date Date NOT NULL, 
    adminrt_name_eng  Varchar2(100) default '' NOT NULL, 
    adminrt_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  administration_routes ADD (
    CONSTRAINT  administration_routes_PK 
    PRIMARY KEY ( adminrt_id ) 
    USING INDEX 
); 
ALTER TABLE  administration_routes
    ADD CONSTRAINT  administration_routes_UK1 
    UNIQUE (adminrt_code);
ALTER TABLE  administration_routes
    ADD CONSTRAINT  administration_routes_UK2 
    UNIQUE (adminrt_name_eng);
ALTER TABLE  administration_routes
    ADD CONSTRAINT  administration_routes_UK3 
    UNIQUE (adminrt_name_fr);
CREATE TABLE  adminrte_citations (
    	monoadminrt_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

---------------------------------------------------------------------
-- Create our Properties Table & Insert the version.
CREATE TABLE application_properties (
  KEY  VARCHAR2(200 BYTE) NOT NULL ENABLE, 
  DATA VARCHAR2(1000 BYTE),
  FLAG INTEGER
);
ALTER TABLE  application_properties ADD (
    CONSTRAINT  application_properties_PK 
    PRIMARY KEY ( key ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE  application_text (
    apptext_code  Varchar2(100) default '' NOT NULL, 
    apptext_creation_date Date NOT NULL, 
    apptext_id Integer default '0' NOT NULL, 
    apptext_lastupdate_date Date NOT NULL, 
    apptext_text_eng clob default null NOT NULL, 
    apptext_text_fr clob default null NOT NULL, 
    isinternalabls VARCHAR2(10 BYTE) DEFAULT '',
    useracc_id Integer default NULL 
);
ALTER TABLE  application_text ADD (
    CONSTRAINT  application_text_PK 
    PRIMARY KEY ( apptext_id ) 
    USING INDEX 
); 
ALTER TABLE  application_text
    ADD CONSTRAINT  application_text_UK1 
    UNIQUE (apptext_code);
    
---------------------------------------------------------------------
CREATE TABLE  attachments (
    attachment_cacheable_flag  Varchar2(1) default null NOT NULL, 
    attachment_content  blob default null NOT NULL, 
    attachment_creation_date Date , 
    attachment_desc_eng  Varchar2(1000) default NULL , 
    attachment_desc_fr  Varchar2(1000) default NULL , 
    attachment_height Integer default NULL , 
    attachment_id Integer default null NOT NULL, 
    attachment_lastupdate_date Date , 
    attachment_mimetype  Varchar2(100) default null NOT NULL, 
    attachment_name_eng  Varchar2(100) default NULL , 
    attachment_name_fr  Varchar2(100) default NULL , 
    attachment_size Integer default NULL , 
    attachment_width Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  attachments ADD (
    CONSTRAINT  attachments_PK 
    PRIMARY KEY ( attachment_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  audit_entries (
    audit_entries_id                 Integer       default '0'  NOT NULL, 
    audit_entries_object_id          Integer       default '0'  NOT NULL, 
    audit_entries_object_name        VarChar(200)  default '0'  NOT NULL, 
    audit_entries_trans_type         VarChar2(20)  default ''   NOT NULL,
    audit_entries_original_value     VarChar2(200) default ''   	,
    audit_entries_new_value          VarChar2(200) default ''   	,
    audit_entries_field_name         VarChar2(50)  default ''   NOT NULL,
    audit_entries_creation_date      Date                       NOT NULL,
    audit_entries_lastUpdate_date    Date                       NOT NULL,
    useracc_id                       Integer       default      NULL
);

ALTER TABLE  audit_entries ADD (
    CONSTRAINT  audit_entries_PK 
    PRIMARY KEY ( audit_entries_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  biological_impurities (
    bioimpurity_creation_date Date , 
    bioimpurity_id Integer default null NOT NULL, 
    bioimpurity_lastupdate_date Date , 
    bioimpurity_tolerance Numeric(22, 8) default null NOT NULL, 
    impurity_id Integer default null NOT NULL, 
    units_id Integer default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  biological_impurities ADD (
    CONSTRAINT  biological_impurities_PK 
    PRIMARY KEY ( bioimpurity_id ) 
    USING INDEX 
); 
CREATE INDEX  biological_impurities_IDX1 
    ON  biological_impurities (impurity_id); 

CREATE INDEX  biological_impurities_IDX2 
    ON  biological_impurities (units_id);
    
---------------------------------------------------------------------
CREATE TABLE  registry_entries (
    regentry_code  Varchar2(30) default '' NOT NULL, 
    regentry_comment_eng  Varchar2(1000) default NULL , 
    regentry_comment_fr  Varchar2(1000) default NULL , 
    regentry_creation_date Date NOT NULL, 
    regentry_id Integer default null NOT NULL, 
    regentry_lastupdate_date Date NOT NULL, 
    regentry_name_eng  Varchar2(100) default NULL , 
    regentry_name_fr  Varchar2(100) default NULL , 
    ref_id Integer default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  registry_entries ADD (
    CONSTRAINT  registry_entries_PK 
    PRIMARY KEY ( regentry_id ) 
    USING INDEX 
); 
ALTER TABLE  registry_entries
    ADD CONSTRAINT  registry_entries_UK1 
    UNIQUE (regentry_code);
ALTER TABLE  registry_entries
    ADD CONSTRAINT  registry_entries_UK2 
    UNIQUE (regentry_name_eng);
ALTER TABLE  registry_entries
    ADD CONSTRAINT  registry_entries_UK3 
    UNIQUE (regentry_name_fr);
CREATE INDEX  registry_entries_IDX1 
    ON  registry_entries (ref_id); 

---------------------------------------------------------------------
CREATE TABLE  chem_classes (
    chemcl_code  Varchar2(20) default null NOT NULL, 
    chemcl_comment_eng  Varchar2(1000) default NULL , 
    chemcl_comment_fr  Varchar2(1000) default NULL , 
    chemcl_creation_date Date NOT NULL, 
    chemcl_id Integer default null NOT NULL, 
    chemcl_lastupdate_date Date NOT NULL, 
    chemcl_name_eng  Varchar2(100) default null NOT NULL, 
    chemcl_name_fr  Varchar2(100) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  chem_classes ADD (
    CONSTRAINT  chem_classes_PK 
    PRIMARY KEY ( chemcl_id ) 
    USING INDEX 
); 
ALTER TABLE  chem_classes
    ADD CONSTRAINT  chem_classes_UK1 
    UNIQUE (chemcl_code);
ALTER TABLE  chem_classes
    ADD CONSTRAINT  chem_classes_UK2 
    UNIQUE (chemcl_name_eng);
ALTER TABLE  chem_classes
    ADD CONSTRAINT  chem_classes_UK3 
    UNIQUE (chemcl_name_fr);

---------------------------------------------------------------------
CREATE TABLE  chem_subclasses (
    chemcl_id Integer default null NOT NULL, 
    chemsub_code  Varchar2(20) default null NOT NULL, 
    chemsub_comment_eng  Varchar2(1000) default NULL , 
    chemsub_comment_fr  Varchar2(1000) default NULL , 
    chemsub_creation_date Date NOT NULL, 
    chemsub_id Integer default null NOT NULL, 
    chemsub_lastupdate_date Date NOT NULL, 
    chemsub_name_eng  Varchar2(100) default null NOT NULL, 
    chemsub_name_fr  Varchar2(100) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  chem_subclasses ADD (
    CONSTRAINT  chem_subclasses_PK 
    PRIMARY KEY ( chemsub_id ) 
    USING INDEX 
); 
ALTER TABLE  chem_subclasses
    ADD CONSTRAINT  chem_subclasses_UK1 
    UNIQUE (chemsub_code);
ALTER TABLE  chem_subclasses
    ADD CONSTRAINT  chem_subclasses_UK2 
    UNIQUE (chemsub_name_eng);
ALTER TABLE  chem_subclasses
    ADD CONSTRAINT  chem_subclasses_UK3 
    UNIQUE (chemsub_name_fr);
CREATE INDEX  chem_subclasses_IDX1 
    ON  chem_subclasses (chemcl_id); 

---------------------------------------------------------------------
CREATE TABLE  citation_specials (
    citationspec_class_name  Varchar2(100) default '' NOT NULL, 
    citationspec_creation_date Date NOT NULL, 
    citationspec_id Integer default '0' NOT NULL, 
    citationspec_lastupdate_date Date NOT NULL, 
    citationspec_name_eng  Varchar2(100) default '' NOT NULL, 
    citationspec_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  citation_specials ADD (
    CONSTRAINT  citation_specials_PK 
    PRIMARY KEY ( citationspec_id ) 
    USING INDEX 
); 
ALTER TABLE  citation_specials
    ADD CONSTRAINT  citation_specials_UK1 
    UNIQUE (citationspec_class_name);
ALTER TABLE  citation_specials
    ADD CONSTRAINT  citation_specials_UK2 
    UNIQUE (citationspec_name_eng);
ALTER TABLE  citation_specials
    ADD CONSTRAINT  citation_specials_UK3 
    UNIQUE (citationspec_name_fr);

---------------------------------------------------------------------
CREATE TABLE  citations (
    code Varchar2(20) default NULL ,
    citation_abridged_title_eng Varchar2(100) default NULL ,
    citation_abridged_title_fr Varchar2(100) default NULL ,
    citation_access_date  date default NULL , 
    citation_comment_eng  Varchar2(1000) default NULL , 
    citation_comment_fr  Varchar2(1000) default NULL , 
    citation_creation_date Date NOT NULL, 
    citation_degree  Varchar2(100) default NULL , 
    citation_edition  Varchar2(20) default NULL , 
    citation_editor  Varchar2(100) default NULL , 
    citation_id Integer default null NOT NULL, 
    citation_lastupdate_date Date NOT NULL, 
    citation_meeting_place  Varchar2(100) default NULL , 
    citation_page_numbers  Varchar2(100) default NULL , 
    citation_part  Varchar2(20) default NULL , 
    citation_place  Varchar2(100) default NULL , 
    citation_publication_title  Varchar2(200) default NULL , 
    citation_publication_title_eng  Varchar2(200) default NULL , 
    citation_publication_title_fr  Varchar2(200) default NULL , 
    citation_publisher_eng  Varchar2(100) default NULL , 
    citation_publisher_fr  Varchar2(100) default NULL , 
    citation_university  Varchar2(100) default NULL , 
    citation_url_eng  Varchar2(200) default NULL , 
    citation_url_fr  Varchar2(200) default NULL , 
    citation_volume  Varchar2(100) default NULL , 
    citation_work_author_eng  Varchar2(500) default NULL , 
    citation_work_author_fr  Varchar2(500) default NULL , 
    citation_work_date  date default NULL , 
    citation_work_title_eng  Varchar2(500) default NULL NULL, 
    citation_work_title_fr  Varchar2(500) default NULL NULL, 
    citation_work_year_string  Varchar2(100) default NULL , 
    citation_full_text_eng Varchar2(750) default NULL,
    citation_full_text_fr Varchar2(750) default NULL,
    citationspec_class_name  Varchar2(100) default NULL, 
    journal_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  citations ADD (
    CONSTRAINT  citations_PK 
    PRIMARY KEY ( citation_id ) 
    USING INDEX 
); 
CREATE INDEX  citations_IDX1 
    ON  citations (citationspec_class_name);
CREATE INDEX  citations_IDX2 
    ON  citations (journal_id);
CREATE INDEX  citations_IDX3 
    ON  citations (code);
    
---------------------------------------------------------------------
CREATE TABLE  coded_risks (
    codedrisk_id Integer NOT NULL,
	meddrapref_id Integer default null,
	risktype_id Integer default null,
	codedrisk_creation_date Date NOT NULL, 
    codedrisk_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL 
);
ALTER TABLE  coded_risks ADD (
    CONSTRAINT  coded_risks_PK 
    PRIMARY KEY (codedrisk_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE context_sensitive_citations (
    consencit_id Integer NOT NULL,
    context_object_id Integer NOT NULL,
    context_object_name varchar2(100) NOT NULL,
    primary_object_id Integer Not NULL,
    primary_object_name varchar2(100) NOT NULL,
    consencit_creation_date Date NOT NULL, 
    consencit_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  context_sensitive_citations ADD (
    CONSTRAINT  context_senstve_citations_PK 
    PRIMARY KEY ( consencit_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  countries (
    country_code  Varchar2(2) default null NOT NULL, 
    country_creation_date Date NOT NULL, 
    country_id Integer default null NOT NULL, 
    country_lastupdate_date Date NOT NULL, 
    country_name_eng  Varchar2(100) default '' NOT NULL, 
    country_name_fr  Varchar2(100) default '' NOT NULL, 
    risk_id Integer default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  countries ADD (
    CONSTRAINT  countries_PK 
    PRIMARY KEY ( country_id ) 
    USING INDEX 
); 
ALTER TABLE  countries
    ADD CONSTRAINT  countries_UK1 
    UNIQUE (country_code);
ALTER TABLE  countries
    ADD CONSTRAINT  countries_UK2 
    UNIQUE (country_name_eng);
ALTER TABLE  countries
    ADD CONSTRAINT  countries_UK3 
    UNIQUE (country_name_fr);
CREATE INDEX  countries_IDX1 
    ON  countries (risk_id)
     


---------------------------------------------------------------------
CREATE TABLE dietary_allowances (
    dietallo_id              Integer      DEFAULT NULL NOT NULL,
    dietallo_min_threshold   Number(12,4) DEFAULT NULL NOT NULL,
    subpop_id                Integer      DEFAULT NULL NOT NULL,
    recdietallo_id           Integer      DEFAULT NULL NOT NULL,
    dietallo_creation_date   Date                      NOT NULL, 
    dietallo_lastupdate_date Date                      NOT NULL, 
    useracc_id               Integer      DEFAULT NULL NOT NULL
);

ALTER TABLE  dietary_allowances ADD (
  CONSTRAINT  dietary_allowances_PK
  PRIMARY KEY (dietallo_id) 
  USING INDEX 
);
        
---------------------------------------------------------------------
CREATE TABLE  dosage_form_groups (
    dosefrmgrp_code  Varchar2(20) default null NOT NULL, 
    dosefrmgrp_creation_date Date , 
    dosefrmgrp_id Integer default null NOT NULL, 
    dosefrmgrp_lastupdate_date Date , 
    dosefrmgrp_name_eng  Varchar2(100) default null NOT NULL, 
    dosefrmgrp_name_fr  Varchar2(100) default null NOT NULL, 
    dosefrmgrp_upper_tolerance Numeric(22, 8) default NULL , 
    units_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  dosage_form_groups ADD (
    CONSTRAINT  dosage_form_groups_PK 
    PRIMARY KEY ( dosefrmgrp_id ) 
    USING INDEX 
); 
ALTER TABLE  dosage_form_groups
    ADD CONSTRAINT  dosage_form_groups_UK1 
    UNIQUE (dosefrmgrp_code);
ALTER TABLE  dosage_form_groups
    ADD CONSTRAINT  dosage_form_groups_UK2 
    UNIQUE (dosefrmgrp_name_eng);
ALTER TABLE  dosage_form_groups
    ADD CONSTRAINT  dosage_form_groups_UK3 
    UNIQUE (dosefrmgrp_name_fr);
CREATE INDEX  dosage_form_groups_IDX1 
    ON  dosage_form_groups (units_id) 

---------------------------------------------------------------------
CREATE TABLE  dosage_forms (
    dosefrm_code  Varchar2(20) default null NOT NULL, 
    dosefrm_creation_date Date NOT NULL, 
    dosefrm_desc_eng  Varchar2(1000) default NULL , 
    dosefrm_desc_fr  Varchar2(1000) default NULL , 
    dosefrm_id Integer default null NOT NULL, 
    dosefrm_lastupdate_date Date NOT NULL, 
    dosefrm_name_eng  Varchar2(100) default '' NOT NULL, 
    dosefrm_name_fr  Varchar2(100) default '' NOT NULL, 
    dosefrm_upper_tolerance Numeric(22, 8) default NULL, 
    dosefrm_assessment_required varchar2(1) default NULL,
    units_id Integer default NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  dosage_forms ADD (
    CONSTRAINT  dosage_forms_PK 
    PRIMARY KEY ( dosefrm_id ) 
    USING INDEX 
); 
ALTER TABLE  dosage_forms
    ADD CONSTRAINT  dosage_forms_UK1 
    UNIQUE (dosefrm_code);
ALTER TABLE  dosage_forms
    ADD CONSTRAINT  dosage_forms_UK2 
    UNIQUE (dosefrm_name_eng);
ALTER TABLE  dosage_forms
    ADD CONSTRAINT  dosage_forms_UK3 
    UNIQUE (dosefrm_name_fr);
CREATE INDEX  dosage_forms_IDX1 
    ON  dosage_forms (dosefrmgrp_id) 
CREATE INDEX  dosage_forms_IDX2 
    ON  dosage_forms (units_id)

---------------------------------------------------------------------    
CREATE TABLE  dosage_ranges (
    dosrge_id Integer NOT NULL,
    units_id Integer default null, 
    dosrge_maximum_dosage Number default NULL, 
    dosrge_minimum_dosage Number default NULL, 
    dosrge_creation_date Date NOT NULL, 
    dosrge_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL
);
ALTER TABLE  dosage_ranges ADD (
    CONSTRAINT  dosage_ranges_PK 
    PRIMARY KEY ( dosrge_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  evidence_types (
    evidtype_code  Varchar2(50) default null NOT NULL, 
    evidtype_creation_date Date NOT NULL, 
    evidtype_id Integer default null NOT NULL, 
    evidtype_lastupdate_date Date NOT NULL, 
    evidtype_name_eng  Varchar2(100) default '' NOT NULL, 
    evidtype_name_fr  Varchar2(100) default '' NOT NULL, 
    evidtype_desc_eng  Varchar2(1000) default NULL, 
    evidtype_desc_fr  Varchar2(1000) default NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  evidence_types ADD (
    CONSTRAINT  evidence_types_PK 
    PRIMARY KEY ( evidtype_id ) 
    USING INDEX 
); 
ALTER TABLE  evidence_types
    ADD CONSTRAINT  evidence_types_UK1 
    UNIQUE (evidtype_code);
ALTER TABLE  evidence_types
    ADD CONSTRAINT  evidence_types_UK2 
    UNIQUE (evidtype_name_eng);
ALTER TABLE  evidence_types
    ADD CONSTRAINT  evidence_types_UK3 
    UNIQUE (evidtype_name_fr);
    
---------------------------------------------------------------------
CREATE TABLE  families (
    family_creation_date Date NOT NULL, 
    family_id Integer default null NOT NULL, 
    family_lastupdate_date Date NOT NULL, 
    family_name  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  families ADD (
    CONSTRAINT  families_PK 
    PRIMARY KEY ( family_id ) 
    USING INDEX 
); 
ALTER TABLE  families
    ADD CONSTRAINT  families_UK1 
    UNIQUE (family_name);

---------------------------------------------------------------------
CREATE TABLE  genera (
    family_id Integer default null NOT NULL, 
    genus_creation_date Date NOT NULL, 
    genus_id Integer default null NOT NULL, 
    genus_lastupdate_date Date NOT NULL, 
    genus_name  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  genera ADD (
    CONSTRAINT  genera_PK 
    PRIMARY KEY ( genus_id ) 
    USING INDEX 
); 
ALTER TABLE  genera
    ADD CONSTRAINT  genera_UK1 
    UNIQUE (genus_name);

CREATE INDEX  genera_IDX1 
    ON  genera (family_id) 
    
---------------------------------------------------------------------
CREATE TABLE generic_texts (
  gentext_id Integer default null NOT NULL,	
  gentext_text_eng Varchar2(2500) default NULL,
  gentext_text_fr Varchar2(2500) default NULL,
  gentext_class_name Varchar2(100) default '' NOT NULL, 
  gentext_publish_date Date default NULL,
  gentext_creation_date Date NOT NULL, 
  gentext_lastupdate_date Date NOT NULL, 
  useracc_id Integer NOT NULL  
);
ALTER TABLE generic_texts ADD (
   	CONSTRAINT generic_texts_PK
    	PRIMARY KEY (gentext_id) 
    	USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  geographic_areas (
    geogarea_code  Varchar2(20) default null NOT NULL, 
    geogarea_comment_eng  Varchar2(1000) default NULL , 
    geogarea_comment_fr  Varchar2(1000) default NULL , 
    geogarea_creation_date Date NOT NULL, 
    geogarea_id Integer default null NOT NULL, 
    geogarea_lastupdate_date Date NOT NULL, 
    geogarea_name_eng  Varchar2(100) default '' NOT NULL, 
    geogarea_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  geographic_areas ADD (
    CONSTRAINT  geographic_areas_PK 
    PRIMARY KEY ( geogarea_id ) 
    USING INDEX 
); 
ALTER TABLE  geographic_areas
    ADD CONSTRAINT  geographic_areas_UK1 
    UNIQUE (geogarea_code);
ALTER TABLE  geographic_areas
    ADD CONSTRAINT  geographic_areas_UK2 
    UNIQUE (geogarea_name_eng);
ALTER TABLE  geographic_areas
    ADD CONSTRAINT  geographic_areas_UK3 
    UNIQUE (geogarea_name_fr);

---------------------------------------------------------------------
CREATE TABLE  homeopathic_dilutions (
    homeodilu_id                  Integer       default '0'  NOT NULL,
    homeodilu_code                varchar2(20)  default null NOT NULL,
    homeodilu_name_eng            varchar2(200) default null,
    homeodilu_name_fr             varchar2(200) default null,
    homeodilu_comment_eng         varchar2(500) default null,
    homeodilu_comment_fr          varchar2(500) default null,
    homeodilu_creation_date       Date          NOT NULL, 
    homeodilu_lastupdate_date     Date          NOT NULL, 
    useracc_id                    Integer       default NULL 
);

ALTER TABLE  homeopathic_dilutions ADD (
    CONSTRAINT  homeopathic_dilutions_PK 
    PRIMARY KEY ( homeodilu_id ) 
    USING INDEX 
); 

ALTER TABLE  homeopathic_dilutions 
    ADD CONSTRAINT  homeopathic_dilutions_UK1 
    UNIQUE (homeodilu_code );

---------------------------------------------------------------------
CREATE TABLE  homeopathic_generic_texts (
    homeogentext_id                Integer       default '0'  NOT NULL,
    homeogentext_name_eng           varchar2(500) default null NOT NULL,
    homeogentext_name_fr            varchar2(500) default null NOT NULL,
    homeogentext_creation_date      Date          NOT NULL, 
    homeogentext_lastupdate_date    Date          NOT NULL, 
    useracc_id                 Integer       default NULL 
);

ALTER TABLE  homeopathic_generic_texts ADD (
    CONSTRAINT  homeopathic_generic_texts_PK 
    PRIMARY KEY ( homeogentext_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  homeopathic_formulas (
    homeoform_id                 Integer       default '0'  NOT NULL, 
    homeoform_atf_required       Varchar2(1)   default null NOT NULL, 
    homeoform_atf_req_in_proc_mi Varchar2(1)   default null NOT NULL, 
    homeodilu_id                 Integer       default '0', 
    homeoform_known_as_synthetic Varchar2(1)   default null NOT NULL, 
    homeoform_notes              varchar2(500) default null,
    homeoform_source_material    varchar2(200) default null,
    ingredrole_id                Integer       default NULL NOT NULL,
    ref_id                       Integer       default NULL,
    homeoform_creation_date      Date          NOT NULL, 
    homeoform_lastupdate_date    Date          NOT NULL, 
    useracc_id                   Integer       default NULL 
);

ALTER TABLE  homeopathic_formulas ADD (
    CONSTRAINT  homeopathic_formulas_PK 
    PRIMARY KEY ( homeoform_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  homeopathic_methofpreps (
    homeomop_id                  Integer       default '0'  NOT NULL, 
    homeoform_id                 Integer       default NULL NOT NULL,
    homeomop_minimum             Integer       default NULL,
    homeomop_maximum             Integer       default NULL,
    homeopreptype_id             Integer       default NULL,
    homeomop_creation_date       Date          NOT NULL, 
    homeomop_lastupdate_date     Date          NOT NULL, 
    useracc_id                   Integer       default NULL 
);

ALTER TABLE  homeopathic_methofpreps ADD (
    CONSTRAINT  homeopathic_methofpreps_PK 
    PRIMARY KEY ( homeomop_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  homeopathic_preparation_Types (
    homeopreptype_id                  Integer       default '0'  NOT NULL,
    homeopreptype_code                varchar2(50)  default null NOT NULL,
    homeopreptype_name_eng            varchar2(200) default null,
    homeopreptype_name_fr             varchar2(200) default null,
    homeopreptype_comment_eng         varchar2(500) default null,
    homeopreptype_comment_fr          varchar2(500) default null,
    homeopreptype_creation_date       Date          NOT NULL, 
    homeopreptype_lastupdate_date     Date          NOT NULL, 
    useracc_id                    Integer       default NULL 
);

ALTER TABLE  homeopathic_preparation_Types ADD (
    CONSTRAINT  homeopathic_prep_Types_PK 
    PRIMARY KEY ( homeopreptype_id ) 
    USING INDEX 
); 

ALTER TABLE  homeopathic_preparation_Types 
    ADD CONSTRAINT  homeopathic_prep_Types_UK1 
    UNIQUE (homeopreptype_code );

---------------------------------------------------------------------
CREATE TABLE  ich_classes (
    ichclass_code  Varchar2(20) default null NOT NULL, 
    ichclass_creation_date Date , 
    ichclass_desc_eng  Varchar2(1000) default NULL , 
    ichclass_desc_fr  Varchar2(1000) default NULL , 
    ichclass_id Integer default null NOT NULL, 
    ichclass_lastupdate_date Date , 
    ichclass_name_eng  Varchar2(100) default null NOT NULL, 
    ichclass_name_fr  Varchar2(100) default null NOT NULL, 
    ichclass_residual_limit Numeric(22, 8) default NULL , 
    units_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  ich_classes ADD (
    CONSTRAINT  ich_classes_PK 
    PRIMARY KEY ( ichclass_id ) 
    USING INDEX 
); 
ALTER TABLE  ich_classes
    ADD CONSTRAINT  ich_classes_UK1 
    UNIQUE (ichclass_code);
ALTER TABLE  ich_classes
    ADD CONSTRAINT  ich_classes_UK2 
    UNIQUE (ichclass_name_eng);
ALTER TABLE  ich_classes
    ADD CONSTRAINT  ich_classes_UK3 
    UNIQUE (ichclass_name_fr);
CREATE INDEX  ich_classes_IDX1 
    ON  ich_classes (units_id) 

---------------------------------------------------------------------
CREATE TABLE  impurities (
    impurity_comment_eng  Varchar2(1000) default NULL , 
    impurity_comment_fr  Varchar2(1000) default NULL , 
    impurity_creation_date Date , 
    impurity_desc_eng  Varchar2(400) default NULL , 
    impurity_desc_fr  Varchar2(400) default NULL , 
    impurity_id Integer default null NOT NULL, 
    impurity_lastupdate_date Date , 
    impurity_name_eng  Varchar2(100) default null NOT NULL, 
    impurity_name_fr  Varchar2(100) default null NOT NULL, 
    impurityspec_class_name  Varchar2(100) default null NOT NULL, 
    restrict_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  impurities ADD (
    CONSTRAINT  impurities_PK 
    PRIMARY KEY ( impurity_id ) 
    USING INDEX 
); 
ALTER TABLE  impurities
    ADD CONSTRAINT  impurities_UK1 
    UNIQUE (impurity_name_eng);
ALTER TABLE  impurities
    ADD CONSTRAINT  impurities_UK2 
    UNIQUE (impurity_name_fr);
CREATE INDEX  impurities_IDX1 
    ON  impurities (impurityspec_class_name) 
CREATE INDEX  impurities_IDX2 
    ON  impurities (restrict_id) 
    
---------------------------------------------------------------------
CREATE TABLE  impurity_specials (
    impurityspec_class_name  Varchar2(100) default null NOT NULL, 
    impurityspec_id Integer default null NOT NULL, 
    impurityspec_name_eng  Varchar2(100) default null NOT NULL, 
    impurityspec_name_fr  Varchar2(100) default null NOT NULL
);
ALTER TABLE  impurity_specials ADD (
    CONSTRAINT  impurity_specials_PK 
    PRIMARY KEY ( impurityspec_id ) 
    USING INDEX 
); 
ALTER TABLE  impurity_specials
    ADD CONSTRAINT  impurity_specials_UK1 
    UNIQUE (impurityspec_class_name);
ALTER TABLE  impurity_specials
    ADD CONSTRAINT  impurity_specials_UK2 
    UNIQUE (impurityspec_name_eng);
ALTER TABLE  impurity_specials
    ADD CONSTRAINT  impurity_specials_UK3 
    UNIQUE (impurityspec_name_fr);

---------------------------------------------------------------------
CREATE TABLE  indications (
    indication_id Integer NOT NULL,
    meddrapref_id number default NULL, 
    indictype_id number default NULL, 
    indication_creation_date Date NOT NULL, 
    indication_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL 
);
ALTER TABLE  indications ADD (
    CONSTRAINT  indications_PK 
    PRIMARY KEY ( indication_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  indication_types ( 
    indictype_id Integer NOT NULL,
    indictype_code varchar2(50) NOT NULL,
    indictype_name_eng varchar2(100) default NULL, 
	indictype_name_fr  varchar2(100) default NULL,
    indictype_desc_eng varchar2(1000) default NULL, 
	indictype_desc_fr  varchar2(1000) default NULL,
    indictype_creation_date Date NOT NULL, 
   	indictype_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL 
);
ALTER TABLE  indication_types ADD (
    CONSTRAINT  indication_types_PK 
    PRIMARY KEY ( indictype_id ) 
    USING INDEX 
); 
ALTER TABLE  indication_types
    ADD CONSTRAINT  indication_types_UK1 
    UNIQUE (indictype_code);

---------------------------------------------------------------------
CREATE TABLE  ingredient_categories (
    ingredcat_code  Varchar2(20) default '' NOT NULL, 
    ingredcat_id Integer default '0' NOT NULL, 
    ingredcat_name_eng  Varchar2(100) default '' NOT NULL, 
    ingredcat_name_fr  Varchar2(100) default '' NOT NULL, 
    ingredspec_id Integer default '0' NOT NULL,
    ingredcat_creation_date Date NOT NULL, 
    ingredcat_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL 
);
ALTER TABLE  ingredient_categories ADD (
    CONSTRAINT  ingredient_categories_PK 
    PRIMARY KEY ( ingredcat_id ) 
    USING INDEX 
); 
ALTER TABLE  ingredient_categories
    ADD CONSTRAINT  ingredient_categories_UK1 
    UNIQUE (ingredcat_code);
ALTER TABLE  ingredient_categories
    ADD CONSTRAINT  ingredient_categories_UK2 
    UNIQUE (ingredcat_name_eng);
ALTER TABLE  ingredient_categories
    ADD CONSTRAINT  ingredient_categories_UK3 
    UNIQUE (ingredcat_name_fr);
CREATE INDEX  ingredient_categories_IDX1 
    ON  ingredient_categories (ingredspec_id) 
    
---------------------------------------------------------------------
CREATE TABLE  ingredient_roles (
    ingred_id Integer default null NOT NULL, 
    ingredrole_creation_date Date , 
    ingredrole_id Integer default null NOT NULL, 
    ingredrole_lastupdate_date Date , 
    rolespec_class_name  Varchar2(100) default '' NOT NULL, 
    rationale_id Integer default NULL,
    restrict_id Integer default NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  ingredient_roles ADD (
    CONSTRAINT  ingredient_roles_PK 
    PRIMARY KEY ( ingredrole_id ) 
    USING INDEX 
); 
ALTER TABLE  ingredient_roles
    ADD CONSTRAINT  ingredient_roles_UK1 
    UNIQUE (ingred_id,rolespec_class_name);
CREATE INDEX  ingredient_roles_IDX1 
    ON  ingredient_roles (ingred_id) 

---------------------------------------------------------------------
CREATE TABLE  ingredient_sources (
    ingredsrc_id Integer default '0' NOT NULL, 
    ingredsrc_name_eng  Varchar2(100) default '' NOT NULL, 
    ingredsrc_name_fr  Varchar2(100) default '' NOT NULL
);
ALTER TABLE  ingredient_sources ADD (
    CONSTRAINT  ingredient_sources_PK 
    PRIMARY KEY ( ingredsrc_id ) 
    USING INDEX 
); 
ALTER TABLE  ingredient_sources
    ADD CONSTRAINT  ingredient_sources_UK1 
    UNIQUE (ingredsrc_name_eng);
ALTER TABLE  ingredient_sources
    ADD CONSTRAINT  ingredient_sources_UK2 
    UNIQUE (ingredsrc_name_fr);

---------------------------------------------------------------------
CREATE TABLE  ingredient_specials (
    ingredspec_class_name  Varchar2(100) default '' NOT NULL, 
    ingredspec_id Integer default '0' NOT NULL, 
    ingredspec_name_eng  Varchar2(100) default '' NOT NULL, 
    ingredspec_name_fr  Varchar2(100) default '' NOT NULL,
    ingredspec_creation_date Date NOT NULL, 
    ingredspec_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL 
);
ALTER TABLE  ingredient_specials ADD (
    CONSTRAINT  ingredient_specials_PK 
    PRIMARY KEY ( ingredspec_id ) 
    USING INDEX 
); 
ALTER TABLE  ingredient_specials
    ADD CONSTRAINT  ingredient_specials_UK1 
    UNIQUE (ingredspec_class_name);
ALTER TABLE  ingredient_specials
    ADD CONSTRAINT  ingredient_specials_UK2 
    UNIQUE (ingredspec_name_eng);
ALTER TABLE  ingredient_specials
    ADD CONSTRAINT  ingredient_specials_UK3 
    UNIQUE (ingredspec_name_fr);
    
---------------------------------------------------------------------
CREATE TABLE  ingredients (
    attachment_id Integer default NULL , 
    chemsub_id Integer default NULL , 
    ingred_add_detail_eng varchar2(1000),
    ingred_add_detail_fr varchar2(1000),
    ingred_comment_eng  Varchar2(1000) default NULL , 
    ingred_comment_fr  Varchar2(1000) default NULL , 
    ingred_creation_date Date NOT NULL, 
    ingred_desc_eng  Varchar2(1000) default NULL , 
    ingred_desc_fr  Varchar2(1000) default NULL , 
    ingred_group_flag  Varchar2(1) default NULL , 
    ingred_id Integer default null NOT NULL, 
    ingred_lastupdate_date Date NOT NULL, 
    ingred_lower_tolerance Numeric(22, 8) default NULL , 
    ingred_authorized_name_eng  Varchar2(200) default NULL , 
    ingred_authorized_name_fr  Varchar2(200) default NULL , 
    ingred_smiles_notation  Varchar2(1000) default NULL , 
    ingred_upper_tolerance Numeric(22, 8) default NULL , 
    ingredcat_id Integer default '0' NOT NULL, 
    ingredspec_class_name  Varchar2(100) default '' NOT NULL, 
    ingredsrc_id Integer default NULL , 
    org_grp_id Integer default NULL, 
    ref_id Integer default NULL , 
    regentry_id Integer default NULL , 
    restrict_id Integer default NULL , 
    organism_id Integer default NULL , 
    orgpart_id Integer default NULL , 
    preptype_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  ingredients ADD (
    CONSTRAINT  ingredients_PK 
    PRIMARY KEY ( ingred_id ) 
    USING INDEX 
); 
ALTER TABLE  ingredients
    ADD CONSTRAINT  ingredients_UK1 
    UNIQUE (ingred_authorized_name_eng);
ALTER TABLE  ingredients
    ADD CONSTRAINT  ingredients_UK2 
    UNIQUE (ingred_authorized_name_fr);
CREATE INDEX  ingredients_IDX1 
    ON  ingredients (attachment_id) 
CREATE INDEX  ingredients_IDX2 
    ON  ingredients (chemsub_id) 
CREATE INDEX  ingredients_IDX3 
    ON  ingredients (ingredcat_id) 
CREATE INDEX  ingredients_IDX4 
    ON  ingredients (ingredspec_class_name) 
CREATE INDEX  ingredients_IDX5 
    ON  ingredients (ingredsrc_id) 
CREATE INDEX  ingredients_IDX6 
    ON  ingredients (ref_id) 
CREATE INDEX  ingredients_IDX7 
    ON  ingredients (restrict_id) 
CREATE INDEX  ingredients_IDX8 
    ON  ingredients (organism_id) 
CREATE INDEX  ingredients_IDX9 
    ON  ingredients (orgpart_id) 
CREATE INDEX  ingredientsIDX10 
    ON  ingredients (preptype_id) 
CREATE INDEX  ingredients_IDX11 
    ON  ingredients (regentry_id) 

---------------------------------------------------------------------
CREATE TABLE  manufact_processes (
    attachment_id Integer default NULL , 
    citation_id Integer default NULL , 
    ingredrole_id Integer default null NOT NULL, 
    manproc_creation_date Date , 
    manproc_desc_eng  Varchar2(400) default NULL , 
    manproc_desc_fr  Varchar2(400) default NULL , 
    manproc_id Integer default null NOT NULL, 
    manproc_lastupdate_date Date , 
    manproc_name_eng  Varchar2(100) default null NOT NULL, 
    manproc_name_fr  Varchar2(100) default null NOT NULL, 
    manproc_synthesis_flag  Varchar2(1) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  manufact_processes ADD (
    CONSTRAINT  manufact_processes_PK 
    PRIMARY KEY ( manproc_id ) 
    USING INDEX 
); 
CREATE INDEX  manufact_processes_IDX1 
    ON  manufact_processes (attachment_id) 
CREATE INDEX  manufact_processes_IDX2 
    ON  manufact_processes (citation_id) 
CREATE INDEX  manufact_processes_IDX3 
    ON  manufact_processes (ingredrole_id) 
CREATE INDEX  manufact_processes_IDX4 
    ON  manufact_processes (manproc_id) 
CREATE INDEX  manufact_processes_IDX5 
    ON  manufact_processes (manproc_name_eng) 
CREATE INDEX  manufact_processes_IDX6 
    ON  manufact_processes (manproc_name_fr) 

---------------------------------------------------------------------
CREATE TABLE  meddra_hlgts (
    hlgt_id Integer default null NOT NULL, 
    hlgt_name_eng  Varchar2(100) default null NOT NULL, 
    hlgt_name_fr  Varchar2(100) default NULL 
);
ALTER TABLE  meddra_hlgts ADD (
    CONSTRAINT  meddra_hlgts_PK 
    PRIMARY KEY ( hlgt_id ) 
    USING INDEX 
); 
ALTER TABLE  meddra_hlgts
    ADD CONSTRAINT  meddra_hlgts_UK1 
    UNIQUE (hlgt_name_eng);
ALTER TABLE  meddra_hlgts
    ADD CONSTRAINT  meddra_hlgts_UK2 
    UNIQUE (hlgt_name_fr);

---------------------------------------------------------------------
CREATE TABLE  meddra_hlts (
    hlt_id Integer default null NOT NULL, 
    hlt_name_eng  Varchar2(100) default null NOT NULL, 
    hlt_name_fr  Varchar2(100) default NULL 
);
ALTER TABLE  meddra_hlts ADD (
    CONSTRAINT  meddra_hlts_PK 
    PRIMARY KEY ( hlt_id ) 
    USING INDEX 
); 
ALTER TABLE  meddra_hlts
    ADD CONSTRAINT  meddra_hlts_UK1 
    UNIQUE (hlt_name_eng);
ALTER TABLE  meddra_hlts
    ADD CONSTRAINT  meddra_hlts_UK2 
    UNIQUE (hlt_name_fr);

---------------------------------------------------------------------
CREATE TABLE  meddra_llts (
    llt_abbrev  Varchar2(50) default NULL , 
    llt_id Integer default null NOT NULL, 
    llt_name_eng  Varchar2(200) default null NOT NULL, 
    llt_name_fr  Varchar2(200) default NULL , 
    llt_valid  Varchar2(1) default null NOT NULL, 
    pt_id Integer default null NOT NULL
);
ALTER TABLE  meddra_llts ADD (
    CONSTRAINT  meddra_llts_PK 
    PRIMARY KEY ( llt_id ) 
    USING INDEX 
); 
ALTER TABLE  meddra_llts
    ADD CONSTRAINT  meddra_llts_UK1 
    UNIQUE (llt_name_eng);
CREATE INDEX  meddra_llts_IDX1 
    ON  meddra_llts (pt_id) 

---------------------------------------------------------------------
CREATE TABLE meddra_pref_terms (
   	meddrapref_id Integer NOT NULL,
	meddrapref_code Integer NOT NULL,	
	meddrapref_name_eng  Varchar2(100) default NULL , 
	meddrapref_name_fr  Varchar2(100) default NULL ,
	meddrapref_creation_date Date NOT NULL, 
	meddrapref_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  meddra_pref_terms ADD (
  CONSTRAINT  meddra_pref_terms_PK
  PRIMARY KEY (meddrapref_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  meddra_pts (
    pt_id Integer default null NOT NULL, 
    pt_name_eng  Varchar2(100) default null NOT NULL, 
    pt_name_fr  Varchar2(100) default NULL 
);
ALTER TABLE  meddra_pts ADD (
    CONSTRAINT  meddra_pts_PK 
    PRIMARY KEY ( pt_id ) 
    USING INDEX 
); 
ALTER TABLE  meddra_pts
    ADD CONSTRAINT  meddra_pts_UK1 
    UNIQUE (pt_name_eng);

---------------------------------------------------------------------
CREATE TABLE  meddra_socs (
    soc_abbrev  Varchar2(50) default null NOT NULL, 
    soc_id Integer default null NOT NULL, 
    soc_name_eng  Varchar2(100) default null NOT NULL, 
    soc_name_fr  Varchar2(100) default NULL , 
    soc_order Integer default NULL 
);
ALTER TABLE  meddra_socs ADD (
    CONSTRAINT  meddra_socs_PK 
    PRIMARY KEY ( soc_id ) 
    USING INDEX 
); 
ALTER TABLE  meddra_socs
    ADD CONSTRAINT  meddra_socs_UK1 
    UNIQUE (soc_name_eng);
ALTER TABLE  meddra_socs
    ADD CONSTRAINT  meddra_socs_UK2 
    UNIQUE (soc_name_fr);

---------------------------------------------------------------------
CREATE TABLE  monograph_directions_for_uses (
    monodfu_id Integer NOT NULL,
    code varchar2(20) default NULL,
    mono_id Integer default NULL,
    monodfu_name_eng varchar2(1000) default NULL,
    monodfu_name_fr varchar2(1000) default NULL,
    monodfu_creation_date Date NOT NULL, 
    monodfu_lastupdate_date Date NOT NULL, 
    mono_source_ingred_id Integer default NULL,
    monodosecomb_id Integer default NULL,
    monoprep_id Integer default NULL,
    subpop_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_directions_for_uses ADD (
  CONSTRAINT  monograph_directs_for_uses_PK
  PRIMARY KEY (monodfu_id) 
  USING INDEX 
);
CREATE INDEX  monograph_dirctns_fr_uses_IDX1 
    ON  monograph_directions_for_uses (code);

---------------------------------------------------------------------
-- MonographDoseCombinations 
CREATE TABLE monograph_dosecombs (
    monodosecomb_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monodosecomb_visible varchar2(1) default NULL,
    monodosecomb_creation_date Date NOT NULL, 
    monodosecomb_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_dosecombs ADD (
  CONSTRAINT  monograph_dosecombs_PK
  PRIMARY KEY (monodosecomb_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  monograph_doses (
    monodose_id Integer NOT NULL,
    code varchar2(20) NOT NULL,
    monodose_creation_date Date NOT NULL, 
    monodose_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL,
    monodose_add_details_eng varchar2(1000) default NULL, 
    monodose_add_details_fr varchar2(1000) default NULL, 
    monodose_maximum_dose Number(12,4) default NULL,
    monodose_minimum_dose Number(12,4) default NULL,
    monodose_maximum_frequency Integer default NULL,
    monodose_minimum_frequency Integer default NULL,
    monodose_frequency_units_id Integer default NULL,
    resttype_id Integer default NULL,
    subingred_id Integer default NULL,
    units_id Integer default NULL,
    mono_source_ingred_id Integer default NULL,
    monoprep_id Integer default NULL,
    subpop_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    monodose_visible varchar2(1) default NULL,
    dosefrm_assessment_required varchar2(1) default NULL
);
ALTER TABLE  monograph_doses ADD (
  CONSTRAINT  monograph_doses_PK
  PRIMARY KEY (monodose_id) 
  USING INDEX 
);
CREATE INDEX  monograph_doses_IDX1 
    ON  monograph_doses (code);

---------------------------------------------------------------------
CREATE TABLE  monograph_durations (
    monodur_id Integer NOT NULL,
    code varchar2(20) NOT NULL,
    monodur_statement_eng varchar2(1000) default NULL, 
    monodur_statement_fr varchar2(1000) default NULL, 
    monodur_maximum_duration Integer default NULL,
    monodur_minimum_duration Integer default NULL,
    units_id Integer default NULL,    
    mono_source_ingred_id Integer default NULL,
    monodosecomb_id Integer default NULL,
    monoprep_id Integer default NULL,
    subpop_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,    
    monodur_creation_date Date NOT NULL, 
    monodur_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_durations ADD (
  CONSTRAINT  monograph_durations_PK
  PRIMARY KEY (monodur_id)
  USING INDEX 
);
CREATE INDEX  monograph_durations_IDX1 
    ON  monograph_durations (code);

---------------------------------------------------------------------
CREATE TABLE  monograph_entries (
    monoent_id Integer default null NOT NULL,
	mono_id Integer default NULL NOT NULL,	
    code varchar2(20) default NULL,
	ingred_id Integer default NULL NOT NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    monoentry_class_name varchar2(100) default null NOT NULL,
    source_notes_eng varchar2(1000) default NULL,
    source_notes_fr varchar2(1000) default NULL,
	monoent_creation_date Date NOT NULL,
	monoent_lastupdate_date Date NOT NULL,
	useracc_id Integer default NULL
);
ALTER TABLE  monograph_entries ADD (
   	CONSTRAINT  monograph_entries_PK 
    	PRIMARY KEY ( monoent_id) 
    	USING INDEX 
);
CREATE INDEX  monograph_entries_IDX1 
    ON  monograph_entries (code);
ALTER TABLE  monograph_entries ADD
    CONSTRAINT  monograph_entries_UK1 
    UNIQUE (mono_id,ingred_id);

---------------------------------------------------------------------
CREATE TABLE  monograph_groups (
    monogroup_creation_date Date NOT NULL, 
    monogroup_id Integer default null NOT NULL, 
    monogroup_lastupdate_date Date NOT NULL, 
    monogroup_name_eng  Varchar2(100) default '' NOT NULL, 
    monogroup_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  monograph_groups ADD (
    CONSTRAINT  monograph_groups_PK 
    PRIMARY KEY ( monogroup_id ) 
    USING INDEX 
); 
ALTER TABLE  monograph_groups
    ADD CONSTRAINT  monograph_groups_UK1 
    UNIQUE (monogroup_name_eng);

ALTER TABLE  monograph_groups
    ADD CONSTRAINT  monograph_groups_UK2
    UNIQUE (monogroup_name_fr);
     
---------------------------------------------------------------------
CREATE TABLE monograph_phases (
    monophase_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monophase_name_eng Varchar2(100), 
    monophase_name_fr Varchar(100),
    monophase_order Integer NOT NULL, 
    monophase_creation_date Date NOT NULL, 
    monophase_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_phases ADD (
  CONSTRAINT  monograph_phases_PK
  PRIMARY KEY (monophase_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_preparations (
    monoprep_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monoprep_name_eng  Varchar2(150) default '', 
    monoprep_name_fr  Varchar2(150) default '',
    monoprep_maximum_extract_ratio Number(12,4) default NULL,
    monoprep_minimum_extract_ratio Number(12,4) default NULL,
    preptype_id Integer default NULL,
    preptypegrp_id Integer default NULL,
    monoprep_creation_date Date NOT NULL, 
    monoprep_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_preparations ADD (
  CONSTRAINT  monograph_preparations_PK
  PRIMARY KEY (monoprep_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_risk_stmts (
    monoriskstmt_id Integer NOT NULL,
    code varchar2(20) default NULL,
    synonym_id Integer default NULL,
    llt_id Integer default NULL,
    mono_source_ingred_id Integer default NULL,
    monodosecomb_id Integer default NULL,
    monoprep_id Integer default NULL,
    subpop_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    monoriskstmt_creation_date Date NOT NULL, 
	monoriskstmt_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  monograph_risk_stmts ADD (
  CONSTRAINT  monograph_risk_stmts_PK
  PRIMARY KEY (monoriskstmt_id) 
  USING INDEX 
);
CREATE INDEX  monograph_risk_stmts_IDX1 
    ON  monograph_risk_stmts (code);

---------------------------------------------------------------------
CREATE TABLE monograph_routeofadmins (
   	monoadminrt_id Integer NULL,
    code varchar2(20) default NULL,
    adminrt_id Integer default null,
    monodose_id Integer default NULL,
    monodep_id Integer default null,
	monoadminrt_creation_date Date NOT NULL, 
	monoadminrt_lastupdate_date Date NOT NULL, 
    dosefrm_notes_eng varchar2(500) default NULL,
    dosefrm_notes_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
	useracc_id Integer NOT NULL
);
ALTER TABLE  monograph_routeofadmins ADD (
  CONSTRAINT  monograph_routeofadmins_PK
  PRIMARY KEY (monoadminrt_id) 
  USING INDEX 
);
CREATE INDEX  monograph_routeofadmins_IDX1 
    ON  monograph_routeofadmins (code);

---------------------------------------------------------------------
CREATE TABLE  monograph_types (
    monotype_code  Varchar2(50) default '' NOT NULL, 
    monotype_creation_date Date NOT NULL, 
    monotype_id Integer default null NOT NULL, 
    monotype_lastupdate_date Date NOT NULL, 
    monotype_name_eng  Varchar2(100) default '' NOT NULL, 
    monotype_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  monograph_types ADD (
    CONSTRAINT  monograph_types_PK
    PRIMARY KEY ( monotype_id ) 
    USING INDEX 
); 
ALTER TABLE  monograph_types
    ADD CONSTRAINT  monograph_types_UK1 
    UNIQUE (monotype_code);
ALTER TABLE  monograph_types
    ADD CONSTRAINT  monograph_types_UK2 
    UNIQUE (monotype_name_eng);
ALTER TABLE  monograph_types
    ADD CONSTRAINT  monograph_types_UK3 
    UNIQUE (monotype_name_fr);
    
---------------------------------------------------------------------
CREATE TABLE mono_source_ingredients (
	mono_source_ingred_id Integer NOT NULL,
	ingred_id Integer NOT NULL,
	subpop_id Integer default NULL,
	monosrc_creation_date Date NOT NULL, 
    monosrc_lastupdate_date Date NOT NULL,
    useracc_id Integer NOT NULL
);

ALTER TABLE  mono_source_ingredients ADD (
  CONSTRAINT  mono_source_ingredients_PK
  PRIMARY KEY (mono_source_ingred_id) 
 USING INDEX 
);

    
---------------------------------------------------------------------
CREATE TABLE mono_source_orgparts (	
	mono_source_orgpart_id Integer NOT NULL,
	orgpart_id Integer NOT NULL,
	monosrcorgpart_creation_date Date NOT NULL, 
	monosrcorgpart_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  mono_source_orgparts ADD (
  CONSTRAINT  mono_source_orgparts_PK
  PRIMARY KEY (mono_source_orgpart_id) 
 USING INDEX 
);
CREATE INDEX  mono_source_orgparts_IDX1 
    ON  mono_source_orgparts (orgpart_id);

---------------------------------------------------------------------
-- TODO - not being used?
--CREATE TABLE monograph_sources (
--    monosource_id Integer NOT NULL,	
--    monoent_id Integer NOT NULL,
--	ingred_id Integer default null,
--	orgpart_id Integer default null,
--	monosource_addsource  Varchar2(100) default NULL,
--	monosource_creation_date Date NOT NULL, 
--	monosource_lastupdate_date Date NOT NULL, 
--	useracc_id Integer NOT NULL
--);
--ALTER TABLE  monograph_sources ADD (
--  CONSTRAINT  monograph_source_PK
--  PRIMARY KEY (monosource_id) 
-- USING INDEX 
--);

---------------------------------------------------------------------
-- MonographSubIngredientDoseCombinations 
CREATE TABLE monograph_subing_dosecombs (
    mono_subing_dosecomb_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monosingdc_creation_date Date NOT NULL, 
    monosingdc_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);
ALTER TABLE  monograph_subing_dosecombs ADD (
  CONSTRAINT  monograph_subingdcs_PK
  PRIMARY KEY (mono_subing_dosecomb_id) 
  USING INDEX 
);

---------------------------------------------------------------------
---   MonographSubIngredientDose  
CREATE TABLE  monograph_subingdoses (
    monosubingdose_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monosubingdose_creation_date Date NOT NULL, 
    monosubingdose_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL,
    resttype_id Integer default NULL,
    monosubingdose_maximum_dose Number(12,4) default NULL,
    monosubingdose_minimum_dose Number(12,4) default NULL,
    monosubingdose_dose_units_id Integer default NULL,
    monosubingdose_maximum_freq Integer default NULL,
    monosubingdose_minimum_freq Integer default NULL,
    monosubingdose_freq_units_id Integer default NULL,
    monosubingcomb_id Integer default NULL
);
ALTER TABLE  monograph_subingdoses ADD (
  CONSTRAINT  monograph_subingdoses_PK
  PRIMARY KEY (monosubingdose_id) 
  USING INDEX 
);

---------------------------------------------------------------------
--  MonographSubIngredientCombinations
CREATE TABLE monograph_subingcombs (
    monosubingcomb_id Integer NOT NULL,
    code varchar2(20) default NULL,
    monosubingcomb_maximum_ratio Number(12,4) default NULL,
    monosubingcomb_minimum_ratio Number(12,4) default NULL,
    prim_subingred_id Integer default NULL,
    sec_subingred_id Integer default NULL,
    monosubingcomb_creation_date Date NOT NULL, 
    monosubingcomb_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
); 
ALTER TABLE  monograph_subingcombs ADD (
  CONSTRAINT  monograph_subingcombs_PK
  PRIMARY KEY (monosubingcomb_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_uses (
    monouse_id Integer NOT NULL,
    code varchar2(20) default NULL,	
    monouse_sort_order Integer default NULL,
    monouse_stmt_long_eng Varchar2(1000) default NULL,
    monouse_stmt_long_fr Varchar2(1000) default NULL,
    monouse_stmt_short_eng Varchar2(1000) default NULL,
    monouse_stmt_short_fr Varchar2(1000) default NULL,
    mono_source_ingred_id Integer default NULL,
    monodosecomb_id Integer default NULL,
    monoprep_id Integer default NULL,
    subpop_id Integer default NULL,
    usetype_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    monouse_creation_date Date NOT NULL, 
	monouse_lastupdate_date Date NOT NULL, 
	useracc_id Integer -- NOT NULL
);
ALTER TABLE  monograph_uses ADD (
  CONSTRAINT  monograph_uses_PK
  PRIMARY KEY (monouse_id) 
  USING INDEX 
);
CREATE INDEX  monograph_uses_IDX1 
    ON  monograph_uses (code);

---------------------------------------------------------------------
CREATE TABLE  monographs (
	mono_id Integer default null NOT NULL,	
    code varchar2(20) default NULL,
    monogroup_id Integer default NULL,
    monoadminrt_id Integer default NULL,
	mono_name_eng  Varchar2(100) default NULL , 
	mono_name_fr  Varchar2(100) default NULL , 
	mono_appendix_eng clob default NULL, 
    mono_appendix_fr clob default NULL, 
    mono_status varchar(5) default NULL,
    mono_type varchar(5) default NULL,
    mono_url_eng varchar(250) default NULL,
    mono_url_fr varchar(250) default NULL,
    mono_web_page_content_eng clob default NULL, 
    mono_web_page_content_fr clob default NULL, 
    monospec_class_name  Varchar2(100) default '' NOT NULL, 
	mono_publish_date Date default NULL,
	attachment_eng_id Integer default null,
	attachment_fr_id Integer default null,
	mono_creation_date Date NOT NULL, 
	mono_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  monographs ADD (
   	CONSTRAINT  mono_PK 
    	PRIMARY KEY ( mono_id) 
    	USING INDEX 
);
CREATE INDEX  monographs_IDX1 
    ON  monographs (code);





---------------------------------------------------------------------
CREATE TABLE  nhp_classifications (
    nhpclass_code  Varchar2(20) default '' NOT NULL, 
    nhpclass_creation_date Date NOT NULL, 
    nhpclass_desc_eng  Varchar2(1000) default NULL , 
    nhpclass_desc_fr  Varchar2(1000) default NULL , 
    nhpclass_id Integer default '0' NOT NULL, 
    nhpclass_lastupdate_date Date NOT NULL, 
    nhpclass_lower_tolerance Numeric(22, 8) default NULL , 
    nhpclass_manufactured_flag  Varchar2(1) default null NOT NULL, 
    nhpclass_name_eng  Varchar2(100) default '' NOT NULL, 
    nhpclass_name_fr  Varchar2(100) default '' NOT NULL, 
    nhpclass_upper_tolerance Numeric(22, 8) default NULL , 
    qualitymodel_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  nhp_classifications ADD (
    CONSTRAINT  nhp_classifications_PK 
    PRIMARY KEY ( nhpclass_id ) 
    USING INDEX 
); 
ALTER TABLE  nhp_classifications
    ADD CONSTRAINT  nhp_classifications_UK1 
    UNIQUE (nhpclass_code);
ALTER TABLE  nhp_classifications
    ADD CONSTRAINT  nhp_classifications_UK2 
    UNIQUE (nhpclass_name_eng);
ALTER TABLE  nhp_classifications
    ADD CONSTRAINT  nhp_classifications_UK3 
    UNIQUE (nhpclass_name_fr);
CREATE INDEX  nhp_classifications_IDX1 
    ON  nhp_classifications (qualitymodel_id) 
    
---------------------------------------------------------------------
CREATE TABLE  organism_part_types (
    orgparttype_code  Varchar2(20) default null NOT NULL, 
    orgparttype_creation_date Date , 
    orgparttype_desc_eng  Varchar2(1000) default NULL , 
    orgparttype_desc_fr  Varchar2(1000) default NULL , 
    orgparttype_id Integer default null NOT NULL, 
    orgparttype_lastupdate_date Date , 
    orgparttype_name_eng  Varchar2(100) default '' NOT NULL, 
    orgparttype_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  organism_part_types ADD (
    CONSTRAINT  organism_part_types_PK 
    PRIMARY KEY ( orgparttype_id ) 
    USING INDEX 
); 
ALTER TABLE  organism_part_types
    ADD CONSTRAINT  organism_part_types_UK1 
    UNIQUE (orgparttype_code);
    
ALTER TABLE  organism_part_types
    ADD CONSTRAINT  organism_part_types_UK2 
    UNIQUE (orgparttype_name_eng);

ALTER TABLE  organism_part_types
    ADD CONSTRAINT  organism_part_types_UK3 
    UNIQUE (orgparttype_name_fr);

---------------------------------------------------------------------
CREATE TABLE  organism_parts (
    organism_id Integer default null, 
    orgpart_id Integer NOT NULL, 
    orgparttype_id Integer NOT NULL,
    org_grp_id Integer default null,
    orgpart_lastupdate_date Date ,
    orgpart_creation_date Date ,
    useracc_id Integer NOT NULL
);
ALTER TABLE  organism_parts ADD (
    CONSTRAINT  organism_parts_PK 
    PRIMARY KEY ( orgpart_id ) 
    USING INDEX 
); 
CREATE INDEX  organism_parts_IDX1 
    ON  organism_parts (organism_id) 
CREATE INDEX  organism_parts_IDX2 
    ON  organism_parts (orgparttype_id) 

---------------------------------------------------------------------
CREATE TABLE  organism_type_groups (
    orgtypegrp_comment_eng  Varchar2(1000) default NULL , 
    orgtypegrp_comment_fr  Varchar2(1000) default NULL , 
    orgtypegrp_country_flag  Varchar2(1) default null NOT NULL, 
    orgtypegrp_geog_flag  Varchar2(1) default null NOT NULL, 
    orgtypegrp_id Integer default null NOT NULL, 
    orgtypegrp_name_eng  Varchar2(100) default '' NOT NULL, 
    orgtypegrp_name_fr  Varchar2(100) default '' NOT NULL,
    orgtypegrp_lastupdate_date Date ,
    orgtypegrp_creation_date Date ,
    useracc_id Integer NOT NULL
);
ALTER TABLE  organism_type_groups ADD (
    CONSTRAINT  organism_type_groups_PK 
    PRIMARY KEY ( orgtypegrp_id ) 
    USING INDEX 
); 
ALTER TABLE  organism_type_groups
    ADD CONSTRAINT  organism_type_groups_UK1 
    UNIQUE (orgtypegrp_name_eng);
ALTER TABLE  organism_type_groups
    ADD CONSTRAINT  organism_type_groups_UK2 
    UNIQUE (orgtypegrp_name_fr);

---------------------------------------------------------------------
CREATE TABLE  organism_types (
    orgtype_code  Varchar2(20) default null NOT NULL, 
    orgtype_comment_eng  Varchar2(1000) default NULL , 
    orgtype_comment_fr  Varchar2(1000) default NULL , 
    orgtype_id Integer default null NOT NULL, 
    orgtype_name_eng  Varchar2(100) default '' NOT NULL, 
    orgtype_name_fr  Varchar2(100) default '' NOT NULL, 
    orgtypegrp_id Integer default null NOT NULL,
    orgtype_lastupdate_date Date ,
    orgtype_creation_date Date ,
    useracc_id Integer NOT NULL
);
ALTER TABLE  organism_types ADD (
    CONSTRAINT  organism_types_PK 
    PRIMARY KEY ( orgtype_id ) 
    USING INDEX 
); 
ALTER TABLE  organism_types
    ADD CONSTRAINT  organism_types_UK1 
    UNIQUE (orgtype_code);
ALTER TABLE  organism_types
    ADD CONSTRAINT  organism_types_UK2 
    UNIQUE (orgtype_name_eng);
ALTER TABLE  organism_types
    ADD CONSTRAINT  organism_types_UK3 
    UNIQUE (orgtype_name_fr);
CREATE INDEX  organism_types_IDX1 
    ON  organism_types (orgtypegrp_id)
     
---------------------------------------------------------------------
CREATE TABLE  organisms (
    organism_comment_eng  Varchar2(1000) default NULL , 
    organism_comment_fr  Varchar2(1000) default NULL , 
    organism_creation_date Date NOT NULL, 
    organism_desc_eng  Varchar2(1000) default NULL , 
    organism_desc_fr  Varchar2(1000) default NULL , 
    organism_id Integer default null NOT NULL, 
    organism_lastupdate_date Date NOT NULL, 
    organism_name  Varchar2(100) default '' NOT NULL, 
    orgtype_id Integer default '0' NOT NULL, 
    ref_id Integer default null NOT NULL, 
    species_id Integer default NULL , 
    subtaxa_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  organisms ADD (
    CONSTRAINT  organisms_PK 
    PRIMARY KEY ( organism_id ) 
    USING INDEX 
); 
ALTER TABLE  organisms
    ADD CONSTRAINT  organisms_UK1 
    UNIQUE (organism_name);
CREATE INDEX  organisms_IDX1 
    ON  organisms (orgtype_id) 
CREATE INDEX  organisms_IDX2 
    ON  organisms (ref_id) 
CREATE INDEX  organisms_IDX3 
    ON  organisms (species_id) 
CREATE INDEX  organisms_IDX4 
    ON  organisms (subtaxa_id) 
    
---------------------------------------------------------------------
CREATE TABLE  organism_groups (
	org_grp_id Integer  NOT NULL,	
	org_grp_name_eng  Varchar2(100) default NULL, 
	org_grp_name_fr  Varchar2(100) default NULL, 
	org_grp_class_name Varchar2(100) default NULL,
	ref_id	Integer  default NULL,
	org_grp_creation_date Date NOT NULL, 
	org_grp_lastupdate_date Date NOT NULL, 
	useracc_id Integer default NULL 
);
ALTER TABLE  organism_groups ADD (
   	CONSTRAINT  org_grp_PK 
    	PRIMARY KEY ( org_grp_id) 
    	USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  permissions (
    permission_id              Integer       default '0' NOT NULL, 
    permission_name_eng        Varchar2(200) default ''  NOT NULL, 
    permission_name_fr         Varchar2(200) default ''  NOT NULL, 
    permission_creation_date   Date                      NOT NULL, 
    permission_lastupdate_date Date                      NOT NULL, 
    useracc_id                 Integer       default     NULL 
);

ALTER TABLE  permissions ADD (
    CONSTRAINT  permission_PK 
    PRIMARY KEY ( permission_id ) 
    USING INDEX 
); 

ALTER TABLE  permissions
    ADD CONSTRAINT  permissions_UK1
    UNIQUE (permission_name_eng);

ALTER TABLE  permissions
    ADD CONSTRAINT  permissions_UK2
    UNIQUE (permission_name_fr);

---------------------------------------------------------------------
CREATE TABLE  preparation_types (
    preptype_code  Varchar2(30) default null NOT NULL, 
    preptype_desc_eng  Varchar2(1000) default NULL , 
    preptype_desc_fr  Varchar2(1000) default NULL , 
    preptype_id Integer default null NOT NULL, 
    preptype_name_eng  Varchar2(100) default '' NOT NULL, 
    preptype_name_fr  Varchar2(100) default '' NOT NULL,
    preptype_visible varchar2(1) default NULL,
    preptype_creation_date Date NOT NULL, 
    preptype_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  preparation_types ADD (
    CONSTRAINT  preparation_types_PK 
    PRIMARY KEY ( preptype_id ) 
    USING INDEX 
); 
ALTER TABLE  preparation_types
    ADD CONSTRAINT  preparation_types_UK1 
    UNIQUE (preptype_code);
ALTER TABLE  preparation_types
    ADD CONSTRAINT  preparation_types_UK2 
    UNIQUE (preptype_name_eng);
ALTER TABLE  preparation_types
    ADD CONSTRAINT  preparation_types_UK3 
    UNIQUE (preptype_name_fr);
    
---------------------------------------------------------------------
CREATE TABLE  preparation_type_groups (
    preptypegrp_code  Varchar2(20) default null NOT NULL, 
    preptypegrp_name_eng  Varchar2(1000) default NULL , 
    preptypegrp_name_fr  Varchar2(1000) default NULL , 
    preptypegrp_id Integer default null NOT NULL, 
    preptypegrp_creation_date Date NOT NULL, 
    preptypegrp_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  preparation_type_groups ADD (
    CONSTRAINT  preparation_type_groups_PK 
    PRIMARY KEY ( preptypegrp_id ) 
    USING INDEX 
); 
ALTER TABLE  preparation_type_groups
    ADD CONSTRAINT  preparation_type_groups_UK1 
    UNIQUE (preptypegrp_code);

-- ALTER TABLE  preparation_type_groups
--     ADD CONSTRAINT  preparation_type_groups_UK2 
--     UNIQUE (preptypegrp_name_eng);

-- ALTER TABLE  preparation_type_groups
--    ADD CONSTRAINT  preparation_type_groups_UK3 
--    UNIQUE (preptypegrp_name_fr);

---------------------------------------------------------------------
CREATE TABLE  product_types (
    prodtype_code  Varchar2(20) default null NOT NULL, 
    prodtype_creation_date Date , 
    prodtype_desc_eng  Varchar2(1000) default NULL , 
    prodtype_desc_fr  Varchar2(1000) default NULL , 
    prodtype_id Integer default null NOT NULL, 
    prodtype_lastupdate_date Date , 
    prodtype_name_eng  Varchar2(100) default null NOT NULL, 
    prodtype_name_fr  Varchar2(100) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  product_types ADD (
    CONSTRAINT  product_types_PK 
    PRIMARY KEY ( prodtype_id ) 
    USING INDEX 
); 
ALTER TABLE  product_types
    ADD CONSTRAINT  product_types_UK1 
    UNIQUE (prodtype_code);
ALTER TABLE  product_types
    ADD CONSTRAINT  product_types_UK2 
    UNIQUE (prodtype_name_eng);
ALTER TABLE  product_types
    ADD CONSTRAINT  product_types_UK3 
    UNIQUE (prodtype_name_fr);

---------------------------------------------------------------------
CREATE TABLE  published_journals (
    journal_access_date Date , 
    journal_author  Varchar2(1000) default NULL , 
    journal_category  Varchar2(500) default NULL , 
    journal_creation_date Date NOT NULL, 
    journal_desc  Varchar2(150) default NULL , 
    journal_essn  Varchar2(10) default NULL , 
    journal_frequency  Varchar2(150) default NULL , 
    journal_id Integer default null NOT NULL, 
    journal_index_doc  Varchar2(500) default NULL , 
    journal_index_history  Varchar2(300) default NULL , 
    journal_iso_abbrev  Varchar2(200) default NULL , 
    journal_issn  Varchar2(60) default NULL , 
    journal_language  Varchar2(100) default NULL , 
    journal_lastupdate_date Date NOT NULL, 
    journal_more_info  Varchar2(3000) default NULL , 
    journal_notes  Varchar2(3000) default NULL , 
    journal_pub_dates  Varchar2(1000) default NULL , 
    journal_pub_types  Varchar2(100) default NULL , 
    journal_publisher  Varchar2(500) default NULL , 
    journal_pubmed_id  Varchar2(10) default NULL, 
    journal_title_abbrev  Varchar2(500) default NULL, 
    journal_titles  Varchar2(1000) default '' NOT NULL, 
    useracc_id Integer default NULL,
    ref_id Integer default NULL
);
ALTER TABLE  published_journals ADD (
    CONSTRAINT  published_journals_PK 
    PRIMARY KEY ( journal_id ) 
    USING INDEX 
); 
CREATE INDEX  published_journals_IDX1 
    ON  published_journals (journal_pubmed_id) 

---------------------------------------------------------------------
CREATE TABLE  purposes (
    adminrt_id Integer default NULL , 
    purpose_code  Varchar2(20) default null NOT NULL, 
    purpose_comment_eng  Varchar2(1000) default NULL , 
    purpose_comment_fr  Varchar2(1000) default NULL , 
    purpose_creation_date Date NOT NULL, 
    purpose_id Integer default null NOT NULL, 
    purpose_lastupdate_date Date NOT NULL, 
    purpose_name_eng  Varchar2(100) default '' NOT NULL, 
    purpose_name_fr  Varchar2(100) default '' NOT NULL, 
    purpose_upper_limit Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  purposes ADD (
    CONSTRAINT  purposes_PK 
    PRIMARY KEY ( purpose_id ) 
    USING INDEX 
); 
ALTER TABLE  purposes
    ADD CONSTRAINT  purposes_UK1 
    UNIQUE (purpose_code);
ALTER TABLE  purposes
    ADD CONSTRAINT  purposes_UK2 
    UNIQUE (purpose_name_eng);
ALTER TABLE  purposes
    ADD CONSTRAINT  purposes_UK3 
    UNIQUE (purpose_name_fr);
CREATE INDEX  purposes_IDX1 
    ON  purposes (adminrt_id) 
CREATE INDEX  purposes_IDX2 
    ON  purposes (useracc_id) 

---------------------------------------------------------------------
CREATE TABLE qualified_synonyms (
    qualsyno_id Integer NOT NULL,
    synonym_id Integer NOT NULL,
    qualsyno_creation_date Date NOT NULL, 
    qualsyno_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL
);
ALTER TABLE  qualified_synonyms ADD (
    CONSTRAINT  qualified_synonyms_PK 
    PRIMARY KEY ( qualsyno_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  quality_tolerance_models (
    qualitymodel_code  Varchar2(20) default null NOT NULL, 
    qualitymodel_creation_date Date , 
    qualitymodel_desc_eng  Varchar2(1000) default NULL , 
    qualitymodel_desc_fr  Varchar2(1000) default NULL , 
    qualitymodel_id Integer default null NOT NULL, 
    qualitymodel_lastupdate_date Date , 
    qualitymodel_name_eng  Varchar2(100) default null NOT NULL, 
    qualitymodel_name_fr  Varchar2(100) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  quality_tolerance_models ADD (
    CONSTRAINT  quality_tolerance_models_PK 
    PRIMARY KEY ( qualitymodel_id ) 
    USING INDEX 
); 
ALTER TABLE  quality_tolerance_models
    ADD CONSTRAINT  quality_tolerance_models_UK1 
    UNIQUE (qualitymodel_code);
CREATE INDEX  quality_tolerance_models_IDX1 
    ON  quality_tolerance_models (qualitymodel_name_eng) 
CREATE INDEX  quality_tolerance_models_IDX2 
    ON  quality_tolerance_models (qualitymodel_name_fr) 
    
---------------------------------------------------------------------
CREATE TABLE  rationales (
    notes_eng  Varchar2(2000) default NULL , 
    notes_fr  Varchar2(2000) default NULL , 
    rationale_creation_date Date NOT NULL, 
    rationale_date  date default NULL , 
    rationale_id Integer default null NOT NULL, 
    rationale_lastupdate_date Date NOT NULL, 
    ref_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  rationales ADD (
    CONSTRAINT  rationales_PK 
    PRIMARY KEY ( rationale_id ) 
    USING INDEX 
); 
CREATE INDEX  rationales_IDX1 
    ON  rationales (ref_id) 

---------------------------------------------------------------------
CREATE TABLE recommended_dietary_allowances (
    recdietallo_id              Integer DEFAULT NULL NOT NULL,
    recdietallo_code            varchar2(20) DEFAULT NULL,
    monoent_id                  Integer DEFAULT NULL NOT NULL,
    monouse_id                  Integer DEFAULT NULL NOT NULL,
    units_id                    Integer DEFAULT NULL NOT NULL,
    recdietallo_creation_date   Date                 NOT NULL, 
    recdietallo_lastupdate_date Date                 NOT NULL, 
    useracc_id                  Integer DEFAULT NULL NOT NULL
);
  
ALTER TABLE  recommended_dietary_allowances ADD (
  CONSTRAINT  recommended_dietary_allow_PK
  PRIMARY KEY (recdietallo_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  refs (
    citation_id Integer default null NOT NULL, 
    ref_code  Varchar2(100) default null NOT NULL, 
    ref_comment_eng  Varchar2(1000) default NULL , 
    ref_comment_fr  Varchar2(1000) default NULL , 
    ref_creation_date Date NOT NULL, 
    ref_id Integer default null NOT NULL, 
    ref_lastupdate_date Date NOT NULL, 
    ref_name_eng  Varchar2(500) default '' NOT NULL, 
    ref_name_fr  Varchar2(500) default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  refs ADD (
    CONSTRAINT  refs_PK 
    PRIMARY KEY ( ref_id ) 
    USING INDEX 
); 
ALTER TABLE  refs
    ADD CONSTRAINT  refs_UK1 
    UNIQUE (citation_id);
ALTER TABLE  refs
    ADD CONSTRAINT  refs_UK2 
    UNIQUE (ref_code);
ALTER TABLE  refs
    ADD CONSTRAINT  refs_UK3 
    UNIQUE (ref_name_eng);
ALTER TABLE  refs
    ADD CONSTRAINT  refs_UK4 
    UNIQUE (ref_name_fr);

---------------------------------------------------------------------
CREATE TABLE  restriction_types (
    resttype_code  Varchar2(20) default null NOT NULL, 
    resttype_id Integer default null NOT NULL, 
    resttype_name_eng  Varchar2(100) default '' NOT NULL, 
    resttype_name_fr  Varchar2(100) default '' NOT NULL,
    resttype_creation_date Date NOT NULL, 
    resttype_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  restriction_types ADD (
    CONSTRAINT  restriction_types_PK 
    PRIMARY KEY ( resttype_id ) 
    USING INDEX 
); 
ALTER TABLE  restriction_types
    ADD CONSTRAINT  restriction_types_UK1 
    UNIQUE (resttype_code);
ALTER TABLE  restriction_types
    ADD CONSTRAINT  restriction_types_UK2 
    UNIQUE (resttype_name_eng);
ALTER TABLE  restriction_types
    ADD CONSTRAINT  restriction_types_UK3 
    UNIQUE (resttype_name_fr);

---------------------------------------------------------------------
CREATE TABLE  restrictions (
    adminrt_id Integer default NULL , 
    ingredrole_id Integer default NULL , 
    ref_id Integer default null NOT NULL, 
    restrict_class_name  Varchar2(100) default '' NOT NULL, 
    restrict_creation_date Date NOT NULL, 
    restrict_detail_eng  Varchar2(2000) default NULL , 
    restrict_detail_fr  Varchar2(2000) default NULL , 
    restrict_failure_message_eng  Varchar2(1000) default NULL , 
    restrict_failure_message_fr  Varchar2(1000) default NULL , 
    restrict_id Integer default null NOT NULL, 
    restrict_lastupdate_date Date NOT NULL, 
    restrict_lower_limit Numeric(22, 8) default NULL , 
    restrict_upper_limit Numeric(22, 8) default NULL , 
    resttype_id Integer default NULL , 
    units_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  restrictions ADD (
    CONSTRAINT  restrictions_PK 
    PRIMARY KEY ( restrict_id ) 
    USING INDEX 
); 
CREATE INDEX  restrictions_IDX1 
    ON  restrictions (adminrt_id) 
CREATE INDEX  restrictions_IDX2 
    ON  restrictions (ingredrole_id) 
CREATE INDEX  restrictions_IDX3 
    ON  restrictions (ref_id) 
CREATE INDEX  restrictions_IDX4 
    ON  restrictions (resttype_id) 
CREATE INDEX  restrictions_IDX5 
    ON  restrictions (units_id) 

---------------------------------------------------------------------
CREATE TABLE  risks (
    risk_creation_date Date NOT NULL, 
    risk_id Integer default null NOT NULL, 
    risk_lastupdate_date Date NOT NULL, 
    risk_name_eng  Varchar2(100) default '' NOT NULL, 
    risk_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  risks ADD (
    CONSTRAINT  risks_PK 
    PRIMARY KEY ( risk_id ) 
    USING INDEX 
); 
ALTER TABLE  risks
    ADD CONSTRAINT  risks_UK1 
    UNIQUE (risk_name_eng);
ALTER TABLE  risks
    ADD CONSTRAINT  risks_UK2 
    UNIQUE (risk_name_fr);
CREATE INDEX  risks_IDX1 
    ON  risks (useracc_id) 

---------------------------------------------------------------------
CREATE TABLE risk_types (
    risktype_id Integer NOT NULL,
	risktype_code Varchar2(50) default '' NOT NULL,	
	risktype_name_eng  Varchar2(100) default NULL , 
	risktype_name_fr  Varchar2(100) default NULL ,
	risktype_desc_eng  Varchar2(1000) default NULL , 
	risktype_desc_fr  Varchar2(1000) default NULL ,
	risktype_creation_date Date NOT NULL, 
	risktype_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  risk_types ADD (
  CONSTRAINT  risk_types_PK
  PRIMARY KEY (risktype_id) 
  USING INDEX 
);
ALTER TABLE  risk_types
    ADD CONSTRAINT  risk_types_UK1 
    UNIQUE (risktype_code);

---------------------------------------------------------------------
CREATE TABLE  role_specials (
    rolespec_class_name  Varchar2(100) default '' NOT NULL, 
    rolespec_creation_date Date NOT NULL, 
    rolespec_id Integer default '0' NOT NULL, 
    rolespec_lastupdate_date Date NOT NULL, 
    rolespec_name_eng  Varchar2(100) default '' NOT NULL, 
    rolespec_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  role_specials ADD (
    CONSTRAINT  role_specials_PK 
    PRIMARY KEY ( rolespec_id ) 
    USING INDEX 
); 
ALTER TABLE  role_specials
    ADD CONSTRAINT  role_specials_UK1 
    UNIQUE (rolespec_class_name);
ALTER TABLE  role_specials
    ADD CONSTRAINT  role_specials_UK2 
    UNIQUE (rolespec_name_eng);
ALTER TABLE  role_specials
    ADD CONSTRAINT  role_specials_UK3 
    UNIQUE (rolespec_name_fr);
    
---------------------------------------------------------------------    
CREATE TABLE  solvents (
    solvents_code  Varchar2(50) default '' NOT NULL, 
    solvents_creation_date Date NOT NULL, 
    solvents_id Integer default null NOT NULL, 
    solvents_lastupdate_date Date NOT NULL, 
    solvents_name_eng  Varchar2(100) default '' NOT NULL, 
    solvents_name_fr  Varchar2(100) default '' NOT NULL, 
    ichclass_id Integer default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  solvents ADD (
    CONSTRAINT  solvents_PK 
    PRIMARY KEY ( solvents_id ) 
    USING INDEX 
); 
ALTER TABLE  solvents
    ADD CONSTRAINT  solvents_UK1 
    UNIQUE (solvents_code);
ALTER TABLE  solvents
    ADD CONSTRAINT  solvents_UK2 
    UNIQUE (solvents_name_eng);
ALTER TABLE  solvents
    ADD CONSTRAINT  solvents_UK3 
    UNIQUE (solvents_name_fr);
CREATE INDEX  solvents_IDX1 
    ON  solvents(ichclass_id) 

---------------------------------------------------------------------
CREATE TABLE  species (
    genus_id Integer default null NOT NULL, 
    species_creation_date Date NOT NULL, 
    species_id Integer default null NOT NULL, 
    species_lastupdate_date Date NOT NULL, 
    species_name  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  species ADD (
    CONSTRAINT  species_PK 
    PRIMARY KEY ( species_id ) 
    USING INDEX 
); 

CREATE INDEX  species_IDX1 
    ON  species (genus_id) 

---------------------------------------------------------------------
CREATE TABLE  subingredients (
    ingred_id Integer default null NOT NULL, 
    subingred_comment_eng  Varchar2(1000) default NULL , 
    subingred_comment_fr  Varchar2(1000) default NULL , 
    subingred_creation_date Date NOT NULL, 
    subingred_id Integer default null NOT NULL, 
    subingred_lastupdate_date Date NOT NULL, 
    subingred_lower_yield Numeric(22, 8) default NULL , 
    subingred_mandatory_flag  Varchar2(1) default null NOT NULL, 
    subingred_upper_yield Numeric(22, 8) default NULL , 
    units_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  subingredients ADD (
    CONSTRAINT  subingredients_PK 
    PRIMARY KEY ( subingred_id ) 
    USING INDEX 
); 
CREATE INDEX  subingredients_IDX1 
    ON  subingredients (ingred_id) 
CREATE INDEX  subingredients_IDX2 
    ON  subingredients (units_id) 

---------------------------------------------------------------------
CREATE TABLE  subtaxa (
    species_id Integer default null NOT NULL, 
    subtaxa_creation_date Date NOT NULL, 
    subtaxa_id Integer default null NOT NULL, 
    subtaxa_lastupdate_date Date NOT NULL, 
    subtaxa_name  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  subtaxa ADD (
    CONSTRAINT  subtaxa_PK 
    PRIMARY KEY ( subtaxa_id ) 
    USING INDEX 
); 
CREATE INDEX  subtaxa_IDX1 
    ON  subtaxa (species_id) 

---------------------------------------------------------------------
CREATE TABLE sub_population (
    subpop_id Integer NOT NULL,
    code varchar2(20) default NULL,
	subpop_name_eng  Varchar2(100) default NULL , 
	subpop_name_fr  Varchar2(100) default NULL ,
	subpop_comment_eng  Varchar2(1000) default NULL , 
	subpop_comment_fr  Varchar2(1000) default NULL ,
	subpop_desc_eng  Varchar2(1000) default NULL , 
	subpop_desc_fr  Varchar2(1000) default NULL ,
	subpop_ex_crit_eng  Varchar2(300) default NULL , 
	subpop_ex_crit_fr  Varchar2(300) default NULL ,
	subpop_in_crit_eng  Varchar2(300) default NULL , 
	subpop_in_crit_fr  Varchar2(300) default NULL ,
	subpop_max_age Integer default NULL ,
	subpop_min_age Integer default NULL ,
	units_id_max  Integer default NULL ,
	units_id_min Integer default NULL ,
	subpop_creation_date Date NOT NULL, 
	subpop_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
CREATE INDEX  sub_population_IDX1 
    ON  sub_population (code); 
ALTER TABLE  sub_population ADD (
  CONSTRAINT  sub_population_PK
  PRIMARY KEY (subpop_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  synonyms (
    synonym_class_name  Varchar2(100) default NULL , 
    synonym_comment_eng  Varchar2(1000) default NULL , 
    synonym_comment_fr  Varchar2(1000) default NULL , 
    synonym_creation_date Date NOT NULL, 
    synonym_desc_eng  Varchar2(1000) default NULL , 
    synonym_desc_fr  Varchar2(1000) default NULL , 
    synonym_id Integer default null NOT NULL, 
    synonym_lastupdate_date Date NOT NULL, 
    synonym_name_eng  Varchar2(700) default NULL, 
    synonym_name_fr  Varchar2(700) default NULL, 
    -- synonym_unique_name_eng Varchar2(1000) default null NOT NULL, 
    -- synonym_unique_name_fr Varchar2(1000) default null NOT NULL,
    useracc_id Integer default NULL,
    indication_id Integer default NULL,
    tradparadigm_id Integer default NULL,
    codedrisk_id Integer default null
);
ALTER TABLE  synonyms ADD (
    CONSTRAINT  synonyms_PK 
    PRIMARY KEY ( synonym_id ) 
    USING INDEX 
); 
CREATE INDEX  synonyms_IDX1
    ON  synonyms (synonym_name_eng);
CREATE INDEX  synonyms_IDX2
    ON  synonyms (synonym_name_fr);
ALTER TABLE  synonyms
    ADD CONSTRAINT  synonyms_UK1 
    UNIQUE (synonym_name_eng,synonym_name_fr,synonym_class_name);

-- ALTER TABLE  synonyms
--    ADD CONSTRAINT  synonyms_UK2 
--    UNIQUE (synonym_unique_name_fr,synonym_class_name);

---------------------------------------------------------------------
CREATE TABLE  taxa (
    organism_id Integer default null NOT NULL, 
    ref_id Integer default null NOT NULL, 
    taxon_comment_eng  Varchar2(1000) default NULL , 
    taxon_comment_fr  Varchar2(1000) default NULL , 
    taxon_creation_date Date NOT NULL, 
    taxon_id Integer default null NOT NULL, 
    taxon_lastupdate_date Date NOT NULL, 
    taxon_name  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  taxa ADD (
    CONSTRAINT  taxa_PK 
    PRIMARY KEY ( taxon_id ) 
    USING INDEX 
); 
ALTER TABLE  taxa
    ADD CONSTRAINT  taxa_UK1 
    UNIQUE (taxon_name);
CREATE INDEX  taxa_IDX1 
    ON  taxa (organism_id) ;
CREATE INDEX  taxa_IDX2 
    ON  taxa (ref_id); 
    
---------------------------------------------------------------------
CREATE TABLE  test_categories (
    testcat_code  Varchar2(20) default null NOT NULL, 
    testcat_creation_date Date , 
    testcat_desc_eng  Varchar2(1000) default NULL , 
    testcat_desc_fr  Varchar2(1000) default NULL , 
    testcat_id Integer default null NOT NULL, 
    testcat_lastupdate_date Date , 
    testcat_name_eng  Varchar2(100) default null NOT NULL, 
    testcat_name_fr  Varchar2(100) default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  test_categories ADD (
    CONSTRAINT  test_categories_PK 
    PRIMARY KEY ( testcat_id ) 
    USING INDEX 
); 
CREATE INDEX  test_categories_IDX1 
    ON  test_categories (testcat_code) 
CREATE INDEX  test_categories_IDX2 
    ON  test_categories (testcat_id) 
CREATE INDEX  test_categories_IDX3 
    ON  test_categories (testcat_name_eng) 
CREATE INDEX  test_categories_IDX4 
    ON  test_categories (testcat_name_fr) 

---------------------------------------------------------------------
CREATE TABLE  test_methods (
    testmethod_id Integer default null NOT NULL, 
    testmethod_code Varchar2(100) default null NOT NULL, 
    testmethod_name_eng Varchar2(500) default null NOT NULL, 
    testmethod_name_fr Varchar2(500) default null NOT NULL, 
    testmethod_restriction_eng VarChar(2000) default '',
	testmethod_restriction_fr VarChar(2000)	default '',
    testmethod_desc_eng Varchar2(2000) default NULL , 
    testmethod_desc_fr Varchar2(2000) default NULL , 
    testmthd_type_id Integer default null NOT NULL,
    testmthd_subtype_id Integer default '0'  NOT NULL,
    testmethod_creation_date Date NOT NULL, 
    testmethod_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  test_methods ADD (
    CONSTRAINT  test_methods_PK 
    PRIMARY KEY ( testmethod_id ) 
    USING INDEX 
); 
CREATE INDEX  test_methods_IDX1 
    ON  test_methods (testmethod_code) 
CREATE INDEX  test_methods_IDX2 
    ON  test_methods (testmethod_id) 
CREATE INDEX  test_methods_IDX3 
    ON  test_methods (testmethod_name_eng) 
CREATE INDEX  test_methods_IDX4 
    ON  test_methods (testmethod_name_fr) 

---------------------------------------------------------------------
CREATE TABLE  test_method_types (
	testmthd_type_id Integer default '0' NOT NULL, 
	testmthd_type_name_eng VarChar(100) default '',
	testmthd_type_name_fr VarChar(100)	default '',
	testmthd_type_creation_date Date NOT NULL,
	testmthd_type_lastupdate_date Date NOT NULL,
	useracc_id Integer default NULL
);
ALTER TABLE  test_method_types ADD (
    CONSTRAINT  test_method_type_PK 
    PRIMARY KEY ( testmthd_type_id ) 
    USING INDEX 
); 
CREATE INDEX  test_method_types_IDX1 
    ON  test_method_types (testmthd_type_id) 

---------------------------------------------------------------------
CREATE TABLE  test_method_subtypes (
    testmthd_subtype_id Integer default '0' NOT NULL, 
    testmthd_subtype_name_eng VarChar(100) default '',
    testmthd_subtype_name_fr  VarChar(100) default '',
    testmthd_subtype_creation_date Date NOT NULL,
    testmthd_subtype_lastupdt_date Date NOT NULL,
    useracc_id Integer default NULL
);
ALTER TABLE  test_method_subtypes ADD (
    CONSTRAINT  test_method_subtypes_PK 
    PRIMARY KEY ( testmthd_subtype_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  test_specifications (
    testspec_creation_date Date , 
    testspec_id Integer default null NOT NULL, 
    testspec_lastupdate_date Date , 
    testspec_lower_tolerance Numeric(22, 8) default NULL , 
    testspec_upper_tolerance Numeric(22, 8) default NULL , 
    units_id Integer default NULL , 
    useracc_id Integer default NULL 
);
ALTER TABLE  test_specifications ADD (
    CONSTRAINT  test_specifications_PK 
    PRIMARY KEY ( testspec_id ) 
    USING INDEX 
); 
CREATE INDEX  test_specifications_IDX1 
    ON  test_specifications (units_id) 


---------------------------------------------------------------------
CREATE TABLE  text_mono_srcmaterials (
    text_mono_srcmaterial_id Integer NOT NULL, 
    qualified_assertion_id Integer NOT NULL, 
    source_material_name_eng Varchar2(1000) default NULL, 
    source_material_name_fr Varchar2(1000) default NULL,
    txtmonosrc_creation_date Date NOT NULL, 
    txtmonosrc_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);    
ALTER TABLE  text_mono_srcmaterials ADD (
    CONSTRAINT  text_mono_srcmaterials_PK 
    PRIMARY KEY ( text_mono_srcmaterial_id ) 
    USING INDEX 
); 
CREATE INDEX  text_mono_srcmaterials_IDX1 
    ON  text_mono_srcmaterials (qualified_assertion_id); 


---------------------------------------------------------------------
CREATE TABLE trad_paradigms (
    tradparadigm_id Integer NOT NULL,
	tradparadigm_code Integer NOT NULL,	
	tradparadigm_name_eng  Varchar2(100) default NULL , 
	tradparadigm_name_fr  Varchar2(100) default NULL ,
	tradparadigm_desc_eng  Varchar2(1000) default NULL , 
	tradparadigm_desc_fr  Varchar2(1000) default NULL ,
	tradparadigm_creation_date Date NOT NULL, 
	tradparadigm_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);
ALTER TABLE  trad_paradigms ADD (
  CONSTRAINT  trad_paradigms_PK
  PRIMARY KEY (tradparadigm_id) 
  USING INDEX 
);
ALTER TABLE  trad_paradigms
    ADD CONSTRAINT  trad_paradigms_UK1 
    UNIQUE (tradparadigm_code);
    
---------------------------------------------------------------------
CREATE TABLE  units (
    units_code  Varchar2(50) default '' NOT NULL, 
    units_creation_date Date NOT NULL, 
    units_id Integer default null NOT NULL, 
    units_lastupdate_date Date NOT NULL, 
    units_name_eng  Varchar2(100) default '' NOT NULL, 
    units_name_fr  Varchar2(100) default '' NOT NULL, 
    unitstype_id Integer default null NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  units ADD (
    CONSTRAINT  units_PK 
    PRIMARY KEY ( units_id ) 
    USING INDEX 
); 
ALTER TABLE  units
    ADD CONSTRAINT  units_UK1 
    UNIQUE (units_code);
ALTER TABLE  units
    ADD CONSTRAINT  units_UK2 
    UNIQUE (units_name_eng);
ALTER TABLE  units
    ADD CONSTRAINT  units_UK3 
    UNIQUE (units_name_fr);
CREATE INDEX  units_IDX1 
    ON  units (unitstype_id) 

---------------------------------------------------------------------
CREATE TABLE  units_types (
    unitstype_creation_date Date NOT NULL, 
    unitstype_id Integer default null NOT NULL, 
    unitstype_lastupdate_date Date NOT NULL, 
    unitstype_name_eng  Varchar2(100) default '' NOT NULL, 
    unitstype_name_fr  Varchar2(100) default '' NOT NULL, 
    useracc_id Integer default NULL 
);
ALTER TABLE  units_types ADD (
    CONSTRAINT  units_types_PK 
    PRIMARY KEY ( unitstype_id ) 
    USING INDEX 
); 
ALTER TABLE  units_types
    ADD CONSTRAINT  units_types_UK1 
    UNIQUE (unitstype_name_eng);
ALTER TABLE  units_types
    ADD CONSTRAINT  units_types_UK2 
    UNIQUE (unitstype_name_fr);

---------------------------------------------------------------------
CREATE TABLE  useraccount_userroles (
    useracc_id  Integer NOT NULL, 
    userrole_id Integer NOT NULL 
);

ALTER TABLE  useraccount_userroles
    ADD CONSTRAINT  useraccount_userroles_UK 
    UNIQUE (useracc_id, userrole_id);

---------------------------------------------------------------------
CREATE TABLE  userrole_permissions (
    userrole_id   Integer NOT NULL,
    permission_id Integer NOT NULL
);

ALTER TABLE  userrole_permissions
    ADD CONSTRAINT  userrole_permissions_UK 
    UNIQUE (userrole_id, permission_id);

---------------------------------------------------------------------
CREATE TABLE  user_accounts (
    useracc_account_name  Varchar2(40) default '' NOT NULL, 
    useracc_creation_date Date NOT NULL, 
    useracc_email  Varchar2(200) default '' NOT NULL, 
    useracc_first_name  Varchar2(60) default '' NOT NULL, 
    useracc_id Integer default '0' NOT NULL, 
    password varchar2(256) default '',
    useracc_last_name  Varchar2(60) default '' NOT NULL, 
    useracc_lastupdate_date Date NOT NULL, 
    useracc_useracc_id Integer default NULL 
);
ALTER TABLE  user_accounts ADD (
    CONSTRAINT  user_accounts_PK 
    PRIMARY KEY ( useracc_id ) 
    USING INDEX 
); 
CREATE INDEX  user_accounts_IDX1 
    ON  user_accounts (useracc_account_name);

---------------------------------------------------------------------
CREATE TABLE  user_roles (
    userrole_id              Integer       default '0' NOT NULL, 
    userrole_name_eng        Varchar2(200) default ''  NOT NULL, 
    userrole_name_fr         Varchar2(200) default ''  NOT NULL, 
    userrole_creation_date   Date                      NOT NULL, 
    userrole_lastupdate_date Date                      NOT NULL, 
    useracc_id               Integer       default     NULL 
);

ALTER TABLE  user_roles ADD (
    CONSTRAINT  user_roles_PK 
    PRIMARY KEY ( userrole_id ) 
    USING INDEX 
); 
    
ALTER TABLE  user_roles
    ADD CONSTRAINT  user_roles_UK1
    UNIQUE (userrole_name_eng);

ALTER TABLE  user_roles
    ADD CONSTRAINT  user_roles_UK2
    UNIQUE (userrole_name_fr);
    
---------------------------------------------------------------------
CREATE TABLE  use_types (
    usetype_code  Varchar2(50) default '' NOT NULL, 
    usetype_creation_date Date NOT NULL, 
    usetype_id Integer default null NOT NULL, 
    usetype_lastupdate_date Date NOT NULL, 
    usetype_name_eng  Varchar2(100) default '' NOT NULL, 
    usetype_name_fr  Varchar2(100) default '' NOT NULL, 
    usetype_desc_eng  Varchar2(1000) default '', 
    usetype_desc_fr  Varchar2(1000) default '', 
    useracc_id Integer default NULL 
);
ALTER TABLE  use_types ADD (
    CONSTRAINT  use_types_PK 
    PRIMARY KEY ( usetype_id ) 
    USING INDEX 
); 
ALTER TABLE  use_types
    ADD CONSTRAINT  use_types_UK1 
    UNIQUE (usetype_code);
ALTER TABLE  use_types
    ADD CONSTRAINT  use_types_UK2 
    UNIQUE (usetype_name_eng);
ALTER TABLE  use_types
    ADD CONSTRAINT  use_types_UK3 
    UNIQUE (usetype_name_fr);




