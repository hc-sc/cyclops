-- The following removes a constituent from an ingredient organism+part
-- for which there is at least one standardized method of preparation
-- the select statements are in a single line so that you can run them one at a
-- time inside sqlplus or sqldeveloper and see results before continuing.
-- Author: JHAYDT
-- DATE: 2012-12-06

-- chose the ingredient+part and constituent you want to delete
-- 1. Delete the constituent Vitamin C from the (organism)Ingredient - part
--    Capsicum annuum - Fruit 
--    http://was-tqa-dev/nhpid-bdipsn/orgpReq.do?id=2946&lang=eng
-- 2. Find their ingredient IDs from the INGREDIENTS table
      select ingred_id from ingredients where INGRED_AUTHORIZED_NAME_ENG = 'Capsicum annuum';
      -- 5973
      -- http://was-tqa-dev/nhpid-bdipsn/ingredReq.do?id=5973&lang=eng
      select ingred_id from ingredients where INGRED_AUTHORIZED_NAME_ENG = 'Vitamin C';
      -- 916
      -- http://was-tqa-dev/nhpid-bdipsn/ingredReq.do?id=916&lang=eng
-- 3. find the organism id of the organism for the ingredient with the constituent
      select organism_id from ingredient_organism_mv where ingred_id = 5973;
      -- organism_id for Capsicum annuum = 155
      -- http://was-tqa-dev/nhpid-bdipsn/orgReq.do?id=155&lang=eng
-- 4. find the orgpart_id of the organism part that contains the constituent to
-- delete.  You can only find the textual reference to the part in the
-- following way.
  SELECT ORGPART_ID, orgparttype_id FROM ORGANISM_PARTS WHERE ORGANISM_ID = 155;
  -- 469	197
  -- 156	31
  -- 2946	21 <<<- http://was-tqa-dev/nhpid-bdipsn/orgpReq.do?id=2946&lang=eng
  -- 3059	89
  SELECT orgparttype_id, ORGPARTTYPE_NAME_ENG from organism_part_types where orgparttype_id in (197, 31, 21, 89);
  -- 21	 Fruit <<<- corresponds to ORGPART_ID 2946 = Capsicum annuum - Fruit
  -- 31	 Fruit oleoresin
  -- 89	 Petal
  -- 197 Whole plant
  -- on the web app, search on the ingredient name, select the "Source
  -- Materials" link (in this case the organism page in 3. above).  On the
  -- Organism page, select the part that contains the constituent you want to
  -- remove (in this case the Fruit link).  The id in the resulting page's link
  -- is the organism part id to use in the following queries.
-- Find the reference inside the SUBINGREDIENTS table that corresponds
-- to the chosen organism, part, and constituent
  select subingred_id from organism_part_subingredients where orgpart_id = 2946;
  -- 1699,2095,2189,2197,2198,2199,2200,2204,2403,2470,2526,3521,3522,1044,1290,1364,3724
  select subingred_id, ingred_id from subingredients where subingred_id in (1699,2095,2189,2197,2198,2199,2200,2204,2403,2470,2526,3521,3522,1044,1290,1364,3724);
  -- 1048,3058,2983,2771,3050,7062,7067,7068,7069,3066,916,7070,1717,3428,7159,7160,13160
  -- sub  ing
  -- 1044	1048
  -- 1290	3058
  -- 1364	2983
  -- 1699	2771
  -- 2095	3050
  -- 2189	7062
  -- 2197	7067
  -- 2198	7068
  -- 2199	7069
  -- 2200	3066
  -- 2204	7070
  -- 2403	1717
  -- 2470	3428
  -- 2526   916    <<<< this is your Subingredient <-> Organism+part+constituent
  -- 3521	7159
  -- 3522	7160
  -- 3724	13160
-- Remove the reference from the ORGANISM_PART_SUBINGREDIENTS table
delete from organism_part_subingredients where orgpart_id = 2496 and subingred_id = 2526;
commit;