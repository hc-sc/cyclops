-- Add/modify columns 
--drop table x$preparation_methods_xref;
--create table x$preparation_methods_xref as
truncate table x$preparation_methods_xref;
insert into  x$preparation_methods_xref
(prep_method_id, prep_type_id, --prep_type_code, approved_name, 
 unique_prep_code, unique_prep_description_eng, unique_prep_description_fr, 
 prep_group_code, potency, extract, ratio_type, solvents, 
 quantity_crude_equivalent, solvent_list_id, useracc_id, creation_date) -- vp added FRESH/DRY
select rownum prep_method_id, q.*
  from (select unique
       pt.prep_type_id,
--       pt.prep_type_code,
--       pt.approved_name,
       xp.unique_prep_code, 
       xp.unique_prep_description unique_prep_description_eng, 
       xp.unique_prep_description_fr, 
       xp.prep_group_code, 
       decode(lower(pt.potency),'required','y','-',null,lower(pt.potency)) potency, 
       decode(lower(pt.extract),'required','y','optional','n','-',null,lower(pt.extract)) extract, 
       replace(pt.ratio_type,'-') ratio_type,
       decode(lower(pt.solvents),'required','y','optional','n','-',null,lower(pt.solvents)) solvents, 
       decode(lower(pt.quantity_crude_equivalent),'required','y','optional','n','-',null,lower(pt.quantity_crude_equivalent)) quantity_crude_equivalent,
       to_number(substr(xp.solvent_group_code,3)) solvent_list_id,
       0, trunc(sysdate)
  from x_prep_method xp,
       x$prep_type_info_xref pt
 where xp.nhpid_prep_code = pt.prep_type_code(+)
 order by 1 nulls last
) q 
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$PREPARATION_METHODS_XREF
  add constraint X$PREPARATION_METHODS_XREF_PK primary key (prep_method_id);
alter table X$PREPARATION_METHODS_XREF
  add constraint X$PREPARATION_METHODS_XREF_UK unique (PREP_GROUP_CODE, PREP_TYPE_CODE);
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$PREPARATION_METHODS_XREF
  add constraint X$PREPARATION_METHODS_XREF_FK1 foreign key (PREP_TYPE_ID)
  references PREPARATION_TYPES (PREPTYPE_ID); 
alter table X$PREPARATION_METHODS_XREF 
  add constraint X$PREPARATION_METHODS_XREF_FK2 foreign key (solvent_list_id) 
  references x$solvent_lists (solvent_list_id);
-- Add check constraints
-- Create/Recreate check constraints 
alter table X$PREPARATION_METHODS_XREF add constraint X$PREPARATION_METHODS_XREF_CH1 check (lower(potency) in ('y','n'));
alter table X$PREPARATION_METHODS_XREF add constraint X$PREPARATION_METHODS_XREF_CH2 check (lower(extract) in ('y','n'));
alter table X$PREPARATION_METHODS_XREF add constraint X$PREPARATION_METHODS_XREF_CH3 check (lower(solvents) in ('y','n'));
alter table X$PREPARATION_METHODS_XREF add constraint X$PREPARATION_METHODS_XREF_CH3 check (lower(quantity_crude_equivalent) in ('y','n'));
*/

