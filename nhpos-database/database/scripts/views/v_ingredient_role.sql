create or replace force view
v_ingredient_role
as
select ir.ingred_id, rs.rolespec_class_name,
       rs.rolespec_name_eng, rs.rolespec_name_fr,
       ir.ingredrole_id, rs.rolespec_id
  from INGREDIENT_ROLES ir, ROLE_SPECIALS rs
 where ir.rolespec_class_name = rs.rolespec_class_name
;

--grant select on v_ingredient_role to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_ingredient_role for nhpdweb_dev.v_ingredient_role;

