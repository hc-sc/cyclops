--------------------------------------------------------------------------------
--                                   Commons_Pkg                              --
--------------------------------------------------------------------------------
-- The common package contains functions and constants that are leveraged by  -- 
-- both the set_flags and controller packages.  This package is also the only --
-- one that talks dirrectly to the Application_Properties table.  This code   --
-- centralization and encapsulation allows for greater simplifications in the --
-- dependent packages.                                                        --
--------------------------------------------------------------------------------
-- Security Settings:                                                         --
-- - Commons_Pkg - performes inserts and updates into the                     --
--                 application_properties table.                              --
--                 Execute access required by both the client and admin.      --
-- - set_flags_Pkg - client driven, makes calls to the commons_Pkg.           --
--                   Execute access required by the client only.              --
-- - Controller_Pkg - Admin driven, makes calls to other packages pre & post  --
--                    loader that require special access (refreshing          --
--                    materialized views, truncating tables, etc.)            --
--                    Execute access required by the admin only.              --
--------------------------------------------------------------------------------
-- Workflow Explained:                                                        --
-- 1. - No Action:  The system is ready to start a load.  This is the status  -- 
--                  that is set once the load process is completed.           --
-- 2. - Start: The loader is indicating that this event can now be started.   --
--             Once this status is set, the controller should start the       --
--             appropriate process.                                           --
-- 3. - Working: Once the controller has started to work on a task, the       --
--               controller sets that task to this status.  This status and   --
--               the start status prevent the selected task from being        --
--               initiated by multiple processes.                             --
-- 4. - Completed: This flag is set by the controller once the task has been  --
--                 completed.                                                 --
--------------------------------------------------------------------------------
-- Author:   Germain Brunet                                                   --
-- Version:  0.9.1.B1                                                         --
-- Date:     2011-11-28                                                       --
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE Commons_Pkg
AUTHID CURRENT_USER
IS

  /* Processes */
  Loader_Ready_For_Pre_Load   CONSTANT VARCHAR2(50) := 'loader.ready.for.pre.load';  
  Loader_Ready_For_Post_Load  CONSTANT VARCHAR2(50) := 'loader.ready.for.post.load';  
  Loader_Ready_To_Publish     CONSTANT VARCHAR2(50) := 'loader.ready.to.publish';  

  /* Status - Text */
  No_Action_Text              CONSTANT VARCHAR2(50) := 'no.action'; 
  Start_Text                  CONSTANT VARCHAR2(50) := 'start';
  Working_Text                CONSTANT VARCHAR2(50) := 'working';  
  Completed_Text              CONSTANT VARCHAR2(50) := 'completed';
  
  /* Status - Integer */
  No_Action_Int               CONSTANT INTEGER      := 0; 
  Start_Int                   CONSTANT INTEGER      := 1;
  Working_Int                 CONSTANT INTEGER      := 2;
  Completed_Int               CONSTANT INTEGER      := 3;

  /* Default Actions */
  Default_Action_Text         CONSTANT VARCHAR2(50) := No_Action_Text;
  Default_Action_Int          CONSTANT INTEGER      := No_Action_Int;
  
  /* Logs number of days to keep the log and log detail */
  Log_Expire_In_Days          CONSTANT NUMBER       := 31;
  
  ------------------------------------------------------------------------------
  --                               SET_STATUS                                 --
  ------------------------------------------------------------------------------
  -- (Private) This procedure sets a given text and integer status to a given --
  -- process key.  This is accomplished by creating or updating a record      --
  -- located in the application_properties table.  The process key is the     --
  -- id used to determin if a record should be updated or created.  This is   --
  -- a private method and should be access via the public methods that set    --
  -- specific flags.  In addition, we support both the use of a text and      --
  -- integer flag.  The workflow is driven from the text flag only.           --
  ------------------------------------------------------------------------------
  PROCEDURE SET_STATUS(
    process_key IN Application_Properties.KEY%TYPE,
    status_text IN Application_Properties.DATA%TYPE,
    status_int  IN Application_Properties.FLAG%TYPE
  );

  ------------------------------------------------------------------------------
  --                            GET_STATUS_TEXT                               --
  ------------------------------------------------------------------------------
  -- (Public) This function returns the text status for a given process key.  --
  ------------------------------------------------------------------------------
  FUNCTION GET_STATUS_TEXT(
    process_key IN Application_Properties.KEY%TYPE
  ) RETURN VARCHAR2;

  ------------------------------------------------------------------------------
  -- (Public) This procedure sets the given process key to a status of        --
  -- no action.  This is done by the reset procedure once the load has been   --
  -- completed.                                                               --
  ------------------------------------------------------------------------------
  PROCEDURE SET_NO_ACTION(
    process_key IN Application_Properties.KEY%TYPE
  );

  ------------------------------------------------------------------------------
  --                               SET_START                                  --
  ------------------------------------------------------------------------------
  -- (Public) This procedure sets the given process key to a status of        --
  -- start.  This tells the controller that it can now start a specific       --
  -- process.                                                                 --
  ------------------------------------------------------------------------------
  PROCEDURE SET_START(
    process_key IN Application_Properties.KEY%TYPE
  );

  ------------------------------------------------------------------------------
  --                              SET_WORKING                                 --
  ------------------------------------------------------------------------------
  -- (Public) This procedure sets the given process key to a status of        --
  -- working.  This is done by the controller to tell outside processes that  --
  -- work is being performed on a specific process.                           --
  ------------------------------------------------------------------------------
  PROCEDURE SET_WORKING(
    process_key IN Application_Properties.KEY%TYPE
  );

  ------------------------------------------------------------------------------
  --                             SET_COMPLETED                                --
  ------------------------------------------------------------------------------
  -- (Public) This procedure sets the given process key to a status of        --
  -- completed.  This is done by the controller to tell outside processes     --
  -- that the work has been completed.                                        --
  ------------------------------------------------------------------------------
  PROCEDURE SET_COMPLETED(
    process_key IN Application_Properties.KEY%TYPE
  );

  ------------------------------------------------------------------------------
  --                               IS_BUSY                                    --
  ------------------------------------------------------------------------------
  -- (Public) This function returns a busy flag (1 = true, 0 = false) when    --
  -- the process identified by the process key has a status of start or       --
  -- working.  This is used by the loading process to wait until Oracle has   --
  -- completed the pre / post loader processes.                               --
  ------------------------------------------------------------------------------
  FUNCTION IS_BUSY(
    process_key IN Application_Properties.KEY%TYPE
  ) RETURN INTEGER;

  ------------------------------------------------------------------------------
  --                                RESET                                     --
  ------------------------------------------------------------------------------
  -- (Public) This procedure sets the loader pre/post processes to the        --
  -- default flags.  This is done once the loading is completed to indicate   --
  -- that a new load can now be started.  Also, this should be done if the    --
  -- loading process had to be aborted or restarted.                          --
  ------------------------------------------------------------------------------
  PROCEDURE RESET;

  ------------------------------------------------------------------------------
  --                               IS_READY                                   --
  ------------------------------------------------------------------------------
  -- (Public) This procedure returns a ready flag (1 = true, 0 = false) when  --
  -- both the pre / post loader processes are in a no action state.  This     --
  -- prevents the client from running multiple instances of the loader at the --
  -- same time, or of starting a loading process when oracle is busy with a   --
  -- pre or post loading process.                                             --
  ------------------------------------------------------------------------------
  FUNCTION IS_READY RETURN INTEGER;

END Commons_Pkg;
/

-- See package specification for implementation information.
CREATE OR REPLACE PACKAGE BODY Commons_Pkg
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

--------------------------------------------------------------------------------
--                                SET_STATUS                                  --
--------------------------------------------------------------------------------
PROCEDURE SET_STATUS(
  process_key IN Application_Properties.KEY%TYPE,
  status_text IN Application_Properties.DATA%TYPE,
  status_int  IN Application_Properties.FLAG%TYPE
) 
IS
  counter INTEGER;
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'set_status';
  package_name   := 'commons_pkg';
  process_values := process_key || '(' || status_text || ')';

  -- Start of the code.
  -- Does this record already exists?
  SELECT count(*) INTO counter 
  FROM application_properties
  WHERE key = process_key;

  IF counter = 0 THEN
    -- No record found, add a new record.
    insert into application_properties (key, data, flag) values (
      process_key, status_text, status_int);
  ELSE
    -- Record found, update the status.
    update application_properties set data=status_text, 
      flag=status_int where key=process_key;
  END IF;

  COMMIT;

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
END SET_STATUS;

--------------------------------------------------------------------------------
--                             GET_STATUS_TEXT                                --
--------------------------------------------------------------------------------
FUNCTION GET_STATUS_TEXT(
  process_key IN Application_Properties.KEY%TYPE
) RETURN VARCHAR2
IS
  status  Application_Properties.DATA%TYPE;
  counter Integer;
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'get_status_text';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  SELECT COUNT(*) INTO counter
  FROM application_properties
  WHERE key = process_key;

  IF counter = 0 THEN
    RETURN commons_pkg.Default_Action_Text;
  END IF;

  -- Start of the code.
  SELECT data INTO status
  FROM application_properties
  WHERE key = process_key;

  RETURN status;

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
END GET_STATUS_TEXT;

--------------------------------------------------------------------------------
--                              SET_NO_ACTION                                 --
--------------------------------------------------------------------------------
PROCEDURE SET_NO_ACTION(
  process_key IN Application_Properties.KEY%TYPE
) 
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'set_no_action';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  Commons_Pkg.SET_STATUS(
    process_key,
    Commons_Pkg.No_Action_Text,
    Commons_Pkg.No_Action_Int
  );

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
END SET_NO_ACTION;

--------------------------------------------------------------------------------
--                                SET_START                                   --
--------------------------------------------------------------------------------
PROCEDURE SET_START(
  process_key IN Application_Properties.KEY%TYPE
)
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'set_start';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  Commons_Pkg.SET_STATUS(
    process_key,
    Commons_Pkg.Start_Text,
    Commons_Pkg.Start_Int
  );

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
END SET_START;

--------------------------------------------------------------------------------
--                               SET_WORKING                                  --
--------------------------------------------------------------------------------
PROCEDURE SET_WORKING(
  process_key IN Application_Properties.KEY%TYPE
)
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'set_status';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  Commons_Pkg.SET_STATUS(
    process_key,
    Commons_Pkg.Working_Text,
    Commons_Pkg.Working_Int
  );

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
END SET_WORKING;

--------------------------------------------------------------------------------
--                              SET_COMPLETED                                 --
--------------------------------------------------------------------------------
PROCEDURE SET_COMPLETED(
  process_key IN Application_Properties.KEY%TYPE
)
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'set_completed';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  Commons_Pkg.SET_STATUS(
    process_key,
    Commons_Pkg.Completed_Text,
    Commons_Pkg.Completed_Int
  );

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
END SET_COMPLETED;

--------------------------------------------------------------------------------
--                                 IS_BUSY                                    --
--------------------------------------------------------------------------------
FUNCTION IS_BUSY(
  process_key IN Application_Properties.KEY%TYPE
) RETURN INTEGER
IS
  status Application_Properties.DATA%TYPE;
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'is_busy';
  package_name   := 'commons_pkg';
  process_values := process_key;

  -- Start of the code.
  status := Commons_Pkg.GET_STATUS_TEXT(process_key);
  IF status = Commons_Pkg.Start_Text THEN
    RETURN 1;
  END IF;
  IF status = Commons_Pkg.Working_Text THEN
    RETURN 1;
  END IF;
  RETURN 0;

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
END IS_BUSY;

--------------------------------------------------------------------------------
--                                  RESET                                     --
--------------------------------------------------------------------------------
PROCEDURE RESET
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'reset';
  package_name   := 'commons_pkg';
  process_values := '';

  -- Start of the code.
  Commons_Pkg.SET_STATUS(
    Commons_Pkg.Loader_Ready_For_Pre_Load,
    Commons_Pkg.Default_Action_Text,
    Commons_Pkg.Default_Action_Int
  );
  Commons_Pkg.SET_STATUS(
    Commons_Pkg.Loader_Ready_For_Post_Load,
    Commons_Pkg.Default_Action_Text,
    Commons_Pkg.Default_Action_Int
  );

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
END RESET;

--------------------------------------------------------------------------------
--                                IS_READY                                    --
--------------------------------------------------------------------------------
FUNCTION IS_READY RETURN INTEGER
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'is_ready';
  package_name   := 'commons_pkg';
  process_values := '';

  -- Start of the code.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Pre_Load) != 
        Commons_Pkg.Default_Action_Text THEN
    RETURN 0;
  END IF;
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Post_Load) != 
        Commons_Pkg.Default_Action_Text THEN
    RETURN 0;
  END IF;
  RETURN 1;

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
END IS_READY;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'commons_pkg';
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
END Commons_Pkg;
/



--------------------------------------------------------------------------------
--                                 Set_Flags_Pkg                              --
--------------------------------------------------------------------------------
-- The set flag package contains functions that will be called from the java  --
-- based loader.  These functions will be called as part of the pre and post  --
-- loading processes.  In addition, two functions are provided to allow the   --
-- the loader to wait until the pre and post loading processes are completed. --
--------------------------------------------------------------------------------
-- Security:  The loader should have execute access to the contents of this   --
-- package.  This package does not create tables, refresh materialized views  --
-- or truncate any information.                                               --
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE SET_FLAGS_PKG
AUTHID CURRENT_USER
IS

  ------------------------------------------------------------------------------
  --                            START_PRE_LOAD                                --
  ------------------------------------------------------------------------------
  -- (Public) This procedure will set the flag to start the pre-loading       --
  -- processes.  The requirement for triggering the loading process is that   --
  -- the flags are in a status of no action.                                  --
  -- Note:  To reset the flag, use the Reset procedure.                       --
  ------------------------------------------------------------------------------
  PROCEDURE START_PRE_LOAD;

  ------------------------------------------------------------------------------
  --                           IS_BUSY_PRE_LOAD                               --
  ------------------------------------------------------------------------------
  -- (Public) This function returns a (1 - true, 0 - false) indicating that   --
  -- oracle is busy with the pre-loading tasks.  Once oracle has completed    --
  -- the pre-loading tasks, this value is set to 0.  This tells the loader    --
  -- that it is safe to continue with the loading process.                    --
  -- Note:  This function is called by the Java loader every second for a     --
  -- maximum period of 10 minutes.                                            --
  ------------------------------------------------------------------------------
  FUNCTION IS_BUSY_PRE_LOAD RETURN INTEGER;

  ------------------------------------------------------------------------------
  --                            START_POST_LOAD                               --
  ------------------------------------------------------------------------------
  -- (Public) This procedure will set the flag to start the post-loading      --
  -- processes.  The requirement for triggering the loading process is that   --
  -- the flags are in a status of no action.                                  --
  -- Note:  To reset the flag, use the Reset procedure.                       --
  ------------------------------------------------------------------------------
  PROCEDURE START_POST_LOAD;

  ------------------------------------------------------------------------------
  --                           IS_BUSY_POST_LOAD                              --
  ------------------------------------------------------------------------------
  -- (Public) This function returns a (1 - true, 0 - false) indicating that   --
  -- oracle is busy with the post-loading tasks.  Once oracle has completed   --
  -- the post-loading tasks, this value is set to 0.  This tells the loader   --
  -- that it is safe to continue with the loading process.                    --
  -- Note:  This function is called by the Java loader every second for a     --
  -- maximum period of 10 minutes.                                            --
  ------------------------------------------------------------------------------
  FUNCTION IS_BUSY_POST_LOAD RETURN INTEGER;  

  ------------------------------------------------------------------------------
  --                                 RESET                                    --
  ------------------------------------------------------------------------------
  -- (Public) In some rare cases, the loading process is aborted in           --
  -- mid-process.  This may leave some flags in an unusable state preventing  --
  -- the loader from re-starting.  This procedure can be used to reset all    --
  -- relevant loading flags.                                                  --
  ------------------------------------------------------------------------------
  PROCEDURE RESET;

END SET_FLAGS_PKG;
/

CREATE OR REPLACE PACKAGE BODY SET_FLAGS_PKG
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

--------------------------------------------------------------------------------
--                             START_PRE_LOAD                                --
--------------------------------------------------------------------------------
PROCEDURE START_PRE_LOAD
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'start_pre_load';
  package_name   := 'set_flags_pkg';
  process_values := '';

  -- Start of the code.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Pre_Load) != 
        Commons_Pkg.Default_Action_Text THEN
    RETURN;
  END IF;
  Commons_Pkg.SET_START(Commons_Pkg.Loader_Ready_For_Pre_Load);

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
END START_PRE_LOAD;

-------------------------------------------------------------------------------
--                            IS_BUSY_PRE_LOAD                               --
-------------------------------------------------------------------------------
FUNCTION IS_BUSY_PRE_LOAD RETURN INTEGER 
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'is_busy_pre_load';
  package_name   := 'set_flags_pkg';
  process_values := '';

  -- Start of the code.
  return Commons_Pkg.IS_BUSY(Commons_Pkg.Loader_Ready_For_Pre_Load);

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
END IS_BUSY_PRE_LOAD;

-------------------------------------------------------------------------------
--                             START_POST_LOAD                               --
-------------------------------------------------------------------------------
PROCEDURE START_POST_LOAD
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'start_post_load';
  package_name   := 'set_flags_pkg';
  process_values := '';

  -- Start of the code.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Post_Load) != 
        Commons_Pkg.Default_Action_Text THEN
    RETURN;
  END IF;

  -- Start of the code.
  Commons_Pkg.SET_START(Commons_Pkg.Loader_Ready_For_Post_Load);

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
END START_POST_LOAD;

-------------------------------------------------------------------------------
--                            IS_BUSY_POST_LOAD                              --
-------------------------------------------------------------------------------
FUNCTION IS_BUSY_POST_LOAD RETURN INTEGER 
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'is_busy_post_load';
  package_name   := 'set_flags_pkg';
  process_values := '';

  -- Start of the code.
  return Commons_Pkg.IS_BUSY(Commons_Pkg.Loader_Ready_For_Post_Load);

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
END IS_BUSY_POST_LOAD;

-------------------------------------------------------------------------------
--                                  RESET                                    --
-------------------------------------------------------------------------------
PROCEDURE RESET
IS
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'reset';
  package_name   := 'set_flags_pkg';
  process_values := '';

  -- Start of the code.
  Commons_Pkg.RESET;

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
END RESET;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'set_flags_pkg';
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
END SET_FLAGS_PKG;
/


--------------------------------------------------------------------------------
--                                Controller_Pkg                              --
--------------------------------------------------------------------------------
-- The controller package should be executed by a scheduled cron job on at    --
-- intervals of 5 minutes.  This package is responsible for looking at the    --
-- state of the loader flags and of executing the appropriate pre and post    --
-- loader packages / procedures.                                              --
--------------------------------------------------------------------------------
-- Security:  This package needs to be able to truncate the contents of       --
-- tables, alter sequences, refresh materialized views.                       --
--------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE CONTROLLER_PKG
IS

  ------------------------------------------------------------------------------
  --                                 PRE_LOAD                                 --
  ------------------------------------------------------------------------------
  -- Private:  This procedure checks to see if the pre-loader flag has been   --
  -- set.  If so then the package / procedure associated with the pre-loading --
  -- is triggered.  This is a private stored procedure and should be exected  --
  -- via the init procedure.                                                  --
  ------------------------------------------------------------------------------
  PROCEDURE PRE_LOAD;

  ------------------------------------------------------------------------------
  --                                POST_LOAD                                 --
  ------------------------------------------------------------------------------
  -- Private:  This procedure checks to see if the post-loader flag has been  --
  -- set. If so then the package / procedure associated with the post-loading --
  -- is triggered.  This is a private stored procedure and should be executed --
  -- via the init procedure.
  ------------------------------------------------------------------------------
  PROCEDURE POST_LOAD;

  ------------------------------------------------------------------------------
  --                                   INIT                                   --
  ------------------------------------------------------------------------------
  -- Public:  This procedure calls on both the pre_load and post_load         --
  -- procedures.  This should be done every 5 minutes to work with the client --
  -- started loading process.                                                 --
  ------------------------------------------------------------------------------
  PROCEDURE INIT;

END CONTROLLER_PKG;
/


CREATE OR REPLACE PACKAGE BODY CONTROLLER_PKG
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

-------------------------------------------------------------------------------
--                                  PRE_LOAD                                 --
-------------------------------------------------------------------------------
PROCEDURE PRE_LOAD
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'pre_load';
  package_name   := 'controller_pkg';
  process_values := '';

  -- Start of the code.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Pre_Load) =
    Commons_Pkg.Start_Text THEN

    -- Start the logging process.
    log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

    LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'running pre-loader scripts.'); 
    Commons_Pkg.SET_WORKING(Commons_Pkg.Loader_Ready_For_Pre_Load);
    PRE_LOADER_PKG.preLoaderInit;
    Commons_Pkg.SET_COMPLETED(Commons_Pkg.Loader_Ready_For_Pre_Load);

    -- Stop the logging process.
    LOG_MANAGER_PKG.CLOSE_LOG_PROC(  Log_ID, 'Process Completed.');  

  END IF;

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
END PRE_LOAD;

-------------------------------------------------------------------------------
--                                 POST_LOAD                                 --
-------------------------------------------------------------------------------
PROCEDURE POST_LOAD
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'post_load';
  package_name   := 'controller_pkg';
  process_values := '';

  -- Start of the code.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Post_Load) =
    Commons_Pkg.Start_Text THEN

    -- Start the logging process.
    log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

    LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'running post-loader scripts.'); 
    Commons_Pkg.SET_WORKING(Commons_Pkg.Loader_Ready_For_Post_Load);
    POST_LOADER_PKG.postLoaderInit;
    Commons_Pkg.SET_COMPLETED(Commons_Pkg.Loader_Ready_For_Post_Load);

    -- Stop the logging process.
    LOG_MANAGER_PKG.CLOSE_LOG_PROC(  Log_ID, 'Process Completed.');  

  END IF;

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
END POST_LOAD;

--------------------------------------------------------------------------------
--                                    INIT                                    --
--------------------------------------------------------------------------------
PROCEDURE INIT
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'init';
  package_name   := 'controller_pkg';
  process_values := '';

  -- Start the logging process.
  log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

  -- Start of the code.

  -- Run the reset once everything is completed.
  -- by doing this before starting the process, we will be in a completed state
  -- for 5 minutes...  for debugging purposes.
  IF Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Pre_Load) =
       Commons_Pkg.Completed_Text AND
     Commons_Pkg.GET_STATUS_TEXT(Commons_Pkg.Loader_Ready_For_Post_Load) =
       Commons_Pkg.Completed_Text THEN
    LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'Both process completed!  Reset triggered.'); 
    Commons_Pkg.Reset;
  END IF;

  -- Run the PRE and POST loader code.
  CONTROLLER_PKG.PRE_LOAD;
  CONTROLLER_PKG.POST_LOAD;

  -- Stop the logging process.
  LOG_MANAGER_PKG.CLOSE_LOG_PROC(  log_id, 'Process Completed.');  

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
END INIT;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'controller_pkg';
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
END CONTROLLER_PKG;
/



