package ca.gc.hc.nhpd.servicepilot.monograph;

import ca.gc.hc.nhpd.dao.MonographDao;
import ca.gc.hc.nhpd.dao.OrganismPartDao;
import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;

import ca.gc.hc.nhpd.exception.IngredientsException;

import ca.gc.hc.nhpd.model.ChemicalMonographEntry;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.CodedRisk;
import ca.gc.hc.nhpd.model.DependantObject;
import ca.gc.hc.nhpd.model.DietaryAllowance;
import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.DosageFormGroup;
import ca.gc.hc.nhpd.model.DosageUnit;
import ca.gc.hc.nhpd.model.GeneratedMonograph;
import ca.gc.hc.nhpd.model.GenericText;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientId;
import ca.gc.hc.nhpd.model.MonographEntry;
import ca.gc.hc.nhpd.model.MonographDirectionForUse;
import ca.gc.hc.nhpd.model.MonographDose;
import ca.gc.hc.nhpd.model.MonographDoseCombination;
import ca.gc.hc.nhpd.model.MonographDuration;
import ca.gc.hc.nhpd.model.MonographId;
import ca.gc.hc.nhpd.model.MonographPhase;
import ca.gc.hc.nhpd.model.MonographPreparation;
import ca.gc.hc.nhpd.model.MonographRiskStatement;
import ca.gc.hc.nhpd.model.MonographRouteOfAdministration;
import ca.gc.hc.nhpd.model.MonographSourceIngredient;
import ca.gc.hc.nhpd.model.MonographSourceMaterial;
import ca.gc.hc.nhpd.model.MonographSourceOrganismPart;
import ca.gc.hc.nhpd.model.MonographSubIngredientCombination;
import ca.gc.hc.nhpd.model.MonographSubIngredientDose;
import ca.gc.hc.nhpd.model.MonographSubIngredientDoseCombination;
import ca.gc.hc.nhpd.model.MonographUse;
import ca.gc.hc.nhpd.model.OrganismMonographEntry;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.PreparationTypeGroup;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.RecommendedDietaryAllowance;
import ca.gc.hc.nhpd.model.RestrictionType;
import ca.gc.hc.nhpd.model.RiskStatement;
import ca.gc.hc.nhpd.model.RiskType;
import ca.gc.hc.nhpd.model.RouteOfAdministration;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.model.TextMonographSourceMaterial;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.model.UnitsType;

import ca.gc.hc.nhpd.servicepilot.ingredient.IngredientSessionBean;

import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.ThreadContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;

import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * This class provides the Monograph service implementation. It is targetted for
 * future deployment as an EJB Session Bean, which implements monograph
 * management services. It is currently used by the Monograph Web Service client
 * 
 * TODO:  In order to get the web service to return the appropriate 
 * source organism part (IMSD # 2336), I tried to enable the MonographDose =>
 * MonographDoseCombination relationship to get to the MonographEntry and
 * eventually get a handle on the Ingredient.  The ingredient is required to
 * get the MonographSourceOrganismParts.  
 * 
 * This attempt did not work as it was causing circular references within
 * hibernate, and was causing lazy init errors on objects that had been 
 * instantiated.  After spending 3 days on this problem, I decided to simply
 * make the Monograph Entry object available at the point in the code when 
 * we need the Monograph Entry's ingredient.  This code change works.
 * 
 * As an alternative and once we can update the data model, I would like 
 * to change the relationship between the MonographDose and MonographDoseCombination
 * from a one-to-many using a table join, to a one-to-many using no join table.  This
 * may help hibernate get around the circular reference issue. 
 * 
 * An alternative approach that could be considered is to add an additional column
 * to the materialized view that provides the SourceOrganismPart information.  This 
 * additional column could possibly contain a piece of information that would 
 * help uniquely identify which SourceOrganismPart to return - GB
 * 
 * @author MRABE
 * @update GBRUNET
 * 
 */

public class MonographSessionBean {

   
	private static final Log log = LogFactory.getLog(MonographSessionBean.class);
    
    public MonographSessionBean() {
        // log.debug ("MSB ctor");
    }

    /*
     * Searches monographs via a text String. Uses the Monograph_Synonyms_MV
     * Oracle Materialized View. Search results are specially constructed at the
     * DAO tier from the JDBC resultset - Hibernate ORM not used in this case to
     * maximize performance.
     */
    public List<MonographSearchCriteria> searchMonographs(MonographSearchCriteria criteria)
            throws RemoteException {
        log.debug("MonographSessionBean: searchIngredients ");

        List<MonographSearchCriteria> monographSearchResults = null;
        Session session = null;
        MonographDao dao = new MonographDao();

        try {
            session = HibernateUtil.startHibernateTransaction(false);
            monographSearchResults = dao.findBySearchCriteria(
                    (SearchCriteria) criteria, ThreadContext.getInstance()
                            .getUserLanguage(), MonographServicePropertyManager
                            .getPlaMaxSearchResultSize());

            if (monographSearchResults == null) {
                log.error("DAO returned Null Monograph Search results");
            } else {
                log.debug("Retrieved " + monographSearchResults.size()
                        + " monographs");
            }
        } catch (Throwable ex) {
            log.error("Exception occured in searchMonographs ", ex);
            ex.printStackTrace();
        } finally {
        	HibernateUtil.commitHibernateTransaction(session);
        }

        return monographSearchResults;

    }
    
    /*
     * Retrieves the object graph for a single monograph object.
     */
    public GeneratedMonograph getMonographById(MonographId monographId) {
       
        MonographDao dao = new MonographDao();
        GeneratedMonograph generatedMonograph = null;
        log.debug("MSB: getById:" + monographId.getId());
        Session session = null;
        
        try {
            session = HibernateUtil.startHibernateTransaction(false);
            generatedMonograph = (GeneratedMonograph) dao.findById(monographId
                    .getId(), false);
            loadMonographData(generatedMonograph);
        }
        // exceptions we expect may happen
        catch (IngredientsException ex) {
            throw ex;
        }
        // any other exception is unexpected...
        catch (Throwable ex) {
            log.error("Unexpected exception occured in getMonographById ", ex);
            ex.printStackTrace();
            throw new IngredientsException(ex);
        } finally {
        	HibernateUtil.commitHibernateTransaction(session);
        }

        log.debug("MSB: returning Monograph:" + monographId.getId()
                + "  Name: " + generatedMonograph.getName());
        return generatedMonograph;

    }
    
    private void loadChemicalMonographEntryAttrs(
            ChemicalMonographEntry chemMonoEntry) {

        // load the MonographSourceOrganismPart (source material)
        Set<MonographSourceOrganismPart> orgParts = chemMonoEntry
                .getMonographSourceOrganismParts();
        
        Set<MonographEntry> monographEntries = new TreeSet<MonographEntry>();
        monographEntries.add(chemMonoEntry);
        
        
        if (orgParts != null) {
            for (MonographSourceOrganismPart msop : orgParts) {
                loadMonographSourceOrganismPart(msop, monographEntries);
            }
        }

        // load the source ingredient. 
        Set<MonographSourceIngredient> sourceIngreds = chemMonoEntry
                .getMonographSourceIngredients();
        if (sourceIngreds != null) {
            for (MonographSourceIngredient msi : sourceIngreds) {
                msi.getIngredient().getAuthorizedName();
                if (msi.getSubPopulation() != null) {
                    loadSubPopulation(msi.getSubPopulation());
                }
            }
        }
    }

    private void loadCommonNames(GeneratedMonograph generatedMonograph) {
        if (generatedMonograph == null)
            return;
        Set<QualifiedSynonym> commonNames = generatedMonograph.getCommonNames();
        if (commonNames != null) {
            for (QualifiedSynonym commonName : commonNames) {
                commonName.getName();
                // log("MSB: Loading ME common name: " + commonName.getText());
            }
        }
    }

    /*
     * TBD - this could be optimized with some sort of metadata implementation
     * that tells us what the relationships captured via the dependant objects
     * are; i.e. what objects have other objects as dependents. e.g.
     * RiskStatement may have RouteOfAdmin as a dependant
     * 
     * NB. We need to track "circular" dependencies. Fish Oil and Seal Oil have
     * Uses which depend on Doses and vice versa. We need to ensure that we
     * catch these situations so that the recursive nature of loading
     * dependencies does not go into an infinite loop; (causes StackOverflow)
     * 
     */
    private void loadDependentObject(DependantObject dependentObject, Set<MonographEntry> monographEntries, int level) {

    	log(level * 4, "** MSB:loadDependentObject:  Type: "
                + getMdoType(dependentObject));

        // if (! (dependentObject instanceof MonographUse)) {
        Set<MonographUse> monoUses = dependentObject.getMonographUses();
        if (monoUses != null) {
            for (MonographUse monoUse : monoUses) {
                log(level * 4, "**** Found " + monoUses.size()
                    + " uses.  Recursing back into loadDependentObject to load...");
                loadDependentObject(monoUse, monographEntries, 1);
            }
        }

        // if (!(dependentObject instanceof MonographDose)) {
            
            loadMonographDoseCombination(dependentObject
                .getMonographDoseCombination(), monographEntries, level++);

        // Added to fix a lazy issue.
        /* same line as below!?
        loadMonographSourceOrganismParts(dependentObject
                .getMonographSourceOrganismParts());
		*/
        
        // MRApr7 changed to a collection
        loadMonographSourceOrganismParts(dependentObject
                .getMonographSourceOrganismParts(), monographEntries);
        
        // Germain 2010/05/03.
        loadDosageFormGroups(dependentObject.getDosageFormGroups());
        
        dependentObject.getOtherDependency();

        loadMonographPreparation(dependentObject.getMonographPreparation());

        loadSubPopulation(dependentObject.getSubPopulation());
        loadMonographSourceIngredient(dependentObject
                .getMonographSourceIngredient());
        loadOtherSources(dependentObject.getOtherSources());
        
        dependentObject.getOtherDependency();

    }

    /*
     * MonographDose is an MDO which contains the MonographDirectionsForUse MDO
     */
    private void loadDoseAttributes(Set<MonographDose> doses, Set<MonographEntry> monographEntries, int level) {

        log.debug("MSB:loadDoseAttributes");
        Iterator<MonographDose> iter = doses.iterator();

        iter = doses.iterator();
        // load the DirectionsForUse associated to the dose
        MonographDose monoDose = null;
        while (iter.hasNext()) {
            monoDose = (MonographDose) (iter.next());
            if (monoDose != null) {
                loadMonographDose(monoDose, monographEntries);
            }
        }

    }

    private void loadDosageForm(DosageForm dosageForm) {
        if (dosageForm == null)
            return;
        dosageForm.getName();
        dosageForm.getCode();
        dosageForm.getDescription();
        dosageForm.getAssessmentRequiredString();
        loadRoutesOfAdministration(dosageForm.getAcceptableRoutesOfAdmin());
        loadSynonyms(dosageForm.getSynonyms());
        dosageForm.getCategoryTestSpecMap();
        if (dosageForm.getDosageUnits() != null) {
            for (DosageUnit dosageUnit : dosageForm.getDosageUnits()) {
                dosageUnit.getCode();
            }
        }
    }

    private void loadDosageForms(Set<DosageForm> dosageForms) {
        for (DosageForm dosageForm : dosageForms) {
            loadDosageForm(dosageForm);
        }
    }

    private void loadDurationAttributes(Set<MonographDuration> durations, Set<MonographEntry> monographEntries) {
        log.debug("MSB:loadDurationAttributes");
        Iterator<MonographDuration> iter = durations.iterator();
        MonographDuration duration = null;
        while (iter.hasNext()) {
            duration = (MonographDuration) (iter.next());
            duration.getDurationStatement();
            duration.getMinimumDuration();
            duration.getMaximumDuration();
            loadUnits(duration.getUnits());
            loadDependentObject(duration, monographEntries, 0);
        }
    }

    // loads all monograph dependent data
    private void loadMonographData(GeneratedMonograph generatedMonograph) {
        /*----------------------------------------------------------------------
         * Load the required elements of the Monograph object graph:
         * 1) retrieve the common and proper names
         * 2) retrieve the associated Ingredient
         * 3) retrieve all associated Monograph Dependant Objects (MDO) from the
         *    Monograph object (and other paths).  These are subclasses of 
         *    DependentObject, an implementation of the "mixin" pattern) 
         * 4) for each MDO retrieved in step 2, get all dependent objects via
         * the strongly typed getters exposed by DependantObject.  If the 
         * retrieved dependant objects contain a MonographUse MDO, we need to
         * recursively retrieve its DependantObjects.   
         *----------------------------------------------------------------------*/

        // Load the common and proper names and Organism parts - now part of
        // the top level Monograph MR Apr 3, 2008
        loadCommonNames(generatedMonograph);
        loadProperNames(generatedMonograph);
        // loadSourceOrganismParts(generatedMonograph);


        // 1) retrieve the MonographEntry data. Each entry is associated with an
        // Ingredient.
        Set<MonographEntry> monographEntries = generatedMonograph
                .getMonographEntries();
        boolean entryFound = loadMonographEntries(monographEntries);
        if (!entryFound) {
            String msg = "No Monograph Entries found for Monograph: "
                    + generatedMonograph.getName() + "  (Monograph Id: "
                    + generatedMonograph.getId() + ")";
            log.error(msg);
            throw new IngredientsException(msg);
        }

        // monograph Doses needs to know abou the ingredient associated with 
        // the monograph entry to load correctly.
         loadMonographDoses(generatedMonograph, monographEntries, 0);

        /*
         * 2) retrieve the Monograph Dependant Objects (MDOs) and other objects
         * contained by the Monograph object.
         * 
         * NB: The MonographDirectionsForUse MDO is contained by the
         * MonographDose, which is contained by the Monograph
         */
        MonographRouteOfAdministration routeOfAdmin = generatedMonograph
                .getMonographRouteOfAdministration();

        // Set<MonographDose> doses =
        // generatedMonograph.getVisibleMonographDoses();
        Set<MonographDose> doses = null;

        Set<MonographDuration> durations = generatedMonograph
                .getMonographDurations();
        Set<MonographRiskStatement> riskStatements = generatedMonograph
                .getMonographRiskStatements();
        Set<MonographUse> uses = generatedMonograph.getMonographUses();
        Set<SubPopulation> subPopulations = generatedMonograph
                .getSubPopulations();
        Set<MonographPhase> monoPhases = generatedMonograph
                .getMonographPhases();
        
        logMdoSizes(routeOfAdmin, doses, durations, riskStatements, uses);

        // 3) for each attribute/collection retrieved in step 2, retrieve all
        // the attributes and dependent objects.
        if (routeOfAdmin != null)
            loadMonographRouteOfAdministration(routeOfAdmin);
        if ((doses != null) && (doses.size() > 0))
            loadDoseAttributes(doses, monographEntries, 0);
        if ((durations != null) && (durations.size() > 0))
            loadDurationAttributes(durations, monographEntries);
        if ((riskStatements != null) && (riskStatements.size() > 0))
            loadRiskStatementAttributes(riskStatements, monographEntries);
        if ((uses != null) && (uses.size() > 0))
            loadUsesAttributes(uses, monographEntries);
        if ((subPopulations != null) && (subPopulations.size() > 0))
            loadSubPopulations(subPopulations);
        if ((monoPhases != null) && (monoPhases.size() > 0))
            loadMonographPhases(monoPhases, monographEntries, 0);
        
    }


	private void loadMonographDose(MonographDose monographDose, Set<MonographEntry> monographEntries) {
        
        if (monographDose == null)
            return;
        monographDose.getDoseMinimum();
        monographDose.getDoseMaximum();
        loadUnits(monographDose.getDoseUnits());
        monographDose.getFrequencyMinimum();
        monographDose.getFrequencyMaximum();
        loadUnits(monographDose.getFrequencyUnits());
        loadRestrictionType(monographDose.getRestrictionType());

        // load contained MonographDirectionForUse collection
        Set<MonographDirectionForUse> monoDirections = monographDose
                .getMonographDirectionsForUse();
        if ((monoDirections != null) && (!monoDirections.isEmpty())) {
            for (MonographDirectionForUse mdfu : monoDirections) {
                mdfu.getDirectionForUse(); // attr 112 on PLA
                
                if (mdfu.getMonographSourceOrganismParts() != null) {
                    Set<MonographSourceOrganismPart> msops = mdfu.getMonographSourceOrganismParts();
                    for (MonographSourceOrganismPart monographSourceOrganismPart : msops) {
                        loadMonographSourceOrganismPart(monographSourceOrganismPart, monographEntries);
                    }
                }
                
                // Directions for use, which is contained by Dose, may also have
                // dependencies...
                log
                        .debug("BEGIN Loading dependent objects of DirectionsForUse");
                loadDependentObject(mdfu, monographEntries, 0);
                log.debug("DONE Loading dependent objects of DirectionsForUse");
            }
        }

        
        // New Code
        if (monographDose.getMonographSourceOrganismParts() != null) {
            loadMonographSourceOrganismParts(monographDose.getMonographSourceOrganismParts(), monographEntries);
        }
        
        // Load MSIDCs
        Set<MonographSubIngredientDoseCombination> msidcs = monographDose
                .getMonographSubIngredientDoseCombinations();

        if (msidcs == null)
            log.debug("MSB: monoSubdoses is null");
        if ((msidcs != null) && (!msidcs.isEmpty())) {
            for (MonographSubIngredientDoseCombination msidc : msidcs) {
                loadMonoSubIngredDoseComboAttrs(msidc);
            }
        }
        
        log.debug("LoadMonographDose: calling loadDependentObject for Dose: " + 
        		monographDose.getCode());
        
        loadDependentObject(monographDose, monographEntries, 0);
        
    }

    private void loadMonographDoseCombination(
            MonographDoseCombination monographDoseCombination, Set<MonographEntry> monographEntries, int level) {

        if (monographDoseCombination == null)
            return;
        
        monographDoseCombination.getCode();
        monographDoseCombination.isVisible();
        
        // loadDependentObject(monographDoseCombination, 0);

        for (MonographDose monographDose : monographDoseCombination
                .getMonographDoses()) {
            loadMonographDose(monographDose, monographEntries);
        }
    }

    /**
     * Touch the MonographDoses to ensure that they are loaded.
     * 
     * @param generatedMonograph
     */
    private void loadMonographDoses(GeneratedMonograph generatedMonograph, Set<MonographEntry> monographEntries, int level1) {

        if (generatedMonograph == null
                || generatedMonograph.getMonographDoses() == null) {
            return;
        }

        for (MonographDose monographDose : generatedMonograph
                .getMonographDoses()) {
            loadMonographDose(monographDose, monographEntries);
        }
    }

    /*
     * private void loadMonographDose(MonographDose monographDose) { if
     * (monographDose == null) return;
     * 
     * loadUnits(monographDose.getDoseUnits());
     * loadUnits(monographDose.getFrequencyUnits());
     * 
     * if (monographDose != null) { loadDependentObject(monographDose, 1); for
     * (MonographSourceOrganismPart monographSourceOrganismPart :
     * monographDose.getMonographSourceOrganismParts()) {
     * monographSourceOrganismPart.getValuesAsString(); } } }
     */

    private boolean loadMonographEntries(Set<MonographEntry> monographEntries) {
        /*
         * Load MonographEntry and Ingredient data. Currently, there is a single
         * Ingredient per Monograph. We reuse the Ingredient Session Bean here;
         * the build script pulls in the IWS code. TODO - should use the
         * IngredientClientProxy here in the future; it would do stuff like
         * session bean lookup/create, etc.)
         */
        
        IngredientSessionBean ingredientSessionBean = new IngredientSessionBean();

        if (monographEntries == null) {
            return false;
        }
        
        for (MonographEntry monographEntry : monographEntries) {
            
            // load the MonographEntry subtype specific data
            loadMonographEntryData(monographEntry);
            // load the Ingredient data associated to the Monograph
            IngredientId ingredientId = new IngredientId();
            ingredientId.setId(monographEntry.getIngredient().getId());

            // Use the IngredientSessionBean to load the Ingredient object
            // graph.
            try {
                ingredientSessionBean.getIngredientById(ingredientId, false);
            } catch (Throwable ex) {
                log
                        .error("MSB: Exception occurred getting Ingredient data via ISB");
                ex.printStackTrace();
            }
        }
        return true;

    }

    private void loadMonographEntryData(MonographEntry monographEntry) {

        // monographEntry.getId();
        Set<MonographSourceOrganismPart> orgParts = monographEntry
                .getMonographSourceOrganismParts();
        Set<MonographEntry> monographEntries = new TreeSet<MonographEntry>();
        monographEntries.add(monographEntry);
        
        if (orgParts != null) {
            for (MonographSourceOrganismPart msop : orgParts) {
                loadMonographSourceOrganismPart(msop, monographEntries);
            }
        }

        // This set moved up from ChemicalMonographEntry Dec 16, 2009
        Set<TextMonographSourceMaterial> tmsms = monographEntry
                .getOtherMonographSources();
        if (tmsms != null) {
            for (TextMonographSourceMaterial tmsm : tmsms) {
                loadTextMonoSourceMaterialAttrs(tmsm);
                
            }
        }

        if (monographEntry instanceof ChemicalMonographEntry) {
            ChemicalMonographEntry chemMonoEntry = (ChemicalMonographEntry) monographEntry;
            loadChemicalMonographEntryAttrs(chemMonoEntry);
        } else if (monographEntry instanceof OrganismMonographEntry) {
            OrganismMonographEntry orgMonoEntry = (OrganismMonographEntry) monographEntry;
            loadOrganismMonographEntryAttrs(orgMonoEntry);
        }

    }

    private void loadMonographPhases(Set<MonographPhase> monoPhases, Set<MonographEntry> monographEntries, int level) {

        for (MonographPhase monoPhase : monoPhases) {
            monoPhase.getName();
            monoPhase.getOrder();

            Set<MonographDoseCombination> monoDoseCombos = monoPhase
                    .getMonographDoseCombinations();
            if (monoDoseCombos != null) {
                for (MonographDoseCombination mdc : monoDoseCombos) {
                    loadMonographDoseCombination(mdc, monographEntries, level);
                }
            }
            Set<MonographDuration> monoDurations = monoPhase
                    .getMonographDurations();
            loadDurationAttributes(monoDurations, monographEntries);
        }

    }

    private void loadMonographPreparation(MonographPreparation monoPrep) {

        if (monoPrep != null) {
            monoPrep.getCode();
            monoPrep.getName();
            monoPrep.getMaximumExtractionRatio();
            monoPrep.getMinimumExtractionRatio();

            PreparationType preparationType = monoPrep.getPreparationType();
            PreparationTypeGroup prepTypeGroup = monoPrep
                    .getPreparationTypeGroup();

            if (preparationType != null) {
                preparationType.getName();
            }
            if (prepTypeGroup != null) {
                prepTypeGroup.getName();
                Set<PreparationType> prepTypes = prepTypeGroup
                        .getPreparationTypes();
                for (PreparationType prepType : prepTypes) {
                    prepType.getCode();
                    prepType.getName();
                }
            }
        }
    }

    private void loadDosageFormGroups(Set<DosageFormGroup> dosageFormGroups) {
        if (dosageFormGroups != null) {
            for (DosageFormGroup dosageFormGroup : dosageFormGroups) {
                loadDosageFormGroup(dosageFormGroup);
            }
        }
    }

    private void loadDosageFormGroup(DosageFormGroup dosageFormGroup) {
        if (dosageFormGroup != null) {
            dosageFormGroup.getCode();
            dosageFormGroup.getName();
            if (dosageFormGroup.getDosageForms() != null) {
                for (DosageForm dosageForm : dosageFormGroup.getDosageForms()) {
                    loadDosageForm(dosageForm);
                }
            }
        }
    }
    
    /* NOT an MDO, but contains MonographDosageForm MDO */
    private void loadMonographRouteOfAdministration(
            MonographRouteOfAdministration monographRouteOfAdministration) {
        log.debug("MSB:loadRouteOfAdminAttributes ");

        monographRouteOfAdministration.getShortDescription(); // need this
        // attr?

        if (monographRouteOfAdministration.getRouteOfAdministration() != null) {
        	RouteOfAdministration routeOfAdministration = monographRouteOfAdministration.getRouteOfAdministration();
        	routeOfAdministration.getCode();
        }
        
        /*
         * A MonographRouteOfAdmin object contains 2 sets of dosage forms: 1.
         * standard dosage forms for the monograph admin route 2. monograph
         * specific dosage forms (of type MonographDosageForm), which are MDOs;
         * e.g. they may have dependencies. NB. MonographDosageForm (an MDO),
         * which used to model the specific dosage forms has been removed from
         * the object model
         * 
         * 
         */
        // standard dosage forms
        Set<DosageForm> applicDosageForms = monographRouteOfAdministration
                .getApplicableDosageForms();
        if (applicDosageForms != null) {
            loadDosageForms(applicDosageForms);
        }
        // specific dosage forms
        Set<DosageForm> specificDosageForms = monographRouteOfAdministration
                .getSpecificDosageForms();
        if (specificDosageForms != null) {
            loadDosageForms(specificDosageForms);
        }

    }

    private void loadMonographSourceIngredient(
            MonographSourceIngredient monoSourceIngred
            ) {
        if (monoSourceIngred != null) {
            monoSourceIngred.getIngredient();
            monoSourceIngred.getSourceMaterialName();
        }
    }

    private void loadMonographSourceOrganismParts(
            Set<MonographSourceOrganismPart> monoSourceOrgParts,
            Set<MonographEntry> monographEntries) {

        if (monoSourceOrgParts != null && monographEntries != null) {
            for (MonographSourceOrganismPart msop : monoSourceOrgParts) {
                loadMonographSourceOrganismPart(msop, monographEntries);
            }
        }

    }

    private void loadMonographSourceOrganismPart(
            MonographSourceOrganismPart monoSourceOrgPart, 
            Set<MonographEntry> monographEntries) {
        
        if (monoSourceOrgPart == null)
            return;

        if (monographEntries == null) {
            return;
        }
        
        monoSourceOrgPart.getSourceMaterialName();
        
        OrganismPart orgPart = monoSourceOrgPart.getOrganismPart();
        // Ingredient ingredient = monographEntry.getIngredient();

        /* Previous code navigating the object model. 
        if (orgPart != null) {
            orgPart.getName();
            loadSubIngredients(orgPart);

            OrganismPartType orgPartType = orgPart.getType();
            if (orgPartType != null) {
                orgPartType.getCode();
                log.debug("MSB: got org part type code: "
                        + orgPartType.getCode());
            }
        }
        */

        Set<Long> ingredientIds = new TreeSet<Long>();
        
        for (MonographEntry monographEntry : monographEntries) {
            if (monographEntry.getIngredient() != null
                    && monographEntry.getIngredient().getId() != null) {
                ingredientIds.add(monographEntry.getIngredient().getId());
            }
        }

        if (ingredientIds.size() > 1) {
            log.error("Too many Ingredient Ids exists for this sourceOrganismPart!");
            throw new IngredientsException("Too many Ingredient Ids exists for this sourceOrganismPart!");
        }
        
        Long ingredientId = (Long) ingredientIds.iterator().next();
                
        OrganismPartDao orgPartDao = new OrganismPartDao();
        List<SourceOrganismPart> orgPartResults = 
                    orgPartDao.getSourceOrganismParts(ingredientId, orgPart.getId(), monoSourceOrgPart.isLanguageFrench());
                        
        
        if (orgPartResults != null) {
            // Pull the first available object.
            Object object = orgPartResults.iterator().next();
            if (object instanceof SourceOrganismPart) {
                monoSourceOrgPart.setSourceOrganismPart(orgPartResults.iterator().next());
                
            }
        }
    }

    private void loadMonographSubIngredientCombination(
            MonographSubIngredientCombination msic) {
        if (msic != null) {
            msic.getCode();

            SubIngredient primarySubIngred = msic.getPrimarySubIngredient();
            SubIngredient secondarySubIngred = msic.getSecondarySubIngredient();
            if (primarySubIngred != null) {
                loadSubIngredient(primarySubIngred);
            }
            if (secondarySubIngred != null) {
                loadSubIngredient(secondarySubIngred);
            }

            msic.getMinimumRatio();
            msic.getMinimumRatio();
        }

    }

    private void loadMonographSubIngredientDoseAttrs(
            MonographSubIngredientDose monoSubDose) {
        MonographSubIngredientCombination msic = monoSubDose
                .getSubIngredientCombination();
        loadMonographSubIngredientCombination(msic);

        // log("loading mono dose subingred: " +
        // monoSubDose.getSubIngredient().getName());
        monoSubDose.getDoseMinimum();
        monoSubDose.getDoseMaximum();
        loadUnits(monoSubDose.getDoseUnits());
        monoSubDose.getFrequencyMinimum();
        monoSubDose.getFrequencyMaximum();
        loadUnits(monoSubDose.getFrequencyUnits());
        loadRestrictionType(monoSubDose.getRestrictionType());

    }

    private void loadMonoSubIngredDoseComboAttrs(
            MonographSubIngredientDoseCombination msidc) {
        Set<MonographSubIngredientDose> msids = msidc
                .getMonographSubIngredientDoses();
        if (msids != null) {
            for (MonographSubIngredientDose msid : msids) {
                loadMonographSubIngredientDoseAttrs(msid);
            }
        }
    }

    /*
     * Load PreparationType and PreparationTypeGroup attributes. The
     * PreparationType Set is populated with both: a) individual
     * PreparationTypes populated for the OrganismMonographEntry b) all
     * preparationTypes that are associated with any PreparationTypeGroups
     * populated in the OrganismMonographEntry.
     */
    private void loadOrganismMonographEntryAttrs(
            OrganismMonographEntry orgMonoEntry) {

        Iterator iter = null;

        Set<PreparationType> prepTypes = orgMonoEntry.getPreparationTypes();
        if (prepTypes != null) {
            iter = prepTypes.iterator();
            while (iter.hasNext()) {
                loadPreparationType((PreparationType) (iter.next()));
            }
        }

        PreparationTypeGroup prepTypeGroup = null;
        Set<PreparationTypeGroup> prepTypeGroups = orgMonoEntry
                .getPreparationTypeGroups();
        if (prepTypeGroups != null) {
            // PreparationTypeDao prepTypeDao = new PreparationTypeDao();
            iter = prepTypeGroups.iterator();
            while (iter.hasNext()) {
                prepTypeGroup = (PreparationTypeGroup) (iter.next());
                loadPreparationTypeGroup(prepTypeGroup);
            }
        }

    }

    /* MR added May 27 2008 */
    /* set of GenericText */
    private void loadOtherSources(Set otherSources) {
        if (otherSources == null)
            return;
        Iterator iter = otherSources.iterator();
        while (iter.hasNext()) {
            GenericText genText = (GenericText) iter.next();
            genText.getId();
            genText.getText();
            // log ("MSB: loadOtherSources - text: " + genText.getText());
        }
    }

    private void loadRestrictionType(RestrictionType restrictionType) {

        if (restrictionType == null)
            return;
        restrictionType.getCode();
        restrictionType.getName();
        restrictionType.getShortDescription();
    }

    /* - unused at this time.  GB 2011/02/09
    private void loadSubIngredients(OrganismPart orgPart) {
        if (orgPart == null)
            return;

        Set<SubIngredient> subIngredients = orgPart.getSubIngredients();
        if (subIngredients != null) {
            Iterator iter = subIngredients.iterator();
            while (iter.hasNext()) {
                SubIngredient subIngred = (SubIngredient) (iter.next());
                loadSubIngredient(subIngred);
            }
        }
    }
    */

    // want to reuse SubIngredientWS, so load its attrs.
    private void loadSubIngredient(SubIngredient subIngredient) {
        if (subIngredient == null)
            return;

        Ingredient ingredient = subIngredient.getIngredient();
        // approved name
        ingredient.getAuthorizedName();
        // common names
        Set<QualifiedSynonym> commonNames = ingredient.getCommonNames();
        Iterator iter = commonNames.iterator();
        while (iter.hasNext()) {
            QualifiedSynonym commonName = (QualifiedSynonym) iter.next();
            commonName.getName();
        }
        
        //proper names
        Set<QualifiedSynonym> properNames = ingredient.getProperNames();
        iter = properNames.iterator();
        while (iter.hasNext()) {
            QualifiedSynonym properName = (QualifiedSynonym) iter.next();
            properName.getName();
            properName.getDisplayName();
        }
        
        // organism part name
        OrganismPart orgPart = subIngredient.getParentOrganismPart();
        if (orgPart != null) {
            orgPart.getName();
        }

    }

    private void loadSubPopulation(SubPopulation subPopulation) {
        if (subPopulation == null)
            return;

        subPopulation.getCode();
        subPopulation.getName();
        subPopulation.getMinimumAge();
        subPopulation.getMaximumAge();
        loadUnits(subPopulation.getMinimumAgeUnits());
        loadUnits(subPopulation.getMaximumAgeUnits());
        subPopulation.getDescription();
    }

    private void loadSynonym(Synonym synonym) {
        if (synonym == null)
            return;

        synonym.getNameE();
        synonym.getNameF();
    }

    private void loadSynonyms(Set<Synonym> synonyms) {
        if (synonyms == null)
            return;
        for (Synonym synonym : synonyms) {
            loadSynonym(synonym);
        }
    }

    private void loadPreparationType(PreparationType prepType) {
        if (prepType == null)
            return;

        prepType.getName();
        prepType.getCode();
        prepType.getShortDescription();
        
        if (prepType.getRestrictedSolvents() != null
        		&& prepType.getRestrictedSolvents().size() != 0) {
        	for (Solvent solvent : prepType.getRestrictedSolvents()) {
        		loadSolvent(solvent);
        	}
        }
    }

    private void loadSolvent(Solvent solvent) {
    	if (solvent == null) {
    		return;
    	}
    	solvent.getName();
    	solvent.getCode();
    }
        
    private void loadPreparationTypeGroup(PreparationTypeGroup prepTypeGroup) {
        if (prepTypeGroup == null)
            return;

        prepTypeGroup.getName();
        prepTypeGroup.getCode();
        prepTypeGroup.getShortDescription();
        Set prepTypes = prepTypeGroup.getPreparationTypes();
        Iterator prepTypeIter = prepTypes.iterator();
        if (prepTypeIter != null) {
            PreparationType prepType = null;
            while (prepTypeIter.hasNext()) {
                prepType = (PreparationType) (prepTypeIter.next());
                loadPreparationType(prepType);
                log.debug("loaded SUB prepType " + prepType.getDescription());
            }
        }
    }

    private void loadProperNames(GeneratedMonograph generatedMonograph) {
        if (generatedMonograph == null)
            return;
        Set<QualifiedSynonym> properNames = generatedMonograph.getProperNames();
        if (properNames != null) {
            for (QualifiedSynonym properName : properNames) {
                properName.getName();
                // log("MSB: Loading ME proper name: " + commonName.getText());
            }
        }
    }

    private void loadRoutesOfAdministration(
            Set<RouteOfAdministration> routesOfAdministration) {
        if (routesOfAdministration == null)
            return;

        for (RouteOfAdministration routeOfAdministration : routesOfAdministration) {
            loadRouteOfAdministration(routeOfAdministration);
        }
    }

    private void loadRouteOfAdministration(
            RouteOfAdministration routeOfAdministration) {
        if (routeOfAdministration == null)
            return;
        routeOfAdministration.getNameE();
        routeOfAdministration.getNameF();
    }

    /*
     * private void loadSourceOrganismParts(GeneratedMonograph
     * generatedMonograph) { if (generatedMonograph == null ) return; Set<OrganismPart>
     * orgParts = generatedMonograph.getSourceOrganismParts(); OrganismPart
     * orgPart = null; Iterator iter = orgParts.iterator(); while
     * (iter.hasNext()) { orgPart = (OrganismPart)(iter.next());
     * loadOrganismPart(orgPart); } }
     */

    private void loadRiskStatementAttributes(Set<MonographRiskStatement> risks, Set<MonographEntry> monographEntries) {
        log.debug("MSB:loadRiskStatementAttributes");
        Iterator iter = risks.iterator();
        MonographRiskStatement risk = null;
        while (iter.hasNext()) {
            risk = (MonographRiskStatement) (iter.next());
            RiskStatement riskStatement = risk.getRiskStatement();
            if (riskStatement != null)
                loadRiskStatementAttrs(riskStatement);

            loadDependentObject(risk, monographEntries, 0);
        }
    }

    /*
     * RiskStatement object graph:
     * 
     * RiskStatement subclasses Synonym, and inherits the Name attribute, which
     * can store the risk text. It also contains CodedRisk, which contains a Set
     * of RiskStatements TODO. Do I need to look at both statement sources?
     */
    private void loadRiskStatementAttrs(RiskStatement riskStatement) {

        log.debug("Risk Name: " + riskStatement.getName());
        CodedRisk codedRisk = riskStatement.getCodedRisk();
        if (codedRisk == null)
            return;

        // iterate thru the statements to initialize...
        Set<RiskStatement> statements = codedRisk.getRiskStatements();
        Iterator statementIter = statements.iterator();
        while (statementIter.hasNext()) {
            String stmnt = (String) (statementIter.next());
            log.debug("loaded risk statement: " + stmnt);
        }

        RiskType riskType = codedRisk.getType();
        if (riskType == null)
            return;

        riskType.getCode();
        riskType.getDescription();
        log.debug("RiskType:  Code: " + riskType.getCode() + " Desc: "
                + riskType.getDescription());
    }

    private void loadSubPopulations(Set<SubPopulation> subPopulations) {

        Iterator iter = subPopulations.iterator();
        SubPopulation subPop = null;
        while (iter.hasNext()) {
            subPop = (SubPopulation) (iter.next());
            loadSubPopulation(subPop);
        }
    }

    private void loadTextMonoSourceMaterialAttrs(
            TextMonographSourceMaterial tmsm) {
        tmsm.getShortDescription();
        tmsm.getSourceMaterialName();
    }

    /*
     * private void loadMonographDosageFormAttributes(MonographDosageForm mdf) {
     * if (mdf == null) return; DosageForm dosageForm = mdf.getDosageForm(); if
     * (dosageForm != null) loadDosageFormAttributes(dosageForm); }
     */

    private void loadUnits(Units units) {
        if (units != null) {
            units.getCode();
            units.getName();
            UnitsType unitType = units.getType();
            if (unitType != null)
                unitType.getName();
        }
    }

    private void loadUsesAttributes(Set<MonographUse> monographUses, Set<MonographEntry> monographEntries) {
        log.debug("MSB:loadUsesAttributes");
        for (MonographUse monographUse : monographUses) {
            monographUse.getLongStatement();
            monographUse.getShortStatement();
            loadDependentObject(monographUse, monographEntries, 0);
            
            // Added to look into the Recommended Dietary Allowance
            if (monographUse != null 
                    && monographUse.getRecommendedDietaryAllowance() != null) {
                RecommendedDietaryAllowance recommendedDietaryAllowance = monographUse.getRecommendedDietaryAllowance();
                recommendedDietaryAllowance.getUnits();
                if (recommendedDietaryAllowance.getChemicalMonographEntry() != null) {
                    ChemicalMonographEntry chemicalMonographEntry = recommendedDietaryAllowance.getChemicalMonographEntry();
                    if (chemicalMonographEntry.getIngredient() != null) {
                        Ingredient ingredient = chemicalMonographEntry.getIngredient();
                        if (ingredient instanceof ChemicalSubstance) {
                            ChemicalSubstance chemicalSubstance = (ChemicalSubstance) ingredient;
                            chemicalSubstance.getAuthorizedName();
                        }
                    }
                }
            
                recommendedDietaryAllowance.getUnits();
                for (DietaryAllowance dietaryAllowance : recommendedDietaryAllowance.getDietaryAllowances()) {
                    dietaryAllowance.getMinimumThreshold();
                    loadSubPopulation(dietaryAllowance.getSubPopulation());
                }
            }
        }
    }
    
    /* just for debugging... */
    private String getMdoType(DependantObject dependantObject) {
        if (dependantObject instanceof MonographDose)
            return "MonographDose";
        if (dependantObject instanceof MonographDirectionForUse)
            return "MonographDirectionForUse";
        if (dependantObject instanceof MonographDuration)
            return "MonographDuration";
        if (dependantObject instanceof MonographRiskStatement)
            return "MonographRiskStatement";
        if (dependantObject instanceof MonographUse)
            return "MonographUse";
        return "Unknown";
    }

    private void logMdoSizes(MonographRouteOfAdministration routeOfAdmin,
            Set<MonographDose> doses, Set<MonographDuration> durations,
            Set<MonographRiskStatement> riskStatements, Set<MonographUse> uses) {

        if (routeOfAdmin != null)
            log.debug("Retrieved " + 1 + " Routes of Admin");
        if (doses != null)
            log.debug("Retrieved " + doses.size() + " Doses");
        if (durations != null)
            log.debug("Retrieved " + durations.size() + " Durations");
        if (riskStatements != null)
            log.debug("Retrieved " + riskStatements.size() + " Risks");
        if (uses != null)
            log.debug("Retrieved " + uses.size() + " Uses");
    }

    /*
     * Logging method which provides indentation.
     */
    private static void log(int indentSize, String msg) {
        String spaces = "                                        ";
        int indent = Math.min(spaces.length(), indentSize);

        StringBuffer buffer = new StringBuffer();
        // prepend the requested number of spaces
        buffer = buffer.append(spaces.substring(0, indent));
        buffer = buffer.append(msg);

        log.debug(buffer.toString());
    }

}
