--drop table x$sub_pop_sexes;
/*
create table X$SUB_POP_SEXES
(
  sex_id       NUMBER not null,
  sex_code     VARCHAR2(1),
  sex_desc_eng VARCHAR2(20),
  sex_desc_fr  VARCHAR2(20),
  constraint x$sub_pop_sexes_pk primary key (sex_id)
);
*/
truncate table x$sub_pop_sexes;
prompt Importing table X$SUB_POP_SEXES...
set feedback off
set define off
insert into X$SUB_POP_SEXES (SEX_ID, SEX_CODE, SEX_DESC_ENG, SEX_DESC_FR,useracc_id, creation_date)
values (0, 'N', 'N/A', 'N/A', 0, trunc(sysdate));
insert into X$SUB_POP_SEXES (SEX_ID, SEX_CODE, SEX_DESC_ENG, SEX_DESC_FR,useracc_id, creation_date)
values (1, 'M', 'Males', 'Hommes', 0, trunc(sysdate));
insert into X$SUB_POP_SEXES (SEX_ID, SEX_CODE, SEX_DESC_ENG, SEX_DESC_FR,useracc_id, creation_date)
values (2, 'F', 'Females', 'Femmes', 0, trunc(sysdate));

prompt Done.
