create or replace force view
v_ingredient_organism_parts
as
with pt as (
-- with ingredient groups of parts
select i.ingred_id, op.org_grp_id, op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr
  from INGREDIENTS i,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt
 where i.org_grp_id = op.org_grp_id
   and op.orgparttype_id = opt.orgparttype_id
)
select unique -- DefinedOrganismSubstance (6067)
       o.organism_id, o.organism_name,
       op.org_grp_id org_group_id,
       og.org_grp_name_eng org_group_name_eng, og.org_grp_name_fr org_group_name_fr,
       ot.orgtype_id, ot.orgtype_name_eng, ot.orgtype_name_fr,
       op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       i.ingred_id,
       null family_id, null family_name,
       null species_id, null genus_species_name
  from INGREDIENTS i, 
       IN_ORG_GRP_ORGANISM iogo, 
       ORGANISM_PARTS op,
       ORGANISM_PART_SUBINGREDIENTS ops, -- define applicable set of parts
       ORGANISMS o, 
       ORGANISM_PART_TYPES opt, 
       ORGANISM_GROUPS og, 
       ORGANISM_TYPES ot
 where i.org_grp_id = iogo.org_grp_id
   and iogo.organism_id = op.organism_id
   and op.orgpart_id = ops.orgpart_id -- define applicable set of parts
   and op.organism_id = o.organism_id(+)
   and op.orgparttype_id = opt.orgparttype_id(+)
   and op.org_grp_id = og.org_grp_id(+)
   and o.orgtype_id = ot.orgtype_id(+)
 union all -- ChemicalSubstance (4035)
select unique o.organism_id, o.organism_name,
       op.org_grp_id org_group_id, null org_group_name_eng, null org_group_name_fr,
       ot.orgtype_id, ot.orgtype_name_eng, ot.orgtype_name_fr,
       op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       su.ingred_id,
       null family_id, null family_name,
       null species_id, null genus_species_name
  from SUBINGREDIENTS su,
       ORGANISM_PART_SUBINGREDIENTS ops,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt,
       ORGANISMS o,
       ORGANISM_TYPES ot
 where su.subingred_id = ops.subingred_id
   and ops.orgpart_id = op.orgpart_id
   and op.orgparttype_id = opt.orgparttype_id
   and op.organism_id = o.organism_id(+)
   and o.orgtype_id = ot.orgtype_id(+)
 union all -- ingredient families
select null organism_id, null organism_name,
       i.org_grp_id org_group_id,
       og.org_grp_name_eng org_group_name_eng, og.org_grp_name_fr org_group_name_fr,
       null orgtype_id, null orgtype_name_eng, null orgtype_name_fr,
       pt.orgpart_id, pt.orgparttype_id,
       pt.orgparttype_code, pt.orgparttype_name_eng, pt.orgparttype_name_fr,
       i.ingred_id,
       f.family_id, f.family_name,
       null species_id, null genus_species_name
  from INGREDIENTS i,
       IN_ORG_GRP_FAMILIES gf,
       FAMILIES f,
       ORGANISM_GROUPS og, pt
 where i.org_grp_id = gf.org_grp_id
   and gf.family_id = f.family_id
   and i.org_grp_id = og.org_grp_id(+)
   and i.ingred_id = pt.ingred_id(+)
   and i.org_grp_id = pt.org_grp_id(+)
 union all -- ingredient species
select null organism_id, null organism_name,
       i.org_grp_id org_group_id,
       og.org_grp_name_eng org_group_name_eng, og.org_grp_name_fr org_group_name_fr,
       null orgtype_id, null orgtype_name_eng, null orgtype_name_fr,
       pt.orgpart_id, pt.orgparttype_id,
       pt.orgparttype_code, pt.orgparttype_name_eng, pt.orgparttype_name_fr,
       i.ingred_id,
       null family_id, null family_name,
       s.species_id, g.genus_name||' '||s.species_name genus_species_name
  from INGREDIENTS i,
       IN_ORG_GRP_SPECIES gs,
       SPECIES s, GENERA g,
       ORGANISM_GROUPS og, pt
 where i.org_grp_id = gs.org_grp_id
   and gs.species_id = s.species_id and s.genus_id = g.genus_id
   and i.org_grp_id = og.org_grp_id(+)
   and i.ingred_id = pt.ingred_id(+)
   and i.org_grp_id = pt.org_grp_id(+)
 union all -- with sources (sub-ingredients?) groups of parts
select null, null,
       og.org_grp_id org_group_id,
       og.org_grp_name_eng org_group_name_eng, og.org_grp_name_fr org_group_name_fr,
       null, null, null,
       ps.orgpart_id, opt.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       s.ingred_id,
       null family_id, null family_name,
       sp.species_id, g.genus_name||' '||sp.species_name genus_species_name
  from SUBINGREDIENTS s,
       ORGANISM_PART_SUBINGREDIENTS ps,
       ORGANISM_PARTS op,
       ORGANISM_GROUPS og,
       ORGANISM_PART_TYPES opt,
       IN_ORG_GRP_SPECIES ogs, SPECIES sp, GENERA g -- vp fix NPW-64
 where 1=1
   and s.subingred_id = ps.subingred_id
   and ps.orgpart_id = op.orgpart_id
   and op.org_grp_id = og.org_grp_id
   and op.orgparttype_id = opt.orgparttype_id
   and op.org_grp_id = ogs.org_grp_id(+)
   and ogs.species_id = sp.species_id(+)
   and sp.genus_id = g.genus_id(+)
;
