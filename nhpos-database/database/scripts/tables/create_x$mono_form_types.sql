--drop table x$mono_form_types;
--create table x$mono_form_types as
truncate table x$mono_form_types;
insert into x$mono_form_types (mono_name, nhpid_mono_code, mono_form_type, mono_form_type_id)
select q.*, rownum mono_form_type_id from (
select mono_name, nhpid_mono_code, 
       decode(nhpid_mono_code,'HOMEO','H',
                              'SBPR2','SS',
                              'SBPR','SS',
                              'DECOMISSIONED','D',
                              decode(count(unique x.mono_code),1,'S',
                              'P')) mono_form_type
  from x_monograph x
 group by x.nhpid_mono_code, x.mono_name
) q;

commit;

--alter table x$mono_form_types add constraint x$mono_form_types_pk primary key (mono_form_type_id);

