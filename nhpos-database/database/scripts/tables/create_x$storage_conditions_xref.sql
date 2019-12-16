--drop table x$storage_conditions_xref;
--create table x$storage_conditions_xref as
truncate table x$storage_conditions_xref;
insert into x$storage_conditions_xref
(storage_cond_id, storage_cond_code, storage_cond_desc_eng, storage_cond_desc_fr,
useracc_id, creation_date)
select rownum storage_cond_id, q.*, 0, trunc(sysdate) from (
select unique
       t.storage_cond_group_code storage_cond_code,
       t.storage_cond_group_decode storage_cond_desc_eng,
       t.storage_cond_group_decode_fr storage_cond_desc_fr 
  from X_STORAGE_CONDITIONS t
 union
select t.storage_cond_code,
       t.storage_cond_decode,
       t.storage_cond_decode_fr
  from X_STORAGE_CONDITIONS t
 where storage_cond_code is not null
 order by 1
) q
;

commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$STORAGE_CONDITIONS_XREF
  add constraint X$STORAGE_CONDITIONS_XREF_PK primary key (STORAGE_COND_ID)
  using index;
alter table X$STORAGE_CONDITIONS_XREF
  add constraint X$STORAGE_CONDITIONS_XREF_UK unique (STORAGE_COND_CODE)
  using index;
*/
