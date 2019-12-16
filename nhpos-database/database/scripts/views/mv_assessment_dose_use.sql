create materialized view
mv_assessment_dose_use
build immediate
refresh force on demand
as
select /*+ parallel(4) */
       x.use_id,
       ad.assessment_dose_id,
       m.code mono_code,
       --m.product_mono_code,
       --m.single_mono_code,
       m.mono_name_eng, 
       m.mono_name_fr,
       --
       sp.use_code ,
       sp.use_decode_eng,
       sp.use_decode_fr,
       decode(count(unique ad.ingredient_id) over (partition by ad.monograph_id, sp.use_code),1,'Specific','General') use_type,
       ad.ingredient_id, 
       i.ingred_authorized_name_eng ingred_name_eng, 
       i.ingred_authorized_name_fr ingred_name_fr,
       ad.sub_ingredient_id, ad.source_ingredient_id,
       a.adminrt_code admin_route_code
  from x$assessment_dose_use x,
       x$assessment_dose ad,
       x$use_xref sp,
       MONOGRAPHS m,
       INGREDIENTS i,
       ADMINISTRATION_ROUTES a
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.use_id = sp.use_id
   and ad.monograph_id = m.mono_id
   and ad.ingredient_id = i.ingred_id
   and ad.roa_id = a.adminrt_id
;
