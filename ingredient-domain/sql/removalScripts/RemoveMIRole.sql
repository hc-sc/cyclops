-- Removes the MI Role from an ingredient with more than one Role.
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/05

select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 86;
-- Magnesium caprylate
select ingredrole_id from INGREDIENT_ROLES where ingred_id = 86 and ROLESPEC_CLASS_NAME='MedicinalRole';
-- 2244
delete from ingredient_roles where ingred_id = 86 and ROLESPEC_CLASS_NAME='MedicinalRole';
-- 1 rows deleted
commit;