-----------------------------------
--patch #10
-- date: 2019/09/25
-- fixes: V_MONOGRAPHS
-- log: patch_10.log
----------------------------------
spool patch_10.log
prompt Patch 10
prompt update view V_MONOGRAPHS

 CREATE OR REPLACE FORCE VIEW 
 V_MONOGRAPHS
 AS--" ("MONO_CODE", "SINGLE_MONO_CODE", "PRODUCT_MONO_CODE", "MONO_NAME_ENG", "MONO_NAME_FR", "MONO_PUBLISH_DATE", "MONO_TYPE_CODE", "PCI", "STERILE_REQUIRED", "MONO_ID") AS 
  select distinct
       m.code mono_code,
       mf.single_mono_code,
       mf.product_mono_code,
       m.mono_name_eng, m.mono_name_fr,
       m.mono_publish_date,
       case when mf.product_mono_code is not null and mf.single_mono_code like '%-NM' then 'NM'
            when mf.product_mono_code is not null and mf.single_mono_code is not null then 'SP'
            else mf.mono_form_type
       end as mono_type_code,
       case when m.code in ('FLWRESS','GEMMO','LITHO','NOS','OLIGO','ORGANO') then 'n'
            when mf.mono_form_type in ('P','S','H') then 'y' 
            else 'n' end pci,
       nvl(mr.admin_route_sterile_required,'n') sterile_required,
       m.mono_id
  from MONOGRAPHS m,
       v_mono_form_types mf,
       v_monograph_roa mr
where 1=1
   and m.mono_id = mf.mono_id(+)
   and m.mono_id = mr.mono_id(+)
union all
select --unique
       x.mono_code,
       x.single_mono_code,
       x.product_mono_code,
       x.mono_name_eng, null mono_name_fr,
       null mono_publish_date,
       case when x.product_mono_code is not null and x.single_mono_code like '%-NM' then 'NM'
            when x.mono_code = 'DECOMISSIONED' then 'D'
            when x.mono_code like 'SBPR%' then 'SS'
            when x.product_mono_code is not null and x.single_mono_code is null then 'P'
            when x.product_mono_code is null and x.single_mono_code is not null then 'S'
            when x.product_mono_code is not null and x.single_mono_code is not null then 'SP'
            else '?'
       end as mono_type_code,
       'n' pci,
       nvl(mr.admin_route_sterile_required,'n') sterile_required,
       mr.mono_id
  from x$monograph_xref x,
       v_monograph_roa mr
where x.mono_id is null
   and x.mono_code = mr.mono_code(+)
;

spool off

