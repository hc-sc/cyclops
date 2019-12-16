create or replace force view
v_ingredient_specials
as
select ingredspec_class_name ingredient_type_code, 
       ingredspec_name_eng ingredient_type_eng, 
       ingredspec_name_fr ingredient_type_fr, 
       ingredspec_id ingredient_type_id,
       decode(substr(lower(ingredspec_class_name),1,2),'ch',1,'pr',1,'or',1,'de',1,2) group_id
  from INGREDIENT_SPECIALS
 order by decode(substr(lower(ingredspec_class_name),1,2),'ch',1,'pr',2,'or',3,'de',4,ingredspec_id + 4)
;

--grant select on v_ingredient_specials to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_ingredient_specials for nhpdweb_dev.v_ingredient_specials;
