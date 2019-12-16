prompt create x_temp_assessment_dose
drop table x_tmp_assess_doses;
create table x_tmp_assess_doses as
with ad as (
select --unique
       xi.product_mono_code,
       xi.mono_code,
       xi.source_group_code,
       xi.group_rule_group_id group_group_id,
       xi.rule_group_id group_id,
       xi.combo_dose_code,
       xi.version,
       xi.ingred_id_nhpid,
       xi.nhpid_name,
       xi.ingred_dose_type mi_dose_type, 
       xi.qdu_min,
       xi.qdu_max,
       xi.qie_min,
       xi.qie_max,
       xi.quantity_units,
       xi.dose_cat,
       xi.min_freq,
       xi.max_freq,
       xi.frequency_interval,
       xi.use_code,
       xi.duration_code,
       xi.risk_code,
       xi.dfu_group_code,
       xi.prep_group_code,
       xi.sub_pop_code,
       xi.ratio_min,
       xi.ratio_max,
       xi.mi_doseid mi_dose_code,
       xi.alt_mi_doseid mi_dose_code_ref,
       xi.dosage_form_group_code dosage_form_group,
       xi.storage_conditions_group_code,
       null subing_id_nhpid,   -- vp added to include sub_ingred in assessment doses to handle different sub preps from ingred
       null subing_name_nhpid, -- vp added to include sub_ingred in assessment doses
       m.adminrt_id roa_id    -- vp add ROA to assessment doses
  from x_ingredient_matrix xi, x_monograph m
 where xi.ingred_id_nhpid = m.ingred_id_nhpid 
   and nvl(xi.product_mono_code,'x') = nvl(m.product_mono_code,'x') 
   and nvl(xi.mono_code,'x') = nvl(m.mono_code,'x') -- adds HOMEO
 union
select --unique
       xs.product_mono_code,
       xs.mono_code,
       xs.source_group_code,
       xs.group_rule_group_id group_group_id,
       xs.rule_group_id parent_group_id, 
       xs.combo_dose_code,
       xs.version,
       xs.ingred_id_nhpid,
       xs.parent_nhpid_name,
       xs.subing_dose_type mi_dose_type,
       xs.qdu_min,
       xs.qdu_max,
       xs.qie_min,
       xs.qie_max,
       xs.quantity_units,
       xs.dose_cat,
       xs.min_freq,
       xs.max_freq,
       xs.freq_interval frequency_interval,
       xs.use_code,
       xs.duration_code,
       xs.risk_code,
       xs.dfu_code dfu_group_code, --sub
       xs.prep_group_code,
       xs.sub_pop_code,
       xs.ratio_min,
       xs.ratio_max,
       xs.subing_dose_id mi_dose_code,
       xs.alt_subing_dose_id mi_dose_code_ref,
       xs.dosage_form_group_code, --sub
       xs.storage_condition_group_code, --sub
       xs.subing_id_nhpid, -- vp added to include sub_ingred in assessment doses
       xs.subing_name_nhpid,   -- vp added to include sub_ingred in assessment doses
       m.adminrt_id roa_id    -- vp add ROA to assessment doses
  from x_sub_ingredient_matrix xs, x_monograph m
 where xs.mono_code = m.mono_code 
   and nvl(xs.product_mono_code,'x') = nvl(m.product_mono_code,'x') 
   and xs.ingred_id_nhpid = m.ingred_id_nhpid
) select rownum assess_dose_id, ad.* from ad
;

prompt refresh x$assessment_dose_xref
truncate table x$assessment_dose_xref;
insert into x$assessment_dose_xref
(assess_dose_id, product_mono_code, single_mono_code, source_group_code, group_group_id, group_id, 
combo_dose_code, version, ingredient_id_nhpid, nhpid_name, mi_dose_type, qdu_min, qdu_max, 
qie_min, qie_max, quantity_units, dose_cat, min_freq, max_freq, frequency_interval, 
use_code, duration_code, risk_code, dfu_group_code, prep_group_code, sub_pop_code, 
ratio_min, ratio_max, mi_dose_code, mi_dose_code_ref, dosage_form_group, storage_conditions_group_code, 
ingred_group_id, sub_ingred_id_nhpid, subing_name_nhpid, roa_id, quantity_unit_id, frequency_unit_id, 
source_nhpid_name, source_ingred_id_nhpid, nhpid_mono_code, mono_id, ingredient_id, source_id, 
monograph_id, assessment_dose_id, useracc_id, creation_date, lastupdate_date, row_id)
with x as ( -- get Excel doses w/o ingred. groups
        select /*+ materialize */
               t1.assess_dose_id, 
               t1.product_mono_code, 
               t1.mono_code single_mono_code, 
               t1.source_group_code, 
               t1.group_group_id,
               t1.group_id,
               t1.combo_dose_code, 
               t1.version, 
               t1.ingred_id_nhpid ingredient_id_nhpid,
               t1.nhpid_name, 
               t1.mi_dose_type, 
               t1.qdu_min, 
               t1.qdu_max, 
               t1.qie_min, 
               t1.qie_max, 
               t1.quantity_units, 
               t1.dose_cat, 
               t1.min_freq, 
               t1.max_freq, 
               decode(t1.frequency_interval,'Daily','d','Weekly','wk') frequency_interval, 
               t1.use_code, 
               t1.duration_code, 
               t1.risk_code, 
               t1.dfu_group_code, 
               t1.prep_group_code, 
               t1.sub_pop_code, 
               t1.ratio_min, 
               t1.ratio_max, 
               t1.mi_dose_code, 
               t1.mi_dose_code_ref, 
               t1.dosage_form_group, 
               t1.storage_conditions_group_code,
               null ingred_group_id,
               t1.subing_id_nhpid sub_ingred_id_nhpid, -- vp added to include sub_ingred in assessment doses
               t1.subing_name_nhpid, -- vp added to include sub_ingred in assessment doses
               t1.roa_id  -- vp add ROA to assessment doses
          from x_tmp_assess_doses t1 
         where t1.group_group_id is null and t1.group_id is null
         union all -- Excel ingredient doses with group ID
        select ta.assess_dose_id, 
               ta.product_mono_code, 
               ta.mono_code single_mono_code, 
               ta.source_group_code, 
               ta.group_group_id,
               ta.group_id,
               ta.combo_dose_code, 
               ta.version, 
               ta.ingred_id_nhpid,
               ta.nhpid_name, 
               ta.mi_dose_type, 
               ta.qdu_min, 
               ta.qdu_max, 
               ta.qie_min, 
               ta.qie_max, 
               ta.quantity_units, 
               ta.dose_cat, 
               ta.min_freq, 
               ta.max_freq, 
               decode(ta.frequency_interval,'Daily','d','Weekly','wk'), 
               ta.use_code, 
               ta.duration_code, 
               ta.risk_code, 
               ta.dfu_group_code, 
               ta.prep_group_code, 
               ta.sub_pop_code, 
               ta.ratio_min, 
               ta.ratio_max, 
               ta.mi_dose_code, 
               ta.mi_dose_code_ref, 
               ta.dosage_form_group, 
               ta.storage_conditions_group_code,
               pg.ingred_group_id,
               ta.subing_id_nhpid, -- vp added to include sub_ingred in assessment doses
               ta.subing_name_nhpid, -- vp added to include sub_ingred in assessment doses
               ta.roa_id  -- vp add ROA to assessment doses
          from x_tmp_assess_doses ta, 
               v_ingredient_groups pg --x_ingredient_groups
         where 1=1
           and nvl(ta.product_mono_code,'x') = nvl(pg.product_mono_code,'x')
           and ta.mono_code = pg.single_mono_code
           and nvl(ta.group_id, ta.group_group_id) = nvl(pg.ingred_group_code, pg.ingred_group_group_code)
           and nvl(ta.combo_dose_code,'x') = nvl(pg.combo_dose_code,'x')
           and (nvl(ta.ingred_id_nhpid,0) = nvl(pg.ingred_id,0)
            or  nvl(lower(trim(ta.nhpid_name)),'x') = nvl(lower(trim(pg.ingred_name)),'x'))
           and pg.sub_ingred_id is null
         union all -- Excel sub_ingredient doses with group ID
        select ta.assess_dose_id, 
               ta.product_mono_code, 
               ta.mono_code single_mono_code, 
               ta.source_group_code, 
               ta.group_group_id,
               ta.group_id,
               ta.combo_dose_code, 
               ta.version, 
               ta.ingred_id_nhpid,
               ta.nhpid_name, 
               --pg.sub_ingred_id,
               --pg.sub_ingred_name,
               ta.mi_dose_type, 
               ta.qdu_min, 
               ta.qdu_max, 
               ta.qie_min, 
               ta.qie_max, 
               ta.quantity_units, 
               ta.dose_cat, 
               ta.min_freq, 
               ta.max_freq, 
               decode(ta.frequency_interval,'Daily','d','Weekly','wk'), 
               ta.use_code, 
               ta.duration_code, 
               ta.risk_code, 
               ta.dfu_group_code, 
               ta.prep_group_code, 
               ta.sub_pop_code, 
               ta.ratio_min, 
               ta.ratio_max, 
               ta.mi_dose_code, 
               ta.mi_dose_code_ref, 
               ta.dosage_form_group, 
               ta.storage_conditions_group_code,
               pg.ingred_group_id,
               ta.subing_id_nhpid, -- vp added to include sub_ingred in assessment doses
               ta.subing_name_nhpid, -- vp added to include sub_ingred in assessment doses
               ta.roa_id  -- vp add ROA to assessment doses
          from x_tmp_assess_doses ta, 
               v_ingredient_groups pg --x_ingredient_groups
         where 1=1
           and nvl(ta.product_mono_code,'x') = nvl(pg.product_mono_code,'x')
           and ta.mono_code = pg.single_mono_code
           and nvl(ta.group_id, ta.group_group_id) = nvl(pg.ingred_group_code, pg.ingred_group_group_code)
           and nvl(ta.combo_dose_code,'x') = nvl(pg.combo_dose_code,'x')
           and ta.ingred_id_nhpid = pg.ingred_id -- vp added to include sub_ingred in assessment doses
           and ta.subing_id_nhpid = pg.sub_ingred_id -- vp added to include sub_ingred in assessment doses
           --and (nvl(ta.ingred_id_nhpid,0) = nvl(pg.sub_ingred_id,0)
           -- or  nvl(lower(trim(ta.nhpid_name)),'x') = nvl(lower(trim(pg.sub_ingred_name)),'x'))
           and pg.sub_ingred_id is not null
) --select * from x where product_mono_code = 'HOMEO'
, xu as ( -- map NHPID unit IDs
select x.*, uq.units_id quantity_unit_id, ut.units_id frequency_unit_id
  from x, x_unit_of_measure xu, units uq, units ut 
 where decode(x.quantity_units,'PERCENT','PER','D','X',x.quantity_units) = xu.uom_submission_code(+)
   and nvl(to_number(xu.conversion_factor(+)),1) = 1
   and xu.uom_abbreviation = uq.units_code(+)
   --and xu.uom_type_code = uq.unitstype_code(+)
   and x.frequency_interval = ut.units_code(+)
) --select * from xu where product_mono_code = 'HOMEO'
, s as (
select * from x$mono_source_ingredient
union
select * from x$mono_source_sub_ingredient
) --select * from s where product_mono_code = 'HOMEO'
, t as ( -- join Excel doses + sources + monographs
select unique xu.*,
       --s.source_rule_code, 
       s.source_name source_nhpid_name, s. source_id source_ingred_id_nhpid,
       --m.mono_name, 
       nvl(s.mono_code_nhpid, m.mono_code)  nhpid_mono_code, 
       nvl(s.monograph_id, m.mono_id)  mono_id
  from xu, s, x$monograph_xref m
 where 1=1
   and xu.ingredient_id_nhpid = s.ingredient_id(+) 
   and xu.single_mono_code = s.mono_code(+)
   and nvl(xu.product_mono_code,'x') = nvl(s.product_mono_code(+),'x')
   and nvl(xu.single_mono_code,'x') = nvl(m.single_mono_code,'x')
   and nvl(xu.product_mono_code,'x') = nvl(m.product_mono_code,'x')
) --select * from t where product_mono_code = 'HOMEO'
--select unique use_code from x where product_mono_code = 'COG'
-- join all together
,a as (
select t.*, 
       t.ingredient_id_nhpid ingredient_id,
       t.source_ingred_id_nhpid source_id,
       t.mono_id monograph_id,
       dense_rank() over (order by t.ingredient_id_nhpid, t.source_ingred_id_nhpid, t.mono_id, 
                                   t.ingred_group_id,
                                   t.product_mono_code, t.single_mono_code, -- vp
                                   t.quantity_unit_id, t.frequency_unit_id,
                                   t.qdu_min, t.qdu_max, t.qie_min, t.qie_max,
                                   t.min_freq, t.max_freq, t.ratio_min, t.ratio_max,
                                   t.use_code, t.duration_code, t.risk_code, 
                                   t.dfu_group_code, t.prep_group_code, 
                                   t.sub_pop_code, t.dosage_form_group, 
                                   t.storage_conditions_group_code,
                                   t.mi_dose_type,
                                   t.sub_ingred_id_nhpid,
                                   t.roa_id
                                   ) assessment_dose_id
  from t
 where t.mono_id is not null and t.ingredient_id_nhpid is not null
)
select a.*, 0, trunc(sysdate), null, rownum from a 
;

drop table x_tmp_assess_doses;

