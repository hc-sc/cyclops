--drop table x$duration_xref;
--create table x$duration_xref as
truncate table x$duration_xref;
insert into x$duration_xref
(duration_id, duration_code, duration_decode_eng, 
 duration_decode_fr, min_duration, max_duration, unit_id,
 useracc_id, creation_date)
select rownum duration_id, q.*, 0, trunc(sysdate) from (
select t.duration_code,
       t.duration_decode duration_decode_eng,
       t.duration_decode_fr,
       to_number(t.min_duration) min_duration,
       to_number(t.max_duration) max_duration,
       u.units_id unit_id
  from X_DURATION t, UNITS u
 where replace(t.duration_units,'(s)') = u.units_name_eng(+)
 order by to_number(substr(t.duration_code,4))
) q
;
commit;

-- Create/Recreate primary, unique and foreign key constraints 
--alter table X$DURATION_XREF add constraint X$DURATION_XREF_PK primary key (DURATION_ID) using index;
--alter table X$DURATION_XREF add constraint X$DURATION_XREF_UK unique (DURATION_CODE) using index;

