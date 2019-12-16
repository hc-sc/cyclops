--spool create_x$source_id_source_org_types.log
--prompt Max INGRED_ID value from INGREDIENTS table
--select max(ingred_id) ingred_id_max from ingredients; --17529
/*
drop table x$source_id_source_org_types;
create table x$source_id_source_org_types
( orgtype_id number not null,
  source_org_id number not null,
  source_org_part_id number not null,
  source_org_parttype_id number not null,
  ingred_id number not null,
  source_ingred_id number,
  source_id_src_org_type_id number not null,
  product_mono_code varchar2(20), 
  single_mono_code varchar2(20),
  constraint x$source_id_src_org_types_pk primary key (source_id_src_org_type_id)
);
*/

truncate table x$source_id_source_org_types;
insert into x$source_id_source_org_types
(orgtype_id, source_org_id, source_org_part_id, source_org_parttype_id, ingred_id, 
 source_ingred_id, source_id_src_org_type_id, product_mono_code, single_mono_code,
 useracc_id, creation_date)
select unique o.orgtype_id, --337
       op.organism_id source_org_id, 
       op.orgpart_id source_org_part_id, 
       op.orgparttype_id source_org_parttype_id,
       su.ingred_id,
       null source_ingred_id, --mx.ingred_id_max + rownum --ingredients_seq.nextval source_ingred_id
       rownum source_id_src_org_type_id,
       x.product_mono_code, x.mono_code,
       0, trunc(sysdate)
  from ORGANISM_PARTS op,
       ORGANISM_PART_TYPES opt,
       ORGANISM_PART_SUBINGREDIENTS ops,
       SUBINGREDIENTS su,
       ORGANISMS o,
       x$organism_ingredient x
 where op.orgpart_id = ops.orgpart_id
   and op.orgparttype_id = opt.orgparttype_id
   and su.subingred_id = ops.subingred_id
   and o.organism_id = op.organism_id
   and op.organism_id = x.organism_id(+)
   and x.ingred_id is null
;
commit;

-- Add/modify columns 
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify orgtype_id not null;
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify source_org_id not null;
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify source_org_part_id not null;
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify source_org_parttype_id not null;
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify ingred_id not null;
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES modify source_ingred_id not null;
/*
--spool off
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_UK unique (orgtype_id, source_org_id, source_org_part_id, source_org_parttype_id, ingred_id);
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK1 foreign key (INGRED_ID)
  references ingredients (INGRED_ID);
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK2 foreign key (source_org_id )
  references ORGANISMS (organism_id);
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK3 foreign key (orgtype_id)
  references ORGANISM_TYPES (orgtype_id);
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK4 foreign key (source_org_part_id )
  references ORGANISM_PARTS (orgpart_id);
alter table X$SOURCE_ID_SOURCE_ORG_TYPES
  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK5 foreign key (source_org_parttype_id )
  references ORGANISM_PART_TYPES (orgparttype_id);
--alter table X$SOURCE_ID_SOURCE_ORG_TYPES -- all artificial keys
--  add constraint X$SOURCE_ID_SRC_ORG_TYPES_FK6 foreign key (source_ingred_id)
--  references INGREDIENTS (ingred_id);
*/
