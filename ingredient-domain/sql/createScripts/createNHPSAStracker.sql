--------------------------------------------------------------------------------
-- The Constants_Pkg contains variables that are referenced by stored procedures
-- and functions through-out the code.  
-- Note:  This package specification needs to be created before many of the 
-- functions and procedures.
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE Constants_Pkg
AUTHID CURRENT_USER

IS
  --
  -- Standard Initiation Form Information 
  --

  /* Import Process */
  Process_Completed         CONSTANT VARCHAR2(30):= 'Process Completed';  

  /*Log Values*/
  Log_Status_Started        CONSTANT VARCHAR2(10):= 'Started';
  Log_Status_WorkInProgress CONSTANT VARCHAR2(10):= 'WIP';
  Log_Status_Completed      CONSTANT VARCHAR2(10):= 'Completed';
  Log_Status_Error          CONSTANT VARCHAR2(10):= 'Error';
  Log_MailSent_Yes          CONSTANT VARCHAR2(10):= 'Y';
  Log_MailSent_No           CONSTANT VARCHAR2(10):= 'N';

  /*Standard Values*/
  Package_Name                       VARCHAR2(100);
  Process_Name                       VARCHAR2(100);
  Process_Values                     VARCHAR2(4000);
  History_Number                     INTEGER;
  Project_Number                     INTEGER;

  /*Error Codes*/
  Error_Code                         VARCHAR2(10);
  Error_Message                      VARCHAR2(4000);

  /*Error Codes*/
  HashManager_Add           CONSTANT VARCHAR2(10):= 'Add';
  HashManager_Update        CONSTANT VARCHAR2(10):= 'Update';
  HashManager_Delete        CONSTANT VARCHAR2(10):= 'Delete';
  HashManager_NoChange      CONSTANT VARCHAR2(10):= 'NoChange';

END Constants_Pkg;
/

--------------------------------------------------------------------------------
-- The Error Manager is used to track errors that have occured when the SQL
-- code is executed.  By default, functions, procedures and packages should
-- call these functions to log any error they encounter during execution.
--
-- Note:  A mail sent flag has been created.  The intent is that when an error
-- is created, the flag is set to "n" indicating that no message has been sent.
-- Once the mail manager is up and running, we can hook it up to the flag
-- and have the mail manager send e-mail based on errors encountered in Oracle.
-- Once the mail has been sent, the mail_sent flag should be set to 'S' for sent.
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE ERROR_MANAGER_PKG
IS

  -- Declare Process Status variables.
  mail_new          CONSTANT VARCHAR2(1) := 'N';
  mail_sent         CONSTANT VARCHAR2(1) := 'S';

  --------------------------------------------------------------------------------
  -- Procedure Report_Error should be called when an error is encountered.
  -- It's function is to record the error_code and error_message along with other
  -- usefull parameters into the error log table for future review.
  --------------------------------------------------------------------------------
  PROCEDURE REPORT_ERROR (
    error_code       IN Error_Logs.Error_Code%TYPE,
    error_message    IN Error_Logs.Error_Message%TYPE,
    history_number   IN Error_Logs.History_Number%TYPE,
    package_name     IN Error_Logs.Package_Name%TYPE,
    process_name     IN Error_Logs.Process_Name%TYPE,
    process_values   IN Error_Logs.Process_Values%TYPE
  );

  --------------------------------------------------------------------------------
  -- Procedure Set_Mail_Sent_Flag set the mail_sent flag to 'S'.  This should
  -- be done once the mail on the specific error has been sent.
  --------------------------------------------------------------------------------
  PROCEDURE SET_MAIL_SENT_FLAG (
    id_e             IN Error_Logs.id_error%TYPE
  );

END ERROR_MANAGER_PKG;
/

-- See package specification for implementation information.

CREATE OR REPLACE PACKAGE BODY ERROR_MANAGER_PKG
IS
 
  PROCEDURE REPORT_ERROR (
    error_code       IN Error_Logs.Error_Code%TYPE,
    error_message    IN Error_Logs.Error_Message%TYPE,
    history_number   IN Error_Logs.History_Number%TYPE,
    package_name     IN Error_Logs.Package_Name%TYPE,
    process_name     IN Error_Logs.Process_Name%TYPE,
    process_values   IN Error_Logs.Process_Values%TYPE
  )
  IS
    ID_E INTEGER;
  BEGIN
    ID_E := 0;
    SELECT decode(max(id_error),Null,1,max(id_error)+1) into ID_E FROM ERROR_LOGS;
    INSERT INTO ERROR_LOGS (ID_Error, error_date, error_code, error_message, history_number,
      Package_name, Process_name, Process_values, email_flag) Values (
      ID_E, SysDate, Error_Code, Error_Message, History_Number, Package_Name, Process_Name,
      Process_Values, mail_new);
   COMMIT;
  END REPORT_ERROR;

  PROCEDURE SET_MAIL_SENT_FLAG (
    id_e            IN Error_Logs.id_error%TYPE
  )
  IS
  BEGIN
    UPDATE ERROR_LOGS SET email_flag=mail_sent WHERE id_error=id_e;
    COMMIT;
  END SET_MAIL_SENT_FLAG;

END ERROR_MANAGER_PKG;
/

--------------------------------------------------------------------------------
-- The Log Manager is used to track execution of SQL scripts.  Some scripts
-- are executed at odd times (at night), some scripts take a long time to 
-- execute (refresh materialized views, NHPSAS Hash updates, etc) and other
-- scripts need to be tracked.  This logs table and log_details is used to track 
-- this information and make it available for review.
--
-- Note:  The log table will record the name of the script being executed,
-- a start time.  As the script continues to execute and ends, the log process
-- must be called again to set the flag indicating that the process was 
-- terminated.  The log_details can be used to recored details pertaining
-- the script being executed.
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE LOG_MANAGER_PKG
IS

  --------------------------------------------------------------------------------
  -- Function Open_Log_Func is used to initiate the logging process for a 
  -- a specific process_name.  It returns a unique identifyer that can 
  -- then be used to update and close the logging process.
  --------------------------------------------------------------------------------
  FUNCTION OPEN_LOG_FUNC (
	p_name Logs.process_name%TYPE
  ) RETURN NUMBER;

  --------------------------------------------------------------------------------
  -- Function Update_Log_Func is used to update the logging process with 
  -- details that may be relevant and need to be tracked.
  -- Note:  The p_details information is stored into the General_Detail column
  -- of the Logs table.  This automatically overwrites any previous log
  -- detail that may have been recorded.  For streaming details and for keeping
  -- a longer stream of details, use the ADDDetails_PROC.
  --------------------------------------------------------------------------------
  PROCEDURE UPDATE_LOG_PROC (
          p_id      IN Logs.log_id%TYPE,
		p_details IN Logs.General_Detail%TYPE
  );

  --------------------------------------------------------------------------------
  -- Procedure Update_Log_Error_Proc is used to record an error that may have 
  -- been encountered by our process.  These errors are tracked via the error
  -- counter in the logs table.  Details of the error are stored into the 
  -- details column of the logs table.
  -- Note:  Normally a script will be aborted when an error is encountered.  You
  -- can choose to continue running the script and leverage the error loggging
  -- functionality to log any/all errors encountered.
  --------------------------------------------------------------------------------
  PROCEDURE UPDATE_LOG_ERROR_PROC (
          p_id      IN Logs.log_id%TYPE,
          p_details IN Logs.General_Detail%TYPE
  );

  --------------------------------------------------------------------------------
  -- Procedure Add_Details_Proc is used to add details to a process being 
  -- logged.
  --------------------------------------------------------------------------------
  PROCEDURE ADD_Details_PROC (
          p_id      IN Logs.log_id%TYPE,
          p_details IN Log_Details.Details%TYPE
  );

  --------------------------------------------------------------------------------
  -- Procedure Close_Log_Proc is used to close an active logging process.
  --------------------------------------------------------------------------------
  PROCEDURE CLOSE_LOG_PROC (
          p_id      IN Logs.log_id%TYPE,
          p_details IN Logs.General_Detail%TYPE
  );
	
  --------------------------------------------------------------------------------
  -- Function getNextPk returns the next id to be used in the logs table.
  -- TODO: Use a Sequence
  --------------------------------------------------------------------------------
  Function getNextPk RETURN NUMBER;

  --------------------------------------------------------------------------------
  -- Function getNextPkDetail returns the next id to be used in the log_details
  -- table.
  -- TODO: Use a Sequence
  --------------------------------------------------------------------------------
  Function getNextPkDetail RETURN NUMBER;

END LOG_MANAGER_PKG;
/

-- See package specification for implementation information.

CREATE OR REPLACE PACKAGE BODY Log_Manager_PKG
IS

-- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

---------------------------------------------------------------
-----              Start the Log process.                 -----
---------------------------------------------------------------
FUNCTION OPEN_LOG_FUNC (
	p_name Logs.process_name%TYPE
) RETURN NUMBER
IS
  ProcessNumber Logs.log_id%Type;
BEGIN
  -- Set basic values for error trapping.
  process_name   := 'Log_Manager_PKG.Open_Log_Func';
  process_values := 'p_name=' || p_name;
  -- Start of Code

  -- Get the process number
  ProcessNumber := getNextPk;

  -- Create a new row in the ProcessLog table with the information. 
  INSERT INTO Logs (
    log_ID,
    Process_Name,
    Start_Time,
    Error_Count,
    Mail_Sent,
    Status,
    General_Detail
  ) values (
    ProcessNumber, 
    p_name, 
    SysDate,
    0,
    Constants_Pkg.Log_MailSent_No,
    Constants_Pkg.Log_Status_Started, 
    'Log Started!'
  );
  -- Commit the change
  Commit;
  -- Return the processnumber for future use. 
  RETURN 	ProcessNumber;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END OPEN_LOG_FUNC;

---------------------------------------------------------------
-----             Update the Log process.                 -----
---------------------------------------------------------------
PROCEDURE UPDATE_LOG_PROC (
  p_id      IN Logs.log_id%TYPE,
  p_details IN Logs.General_Detail%TYPE
)IS
  BEGIN
  -- Set basic values for error trapping.
  process_name   := 'Log_Manager_PKG.Update_Log_Proc';
  process_values := 'p_details=' || p_details || ', p_id=' || to_char(p_id);
  -- Start of Code
  -- Find and update the details.
  UPDATE Logs
  SET 
    general_detail = p_details, 
    status         = Constants_Pkg.Log_Status_WorkInProgress
  WHERE 
    log_id             = p_id;
  -- Commit the change
  Commit;
  -- Commit the changing details
  ADD_Details_PROC( p_id, p_details );
  Commit;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END UPDATE_LOG_PROC;

---------------------------------------------------------------
-----        Update the Log process with an Error.        -----
---------------------------------------------------------------

PROCEDURE UPDATE_LOG_ERROR_PROC (
    p_id      IN Logs.log_id%TYPE,
    p_details IN Logs.General_Detail%TYPE
)IS
  error_counter number(38);
  BEGIN
  -- Set basic values for error trapping.
  process_name   := 'Log_Manager_PKG.Update_Log_Proc';
  process_values := 'p_details=' || p_details || ', p_id=' || to_char(p_id);
  -- Start of Code
  Select error_count into error_counter from logs where log_id = p_id;
  -- Find and update the details.
  UPDATE Logs 
  SET 
    general_detail = p_details, 
    status         = Constants_Pkg.Log_Status_Error,
    error_count    = error_counter + 1,
    end_time       = SysDate 
  WHERE 
    log_id             = p_id;
  -- Commit the change
  Commit;
  -- Commit the changing details
  ADD_Details_PROC( p_id, p_details );
  Commit;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END UPDATE_LOG_ERROR_PROC;

---------------------------------------------------------------
-----        Update the Log Process - Add Details         -----
---------------------------------------------------------------
PROCEDURE ADD_Details_PROC (
  p_id      IN Logs.log_id%TYPE,
  p_details   IN Log_Details.Details%TYPE
)
IS
  ProcessNumber Number(38);
BEGIN
  -- Set basic values for error trapping.
  process_name   := 'Log_Manager_PKG.ADD_Details_Proc';
  process_values := 'p_id=' || to_char(p_id) || ', p_details=' || p_details;
  -- Start of Code
  -- Get the process number
  ProcessNumber := getNextPkDetail;
  -- Create a new row in the ProcessLog table with the information. 
  INSERT INTO Log_Details (
    log_detail_ID,
    log_id,
    Time_Stamp,
    Details
  ) values (
    ProcessNumber,
    p_id, 
    SysDate,
    p_details
  );
  -- Commit the change
  Commit;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END ADD_Details_PROC;

---------------------------------------------------------------
-----         Close the Log Process - Add Details         -----
---------------------------------------------------------------

-- Close the Log process.
PROCEDURE CLOSE_LOG_PROC (
  p_id      IN Logs.log_id%TYPE,
  p_details IN Logs.General_Detail%TYPE
)
IS
  BEGIN
  -- Set basic values for error trapping.
  process_name   := 'Log_Manager_PKG.CLOSE_LOG_PROC';
  process_values := 'p_details=' || p_details || ', p_id=' || p_id;
  -- Start of Code
  -- Find and update the details.
  UPDATE Logs 
    SET 
      general_detail  = p_details, 
      status          = Constants_Pkg.Log_Status_Completed, 
      end_time        = SysDate 
    WHERE 
      log_id          = p_id;
  -- Commit the change
  Commit;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END CLOSE_LOG_PROC;

Function getNextPk RETURN NUMBER
IS
  PK Logs.Log_id%Type;
BEGIN
  -- Set some basic values in the event that error trapping is required.
  process_name   := 'Log_Manager_PKG.getNextPk';
  process_values := 'No Process Values';
  -- Start of Code
  SELECT max(log_ID) INTO PK 
    FROM Logs;
    PK := PK + 1;
  IF PK IS NULL THEN
    PK := 1;
  END IF;
  RETURN PK;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 1;    
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END getNextPk;

Function getNextPkDetail RETURN NUMBER
IS
  PK Log_Details.LOG_DETAIL_ID%Type;
BEGIN
  -- Set some basic values in the event that error trapping is required.
  process_name   := 'Log_Manager_PKG.getNextPkDetail';
  process_values := 'No Process Values';
  -- Start of Code
  SELECT max(log_detail_ID) INTO PK 
    FROM Log_Details;
    PK := PK + 1;
  IF PK IS NULL THEN
    PK := 1;
  END IF;
  RETURN PK;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 1;    
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END getNextPkDetail;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'Log_Manager_PKG';
EXCEPTION
-- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END LOG_MANAGER_PKG;
/

--------------------------------------------------------------------------------
-- Create the table that will hold the hash information.
--------------------------------------------------------------------------------
CREATE TABLE NHPSAS_INGREDIENT_SYNC (
  ingred_id                     NUMBER        NOT NULL,
  nhpsas_ingred_auth_name_eng   VARCHAR2(200) NOT NULL,
  nhpsas_ingred_auth_name_fr    VARCHAR2(200) NOT NULL,
  nhpsas_ingred_hash            VARCHAR2(100),
  nhpsas_ingred_lastupdate_date DATE
);

CREATE INDEX NHPSAS_INGREDIENT_SYNC_IDX1 
    ON NHPSAS_INGREDIENT_SYNC (ingred_id);



CREATE OR REPLACE PACKAGE NHPSAS_HashManager_PKG
IS

--------------------------------------------------------------------------------
-- Function that converts a given string into an MD5 hash.
--------------------------------------------------------------------------------
FUNCTION md5_digest (vin_string IN VARCHAR2)
                     RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that creates a hash given an ingredient / monograph name and string 
-- of values relevant to NHP SAS.
--------------------------------------------------------------------------------
FUNCTION hash_digest (name_string IN VARCHAR2,
                      values_string IN VARCHAR2) 
                      RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the authorized name as a 
-- string.
--------------------------------------------------------------------------------
FUNCTION getAuthorizedName(id IN INTEGER, 
                           seperator IN VARCHAR2)
                           RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the English authorized name as
-- a string from the NHPSAS table containing the hash.  Mainly used for logging
-- purposes.
--------------------------------------------------------------------------------
FUNCTION getIngredientNHPSASName(id IN INTEGER) RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns a string composed of the 
-- ingredient's common names.  Common names come from two different places
-- depending on the nature of the ingredient.  (Homeopathic vs the rest)
--------------------------------------------------------------------------------
FUNCTION getCommonNames(ingred_id    INTEGER,
                        separator    VARCHAR2) 
                        RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns a string composed of the 
-- ingredient's proper names.  Proper names come from two different places
-- depending on the nature of the ingredient.  (Homeopathic vs the rest)
--------------------------------------------------------------------------------
FUNCTION getProperNames(ingred_id    INTEGER,
                        separator    VARCHAR2) 
                        RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the class of an ingredient.
--------------------------------------------------------------------------------
FUNCTION getIngredientClass(id        IN INTEGER, 
                            seperator IN VARCHAR2)
                            RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the nhp classification of
-- an ingredient.
--------------------------------------------------------------------------------
FUNCTION getIngredientNHPClassification(id        IN INTEGER, 
                                        seperator IN VARCHAR2)
                                        RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the organism type and
-- organism type group and if an organism type is animal, then returns
-- a yes for annimual tissue.
--------------------------------------------------------------------------------
FUNCTION getOrganismInformation(ingred_id    INTEGER,
                                seperator    VARCHAR2) 
                                RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the source material 
-- involved with the ingredient.
-- For chemical substances, the source material is derived through the 
-- relationship with the subingredient table.  (SQL select 1)
-- For Defined Organism Substances, the source material is defined as 
-- constituants and is derived through the relationship through the 
-- organism and organism part tables to the subingredient table.
--------------------------------------------------------------------------------
FUNCTION getSourceMaterial(ingred_id    INTEGER,
                           seperator    VARCHAR2)
                           RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id creates a unique string representing
-- the fields for NHPSAS.  
--------------------------------------------------------------------------------
FUNCTION getIngredientString(id        IN INTEGER, 
                             seperator IN VARCHAR2)
                             RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Function that given an ingredient id returns a unique hash
-- using the ingredient authorized name and a select list of fields required
-- by NHPSAS.
--------------------------------------------------------------------------------
FUNCTION getIngredientHash(id IN INTEGER) RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- This function returns the current hash from the NHPSAS_INGREDIENT_SYNC
-- table and encapsulates the select statement with a NO_DATA_FOUND which 
-- traps cases where the table does not contain any information.
--------------------------------------------------------------------------------
FUNCTION getCurrentHash(id IN INTEGER) RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- This function returns the current hash from the NHPSAS_INGREDIENT_SYNC
-- table and encapsulates the select statement with a NO_DATA_FOUND which 
-- traps cases where the table does not contain any information.
--------------------------------------------------------------------------------
FUNCTION hasValueInSyncTable(id IN INTEGER) RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- Create a function / procedure that will cursor through all the ingredients,
-- and update the NHPSAS_INGREDIENT_SYNC table.
--------------------------------------------------------------------------------
FUNCTION updateIngredientHash(id IN INTEGER) RETURN VARCHAR2;

--------------------------------------------------------------------------------
-- This function is used to refresh the materialized views that are 
-- leveraged by the updateHashTable code.
--------------------------------------------------------------------------------
PROCEDURE refreshViews;

--------------------------------------------------------------------------------
-- Function that is used to update the hash table.
-- This function will add new hash values when new ingredients are detected,
-- Update hash values when ingredients are modified and
-- Delete the hash value (leave ID intact) when an ingredient is deleted.
--------------------------------------------------------------------------------
PROCEDURE updateHashTable;

END NHPSAS_HashManager_PKG;
/

CREATE OR REPLACE PACKAGE BODY NHPSAS_HashManager_PKG
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

--------------------------------------------------------------------------------
-- Function that converts a given string into an MD5 hash.
--------------------------------------------------------------------------------
FUNCTION md5_digest (vin_string IN VARCHAR2)
RETURN VARCHAR2 IS
BEGIN
RETURN
 lower(dbms_obfuscation_toolkit.md5(input => utl_raw.cast_to_raw(vin_string)));
END md5_digest;

--------------------------------------------------------------------------------
-- Function that creates a hash given an ingredient / monograph name and string 
-- of values relevant to NHP SAS.
--------------------------------------------------------------------------------
FUNCTION hash_digest (name_string   IN VARCHAR2,
                      values_string IN VARCHAR2)
RETURN VARCHAR2 IS
BEGIN
RETURN
 lower(dbms_obfuscation_toolkit.md5(input => utl_raw.cast_to_raw(name_string))) ||
 lower(dbms_obfuscation_toolkit.md5(input => utl_raw.cast_to_raw(values_string)));
END hash_digest;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the authorized name as a 
-- string.
--------------------------------------------------------------------------------
FUNCTION getAuthorizedName(id IN INTEGER, seperator IN VARCHAR2)
RETURN VARCHAR2 IS
  ingredientName VARCHAR2(8000);
BEGIN
  SELECT 
    ingred_authorized_name_eng into ingredientName 
  from 
    ingredients
  where 
    ingred_id = id;
RETURN ingredientName || seperator;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN null;    
END getAuthorizedName;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the English authorized name as
-- a string from the NHPSAS table containing the hash.  Mainly used for logging
-- purposes.
--------------------------------------------------------------------------------
FUNCTION getIngredientNHPSASName(id IN INTEGER)
RETURN VARCHAR2 IS
  ingredientName VARCHAR2(8000);
BEGIN
  select 
    nhpsas_ingred_auth_name_eng into ingredientName 
  from
    nhpsas_ingredient_sync 
  where
    ingred_id = id;
  RETURN ingredientName;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN null;    
END getIngredientNHPSASName;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns a string composed of the 
-- ingredient's common names.  Common names come from two different places
-- depending on the nature of the ingredient.  (Homeopathic vs the rest)
--------------------------------------------------------------------------------
FUNCTION getCommonNames(
  ingred_id    INTEGER,
  separator    VARCHAR2
) RETURN VARCHAR2
IS
  TYPE commonNamecursorType IS REF CURSOR;
  commonNamecursor commonNameCursorType;
  TYPE commonNameType is record (
    commonNameEnglish VARCHAR2(1000),
    commonNameFrench  VARCHAR2(1000)
  );
  commonNameRecord  commonNameType;
  SQLQ              VARCHAR2(8000);
  commonNameList    VARCHAR2(8000);
  commonNameEnglish varchar2(1000);
  commonNameFrench  varchar2(1000);
BEGIN
  -- Define an empty synonym list.
  commonNameList := '';

  SQLQ :=         'select ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  ingredient_qualsyno_cns cns ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  cns.qualsyno_id=qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  syn.synonym_id=qsyn.synonym_id and ';
  SQLQ := SQLQ || '  cns.ingred_id = ' || ingred_id;
  SQLQ := SQLQ || 'union ';
  SQLQ := SQLQ || 'select '; 
  SQLQ := SQLQ || '  hgt.homeogentext_name_eng, ';
  SQLQ := SQLQ || '  hgt.homeogentext_name_fr '; 
  SQLQ := SQLQ || 'from ';  
  SQLQ := SQLQ || '  homeopathic_generic_texts hgt, ';
  SQLQ := SQLQ || '  homeopathic_common_name_hgts hgts ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  hgts.homeogentext_id=hgt.homeogentext_id and ';
  SQLQ := SQLQ || '  hgts.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN commonNameCursor FOR SQLQ;
  LOOP
    Fetch commonNameCursor INTO commonNameRecord;
    IF commonNameCursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      commonNameEnglish := commonNameRecord.commonNameEnglish;
      commonNameFrench := commonNameRecord.commonNameFrench;
      IF (commonNameEnglish is not null AND commonNameEnglish <> 'NA') THEN
          commonNameList := commonNameList || commonNameEnglish || separator;
      END IF;
      IF (commonNameFrench is not null AND commonNameFrench <> 'NA') THEN
          commonNameList := commonNameList || commonNameFrench || separator;
      END IF;
    END IF;
  END LOOP;
  CLOSE commonNameCursor;
  RETURN commonNameList;
END getCommonNames;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns a string composed of the 
-- ingredient's proper names.  Proper names come from two different places
-- depending on the nature of the ingredient.  (Homeopathic vs the rest)
--------------------------------------------------------------------------------
FUNCTION getProperNames(
  ingred_id    INTEGER,
  separator    VARCHAR2
) RETURN VARCHAR2
IS
  TYPE properNamecursorType IS REF CURSOR;
  properNamecursor properNameCursorType;
  TYPE properNameType is record (
    properNameEnglish VARCHAR2(1000),
    properNameFrench  VARCHAR2(1000)
  );
  properNameRecord  properNameType;
  SQLQ              VARCHAR2(8000);
  properNameList    VARCHAR2(8000);
  properNameEnglish varchar2(1000);
  properNameFrench  varchar2(1000);
BEGIN
  -- Define an empty synonym list.
  properNameList := '';

  SQLQ :=         'select ';
  SQLQ := SQLQ || '  syn.synonym_name_eng, ';
  SQLQ := SQLQ || '  syn.synonym_name_fr ';
  SQLQ := SQLQ || 'from  ';
  SQLQ := SQLQ || '  synonyms syn, ';
  SQLQ := SQLQ || '  qualified_synonyms qsyn, ';
  SQLQ := SQLQ || '  ingredient_qualsyno_pns pns ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  pns.qualsyno_id=qsyn.qualsyno_id and ';
  SQLQ := SQLQ || '  syn.synonym_id=qsyn.synonym_id and ';
  SQLQ := SQLQ || '  pns.ingred_id = ' || ingred_id;
  SQLQ := SQLQ || 'union ';
  SQLQ := SQLQ || 'select '; 
  SQLQ := SQLQ || '  hgt.homeogentext_name_eng, ';
  SQLQ := SQLQ || '  hgt.homeogentext_name_fr '; 
  SQLQ := SQLQ || 'from ';  
  SQLQ := SQLQ || '  homeopathic_generic_texts hgt, ';
  SQLQ := SQLQ || '  homeopathic_proper_name_hgts hgts ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  hgts.homeogentext_id=hgt.homeogentext_id and ';
  SQLQ := SQLQ || '  hgts.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN properNameCursor FOR SQLQ;
  LOOP
    Fetch properNameCursor INTO properNameRecord;
    IF properNameCursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      properNameEnglish := properNameRecord.properNameEnglish;
      properNameFrench := properNameRecord.properNameFrench;
      IF (properNameEnglish is not null AND properNameEnglish <> 'NA') THEN
          properNameList := properNameList || properNameEnglish || separator;
      END IF;
      IF (properNameFrench is not null AND properNameFrench <> 'NA') THEN
          properNameList := properNameList || properNameFrench || separator;
      END IF;
    END IF;
  END LOOP;
  CLOSE properNameCursor;
  RETURN properNameList;
END getProperNames;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the class of an ingredient.
--------------------------------------------------------------------------------
FUNCTION getIngredientClass(id IN INTEGER, seperator IN VARCHAR2)
RETURN VARCHAR2 IS
  ingredientClass VARCHAR2(8000);
BEGIN
  SELECT 
    ingredspec_class_name into ingredientClass 
  from 
    ingredients
  where 
    ingred_id = id;
RETURN ingredientClass || seperator;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN null;    
END getIngredientClass;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the nhp classification of
-- an ingredient.
--------------------------------------------------------------------------------
FUNCTION getIngredientNHPClassification(
    id        IN INTEGER, 
    seperator IN VARCHAR2)
RETURN VARCHAR2 IS
  ingredientClassification VARCHAR2(8000);
BEGIN
  SELECT 
    nhpc.nhpclass_code || seperator || 
    nhpc.nhpclass_name_eng || seperator ||
    nhpc.nhpclass_name_fr || seperator
    into ingredientClassification 
  from 
    nhp_classifications nhpc,
    ingredient_nhpclassifications ic
  where 
    ic.nhpclass_id = nhpc.nhpclass_id and
    ic.ingred_id = id;
RETURN ingredientClassification;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN '';    
END getIngredientNHPClassification;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the organism type and
-- organism type group and if an organism type is animal, then returns
-- a yes for annimual tissue.
--------------------------------------------------------------------------------
FUNCTION getOrganismInformation(
  ingred_id    INTEGER,
  seperator    VARCHAR2
) RETURN VARCHAR2
IS
  TYPE organismInformationCursorType IS REF CURSOR;
  organismInformationCursor organismInformationCursorType;
  TYPE organismInformationType is record (
    organismInformation VARCHAR2(1000)
  );
  organismInformationRecord  organismInformationType;
  SQLQ                  VARCHAR2(8000);
  organismInformationList    VARCHAR2(8000);
  organismInformationString  varchar2(1000);
BEGIN
  -- Define an empty Source Material list.
  organismInformationList := '';
  -- Select for the Organism Information.
  SQLQ :=         'select ';
  SQLQ := SQLQ || '  org.organism_id || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  org.organism_name || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  orgt.orgtype_code || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  orgt.orgtype_name_eng || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  orgt.orgtype_name_fr || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  orgtg.orgtypegrp_name_eng || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  orgtg.orgtypegrp_name_fr || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  decode(orgtg.orgtypegrp_id, 2, ''yes'', ''no'') ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  organisms org, ';
  SQLQ := SQLQ || '  ingredient_organism_mv iomv, ';
  SQLQ := SQLQ || '  organism_types orgt, ';
  SQLQ := SQLQ || '  organism_type_groups orgtg ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  iomv.organism_id = org.organism_id and '; 
  SQLQ := SQLQ || '  org.orgtype_id = orgt.orgtype_id and ';
  SQLQ := SQLQ || '  orgt.orgtypegrp_id = orgtg.orgtypegrp_id and ';
  SQLQ := SQLQ || '  iomv.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN organismInformationCursor FOR SQLQ;
  LOOP
    Fetch organismInformationCursor INTO organismInformationRecord;
    IF organismInformationCursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      organismInformationString := organismInformationRecord.organismInformation;
      IF (organismInformationString is not null) THEN
          organismInformationList := organismInformationList || organismInformationString || seperator;
      END IF;
    END IF;
  END LOOP;
  CLOSE organismInformationCursor;
  RETURN organismInformationList;
END getOrganismInformation;

--------------------------------------------------------------------------------
-- Function that given an ingredient id, returns the source material 
-- involved with the ingredient.
-- For chemical substances, the source material is derived through the 
-- relationship with the subingredient table.  (SQL select 1)
-- For Defined Organism Substances, the source material is defined as 
-- constituants and is derived through the relationship through the 
-- organism and organism part tables to the subingredient table.
--------------------------------------------------------------------------------
FUNCTION getSourceMaterial(
  ingred_id    INTEGER,
  seperator    VARCHAR2
) RETURN VARCHAR2
IS
  TYPE sourceMaterialCursorType IS REF CURSOR;
  sourceMaterialCursor sourceMaterialCursorType;
  TYPE sourceMaterialType is record (
    sourceMaterial VARCHAR2(1000)
  );
  sourceMaterialRecord  sourceMaterialType;
  SQLQ                  VARCHAR2(8000);
  sourceMaterialList    VARCHAR2(8000);
  sourceMaterialString  varchar2(1000);
BEGIN
  -- Define an empty Source Material list.
  sourceMaterialList := '';
  -- Select for Chemical Substances.
  SQLQ :=         'select ';
  SQLQ := SQLQ || '  ii.ingred_authorized_name_eng || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  ii.ingred_authorized_name_fr ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredients i, ';
  SQLQ := SQLQ || '  ingredient_subingredients isub, ';
  SQLQ := SQLQ || '  subingredients subi, ';
  SQLQ := SQLQ || '  ingredients ii ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  i.ingred_id = isub.ingred_id and ';
  SQLQ := SQLQ || '  isub.subingred_id = subi.subingred_id and ';
  SQLQ := SQLQ || '  subi.ingred_id = ii.ingred_id and ';
  SQLQ := SQLQ || '  i.ingred_id = ' || ingred_id || ' ';
  SQLQ := SQLQ || 'union ';
  -- select for Defined Organism Constituants.
  SQLQ := SQLQ || 'select ';
  SQLQ := SQLQ || '  ii.ingred_authorized_name_eng || ''' || seperator || ''' || ';
  SQLQ := SQLQ || '  ii.ingred_authorized_name_fr ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '    ingredient_organism_mv iomv, ';
  SQLQ := SQLQ || '    organism_constituent_mv ocmv, ';
  SQLQ := SQLQ || '    ingredients ii ';
  SQLQ := SQLQ || '  where ';
  SQLQ := SQLQ || '    iomv.organism_id = ocmv.organism_id and '; 
  SQLQ := SQLQ || '    ii.ingred_id = ocmv.ingred_id and ';
  SQLQ := SQLQ || '    iomv.ingred_id = ' || ingred_id;

  -- Walk through the result set.
  OPEN sourceMaterialCursor FOR SQLQ;
  LOOP
    Fetch sourceMaterialCursor INTO sourceMaterialRecord;
    IF sourceMaterialCursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      sourceMaterialString := sourceMaterialRecord.sourceMaterial;
      IF (sourceMaterialString is not null) THEN
          sourceMaterialList := sourceMaterialList || sourceMaterialString || seperator;
      END IF;
    END IF;
  END LOOP;
  CLOSE sourceMaterialCursor;
  RETURN sourceMaterialList;

END getSourceMaterial;

--------------------------------------------------------------------------------
-- Function that given an ingredient id creates a unique string representing
-- the fields for NHPSAS.  
--------------------------------------------------------------------------------
FUNCTION getIngredientString(id IN INTEGER, seperator IN VARCHAR2)
RETURN VARCHAR2 IS
  ingredientString VARCHAR2(8000);
BEGIN
  ingredientString := '';
  ingredientString := ingredientString || getCommonNames(id, seperator);
  ingredientString := ingredientString || getProperNames(id, seperator);
  ingredientString := ingredientString || getIngredientClass(id, seperator);
  ingredientString := ingredientString || getIngredientNHPClassification(id, seperator);
  ingredientString := ingredientString || getOrganismInformation(id, seperator);
  ingredientString := ingredientString || getSourceMaterial(id, seperator);
  RETURN ingredientString;
END getIngredientString;

--------------------------------------------------------------------------------
-- Function that given an ingredient id returns a unique hash
-- using the ingredient authorized name and a select list of fields required
-- by NHPSAS.
--------------------------------------------------------------------------------
FUNCTION getIngredientHash(id IN INTEGER)
RETURN VARCHAR2 IS
  hash             VARCHAR2(64);
  seperator        VARCHAR2(1);
  authorizedName   VARCHAR2(8000);
  ingredientString VARCHAR2(8000);   
BEGIN
  seperator := '~';
  authorizedName := getAuthorizedName(id, seperator);
  ingredientString := getIngredientString(id, seperator);
  IF authorizedName is not null THEN
    hash := hash_digest(authorizedName, ingredientString);
  ELSE
    hash := null;
  END IF; 
  return hash;
END getIngredientHash;

--------------------------------------------------------------------------------
-- This function returns the current hash from the NHPSAS_INGREDIENT_SYNC
-- table and encapsulates the select statement with a NO_DATA_FOUND which 
-- traps cases where the table does not contain any information.
--------------------------------------------------------------------------------
FUNCTION getCurrentHash(id IN INTEGER) 
RETURN VARCHAR2 IS
  current_hash         VARCHAR2(64);
BEGIN
  select 
    nhpsas_ingred_hash into current_hash 
  from
    nhpsas_ingredient_sync 
  where
    ingred_id = id;
  RETURN current_hash;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN null;    
END getCurrentHash;

--------------------------------------------------------------------------------
-- This function returns the current hash from the NHPSAS_INGREDIENT_SYNC
-- table and encapsulates the select statement with a NO_DATA_FOUND which 
-- traps cases where the table does not contain any information.
--------------------------------------------------------------------------------
FUNCTION hasValueInSyncTable(id IN INTEGER) 
RETURN VARCHAR2 IS
  current_id NUMBER;
BEGIN
  select 
    ingred_id into current_id 
  from
    nhpsas_ingredient_sync 
  where
    ingred_id = id;
  RETURN current_id;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
  RETURN null;    
END hasValueInSyncTable;
 
--------------------------------------------------------------------------------
-- Create a function / procedure that will cursor through all the ingredients,
-- and update the NHPSAS_INGREDIENT_SYNC table.
--------------------------------------------------------------------------------
FUNCTION updateIngredientHash(id IN INTEGER)
RETURN VARCHAR2 IS
  new_hash             VARCHAR2(64);
  current_hash         VARCHAR2(64);
  auth_name_eng        VARCHAR2(200);
  auth_name_fr         VARCHAR2(200);
  status               VARCHAR2(10);
  current_id           NUMBER;
BEGIN
  -- Set basic values for error trapping.
  process_name   := 'NHPSAS_HashMAnager_PKG.updateHashTable';
  process_values := 'Working on ID : ' || id;

  -- Lookup required values.
  new_hash := getIngredientHash(id);
  current_hash := getCurrentHash(id);
  current_id   := hasValueInSyncTable(id);

  IF current_id is null and new_hash is not null THEN
    -- First Time Create.
    select ingred_authorized_name_eng into auth_name_eng from ingredients where ingred_id = id;
    select ingred_authorized_name_fr into auth_name_fr from ingredients where ingred_id = id;
    insert into nhpsas_ingredient_sync (
      ingred_id, nhpsas_ingred_auth_name_eng, nhpsas_ingred_auth_name_fr, nhpsas_ingred_hash,
      nhpsas_ingred_lastupdate_date) values (id, auth_name_eng, auth_name_fr, new_hash, sysdate);
    status := Constants_Pkg.HashManager_Add;
  ELSIF current_hash is not null and new_hash is null THEN
    -- Ingredient has been deleted.
      Update nhpsas_ingredient_sync 
      set 
        nhpsas_ingred_lastupdate_date=sysdate, 
        nhpsas_ingred_hash=null
      where
        ingred_id = id;
    status := Constants_Pkg.HashManager_Delete;
  ELSIF current_hash != new_hash THEN
      -- Hash Has Changed
      Update nhpsas_ingredient_sync 
      set 
        nhpsas_ingred_lastupdate_date=sysdate, 
        nhpsas_ingred_hash=new_hash
      where
        ingred_id = id;
    status := Constants_Pkg.HashManager_Update;
  ELSE
    status := Constants_Pkg.HashManager_NoChange;
  END IF;
  -- Return the status
  Return status;
EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END updateIngredientHash;

--------------------------------------------------------------------------------
-- This function is used to refresh the materialized views that are 
-- leveraged by the updateHashTable code.
--------------------------------------------------------------------------------
PROCEDURE refreshViews
IS
BEGIN
  DBMS_SNAPSHOT.REFRESH( 'ingredient_organism_mv','C');
  DBMS_SNAPSHOT.REFRESH( 'organism_constituent_mv','C');
END refreshViews;

--------------------------------------------------------------------------------
-- Function that is used to update the hash table.
-- This function will add new hash values when new ingredients are detected,
-- Update hash values when ingredients are modified and
-- Delete the hash value (leave ID intact) when an ingredient is deleted.
--------------------------------------------------------------------------------
PROCEDURE updateHashTable
IS
  TYPE ingredientCursorType IS REF CURSOR;
  ingredientCursor ingredientCursorType;
  TYPE ingredientType is record (
    ingred_id NUMBER
  );
  ingredientRecord      ingredientType;
  SQLQ                  VARCHAR2(8000);
  ingred_id             NUMBER;
  Log_ID                Number(38);
  Counter               NUMBER;
  Status                VARCHAR2(10);
  Stats_Added           NUMBER;
  Stats_Deleted         NUMBER;
  Stats_Updated         NUMBER;
  Stats_NoChange        NUMBER;
BEGIN
  -- Set basic values for error trapping.
  process_name   := 'NHPSAS_HashMAnager_PKG.updateHashTable';
  process_values := 'Process Started!';

  -- Start logging the process.
  Log_ID := LOG_MANAGER_PKG.OPEN_LOG_FUNC( process_name );  
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, 'Process started at : ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS') );

  -- Refresh the required materialized views.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'Updating materialized views.'); 
  refreshViews;

  -- Start the counter at 0
  Counter := 0;

  -- Set the statistics.
  Stats_Added := 0;
  Stats_Deleted := 0;
  Stats_Updated := 0;
  Stats_NoChange := 0;

  -- This selection should bring back a list of all IDs ever used
  -- including deleted ids.
  SQLQ :=         'select unique(ingred_id) from ( ';
  SQLQ := SQLQ || 'select ';
  SQLQ := SQLQ || '  i1.ingred_id ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredients i1, ';
  SQLQ := SQLQ || '  NHPSAS_INGREDIENT_SYNC i2 ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  i1.ingred_id = i2.ingred_id (+) ';
  SQLQ := SQLQ || 'union ';
  SQLQ := SQLQ || 'select ';
  SQLQ := SQLQ || '  i2.ingred_id ';
  SQLQ := SQLQ || 'from ';
  SQLQ := SQLQ || '  ingredients i1, ';
  SQLQ := SQLQ || '  NHPSAS_INGREDIENT_SYNC i2 ';
  SQLQ := SQLQ || 'where ';
  SQLQ := SQLQ || '  i2.ingred_id = i1.ingred_id (+) ';
  SQLQ := SQLQ || ') order by ingred_id';

  -- Walk through the result set.
  OPEN ingredientCursor FOR SQLQ;
  LOOP
    Fetch ingredientCursor INTO ingredientRecord;
    IF ingredientCursor%NOTFOUND
    THEN
      EXIT;
    ELSE
      -- Get handle on Ingredient id.
      ingred_id := ingredientRecord.ingred_id;
      -- Try to update that ingredient hash.
      status := updateIngredientHash(ingred_id);
      -- Track statistics.
      IF status = Constants_Pkg.HashManager_Add THEN
        Stats_Added := Stats_Added + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Added   : ' || ingred_id || ' (' || getIngredientNHPSASName(ingred_id) || ')');
      ELSIF status = Constants_Pkg.HashManager_Update THEN
        Stats_Updated := Stats_Updated + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Updated : ' || ingred_id || ' (' || getIngredientNHPSASName(ingred_id) || ')');
      ELSIF status = Constants_Pkg.HashManager_Delete THEN
        Stats_Deleted := Stats_Deleted + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Deleted : ' || ingred_id || ' (' || getIngredientNHPSASName(ingred_id) || ')');
      ELSIF status = Constants_Pkg.HashManager_NoChange THEN
        Stats_NoChange := Stats_NoChange + 1;
      END IF;
      -- Track the number of ingredients processed.
      counter := counter + 1;
      IF mod(counter, 1000) = 0 THEN
        LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'Processing ingredient record: ' || counter ); 
      END IF;
      -- Commit any / all changes.
      Commit;
    END IF;
  END LOOP;
  CLOSE ingredientCursor;

  -- Record the statistics in the log details.
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '********************************************************************************' );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**************  NHPSAS Hash Manager - UpdateHashTable Statistics  **************' );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '********************************************************************************' );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**** Total Ingredients Records Processed: ' || counter );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**** Total Ingredients Records Added:     ' || Stats_Added );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**** Total Ingredients Records Updated:   ' || Stats_Updated );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**** Total Ingredients Records Deleted:   ' || Stats_Deleted );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '**** Total Ingredients Records No Change: ' || Stats_NoChange );
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, '********************************************************************************' );

  -- Close the logging process.
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, 'Process finished at : ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS') );
  LOG_MANAGER_PKG.CLOSE_LOG_PROC(  Log_ID, 'Process Completed.  Ingredient records processed: ' || counter );  

EXCEPTION
  -- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END updateHashTable;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'NHPSAS_HashMAnager_PKG';
EXCEPTION
-- Trap all other exceptions.
  WHEN OTHERS THEN
    DECLARE
      Error_Code    Error_Logs.Error_Code%Type    := SQLCODE;
      Error_Message Error_Logs.Error_Message%Type := SQLERRM;
    BEGIN
      ERROR_MANAGER_PKG.REPORT_ERROR(
        Error_Code, Error_Message, Constants_Pkg.History_Number,
        Package_Name, Process_Name, Process_Values);
    END;
    -- raise up the current unexpected exception.
    RAISE;
END NHPSAS_HashManager_PKG;
/


