create or replace force view 
v_ingred_org_part_subingreds
as
select i.ingred_authorized_name_eng ingred_nhpid_name,--ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
-- no organism name and no monograph (non compendial)
       og.org_grp_name_eng,
       null organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng sub_ingred_nhpid_name_eng,--subing_authorized_name_eng,
       nm.ingred_authorized_name_fr sub_ingred_nhpid_name_fr,--subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingred_ingred_id,--sub_ingredient_ingred_id,
       null mono_id, null mono_code
  from INGREDIENTS i,
       ORGANISM_GROUPS og,
       ORGANISM_PARTS op,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       ORGANISM_PART_TYPES opt
 where 1=1
   and i.org_grp_id = og.org_grp_id
   and og.org_grp_id = op.org_grp_id
   and op.orgpart_id = ops.orgpart_id(+)
   and ops.subingred_id = si.subingred_id(+)
   and si.ingred_id = nm.ingred_id(+)
   and op.orgparttype_id = opt.orgparttype_id(+)
 union all
-- no organism name and existing monograph (compendial)
select i.ingred_authorized_name_eng ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       og.org_grp_name_eng,
       null organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng subing_authorized_name_eng,
       nm.ingred_authorized_name_fr subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingredient_ingred_id,
       m.mono_id, m.mono_code
  from INGREDIENTS i,
       ORGANISM_GROUPS og,
       ORGANISM_PARTS op,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       ORGANISM_PART_TYPES opt,
       x$monograph_sub_ingredient m
 where 1=1
   and i.org_grp_id = og.org_grp_id
   and og.org_grp_id = op.org_grp_id
   and op.orgpart_id = ops.orgpart_id
   and op.orgparttype_id = opt.orgparttype_id
   and ops.subingred_id = si.subingred_id
   and si.ingred_id = nm.ingred_id
   and nm.ingred_id = m.sub_ingred_id
   and m.ingred_id = i.ingred_id
--  and m.mono_code is not null
 union all
-- has organism name and ingredient organism group ID
select i.ingred_authorized_name_eng ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       og.org_grp_name_eng,
       o.organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng subing_authorized_name_eng,
       nm.ingred_authorized_name_fr subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingredient_ingred_id,
       null mono_id, null mono_code
  from INGREDIENTS i,
       ORGANISM_GROUPS og,--???
       IN_ORG_GRP_ORGANISM iogo,
       ORGANISMS o,
       ORGANISM_PARTS op,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       ORGANISM_PART_TYPES opt
 where 1=1
   and i.org_grp_id = iogo.org_grp_id
   and iogo.organism_id = o.organism_id(+)
   and iogo.organism_id = op.organism_id(+)
   and op.orgpart_id = ops.orgpart_id(+)
   and ops.subingred_id = si.subingred_id(+)
   and si.ingred_id = nm.ingred_id(+)
   and op.orgparttype_id = opt.orgparttype_id(+)
   and i.org_grp_id = og.org_grp_id(+)
 union all
select i.ingred_authorized_name_eng ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       og.org_grp_name_eng,
       o.organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng subing_authorized_name_eng,
       nm.ingred_authorized_name_fr subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingredient_ingred_id,
       m.mono_id, m.mono_code
  from INGREDIENTS i,
       ORGANISM_GROUPS og,--???
       IN_ORG_GRP_ORGANISM iogo,
       ORGANISMS o,
       ORGANISM_PARTS op,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       ORGANISM_PART_TYPES opt,
       x$monograph_sub_ingredient m
 where 1=1
   and i.org_grp_id = iogo.org_grp_id
   and iogo.organism_id = o.organism_id
   and iogo.organism_id = op.organism_id
   and op.orgpart_id = ops.orgpart_id
   and ops.subingred_id = si.subingred_id
   and si.ingred_id = nm.ingred_id
   and op.orgparttype_id = opt.orgparttype_id
   and i.org_grp_id = og.org_grp_id
   and nm.ingred_id = m.sub_ingred_id
   and m.ingred_id = i.ingred_id
 union all
-- has ingredient organism group ID and no organism name
select i.ingred_authorized_name_eng ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       og.org_grp_name_eng,
       null organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng subing_authorized_name_eng,
       nm.ingred_authorized_name_fr subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingredient_ingred_id,
       null, null
  from INGREDIENTS i,
       INGREDIENT_SUBINGREDIENTS isu,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       --
       ORGANISM_GROUPS og,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt
 where 1=1
   and isu.ingred_id = i.ingred_id
   and isu.subingred_id = si.subingred_id
   and si.ingred_id = nm.ingred_id
   --
   and i.org_grp_id = og.org_grp_id(+)
   and i.org_grp_id = op.org_grp_id(+)
   and op.orgparttype_id = opt.orgparttype_id(+)
 union all
select i.ingred_authorized_name_eng ingred_authorized_name,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       og.org_grp_name_eng,
       null organism_name,
       opt.orgparttype_code org_part_code,
       opt.orgparttype_name_eng org_part_name_eng,
       opt.orgparttype_name_fr  org_part_name_fr,
       nm.ingred_authorized_name_eng subing_authorized_name_eng,
       nm.ingred_authorized_name_fr subing_authorized_name_fr,
       i.ingred_id, nm.ingred_id sub_ingredient_ingred_id,
       m.mono_id, m.mono_code
  from INGREDIENTS i,
       INGREDIENT_SUBINGREDIENTS isu,
       SUBINGREDIENTS si,
       INGREDIENTS nm,
       --
       ORGANISM_GROUPS og,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt,
       x$monograph_sub_ingredient m
 where 1=1
   and isu.ingred_id = i.ingred_id
   and isu.subingred_id = si.subingred_id
   and si.ingred_id = nm.ingred_id
   --
   and i.org_grp_id = og.org_grp_id(+)
   and i.org_grp_id = op.org_grp_id(+)
   and op.orgparttype_id = opt.orgparttype_id(+)
   and nm.ingred_id = m.sub_ingred_id
   and  m.ingred_id = i.ingred_id
;

