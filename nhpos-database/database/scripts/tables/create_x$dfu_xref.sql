--drop table x$dfu_xref cascade constraints;
--create table x$dfu_xref as
truncate table x$dfu_xref;
insert into x$dfu_xref
(dfu_id, dfu_group_code, dfu_group_decode_eng, dfu_group_decode_fr, 
 dfu_type, unique_dfu_code, unique_dfu_decode, unique_dfu_decode_fr,
 useracc_id, creation_date)
select rownum dfu_id, q.* from (
select t.dfu_group_code,
       nvl(t.unique_dfu_decode,t.dfu_group_decode) dfu_group_decode_eng,
       nvl(t.unique_dfu_decode_fr,t.dfu_group_decode_fr) dfu_group_decode_fr,
       t.dfu_type,
       t.unique_dfu_code, t.unique_dfu_decode, t.unique_dfu_decode_fr,
       0, trunc(sysdate)
  from X_DFU t
-- where not (t.dfu_group_code = 'DFU162' 
--   and t.dfu_group_decode_fr is null) 
--   and t.dfu_group_code <> 'DFU316'
 order by to_number(substr(t.dfu_group_code,4)), to_number(substr(t.unique_dfu_code,5))
) q
;
commit;

-- Create/Recreate primary, unique and foreign key constraints 
--alter table X$DFU_XREF add constraint X$DFU_XREF_PK primary key (DFU_ID);
--alter table X$DFU_XREF add constraint X$DFU_XREF_UK unique (DFU_GROUP_CODE);

