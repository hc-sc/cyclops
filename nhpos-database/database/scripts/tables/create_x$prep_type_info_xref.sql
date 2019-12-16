/*
drop table x$prep_type_info_xref;
create table x$prep_type_info_xref (
      prep_type_id number,
      prep_type_code varchar2(30),
      approved_name varchar2(100),
      potency varchar2(20),
      extract varchar2(20),
      ratio_type varchar2(20),
      solvents varchar2(20),
      solvent_list varchar2(255),
      quantity_crude_equivalent varchar2(20)
);
*/
truncate table x$prep_type_info_xref;
insert into x$prep_type_info_xref
(prep_type_id, prep_type_code, approved_name, potency, extract, 
ratio_type, solvents, solvent_list, quantity_crude_equivalent,
useracc_id, creation_date, row_id)
select pt.preptype_id, 
       pt.preptype_code, --pt.preptype_name_eng, pt.preptype_name_fr, 
       nvl(x.approved_name,pt.preptype_name_eng) approved_name,
       nvl(x.potency,pt.preptype_standardized) potency,
       nvl(x.extract,pt.preptype_extract) extract,
       nvl(x.extract_ratio,pt.preptype_ratiotype) extract_ratio,
       x.solvents,
       x.solvent_list,
       x.quantity_crude_equivalent,
       0, trunc(sysdate), rownum
  from PREPARATION_TYPES pt
  full join x_mop_info x
    --on replace(lower(trim(pt.preptype_name_eng)),' ') = replace(replace(lower(trim(x.approved_name)),'standardized','standardised'),' ')
    on regexp_replace(lower(pt.preptype_name_eng),'[^0-9a-z]','') = regexp_replace(lower(replace(x.approved_name,'standardised','standardized')),'[^0-9a-z]','')
;
commit;
/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$PREP_TYPE_INFO_XREF
  add constraint X$PREP_TYPE_INFO_XREF_FK1 foreign key (PREP_TYPE_ID)
  references preparation_types (PREPTYPE_ID);
*/
