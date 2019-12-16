create or replace force view 
v_roa_dosage_forms 
as
select ar.adminrt_code admin_route_code,
       df.dosage_form_syn_code,
       df.dosage_form_code,
       ar.adminrt_name_eng admin_route_name_eng, ar.adminrt_name_fr admin_route_name_fr,
       ar.adminrt_no_fixed_limits admin_route_no_fixed_limits,
       ar.adminrt_sterilerequired admin_route_sterile_required,
       df.dosage_form_name_eng,
       df.dosage_form_name_fr,
       rdf.min_age,
       rdf.dfu_min_age,
       u.units_code age_unit_code,
       ud.units_code dfu_age_unit_code,
       dfu.dfu_group_code,
       dfu.dfu_group_decode_eng dfu_decode_eng,
       dfu.dfu_group_decode_fr dfu_decode_fr,
       df.dosefrm_id, ar.adminrt_id
  from x$roa_dosage_form rdf,
       ADMINISTRATION_ROUTES ar,
       v_dosage_forms df,
       x$dfu_xref dfu,
       UNITS u, UNITS ud
 where rdf.admin_route_id = ar.adminrt_id
   and rdf.dosage_form_id = df.dosefrm_id
   and rdf.dfu_id = dfu.dfu_id(+)
   and rdf.age_unit_id = u.units_id(+)
   and rdf.dfu_age_unit_id = ud.units_id(+)
;
