create or replace force view 
v_common_terms
as
select t.commonterm_code common_term_code,
       t.commonterm_name_eng common_term_name_eng,
       t.commonterm_name_fr common_term_name_fr,
       t.commonterm_id common_term_id,
       t.commontermtype_id common_term_type_id
  from COMMON_TERMS t
;

--grant select on v_common_terms to nhpdweb_user;
--create or replace synonym nhpdweb_user.v_common_terms for nhpdweb_dev.v_common_terms;

