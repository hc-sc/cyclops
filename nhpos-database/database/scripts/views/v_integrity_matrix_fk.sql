-- integrity check for ingredient matrix
create or replace view 
v_integrity_matrix_fk
as
-- monograph - source
select unique 'monograph' src_tab_name, 'source' trgt_tab_name, 'source_code' column_name, m.source_code missing_value from x_monograph m
 where m.source_code not in (select unique source_rule_code from x_sources)
union all
select unique 'monograph' src_tab_name, 'source' trgt_tab_name, 'mono_code' col, x.mono_code from x_ingredient_matrix x
 where x.mono_code  not in (select unique mono_code from x_sources)
union all
select unique 'monograph' src_tab_name, 'source' trgt_tab_name, 'product_mono_code' col, x.product_mono_code from x_ingredient_matrix x
 where x.product_mono_code  not in (select unique product_mono_code from x_sources)
union all
-- ingredient matrix
select unique 'ingredient' src_tab_name, 'source' trgt_tab_name, 'source_group_code' column_name, x.source_group_code missing_value from x_ingredient_matrix x 
where x.source_group_code  not in (select unique source_group_code from x_sources)
union all
select unique 'ingredient' src_tab_name, 'dosage_form' trgt_tab_name, 'dosage_form_code' column_name, x.dosage_form_group_code missing_value from x_ingredient_matrix x 
where x.dosage_form_group_code   not in (select unique dosage_form_group_code  from x_dosage_form)
union all
select unique 'ingredient' src_tab_name, 'monograph' trgt_tab_name, 'mono_code' col, x.mono_code from x_ingredient_matrix x 
where x.mono_code  not in (select unique mono_code from x_monograph)
union all
select unique 'ingredient' src_tab_name, 'monograph' trgt_tab_name, 'product_mono_code' col, x.product_mono_code from x_ingredient_matrix x 
where x.product_mono_code  not in (select unique product_mono_code from x_monograph)
union all
select unique 'ingredient' src_tab_name, 'ingredient_group_group' trgt_tab_name, 'group_rule_group_id' col, x.rule_group_id from x_ingredient_matrix x 
where x.group_rule_group_id  not in (select unique group_group_id  from x_ingredient_groups)
union all
select unique 'ingredient' src_tab_name, 'ingredient_group' trgt_tab_name, 'rule_group_id' col, x.rule_group_id from x_ingredient_matrix x 
where x.rule_group_id  not in (select unique group_id from x_ingredient_groups)
union all
select unique 'ingredient' src_tab_name, 'use' trgt_tab_name, 'use_code' col, x.use_code from x_ingredient_matrix x 
where x.use_code  not in (select unique use_code from x_use)
union all
select unique 'ingredient' src_tab_name, 'duration' trgt_tab_name, 'duration_code' col, x.duration_code from x_ingredient_matrix x 
where x.duration_code  not in (select unique duration_code from x_duration)
union all
select unique 'ingredient' src_tab_name, 'risk' trgt_tab_name, 'risk_code' col, x.risk_code from x_ingredient_matrix x 
where x.risk_code  not in (select unique risk_code from x_risk)
union all
select unique 'ingredient' src_tab_name, 'dfu' trgt_tab_name, 'dfu_group_code' col, x.dfu_group_code from x_ingredient_matrix x 
where x.dfu_group_code  not in (select unique dfu_group_code from x_dfu)
union all
select unique 'ingredient' src_tab_name, 'prep_method' trgt_tab_name, 'prep_group_code' col, x.prep_group_code from x_ingredient_matrix x 
where x.prep_group_code  not in (select unique prep_group_code from x_prep_method)
union all
select unique 'ingredient' src_tab_name, 'sub_pop' trgt_tab_name, 'sub_pop_code' col, x.sub_pop_code from x_ingredient_matrix x 
where x.sub_pop_code  not in (select unique sub_pop_code from x_sub_pop)
union all
-- integrity check for sub ingredient matrix
select unique 'sub_ingredient' src_tab_name, 'source' trgt_tab_name, 'source_group_code' column_name, x.source_group_code missing_value from x_sub_ingredient_matrix x 
where x.source_group_code  not in (select unique source_group_code from x_sources)
union all
select unique 'sub_ingredient' src_tab_name, 'dosage_form' trgt_tab_name, 'dosage_form_group_code' column_name, x.dosage_form_group_code missing_value from x_sub_ingredient_matrix x 
where x.dosage_form_group_code  not in (select unique dosage_form_group_code  from x_dosage_form)
union all
select unique 'sub_ingredient' src_tab_name, 'monograph' trgt_tab_name, 'mono_code' col, x.mono_code from x_sub_ingredient_matrix x 
where x.mono_code  not in (select unique mono_code from x_monograph)
union all
select unique 'sub_ingredient' src_tab_name, 'monograph' trgt_tab_name, 'product_mono_code' col, x.product_mono_code from x_sub_ingredient_matrix x 
where x.product_mono_code  not in (select unique product_mono_code from x_monograph)
union all
select unique 'sub_ingredient' src_tab_name, 'ingredient_group_groups' trgt_tab_name, 'group_rule_group_id' col, x.group_rule_group_id  from x_sub_ingredient_matrix x 
where x.group_rule_group_id   not in (select unique group_group_id from x_ingredient_groups)
union all
select unique 'sub_ingredient' src_tab_name, 'ingredient_groups' trgt_tab_name, 'rule_group_id' col, x.rule_group_id  from x_sub_ingredient_matrix x 
where x.rule_group_id   not in (select unique group_id from x_ingredient_groups)
union all
select unique 'sub_ingredient' src_tab_name, 'use' trgt_tab_name, 'use_code' col, x.use_code from x_sub_ingredient_matrix x 
where x.use_code  not in (select unique use_code from x_use)
union all
select unique 'sub_ingredient' src_tab_name, 'duration' trgt_tab_name, 'duration_code' col, x.duration_code from x_sub_ingredient_matrix x 
where x.duration_code  not in (select unique duration_code from x_duration)
union all
select unique 'sub_ingredient' src_tab_name, 'risk' trgt_tab_name, 'risk_code' col, x.risk_code from x_sub_ingredient_matrix x 
where x.risk_code  not in (select unique risk_code from x_risk)
union all
select unique 'sub_ingredient' src_tab_name, 'dfu' trgt_tab_name, 'dfu_code' col, x.dfu_code from x_sub_ingredient_matrix x 
where x.dfu_code  not in (select unique dfu_code from x_dfu)
union all
select unique 'sub_ingredient' src_tab_name, 'prep_method' trgt_tab_name, 'sub_ingred_prep_code' col, x.subing_prep_group_code  from x_sub_ingredient_matrix x 
where x.subing_prep_group_code   not in (select unique prep_group_code  from x_prep_method)
union all
select unique 'sub_ingredient' src_tab_name, 'sub_pop' trgt_tab_name, 'sub_pop_code' col, x.sub_pop_code from x_sub_ingredient_matrix x 
where x.sub_pop_code  not in (select unique sub_pop_code from x_sub_pop)
order by 1,2,3
;
/*
select  unique
        --t.product_mono_code
        t.group_group_id
        --t.combo_group_id
        ,t.parent_group_id
        --,t.group_id
        --t.mono_code,
        --t.nhpid_name
--        t.nhpid_ingred_id,
from X_sub_INGREDIENT_MATRIX t
where t.group_group_id is not null 
--or t.group_id is not null
or t.parent_group_id is not null or t.combo_group_id is not null
minus
select unique 
        --t.product_mono_code
        t.group_group_id
        ,t.group_id
        --t.mono_code,
        --t.ingredient_nhpid_name
--        t.nhpid_ingred_id
 from X_INGREDIENT_GROUPS t
*/
