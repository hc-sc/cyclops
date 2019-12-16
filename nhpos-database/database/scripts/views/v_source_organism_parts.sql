create or replace force view 
v_source_organism_parts 
as
select -- organisms with mono_code, ingred_id, source_ingred_id and organism_id
       op.organism_name,
       op.orgtype_id,
       op.orgtype_name_eng,
       op.orgtype_name_fr,
       op.organism_id source_org_id,
       op.orgpart_id source_org_part_id,
       op.orgparttype_id source_org_parttype_id,
       op.org_group_id source_org_group_id,
       op.org_group_name_eng source_org_group_name_eng,
       op.org_group_name_fr source_org_group_name_fr, -- vp added groups
       --op.ingred_id, x.ingred_id source_ingred_id, -- vp case ingred_id = 1548
       mx.ingredient_id  ingred_id,
       mx.source_id source_ingred_id, -- vp case ingred_id = 1548
       op.orgparttype_code source_org_parttype_code,
       op.orgparttype_name_eng source_org_parttype_name_eng,
       op.orgparttype_name_fr source_org_parttype_name_fr,
       x.product_mono_code, x.mono_code, mx.mono_code_nhpid mono_code_nhpid, mx.monograph_id mono_id,
       x.source_material_strain, x.source_material_org_type -- vp added
  from x$organism_ingredient x,
       x$mono_source_ingredient mx,
       v_ingredient_organism_parts op
 where 1=1
   and nvl(x.product_mono_code,'x') = nvl(mx.product_mono_code,'x')
   and x.mono_code = mx.mono_code
   --and x.ingred_id = mx.ingredient_id
   and op.ingred_id = mx.source_id --ingredient_id -- vp case for ingred_id = 1548
   and op.organism_id = nvl(x.organism_id,op.organism_id) -- vp case for ingred_id = 1548
   and op.orgparttype_id = x.orgparttype_id
union --all
select -- organisms with both ingred_id and organism_id, no mono_code or source_ingred_id
       op.organism_name,
       op.orgtype_id,
       op.orgtype_name_eng,
       op.orgtype_name_fr,
       op.organism_id source_org_id,
       op.orgpart_id source_org_part_id,
       op.orgparttype_id source_org_parttype_id,
       op.org_group_id source_org_group_id,
       op.org_group_name_eng source_org_group_name_eng,
       op.org_group_name_fr source_org_group_name_fr, -- vp added groups
       op.ingred_id,
       xs.source_ingred_id,
       op.orgparttype_code source_org_parttype_code,
       op.orgparttype_name_eng source_org_parttype_name_eng,
       op.orgparttype_name_fr source_org_parttype_name_fr,
       null, null, null, null, --xs.product_mono_code, xs.single_mono_code, mx.mono_code, mx.mono_id
       null, null --x.source_material_strain, x.source_material_org_type
  from v_ingredient_organism_parts op,
       x$source_id_source_org_types xs
 where 1=1
   and op.orgtype_id = xs.orgtype_id
   and op.organism_id = xs.source_org_id
   and op.orgpart_id = xs.source_org_part_id
   and op.orgparttype_id = xs.source_org_parttype_id
   and op.ingred_id = xs.ingred_id
union
select -- organism parts species with both ingred_id and mono_code, w/o source_id
       op.organism_name,
       op.orgtype_id,
       op.orgtype_name_eng,
       op.orgtype_name_fr,
       op.organism_id source_org_id,
       op.orgpart_id source_org_part_id,
       op.orgparttype_id source_org_parttype_id,
       op.org_group_id source_org_group_id,
       op.org_group_name_eng source_org_group_name_eng,
       op.org_group_name_fr source_org_group_name_fr, -- vp added groups
       op.ingred_id,
       null source_ingred_id,
       op.orgparttype_code source_org_parttype_code,
       op.orgparttype_name_eng source_org_parttype_name_eng,
       op.orgparttype_name_fr source_org_parttype_name_fr,
       m.product_mono_code, m.single_mono_code, m.mono_code, m.mono_id,
       null, null --x.source_material_strain, x.source_material_org_type
  from v_ingredient_organism_parts op,
       x$organism_ingredient oi,
       x$monograph_xref m
 where 1=1
   and op.ingred_id = oi.ingred_id
   and oi.source_id is null
   and nvl(oi.product_mono_code,'x') = nvl(m.product_mono_code,'x')
   and oi.mono_code = m.single_mono_code
union
select -- organism types as a sources
       null o_organism_name,
       nvl(ot.orgtype_id,x.source_organism_type_id), -- vp fix orgtype_id
       nvl(x.source_material_org_type,ot.orgtype_name_eng) orgtype_name_eng,
       nvl(x.source_material_org_type,ot.orgtype_name_fr) orgtype_name_fr,
       null op_organism_id,-- source_org_id,
       null op_orgpart_id,-- source_org_part_id,
       opt.orgparttype_id source_org_parttype_id,
       null source_org_group_id,
       null source_org_group_name_eng,
       null source_org_group_name_fr, -- vp added groups
       null su_ingred_id, -- sub-ingredient ingred_id
       x.source_id, --x.ingred_id source_ingred_id,
       opt.orgparttype_code source_org_parttype_code,
       opt.orgparttype_name_eng source_org_parttype_name_eng,
       opt.orgparttype_name_fr source_org_parttype_name_fr,
       x.product_mono_code, x.single_mono_code, --mx.mono_code, mx.mono_id,
       x.mono_code, x.mono_id, null, x.source_material_org_type
  from x$source_organism_types x,
       ORGANISM_TYPES ot,
       ORGANISM_PART_TYPES opt,
       x$monograph_ingredients_xref mx
 where 1=1
   and x.orgtype_id = ot.orgtype_id(+)
   and x.orgparttype_id = opt.orgparttype_id(+)
   and nvl(x.product_mono_code,'x') = nvl(mx.product_mono_code(+),'x')
   and x.single_mono_code = mx.mono_code(+)
union
select -- organism groups as a sources
       null o_organism_name,
       ot.orgtype_id,
       ot.orgtype_name_eng,
       ot.orgtype_name_fr,
       op.organism_id,-- source_org_id,
       op.orgpart_id,-- source_org_part_id,
       opt.orgparttype_id source_org_parttype_id,
       og.org_grp_id source_org_group_id,
       og.org_grp_name_eng source_org_group_name_eng,
       og.org_grp_name_fr source_org_group_name_fr, -- vp added groups
       null su_ingred_id, -- sub-ingredient ingred_id
       x.source_id, --x.ingred_id source_ingred_id,
       opt.orgparttype_code source_org_parttype_code,
       opt.orgparttype_name_eng source_org_parttype_name_eng,
       opt.orgparttype_name_fr source_org_parttype_name_fr,
       x.product_mono_code, x.single_mono_code, x.mono_code, x.mono_id,
       null, x.source_material_org_type
  from x$source_organism_types x,
       ORGANISM_GROUPS og,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt,
       ORGANISM_TYPES ot
 where 1=1
   and x.source_id is null and x.organism_name is null
   and lower(trim(x.source_nhpid_name)) = lower(trim(og.org_grp_name_eng))
   and x.orgparttype_id = opt.orgparttype_id
   and x.orgparttype_id = op.orgparttype_id
   and og.org_grp_id = op.org_grp_id
   and x.orgtype_id = ot.orgtype_id(+)
;
