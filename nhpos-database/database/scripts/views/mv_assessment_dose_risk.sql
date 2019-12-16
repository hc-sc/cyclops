create materialized view
mv_assessment_dose_risk
build immediate
refresh force on demand
as
select /*+ parallel(4) */
       x.risk_id,
       ad.assessment_dose_id,
       m.code mono_code,
       --m.product_mono_code,
       --m.single_mono_code,
       m.mono_name_eng, 
       m.mono_name_fr,
       --
       sp.risk_code,
       spt.risk_type_code,
       sp.risk_decode_eng,
       sp.risk_decode_fr,
       spt.risk_type_decode_eng,
       spt.risk_type_decode_fr,
       ad.ingredient_id, 
       i.ingred_authorized_name_eng ingred_name_eng, 
       i.ingred_authorized_name_fr ingred_name_fr,
       ad.sub_ingredient_id, ad.source_ingredient_id,
       a.adminrt_code admin_route_code
  from x$assessment_dose_risk x,
       x$assessment_dose ad,
       x$risk_xref sp,
       x$risk_types spt,
       MONOGRAPHS m,
       INGREDIENTS i,
       ADMINISTRATION_ROUTES a
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.risk_id = sp.risk_id
   and sp.risk_type_id = spt.risk_type_id(+)
   and ad.monograph_id = m.mono_id
   and ad.ingredient_id = i.ingred_id
   and ad.roa_id = a.adminrt_id
;
