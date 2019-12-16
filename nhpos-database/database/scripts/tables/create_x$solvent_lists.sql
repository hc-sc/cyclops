--drop table x$solvent_solvent_lists;
--drop table x$solvent_lists cascade constraints;
--drop table x$solvents;
--
--create table x$solvent_lists as
truncate table x$solvent_lists;
insert into x$solvent_lists
(solvent_list_id, solvent_list_decode_eng, solvent_list_decode_fr,useracc_id, creation_date)
select unique
       to_number(substr(t.solvent_group_code,3)) solvent_list_id, 
       t.solvent_group_decode solvent_list_decode_eng,
       t.solvent_group_decode||' (f)' solvent_list_decode_fr, 0, trunc(sysdate)
  from X_SOLVENT_LIST t
 order by 1
;
commit;

--alter table x$solvent_lists add constraint x$solvent_lists_pk primary key (solvent_list_id);
--
--drop table x$solvent_solvent_lists;
--create table x$solvent_solvent_lists as
truncate table x$solvent_solvent_lists;
insert into x$solvent_solvent_lists
(solvent_list_id, solvent_id,useracc_id, creation_date)
select unique to_number(substr(t.solvent_group_code,3)) solvent_list_id, s.solvents_id solvent_id, 0, trunc(sysdate) 
  from X_SOLVENT_LIST t, solvents s
 where t.solvent_nhpid_code = s.solvents_code
;
commit;

/*
alter table x$solvent_solvent_lists add constraint x$solvent_solvent_lists_pk primary key (solvent_list_id, solvent_id);
alter table x$solvent_solvent_lists add constraint x$solvent_solvent_lists_fk1 
  foreign key (solvent_id) references SOLVENTS (solvents_id);
alter table x$solvent_solvent_lists add constraint x$solvent_solvent_lists_fk2 
  foreign key (solvent_list_id) references x$solvent_lists (solvent_list_id);
alter table X$PREPARATION_METHODS_XREF add constraint X$PREPARATION_METHODS_XREF_FK2
  foreign key (solvent_list_id) references x$solvent_lists (solvent_list_id);
*/
