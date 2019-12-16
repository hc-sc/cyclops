create or replace force view
v_organism_type_parts
as
select ot.orgtype_code, ot.orgtype_name_eng, ot.orgtype_name_fr,
       opt.orgparttype_code, opt.orgparttype_name_eng, opt.orgparttype_name_fr,
       ot.orgtype_id, opt.orgparttype_id
  from ORGANISM_TYPES ot,
       ORGANISM_TYPE_GROUPS otg,
       ORGANISM_TYPE_GRP_ORGPARTTYPES gop,
       ORGANISM_PART_TYPES opt
 where 1=1
   and ot.orgtypegrp_id = otg.orgtypegrp_id
   and ot.orgtypegrp_id = gop.orgtypegrp_id
   and gop.orgparttype_id = opt.orgparttype_id
;
create or replace public synonym v_organism_type_parts for nhpdweb_owner.v_organism_type_parts;
grant select on v_organism_type_parts to NHPDWEB_BUFFER;
grant select  on v_organism_type_parts to NHPDWEB_USER;
grant select on v_organism_type_parts to NHPID_APEX;
grant select on v_organism_type_parts to RL__NHPDWEB_ADMIN;
grant select  on v_organism_type_parts to RL__NHPDWEB_READ;
grant select  on v_organism_type_parts to R_NHPD_ING_READ;

