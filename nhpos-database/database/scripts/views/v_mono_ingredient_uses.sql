create or replace force view 
v_mono_ingredient_uses 
as
select unique 
       m.code mono_code, 
       d.ingredient_id, 
       i.ingred_authorized_name_eng ingred_name_eng,
       sp.use_code, 
       sp.use_decode_eng, 
       sp.use_decode_fr,
       r.admin_route_code, 
       decode(instr(lower(sp.use_decode_eng),'traditional'),0,'General','Traditional') use_type
  from x$assessment_dose_use x,
       x$use_xref sp,
       x$assessment_dose d,
       MONOGRAPHS m,
       INGREDIENTS i,
       v_monograph_roa r
 where x.use_id = sp.use_id
   and d.assessment_dose_id = x.assessment_dose_id
   and d.monograph_id = m.mono_id
   and d.ingredient_id = i.ingred_id
   and d.monograph_id = r.mono_id(+)
   and d.roa_id = r.adminrt_id(+)
;
