Staging Scripts
===============

The scripts located in the stagingScripts folder are responsible for preparing
the ingredient database schema for receiving and presenting information.  These
scripts are contained in the following 4 files that have dependencies on one 
another.

configuration.sql:  Configuration related information required for both the pre 
                    and post loading of data.
                    
preloader.sql:      Scripts to be executed before the loading of data is started.

postLoader.sql:     Scripts to be executed after the loading of data is completed.

handShake.sql:      Scripts that help manage the conversation between the loader
                    and the oracle cron job responsible for running the pre and 
                    post loader scripts.
               
Installation
============
Account:  These scripts must be installed using the owner account.
                    
Loading Order:  Because of the dependencies, the scripts should be run in the 
following order.

@C:\EclipseWorkspace\IngredientDomain\sql\stagingScripts\configuration.sql
@C:\EclipseWorkspace\IngredientDomain\sql\stagingScripts\preLoader.sql
@C:\EclipseWorkspace\IngredientDomain\sql\stagingScripts\postLoader.sql
@C:\EclipseWorkspace\IngredientDomain\sql\stagingScripts\handShake.sql
commit;

Running
=======

A cron job needs to run every 5 minutes to see if one of the flags has been set.
If the flag has been set, then the appropriate pre or post loader process is
called.  The cron job must run using the owner account.

exec CONTROLLER_PKG.INIT;


Reset
=====

If a load is unsuccessful, the flag may need to be reset via the following command:

exec SET_FLAGS_PKG.reset;



