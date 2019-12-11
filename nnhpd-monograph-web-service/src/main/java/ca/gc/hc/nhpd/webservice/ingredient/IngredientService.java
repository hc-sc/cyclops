package ca.gc.hc.nhpd.webservice.ingredient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlEnumValue;
/*
 * This class is a Web Service implementation which implements the
 * IngredientSession interface. It calls a n EJB Session Bean which hosts the
 * DAO call(s) which fetch the required data.
 * 
 * Author: M. Rabe Date: May 2007 TODO - can we separate interface from
 * implementation with JAX-WS Tomcat?
 * 
 * Modifications added on 2009-11-17 by Germain Brunet.  Added the possibility of handling 
 * bilingual objects by implementing a bilingual flag in several of the 
 * methods.  This flag is leveraged by the web service model and determins
 * when the method should return English/French specific attributed.  
 * 
 * In addition, we implemented the getBilingualIngredientById method that
 * returns an ingredient with the above flag enabled.
 * 
 */
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.gc.hc.nhpd.dto.IngredientSearchCriteria;
import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.dto.MonographSearchResult;
/* This import for testing only! Remove */
// import
// ca.gc.hc.nhpd.webservice.ingredient.testclient.IngredientSessionTester;
import ca.gc.hc.nhpd.dto.PreClearedInfo;
import ca.gc.hc.nhpd.dto.SearchCriterion;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ApplicationType;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.CustomOrganismSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.GeneratedMonograph;
import ca.gc.hc.nhpd.model.HomeopathicDilution;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientId;
import ca.gc.hc.nhpd.model.MonographId;
import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.OrganismPartType;
import ca.gc.hc.nhpd.model.OrganismType;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.model.PreparationRule;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.RouteOfAdministration;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.model.StandardOrGradeReference;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.serviceclient.IngredientClientProxy;
import ca.gc.hc.nhpd.servicepilot.ingredient.IngredientServicePropertyManager;
import ca.gc.hc.nhpd.util.ThreadContext;
import ca.gc.hc.nhpd.util.VersionInfo;
import ca.gc.hc.nhpd.webservice.bilingualmodel.BilingualChemicalSubstanceWS;
import ca.gc.hc.nhpd.webservice.bilingualmodel.BilingualCountryWS;
import ca.gc.hc.nhpd.webservice.bilingualmodel.BilingualDefinedOrganismSubstanceWS;
import ca.gc.hc.nhpd.webservice.bilingualmodel.BilingualHomeopathicSubstanceWS;
import ca.gc.hc.nhpd.webservice.bilingualmodel.BilingualIngredientWS;
import ca.gc.hc.nhpd.webservice.model.ApplicationTypeWS;
import ca.gc.hc.nhpd.webservice.model.ChemicalSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.CommonTermWS;
import ca.gc.hc.nhpd.webservice.model.ConfigPropertyWS;
import ca.gc.hc.nhpd.webservice.model.CustomOrganismSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.DefinedOrganismSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.DosageFormWS;
import ca.gc.hc.nhpd.webservice.model.HomeopathicDilutionWS;
import ca.gc.hc.nhpd.webservice.model.HomeopathicSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.IngredientIdWS;
import ca.gc.hc.nhpd.webservice.model.IngredientSearchCriteriaWS;
import ca.gc.hc.nhpd.webservice.model.IngredientSearchResultWS;
import ca.gc.hc.nhpd.webservice.model.IngredientWS;
import ca.gc.hc.nhpd.webservice.model.MonographIdWS;
import ca.gc.hc.nhpd.webservice.model.MonographSearchCriteriaWS;
import ca.gc.hc.nhpd.webservice.model.MonographSearchResultWS;
import ca.gc.hc.nhpd.webservice.model.MonographWS;
import ca.gc.hc.nhpd.webservice.model.NonMedicinalPurposeWS;
import ca.gc.hc.nhpd.webservice.model.OrganismPartTypeWS;
import ca.gc.hc.nhpd.webservice.model.OrganismTypeGroupWS;
import ca.gc.hc.nhpd.webservice.model.OrganismTypeWS;
import ca.gc.hc.nhpd.webservice.model.PreClearedInfoWS;
import ca.gc.hc.nhpd.webservice.model.PreparationRuleWS;
import ca.gc.hc.nhpd.webservice.model.PreparationTypeWS;
import ca.gc.hc.nhpd.webservice.model.RouteOfAdministrationWS;
import ca.gc.hc.nhpd.webservice.model.SolventWS;
import ca.gc.hc.nhpd.webservice.model.StandardOrGradeReferenceWS;
import ca.gc.hc.nhpd.webservice.model.SubIngredientWS;
import ca.gc.hc.nhpd.webservice.model.SubPopulationWS;
import ca.gc.hc.nhpd.webservice.model.UnitsWS;
import ca.gc.hc.nhpd.webservice.servlet.WebServiceInitServlet;
import ca.gc.hc.webservice.monograph.client.MonographClientProxy;

@WebService(serviceName = "IngredientService")
@HandlerChain(file = "handlers.xml")
// relative path from the class file
public class IngredientService {

	// private static String version;

	private static final Log log = LogFactory.getLog(IngredientService.class);

	private static String buildVersion;

	@WebMethod
	public List<MonographSearchResultWS> searchMonographs(
			@WebParam(name = "criteria") MonographSearchCriteriaWS criteria) {

		// Use the Ingredient client proxy; it calls the implementation
		// IngredientWS[] ingredients = null;
		List<MonographSearchResultWS> retList = null;

		MonographClientProxy proxy = new MonographClientProxy();
		try {
			MonographSearchCriteria monographSearchCriteria = extractCriteria(criteria);
			List<MonographSearchCriteria> list = proxy.searchMonographs(monographSearchCriteria);
			retList = wrapMonographSearchResults(list);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		}

		return retList;
	}

	private List<MonographSearchResultWS> wrapMonographSearchResults(List monographSearchResults) {

		ArrayList<MonographSearchResultWS> arrayList = new ArrayList<MonographSearchResultWS>();
		try {
			log.debug("wrapMonographSearchResults:  list size: " + monographSearchResults.size());

			for (int x = 0; x < monographSearchResults.size(); x++) {
				// log.debug("Impl: Adding MonographSearchResult...");
				arrayList.add(new MonographSearchResultWS((MonographSearchResult) monographSearchResults.get(x)));
			}
			log.debug("wrapIngredientSearchResults:  arrayList size: " + arrayList.size());

		} catch (Throwable ex) {
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;

	}

	private MonographSearchCriteria extractCriteria(MonographSearchCriteriaWS criteria) {
		List<SearchCriterion> criteriaList = criteria.getCriteria();

		MonographSearchCriteria monographSearchCriteria = new MonographSearchCriteria();
		Iterator<SearchCriterion> iter = criteriaList.iterator();
		while (iter.hasNext()) {
			monographSearchCriteria.addCriterion((SearchCriterion) iter.next());
		}
		return monographSearchCriteria;
	}

	/*
	 * Retrieve Monograph By its Id. Returns the Monograph object, and its related
	 * Ingredient, and Monograph Dependant Objects (MDOs). This method is typically
	 * used by the PLA after selecting one of the Ingredients from from the search
	 * results.
	 */

	@WebMethod
	public MonographWS getMonographById(@WebParam(name = "monoID") MonographIdWS monographId)

	{
		log.debug("MSI: getMonographById");
		MonographId monoId = new MonographId();
		monoId.setId(monographId.getId());

		log.debug("MSI:getMonographById creating proxy...");
		MonographClientProxy proxy = new MonographClientProxy();

		log.debug("MSI:getMonographById invoking proxy...");
		GeneratedMonograph generatedMonograph = proxy.getMonographById(monoId);

		return new MonographWS(generatedMonograph);
	}

	// private String versionRequest = ThreadContext.getInstance().getVersion();
	// private Boolean displayVersion =
	// ThreadContext.getInstance().getDisplayVersion();

	/*
	 * The base class PersistentObject is used by all the model specific subclasses0
	 * to determine the current user language. We set the LocaleSource of the
	 * PersistentObject to the static ThreadContext instance, which contains a
	 * ThreadLocal user locale. The ThreadContext locale is set on every web service
	 * request via the SoapHeaderHandler.
	 */
	static {
		PersistentObject.setLocaleSource(ThreadContext.getInstance());
	}

	/*
	 * Enumeration which exposes the unit types to the web service.
	 */
	@XmlType(name = "NHPDUnitTypes")
	public static enum UnitTypesWS {
		BIOUNITS("Biological units"), EQUIVALENTVOLUME("Equivalent volume"), EQUIVALENTWEIGHTS(
				"Equivalent weights"), IMPREGNATEDDRESSING("Impregnated dressing"), IUS("International units"), MASS(
						"Mass"), MICROCULTURE("Microbiological culture"), MOLECULAREQUIVALENTS(
								"Molecular equivalents"), PERCENTAGE("Percentage"), PHARMACOPOEIAL(
										"Pharmacopoeial units"), TIME("Time"), VOLUME("Volume");

		private final String unitName;

		UnitTypesWS(String unitName) {
			this.unitName = unitName;
		}

		public String unitName() {
			return unitName;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName() {
		}
	}

	private String[] allDbUnitTypes = { UnitTypesWS.BIOUNITS.unitName(), UnitTypesWS.EQUIVALENTVOLUME.unitName(),
			UnitTypesWS.EQUIVALENTWEIGHTS.unitName(), UnitTypesWS.IMPREGNATEDDRESSING.unitName(),
			UnitTypesWS.IUS.unitName(), UnitTypesWS.MASS.unitName(), UnitTypesWS.MICROCULTURE.unitName(),
			UnitTypesWS.MOLECULAREQUIVALENTS.unitName(), UnitTypesWS.PERCENTAGE.unitName(),
			UnitTypesWS.PHARMACOPOEIAL.unitName(), UnitTypesWS.TIME.unitName(), UnitTypesWS.VOLUME.unitName() };

	/*
	 * Enumeration which exposes the unit types to the web service.
	 */
	@XmlType(name = "NHPDUnitTypeCodes")
	public static enum UnitTypeCodesWS {
		@XmlEnumValue("ADI")
		ACCEPTABLEDAILYINTAKE("ADI"), @XmlEnumValue("AREA")
		AREA("AREA"), @XmlEnumValue("BIOU")
		BIOLOGICALUNITS("BIOU"), @XmlEnumValue("DEN")
		DENSITY("DEN"), @XmlEnumValue("DOSF")
		DOSAGEFORM("DOSF"), @XmlEnumValue("EQUV")
		EQUIVALENTVOLUME("EQUV"), @XmlEnumValue("EQUW")
		EQUIVALENTWEIGHTS("EQUW"), @XmlEnumValue("HOMU")
		HOMEOPATHICUNITS("HOMU"), @XmlEnumValue("IMPD")
		IMPREGNATEDDRESSING("IMPD"), @XmlEnumValue("INTU")
		INTERNATIONALUNITS("INTU"), @XmlEnumValue("LEN")
		LENGTH("LEN"), @XmlEnumValue("MASS")
		MASS("MASS"), @XmlEnumValue("MICC")
		MICROBIOLOGICALCULTURE("MICC"), @XmlEnumValue("MOLE")
		MOLECULAREQUIVALENTS("MOLE"), @XmlEnumValue("PERC")
		PERCENTAGE("PERC"), @XmlEnumValue("QUAS")
		QUANTITYSUFFICIENT("QUAS"), @XmlEnumValue("RAD")
		RADIOACTIVITY("RAD"), @XmlEnumValue("TEMP")
		TEMPERATURE("TEMP"), @XmlEnumValue("TIME")
		TIME("TIME"), @XmlEnumValue("VOLUME")
		VOLUME("VOLUME"), @XmlEnumValue("WGHT")
		WEIGHT("WGHT");

		private final String unitTypeCode;

		UnitTypeCodesWS(String unitTypeCode) {
			this.unitTypeCode = unitTypeCode;
		}

		public String unitTypeCode() {
			return unitTypeCode;
		}

		public String getUnitTypeCode() {
			return unitTypeCode;
		}

		public void setUnitTypeCode() {
		}
	}

	private String[] allDbUnitTypeCodes = { UnitTypeCodesWS.ACCEPTABLEDAILYINTAKE.unitTypeCode(),
			UnitTypeCodesWS.AREA.unitTypeCode(), UnitTypeCodesWS.BIOLOGICALUNITS.unitTypeCode(),
			UnitTypeCodesWS.DENSITY.unitTypeCode(), UnitTypeCodesWS.DOSAGEFORM.unitTypeCode(),
			UnitTypeCodesWS.EQUIVALENTVOLUME.unitTypeCode(), UnitTypeCodesWS.EQUIVALENTWEIGHTS.unitTypeCode(),
			UnitTypeCodesWS.HOMEOPATHICUNITS.unitTypeCode(), UnitTypeCodesWS.IMPREGNATEDDRESSING.unitTypeCode(),
			UnitTypeCodesWS.INTERNATIONALUNITS.unitTypeCode(), UnitTypeCodesWS.LENGTH.unitTypeCode(),
			UnitTypeCodesWS.MASS.unitTypeCode(), UnitTypeCodesWS.MICROBIOLOGICALCULTURE.unitTypeCode(),
			UnitTypeCodesWS.MOLECULAREQUIVALENTS.unitTypeCode(), UnitTypeCodesWS.PERCENTAGE.unitTypeCode(),
			UnitTypeCodesWS.QUANTITYSUFFICIENT.unitTypeCode(), UnitTypeCodesWS.RADIOACTIVITY.unitTypeCode(),
			UnitTypeCodesWS.TEMPERATURE.unitTypeCode(), UnitTypeCodesWS.TIME.unitTypeCode(),
			UnitTypeCodesWS.VOLUME.unitTypeCode(), UnitTypeCodesWS.WEIGHT.unitTypeCode() };

	/*
	 * Enumeration class which exposes the common term types to the web service, and
	 * maps the types to the database specific types
	 * 
	 * Note: This code was expanded to add the French organism types.
	 */
	@XmlType(name = "NHPDCommonTermTypes")
	public static enum CommonTermTypesWS {
		@XmlEnumValue("Flame Projection")
		FLAMEPROJECTION("Flame Projection", "Flame Projection (F)"), @XmlEnumValue("Exceptions")
		EXCEPTION("Exceptions", "Exceptions (F)"), @XmlEnumValue("Irradiation")
		IRRADIATION("Irradiation", "Irradiation (F)"), @XmlEnumValue("Storage")
		STORAGE("Storage", "Storage (F)"), @XmlEnumValue("Lot")
		LOT("Lot", "Lot (F)"), @XmlEnumValue("Original Material")
		ORIGINALMATERIAL("Original Material", "Original Material (F)"), @XmlEnumValue("Homeopathic Use")
		HOMEOPATHICUSE("Homeopathic Use", "Homeopathic Use (F)"), @XmlEnumValue("Homeopathic Dosage Unit")
		HOMEOPATHICDOSAGEUNIT("Homeopathic Dosage Unit",
				"Homeopathic Dosage Unit (F)"), @XmlEnumValue("Dose Frequency Unit")
		DOSEFREQUENCYUNIT("Dose Frequency Unit", "Dose Frequency Unit (F)");

		private final String commonTermTypeNameEnglish;
		private final String commonTermTypeNameFrench;

		CommonTermTypesWS(String commonTermTypeNameEnglish, String commonTermTypeNameFrench) {
			this.commonTermTypeNameEnglish = commonTermTypeNameEnglish;
			this.commonTermTypeNameFrench = commonTermTypeNameFrench;
		}

		public String commonTermTypeNameEnglish() {
			return commonTermTypeNameEnglish;
		}

		public String commonTermTypeNameFrench() {
			return commonTermTypeNameFrench;
		}
	}

	private String[] allDbCommonTermTypes = { CommonTermTypesWS.EXCEPTION.commonTermTypeNameEnglish(),
			CommonTermTypesWS.FLAMEPROJECTION.commonTermTypeNameEnglish(),
			CommonTermTypesWS.IRRADIATION.commonTermTypeNameEnglish(),
			CommonTermTypesWS.STORAGE.commonTermTypeNameEnglish(), CommonTermTypesWS.LOT.commonTermTypeNameEnglish(),
			CommonTermTypesWS.ORIGINALMATERIAL.commonTermTypeNameEnglish(),
			CommonTermTypesWS.HOMEOPATHICUSE.commonTermTypeNameEnglish(),
			CommonTermTypesWS.HOMEOPATHICDOSAGEUNIT.commonTermTypeNameEnglish(),
			CommonTermTypesWS.DOSEFREQUENCYUNIT.commonTermTypeNameEnglish() };

	/*
	 * Enumeration class which exposes the organism types to the web service, and
	 * maps the types to the database specific types
	 * 
	 * Note: This code was expanded to add the French organism types.
	 */
	@XmlType(name = "NHPDOrganismTypes")
	public static enum OrganismTypesWS {
		ALGAE("Algae", "Algue"), ANIMAL("Animal", "Animal"), BACTERIUM("Bacterium", "Bactérie"), FUNGI("Fungi",
				"Champignon"), NONHUMANANIMAL("Non-human Animal", "Animal non-humain"), PLANT("Plant", "Plante");

		private final String organismTypeNameEnglish;
		private final String organismTypeNameFrench;

		OrganismTypesWS(String organismTypeNameEnglish, String organismTypeNameFrench) {
			this.organismTypeNameEnglish = organismTypeNameEnglish;
			this.organismTypeNameFrench = organismTypeNameFrench;
		}

		public String organismTypeNameEnglish() {
			return organismTypeNameEnglish;
		}

		public String organismTypeNameFrench() {
			return organismTypeNameFrench;
		}

	}

	@WebMethod
	public Set<String> getVersion() throws RemoteException {

		Boolean displayVersion = ThreadContext.getInstance().getDisplayVersion();
		Set<String> versions = new TreeSet<String>();

		// default functionality
		if (displayVersion == false) {
			versions.add(getBuildVersion());
			return versions;
		}

		// version functionality. (Additional information is being returned)
		versions.add("version" + VersionInfo.VERSION_LIST_SEPERATOR + getBuildVersion());
		versions.addAll(new VersionInfo().getVersionList());
		return versions;
	}

	/*
	 * Search Ingredients by SearchCriteria, which provides name value pairs to
	 * search by name string, role, category and ingredient class name
	 */
	@WebMethod
	public List<IngredientSearchResultWS> searchIngredients(IngredientSearchCriteriaWS criteria)
			throws RemoteException {

		// Use the Ingredient client proxy; it calls the implementation
		// IngredientWS[] ingredients = null;
		List<IngredientSearchResultWS> retList = null;

		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			IngredientSearchCriteria ingredientSearchCriteria = extractCriteria(criteria);

			// Determin if this is a non-medicinal search.
			boolean nonMedicinal = false;
			List<SearchCriterion> searchCriterion = ingredientSearchCriteria.getCriteria();
			for (SearchCriterion criterion : searchCriterion) {
				if (criterion.getAttributeName().equals(IngredientSearchCriteria.ROLE)
						&& criterion.getAttributeValue().equals(IngredientSearchCriteria.NONMEDICINAL)) {
					nonMedicinal = true;
					break;
				}
			}

			List<IngredientSearchResult> list = proxy.searchIngredients(ingredientSearchCriteria);
			retList = wrapIngredientSearchResults(list, nonMedicinal);
			// } catch (IngredientsException ie) {
			// log.error("Ingredients Exception occured in searchIngredients ", ie);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchIngredientsByRole ", ex);
		}

		return retList;
	}

	/*
	 * Get all application types.
	 */
	@WebMethod
	public List<ApplicationTypeWS> getApplicationTypes() throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<ApplicationTypeWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<ApplicationType> list = proxy.getApplicationTypes();
			retList = wrapApplicationTypes(list);
		} catch (Throwable ex) {
			log.error("Exception occured in searchIngredientsByRole ", ex);
		}

		return retList;
	}

	// wraps the ApplicationType in the WS wrapper
	private List<ApplicationTypeWS> wrapApplicationTypes(List<ApplicationType> applicationTypes) {
		log.debug("WrapApplicationTypes:  list size: " + applicationTypes.size());

		ArrayList<ApplicationTypeWS> arrayList = new ArrayList<ApplicationTypeWS>();
		for (ApplicationType applicationType : applicationTypes) {
			arrayList.add(new ApplicationTypeWS(applicationType));
		}

		return arrayList;
	}

	/*
	 * Get all homeopathic dilutions.
	 */
	@WebMethod
	public List<HomeopathicDilutionWS> getHomeopathicDilutions() throws RemoteException {
		List<HomeopathicDilutionWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<HomeopathicDilution> list = proxy.getHomeopathicDilutions();
			retList = wrapHomeopathicDilutions(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getHomeopathicDilutions ", ex);
		}

		return retList;
	}

	/*
	 * Get all solvents.
	 */
	@WebMethod
	public List<SolventWS> getSolvents() throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<SolventWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<Solvent> list = proxy.getSolvents();
			retList = wrapSolvents(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getSolvents ", ex);
		}

		return retList;
	}

	/**
	 * Get a list of SubPopulations Note: Currently only returns a list of preferred
	 * SubPopulations.
	 * 
	 * @param preferred(Boolean)
	 *            true to return only preferred SubPopulations false to return all
	 *            SubPopulations
	 * @return a list of SubPopulations
	 */
	@WebMethod
	@WebResult(name = "subPopulation")
	public List<SubPopulationWS> getSubPopulations(@WebParam(name = "preferred") Boolean preferred) {
		List<SubPopulationWS> subPopulations = null;

		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			if (preferred) {
				List<SubPopulation> list = proxy.getPreferredSubPopulations();
				subPopulations = wrapSubPopulations(list);
			}
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in getSubPopulations ", ex);
		}

		return subPopulations;
	}

	/**
	 * Converts SubPopulation objects into their for-web-service counterparts. This
	 * is done to limit the exposed pieces.
	 * 
	 * @param subPopulations
	 *            a list of SubPopulation objects to be wrapped
	 * @return a list of SubPopulationWS objects that contain a subset of
	 *         SubPopulation data
	 */
	private List<SubPopulationWS> wrapSubPopulations(List<SubPopulation> subPopulations) {
		ArrayList<SubPopulationWS> arrayList = new ArrayList<SubPopulationWS>();
		try {
			log.debug("wrapSubPopulations:  list size: " + subPopulations.size());

			for (SubPopulation subPopulation : subPopulations) {
				arrayList.add(new SubPopulationWS(subPopulation));
			}
		} catch (Throwable ex) {
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;
	}

	@WebMethod
	public List<PreparationRuleWS> getPreparationRules() throws RemoteException {
		List<PreparationRuleWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<PreparationRule> list = proxy.getPreparationRule();
			retList = wrapPreparationRule(list);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		}

		return retList;
	}

	private List<PreparationRuleWS> wrapPreparationRule(List<PreparationRule> preparationRules) {

		ArrayList<PreparationRuleWS> arrayList = new ArrayList<PreparationRuleWS>();
		try {
			log.debug("wrapPreparationRuleResults:  list size: " + preparationRules.size());

			for (PreparationRule preparationRule : preparationRules) {
				arrayList.add(new PreparationRuleWS(preparationRule));
			}
		} catch (Throwable ex) {
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;

	}

	@WebMethod
	public List<DosageFormWS> getDosageForms() throws RemoteException {
		List<DosageFormWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<DosageForm> list = proxy.getDosageForm();
			retList = wrapDosageForm(list);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		}

		return retList;
	}

	private List<DosageFormWS> wrapDosageForm(List<DosageForm> dosageForms) {

		ArrayList<DosageFormWS> arrayList = new ArrayList<DosageFormWS>();
		try {
			log.debug("wrapDosageFormResults:  list size: " + dosageForms.size());

			for (DosageForm dosageForm : dosageForms) {
				arrayList.add(new DosageFormWS(dosageForm));
			}
		} catch (Throwable ex) {
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;

	}

	/*
	 * Get all Organism Part Types, filtering on the provided organismTypeGroup. If
	 * organismTypeGroup is null or null String, *all* org part types are returned.
	 * 
	 * Note: Since the OrganismTypeGroup does not have a code, we are now supporting
	 * searching in both the English and French language respectivly.
	 */
	@WebMethod
	public List<OrganismPartTypeWS> getOrganismPartTypes(String organismTypeGroup) throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<OrganismPartTypeWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();

		String mappedOrgType = mapOrganismTypeGroup(organismTypeGroup);
		try {
			List<OrganismPartType> list = proxy.getOrganismPartTypes(mappedOrgType);
			retList = wrapOrganismPartTypes(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getOrganismPartTypes ", ex);
		}

		return retList;
	}

	/*
	 * Get all Organism Part Types (TODO - could restrict by organism type).
	 */
	@WebMethod
	public List<PreparationTypeWS> getPreparationTypes() throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<PreparationTypeWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<PreparationType> list = proxy.getPreparationTypes();
			retList = wrapPreparationTypes(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getPartPreparations ", ex);
		}

		return retList;
	}

	/*
	 * Get all Non Medicinal Purposes.
	 */
	@WebMethod
	public Set<NonMedicinalPurposeWS> getNonMedicinalPurposes() throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		Set<NonMedicinalPurposeWS> purposes = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<NonMedicinalPurpose> list = proxy.getNonMedicinalPurposes();
			purposes = wrapNonMedicinalPurposes(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getNonMedicinalPurposes ", ex);
		}

		return purposes;
	}

	/*
	 * Get Units by types.
	 */

	/*
	 * String[] parameter type does not work! IBM bug! List<String> is OK...
	 */
	@WebMethod
	public List<UnitsWS> getUnitsByTypes(List<String> unitTypes) throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<UnitsWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();

		// map the requested types to the DB types
		String[] dbUnitTypes = mapDBUnitTypes(unitTypes);
		try {
			List<Units> list = proxy.getUnitsByTypes(dbUnitTypes);
			retList = wrapUnits(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getUnitsByType ", ex);
		}

		// spewUnits(retList);
		return retList;
	}

	/*
	 * Get Units by type code.
	 */

	/*
	 * String[] parameter type does not work! IBM bug! List<String> is OK...
	 */
	@WebMethod
	public List<UnitsWS> getUnitsByTypeCodes(List<String> unitTypeCodes) throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<UnitsWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();

		// map the requested types to the DB types
		String[] dbUnitTypes = mapDBUnitTypeCodes(unitTypeCodes);
		try {
			List<Units> list = proxy.getUnitsByTypeCodes(dbUnitTypes);
			retList = wrapUnits(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getUnitsByTypeCodes ", ex);
		}

		// spewUnits(retList);
		return retList;
	}

	/*
	 * Retrieves server side configuration items that the client needs
	 */
	@WebMethod
	public List<ConfigPropertyWS> getConfigProperties() {

		List<ConfigPropertyWS> properties = new ArrayList<ConfigPropertyWS>();

		// IDB web app base url
		properties.add(new ConfigPropertyWS(IngredientServicePropertyManager.INGREDIENT_DB_URL,
				IngredientServicePropertyManager.getIngredientsWebSiteUrl()));
		// IDB web app: ingredient url
		properties.add(new ConfigPropertyWS(IngredientServicePropertyManager.INGREDIENT_REQUEST_URL,
				IngredientServicePropertyManager.getIngredientRequestUrl()));
		// max number of search results that can be returned
		properties.add(new ConfigPropertyWS(IngredientServicePropertyManager.PLA_MAX_SEARCH_RESULTS_SIZE,
				new Integer(IngredientServicePropertyManager.getPlaMaxSearchResultSize()).toString()));

		return properties;

	}

	/*
	 * private void spewUnits(List<UnitsWS> units) {
	 * 
	 * log ("ISI: Units:    size:" + units.size()); for (int x=0;x<units.size();x++)
	 * { UnitsWS unitsWS = (UnitsWS)units.get(x); log ("    " + unitsWS.getCode() +
	 * "  " + unitsWS.getName()); }
	 * 
	 * }
	 */

	// wraps the NMPs in the WS wrapper
	/*
	 * private List<NonMedicinalPurposeWS> wrapNonMedicinalPurposes(List
	 * nonMedicinalPurposes) { log.debug("wrapNonMedicinalPurposes:  list size: " +
	 * nonMedicinalPurposes.size());
	 * 
	 * ArrayList<NonMedicinalPurposeWS> arrayList = new
	 * ArrayList<NonMedicinalPurposeWS>(); for (int
	 * x=0;x<nonMedicinalPurposes.size();x++) { arrayList.add(new
	 * NonMedicinalPurposeWS((NonMedicinalPurpose)nonMedicinalPurposes.get(x))); }
	 * 
	 * return arrayList; }
	 */
	/*
	 * Oracle 9 sorting is wonky, especially when dealing with accented characters,
	 * so we put this collection into a TreeSet
	 */
	private Set<NonMedicinalPurposeWS> wrapNonMedicinalPurposes(List<NonMedicinalPurpose> nonMedicinalPurposes) {
		log.debug("wrapNonMedicinalPurposes:  list size: " + nonMedicinalPurposes.size());

		TreeSet<NonMedicinalPurposeWS> purposes = new TreeSet<NonMedicinalPurposeWS>();
		for (int x = 0; x < nonMedicinalPurposes.size(); x++) {
			purposes.add(new NonMedicinalPurposeWS((NonMedicinalPurpose) nonMedicinalPurposes.get(x)));
		}

		return purposes;
	}

	// wraps the Solvents in the WS wrapper
	private List<HomeopathicDilutionWS> wrapHomeopathicDilutions(List<HomeopathicDilution> homeopathicDilutions) {
		ArrayList<HomeopathicDilutionWS> homeopathicDilutionsWS = new ArrayList<HomeopathicDilutionWS>();
		for (HomeopathicDilution homeopathicDilution : homeopathicDilutions) {
			homeopathicDilutionsWS.add(new HomeopathicDilutionWS(homeopathicDilution));
		}
		return homeopathicDilutionsWS;
	}

	// wraps the Solvents in the WS wrapper
	private List<SolventWS> wrapSolvents(List<Solvent> solvents) {
		log.debug("WrapSolvents:  list size: " + solvents.size());

		ArrayList<SolventWS> arrayList = new ArrayList<SolventWS>();
		for (int x = 0; x < solvents.size(); x++) {
			arrayList.add(new SolventWS((Solvent) solvents.get(x)));
		}

		return arrayList;
	}

	// wraps the Units in the WS wrapper
	private List<UnitsWS> wrapUnits(List<Units> units) {

		// log.debug("wrapUnits: list size: " + units.size());

		ArrayList<UnitsWS> arrayList = new ArrayList<UnitsWS>();

		for (int x = 0; x < units.size(); x++) {
			arrayList.add(new UnitsWS((Units) units.get(x)));
		}

		return arrayList;
	}

	// wraps the PartPreparations in the WS wrapper
	private List<PreparationTypeWS> wrapPreparationTypes(List<PreparationType> partPreps) {
		log.debug("wrapPartPreparations:  list size: " + partPreps.size());

		ArrayList<PreparationTypeWS> arrayList = new ArrayList<PreparationTypeWS>();
		for (int x = 0; x < partPreps.size(); x++) {
			arrayList.add(new PreparationTypeWS((PreparationType) partPreps.get(x)));
		}

		return arrayList;
	}

	// wraps the OrganismPartTypes in the WS wrapper
	private List<OrganismPartTypeWS> wrapOrganismPartTypes(List<OrganismPartType> orgPartTypes) {
		log.debug("wrapOrganismPartTypes:  list size: " + orgPartTypes.size());

		ArrayList<OrganismPartTypeWS> arrayList = new ArrayList<OrganismPartTypeWS>();
		for (int x = 0; x < orgPartTypes.size(); x++) {
			arrayList.add(new OrganismPartTypeWS((OrganismPartType) orgPartTypes.get(x)));
		}

		return arrayList;
	}

	// wraps the Ingredients in the WS wrapper
	/*
	 * private List<IngredientWS> wrapIngredients(List<Ingredient> ingredients) {
	 * 
	 * ArrayList<IngredientWS> arrayList = new ArrayList<IngredientWS>(); try {
	 * log.debug("WrapIngredients:  list size: " + ingredients.size()); for (int x =
	 * 0; x < ingredients.size(); x++) { arrayList .add(new
	 * IngredientWS((Ingredient) ingredients.get(x))); }
	 * log.debug("WrapIngredients:  arrayList size: " + arrayList.size());
	 * 
	 * } catch (Throwable ex) { log.debug("Unexpected exception occured...");
	 * ex.printStackTrace(); } return arrayList; }
	 */

	// wraps the Ingredients in the WS wrapper
	private List<IngredientSearchResultWS> wrapIngredientSearchResults(List<IngredientSearchResult> ingredients,
			boolean nonMedicinal) {

		ArrayList<IngredientSearchResultWS> arrayList = new ArrayList<IngredientSearchResultWS>();
		try {
			for (int x = 0; x < ingredients.size(); x++) {
				// log.debug("Impl: Adding IngredientWS...");
				arrayList.add(new IngredientSearchResultWS((IngredientSearchResult) ingredients.get(x), nonMedicinal));
			}
			log.debug("wrapIngredientSearchResults:  arrayList size: " + arrayList.size());
		} catch (Throwable ex) {
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;
	}

	private IngredientSearchCriteria extractCriteria(IngredientSearchCriteriaWS criteria) {
		SearchCriterion[] criterion = criteria.getCriteria();

		IngredientSearchCriteria ingredientSearchCriteria = new IngredientSearchCriteria();
		for (int x = 0; x < criterion.length; x++)
			ingredientSearchCriteria.addCriterion(criterion[x]);

		return ingredientSearchCriteria;
	}

	/*
	 * Search Ingredients by Role and an optional searchString. Use one of the Role
	 * strings specified in the IngredientService interface.
	 */
	@WebMethod
	public IngredientWS getIngredientById(IngredientIdWS ingredientIdWS, boolean bilingual) throws RemoteException {

		IngredientWS ingredientWS = null; // Ingredient to return
		IngredientId ingredientId = new IngredientId();
		ingredientId.setIngredientName(ingredientIdWS.getIngredientName());
		ingredientId.setVersion(ingredientIdWS.getVersion());
		ingredientId.setId(ingredientIdWS.getId());

		// Use the Ingredient client proxy; it calls the implementation
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			Ingredient ingredient = proxy.getIngredientById(ingredientId);

			// wrap it up in the WS class
			ingredientWS = wrapIngredient(ingredient, bilingual);

		} catch (IngredientsException ie) {

			// To cleanup the log, we want to catch errors for missing objects,
			// and trap them to prevent from overwelming the logs.
			if (ie.getMessage().indexOf(IngredientsException.EXCEPTION_UNSUPPORTED_OBJECT) != -1) {

				log.debug("getIngredientById - Missing ingredient error trapped, and null returned!");
				return null;
			}
			throw ie;

		} catch (Throwable ex) {

			// Throw out the error normally.
			log.error("Exception occured in getIngredientById ", ex);

		}
		if (ingredientWS == null) {
			log.debug("wrapIngredient returned null Ingredient!");
		} else {
			log.debug("wrapIngredient returned Ingredient type: " + ingredientWS.getType());
		}

		return ingredientWS;
	}

	@WebMethod
	public List<CommonTermWS> getCommonTermsByTypes(List<String> commonTermTypes) throws RemoteException {
		// Use the Ingredient client proxy; it calls the implementation
		List<CommonTermWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();

		// map the requested types to the DB types
		String[] dbCommonTermTypes = mapDBCommonTermTypes(commonTermTypes);
		try {
			List<CommonTerm> list = proxy.getCommonTermsByTypes(dbCommonTermTypes);
			retList = wrapCommonTerms(list);
		} catch (Throwable ex) {
			log.error("Exception occured in getCommonTermsByTypes ", ex);
		}

		// spewUnits(retList);
		return retList;
	}

	// wraps the CommonTermType in the WS wrapper
	private List<CommonTermWS> wrapCommonTerms(List<CommonTerm> commonTerms) {
		log.debug("wrapPartPreparations:  list size: " + commonTerms.size());

		ArrayList<CommonTermWS> arrayList = new ArrayList<CommonTermWS>();
		for (CommonTerm commonTerm : commonTerms) {
			arrayList.add(new CommonTermWS(commonTerm));
		}
		return arrayList;
	}

	@WebMethod
	public List<BilingualCountryWS> getBilingualCountries() throws RemoteException {
		List<BilingualCountryWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<Country> list = proxy.getCountries();
			retList = wrapBilingualCountry(list);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		}

		return retList;
	}

	// wraps the PartPreparations in the WS wrapper
	private List<BilingualCountryWS> wrapBilingualCountry(List<Country> countries) {
		log.debug("wrapPartPreparations:  list size: " + countries.size());

		ArrayList<BilingualCountryWS> arrayList = new ArrayList<BilingualCountryWS>();
		for (Country country : countries) {
			arrayList.add(new BilingualCountryWS(country));
		}
		return arrayList;
	}

	@WebMethod
	public List<StandardOrGradeReferenceWS> getStandardOrGradeReferences() throws RemoteException {
		List<StandardOrGradeReferenceWS> retList = null;
		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<StandardOrGradeReference> list = proxy.getStandardOrGradeReferences();
			retList = wrapStandardOrGradeReference(list);
		} catch (IngredientsException ie) {
			// We want to throw any Ingredient Exception.
			// The exception is normally an ingredient that sql is not available.
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		}

		return retList;
	}

	// wraps the StandardOrGradeReference in the WS wrapper
	private List<StandardOrGradeReferenceWS> wrapStandardOrGradeReference(
			List<StandardOrGradeReference> standardOrGradeReferences) {
		log.debug("wrapStandardOrGradeReference:  list size: " + standardOrGradeReferences.size());

		ArrayList<StandardOrGradeReferenceWS> arrayList = new ArrayList<StandardOrGradeReferenceWS>();
		for (StandardOrGradeReference standardOrGradeReference : standardOrGradeReferences) {
			arrayList.add(new StandardOrGradeReferenceWS(standardOrGradeReference));
		}
		return arrayList;
	}

	/*
	 * Search Bilingual Ingredients by Id. This returns ingredients with additional
	 * language settings exposed for consumption by the user community. The initial
	 * consumer of this specific web service call if for NHPSAS.
	 */
	@WebMethod
	public BilingualIngredientWS getBilingualIngredientById(IngredientIdWS ingredientIdWS) throws RemoteException {

		IngredientWS ingredientWS = getIngredientById(ingredientIdWS, true);
		Ingredient ingredient = ingredientWS.getIngredient();

		if (ingredient instanceof DefinedOrganismSubstance) {
			return new BilingualDefinedOrganismSubstanceWS((DefinedOrganismSubstance) ingredient);
		} else if (ingredient instanceof HomeopathicSubstance) {
			return new BilingualHomeopathicSubstanceWS((HomeopathicSubstance) ingredient);
		} else if (ingredient instanceof ChemicalSubstance) {
			return new BilingualChemicalSubstanceWS((ChemicalSubstance) ingredient);
		} else {
			return new BilingualIngredientWS(ingredient);
		}
	}

	private IngredientWS wrapIngredient(Ingredient ingredient, boolean bilingual) {

		if (ingredient instanceof ChemicalSubstance) {
			// log.debug("Wrapping ChemicalSubstance Ingredient...");
			return new ChemicalSubstanceWS((ChemicalSubstance) ingredient, bilingual);
		}
		if (ingredient instanceof DefinedOrganismSubstance) {
			// log.debug("Wrapping DefinedOrganismSubstance Ingredient...");
			return new DefinedOrganismSubstanceWS((DefinedOrganismSubstance) ingredient, bilingual);
		}
		if (ingredient instanceof CustomOrganismSubstance) {
			// log.debug("Wrapping CustomOrganismSubstance Ingredient...");
			return new CustomOrganismSubstanceWS((CustomOrganismSubstance) ingredient, bilingual);
		}
		if (ingredient instanceof HomeopathicSubstance) {
			return new HomeopathicSubstanceWS((HomeopathicSubstance) ingredient, bilingual);
		}

		log.warn(" Unknown Ingredient subtype in WrapIngredient!");
		return null;

	}

	/**
	 * Code never used, commented out by Germain private IngredientWS
	 * wrapIngredientSearchResult(Ingredient ingredient) {
	 * 
	 * if (ingredient instanceof ChemicalSubstance) { //log.debug("Wrapping
	 * ChemicalSubstance Ingredient..."); return new
	 * ChemicalSubstanceWS((ChemicalSubstance)ingredient, false); } if (ingredient
	 * instanceof DefinedOrganismSubstance) { //log.debug("Wrapping
	 * DefinedOrganismSubstance Ingredient..."); return new
	 * DefinedOrganismSubstanceWS((DefinedOrganismSubstance)ingredient, false); } if
	 * (ingredient instanceof CustomOrganismSubstance) { //log.debug("Wrapping
	 * CustomOrganismSubstance Ingredient..."); return new
	 * CustomOrganismSubstanceWS((CustomOrganismSubstance)ingredient, false); } if
	 * (ingredient instanceof HomeopathicSubstance) { //log.debug("Wrapping
	 * CustomOrganismSubstance Ingredient..."); return new
	 * HomeopathicSubstanceWS((HomeopathicSubstance)ingredient, false); }
	 * 
	 * log.warn(" Unknown Ingredient subtype in WrapIngredient!"); return null;
	 * 
	 * }
	 */

	/*
	 * Retrieves all Administration Routes
	 */
	@WebMethod
	public List<RouteOfAdministrationWS> getRoutesOfAdministration() throws RemoteException {
		log.debug("getRoutesOfAdministration");
		List<RouteOfAdministrationWS> retList = null;

		// Use the Ingredient client proxy; it calls the implementation
		log.debug("creating proxy");
		IngredientClientProxy proxy = new IngredientClientProxy();
		log.debug("created proxy");

		try {
			log.debug("invoking proxy");
			List<RouteOfAdministration> routes = proxy.getRoutesOfAdministration();
			retList = wrapRoutes(routes);
		} catch (Throwable ex) {
			log.error("Exception occured in getRoutesOfAdministration ", ex);
		}

		return retList;

	}

	/*
	 * Retrieves all Organism Types
	 */
	@WebMethod
	public List<OrganismTypeWS> getOrganismTypes() throws RemoteException {
		log.debug("getOrganismTypes");
		List<OrganismTypeWS> retList = null;

		// Use the Ingredient client proxy; it calls the implementation
		log.debug("creating proxy");
		IngredientClientProxy proxy = new IngredientClientProxy();
		log.debug("created proxy");

		try {
			log.debug("invoking proxy");
			List<OrganismType> organismTypes = proxy.getOrganismTypes();
			retList = wrapOrganismTypes(organismTypes);
		} catch (Throwable ex) {
			log.error("Exception occured in getOrganismTypes ", ex);
		}

		return retList;

	}

	/*
	 * Retrieves all Organism Type Groups
	 */
	@WebMethod
	public List<OrganismTypeGroupWS> getOrganismTypeGroups() throws RemoteException {
		log.debug("getOrganismTypeGroups");
		List<OrganismTypeGroupWS> retList = null;

		// Use the Ingredient client proxy; it calls the implementation
		log.debug("creating proxy");
		IngredientClientProxy proxy = new IngredientClientProxy();
		log.debug("created proxy");

		try {
			log.debug("invoking proxy");
			List<OrganismTypeGroup> organismTypeGroups = proxy.getOrganismTypeGroups();
			retList = wrapOrganismTypeGroups(organismTypeGroups);
		} catch (Throwable ex) {
			log.error("Exception occured in getOrganismTypes ", ex);
		}

		return retList;

	}

	/*
	 * Retrieves DosageForms that are suitable for the provided Route of
	 * Administration. "assessmentRequired" flag indicates which dosage forms should
	 * be returned by the query; e.g. a Time Release Pill dosage form requires
	 * assessment, a regular pill does not. Flag values are as exposed by the
	 * "DosageForm" model class:
	 * 
	 * ASSESSMENT_REQUIRED = 1; ASSESSMENT_NOT_REQUIRED = 2; ALL = 3;
	 */
	/*
	 * @WebMethod public List<DosageFormWS> getDosageFormsForRouteOfAdmin(String
	 * routeOfAdmin, String assessmentRequired) {
	 * 
	 * log.debug("getDosageFormsForRouteOfAdmin" ); List<DosageFormWS> retList =
	 * null;
	 * 
	 * //Use the Ingredient client proxy; it calls the implementation
	 * IngredientClientProxy proxy = new IngredientClientProxy();
	 * 
	 * try { log.debug ("invoking proxy"); List dosageForms =
	 * proxy.getDosageFormsForRouteOfAdmin(routeOfAdmin, assessmentRequired, retList
	 * = wrapDosageForms(dosageForms); } catch (Throwable ex) {
	 * log.error("Exception occured in getDosageFormsForRouteOfAdmin ", ex); }
	 * 
	 * return retList;
	 * 
	 * }
	 * 
	 * private List<DosageFormWS> wrapDosageForms(List dosageForms) {
	 * ArrayList<DosageFormWS> arrayList = new ArrayList<DosageFormWS>(); for (int
	 * x=0;x<dosageForms.size();x++) { arrayList.add(new
	 * DosageFormWS((DosageForm)dosageForms.get(x))); } return arrayList; }
	 */

	private List<RouteOfAdministrationWS> wrapRoutes(List<RouteOfAdministration> routesOfAdmin) {
		ArrayList<RouteOfAdministrationWS> arrayList = new ArrayList<RouteOfAdministrationWS>();
		for (int x = 0; x < routesOfAdmin.size(); x++) {
			arrayList.add(new RouteOfAdministrationWS((RouteOfAdministration) routesOfAdmin.get(x)));
		}

		return arrayList;
	}

	private List<OrganismTypeWS> wrapOrganismTypes(List<OrganismType> organismTypes) {
		ArrayList<OrganismTypeWS> arrayList = new ArrayList<OrganismTypeWS>();
		for (int x = 0; x < organismTypes.size(); x++) {
			arrayList.add(new OrganismTypeWS((OrganismType) organismTypes.get(x)));
		}

		return arrayList;
	}

	private List<OrganismTypeGroupWS> wrapOrganismTypeGroups(List<OrganismTypeGroup> organismTypeGroups) {
		ArrayList<OrganismTypeGroupWS> arrayList = new ArrayList<OrganismTypeGroupWS>();
		for (int x = 0; x < organismTypeGroups.size(); x++) {
			arrayList.add(new OrganismTypeGroupWS((OrganismTypeGroup) organismTypeGroups.get(x)));
		}

		return arrayList;
	}

	/*
	 * Validate the Organism Type Group. It can be null, a null String, or one of
	 * the enumerated values.
	 */
	private String mapOrganismTypeGroup(String organismTypeGroup) {
		if (organismTypeGroup == null)
			return null; // null means get all
		if (organismTypeGroup.equals(""))
			return null;
		for (OrganismTypesWS orgType : OrganismTypesWS.values()) {
			if (orgType.organismTypeNameEnglish().equals(organismTypeGroup)) {
				return orgType.organismTypeNameEnglish();
			}
			if (orgType.organismTypeNameFrench().equals(organismTypeGroup)) {
				return orgType.organismTypeNameFrench();
			}
		}
		throw new IngredientsException(
				"Invalid Organism Type Group: " + organismTypeGroup + " parameter provided to getOrganismPartTypes");
	}

	/*
	 * Map to the DB equivalent of the requested common term types. If the requested
	 * types are null or empty, return all common term types.
	 */
	private String[] mapDBCommonTermTypes(List<String> commonTermTypes) {

		if (!(typesProvided(commonTermTypes))) {
			return allDbCommonTermTypes;
		}

		ArrayList<String> dbCommonTermTypes = new ArrayList<String>();

		Iterator<String> iter = commonTermTypes.iterator();
		String commonTermType = null;
		while (iter.hasNext()) {
			commonTermType = (String) (iter.next());
			if (commonTermType.equals(CommonTermTypesWS.EXCEPTION.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.EXCEPTION.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.EXCEPTION.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.FLAMEPROJECTION.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.FLAMEPROJECTION.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.FLAMEPROJECTION.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.IRRADIATION.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.IRRADIATION.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.IRRADIATION.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.STORAGE.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.STORAGE.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.STORAGE.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.LOT.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.LOT.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.LOT.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.ORIGINALMATERIAL.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.ORIGINALMATERIAL.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.ORIGINALMATERIAL.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.HOMEOPATHICUSE.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.HOMEOPATHICUSE.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.HOMEOPATHICUSE.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.HOMEOPATHICDOSAGEUNIT.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.HOMEOPATHICDOSAGEUNIT.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.HOMEOPATHICDOSAGEUNIT.commonTermTypeNameEnglish());
			else if (commonTermType.equals(CommonTermTypesWS.DOSEFREQUENCYUNIT.commonTermTypeNameEnglish())
					|| commonTermType.equals(CommonTermTypesWS.DOSEFREQUENCYUNIT.commonTermTypeNameFrench()))
				dbCommonTermTypes.add(CommonTermTypesWS.DOSEFREQUENCYUNIT.commonTermTypeNameEnglish());
			else
				throw new IngredientsException("Unsupported Common Term Type " + commonTermType
						+ " provided to getCommonTermsByTypes method ");
		}
		// TODO exception handling
		return dbCommonTermTypes.toArray(new String[0]);
	}

	/*
	 * Map to the DB equivalent of the requested unit types. If the requested types
	 * are null or empty, return all types.
	 */
	private String[] mapDBUnitTypes(List<String> unitTypes) {
		if (!(typesProvided(unitTypes))) {
			return allDbUnitTypes;
		}

		ArrayList<String> dbUnitTypes = new ArrayList<String>();

		Iterator<String> iter = unitTypes.iterator();
		String unitType = null;
		while (iter.hasNext()) {
			unitType = (String) (iter.next());
			if (unitType.equals(UnitTypesWS.BIOUNITS.name()))
				dbUnitTypes.add(UnitTypesWS.BIOUNITS.unitName());
			else if (unitType.equals(UnitTypesWS.EQUIVALENTVOLUME.name()))
				dbUnitTypes.add(UnitTypesWS.EQUIVALENTVOLUME.unitName());
			else if (unitType.equals(UnitTypesWS.EQUIVALENTWEIGHTS.name()))
				dbUnitTypes.add(UnitTypesWS.EQUIVALENTWEIGHTS.unitName());
			else if (unitType.equals(UnitTypesWS.IMPREGNATEDDRESSING.name()))
				dbUnitTypes.add(UnitTypesWS.IMPREGNATEDDRESSING.unitName());
			else if (unitType.equals(UnitTypesWS.IUS.name()))
				dbUnitTypes.add(UnitTypesWS.IUS.unitName());
			else if (unitType.equals(UnitTypesWS.MASS.name()))
				dbUnitTypes.add(UnitTypesWS.MASS.unitName());
			else if (unitType.equals(UnitTypesWS.MICROCULTURE.name()))
				dbUnitTypes.add(UnitTypesWS.MICROCULTURE.unitName());
			else if (unitType.equals(UnitTypesWS.MOLECULAREQUIVALENTS.name()))
				dbUnitTypes.add(UnitTypesWS.MOLECULAREQUIVALENTS.unitName());
			else if (unitType.equals(UnitTypesWS.PERCENTAGE.name()))
				dbUnitTypes.add(UnitTypesWS.PERCENTAGE.unitName());
			else if (unitType.equals(UnitTypesWS.PHARMACOPOEIAL.name()))
				dbUnitTypes.add(UnitTypesWS.PHARMACOPOEIAL.unitName());
			else if (unitType.equals(UnitTypesWS.TIME.name()))
				dbUnitTypes.add(UnitTypesWS.TIME.unitName());
			else if (unitType.equals(UnitTypesWS.VOLUME.name()))
				dbUnitTypes.add(UnitTypesWS.VOLUME.unitName());
			else
				throw new IngredientsException(
						"Unsupported Unit Type " + unitType + " provided to getUnitsByTypes method ");
		}
		// TODO exception handling
		return dbUnitTypes.toArray(new String[0]);

	}

	/*
	 * Map to the DB equivalent of the requested unit types. If the requested types
	 * are null or empty, return all types.
	 */
	private String[] mapDBUnitTypeCodes(List<String> unitTypeCodes) {

		if (!(typesProvided(unitTypeCodes))) {
			return allDbUnitTypeCodes;
		}

		ArrayList<String> dbUnitTypeCodes = new ArrayList<String>();

		Iterator<String> iter = unitTypeCodes.iterator();
		String unitTypeCode = null;
		while (iter.hasNext()) {
			unitTypeCode = (String) (iter.next());
			if (unitTypeCode.equals(UnitTypeCodesWS.ACCEPTABLEDAILYINTAKE.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.ACCEPTABLEDAILYINTAKE.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.AREA.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.AREA.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.BIOLOGICALUNITS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.BIOLOGICALUNITS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.DENSITY.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.DENSITY.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.DOSAGEFORM.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.DOSAGEFORM.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.EQUIVALENTVOLUME.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.EQUIVALENTVOLUME.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.EQUIVALENTWEIGHTS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.EQUIVALENTWEIGHTS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.HOMEOPATHICUNITS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.HOMEOPATHICUNITS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.IMPREGNATEDDRESSING.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.IMPREGNATEDDRESSING.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.INTERNATIONALUNITS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.INTERNATIONALUNITS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.LENGTH.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.LENGTH.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.MASS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.MASS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.MICROBIOLOGICALCULTURE.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.MICROBIOLOGICALCULTURE.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.MOLECULAREQUIVALENTS.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.MOLECULAREQUIVALENTS.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.PERCENTAGE.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.PERCENTAGE.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.QUANTITYSUFFICIENT.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.QUANTITYSUFFICIENT.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.RADIOACTIVITY.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.RADIOACTIVITY.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.TEMPERATURE.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.TEMPERATURE.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.TIME.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.TIME.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.VOLUME.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.VOLUME.unitTypeCode());
			else if (unitTypeCode.equals(UnitTypeCodesWS.WEIGHT.unitTypeCode()))
				dbUnitTypeCodes.add(UnitTypeCodesWS.WEIGHT.unitTypeCode());
			else
				throw new IngredientsException(
						"Unsupported Unit Type " + unitTypeCode + " provided to getUnitsByTypes method ");
		}
		// TODO exception handling
		return dbUnitTypeCodes.toArray(new String[0]);

	}

	private boolean typesProvided(List<String> types) {
		if ((types == null) || types.isEmpty())
			return false;
		if ((types.size() == 1) && (types.get(0).equals("")))
			return false;

		return true;
	}

	/*
	 * TBD: Future Ingredient operations.
	 * 
	 * CRUD ops: create update get delete State management operations: publish
	 * unpublish withdraw register etc...
	 */

	/*
	 * The following "expose" methods appear only to expose the required classes in
	 * the WSDL. Note that the Ingredient inheritance hierarchy is captured here;
	 * e.g. ChemicalSubstanceWS and DefinedOrganismSubstanceWS are subtypes of
	 * IngredientWS. TODO - is there a better way to expose these classes via
	 * annotations?
	 */
	@WebMethod
	public void exposeOrganismTypes(OrganismTypesWS organismTypes) {
	}

	@WebMethod
	public void exposeChemicalSubstanceWS(ChemicalSubstanceWS chemicalSubstanceWS) {
	}

	@WebMethod
	public void exposeCustomOrganismSubstanceWS(CustomOrganismSubstanceWS chemicalSubstanceWS) {
	}

	@WebMethod
	public void exposeDefinedOrganismSubstanceWS(DefinedOrganismSubstanceWS definedOrganismSubstanceWS) {
	}

	@WebMethod
	public void exposeBilingualDefinedOrganismSubstanceWS(
			BilingualDefinedOrganismSubstanceWS bilingualDefinedOrganismSubstanceWS) {
	}

	@WebMethod
	public void exposeBilingualHomeopathicSubstanceWS(BilingualHomeopathicSubstanceWS bilingualHomeopathicSubstanceWS) {
	}

	@WebMethod
	public void exposeBilingualChemicalSubstanceWS(BilingualChemicalSubstanceWS bilingualHomeopathicSubstanceWS) {
	}

	@WebMethod
	public void exposeSubIngredientWS(SubIngredientWS subIngredientWS) {
	}

	@WebMethod
	public void exposeTypes(UnitTypeCodesWS unitTypes) {
	}

	@WebMethod
	public void exposeCommonTermsTypes(CommonTermTypesWS commonTerms) {
	}

	@WebMethod
	public void exposeHomeopathicSubstanceWS(HomeopathicSubstanceWS homeopathicSubstanceWS) {
	}

	public void log(String msg) {
		log.debug(msg);
	}

	/**
	 * Returns the buid version read when loading the Web Service from MANIFEST.MF.
	 * 
	 * @return
	 */
	public static String getBuildVersion() {
		if (buildVersion == null) {
			buildVersion = "NHP Ingredients Web Service - " + WebServiceInitServlet.getVersion();
		}

		return buildVersion;
	}

	@WebMethod
	public List<PreClearedInfoWS> getPcis() throws RemoteException {

		// Use the Ingredient client proxy; it calls the implementation
		List<PreClearedInfoWS> retList = null;

		IngredientClientProxy proxy = new IngredientClientProxy();
		try {
			List<PreClearedInfo> preClearedInfoList = proxy.getPcis();
			retList = wrapPreClearedInfo(preClearedInfoList);
		} catch (IngredientsException ie) {
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in getPcis ", ex);
		}

		return retList;
	}

	private List<PreClearedInfoWS> wrapPreClearedInfo(List<PreClearedInfo> preClearedInfoList) {

		ArrayList<PreClearedInfoWS> arrayList = new ArrayList<PreClearedInfoWS>();
		for (int x = 0; x < preClearedInfoList.size(); x++) {
			PreClearedInfo preClearedInfo = preClearedInfoList.get(x);
			arrayList.add(new PreClearedInfoWS(preClearedInfo));
		}
		return arrayList;

	}
}
