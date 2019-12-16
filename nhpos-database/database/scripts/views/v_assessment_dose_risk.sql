create or replace force view 
v_assessment_dose_risk
as
select  x.risk_id,
        ad.assessment_dose_id,
        m.mono_code,
        m.product_mono_code,
        m.single_mono_code,
        --
        sp.risk_code,
        spt.risk_type_code,
        sp.risk_decode_eng,
        sp.risk_decode_fr,
        spt.risk_type_decode_eng,
        spt.risk_type_decode_fr,
        ad.ingredient_id, 
        ad.sub_ingredient_id, 
        ad.source_ingredient_id, 
        ad.monograph_id
  from x$assessment_dose_risk x,
       x$assessment_dose ad, 
       x$risk_xref sp,
       x$risk_types spt,
       x$monograph_xref m
 where x.assessment_dose_id = ad.assessment_dose_id
   and x.risk_id = sp.risk_id
   and sp.risk_type_id = spt.risk_type_id
   and ad.monograph_id = m.mono_id
;
