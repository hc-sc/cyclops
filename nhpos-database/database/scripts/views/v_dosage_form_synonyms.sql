create or replace force view 
v_dosage_form_synonyms
as
with q as (
select df.dosefrm_code dosage_form_code,
       df.dosefrm_name_eng dosage_form_name_eng, 
       df.dosefrm_name_fr dosage_form_name_fr,
       s.synonym_name_eng,
       s.synonym_name_fr,
--       nvl(s.synonym_name_eng, s.synonym_name_fr) synonym_name_eng, 
--       nvl(s.synonym_name_fr, s.synonym_name_eng) synonym_name_fr,
       df.dosefrm_id, ds.synonym_id,
       replace(upper(nvl(s.synonym_name_eng,
         utl_raw.cast_to_varchar2((nlssort(s.synonym_name_fr, 'nls_sort=binary_ai'))))),'SOFTGEL,','SOFT GEL,') syn
  from DOSAGE_FORMS df,
       DOSAGEFORM_SYNONYMS ds,
       SYNONYMS s
 where ds.synonym_id = s.synonym_id
   and ds.dosefrm_id = df.dosefrm_id
)
select q.dosage_form_code, q.dosage_form_name_eng, q.dosage_form_name_fr,
       q.synonym_name_eng, q.synonym_name_fr,
       case when length(q.syn) - length(replace(q.syn,' ')) = 0
            then substr(regexp_replace(q.syn,'[ AEIOU-]',''),1,4)
            when length(q.syn) - length(replace(q.syn,' ')) = 1
            then regexp_replace(regexp_substr(q.syn,'(^|\s)\w{2}')||regexp_substr(q.syn,'(^|\s)\w{2}',1,2),'[ ]','')
            when length(q.syn) - length(replace(q.syn,' ')) = 2
            then regexp_replace(initcap(q.syn), '([[:lower:]]| |,)')
                 --regexp_substr(q.syn,'\s\w*',1,2)
            when length(q.syn) - length(replace(q.syn,' ')) > 2
            then regexp_replace(regexp_replace(initcap(q.syn), '([[:lower:]]| |,)'),'[,\(\)-]')
            else q.dosage_form_code end synonym_code,
       q.dosefrm_id, q.synonym_id
       ,syn,length(q.syn) - length(replace(q.syn,' ')) ll
  from q
;
