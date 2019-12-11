-- --------------------
-- Removes dosage form BLOCK
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- --------------------

select *
from dosage_forms
where dosefrm_code = 'BLOCK';


----

delete from dosageform_synonyms 
where dosefrm_id = 7;

delete from dosage_form_admin_routes 
where dosefrm_id = 7;

delete from dosageform_dosageunits
where dosefrm_id = 7;

delete from dosage_forms
where dosefrm_code = 'BLOCK';