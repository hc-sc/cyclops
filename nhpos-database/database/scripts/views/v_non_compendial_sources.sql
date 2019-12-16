create or replace force view
v_non_compendial_sources
as
select i.ingred_authorized_name_eng ingred_nhpid_name_eng, 
       i.ingred_authorized_name_fr ingred_nhpid_name_fr, 
       si.ingred_authorized_name_eng source_nhpid_name_eng, 
       si.ingred_authorized_name_fr source_nhpid_name_fr, 
       'Source Ingredient' source_type, 
       i.ingred_id ingredient_id, 
       si.ingred_id source_id, 
       si.ingredcat_id src_name_type_id, 
       null organism_id, 
       null organism_name, 
       null organism_type_id, 
       null organism_type_name_eng, 
       null organism_type_name_fr,
       null organism_group_id,
       null organism_group_name_eng,
       null organism_group_name_fr, 
       p.prep_type_id preparation_id,
       null source_material_part_id, 
       null source_material_part_type_code, 
       null source_material_part_name_eng, 
       null source_material_part_name_fr, 
       null source_material_strain, 
       null orgparttype_id
  from SUBINGREDIENTS s,
       INGREDIENT_SUBINGREDIENTS ins,
       INGREDIENTS si,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where 1=1
   and s.subingred_id = ins.subingred_id
   and ins.ingred_id = si.ingred_id
   and s.ingred_id = i.ingred_id
   and s.subingred_id not in (select subingred_id from ORGANISM_PART_SUBINGREDIENTS)
   and si.ingred_id = p.ingred_id(+)
 union
select i.ingred_authorized_name_eng ingred_nhpid_name_eng, 
       i.ingred_authorized_name_fr ingred_nhpid_name_fr, 
       i.ingred_authorized_name_eng source_nhpid_name_eng, 
       i.ingred_authorized_name_fr source_nhpid_name_fr, 
       'Source Material' source_type, 
       i.ingred_id ingredient_id, 
       i.ingred_id source_id, 
       i.ingredcat_id src_name_type_id, 
       t.organism_id, 
       t.organism_name, 
       t.orgtype_id organism_type_id, 
       t.orgtype_name_eng organism_type_name_eng, 
       t.orgtype_name_fr organism_type_name_fr, 
       t.org_group_id,
       t.org_group_name_eng,
       t.org_group_name_fr, 
       p.prep_type_id preparation_id,
       t.orgparttype_id source_material_part_id, 
       t.orgparttype_code source_material_part_type_code, 
       t.orgparttype_name_eng source_material_part_name_eng, 
       t.orgparttype_name_fr source_material_part_name_fr, 
       null source_material_strain, 
       t.orgparttype_id
  from v_ingredient_organism_parts t,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where t.ingred_id = i.ingred_id
   and t.ingred_id = p.ingred_id(+)
;
  
create or replace public synonym v_non_compendial_sources for nhpdweb_owner.v_non_compendial_sources;
grant select,references on v_non_compendial_sources to nhpdweb_buffer;
grant select on v_non_compendial_sources to nhpid_apex;
grant select on v_non_compendial_sources to rl__nhpdweb_admin;
grant select on v_non_compendial_sources to rl__nhpdweb_read;
grant select on v_non_compendial_sources to r_nhpd_ing_read;
grant select on v_non_compendial_sources to nhpdweb_user;

