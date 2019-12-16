--drop table x$orphan_source_organism_types;
--create table x$orphan_source_organism_types as
truncate table x$orphan_source_organism_types;
insert into x$orphan_source_organism_types
(mono_code_nhpid, mono_id, ingred_id_nhpid, source_prep_code, organism_type_id,
 orgtype_code, orgparttype_id, orgparttype_code, orgparttype_name_eng,
 orgparttype_name_fr, product_mono_code, mono_code, orphan_src_org_type_id,
 useracc_id, creation_date)
with t as (
select unique ot.orgparttype_id, ot.orgparttype_code, 
       ot.orgparttype_name_eng, ot.orgparttype_name_fr
  from ORGANISM_PARTS op,
       ORGANISM_PART_TYPES ot
 where op.orgparttype_id = ot.orgparttype_id
)
,q as (
select unique 
       mm.code mono_code_nhpid, mm.mono_id, 
       to_number(m.ingred_id_nhpid) ingred_id_nhpid, 
       x.source_prep_code,
       o.orgtype_id organism_type_id, o.orgtype_code,
       t.orgparttype_id, 
       t.orgparttype_code, 
       t.orgparttype_name_eng, t.orgparttype_name_fr,
       x.product_mono_code, x.mono_code
  from x_sources x, 
       x_monograph m, 
       ORGANISM_TYPES o, 
       MONOGRAPHS mm, T
 where 1=1
   and x.organism_name is null
   and x.organism_type_code_nhpid is not null
--   and nvl(x.product_mono_code,  x.mono_code) = nvl(m.product_mono_code, m.mono_code)
   and nvl(x.product_mono_code,'x') = nvl(m.product_mono_code,'x')
   and x.mono_code = m.mono_code
   and x.source_rule_code = m.source_code
   and x.organism_type_code_nhpid = o.orgtype_code
   and m.nhpid_mono_code = mm.code(+)
   and lower(trim(x.source_material_part)) = lower(trim(t.orgparttype_name_eng(+)))
)
select q.*, rownum ORPHAN_SRC_ORG_TYPE_ID,
       0, trunc(sysdate)
from q
;
commit;

/*
-- Add/modify columns 
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES modify ORGANISM_TYPE_ID not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_PK primary key (ORPHAN_SRC_ORG_TYPE_ID);
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_UK unique (ingred_id_nhpid, ORGANISM_TYPE_ID, ORGPARTTYPE_ID, MONO_ID, source_prep_code);
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_FK1 foreign key (MONO_ID)
  references monographs (MONO_ID);
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_FK2 foreign key (ORGANISM_TYPE_ID)
  references organism_types (ORGTYPE_ID);
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_FK3 foreign key (ORGPARTTYPE_ID)
  references organism_part_types (ORGPARTTYPE_ID);
alter table X$ORPHAN_SOURCE_ORGANISM_TYPES
  add constraint X$ORPHAN_SOURCE_ORG_TYPES_FK4 foreign key (INGRED_ID_NHPID)
  references ingredients (INGRED_ID);
*/
