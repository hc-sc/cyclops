--
-- Create join (aka junction) tables.  These are tables which define relationships
-- between data in other tables.  Please maintain alpha order of created table names
--
-- TODO - add indices on new join tables
---------------------------------------------------------------------
CREATE TABLE coded_risks_synonyms (
    codedrisk_id Integer NOT NULL,
    synonym_id Integer NOT NULL
);
ALTER TABLE  coded_risks_synonyms ADD (
    CONSTRAINT  coded_risks_synonyms_PK
    PRIMARY KEY ( codedrisk_id,synonym_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE consencit_citations (
    consencit_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  consencit_citations ADD (
    CONSTRAINT  consencit_citations_PK 
    PRIMARY KEY ( consencit_id,citation_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  dosageform_citations (
    	monodosfrm_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);
ALTER TABLE  dosageform_citations ADD (
    CONSTRAINT  dosageform_citations_PK 
    PRIMARY KEY ( monodosfrm_id,citation_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  dosageform_synonyms (
    	dosefrm_id Integer  NOT NULL,
	synonym_id Integer  NOT NULL 
);
ALTER TABLE  dosageform_synonyms ADD (
    CONSTRAINT  dosageform_synonyms_PK 
    PRIMARY KEY ( dosefrm_id,synonym_id ) 
    USING INDEX 
);
  
---------------------------------------------------------------------
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
    ON  dosage_form_admin_routes (adminrt_id); 
CREATE INDEX  dosage_form_adminrt_IDX2 
    ON  dosage_form_admin_routes (dosefrm_id);

---------------------------------------------------------------------
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
    ON  dosage_form_group_test_specs (dosefrmgrp_id); 
CREATE INDEX  dosage_frm_gp_ts_sp_IDX2 
    ON  dosage_form_group_test_specs (testcat_id); 
CREATE INDEX  dosage_frm_gp_ts_sp_IDX3 
    ON  dosage_form_group_test_specs (testspec_id); 

--------------------------------------------------------------------- 
CREATE TABLE  dosageform_dosageformgroups (
    dosefrm_id Integer  NOT NULL,
	dosefrmgrp_id Integer  NOT NULL 
);
ALTER TABLE  dosageform_dosageformgroups ADD (
    CONSTRAINT  dosageform_dosageformgrps_PK 
    PRIMARY KEY ( dosefrm_id, dosefrmgrp_id ) 
    USING INDEX 
); 
CREATE INDEX  dosageform_dosageformgrps_IDX1 
    ON  dosageform_dosageformgroups (dosefrm_id);
CREATE INDEX  dosageform_dosageformgrps_IDX2 
    ON  dosageform_dosageformgroups (dosefrmgrp_id); 

--------------------------------------------------------------------- 
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
    ON  dosage_form_test_specs (dosefrm_id); 
CREATE INDEX  dosage_form_test_specs_IDX2 
    ON  dosage_form_test_specs (testcat_id); 
CREATE INDEX  dosage_form_test_specs_IDX3 
    ON  dosage_form_test_specs (testspec_id);
         
---------------------------------------------------------------------
CREATE TABLE  endang_organism_countries (
    country_id Integer default '0' NOT NULL, 
    organism_id Integer default '0' NOT NULL
);
CREATE INDEX  endang_organism_countries_IDX1 
    ON  endang_organism_countries (country_id); 
CREATE INDEX  endang_organism_countries_IDX2 
    ON  endang_organism_countries (organism_id); 
    
---------------------------------------------------------------------    
CREATE TABLE  ex_org_grp_families (
	org_grp_id Integer NOT NULL,	
	family_id Integer  NOT NULL 
);
ALTER TABLE ex_org_grp_families ADD (
  CONSTRAINT ex_org_grp_families_PK
  PRIMARY KEY (org_grp_id, family_id) 
  USING INDEX 
);
---------------------------------------------------------------------
CREATE TABLE  ex_org_grp_genera (
	org_grp_id Integer NOT NULL,	
	genus_id Integer  NOT NULL 
);
ALTER TABLE ex_org_grp_genera ADD (
  CONSTRAINT ex_org_grp_genera_PK
  PRIMARY KEY (org_grp_id, genus_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  ex_org_grp_organism (
	org_grp_id Integer NOT NULL,	
	organism_id Integer  NOT NULL 
);
ALTER TABLE ex_org_grp_organism ADD (
  CONSTRAINT ex_org_grp_organism_PK
  PRIMARY KEY (org_grp_id, organism_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  ex_org_grp_species (
	org_grp_id Integer NOT NULL,	
	species_id Integer  NOT NULL 
);
ALTER TABLE ex_org_grp_species ADD (
  CONSTRAINT ex_org_grp_species_PK
  PRIMARY KEY (org_grp_id, species_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  ex_org_grp_subtaxa (
	org_grp_id Integer NOT NULL,	
	subtaxa_id Integer  NOT NULL 
);
ALTER TABLE ex_org_grp_subtaxa ADD (
  CONSTRAINT ex_org_grp_subtaxa_PK
  PRIMARY KEY (org_grp_id, subtaxa_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE generic_texts_eng_citations (
    gentext_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  generic_texts_eng_citations ADD (
    CONSTRAINT  generic_texts_eng_citats_PK 
    PRIMARY KEY ( gentext_id, citation_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  homeopathic_common_name_hgts (
    ingred_id                  Integer       default '0'  NOT NULL,
    homeogentext_id            Integer       default '0'  NOT NULL
);

ALTER TABLE  homeopathic_common_name_hgts
    ADD CONSTRAINT  homeopathic_comm_name_hgts_UK1 
    UNIQUE (ingred_id, homeogentext_id);

---------------------------------------------------------------------
CREATE TABLE  homeopathic_proper_name_hgts (
    ingred_id                  Integer       default '0'  NOT NULL,
    homeogentext_id            Integer       default '0'  NOT NULL 
);

ALTER TABLE  homeopathic_proper_name_hgts
    ADD CONSTRAINT  homeopathic_prop_name_hgts_UK1 
    UNIQUE ( ingred_id, homeogentext_id );

---------------------------------------------------------------------
CREATE TABLE  homeopathic_source_mat_hgts (
    homeoform_id               Integer       default '0'  NOT NULL,
    homeogentext_id            Integer       default '0'  NOT NULL 
);

ALTER TABLE  homeopathic_source_mat_hgts 
    ADD CONSTRAINT  homeopathic_sour_mat_hgts_UK1 
    UNIQUE ( homeoform_id, homeogentext_id );

---------------------------------------------------------------------
CREATE TABLE generic_texts_fr_citations (
    gentext_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  generic_texts_fr_citations ADD (
    CONSTRAINT  generic_texts_fr_citats_PK 
    PRIMARY KEY ( gentext_id, citation_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
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
    ON  ich_class_test_methods (ichclass_id); 
CREATE INDEX  ich_class_test_methods_IDX2 
    ON  ich_class_test_methods (testmethod_id); 
    
---------------------------------------------------------------------
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
    ON  impurity_test_methods (impurity_id); 
CREATE INDEX  impurity_test_methods_IDX2 
    ON  impurity_test_methods (testmethod_id);
    
---------------------------------------------------------------------
CREATE TABLE  indication_admin_routes (
    indication_id Integer  NOT NULL,
	adminrt_id Integer  NOT NULL 
);
ALTER TABLE  indication_admin_routes ADD (
    CONSTRAINT  indication_admin_routes_PK 
    PRIMARY KEY ( indication_id,adminrt_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
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
-- The following index is created implicitly by Oracle when the preceding
--unique key constraint is created. 
--CREATE INDEX  ingredient_bio_impurities_IDX1 
--    ON  ingredient_bio_impurities (bioimpurity_id); 
CREATE INDEX  ingredient_bio_impurities_IDX2 
    ON  ingredient_bio_impurities (ingred_id);
       
---------------------------------------------------------------------
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
    ON  ingredient_impurities (impurity_id); 
CREATE INDEX  ingredient_impurities_IDX2 
    ON  ingredient_impurities (ingred_id); 
---------------------------------------------------------------------
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
    ON  ingredient_ingred_impurities (ingred_id); 

CREATE INDEX  ingredient_ingred_impurts_IDX2 
    ON  ingredient_ingred_impurities (ingred_id_impur); 

---------------------------------------------------------------------
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
    ON  ingredient_nhpclassifications (ingred_id); 

CREATE INDEX  ingredient_nhpclass_IDX2 
    ON  ingredient_nhpclassifications (nhpclass_id); 

---------------------------------------------------------------------
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
    ON  ingredient_orgparts (orgpart_id); 

---------------------------------------------------------------------
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
    ON  ingredient_preptypes (preptype_id);
    
---------------------------------------------------------------------
CREATE TABLE  ingredient_reg_entries (
    regentry_id Integer default null NOT NULL, 
    ingred_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_reg_entries_IDX1 
    ON  ingredient_reg_entries (regentry_id); 
CREATE INDEX  ingredient_reg_entries_IDX2 
    ON  ingredient_reg_entries (ingred_id);
    
---------------------------------------------------------------------
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
---------------------------------------------------------------------
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
---------------------------------------------------------------------
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
 
---------------------------------------------------------------------
CREATE TABLE  ingredient_role_purposes (
    ingredrole_id Integer default null NOT NULL, 
    purpose_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_role_purposes_IDX1 
    ON  ingredient_role_purposes (ingredrole_id); 

CREATE INDEX  ingredient_role_purposes_IDX2 
    ON  ingredient_role_purposes (purpose_id);

---------------------------------------------------------------------
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
    ON  ingredient_std_grade_refs (ingred_id); 

CREATE INDEX  ingredient_std_grade_refs_IDX2 
    ON  ingredient_std_grade_refs (ref_id); 

---------------------------------------------------------------------
CREATE TABLE  ingredient_subingredients (
    ingred_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  ingredient_subingred_IDX1 
    ON  ingredient_subingredients (ingred_id); 
CREATE INDEX  ingredient_subingred_IDX2 
    ON  ingredient_subingredients (subingred_id); 

---------------------------------------------------------------------
CREATE TABLE  ingredient_synonyms (
    ingred_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
ALTER TABLE  ingredient_synonyms
    ADD CONSTRAINT  ingredient_synonyms_UK1 
    UNIQUE (ingred_id,synonym_id);
CREATE INDEX  ingredient_synonyms_IDX1 
    ON  ingredient_synonyms (ingred_id); 
CREATE INDEX  ingredient_synonyms_IDX2 
    ON  ingredient_synonyms (synonym_id);
    
---------------------------------------------------------------------     
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

---------------------------------------------------------------------
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
    ON  ingredient_test_specs (dosefrmgrp_id); 

CREATE INDEX  ingredient_test_specs_IDX2 
    ON  ingredient_test_specs (ingred_id); 

CREATE INDEX  ingredient_test_specs_IDX3 
    ON  ingredient_test_specs (testspec_id);

---------------------------------------------------------------------     
CREATE TABLE  in_org_grp_families (
	org_grp_id Integer NOT NULL,	
	family_id Integer  NOT NULL 
);
ALTER TABLE in_org_grp_families ADD (
  CONSTRAINT in_org_grp_families_PK
  PRIMARY KEY (org_grp_id, family_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  in_org_grp_genera (
	org_grp_id Integer NOT NULL,	
	genus_id Integer  NOT NULL
);
ALTER TABLE in_org_grp_genera ADD (
  CONSTRAINT in_org_grp_genera_PK
  PRIMARY KEY (org_grp_id, genus_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  in_org_grp_organism (
	org_grp_id Integer NOT NULL,	
	organism_id Integer  NOT NULL
);
ALTER TABLE in_org_grp_organism ADD (
  CONSTRAINT in_org_grp_organism_PK
  PRIMARY KEY (org_grp_id, organism_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  in_org_grp_species (
	org_grp_id Integer NOT NULL,	
	species_id Integer  NOT NULL 
);
ALTER TABLE in_org_grp_species ADD (
  CONSTRAINT in_org_grp_species_PK
  PRIMARY KEY (org_grp_id, species_id) 
  USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  in_org_grp_subtaxa (
	org_grp_id Integer NOT NULL,	
	subtaxa_id Integer  NOT NULL 
);
ALTER TABLE in_org_grp_subtaxa ADD (
  CONSTRAINT in_org_grp_subtaxa_PK
  PRIMARY KEY (org_grp_id, subtaxa_id) 
  USING INDEX 
);

---------------------------------------------------------------------
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
    ON  manufact_proc_bio_impurts (bioimpurity_id); 
CREATE INDEX  manufact_proc_bio_impurts_IDX2 
    ON  manufact_proc_bio_impurts (manproc_id); 
     
---------------------------------------------------------------------
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
    ON  manufact_process_impurities (impurity_id); 
CREATE INDEX  manufact_proc_impurts_IDX2 
    ON  manufact_process_impurities (manproc_id);
     
---------------------------------------------------------------------
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
    ON  manufact_process_ingredients (ingred_id); 

CREATE INDEX  manufact_proc_ingreds_IDX2 
    ON  manufact_process_ingredients (manproc_id);
    
---------------------------------------------------------------------    
CREATE TABLE  meddra_hlgt_hlts (
    hlgt_id Integer default null NOT NULL, 
    hlt_id Integer default null NOT NULL
);
ALTER TABLE  meddra_hlgt_hlts
    ADD CONSTRAINT  meddra_hlgt_hlts_UK1 
    UNIQUE (hlgt_id,hlt_id);
CREATE INDEX  meddra_hlgt_hlts_IDX1 
    ON  meddra_hlgt_hlts (hlgt_id); 
CREATE INDEX  meddra_hlgt_hlts_IDX2 
    ON  meddra_hlgt_hlts (hlt_id);
    
---------------------------------------------------------------------
CREATE TABLE  meddra_hlt_pts (
    hlt_id Integer default null NOT NULL, 
    pt_id Integer default null NOT NULL
);
ALTER TABLE  meddra_hlt_pts
    ADD CONSTRAINT  meddra_hlt_pts_UK1 
    UNIQUE (hlt_id,pt_id);
CREATE INDEX  meddra_hlt_pts_IDX1 
    ON  meddra_hlt_pts (hlt_id); 
CREATE INDEX  meddra_hlt_pts_IDX2 
    ON  meddra_hlt_pts (pt_id); 
    
---------------------------------------------------------------------
CREATE TABLE  meddra_soc_hlgts (
    hlgt_id Integer default null NOT NULL, 
    soc_id Integer default null NOT NULL
);
ALTER TABLE  meddra_soc_hlgts
    ADD CONSTRAINT  meddra_soc_hlgts_UK1 
    UNIQUE (soc_id,hlgt_id);
CREATE INDEX  meddra_soc_hlgts_IDX1 
    ON  meddra_soc_hlgts (hlgt_id); 
CREATE INDEX  meddra_soc_hlgts_IDX2 
    ON  meddra_soc_hlgts (soc_id);

---------------------------------------------------------------------
CREATE TABLE mono_dosecomb_mono_doses (
    monodosecomb_id Integer NOT NULL,
    monodose_id Integer NOT NULL
);
ALTER TABLE mono_dosecomb_mono_doses ADD (
    CONSTRAINT  mono_dosecomb_mono_doses_PK
    PRIMARY KEY ( monodosecomb_id,monodose_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monodosecomb_monouses (
    monodosecomb_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);
ALTER TABLE monodosecomb_monouses ADD (
    CONSTRAINT  monodosecomb_monouses_PK
    PRIMARY KEY ( monodosecomb_id,monouse_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monodosecomb_monosrcorgparts (
    monodosecomb_id Integer NOT NULL,
    monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE monodosecomb_monosrcorgparts ADD (
    CONSTRAINT  monodosecomb_monosrcorgprts_PK
    PRIMARY KEY ( monodosecomb_id,monosrc_orgpart_id ) 
    USING INDEX 
);


---------------------------------------------------------------------
-- Joins MonograpDoses to MonographSubingredientDoseCombination
CREATE TABLE monodose_monosubidosecombs (
    monodose_id Integer NOT NULL,
    mono_subing_dosecomb_id Integer NOT NULL
);
ALTER TABLE monodose_monosubidosecombs ADD (
    CONSTRAINT  monodose_monosubidosecombs_PK
    PRIMARY KEY ( monodose_id,mono_subing_dosecomb_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE mono_entry_mono_dosecombs (
    monoent_id Integer NOT NULL,
    monodosecomb_id Integer NOT NULL
);
ALTER TABLE mono_entry_mono_dosecombs ADD (
    CONSTRAINT  mono_entry_mono_dscmbs_PK
    PRIMARY KEY ( monoent_id,monodosecomb_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE mono_entry_monosrcorgparts (
    monoent_id Integer NOT NULL,
    mono_source_orgpart_id Integer NOT NULL
);
ALTER TABLE mono_entry_monosrcorgparts ADD (
    CONSTRAINT  mono_entry_monosrcorgparts_PK
    PRIMARY KEY ( monoent_id,mono_source_orgpart_id ) 
    USING INDEX 
);
    
---------------------------------------------------------------------
CREATE TABLE mono_phase_mono_dosecombs ( 
    monophase_id Integer NOT NULL,
    monodosecomb_id Integer NOT NULL
);
ALTER TABLE mono_phase_mono_dosecombs ADD (
    CONSTRAINT  mono_phase_mono_dosecombs_PK
    PRIMARY KEY ( monophase_id,monodosecomb_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE  mono_srcorgpart_citations (
    mono_source_orgpart_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);
ALTER TABLE  mono_srcorgpart_citations ADD (
    CONSTRAINT  mono_srcorgpart_citations_PK 
    PRIMARY KEY ( mono_source_orgpart_id,citation_id ) 
    USING INDEX 
);
    
---------------------------------------------------------------------
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
 
---------------------------------------------------------------------
CREATE TABLE monograph_dose_citations (
    monodose_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_PK 
    PRIMARY KEY ( monodose_id,citation_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE monograph_drfruse_citations (
    monodfu_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_drfruse_citations ADD (
    CONSTRAINT  monograph_drfruse_citations_PK 
    PRIMARY KEY ( monodfu_id,citation_id ) 
    USING INDEX 
); 


---------------------------------------------------------------------
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

---------------------------------------------------------------------
CREATE TABLE monograph_drfruse_mono_uses (
    monodfu_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);
ALTER TABLE  monograph_drfruse_mono_uses ADD (
    CONSTRAINT  monograph_drfruse_mono_uses_PK 
    PRIMARY KEY ( monodfu_id,monouse_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE monograph_dose_mono_dfus (
    monodose_id Integer NOT NULL,
    monodfu_id Integer NOT NULL
);
ALTER TABLE  monograph_dose_mono_dfus ADD (
    CONSTRAINT  mono_dose_mono_dfu_PK 
    PRIMARY KEY ( monodose_id,monodfu_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
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
 
---------------------------------------------------------------------
CREATE TABLE monograph_dose_mono_uses (
    monodose_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);
ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  monograph_dose_mono_uses_PK 
    PRIMARY KEY ( monodose_id,monouse_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------           
CREATE TABLE monograph_dose_note_gentexts ( 
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_dose_note_gentexts ADD (
    CONSTRAINT  monograph_dosenote_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_dose_orgparts (
    monodose_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);
ALTER TABLE  monograph_dose_orgparts ADD (
    CONSTRAINT  monodose_dose_orgparts_PK 
    PRIMARY KEY ( monodose_id,orgpart_id ) 
    USING INDEX 
);
 
--------------------------------------------------------------------- 
CREATE TABLE monograph_duration_citations (
    monodur_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_duration_citations ADD (
    CONSTRAINT  monograph_dur_citations_PK 
    PRIMARY KEY ( monodur_id,citation_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_dur_monographs (
    monodur_id Integer NOT NULL,
    mono_id Integer NOT NULL
);
ALTER TABLE  monograph_dur_monographs ADD (
    CONSTRAINT  monograph_dur_mono_PK 
    PRIMARY KEY ( monodur_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
--ALTER TABLE  monograph_dur_monographs
--    ADD CONSTRAINT  monograph_dur_mono_UK 
--    UNIQUE ( monodur_id );

---------------------------------------------------------------------
CREATE TABLE monograph_dur_mono_uses (
    monodur_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);
ALTER TABLE  monograph_dur_mono_uses ADD (
    CONSTRAINT  monograph_dur_mono_uses_PK 
    PRIMARY KEY ( monodur_id,monouse_id ) 
    USING INDEX 
);
  
---------------------------------------------------------------------
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
 
---------------------------------------------------------------------
CREATE TABLE monograph_duration_orgparts (
    monodur_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);
ALTER TABLE  monograph_duration_orgparts ADD (
    CONSTRAINT  monodose_duration_orgparts_PK 
    PRIMARY KEY ( monodur_id,orgpart_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
-- Monograph Directions For Use -> org parts
CREATE TABLE monograph_drfruse_orgparts (
    monodfu_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);

ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_PK 
    PRIMARY KEY ( monodfu_id,orgpart_id ) 
    USING INDEX 
); 
  
---------------------------------------------------------------------
CREATE TABLE monograph_entries_preptypegrps (
    monoent_id Integer NOT NULL,
    preptypegrp_id Integer NOT NULL
);
ALTER TABLE  monograph_entries_preptypegrps ADD (
    CONSTRAINT  monograph_entries_prptpgrps_PK 
    PRIMARY KEY ( monoent_id,preptypegrp_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_entries_preptypes (
    monoent_id Integer NOT NULL,
    preptype_id Integer NOT NULL
);
ALTER TABLE  monograph_entries_preptypes ADD (
    CONSTRAINT  monograph_entries_preptypes_PK 
    PRIMARY KEY ( monoent_id,preptype_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_entries_orgparts (
    monoent_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);
ALTER TABLE  monograph_entries_orgparts ADD (
    CONSTRAINT  monograph_entries_orgparts_PK 
    PRIMARY KEY ( monoent_id,orgpart_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_entries_commonnames (
    monoent_id Integer NOT NULL,
    synonym_id Integer NOT NULL
);
ALTER TABLE  monograph_entries_commonnames ADD (
    CONSTRAINT  monograph_entries_comnames_PK 
    PRIMARY KEY ( monoent_id,synonym_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_entries_src_ingreds (
    monoent_id Integer NOT NULL,
    ingred_id Integer NOT NULL
);
ALTER TABLE  monograph_entries_src_ingreds ADD (
    CONSTRAINT  monograph_entries_src_igrds_PK 
    PRIMARY KEY ( monoent_id,ingred_id ) 
    USING INDEX 
); 

--------------------------------------------------------------------- 
-- this table maps monograph entries to TextMonographSourceMaterial
-- objects, which are QualifiedAssertions.  
CREATE TABLE mono_entries_txt_monosources (
    monoent_id Integer NOT NULL,
    txtmonosource_id Integer NOT NULL
);
ALTER TABLE  mono_entries_txt_monosources ADD (
    CONSTRAINT  mono_entries_txt_monosrcs_PK 
    PRIMARY KEY ( monoent_id,txtmonosource_id ) 
    USING INDEX 
); 

--------------------------------------------------------------------- 

CREATE TABLE monograph_entry_generic_texts (
    monoent_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_PK 
    PRIMARY KEY ( monoent_id,gentext_id ) 
    USING INDEX 
); 

--------------------------------------------------------------------- 
CREATE TABLE mono_entry_monosrcingreds (
    monoent_id Integer NOT NULL,
    mono_source_ingred_id Integer NOT NULL
);
ALTER TABLE mono_entry_monosrcingreds ADD (
    CONSTRAINT mono_entry_monosrcingreds_PK 
    PRIMARY KEY ( monoent_id,mono_source_ingred_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
-- MonographEntry common names (QualifiedSynonyms)
CREATE TABLE monograph_entry_qualsyno_cns (
    monoent_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);
ALTER TABLE monograph_entry_qualsyno_cns ADD (
    CONSTRAINT  mono_entry_qualsyno_cns_PK
    PRIMARY KEY ( monoent_id,qualsyno_id ) 
    USING INDEX 
); 
---------------------------------------------------------------------
-- MonographEntry proper names (QualifiedSynonyms)
CREATE TABLE monograph_entry_qualsyno_pns (
    monoent_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);
ALTER TABLE monograph_entry_qualsyno_pns ADD (
    CONSTRAINT  mono_entry_qualsyno_pns_PK
    PRIMARY KEY ( monoent_id,qualsyno_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
--Monograph General Notes - general text
CREATE TABLE monograph_gen_note_gentexts (
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_gen_note_gentexts ADD (
    CONSTRAINT  monograph_gen_note_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_generic_texts (
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_generic_texts ADD (
    CONSTRAINT monograph_gentext_PK 
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_nmi_note_gentexts ( 
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_nmi_note_gentexts ADD (
    CONSTRAINT  monograph_nmi_note_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE monograph_phase_mono_durations (
    monophase_id Integer NOT NULL,
    monodur_id Integer NOT NULL
);
ALTER TABLE monograph_phase_mono_durations ADD (
    CONSTRAINT  mono_phase_mono_durations_PK
    PRIMARY KEY ( monophase_id,monodur_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_monograph_types (
    monotype_id Integer NOT NULL,
    mono_id Integer NOT NULL
);
ALTER TABLE  monograph_monograph_types ADD (
    CONSTRAINT  monograph_monograph_types__PK 
    PRIMARY KEY ( monotype_id,mono_id ) 
    USING INDEX
);  
 
---------------------------------------------------------------------
CREATE TABLE monograph_phase_monographs (
    monophase_id Integer NOT NULL,
    mono_id Integer NOT NULL
);
ALTER TABLE  monograph_phase_monographs ADD (
    CONSTRAINT  monograph_phase_monographs_PK 
    PRIMARY KEY ( monophase_id,mono_id ) 
    USING INDEX
);  
---------------------------------------------------------------------
CREATE TABLE monograph_risk_monographs (
    monoriskstmt_id Integer NOT NULL,
    mono_id Integer NOT NULL
);
ALTER TABLE  monograph_risk_monographs ADD (
    CONSTRAINT  monograph_risk_mono_PK 
    PRIMARY KEY ( monoriskstmt_id,mono_id ) 
    USING INDEX
);  
-- Temporarily removed until data is corrected.  GB.  2007-11-08
-- ALTER TABLE  monograph_risk_monographs
--    ADD CONSTRAINT  monograph_risk_mono_UK 
--    UNIQUE ( monoriskstmt_id );

---------------------------------------------------------------------
CREATE TABLE monograph_risk_note_gentexts (
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_risk_note_gentexts ADD (
    CONSTRAINT  monograph_risknote_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_risk_src_ingreds ( 
    monoriskstmt_id Integer NOT NULL,
    ingred_id Integer NOT NULL
);
ALTER TABLE monograph_risk_src_ingreds ADD (
    CONSTRAINT  monograph_risk_src_ingreds_PK
    PRIMARY KEY ( monoriskstmt_id,ingred_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE monograph_riskstmt_citations (
    monoriskstmt_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_riskstmt_citations ADD (
    CONSTRAINT  monograph_riskstmt_citats_PK 
    PRIMARY KEY ( monoriskstmt_id,citation_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
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

---------------------------------------------------------------------
CREATE TABLE monograph_riskstmt_orgparts (
    monoriskstmt_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);
ALTER TABLE  monograph_riskstmt_orgparts ADD (
    CONSTRAINT  monograph_riskstmt_orgparts_PK 
    PRIMARY KEY ( monoriskstmt_id,orgpart_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_roa_citations (
    monoadminrt_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_roa_citations ADD (
    CONSTRAINT  monograph_roa_citations_PK 
    PRIMARY KEY ( monoadminrt_id,citation_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE monograph_roa_dosage_forms ( 
    monoadminrt_id Integer NOT NULL,
    dosefrm_id Integer NOT NULL
);
ALTER TABLE monograph_roa_dosage_forms ADD (
    CONSTRAINT  monograph_roa_dosage_forms_PK
    PRIMARY KEY ( monoadminrt_id,dosefrm_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
-- MonographRouteOfAdministration DosageFormNotes
CREATE TABLE monograph_roa_gentexts ( 
    monoadminrt_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_roa_gentexts ADD (
    CONSTRAINT  monograph_roa_gentexts_PK
    PRIMARY KEY ( monoadminrt_id,gentext_id ) 
    USING INDEX 
); 


---------------------------------------------------------------------
CREATE TABLE monograph_rskstmt_mono_uses (
    monoriskstmt_id Integer NOT NULL,
    monouse_id Integer NOT NULL
);
ALTER TABLE  monograph_rskstmt_mono_uses ADD (
    CONSTRAINT  monograph_rskstmt_mono_uses_PK 
    PRIMARY KEY ( monoriskstmt_id,monouse_id ) 
    USING INDEX 
); 



---------------------------------------------------------------------
--CREATE TABLE monograph_subingdose_monodose (
--    monodose_id Integer NOT NULL,
--    monosubingdose_id Integer NOT NULL
--);
--
--ALTER TABLE  monograph_subingdose_monodose ADD (
--    CONSTRAINT  monograph_sbngds_mndss_PK 
--    PRIMARY KEY ( monodose_id,monosubingdose_id ) 
--    USING INDEX 
--); 

---------------------------------------------------------------------
CREATE TABLE monograph_spec_info_gentexts ( 
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_spec_info_gentexts ADD (
    CONSTRAINT  monograph_specinfo_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_stor_cond_gentexts (  
    mono_id Integer NOT NULL,
    gentext_id Integer NOT NULL
);
ALTER TABLE monograph_stor_cond_gentexts ADD (
    CONSTRAINT  monograph_storcond_gentexts_PK
    PRIMARY KEY ( mono_id,gentext_id ) 
    USING INDEX 
); 

--------------------------------------------------------------------- 
CREATE TABLE monograph_sub_populations (
    mono_id Integer NOT NULL,
    subpop_id Integer NOT NULL
);
ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_subpopulations_PK 
    PRIMARY KEY (mono_id,subpop_id ) 
    USING INDEX 
);
 
---------------------------------------------------------------------
CREATE TABLE monograph_use_citations (
    monouse_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  monograph_use_citations ADD (
    CONSTRAINT  monograph_use_citations_PK 
    PRIMARY KEY ( monouse_id,citation_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
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

---------------------------------------------------------------------
CREATE TABLE monograph_use_orgparts (
    monouse_id Integer NOT NULL,
    orgpart_id Integer NOT NULL
);
ALTER TABLE  monograph_use_orgparts ADD (
    CONSTRAINT  monograph_use_orgparts_PK 
    PRIMARY KEY ( monouse_id,orgpart_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monograph_uses_monographs (
    monouse_id Integer NOT NULL,
    mono_id Integer NOT NULL
);
ALTER TABLE  monograph_uses_monographs ADD (
    CONSTRAINT  monograph_uses_mono_PK 
    PRIMARY KEY ( monouse_id,mono_id ) 
    USING INDEX 
); 

-- Temporarily removed until data is corrected.  GB.  2007-11-08
--ALTER TABLE  monograph_uses_monographs
--    ADD CONSTRAINT  monograph_uses_mono_UK 
--    UNIQUE ( monouse_id );


---------------------------------------------------------------------
-- Joins MonographSubIngredientDoseCombination to MonographSubIngredientDose
CREATE TABLE monosbingdcmbs_monosbingdss (
    mono_subing_dosecomb_id Integer NOT NULL,
    mono_subing_dose_id Integer NOT NULL
);
ALTER TABLE  monosbingdcmbs_monosbingdss ADD (
    CONSTRAINT  monosbingdcmbs_monosbingdss_PK 
    PRIMARY KEY ( mono_subing_dosecomb_id,mono_subing_dose_id ) 
    USING INDEX 
); 
    
---------------------------------------------------------------------
CREATE TABLE mono_srcingred_citations (
	mono_source_ingred_id  Integer NOT NULL,	 
	citation_id Integer NOT NULL
);
ALTER TABLE  mono_srcingred_citations ADD (
   	CONSTRAINT  mono_srcingred_citations_PK 
    PRIMARY KEY ( mono_source_ingred_id,citation_id ) 
    USING INDEX 
);

---------------------------------------------------------------------
CREATE TABLE monodrfruse_monosrcorgparts (
	monodfu_id  Integer NOT NULL,	 
	monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE  monodrfruse_monosrcorgparts ADD (
   	CONSTRAINT  monodrfruse_monosrcorgparts_PK 
    PRIMARY KEY ( monodfu_id,monosrc_orgpart_id ) 
    USING INDEX 
);
CREATE INDEX  monodrfruse_mnsrcorgparts_IDX1 
    ON  monodrfruse_monosrcorgparts (monosrc_orgpart_id);


---------------------------------------------------------------------
CREATE TABLE monoduration_monosrcorgparts (
	monodur_id  Integer NOT NULL,	 
	monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE  monoduration_monosrcorgparts ADD (
   	CONSTRAINT  monodur_monosrcorgparts_PK 
    PRIMARY KEY ( monodur_id,monosrc_orgpart_id ) 
    USING INDEX 
);
CREATE INDEX  monodur_monosrcorgparts_IDX1 
    ON  monoduration_monosrcorgparts (monosrc_orgpart_id);


---------------------------------------------------------------------
CREATE TABLE monorisk_monosrcorgparts (
	monoriskstmt_id  Integer NOT NULL,	 
	monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE  monorisk_monosrcorgparts ADD (
   	CONSTRAINT  monorisk_monosrcorgparts_PK 
    PRIMARY KEY ( monoriskstmt_id,monosrc_orgpart_id ) 
    USING INDEX 
);
CREATE INDEX  monorisk_monosrcorgparts_IDX1 
    ON  monorisk_monosrcorgparts (monosrc_orgpart_id);

---------------------------------------------------------------------
CREATE TABLE monodose_monosrcorgparts (
	monodose_id  Integer NOT NULL,	 
	monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE  monodose_monosrcorgparts ADD (
   	CONSTRAINT  monodose_monosrcorgparts_PK 
    PRIMARY KEY ( monodose_id,monosrc_orgpart_id ) 
    USING INDEX 
);
CREATE INDEX  monodose_monosrcorgparts_IDX1 
    ON  monodose_monosrcorgparts (monosrc_orgpart_id);
    
---------------------------------------------------------------------
CREATE TABLE monouse_dosageformgroups (
	monouse_id  Integer NOT NULL,	 
	monouse_dosageformgroup_id Integer NOT NULL
);
ALTER TABLE  monouse_dosageformgroups ADD (
   	CONSTRAINT  monouse_dosageformgroups_PK 
    PRIMARY KEY ( monouse_id,monouse_dosageformgroup_id ) 
    USING INDEX 
);
CREATE INDEX  monouse_dosageformgroups_IDX1 
    ON  monouse_dosageformgroups (monouse_dosageformgroup_id);

---------------------------------------------------------------------
CREATE TABLE monouse_monosrcorgparts (
	monouse_id  Integer NOT NULL,	 
	monosrc_orgpart_id Integer NOT NULL
);
ALTER TABLE  monouse_monosrcorgparts ADD (
   	CONSTRAINT  monouse_monosrcorgparts_PK 
    PRIMARY KEY ( monouse_id,monosrc_orgpart_id ) 
    USING INDEX 
);
CREATE INDEX  monouse_monosrcorgparts_IDX1 
    ON  monouse_monosrcorgparts (monosrc_orgpart_id);
    
-----------------------------------------------------
-- NHP Classification - Microbiological Impurity 
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
    ON  nhp_class_bio_impurities (bioimpurity_id); 

CREATE INDEX  nhp_class_bio_impurities_IDX2 
    ON  nhp_class_bio_impurities (nhpclass_id);
    
---------------------------------------------------------------------
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
    ON  nhp_class_idnt_test_methods (nhpclass_id); 

CREATE INDEX  nhp_cls_idnt_tst_mtds_IDX2 
    ON  nhp_class_idnt_test_methods (testmethod_id); 
    
---------------------------------------------------------------------
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
    
---------------------------------------------------------------------
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
    ON  nhp_class_ingredients (ingred_id); 
CREATE INDEX  nhp_class_ingredients_IDX2 
    ON  nhp_class_ingredients (nhpclass_id); 
    
---------------------------------------------------------------------
CREATE TABLE  nhp_class_qty_test_methods (
    nhpclass_id Integer default null NOT NULL, 
    testmethod_id Integer default null NOT NULL
);
ALTER TABLE  nhp_class_qty_test_methods ADD (
    CONSTRAINT  nhp_class_qty_test_methods_PK 
    PRIMARY KEY ( nhpclass_id,testmethod_id ) 
    USING INDEX 
); 
CREATE INDEX  nhp_class_qty_test_mthds_IDX1 
    ON  nhp_class_qty_test_methods (nhpclass_id); 
CREATE INDEX  nhp_class_qty_test_mthds_IDX2 
    ON  nhp_class_qty_test_methods (testmethod_id); 
    
---------------------------------------------------------------------    
CREATE TABLE  organism_countries (
    country_id Integer default '0' NOT NULL, 
    organism_id Integer default null NOT NULL
);
CREATE INDEX  organism_countries_IDX1 
    ON  organism_countries (country_id); 
CREATE INDEX  organism_countries_IDX2 
    ON  organism_countries (organism_id); 
    
---------------------------------------------------------------------
CREATE TABLE  organism_geographic_areas (
    geogarea_id Integer default null NOT NULL, 
    organism_id Integer default null NOT NULL
);
CREATE INDEX  organism_geographic_areas_IDX1 
    ON  organism_geographic_areas (geogarea_id); 
CREATE INDEX  organism_geographic_areas_IDX2 
    ON  organism_geographic_areas (organism_id); 
    
---------------------------------------------------------------------
CREATE TABLE  organism_part_subingredients (
    orgpart_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  organism_part_subingred_IDX1 
    ON  organism_part_subingredients (orgpart_id); 
CREATE INDEX  organism_part_subingred_IDX2 
    ON  organism_part_subingredients (subingred_id); 
    

---------------------------------------------------------------------
CREATE TABLE organism_qualifiedsynonyms (
    organism_id Integer NOT NULL,
    qualsyno_id Integer NOT NULL
);
ALTER TABLE  organism_qualifiedsynonyms ADD (
    CONSTRAINT  organism_qualifiedsynonyms_PK 
    PRIMARY KEY ( organism_id , qualsyno_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  organism_synonyms (
    organism_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
ALTER TABLE  organism_synonyms
    ADD CONSTRAINT  organism_synonyms_UK1 
    UNIQUE (organism_id,synonym_id);
CREATE INDEX  organism_synonyms_IDX1 
    ON  organism_synonyms (organism_id); 
CREATE INDEX  organism_synonyms_IDX2 
    ON  organism_synonyms (synonym_id);
    
    --------------------------------------------------------------------- 
CREATE TABLE  organism_type_grp_orgparttypes (
    orgparttype_id Integer default null NOT NULL, 
    orgtypegrp_id Integer default null NOT NULL
);
CREATE INDEX  organism_typegrporgprttp_IDX1 
    ON  organism_type_grp_orgparttypes (orgparttype_id); 

CREATE INDEX  organism_typegrporgprttp_IDX2 
    ON  organism_type_grp_orgparttypes (orgtypegrp_id); 
               
---------------------------------------------------------------------
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
    ON  orgparttype_synonyms (synonym_id); 
    
---------------------------------------------------------------------
--  Preparation Types - Preparation Type Groups 
CREATE TABLE  preptype_preptypegroups (
    preptype_id Integer default null NOT NULL, 
    preptypegrp_id Integer default null NOT NULL
);
ALTER TABLE  preptype_preptypegroups ADD (
    CONSTRAINT  preptype_preptypegroups_PK 
    PRIMARY KEY ( preptype_id,preptypegrp_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
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
    ON  purpose_adminrts (adminrt_id); 
    
---------------------------------------------------------------------
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
    ON  purpose_synonyms (synonym_id); 
    
---------------------------------------------------------------------
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
    ON  purpose_test_specs (purpose_id); 
CREATE INDEX  purpose_test_specs_IDX2 
    ON  purpose_test_specs (testcat_id); 
CREATE INDEX  purpose_test_specs_IDX3 
    ON  purpose_test_specs (testspec_id); 
            
---------------------------------------------------------------------
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
 
---------------------------------------------------------------------
CREATE TABLE  restriction_citations (
    citation_id Integer default null NOT NULL, 
    restrict_id Integer default null NOT NULL
);
ALTER TABLE  restriction_citations
    ADD CONSTRAINT  restriction_citations_UK1 
    UNIQUE (restrict_id,citation_id);
CREATE INDEX  restriction_citations_IDX1 
    ON  restriction_citations (citation_id); 
CREATE INDEX  restriction_citations_IDX2 
    ON  restriction_citations (restrict_id); 
    
---------------------------------------------------------------------
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
    ON  restriction_product_types (prodtype_id); 
CREATE INDEX  restriction_product_types_IDX2 
    ON  restriction_product_types (restrict_id); 
    
---------------------------------------------------------------------
CREATE TABLE  restriction_purposes (
    purpose_id Integer default null NOT NULL, 
    restrict_id Integer default null NOT NULL
);
CREATE INDEX  restriction_purposes_IDX1 
    ON  restriction_purposes (purpose_id); 
CREATE INDEX  restriction_purposes_IDX2 
    ON  restriction_purposes (restrict_id);
     
---------------------------------------------------------------------   
CREATE TABLE  risk_stmts_citations (
    	monoriskstmt_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);
ALTER TABLE  risk_stmts_citations ADD (
    CONSTRAINT  risk_stmts_citations_PK 
    PRIMARY KEY ( monoriskstmt_id,citation_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE  source_citations (
    	monosource_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);
ALTER TABLE  source_citations ADD (
    CONSTRAINT  source_citations_PK 
    PRIMARY KEY ( monosource_id,citation_id ) 
    USING INDEX 
);  

---------------------------------------------------------------------
CREATE TABLE  subingredient_admin_routes (
    adminrt_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_adminrt_IDX1 
    ON  subingredient_admin_routes (adminrt_id); 
CREATE INDEX  subingredient_adminrt_IDX2 
    ON  subingredient_admin_routes (subingred_id); 
    
---------------------------------------------------------------------
CREATE TABLE  subingredient_dosage_forms (
    dosefrm_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_dosefrm_IDX1 
    ON  subingredient_dosage_forms (dosefrm_id); 
CREATE INDEX  subingredient_dosefrm_IDX2 
    ON  subingredient_dosage_forms (subingred_id); 
    
---------------------------------------------------------------------
CREATE TABLE  subingredient_refs (
    ref_id Integer default null NOT NULL, 
    subingred_id Integer default null NOT NULL
);
CREATE INDEX  subingredient_refs_IDX1 
    ON  subingredient_refs (ref_id); 
CREATE INDEX  subingredient_refs_IDX2 
    ON  subingredient_refs (subingred_id); 
    
---------------------------------------------------------------------    
CREATE TABLE  synonym_refs (
    ref_id Integer default null NOT NULL, 
    synonym_id Integer default null NOT NULL
);
CREATE INDEX  synonym_refs_IDX1 
    ON  synonym_refs (ref_id); 
CREATE INDEX  synonym_refs_IDX2 
    ON  synonym_refs (synonym_id);
    
---------------------------------------------------------------------
CREATE TABLE synonym_texts_eng_citations (
    synonym_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  synonym_texts_eng_citations ADD (
    CONSTRAINT  synonym_texts_eng_citats_PK 
    PRIMARY KEY ( synonym_id, citation_id ) 
    USING INDEX 
); 

---------------------------------------------------------------------
CREATE TABLE synonym_texts_fr_citations (
    synonym_id Integer NOT NULL,
    citation_id Integer NOT NULL
);
ALTER TABLE  synonym_texts_fr_citations ADD (
    CONSTRAINT  synonym_texts_fr_citats_PK 
    PRIMARY KEY ( synonym_id, citation_id ) 
    USING INDEX 
);

--------------------------------------------------------------------- 
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
    ON  test_method_categories (testcat_id); 
CREATE INDEX  test_method_categories_IDX2 
    ON  test_method_categories (testmethod_id);  
    
---------------------------------------------------------------------    
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
    ON  test_spec_tst_mtds (testmethod_id); 

CREATE INDEX  test_spec_tst_mtds_IDX2 
    ON  test_spec_tst_mtds (testspec_id);

    
---------------------------------------------------------------------    
CREATE TABLE  txtmonosrcmat_citations (
    text_mono_srcmaterial_id Integer default null NOT NULL, 
    citation_id Integer default null NOT NULL
);
ALTER TABLE  txtmonosrcmat_citations ADD (
    CONSTRAINT  txtmonosrcmat_citations_PK 
    PRIMARY KEY ( text_mono_srcmaterial_id,citation_id ) 
    USING INDEX 
); 
           
---------------------------------------------------------------------
-- TODO rename?  is this mono specific?
CREATE TABLE  use_citations (
    monouse_id Integer  NOT NULL,
	citation_id Integer  NOT NULL 
);
ALTER TABLE  use_citations ADD (
    CONSTRAINT  use_citations_PK 
    PRIMARY KEY ( monouse_id,citation_id ) 
    USING INDEX 
); 
                      