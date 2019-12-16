create or replace force view
v_mono_subingreds
as 
select 
    t.mono_code,
    t.ingred_id,
    t.ingred_nhpid_name,
    t.ingred_nhpid_name_fr,
    t.sub_ingred_ingred_id subingredient_id,
    t.sub_ingred_nhpid_name_eng,
    t.sub_ingred_nhpid_name_fr,
    t.mono_id
from v_ingred_org_part_subingreds t
where mono_code is not null
union
select 
    x.mono_code,
    x.ingred_id,
    x.ingred_nhpid_name_eng ingred_nhpid_name,
    x.ingred_nhpid_name_fr,
    x.sub_ingred_id subingredient_id,
    x.sub_ingred_nhpid_name_eng,
    x.sub_ingred_nhpid_name_fr,
    x.mono_id
from v_chemical_ingred_subingreds x
where mono_code is not null
;
