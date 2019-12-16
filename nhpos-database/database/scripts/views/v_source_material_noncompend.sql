create or replace view 
v_source_material_noncompend 
as
select --x.product_mono_code,x.single_mono_code,x.mono_code,x.mono_id,
       x.ingred_nhpid_name_eng,x.ingred_nhpid_name_fr,x.ingredient_id,
       x.source_nhpid_name_eng,x.source_nhpid_name_fr,x.src_name_type_id,
       max(x.source_id) over (partition by x.ingredient_id,x.source_nhpid_name_eng ) source_id, -- make source_id same number for same source name (i.e. org_group and org_type same name different IDs).
       x.organism_id,x.organism_name,x.organism_type_id,x.organism_type_name_eng,x.organism_type_name_fr,x.organism_group_id,
       x.species_id,x.species_name,x.family_id,x.family_name,
       x.source_material_part_id,x.source_material_part_type_code,x.source_material_part_name_eng,x.source_material_part_name_fr,
       x.source_material_strain,x.preparation_id,x.orgparttype_id
  from (
select -- 6 non compendial sources
--       null product_mono_code,
--       null single_mono_code,
--       null mono_code,
--       null mono_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       i.ingred_id ingredient_id,
       case when t.organism_id is not null then t.organism_name
            when t.orgtype_id  is not null then t.orgtype_name_eng
            when t.org_group_id is not null then t.org_group_name_eng
            when t.species_id  is not null then t.genus_species_name
            when t.family_id   is not null then t.family_name
            --when t.orgparttype_id is not null then t.orgparttype_name_eng
            else i.ingred_authorized_name_eng
       end source_nhpid_name_eng,
       case when t.organism_id is not null then t.organism_name
            when t.orgtype_id  is not null then t.orgtype_name_fr
            when t.org_group_id is not null then t.org_group_name_fr
            when t.species_id  is not null then t.genus_species_name
            when t.family_id   is not null then t.family_name
            --when t.orgparttype_id is not null then t.orgparttype_name_fr
            else i.ingred_authorized_name_fr
       end source_nhpid_name_fr,
       i.ingredcat_id src_name_type_id,
       case when t.organism_id is not null then t.organism_id
            when t.orgtype_id  is not null then t.orgtype_id
            when t.org_group_id is not null then t.org_group_id
            when t.species_id  is not null then t.species_id
            when t.family_id   is not null then t.family_id
            --when t.orgparttype_id is not null then t.orgparttype_id
            else i.ingred_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
       t.organism_id, 
       t.organism_name,
       t.orgtype_id organism_type_id, 
       t.orgtype_name_eng organism_type_name_eng, 
       t.orgtype_name_fr organism_type_name_fr,
       t.org_group_id organism_group_id,
       --
       t.species_id, 
       t.genus_species_name species_name,
       t.family_id, 
       t.family_name,
       --
       t.orgparttype_id source_material_part_id,
       t.orgparttype_code source_material_part_type_code,
       t.orgparttype_name_eng source_material_part_name_eng,
       t.orgparttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       p.prep_type_id preparation_id,
       t.orgparttype_id
  from v_ingredient_organism_parts t,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where t.ingred_id = i.ingred_id
   and t.ingred_id = p.ingred_id(+)
) x where x.source_id is not null and x.source_nhpid_name_eng is not null
;
