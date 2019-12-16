--drop table x$combo_doses cascade constraints;
--create table x$combo_doses as
truncate table x$combo_doses;
insert into x$combo_doses
(combo_dose_code, assessment_dose_id, ingredient_id, nhpid_name,
 product_mono_code, single_mono_code, mono_id,
 useracc_id, creation_date)
select unique 
       t.combo_dose_code,
       t.assessment_dose_id,
       t.ingredient_id,
       t.nhpid_name,
       t.product_mono_code,
       t.single_mono_code,
       t.monograph_id mono_id,
       0 useracc_id, trunc(sysdate) creation_date
from X$ASSESSMENT_DOSE_XREF t
where t.combo_dose_code is not null
;

commit;

/*
-- Create/Recreate primary, unique and foreign key constraints 
alter table X$COMBO_DOSES
  add constraint X$COMBO_DOSES_PK primary key (INGREDIENT_ID, ASSESSMENT_DOSE_ID);
alter table X$COMBO_DOSES
  add constraint X$COMBO_DOSES_FK1 foreign key (INGREDIENT_ID)
  references ingredients (INGRED_ID);
alter table X$COMBO_DOSES
  add constraint X$COMBO_DOSES_FK2 foreign key (ASSESSMENT_DOSE_ID)
  references x$assessment_dose (ASSESSMENT_DOSE_ID);
alter table X$COMBO_DOSES add constraint X$COMBO_DOSES_FK4 foreign key (combo_dose_code)
  references X$COMBO_DOSE_CODES (combo_dose_code);
*/

