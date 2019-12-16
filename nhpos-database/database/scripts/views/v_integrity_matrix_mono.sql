create or replace view 
v_integrity_matrix_mono
as
with m as (
select unique trim(t.product_mono_code) product_mono_code,
       t.mono_code,
       t.ingred_id_nhpid
 from X_MONOGRAPH t, ingredients i
 where t.mono_code is not null and to_number(t.ingred_id_nhpid) = i.ingred_id--(+)
)
,i as (select  unique trim(x.product_mono_code) product_mono_code,
       x.mono_code,
       x.ingred_id_nhpid
from x_ingredient_matrix x
union -- x_monograph has only ingredient IDs
select  unique xs.product_mono_code,
       xs.mono_code,
       xs.ingred_id_nhpid
from x_sub_ingredient_matrix xs
)
select i.product_mono_code ingred_prod_mono_code,
       i.mono_code ingred_mono_code,
       i.ingred_id_nhpid ingred_ingred_id,
       m.product_mono_code mono_prod_mono_code,
       m.mono_code mono_mono_code,
       m.ingred_id_nhpid mono_ingred_ingred_id
  from i
  full outer join m
    on (nvl(i.product_mono_code,i.mono_code) = nvl(m.product_mono_code,m.mono_code)
   and  i.mono_code = m.mono_code
   and  i.ingred_id_nhpid = m.ingred_id_nhpid)
 where i.mono_code is null or m.mono_code is null
;
