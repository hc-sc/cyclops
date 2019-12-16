create or replace force view 
v_country_province
as
select c.country_code, 
       c.country_name_eng, c.country_name_fr, 
       p.provstate_code prov_state_code, 
       p.provstate_name_eng prov_state_name_eng, p.provstate_name_fr prov_state_name_fr, 
       c.risk_id, c.country_id, p.provstate_id
  from COUNTRIES c, 
       PROVINCES_STATES p
 where c.country_id = p.country_id(+)
;

--grant select on v_country_province to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_country_province for nhpdweb_dev.v_country_province;

