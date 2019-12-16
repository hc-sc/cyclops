create or replace force view
v_homeopathic_ingredients
as
select distinct
       i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
       cg.homeogentext_name_eng common_name_eng, cg.homeogentext_name_fr common_name_fr,
       pg.homeogentext_name_eng proper_name_eng, pg.homeogentext_name_fr proper_name_fr,
       ic.ingredcat_code ingred_name_type_code,--ingred_category_code, ic.ingredcat_name_eng ingred_category_eng, ic.ingredcat_name_fr ingred_category_fr,
       isp.ingredspec_class_name ingred_type_code, isp.ingredspec_name_eng ingred_type_name_eng, isp.ingredspec_name_fr ingred_type_name_fr,
       hf.homeoform_atf_required is_atf_required, 
       hd.homeodilu_code dilution_code,
       case when hd.homeodilu_code = 'MT' then 'n' else 'y' end is_diluted,
       to_char(hm.homeomop_minimum)||hd.homeodilu_code potency, 
       hp.homeopreptype_code prep_type_code,
       sgr.ref_code standard_or_grade_code,
       sgr.ref_name_eng standard_or_grade_name_eng,
       sgr.ref_name_fr standard_or_grade_name_fr,
       --rl.rolespec_name_eng role_eng, rl.rolespec_name_fr role_fr,
       cg.homeogentext_id common_name_id, 
       pg.homeogentext_id proper_name_id,
       hp.homeopreptype_id standard_or_grade_id, 
       hd.homeodilu_id, hf.homeoform_id,
       ic.ingredcat_id ingred_name_type_id, 
       isp.ingredspec_id ingred_type_id--, rl.ingredrole_id, rl.rolespec_id
  from INGREDIENTS i,
       INGREDIENT_CATEGORIES ic,
       INGREDIENT_SPECIALS isp,
       HOMEOPATHIC_COMMON_NAME_HGTS cn,
       HOMEOPATHIC_GENERIC_TEXTS cg,
       HOMEOPATHIC_PROPER_NAME_HGTS pn,
       HOMEOPATHIC_GENERIC_TEXTS pg,
       HOMEOPATHIC_DILUTIONS hd,
       HOMEOPATHIC_FORMULAS hf,
       HOMEOPATHIC_PREPARATION_TYPES hp,
       HOMEOPATHIC_METHOFPREPS hm,
       v_standard_grade_references sgr,
       v_ingredient_role rl
where i.ingred_id = cn.ingred_id
   and hp.homeopreptype_id = hm.homeopreptype_id
   and hm.homeoform_id = hf.homeoform_id
   and rl.ingredrole_id = hf.ingredrole_id
   and hf.homeodilu_id = hd.homeodilu_id
   and cn.homeogentext_id = cg.homeogentext_id
   and i.ingred_id = pn.ingred_id
   and pn.homeogentext_id = pg.homeogentext_id
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
   and i.ingred_id = rl.ingred_id
   and i.ref_id = sgr.ref_id
   and isp.ingredspec_name_eng = 'Homeopathic Substance'
;

--grant select on v_homeopathic_ingredients to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_homeopathic_ingredients for nhpdweb_dev.v_homeopathic_ingredients;
