-------------------------------------------------------------------------------
--                              postLoader.sql                               --
-------------------------------------------------------------------------------
-- This file contains the scripts that run after the loader has completed.   --
-- These scripts refresh the materialized views that are required for        --
-- searching ingredients, rapid web service responses and the                --
-- synchronization with the NHPSAS legacy system.                            --
-------------------------------------------------------------------------------
-- author:  Germain Brunet                                                   --
-- date:    2011/12/02                                                       --
-------------------------------------------------------------------------------
-- To update the code from the workstation use the following command in SQL PLUS.
-- SQL> @c:/RADProjects/Ingredients_Head/IngredientDomain/sql/createScripts/postLoader.sql
-- To initiate the post loader process use the following statement in SQL PLUS.
-- SQL> exec POST_LOADER_PKG.postLoaderInit;

CREATE OR REPLACE PACKAGE POST_LOADER_PKG
IS

  -----------------------------------------------------------------------------
  --                              refreshViews                               --
  -----------------------------------------------------------------------------
  -- Private:  Procedure Refresh Views is a public procedure and should be   --
  -- called when all the views associated with the post loading process      --
  -- should be refreshed.                                                    --
  -----------------------------------------------------------------------------
  PROCEDURE refreshViews;

  -----------------------------------------------------------------------------
  --                               refreshView                               --
  -----------------------------------------------------------------------------
  -- Private:  Procedure Refresh View is a private procedure and is called   --
  -- by refreshViews in order to refresh the materialized views in correct   --
  -- order.                                                                  --
  -----------------------------------------------------------------------------
  PROCEDURE refreshView(viewName IN VARCHAR2);

  -----------------------------------------------------------------------------
  --                                cleanLogs                                --
  -----------------------------------------------------------------------------
  -- Private:  This procedure removes old log entires that are greater than  --
  -- the number of days as defined by the Commons_Pkg.Log_Expire_In_Days     --
  -- constant.  The logging has been configured in a way that this procedure -- 
  -- will only show up in the logs when it is infact deleting something.     --
  -----------------------------------------------------------------------------
  PROCEDURE cleanLogs;
    
  -----------------------------------------------------------------------------
  --                              preLoaderInit                              --
  -----------------------------------------------------------------------------
  -- Public:  Scrip used to refresh the materialized views after the         --
  -- database load is completed.                                             --
  -----------------------------------------------------------------------------
  PROCEDURE postLoaderInit;
  
END POST_LOADER_PKG;
/

-- See package specification for implementation information.
CREATE OR REPLACE PACKAGE BODY POST_LOADER_PKG
IS

  -- Declare package level variables used for error logging purposes.
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;

-------------------------------------------------------------------------------
--                               refreshViews                                --
-------------------------------------------------------------------------------
PROCEDURE refreshViews
IS
  log_id          NUMBER(38);
  v_mat_view_name NHP_STAGE_MAT_VIEWS.MAT_VIEW_NAME%TYPE;
  CURSOR c_mat_view IS
    SELECT mat_view_name FROM NHP_STAGE_MAT_VIEWS ORDER BY mat_view_refresh_order; 
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'refreshViews';
  package_name   := 'POST_LOADER_PKG';
  process_values := '';

  -- Start the logging process.
  log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

  -- Log the start of the view refresh process.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'Starting the refreshing of all Materialized Views'); 
  
  -- Start the script.
  OPEN c_mat_view;
  LOOP
    FETCH c_mat_view INTO v_mat_view_name;
    refreshView(v_mat_view_name);
    EXIT WHEN c_mat_view%NOTFOUND;
  END LOOP;
  CLOSE c_mat_view;
  
  -- Log the end of the view refresh process.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'Ending the refreshing of all Materialied Views'); 

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
END refreshViews;

-------------------------------------------------------------------------------
--                                refreshView                                --
-------------------------------------------------------------------------------
PROCEDURE refreshView(
  viewName IN VARCHAR2
)
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'refreshView (' || viewName || ')';
  package_name   := 'POST_LOADER_PKG';
  process_values := viewName;

  -- Start the logging process.
  log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

  -- Log the start of the view refresh process.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'starting view refresh (' || viewName || ')'); 
  
  -- Start the script.
  DBMS_SNAPSHOT.REFRESH( viewName,'C');

  -- Log the end of the view refresh process.
  LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'ending view refresh (' || viewName || ')'); 

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
END refreshView;

-------------------------------------------------------------------------------
--                                 cleanLogs                                 --
-------------------------------------------------------------------------------
PROCEDURE cleanLogs
AS
  log_details_deleted_row_count NUMBER;
  logs_deleted_row_count        NUMBER;
  log_id                        NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'cleanLogs';
  package_name   := 'POST_LOADER_PKG';
  process_values := '';

  -- Start the script.
  -- init some values.
  log_details_deleted_row_count := 0;
  logs_deleted_row_count := 0;

  -- remove the old records from the log_details table.
  DELETE FROM log_details
  WHERE
    log_id in (
      SELECT 
        log_id
      FROM
        logs
      WHERE
        TRUNC(TO_NUMBER(SUBSTR((CURRENT_TIMESTAMP - end_time),1, INSTR(CURRENT_TIMESTAMP - end_time,' ')))) > Commons_Pkg.Log_Expire_In_Days
    );
  log_details_deleted_row_count := sql%rowcount;

  -- remove the old records from the logs table.
  DELETE FROM logs
    WHERE
      TRUNC(TO_NUMBER(SUBSTR((CURRENT_TIMESTAMP - end_time),1, INSTR(CURRENT_TIMESTAMP - end_time,' ')))) > Commons_Pkg.Log_Expire_In_Days;
  logs_deleted_row_count := sql%rowcount;

  IF log_details_deleted_row_count != 0 OR
     logs_deleted_row_count != 0 THEN
    -- Record this event to the log.
    log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);
    LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'deleted ' || logs_deleted_row_count || ' logs records.');
    LOG_MANAGER_PKG.UPDATE_LOG_PROC( Log_ID, 'deleted ' || log_details_deleted_row_count || ' log_details records.');
    LOG_MANAGER_PKG.CLOSE_LOG_PROC(  Log_ID, 'Process Completed.');  
  END IF;

END cleanLogs; 

-------------------------------------------------------------------------------
--                               preLoaderInit                               --
-------------------------------------------------------------------------------
PROCEDURE postLoaderInit
IS
  log_id         NUMBER(38);
BEGIN
  -- Start the logging process for error trapping purposes.
  process_name   := 'postLoaderInit';
  package_name   := 'POST_LOADER_PKG';
  process_values := '';

  -- Start the logging process.
  log_id := LOG_MANAGER_PKG.OPEN_LOG_FUNC(process_name);

  -- Start the script.
  refreshViews;
  cleanLogs;

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
END postLoaderInit;

-- Define package name for error trapping.
BEGIN
  Package_Name := 'POST_LOADER_PKG';
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
END POST_LOADER_PKG;
/
