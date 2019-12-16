create or replace force view
v_ingredient_groups
as
select ig.ingred_group_id, ig.ingred_group_code,
       gi.ingred_group_ingred_id, gi.product_mono_code, gi.single_mono_code, gi.mono_id, 
       gi.ingred_nhpid_name ingred_name, gi.ingred_nhpid_id ingred_id, 
       gi.sub_ingred_nhpid_name sub_ingred_name, gi.sub_ingred_nhpid_id sub_ingred_id,
       ig.group_description, gg.ingred_group_group_code, ig.group_group_description,
       ig.combo_dose_code
  from x$ingredient_groups ig, x$ingredient_group_groups gg, x$ingredient_group_ingredients gi
 where 1=1
   and ig.ingred_group_ingred_id = gi.ingred_group_ingred_id(+)
   and ig.ingred_group_group_id = gg.ingred_group_group_id(+)
;


