-----------------------------------
--patch #13
-- date: 2019/10/08
-- fixes: V_ALL_SOURCES, V_MONO_SOURCE_INGREDIENT, V_MONOGRAPHS
-- log: patch_13.log
----------------------------------
spool patch_13.log
prompt Patch 13
prompt update ORGANISM_PARTS missing organisms

insert all 
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2136, 5221, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2191, 5222, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2133, 5223, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2132, 5224, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2130, 5225, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2134, 5226, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2131, 5227, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2135, 5228, 266, null, sysdate, sysdate, 2)
into ORGANISM_PARTS (ORGANISM_ID, ORGPART_ID, ORGPARTTYPE_ID, ORG_GRP_ID, ORGPART_LASTUPDATE_DATE, ORGPART_CREATION_DATE, USERACC_ID)
values (2190, 5229, 266, null, sysdate, sysdate, 2)
select 1 from dual;
commit;

prompt create view V_INGREDIENT_ORGANISM_PARTS
create or replace force view
v_ingredient_organism_parts
as
select o.organism_id, o.organism_name,
       ot.orgtype_id, ot.orgtype_name_eng, ot.orgtype_name_fr,
       op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       su.ingred_id
  from ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS su,
       ORGANISMS o,
       ORGANISM_TYPES ot
 where 1=1
   and op.orgparttype_id = opt.orgparttype_id
   and op.organism_id = o.organism_id
   and o.orgtype_id = ot.orgtype_id
   and op.orgpart_id = ops.orgpart_id
   and ops.subingred_id = su.subingred_id
 union all
select null, null,
       null, null, null,
       op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       i.ingred_id
  from INGREDIENTS i, 
       ORGANISM_PARTS op, 
       ORGANISM_PART_TYPES opt
 where i.org_grp_id is not null
   and i.org_grp_id = op.org_grp_id
   and op.orgparttype_id = opt.orgparttype_id
 union all
select o.organism_id, o.organism_name,
       ot.orgtype_id, ot.orgtype_name_eng, ot.orgtype_name_fr,
       op.orgpart_id, op.orgparttype_id,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       i.ingred_id
  from INGREDIENTS i, 
       IN_ORG_GRP_ORGANISM iog, 
       ORGANISMS o,
       ORGANISM_TYPES ot,
       ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt
 where i.org_grp_id is not null
   and i.org_grp_id = iog.org_grp_id
   and iog.organism_id = o.organism_id
   and o.orgtype_id = ot.orgtype_id
   and o.organism_id = op.organism_id
   and op.orgparttype_id = opt.orgparttype_id
;

prompt create view V_MONO_SOURCE_INGREDIENT 
create or replace force view 
v_mono_source_ingredient 
as
select unique
       m.code mono_code, m.mono_name_eng, m.mono_name_fr,
       msi.product_mono_code, msi.mono_code single_mono_code,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng, i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       s.ingred_authorized_name_eng source_nhpid_name_eng, s.ingred_authorized_name_fr source_nhpid_name_fr,
       'Source '||nvl2(x.organism_id,'Material','Ingredient') source_type,
       msi.monograph_id mono_id, msi.ingredient_id, msi.source_id,
       s.ingredcat_id src_name_type_id
       --x.organism_id, x.orgparttype_id, x.source_material_strain
  from x$mono_source_ingredient msi,
       MONOGRAPHS m,
       INGREDIENTS i,
       INGREDIENTS s,
       x$organism_ingredient x
 where 1=1
   and msi.monograph_id = m.mono_id
   and msi.ingredient_id = i.ingred_id
   and msi.source_id = s.ingred_id(+)
   and nvl(msi.source_id, msi.ingredient_id) = x.ingred_id(+)
;

prompt create view V_ALL_SOURCES 
create or replace force view 
v_all_sources
as
with q as ( -- sources of organism type i.e. "source material"
select -- organisms with mono_code, ingred_id, source_ingred_id and organism_id
       op.organism_name, op.orgtype_id, op.orgtype_name_eng, op.orgtype_name_fr,
       op.organism_id source_org_id, op.orgpart_id source_org_part_id, op.orgparttype_id source_org_parttype_id,
       op.ingred_id, x.ingred_id source_ingred_id,
       op.orgparttype_code source_org_parttype_code,
       op.orgparttype_name_eng source_org_parttype_name_eng, op.orgparttype_name_fr source_org_parttype_name_fr,
       x.product_mono_code, x.mono_code, mx.mono_code_nhpid mono_code_nhpid, mx.monograph_id mono_id,
       x.source_material_strain
  from v_ingredient_organism_parts op,
       x$organism_ingredient x,
       x$mono_source_ingredient mx
 where 1=1
   and op.organism_id = x.organism_id
   and lower(trim(nvl(op.orgparttype_name_eng,'x'))) = lower(trim(nvl(x.source_material_part,'x')))
   and op.orgparttype_id = x.orgparttype_id
   and op.ingred_id = mx.ingredient_id
   and nvl(x.product_mono_code,'x') = nvl(mx.product_mono_code,'x')
   and x.mono_code = mx.mono_code
union --all
select -- organisms with both ingred_id and organism_id, no mono_code or source_ingred_id
       op.organism_name, op.orgtype_id, op.orgtype_name_eng, op.orgtype_name_fr,
       op.organism_id source_org_id, op.orgpart_id source_org_part_id, op.orgparttype_id source_org_parttype_id,
       op.ingred_id, xs.source_ingred_id,
       op.orgparttype_code source_org_parttype_code,
       op.orgparttype_name_eng source_org_parttype_name_eng, op.orgparttype_name_fr source_org_parttype_name_fr,
       null, null, null, null, null
       --xs.product_mono_code, xs.single_mono_code, mx.mono_code, mx.mono_id
  from v_ingredient_organism_parts op,
       x$source_id_source_org_types xs
 where 1=1
   and op.orgtype_id = xs.orgtype_id
   and op.organism_id = xs.source_org_id
   and op.orgpart_id = xs.source_org_part_id
   and op.orgparttype_id = xs.source_org_parttype_id
   and op.ingred_id = xs.ingred_id
union
select -- organism types as a sources
       null o_organism_name,
       ot.orgtype_id,
       nvl(x.source_material_org_type,ot.orgtype_name_eng) orgtype_name_eng, 
       nvl(x.source_material_org_type,ot.orgtype_name_fr) orgtype_name_fr, 
       null op_organism_id,-- source_org_id,
       null op_orgpart_id,-- source_org_part_id,
       opt.orgparttype_id source_org_parttype_id,
       null su_ingred_id, -- sub-ingredient ingred_id
       x.source_id, --x.ingred_id source_ingred_id,
       opt.orgparttype_code source_org_parttype_code,
       opt.orgparttype_name_eng source_org_parttype_name_eng,
       opt.orgparttype_name_fr source_org_parttype_name_fr,
       x.product_mono_code, x.single_mono_code, mx.mono_code, mx.mono_id,
       null
  from x$source_organism_types x,
       ORGANISM_TYPES ot,
       ORGANISM_PART_TYPES opt,
       x$monograph_ingredients_xref mx
 where 1=1
   and x.orgtype_id = ot.orgtype_id(+)
   and x.orgparttype_id = opt.orgparttype_id(+)
   and nvl(x.product_mono_code,'x') = nvl(mx.product_mono_code(+),'x')
   and x.single_mono_code = mx.mono_code(+)
)
-- all sources from monographs
select --unique
       m.ingred_nhpid_name_eng,
       m.ingred_nhpid_name_fr,
       m.source_nhpid_name_eng,
       m.source_nhpid_name_fr,
       m.product_mono_code,
       m.single_mono_code,
       m.mono_code,
       m.source_type,
       m.mono_id,
       m.ingredient_id,
       m.source_id,
       m.src_name_type_id,
       x.organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       p.prep_type_id preparation_id,
       nvl(q.source_org_part_id, x.orgparttype_id) source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       x.source_material_strain,
       x.orgparttype_id
  from v_mono_source_ingredient m,
       x$organism_ingredient x,
       Q, x$ingredient_prep_code p
 where 1=1
   and m.ingredient_id = x.ingred_id
   and nvl(m.product_mono_code, 'x') = nvl(x.product_mono_code, 'x')
   and m.single_mono_code = x.mono_code
   and m.ingredient_id = q.ingred_id
   and m.source_id =  q.source_ingred_id
   and x.organism_id = q.source_org_id
   and x.orgparttype_id = q.source_org_parttype_id
--   and x.ingred_id = q.ingred_id
   and m.source_id = p.ingred_id(+)
 union
select --unique
       m.ingred_nhpid_name_eng,
       m.ingred_nhpid_name_fr,
       m.source_nhpid_name_eng,
       m.source_nhpid_name_fr,
       m.product_mono_code,
       m.single_mono_code,
       m.mono_code,
       m.source_type,
       m.mono_id,
       m.ingredient_id,
       m.source_id,
       m.src_name_type_id,
       x.organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       p.prep_type_id preparation_id,
       nvl(q.source_org_part_id, x.orgparttype_id) source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       x.source_material_strain,
       x.orgparttype_id
  from v_mono_source_ingredient m,
       x$organism_ingredient x,
       Q, x$ingredient_prep_code p
 where 1=1
   and m.ingredient_id = x.ingred_id
   and nvl(m.product_mono_code,'x') = nvl(x.product_mono_code,'x')
   and m.single_mono_code = x.mono_code
   and x.organism_id is null
   and x.orgparttype_id= q.source_org_parttype_id(+)
--   and q.source_ingred_id is null
   and q.orgtype_id is null
   and m.source_id = p.ingred_id(+)
 union --all
select --unique
       m.ingred_nhpid_name_eng,
       m.ingred_nhpid_name_fr,
       m.source_nhpid_name_eng,
       m.source_nhpid_name_fr,
       m.product_mono_code,
       m.single_mono_code,
       m.mono_code,
       m.source_type,
       m.mono_id,
       m.ingredient_id,
       m.source_id,
       t.src_name_type_id,
       q.source_org_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       p.prep_type_id preparation_id,
       q.source_org_part_id source_material_part_id,
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       q.source_org_parttype_id orgparttype_id
  from v_mono_source_ingredient t,
       v_mono_source_ingredient m,
       q, x$ingredient_prep_code p
 where 1=1
   and t.ingredient_id = m.ingredient_id
   and t.source_id = m.source_id
   and m.ingredient_id = q.ingred_id
   and m.source_id = p.ingred_id(+)
 union --all
-- non monograph sources of type "source ingredient"
select i.ingred_authorized_name_eng,
       i.ingred_authorized_name_fr,
       ing.ingred_authorized_name_eng,
       ing.ingred_authorized_name_fr,
       null product_mono_code,
       null mono_code,
       null mono_code_nhpid,
       'Source Ingredient',
       null mono_id,
       subs.ingred_id,
       ing.ingred_id,
       ing.ingredcat_id,
       null organism_id,
       null organism_name,
       null organism_type_id,
       null organism_type_name_eng,
       null organism_type_name_fr,
       p.prep_type_id preparation_id,
       null source_material_part_id,
       null source_org_parttype_code,
       null source_material_part_name_eng,
       null source_material_part_name_fr,
       null source_material_strain,
       null orgparttype_id
  from INGREDIENTS ing,
       INGREDIENT_SUBINGREDIENTS ingsub,
       SUBINGREDIENTS subs,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where ing.ingred_id = ingsub.ingred_id
   and ingsub.subingred_id = subs.subingred_id
   and ing.ingredspec_class_name = 'ChemicalSubstance'
   and i.ingred_id = subs.ingred_id
   and ing.ingred_id = p.ingred_id(+)
union --all
-- non ingredient organisms
select --unique
       n.ingred_authorized_name_eng nhpid_ingred_name_eng,
       n.ingred_authorized_name_fr nhpid_ingred_name_fr,
       null nhpid_source_name_eng,
       null nhpid_source_name_fr,
       q.product_mono_code,
       q.mono_code,
       q.mono_code_nhpid,
       'Source Material' source_type,
       q.mono_id,
       nvl(q.ingred_id,mi.ingred_id) ingred_id,--null ingredient_id,
       q.source_ingred_id source_id,
       null src_ingred_cat_id,
       q.source_org_id organism_id,
       q.organism_name,
       q.orgtype_id organism_type_id,
       q.orgtype_name_eng organism_type_name_eng,
       q.orgtype_name_fr organism_type_name_fr,
       p.prep_type_id preparation_id,
       --q.source_org_part_id source_material_part_id,
       nvl(q.source_org_part_id,q.source_org_parttype_id) source_material_part_id, -- artificial ID substitution
       q.source_org_parttype_code source_material_part_type_code,
       q.source_org_parttype_name_eng source_material_part_name_eng,
       q.source_org_parttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       q.source_org_parttype_id orgparttype_id
  from INGREDIENTS i,
       Q, x$non_ingred_orgnsm_prep_code p,
       x$monograph_ingredients_xref mi,
       INGREDIENTS n
 where lower(trim(q.organism_name)) = lower(trim(i.ingred_authorized_name_eng(+)))
   and i.ingred_id is null
   and lower(trim(q.organism_name)) = lower(trim(p.organism_name(+)))
   and nvl(q.product_mono_code,'x') = nvl(mi.product_mono_code(+),'x')
   and q.mono_code = mi.mono_code(+)
   and mi.ingred_id = n.ingred_id(+)
union --all
-- orphan source organism types
select --unique
       i.ingred_authorized_name_eng nhpid_ingred_name_eng,
       i.ingred_authorized_name_fr nhpid_ingred_name_fr,
       null nhpid_source_name_eng,
       null nhpid_source_name_fr,
       mx.product_mono_code,
       mx.mono_code,
       mx.mono_code_nhpid,
       'Source Material' source_type,
       mx.mono_id,
       mx.ingred_id_nhpid ingredient_id,
       null source_id,
       null src_ingred_cat_id,
       null,--q.source_org_id,
       null,--q.organism_name,
       mx.organism_type_id,
       ot.orgtype_name_eng organism_type_name_eng,
       ot.orgtype_name_fr organism_type_name_fr,
       p.prep_type_id preparation_id,
       mx.orgparttype_id source_material_part_id,
       mx.orgparttype_code source_material_part_type_code,
       mx.orgparttype_name_eng source_material_part_name_eng,
       mx.orgparttype_name_fr source_material_part_name_fr,
       null source_material_strain,
       mx.orgparttype_id
  from x$orphan_source_organism_types mx,
       organism_types ot,
       x$non_ingred_orgnsm_prep_code p,
       INGREDIENTS i
 where 1=1
   and mx.organism_type_id = ot.orgtype_id
   and mx.ingred_id_nhpid = i.ingred_id(+)
   and mx.source_prep_code = p.source_prep_code(+)
;

prompt create view V_MONOGRAPHS 
create or replace force view 
v_monographs 
as 
select distinct
       m.code mono_code,
       mf.single_mono_code,
       mf.product_mono_code,
       m.mono_name_eng, m.mono_name_fr,
       m.mono_publish_date,
       case when mf.product_mono_code is not null and mf.single_mono_code like '%-NM' then 'NM'
            when mf.product_mono_code is not null and mf.single_mono_code is not null then 'SP'
            else mf.mono_form_type
       end as mono_type_code,
       case when m.code in ('FLWRESS','GEMMO','LITHO','NOS','OLIGO','ORGANO') then 'n'
            when mf.mono_form_type in ('P','S','H','SS') then 'y' 
            else 'n' end pci,
       nvl(mr.admin_route_sterile_required,'n') sterile_required,
       m.mono_id
  from MONOGRAPHS m,
       v_mono_form_types mf,
       v_monograph_roa mr
 where 1=1
   and m.mono_id = mf.mono_id(+)
   and m.mono_id = mr.mono_id(+)
union all
select --unique
       x.mono_code,
       x.single_mono_code,
       x.product_mono_code,
       x.mono_name_eng, null mono_name_fr,
       null mono_publish_date,
       case when x.product_mono_code is not null and x.single_mono_code like '%-NM' then 'NM'
            when x.mono_code = 'DECOMISSIONED' then 'D'
            when x.mono_code like 'SBPR%' then 'SS'
            when x.product_mono_code is not null and x.single_mono_code is null then 'P'
            when x.product_mono_code is null and x.single_mono_code is not null then 'S'
            when x.product_mono_code is not null and x.single_mono_code is not null then 'SP'
            else '?'
       end as mono_type_code,
       'n' pci,
       nvl(mr.admin_route_sterile_required,'n') sterile_required,
       mr.mono_id
  from x$monograph_xref x,
       v_monograph_roa mr
where x.mono_id is null
   and x.mono_code = mr.mono_code(+)
;

prompt create view V_ASSESSMENT_DOSE_MOP
create or replace force view 
v_assessment_dose_mop
as
select distinct 
       x.prep_method_id,
       ad.assessment_dose_id,
       ad.mono_code,
       ad.product_mono_code,
       ad.single_mono_code,
       --
       sp.prep_group_code,
       sp.unique_prep_code,
       sp.unique_prep_description_eng prep_group_decode_eng,
       sp.unique_prep_description_fr prep_group_decode_fr,
       pt.preptype_code prep_type_code,
       sp.potency, sp.extract, sp.ratio_type, sp.solvents, 
       sl.solvent_list_decode_eng solvent_list, 
       sp.quantity_crude_equivalent, 
       sp.solvent_list_id, sp.prep_type_id,
       ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_mop x,
       v_assessment_doses ad,
       x$preparation_methods_xref sp,
       PREPARATION_TYPES pt,
       x$solvent_lists sl
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.prep_method_id = sp.prep_method_id
   and sp.prep_type_id = pt.preptype_id(+)
   and sp.solvent_list_id = sl.solvent_list_id
;

exec dbms_utility.compile_schema(user);

select object_name, object_type from user objects where status = 'INVALID';

spool off
