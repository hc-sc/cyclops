-- --------------------
-- Removes a single ingredient from the Ingredient Database
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- JH: 2012-12-07: When deleting Magnesium Caprylate using the scripts below,
--                 other magnesium based ingredients no longer populate the
--                 epla.  I'm not sure why this is so, and I'm not likely to
--                 look into it anytime soon.
-- --------------------

-- Do a replace on the ingred_id to delete a different ingredient

select *
from ingredients
where ingred_id = 13461;

-- Removal

delete from subingredient_admin_routes
where subingred_id in (
	select subingred_id 
	from subingredients 
	where ingred_id = 13461);

delete from subingredient_refs
where subingred_id in (
	select subingred_id 
	from subingredients 
	where ingred_id = 13461);
	
delete from ingredient_subingredients
where subingred_id in (
	select subingred_id
	from subingredients
	where ingred_id = 13461
);	
	
delete from organism_part_subingredients
where subingred_id in (
	select subingred_id
	from subingredients
	where ingred_id = 13461
);	
	
delete from subingredients
where ingred_id = 13461;

delete from ingredient_nhpclassifications
where ingred_id = 13461;

delete from ingredient_qualsyno_qss
where ingred_id = 13461;

delete from ingredient_qualsyno_cns
where ingred_id = 13461;

delete from ingredient_qualsyno_pns
where ingred_id = 13461;

update ingredients 
set restrict_id = null 
where ingred_id = 13461;

-- delete from restriction_purposes;
delete from restriction_purposes
where restrict_id in (
	select restrict_id 
	from restrictions where ingredrole_id in (
		select ingredrole_id 
		from ingredient_roles 
		where ingred_id = 13461)
	);

-- delete from restriction_citations;
delete from restriction_citations 
where restrict_id in (
	select restrict_id 
	from restrictions where ingredrole_id in (
		select ingredrole_id 
		from ingredient_roles 
		where ingred_id = 13461)
	);

delete from restrictions 
where ingredrole_id in (
	select ingredrole_id 
	from ingredient_roles 
	where ingred_id = 13461);

-- delete from homeopathic_methofpreps;
-- delete from homeopathic_source_mat_hgts;
-- delete from homeopathic_formulas;

delete from ingredient_roles
where ingred_id = 13461;

-- delete from monograph_entries_preptypes;
-- delete from monograph_entries_preptypegrps;

-- delete from dietary_allowances;
-- delete from recommended_dietary_allowances;

delete from monograph_entries 
where ingred_id = 13461;

delete from ingredient_orgparts
where ingred_id = 13461;

delete from ingredient_preptypes
where ingred_id = 13461;

delete from ingredient_reg_entries
where ingred_id = 13461;

-- delete from homeopathic_common_name_hgts;

-- delete from homeopathic_proper_name_hgts;

delete from ingredients
where ingred_id = 13461;

--

commit;

