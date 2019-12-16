create or replace view 
v_mono_source_ingredient 
as
select unique
       m.code mono_code, m.mono_name_eng, m.mono_name_fr,
       msi.product_mono_code, msi.mono_code single_mono_code,
       i.ingred_authorized_name_eng ingred_nhpid_name_eng, i.ingred_authorized_name_fr ingred_nhpid_name_fr,
       s.ingred_authorized_name_eng source_nhpid_name_eng, s.ingred_authorized_name_fr source_nhpid_name_fr,
       --'Source '||nvl2(x.organism_id,'Material','Ingredient') source_type,
       'Source '||nvl2(nvl(x.organism_id,x.orgparttype_id),'Material','Ingredient') source_type, -- vp change to part
       msi.monograph_id mono_id, msi.ingredient_id, msi.source_id,
       s.ingredcat_id src_name_type_id
       --x.organism_id, x.orgparttype_id, x.source_material_strain
  from x$mono_source_ingredient msi,
       MONOGRAPHS m,
       INGREDIENTS i,
       INGREDIENTS s,
       x$organism_ingredient x
 where 1=1
   and msi.monograph_id = m.mono_id
   and msi.ingredient_id = i.ingred_id
   and msi.source_id = s.ingred_id(+)
   and nvl(msi.source_id, msi.ingredient_id) = x.ingred_id(+)
;

create or replace public synonym v_mono_source_ingredient for v_mono_source_ingredient;
grant select on v_mono_source_ingredient to public;

