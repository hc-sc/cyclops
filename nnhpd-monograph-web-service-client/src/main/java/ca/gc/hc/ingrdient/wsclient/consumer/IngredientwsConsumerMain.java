package ca.gc.hc.ingrdient.wsclient.consumer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import ca.gc.hc.handler.HandlerBase;
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
import ca.gc.hc.webservice.IngredientService;
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

public class IngredientwsConsumerMain implements IngredientWSClientConsumerInterFace {

	private String urlOfWsdl;

	private URL wsdlURL;

	private String lang;

	IngredientService client;

	public IngredientwsConsumerMain(String urlofWSDL, String prmLang) {
		this.urlOfWsdl = urlofWSDL;
		this.lang = prmLang;
	}

	public boolean initialize() {

		boolean initialSuccess = true;

		try {
			wsdlURL = new URL(urlOfWsdl);

			QName SERVICE_NAME = new QName("http://ingredient.webservice.nhpd.hc.gc.ca/", "IngredientService");
			Service service = Service.create(wsdlURL, SERVICE_NAME);
			client = service.getPort(IngredientService.class);
			addSoapHeaderAttributes(client, lang);
		} catch (Throwable te) {
			System.out.println("Ingredient ws initilization fail...");
			return false;
		}
		return initialSuccess;

	}
	/*
	 * addSoapHeaderAttributes: Gets a copy of the handler chain for a protocol
	 * binding instance. Modifies the chain by adding a handler to modify the soap
	 * header with the new parameters for every outbound message and configures the
	 * binding instance with the new chain.
	 * 
	 */

	private void addSoapHeaderAttributes(IngredientService port, String language) {

		BindingProvider proxy = (BindingProvider) port;
		Binding binding = proxy.getBinding();
		List handlerList = binding.getHandlerChain();
		if (handlerList == null) {
			handlerList = new ArrayList();
		}
		HandlerBase soapHeader = new HandlerBase(language, "2.0");
		handlerList.add(soapHeader);
		binding.setHandlerChain(handlerList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getApplicationTypes()
	 */

	public List<ApplicationTypeWS> getApplicationTypes() {
		return client.getApplicationTypes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getHomeopathicDilutions()
	 */

	public List<HomeopathicDilutionWS> getHomeopathicDilutions() {
		return client.getHomeopathicDilutions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getSolvents()
	 */

	public List<SolventWS> getSolvents() {
		return client.getSolvents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getSubPopulations(java.lang.Boolean)
	 */

	public List<SubPopulationWS> getSubPopulations(Boolean preferred) {
		return client.getSubPopulations(preferred);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getCommonTermsByTypes(java.util.List)
	 */

	public List<CommonTermWS> getCommonTermsByTypes(List<String> arg0) {
		return client.getCommonTermsByTypes(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getBilingualCountries()
	 */

	public List<BilingualCountryWS> getBilingualCountries() {
		return client.getBilingualCountries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getStandardOrGradeReferences()
	 */

	public List<StandardOrGradeReferenceWS> getStandardOrGradeReferences() {
		return client.getStandardOrGradeReferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getBilingualIngredientById(ca.gc.hc.ingredient.client.IngredientIdWS)
	 */

	public BilingualIngredientWS getBilingualIngredientById(IngredientIdWS arg0) {
		return client.getBilingualIngredientById(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getRoutesOfAdministration()
	 */

	public List<RouteOfAdministrationWS> getRoutesOfAdministration() {
		return client.getRoutesOfAdministration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getOrganismTypes()
	 */

	public List<OrganismTypeWS> getOrganismTypes() {
		return client.getOrganismTypes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * getOrganismTypeGroups()
	 */

	public List<OrganismTypeGroupWS> getOrganismTypeGroups() {
		return client.getOrganismTypeGroups();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace#
	 * exposeOrganismTypes(ca.gc.hc.ingredient.client.NHPDOrganismTypes)
	 */

	public void exposeOrganismTypes(NHPDOrganismTypes arg0) {
		client.exposeOrganismTypes(arg0);

	}

	@Override
	public void exposeChemicalSubstanceWS(ChemicalSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeCustomOrganismSubstanceWS(CustomOrganismSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeDefinedOrganismSubstanceWS(DefinedOrganismSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeBilingualDefinedOrganismSubstanceWS(BilingualDefinedOrganismSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeBilingualHomeopathicSubstanceWS(BilingualHomeopathicSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeBilingualChemicalSubstanceWS(BilingualChemicalSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeSubIngredientWS(SubIngredientWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeTypes(NHPDUnitTypeCodes arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeCommonTermsTypes(NHPDCommonTermTypes arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exposeHomeopathicSubstanceWS(HomeopathicSubstanceWS arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PreClearedInfoWS> getPcis() {
		// TODO Auto-generated method stub
		return client.getPcis();
	}

	@Override
	public List<NonMedicinalPurposeWS> getNonMedicinalPurposes() {
		// TODO Auto-generated method stub
		return client.getNonMedicinalPurposes();
	}

	@Override
	public List<UnitsWS> getUnitsByTypes(List<String> arg0) {
		// TODO Auto-generated method stub
		return client.getUnitsByTypeCodes(arg0);
	}

	@Override
	public List<UnitsWS> getUnitsByTypeCodes(List<String> arg0) {
		// TODO Auto-generated method stub
		return client.getUnitsByTypeCodes(arg0);
	}

	@Override
	public List<ConfigPropertyWS> getConfigProperties() {
		// TODO Auto-generated method stub
		return client.getConfigProperties();
	}

	@Override
	public IngredientWS getIngredientById(IngredientIdWS arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return client.getIngredientById(arg0, arg1);
	}

	@Override
	public List<IngredientSearchResultWS> searchIngredients(IngredientSearchCriteriaWS arg0) {
		// TODO Auto-generated method stub
		return client.searchIngredients(arg0);
	}

	@Override
	public List<PreparationRuleWS> getPreparationRules() {
		// TODO Auto-generated method stub
		return client.getPreparationRules();
	}

	@Override
	public List<DosageFormWS> getDosageForms() {
		// TODO Auto-generated method stub
		return client.getDosageForms();
	}

	@Override
	public List<OrganismPartTypeWS> getOrganismPartTypes(String arg0) {
		// TODO Auto-generated method stub
		return client.getOrganismPartTypes(arg0);
	}

	@Override
	public List<PreparationTypeWS> getPreparationTypes() {
		// TODO Auto-generated method stub
		return client.getPreparationTypes();
	}

	@Override
	public List<String> getVersion() {
		// TODO Auto-generated method stub
		return client.getVersion();
	}

	@Override
	public void log(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MonographSearchResultWS> searchMonographs(MonographSearchCriteriaWS criteria) {
		return client.searchMonographs(criteria);
	}

	@Override
	public MonographWS getMonographById(MonographIdWS monoID) {
		return client.getMonographById(monoID);
	}

}
