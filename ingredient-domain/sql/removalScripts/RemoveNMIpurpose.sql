-- Removes the NMI Purpose from a NM ingredient
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/05

-- chose the ingredient with more than one purpose - Agar
-- find the ingredient ID from the ingredients table
select ingred_id from ingredients where INGRED_AUTHORIZED_NAME_ENG = 'Agar';
-- 3741
-- find the Purpose ID of the purpose you want to delete - Binder
select purpose_id from purposes where PURPOSE_NAME_ENG = 'Binder';
-- 17
-- find the ingredient-role id for the ingredient, this assumes the ingredient
-- DOES NOT HAVE ANOTHER ROLE.
select ingredrol_id from ingredient_roles where ingred_id = 3741;
-- 8615
-- if you get more than 1 record for the above, you will need to find which one
-- corresponds to the NMI role by selecting all columns and finding the one for
-- NonMedicinal.
select * from ingredient_role_purposes where ingredrole_id = 8615 and purpose_id = 17;
-- you should get one entry, delete it.
delete from ingredient_role_purposes where ingredrole_id = 8615 and purpose_id = 17;
commit;
