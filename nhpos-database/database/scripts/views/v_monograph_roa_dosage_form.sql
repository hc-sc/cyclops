create or replace force view
v_monograph_roa_dosage_forms
as
select unique 
       mr.mono_code,
       df.admin_route_code,
       df.dosage_form_syn_code,
       df.dosage_form_code,
       mr.mono_name_eng,
       mr.mono_name_fr,
       mr.mono_publish_date,
       df.admin_route_name_eng,
       df.admin_route_name_fr,
       df.admin_route_no_fixed_limits,
       df.admin_route_sterile_required,
       df.dosage_form_name_eng,
       df.dosage_form_name_fr,
       df.dosefrm_id,
       df.adminrt_id,
       mr.mono_id
  from x$roa_dosage_form mrdf, 
       v_monograph_roa mr, 
       v_roa_dosage_forms df
 where mrdf.admin_route_id = mr.adminrt_id
   and mrdf.admin_route_id = df.adminrt_id
   and mrdf.dosage_form_id = df.dosefrm_id
;

--grant select on v_monograph_roa_dosage_forms to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_monograph_roa_dosage_forms for nhpdweb_dev.v_monograph_roa_dosage_forms;

