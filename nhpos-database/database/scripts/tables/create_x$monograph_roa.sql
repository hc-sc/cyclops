--create table x$monograph_roa as
truncate table x$monograph_roa;
insert into x$monograph_roa (
mono_code, product_mono_code, nhpid_mono_code, roa, adminrt_id, mono_id, mono_roa_id, useracc_id, creation_date)
select q.*, rownum mono_roa_id, 0, trunc(sysdate) from (
select unique 
       t.mono_code,t.product_mono_code, t.nhpid_mono_code, t.roa, t.adminrt_id, x.mono_id
  from x_monograph t, x$monograph_xref x
 where t.roa is not null --1123
   and nvl(t.product_mono_code,'x') = nvl(x.product_mono_code,'x')
   and nvl(t.mono_code,'x') = nvl(x.single_mono_code,'x')
 order by 1,2) q
;
commit;

--alter table x$monograph_roa add constraint x$monograph_roa_PK primary key (mono_roa_id) using index tablespace NHPD_INDEX;
--alter table x$monograph_roa add constraint x$monograph_roa_FK1
