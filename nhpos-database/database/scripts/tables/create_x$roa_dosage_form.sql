truncate  table x$roa_dosage_form;
insert into x$roa_dosage_form
(admin_route_id, dosage_form_id, dfu_id, min_age, age_unit_code, 
 age_unit_id, dfu_min_age, dfu_age_unit_code, dfu_age_unit_id,
 useracc_id, creation_date)
with df as (
select a.adminrt_code admin_route_code, 
       s.dosage_form_syn_code,
       d.dosefrm_code dosage_form_code,
       a.adminrt_name_eng admin_route_name_eng,
       a.adminrt_name_fr admin_route_name_fr,
       a.adminrt_no_fixed_limits admin_route_no_fixed_limits, 
       a.adminrt_sterilerequired admin_route_sterile_required,
       d.dosefrm_name_eng dosage_form_name_eng, 
       d.dosefrm_name_fr dosage_form_name_fr,
       d.dosefrm_id, a.adminrt_id
  from DOSAGE_FORM_ADMIN_ROUTES t, 
       ADMINISTRATION_ROUTES a, 
       DOSAGE_FORMS d,
       v_dosage_forms s
 where t.adminrt_id = a.adminrt_id
   and t.dosefrm_id = d.dosefrm_id
   and d.dosefrm_id = s.dosefrm_id(+)
)
,xf as (
select x.min_age,
       x.alt_min_age dfu_min_age,
       nvl2(x.min_age,169,null) age_unit_id,
       nvl2(x.min_age,'yr',null) age_unit_code,
       nvl2(x.min_age,'yr',null) dfu_age_unit_code,
       nvl2(x.min_age,169,null) dfu_age_unit_id,
       x.unique_dfu_code dfu_group_code,
       --df.dfu_group_code,
       df.dfu_group_decode_eng dfu_decode_eng,
       df.dfu_group_decode_fr dfu_decode_fr,
       x.roa_code, x.df_code, df.dfu_id 
  from x_df_roa_min_age x, x$dfu_xref df
 where x.unique_dfu_code = df.dfu_group_code(+)
)
select unique
       df.adminrt_id admin_route_id,
       df.dosefrm_id dosage_form_id,
       xf.dfu_id,
       to_number(xf.min_age) min_age,
       xf.age_unit_code,
       xf.age_unit_id,
       xf.dfu_min_age,
       xf.dfu_age_unit_code,
       xf.dfu_age_unit_id,
       0, trunc(sysdate)
  from df full join xf
    on (df.admin_route_code = xf.roa_code and df.dosage_form_code = xf.df_code)
;

commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ROA_DOSAGE_FORM
  add constraint X$ROA_DOSAGE_FORM_PK primary key (ADMIN_ROUTE_ID, DOSAGE_FORM_ID);
alter table X$ROA_DOSAGE_FORM
  add constraint X$ROA_DOSAGE_FORM_FK1 foreign key (ADMIN_ROUTE_ID)
  references administration_routes (ADMINRT_ID);
alter table X$ROA_DOSAGE_FORM
  add constraint X$ROA_DOSAGE_FORM_FK2 foreign key (DOSAGE_FORM_ID)
  references dosage_forms (DOSEFRM_ID);
alter table X$ROA_DOSAGE_FORM
  add constraint X$ROA_DOSAGE_FORM_FK3 foreign key (DFU_ID)
  references x$dfu_xref (DFU_ID);
*/

