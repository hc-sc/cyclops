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

CREATE TABLE  application_text (
    apptext_code  Varchar2(100) default '' NOT NULL, 
    apptext_creation_date Date NOT NULL, 
    apptext_id Integer default '0' NOT NULL, 
    apptext_lastupdate_date Date NOT NULL, 
    apptext_text_eng clob default null NOT NULL, 
    apptext_text_fr clob default null NOT NULL, 
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

CREATE TABLE coded_risks_synonyms (
    codedrisk_id Integer NOT NULL,
    synonym_id Integer NOT NULL
);

ALTER TABLE  coded_risks_synonyms ADD (
    CONSTRAINT  coded_risks_synonyms_PK
    PRIMARY KEY ( codedrisk_id,synonym_id ) 
    USING INDEX 
); 

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

CREATE TABLE consencit_citations (
    consencit_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  consencit_citations ADD (
    CONSTRAINT  consencit_citations_PK 
    PRIMARY KEY ( consencit_id,citation_id ) 
    USING INDEX 
); 

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

CREATE TABLE  dosage_form_admin_routes (
    adminrt_id Integer default null NOT NULL, 
    dosefrm_id Integer default null NOT NULL
);

ALTER TABLE  dosage_form_admin_routes ADD (
    CONSTRAINT  dosage_form_adminrt_PK 
    PRIMARY KEY ( dosefrm_id,adminrt_id ) 
    USING INDEX 
); 

CREATE INDEX  dosage_form_adminrt_IDX1 
    ON  dosage_form_admin_routes (adminrt_id) 

CREATE INDEX  dosage_form_adminrt_IDX2 
    ON  dosage_form_admin_routes (dosefrm_id) 

CREATE TABLE  dosage_form_group_test_specs (
    dosefrmgrp_id Integer default null NOT NULL, 
    testcat_id Integer default null NOT NULL, 
    testspec_id Integer default null NOT NULL
);
ALTER TABLE  dosage_form_group_test_specs ADD (
    CONSTRAINT  dosage_frm_gp_ts_sp_PK 
    PRIMARY KEY ( dosefrmgrp_id,testspec_id,testcat_id ) 
    USING INDEX 
); 

CREATE INDEX  dosage_frm_gp_ts_sp_IDX1 
    ON  dosage_form_group_test_specs (dosefrmgrp_id) 

CREATE INDEX  dosage_frm_gp_ts_sp_IDX2 
    ON  dosage_form_group_test_specs (testcat_id) 

CREATE INDEX  dosage_frm_gp_ts_sp_IDX3 
    ON  dosage_form_group_test_specs (testspec_id) 

CREATE TABLE  dosageform_citations (
    	monodosfrm_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

ALTER TABLE  dosageform_citations ADD (
    CONSTRAINT  dosageform_citations_PK 
    PRIMARY KEY ( monodosfrm_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE  dosageform_synonyms (
    	dosefrm_id Integer  NOT NULL,
	synonym_id Integer  NOT NULL 
);

ALTER TABLE  dosageform_synonyms ADD (
    CONSTRAINT  dosageform_synonyms_PK 
    PRIMARY KEY ( dosefrm_id,synonym_id ) 
    USING INDEX 
); 

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


CREATE TABLE  dosage_form_test_specs (
    dosefrm_id Integer default null NOT NULL, 
    testcat_id Integer default null NOT NULL, 
    testspec_id Integer default null NOT NULL
);
ALTER TABLE  dosage_form_test_specs ADD (
    CONSTRAINT  dosage_form_test_specs_PK 
    PRIMARY KEY ( dosefrm_id,testspec_id,testcat_id ) 
    USING INDEX 
); 

CREATE INDEX  dosage_form_test_specs_IDX1 
    ON  dosage_form_test_specs (dosefrm_id) 

CREATE INDEX  dosage_form_test_specs_IDX2 
    ON  dosage_form_test_specs (testcat_id) 

CREATE INDEX  dosage_form_test_specs_IDX3 
    ON  dosage_form_test_specs (testspec_id) 

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
    dosefrmgrp_id Integer default NULL, 
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

CREATE TABLE  endang_organism_countries (
    country_id Integer default '0' NOT NULL, 
    organism_id Integer default '0' NOT NULL
);
CREATE INDEX  endang_organism_countries_IDX1 
    ON  endang_organism_countries (country_id) 

CREATE INDEX  endang_organism_countries_IDX2 
    ON  endang_organism_countries (organism_id) 

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


CREATE TABLE  ex_org_grp_families (
	org_grp_id Integer NOT NULL,	
	family_id Integer  NOT NULL 
);

ALTER TABLE ex_org_grp_families ADD (
  CONSTRAINT ex_org_grp_families_PK
  PRIMARY KEY (org_grp_id, family_id) 
  USING INDEX 
);

CREATE TABLE  ex_org_grp_genera (
	org_grp_id Integer NOT NULL,	
	genus_id Integer  NOT NULL 
);

ALTER TABLE ex_org_grp_genera ADD (
  CONSTRAINT ex_org_grp_genera_PK
  PRIMARY KEY (org_grp_id, genus_id) 
  USING INDEX 
);

CREATE TABLE  ex_org_grp_organism (
	org_grp_id Integer NOT NULL,	
	organism_id Integer  NOT NULL 
);

ALTER TABLE ex_org_grp_organism ADD (
  CONSTRAINT ex_org_grp_organism_PK
  PRIMARY KEY (org_grp_id, organism_id) 
  USING INDEX 
);

CREATE TABLE  ex_org_grp_species (
	org_grp_id Integer NOT NULL,	
	species_id Integer  NOT NULL 
);

ALTER TABLE ex_org_grp_species ADD (
  CONSTRAINT ex_org_grp_species_PK
  PRIMARY KEY (org_grp_id, species_id) 
  USING INDEX 
);


CREATE TABLE  ex_org_grp_subtaxa (
	org_grp_id Integer NOT NULL,	
	subtaxa_id Integer  NOT NULL 
);

ALTER TABLE ex_org_grp_subtaxa ADD (
  CONSTRAINT ex_org_grp_subtaxa_PK
  PRIMARY KEY (org_grp_id, subtaxa_id) 
  USING INDEX 
);


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

CREATE TABLE generic_texts (
  gentext_id Integer default null NOT NULL,	
  gentext_text_eng Varchar2(2500) default NULL,
  gentext_text_fr Varchar2(2500) default NULL,
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

CREATE TABLE generic_texts_eng_citations (
    gentext_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  generic_texts_eng_citations ADD (
    CONSTRAINT  generic_texts_eng_citats_PK 
    PRIMARY KEY ( gentext_id, citation_id ) 
    USING INDEX 
); 

CREATE TABLE generic_texts_fr_citations (
    gentext_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  generic_texts_fr_citations ADD (
    CONSTRAINT  generic_texts_fr_citats_PK 
    PRIMARY KEY ( gentext_id, citation_id ) 
    USING INDEX 
); 


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


CREATE TABLE  ich_class_test_methods (
    ichclass_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  ich_class_test_methods ADD (
    CONSTRAINT  ich_class_test_methods_PK 
    PRIMARY KEY ( ichclass_id,testmethod_id ) 
    USING INDEX 
); 

CREATE INDEX  ich_class_test_methods_IDX1 
    ON  ich_class_test_methods (ichclass_id) 

CREATE INDEX  ich_class_test_methods_IDX2 
    ON  ich_class_test_methods (testmethod_id) 

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


CREATE TABLE  impurity_test_methods (
    impurity_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  impurity_test_methods ADD (
    CONSTRAINT  impurity_test_methods_PK 
    PRIMARY KEY ( impurity_id,testmethod_id ) 
    USING INDEX 
); 

CREATE INDEX  impurity_test_methods_IDX1 
    ON  impurity_test_methods (impurity_id) 

CREATE INDEX  impurity_test_methods_IDX2 
    ON  impurity_test_methods (testmethod_id) 

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

CREATE TABLE  indication_admin_routes (
    	indication_id Integer  NOT NULL,
	adminrt_id Integer  NOT NULL 
);

ALTER TABLE  indication_admin_routes ADD (
    CONSTRAINT  indication_admin_routes_PK 
    PRIMARY KEY ( indication_id,adminrt_id ) 
    USING INDEX 
); 

CREATE TABLE  ingredient_bio_impurities (
    bioimpurity_id Integer default null NOT NULL, 
    ingred_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_bio_impurities ADD (
    CONSTRAINT  ingredient_bio_impurities_PK 
    PRIMARY KEY ( ingred_id,bioimpurity_id ) 
    USING INDEX 
); 

ALTER TABLE  ingredient_bio_impurities
    ADD CONSTRAINT  ingredient_bio_impurities_UK1 
    UNIQUE (bioimpurity_id);


CREATE INDEX  ingredient_bio_impurities_IDX1 
    ON  ingredient_bio_impurities (bioimpurity_id) 

CREATE INDEX  ingredient_bio_impurities_IDX2 
    ON  ingredient_bio_impurities (ingred_id) 

CREATE TABLE  ingredient_reg_entries (
    regentry_id Integer default null NOT NULL, 
    ingred_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_reg_entries_IDX1 
    ON  ingredient_registry_entries (regentry_id) 

CREATE INDEX  ingredient_reg_entries_IDX2 
    ON  ingredient_registry_entries (ingred_id) 

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

CREATE TABLE  ingredient_impurities (
    impurity_id Integer default null NOT NULL, 
    ingred_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_impurities ADD (
    CONSTRAINT  ingredient_impurities_PK 
    PRIMARY KEY ( ingred_id,impurity_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_impurities_IDX1 
    ON  ingredient_impurities (impurity_id) 

CREATE INDEX  ingredient_impurities_IDX2 
    ON  ingredient_impurities (ingred_id) 

CREATE TABLE  ingredient_ingred_impurities (
    ingred_id Integer default null NOT NULL, 
    ingred_id_impur Integer default null NOT NULL
);
ALTER TABLE  ingredient_ingred_impurities ADD (
    CONSTRAINT  ingredient_ingred_impurts_PK 
    PRIMARY KEY ( ingred_id,ingred_id_impur ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_ingred_impurts_IDX1 
    ON  ingredient_ingred_impurities (ingred_id) 

CREATE INDEX  ingredient_ingred_impurts_IDX2 
    ON  ingredient_ingred_impurities (ingred_id_impur) 

CREATE TABLE  ingredient_nhpclassifications (
    ingred_id Integer default null NOT NULL, 
    nhpclass_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_nhpclassifications ADD (
    CONSTRAINT  ingredient_nhpclass_PK 
    PRIMARY KEY ( ingred_id,nhpclass_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_nhpclass_IDX1 
    ON  ingredient_nhpclassifications (ingred_id) 

CREATE INDEX  ingredient_nhpclass_IDX2 
    ON  ingredient_nhpclassifications (nhpclass_id) 

CREATE TABLE  ingredient_orgparts (
    ingred_id Integer default null NOT NULL, 
    orgpart_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_orgparts ADD (
    CONSTRAINT  ingredient_orgparts_PK 
    PRIMARY KEY ( ingred_id,orgpart_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_orgparts_IDX1 
    ON  ingredient_orgparts (orgpart_id) 

CREATE TABLE  ingredient_preptypes (
    ingred_id Integer default null NOT NULL, 
    preptype_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_preptypes ADD (
    CONSTRAINT  ingredient_preptypes_PK 
    PRIMARY KEY ( ingred_id,preptype_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_preptypes_IDX1 
    ON  ingredient_preptypes (preptype_id) 

-- Join between Ingredient and Qualified Synonym defining Qualified Synonyms relationship.

CREATE TABLE ingredient_qualsyno_qss (
    ingred_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);

ALTER TABLE  ingredient_qualsyno_qss ADD (
    CONSTRAINT  ingredient_qualsyno_qss_PK 
    PRIMARY KEY ( ingred_id,qualsyno_id ) 
    USING INDEX 
); 

-- Join between Ingredient and Qualified Synonym defining Common Name Synonyms relationship.

CREATE TABLE ingredient_qualsyno_cns (
    ingred_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);

ALTER TABLE  ingredient_qualsyno_cns ADD (
    CONSTRAINT  ingredient_qualsyno_cns_PK 
    PRIMARY KEY ( ingred_id,qualsyno_id ) 
    USING INDEX 
); 

-- Join between Ingredient and Qualified Synonym defining Proper Name Synonyms relationship.

CREATE TABLE ingredient_qualsyno_pns (
    ingred_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);

ALTER TABLE  ingredient_qualsyno_pns ADD (
    CONSTRAINT  ingredient_qualsyno_pns_PK 
    PRIMARY KEY ( ingred_id,qualsyno_id ) 
    USING INDEX 
); 

CREATE TABLE  ingredient_role_purposes (
    ingredrole_id Integer default null NOT NULL, 
    purpose_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_role_purposes_IDX1 
    ON  ingredient_role_purposes (ingredrole_id) 

CREATE INDEX  ingredient_role_purposes_IDX2 
    ON  ingredient_role_purposes (purpose_id) 

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


CREATE TABLE  ingredient_std_grade_refs (
    ingred_id Integer default null NOT NULL, 
    ref_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_std_grade_refs ADD (
    CONSTRAINT  ingredient_std_grade_refs_PK 
    PRIMARY KEY ( ingred_id,ref_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_std_grade_refs_IDX1 
    ON  ingredient_std_grade_refs (ingred_id) 

CREATE INDEX  ingredient_std_grade_refs_IDX2 
    ON  ingredient_std_grade_refs (ref_id) 

CREATE TABLE  ingredient_subingredients (
    ingred_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_subingred_IDX1 
    ON  ingredient_subingredients (ingred_id) 

CREATE INDEX  ingredient_subingred_IDX2 
    ON  ingredient_subingredients (subingred_id) 

CREATE TABLE  ingredient_synonyms (
    ingred_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_synonyms
    ADD CONSTRAINT  ingredient_synonyms_UK1 
    UNIQUE (ingred_id,synonym_id);


CREATE INDEX  ingredient_synonyms_IDX1 
    ON  ingredient_synonyms (ingred_id) 

CREATE INDEX  ingredient_synonyms_IDX2 
    ON  ingredient_synonyms (synonym_id) 

CREATE TABLE  ingredient_test_methods (
    ingred_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_test_methods ADD (
    CONSTRAINT  ingredient_test_methods_PK 
    PRIMARY KEY ( ingred_id,testmethod_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_test_methods_IDX1 
    ON  ingredient_test_methods (ingred_id) 

CREATE INDEX  ingredient_test_methods_IDX2 
    ON  ingredient_test_methods (testmethod_id) 

CREATE TABLE  ingredient_test_specs (
    dosefrmgrp_id Integer default null NOT NULL, 
    ingred_id Integer default null NOT NULL, 
    testspec_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_test_specs ADD (
    CONSTRAINT  ingredient_test_specs_PK 
    PRIMARY KEY ( ingred_id,testspec_id,dosefrmgrp_id ) 
    USING INDEX 
); 

CREATE INDEX  ingredient_test_specs_IDX1 
    ON  ingredient_test_specs (dosefrmgrp_id) 

CREATE INDEX  ingredient_test_specs_IDX2 
    ON  ingredient_test_specs (ingred_id) 

CREATE INDEX  ingredient_test_specs_IDX3 
    ON  ingredient_test_specs (testspec_id) 



CREATE TABLE  ingredients (
    attachment_id Integer default NULL , 
    chemsub_id Integer default NULL , 
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


CREATE TABLE  in_org_grp_families (
	org_grp_id Integer NOT NULL,	
	family_id Integer  NOT NULL 
);

ALTER TABLE in_org_grp_families ADD (
  CONSTRAINT in_org_grp_families_PK
  PRIMARY KEY (org_grp_id, family_id) 
  USING INDEX 
);


CREATE TABLE  in_org_grp_genera (
	org_grp_id Integer NOT NULL,	
	genus_id Integer  NOT NULL
);

ALTER TABLE in_org_grp_genera ADD (
  CONSTRAINT in_org_grp_genera_PK
  PRIMARY KEY (org_grp_id, genus_id) 
  USING INDEX 
);

CREATE TABLE  in_org_grp_organism (
	org_grp_id Integer NOT NULL,	
	organism_id Integer  NOT NULL
);

ALTER TABLE in_org_grp_organism ADD (
  CONSTRAINT in_org_grp_organism_PK
  PRIMARY KEY (org_grp_id, organism_id) 
  USING INDEX 
);

CREATE TABLE  in_org_grp_species (
	org_grp_id Integer NOT NULL,	
	species_id Integer  NOT NULL 
);

ALTER TABLE in_org_grp_species ADD (
  CONSTRAINT in_org_grp_species_PK
  PRIMARY KEY (org_grp_id, species_id) 
  USING INDEX 
);

CREATE TABLE  in_org_grp_subtaxa (
	org_grp_id Integer NOT NULL,	
	subtaxa_id Integer  NOT NULL 
);

ALTER TABLE in_org_grp_subtaxa ADD (
  CONSTRAINT in_org_grp_subtaxa_PK
  PRIMARY KEY (org_grp_id, subtaxa_id) 
  USING INDEX 
);

CREATE TABLE  manufact_proc_bio_impurts (
    bioimpurity_id Integer default null NOT NULL, 
    manproc_id Integer default null NOT NULL
);
ALTER TABLE  manufact_proc_bio_impurts ADD (
    CONSTRAINT  manufact_proc_bio_impurts_PK 
    PRIMARY KEY ( manproc_id,bioimpurity_id ) 
    USING INDEX 
); 

CREATE INDEX  manufact_proc_bio_impurts_IDX1 
    ON  manufact_proc_bio_impurts (bioimpurity_id) 

CREATE INDEX  manufact_proc_bio_impurts_IDX2 
    ON  manufact_proc_bio_impurts (manproc_id) 

CREATE INDEX  manufact_proc_bio_impurts_IDX3 
    ON  manufact_proc_bio_impurts (manproc_id,bioimpurity_id) 

CREATE TABLE  manufact_process_impurities (
    impurity_id Integer default null NOT NULL, 
    manproc_id Integer default null NOT NULL
);
ALTER TABLE  manufact_process_impurities ADD (
    CONSTRAINT  manufact_proc_impurts_PK 
    PRIMARY KEY ( manproc_id,impurity_id ) 
    USING INDEX 
); 

CREATE INDEX  manufact_proc_impurts_IDX1 
    ON  manufact_process_impurities (impurity_id) 

CREATE INDEX  manufact_proc_impurts_IDX2 
    ON  manufact_process_impurities (manproc_id) 

CREATE TABLE  manufact_process_ingredients (
    ingred_id Integer default null NOT NULL, 
    manproc_id Integer default null NOT NULL
);
ALTER TABLE  manufact_process_ingredients ADD (
    CONSTRAINT  manufact_proc_ingreds_PK 
    PRIMARY KEY ( manproc_id,ingred_id ) 
    USING INDEX 
); 

CREATE INDEX  manufact_proc_ingreds_IDX1 
    ON  manufact_process_ingredients (ingred_id) 

CREATE INDEX  manufact_proc_ingreds_IDX2 
    ON  manufact_process_ingredients (manproc_id) 

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


CREATE TABLE  meddra_hlgt_hlts (
    hlgt_id Integer default null NOT NULL, 
    hlt_id Integer default null NOT NULL
);

ALTER TABLE  meddra_hlgt_hlts
    ADD CONSTRAINT  meddra_hlgt_hlts_UK1 
    UNIQUE (hlgt_id,hlt_id);


CREATE INDEX  meddra_hlgt_hlts_IDX1 
    ON  meddra_hlgt_hlts (hlgt_id) 

CREATE INDEX  meddra_hlgt_hlts_IDX2 
    ON  meddra_hlgt_hlts (hlt_id) 

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


CREATE TABLE  meddra_hlt_pts (
    hlt_id Integer default null NOT NULL, 
    pt_id Integer default null NOT NULL
);
ALTER TABLE  meddra_hlt_pts
    ADD CONSTRAINT  meddra_hlt_pts_UK1 
    UNIQUE (hlt_id,pt_id);


CREATE INDEX  meddra_hlt_pts_IDX1 
    ON  meddra_hlt_pts (hlt_id) 

CREATE INDEX  meddra_hlt_pts_IDX2 
    ON  meddra_hlt_pts (pt_id) 

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


CREATE TABLE  meddra_llts (
    llt_abbrev  Varchar2(50) default NULL , 
    llt_id Integer default null NOT NULL, 
    llt_name_eng  Varchar2(100) default null NOT NULL, 
    llt_name_fr  Varchar2(100) default NULL , 
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


CREATE TABLE  meddra_soc_hlgts (
    hlgt_id Integer default null NOT NULL, 
    soc_id Integer default null NOT NULL
);
ALTER TABLE  meddra_soc_hlgts
    ADD CONSTRAINT  meddra_soc_hlgts_UK1 
    UNIQUE (soc_id,hlgt_id);


CREATE INDEX  meddra_soc_hlgts_IDX1 
    ON  meddra_soc_hlgts (hlgt_id) 

CREATE INDEX  meddra_soc_hlgts_IDX2 
    ON  meddra_soc_hlgts (soc_id) 

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


CREATE TABLE monograph_appendices (
    monoappend_id Integer default null not null,
    mono_id Integer default null not null,
    appendices_text_eng clob default NULL, 
    appendices_text_fr clob default NULL,
    monoappend_creation_date Date NOT NULL, 
    monoappend_lastupdate_date Date NOT NULL, 
    useracc_id Integer NOT NULL
);

ALTER TABLE  monograph_appendices ADD (
    CONSTRAINT  monograph_appendices_PK 
    PRIMARY KEY ( monoappend_id ) 
    USING INDEX 
); 

CREATE TABLE  monograph_citations (
    	mono_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

ALTER TABLE  monograph_citations ADD (
    CONSTRAINT  monograph_citations_PK 
    PRIMARY KEY ( mono_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE  monograph_attachments (
    monoatt_id Integer NOT NULL,
    mono_id Integer NOT NULL,
    monoatt_creation_date Date NOT NULL, 
    monoatt_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL 
);

ALTER TABLE  monograph_attachments ADD (
  CONSTRAINT  monograph_attachments_PK
  PRIMARY KEY (monoatt_id) 
  USING INDEX 
);

CREATE TABLE monograph_attach_citations (
    monoatt_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_attach_citations ADD (
    CONSTRAINT  monoatt_citations_PK 
    PRIMARY KEY ( monoatt_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_attach_rationales (
    monoatt_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_attach_rationales ADD (
    CONSTRAINT  monograph_attach_rationales_PK 
    PRIMARY KEY ( monoatt_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_citation_joins (
    mono_id Integer NOT NULL,
    citation_id Integer NOT NULL,
    monocita_relationship Integer NOT NULL
    -- monocita_creation_date Date NOT NULL, 
    -- monocita_lastupdate_date Date NOT NULL, 
    -- useracc_id Integer default NULL 
);

ALTER TABLE  monograph_citation_joins ADD (
    CONSTRAINT  monograph_citation_joins_PK 
    PRIMARY KEY ( mono_id,citation_id,monocita_relationship ) 
    USING INDEX 
); 

----------------------------------------
---   Monograph Direction For Uses   ---
----------------------------------------
CREATE TABLE  monograph_directions_for_uses (
    monodfu_id Integer NOT NULL,
    code varchar2(20) default NULL,
    mono_id Integer default NULL,
    monodfu_name_eng varchar2(1000) default NULL,
    monodfu_name_fr varchar2(1000) default NULL,
    monodfu_creation_date Date NOT NULL, 
    monodfu_lastupdate_date Date NOT NULL, 
    adminrt_id Integer default NULL,
    ingred_id Integer default NULL,
    monodose_id Integer default NULL,
    preptype_id Integer default NULL,
    preptypegrp_id Integer default NULL,
    subpop_id Integer default NULL,
    monoent_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    presentation_eng varchar2(500) default NULL,
    presentation_fr varchar2(500) default NULL,
    useracc_id Integer default NULL
);

ALTER TABLE  monograph_directions_for_uses ADD (
  CONSTRAINT  monograph_directs_for_uses_PK
  PRIMARY KEY (monodfu_id) 
  USING INDEX 
);

CREATE INDEX  monograph_dirctns_fr_uses_IDX1 
    ON  monograph_directions_for_uses (code);

CREATE TABLE monograph_drfruse_citations (
    monodfu_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_citations ADD (
    CONSTRAINT  monograph_drfruse_citations_PK 
    PRIMARY KEY ( monodfu_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_drfruse_rationales (
    monodfu_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_rationales ADD (
    CONSTRAINT  monograph_drfruse_ratnls_PK 
    PRIMARY KEY ( monodfu_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_drfruse_mono_uses (
    monodfu_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_mono_uses ADD (
    CONSTRAINT  monograph_drfruse_mono_uses_PK 
    PRIMARY KEY ( monodfu_id,monouse_id ) 
    USING INDEX 
); 

-- Monograph Directions of Use to Generic Text
CREATE TABLE monograph_drfruse_gentexts (
    monodfu_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_gentexts ADD (
    CONSTRAINT  monograph_drfruse_gentexts_PK 
    PRIMARY KEY ( monodfu_id,gentext_id ) 
    USING INDEX 
); 

----------------------------------------
---      Monograph Dosage Form       ---
----------------------------------------
CREATE TABLE  monograph_dosage_forms (
    monodosfrm_id Integer NOT NULL,
    dosefrm_id Integer NOT NULL,
    mono_id Integer NOT NULL,
    adminrt_id Integer default NULL,
    ingred_id Integer default NULL,
    monodose_id Integer default NULL,
    preptype_id Integer default NULL,
    preptypegrp_id Integer default NULL,
    subpop_id Integer default NULL,
    monoent_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    presentation_eng varchar2(500) default NULL,
    presentation_fr varchar2(500) default NULL,
    monodosfrm_creation_date Date NOT NULL, 
    monodosfrm_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);

ALTER TABLE  monograph_dosage_forms ADD (
  CONSTRAINT  monograph_dosage_forms_PK
  PRIMARY KEY (monodosfrm_id) 
  USING INDEX 
);

----------------------------------------
---    Monograph Dosage Form Notes   ---
----------------------------------------
CREATE TABLE  monograph_dosage_form_notes (
    monodosfrmnote_id Integer NOT NULL,
    monodosfrmnote_code varchar2(20) NOT NULL,
    monodosfrmnote_notes_eng varchar2(1000) NOT NULL,
    monodosfrmnote_notes_fr varchar2(1000) NOT NULL,
    monodosfrmnote_creation_date Date NOT NULL, 
    monodosfrmnote_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);

ALTER TABLE  monograph_dosage_form_notes ADD (
  CONSTRAINT  monograph_dosage_form_notes_PK
  PRIMARY KEY (monodosfrmnote_id) 
  USING INDEX 
);

CREATE TABLE  monograph_roa_monograph_dfns (
    monodosfrmnote_id Integer NOT NULL,
    monoadminrt_id Integer NOT NULL
);

ALTER TABLE  monograph_roa_monograph_dfns ADD (
  CONSTRAINT  monograph_roa_monogrph_dfns_PK
  PRIMARY KEY ( monodosfrmnote_id, monoadminrt_id ) 
  USING INDEX 
);

CREATE TABLE monograph_dsgfrm_mono_uses (
    monodosfrm_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);

ALTER TABLE  monograph_dsgfrm_mono_uses ADD (
    CONSTRAINT  monograph_dsgfrm_mono_uses_PK 
    PRIMARY KEY ( monodosfrm_id,monouse_id ) 
    USING INDEX 
); 

-------------------------------------------------------
---  Monograph Dosage Form - Monograph Dependencies ---
-------------------------------------------------------
CREATE TABLE monograph_dosage_frm_dpnds (
    monodosfrm_id Integer NOT NULL,
    monodep_id Integer NOT NULL
);

ALTER TABLE  monograph_dosage_frm_dpnds ADD (
    CONSTRAINT  monograph_dosage_frm_dpnds_PK 
    PRIMARY KEY ( monodosfrm_id,monodep_id ) 
    USING INDEX 
); 

----------------------------------------
---          Monograph Doses         ---
----------------------------------------
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
    adminrt_id Integer default NULL,
    ingred_id Integer default NULL,
    monodose_dep_id Integer default NULL,
    preptype_id Integer default NULL,
    preptypegrp_id Integer default NULL,
    subpop_id Integer default NULL,
    monoent_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,
    notes_eng varchar2(500) default NULL,
    notes_fr varchar2(500) default NULL,
    presentation_eng varchar2(500) default NULL,
    presentation_fr varchar2(500) default NULL,
    monodose_qde varchar2(1) default NULL,
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

CREATE TABLE monograph_dose_citations (
    monodose_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_PK 
    PRIMARY KEY ( monodose_id,citation_id ) 
    USING INDEX 
); 

----------------------------------------
---       Monograph Dose Notes       ---
----------------------------------------
CREATE TABLE  monograph_dose_notes (
    monodosenote_id Integer NOT NULL,
    monodosenote_code varchar2(20) NOT NULL,
    monodosenote_notes_eng varchar2(2500) NOT NULL,
    monodosenote_notes_fr varchar2(2500) NOT NULL,
    monodosenote_creation_date Date NOT NULL, 
    monodosenote_lastupdate_date Date NOT NULL, 
    useracc_id Integer default NULL
);

ALTER TABLE  monograph_dose_notes ADD (
  CONSTRAINT  monograph_dose_notes_PK
  PRIMARY KEY (monodosenote_id) 
  USING INDEX 
);

CREATE TABLE  monograph_monograph_dosenotes (
    monodosenote_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_monograph_dosenotes ADD (
  CONSTRAINT  monograph_monograph_dosents_PK
  PRIMARY KEY ( monodosenote_id, mono_id ) 
  USING INDEX 
);

CREATE TABLE monograph_dose_rationales (
    monodose_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_rationales ADD (
    CONSTRAINT  monodose_rationales_PK 
    PRIMARY KEY ( monodose_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dose_shrd_monographs (
    monodose_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_shrd_monographs ADD (
    CONSTRAINT  monograph_dose_shrd_mono_PK 
    PRIMARY KEY ( monodose_id,mono_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dose_dist_monographs (
    monodose_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_dist_monographs ADD (
    CONSTRAINT  monograph_dose_dist_mono_PK 
    PRIMARY KEY ( monodose_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_dose_dist_monographs
--     ADD CONSTRAINT  monograph_dose_dist_mono_UK 
--     UNIQUE ( monodose_id );

CREATE TABLE monograph_dose_shrd_mono_dfus (
    monodose_id Integer NOT NULL,
    monodfu_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_shrd_mono_dfus ADD (
    CONSTRAINT  mono_dose_shrd_mono_dfu_PK 
    PRIMARY KEY ( monodose_id,monodfu_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dose_dist_mono_dfus (
    monodose_id Integer NOT NULL,
    monodfu_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_dist_mono_dfus ADD (
    CONSTRAINT  mono_dose_dist_mono_dfu_PK 
    PRIMARY KEY ( monodose_id,monodfu_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dose_mono_uses (
    monodose_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  monograph_dose_mono_uses_PK 
    PRIMARY KEY ( monodose_id,monouse_id ) 
    USING INDEX 
); 

-- Monograph Dose to Generic Text
CREATE TABLE monograph_dose_gentexts (
    monodose_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_gentexts ADD (
    CONSTRAINT  monodose_dose_gentexts_PK 
    PRIMARY KEY ( monodose_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dose_orgparts (
    monodose_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_dose_orgparts ADD (
    CONSTRAINT  monodose_dose_orgparts_PK 
    PRIMARY KEY ( monodose_id,orgpart_id ) 
    USING INDEX 
); 

----------------------------------------
---       Monograph Dosage Form      ---
----------------------------------------

CREATE TABLE monograph_dosfrm_citations (
    monodosfrm_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_dosfrm_citations ADD (
    CONSTRAINT  monograph_dosfrm_citations_PK 
    PRIMARY KEY ( monodosfrm_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dosfrm_rationales (
    monodosfrm_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_dosfrm_rationales ADD (
    CONSTRAINT  monograph_dosfrm_ratnls_PK 
    PRIMARY KEY ( monodosfrm_id,rationale_id ) 
    USING INDEX 
); 

-- Monograph Dosage Form to Generic Text
CREATE TABLE monograph_dosfrm_gentexts (
    monodosfrm_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_dosfrm_gentexts ADD (
    CONSTRAINT  monograph_dosfrm_gentexts_PK 
    PRIMARY KEY ( monodosfrm_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dosfrm_orgparts (
    monodosfrm_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_dosfrm_orgparts ADD (
    CONSTRAINT  monograph_dosfrm_orgparts_PK 
    PRIMARY KEY ( monodosfrm_id,orgpart_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_drfruse_orgparts (
    monodfu_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_PK 
    PRIMARY KEY ( monodfu_id,orgpart_id ) 
    USING INDEX 
); 
    
----------------------------------------
---        Monograph Duration        ---
----------------------------------------
CREATE TABLE  monograph_durations (
    monodur_id Integer NOT NULL,
    code varchar2(20) NOT NULL,
    monodur_statement_eng varchar2(1000) default NULL, 
    monodur_statement_fr varchar2(1000) default NULL, 
    monodur_maximum_duration Integer default NULL,
    monodur_minimum_duration Integer default NULL,
    units_id Integer default NULL,    
    adminrt_id Integer default NULL,
    ingred_id Integer default NULL,
    monodose_id Integer default NULL,
    preptype_id Integer default NULL,
    preptypegrp_id Integer default NULL,
    subpop_id Integer default NULL,
    monoent_id Integer default NULL,
    other_dependency_eng varchar2(500) default NULL,
    other_dependency_fr varchar2(500) default NULL,    
    presentation_eng varchar2(500) default NULL,
    presentation_fr varchar2(500) default NULL,
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

CREATE TABLE monograph_duration_citations (
    monodur_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_duration_citations ADD (
    CONSTRAINT  monograph_dur_citations_PK 
    PRIMARY KEY ( monodur_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_duration_rationales (
    monodur_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_duration_rationales ADD (
    CONSTRAINT  monodose_duration_PK 
    PRIMARY KEY ( monodur_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dur_shrd_monographs (
    monodur_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_dur_shrd_monographs ADD (
    CONSTRAINT  monograph_dur_shrd_mono_PK 
    PRIMARY KEY ( monodur_id,mono_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_dur_dist_monographs (
    monodur_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_dur_dist_monographs ADD (
    CONSTRAINT  monograph_dur_dist_mono_PK 
    PRIMARY KEY ( monodur_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_dur_dist_monographs
--    ADD CONSTRAINT  monograph_dur_dist_mono_UK 
--    UNIQUE ( monodur_id );

CREATE TABLE monograph_dur_mono_uses (
    monodur_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);

ALTER TABLE  monograph_dur_mono_uses ADD (
    CONSTRAINT  monograph_dur_mono_uses_PK 
    PRIMARY KEY ( monodur_id,monouse_id ) 
    USING INDEX 
); 

-- Monograph Duration to Generic Text
CREATE TABLE monograph_duration_gentexts (
    monodur_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_duration_gentexts ADD (
    CONSTRAINT  monodose_duration_gentexts_PK 
    PRIMARY KEY ( monodur_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_duration_orgparts (
    monodur_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_duration_orgparts ADD (
    CONSTRAINT  monodose_duration_orgparts_PK 
    PRIMARY KEY ( monodur_id,orgpart_id ) 
    USING INDEX 
); 

---------------------------------------------
---           Monograph Entries           ---
---------------------------------------------

CREATE TABLE  monograph_entries (
        monoent_id Integer default null NOT NULL,
	mono_id Integer default null NOT NULL,	
        code varchar2(20) default NULL,
	ingred_id Integer default null NOT NULL,
        notes_eng varchar2(500) default NULL,
        notes_fr varchar2(500) default NULL,
        monoent_primary_ingredient varchar2(1) default '',
        monoentry_class_name varchar2(100) default null NOT NULL,
        source_notes_eng varchar2(500) default NULL,
        source_notes_fr varchar2(500) default NULL,
	monoent_creation_date Date NOT NULL,
	monoent_lastupdate_date Date NOT NULL,
	useracc_id Integer default NULL
);

ALTER TABLE  monograph_entries ADD (
   	CONSTRAINT  monograph_entries_PK 
    	PRIMARY KEY ( monoent_id) 
    	USING INDEX 
);

CREATE INDEX  monographs_entries_IDX1 
    ON  monograph_entries (code);

ALTER TABLE  monograph_entries ADD
    CONSTRAINT  monograph_entries_UK1 
    UNIQUE (mono_id,ingred_id);

CREATE TABLE monograph_entries_citations (
    monoent_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_citations ADD (
    CONSTRAINT  monograph_entries_citats_PK 
    PRIMARY KEY ( monoent_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_rationales (
    monoent_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_rationales ADD (
    CONSTRAINT  monograph_entries_rtnls_PK 
    PRIMARY KEY ( monoent_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entry_generic_texts (
    monoent_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_PK 
    PRIMARY KEY ( monoent_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_cn_gentexts (
    monoent_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE monograph_entries_cn_gentexts ADD (
    CONSTRAINT monograph_entries_cn_gntxts_PK 
    PRIMARY KEY ( monoent_id,gentext_id ) 
    USING INDEX 
); 


CREATE TABLE monograph_entries_preptypes (
    monoent_id Integer NOT NULL,
    preptype_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_preptypes ADD (
    CONSTRAINT  monograph_entries_preptypes_PK 
    PRIMARY KEY ( monoent_id,preptype_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_preptypegrps (
    monoent_id Integer NOT NULL,
    preptypegrp_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_preptypegrps ADD (
    CONSTRAINT  monograph_entries_prptpgrps_PK 
    PRIMARY KEY ( monoent_id,preptypegrp_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_orgparts (
    monoent_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_orgparts ADD (
    CONSTRAINT  monograph_entries_orgparts_PK 
    PRIMARY KEY ( monoent_id,orgpart_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_src_ingreds (
    monoent_id Integer NOT NULL,
    ingred_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_src_ingreds ADD (
    CONSTRAINT  monograph_entries_src_igrds_PK 
    PRIMARY KEY ( monoent_id,ingred_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_commonnames (
    monoent_id Integer NOT NULL,
    synonym_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_commonnames ADD (
    CONSTRAINT  monograph_entries_comnames_PK 
    PRIMARY KEY ( monoent_id,synonym_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_entries_synonyms (
    monoent_id Integer NOT NULL,
    synonym_id Integer NOT NULL
);

ALTER TABLE  monograph_entries_synonyms ADD (
    CONSTRAINT  monograph_entries_synonyms_PK 
    PRIMARY KEY ( monoent_id,synonym_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_generic_texts (
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE monograph_generic_texts ADD (
    CONSTRAINT monograph_gentext_PK 
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
); 

---------------------------------------------
---            Monograph Group            ---
---------------------------------------------

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

---------------------------------------------
---       Monograph Risk Statements       ---
---------------------------------------------

CREATE TABLE monograph_risk_stmts (
    	monoriskstmt_id Integer NOT NULL,
        code varchar2(20) default NULL,
        synonym_id Integer default NULL,
        llt_id Integer default NULL,
        adminrt_id Integer default NULL,
        ingred_id Integer default NULL,
        monodose_id Integer default NULL,
        preptype_id Integer default NULL,
        preptypegrp_id Integer default NULL,
        subpop_id Integer default NULL,
        monoent_id Integer default NULL,
        other_dependency_eng varchar2(500) default NULL,
        other_dependency_fr varchar2(500) default NULL,
        notes_eng varchar2(500) default NULL,
        notes_fr varchar2(500) default NULL,
        presentation_eng varchar2(500) default NULL,
        presentation_fr varchar2(500) default NULL,
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

CREATE TABLE monograph_riskstmt_citations (
    monoriskstmt_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_riskstmt_citations ADD (
    CONSTRAINT  monograph_riskstmt_citats_PK 
    PRIMARY KEY ( monoriskstmt_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_riskstmt_rationales (
    monoriskstmt_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_riskstmt_rationales ADD (
    CONSTRAINT  monograph_riskstmt_ratnles_PK 
    PRIMARY KEY ( monoriskstmt_id,rationale_id ) 
    USING INDEX 
); 


CREATE TABLE monograph_risk_shrd_monographs (
    monoriskstmt_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_risk_shrd_monographs ADD (
    CONSTRAINT  monograph_risk_shrd_mono_PK 
    PRIMARY KEY ( monoriskstmt_id,mono_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_risk_dist_monographs (
    monoriskstmt_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_risk_dist_monographs ADD (
    CONSTRAINT  monograph_risk_dist_mono_PK 
    PRIMARY KEY ( monoriskstmt_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_risk_dist_monographs
--    ADD CONSTRAINT  monograph_risk_dist_mono_UK 
--    UNIQUE ( monoriskstmt_id );

CREATE TABLE monograph_rskstm_mono_uses (
    monoriskstmt_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);

ALTER TABLE  monograph_rskstm_mono_uses ADD (
    CONSTRAINT  monograph_rskstm_mono_uses_PK 
    PRIMARY KEY ( monoriskstmt_id,monouse_id ) 
    USING INDEX 
); 

-- Monograph Risk Statement to Generic Text
CREATE TABLE monograph_riskstmt_gentexts (
    monoriskstmt_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_riskstmt_gentexts ADD (
    CONSTRAINT  monograph_riskstmt_gentexts_PK 
    PRIMARY KEY ( monoriskstmt_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_riskstmt_orgparts (
    monoriskstmt_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_riskstmt_orgparts ADD (
    CONSTRAINT  monograph_riskstmt_orgparts_PK 
    PRIMARY KEY ( monoriskstmt_id,orgpart_id ) 
    USING INDEX 
); 

---------------------------------------------
---   Monograph Route of Administration   ---
---------------------------------------------
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

CREATE TABLE monograph_rteofadmns_citations (
    monoadminrt_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_rteofadmns_citations ADD (
    CONSTRAINT  monograph_rteofadmns_citats_PK 
    PRIMARY KEY ( monoadminrt_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_rteofadmns_ratnls (
    monoadminrt_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_rteofadmns_ratnls ADD (
    CONSTRAINT  monograph_rteofadmns_ratnls_PK 
    PRIMARY KEY ( monoadminrt_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_roa_shrd_monographs (
    monoadminrt_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_roa_shrd_monographs ADD (
    CONSTRAINT  monograph_roa_shrd_mono_PK 
    PRIMARY KEY ( monoadminrt_id,mono_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_roa_dist_monographs (
    monoadminrt_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_roa_dist_monographs ADD (
    CONSTRAINT  monograph_roa_dist_mono_PK 
    PRIMARY KEY ( monoadminrt_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_roa_dist_monographs
--     ADD CONSTRAINT  monograph_roa_dist_mono_UK 
--     UNIQUE ( monoadminrt_id );

---------------------------------------------
---           Monograph Sources           ---
---------------------------------------------
CREATE TABLE monograph_sources (
    	monosource_id Integer NOT NULL,	
        monoent_id Integer NOT NULL,
	ingred_id Integer default null,
	orgpart_id Integer default null,
	monosource_addsource  Varchar2(100) default NULL,
	monosource_creation_date Date NOT NULL, 
	monosource_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);


ALTER TABLE  monograph_sources ADD (
  CONSTRAINT  monograph_source_PK
  PRIMARY KEY (monosource_id) 
  USING INDEX 
);

----------------------------------------
---   Monograph SubIngredient Doses  ---
----------------------------------------
CREATE TABLE  monograph_subingdoses (
    monosubingdose_id Integer NOT NULL,
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
    subingred_id Integer default NULL
);

ALTER TABLE  monograph_subingdoses ADD (
  CONSTRAINT  monograph_subingdoses_PK
  PRIMARY KEY (monosubingdose_id) 
  USING INDEX 
);

CREATE TABLE monograph_subingdose_cttns (
    monosubingdose_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_subingdose_cttns ADD (
    CONSTRAINT  monograph_subingdose_cttns_PK 
    PRIMARY KEY ( monosubingdose_id,citation_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_subingdose_rtnls (
    monosubingdose_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_subingdose_rtnls ADD (
    CONSTRAINT  monosubingdose_rtnls_PK 
    PRIMARY KEY ( monosubingdose_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_sbngds_mndss_shrd (
    monodose_id Integer NOT NULL,
    monosubingdose_id Integer NOT NULL
);

ALTER TABLE  monograph_sbngds_mndss_shrd ADD (
    CONSTRAINT  monograph_sbngds_mndss_shrd_PK 
    PRIMARY KEY ( monodose_id,monosubingdose_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_sbngds_mndss_dist (
    monodose_id Integer NOT NULL,
    monosubingdose_id Integer NOT NULL
);

ALTER TABLE  monograph_sbngds_mndss_dist ADD (
    CONSTRAINT  monograph_sbngds_mndss_dist_PK 
    PRIMARY KEY ( monodose_id,monosubingdose_id ) 
    USING INDEX 
); 

----------------------------------------
---   Monograph Entry SubPopulation  ---
----------------------------------------


CREATE TABLE  monoentry_subpopulation (
    monoent_id Integer NOT NULL, 
    subpop_id Integer NOT NULL
);

ALTER TABLE  monoentry_subpopulation ADD (
    CONSTRAINT  monoentry_subpopulation_PK 
    PRIMARY KEY ( monoent_id,subpop_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_uses (
    	monouse_id Integer NOT NULL,
        code varchar2(20) default NULL,
	synonym_id Integer default NULL,
        monouse_sort_order Integer default NULL,
        monouse_stmt_long_eng Varchar2(1000) default NULL,
        monouse_stmt_long_fr Varchar2(1000) default NULL,
        monouse_stmt_short_eng Varchar2(1000) default NULL,
        monouse_stmt_short_fr Varchar2(1000) default NULL,
        adminrt_id Integer default NULL,
        ingred_id Integer default NULL,
        monodose_id Integer default NULL,
        preptype_id Integer default NULL,
        preptypegrp_id Integer default NULL,
        subpop_id Integer default NULL,
        monoent_id Integer default NULL,
        usetype_id Integer default NULL,
        other_dependency_eng varchar2(500) default NULL,
        other_dependency_fr varchar2(500) default NULL,
        presentation_eng varchar2(500) default NULL,
        presentation_fr varchar2(500) default NULL,
        monouse_creation_date Date NOT NULL, 
	monouse_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL
);

ALTER TABLE  monograph_uses ADD (
  CONSTRAINT  monograph_uses_PK
  PRIMARY KEY (monouse_id) 
  USING INDEX 
);

CREATE INDEX  monograph_uses_IDX1 
    ON  monograph_uses (code);

CREATE TABLE monograph_use_citations (
    monouse_id Integer NOT NULL,
    citation_id Integer NOT NULL
);

ALTER TABLE  monograph_use_citations ADD (
    CONSTRAINT  monograph_use_citations_PK 
    PRIMARY KEY ( monouse_id,citation_id ) 
    USING INDEX 
); 

-- Monograph Use to Generic Text
CREATE TABLE monograph_use_gentexts (
    monouse_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);

ALTER TABLE  monograph_use_gentexts ADD (
    CONSTRAINT  monograph_use_gentexts_PK 
    PRIMARY KEY ( monouse_id,gentext_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_use_rationales (
    monouse_id Integer NOT NULL,
    rationale_id Integer NOT NULL
);

ALTER TABLE  monograph_use_rationales ADD (
    CONSTRAINT  monograph_use_rationale_id_PK 
    PRIMARY KEY ( monouse_id,rationale_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_uses_shrd_monographs (
    monouse_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_uses_shrd_monographs ADD (
    CONSTRAINT  monograph_uses_shrd_mono_PK 
    PRIMARY KEY ( monouse_id,mono_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_uses_dist_monographs (
    monouse_id Integer NOT NULL,
    mono_id Integer NOT NULL
);

ALTER TABLE  monograph_uses_dist_monographs ADD (
    CONSTRAINT  monograph_uses_dist_mono_PK 
    PRIMARY KEY ( monouse_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_uses_dist_monographs
--    ADD CONSTRAINT  monograph_uses_dist_mono_UK 
--    UNIQUE ( monouse_id );

CREATE TABLE monograph_use_orgparts (
    monouse_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_use_orgparts ADD (
    CONSTRAINT  monograph_use_orgparts_PK 
    PRIMARY KEY ( monouse_id,orgpart_id ) 
    USING INDEX 
); 

CREATE TABLE monograph_use_mono_uses (
    monouse_id Integer NOT NULL,
    monousedep_id Integer NOT NULL
);

ALTER TABLE  monograph_use_mono_uses ADD (
    CONSTRAINT  monograph_use_mono_uses_PK 
    PRIMARY KEY ( monouse_id,monousedep_id ) 
    USING INDEX 
); 

------------------
--- Monographs ---
------------------
CREATE TABLE  monographs (
	mono_id Integer default null NOT NULL,	
        code varchar2(20) default NULL,
        monogroup_id Integer default NULL,
	mono_name_eng  Varchar2(100) default NULL , 
	mono_name_fr  Varchar2(100) default NULL , 
	mono_url_eng  Varchar2(200) default NULL , 
	mono_url_fr  Varchar2(200) default NULL , 
	mono_publish_date Date default NULL,
	mono_creation_date Date NOT NULL, 
	mono_lastupdate_date Date NOT NULL, 
	useracc_id Integer NOT NULL,
        general_notes_gentext_id Integer default NULL,
        dose_notes_gentext_id Integer default NULL,
        risk_notes_gentext_id Integer default NULL,
        nmi_notes_gentext_id Integer default NULL,
        spec_info_gentext_id Integer default NULL,
        stor_cond_gentext_id Integer default NULL
);

ALTER TABLE  monographs ADD (
   	CONSTRAINT  mono_PK 
    	PRIMARY KEY ( mono_id) 
    	USING INDEX 
);

CREATE INDEX  monographs_IDX1 
    ON  monographs (code);

CREATE TABLE monograph_sub_populations (
    mono_id Integer NOT NULL,
    subpop_id Integer NOT NULL
);

ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_subpopulations_PK 
    PRIMARY KEY (mono_id,subpop_id ) 
    USING INDEX 
); 

-----------------------------------------------------
--- NHP Classification - Microbiological Impurity ---
-----------------------------------------------------
CREATE TABLE  nhp_class_bio_impurities (
    bioimpurity_id Integer default null NOT NULL, 
    nhpclass_id Integer default null NOT NULL
);
ALTER TABLE  nhp_class_bio_impurities ADD (
    CONSTRAINT  nhp_class_bio_impurities_PK 
    PRIMARY KEY ( nhpclass_id,bioimpurity_id ) 
    USING INDEX 
); 

CREATE INDEX  nhp_class_bio_impurities_IDX1 
    ON  nhp_class_bio_impurities (bioimpurity_id) 

CREATE INDEX  nhp_class_bio_impurities_IDX2 
    ON  nhp_class_bio_impurities (nhpclass_id) 

CREATE TABLE  nhp_class_idnt_test_methods (
    nhpclass_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  nhp_class_idnt_test_methods ADD (
    CONSTRAINT  nhp_cls_idnt_tst_mtds_PK 
    PRIMARY KEY ( nhpclass_id,testmethod_id ) 
    USING INDEX 
); 

CREATE INDEX  nhp_cls_idnt_tst_mtds_IDX1 
    ON  nhp_class_idnt_test_methods (nhpclass_id) 

CREATE INDEX  nhp_cls_idnt_tst_mtds_IDX2 
    ON  nhp_class_idnt_test_methods (testmethod_id) 

CREATE TABLE  nhp_class_impurities (
    impurity_id Integer default null NOT NULL, 
    nhpclass_id Integer default null NOT NULL
);

ALTER TABLE  nhp_class_impurities ADD (
    CONSTRAINT  nhp_class_impurities_PK 
    PRIMARY KEY ( nhpclass_id,impurity_id ) 
    USING INDEX 
); 

CREATE INDEX  nhp_class_impurities_IDX1 
    ON  nhp_class_impurities (impurity_id) 

CREATE INDEX  nhp_class_impurities_IDX2 
    ON  nhp_class_impurities (nhpclass_id) 

CREATE TABLE  nhp_class_ingredients (
    ingred_id Integer default null NOT NULL, 
    nhpclass_id Integer default null NOT NULL
);
ALTER TABLE  nhp_class_ingredients ADD (
    CONSTRAINT  nhp_class_ingredients_PK 
    PRIMARY KEY ( nhpclass_id,ingred_id ) 
    USING INDEX 
); 

CREATE INDEX  nhp_class_ingredients_IDX1 
    ON  nhp_class_ingredients (ingred_id) 

CREATE INDEX  nhp_class_ingredients_IDX2 
    ON  nhp_class_ingredients (nhpclass_id) 

CREATE TABLE  nhp_class_qty_test_methods (
    nhpclass_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  nhp_class_qty_test_methods ADD (
    CONSTRAINT  nhp_class_qty_test_methods_PK 
    PRIMARY KEY ( nhpclass_id,testmethod_id ) 
    USING INDEX 
); 

CREATE INDEX  nhp_class_qty_test_methods_IDX1 
    ON  nhp_class_qty_test_methods (nhpclass_id) 

CREATE INDEX  nhp_class_qty_test_methods_IDX2 
    ON  nhp_class_qty_test_methods (testmethod_id) 

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


CREATE TABLE  organism_countries (
    country_id Integer default '0' NOT NULL, 
    organism_id Integer default null NOT NULL
);
CREATE INDEX  organism_countries_IDX1 
    ON  organism_countries (country_id) 

CREATE INDEX  organism_countries_IDX2 
    ON  organism_countries (organism_id) 

CREATE TABLE  organism_geographic_areas (
    geogarea_id Integer default null NOT NULL, 
    organism_id Integer default null NOT NULL
);
CREATE INDEX  organism_geographic_areas_IDX1 
    ON  organism_geographic_areas (geogarea_id) 

CREATE INDEX  organism_geographic_areas_IDX2 
    ON  organism_geographic_areas (organism_id) 

CREATE TABLE  organism_part_subingredients (
    orgpart_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  organism_part_subingred_IDX1 
    ON  organism_part_subingredients (orgpart_id) 

CREATE INDEX  organism_part_subingred_IDX2 
    ON  organism_part_subingredients (subingred_id) 

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

-- Join between Organism and Qualified Synonyms.

CREATE TABLE organism_qualifiedsynonyms (
    organism_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);

ALTER TABLE  organism_qualifiedsynonyms ADD (
    CONSTRAINT  organism_qualifiedsynonyms_PK 
    PRIMARY KEY ( organism_id , qualsyno_id ) 
    USING INDEX 
); 

CREATE TABLE  organism_synonyms (
    organism_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);

ALTER TABLE  organism_synonyms
    ADD CONSTRAINT  organism_synonyms_UK1 
    UNIQUE (organism_id,synonym_id);


CREATE INDEX  organism_synonyms_IDX1 
    ON  organism_synonyms (organism_id) 

CREATE INDEX  organism_synonyms_IDX2 
    ON  organism_synonyms (synonym_id) 

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


CREATE TABLE  organism_type_grp_orgparttypes (
    orgparttype_id Integer default null NOT NULL, 
    orgtypegrp_id Integer default null NOT NULL
);

CREATE INDEX  organism_typegrporgprttp_IDX1 
    ON  organism_type_grp_orgparttypes (orgparttype_id) 

CREATE INDEX  organism_typegrporgprttp_IDX2 
    ON  organism_type_grp_orgparttypes (orgtypegrp_id) 

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

CREATE TABLE  orgparttype_synonyms (
    orgparttype_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
ALTER TABLE  orgparttype_synonyms ADD (
    CONSTRAINT  orgparttype_synonyms_PK 
    PRIMARY KEY ( orgparttype_id,synonym_id ) 
    USING INDEX 
); 

CREATE INDEX  orgparttype_synonyms_IDX1 
    ON  orgparttype_synonyms (synonym_id) 

-----------------------------------------------------
---                Preparation Types              ---
-----------------------------------------------------
CREATE TABLE  preparation_types (
    preptype_code  Varchar2(20) default null NOT NULL, 
    preptype_desc_eng  Varchar2(1000) default NULL , 
    preptype_desc_fr  Varchar2(1000) default NULL , 
    preptype_id Integer default null NOT NULL, 
    preptype_name_eng  Varchar2(100) default '' NOT NULL, 
    preptype_name_fr  Varchar2(100) default '' NOT NULL,
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

-----------------------------------------------------
---             Preparation Type Groups           ---
-----------------------------------------------------
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

-----------------------------------------------------
---  Preparation Types - Preparation Type Groups  ---
-----------------------------------------------------
CREATE TABLE  preptype_preptypegroups (
    preptype_id Integer default null NOT NULL, 
    preptypegrp_id Integer default null NOT NULL
);

ALTER TABLE  preptype_preptypegroups ADD (
    CONSTRAINT  preptype_preptypegroups_PK 
    PRIMARY KEY ( preptype_id,preptypegrp_id ) 
    USING INDEX 
); 


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
    journal_iso_abbrev  Varchar2(100) default NULL , 
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


CREATE TABLE  purpose_adminrts (
    adminrt_id Integer default null NOT NULL, 
    purpose_id Integer default null NOT NULL
);

ALTER TABLE  purpose_adminrts ADD (
    CONSTRAINT  purpose_adminrts_PK 
    PRIMARY KEY ( purpose_id,adminrt_id ) 
    USING INDEX 
); 

CREATE INDEX  purpose_adminrts_IDX1 
    ON  purpose_adminrts (adminrt_id) 

CREATE TABLE  purpose_synonyms (
    purpose_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);

ALTER TABLE  purpose_synonyms ADD (
    CONSTRAINT  purpose_synonyms_PK 
    PRIMARY KEY ( purpose_id,synonym_id ) 
    USING INDEX 
); 

CREATE INDEX  purpose_synonyms_IDX1 
    ON  purpose_synonyms (synonym_id) 

CREATE TABLE  purpose_test_specs (
    purpose_id Integer default null NOT NULL, 
    testcat_id Integer default null NOT NULL, 
    testspec_id Integer default null NOT NULL
);

ALTER TABLE  purpose_test_specs ADD (
    CONSTRAINT  purpose_test_specs_PK 
    PRIMARY KEY ( purpose_id,testcat_id,testspec_id ) 
    USING INDEX 
); 

CREATE INDEX  purpose_test_specs_IDX1 
    ON  purpose_test_specs (purpose_id) 

CREATE INDEX  purpose_test_specs_IDX2 
    ON  purpose_test_specs (testcat_id) 

CREATE INDEX  purpose_test_specs_IDX3 
    ON  purpose_test_specs (testspec_id) 

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

-- Qualified Synonyms

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

-- Join between Qualified Synonym and References.

CREATE TABLE QualifiedSynonym_References (
    qualsyno_id Integer NOT NULL,
    ref_id Integer NOT NULL
);

ALTER TABLE  QualifiedSynonym_References ADD (
    CONSTRAINT  QualifiedSynonym_References_PK 
    PRIMARY KEY ( qualsyno_id, ref_id ) 
    USING INDEX 
); 

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



CREATE TABLE  restriction_citations (
    citation_id Integer default null NOT NULL, 
    restrict_id Integer default null NOT NULL
);

ALTER TABLE  restriction_citations
    ADD CONSTRAINT  restriction_citations_UK1 
    UNIQUE (restrict_id,citation_id);


CREATE INDEX  restriction_citations_IDX1 
    ON  restriction_citations (citation_id) 

CREATE INDEX  restriction_citations_IDX2 
    ON  restriction_citations (restrict_id) 

CREATE TABLE  restriction_product_types (
    prodtype_id Integer default null NOT NULL, 
    restrict_id Integer default null NOT NULL
);

ALTER TABLE  restriction_product_types ADD (
    CONSTRAINT  restriction_product_types_PK 
    PRIMARY KEY ( restrict_id,prodtype_id ) 
    USING INDEX 
); 

CREATE INDEX  restriction_product_types_IDX1 
    ON  restriction_product_types (prodtype_id) 

CREATE INDEX  restriction_product_types_IDX2 
    ON  restriction_product_types (restrict_id) 

CREATE TABLE  restriction_purposes (
    purpose_id Integer default null NOT NULL, 
    restrict_id Integer default null NOT NULL
);
CREATE INDEX  restriction_purposes_IDX1 
    ON  restriction_purposes (purpose_id) 

CREATE INDEX  restriction_purposes_IDX2 
    ON  restriction_purposes (restrict_id) 

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


CREATE TABLE  restrictions (
    adminrt_id Integer default NULL , 
    ingredrole_id Integer default NULL , 
    ref_id Integer default null NOT NULL, 
    restrict_class_name  Varchar2(100) default '' NOT NULL, 
    restrict_creation_date Date NOT NULL, 
    restrict_detail_eng  Varchar2(1000) default NULL , 
    restrict_detail_fr  Varchar2(1000) default NULL , 
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

CREATE TABLE  risk_stmts_citations (
    	monoriskstmt_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

ALTER TABLE  risk_stmts_citations ADD (
    CONSTRAINT  risk_stmts_citations_PK 
    PRIMARY KEY ( monoriskstmt_id,citation_id ) 
    USING INDEX 
); 

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

CREATE TABLE  source_citations (
    	monosource_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

ALTER TABLE  source_citations ADD (
    CONSTRAINT  source_citations_PK 
    PRIMARY KEY ( monosource_id,citation_id ) 
    USING INDEX 
); 

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


CREATE TABLE  subingredient_admin_routes (
    adminrt_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_adminrt_IDX1 
    ON  subingredient_admin_routes (adminrt_id) 

CREATE INDEX  subingredient_adminrt_IDX2 
    ON  subingredient_admin_routes (subingred_id) 

CREATE TABLE  subingredient_dosage_forms (
    dosefrm_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_dosefrm_IDX1 
    ON  subingredient_dosage_forms (dosefrm_id) 

CREATE INDEX  subingredient_dosefrm_IDX2 
    ON  subingredient_dosage_forms (subingred_id) 

CREATE TABLE  subingredient_refs (
    ref_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_refs_IDX1 
    ON  subingredient_refs (ref_id) 

CREATE INDEX  subingredient_refs_IDX2 
    ON  subingredient_refs (subingred_id) 

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

CREATE TABLE  synonym_refs (
    ref_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
CREATE INDEX  synonym_refs_IDX1 
    ON  synonym_refs (ref_id); 

CREATE INDEX  synonym_refs_IDX2 
    ON  synonym_refs (synonym_id);

CREATE TABLE  synonyms (
    synonym_class_name  Varchar2(100) default NULL , 
    synonym_comment_eng  Varchar2(1000) default NULL , 
    synonym_comment_fr  Varchar2(1000) default NULL , 
    synonym_creation_date Date NOT NULL, 
    synonym_desc_eng  Varchar2(1000) default NULL , 
    synonym_desc_fr  Varchar2(1000) default NULL , 
    synonym_id Integer default null NOT NULL, 
    synonym_lastupdate_date Date NOT NULL, 
    synonym_name_eng  Varchar2(1000) default NULL, 
    synonym_name_fr  Varchar2(1000) default NULL, 
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

CREATE TABLE synonym_texts_eng_citations (
    synonym_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  synonym_texts_eng_citations ADD (
    CONSTRAINT  synonym_texts_eng_citats_PK 
    PRIMARY KEY ( synonym_id, citation_id ) 
    USING INDEX 
); 

CREATE TABLE synonym_texts_fr_citations (
    synonym_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  synonym_texts_fr_citations ADD (
    CONSTRAINT  synonym_texts_fr_citats_PK 
    PRIMARY KEY ( synonym_id, citation_id ) 
    USING INDEX 
); 

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


CREATE TABLE  test_method_categories (
    testcat_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  test_method_categories ADD (
    CONSTRAINT  test_method_categories_PK 
    PRIMARY KEY ( testmethod_id,testcat_id ) 
    USING INDEX 
); 

CREATE INDEX  test_method_categories_IDX1 
    ON  test_method_categories (testcat_id) 

CREATE INDEX  test_method_categories_IDX2 
    ON  test_method_categories (testmethod_id) 

CREATE TABLE  test_methods (
    testmethod_code  Varchar2(20) default null NOT NULL, 
    testmethod_creation_date Date , 
    testmethod_desc_eng  Varchar2(1000) default NULL , 
    testmethod_desc_fr  Varchar2(1000) default NULL , 
    testmethod_id Integer default null NOT NULL, 
    testmethod_lastupdate_date Date , 
    testmethod_name_eng  Varchar2(100) default null NOT NULL, 
    testmethod_name_fr  Varchar2(100) default null NOT NULL, 
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


CREATE TABLE  test_spec_tst_mtds (
    testmethod_id Integer default null NOT NULL, 
    testspec_id Integer default null NOT NULL
);
ALTER TABLE  test_spec_tst_mtds ADD (
    CONSTRAINT  test_spec_tst_mtds_PK 
    PRIMARY KEY ( testmethod_id,testspec_id ) 
    USING INDEX 
); 

CREATE INDEX  test_spec_tst_mtds_IDX1 
    ON  test_spec_tst_mtds (testmethod_id) 

CREATE INDEX  test_spec_tst_mtds_IDX2 
    ON  test_spec_tst_mtds (testspec_id) 

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


CREATE TABLE  use_citations (
    	monouse_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);

ALTER TABLE  use_citations ADD (
    CONSTRAINT  use_citations_PK 
    PRIMARY KEY ( monouse_id,citation_id ) 
    USING INDEX 
); 


CREATE TABLE  user_accounts (
    useracc_account_name  Varchar2(40) default '' NOT NULL, 
    useracc_creation_date Date NOT NULL, 
    useracc_email  Varchar2(200) default '' NOT NULL, 
    useracc_first_name  Varchar2(60) default '' NOT NULL, 
    useracc_id Integer default '0' NOT NULL, 
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

ALTER TABLE  adminrte_citations ADD (
    CONSTRAINT  adminrte_citations_FK1 
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins ( monoadminrt_id ));

ALTER TABLE  adminrte_citations ADD (
    CONSTRAINT  adminrte_citations_FK2 
    FOREIGN KEY (citation_id ) 
    REFERENCES citations ( citation_id ));

ALTER TABLE  administration_routes ADD 
    CONSTRAINT  administration_routes_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  application_text ADD 
    CONSTRAINT  application_text_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  attachments ADD 
    CONSTRAINT  attachments_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  biological_impurities ADD 
    CONSTRAINT  biological_impurities_FK1 
    FOREIGN KEY ( impurity_id ) 
    REFERENCES impurities ( impurity_id ); 

ALTER TABLE  biological_impurities ADD 
    CONSTRAINT  biological_impurities_FK2 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  biological_impurities ADD 
    CONSTRAINT  biological_impurities_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  registry_entries ADD 
    CONSTRAINT  registry_entries_FK1 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  registry_entries ADD 
    CONSTRAINT  registry_entries_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  chem_classes ADD 
    CONSTRAINT  chem_classes_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  chem_subclasses ADD 
    CONSTRAINT  chem_subclasses_FK1 
    FOREIGN KEY ( chemcl_id ) 
    REFERENCES chem_classes ( chemcl_id ); 

ALTER TABLE  chem_subclasses ADD 
    CONSTRAINT  chem_subclasses_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  citation_specials ADD 
    CONSTRAINT  citation_specials_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  citations ADD 
    CONSTRAINT  citations_FK1 
    FOREIGN KEY ( citationspec_class_name ) 
    REFERENCES citation_specials ( citationspec_class_name ); 

ALTER TABLE  citations ADD 
    CONSTRAINT  citations_FK2 
    FOREIGN KEY ( journal_id ) 
    REFERENCES published_journals ( journal_id ); 

ALTER TABLE  citations ADD 
    CONSTRAINT  citations_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE coded_risks ADD (
   CONSTRAINT coded_risks_FK1 
   FOREIGN KEY (meddrapref_id) 
   REFERENCES meddra_pref_terms (meddrapref_id));

ALTER TABLE coded_risks ADD (
   CONSTRAINT coded_risks_FK2 
   FOREIGN KEY (risktype_id) 
   REFERENCES risk_types (risktype_id));

ALTER TABLE  coded_risks ADD (
    CONSTRAINT  coded_risks_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE coded_risks_synonyms ADD (
   CONSTRAINT coded_risks_synonyms_FK1 
   FOREIGN KEY (codedrisk_id) 
   REFERENCES coded_risks (codedrisk_id));

ALTER TABLE coded_risks_synonyms ADD (
   CONSTRAINT coded_risks_synonyms_FK2 
   FOREIGN KEY (synonym_id) 
   REFERENCES synonyms (synonym_id));

ALTER TABLE  context_sensitive_citations ADD (
    CONSTRAINT  context_senstve_citations_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id )); 

ALTER TABLE  consencit_citations ADD (
    CONSTRAINT  consencit_citations_FK1
    FOREIGN KEY ( consencit_id ) 
    REFERENCES context_sensitive_citations ( consencit_id )); 

ALTER TABLE  consencit_citations ADD (
    CONSTRAINT  consencit_citations_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 
   
ALTER TABLE  countries ADD 
    CONSTRAINT  countries_FK1 
    FOREIGN KEY ( risk_id ) 
    REFERENCES risks ( risk_id ); 

ALTER TABLE  countries ADD 
    CONSTRAINT  countries_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  dosageform_citations ADD (
    CONSTRAINT  dosageform_citations_FK1 
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms ( monodosfrm_id));

ALTER TABLE  dosageform_citations ADD (
    CONSTRAINT  dosageform_citations_FK2 
    FOREIGN KEY (citation_id ) 
    REFERENCES citations ( citation_id ));

ALTER TABLE  dosageform_synonyms ADD (
    CONSTRAINT  dosageform_synonyms_FK1 
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id));

ALTER TABLE  dosageform_synonyms ADD (
    CONSTRAINT  dosageform_synonyms_FK2 
    FOREIGN KEY (synonym_id ) 
    REFERENCES synonyms ( synonym_id ));
    
ALTER TABLE  dosage_form_admin_routes ADD 
    CONSTRAINT  dosage_form_adminrt_FK1 
    FOREIGN KEY ( adminrt_id ) 
    REFERENCES administration_routes ( adminrt_id ); 

ALTER TABLE  dosage_form_admin_routes ADD 
    CONSTRAINT  dosage_form_adminrt_FK2 
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id ); 

ALTER TABLE  dosage_form_group_test_specs ADD 
    CONSTRAINT  dosage_frm_gp_ts_sp_FK1 
    FOREIGN KEY ( dosefrmgrp_id ) 
    REFERENCES dosage_form_groups ( dosefrmgrp_id ); 

ALTER TABLE  dosage_form_group_test_specs ADD 
    CONSTRAINT  dosage_frm_gp_ts_sp_FK2 
    FOREIGN KEY ( testcat_id ) 
    REFERENCES test_categories ( testcat_id ); 

ALTER TABLE  dosage_form_group_test_specs ADD 
    CONSTRAINT  dosage_frm_gp_ts_sp_FK3 
    FOREIGN KEY ( testspec_id ) 
    REFERENCES test_specifications ( testspec_id ); 

ALTER TABLE  dosage_form_groups ADD 
    CONSTRAINT  dosage_form_groups_FK1 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  dosage_form_groups ADD 
    CONSTRAINT  dosage_form_groups_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  dosage_form_test_specs ADD 
    CONSTRAINT  dosage_form_test_specs_FK1 
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id ); 

ALTER TABLE  dosage_form_test_specs ADD 
    CONSTRAINT  dosage_form_test_specs_FK2 
    FOREIGN KEY ( testcat_id ) 
    REFERENCES test_categories ( testcat_id ); 

ALTER TABLE  dosage_form_test_specs ADD 
    CONSTRAINT  dosage_form_test_specs_FK3 
    FOREIGN KEY ( testspec_id ) 
    REFERENCES test_specifications ( testspec_id ); 

ALTER TABLE  dosage_forms ADD 
    CONSTRAINT  dosage_forms_FK1 
    FOREIGN KEY ( dosefrmgrp_id ) 
    REFERENCES dosage_form_groups ( dosefrmgrp_id ); 

ALTER TABLE  dosage_forms ADD 
    CONSTRAINT  dosage_forms_FK2 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  dosage_forms ADD 
    CONSTRAINT  dosage_forms_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE dosage_ranges ADD (
   CONSTRAINT dosage_ranges_FK1 
   FOREIGN KEY (units_id) 
   REFERENCES units (units_id));

ALTER TABLE  dosage_ranges ADD (
    CONSTRAINT  dosage_ranges_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));


ALTER TABLE  endang_organism_countries ADD 
    CONSTRAINT  endang_organism_countries_FK1 
    FOREIGN KEY ( country_id ) 
    REFERENCES countries ( country_id ); 

ALTER TABLE  endang_organism_countries ADD 
    CONSTRAINT  endang_organism_countries_FK2 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  evidence_types ADD 
    CONSTRAINT  evidence_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE ex_org_grp_families ADD (
 CONSTRAINT ex_org_grp_families_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE ex_org_grp_families ADD (
  CONSTRAINT ex_org_grp_families_FK2 
 FOREIGN KEY (family_id) 
 REFERENCES families (family_id));

ALTER TABLE ex_org_grp_genera ADD (
 CONSTRAINT ex_org_grp_genera_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE ex_org_grp_genera ADD (
  CONSTRAINT ex_org_grp_genera_FK2 
 FOREIGN KEY (genus_id) 
 REFERENCES genera (genus_id));

ALTER TABLE ex_org_grp_organism ADD (
 CONSTRAINT ex_org_grp_organism_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE ex_org_grp_organism ADD (
  CONSTRAINT ex_org_grp_organism_FK2 
 FOREIGN KEY (organism_id) 
 REFERENCES organisms (organism_id));

ALTER TABLE ex_org_grp_species ADD (
 CONSTRAINT ex_org_grp_species_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE ex_org_grp_species ADD (
  CONSTRAINT ex_org_grp_species_FK2 
 FOREIGN KEY (species_id) 
 REFERENCES species (species_id));

ALTER TABLE ex_org_grp_subtaxa ADD (
 CONSTRAINT ex_org_grp_subtaxa_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE ex_org_grp_subtaxa ADD (
  CONSTRAINT ex_org_grp_subtaxa_FK2 
 FOREIGN KEY (subtaxa_id) 
 REFERENCES subtaxa (subtaxa_id)); 

ALTER TABLE  families ADD 
    CONSTRAINT  families_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  genera ADD 
    CONSTRAINT  genera_FK1 
    FOREIGN KEY ( family_id ) 
    REFERENCES families ( family_id ); 

ALTER TABLE  genera ADD 
    CONSTRAINT  genera_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  generic_texts_eng_citations ADD (
    CONSTRAINT  generic_texts_eng_citats_FK1
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  generic_texts_eng_citations ADD (
    CONSTRAINT  generic_texts_eng_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 

ALTER TABLE  generic_texts_fr_citations ADD (
    CONSTRAINT  generic_texts_fr_citats_FK1
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  generic_texts_fr_citations ADD (
    CONSTRAINT  generic_texts_fr_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 

ALTER TABLE  geographic_areas ADD 
    CONSTRAINT  geographic_areas_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  ich_class_test_methods ADD 
    CONSTRAINT  ich_class_test_methods_FK1 
    FOREIGN KEY ( ichclass_id ) 
    REFERENCES ich_classes ( ichclass_id ); 

ALTER TABLE  ich_class_test_methods ADD 
    CONSTRAINT  ich_class_test_methods_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  ich_classes ADD 
    CONSTRAINT  ich_classes_FK1 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  ich_classes ADD 
    CONSTRAINT  ich_classes_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE indication_admin_routes ADD (
   CONSTRAINT indication_admin_routes_FK1 
   FOREIGN KEY (indication_id) 
   REFERENCES indications (indication_id));

ALTER TABLE indication_admin_routes ADD (
   CONSTRAINT indication_admin_routes_FK2 
   FOREIGN KEY (adminrt_id) 
   REFERENCES ADMINISTRATION_ROUTES (adminrt_id));

ALTER TABLE  impurities ADD 
    CONSTRAINT  impurities_FK1 
    FOREIGN KEY ( impurityspec_class_name ) 
    REFERENCES impurity_specials ( impurityspec_class_name ); 

ALTER TABLE  impurities ADD 
    CONSTRAINT  impurities_FK2 
    FOREIGN KEY ( restrict_id ) 
    REFERENCES restrictions ( restrict_id ); 

ALTER TABLE  impurities ADD 
    CONSTRAINT  impurities_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  impurity_test_methods ADD 
    CONSTRAINT  impurity_test_methods_FK1 
    FOREIGN KEY ( impurity_id ) 
    REFERENCES impurities ( impurity_id ); 

ALTER TABLE  impurity_test_methods ADD 
    CONSTRAINT  impurity_test_methods_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE indications ADD (
   CONSTRAINT indications_FK1 
   FOREIGN KEY (meddrapref_id) 
   REFERENCES meddra_pref_terms (meddrapref_id));

ALTER TABLE indications ADD (
   CONSTRAINT indications_FK2 
   FOREIGN KEY (indictype_id) 
   REFERENCES indication_types (indictype_id));

ALTER TABLE  indications ADD (
    CONSTRAINT  indications_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  indication_types ADD (
    CONSTRAINT  indication_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  ingredient_bio_impurities ADD 
    CONSTRAINT  ingredient_bio_impurities_FK1 
    FOREIGN KEY ( bioimpurity_id ) 
    REFERENCES biological_impurities ( bioimpurity_id ); 

ALTER TABLE  ingredient_bio_impurities ADD 
    CONSTRAINT  ingredient_bio_impurities_FK2 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_reg_entries ADD 
    CONSTRAINT  ingredient_reg_entries_FK1 
    FOREIGN KEY ( regentry_id ) 
    REFERENCES registry_entries ( regentry_id ); 

ALTER TABLE  ingredient_reg_entries ADD 
    CONSTRAINT  ingredient_reg_entries_FK2 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_categories ADD 
    CONSTRAINT  ingredient_categories_FK1 
    FOREIGN KEY ( ingredspec_id ) 
    REFERENCES ingredient_specials ( ingredspec_id ); 

ALTER TABLE  ingredient_categories ADD 
    CONSTRAINT  ingredient_categories_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE  ingredient_impurities ADD 
    CONSTRAINT  ingredient_impurities_FK1 
    FOREIGN KEY ( impurity_id ) 
    REFERENCES impurities ( impurity_id ); 

ALTER TABLE  ingredient_impurities ADD 
    CONSTRAINT  ingredient_impurities_FK2 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_ingred_impurities ADD 
    CONSTRAINT  ingredient_ingred_impurts_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_ingred_impurities ADD 
    CONSTRAINT  ingredient_ingred_impurts_FK2 
    FOREIGN KEY ( ingred_id_impur ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_nhpclassifications ADD 
    CONSTRAINT  ingredient_nhpclass_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_nhpclassifications ADD 
    CONSTRAINT  ingredient_nhpclass_FK2 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  ingredient_orgparts ADD 
    CONSTRAINT  ingredient_orgparts_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_orgparts ADD 
    CONSTRAINT  ingredient_orgparts_FK2 
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id ); 

ALTER TABLE  ingredient_preptypes ADD 
    CONSTRAINT  ingredient_preptypes_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_preptypes ADD 
    CONSTRAINT  ingredient_preptypes_FK2 
    FOREIGN KEY ( preptype_id ) 
    REFERENCES preparation_types ( preptype_id ); 

ALTER TABLE  ingredient_qualsyno_qss ADD (
    CONSTRAINT  ingredient_qualsyno_qss_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  ingredient_qualsyno_qss ADD (
    CONSTRAINT  ingredient_qualsyno_qss_FK2
    FOREIGN KEY ( qualsyno_id ) 
    REFERENCES qualified_synonyms ( qualsyno_id )); 

ALTER TABLE  ingredient_qualsyno_cns ADD (
    CONSTRAINT  ingredient_qualsyno_cns_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  ingredient_qualsyno_cns ADD (
    CONSTRAINT  ingredient_qualsyno_cns_FK2
    FOREIGN KEY ( qualsyno_id ) 
    REFERENCES qualified_synonyms ( qualsyno_id )); 

ALTER TABLE  ingredient_qualsyno_pns ADD (
    CONSTRAINT  ingredient_qualsyno_pns_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  ingredient_qualsyno_pns ADD (
    CONSTRAINT  ingredient_qualsyno_pns_FK2
    FOREIGN KEY ( qualsyno_id ) 
    REFERENCES qualified_synonyms ( qualsyno_id )); 

ALTER TABLE  ingredient_role_purposes ADD 
    CONSTRAINT  ingredient_role_purposes_FK1 
    FOREIGN KEY ( purpose_id ) 
    REFERENCES purposes ( purpose_id ); 

ALTER TABLE  ingredient_roles ADD 
    CONSTRAINT  ingredient_roles_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_roles ADD 
    CONSTRAINT  ingredient_roles_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  ingredient_roles ADD 
    CONSTRAINT  ingredient_roles_FK3 
    FOREIGN KEY ( rationale_id) 
    REFERENCES rationales ( rationale_id); 

ALTER TABLE  ingredient_roles ADD 
    CONSTRAINT  ingredient_roles_FK4 
    FOREIGN KEY ( restrict_id) 
    REFERENCES restrictions ( restrict_id); 

ALTER TABLE  ingredient_specials  ADD 
    CONSTRAINT  ingredient_specials_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE  ingredient_std_grade_refs ADD 
    CONSTRAINT  ingredient_std_grade_refs_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_std_grade_refs ADD 
    CONSTRAINT  ingredient_std_grade_refs_FK2 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  ingredient_subingredients ADD 
    CONSTRAINT  ingredient_subingred_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_subingredients ADD 
    CONSTRAINT  ingredient_subingred_FK2 
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  ingredient_synonyms ADD 
    CONSTRAINT  ingredient_synonyms_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_synonyms ADD 
    CONSTRAINT  ingredient_synonyms_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ); 

ALTER TABLE  ingredient_test_methods ADD 
    CONSTRAINT  ingredient_test_methods_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_test_methods ADD 
    CONSTRAINT  ingredient_test_methods_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  ingredient_test_specs ADD 
    CONSTRAINT  ingredient_test_specs_FK1 
    FOREIGN KEY ( dosefrmgrp_id ) 
    REFERENCES dosage_form_groups ( dosefrmgrp_id ); 

ALTER TABLE  ingredient_test_specs ADD 
    CONSTRAINT  ingredient_test_specs_FK2 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  ingredient_test_specs ADD 
    CONSTRAINT  ingredient_test_specs_FK3 
    FOREIGN KEY ( testspec_id ) 
    REFERENCES test_specifications ( testspec_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK1 
    FOREIGN KEY ( attachment_id ) 
    REFERENCES attachments ( attachment_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK2 
    FOREIGN KEY ( chemsub_id ) 
    REFERENCES chem_subclasses ( chemsub_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK3 
    FOREIGN KEY ( ingredcat_id ) 
    REFERENCES ingredient_categories ( ingredcat_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK4 
    FOREIGN KEY ( ingredspec_class_name ) 
    REFERENCES ingredient_specials ( ingredspec_class_name ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK5 
    FOREIGN KEY ( ingredsrc_id ) 
    REFERENCES ingredient_sources ( ingredsrc_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK6 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK7 
    FOREIGN KEY ( restrict_id ) 
    REFERENCES restrictions ( restrict_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK8 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE ingredients ADD (
    CONSTRAINT ingredients_FK9 
    FOREIGN KEY (org_grp_id) 
    REFERENCES organism_groups (org_grp_id));

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK10 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK11 
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id ); 

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK12 
    FOREIGN KEY ( preptype_id ) 
    REFERENCES preparation_types ( preptype_id );

ALTER TABLE  ingredients ADD 
    CONSTRAINT  ingredients_FK13 
    FOREIGN KEY ( regentry_id ) 
    REFERENCES registry_entries ( regentry_id ); 

ALTER TABLE in_org_grp_families ADD (
 CONSTRAINT in_org_grp_families_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE in_org_grp_families ADD (
  CONSTRAINT in_org_grp_families_FK2 
 FOREIGN KEY (family_id) 
 REFERENCES families (family_id));

ALTER TABLE in_org_grp_genera ADD (
 CONSTRAINT in_org_grp_genera_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE in_org_grp_genera ADD (
  CONSTRAINT in_org_grp_genera_FK2 
 FOREIGN KEY (genus_id) 
 REFERENCES genera (genus_id));

ALTER TABLE in_org_grp_organism ADD (
 CONSTRAINT in_org_grp_organism_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE in_org_grp_organism ADD (
  CONSTRAINT in_org_grp_organism_FK2 
 FOREIGN KEY (organism_id) 
 REFERENCES organisms (organism_id));

ALTER TABLE in_org_grp_species ADD (
 CONSTRAINT in_org_grp_species_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE in_org_grp_species ADD (
  CONSTRAINT in_org_grp_species_FK2 
 FOREIGN KEY (species_id) 
 REFERENCES species (species_id));

ALTER TABLE in_org_grp_subtaxa ADD (
 CONSTRAINT in_org_grp_subtaxa_FK1 
 FOREIGN KEY (org_grp_id) 
 REFERENCES organism_groups (org_grp_id));

ALTER TABLE in_org_grp_subtaxa ADD (
  CONSTRAINT in_org_grp_subtaxa_FK2 
 FOREIGN KEY (subtaxa_id) 
 REFERENCES subtaxa (subtaxa_id)); 

ALTER TABLE  manufact_proc_bio_impurts ADD 
    CONSTRAINT  manufact_proc_bio_impurts_FK1 
    FOREIGN KEY ( bioimpurity_id ) 
    REFERENCES biological_impurities ( bioimpurity_id ); 

ALTER TABLE  manufact_proc_bio_impurts ADD 
    CONSTRAINT  manufact_proc_bio_impurts_FK2 
    FOREIGN KEY ( manproc_id ) 
    REFERENCES manufact_processes ( manproc_id ); 

ALTER TABLE  manufact_process_impurities ADD 
    CONSTRAINT  manufact_proc_impurts_FK1 
    FOREIGN KEY ( impurity_id ) 
    REFERENCES impurities ( impurity_id ); 

ALTER TABLE  manufact_process_impurities ADD 
    CONSTRAINT  manufact_proc_impurts_FK2 
    FOREIGN KEY ( manproc_id ) 
    REFERENCES manufact_processes ( manproc_id ); 

ALTER TABLE  manufact_process_ingredients ADD 
    CONSTRAINT  manufact_proc_ingreds_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  manufact_process_ingredients ADD 
    CONSTRAINT  manufact_proc_ingreds_FK2 
    FOREIGN KEY ( manproc_id ) 
    REFERENCES manufact_processes ( manproc_id ); 

ALTER TABLE  manufact_processes ADD 
    CONSTRAINT  manufact_processes_FK1 
    FOREIGN KEY ( attachment_id ) 
    REFERENCES attachments ( attachment_id ); 

ALTER TABLE  manufact_processes ADD 
    CONSTRAINT  manufact_processes_FK2 
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id ); 

ALTER TABLE  manufact_processes ADD 
    CONSTRAINT  manufact_processes_FK3 
    FOREIGN KEY ( ingredrole_id ) 
    REFERENCES ingredient_roles ( ingredrole_id ); 

ALTER TABLE  manufact_processes ADD 
    CONSTRAINT  manufact_processes_FK4 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  meddra_hlgt_hlts ADD 
    CONSTRAINT  meddra_hlgt_hlts_FK1 
    FOREIGN KEY ( hlgt_id ) 
    REFERENCES meddra_hlgts ( hlgt_id ); 

ALTER TABLE  meddra_hlgt_hlts ADD 
    CONSTRAINT  meddra_hlgt_hlts_FK2 
    FOREIGN KEY ( hlt_id ) 
    REFERENCES meddra_hlts ( hlt_id ); 

ALTER TABLE  meddra_hlt_pts ADD 
    CONSTRAINT  meddra_hlt_pts_FK1 
    FOREIGN KEY ( hlt_id ) 
    REFERENCES meddra_hlts ( hlt_id ); 

ALTER TABLE  meddra_hlt_pts ADD 
    CONSTRAINT  meddra_hlt_pts_FK2 
    FOREIGN KEY ( pt_id ) 
    REFERENCES meddra_pts ( pt_id ); 

ALTER TABLE  meddra_llts ADD 
    CONSTRAINT  meddra_llts_FK1 
    FOREIGN KEY ( pt_id ) 
    REFERENCES meddra_pts ( pt_id ); 

ALTER TABLE  meddra_soc_hlgts ADD 
    CONSTRAINT  meddra_soc_hlgts_FK1 
    FOREIGN KEY ( hlgt_id ) 
    REFERENCES meddra_hlgts ( hlgt_id ); 

ALTER TABLE  meddra_soc_hlgts ADD 
    CONSTRAINT  meddra_soc_hlgts_FK2 
    FOREIGN KEY ( soc_id ) 
    REFERENCES meddra_socs ( soc_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK2 
    FOREIGN KEY ( monogroup_id ) 
    REFERENCES monograph_groups ( monogroup_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK3 
    FOREIGN KEY ( general_notes_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK4 
    FOREIGN KEY ( dose_notes_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK5 
    FOREIGN KEY ( risk_notes_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK6 
    FOREIGN KEY ( nmi_notes_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK7 
    FOREIGN KEY ( spec_info_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK8 
    FOREIGN KEY ( stor_cond_gentext_id ) 
    REFERENCES generic_texts ( gentext_id ); 

ALTER TABLE  monograph_appendices ADD (
    CONSTRAINT  monograph_appendicies_FK1
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id )); 

ALTER TABLE  monograph_appendices ADD (
    CONSTRAINT  monograph_appendicies_FK2
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id )); 

ALTER TABLE  monograph_attachments ADD (
    CONSTRAINT  monograph_attachments_FK1 
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_attach_citations ADD (
    CONSTRAINT  monoatt_citations_FK1 
    FOREIGN KEY (monoatt_id) 
    REFERENCES monograph_attachments (monoatt_id));

ALTER TABLE  monograph_attach_citations ADD (
    CONSTRAINT  monoatt_citations_FK2 
    FOREIGN KEY (citation_id) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_attach_rationales ADD (
    CONSTRAINT  monoatt_rationales_FK1 
    FOREIGN KEY (monoatt_id) 
    REFERENCES monograph_attachments (monoatt_id));

ALTER TABLE  monograph_attach_rationales ADD (
    CONSTRAINT  monoatt_rationales_FK2 
    FOREIGN KEY (rationale_id) 
    REFERENCES rationales (rationale_id));

ALTER TABLE  monograph_citation_joins ADD (
    CONSTRAINT  monograph_citation_joins_FK1
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id )); 

ALTER TABLE  monograph_citation_joins ADD (
    CONSTRAINT  monograph_citation_joins_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK1 
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK2 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK3 
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK4 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK5 
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK6 
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK7 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_fr_uses_FK8
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 
    
ALTER TABLE  monograph_drfruse_citations ADD (
    CONSTRAINT  monograph_drfruse_citats_FK1
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses (monodfu_id));

ALTER TABLE  monograph_drfruse_citations ADD (
    CONSTRAINT  monograph_drfruse_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_drfruse_mono_uses ADD (
    CONSTRAINT  mono_drfruse_mono_uses_FK1
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses (monodfu_id));

ALTER TABLE  monograph_drfruse_mono_uses ADD (
    CONSTRAINT  mono_drfruse_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_drfruse_gentexts ADD (
    CONSTRAINT  monograph_drfruse_gentexts_FK1 
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses ( monodfu_id )); 

ALTER TABLE  monograph_drfruse_gentexts ADD (
    CONSTRAINT  monograph_drfruse_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  monograph_dosage_forms ADD 
    CONSTRAINT  monograph_dosage_forms_FK1
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id ); 

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK3 
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id ));
    
ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK4 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK5 
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK6 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK7 
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK8 
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK9 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_dosage_forms ADD (
    CONSTRAINT  monograph_dosage_forms_FK10 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ));

ALTER TABLE  monograph_dosage_form_notes ADD 
    CONSTRAINT  monograph_dosage_form_nots_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE  monograph_roa_monograph_dfns ADD 
    CONSTRAINT  monograph_roa_mnogrph_dfns_FK1 
    FOREIGN KEY ( monodosfrmnote_id ) 
    REFERENCES monograph_dosage_form_notes ( monodosfrmnote_id );

ALTER TABLE  monograph_roa_monograph_dfns ADD 
    CONSTRAINT  monograph_roa_mnogrph_dfns_FK2 
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins ( monoadminrt_id );
    
ALTER TABLE  monograph_dosfrm_citations ADD (
    CONSTRAINT  monograph_dosfrm_citations_FK1
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms (monodosfrm_id));

ALTER TABLE  monograph_dosfrm_citations ADD (
    CONSTRAINT  monograph_dosfrm_citations_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_dsgfrm_mono_uses ADD (
    CONSTRAINT  mono_dsgfrm_mono_uses_FK1
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms (monodosfrm_id));

ALTER TABLE  monograph_dsgfrm_mono_uses ADD (
    CONSTRAINT  mono_dsgfrm_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_dosfrm_gentexts ADD (
    CONSTRAINT  monograph_dosfrm_gentexts_FK1
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms ( monodosfrm_id )); 

ALTER TABLE  monograph_dosfrm_gentexts ADD (
    CONSTRAINT  monograph_dosfrm_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  monograph_dosfrm_orgparts ADD (
    CONSTRAINT  monograph_dosfrm_orgparts_FK1
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms ( monodosfrm_id )); 

ALTER TABLE  monograph_dosfrm_orgparts ADD (
    CONSTRAINT  monograph_dosfrm_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 

ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_FK1 
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses ( monodfu_id )); 

ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK2 
    FOREIGN KEY (subingred_id) 
    REFERENCES subingredients (subingred_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK3 
    FOREIGN KEY (units_id) 
    REFERENCES units (units_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK4 
    FOREIGN KEY (monodose_frequency_units_id) 
    REFERENCES units (units_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK5 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK6
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK7 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK8
    FOREIGN KEY (resttype_id) 
    REFERENCES restriction_types (resttype_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK9
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK10
    FOREIGN KEY ( monodose_dep_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK11
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK12
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 
    
ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_FK1 
    FOREIGN KEY (monodose_id) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_FK2 
    FOREIGN KEY (citation_id) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_dose_notes ADD 
    CONSTRAINT  monograph_dose_notes_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE  monograph_monograph_dosenotes ADD 
    CONSTRAINT  monograph_monograph_dosnts_FK1 
    FOREIGN KEY ( monodosenote_id ) 
    REFERENCES monograph_dose_notes ( monodosenote_id );

ALTER TABLE  monograph_monograph_dosenotes ADD 
    CONSTRAINT  monograph_monograph_dosnts_FK2 
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id );

ALTER TABLE  monograph_dose_rationales ADD (
    CONSTRAINT  monograph_dose_rationales_FK1 
    FOREIGN KEY (monodose_id) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_rationales ADD (
    CONSTRAINT  monograph_dose_rationales_FK2 
    FOREIGN KEY (rationale_id) 
    REFERENCES rationales (rationale_id));

ALTER TABLE  monograph_dose_shrd_monographs ADD (
    CONSTRAINT  monograph_dose_shrd_mono_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_shrd_monographs ADD (
    CONSTRAINT  monograph_dose_shrd_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_dose_dist_monographs ADD (
    CONSTRAINT  monograph_dose_dist_mono_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_dist_monographs ADD (
    CONSTRAINT  monograph_dose_dist_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  mono_dose_mono_uses_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  mono_dose_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_dose_shrd_mono_dfus ADD (
    CONSTRAINT  mono_dose_shrd_mono_dfu_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));
  
ALTER TABLE  monograph_dose_shrd_mono_dfus ADD (
    CONSTRAINT  mono_dose_shrd_mono_dfu_FK2
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses (monodfu_id));
  
ALTER TABLE  monograph_dose_dist_mono_dfus ADD (
    CONSTRAINT  mono_dose_dist_mono_dfu_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_dist_mono_dfus ADD (
    CONSTRAINT  mono_dose_dist_mono_dfu_FK2
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses (monodfu_id));

ALTER TABLE  monograph_dose_gentexts ADD (
    CONSTRAINT  monodose_dose_gentexts_FK1 
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_dose_gentexts ADD (
    CONSTRAINT  monodose_dose_gentexts_FK2 
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  monograph_dose_orgparts ADD (
    CONSTRAINT  monodose_dose_orgparts_FK1 
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_dose_orgparts ADD (
    CONSTRAINT  monodose_dose_orgparts_FK2 
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 

ALTER TABLE  monograph_dosage_frm_dpnds ADD 
    CONSTRAINT  monograph_dosage_frm_dpnds_FK1
    FOREIGN KEY ( monodosfrm_id ) 
    REFERENCES monograph_dosage_forms ( monodosfrm_id ); 

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK3 
    FOREIGN KEY (units_id) 
    REFERENCES units (units_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK4 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK5
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK6 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK7 
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK8
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK9
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK10
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  monograph_duration_citations ADD (
    CONSTRAINT  monograph_durat_citations_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations (monodur_id));

ALTER TABLE  monograph_duration_citations ADD (
    CONSTRAINT  monograph_durat_citations_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));   

ALTER TABLE  monograph_duration_orgparts ADD (
    CONSTRAINT  monodose_duration_orgparts_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations ( monodur_id )); 

ALTER TABLE  monograph_duration_orgparts ADD (
    CONSTRAINT  monodose_duration_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 
  
ALTER TABLE  monograph_dur_shrd_monographs ADD (
    CONSTRAINT  monograph_dur_shrd_mono_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations (monodur_id));

ALTER TABLE  monograph_dur_shrd_monographs ADD (
    CONSTRAINT  monograph_dur_shrd_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_dur_dist_monographs ADD (
    CONSTRAINT  monograph_dur_dist_mono_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations (monodur_id));

ALTER TABLE  monograph_dur_dist_monographs ADD (
    CONSTRAINT  monograph_dur_dist_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_dur_mono_uses ADD (
    CONSTRAINT  mono_dur_mono_uses_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations (monodur_id));

ALTER TABLE  monograph_dur_mono_uses ADD (
    CONSTRAINT  mono_dur_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_duration_gentexts ADD (
    CONSTRAINT  monodose_duration_gentexts_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations ( monodur_id )); 

ALTER TABLE  monograph_duration_gentexts ADD (
    CONSTRAINT  monodose_duration_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 
   
ALTER TABLE  monograph_entries ADD (
    CONSTRAINT  monograph_entries_FK1 
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_entries ADD (
    CONSTRAINT  monograph_entries_FK2 
    FOREIGN KEY (ingred_id) 
    REFERENCES ingredients (ingred_id));

ALTER TABLE  monograph_entries ADD (
    CONSTRAINT  monograph_entries_FK3 
    FOREIGN KEY (useracc_id) 
    REFERENCES user_accounts ( useracc_id )); 

ALTER TABLE  monograph_entries_citations ADD (
    CONSTRAINT  monograph_entries_citats_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_citations ADD (
    CONSTRAINT  monograph_entries_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));
  
ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts (gentext_id));

ALTER TABLE monograph_entries_cn_gentexts ADD (
    CONSTRAINT monograph_entries_cn_gntxs_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));
  
ALTER TABLE monograph_entries_cn_gentexts ADD (
    CONSTRAINT monograph_entries_cn_gntxs_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts (gentext_id));

ALTER TABLE  monograph_entries_preptypes ADD (
    CONSTRAINT  monograph_entries_prptps_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_preptypes ADD (
    CONSTRAINT  monograph_entries_prptps_FK2
    FOREIGN KEY ( preptype_id ) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_entries_preptypegrps ADD (
    CONSTRAINT  monograph_entrs_prptpgrps_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_preptypegrps ADD (
    CONSTRAINT  monograph_entrs_prptpgrps_FK2
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups (preptypegrp_id));

ALTER TABLE  monograph_entries_orgparts ADD (
    CONSTRAINT  monograph_entries_orgparts_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_orgparts ADD (
    CONSTRAINT  monograph_entries_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts (orgpart_id));

ALTER TABLE  monograph_entries_src_ingreds ADD (
    CONSTRAINT  monograph_entrs_src_igrds_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_src_ingreds ADD (
    CONSTRAINT  monograph_entrs_src_igrds_FK2
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients (ingred_id));

ALTER TABLE  monograph_entries_commonnames ADD (
    CONSTRAINT  monograph_entries_comnames_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_commonnames ADD (
    CONSTRAINT  monograph_entries_comnames_FK2
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms (synonym_id));

ALTER TABLE  monograph_entries_synonyms ADD (
    CONSTRAINT  monograph_entries_synonyms_FK1
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));

ALTER TABLE  monograph_entries_synonyms ADD (
    CONSTRAINT  monograph_entries_synonyms_FK2
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms (synonym_id));

ALTER TABLE monograph_generic_texts ADD (
    CONSTRAINT monograph_gentext_FK1 
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));
  
ALTER TABLE monograph_generic_texts ADD (
    CONSTRAINT monograph_gentext_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts (gentext_id));

ALTER TABLE  monograph_groups ADD (
    CONSTRAINT  monograph_groups_FK1
    FOREIGN KEY (useracc_id) 
    REFERENCES user_accounts ( useracc_id )); 
    
ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK3 
    FOREIGN KEY ( llt_id ) 
    REFERENCES meddra_llts ( llt_id ));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK4 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK5
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK6 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK7
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK8
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK9
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK10
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  monograph_risk_shrd_monographs ADD (
    CONSTRAINT  monograph_risk_shrd_mono_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_risk_shrd_monographs ADD (
    CONSTRAINT  monograph_risk_shrd_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_risk_dist_monographs ADD (
    CONSTRAINT  monograph_risk_dist_mono_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_risk_dist_monographs ADD (
    CONSTRAINT  monograph_risk_dist_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   
    
ALTER TABLE  monograph_riskstmt_citations ADD (
    CONSTRAINT  monograph_riskstmt_citats_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_riskstmt_citations ADD (
    CONSTRAINT  monograph_riskstmt_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_riskstmt_orgparts ADD (
    CONSTRAINT  monograph_rskstmt_orgparts_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts ( monoriskstmt_id )); 

ALTER TABLE  monograph_riskstmt_orgparts ADD (
    CONSTRAINT  monograph_rskstmt_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 

ALTER TABLE  monograph_rskstm_mono_uses ADD (
    CONSTRAINT  mono_rskstm_mono_uses_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_rskstm_mono_uses ADD (
    CONSTRAINT  mono_rskstm_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_riskstmt_gentexts ADD (
    CONSTRAINT  monograph_rskstmt_gentexts_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts ( monoriskstmt_id )); 

ALTER TABLE  monograph_riskstmt_gentexts ADD (
    CONSTRAINT  monograph_rskstmt_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 
    
ALTER TABLE  monograph_routeofadmins ADD (
    CONSTRAINT  monograph_routeofadmins_FK1 
    FOREIGN KEY ( adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_routeofadmins ADD (
    CONSTRAINT  monograph_routeofadmins_FK2
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts (useracc_id));

ALTER TABLE  monograph_rteofadmns_citations ADD (
    CONSTRAINT  monograph_rteofadmns_cits_FK1
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins (monoadminrt_id));

ALTER TABLE  monograph_rteofadmns_citations ADD (
    CONSTRAINT  monograph_rteofadmns_cits_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_roa_shrd_monographs ADD (
    CONSTRAINT  monograph_roa_shrd_mono_FK1
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins (monoadminrt_id));

ALTER TABLE  monograph_roa_shrd_monographs ADD (
    CONSTRAINT  monograph_roa_shrd_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_roa_dist_monographs ADD (
    CONSTRAINT  monograph_roa_dist_mono_FK1
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins (monoadminrt_id));

ALTER TABLE  monograph_roa_dist_monographs ADD (
    CONSTRAINT  monograph_roa_dist_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_sources ADD 
    CONSTRAINT  monograph_sources_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id ); 

ALTER TABLE monograph_subingdoses ADD (
    CONSTRAINT  monograph_subingdoses_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts (useracc_id));

ALTER TABLE monograph_subingdoses ADD
    CONSTRAINT  monograph_subingdoses_FK2 
    FOREIGN KEY ( monosubingdose_dose_units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE monograph_subingdoses ADD
    CONSTRAINT  monograph_subingdoses_FK3 
    FOREIGN KEY ( monosubingdose_freq_units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE monograph_subingdoses ADD
    CONSTRAINT  monograph_subingdoses_FK4
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  monograph_subingdoses ADD (
    CONSTRAINT  monograph_subingdoses_FK5
    FOREIGN KEY (resttype_id) 
    REFERENCES restriction_types (resttype_id));

ALTER TABLE  monograph_subingdose_cttns ADD (
    CONSTRAINT  monograph_subingdose_cttns_FK1 
    FOREIGN KEY (monosubingdose_id) 
    REFERENCES monograph_subingdoses (monosubingdose_id));

ALTER TABLE  monograph_subingdose_cttns ADD (
    CONSTRAINT  monograph_subingdose_cttns_FK2 
    FOREIGN KEY (citation_id) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_subingdose_rtnls ADD (
    CONSTRAINT  monograph_subingdose_rtnls_FK1 
    FOREIGN KEY (monosubingdose_id) 
    REFERENCES monograph_subingdoses (monosubingdose_id));

ALTER TABLE  monograph_subingdose_rtnls ADD (
    CONSTRAINT  monograph_subingdose_rtnls_FK2 
    FOREIGN KEY (rationale_id) 
    REFERENCES rationales (rationale_id));
    
ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_sub_populations_FK1
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_sub_populations_FK2
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));    
    
ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK1 
    FOREIGN KEY ( synonym_id) 
    REFERENCES SYNONYMS (synonym_id));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK2
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK3 
    FOREIGN KEY (adminrt_id) 
    REFERENCES administration_routes (adminrt_id));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK4
    FOREIGN KEY (preptype_id) 
    REFERENCES preparation_types (preptype_id));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK5 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK6
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK7
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses ( monodose_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK8
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK9
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK10
    FOREIGN KEY ( usetype_id ) 
    REFERENCES use_types ( usetype_id )); 

ALTER TABLE  monograph_uses_shrd_monographs ADD (
    CONSTRAINT  monograph_uses_shrd_mono_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_uses_shrd_monographs ADD (
    CONSTRAINT  monograph_uses_shrd_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   

ALTER TABLE  monograph_uses_dist_monographs ADD (
    CONSTRAINT  monograph_uses_dist_mono_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_uses_dist_monographs ADD (
    CONSTRAINT  monograph_uses_dist_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   
   
ALTER TABLE  monograph_citations ADD (
    CONSTRAINT  monograph_citations_FK1 
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id ));

ALTER TABLE  monograph_citations ADD (
    CONSTRAINT  monograph_citations_FK2 
    FOREIGN KEY (citation_id ) 
    REFERENCES citations ( citation_id ));

ALTER TABLE  monograph_use_citations ADD (
    CONSTRAINT  monograph_use_citations_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_use_citations ADD (
    CONSTRAINT  monograph_use_citations_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_use_orgparts ADD (
    CONSTRAINT  monograph_use_orgparts_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses ( monouse_id )); 

ALTER TABLE  monograph_use_orgparts ADD (
    CONSTRAINT  monograph_use_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id )); 

ALTER TABLE  monograph_use_mono_uses ADD (
    CONSTRAINT  mono_use_mono_uses_FK1
    FOREIGN KEY ( monousedep_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_use_mono_uses ADD (
    CONSTRAINT  mono_use_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_use_gentexts ADD (
    CONSTRAINT  monograph_use_gentexts_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses ( monouse_id )); 

ALTER TABLE  monograph_use_gentexts ADD (
    CONSTRAINT  monograph_use_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 

ALTER TABLE  monoentry_subpopulation ADD (
    CONSTRAINT  monoentry_subpopulation_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id ));

ALTER TABLE  monoentry_subpopulation ADD (
    CONSTRAINT  monoentry_subpopulation_FK2 
    FOREIGN KEY ( subpop_id ) 
    REFERENCES sub_population ( subpop_id ));

ALTER TABLE  nhp_class_bio_impurities ADD 
    CONSTRAINT  nhp_class_bio_impurities_FK1 
    FOREIGN KEY ( bioimpurity_id ) 
    REFERENCES biological_impurities ( bioimpurity_id ); 

ALTER TABLE  nhp_class_bio_impurities ADD 
    CONSTRAINT  nhp_class_bio_impurities_FK2 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  nhp_class_idnt_test_methods ADD 
    CONSTRAINT  nhp_cls_idnt_tst_mtds_FK1 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  nhp_class_idnt_test_methods ADD 
    CONSTRAINT  nhp_cls_idnt_tst_mtds_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  nhp_class_impurities ADD 
    CONSTRAINT  nhp_class_impurities_FK1 
    FOREIGN KEY ( impurity_id ) 
    REFERENCES impurities ( impurity_id ); 

ALTER TABLE  nhp_class_impurities ADD 
    CONSTRAINT  nhp_class_impurities_FK2 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  nhp_class_ingredients ADD 
    CONSTRAINT  nhp_class_ingredients_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  nhp_class_ingredients ADD 
    CONSTRAINT  nhp_class_ingredients_FK2 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  nhp_class_qty_test_methods ADD 
    CONSTRAINT  nhp_class_qty_test_methods_FK1 
    FOREIGN KEY ( nhpclass_id ) 
    REFERENCES nhp_classifications ( nhpclass_id ); 

ALTER TABLE  nhp_class_qty_test_methods ADD 
    CONSTRAINT  nhp_class_qty_test_methods_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  nhp_classifications ADD 
    CONSTRAINT  nhp_classifications_FK1 
    FOREIGN KEY ( qualitymodel_id ) 
    REFERENCES quality_tolerance_models ( qualitymodel_id ); 

ALTER TABLE  nhp_classifications ADD 
    CONSTRAINT  nhp_classifications_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  organism_countries ADD 
    CONSTRAINT  organism_countries_FK1 
    FOREIGN KEY ( country_id ) 
    REFERENCES countries ( country_id ); 

ALTER TABLE  organism_countries ADD 
    CONSTRAINT  organism_countries_FK2 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  organism_geographic_areas ADD 
    CONSTRAINT  organism_geographic_areas_FK1 
    FOREIGN KEY ( geogarea_id ) 
    REFERENCES geographic_areas ( geogarea_id ); 

ALTER TABLE  organism_geographic_areas ADD 
    CONSTRAINT  organism_geographic_areas_FK2 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  organism_part_subingredients ADD 
    CONSTRAINT  organism_part_subingred_FK1 
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id ); 

ALTER TABLE  organism_part_subingredients ADD 
    CONSTRAINT  organism_part_subingred_FK2 
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  organism_part_types ADD 
    CONSTRAINT  organism_part_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  organism_parts ADD 
    CONSTRAINT  organism_parts_FK1 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  organism_parts ADD 
    CONSTRAINT  organism_parts_FK2 
    FOREIGN KEY ( orgparttype_id ) 
    REFERENCES organism_part_types ( orgparttype_id ); 

ALTER TABLE  organism_parts ADD 
    CONSTRAINT  organism_parts_FK3 
    FOREIGN KEY ( org_grp_id ) 
    REFERENCES organism_groups ( org_grp_id ); 

ALTER TABLE  organism_parts ADD 
    CONSTRAINT  organism_parts_FK4
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 	 	

ALTER TABLE  organism_synonyms ADD 
    CONSTRAINT  organism_synonyms_FK1 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  organism_synonyms ADD 
    CONSTRAINT  organism_synonyms_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ); 

ALTER TABLE  organism_type_groups ADD 
    CONSTRAINT  organism_type_groups_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );  

ALTER TABLE  organism_type_grp_orgparttypes ADD 
    CONSTRAINT  organism_typegrporgprttp_FK1 
    FOREIGN KEY ( orgparttype_id ) 
    REFERENCES organism_part_types ( orgparttype_id ); 

ALTER TABLE  organism_type_grp_orgparttypes ADD 
    CONSTRAINT  organism_typegrporgprttp_FK2 
    FOREIGN KEY ( orgtypegrp_id ) 
    REFERENCES organism_type_groups ( orgtypegrp_id ); 

ALTER TABLE  organism_types ADD 
    CONSTRAINT  organism_types_FK1 
    FOREIGN KEY ( orgtypegrp_id ) 
    REFERENCES organism_type_groups ( orgtypegrp_id ); 

ALTER TABLE  organism_types ADD 
    CONSTRAINT  organism_types_FK2
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 	  

ALTER TABLE  organisms ADD 
    CONSTRAINT  organisms_FK1 
    FOREIGN KEY ( orgtype_id ) 
    REFERENCES organism_types ( orgtype_id ); 

ALTER TABLE  organisms ADD 
    CONSTRAINT  organisms_FK2 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  organisms ADD 
    CONSTRAINT  organisms_FK3 
    FOREIGN KEY ( species_id ) 
    REFERENCES species ( species_id ); 

ALTER TABLE  organisms ADD 
    CONSTRAINT  organisms_FK4 
    FOREIGN KEY ( subtaxa_id ) 
    REFERENCES subtaxa ( subtaxa_id ); 

ALTER TABLE  organisms ADD 
    CONSTRAINT  organisms_FK5 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  organism_groups ADD 
    CONSTRAINT  org_grp_FK2 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  organism_groups ADD 
    CONSTRAINT  org_grp_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 
 
ALTER TABLE  organism_qualifiedsynonyms ADD (
    CONSTRAINT  organism_qualifiedsynonyms_FK1 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id )); 

ALTER TABLE  organism_qualifiedsynonyms ADD (
    CONSTRAINT  organism_qualifiedsynonyms_FK2
    FOREIGN KEY ( qualsyno_id ) 
    REFERENCES qualified_synonyms ( qualsyno_id )); 

ALTER TABLE  orgparttype_synonyms ADD 
    CONSTRAINT  orgparttype_synonyms_FK1 
    FOREIGN KEY ( orgparttype_id ) 
    REFERENCES organism_part_types ( orgparttype_id ); 

ALTER TABLE  orgparttype_synonyms ADD 
    CONSTRAINT  orgparttype_synonyms_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ); 

ALTER TABLE  preparation_types  ADD 
    CONSTRAINT  preparation_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 	 

ALTER TABLE  preparation_type_groups  ADD 
    CONSTRAINT  preparation_type_groups_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 	 

ALTER TABLE preptype_preptypegroups ADD
    CONSTRAINT  preptype_preptypegroups_FK1 
    FOREIGN KEY ( preptype_id ) 
    REFERENCES preparation_types ( preptype_id ); 	 

ALTER TABLE preptype_preptypegroups ADD
    CONSTRAINT  preptype_preptypegroups_FK2 
    FOREIGN KEY ( preptypegrp_id ) 
    REFERENCES preparation_type_groups ( preptypegrp_id ); 	 

ALTER TABLE  product_types ADD 
    CONSTRAINT  product_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  published_journals ADD 
    CONSTRAINT  published_journals_FK1 
    FOREIGN KEY ( ref_id  ) 
    REFERENCES refs ( ref_id  ); 

ALTER TABLE  published_journals ADD 
    CONSTRAINT  published_journals_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  purpose_adminrts ADD 
    CONSTRAINT  purpose_adminrts_FK1 
    FOREIGN KEY ( adminrt_id ) 
    REFERENCES administration_routes ( adminrt_id ); 

ALTER TABLE  purpose_adminrts ADD 
    CONSTRAINT  purpose_adminrts_FK2 
    FOREIGN KEY ( purpose_id ) 
    REFERENCES purposes ( purpose_id ); 

ALTER TABLE  purpose_synonyms ADD 
    CONSTRAINT  purpose_synonyms_FK1 
    FOREIGN KEY ( purpose_id ) 
    REFERENCES purposes ( purpose_id ); 

ALTER TABLE  purpose_synonyms ADD 
    CONSTRAINT  purpose_synonyms_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ); 

ALTER TABLE  purpose_test_specs ADD 
    CONSTRAINT  purpose_test_specs_FK1 
    FOREIGN KEY ( purpose_id ) 
    REFERENCES purposes ( purpose_id ); 

ALTER TABLE  purpose_test_specs ADD 
    CONSTRAINT  purpose_test_specs_FK2 
    FOREIGN KEY ( testcat_id ) 
    REFERENCES test_categories ( testcat_id ); 

ALTER TABLE  purpose_test_specs ADD 
    CONSTRAINT  purpose_test_specs_FK3 
    FOREIGN KEY ( testspec_id ) 
    REFERENCES test_specifications ( testspec_id ); 

ALTER TABLE  purposes ADD 
    CONSTRAINT  purposes_FK1 
    FOREIGN KEY ( adminrt_id ) 
    REFERENCES administration_routes ( adminrt_id ); 

ALTER TABLE  purposes ADD 
    CONSTRAINT  purposes_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  qualified_synonyms ADD (
    CONSTRAINT  qualified_synonyms_FK1
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id )); 

ALTER TABLE  qualified_synonyms ADD (
    CONSTRAINT  qualified_synonyms_FK2
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id )); 

ALTER TABLE  quality_tolerance_models ADD 
    CONSTRAINT  quality_tolerance_models_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  QualifiedSynonym_References ADD (
    CONSTRAINT  QualifiedSynonym_Refs_FK1
    FOREIGN KEY ( qualsyno_id ) 
    REFERENCES qualified_synonyms ( qualsyno_id )); 

ALTER TABLE  QualifiedSynonym_References ADD (
    CONSTRAINT  QualifiedSynonym_Refs_FK2
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id )); 

ALTER TABLE  rationales ADD 
    CONSTRAINT  rationales_FK1 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  rationales ADD 
    CONSTRAINT  rationales_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  refs ADD 
    CONSTRAINT  refs_FK1 
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id ); 

ALTER TABLE  refs ADD 
    CONSTRAINT  refs_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  restriction_citations ADD 
    CONSTRAINT  restriction_citations_FK1 
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id ); 

ALTER TABLE  restriction_citations ADD 
    CONSTRAINT  restriction_citations_FK2 
    FOREIGN KEY ( restrict_id ) 
    REFERENCES restrictions ( restrict_id ); 

ALTER TABLE  restriction_product_types ADD 
    CONSTRAINT  restriction_product_types_FK1 
    FOREIGN KEY ( prodtype_id ) 
    REFERENCES product_types ( prodtype_id ); 

ALTER TABLE  restriction_product_types ADD 
    CONSTRAINT  restriction_product_types_FK2 
    FOREIGN KEY ( restrict_id ) 
    REFERENCES restrictions ( restrict_id ); 

ALTER TABLE  restriction_purposes ADD 
    CONSTRAINT  restriction_purposes_FK1 
    FOREIGN KEY ( purpose_id ) 
    REFERENCES purposes ( purpose_id ); 

ALTER TABLE  restriction_purposes ADD 
    CONSTRAINT  restriction_purposes_FK2 
    FOREIGN KEY ( restrict_id ) 
    REFERENCES restrictions ( restrict_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK1 
    FOREIGN KEY ( adminrt_id ) 
    REFERENCES administration_routes ( adminrt_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK2 
    FOREIGN KEY ( ingredrole_id ) 
    REFERENCES ingredient_roles ( ingredrole_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK3 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK4 
    FOREIGN KEY ( resttype_id ) 
    REFERENCES restriction_types ( resttype_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK5 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  restrictions ADD 
    CONSTRAINT  restrictions_FK6 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  restriction_types ADD 
    CONSTRAINT  restriction_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 	 

ALTER TABLE  risks ADD 
    CONSTRAINT  risks_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  risk_types ADD (
    CONSTRAINT  risk_types_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  risk_stmts_citations ADD (
    CONSTRAINT  risk_stmts_citations_FK1 
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts ( monoriskstmt_id ));

ALTER TABLE  risk_stmts_citations ADD (
    CONSTRAINT  risk_stmts_citations_FK2 
    FOREIGN KEY (citation_id ) 
    REFERENCES citations ( citation_id ));

ALTER TABLE  role_specials ADD 
    CONSTRAINT  role_specials_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  solvents ADD 
    CONSTRAINT  solvents_FK1 
    FOREIGN KEY ( ichclass_id ) 
    REFERENCES ich_classes ( ichclass_id ); 
    
ALTER TABLE  source_citations ADD (
    CONSTRAINT  source_citations_FK1 
    FOREIGN KEY ( monosource_id ) 
    REFERENCES monograph_sources ( monosource_id ));

ALTER TABLE  source_citations ADD (
    CONSTRAINT  source_citations_FK2 
    FOREIGN KEY (citation_id ) 
    REFERENCES citations ( citation_id ));

ALTER TABLE  species ADD 
    CONSTRAINT  species_FK1 
    FOREIGN KEY ( genus_id ) 
    REFERENCES genera ( genus_id ); 

ALTER TABLE  species ADD 
    CONSTRAINT  species_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  subingredient_admin_routes ADD 
    CONSTRAINT  subingredient_adminrt_FK1 
    FOREIGN KEY ( adminrt_id ) 
    REFERENCES administration_routes ( adminrt_id ); 

ALTER TABLE  subingredient_admin_routes ADD 
    CONSTRAINT  subingredient_adminrt_FK2 
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  subingredient_dosage_forms ADD 
    CONSTRAINT  subingredient_dosefrm_FK1 
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id ); 

ALTER TABLE  subingredient_dosage_forms ADD 
    CONSTRAINT  subingredient_dosefrm_FK2 
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  subingredient_refs ADD 
    CONSTRAINT  subingredient_refs_FK1 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  subingredient_refs ADD 
    CONSTRAINT  subingredient_refs_FK2 
    FOREIGN KEY ( subingred_id ) 
    REFERENCES subingredients ( subingred_id ); 

ALTER TABLE  subingredients ADD 
    CONSTRAINT  subingredients_FK1 
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  subingredients ADD 
    CONSTRAINT  subingredients_FK2 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  subingredients ADD 
    CONSTRAINT  subingredients_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  subtaxa ADD 
    CONSTRAINT  subtaxa_FK1 
    FOREIGN KEY ( species_id ) 
    REFERENCES species ( species_id ); 

ALTER TABLE  subtaxa ADD 
    CONSTRAINT  subtaxa_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  sub_population ADD (
    CONSTRAINT  sub_population_FK1 
    FOREIGN KEY ( units_id_max) 
    REFERENCES units (units_id));

ALTER TABLE  sub_population ADD (
    CONSTRAINT  sub_population_FK2
    FOREIGN KEY ( units_id_min ) 
    REFERENCES units (units_id));

ALTER TABLE  sub_population ADD (
    CONSTRAINT  sub_population_FK3
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  synonym_refs ADD 
    CONSTRAINT  synonym_refs_FK1 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  synonym_refs ADD 
    CONSTRAINT  synonym_refs_FK2 
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id ); 

ALTER TABLE  synonyms ADD 
    CONSTRAINT  synonyms_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  synonyms ADD 
    CONSTRAINT  synonyms_FK2 
    FOREIGN KEY ( indication_id ) 
    REFERENCES indications ( indication_id ); 

ALTER TABLE  synonyms ADD 
    CONSTRAINT  synonyms_FK3 
    FOREIGN KEY ( tradparadigm_id ) 
    REFERENCES trad_paradigms ( tradparadigm_id ); 

ALTER TABLE  synonym_texts_eng_citations ADD (
    CONSTRAINT  synonym_texts_eng_citats_FK1
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id )); 

ALTER TABLE  synonym_texts_eng_citations ADD (
    CONSTRAINT  synonym_texts_eng_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 

ALTER TABLE  synonym_texts_fr_citations ADD (
    CONSTRAINT  synonym_texts_fr_citats_FK1
    FOREIGN KEY ( synonym_id ) 
    REFERENCES synonyms ( synonym_id )); 

ALTER TABLE  synonym_texts_fr_citations ADD (
    CONSTRAINT  synonym_texts_fr_citats_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 
    
ALTER TABLE  taxa ADD 
    CONSTRAINT  taxa_FK1 
    FOREIGN KEY ( organism_id ) 
    REFERENCES organisms ( organism_id ); 

ALTER TABLE  taxa ADD 
    CONSTRAINT  taxa_FK2 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  taxa ADD 
    CONSTRAINT  taxa_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  test_categories ADD 
    CONSTRAINT  test_categories_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  test_method_categories ADD 
    CONSTRAINT  test_method_categories_FK1 
    FOREIGN KEY ( testcat_id ) 
    REFERENCES test_categories ( testcat_id ); 

ALTER TABLE  test_method_categories ADD 
    CONSTRAINT  test_method_categories_FK2 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  test_methods ADD 
    CONSTRAINT  test_methods_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  test_spec_tst_mtds ADD 
    CONSTRAINT  test_spec_tst_mtds_FK1 
    FOREIGN KEY ( testmethod_id ) 
    REFERENCES test_methods ( testmethod_id ); 

ALTER TABLE  test_spec_tst_mtds ADD 
    CONSTRAINT  test_spec_tst_mtds_FK2 
    FOREIGN KEY ( testspec_id ) 
    REFERENCES test_specifications ( testspec_id ); 

ALTER TABLE  test_specifications ADD 
    CONSTRAINT  test_specifications_FK1 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  test_specifications ADD 
    CONSTRAINT  test_specifications_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  trad_paradigms ADD (
    CONSTRAINT  trad_paradigms_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  units ADD 
    CONSTRAINT  units_FK1 
    FOREIGN KEY ( unitstype_id ) 
    REFERENCES units_types ( unitstype_id ); 

ALTER TABLE  units ADD 
    CONSTRAINT  units_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  units_types ADD 
    CONSTRAINT  units_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 







CREATE OR REPLACE TRIGGER administration_routes_IU_TRG 
BEFORE INSERT OR UPDATE ON administration_routes 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.adminrt_creation_date := SYSDATE; 
    :NEW.adminrt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.adminrt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER application_text_IU_TRG 
BEFORE INSERT OR UPDATE ON application_text 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.apptext_creation_date := SYSDATE; 
    :NEW.apptext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.apptext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER attachments_IU_TRG 
BEFORE INSERT OR UPDATE ON attachments 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.attachment_creation_date := SYSDATE; 
    :NEW.attachment_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.attachment_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER biological_impurities_IU_TRG 
BEFORE INSERT OR UPDATE ON biological_impurities 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.bioimpurity_creation_date := SYSDATE; 
    :NEW.bioimpurity_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.bioimpurity_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER registry_entries_IU_TRG 
BEFORE INSERT OR UPDATE ON registry_entries 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.regentry_creation_date := SYSDATE; 
    :NEW.regentry_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.regentry_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER chem_subclasses_IU_TRG 
BEFORE INSERT OR UPDATE ON chem_subclasses 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.chemsub_creation_date := SYSDATE; 
    :NEW.chemsub_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.chemsub_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER citation_specials_IU_TRG 
BEFORE INSERT OR UPDATE ON citation_specials 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.citationspec_creation_date := SYSDATE; 
    :NEW.citationspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.citationspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER citations_IU_TRG 
BEFORE INSERT OR UPDATE ON citations 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.citation_creation_date := SYSDATE; 
    :NEW.citation_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.citation_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER coded_risks_IU_TRG 
BEFORE INSERT OR UPDATE ON coded_risks 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.codedrisk_creation_date := SYSDATE; 
    :NEW.codedrisk_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.codedrisk_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER context_sens_citations_IU_TRG 
BEFORE INSERT OR UPDATE ON context_sensitive_citations  
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.consencit_creation_date := SYSDATE; 
    :NEW.consencit_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.consencit_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER countries_IU_TRG 
BEFORE INSERT OR UPDATE ON countries 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.country_creation_date := SYSDATE; 
    :NEW.country_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.country_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER dosage_forms_IU_TRG 
BEFORE INSERT OR UPDATE ON dosage_forms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.dosefrm_creation_date := SYSDATE; 
    :NEW.dosefrm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.dosefrm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER dosage_form_groups_IU_TRG 
BEFORE INSERT OR UPDATE ON dosage_form_groups
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.dosefrmgrp_creation_date := SYSDATE; 
    :NEW.dosefrmgrp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.dosefrmgrp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER dosage_ranges_IU_TRG 
BEFORE INSERT OR UPDATE ON dosage_ranges
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.dosrge_creation_date := SYSDATE; 
    :NEW.dosrge_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.dosrge_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/




CREATE OR REPLACE TRIGGER evidence_types_IU_TRG 
BEFORE INSERT OR UPDATE ON evidence_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.evidtype_creation_date := SYSDATE; 
    :NEW.evidtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.evidtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER families_IU_TRG 
BEFORE INSERT OR UPDATE ON families 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.family_creation_date := SYSDATE; 
    :NEW.family_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.family_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER genera_IU_TRG 
BEFORE INSERT OR UPDATE ON genera 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.genus_creation_date := SYSDATE; 
    :NEW.genus_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.genus_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER generic_text_IU_TRG 
BEFORE INSERT OR UPDATE ON generic_texts 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.gentext_creation_date := SYSDATE; 
    :NEW.gentext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.gentext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER geographic_areas_IU_TRG 
BEFORE INSERT OR UPDATE ON geographic_areas 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.geogarea_creation_date := SYSDATE; 
    :NEW.geogarea_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.geogarea_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER ich_classes_IU_TRG 
BEFORE INSERT OR UPDATE ON ich_classes
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ichclass_creation_date := SYSDATE; 
    :NEW.ichclass_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ichclass_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER impurities_IU_TRG 
BEFORE INSERT OR UPDATE ON impurities
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.impurity_creation_date := SYSDATE; 
    :NEW.impurity_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.impurity_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER indications_IU_TRG 
BEFORE INSERT OR UPDATE ON indications
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.indication_creation_date := SYSDATE; 
    :NEW.indication_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.indication_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER indication_types_IU_TRG 
BEFORE INSERT OR UPDATE ON indication_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.indictype_creation_date := SYSDATE; 
    :NEW.indictype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.indictype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER ingredient_categories_IU_TRG 
BEFORE INSERT OR UPDATE ON ingredient_categories
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ingredcat_creation_date := SYSDATE; 
    :NEW.ingredcat_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ingredcat_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER ingredient_roles_IU_TRG 
BEFORE INSERT OR UPDATE ON ingredient_roles
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ingredrole_creation_date := SYSDATE; 
    :NEW.ingredrole_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ingredrole_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER ingredient_specials_IU_TRG 
BEFORE INSERT OR UPDATE ON ingredient_specials
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ingredspec_creation_date := SYSDATE; 
    :NEW.ingredspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ingredspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER ingredients_IU_TRG 
BEFORE INSERT OR UPDATE ON ingredients 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ingred_creation_date := SYSDATE; 
    :NEW.ingred_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ingred_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER manufact_processes_IU_TRG 
BEFORE INSERT OR UPDATE ON manufact_processes 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.manproc_creation_date := SYSDATE; 
    :NEW.manproc_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.manproc_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monographs_IU_TRG 
BEFORE INSERT OR UPDATE ON monographs 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.mono_creation_date := SYSDATE; 
    :NEW.mono_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.mono_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_appendices_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_appendices 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monoappend_creation_date := SYSDATE; 
    :NEW.monoappend_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monoappend_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_attachments_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_attachments 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monoatt_creation_date := SYSDATE; 
    :NEW.monoatt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monoatt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_dirs_for_uses_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_directions_for_uses 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monodfu_creation_date := SYSDATE; 
    :NEW.monodfu_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monodfu_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_dosage_forms_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_dosage_forms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monodosfrm_creation_date := SYSDATE; 
    :NEW.monodosfrm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monodosfrm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_dsg_frm_nts_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_dosage_form_notes 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monodosfrmnote_creation_date := SYSDATE; 
    :NEW.monodosfrmnote_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monodosfrmnote_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_dose_notes_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_dose_notes 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monodosenote_creation_date := SYSDATE; 
    :NEW.monodosenote_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monodosenote_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_entries_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_entries 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monoent_creation_date := SYSDATE; 
    :NEW.monoent_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monoent_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_risk_stmts_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_risk_stmts
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monoriskstmt_creation_date := SYSDATE; 
    :NEW.monoriskstmt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monoriskstmt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_routeofadmins_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_routeofadmins 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monoadminrt_creation_date := SYSDATE; 
    :NEW.monoadminrt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monoadminrt_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER monograph_sources_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_sources
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monosource_creation_date := SYSDATE; 
    :NEW.monosource_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monosource_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_subingdoses_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_subingdoses
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monosubingdose_creation_date := SYSDATE; 
    :NEW.monosubingdose_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monosubingdose_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER monograph_uses_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_uses
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monouse_creation_date := SYSDATE; 
    :NEW.monouse_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monouse_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER nhp_classifications_IU_TRG 
BEFORE INSERT OR UPDATE ON nhp_classifications 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.nhpclass_creation_date := SYSDATE; 
    :NEW.nhpclass_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.nhpclass_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER organism_part_types_IU_TRG 
BEFORE INSERT OR UPDATE ON organism_part_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.orgparttype_creation_date := SYSDATE; 
    :NEW.orgparttype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.orgparttype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER organism_parts_IU_TRG 
BEFORE INSERT OR UPDATE ON organism_parts
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.orgpart_creation_date := SYSDATE; 
    :NEW.orgpart_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.orgpart_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER organism_type_groups_IU_TRG 
BEFORE INSERT OR UPDATE ON organism_type_groups
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.orgtypegrp_creation_date := SYSDATE; 
    :NEW.orgtypegrp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.orgtypegrp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER organism_types_IU_TRG 
BEFORE INSERT OR UPDATE ON organism_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.orgtype_creation_date := SYSDATE; 
    :NEW.orgtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.orgtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER organisms_IU_TRG 
BEFORE INSERT OR UPDATE ON organisms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.organism_creation_date := SYSDATE; 
    :NEW.organism_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.organism_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER organism_groups_IU_TRG 
BEFORE INSERT OR UPDATE ON organism_groups 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.org_grp_creation_date := SYSDATE; 
    :NEW.org_grp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.org_grp_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER preparation_types_IU_TRG 
BEFORE INSERT OR UPDATE ON preparation_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.preptype_creation_date := SYSDATE; 
    :NEW.preptype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.preptype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER product_types_IU_TRG 
BEFORE INSERT OR UPDATE ON product_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.prodtype_creation_date := SYSDATE; 
    :NEW.prodtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.prodtype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER published_journals_IU_TRG 
BEFORE INSERT OR UPDATE ON published_journals 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.journal_creation_date := SYSDATE; 
    :NEW.journal_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.journal_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER purposes_IU_TRG 
BEFORE INSERT OR UPDATE ON purposes 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.purpose_creation_date := SYSDATE; 
    :NEW.purpose_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.purpose_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER qualified_synonyms_IU_TRG 
BEFORE INSERT OR UPDATE ON qualified_synonyms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.qualsyno_creation_date := SYSDATE; 
    :NEW.qualsyno_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.qualsyno_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER quality_tolerance_IU_TRG 
BEFORE INSERT OR UPDATE ON quality_tolerance_models 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.qualitymodel_creation_date := SYSDATE; 
    :NEW.qualitymodel_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.qualitymodel_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER rationales_IU_TRG 
BEFORE INSERT OR UPDATE ON rationales 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.rationale_creation_date := SYSDATE; 
    :NEW.rationale_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.rationale_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER refs_IU_TRG 
BEFORE INSERT OR UPDATE ON refs 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.ref_creation_date := SYSDATE; 
    :NEW.ref_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.ref_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER restriction_types_IU_TRG 
BEFORE INSERT OR UPDATE ON restriction_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.resttype_creation_date := SYSDATE; 
    :NEW.resttype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.resttype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER restrictions_IU_TRG 
BEFORE INSERT OR UPDATE ON restrictions 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.restrict_creation_date := SYSDATE; 
    :NEW.restrict_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.restrict_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER risks_IU_TRG 
BEFORE INSERT OR UPDATE ON risks 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.risk_creation_date := SYSDATE; 
    :NEW.risk_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.risk_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER risk_types_IU_TRG 
BEFORE INSERT OR UPDATE ON risk_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.risktype_creation_date := SYSDATE; 
    :NEW.risktype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.risktype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER role_specials_IU_TRG 
BEFORE INSERT OR UPDATE ON role_specials 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.rolespec_creation_date := SYSDATE; 
    :NEW.rolespec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.rolespec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER species_IU_TRG 
BEFORE INSERT OR UPDATE ON species 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.species_creation_date := SYSDATE; 
    :NEW.species_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.species_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER subingredients_IU_TRG 
BEFORE INSERT OR UPDATE ON subingredients 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.subingred_creation_date := SYSDATE; 
    :NEW.subingred_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.subingred_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER subtaxa_IU_TRG 
BEFORE INSERT OR UPDATE ON subtaxa 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.subtaxa_creation_date := SYSDATE; 
    :NEW.subtaxa_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.subtaxa_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER sub_population_IU_TRG 
BEFORE INSERT OR UPDATE ON sub_population 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.subpop_creation_date := SYSDATE; 
    :NEW.subpop_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.subpop_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER synonyms_IU_TRG 
BEFORE INSERT OR UPDATE ON synonyms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.synonym_creation_date := SYSDATE; 
    :NEW.synonym_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2;
  ELSIF UPDATING 
  THEN 
    :NEW.synonym_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER taxa_IU_TRG 
BEFORE INSERT OR UPDATE ON taxa 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.taxon_creation_date := SYSDATE; 
    :NEW.taxon_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.taxon_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER test_categories_IU_TRG 
BEFORE INSERT OR UPDATE ON test_categories 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.testcat_creation_date := SYSDATE; 
    :NEW.testcat_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.testcat_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER test_methods_IU_TRG 
BEFORE INSERT OR UPDATE ON test_methods 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.testmethod_creation_date := SYSDATE; 
    :NEW.testmethod_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.testmethod_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER test_specifications_IU_TRG 
BEFORE INSERT OR UPDATE ON test_specifications
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.testspec_creation_date := SYSDATE; 
    :NEW.testspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.testspec_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER trad_paradigms_IU_TRG 
BEFORE INSERT OR UPDATE ON trad_paradigms 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.tradparadigm_creation_date := SYSDATE; 
    :NEW.tradparadigm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.tradparadigm_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/


CREATE OR REPLACE TRIGGER units_IU_TRG 
BEFORE INSERT OR UPDATE ON units 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.units_creation_date := SYSDATE; 
    :NEW.units_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.units_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER use_types_IU_TRG 
BEFORE INSERT OR UPDATE ON use_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.usetype_creation_date := SYSDATE; 
    :NEW.usetype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.usetype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

-- Create a function that stamps the load.
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

CREATE OR REPLACE TRIGGER units_types_IU_TRG 
BEFORE INSERT OR UPDATE ON units_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.unitstype_creation_date := SYSDATE; 
    :NEW.unitstype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.unitstype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

-- Functions
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
  Syn_Rec Syn_Type;
  Synonym_List VARCHAR2(8000);
  SQLQ VARCHAR2(8000);
BEGIN
  -- Define an empty synonym list.
  Synonym_List := '';

  -- Build the select statement. 
  -- 1. get the synonyms from the ingredient.
  -- 2. get the synonyms from the organism when ingredient.name = organism.name
  SQLQ :=         'select ';
  SQLQ := SQLQ || 'synonym_name_eng, ';
  SQLQ := SQLQ || 'synonym_name_fr ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  ingredient_synonyms ing_syn ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  syn.synonym_id = ing_syn.synonym_id and ';
  SQLQ := SQLQ || '  ing_syn.ingred_id = ' || ingred_id;
  SQLQ := SQLQ || 'union ';
  SQLQ := SQLQ || 'select ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  ingredients ing, ';
  SQLQ := SQLQ || '  in_org_grp_organism iogo, ';
  SQLQ := SQLQ || '  organisms org, ';
  SQLQ := SQLQ || '  organism_synonyms orgsyn ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  ing.org_grp_id = iogo.org_grp_id and ';
  SQLQ := SQLQ || '  iogo.organism_id = orgsyn.organism_id and ';
  SQLQ := SQLQ || '  iogo.organism_id = org.organism_id and ';
  SQLQ := SQLQ || '  ing.ingred_authorized_name_eng = org.organism_name and ';
  SQLQ := SQLQ || '  orgsyn.synonym_id = syn.synonym_id and ';
  SQLQ := SQLQ || '  ing.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN synonym_cursor FOR SQLQ;
  LOOP
    Fetch Synonym_cursor INTO Syn_Rec;
    IF Synonym_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      -- Add a concatinator if the list contains data.
      IF length(Synonym_List) is not null THEN 
        Synonym_List := Synonym_List || concatenator;
      END IF;
      -- Append the new synonym using the language of choice.
      IF LANG='ENG' THEN
        Synonym_List := Synonym_List || Syn_Rec.synonym_name_eng;
      ELSE
        Synonym_List := Synonym_List || Syn_Rec.synonym_name_fr;
      END IF;
    END IF;
  END LOOP;
  CLOSE synonym_cursor;
  RETURN synonym_List;
END GETSYNONYMS;
/

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
  SearchTerm_Rec SearchTerm_Type;
  SearchTerm_List VARCHAR2(8000);
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
    IF SearchTerm_cursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      -- Add a concatinator if the list contains data.
      IF length(SearchTerm_List) is not null THEN 
        SearchTerm_List := SearchTerm_List || concatenator;
      END IF;
      -- Append the new synonym using the language of choice.
      IF LANG='ENG' THEN
        SearchTerm_List := SearchTerm_List || SearchTerm_Rec.SearchTerm_eng;
      ELSE
        SearchTerm_List := SearchTerm_List || SearchTerm_Rec.SearchTerm_fr;
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

-- Materialized Views
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
  ingredient_synonyms i_syn,
  synonyms syn
WHERE 
  i.ingred_id = i_syn.ingred_id and 
  i_syn.synonym_id = syn.synonym_id
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
  orgFrmGrpSyn.synonym_name_eng as english_name,
  orgFrmGrpSyn.synonym_name_fr as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  in_org_grp_organism org_grps,
  organisms orgFrmGrp,
  organism_synonyms orgFrmGrp_syn,
  synonyms orgFrmGrpSyn
WHERE
  i.org_grp_id = org_grps.org_grp_id and
  org_grps.organism_id = orgFrmGrp.organism_id and
  orgFrmGrp.organism_id = orgFrmGrp_syn.organism_id and
  orgFrmGrp_syn.synonym_id = orgFrmGrpSyn.synonym_id
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
  t.taxon_name as english_name,
  t.taxon_name as french_name,
  r.rolespec_class_name as role_name
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id,
  taxa t
WHERE
  i.organism_id = t.organism_id;

CREATE INDEX  ingredient_name_search_IDX1 
    ON  ingredient_name_search_mv (ingred_id);
CREATE INDEX  ingredient_name_search_IDX2 
    ON  ingredient_name_search_mv (english_name);
CREATE INDEX  ingredient_name_search_IDX3 
    ON  ingredient_name_search_mv (french_name);
CREATE INDEX  ingredient_name_search_IDX4 
    ON  ingredient_name_search_mv (role_name);

-- Used by the web services
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
  getSearchTermS('ENG', '~', i.ingred_id) as english_searchterm,
  getSearchTermS('FR', '~', i.ingred_id) as french_searchterm,
  getSynonyms('ENG', '<br/>', i.ingred_id) as english_synonyms,
  getSynonyms('FR', '<br/>', i.ingred_id) as french_synonyms
FROM
  ingredients i
  LEFT JOIN ingredient_roles r ON i.ingred_id = r.ingred_id
  LEFT JOIN ingredient_categories c ON i.ingredcat_id = c.ingredcat_id;

CREATE INDEX  ingredient_synonyms_IDX1 
    ON  ingredient_synonyms_mv (ingred_id);

CREATE INDEX  ingredient_synonyms_IDX2 
    ON  ingredient_synonyms_mv (english_name);

CREATE INDEX  ingredient_synonyms_IDX3 
    ON  ingredient_synonyms_mv (french_name);

CREATE INDEX  ingredient_synonyms_IDX4 
    ON  ingredient_synonyms_mv (class_name);

CREATE INDEX  ingredient_synonyms_IDX5 
    ON  ingredient_synonyms_mv (role_name);

CREATE INDEX  ingredient_synonyms_IDX6 
    ON  ingredient_synonyms_mv (category_code);

-- Not currently used with the contains function.  Need access to update the indexes.
CREATE INDEX  ingredient_synonyms_IDX7 
    ON ingredient_synonyms_mv(english_searchterm) INDEXTYPE IS CTXSYS.CONTEXT;

-- Not currently used with the contains function.  Need access to update the indexes.
CREATE INDEX  ingredient_synonyms_IDX8 
    ON ingredient_synonyms_mv(french_searchterm) INDEXTYPE IS CTXSYS.CONTEXT;

-- Create the Materialized View for the Monograph.
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
   me.ingred_id,
   getSearchTermS('ENG', '~', me.ingred_id) as english_searchterm,
   getSearchTermS('FR', '~', me.ingred_id) as french_searchterm,
   getSynonyms('ENG', '<br/>', me.ingred_id) as english_synonyms,
   getSynonyms('FR', '<br/>', me.ingred_id) as french_synonyms
 FROM
   monographs m
   INNER JOIN monograph_entries me ON m.mono_id = me.mono_id
   WHERE  (EXISTS (select mono_id from monograph_roa_shrd_monographs smr where smr.mono_id = m.mono_id)
       OR  EXISTS (select mono_id from monograph_roa_dist_monographs smr where smr.mono_id = m.mono_id))
   AND me.MONOENT_PRIMARY_INGREDIENT = 'Y';

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


-- Temporary Table
create global temporary table 
results_temp (
  rnum integer,
  ingred_id integer,
  name varchar2(1000),
  synonyms varchar2(4000)
) ON COMMIT PRESERVE ROWS;

-- Cursor Package
CREATE OR REPLACE PACKAGE Types AS 
  TYPE cursor_type IS REF CURSOR;
END Types; 
/

-- Sequences
CREATE SEQUENCE ADMINISTRATION_ROUTES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE APPLICATION_TEXT_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ATTACHMENTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE BIOLOGICAL_IMPURITIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE REGISTRY_ENTRIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CHEM_CLASSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CHEM_SUBCLASSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CODED_RISK_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CITATIONS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CITATION_SPECIALS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE CONTEXT_SENSTVE_CITATIONS_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE COUNTRIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE DOSAGE_FORMS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE DOSAGE_FORM_GROUPS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE DOSAGE_RANGE_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE EVIDENCE_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE FAMILIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE GENERA_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE GENERIC_TEXT_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE GEOGRAPHIC_AREAS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ICH_CLASSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE IMPURITIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INDICATIONS_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INDICATION_TYPES_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INGREDIENTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INGREDIENT_CATEGORIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INGREDIENT_ROLES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INGREDIENT_SOURCES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE INGREDIENT_SPECIALS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MANUFACT_PROCESSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE meddra_pref_terms_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPHS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_GROUPS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_APPENDICES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_ATTACHMENTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_DIRECTS_FOR_USES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_DOSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_DURATIONS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_ENTRIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_DOSAGE_FORMS_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_DOSAGE_FORM_NTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;
    
CREATE SEQUENCE MONOGRAPH_DOSE_NOTES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE monograph_risk_stmts_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE monograph_routeofadmins_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE monograph_sources_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE MONOGRAPH_SUBINGDOSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE monograph_uses_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE NHP_CLASSIFICATIONS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ORGANISMS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ORGANISM_GROUPS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;
    
CREATE SEQUENCE ORGANISM_PARTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ORGANISM_PART_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ORGANISM_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ORGANISM_TYPE_GROUPS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE preparation_types_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE preparation_type_groups_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE PRODUCT_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

-- Published Journal sequence starts high because information is
-- being loaded from an external source, before being loaded
-- by the Ingredients loader.
CREATE SEQUENCE PUBLISHED_JOURNALS_SEQ
    INCREMENT BY 1 START WITH 25000 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE PURPOSES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE QUALIFIED_SYNONYMS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE QUALITY_TOLERANCE_MODELS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE RATIONALES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE REFS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE RESTRICTIONS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE RESTRICTION_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE RISKS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE risk_types_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE ROLE_SPECIALS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE solvents_seq
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;
CREATE SEQUENCE SPECIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE SUBINGREDIENTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE subtaxa_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE sub_population_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE SYNONYMS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE TAXA_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE TEST_CATEGORIES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE TEST_METHODS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE TEST_SPECIFICATIONS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE trad_paradigms_seq 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE UNITS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE UNITS_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE USER_ACCOUNTS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;
    
CREATE SEQUENCE USE_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

-- The following set of 3 functions "ADDMIROLES3", "ADDMIROLES2"
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

-- Functions
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

-- Functions
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


