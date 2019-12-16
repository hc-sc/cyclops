CREATE OR REPLACE VIEW 
V_ASSESSMENT_DOSE_GUI 
AS
SELECT (SELECT m.code
           FROM monographs m
           WHERE ad.monograph_id = m.mono_id) mono_code
        , ad.product_mono_code
        , ad.single_mono_code
        , (SELECT m.mono_name_eng
           FROM monographs m
           WHERE ad.monograph_id = m.mono_id) mono_name_eng
        , (SELECT m.mono_name_fr
           FROM monographs m
           WHERE ad.monograph_id = m.mono_id) mono_name_fr
        , (SELECT m.mono_publish_date
           FROM monographs m
           WHERE ad.monograph_id = m.mono_id) mono_publish_date
        , (SELECT i.ingred_authorized_name_eng
           FROM ingredients i
           WHERE ad.ingredient_id = i.ingred_id) ingred_name_eng
        , (SELECT i.ingred_authorized_name_fr
           FROM ingredients i
           WHERE ad.ingredient_id = i.ingred_id) ingred_name_fr
        , (SELECT s.ingred_authorized_name_eng
           FROM ingredients s
           WHERE ad.source_ingredient_id = s.ingred_id) source_name_eng
        , (SELECT s.ingred_authorized_name_fr
           FROM ingredients s
           WHERE ad.source_ingredient_id = s.ingred_id) source_name_fr
        , (SELECT si.ingred_authorized_name_eng
           FROM ingredients si
           WHERE ad.sub_ingredient_id = si.ingred_id) sub_ingredient_name_eng
        , (SELECT si.ingred_authorized_name_fr
           FROM ingredients si
           WHERE ad.sub_ingredient_id = si.ingred_id) sub_ingredient_name_fr
        , ad.qdu_min
        , ad.qdu_max
        , ad.qie_min
        , ad.qie_max
        , (SELECT uq.units_code
           FROM units uq
           WHERE ad.quantity_unit_id = uq.units_id) quantity_unit_code
        , ad.min_freq
        , ad.max_freq
        , (SELECT NVL2 (ad.min_freq, uf.units_code, NVL2 (ad.max_freq, uf.units_code, NULL))
           FROM units uf
           WHERE ad.quantity_unit_id = uf.units_id) frequency_unit_code
        , ad.ratio_min
        , ad.ratio_max
        , ad.mi_dose_type
        , (SELECT ig.group_description
           FROM x$ingredient_groups ig
           WHERE ad.ingredient_group_id = ig.ingred_group_id) ingred_group_desc
        , (SELECT ig.group_group_description
           FROM x$ingredient_groups ig
           WHERE ad.ingredient_group_id = ig.ingred_group_id) ingred_group_group_desc
        , ad.assessment_dose_id
        , ad.ingredient_id
        , ad.sub_ingredient_id
        , ad.source_ingredient_id
        , ad.monograph_id
        , ad.quantity_unit_id
        , ad.frequency_unit_id
        , ad.ingredient_group_id
   FROM x$assessment_dose ad
;


