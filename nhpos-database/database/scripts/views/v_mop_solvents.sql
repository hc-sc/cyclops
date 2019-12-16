create or replace view 
v_mop_solvents 
as
select unique
       --pm.prep_type_code, pm.prep_group_code, pm.unique_prep_code,
       vs.solvent_code, vs.solvent_name_eng, vs.solvent_name_fr,
       --pm.prep_method_id,
       sl.solvent_list_decode_eng,
       sl.solvent_list_decode_fr,
       sl.solvent_list_id, vs.solvent_id
  from --x$preparation_methods_xref pm,
       x$solvent_solvent_lists ssl,
       x$solvent_lists sl,
       v_solvents vs
 where 1=1
--   and pm.solvent_list_id = ssl.solvent_list_id
   and ssl.solvent_list_id = sl.solvent_list_id
   and ssl.solvent_id = vs.solvent_id
;
