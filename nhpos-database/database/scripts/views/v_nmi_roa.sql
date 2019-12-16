create or replace force view 
v_nmi_roa 
as
with q as (
/*NMI ROA based on Purposes */
select 
       i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
       ir.rolespec_name_eng ingred_role_eng, ir.rolespec_name_fr ingred_role_fr,
       null adminrt_name_eng, null adminrt_name_fr, null adminrt_code,
       p.purpose_name_eng, p.purpose_name_fr, p.purpose_code,
       ar.adminrt_name_eng purpose_adminrt_eng, ar.adminrt_name_fr purpose_adminrt_fr, ar.adminrt_code purpose_adminrt_code 
       ,nvl2(ar.adminrt_code,2,9) x
  from ingredients i,
       v_ingredient_role ir,
       ingredient_role_purposes irp,
       purposes p,
       purpose_adminrts par,
       administration_routes ar
where i.ingred_id = ir.ingred_id
   and irp.ingredrole_id = ir.ingredrole_id
   and irp.purpose_id = p.purpose_id
   and p.purpose_id = par.purpose_id
   and par.adminrt_id = ar.adminrt_id
   and ir.rolespec_id = 2
/*NMI ROA based on Restriction*/
union
select 
       i.ingred_id, i.ingred_authorized_name_eng nhpid_name_eng, i.ingred_authorized_name_fr nhpid_name_fr,
       ir.rolespec_name_eng ingred_role_eng, ir.rolespec_name_fr ingred_role_fr,
       ar.adminrt_name_eng, ar.adminrt_name_fr, ar.adminrt_code,
       null,null,null,
       null,null,null
       ,nvl2(ar.adminrt_code,1,9)
  from ingredients i,
       v_ingredient_role ir,
       restrictions rst,
       administration_routes ar
where i.ingred_id = ir.ingred_id
   and ir.ingredrole_id = rst.ingredrole_id(+)
   and rst.adminrt_id = ar.adminrt_id(+) 
   and ir.rolespec_id = 2
)
, x as (
select q.ingred_id, q.nhpid_name_eng, q.nhpid_name_fr, q.ingred_role_eng, q.ingred_role_fr,
       q.adminrt_code, q.purpose_adminrt_code,
       x, min(x) over (partition by q.ingred_id) xx
  from q
 where 1=1
   and nvl(q.adminrt_code, q.purpose_adminrt_code) is not null
)
select unique 
       ingred_id, 
       nhpid_name_eng, 
       nhpid_name_fr, 
       ingred_role_eng, 
       ingred_role_fr, 
       nvl(x.adminrt_code, x.purpose_adminrt_code)  adminrt_code
  from x 
 where x = xx;
