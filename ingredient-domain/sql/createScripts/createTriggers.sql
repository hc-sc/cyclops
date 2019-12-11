--
-- Create triggers.  Please maintain alpha order.
--
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

CREATE OR REPLACE TRIGGER audit_entries_TRG 
BEFORE INSERT OR UPDATE ON audit_entries
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.audit_entries_creation_date := SYSDATE; 
    :NEW.audit_entries_lastupdate_date := SYSDATE; 
  ELSIF UPDATING 
  THEN 
    :NEW.audit_entries_lastupdate_date := SYSDATE; 
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


CREATE OR REPLACE TRIGGER dietary_allowances_IU_TRG 
BEFORE INSERT OR UPDATE ON dietary_allowances 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.dietallo_creation_date := SYSDATE; 
    :NEW.dietallo_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.dietallo_lastupdate_date := SYSDATE; 
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

CREATE OR REPLACE TRIGGER homeopathic_dilutions_IU_TRG 
BEFORE INSERT OR UPDATE ON homeopathic_dilutions 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.homeodilu_creation_date := SYSDATE; 
    :NEW.homeodilu_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.homeodilu_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER homeopathic_formulas_IU_TRG 
BEFORE INSERT OR UPDATE ON homeopathic_formulas 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.homeoform_creation_date := SYSDATE; 
    :NEW.homeoform_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.homeoform_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER homeopathic_gen_text_IU_TRG 
BEFORE INSERT OR UPDATE ON homeopathic_generic_texts  
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.homeogentext_creation_date := SYSDATE; 
    :NEW.homeogentext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.homeogentext_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER homeopathic_methofpreps_IU_TRG 
BEFORE INSERT OR UPDATE ON homeopathic_methofpreps 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.homeomop_creation_date := SYSDATE; 
    :NEW.homeomop_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.homeomop_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER homeopathic_prep_Types_IU_TRG 
BEFORE INSERT OR UPDATE ON homeopathic_preparation_Types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.homeopreptype_creation_date := SYSDATE; 
    :NEW.homeopreptype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.homeopreptype_lastupdate_date := SYSDATE; 
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


--CREATE OR REPLACE TRIGGER monograph_sources_IU_TRG 
--BEFORE INSERT OR UPDATE ON monograph_sources
--FOR EACH ROW 
--BEGIN 
--  IF INSERTING 
--  THEN 
--    :NEW.monosource_creation_date := SYSDATE; 
--    :NEW.monosource_lastupdate_date := SYSDATE; 
--    :NEW.useracc_id := 2; 
--  ELSIF UPDATING 
--  THEN 
--    :NEW.monosource_lastupdate_date := SYSDATE; 
--    :NEW.useracc_id := 2; 
--  END IF; 
--END; 
--/

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

CREATE OR REPLACE TRIGGER monograph_types_IU_TRG 
BEFORE INSERT OR UPDATE ON monograph_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.monotype_creation_date := SYSDATE; 
    :NEW.monotype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.monotype_lastupdate_date := SYSDATE; 
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

CREATE OR REPLACE TRIGGER permissions_IU_TRG 
BEFORE INSERT OR UPDATE ON permissions 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.permission_creation_date := SYSDATE; 
    :NEW.permission_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.permission_lastupdate_date := SYSDATE; 
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


CREATE OR REPLACE TRIGGER recommended_diet_allo_IU_TRG 
BEFORE INSERT OR UPDATE ON recommended_dietary_allowances 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.recdietallo_creation_date := SYSDATE; 
    :NEW.recdietallo_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.recdietallo_lastupdate_date := SYSDATE; 
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

CREATE OR REPLACE TRIGGER test_method_types_TRG 
BEFORE INSERT OR UPDATE ON test_method_types
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.testmthd_type_creation_date := SYSDATE; 
    :NEW.testmthd_type_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.testmthd_type_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE OR REPLACE TRIGGER test_method_subtypes_TRG 
BEFORE INSERT OR UPDATE ON test_method_subtypes
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.testmthd_subtype_creation_date := SYSDATE; 
    :NEW.testmthd_subtype_lastupdt_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.testmthd_subtype_lastupdt_date := SYSDATE; 
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

CREATE OR REPLACE TRIGGER user_roles_IU_TRG 
BEFORE INSERT OR UPDATE ON user_roles 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.userrole_creation_date := SYSDATE; 
    :NEW.userrole_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.userrole_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/
