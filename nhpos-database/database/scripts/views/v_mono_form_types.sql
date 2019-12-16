create or replace force view 
v_mono_form_types 
as
select --unique
       mx.product_mono_code,
       mx.single_mono_code,
       nvl(m.mono_name_eng, x.mono_name) mono_name,
       case when mx.product_mono_code is not null and mx.single_mono_code like '%-NM' then 'NM'
            when mx.product_mono_code is not null and mx.single_mono_code is not null then 'SP'
            else x.mono_form_type
       end as mono_form_type,
       m.mono_id,
       x.nhpid_mono_code mono_code
  from x$mono_form_types x,
       x$monograph_xref mx,
       MONOGRAPHS m
 where 1=1
   and x.nhpid_mono_code = mx.mono_code
   and mx.mono_id = m.mono_id
union
select --unique
       mx.product_mono_code,
       mx.single_mono_code,
       mx.mono_name_eng,
       case when mx.product_mono_code is not null and mx.single_mono_code like '%-NM' then 'NM'
            when mx.product_mono_code is not null and mx.single_mono_code is not null then 'SP'
            else x.mono_form_type
       end as mono_form_type,
       null mono_id,
       x.nhpid_mono_code mono_code
  from x$mono_form_types x, x$monograph_xref mx
 where 1=1
   and x.nhpid_mono_code = mx.mono_code
   and mx.mono_id is null
;
