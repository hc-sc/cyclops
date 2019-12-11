------------------------------------------------------------
--                        preLoader.sql                   --
------------------------------------------------------------
-- The Create Table Truncate Scripts is a collection of functions / stored procedures that
-- are used to help manage the loading process.  These scripts are responsible for 
-- truncating the tables, deleting and recreating the sequences and other important
-- functions involving the loading process.
-- 
-- Note:  This package should be called using the preLoaderInit procedure.
-- 
-- Example:  exec PRE_LOADER_PKG.preLoaderInit;
------------------------------------------------------------
-- author:  Germain Brunet
-- date:    2011/12/02
------------------------------------------------------------

CREATE OR REPLACE PACKAGE PRE_LOADER_PKG
IS

  ------------------------------------------------------------
  --                  disableAllConstraints                 --
  ------------------------------------------------------------
  -- Private:  Used to disable all the database             --
  -- constraints.  This is done before we truncate all the  --
  -- tables for a new load.                                 --  
  ------------------------------------------------------------
  PROCEDURE disableAllConstraints;

  ------------------------------------------------------------
  --                   enableAllConstraints                 --
  ------------------------------------------------------------
  -- Private:  Used to enable all the database constraints. --
  -- This is done after we have truncated all the tables.   --  
  ------------------------------------------------------------
  PROCEDURE enableAllConstraints;
  
  ------------------------------------------------------------
  --                      truncateTable                     --
  ------------------------------------------------------------
  -- Private:  Scrip used to truncate the specified         --
  -- database table.                                        --  
  ------------------------------------------------------------
  PROCEDURE truncateTable(
    table_name           VARCHAR2
  );
  
  ------------------------------------------------------------
  --                    truncateAllTables                   --
  ------------------------------------------------------------
  -- Private:  Scrip used to truncate all the database      --
  -- tables.                                                --  
  ------------------------------------------------------------
  PROCEDURE truncateAllTables;

  ------------------------------------------------------------
  --                   set sequence number                  --
  ------------------------------------------------------------
  -- Private:  Scrip used to update the sequence to the     --
  -- required value.                                        --
  ------------------------------------------------------------
  PROCEDURE setSequenceNumber(
    sequence_name  VARCHAR2,
    sequence_start NUMBER
  );
  
  ------------------------------------------------------------
  --                  reset sequence number                 --
  ------------------------------------------------------------
  -- Private:  Updates a sequence and give it a starting    -- 
  -- number of 1.                                           --
  ------------------------------------------------------------
  PROCEDURE resetSequenceNumber(
    sequence_name  VARCHAR2
  );

  ------------------------------------------------------------
  --                     reset sequences                    --
  ------------------------------------------------------------
  -- Private:  Scrip used to reset all the sequences in the --
  -- schema.                                                --  
  ------------------------------------------------------------
  PROCEDURE resetSequences;
  
  ------------------------------------------------------------
  --                      preLoadScript                     --
  ------------------------------------------------------------
  -- Private:  Scrip used to preload data and stamp the     --
  -- schema version and date into the database.             --  
  ------------------------------------------------------------
  PROCEDURE preLoadScript;

  ------------------------------------------------------------
  --                     preLoaderInit                      --
  ------------------------------------------------------------
  -- Public:  Scrip used to clean the Ingredient Database   --
  -- before a load.  This scrip call all the other relevant -- 
  -- scripts. Note:  This script assumes that the database  -- 
  -- was previously created with the build_script.sql.      --
  ------------------------------------------------------------
  PROCEDURE preLoaderInit;
  
END PRE_LOADER_PKG;
/

CREATE OR REPLACE PACKAGE BODY PRE_LOADER_PKG
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

------------------------------------------------------------
--                  disableAllConstraints                 --
------------------------------------------------------------
PROCEDURE disableAllConstraints 
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'disableAllConstraints';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';
  -- Start the script.
  for i in (
    select 
      constraint_name, 
      table_name 
    from 
      user_constraints 
    where 
      constraint_type ='R' and 
      status = 'ENABLED') LOOP
  execute immediate 'alter table '||i.table_name||' disable constraint '||i.constraint_name||'';
  end loop;
  -- Error Trapping.
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
END disableAllConstraints;

------------------------------------------------------------
--                   enableAllConstraints                 --
------------------------------------------------------------
PROCEDURE enableAllConstraints
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'enableAllConstraints';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';
  -- Start the script.
  for i in (
    select 
      constraint_name, 
      table_name 
    from 
      user_constraints 
    where 
      constraint_type ='R' and 
      status = 'DISABLED') LOOP
  execute immediate 'alter table '||i.table_name||' enable constraint '||i.constraint_name||'';
  end loop;
  -- Error Trapping.
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
END enableAllConstraints;

------------------------------------------------------------
--                      truncateTable                     --
------------------------------------------------------------
-- exec truncateTable('temp'); -- test failure
-- exec truncateTable('ADMINISTRATION_ROUTES'); -- test success
-- exec truncateTable('DR$INGREDIENT_SNNMS_MV_IDX7$I'); -- test success
PROCEDURE truncateTable(
  table_name           VARCHAR2
) IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'truncateTable';
  package_name   := 'PRE_LOADER_PKG';
  process_values := table_name;
  -- Start the script.
  execute immediate 'truncate table "'|| table_name ||'" drop storage';
  -- Error Trapping.
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
END truncateTable;

------------------------------------------------------------
--                    truncateAllTables                   --
------------------------------------------------------------
PROCEDURE truncateAllTables
IS
  v_table_name   NHP_STAGE_TABLES.TABLE_NAME%TYPE;
  CURSOR c_table IS
    SELECT table_name FROM NHP_STAGE_TABLES WHERE table_truncate='Y'; 
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'truncateAllTables';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';
  
  -- Start the script.
  OPEN c_table;
  LOOP
    FETCH c_table INTO v_table_name;
    truncateTable(v_table_name);
    EXIT WHEN c_table%NOTFOUND;
  END LOOP;
  CLOSE c_table;

  -- Error Trapping.
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
END truncateAllTables;

------------------------------------------------------------
--                   set sequence number                  --
------------------------------------------------------------
-- exec setSequenceNumber('PUBLISHED_JOURNALS_SEQ', 0);
-- select PUBLISHED_JOURNALS_SEQ.nextval from dual;
PROCEDURE setSequenceNumber(
  sequence_name  VARCHAR2,
  sequence_start NUMBER
) IS
  current_value  NUMBER;
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'setSequenceNumber';
  package_name   := 'PRE_LOADER_PKG';
  process_values := sequence_name;
  -- Start the script.
  -- Reset the sequence to 0.
  execute immediate 'select ' || sequence_name || '.nextval from dual' INTO current_value;
  execute immediate 'alter sequence ' || sequence_name || ' increment by -' || (current_value) || ' minvalue 0';
  execute immediate 'select ' || sequence_name || '.nextval from dual' INTO current_value;
  IF (sequence_start <> 0) THEN
    -- Increase the sequence to the required value.
    execute immediate 'alter sequence ' || sequence_name || ' increment by ' || (sequence_start) || ' minvalue 0';
    execute immediate 'select ' || sequence_name || '.nextval from dual' INTO current_value;
  END IF;
  -- Set the increment value back to 1.
  execute immediate 'alter sequence ' || sequence_name || ' increment by 1 minvalue 0';
  -- Error Trapping.
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
END setSequenceNumber;

------------------------------------------------------------
--                  reset sequence number                 --
------------------------------------------------------------
-- exec resetSequenceNumber('PUBLISHED_JOURNALS_SEQ');
PROCEDURE resetSequenceNumber(
  sequence_name  VARCHAR2
) IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'resetSequenceNumber';
  package_name   := 'PRE_LOADER_PKG';
  process_values := sequence_name;
  -- Start the script.
  setSequenceNumber(sequence_name, 0);
  -- Error Trapping.
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
END resetSequenceNumber;

------------------------------------------------------------
--                     reset sequences                    --
------------------------------------------------------------
PROCEDURE resetSequences
IS
  v_sequence_name         NHP_STAGE_SEQUENCES.SEQUENCE_NAME%TYPE;
  v_sequence_start_number NHP_STAGE_SEQUENCES.SEQUENCE_START_NUMBER%TYPE;
  CURSOR c_sequence IS
    SELECT sequence_name, sequence_start_number FROM NHP_STAGE_SEQUENCES; 
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'resetSequences';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';

  -- Start the script.
  OPEN c_sequence;
  LOOP
    FETCH c_sequence INTO v_sequence_name, v_sequence_start_number;
    IF v_sequence_start_number = 1 THEN
      resetSequenceNumber(v_sequence_name);
    END IF;
    IF v_sequence_start_number != 1 THEN
      setSequenceNumber(v_sequence_name, v_sequence_start_number);
    END IF;
    EXIT WHEN c_sequence%NOTFOUND;
  END LOOP;
  CLOSE c_sequence;

  -- Error Trapping.
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
END resetSequences;

------------------------------------------------------------
--                      preLoadScript                     --
------------------------------------------------------------
PROCEDURE preLoadScript
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'preLoadScript';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';
  -- Start the script.
  INSERT INTO user_accounts (
    useracc_id,useracc_account_name,useracc_first_name,useracc_last_name,useracc_email,useracc_creation_date,useracc_lastupdate_date,useracc_useracc_id) 
  VALUES (
    1,'uploader','System','Uploader','sysUploader2@hc.ca',SYSDATE ,SYSDATE ,0);
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
END preLoadScript;

------------------------------------------------------------
--                      cleanDatabase                     --
------------------------------------------------------------
-- exec cleanDatabase;
PROCEDURE preLoaderInit
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'preLoaderInit';
  package_name   := 'PRE_LOADER_PKG';
  process_values := '';
  -- Start the script.
  -- Start the logging process.
  log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

  -- Start the script.
  -- Disable all the constraints.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'disabling constraints.'); 
  disableAllConstraints;

  -- Truncate all tables.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'truncating tables.'); 
  truncateAllTables;

  -- Enable all the constraints.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'enabling constraints.'); 
  enableAllConstraints;

  -- Drop all the sequences.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'reseting sequences.'); 
  resetSequences;

  -- Execute pre-load scripts.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'executing pre-load scripts.'); 
  preLoadScript;

  -- Stop the logging process.
  LOG_MANAGER_PKG.CLOSE_LOG_PROC(  Log_ID, 'Process Completed.');  
  -- Error Trapping.
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
END preLoaderInit;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'PRE_LOADER_PKG';
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
END PRE_LOADER_PKG;
/
