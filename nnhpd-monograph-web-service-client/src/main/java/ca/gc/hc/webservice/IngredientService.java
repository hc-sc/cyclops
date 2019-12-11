
package ca.gc.hc.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IngredientService", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IngredientService {


    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.StandardOrGradeReferenceWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStandardOrGradeReferences", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetStandardOrGradeReferences")
    @ResponseWrapper(localName = "getStandardOrGradeReferencesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetStandardOrGradeReferencesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getStandardOrGradeReferencesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getStandardOrGradeReferencesResponse")
    public List<StandardOrGradeReferenceWS> getStandardOrGradeReferences();

    /**
     * 
     * @param arg0
     * @return
     *     returns ca.gc.hc.webservice.BilingualIngredientWS
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBilingualIngredientById", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetBilingualIngredientById")
    @ResponseWrapper(localName = "getBilingualIngredientByIdResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetBilingualIngredientByIdResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getBilingualIngredientByIdRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getBilingualIngredientByIdResponse")
    public BilingualIngredientWS getBilingualIngredientById(
        @WebParam(name = "arg0", targetNamespace = "")
        IngredientIdWS arg0);

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.RouteOfAdministrationWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getRoutesOfAdministration", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetRoutesOfAdministration")
    @ResponseWrapper(localName = "getRoutesOfAdministrationResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetRoutesOfAdministrationResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getRoutesOfAdministrationRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getRoutesOfAdministrationResponse")
    public List<RouteOfAdministrationWS> getRoutesOfAdministration();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.OrganismTypeWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrganismTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismTypes")
    @ResponseWrapper(localName = "getOrganismTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismTypesResponse")
    public List<OrganismTypeWS> getOrganismTypes();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.OrganismTypeGroupWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrganismTypeGroups", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismTypeGroups")
    @ResponseWrapper(localName = "getOrganismTypeGroupsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismTypeGroupsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismTypeGroupsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismTypeGroupsResponse")
    public List<OrganismTypeGroupWS> getOrganismTypeGroups();

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeOrganismTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeOrganismTypes")
    @ResponseWrapper(localName = "exposeOrganismTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeOrganismTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeOrganismTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeOrganismTypesResponse")
    public void exposeOrganismTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        NHPDOrganismTypes arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeChemicalSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeChemicalSubstanceWS")
    @ResponseWrapper(localName = "exposeChemicalSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeChemicalSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeChemicalSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeChemicalSubstanceWSResponse")
    public void exposeChemicalSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        ChemicalSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeCustomOrganismSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeCustomOrganismSubstanceWS")
    @ResponseWrapper(localName = "exposeCustomOrganismSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeCustomOrganismSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeCustomOrganismSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeCustomOrganismSubstanceWSResponse")
    public void exposeCustomOrganismSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        CustomOrganismSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeDefinedOrganismSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeDefinedOrganismSubstanceWS")
    @ResponseWrapper(localName = "exposeDefinedOrganismSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeDefinedOrganismSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeDefinedOrganismSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeDefinedOrganismSubstanceWSResponse")
    public void exposeDefinedOrganismSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        DefinedOrganismSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeBilingualDefinedOrganismSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualDefinedOrganismSubstanceWS")
    @ResponseWrapper(localName = "exposeBilingualDefinedOrganismSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualDefinedOrganismSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualDefinedOrganismSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualDefinedOrganismSubstanceWSResponse")
    public void exposeBilingualDefinedOrganismSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        BilingualDefinedOrganismSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeBilingualHomeopathicSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualHomeopathicSubstanceWS")
    @ResponseWrapper(localName = "exposeBilingualHomeopathicSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualHomeopathicSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualHomeopathicSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualHomeopathicSubstanceWSResponse")
    public void exposeBilingualHomeopathicSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        BilingualHomeopathicSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeBilingualChemicalSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualChemicalSubstanceWS")
    @ResponseWrapper(localName = "exposeBilingualChemicalSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeBilingualChemicalSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualChemicalSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeBilingualChemicalSubstanceWSResponse")
    public void exposeBilingualChemicalSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        BilingualChemicalSubstanceWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeSubIngredientWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeSubIngredientWS")
    @ResponseWrapper(localName = "exposeSubIngredientWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeSubIngredientWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeSubIngredientWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeSubIngredientWSResponse")
    public void exposeSubIngredientWS(
        @WebParam(name = "arg0", targetNamespace = "")
        SubIngredientWS arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeTypes")
    @ResponseWrapper(localName = "exposeTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeTypesResponse")
    public void exposeTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        NHPDUnitTypeCodes arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeCommonTermsTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeCommonTermsTypes")
    @ResponseWrapper(localName = "exposeCommonTermsTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeCommonTermsTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeCommonTermsTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeCommonTermsTypesResponse")
    public void exposeCommonTermsTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        NHPDCommonTermTypes arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "exposeHomeopathicSubstanceWS", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeHomeopathicSubstanceWS")
    @ResponseWrapper(localName = "exposeHomeopathicSubstanceWSResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.ExposeHomeopathicSubstanceWSResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeHomeopathicSubstanceWSRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/exposeHomeopathicSubstanceWSResponse")
    public void exposeHomeopathicSubstanceWS(
        @WebParam(name = "arg0", targetNamespace = "")
        HomeopathicSubstanceWS arg0);

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.PreClearedInfoWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPcis", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPcis")
    @ResponseWrapper(localName = "getPcisResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPcisResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPcisRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPcisResponse")
    public List<PreClearedInfoWS> getPcis();

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ca.gc.hc.webservice.IngredientWS
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIngredientById", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetIngredientById")
    @ResponseWrapper(localName = "getIngredientByIdResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetIngredientByIdResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getIngredientByIdRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getIngredientByIdResponse")
    public IngredientWS getIngredientById(
        @WebParam(name = "arg0", targetNamespace = "")
        IngredientIdWS arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.CommonTermWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCommonTermsByTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetCommonTermsByTypes")
    @ResponseWrapper(localName = "getCommonTermsByTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetCommonTermsByTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getCommonTermsByTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getCommonTermsByTypesResponse")
    public List<CommonTermWS> getCommonTermsByTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        List<String> arg0);

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.BilingualCountryWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBilingualCountries", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetBilingualCountries")
    @ResponseWrapper(localName = "getBilingualCountriesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetBilingualCountriesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getBilingualCountriesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getBilingualCountriesResponse")
    public List<BilingualCountryWS> getBilingualCountries();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.ApplicationTypeWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getApplicationTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetApplicationTypes")
    @ResponseWrapper(localName = "getApplicationTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetApplicationTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getApplicationTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getApplicationTypesResponse")
    public List<ApplicationTypeWS> getApplicationTypes();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.PreparationRuleWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPreparationRules", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPreparationRules")
    @ResponseWrapper(localName = "getPreparationRulesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPreparationRulesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPreparationRulesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPreparationRulesResponse")
    public List<PreparationRuleWS> getPreparationRules();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.DosageFormWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDosageForms", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetDosageForms")
    @ResponseWrapper(localName = "getDosageFormsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetDosageFormsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getDosageFormsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getDosageFormsResponse")
    public List<DosageFormWS> getDosageForms();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.OrganismPartTypeWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrganismPartTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismPartTypes")
    @ResponseWrapper(localName = "getOrganismPartTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetOrganismPartTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismPartTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getOrganismPartTypesResponse")
    public List<OrganismPartTypeWS> getOrganismPartTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.IngredientSearchResultWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "searchIngredients", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.SearchIngredients")
    @ResponseWrapper(localName = "searchIngredientsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.SearchIngredientsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/searchIngredientsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/searchIngredientsResponse")
    public List<IngredientSearchResultWS> searchIngredients(
        @WebParam(name = "arg0", targetNamespace = "")
        IngredientSearchCriteriaWS arg0);

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.PreparationTypeWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPreparationTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPreparationTypes")
    @ResponseWrapper(localName = "getPreparationTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetPreparationTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPreparationTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getPreparationTypesResponse")
    public List<PreparationTypeWS> getPreparationTypes();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.NonMedicinalPurposeWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNonMedicinalPurposes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetNonMedicinalPurposes")
    @ResponseWrapper(localName = "getNonMedicinalPurposesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetNonMedicinalPurposesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getNonMedicinalPurposesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getNonMedicinalPurposesResponse")
    public List<NonMedicinalPurposeWS> getNonMedicinalPurposes();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.UnitsWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUnitsByTypes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetUnitsByTypes")
    @ResponseWrapper(localName = "getUnitsByTypesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetUnitsByTypesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getUnitsByTypesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getUnitsByTypesResponse")
    public List<UnitsWS> getUnitsByTypes(
        @WebParam(name = "arg0", targetNamespace = "")
        List<String> arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.UnitsWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUnitsByTypeCodes", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetUnitsByTypeCodes")
    @ResponseWrapper(localName = "getUnitsByTypeCodesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetUnitsByTypeCodesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getUnitsByTypeCodesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getUnitsByTypeCodesResponse")
    public List<UnitsWS> getUnitsByTypeCodes(
        @WebParam(name = "arg0", targetNamespace = "")
        List<String> arg0);

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.ConfigPropertyWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getConfigProperties", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetConfigProperties")
    @ResponseWrapper(localName = "getConfigPropertiesResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetConfigPropertiesResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getConfigPropertiesRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getConfigPropertiesResponse")
    public List<ConfigPropertyWS> getConfigProperties();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.HomeopathicDilutionWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getHomeopathicDilutions", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetHomeopathicDilutions")
    @ResponseWrapper(localName = "getHomeopathicDilutionsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetHomeopathicDilutionsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getHomeopathicDilutionsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getHomeopathicDilutionsResponse")
    public List<HomeopathicDilutionWS> getHomeopathicDilutions();

    /**
     * 
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.SolventWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSolvents", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetSolvents")
    @ResponseWrapper(localName = "getSolventsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetSolventsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getSolventsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getSolventsResponse")
    public List<SolventWS> getSolvents();

    /**
     * 
     * @param preferred
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.SubPopulationWS>
     */
    @WebMethod
    @WebResult(name = "subPopulation", targetNamespace = "")
    @RequestWrapper(localName = "getSubPopulations", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetSubPopulations")
    @ResponseWrapper(localName = "getSubPopulationsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetSubPopulationsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getSubPopulationsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getSubPopulationsResponse")
    public List<SubPopulationWS> getSubPopulations(
        @WebParam(name = "preferred", targetNamespace = "")
        Boolean preferred);

    /**
     * 
     * @param criteria
     * @return
     *     returns java.util.List<ca.gc.hc.webservice.MonographSearchResultWS>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "searchMonographs", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.SearchMonographs")
    @ResponseWrapper(localName = "searchMonographsResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.SearchMonographsResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/searchMonographsRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/searchMonographsResponse")
    public List<MonographSearchResultWS> searchMonographs(
        @WebParam(name = "criteria", targetNamespace = "")
        MonographSearchCriteriaWS criteria);

    /**
     * 
     * @param monoID
     * @return
     *     returns ca.gc.hc.webservice.MonographWS
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMonographById", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetMonographById")
    @ResponseWrapper(localName = "getMonographByIdResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetMonographByIdResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getMonographByIdRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getMonographByIdResponse")
    public MonographWS getMonographById(
        @WebParam(name = "monoID", targetNamespace = "")
        MonographIdWS monoID);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetVersion")
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.GetVersionResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getVersionRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/getVersionResponse")
    public List<String> getVersion();

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "log", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.Log")
    @ResponseWrapper(localName = "logResponse", targetNamespace = "http://ingredient.webservice.nhpd.hc.gc.ca/", className = "ca.gc.hc.webservice.LogResponse")
    @Action(input = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/logRequest", output = "http://ingredient.webservice.nhpd.hc.gc.ca/IngredientService/logResponse")
    public void log(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
