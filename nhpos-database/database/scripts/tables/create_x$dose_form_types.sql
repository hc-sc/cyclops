--drop table x$dose_form_types;
--create table x$dose_form_types as
truncate table x$dose_form_types;
insert into x$dose_form_types
(dosefrm_id, dosefrm_code, dosage_form_type, 
 valid_for_compendial, allow_ingredient_units,
 useracc_id, creation_date)
select df.dosefrm_id, 
       df.dosefrm_code, 
       xf.dosage_form_type, 
       substr(lower(xf.valid_for_compendial),1,1) valid_for_compendial, 
       substr(lower(xf.allow_ingredient_units),1,1) allow_ingredient_units, 
       0, trunc(sysdate)
       --to_number(nvl(xa.default_min_age,0)) min_age, --min_age,0)) min_age, 
       --to_number(xa.default_min_age), --alternate_min_age) dfu_min_age,
       --xa.dfu_code
  from DOSAGE_FORMS df, 
       xr_dosage_form_syn xf
       --xr_dosage_form_age_mins xa --xr_dosage_form_min_age
 where df.dosefrm_code = xf.code
   --and df.dosefrm_code = xa.oral_code(+)
--order by xa.oral_code
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$DOSE_FORM_TYPES
  add constraint X$DOSE_FORM_TYPES_PK primary key (DOSEFRM_ID);
alter table X$DOSE_FORM_TYPES
  add constraint X$DOSE_FORM_TYPES_UK1 unique (DOSEFRM_CODE);
alter table X$DOSE_FORM_TYPES
  add constraint X$DOSE_FORM_TYPES_FK1 foreign key (DOSEFRM_ID)
  references dosage_forms (DOSEFRM_ID);
*/

