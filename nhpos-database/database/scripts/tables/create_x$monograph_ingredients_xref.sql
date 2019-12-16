--drop table x$monograph_ingredients_xref;
--create table x$monograph_ingredients_xref as
truncate table x$monograph_ingredients_xref;
insert into x$monograph_ingredients_xref
(product_mono_code, single_mono_code, mono_code, mono_name, 
 ingred_id, ingred_nhpd_name, mono_id, mono_ingred_id,
 useracc_id, creation_date)
with m as (
select unique trim(t.product_mono_code) product_mono_code,
       t.mono_code, t.nhpid_mono_code, t.mono_name,
       t.ingred_id_nhpid, i.ingred_authorized_name_eng ingred_nhpd_name
 from x_monograph t, INGREDIENTS i
 where t.mono_code is not null and to_number(t.ingred_id_nhpid) = i.ingred_id
)
,i as (select unique trim(x.product_mono_code) product_mono_code,
       x.mono_code, xm.nhpid_mono_code, xm.mono_name,
       x.ingred_id_nhpid, x.nhpid_name ingred_nhpd_name
  from x_ingredient_matrix x, x_monograph xm
 where nvl(x.product_mono_code,'x') = nvl(xm.product_mono_code(+),'x') and x.mono_code = xm.mono_code(+)
union
select  unique xs.product_mono_code,
       xs.mono_code, xm.nhpid_mono_code, xm.mono_name,
       xs.subing_id_nhpid, xs.subing_name_nhpid
from x_sub_ingredient_matrix xs, x_monograph xm
 where nvl(xs.product_mono_code,'x') = nvl(xm.product_mono_code(+),'x') and xs.mono_code = xm.mono_code(+)
)
,mi as (
select product_mono_code, mono_code single_mono_code, nhpid_mono_code mono_code, 
       mono_name, to_number(ingred_id_nhpid) ingred_id, ingred_nhpd_name from m
union
select product_mono_code, mono_code single_mono_code, nhpid_mono_code mono_code, 
       mono_name, to_number(ingred_id_nhpid) ingred_id, ingred_nhpd_name from i
)
select mi.*, mn.mono_id, rownum mono_ingred_id,
       0, trunc(sysdate)
  from mi, MONOGRAPHS mn
 where mi.mono_code = mn.code(+)
;
commit;

/*
------1 GEM SEQ-NM  GEMMO Gemmotherapy  
------23    CAY_O CAY_O Cayenne - Capsicum annuum - Oral  5973
------24    CAY_T CAY_T Cayenne - Capsicum annuum - Topical 5973
----
---- Create/Recreate primary, unique and foreign key constraints 
alter table X$MONOGRAPH_INGREDIENTS_XREF
  add constraint X$MONOGRAPH_INGRED_XREF_PK primary key (mono_ingred_id);
alter table X$MONOGRAPH_INGREDIENTS_XREF
  add constraint X$MONOGRAPH_INGRED_XREF_FK1 foreign key (MONO_ID)
  references monographs (MONO_ID);
alter table X$MONOGRAPH_INGREDIENTS_XREF
  add constraint X$MONOGRAPH_INGRED_XREF_FK2 foreign key (INGRED_ID)
  references ingredients (INGRED_ID);
*/