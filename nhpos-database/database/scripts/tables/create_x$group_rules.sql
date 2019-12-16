--drop table x$group_rules cascade constraints;
--create table x$group_rules as
--alter table x$ingred_group_group_rules drop constraint x$ingred_group_group_rules_fk2;
truncate table x$group_rules;
insert into  x$group_rules
(rule_id, rule_code, rule_name, rule_description, 
error_message_en, error_message_fr, is_enabled,
useracc_id, creation_date, rule_text)
select unique
       dense_rank() over (order by t.rule_id,
                                   t.rule_group_title,
                                   t.rule_description,
                                   t.error_message_en,
                                   t.error_message_fr,
                                   t.is_enabled) rule_id,
       t.rule_id rule_code,
       t.rule_group_title rule_name,
       t.rule_description,
       t.error_message_en,
       t.error_message_fr,
       t.is_enabled,
       0, trunc(sysdate),
       null rule_text
  from X_MONO_RULES t
 order by 1
;
commit;
--
truncate table X$INGRED_GROUP_GROUP_RULES;
insert into X$INGRED_GROUP_GROUP_RULES
(ingred_group_id, rule_id, useracc_id, creation_date)
select ingred_group_id, gr.rule_id, 0, trunc(sysdate) 
  from X_MONO_RULES t, X$GROUP_RULES gr, X$INGREDIENT_GROUPS ig
 where t.rule_id = gr.rule_code
   and nvl(t.group_id,t.groupgroup_id) = ig.ingred_group_code
union 
select ingred_group_id, gr.rule_id, 0, trunc(sysdate) 
  from X_MONO_RULES t, X$GROUP_RULES gr, X$INGREDIENT_GROUPS ig
 where t.rule_id = gr.rule_code
   and nvl(t.target_group_id,t.target_groupgroup_id) = ig.ingred_group_code
;
commit;

--alter table x$group_rules add constraint x$group_rules_pk primary key (rule_id);
--alter table x$group_rules add constraint x$group_rules_uk unique (rule_code);
--alter table X$INGRED_GROUP_GROUP_RULES add constraint X$INGRED_GROUP_GROUP_RULES_FK1 foreign key (INGRED_GROUP_ID)
--  references X$INGREDIENT_GROUPS (INGRED_GROUP_ID);
--alter table X$INGRED_GROUP_GROUP_RULES add constraint X$INGRED_GROUP_GROUP_RULES_FK2 foreign key (RULE_ID)
--  references X$GROUP_RULES (RULE_ID);

