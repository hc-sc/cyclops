create or replace force view
v_pci_roa
as
select p.mono_code, 
       p.mono_name_eng, p.mono_name_fr, 
       p.mono_type_code, 
       --p.mono_type_name_eng, p.mono_type_name_fr, 
       p.mono_publish_date,
       r.admin_route_code, 
       r.admin_route_name_eng, r.admin_route_name_fr,
       r.admin_route_no_fixed_limits, 
       r.admin_route_sterile_required,
       p.mono_id, 
       r.adminrt_id
  from V_MONOGRAPHS p, V_MONOGRAPH_ROA r
 where p.mono_id = r.mono_id
   and p.pci = 'y'
;

--grant select on v_pci_roa to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_pci_roa for nhpdweb_dev.v_pci_roa;

