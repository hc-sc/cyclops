--drop table x$risk_types cascade constraints;
--create table x$risk_types as
truncate table x$risk_types;
insert into x$risk_types
(risk_type_id, risk_type_code, risk_type_decode_eng, risk_type_decode_fr,
useracc_id, creation_date)
select rownum risk_type_id, q.* from (
select unique
       t.risk_type_code,
       t.risk_type_decode risk_type_decode_eng,
       t.risk_type_fr risk_type_decode_fr, 
       0, trunc(sysdate)
  from X_RISK t
 where t.risk_code is not null and t.risk_type_fr is not null
 order by 1) q
;
commit;

--drop table x$risk_xref;
--create table x$risk_xref as
truncate table x$risk_xref;
insert into x$risk_xref
(risk_id, risk_code, risk_decode_eng, risk_decode_fr, risk_type_id,
useracc_id, creation_date)
select rownum risk_id, q.* from (
select t.risk_code,
       --t.in_use,
       t.risk_decode risk_decode_eng,
       t.risk_decode_fr,
       rt.risk_type_id,
       0, trunc(sysdate)
  from X_RISK t, x$risk_types rt
 where t.risk_type_code = rt.risk_type_code(+)
 order by to_number(replace(substr(t.risk_code,5),'x'))) q
;
commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$RISK_TYPES
  add constraint X$RISK_TYPES_PK primary key (RISK_TYPE_ID)
  using index;
alter table X$RISK_TYPES
  add constraint X$RISK_TYPES_UK unique (RISK_TYPE_CODE)
  using index;
alter table X$RISK_XREF
  add constraint X$RISK_XREF_PK primary key (RISK_ID)
  using index;
alter table X$RISK_XREF
  add constraint X$RISK_XREF_UK unique (RISK_CODE)
  using index;
alter table X$RISK_XREF
  add constraint X$RISK_XREF_FK foreign key (RISK_TYPE_ID)
  references X$RISK_TYPES (RISK_TYPE_ID);
*/
