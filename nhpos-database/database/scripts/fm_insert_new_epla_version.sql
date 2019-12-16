/*
Form registration script is to add ONE record into the table

Adding ePLA 3.0.0 as a valid form version

Database: 		NHPDWEB_SEQ_OWNER
Table:			form_versions
form_current_version:	3.0.0

author:  Bruce Dempsey
date:    2018/10/04

*/

insert into form_versions (form_version_id, form_current_version, form_description_eng, form_description_fr, form_download_url_eng, form_download_url_fr, form_status, form_type_code) values (form_versions_seq.nextval, '3.0.0', 'Product Licence Application', 'Formulaire de demande de licence de mise en marché', 'http://www.hc-sc.gc.ca/dhp-mps/prodnatur/applications/online-enligne/pla-dlmm-view-eng.php',  'http://www.hc-sc.gc.ca/dhp-mps/prodnatur/applications/online-enligne/pla-dlmm-view-fra.php', 0, 'PLA');

commit;


