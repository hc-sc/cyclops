create or replace force view 
v_mop
as
select --unique
       pt.preptype_code prep_type_code,
       nvl(pm.unique_prep_description_eng,pt.preptype_name_eng) prep_type_name_eng, 
       nvl(pm.unique_prep_description_fr,pt.preptype_name_fr) prep_type_name_fr,
       pm.ratio_type qce_ratio_type,
       pm.potency potency_required,
       pm.extract extract_required,
       pm.solvents solvent_required,
       pm.solvent_list_id, --pm.solvent_group_code,
       pm.quantity_crude_equivalent,
       pm.prep_group_code,
       pm.prep_type_id, pm.prep_method_id
  from x$preparation_methods_xref pm,
       PREPARATION_TYPES pt
 where pm.prep_type_id = pt.preptype_id(+)
;
