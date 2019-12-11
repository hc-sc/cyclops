-- Use to remove the common or proper name from an ingredient.
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/05
-- 1. chose an ingredient and get the id from the web app - alangium chinense
select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 13184;
-- Alangium chinense
-- 2. Chose which common name you want to remove - Ba jiao feng;
--    the following synonym and qual synonym ids will likely change when the
--    data changes.
select Synonym_id from synonyms where synonym_name_eng = 'Ba jiao feng';
-- 26584
select qualsyno_id from qualified_synonyms where synonym_id = 26584;
-- 26814
-- 3. delete the qualified synonyms tables
delete from ingredient_qualsyno_cns where qualsyno_id = 26814 and ingred_id = 13184;
delete from ingredient_qualsyno_pns where qualsyno_id = 26814 and ingred_id = 13184;
delete from ingredient_qualsyno_qss where qualsyno_id = 26814 and ingred_id = 13184;
-- 4. delete the synonyms table
delete from ingredient_synonyms where synonym_id = 26584 and ingred_id = 13184;
commit;

-- the following lines helped me to get to the above simplified version
select * from ingredient_synonyms where ingred_id = 13184;
-- empty set
select * from INGREDIENT_QUALSYNO_QSS where ingred_id = 13184;
-- qualsyno_id = 26814, 26815
select * from INGREDIENT_QUALSYNO_PNS where ingred_id = 13184;
-- qualsyno_id = 33260
select * from INGREDIENT_QUALSYNO_CNS where ingred_id = 13184;
-- qualsyno_id = 26814, 26815
select * from qualified_synonyms WHERE qualsyno_id in (33260,26814,26815);
-- qualsyno_id  synonym_id
-- 26814        26584        
-- 26815        26585
-- 33260        29916
select SYNONYM_ID, SYNONYM_NAME_ENG from synonyms where synonym_id in (26584, 26585, 29916);
-- SYNONYM_ID   SYNONYM_NAME_ENG
-- 26584        Ba jiao feng
-- 26585        Chinese Alangium
-- 29916        Alangium chinense