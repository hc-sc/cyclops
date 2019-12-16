create or replace view 
v_source_types 
as
with vs as (
select unique source_nhpid_name_eng, source_nhpid_name_fr, source_id, src_name_type_id
  from v_mono_source_ingredient
)
select vs.source_nhpid_name_eng, vs.source_nhpid_name_fr,
       s.ingredspec_name_eng src_ingred_type_name_eng,--src_ingred_cat_name_eng,
       s.ingredspec_name_fr src_ingred_type_name_fr,--src_ingred_cat_name_fr,
       case when s.ingredspec_class_name in ('ChemicalSubstance','ProteinSubstance') then 'Source Ingredient'
            --when s.ingredspec_class_name in ('HerbalComponent','OrganismSubstance','DefinedOrganismSubstance') then 'Source Material'
            else 'Source Material' end source_type,
       case when s.ingredspec_class_name in ('ChemicalSubstance','ProteinSubstance') then 1
            else 2 end source_type_id,
       ic.ingredspec_id src_ingred_name_type_id,--src_ingred_cat_id,
       vs.source_id
  from INGREDIENT_SPECIALS s,
       INGREDIENT_CATEGORIES ic, vs
 where ic.ingredspec_id = s.ingredspec_id
   and ic.ingredcat_id = vs.src_name_type_id
;
