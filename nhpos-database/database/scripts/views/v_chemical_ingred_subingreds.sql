create or replace force view 
v_chemical_ingred_subingreds
as
select i.ingred_id,
       pi.ingred_id sub_ingred_id,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng,--ingred_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,--ingred_name_fr,
       pi.ingred_authorized_name_eng sub_ingred_nhpid_name_eng,--sub_ingred_name_eng,
       pi.ingred_authorized_name_fr sub_ingred_nhpid_name_fr,--sub_ingred_name_fr,
       null mono_id, null mono_code
  from INGREDIENTS i,
       INGREDIENT_SUBINGREDIENTS isu,
       SUBINGREDIENTS si,
       INGREDIENTS pi,
       --
       INGREDIENT_CATEGORIES ic,
       INGREDIENT_SPECIALS ins
 where 1=1
   and i.ingred_id = isu.ingred_id
   and isu.subingred_id = si.subingred_id
   and si.ingred_id = pi.ingred_id
   --
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = ins.ingredspec_id
   and ins.ingredspec_name_eng = 'Chemical Substance'
 union all
-- monograph
select i.ingred_id,
       pi.ingred_id sub_ingred_id,
       i.ingred_authorized_name_eng ingred_name_eng,
       i.ingred_authorized_name_fr ingred_name_fr,
       pi.ingred_authorized_name_eng sub_ingred_name_eng,
       pi.ingred_authorized_name_fr sub_ingred_name_fr,
       m.mono_id, m.mono_code
  from INGREDIENTS i,
       INGREDIENT_SUBINGREDIENTS isu,
       SUBINGREDIENTS si,
       INGREDIENTS pi,
       --
       INGREDIENT_CATEGORIES ic,
       INGREDIENT_SPECIALS ins,
       x$monograph_sub_ingredient m
 where 1=1
   and i.ingred_id = isu.ingred_id
   and isu.subingred_id = si.subingred_id
   and si.ingred_id = pi.ingred_id
   --
   and i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = ins.ingredspec_id
   and ins.ingredspec_name_eng = 'Chemical Substance'
   --
   and pi.ingred_id = m.sub_ingred_id
   and m.ingred_id = i.ingred_id
;

