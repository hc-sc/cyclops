-- Use to remove the homeopathic ingredient entries for test 5.17.2.c
-- Each command is on one line so you can paste it into your sqlplus or
-- sql developer and execute one at a time to see the results before deleting
-- Author: JHAYDT
-- DATE: 2012/12/11
-- 1. Remove a common name from a homeopathic ingredient
--    a. Chose an ingredient and get the id from the web app - HPUS_Taxus baccata
select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 10866;
-- HPUS_Taxus baccata
--    b. Chose which common name you want to remove - 'If';
--    c. Find the hoemopathic generic text refs for the common names of this ingredient.
select * from homeopathic_common_name_hgts where ingred_id = 10866;
-- INGRED_ID  HOMEOGENTEXT_ID
-- 10866    	29130
-- 10866    	29131
-- 10866    	29132
-- 10866    	29133
--    d. Find the text of the common name in the hoemopathic_generic_texts table
select * from homeopathic_generic_texts where homeogentext_id in (select homeogentext_id from homeopathic_common_name_hgts where ingred_id = 10866);
--29130	Taxus baccata   	Taxus baccata
--29131	Taxus fastigiata	Taxus fastigiata
--29132	Yew             	Yew
--29133	If              	If
--    e. Delete the row from the homeopathic_common_name_hgts table
delete from homeopathic_common_name_hgts where homeogentext_id = 29133 and ingred_id = 10866;
commit;

-- 2. Remove a proper name from a homeopathic ingredient
--    a. Chose the ingredient and find the id from the web app - EHP_Caesalpainia bonducella (8141)
select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 8141;
-- EHP_Caesalpinia bonducella
--    b. Chose the proper name to be removed - Caesalpinia crista
--    c. get the generic texts that contain proper names for this ingredient id
select * from homeopathic_proper_name_hgts where ingred_id=8141;
--    8141	6500
--    8141	6501
--    8141	6502
--    8141	6503
--    8141	6504
--    d. Find the name in the homeopathic_generic_texts table
select HOMEOGENTEXT_ID, HOMEOGENTEXT_NAME_ENG from homeopathic_generic_texts where HOMEOGENTEXT_ID in (select HOMEOGENTEXT_ID from homeopathic_proper_name_hgts where ingred_id=8141);
--    6500	Caesalpinia bonducella
--    6501	Caesalpinia bonduc
--    6502	Caesalpinia crista
--    6503	Guilandina bonduc
--    6504	Guilandina bonducella
--    e. delete the row in the homeopathic_proper_name_hgts table
delete from homeopathic_proper_name_hgts where ingred_id = 8141 and homeogentext_id = 6502;
commit;


-- 3. Remove a Source from the Source Info combo box list.
--    a. Select an ingredient with more than one source - HPUS_Sarsaparilla (10592)
select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 10592;
-- HPUS_Sarsaparilla
--    b. Find the ingredient's record in the ingredient_role table, also check rolespec_class_name
select * from ingredient_roles where ingred_id = 10592;
-- INGRED_ID  INGREDROLE_ID  ROLESPEC_CLASS_NAME
-- 10592      13085          HomeopahicRole
--    c. Find the ingredient's records in the homeopathic_formulas table using the ingredrole_id
select INGREDROLE_ID, HOMEOFORM_ID from homeopathic_formulas where ingredrole_id = 13085;
--    13085	3241
--    d. Find the ingredient's source material generic text ids from the homeopathic_source_mat_hgts
select * from homeopathic_source_mat_hgts where homeoform_id = 3241;
--    3241	26898
--    3241	26899
--    e. Find the generic text for the source you want to delete
select * from homeopathic_generic_texts where homeogentext_id in (select homeogentext_id from homeopathic_source_mat_hgts where homeoform_id = 3241);
--    26898	Root	Root
--    26899	Racine	Racine
--    f. delete the record from the homeopathic_source_mat_hgts table
delete from homeopathic_source_mat_hgts where homeogentext_id = 26899 and homeoform_id = 3241;
commit;

-- 4. Remove a Method of prep from  a homeopathic ingredient
--    a. Select an ingredient with more than one MoP - HPUS_Acetanilidum (7387)
select INGRED_AUTHORIZED_NAME_ENG from ingredients where ingred_id = 7387;
-- HPUS_Acetanilidum
--    b. Find the ingredient's record in the ingredient_role table, also check rolespec_class_name
select * from ingredient_roles where ingred_id = 7387;
-- INGRED_ID  INGREDROLE_ID  ROLESPEC_CLASS_NAME
-- 7387	      9879	         HomeopathicRole
--    c. Find the ingredient's records in the homeopathic_formulas table using the ingredrole_id
select INGREDROLE_ID, HOMEOFORM_ID from homeopathic_formulas where ingredrole_id = 9879;
--    9879	35
--    d. Find the ingredient's MoP records in the homeopathic_methofpreps table
select * from homeopathic_methofpreps where homeoform_id = 35;
--    HOMEOMOP_ID   HOMEOFORM_ID  HOMEOPREPTYPE_ID
--    39          	35          	44
--    40	          35	          45
--    e. Find the MoP reference you want to remove
select * from homeopathic_preparation_types where homeopreptype_id in (select homeopreptype_id from homeopathic_methofpreps where homeoform_id = 35);
--    44	HPUS Class A
--    45	HPUS Class F
--    f. Delete the mop reference from the homeopathic_mthofpreps table for the chosen prep - HPUS Class A
delete from homeopathic_methofpreps where homeomop_id = 40 and homeopreptype_id = 44;
commit;