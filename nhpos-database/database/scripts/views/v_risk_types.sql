create or replace force view
v_risk_types
as
select t.risk_type_code, t.risk_type_decode_eng, t.risk_type_decode_fr, t.risk_type_id
  from x$risk_types t
;

--grant select on x$risk_types to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_risk_types for nhpdweb_dev.v_risk_types;
