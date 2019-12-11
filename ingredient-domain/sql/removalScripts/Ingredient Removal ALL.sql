-- --------------------
-- Ingredient Removal Script - All Ingredients 
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- --------------------

-- Removal

delete from subingredient_admin_routes;

delete from subingredient_refs;
	
delete from ingredient_subingredients;
	
delete from organism_part_subingredients;
	
delete from subingredients;

delete from ingredient_nhpclassifications;

delete from ingredient_qualsyno_qss;

delete from ingredient_qualsyno_cns;

delete from ingredient_qualsyno_pns;

update ingredients set restrict_id = null;

delete from restriction_purposes;

delete from restriction_citations;

delete from restrictions; 

delete from homeopathic_methofpreps;

delete from homeopathic_source_mat_hgts;

delete from homeopathic_formulas;

delete from ingredient_roles;

delete from monograph_entries_preptypes;

delete from monograph_entries_preptypegrps;

delete from dietary_allowances;

delete from recommended_dietary_allowances;

delete from monograph_entries; 

delete from ingredient_orgparts;

delete from ingredient_preptypes;

delete from ingredient_reg_entries;

delete from homeopathic_common_name_hgts;

delete from homeopathic_proper_name_hgts;

delete from ingredients;

--

commit;

