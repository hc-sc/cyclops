create or replace force view 
v_monograph_roa
as
select m.code mono_code,
       m.mono_name_eng, m.mono_name_fr,
       m.mono_publish_date,
       ar.adminrt_code admin_route_code,
       ar.adminrt_name_eng admin_route_name_eng, ar.adminrt_name_fr admin_route_name_fr,
       ar.adminrt_no_fixed_limits admin_route_no_fixed_limits,
       ar.adminrt_sterilerequired admin_route_sterile_required,
       m.mono_id, ar.adminrt_id,
       case when mf.product_mono_code is not null and mf.single_mono_code like '%-NM' then 'NM'
            when mf.product_mono_code is not null and mf.single_mono_code is not null then 'SP'
            else mf.mono_form_type
       end as mono_type_code
  from MONOGRAPH_ROUTEOFADMINS mr,
       ADMINISTRATION_ROUTES ar,
       MONOGRAPHS m,
       v_mono_form_types mf
where mr.adminrt_id = ar.adminrt_id
   and mr.monoadminrt_id = m.monoadminrt_id
    and m.mono_id = mf.mono_id(+)
union
select m.code mono_code,
       m.mono_name_eng, m.mono_name_fr,
       m.mono_publish_date,
       ar.adminrt_code admin_route_code,
       ar.adminrt_name_eng admin_route_name_eng, ar.adminrt_name_fr admin_route_name_fr,
       ar.adminrt_no_fixed_limits admin_route_no_fixed_limits,
       ar.adminrt_sterilerequired admin_route_sterile_required,
       m.mono_id, ar.adminrt_id,
       case when mf.product_mono_code is not null and mf.single_mono_code like '%-NM' then 'NM'
            when mf.product_mono_code is not null and mf.single_mono_code is not null then 'SP'
            else mf.mono_form_type
       end as mono_type_code
  from X$MONOGRAPH_ROA xmr,
       ADMINISTRATION_ROUTES ar,
       MONOGRAPHS m,
       v_mono_form_types mf
where xmr.adminrt_id = ar.adminrt_id
   and xmr.mono_id = m.mono_id
   and m.mono_id = mf.mono_id(+)
;
