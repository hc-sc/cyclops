create or replace force view 
v_source_ingredient 
as
-- non compendial sources
select i.ingred_authorized_name_eng ingred_nhpid_name_eng,
       i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       si.ingred_authorized_name_eng source_nhpid_name_eng,
       si.ingred_authorized_name_fr source_nhpid_name_fr,
       null product_mono_code,
       null single_mono_code,
       null mono_code,
       null mono_id,
       i.ingred_id ingredient_id,
       si.ingred_id source_id,
       si.ingredcat_id src_name_type_id,
       p.prep_type_id preparation_id
  from SUBINGREDIENTS s,
       INGREDIENT_SUBINGREDIENTS ins,
       INGREDIENTS si,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where 1=1
   and s.subingred_id = ins.subingred_id
   and ins.ingred_id = si.ingred_id
   and s.ingred_id = i.ingred_id
   and s.subingred_id not in (select subingred_id from ORGANISM_PART_SUBINGREDIENTS)
   and si.ingred_id = p.ingred_id(+)
 union -- non compendial sources of type "source ingredient"
select i.ingred_authorized_name_eng,
       i.ingred_authorized_name_fr,
       ing.ingred_authorized_name_eng,
       ing.ingred_authorized_name_fr,
       null product_mono_code,
       null mono_code,
       null mono_code_nhpid,
       --'Source Ingredient',
       null mono_id,
       subs.ingred_id,
       ing.ingred_id,
       ing.ingredcat_id,
       p.prep_type_id preparation_id
  from INGREDIENTS ing,
       INGREDIENT_SUBINGREDIENTS ingsub,
       SUBINGREDIENTS subs,
       INGREDIENTS i,
       x$ingredient_prep_code p
 where ing.ingred_id = ingsub.ingred_id
   and ingsub.subingred_id = subs.subingred_id
   and ing.ingredspec_class_name = 'ChemicalSubstance'
   and i.ingred_id = subs.ingred_id
   and ing.ingred_id = p.ingred_id(+)
 union -- compendial sources
select m.ingred_nhpid_name_eng,
       m.ingred_nhpid_name_fr,
       m.source_nhpid_name_eng,
       m.source_nhpid_name_fr,
       m.product_mono_code,
       m.single_mono_code,
       m.mono_code,
       m.mono_id,
       m.ingredient_id,
       m.source_id,
       m.src_name_type_id,
       p.prep_type_id preparation_id
  from v_mono_source_ingredient m,
       x$organism_ingredient x,
       v_source_organism_parts Q,
       x$ingredient_prep_code p
 where 1=1
   and m.ingredient_id = x.ingred_id
   and nvl(m.product_mono_code,'x') = nvl(x.product_mono_code,'x')
   and m.single_mono_code = x.mono_code
   and m.source_id = x.source_id
   and x.organism_id is null 
   and x.orgparttype_id is null
   and x.ingred_id = q.ingred_id(+)
   and x.source_id = q.source_ingred_id(+)
   and nvl(x.product_mono_code,'x') = nvl(q.product_mono_code(+),'x')
   and x.mono_code = q.mono_code(+)
   --and x.orgparttype_id = q.source_org_parttype_id(+)
   and m.mono_id = p.mono_id(+)
   and m.source_id = p.ingred_id(+)
   and q.source_org_part_id is null 
;
