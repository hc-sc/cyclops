DROP TABLE its_nhpid_issues;
CREATE TABLE its_nhpid_issues
(
  its_nhpid_issue_id     NUMBER,
  related_table_name     VARCHAR2(30),
  related_record_key     VARCHAR2(1000),
  issue_tracking_number  VARCHAR2(100),
  its_url                VARCHAR2(2000),
  its_comment            VARCHAR2(2000),
  useracc_id             NUMBER,
  creation_date          DATE,
  lastupdate_date        DATE
);

ALTER TABLE its_nhpid_issues
 ADD CONSTRAINT it_nhpid_issues_uk UNIQUE (related_table_name, related_record_key, issue_tracking_number) ;

ALTER TABLE its_nhpid_issues
 ADD CONSTRAINT it_nhpid_issues_pk PRIMARY KEY (its_nhpid_issue_id) ;

CREATE PUBLIC SYNONYM its_nhpid_issues FOR its_nhpid_issues;

GRANT SELECT, INSERT, UPDATE, DELETE ON its_nhpid_issues TO NHPID_APEX;

CREATE SEQUENCE its_nhpid_issues_seq START WITH 1 NOCACHE;

CREATE OR REPLACE TRIGGER its_nhpid_issues_iu_trg
BEFORE INSERT OR UPDATE ON its_nhpid_issues
FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :NEW.its_nhpid_issue_id IS NULL THEN
      SELECT its_nhpid_issues_seq.nextval INTO :NEW.its_nhpid_issue_id FROM dual;
    END IF;
--    :new.INGREDROLE_creation_date := sysdate;
  END IF;
--  :new.INGREDROLE_lastupdate_date := sysdate;
--  :new.useracc_id := nvl(NHPID_APEX_UTIL.GET_APEX_USER_ID,0);
END;