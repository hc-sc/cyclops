--drop table x$use_xref;
--create table x$use_xref as
truncate table x$use_xref;
insert into x$use_xref
(use_id, use_code, use_decode_eng, use_decode_fr, use_type, 
--product_mono_code, mono_code,
useracc_id, creation_date)
select rownum use_id, q.* from (
select t.use_code,
       t.use_decode use_decode_eng,
       t.use_decode_fr,
       t.monograph_type use_type,
       --t.product_mono_code,
       --t.mono_code, 
       0, trunc(sysdate)
  from X_USE t
 order by 1 --to_number(substr(t.use_code,4))
) q
;
commit;

-- Create/Recreate primary, unique and foreign key constraints 
--alter table X$USE_XREF add constraint X$USE_XREF_PK primary key (USE_ID);
--alter table X$USE_XREF add constraint X$USE_XREF_UK unique (USE_CODE);

