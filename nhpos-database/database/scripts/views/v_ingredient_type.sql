create or replace force view 
v_ingredient_type
as
select i.ingred_id,
       ic.ingredcat_code ingred_name_type_code,
       isp.ingredspec_class_name ingred_type_code,
       case
          when isp.ingredspec_name_eng = 'Organism Substance' then 'Organism'
          else isp.ingredspec_name_eng
       end as ingred_type_name_eng,
       case
         when isp.ingredspec_name_fr = 'Organism Substance (f)' then 'Organisme'
         when isp.ingredspec_name_fr = 'Chemical Substance (f)' then 'Substance chimique'
         when isp.ingredspec_name_fr = 'Defined Organism Substance (f)' then 'Substance dérivée d''un organisme define'
         when isp.ingredspec_name_fr = 'Protein Substance (f)' then 'Substance protéique'
         when isp.ingredspec_name_fr = 'Homeopathic Substance (f)' then 'Substance homéopatique'
         else isp.ingredspec_name_fr
       end as ingred_type_name_fr,
       nc.nhpclass_code nhp_class_code,
       nc.nhpclass_name_eng nhp_class_name_eng,
       nc.nhpclass_name_fr nhp_class_name_fr,
       isp.ingredspec_id ingred_type_id,
       nc.nhpclass_id nhp_class_id,
       ic.ingredcat_id ingred_name_type_id
  from INGREDIENTS i,
       INGREDIENT_CATEGORIES ic,
       INGREDIENT_SPECIALS isp,
       INGREDIENT_NHPCLASSIFICATIONS inc,
       NHP_CLASSIFICATIONS nc
 where i.ingredcat_id = ic.ingredcat_id
   and ic.ingredspec_id = isp.ingredspec_id
   and i.ingred_id = inc.ingred_id(+)
   and inc.nhpclass_id = nc.nhpclass_id(+)
;

