--drop table x$ingredient_groups cascade constraints;
--truncate table x_ingredient_combo_doses;
--insert into  x_ingredient_combo_doses
--(product_mono_code, mono_code, group_rule_group_id, rule_group_id,
-- ingred_id_nhpid, nhpid_name, combo_dose_code)
drop table x_ingredient_combo_doses;
create table x_ingredient_combo_doses as 
select i.product_mono_code, i.mono_code, i.group_rule_group_id, i.rule_group_id, i.combo_dose_code, 
       i.ingred_id_nhpid, i.nhpid_name, null subing_id_nhpid, null subing_name_nhpid 
  from X_INGREDIENT_MATRIX i -- add only ingredients
 where i.combo_dose_code is not null
union -- add ingred - sub-ingred in-line
select s.product_mono_code, s.mono_code, s.group_rule_group_id, s.rule_group_id, s.combo_dose_code,
       s.ingred_id_nhpid, s.parent_nhpid_name, s.subing_id_nhpid, s.subing_name_nhpid
  from X_SUB_INGREDIENT_MATRIX s
 where s.combo_dose_code is not null
union -- add sub-ingredients as ingredients
select s.product_mono_code, s.mono_code, s.group_rule_group_id, s.rule_group_id, s.combo_dose_code,
       s.subing_id_nhpid, s.subing_name_nhpid, null subing_id_nhpid, null subing_name_nhpid
  from X_SUB_INGREDIENT_MATRIX s
 where s.combo_dose_code is not null
;
commit;

--create table x$combo_dose_codes as
truncate table x$combo_dose_codes;
insert into x$combo_dose_codes (combo_dose_code, useracc_id, creation_date)
select combo_dose_code, 0, trunc(sysdate)
from (select unique combo_dose_code from x_ingredient_combo_doses t where t.combo_dose_code is not null)
order by 1
;
commit;

--alter table X$COMBO_DOSE_CODES add constraint x$combo_dose_codes_pk primary key (combo_dose_code);

--drop table x$ingredient_group_ingredients;
--drop table x$ingredient_group_groups;
--create table x$ingredient_group_groups as
truncate table x$ingredient_group_groups;
insert into x$ingredient_group_groups
(ingred_group_group_id, ingred_group_group_code, useracc_id, creation_date)
select rownum ingred_group_group_id, q.*, 0, trunc(sysdate)
  from (select --unique 
               t.group_group_id ingred_group_group_code --, 
               --t.group_group_description ingred_group_group_desc
          from X_INGREDIENT_GROUPS t
         where t.group_group_id is not null
         union -- added combo group ID
        select c.group_rule_group_id 
          from x_ingredient_combo_doses c
         order by 1
) q
;
commit;

--alter table x$ingredient_group_groups 
--  add constraint x$ingredient_group_groups_pk primary key (ingred_group_group_id);

--create table x$ingredient_group_ingredients as
truncate table x$ingredient_group_ingredients;
insert into x$ingredient_group_ingredients
(ingred_group_ingred_id, product_mono_code, single_mono_code, mono_id, 
 ingred_nhpid_name, ingred_nhpid_id, sub_ingred_nhpid_name, sub_ingred_nhpid_id,
 useracc_id, creation_date)
select rownum ingred_group_ingred_id, q.*, 0, trunc(sysdate)
  from (
          select -- sub_ingredients is in-line with ingredients
               t.product_mono_code,
               t.mono_code single_mono_code, 
               m.mono_id,
               t.ingredient_nhpid_name ingred_nhpid_name,
               to_number(t.nhpid_ingred_id) ingred_nhpid_id,
               t.sub_ingredient_nhpid_name sub_ingred_nhpid_name,
               to_number(t.sub_ingred_id_nhpid) sub_ingred_nhpid_id
          from x_ingredient_groups t, 
               x$monograph_xref m 
               --x$sub_ingredient s,
               --INGREDIENTS i
         where nvl(t.product_mono_code,'x') = nvl(m.product_mono_code,'x')
           and t.mono_code = m.single_mono_code
           --and t.nhpid_ingred_id = s.ingred_id
           --and nvl(t.product_mono_code,'x') = nvl(s.product_mono_code,'x')
           --and t.mono_code = s.single_mono_code
           --and s.sub_ingred_id = i.ingred_id
         union 
        select --unique -- only ingredients (comment out the union section to have sub_ingred in-line with ingred only)
               t.product_mono_code,
               t.mono_code single_mono_code, 
               m.mono_id,
               t.ingredient_nhpid_name ingred_nhpid_name,
               --nvl(to_number(t.nhpid_ingred_id),i.ingred_id) ingred_nhpid_id,
               to_number(t.nhpid_ingred_id) ingred_nhpid_id,
               null sub_ingred_nhpid_name, --t.sub_ingredient_nhpid_name sub_ingred_nhpid_name,
               null sub_ingred_nhpid_id --to_number(t.sub_ingred_id_nhpid) sub_ingred_nhpid_id
          from x_ingredient_groups t, 
               x$monograph_xref m 
               --INGREDIENTS i
         where nvl(t.product_mono_code,'x') = nvl(m.product_mono_code,'x')
           and t.mono_code = m.single_mono_code
           --and lower(trim(t.ingredient_nhpid_name)) = lower(trim(i.ingred_authorized_name_eng(+)))
         union
        select --unique -- sub-ingredients as ingredients
               c.product_mono_code,
               c.mono_code,
               m.mono_id,
               c.nhpid_name,
               nvl(to_number(c.ingred_id_nhpid),i.ingred_id) ingred_nhpid_id,
               null sub_ingred_nhpid_name,
               null sub_ingred_nhpid_id
          from x_ingredient_combo_doses c,
               x$monograph_xref m, 
               INGREDIENTS i
         where nvl(c.product_mono_code,'x') = nvl(m.product_mono_code,'x')
           and c.mono_code = m.single_mono_code
           and lower(trim(c.nhpid_name)) = lower(trim(i.ingred_authorized_name_eng(+)))
         order by 1,2,4,5
) q 
;
commit;

--alter table x$ingredient_group_ingredients 
--  add constraint x$ingredient_group_ingred_pk primary key (ingred_group_ingred_id);

--create table x$ingredient_groups as
truncate table x$ingredient_groups;
insert into x$ingredient_groups
(ingred_group_id, ingred_group_code, ingred_group_group_id, ingred_group_ingred_id, 
 group_group_description, group_description, combo_dose_code, 
 product_mono_code, mono_code, mono_id, useracc_id, creation_date)
select rownum ingred_group_id, q.*, 0, trunc(sysdate) from (--2027
select --unique
       nvl(t.group_id,t.group_group_id) ingred_group_code, 
       x.ingred_group_group_id,
       i.ingred_group_ingred_id,
       t.group_group_description,
       t.group_description,
       null combo_dose_code,
       t.product_mono_code, t.mono_code, m.mono_id
  from x_ingredient_groups t, 
       x$ingredient_group_groups x,
       x$ingredient_group_ingredients i,
       x$monograph_xref m
 where t.group_group_id = x.ingred_group_group_code(+)
   and nvl(t.product_mono_code,'x') = nvl(m.product_mono_code(+),'x')
   and t.mono_code = m.single_mono_code(+)
   and nvl(t.product_mono_code,'x') = nvl(i.product_mono_code(+),'x')
   and nvl(t.mono_code,'x') = nvl(i.single_mono_code(+),'x')
   and nvl(t.ingredient_nhpid_name,'x') = nvl(i.ingred_nhpid_name(+),'x')
   and nvl(t.nhpid_ingred_id,0) = nvl(i.ingred_nhpid_id(+),0)
 union
select --unique
       nvl(c.rule_group_id,c.group_rule_group_id) ingred_group_code, 
       x.ingred_group_group_id,
       i.ingred_group_ingred_id,
       'Ingredient dose combination group group', --c.group_group_description,
       'Ingredient dose combination group', --c.group_description
       c.combo_dose_code,
       c.product_mono_code, c.mono_code, m.mono_id
  from x_ingredient_combo_doses c, 
       x$ingredient_group_groups x,
       x$ingredient_group_ingredients i,
       x$monograph_xref m
 where c.group_rule_group_id = x.ingred_group_group_code(+)
   and nvl(c.product_mono_code,'x') = nvl(m.product_mono_code(+),'x')
   and c.mono_code = m.single_mono_code(+)
   and nvl(c.product_mono_code,'x') = nvl(i.product_mono_code(+),'x')
   and c.mono_code = i.single_mono_code(+)
   and c.ingred_id_nhpid = i.ingred_nhpid_id(+)
   -- or (lower(trim(c.nhpid_name)) = lower(trim(i.ingred_nhpid_name(+)))
   and nvl(c.subing_id_nhpid,0) = nvl(i.sub_ingred_nhpid_id(+),0)
 order by 1
) q where ingred_group_code is not null
;
commit;

/*
alter table x$ingredient_groups
  add constraint x$ingredient_groups_pk primary key (ingred_group_id);
alter table x$ingredient_groups 
  add constraint x$ingredient_groups_fk1 foreign key (ingred_group_group_id)
  references x$ingredient_group_groups (ingred_group_group_id);
alter table x$ingredient_groups 
  add constraint x$ingredient_groups_fk2 foreign key (ingred_group_ingred_id)
  references x$ingredient_group_ingredients (ingred_group_ingred_id);
alter table X$INGREDIENT_GROUPS add constraint X$INGREDIENT_GROUPS_FK4 foreign key (combo_dose_code)
  references X$COMBO_DOSE_CODES (combo_dose_code);
*/

drop table x_ingredient_combo_doses;

