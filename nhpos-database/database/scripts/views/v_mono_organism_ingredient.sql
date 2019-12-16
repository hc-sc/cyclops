create or replace view
v_mono_organism_ingredient
as
select unique 
       x.product_mono_code, x.mono_code,x.ingred_id, x.source_id,
       x.organism_id, x.orgtype_id, x.source_material_org_type, 
       x.orgparttype_id, x.source_material_strain,
       g.org_group_id, g.org_group_name_eng, g.org_group_name_fr,
       f.family_id, f.family_name,
       s.species_id, s.genus_species_name
  from x$organism_ingredient x,
       v_ingredient_organism_parts g,
       v_ingredient_organism_parts f,
       v_ingredient_organism_parts s
 where x.mono_code is not null
   and x.ingred_id = f.ingred_id(+) and x.source_material_org_type = f.family_name(+)
   and x.ingred_id = g.ingred_id(+) and x.source_material_org_type = g.org_group_name_eng(+)
   and x.ingred_id = g.ingred_id(+) and x.source_material_org_type = s.genus_species_name(+)
;
