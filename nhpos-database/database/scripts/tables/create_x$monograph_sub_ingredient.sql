--drop table X$MONOGRAPH_SUB_INGREDIENT;
--create table X$MONOGRAPH_SUB_INGREDIENT as
truncate table X$MONOGRAPH_SUB_INGREDIENT;
insert into X$MONOGRAPH_SUB_INGREDIENT
(ingred_id, sub_ingred_id, mono_id, mono_code, 
product_mono_code, single_mono_code, sid,
useracc_id, creation_date)
select q.*, rownum sid,
       0, trunc(sysdate)
from (
select unique 
       --sx.parent_nhpid_name, sx.constituent_nhpid_name, 
       --sx.ingred_id_nhpid, sx.sub_ingred_id_nhpid, 
       i.ingred_id, s.ingred_id sub_ingred_id, 
       m.mono_id, m.code mono_code, sx.product_mono_code, sx.mono_code single_mono_code
  from x_sub_ingredient_matrix sx,
       x_monograph mx, 
       ingredients i, 
       ingredients s, 
       monographs m
where 1=1
and lower(trim(sx.parent_nhpid_name)) = lower(trim(i.ingred_authorized_name_eng))
and lower(trim(sx.subing_name_nhpid)) = lower(trim(s.ingred_authorized_name_eng))
--and nvl(sx.product_mono_code,'x') = nvl(mx.product_mono_code,'x')
--and nvl(sx.mono_code,'x') = nvl(mx.mono_code,'x')
and nvl(sx.product_mono_code,sx.mono_code) = nvl(mx.product_mono_code,mx.mono_code)
and mx.nhpid_mono_code = m.code(+)
) q;

commit;

/*
-- Add/modify columns 
alter table X$SUB_INGREDIENT modify ingred_id null;
alter table X$SUB_INGREDIENT modify sub_ingred_id null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$SUB_INGREDIENT
  add constraint X$SUB_INGREDIENT_PK unique (SID);
alter table X$SUB_INGREDIENT
  add constraint X$SUB_INGREDIENT_UK unique (ingred_id, sub_ingred_id, single_mono_code, product_mono_code, mono_code);
alter table X$SUB_INGREDIENT
  add constraint X$SUB_INGREDIENT_FK1 foreign key (INGRED_ID)
  references ingredients (INGRED_ID);
alter table X$SUB_INGREDIENT
  add constraint X$SUB_INGREDIENT_FK2 foreign key (SUB_INGRED_ID)
  references ingredients (INGRED_ID);
alter table X$SUB_INGREDIENT
  add constraint X$SUB_INGREDIENT_FK3 foreign key (MONO_ID)
  references monographs (MONO_ID);
*/