create materialized view
mv_assessment_doses
build immediate
refresh force on demand
as
select /*+ parallel(4) */
       m.code mono_code,
       ad.product_mono_code,
       ad.single_mono_code,
       m.mono_name_eng ,
       m.mono_name_fr ,
       m.mono_publish_date ,
       i.ingred_authorized_name_eng ingred_name_eng,
       i.ingred_authorized_name_fr ingred_name_fr,
       s.ingred_authorized_name_eng source_name_eng,
       s.ingred_authorized_name_fr source_name_fr,
       si.ingred_authorized_name_eng sub_ingredient_name_eng,
       si.ingred_authorized_name_fr sub_ingredient_name_fr,
       ad.qdu_min ,
       ad.qdu_max ,
       ad.qie_min ,
       ad.qie_max ,
       uq.units_code quantity_unit_code,
       ad.min_freq ,
       ad.max_freq ,
       nvl2(ad.min_freq, uf.units_code, nvl2(ad.max_freq, uf.units_code, null)) frequency_unit_code,
       ad.ratio_min ,
       ad.ratio_max ,
       ad.mi_dose_type ,
       ig.group_description ingred_group_desc,
       ig.group_group_description ingred_group_group_desc,
       r.admin_route_code,
       ad.assessment_dose_id ,
       ad.ingredient_id ,
       ad.sub_ingredient_id,
       ad.source_ingredient_id ,
       ad.monograph_id ,
       ad.quantity_unit_id ,
       ad.frequency_unit_id ,
       ad.ingredient_group_id,
       r.adminrt_id admin_route_id
  from x$assessment_dose ad,
       MONOGRAPHS m,
       INGREDIENTS i,
       INGREDIENTS s,
       INGREDIENTS si,
       UNITS uq, UNITS uf,
       x$ingredient_groups ig,
       v_monograph_roa r
 where ad.ingredient_id > 0
   and ad.monograph_id = m.mono_id
   and ad.ingredient_id = i.ingred_id
   and ad.source_ingredient_id = s.ingred_id(+)
   and ad.sub_ingredient_id = si.ingred_id(+)
   and ad.quantity_unit_id = uq.units_id(+)
   and ad.frequency_unit_id = uf.units_id(+)
   and ad.ingredient_group_id = ig.ingred_group_id(+)
   and ad.monograph_id = r.mono_id(+)
   and ad.roa_id = r.adminrt_id(+)
-- union all
--select /*+ parallel(4) */
--       m.code mono_code,
--       ad.product_mono_code,
--       ad.single_mono_code,
--       m.mono_name_eng ,
--       m.mono_name_fr ,
--       m.mono_publish_date ,
--       i.ingred_authorized_name_eng ingred_name_eng,
--       i.ingred_authorized_name_fr  ingred_name_fr,
--       null  source_name_eng,
--       null  source_name_fr,
--       null sub_ingredient_name_eng,
--       null sub_ingredient_name_fr,
--       ad.qdu_min ,
--       ad.qdu_max ,
--       ad.qie_min ,
--       ad.qie_max ,
--       null quantity_unit_code,
--       ad.min_freq ,
--       ad.max_freq ,
--       null frequency_unit_code,
--       ad.ratio_min ,
--       ad.ratio_max ,
--       ad.mi_dose_type ,
--       null ingred_group_desc,
--       null ingred_group_group_desc,
--       r.admin_route_code,
--       ad.assessment_dose_id ,
--       i.ingred_id ingredient_id ,
--       ad.sub_ingredient_id,
--       ad.source_ingredient_id ,
--       ad.monograph_id ,
--       ad.quantity_unit_id ,
--       ad.frequency_unit_id ,
--       ad.ingredient_group_id,
--       r.adminrt_id admin_route_id
--  from x$assessment_dose ad,
--       MONOGRAPHS m,
--       INGREDIENTS i,
--       INGREDIENT_CATEGORIES ic,
--       INGREDIENT_SPECIALS isp,
--       v_monograph_roa r
-- where ad.ingredient_id = 0
--   and ad.monograph_id = m.mono_id
--   and i.ingredcat_id = ic.ingredcat_id
--   and ic.ingredspec_id = isp.ingredspec_id
--   and isp.ingredspec_class_name = 'HomeopathicSubstance'
--   and ad.monograph_id = r.mono_id(+)
--   and ad.roa_id = r.adminrt_id(+)
;
