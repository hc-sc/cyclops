-- Create table
/*
drop table X$ASSESSMENT_DOSE cascade constraints;
create table X$ASSESSMENT_DOSE
(
  assessment_dose_id   NUMBER not null,
  ingredient_id        NUMBER not null,
  source_ingredient_id NUMBER, --not null,
  monograph_id         NUMBER not null,
  sub_ingredient_id    NUMBER, --not null,
  quantity_unit_id     NUMBER,
  frequency_unit_id    NUMBER,
  ingredient_group_id  NUMBER,
  qdu_min              NUMBER,
  qdu_max              NUMBER,
  qie_min              NUMBER,
  qie_max              NUMBER,
  min_freq             NUMBER,
  max_freq             NUMBER,
  ratio_min            NUMBER,
  ratio_max            NUMBER,
  mi_dose_type         VARCHAR2(255),
  product_mono_code    varchar2(20), 
  single_mono_code     varchar2(20)
--  alt_assessment_dose_id NUMBER
);
*/
truncate table X$ASSESSMENT_DOSE;
alter table x$assessment_dose nologging;
insert /*+ append */ into x$assessment_dose
( assessment_dose_id, ingredient_id, source_ingredient_id, monograph_id,
 sub_ingredient_id, quantity_unit_id, frequency_unit_id,
 ingredient_group_id, qdu_min, qdu_max, qie_min, qie_max,
 min_freq, max_freq, ratio_min, ratio_max, mi_dose_type,
 product_mono_code,  single_mono_code, roa_id,
 useracc_id, creation_date)
select unique
      assessment_dose_id,
      ingredient_id,
      source_id,
      monograph_id,
      sub_ingred_id_nhpid,
      quantity_unit_id,
      frequency_unit_id,
      ingred_group_id,
      qdu_min,
      qdu_max,
      qie_min,
      qie_max,
      min_freq,
      max_freq,
      ratio_min,
      ratio_max,
      mi_dose_type,
      product_mono_code, 
      single_mono_code,
      roa_id,
      0, trunc(sysdate)
  from X$ASSESSMENT_DOSE_XREF t
;
commit;
alter table x$assessment_dose logging;

-- Populate alt_assesment_dose_id
--drop table x$alt_assessment_dose;
--create table x$alt_assessment_dose as
truncate table x$alt_assessment_dose;
insert into x$alt_assessment_dose
(assessment_dose_id, alt_assessment_dose_id, 
 mi_dose_code, mi_dose_code_ref, mi_dose_type,
 useracc_id, creation_date)
select unique
       p.assessment_dose_id, 
       c.assessment_dose_id alt_assessment_dose_id,
       p.mi_dose_code, p.mi_dose_code_ref, p.mi_dose_type,
       0, trunc(sysdate)
       --c.mi_dose_code pc, c.mi_dose_code_ref cref,
  from x$assessment_dose_xref p, x$assessment_dose_xref c
 where p.mi_dose_code = c.mi_dose_code_ref
   and p.mi_dose_code is not null
;
commit;

--update (select x.pid, x.cid, a.assessment_dose_id, a.alt_assessment_dose_id 
--          from x$assessment_dose a, x$alt_dose x
--         where a.assessment_dose_id = x.pid)
--   set alt_assessment_dose_id = cid
--;
--commit;
/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_PK primary key (ASSESSMENT_DOSE_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK1 foreign key (INGREDIENT_ID)
  references ingredients (INGRED_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK2 foreign key (SOURCE_INGREDIENT_ID)
  references ingredients (INGRED_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK3 foreign key (MONOGRAPH_ID)
  references monographs (MONO_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK4 foreign key (quantity_unit_id)
  references UNITS (UNITS_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK5 foreign key (frequency_unit_id)
  references UNITS (UNITS_ID);
alter table X$ASSESSMENT_DOSE add constraint X$ASSESSMENT_DOSE_FK6 foreign key (ingredient_group_id)
  references X$INGREDIENT_GROUPS (ingred_group_id);

alter table x$alt_assessment_dose add constraint x$alt_assessment_dose_pk primary key (assessment_dose_id, alt_assessment_dose_id);
alter table x$alt_assessment_dose add constraint x$alt_assessment_dose_fk1
  foreign key (assessment_dose_id) references x$assessment_dose (assessment_dose_id);
alter table x$alt_assessment_dose add constraint x$alt_assessment_dose_fk2
  foreign key (alt_assessment_dose_id) references x$assessment_dose (assessment_dose_id);
*/


