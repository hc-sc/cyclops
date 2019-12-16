--drop table x$non_ingred_orgnsm_prep_code;
--create table x$non_ingred_orgnsm_prep_code as
truncate table x$non_ingred_orgnsm_prep_code;
insert into x$non_ingred_orgnsm_prep_code
(product_mono_code, mono_code, source_prep_code, organism_name, organism_type_code_nhpid, 
 source_material_org_type, source_material_part, prep_type_id, prep_type_code, non_ingred_org_prep_id,
 useracc_id, creation_date)
select q.*, rownum non_ingred_org_prep_id, 0, trunc(sysdate) from (
select unique
       x.product_mono_code, x.mono_code, x.source_prep_code,
       x.organism_name, x.organism_type_code_nhpid, x.source_material_org_type, x.source_material_part,
       xp.prep_type_id, xp.prep_group_code prep_type_code
  from x_sources x, x$preparation_methods_xref xp
 where x.source_nhpid_name is null
   and x.source_prep_code is not null
   and x.source_prep_code = xp.unique_prep_code(+)
) q
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$NON_INGRED_ORGNSM_PREP_CODE
  add constraint X$NON_INGRED_ORGNSM_PRP_CD_PK primary key (non_ingred_org_prep_id);
alter table X$NON_INGRED_ORGNSM_PREP_CODE
  add constraint X$NON_INGRED_ORGNSM_PRP_CD_FK1 foreign key (PREP_TYPE_ID)
  references preparation_types (PREPTYPE_ID);
*/

