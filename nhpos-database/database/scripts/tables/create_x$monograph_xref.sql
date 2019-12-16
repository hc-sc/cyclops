--drop table x$monograph_xref;
--create table x$monograph_xref as
truncate table x$monograph_xref;
insert into x$monograph_xref (mono_id, mono_code, mono_name_eng, mono_name_fr, product_mono_code, single_mono_code, mid,
useracc_id, creation_date)
select x.*, rownum mid, 0, trunc(sysdate) from (
select --unique 
       m.mono_id,
       nvl(m.code,q.nhpid_mono_code) mono_code, 
       nvl(m.mono_name_eng,q.mono_name) mono_name_eng,
       m.mono_name_fr,
       q.product_mono_code,
       q.mono_code single_mono_code
  from MONOGRAPHS m, x_monograph q
 where q.nhpid_mono_code = m.code(+)
   and q.nhpid_mono_code <> 'DECOMISSIONED'
union
select --unique 
       m.mono_id,
       q.nhpid_mono_code, 
       q.mono_name,
       m.mono_name_fr,
       q.product_mono_code,
       q.mono_code single_mono_code
  from MONOGRAPHS m, x_monograph q
 where q.mono_name = m.mono_name_eng(+)
   and not exists (select 1 from MONOGRAPHS t where t.code = q.nhpid_mono_code)
order by 1
) x;

commit;

/*
alter table X$MONOGRAPH_XREF
  add constraint X$MONOGRAPH_XREF_PK primary key (MID);
--alter table X$MONOGRAPH_XREF
--  add constraint X$MONOGRAPH_XREF_UK unique (MONO_ID);
alter table X$MONOGRAPH_XREF
  add constraint X$MONOGRAPH_XREF_FK1 foreign key (MONO_ID)
  references monographs (MONO_ID);
*/
-- missing monographs
/*
select unique 
       nvl(x.product_mono_code, x.mono_code) mono_code, x.mono_name, x.nhpid_mono_code 
  from x_monograph x
 where x.mono_name is not null
minus
select unique 
       nvl(q.product_mono_code, q.mono_code) mono_code, q.mono_name, q.nhpid_mono_code 
from monographs m, x_monograph q
where q.nhpid_mono_code = m.code
   or lower(trim(q.mono_name)) = lower(trim(m.mono_name_eng))
*/
/*
2739/CAY - Cayenne - Capsicum annuum - Oral
2739/CAY - Cayenne - Capsicum annuum - Topical
1146/7213_AC - DL-alpha Lipoic acid
164/SJW_HE - St. John's Wort - Hydroalcoholic Extract
Dos not exists - Secondary Sunscreen Monograph
*/
