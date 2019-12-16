create or replace force view 
v_assessment_dose_mop
as
select distinct
       x.prep_method_id,
       ad.assessment_dose_id,
       m.mono_code,
       m.product_mono_code,
       m.single_mono_code,
       --
       sp.prep_group_code,
       sp.unique_prep_code,
       sp.unique_prep_description_eng prep_group_decode_eng,
       sp.unique_prep_description_fr prep_group_decode_fr,
       pt.preptype_code prep_type_code,
       sp.potency, sp.extract, sp.ratio_type, sp.solvents,
       sl.solvent_list_decode_eng solvent_list,
       sp.quantity_crude_equivalent,
       pp.prep_type_code source_prep_type_code,
       sp.solvent_list_id, sp.prep_type_id,
       ad.ingredient_id, ad.sub_ingredient_id, ad.source_ingredient_id
  from x$assessment_dose_mop x,
       x$assessment_dose ad,
       x$monograph_xref m,
       x$preparation_methods_xref sp,
       PREPARATION_TYPES pt,
       x$solvent_lists sl,
       x$ingredient_prep_code fd, 
       x$prep_type_info_xref pp
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.prep_method_id = sp.prep_method_id
   and ad.monograph_id = m.mono_id
   and sp.prep_type_id = pt.preptype_id(+)
   and sp.solvent_list_id = sl.solvent_list_id(+)
   and ad.source_ingredient_id = fd.ingred_id(+)
   and ad.monograph_id = fd.mono_id(+)
   and fd.prep_type_id = pp.prep_type_id(+)
;

