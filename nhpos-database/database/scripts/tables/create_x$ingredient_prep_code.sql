--drop table x$ingredient_prep_code;
--create table x$ingredient_prep_code as
truncate table x$ingredient_prep_code;
insert into x$ingredient_prep_code
(ingred_id, nhpid_name, prep_group_code, prep_type_id, source_group_code, 
product_mono_code, single_mono_code, mono_code, mono_id, source_prep_code, ingred_prep_code_id, useracc_id, creation_date)
with q as (
select --unique 
       to_number(xi.ingred_id_nhpid) ingred_id, xi.nhpid_name, 
       upper(xi.prep_group_code) prep_group_code,
       xpm.prep_type_id, xi.source_group_code,
       xi.product_mono_code, xi.mono_code single_mono_code,
       xmi.mono_code, xmi.mono_id,
       null source_prep_code
  from x_ingredient_matrix xi, 
       x$preparation_methods_xref xpm,
       x$monograph_ingredients_xref xmi
 where upper(xi.prep_group_code) = upper(xpm.prep_group_code)
   and nvl(xi.product_mono_code,'x') = nvl(xmi.product_mono_code,'x')
   and  xi.mono_code = xmi.single_mono_code
   and to_number(xi.ingred_id_nhpid) = xmi.ingred_id
union
select to_number(xi.subing_id_nhpid)  ingred_id, xi.subing_name_nhpid, 
       upper(xi.prep_group_code) subing_prep_code,
       xpm.prep_type_id, xi.source_group_code,
       xi.product_mono_code, xi.mono_code single_mono_code, 
       xmi.mono_code, xmi.mono_id,
       null source_prep_code
  from x_sub_ingredient_matrix xi, 
       x$preparation_methods_xref xpm,
       x$monograph_ingredients_xref xmi
 where xi.prep_group_code = xpm.prep_group_code
   and nvl(xi.product_mono_code,'x') = nvl(xmi.product_mono_code,'x')
   and  xi.mono_code = xmi.single_mono_code
   and to_number(xi.ingred_id_nhpid) = xmi.ingred_id
union
select --unique 
       to_number(si.source_ingred_id_nhpid) ingred_id, si.source_nhpid_name, 
       null prep_group_code,
       xpm.prep_type_id, si.source_group_code,
       si.product_mono_code, si.mono_code single_mono_code,
       xmi.mono_code, xmi.mono_id,
       upper(si.source_prep_code) source_prep_code
  from x_sources si, 
       x$preparation_methods_xref xpm,
       x$monograph_ingredients_xref xmi
 where upper(si.source_prep_code) = upper(xpm.prep_group_code)
   and nvl(si.product_mono_code,'x') = nvl(xmi.product_mono_code,'x')
   and  si.mono_code = xmi.single_mono_code
   and to_number(si.source_ingred_id_nhpid) = xmi.ingred_id
union
select unique 
       to_number(si.source_ingred_id_nhpid) ingred_id, si.source_nhpid_name, 
       null prep_group_code,
       xpm.prep_type_id, si.source_group_code,
       si.product_mono_code, si.mono_code single_mono_code,
       xmi.mono_code_nhpid mono_code, 
       xmi.monograph_id mono_id,
       upper(si.source_prep_code) source_prep_code
  from x_sources si, 
       x$preparation_methods_xref xpm,
       x$mono_source_ingredient xmi --x$monograph_ingredients_xref xmi
 where upper(si.source_prep_code) = upper(xpm.prep_group_code)
   and nvl(si.product_mono_code,'x') = nvl(xmi.product_mono_code,'x')
   and  si.mono_code = xmi.mono_code --single_mono_code
   and to_number(si.source_ingred_id_nhpid) = xmi.source_id --ingred_id
)
select unique q.*, rownum ingred_prep_code_id,  0, trunc(sysdate) from q 
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$INGREDIENT_PREP_CODE
  add constraint X$INGREDIENT_PREP_CODE_PK primary key (ingred_prep_code_id);
alter table X$INGREDIENT_PREP_CODE
  add constraint X$INGREDIENT_PREP_CODE_FK1 foreign key (INGRED_ID)
  references ingredients (INGRED_ID);
alter table X$INGREDIENT_PREP_CODE
  add constraint X$INGREDIENT_PREP_CODE_FK2 foreign key (PREP_TYPE_ID)
  references preparation_types (PREPTYPE_ID) novalidate;
*/
