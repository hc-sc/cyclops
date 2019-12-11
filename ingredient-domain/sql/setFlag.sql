-- Description:
-- This script is to be executed in SQL Plus.
-- It will set a FLAG used for the automatic migration of the 
-- ingredient database schema to PROD and TEST.
-- It is to be run after the DEV Load has been confirmed successful.


delete from APPLICATION_PROPERTIES where KEY='loader.ready.to.publish';
insert into APPLICATION_PROPERTIES (KEY, DATA,FLAG) values ('loader.ready.to.publish', '1',1);
commit;

-- NHPSAS Integration
-- This script identifies the new ingredients for update to NHPSAS.
-- May take 10-15 mins.
-- Added by GB SP 2010-07-15
-- Note: The original updateHashTable contains a materialized view refresh command
-- that can no longer be executed via the admin account.  Here is the script that
-- will update the appropriate tables for NHPSAS.
--
-- Call NHPSAS_HashManager_Pkg.updateHashTable();

SET ESCAPE \

DECLARE
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
  Package_Name   ERROR_LOGS.Package_Name%Type;
  Process_Name   ERROR_LOGS.Process_Name%Type;
  Process_Values ERROR_LOGS.Process_Values%Type;
BEGIN
  -- Set basic values for error trapping.
  process_name   := 'NHPSAS_HashMAnager_PKG.updateHashTable';
  process_values := 'Process Started!';

  -- Start logging the process.
  Log_ID := LOG_MANAGER_PKG.OPEN_LOG_FUNC( process_name );  
  LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, 'Process started at : ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS') );

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
      status := NHPSAS_HashManager_Pkg.updateIngredientHash(ingred_id);
      -- Track statistics.
      IF status = Constants_Pkg.HashManager_Add THEN
        Stats_Added := Stats_Added + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Added   : ' || ingred_id || ' (' || NHPSAS_HashManager_Pkg.getIngredientNHPSASName(ingred_id) || ')');
      ELSIF status = Constants_Pkg.HashManager_Update THEN
        Stats_Updated := Stats_Updated + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Updated : ' || ingred_id || ' (' || NHPSAS_HashManager_Pkg.getIngredientNHPSASName(ingred_id) || ')');
      ELSIF status = Constants_Pkg.HashManager_Delete THEN
        Stats_Deleted := Stats_Deleted + 1;
        LOG_MANAGER_PKG.ADD_Details_PROC( Log_ID, ' ... Ingredient Deleted : ' || ingred_id || ' (' || NHPSAS_HashManager_Pkg.getIngredientNHPSASName(ingred_id) || ')');
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
END;
/

commit;