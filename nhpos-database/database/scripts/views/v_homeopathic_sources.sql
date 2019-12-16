create or replace force view
v_homeopathic_sources
as
select m.product_mono_code,
       null single_mono_code,
       m.mono_code,
       m.mono_id,
       gt.homeogentext_name_eng source_name_eng, 
       gt.homeogentext_name_fr source_name_fr,
       hv.ingred_id ingredient_id, hv.ingred_id source_id
  from (select unique ingred_id, homeoform_id from V_HOMEOPATHIC_INGREDIENTS) hv,
       (select t.code product_mono_code, t.code mono_code, t.mono_id from monographs t where code  = 'HOMEO') m,
       HOMEOPATHIC_SOURCE_MAT_HGTS hs, 
       HOMEOPATHIC_GENERIC_TEXTS gt
 where hs.homeogentext_id = gt.homeogentext_id
   and hs.homeoform_id = hv.homeoform_id
;

