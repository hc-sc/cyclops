-- The following removes a Method of Prep from a Defined Organism Substance
-- ingredient.
-- The select statements are in a single line so that you can run them one at a
-- time inside sqlplus or sqldeveloper and see results before continuing.
-- Author: JHAYDT
-- DATE: 2012-12-07

-- Identify the ingredient and method and get the IDs
   select ingred_id from ingredients where INGRED_AUTHORIZED_NAME_ENG = 'Semen Sojae Preparatum';
   -- 12273
   select preptyp_id from preparation_types where PREPTYPE_NAME_ENG = 'Powdered';
   -- 31
-- locate the cross reference in the INGREDIENT_PREPTYPES table.
   select * from ingredient_preptypes where ingred_id = 12273 and preptype_id = 31;
   -- one row returned
delete from ingredient_preptypes where ingred_id = 12273 and preptype_id = 31;
   -- 1 row deleted
commit;