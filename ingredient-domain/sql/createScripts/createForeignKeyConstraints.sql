--
-- Foreign Key Constraints.  Please retain Alpha order.  
--
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

-- TODO - table removed - does this need to be changed?
--ALTER TABLE  dosageform_citations ADD (
--    CONSTRAINT  dosageform_citations_FK1 
--    FOREIGN KEY ( monodosfrm_id ) 
--    REFERENCES monograph_dosage_forms ( monodosfrm_id));

ALTER TABLE  dietary_allowances ADD 
    CONSTRAINT  dietary_allowances_FK1 
    FOREIGN KEY ( subpop_id ) 
    REFERENCES sub_population ( subpop_id ); 

ALTER TABLE  dietary_allowances ADD 
    CONSTRAINT  dietary_allowances_FK2 
    FOREIGN KEY ( recdietallo_id ) 
    REFERENCES recommended_dietary_allowances ( recdietallo_id ); 

ALTER TABLE  dietary_allowances ADD 
    CONSTRAINT  dietary_allowances_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

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
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  dosage_forms ADD 
    CONSTRAINT  dosage_forms_FK2 
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

ALTER TABLE  homeopathic_common_name_hgts ADD 
    CONSTRAINT  homeopathic_comm_name_FK1
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  homeopathic_common_name_hgts ADD 
    CONSTRAINT  homeopathic_comm_name_FK2 
    FOREIGN KEY ( homeogentext_id ) 
    REFERENCES homeopathic_generic_texts ( homeogentext_id ); 

ALTER TABLE  homeopathic_dilutions ADD 
    CONSTRAINT  homeopathic_dilutions_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  homeopathic_formulas ADD 
    CONSTRAINT  homeopathic_formulas_FK1
    FOREIGN KEY ( ingredrole_id ) 
    REFERENCES ingredient_roles ( ingredrole_id ); 

ALTER TABLE  homeopathic_formulas ADD 
    CONSTRAINT  homeopathic_formulas_FK2
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id ); 

ALTER TABLE  homeopathic_formulas ADD 
    CONSTRAINT  homeopathic_formulas_FK3 
    FOREIGN KEY ( homeodilu_id ) 
    REFERENCES homeopathic_dilutions ( homeodilu_id ); 

ALTER TABLE  homeopathic_formulas ADD 
    CONSTRAINT  homeopathic_formulas_FK4 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  homeopathic_generic_texts ADD 
    CONSTRAINT  homeopathic_generic_texts_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  homeopathic_methofpreps ADD 
    CONSTRAINT  homeopathic_methofpreps_FK1
    FOREIGN KEY ( homeoform_id ) 
    REFERENCES homeopathic_formulas ( homeoform_id ); 

ALTER TABLE  homeopathic_methofpreps ADD 
    CONSTRAINT  homeopathic_methofpreps_FK2
    FOREIGN KEY ( homeopreptype_id) 
    REFERENCES homeopathic_preparation_Types ( homeopreptype_id); 

ALTER TABLE  homeopathic_methofpreps ADD 
    CONSTRAINT  homeopathic_methofpreps_FK3 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  homeopathic_preparation_Types ADD 
    CONSTRAINT  homeopathic_prep_Types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  homeopathic_proper_name_hgts ADD 
    CONSTRAINT  homeopathic_proper_name_FK1
    FOREIGN KEY ( ingred_id ) 
    REFERENCES ingredients ( ingred_id ); 

ALTER TABLE  homeopathic_proper_name_hgts ADD 
    CONSTRAINT  homeopathic_proper_name_FK2 
    FOREIGN KEY ( homeogentext_id ) 
    REFERENCES homeopathic_generic_texts ( homeogentext_id ); 

ALTER TABLE  homeopathic_source_mat_hgts ADD 
    CONSTRAINT  homeopathic_source_mat_FK1
    FOREIGN KEY ( homeoform_id ) 
    REFERENCES homeopathic_formulas( homeoform_id );

ALTER TABLE  homeopathic_source_mat_hgts ADD 
    CONSTRAINT  homeopathic_source_mat_FK2 
    FOREIGN KEY ( homeogentext_id ) 
    REFERENCES homeopathic_generic_texts ( homeogentext_id ); 

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
    FOREIGN KEY ( attachment_eng_id ) 
    REFERENCES attachments ( attachment_id ); 

ALTER TABLE  monographs ADD 
    CONSTRAINT  mono_FK4 
    FOREIGN KEY ( attachment_fr_id ) 
    REFERENCES attachments ( attachment_id ); 

ALTER TABLE  monograph_citation_joins ADD (
    CONSTRAINT  monograph_citation_joins_FK1
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs ( mono_id )); 

ALTER TABLE  monograph_citation_joins ADD (
    CONSTRAINT  monograph_citation_joins_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations ( citation_id )); 

---------------------------------------------------------------------
ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK1 
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK2 
    FOREIGN KEY (monoprep_id) 
    REFERENCES monograph_preparations (monoprep_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK3 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_for_uses_FK4 
    FOREIGN KEY ( monodosecomb_id ) 
    REFERENCES monograph_dosecombs ( monodosecomb_id )); 

ALTER TABLE  monograph_directions_for_uses ADD (
    CONSTRAINT  monograph_directs_fr_uses_FK5
    FOREIGN KEY ( mono_source_ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 
    

--------------------------------------------------------------------- 
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
    FOREIGN KEY (monoprep_id) 
    REFERENCES monograph_preparations (monoprep_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK6 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK7
    FOREIGN KEY (resttype_id) 
    REFERENCES restriction_types (resttype_id));

ALTER TABLE  monograph_doses ADD (
    CONSTRAINT  monograph_doses_FK8
    FOREIGN KEY ( mono_source_ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 
    
---------------------------------------------------------------------     
ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_FK1 
    FOREIGN KEY (monodose_id) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_citations ADD (
    CONSTRAINT  monograph_dose_citations_FK2 
    FOREIGN KEY (citation_id) 
    REFERENCES citations (citation_id));

ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  mono_dose_mono_uses_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));

ALTER TABLE  monograph_dose_mono_uses ADD (
    CONSTRAINT  mono_dose_mono_uses_FK2
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_dose_mono_dfus ADD (
    CONSTRAINT  mono_dose_mono_dfu_FK1
    FOREIGN KEY ( monodose_id ) 
    REFERENCES monograph_doses (monodose_id));
  
ALTER TABLE  monograph_dose_mono_dfus ADD (
    CONSTRAINT  mono_dose_mono_dfu_FK2
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
    
---------------------------------------------------------------------    
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


ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_FK1 
    FOREIGN KEY ( monodfu_id ) 
    REFERENCES monograph_directions_for_uses ( monodfu_id )); 

ALTER TABLE  monograph_drfruse_orgparts ADD (
    CONSTRAINT  monograph_drfruse_orgparts_FK2
    FOREIGN KEY ( orgpart_id ) 
    REFERENCES organism_parts ( orgpart_id ));
     
---------------------------------------------------------------------
ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK2 
    FOREIGN KEY (units_id) 
    REFERENCES units (units_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK3
    FOREIGN KEY (monoprep_id) 
    REFERENCES monograph_preparations (monoprep_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK4 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK5
    FOREIGN KEY ( monodosecomb_id ) 
    REFERENCES monograph_dosecombs ( monodosecomb_id )); 

ALTER TABLE  monograph_durations ADD (
    CONSTRAINT  monograph_durations_FK6
    FOREIGN KEY ( mono_source_ingred_id ) 
    REFERENCES ingredients ( ingred_id )); 

---------------------------------------------------------------------
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
  
ALTER TABLE  monograph_dur_monographs ADD (
    CONSTRAINT  monograph_dur_mono_FK1
    FOREIGN KEY ( monodur_id ) 
    REFERENCES monograph_durations (monodur_id));

ALTER TABLE  monograph_dur_monographs ADD (
    CONSTRAINT  monograph_dur_mono_FK2
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
    
ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries (monoent_id));
  
ALTER TABLE monograph_entry_generic_texts ADD (
    CONSTRAINT monograph_entry_gentext_FK2
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

ALTER TABLE monograph_monograph_types ADD (
    CONSTRAINT monograph_mono_types_FK1
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE monograph_monograph_types ADD (
    CONSTRAINT monograph_mono_types_FK2
    FOREIGN KEY (monotype_id) 
    REFERENCES monograph_types (monotype_id));

---------------------------------------------------------------------    
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
    FOREIGN KEY (monoprep_id) 
    REFERENCES monograph_preparations (monoprep_id));

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK5 
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id)); 

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK6
    FOREIGN KEY ( monodosecomb_id ) 
    REFERENCES monograph_dosecombs ( monodosecomb_id )); 

ALTER TABLE  monograph_risk_stmts ADD (
    CONSTRAINT  monograph_risk_stmts_FK7
    FOREIGN KEY ( mono_source_ingred_id ) 
    REFERENCES mono_source_ingredients ( mono_source_ingred_id )); 
---------------------------------------------------------------------

ALTER TABLE  monograph_risk_monographs ADD (
    CONSTRAINT  monograph_risk_mono_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_risk_monographs ADD (
    CONSTRAINT  monograph_risk_mono_FK2
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

ALTER TABLE  monograph_rskstmt_mono_uses ADD (
    CONSTRAINT  mono_rskstmt_mono_uses_FK1
    FOREIGN KEY ( monoriskstmt_id ) 
    REFERENCES monograph_risk_stmts (monoriskstmt_id));

ALTER TABLE  monograph_rskstmt_mono_uses ADD (
    CONSTRAINT  mono_rskstmt_mono_uses_FK2
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

ALTER TABLE  monograph_roa_citations ADD (
    CONSTRAINT  monograph_roa_cits_FK1
    FOREIGN KEY ( monoadminrt_id ) 
    REFERENCES monograph_routeofadmins (monoadminrt_id));

ALTER TABLE  monograph_roa_citations ADD (
    CONSTRAINT  monograph_roa_cits_FK2
    FOREIGN KEY ( citation_id ) 
    REFERENCES citations (citation_id));

--
--ALTER TABLE  monograph_sources ADD 
--    CONSTRAINT  monograph_sources_FK1 
--    FOREIGN KEY ( monoent_id ) 
--    REFERENCES monograph_entries ( monoent_id ); 

ALTER TABLE mono_source_ingredients ADD (
    CONSTRAINT  mono_source_ingred_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts (useracc_id));

ALTER TABLE mono_source_ingredients ADD (
    CONSTRAINT  mono_source_ingred_FK2
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

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
    FOREIGN KEY ( monosubingcomb_id ) 
    REFERENCES monograph_subingcombs ( monosubingcomb_id ); 

ALTER TABLE  monograph_subingdoses ADD (
    CONSTRAINT  monograph_subingdoses_FK5
    FOREIGN KEY (resttype_id) 
    REFERENCES restriction_types (resttype_id));

ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_sub_populations_FK1
    FOREIGN KEY (mono_id) 
    REFERENCES monographs (mono_id));

ALTER TABLE  monograph_sub_populations ADD (
    CONSTRAINT  monograph_sub_populations_FK2
    FOREIGN KEY (subpop_id) 
    REFERENCES sub_population (subpop_id));

ALTER TABLE  monograph_types ADD 
    CONSTRAINT  monograph_types_FK2 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 
        
---------------------------------------------------------------------    
ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK1
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));
 
ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK2
    FOREIGN KEY (monoprep_id) 
    REFERENCES monograph_preparations (monoprep_id));

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK4
    FOREIGN KEY ( monodosecomb_id ) 
    REFERENCES monograph_dosecombs ( monodosecomb_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK5
    FOREIGN KEY ( mono_source_ingred_id ) 
    REFERENCES mono_source_ingredients ( mono_source_ingred_id )); 

ALTER TABLE  monograph_uses ADD (
    CONSTRAINT  monograph_uses_FK6
    FOREIGN KEY ( usetype_id ) 
    REFERENCES use_types ( usetype_id )); 
---------------------------------------------------------------------

ALTER TABLE  monograph_uses_monographs ADD (
    CONSTRAINT  monograph_uses_mono_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses (monouse_id));

ALTER TABLE  monograph_uses_monographs ADD (
    CONSTRAINT  monograph_uses_mono_FK2
    FOREIGN KEY ( mono_id ) 
    REFERENCES monographs (mono_id));   
   
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



ALTER TABLE  monograph_use_gentexts ADD (
    CONSTRAINT  monograph_use_gentexts_FK1
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses ( monouse_id )); 

ALTER TABLE  monograph_use_gentexts ADD (
    CONSTRAINT  monograph_use_gentexts_FK2
    FOREIGN KEY ( gentext_id ) 
    REFERENCES generic_texts ( gentext_id )); 


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

ALTER TABLE  permissions ADD (
    CONSTRAINT  permissions_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));

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

ALTER TABLE  recommended_dietary_allowances ADD 
    CONSTRAINT  recommended_dietary_allow_FK1 
    FOREIGN KEY ( monoent_id ) 
    REFERENCES monograph_entries ( monoent_id ); 

ALTER TABLE  recommended_dietary_allowances ADD 
    CONSTRAINT  recommended_dietary_allow_FK2 
    FOREIGN KEY ( monouse_id ) 
    REFERENCES monograph_uses ( monouse_id ); 

ALTER TABLE  recommended_dietary_allowances ADD 
    CONSTRAINT  recommended_dietary_allow_FK3 
    FOREIGN KEY ( units_id ) 
    REFERENCES units ( units_id ); 

ALTER TABLE  recommended_dietary_allowances ADD 
    CONSTRAINT  recommended_dietary_allow_FK4 
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
    
--ALTER TABLE  source_citations ADD (
--    CONSTRAINT  source_citations_FK1 
--    FOREIGN KEY ( monosource_id ) 
--    REFERENCES monograph_sources ( monosource_id ));

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

ALTER TABLE  test_methods ADD 
    CONSTRAINT  test_methods_FK2 
    FOREIGN KEY ( testmthd_type_id ) 
    REFERENCES test_method_types ( testmthd_type_id ); 

ALTER TABLE  test_methods ADD 
    CONSTRAINT  test_methods_FK3 
    FOREIGN KEY ( testmthd_subtype_id ) 
    REFERENCES test_method_subtypes ( testmthd_subtype_id ); 

ALTER TABLE  test_method_types ADD 
    CONSTRAINT  test_method_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id );

ALTER TABLE  test_method_subtypes ADD 
    CONSTRAINT  test_method_subtypes_FK1 
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

ALTER TABLE  useraccount_userroles ADD 
    CONSTRAINT  useraccount_userroles_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

ALTER TABLE  useraccount_userroles ADD 
    CONSTRAINT  useraccount_userroles_FK2 
    FOREIGN KEY ( userrole_id ) 
    REFERENCES user_roles ( userrole_id ); 

ALTER TABLE  userrole_permissions ADD 
    CONSTRAINT  userrole_permissions_FK1 
    FOREIGN KEY ( userrole_id ) 
    REFERENCES user_roles ( userrole_id ); 

ALTER TABLE  userrole_permissions ADD 
    CONSTRAINT  userrole_permissions_FK2 
    FOREIGN KEY ( permission_id ) 
    REFERENCES permissions ( permission_id ); 

ALTER TABLE  user_roles ADD (
    CONSTRAINT  user_roles_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ));
    