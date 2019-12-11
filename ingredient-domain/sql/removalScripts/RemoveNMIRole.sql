-- Removes the NMI Role and purposes from an ingredient with more than one
-- Role.
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/05

select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 1362;
-- 2-Oxoglutaric acid
select ingredrole_id from INGREDIENT_ROLES where ingred_id = 1362 and ROLESPEC_CLASS_NAME='NonMedicinalRole';
-- 6186
select * from INGREDIENT_ROLE_PURPOSES where ingredrole_id = 4953;
-- 2 rows returned
delete from ingredient_role_purposes where ingredrole_id in (select ingredrole_id from INGREDIENT_ROLES where ingred_id = 86 and ROLESPEC_CLASS_NAME='NonMedicinalRole');
-- 2 rows deleted
delete from ingredient_roles where ingred_id = 1362 and ROLESPEC_CLASS_NAME='NonMedicinalRole';
-- 1 rows deleted
commit;