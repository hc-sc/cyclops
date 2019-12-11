-------------------------------------------------------------------------------
--                                 ePLA 2.0                                  --
-------------------------------------------------------------------------------
-- These are the changes that were done to suppor the ePLA 2.0 functionality.--
-- To implement, you need to run the @build_script that call all the other   --
-- script and then you need to run this script to upgrade the tables for the --
-- new functionality.                                                        --
-------------------------------------------------------------------------------

----------------------
-- Preparation Rule --
----------------------

DROP TABLE preprules_solvents;
DROP TABLE preparation_rules;
CREATE TABLE  preparation_rules (
    preprule_id                    Integer       default '0'  NOT NULL, 
    preprule_code                  Varchar2(10)  default null NOT NULL,
    preprule_freshdrypresent       Varchar2(1)   default null NOT NULL, 
    preprule_quantcrudequivpres    Varchar2(1)   default null NOT NULL,
    preprule_ratio                 Varchar2(10)  default null,
    preprule_solventpresent        Varchar2(1)   default null,
    preprule_standardizedpresent   Varchar2(1)   default null NOT NULL,
    preprule_creation_date         Date          NOT NULL, 
    preprule_lastupdate_date    Date          NOT NULL, 
    useracc_id                   Integer       default NULL 
);

ALTER TABLE  preparation_rules ADD (
    CONSTRAINT  preparation_rules_PK 
    PRIMARY KEY ( preprule_id ) 
    USING INDEX 
); 

ALTER TABLE  preparation_rules 
    ADD CONSTRAINT  preparation_rules_UK1 
    UNIQUE (preprule_code);

ALTER TABLE  preparation_rules ADD 
    CONSTRAINT  preparation_rules_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

CREATE TABLE preprules_solvents (
    preprule_id Integer NOT NULL,
    solvents_id Integer NOT NULL
);
ALTER TABLE  preprules_solvents ADD (
    CONSTRAINT  preprules_solvents_PK
    PRIMARY KEY ( preprule_id, solvents_id ) 
    USING INDEX 
); 

ALTER TABLE  preprules_solvents ADD 
    CONSTRAINT  preprules_solvents_FK1 
    FOREIGN KEY ( preprule_id ) 
    REFERENCES preparation_rules ( preprule_id ); 

ALTER TABLE  preprules_solvents ADD 
    CONSTRAINT  preprules_solvents_FK2 
    FOREIGN KEY ( solvents_id ) 
    REFERENCES solvents ( solvents_id ); 

CREATE OR REPLACE TRIGGER preparation_rules_IU_TRG 
BEFORE INSERT OR UPDATE ON preparation_rules 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.preprule_creation_date := SYSDATE; 
    :NEW.preprule_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.preprule_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE SEQUENCE PREPARATION_RULES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

Commit;

----------------------
-- Application Type --
----------------------

DROP TABLE application_types;
CREATE TABLE  application_types (
    apptype_id                    Integer       default '0'  NOT NULL, 
    apptype_code                  Varchar2(100)  default null NOT NULL,
    apptype_name_eng              Varchar2(100) default null,
    apptype_name_fr               Varchar2(100) default null,
    apptype_creation_date         Date          NOT NULL, 
    apptype_lastupdate_date       Date          NOT NULL, 
    useracc_id                    Integer       default NULL 
);

ALTER TABLE  application_types ADD (
    CONSTRAINT  application_types_PK 
    PRIMARY KEY ( apptype_id ) 
    USING INDEX 
); 

ALTER TABLE  application_types 
    ADD CONSTRAINT  application_types_UK1 
    UNIQUE ( apptype_code );

ALTER TABLE  application_types ADD 
    CONSTRAINT  application_types_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

CREATE OR REPLACE TRIGGER application_types_IU_TRG 
BEFORE INSERT OR UPDATE ON application_types 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.apptype_creation_date := SYSDATE; 
    :NEW.apptype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.apptype_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE SEQUENCE APPLICATION_TYPES_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

Commit;

----------------------
-- Dosage Unit --
----------------------

DROP TABLE dosage_units;
CREATE TABLE  dosage_units (
    dosageunit_id                    Integer       default '0'  NOT NULL, 
    dosageunit_code                  Varchar2(100)  default null NOT NULL,
    dosageunit_name_eng              Varchar2(100) default null,
    dosageunit_name_fr               Varchar2(100) default null,
    dosageunit_creation_date         Date          NOT NULL, 
    dosageunit_lastupdate_date       Date          NOT NULL, 
    useracc_id                       Integer       default NULL 
);

ALTER TABLE  dosage_units ADD (
    CONSTRAINT  dosage_units_PK 
    PRIMARY KEY ( dosageunit_id ) 
    USING INDEX 
); 

ALTER TABLE  dosage_units 
    ADD CONSTRAINT  dosage_units_UK1 
    UNIQUE ( dosageunit_code );

ALTER TABLE  dosage_units ADD 
    CONSTRAINT  dosage_units_FK1 
    FOREIGN KEY ( useracc_id ) 
    REFERENCES user_accounts ( useracc_id ); 

CREATE OR REPLACE TRIGGER dosage_units_IU_TRG 
BEFORE INSERT OR UPDATE ON dosage_units 
FOR EACH ROW 
BEGIN 
  IF INSERTING 
  THEN 
    :NEW.dosageunit_creation_date := SYSDATE; 
    :NEW.dosageunit_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  ELSIF UPDATING 
  THEN 
    :NEW.dosageunit_lastupdate_date := SYSDATE; 
    :NEW.useracc_id := 2; 
  END IF; 
END; 
/

CREATE SEQUENCE DOSAGE_UNITS_SEQ
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

Commit;

-------------------------------------------
-- Dosage Form to Dosage Unit Join Table --
-------------------------------------------

CREATE TABLE dosageform_dosageunits (
    dosefrm_id Integer NOT NULL,
    dosageunit_id Integer NOT NULL
);
ALTER TABLE  dosageform_dosageunits ADD (
    CONSTRAINT  dosageform_dosageunits_PK
    PRIMARY KEY ( dosefrm_id, dosageunit_id ) 
    USING INDEX 
); 

ALTER TABLE  dosageform_dosageunits ADD 
    CONSTRAINT  dosageform_dosageunits_FK1 
    FOREIGN KEY ( dosefrm_id ) 
    REFERENCES dosage_forms ( dosefrm_id ); 

ALTER TABLE  dosageform_dosageunits ADD 
    CONSTRAINT  dosageform_dosageunits_FK2 
    FOREIGN KEY ( dosageunit_id ) 
    REFERENCES dosage_units ( dosageunit_id ); 

----------------------
-- Provinces States --
----------------------

DROP TABLE PROVINCES_STATES;
CREATE TABLE PROVINCES_STATES ( 
   PROVSTATE_ID NUMBER(*,0) DEFAULT null NOT NULL ENABLE, 
   PROVSTATE_CODE VARCHAR2(2) DEFAULT null NOT NULL ENABLE, 
   COUNTRY_ID NUMBER(*,0) DEFAULT null NOT NULL ENABLE, 
   USERACC_ID NUMBER(*,0) DEFAULT NULL,
   PROVSTATE_NAME_ENG VARCHAR2(100) DEFAULT '' NOT NULL ENABLE, 
   PROVSTATE_NAME_FR VARCHAR2(100) DEFAULT '' NOT NULL ENABLE, 
   PROVSTATE_CREATION_DATE DATE NOT NULL ENABLE, 
   PROVSTATE_LASTUPDATE_DATE DATE NOT NULL ENABLE 
);
  
ALTER TABLE PROVINCES_STATES ADD (
    CONSTRAINT  PROVINCES_STATES_PK 
    PRIMARY KEY ( PROVSTATE_ID ) 
    USING INDEX 
);
    
ALTER TABLE PROVINCES_STATES ADD (
    CONSTRAINT  PROVINCES_STATES_UK
    UNIQUE (PROVSTATE_CODE, COUNTRY_ID)
);
    
ALTER TABLE PROVINCES_STATES ADD ( 
    CONSTRAINT PROVINCES_STATES_FK1 
    FOREIGN KEY ( COUNTRY_ID ) 
    REFERENCES COUNTRIES ( COUNTRY_ID )
);
    
ALTER TABLE PROVINCES_STATES ADD (
    CONSTRAINT  PROVINCES_STATES_FK2 
    FOREIGN KEY ( USERACC_ID ) 
    REFERENCES USER_ACCOUNTS ( USERACC_ID )
); 
    
CREATE OR REPLACE TRIGGER PROVINCES_STATES_IU_TRG 
BEFORE INSERT OR UPDATE ON PROVINCES_STATES
FOR EACH ROW
BEGIN
  IF INSERTING
  THEN
    :NEW.PROVSTATE_CREATION_DATE := SYSDATE;
    :NEW.PROVSTATE_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  ELSIF UPDATING
  THEN
    :NEW.PROVSTATE_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  END IF;
END;    
/        

CREATE SEQUENCE PROVINCES_STATES_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

-----------------
-- Common Term --
-----------------
DROP TABLE common_terms;
CREATE TABLE  common_terms (
    commonterm_code            Varchar2(50)  default ''   NOT NULL, 
    commonterm_creation_date   Date                       NOT NULL, 
    commonterm_id              Integer       default null NOT NULL, 
    commonterm_lastupdate_date Date                       NOT NULL, 
    commonterm_name_eng        Varchar2(250) default ''   NOT NULL, 
    commonterm_name_fr         Varchar2(250) default ''   NOT NULL, 
    commontermtype_id          Integer       default null NOT NULL, 
    useracc_id                 Integer       default NULL 
);
ALTER TABLE  common_terms ADD (
    CONSTRAINT  common_terms_PK 
    PRIMARY KEY ( commonterm_id ) 
    USING INDEX 
); 
ALTER TABLE  common_terms 
    ADD CONSTRAINT  common_terms_UK1 
    UNIQUE (commonterm_code);
ALTER TABLE  common_terms 
    ADD CONSTRAINT  common_terms_UK2 
    UNIQUE (commonterm_name_eng);
ALTER TABLE  common_terms 
    ADD CONSTRAINT  common_terms_UK3 
    UNIQUE (commonterm_name_fr);
CREATE INDEX  common_terms_IDX1 
    ON  common_terms (commontermtype_id);
ALTER TABLE common_terms ADD (
    CONSTRAINT  common_terms_FK1 
    FOREIGN KEY ( USERACC_ID ) 
    REFERENCES USER_ACCOUNTS ( USERACC_ID )
); 
commit;

CREATE OR REPLACE TRIGGER common_terms_IU_TRG 
BEFORE INSERT OR UPDATE ON common_terms
FOR EACH ROW
BEGIN
  IF INSERTING
  THEN
    :NEW.commonterm_CREATION_DATE := SYSDATE;
    :NEW.commonterm_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  ELSIF UPDATING
  THEN
    :NEW.commonterm_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  END IF;
END;    
/        

----------------------
-- Common Term Type --
----------------------

DROP TABLE common_term_types;
CREATE TABLE  common_term_types (
    commontermtype_creation_date   Date                       NOT NULL, 
    commontermtype_id              Integer       default null NOT NULL, 
    commontermtype_lastupdate_date Date                       NOT NULL, 
    commontermtype_name_eng        Varchar2(250) default ''   NOT NULL, 
    commontermtype_name_fr         Varchar2(250) default ''   NOT NULL, 
    useracc_id                     Integer       default NULL 
);

ALTER TABLE  common_term_types ADD (
    CONSTRAINT  common_term_types_PK 
    PRIMARY KEY ( commontermtype_id ) 
    USING INDEX 
); 
ALTER TABLE  common_term_types 
    ADD CONSTRAINT  common_term_types_UK1 
    UNIQUE (commontermtype_name_eng);
ALTER TABLE  common_term_types 
    ADD CONSTRAINT  common_term_types_UK2 
    UNIQUE (commontermtype_name_fr);
ALTER TABLE common_term_types ADD (
    CONSTRAINT  common_term_types_FK1 
    FOREIGN KEY ( USERACC_ID ) 
    REFERENCES USER_ACCOUNTS ( USERACC_ID )
); 
commit;

ALTER TABLE common_terms ADD (
    CONSTRAINT  common_terms_FK2 
    FOREIGN KEY ( commontermtype_id ) 
    REFERENCES common_term_types( commontermtype_id )
); 

CREATE OR REPLACE TRIGGER common_term_types_IU_TRG 
BEFORE INSERT OR UPDATE ON common_term_types
FOR EACH ROW
BEGIN
  IF INSERTING
  THEN
    :NEW.commontermtype_CREATION_DATE := SYSDATE;
    :NEW.commontermtype_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  ELSIF UPDATING
  THEN
    :NEW.commontermtype_LASTUPDATE_DATE := SYSDATE;
    :NEW.USERACC_ID := 2;
  END IF;
END;    
/        

CREATE SEQUENCE common_terms_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;

CREATE SEQUENCE common_term_types_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;


---------------------------------
-- Standard or Grade Reference --
---------------------------------
DROP TABLE standard_grade_references;
CREATE TABLE  standard_grade_references (
    standgraderef_ishomeopathic   varchar2(10)  default null NOT NULL,
    standgraderef_creation_date   Date                       NOT NULL, 
    standgraderef_id              Integer       default null NOT NULL, 
    standgraderef_lastupdate_date Date                       NOT NULL, 
    ref_id                        Integer       default null NOT NULL,
    useracc_id                    Integer       default NULL 
);
ALTER TABLE  standard_grade_references  ADD (
    CONSTRAINT  standard_grade_references_PK 
    PRIMARY KEY ( standgraderef_id ) 
    USING INDEX 
); 
ALTER TABLE  standard_grade_references  
    ADD CONSTRAINT  standard_grade_references_UK1 
    UNIQUE ( ref_id );
commit;
ALTER TABLE standard_grade_references ADD (
    CONSTRAINT  standard_grade_references_FK1 
    FOREIGN KEY ( USERACC_ID ) 
    REFERENCES USER_ACCOUNTS ( USERACC_ID )
);
ALTER TABLE standard_grade_references ADD (
    CONSTRAINT  standard_grade_references_FK2 
    FOREIGN KEY ( ref_id ) 
    REFERENCES refs ( ref_id )
);


CREATE OR REPLACE TRIGGER standard_grade_refs_IU_TRG 
BEFORE INSERT OR UPDATE ON standard_grade_references
FOR EACH ROW
BEGIN
  IF INSERTING
  THEN
    :NEW.standgraderef_creation_date := SYSDATE;
    :NEW.standgraderef_lastupdate_date := SYSDATE;
    :NEW.USERACC_ID := 2;
  ELSIF UPDATING
  THEN
    :NEW.standgraderef_lastupdate_date := SYSDATE;
    :NEW.USERACC_ID := 2;
  END IF;
END;    
/    

CREATE SEQUENCE standard_grade_refs_SEQ 
    INCREMENT BY 1 START WITH 1 MAXVALUE 1.0E28 MINVALUE 1 
    NOCYCLE 
    NOCACHE NOORDER;
commit;

------------------
-- Dosage Forms --
------------------
alter table dosage_forms add dosefrm_allow_ingredient_units varchar2(10) default null;

---------------------
-- PreparationType --
---------------------

alter table PREPARATION_TYPES add preptype_extract      Varchar2(1)   default null;
alter table PREPARATION_TYPES add preptype_ratioType    varchar2(10)  default null;
alter table PREPARATION_TYPES add preptype_standardized varchar2(1)   default null;

CREATE TABLE preptypes_solvents (
    preptype_id Integer NOT NULL,
    solvents_id Integer NOT NULL
);
ALTER TABLE  preptypes_solvents ADD (
    CONSTRAINT  preptypes_solvents_PK
    PRIMARY KEY ( preptype_id, solvents_id ) 
    USING INDEX 
); 

ALTER TABLE  preptypes_solvents ADD 
    CONSTRAINT  preptypes_solvents_FK1 
    FOREIGN KEY ( preptype_id ) 
    REFERENCES preparation_types ( preptype_id ); 

ALTER TABLE  preptypes_solvents ADD 
    CONSTRAINT  preptypes_solvents_FK2 
    FOREIGN KEY ( solvents_id ) 
    REFERENCES solvents ( solvents_id ); 

---------------------------
-- Homeopathic Dilutions --
---------------------------

ALTER TABLE homeopathic_dilutions add homeodilu_desc_eng VARCHAR2(250) default null;
ALTER TABLE homeopathic_dilutions add homeodilu_desc_fr VARCHAR2(250) default null;
ALTER TABLE homeopathic_dilutions add homeodilu_diluted VARCHAR2(1) default null;
ALTER TABLE homeopathic_dilutions add homeodilu_ratiotobase NUMBER(22, 8) default null;

--------------------------
-- Homeopathic Formulas --
--------------------------

ALTER TABLE homeopathic_formulas add homeoform_toxic VARCHAR2(1) default null;

-----------
-- Units --
-----------

ALTER TABLE units add units_equivalent VARCHAR2(1) default null;
ALTER TABLE units add units_preferred VARCHAR2(1) default null;

---------------------------------
-- Restriction - SafetyConcern --
---------------------------------

ALTER TABLE restrictions add restrict_safety_concern VARCHAR2(1) default null;
commit;

---------------------------------------------------------
-- Standard Or Grade Reference - Minimum Safety Amount --
---------------------------------------------------------

ALTER TABLE standard_grade_references add standgraderef_min_dil_amount NUMBER(38) default null;

commit;

---------------------------------------------
-- Administration Routes - No Fixed Limits --
---------------------------------------------
ALTER TABLE administration_routes add adminrt_no_fixed_limits VARCHAR2(1) default null;
commit;

---------------------------
-- Units Type - Code     --
-- Units - Ratio To Base --
---------------------------
ALTER TABLE units_types add unitstype_code VARCHAR2(10) default null;
ALTER TABLE units add units_ratio_to_base NUMBER(32,15) default null;
-- ALTER TABLE units modify units_ratio_to_base NUMBER(22,8) default null;
-- ALTER TABLE units add units_ratio_to_base NUMBER(32,15) default null;
commit;


---------------------------------------------
-- Administration Routes - Steril Required --
---------------------------------------------
ALTER TABLE administration_routes add adminrt_sterileRequired VARCHAR2(1) default null;
commit;

---------------------------------------------
-- Non Medicinal Purpose - Artificial Or Natural --
---------------------------------------------
ALTER TABLE purposes add purpose_artificialOrNatural VARCHAR2(1) default null;
commit;


