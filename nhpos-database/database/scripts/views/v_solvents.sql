create or replace force view
v_solvents
as
select --x.solvent_id,
       s.solvents_id solvent_id,
       s.solvents_code solvent_code,
       --decode(substr(s.solvents_code,1,3),'WTR','WTR'/*s.solvents_code*/,'ALC') solvent_group_code,
       s.solvents_name_eng solvent_name_eng,
       s.solvents_name_fr solvent_name_fr,
       s.ichclass_id
  from SOLVENTS s--, X$SOLVENTS x
;
-- where s.solvents_code = x.solvent_nhpid_code
--grant select on v_solvents to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_solvents for nhpdweb_dev.v_solvents;
