--------------------------------------------------------------------------------
-- Create the table that will hold the hash information.
--------------------------------------------------------------------------------
CREATE TABLE Error_Logs (
  ID_Error         INTEGER,
  error_date       DATE,
  error_code       NUMBER(6),
  error_message    VARCHAR2(1000),
  history_number   INTEGER,
  Package_name     VARCHAR2(100),
  Process_name     VARCHAR2(100),
  Process_values   VARCHAR2(4000),
  email_flag       CHAR(1)
);

-- Addition of the Primary Key constraint.
ALTER TABLE Error_Logs ADD (
  CONSTRAINT Error_Logs_PK 
  PRIMARY KEY (ID_Error)
  USING INDEX
);

--------------------------------------------------------------------------------
-- Create the table that will hold the hash information.
-- 
-- Notes:  The Logs table will contain information about the process
-- being executed.  The Log_Details will contain relevant details (counts)
-- as the process executes.
--------------------------------------------------------------------------------
CREATE TABLE  Logs (
  LOG_ID              NUMBER(38)    NOT NULL,
  Process_Name        VARCHAR2(50)  NOT NULL,
  Start_Time          DATE          NOT NULL,
  End_Time            DATE,
  Error_Count         NUMBER(38),
  Mail_Sent           VARCHAR2(10)  NOT NULL,
  Status              VARCHAR2(10)  NOT NULL,
  General_Detail      VARCHAR2(200)
);

CREATE TABLE  Log_Details (
  LOG_ID              NUMBER(38)    NOT NULL,
  LOG_DETAIL_ID       NUMBER(38)    NOT NULL,
  Time_Stamp          DATE          NOT NULL,
  Details             VARCHAR2(2000)
);

ALTER TABLE Logs ADD (
  CONSTRAINT Logs_PK
  PRIMARY KEY ( LOG_ID )
  USING INDEX
);

ALTER TABLE Log_Details ADD (
  CONSTRAINT Log_Details_PK
  PRIMARY KEY ( LOG_DETAIL_ID )
  USING INDEX
);

ALTER TABLE LOG_Details ADD
  CONSTRAINT Log_Details_FK1
  FOREIGN KEY (Log_ID)
  REFERENCES LOGS (Log_ID);
  