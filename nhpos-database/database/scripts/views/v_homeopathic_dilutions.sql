create or replace force view 
v_homeopathic_dilutions
as
select hd.homeodilu_code dilution_code, 
       hd.homeodilu_name_eng dilution_name_eng, 
       hd.homeodilu_name_fr dilution_name_fr,
       hd.homeodilu_desc_eng dilution_desc_eng,
       hd.homeodilu_desc_fr dilution_desc_fr
  from homeopathic_dilutions hd
;

--grant select on v_homeopathic_dilutions to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_homeopathic_dilutions for nhpdweb_dev.v_homeopathic_dilutions;
