package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ChemicalMonographEntry;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.CustomOrganismSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.GeneratedMonograph;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.MonographDuration;
import ca.gc.hc.nhpd.model.MonographEntry;
import ca.gc.hc.nhpd.model.MonographPhase;
import ca.gc.hc.nhpd.model.MonographRiskStatement;
import ca.gc.hc.nhpd.model.MonographSourceMaterial;
import ca.gc.hc.nhpd.model.MonographUse;
import ca.gc.hc.nhpd.model.OrganismMonographEntry;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Web Service wrapper class; wraps the complex Monograph model object.   
 * Note that the setters all have null implementations; the setters must appear 
 * for JAXB compliance; e.g. attributes exposed via the Web Service must conform
 * to JavaBean syntax. 
 */
public class MonographWS {

    private static final Log log = LogFactory.getLog(MonographWS.class);

    private GeneratedMonograph generatedMonograph;

    private String version = ThreadContext.getInstance().getVersion();
    
    // ctor taking the wrapped monograph
    public MonographWS(GeneratedMonograph generatedMonograph) {
        // log ("MonographWS ctor!");
        this.generatedMonograph = generatedMonograph;
    }

    // default ctor
    public MonographWS() {
        this.generatedMonograph = new GeneratedMonograph();
    }

    public String getName() {
        return generatedMonograph.getName();
    }

    public void setName(String name) {
        //
    }

    public Date getPublishedDate() {
        return generatedMonograph.getPublishedDate();
    }

    public void setPublishedDate(Date publishedDate) {
        //
    }

    public Long getId() {
        return generatedMonograph.getId();
    }

    public void setId(Long id) {
        //
    }

    public Set<MonographEntryWS> getMonographEntries() {
//        Set<MonographEntryWS> monoEntriesWS = new TreeSet<MonographEntryWS>();
        Set<MonographEntryWS> monoEntriesWS = new HashSet<MonographEntryWS>();        
        Iterator<MonographEntry> iter = generatedMonograph.getMonographEntries().iterator();
        // wrap em, ship em...
        while (iter.hasNext()) {
            MonographEntry monoEntry = (MonographEntry) (iter.next());
//            if (monoEntry instanceof ChemicalMonographEntry) {
//            	monoEntriesWS.add(new ChemicalMonographEntryWS(
//                        (ChemicalMonographEntry) monoEntry));
//            }
            if (monoEntry instanceof OrganismMonographEntry) {
                monoEntriesWS.add(new OrganismMonographEntryWS(
                        (OrganismMonographEntry) monoEntry));
            }

        }
        return monoEntriesWS;
    }

    public void setMonographEntries(Set<MonographEntryWS> monographEntries) {
        // null impl for JAX-WS
    }

    /*
     * Propernames are stored as QualifiedSynonyms. We just need the String name
     * for the Web Service
     */
    public List<String> getProperNames() {
        ArrayList<String> retProperNames = new ArrayList<String>();

        Set<QualifiedSynonym> properNames = generatedMonograph.getProperNames();
        for (QualifiedSynonym qs : properNames) {
            retProperNames.add(qs.getName());
        }

        return retProperNames;
    }

    public void setProperNames(List<String> properNames) {
        // null impl - for JAX-WS
    }

    /*
     * CommonNames are stored as QualifiedSynonym objects. We just need the
     * String name for the Web Service
     */
    public List<String> getCommonNames() {
        ArrayList<String> retCommonNames = new ArrayList<String>();

        Set<QualifiedSynonym> commonNames = generatedMonograph.getCommonNames();
        for (QualifiedSynonym qs : commonNames) {
            retCommonNames.add(qs.getName());
        }

        return retCommonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        // null impl - for JAX-WS
    }

    // This will be deprecated by the new "getMonographSources" method.
    /*
     * TEST - Use Monograph convenience accessor to get Source Organism Parts.
     * This should be hte same as the sum of all organism parts of the
     * MonographEntrys
     */
    /*
     * Now in MonographEntry public Set<MonographSourceOrganismPartWS>
     * getMonographSourceOrganismParts() {
     * 
     * TreeSet<MonographSourceOrganismPartWS> retOrgParts = new TreeSet<MonographSourceOrganismPartWS>();
     * Set<OrganismPart> orgParts = monograph.
     * getMonographSourceOrganismParts();
     * 
     * Iterator iter = orgParts.iterator(); OrganismPart orgPart = null; while
     * (iter.hasNext()) { orgPart = (OrganismPart)(iter.next());
     * retOrgParts.add(new OrganismPartWS(orgPart)); } return retOrgParts; }
     * 
     * public void setSourceOrganismParts(Set<OrganismPartWS> orgParts) {
     * //null impl for JAX-WS }
     */

    /*
     * Just expose the String names
     */
    public Set<String> getMonographSourceMaterials() {
        Set<String> retMonoSourceMaterials = new TreeSet<String>();

        Set<MonographSourceMaterial> monographSourceMaterials = generatedMonograph
                .getMonographSourceMaterials();

        for (MonographSourceMaterial msm : monographSourceMaterials) {
            retMonoSourceMaterials.add(msm.getSourceMaterialName());
            // log("MonographWS: adding sourceMaterial: " +
            // msm.getSourceMaterialName());
        }

        return retMonoSourceMaterials;

    }

    public void setMonographSourceMaterials(Set<String> monoSources) {
        // null impl for JAX-WS
    }

    /**
     * Returns the ingredient associated with this monograph.    
     * 
     * Note:  This is version 1.4 only functionality.  Version 2.0 returns the
     * ingredients via the Monograph Entries to support the product monographs.
     */
    public IngredientWS getIngredientWS() {
    	
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        IngredientWS retIngredient = null;
        /*
         * Get ingredient object graph via the MonographEntry. For now, we are
         * limited to single ingredient monographs, a single MonographEntry
         * contains the ingredient. Multi ingredient monographs will be
         * addressed at a later date.
         */
        Set<MonographEntry> monoEntries = generatedMonograph
                .getMonographEntries();

        if (monoEntries != null) {
            for (MonographEntry monoEntry : monoEntries) {
                Ingredient ingredient = monoEntry.getIngredient();
                if (ingredient != null) {
                    // return a wrapper for the ingredient subtype. Depending on
                    // the subtype,
                    // a different set of attributes are returned.
                    retIngredient = wrapIngredient(ingredient);
                    log.debug("Returning ingredient of type: "
                            + retIngredient.getType());
                    return retIngredient;
                } else {
                    log.error("MonographWS.getIngredient: No Ingredient in MonographEntry!");
                    throw new IngredientsException(
                            "No ingredient associated to the requested Monograph");
                }
            }
        }

        return retIngredient;

    }

    public void setIngredientWS(IngredientWS ingredient) {
        // null impl
    }

    private IngredientWS wrapIngredient(Ingredient ingredient) {

        try {

            if (ingredient instanceof ChemicalSubstance) {
                log.debug("Wrapping ChemicalSubstance Ingredient...");
                return new ChemicalSubstanceWS((ChemicalSubstance) ingredient,
                        false);
            }
            if (ingredient instanceof DefinedOrganismSubstance) {
                log.debug("Wrapping DefinedOrganismSubstance Ingredient: id: "
                        + ingredient.getId());
                return new DefinedOrganismSubstanceWS(
                        (DefinedOrganismSubstance) ingredient, false);
            }
            if (ingredient instanceof CustomOrganismSubstance) {
                log.debug("Wrapping CustomOrganismSubstance Ingredient...");
                return new CustomOrganismSubstanceWS(
                        (CustomOrganismSubstance) ingredient, false);
            }

        } catch (Exception ex) {
            log.error("CAUGHT EXCEPTION: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Throwable t) {
            log.error("CAUGHT THROWABLE: " + t.getMessage());
            t.printStackTrace();
        }

        log.error(" Unknown Ingredient subtype in WrapIngredient!");
        return new IngredientWS(ingredient);
    }

    /*-------------------------------------------------------------------------
     * Monograph Dependant Objects
     *------------------------------------------------------------------------*/

    /*
     * Doses: moved to MonographEntry, now MonographDoseCombination public Set<MonographDoseWS>
     * getMonographDoses( ) {
     * 
     * Set<MonographDoseWS> retDoses = new TreeSet<MonographDoseWS>();
     * 
     * Set<MonographDose> doses = monograph.getVisibleMonographDoses();
     * MonographDose dose = null;
     * 
     * 
     * log.debug("Iterating thru monoDoses: Size: " + doses.size()); Iterator
     * iter = doses.iterator(); while (iter.hasNext()) { Object object =
     * iter.next(); if (!(object instanceof MonographDose)) log.warn("NOT a
     * MonographDose!"); else { dose = (MonographDose)(object); retDoses.add(new
     * MonographDoseWS(dose)); } } log.debug("DONE Iterating thru monoDoses:
     * Size: " + doses.size()); return retDoses; }
     * 
     * public void setMonographDoses(Set<MonographDoseWS> monographDoses) {
     * //null impl }
     */

    /*
     * Expose the MonographRouteOfAdministration object, which includes the
     * MonographDosageForm (MDF)object. MDF contains the DosageForm class, which
     * provides an "isAssessmentRequired" flag which allows the client to
     * determine if the dosage form is suitable for inclusion onb te compndial
     * form (e.g. should only display the option if assessment is not required)
     */
    /*
     * changed to single ROA public Set<MonographRouteOfAdministrationWS>
     * getMonographRoutesOfAdmin( ) { Set<MonographRouteOfAdministrationWS>
     * retAdminRoutes = new TreeSet<MonographRouteOfAdministrationWS>();
     * 
     * Set<MonographRouteOfAdministration> adminRoutes =
     * monograph.getAllMonographRoutesOfAdministration();
     * MonographRouteOfAdministration routeOfAdmin = null; Iterator iter =
     * adminRoutes.iterator(); while (iter.hasNext()) { routeOfAdmin =
     * (MonographRouteOfAdministration)(iter.next()); retAdminRoutes.add(new
     * MonographRouteOfAdministrationWS(routeOfAdmin)); }
     * 
     * return retAdminRoutes; }
     */

    /*
     * Expose the MonographRouteOfAdministration object, which includes the
     * MonographDosageForm (MDF)object. MDF contains the DosageForm class, which
     * provides an "isAssessmentRequired" flag which allows the client to
     * determine if the dosage form is suitable for inclusion on the compndial
     * form (e.g. should only display the option if assessment is not required)
     */
    public MonographRouteOfAdministrationWS getMonographRouteOfAdmin() {
        return new MonographRouteOfAdministrationWS(generatedMonograph
                .getMonographRouteOfAdministration());
    }

    public void setMonographRouteOfAdmin(
            MonographRouteOfAdministrationWS routesOfAdmin) {
        // null impl
    }

    // wrap up the collection of MonographUses
    public Set<MonographUseWS> getMonographUses() {
        Set<MonographUseWS> retMonoUses = new TreeSet<MonographUseWS>();
        Set<MonographUse> monographUses = generatedMonograph.getMonographUses();
        if (monographUses != null) {
            for (MonographUse monoUse : monographUses) {
                retMonoUses.add(new MonographUseWS(monoUse, 0));
            }
        }
        return retMonoUses;
    }

    public void setMonographUses(Set<MonographUseWS> monographUses) {
        // null impl
    }

    public Set<MonographDurationWS> getMonographDurations() {
        Set<MonographDurationWS> retMonoDurations = new TreeSet<MonographDurationWS>();
        Set<MonographDuration> monoDurations = generatedMonograph
                .getMonographDurations();
        if (monoDurations != null) {
            for (MonographDuration monoDuration : monoDurations) {
                retMonoDurations.add(new MonographDurationWS(monoDuration));
            }
        }

        return retMonoDurations;
    }

    public void setMonographDurations(
            Set<MonographDurationWS> monographDurations) {
        // null impl
    }

    /*
     * NB. Need to navigate from MonographRiskStatement to RiskType to
     * categorize the 3 types: (PLA form attribute #s: 113, 114, 115)
     * 
     * Monograph -> MonographRiskStatement-> RiskStatement -> CodedRisk ->
     * RiskType
     */
    public Set<MonographRiskStatementWS> getMonographRiskStatements() {
        // although there is no accessor for RiskStatements in the
        // DependentObject,
        // RiskStatements may be dependant on other MDO's such as the
        // RouteOfAdmin.
        Set<MonographRiskStatementWS> retRiskStatements = new TreeSet<MonographRiskStatementWS>();

        Set<MonographRiskStatement> riskStatements = generatedMonograph
                .getMonographRiskStatements();

        // wrap 'em, ship 'em...
        if (riskStatements != null) {
            for (MonographRiskStatement riskStatement : riskStatements) {
                retRiskStatements.add(new MonographRiskStatementWS(
                        riskStatement));
            }
        }

        return retRiskStatements;

    }

    public void setMonographRiskStatements(
            Set<MonographRiskStatementWS> monographRiskStatements) {
        // null impl
    }

    /**
     * Returns a list of subpopulations.  Done as a treeset to preserve the
     * order.
     * 
     * @return an ordered list of subpopulations.
     */
    public TreeSet<SubPopulationWS> getSubPopulations() {
        
        TreeSet<SubPopulationWS> subPopulationsWS = new TreeSet<SubPopulationWS>();
        Set<SubPopulation> subPopulations = generatedMonograph.getSubPopulations();
        if (subPopulations != null && subPopulations.size() != 0) {
            for (SubPopulation sortedSubPopulation : subPopulations) {
                subPopulationsWS.add(new SubPopulationWS(sortedSubPopulation));
            }
        }
        return subPopulationsWS;
    }

    public void setSubPopulations(TreeSet<SubPopulationWS> subPopulations) {
        //
    }

    public Set<MonographPhaseWS> getMonographPhases() {
        Set<MonographPhaseWS> retMonoPhases = new TreeSet<MonographPhaseWS>();
        Set<MonographPhase> monoPhases = generatedMonograph
                .getMonographPhases();

        if (monoPhases != null) {
            for (MonographPhase monoPhase : monoPhases) {
                retMonoPhases.add(new MonographPhaseWS(monoPhase));
            }
        }

        return retMonoPhases;
    }

    public void setMonographPhases(Set<MonographPhaseWS> phases) {
        //
    }

}
