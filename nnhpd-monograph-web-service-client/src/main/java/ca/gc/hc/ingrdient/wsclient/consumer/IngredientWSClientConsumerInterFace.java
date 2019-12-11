package ca.gc.hc.ingrdient.wsclient.consumer;

import java.util.List;

import javax.jws.WebParam;

import ca.gc.hc.webservice.ApplicationTypeWS;
import ca.gc.hc.webservice.BilingualChemicalSubstanceWS;
import ca.gc.hc.webservice.BilingualCountryWS;
import ca.gc.hc.webservice.BilingualDefinedOrganismSubstanceWS;
import ca.gc.hc.webservice.BilingualHomeopathicSubstanceWS;
import ca.gc.hc.webservice.BilingualIngredientWS;
import ca.gc.hc.webservice.ChemicalSubstanceWS;
import ca.gc.hc.webservice.CommonTermWS;
import ca.gc.hc.webservice.ConfigPropertyWS;
import ca.gc.hc.webservice.CustomOrganismSubstanceWS;
import ca.gc.hc.webservice.DefinedOrganismSubstanceWS;
import ca.gc.hc.webservice.DosageFormWS;
import ca.gc.hc.webservice.HomeopathicDilutionWS;
import ca.gc.hc.webservice.HomeopathicSubstanceWS;
import ca.gc.hc.webservice.IngredientIdWS;
import ca.gc.hc.webservice.IngredientSearchCriteriaWS;
import ca.gc.hc.webservice.IngredientSearchResultWS;
import ca.gc.hc.webservice.IngredientWS;
import ca.gc.hc.webservice.MonographIdWS;
import ca.gc.hc.webservice.MonographSearchCriteriaWS;
import ca.gc.hc.webservice.MonographSearchResultWS;
import ca.gc.hc.webservice.MonographWS;
import ca.gc.hc.webservice.NHPDCommonTermTypes;
import ca.gc.hc.webservice.NHPDOrganismTypes;
import ca.gc.hc.webservice.NHPDUnitTypeCodes;
import ca.gc.hc.webservice.NonMedicinalPurposeWS;
import ca.gc.hc.webservice.OrganismPartTypeWS;
import ca.gc.hc.webservice.OrganismTypeGroupWS;
import ca.gc.hc.webservice.OrganismTypeWS;
import ca.gc.hc.webservice.PreClearedInfoWS;
import ca.gc.hc.webservice.PreparationRuleWS;
import ca.gc.hc.webservice.PreparationTypeWS;
import ca.gc.hc.webservice.RouteOfAdministrationWS;
import ca.gc.hc.webservice.SolventWS;
import ca.gc.hc.webservice.StandardOrGradeReferenceWS;
import ca.gc.hc.webservice.SubIngredientWS;
import ca.gc.hc.webservice.SubPopulationWS;
import ca.gc.hc.webservice.UnitsWS;



public interface IngredientWSClientConsumerInterFace {

	boolean initialize();

	List<ApplicationTypeWS> getApplicationTypes();

	List<HomeopathicDilutionWS> getHomeopathicDilutions();

	List<SolventWS> getSolvents();

	List<SubPopulationWS> getSubPopulations(Boolean preferred);

	List<CommonTermWS> getCommonTermsByTypes(List<String> arg0);

	List<BilingualCountryWS> getBilingualCountries();

	List<StandardOrGradeReferenceWS> getStandardOrGradeReferences();

	BilingualIngredientWS getBilingualIngredientById(IngredientIdWS arg0);

	List<RouteOfAdministrationWS> getRoutesOfAdministration();

	List<OrganismTypeWS> getOrganismTypes();

	List<OrganismTypeGroupWS> getOrganismTypeGroups();

	void exposeOrganismTypes(NHPDOrganismTypes arg0);

	void exposeChemicalSubstanceWS(ChemicalSubstanceWS arg0);

	void exposeCustomOrganismSubstanceWS(CustomOrganismSubstanceWS arg0);

	void exposeDefinedOrganismSubstanceWS(DefinedOrganismSubstanceWS arg0);

	void exposeBilingualDefinedOrganismSubstanceWS(BilingualDefinedOrganismSubstanceWS arg0);

	void exposeBilingualHomeopathicSubstanceWS(BilingualHomeopathicSubstanceWS arg0);

	void exposeBilingualChemicalSubstanceWS(BilingualChemicalSubstanceWS arg0);

	void exposeSubIngredientWS(SubIngredientWS arg0);

	void exposeTypes(NHPDUnitTypeCodes arg0);

	void exposeCommonTermsTypes(NHPDCommonTermTypes arg0);

	void exposeHomeopathicSubstanceWS(HomeopathicSubstanceWS arg0);

	List<PreClearedInfoWS> getPcis();

	List<NonMedicinalPurposeWS> getNonMedicinalPurposes();

	List<UnitsWS> getUnitsByTypes(List<String> arg0);

	List<UnitsWS> getUnitsByTypeCodes(List<String> arg0);

	List<ConfigPropertyWS> getConfigProperties();

	IngredientWS getIngredientById(IngredientIdWS arg0, boolean arg1);

	List<IngredientSearchResultWS> searchIngredients(IngredientSearchCriteriaWS arg0);

	List<PreparationRuleWS> getPreparationRules();

	List<DosageFormWS> getDosageForms();

	List<OrganismPartTypeWS> getOrganismPartTypes(String arg0);

	List<PreparationTypeWS> getPreparationTypes();

	List<String> getVersion();

	void log(String arg0);

	 public List<MonographSearchResultWS> searchMonographs( MonographSearchCriteriaWS criteria);
	  public MonographWS getMonographById( MonographIdWS monoID);
}