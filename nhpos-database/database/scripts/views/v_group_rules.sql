create or replace force view
v_group_rules
as
select ggr.ingred_group_id,
       gr.rule_id,
       gr.rule_code,
       gr.rule_name,
       gr.rule_description,
       gr.error_message_en,
       gr.error_message_fr,
       gr.is_enabled
  from x$group_rules gr, 
       x$ingred_group_group_rules ggr
 where ggr.rule_id = gr.rule_id
;

