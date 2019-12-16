--drop table x$source_organism_types;
--create table x$source_organism_types as 
truncate table x$source_organism_types;
insert into x$source_organism_types
(source_id, source_nhpid_name, product_mono_code, single_mono_code, source_prep_code, 
 organism_name, organism_type_code_nhpid, source_material_org_type, source_material_part, 
 source_material_strain, prep_type_id, prep_type_code, orgtype_id, orgparttype_id, 
 mono_code, mono_id,
 source_organism_type_id, useracc_id, creation_date)
select q.*, rownum source_organism_type_id, 0, trunc(sysdate) from (
select unique 
       nvl(i.ingred_id, to_number(decode(x.source_ingred_id_nhpid,'#N/A',null,x.source_ingred_id_nhpid))) source_id, 
       x.source_nhpid_name,
       x.product_mono_code, x.mono_code single_mono_code, x.source_prep_code,
       x.organism_name, x.organism_type_code_nhpid, 
       x.source_material_org_type, x.source_material_part,
       x.source_material_strain,
       xp.prep_type_id, xp.prep_group_code prep_type_code,
       --o.organism_id, 
       ot.orgtype_id, opt.orgparttype_id,
       m.mono_code, m.mono_id -- vp add nhpd mono code/id
  from x_sources x,
       x$monograph_xref m, -- vp add nhpd mono code/id
       x$preparation_methods_xref xp,
       INGREDIENTS i,
       --ORGANISMS o,
       ORGANISM_TYPES ot,
       ORGANISM_PART_TYPES opt
 where 1=1
   and x.source_material_org_type is not null
   and x.mono_code = m.single_mono_code(+) and nvl(x.product_mono_code,'x') = nvl(m.product_mono_code,'x')
   and x.source_prep_code = xp.prep_group_code(+)
   and to_number(decode(x.source_ingred_id_nhpid,'#N/A',null,x.source_ingred_id_nhpid)) = i.ingred_id(+)
--    or  lower(trim(x.source_nhpid_name)) = lower(trim(i.ingred_authorized_name_eng)))
   --and lower(trim(x.organism_name)) = lower(trim(o.organism_name(+)))
   and x.organism_type_code_nhpid = ot.orgtype_code(+)
   and lower(trim(x.source_material_part)) = lower(trim(opt.orgparttype_name_eng(+)))
order by 1 desc, 2
) q
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SOURCE_ORGANISM_TYPES
  add constraint X$SOURCE_ORGANISM_TYPES_PK primary key (source_organism_type_id);
alter table X$SOURCE_ORGANISM_TYPES
  add constraint X$SOURCE_ORGANISM_TYPES_FK1 foreign key (SOURCE_ID)
  references ingredients (INGRED_ID);
alter table X$SOURCE_ORGANISM_TYPES
  add constraint X$SOURCE_ORGANISM_TYPES_FK2 foreign key (PREP_TYPE_ID)
  references preparation_types (PREPTYPE_ID) novalidate;
alter table X$SOURCE_ORGANISM_TYPES
  add constraint X$SOURCE_ORGANISM_TYPES_FK3 foreign key (ORGTYPE_ID)
  references organism_types (ORGTYPE_ID);
alter table X$SOURCE_ORGANISM_TYPES
  add constraint X$SOURCE_ORGANISM_TYPES_FK4 foreign key (ORGPARTTYPE_ID)
  references organism_part_types (ORGPARTTYPE_ID);
*/

