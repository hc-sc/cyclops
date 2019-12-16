create or replace force view
v_ingredient_org_type_parts
as
select iop.ingred_id,
       otp.orgtype_code,
       otp.orgtype_name_eng,
       otp.orgtype_name_fr,
       otp.orgparttype_code,
       otp.orgparttype_name_eng,
       otp.orgparttype_name_fr,
       otp.orgtype_id,
       otp.orgparttype_id
  from V_INGREDIENT_ORGANISM_PARTS iop,
       V_INGREDIENT_TYPE it,
       V_ORGANISM_TYPE_PARTS otp
 where iop.ingred_id = it.ingred_id
   and it.ingred_type_id = 3 -- Organism
   and iop.orgtype_id = otp.orgtype_id
;

       
