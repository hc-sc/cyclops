create or replace force view 
v_mop_mi 
as
select --unique
       pm.prep_type_code,
       pm.prep_type_name_eng,
       pm.prep_type_name_fr,
       pm.qce_ratio_type,
       pm.potency_required,
       pm.extract_required,
       pm.solvent_list_id,
       pm.solvent_required,
       pm.prep_group_code,
       nc.ingred_name_type_code, nc.ingred_type_code, nc.nhp_class_code,
       pc.ingred_id,
       null source_id,
       pm.prep_type_id,
       pc.source_group_code,
       pc.nhpid_name ingred_name,
       pc.product_mono_code, pc.single_mono_code, pc.mono_code, pc.mono_id
  from x$ingredient_prep_code pc,
       v_mop pm,
       v_ingredient_type nc,
       v_ingredient_role rl,
       v_monographs m -- vp remove preps for monographs with pci = 'n'
 where 1=1
   and pc.prep_group_code = pm.prep_group_code
   and pc.ingred_id = nc.ingred_id
   and pc.ingred_id = rl.ingred_id
   and rl.rolespec_name_eng = 'Medicinal'
   and pc.mono_id = m.mono_id and m.pci = 'y'
union
select --unique
       ps.prep_type_code,
       ps.prep_type_name_eng,
       ps.prep_type_name_fr,
       ps.qce_ratio_type,
       ps.potency_required,
       ps.extract_required,
       ps.solvent_list_id,
       ps.solvent_required,
       ps.prep_group_code,
       nc.ingred_name_type_code, nc.ingred_type_code, nc.nhp_class_code,
       si.ingredient_id ingred_id,
       pc.ingred_id source_id,
       ps.prep_type_id,
       pc.source_group_code,
       pc.nhpid_name ingred_name,
       pc.product_mono_code, pc.single_mono_code, pc.mono_code, pc.mono_id
  from x$ingredient_prep_code pc,
       x$mono_source_ingredient si,
       v_mop ps,
       v_ingredient_type nc,
       v_ingredient_role rl,
       v_monographs m
 where 1=1
   and nvl(pc.product_mono_code,'x') = nvl(si.product_mono_code,'x')
   and pc.single_mono_code = si.mono_code
   and pc.ingred_id = si.source_id
   and pc.source_prep_code  = ps.prep_group_code
   and pc.ingred_id = nc.ingred_id
   and pc.ingred_id = rl.ingred_id
   and rl.rolespec_name_eng = 'Medicinal'
   and pc.mono_id = m.mono_id and m.pci = 'y'
union -- non compendial mop
select pt.preptype_code prep_type_code,
       pt.preptype_name_eng prep_type_name_eng, pt.preptype_name_fr prep_type_name_fr,
       pt.preptype_ratiotype qce_ratio_type,
       pt.preptype_standardized potency_required,
       pt.preptype_extract extract_required,
       ms.solvent_list_id,
       nvl2(s.solvents_id,'y',null) solvent_required,
       null prep_group_code,
       nc.ingred_name_type_code, nc.ingred_type_code, nc.nhp_class_code,
       i.ingred_id,
       null source_id,
       pt.preptype_id prep_type_id,
       null source_group_code,
       i.ingred_authorized_name_eng ingred_name,
       null product_mono_code, null single_mono_code, null mono_code, null mono_id
       --pt.preptype_visible,
  from INGREDIENT_PREPTYPES ip,
       PREPARATION_TYPES pt, --149
       PREPTYPES_SOLVENTS ps,
       SOLVENTS s,
       INGREDIENTS i,
       v_ingredient_type nc,
       v_ingredient_role rl,
       v_mop_solvents ms
 where 1=1
   and ip.preptype_id = pt.preptype_id
   and pt.preptype_id = ps.preptype_id(+)
   and ps.solvents_id = s.solvents_id(+)
   and ip.ingred_id = i.ingred_id
   and i.ingred_id = nc.ingred_id
   and i.ingred_id = rl.ingred_id
   and s.solvents_id = ms.solvent_id(+) and ms.solvent_list_id(+) > 1
;
