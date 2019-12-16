create or replace view 
v_source_material_compend 
as
select x.product_mono_code,x.single_mono_code,x.mono_code,x.mono_id,
       x.ingred_nhpid_name_eng,x.ingred_nhpid_name_fr,x.ingredient_id,
       x.source_nhpid_name_eng,x.source_nhpid_name_fr,x.src_name_type_id,
       max(x.source_id) over (partition by x.mono_id,x.ingredient_id,x.source_nhpid_name_eng ) source_id, -- make source_id same number for same source name (i.e. org_group and org_type same name different IDs).
       x.organism_id,x.organism_name,x.organism_type_id,x.organism_type_name_eng,x.organism_type_name_fr,x.organism_group_id,
       x.species_id,x.species_name,x.family_id,x.family_name,
       x.source_material_part_id,x.source_material_part_type_code,x.source_material_part_name_eng,x.source_material_part_name_fr,
       x.source_material_strain,x.preparation_id,x.orgparttype_id
  from (
select -- 1 compendial sources - organisms with source_id
       q.product_mono_code,
       q.mono_code single_mono_code,
       q.mono_code_nhpid  mono_code,
       q.mono_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       i.ingred_id ingredient_id,
       case when x.organism_id is not null then q.organism_name
            when q.orgtype_id  is not null then q.orgtype_name_eng
            else s.ingred_authorized_name_eng
       end source_nhpid_name_eng,
       case when x.organism_id is not null then q.organism_name
            when q.orgtype_id  is not null then q.orgtype_name_fr
            else s.ingred_authorized_name_fr
       end source_nhpid_name_fr,
       case when x.organism_id is not null then x.organism_id
            when q.orgtype_id  is not null then q.orgtype_id
            else s.ingred_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
       s.ingredcat_id src_name_type_id,
       x.organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       q.source_org_group_id organism_group_id,
       --
       null species_id,
       null species_name,
       null family_id,
       null family_name,
       q.source_org_parttype_id source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       x.source_material_strain,
       p.prep_type_id preparation_id,
       x.orgparttype_id
  from v_source_organism_parts Q,
       x$organism_ingredient x,
       INGREDIENTS i,
       INGREDIENTS s,
       x$ingredient_prep_code p
 where 1=1
   and q.ingred_id = x.ingred_id
   and nvl(q.product_mono_code, 'x') = nvl(x.product_mono_code, 'x')
   and q.mono_code = x.mono_code
   and x.organism_id = q.source_org_id
   and x.orgparttype_id = q.source_org_parttype_id
   and q.ingred_id = i.ingred_id
   and q.source_ingred_id =  s.ingred_id(+)
   and nvl(q.source_org_part_id, x.orgparttype_id) is not null
   and q.mono_id = p.mono_id(+)
   and q.source_ingred_id = p.ingred_id(+)
 union
select -- 1.1 compendial sources - organisms w/o source_id
       m.product_mono_code,
       m.single_mono_code,
       m.mono_code,
       m.mono_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       m.ingredient_id,
       case when x.organism_id is not null then o.organism_name
            when o.orgtype_id  is not null then ot.orgtype_name_eng
            else i.ingred_authorized_name_eng
       end source_nhpid_name_eng,
       case when x.organism_id is not null then o.organism_name
            when o.orgtype_id  is not null then ot.orgtype_name_fr
            else i.ingred_authorized_name_fr
       end source_nhpid_name_fr,
       case when x.organism_id is not null then x.organism_id
            when o.orgtype_id  is not null then o.orgtype_id
            else x.ingred_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
       i.ingredcat_id src_name_type_id,
       x.organism_id,
       o.organism_name,
       o.orgtype_id organism_type_id,
       ot.orgtype_name_eng organism_type_name_eng,
       ot.orgtype_name_fr organism_type_name_fr,
       null  organism_group_id, --q.source_org_group_id organism_group_id,
       --
       null species_id,
       null species_name,
       null family_id,
       null family_name,
       x.orgparttype_id source_material_part_id,
       opt.orgparttype_code source_material_part_type_code,
       opt.orgparttype_name_eng source_material_part_name_eng,
       opt.orgparttype_name_fr source_material_part_name_fr,
       x.source_material_strain,
       p.prep_type_id preparation_id,
       x.orgparttype_id
  from v_mono_source_ingredient m,
       x$organism_ingredient x,
       ORGANISMS o,
       ORGANISM_TYPES ot,
       ORGANISM_PART_TYPES opt,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where 1=1
   and m.ingredient_id = x.ingred_id
   and nvl(m.product_mono_code, 'x') = nvl(x.product_mono_code, 'x')
   and m.single_mono_code = x.mono_code
   and m.source_id is null
   and x.organism_id = o.organism_id
   and o.orgtype_id = ot.orgtype_id
   and x.orgparttype_id = opt.orgparttype_id
   and m.ingredient_id = i.ingred_id
   and m.mono_id = p.mono_id(+)
   and m.ingredient_id = p.ingred_id(+)
 union
select -- 2 compendial sources - organism parts w/o organism
       x.product_mono_code,
       x.mono_code single_mono_code,
       mx.mono_code,
       mx.mono_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       x.ingred_id ingredient_id,
       case when x.organism_id is not null then q.organism_name
            --when q.source_org_group_id is not null then q.source_org_group_name_eng
            when x.org_group_id is not null then nvl(q.source_org_group_name_eng,x.org_group_name_eng)
            when q.orgtype_id  is not null then q.orgtype_name_eng
            when x.species_id  is not null then x.genus_species_name
            when x.family_id   is not null then x.family_name
            --when q.source_org_part_id is not null then q.source_org_parttype_name_eng
            --when x.orgparttype_id is not null then opt.orgparttype_name_eng
            else s.ingred_authorized_name_eng
       end source_nhpid_name_eng,
       case when x.organism_id is not null then q.organism_name
            --when q.source_org_group_id is not null then q.source_org_group_name_fr
            when x.org_group_id is not null then nvl(q.source_org_group_name_fr,x.org_group_name_fr)
            when q.orgtype_id  is not null then q.orgtype_name_fr
            when x.species_id  is not null then x.genus_species_name
            when x.family_id   is not null then x.family_name
            --when q.source_org_part_id is not null then q.source_org_parttype_name_fr
            --when x.orgparttype_id is not null then opt.orgparttype_name_fr
            else s.ingred_authorized_name_fr
       end source_nhpid_name_fr,
       case when x.organism_id is not null then x.organism_id
            --when q.source_org_group_id is not null then q.source_org_group_id
            when x.org_group_id is not null then nvl(q.source_org_group_id,x.org_group_id)
            when q.orgtype_id  is not null then q.orgtype_id
            when x.species_id  is not null then x.species_id
            when x.family_id   is not null then x.family_id
            --when q.source_org_part_id is not null then q.source_org_part_id
            --when x.orgparttype_id is not null then x.orgparttype_id
            else s.ingred_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
       s.ingredcat_id src_name_type_id,
       x.organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       q.source_org_group_id organism_group_id,
       --
       x.species_id,
       x.genus_species_name species_name,
       x.family_id,
       x.family_name,
       --
       nvl(q.source_org_part_id, x.orgparttype_id) source_material_part_id,
       nvl(q.source_org_parttype_code,opt.orgparttype_code) source_material_part_type_code,
       nvl(q.source_org_parttype_name_eng,opt.orgparttype_name_eng) source_material_part_name_eng,
       nvl(q.source_org_parttype_name_fr,opt.orgparttype_name_fr) source_material_part_name_fr,
       x.source_material_strain,
       p.prep_type_id preparation_id,
       nvl(q.source_org_part_id, x.orgparttype_id) orgparttype_id
  from v_mono_organism_ingredient x,
       v_source_organism_parts Q,
       INGREDIENTS i,
       INGREDIENTS s,
       ORGANISM_PART_TYPES opt,
       x$monograph_xref mx,
       --v_ingredient_organism_parts op,
       x$ingredient_prep_code p
 where 1=1
   and x.mono_code is not null
   and x.organism_id is null
   and (x.orgparttype_id is not null or x.orgtype_id is not null)
   --
   and nvl(x.product_mono_code,'x') = nvl(q.product_mono_code(+),'x')
   and x.mono_code = q.mono_code(+)
   and x.ingred_id = q.ingred_id(+)
   and x.orgparttype_id = q.source_org_parttype_id(+)
   and x.org_group_id = q.source_org_group_id(+)
   and q.source_org_id is null
   and x.ingred_id = i.ingred_id
   and x.source_id = s.ingred_id(+)
   and x.orgparttype_id = opt.orgparttype_id(+)
   and nvl(x.product_mono_code,'x') = nvl(mx.product_mono_code,'x')
   and x.mono_code = mx.single_mono_code
--   and q.ingred_id = op.ingred_id(+)
--   and q.source_org_part_id = op.orgpart_id(+)
--   and q.source_org_parttype_id  = op.orgparttype_id(+)
   and x.mono_code = p.single_mono_code(+)
   and x.source_id = p.ingred_id(+)
 union
select -- 3 compendial sources - organisms with parts
       mx.product_mono_code,
       mx.single_mono_code,
       mx.mono_code,
       mx.mono_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       i.ingred_id ingredient_id,
       case when q.source_org_id is not null then q.organism_name
            when q.orgtype_id  is not null then q.orgtype_name_eng
            else s.ingred_authorized_name_eng
       end source_nhpid_name_eng,
       case when q.source_org_id is not null then q.organism_name
            when q.orgtype_id  is not null then q.orgtype_name_fr
            else s.ingred_authorized_name_fr
       end source_nhpid_name_fr,
       case when q.source_org_id is not null then q.source_org_id
            when q.orgtype_id  is not null then q.orgtype_id
            else m.source_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
       s.ingredcat_id src_name_type_id,
       q.source_org_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       q.source_org_group_id organism_group_id,
       --
       null species_id,
       null species_name,
       null family_id,
       null family_name,
       --
       q.source_org_part_id source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       null preparation_id, -- due to source_ingred_id is null
       --(select p.prep_type_id from x$ingredient_prep_code p where p.mono_id = mx.mono_id and p.ingred_id = m.ingred_id) preparation_id,
       m.orgparttype_id
  from v_mono_organism_ingredient m,
       v_source_organism_parts Q,
       x$monograph_xref mx,
       INGREDIENTS i,
       INGREDIENTS s,
       ORGANISM_PART_TYPES opt
 where 1=1
   and m.mono_code is not null
   and nvl(m.product_mono_code,'x') = nvl(q.product_mono_code,'x')
   and m.mono_code = q.mono_code
   and m.ingred_id = q.ingred_id
   --and nvl(m.source_id,-1) = nvl(q.source_ingred_id,-1) -- ???
   and m.source_id is null --and q.source_ingred_id is null
   and m.organism_id = q.source_org_id
   and m.orgparttype_id = q.source_org_parttype_id --???
--   and q.source_org_part_id is not null
   and nvl(m.product_mono_code,'x') = nvl(mx.product_mono_code,'x')
   and m.mono_code = mx.single_mono_code
   and m.ingred_id = i.ingred_id
   and m.source_id = s.ingred_id(+)
   and m.orgparttype_id = opt.orgparttype_id(+)
union
select -- 4 compendial sources - non ingredient organisms
       unique
       q.product_mono_code,
       q.mono_code single_mono_code,
       q.mono_code_nhpid mono_code,
       q.mono_id,
       nvl(n.ingred_authorized_name_eng,m.ingred_authorized_name_eng) nhpid_ingred_name_eng,
       nvl(n.ingred_authorized_name_fr,m.ingred_authorized_name_fr) nhpid_ingred_name_fr,
       nvl(mi.ingred_id,q.ingred_id) ingredient_id,
--
       case when x.organism_id is not null then q.organism_name
            --when q.source_org_group_id is not null then q.source_org_group_name_eng
            when x.org_group_id is not null then q.source_org_group_name_eng
            when q.orgtype_id  is not null then q.orgtype_name_eng
            when x.species_id  is not null then x.genus_species_name
            when x.family_id   is not null then x.family_name
--            when q.source_org_part_id is not null then q.source_org_parttype_name_eng
--            when x.orgparttype_id is not null then q.source_org_parttype_name_eng
            else s.ingred_authorized_name_eng
       end source_nhpid_name_eng,
--       case when q.source_org_id is not null then q.organism_name
--            when q.source_org_group_id  is not null then q.source_org_group_name_eng
--            when q.orgtype_id  is not null then q.orgtype_name_eng
--            else s.ingred_authorized_name_eng
--       end source_nhpid_name_eng,
       case when x.organism_id is not null then q.organism_name
            --when q.source_org_group_id is not null then q.source_org_group_name_eng
            when x.org_group_id is not null then q.source_org_group_name_fr
            when q.orgtype_id  is not null then q.orgtype_name_fr
            when x.species_id  is not null then x.genus_species_name
            when x.family_id   is not null then x.family_name
--            when q.source_org_part_id is not null then q.source_org_parttype_name_fr
--            when x.orgparttype_id is not null then q.source_org_parttype_name_fr
            else s.ingred_authorized_name_fr
       end source_nhpid_name_fr,
--       case when q.source_org_id is not null then q.organism_name
--            when q.source_org_group_id  is not null then q.source_org_group_name_fr
--            when q.orgtype_id  is not null then q.orgtype_name_fr
--            else s.ingred_authorized_name_fr
--       end source_nhpid_name_fr,
       case when x.organism_id is not null then x.organism_id
            --when q.source_org_group_id is not null then q.source_org_group_name_eng
            when x.org_group_id is not null then x.org_group_id
            when q.orgtype_id  is not null then q.orgtype_id
            when x.species_id  is not null then x.species_id
            when x.family_id   is not null then x.family_id
--            when q.source_org_part_id is not null then q.source_org_part_id
--            when x.orgparttype_id is not null then x.orgparttype_id
            else q.source_ingred_id
       end source_id, -- vp unique source ID for non compendial WPLA-365
--       case when q.source_org_id is not null then q.source_org_id
--            when q.orgtype_id  is not null then q.orgtype_id
--            else q.source_ingred_id
--       end source_id, -- vp unique source ID for non compendial WPLA-365
       null src_ingred_cat_id,
       q.source_org_id organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       q.source_org_group_id organism_group_id,
       --
       null species_id,
       null species_name,
       null family_id,
       null family_name,
       --
       --nvl(q.source_org_part_id,q.source_org_parttype_id) source_material_part_id,
       q.source_org_parttype_id source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       p.prep_type_id preparation_id,
       q.source_org_parttype_id orgparttype_id
  from v_mono_organism_ingredient x,
       v_source_organism_parts Q,
       INGREDIENTS i,
       x$non_ingred_orgnsm_prep_code p,
       x$monograph_ingredients_xref mi,
       INGREDIENTS n,
       INGREDIENTS s,
       INGREDIENTS m
 where 1=1
   and x.mono_code is not null
   and nvl(x.product_mono_code,'x') = nvl(q.product_mono_code,'x')
   and x.mono_code = q.mono_code
   and x.ingred_id = q.ingred_id
--   and x.organism_id is null
--   and (x.orgparttype_id is not null or x.orgtype_id is not null)
--   --
   and x.orgparttype_id = q.source_org_parttype_id
   and x.org_group_id = q.source_org_group_id
   and q.source_org_id is null
--
   and lower(trim(q.organism_name)) = lower(trim(i.ingred_authorized_name_eng(+)))
   and i.ingred_id is null
   and lower(trim(q.organism_name)) = lower(trim(p.organism_name(+)))
   and nvl(q.product_mono_code,'x') = nvl(mi.product_mono_code(+),'x')
   and q.mono_code = mi.mono_code(+)
   and q.ingred_id = m.ingred_id(+)
   and mi.ingred_id = n.ingred_id(+)
   and q.source_ingred_id = s.ingred_id(+)
--) x where 1=1
--and x.mono_code_nhpid = 'FIS' and x.ingred_id = 4269
--and x.mono_code_nhpid = 'HCOL'
 union
select -- 5 compendial sources - orphan source organism types
       mx.product_mono_code,
       mx.mono_code,
       mx.mono_code_nhpid,
       mx.mono_id,
       i.ingred_authorized_name_eng nhpid_ingred_name_eng,
       i.ingred_authorized_name_fr nhpid_ingred_name_fr,
       mx.ingred_id_nhpid ingredient_id,
       ot.orgtype_name_eng nhpid_source_name_eng,
       nvl(ot.orgtype_name_fr,ot.orgtype_name_eng) nhpid_source_name_fr,
       mx.organism_type_id source_id, -- vp unique source ID for non compendial WPLA-365
       null src_ingred_cat_id,
       null,--q.source_org_id,
       null,--q.organism_name,
       mx.organism_type_id,
       ot.orgtype_name_eng organism_type_name_eng,
       ot.orgtype_name_fr organism_type_name_fr,
       null organism_group_id,
       --
       null species_id,
       null species_name,
       null family_id,
       null family_name,
       --
       mx.orgparttype_id source_material_part_id,
       mx.orgparttype_code source_material_part_type_code,
       mx.orgparttype_name_eng source_material_part_name_eng,
       mx.orgparttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       p.prep_type_id preparation_id,
       mx.orgparttype_id
  from x$orphan_source_organism_types mx,
       organism_types ot,
       x$non_ingred_orgnsm_prep_code p,
       INGREDIENTS i
 where 1=1
   and mx.organism_type_id = ot.orgtype_id
   and mx.ingred_id_nhpid = i.ingred_id(+)
   and mx.source_prep_code = p.source_prep_code(+)
) x where x.source_id is not null and x.source_nhpid_name_eng is not null
      and (ingredient_id,mono_id) not in (select sub_ingred_id,mono_id from x$monograph_sub_ingredient)
;
