-- --------------------
-- Monograph Removal Script
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- --------------------


delete from   dietary_allowances;
delete from   dosageform_dosageformgroups;
delete from   monouse_dosageformgroups;
delete from   monouse_monosrcorgparts;
delete from   monorisk_monosrcorgparts;
delete from   monodose_monosrcorgparts;
delete from   monodosecomb_monosrcorgparts;
delete from   monodose_monosubidosecombs;
delete from   monodosecomb_monouses;
delete from   monograph_citation_joins; 
delete from   monograph_dose_citations;
delete from   monograph_dose_gentexts; 
delete from   monograph_dose_mono_dfus; 
delete from   monograph_dose_mono_uses;   
delete from   monograph_dose_note_gentexts; 
delete from   monograph_dose_orgparts;
delete from   monograph_drfruse_citations; 
delete from   monograph_drfruse_gentexts; 
delete from   monograph_drfruse_mono_uses;
delete from   monograph_drfruse_orgparts; 
delete from   monograph_duration_citations;
delete from   monograph_duration_gentexts; 
delete from   monograph_duration_orgparts; 
delete from   monograph_dur_monographs; 
delete from   monograph_dur_mono_uses; 	
delete from   monograph_entries_commonnames;
delete from   monograph_entries_orgparts; 
delete from   monograph_entries_preptypegrps; 
delete from   monograph_entries_preptypes; 
delete from   monograph_entries_src_ingreds; 
delete from   monograph_entry_generic_texts; 
delete from   monograph_entry_qualsyno_cns; 
delete from   monograph_entry_qualsyno_pns;
delete from   monograph_gen_note_gentexts; 
delete from   monograph_nmi_note_gentexts;
delete from   monograph_phase_monographs;
delete from   monograph_phase_mono_durations;
			
delete from   monograph_riskstmt_citations; 
delete from   monograph_riskstmt_gentexts; 
delete from   monograph_riskstmt_orgparts; 
delete from   monograph_risk_monographs; 
delete from   monograph_risk_note_gentexts; 
delete from   monograph_risk_src_ingreds; 
			
delete from   monograph_roa_citations;
delete from   monograph_roa_dosage_forms;
delete from   monograph_roa_gentexts; 
delete from   monograph_rskstmt_mono_uses; 
delete from   monograph_spec_info_gentexts; 
delete from   monograph_stor_cond_gentexts; 
delete from   monograph_subing_dosecombs; 
			
delete from   monograph_sub_populations;
delete from   monograph_uses_monographs;
delete from   monograph_use_citations; 
delete from   monograph_use_gentexts; 
delete from   monograph_use_orgparts; 
delete from   monosbingdcmbs_monosbingdss; 
delete from   mono_dosecomb_mono_doses;
delete from   mono_entries_txt_monosources;
delete from   mono_entry_mono_dosecombs; 
delete from   mono_phase_mono_dosecombs; 
delete from   mono_source_orgparts; 
delete from   preptype_preptypegroups; 
            
delete from   mono_entry_monosrcingreds;
delete from   mono_entry_monosrcorgparts;
delete from   mono_srcingred_citations;
delete from   mono_srcorgpart_citations;

delete from dosage_form_groups;
delete from monograph_directions_for_uses; 
delete from monograph_risk_stmts;
delete from monograph_durations;
delete from recommended_dietary_allowances;
delete from monograph_uses;
delete from monograph_dosecombs;
delete from monograph_doses;
delete from monograph_entries; 
delete from monograph_phases;
delete from monograph_preparations;
delete from monograph_routeofadmins;  
delete from mono_source_ingredients;
delete from mono_source_orgparts;
delete from monograph_subingdoses;
delete from monograph_subingcombs;
delete from monograph_monograph_types;
delete from monographs;
delete from monograph_groups;
delete from sub_population;
delete from preparation_type_groups;

delete from preparation_types where preptype_code like 'TINCT%__%' ESCAPE '_';
