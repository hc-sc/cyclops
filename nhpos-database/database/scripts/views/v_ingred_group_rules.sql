create or replace force view
v_ingred_group_rules
as
select --unique 
       ig.ingred_group_code,
       ig.ingred_id, ig.ingred_name,
       --ig.sub_ingred_id, ig.sub_ingred_name,
       ig.product_mono_code, ig.single_mono_code, ig.mono_id,
       ig.combo_dose_code,
       gr.rule_code, gr.rule_id, 
       ig.ingred_group_id 
  from x$ingred_group_group_rules x, 
       v_ingredient_groups ig, 
       x$group_rules gr
 where x.ingred_group_id = ig.ingred_group_id 
   and x.rule_id = gr.rule_id
;--   order by 7 nulls first
