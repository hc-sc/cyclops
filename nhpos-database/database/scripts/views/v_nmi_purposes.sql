create or replace force view
v_nmi_purposes
as
select i.ingred_id, 
       i.ingred_authorized_name_eng nhpid_name_eng,
       i.ingred_authorized_name_fr nhpid_name_fr,
       rs.rolespec_name_eng ingred_role_eng,
       rs.rolespec_name_fr ingred_role_fr,
       p.purpose_name_eng,
       p.purpose_name_fr,
       p.purpose_code
  from ingredients i,
       ingredient_roles ir,
       ingredient_role_purposes irp,
       purposes p,
       role_specials rs
 where i.ingred_id = ir.ingred_id 
   and irp.ingredrole_id = ir.ingredrole_id
   and irp.purpose_id = p.purpose_id
   and ir.rolespec_class_name = rs.rolespec_class_name
   and rs.rolespec_name_eng = 'Non-medicinal'
;

--grant select on v_nmi_purposes to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_nmi_purposese for nhpdweb_dev.v_nmi_purposes;


