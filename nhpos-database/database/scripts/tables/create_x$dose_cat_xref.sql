--drop table x$dose_cat_xref cascade constraints;
--create table x$dose_cat_xref as
truncate table x$dose_cat_xref;
insert into x$dose_cat_xref
(dose_cat_id, dose_cat, useracc_id, creation_date)
select rownum dose_cat_id, dose_cat, 
       0 useracc_id, trunc(sysdate) creation_date 
from (
select x.dose_cat
  from x$assessment_dose_xref x
 where x.dose_cat is not null
 group by x.dose_cat
 order by 1
);

--commit;


-- Create/Recreate primary, unique and foreign key constraints 
--alter table X$DOSE_CAT_XREF add constraint X$DOSE_CAT_XREF_PK primary key (DOSE_CAT_ID);
--alter table X$DOSE_CAT_XREF add constraint X$DOSE_CAT_XREF_UK unique (DOSE_CAT);

