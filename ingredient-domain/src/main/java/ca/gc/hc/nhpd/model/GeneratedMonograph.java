package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*******************************************************************************
 * This class represents an entry in a Compendium. These are a collection of
 * Ingredients whose characteristics, uses, and limitations are known and
 * published by Health Canada in a standard monograph format.<br/>
 * 
 * Submissions using these ingredients within the published parameters do not
 * need additional evidence to support their use. A single Monograph may have
 * multiple entries (one per Ingredient), however they usually only have one.
 */
public class GeneratedMonograph extends Monograph implements Comparable, NamedObject {

	public static final String LANG_EN = Locale.ENGLISH.getISO3Language(); // ISO 639.2
    public static final String LANG_FR = Locale.CANADA_FRENCH.getISO3Language(); // ISO 639.2
   
    private static final String CITATION_TITLE_NHPD_REMOVE =
        "NHPD Proper Name as per the Natural Health Products Regulations";

    private static final String COMMA_SPACE = ", ";
    private static final String EMPTY_STRING = "";
    private static final Logger log = Logger.getLogger(GeneratedMonograph.class);
    private static final String NAME_CLEAN_PATTERN = "[0-9\\-]";
    private static final Pattern patternRemoveNumbersDash = Pattern.compile(NAME_CLEAN_PATTERN);
 
    //~ Instance fields --------------------------------------------------------
    private transient Set<Citation> allCitations;
    private String appendixE;
    private String appendixF;
    private transient Set<QualifiedSynonym> commonNames;
    private Set<GenericText> doseNotes;
    private Set<GenericText> generalNotes;
    private Set<MonographCitationJoin> monographCitationJoins;
    private Set<MonographDuration> monographDurations;
    private Set<MonographRiskStatement> monographRiskStatements;
    private MonographRouteOfAdministration monographRouteOfAdministration;
    private Set<MonographUse> monographUses;
    private transient Set<MonographEntry> monographEntriesWithSourceNotes;
    private MonographGroup monographGroup;
    private Set<MonographPhase> monographPhases;
    private transient TreeSet<MonographSourceMaterial> monographSourceMaterialsE;
    private transient TreeSet<MonographSourceMaterial> monographSourceMaterialsF;
    private Set<GenericText> nmiNotes;
    private Attachment pdfAttachmentEnglish;
    private Attachment pdfAttachmentFrench;
    private transient Set<QualifiedSynonym> properNames;
    private Date publishedDate;
    private Set<GenericText> riskNotes;
    // private Set<OrganismPart> sourceOrganismParts;
    private Set<GenericText> specificationInfo;
    private Set<GenericText> storageConditions;
    private Set<SubPopulation> subPopulations;

      
    //~ public -----------------------------------------------------------------

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object being compared.
     *
     * @return  comparator integer value.
     *
     * @throws  ClassCastException  when compared object is of wrong class.
     */
    public int compareTo(Object o) throws ClassCastException {

        if (o == null) return -1;
        
        // Sort based on the name if removal of the numbers cause issues.
        if (getNameForSorting().equals(((Monograph) o).getNameForSorting())) {
            return StringComparator.compare(getName(),((Monograph) o).getName());
        }

        return StringComparator.compare(getNameForSorting(),
                                        ((Monograph) o).getNameForSorting());
        
    }


    /**
     * Helper method that travels through all the associated classes and returns
     * a single complete list of citations.
     *
     * <p>The information returned by this list is cached for performance
     * reasons.
     *
     * @return  a set of Citation objects for this monograph
     */
    public Set<Citation> getAllCitations() {

        if (allCitations == null) {
            allCitations = new TreeSet<Citation>(new Citation.NameComparator());

        if (getMonographBibliography() != null) {
            allCitations.addAll(getMonographBibliography());
        }

        allCitations.addAll(getDependantObjectCitations(
                            getMonographDurations()));
        allCitations.addAll(getDependantObjectCitations(
                            getMonographRiskStatements()));
        allCitations.addAll(getDependantObjectCitations(
                            getMonographUses()));

        if (getMonographEntries() != null) {
            for (MonographEntry monographEntry : getMonographEntries()) {
                if (monographEntry.getMonographDoseCombinations() != null) {
                    for (MonographDoseCombination combination 
                         : monographEntry.getMonographDoseCombinations()) {
                        allCitations.addAll(getDependantObjectCitations(
                                            combination.getMonographDoses()));
                    }
                }
            }
        }

        if (getMonographRouteOfAdministration() != null) {
            allCitations.addAll(getMonographRouteOfAdministration()
                                .getCitations());
        }

        // Remove "NHPD-ProperName" from citation list.
        try {
            for (Citation citation : allCitations) {
                if (citation.getWorkTitleE().equals(
                            CITATION_TITLE_NHPD_REMOVE)) {
                    allCitations.remove(citation);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        return allCitations;
    }

    /**
     * Gets the English PDF Attachment associated with 
     * this monograph.
     * 
     * @return the English PDF Attachment.
     */
    public Attachment getPdfAttachmentEnglish() {
        return pdfAttachmentEnglish;
    }

    /**
     * Gets the the French PDF Attachment associated with 
     * this monograph.
     * 
     * @return the French PDF attachment.
     */
    public Attachment getPdfAttachmentFrench() {
        return pdfAttachmentFrench;
    }

    /**
     * Gets the PDF Attachment associated with 
     * this monograph in the locale specified language.
     * 
     * @return the locale-specific PDF attachment.
     */
    public Attachment getPdfAttachment() {
        if (isLanguageFrench()) {
            return getPdfAttachmentFrench();
        }
        return getPdfAttachmentEnglish();
    }
    
    /**
     * Returns the MonographDurations 
     *
     * @return  a set of MonographDurations
     */
    public Set<MonographDuration> getMonographDurations() {
        return monographDurations;
    }

       
    /**
     * Gets the MonographRiskStatements
     * 
     * @return  a set of MonographRiskStatements
     */
    public Set<MonographRiskStatement> getMonographRiskStatements() {

        return monographRiskStatements; 
    }

    /**
     * Gets the MonographRoutesOfAdministration for this Monograph if there is
     * one.
     *
     * @return a MonographRoutesOfAdministration
     */
    public MonographRouteOfAdministration getMonographRouteOfAdministration() {
        return monographRouteOfAdministration;
    }


    /**
     * Gets the MOnographUses
     *
     * @return  a set of MonographUses
     */
    public Set<MonographUse> getMonographUses() {
        return monographUses;
    }

    /**
     * Gets the appendix in the language appropriate for the Locale.
     *
     * @return  the locale-specific appendix.
     */
    public String getAppendix() {

        if (isLanguageFrench()) {
            return getAppendixF();
            }

        return getAppendixE();
    }

    /**
     * Gets the appendix in English.
     *
     * @return  the appendix in English.
     *
     * @see     #setAppendixE()
     */
    public String getAppendixE() {
        return appendixE;
    }

    /**
     * Gets the appendix in French.
     *
     * @return  the appendix in French.
     *
     * @see     #setAppendixF()
     */
    public String getAppendixF() {
        return appendixF;
    }

    /**
     * Pulls together a list of Common Names from the associated Monograph
     * Entries.
     *
     * @return  a list of common names.
     */
    public Set<QualifiedSynonym> getCommonNames() {
        // If this information has been cached, then use the cache.
        if (commonNames == null) {
            commonNames = new TreeSet<QualifiedSynonym>();
            if (getMonographEntries() != null) {
                for (MonographEntry monographEntry : getMonographEntries()) {
                    if (monographEntry.getCommonNames() != null) {
                        commonNames.addAll(monographEntry.getCommonNames());
                    }
                }
            }
        }
        return commonNames;
    }
    
    /**
     * Pulls together a list of Common Names from the associated Monograph
     * Entries, removing entries that show other language equivalents 
     *
     * @return  a list of common names.
     */
    public Set<QualifiedSynonym> getSingleLanguageCommonNames() {
        // If this information has been cached, then use the cache.
        if (commonNames == null) {
            commonNames = new TreeSet<QualifiedSynonym>();
            if (getMonographEntries() != null) {
                for (MonographEntry monographEntry : getMonographEntries()) {
                    if (monographEntry.getSingleLanguageCommonNames() != null) {
                        commonNames.addAll(monographEntry.getSingleLanguageCommonNames());
                    }
                }
            }
        }
        return commonNames;
    }
    /**
     * Gets the set of dose notes.
     *
     * @return  the dose notes.
     *
     * @see     #setDoseNotes()
     */
    public Set<GenericText> getDoseNotes() {
        return doseNotes;
    }

    /**
     * Navigates the organisms of this Monograph and attempts to return a set of
     * Family objects.
     *
     * @return  a set of Family objects related to this monograph.
     */
    public Set<Family> getFamilies() {

        if (getOrganisms() == null) {
            return null;
        }

        TreeSet<Family> families = new TreeSet<Family>();

        for (Organism organism : getOrganisms()) {
            families.add(organism.getFamily());
        }

        return families;
    }

    /**
     * Returns the getFamilies operation as a string for display purposes.
     *
     * @return  a String version of the getFamilies.
     */
    public String getFamiliesAsString() {

        if (getFamilies() == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        for (Family family : getFamilies()) {
            if (sb.length() != 0) {
                sb.append(COMMA_SPACE);
            }
            sb.append(family.getName());
        }

        return sb.toString();
    }

    /**
     * Gets the general notes.
     *
     * @return  the general notes.
     *
     * @see     #setGeneralNotes()
     */
    public Set<GenericText> getGeneralNotes() {
        return generalNotes;
    }

    /** 
     * Getter for the set of Monograph Bibliograph citations in the locale
     * specific language.
     *
     * @return  citations associated to the locale specific Monograph
     *          Bibliography.
     *
     * @see     setMonographBibliographyFrench(), setMonographBibliographyEnglish()
     */
    public Set<Citation> getMonographBibliography() {

        if (isLanguageFrench()) {
            return getMonographBibliographyFrench();
        }

        return getMonographBibliographyEnglish();
    }

    /**
     * Getter for the set of Monograph Bibliography English citations.
     *
     * @return  citations associated to the English Monograph Bibliography.
     *
     * @see     setMonographBibliographyEnglish()
     */
    public Set<Citation> getMonographBibliographyEnglish() {
        Set<Citation> output = getFromMonographCitationJoin(
                MonographCitationJoin.RELATION_BIBLIOGRAPHY_ENGLISH);

        return output;
    }

    /**
     * Getter for the set of Monograph Bibliograph French citations.
     *
     * @return  citations associated to the French Monograph Bibliography.
     *
     * @see     setMonographBibliographyFrench()
     */
    public Set<Citation> getMonographBibliographyFrench() {
        return getFromMonographCitationJoin(
                MonographCitationJoin.RELATION_BIBLIOGRAPHY_FRENCH);
    }

    /**
     * Gets the set of MonographCitationJoin that belong to this Monograph.
     *
     * @return  the set of MonographCitationJoin associated to this monograph.
     *
     * @see     setMonographCitationJoin()
     */
    public Set<MonographCitationJoin> getMonographCitationJoins() {
        return monographCitationJoins;
    }

   
    /**
     * Gets the set of MonographDoses that are associated to this Monograph.
     * The MonographEntry -> MonographDoseCombination associations are navigated.
     *
     * @return  the set of MonographUses associated to this Monograph.
     * 
     */
    public Set<MonographDose> getMonographDoses() {
        
       /* 
        * TODO - this method currently is used by MonographDisplayBean to count 
        * the number of SubPopulations associated with monographDoses.  This 
        * behaviour may not be required in the near future based on how the 
        * MonographDose combinations will be rendered.  
        */
        TreeSet<MonographDose> retMonoDoses = new TreeSet<MonographDose>();
        Set<MonographDoseCombination> mdcs = null;
        
        for (MonographEntry monographEntry : getMonographEntries()) {
            mdcs = monographEntry.getMonographDoseCombinations();
            if (mdcs != null) {
                for (MonographDoseCombination mdc : mdcs ) {
                    if (mdc.getMonographDoses() != null) {
                        for (MonographDose md : mdc.getMonographDoses() ) {
                            retMonoDoses.add(md);
                        }   
                    }
                }
            }   
        }
        
        return retMonoDoses;
    }

    /**
     * Gets the set of MonographEntries that have sourceNotes.
     *
     * @return  the set of MonographEntries that have sourceNotes.
     */
    public Set<MonographEntry> getMonographEntriesWithSourceNotes() {

        if (monographEntriesWithSourceNotes == null) {
            populateMonographSourceMaterials();
        }

        return monographEntriesWithSourceNotes;
    }

    /**
     * Gets the MonographGroup object to which this monograph belongs.
     *
     * @return  the MonographGroup for this monograph.
     *
     * @see     setMonographGroup()
     */
    public MonographGroup getMonographGroup() {
        return monographGroup;
    }
    
    public Set<MonographPhase> getMonographPhases() {
        return monographPhases;
    }

    
    /**
     * Getter for the set of Monograph Bibliograph citations in the locale
     * specific language.
     *
     * @return  citations associated to the locale specific Monograph
     *          Bibliography.
     *
     * @see     setMonographBibliographyFrench(), setMonographBibliographyEnglish()
     */
    public Set<Citation> getMonographReferenceReviewed() {

        if (isLanguageFrench()) {
            return getMonographReferenceReviewedFrench();
        }

        return getMonographReferenceReviewedEnglish();
    }

    /**
     * Getter for the set of Monograph Reference Reviewed English citations.
     *
     * @return  citations associated to the English Monograph Reference
     *          Reviewed.
     *
     * @see     setMonographReferenceReviewedEnglish()
     */
    public Set<Citation> getMonographReferenceReviewedEnglish() {
        return getFromMonographCitationJoin(
                MonographCitationJoin.RELATION_REFERENCE_REVIEWED_ENGLISH);
    }

    /**
     * Getter for the set of Monograph Reference Reviewed French citations.
     *
     * @return  citations associated to the French Monograph Reference Reviewed.
     *
     * @see     setMonographReferenceReviewedFrench()
     */
    public Set<Citation> getMonographReferenceReviewedFrench() {
        return getFromMonographCitationJoin(
                MonographCitationJoin.RELATION_REFERENCE_REVIEWED_FRENCH);
    }

    
    /*
     * Retrieves the set of MonographSourceMaterials (Java interface) contained
     * by the MonographEntries associated to this monograph.  
     * MonographSourceMaterials can be any of the following:   
     * - MonographSourceOrganismPart
     * - MonographSourceIngredient
     * - TextMonographSourceMaterial	
     * 
     */
    public Set<MonographSourceMaterial> getMonographSourceMaterials() {
        
    	populateMonographSourceMaterials();
    	if (isLanguageFrench()) {
            return monographSourceMaterialsF;
        }
        return monographSourceMaterialsE;
    }
    
  

    public Set<MonographSourceMaterial> getMonographSourceMaterialsE() {

        if (monographSourceMaterialsE == null) {
            populateMonographSourceMaterials();
        }
        return monographSourceMaterialsE;
    } 
    
    public Set<MonographSourceMaterial> getMonographSourceMaterialsF() {

        if (monographSourceMaterialsF == null) {
            populateMonographSourceMaterials();
        }
        return monographSourceMaterialsF;
    } 
    
    
    
    
    
    

    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /**
     * Returns an instance of the name stripped for sorting purposes. Used by
     * the MonographListAction to generate the Monograph List.
     *
     * @return
     */
    public String getNameForSorting() {
        return adjustForSorting(getName());
    }

    /**
     * Gets the NMI notes.
     *
     * @return  the NMI notes.
     *
     * @see     #setNmiNotes()
     */
    public Set<GenericText> getNmiNotes() {
        return nmiNotes;
    }

    /**
     * Navigates the relationship between the ingredients found in a monograph
     * and their respective organisms in order to return a set of related
     * organisms. These ingredients are qualified as DefinedOrganismSubstance,
     * and the process tries to navigate the Group Tree either using the
     * SimpleOrganismGroup or NamedOrganismGroup in order to find the relevant
     * Organisms. If a problem is encountered, returns a null.
     *
     * @return  a set of Organisms related to this monograph.
     */
    public Set<Organism> getOrganisms() {

        if ((getIngredients() == null) || (getIngredients().size() == 0)) {
            return null;
        }

        TreeSet<Organism> organisms = new TreeSet<Organism>();

        for (Ingredient ingredient : getIngredients()) {
            if (ingredient.getSimpleClassName().equals(
                        Ingredient.INGREDIENT_DOS)) {
                DefinedOrganismSubstance dos = (DefinedOrganismSubstance)
                    ingredient;
                SimpleOrganismGroup sog = (SimpleOrganismGroup) dos
                    .getOrganismGroup();

                if (sog.getSimpleClassName().equals(
                            OrganismGroup.SIMPLEORGANISMGROUP)) {
                    organisms.addAll(sog.getIncludedOrganisms());
                } else if (sog.getSimpleClassName().equals(
                            OrganismGroup.NAMEDORGANISMGROUP)) {
                    NamedOrganismGroup nog = (NamedOrganismGroup) sog;
                    organisms.addAll(nog.getIncludedOrganisms());
                }
            }
        }

        return organisms;
    }

    /**
     * Navigates the organisms of this Monograph and attempts to return a set of
     * OrganismTypeGrup objects.
     *
     * @return  a set of OrganismTypeGroup objects related to this monograph.
     */
    public Set<OrganismTypeGroup> getOrganismTypeGroups() {

        if (getOrganisms() == null) {
            return null;
        }

        TreeSet<OrganismTypeGroup> organismTypeGroups =
            new TreeSet<OrganismTypeGroup>();

        for (Organism organism : getOrganisms()) {
            if (organism.getType() != null) {
                OrganismType organismType = organism.getType();

                if (organismType.getGroup() != null) {
                    organismTypeGroups.add(organismType.getGroup());
                }
            }
        }

        return organismTypeGroups;
    }

    /**
     * Returns the getOrganismTypeGroup operation as a string for display
     * purposes.
     *
     * @return  a String version of the getOrganismTypeGroup.
     */
    //TODO Should be refactored out
    public String getOrganismTypeGroupsAsString() {

        if (getOrganismTypeGroups() == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        for (OrganismTypeGroup organismTypeGroup : getOrganismTypeGroups()) {
            if (sb.length() != 0) {
                sb.append(COMMA_SPACE);
            }
            sb.append(organismTypeGroup.getName());
        }

        return sb.toString();
    }

    /**
     * Pull together a list of Proper Names from the associated Monograph
     * Entries.
     *
     * @return  a list of proper names.
     */
    public Set<QualifiedSynonym> getProperNames() {
        // If this information has been cached, then use the cache.
        if (properNames == null) {
            properNames = new TreeSet<QualifiedSynonym>();

            if (getMonographEntries() != null) {
                for (MonographEntry monographEntry : getMonographEntries()) {
                	if (monographEntry.getProperNames() != null) {
                		properNames.addAll(monographEntry.getProperNames());
                	}
                }
            }

        }
        return properNames;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    /**
     * Gets the risk notes.
     *
     * @return  the risk notes.
     *
     * @see     #setRiskNotes()
     */
    public Set<GenericText> getRiskNotes() {
        return riskNotes;
    }

   
    /**
     * Gets the specification info notes.
     *
     * @return  the specification info notes.
     */
    public Set<GenericText> getSpecificationInfo() {
        return specificationInfo;
    }

    /**
     * Gets the storage condition notes.
     *
     * @return  the storage condition notes.
     *
     * @see     #setStorageConditions()
     */
    public Set<GenericText> getStorageConditions() {
        return storageConditions;
    }

    /**
     * Gets the collection of subpopulations that are applicable to this
     * monograph. These are used in the electronic forms through the web
     * services.
     *
     * @return  the subPopulation.
     *
     * @see     #setSubPopulation()
     */
    public Set<SubPopulation> getSubPopulations() {
        return subPopulations;
    }

    /**
     * Returns true if this Monograph contains any MonographUses, contained either 
     * directly by the MDCs, or by the MDs.
     * @return
     */
    public boolean isHasMonographUses() {
        if (getMonographEntries() != null) {
            for (MonographEntry monographEntry : getMonographEntries()) {
                if (monographEntry.getMonographDoseCombinations() != null) {
                    for (MonographDoseCombination combination 
                         : monographEntry.getMonographDoseCombinations()) {
                    	//check the MDC for uses
                    	if (combination.getMonographUses() != null && 
                    		combination.getMonographUses().size() > 0) {
                    		return true;
                    	}
                    	//check the MD wrapped by the MDC for uses
                    	if (combination.getMonographDoses() != null) {
                            for (MonographDose md : combination.getMonographDoses()) {
                                if ((md.getMonographUses() != null)
                                    && (md.getMonographUses().size() != 0)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /** 
     * Sets the appendix in English.
     *
     * @param  newVal  the appendix in English
     * 
     * @see    #getAppendixE()
     */
    public void setAppendixE(String newVal) {
        appendixE = newVal;
    }
    
    /**
     * Sets the appendix in French.
     *
     * @param  newVal  the appendix in French
     *
     * @see    #getAppendixF()
     */
    public void setAppendixF(String newVal) {
        appendixF = newVal;
    }

    /**
     * Sets the Attachment for the English PDF file.
     * 
     * @param newVal  the English PDF attachment.
     * 
     * @see #getPdfAttachmentEnglish()
     */
    public void setPdfAttachmentEnglish(Attachment newVal) {
        this.pdfAttachmentEnglish = newVal;
    }

    /**
     * Sets the Attachment for the French PDF file.
     * 
     * @param newVal  the French PDF attachment.
     * 
     * @see #getPdfAttachmentFrench()
     */
    public void setPdfAttachmentFrench(Attachment newVal) {
        this.pdfAttachmentFrench = newVal;
    }
    
    /**
     * Sets the set of dose notes.
     *
     * @param  newVal
     *
     * @see    #setDoseNotes()
     */
    public void setDoseNotes(Set<GenericText> newVal) {
        doseNotes = newVal;
    }

    /**
     * Sets the general notes.
     *
     * @param  newVal  the general notes
     *
     * @see    #getGeneralNotes()
     */
    public void setGeneralNotes(Set<GenericText> newVal) {
        generalNotes = newVal;
    }

    /**
     * Setter for the set of Monograph Bibliography English citations.
     *
     * @param  values  set of citations to be associated as English Monograph
     *                 Bibliographies.
     *
     * @see    getMonographBibliographyEnglish()
     */
    public void setMonographBibliographyEnglish(Set<Citation> values) {
        addToMonographCitationJoin(values,
            MonographCitationJoin.RELATION_BIBLIOGRAPHY_ENGLISH);
    }

    /**
     * Setter for the set of Monograph Bibliography French citations.
     *
     * @param  values  set of citations to be associated as French Monograph
     *                 Bibliographies.
     *
     * @see    setMonographBibliographyFrench()
     */
    public void setMonographBibliographyFrench(Set<Citation> values) {
        addToMonographCitationJoin(values,
            MonographCitationJoin.RELATION_BIBLIOGRAPHY_FRENCH);
    }

    /**
     * Set the MonographCitationJoin object using the value.
     *
     * @param  value  MonographCitationJoin
     *
     * @see    getMonographCitationJoin()
     */
    public void setMonographCitationJoins(Set<MonographCitationJoin> value) {
        monographCitationJoins = value;
    }

    /**
     * DOCUMENT ME!*
     *
     * @param  newVal
     */
    public void setMonographDurations(Set<MonographDuration> newVal) {
        monographDurations = newVal;
    }

    public void setMonographRiskStatements(Set<MonographRiskStatement> newVal) {
        monographRiskStatements = newVal;
    }

    /**
     * Sets the distinct MonographRouteOfAdministration that belongs to this
     * Monograph. Note that a Monograph can only have one
     * MonographRouteOfAdministration, so if this is passed a value, the
     * shared one will be set to null.
     *
     * @param newVal the distinct MonographRouteOfAdministration for this
     *        Monograph.
     *
     * @see   getMonographRouteOfAdministration()
     */
    public void setMonographRouteOfAdministration(
                MonographRouteOfAdministration newVal) {
        monographRouteOfAdministration = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setMonographUses(Set<MonographUse> newVal) {
        monographUses = newVal;
    }

    /**
     * Set the MonographGroup object using the value.
     *
     * @param  value  MonographGroup
     *
     * @see    getMonographGroup()
     */
    public void setMonographGroup(MonographGroup value) {
        monographGroup = value;
    }
    
    /**
     * Set the MonographPhases object using the value.
     *
     * @param  value  MonographPhases
     * @see    getMonographPhases()
     */    
    public void setMonographPhases(Set<MonographPhase> monographPhases) {
        this.monographPhases = monographPhases;
    }
    
    
    /**
     * Setter for the set of Monograph Reference Reviewed English citations.
     *
     * @param  values  set of citations to be associated as English Monograph
     *                 References Reviewed.
     *
     * @see    setMonographReferenceReviewedEnglish()
     */
    public void setMonographReferenceReviewedEnglish(Set<Citation> values) {
        addToMonographCitationJoin(values,
            MonographCitationJoin.RELATION_REFERENCE_REVIEWED_ENGLISH);
    }

    /**
     * Setter for the set of Monograph Reference Reviewed French citations.
     *
     * @param  values  set of citations to be associated as French Monograph
     *                 References Reviewed.
     *
     * @see    setMonographReferenceReviewedFrench()
     */
    public void setMonographReferenceReviewedFrench(Set<Citation> values) {
        addToMonographCitationJoin(values,
            MonographCitationJoin.RELATION_REFERENCE_REVIEWED_FRENCH);
    }

    /**
     * Sets the NMI notes.
     *
     * @param  newVal  the NMI notes
     *
     * @see    #getNmiNotes()
     */
    public void setNmiNotes(Set<GenericText> newVal) {
        nmiNotes = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setPublishedDate(Date newVal) {
        publishedDate = newVal;
    }

    /**
     * Sets the risk notes.
     *
     * @param  newVal  the risk notes
     *
     * @see    #getRiskNotes()
     */
    public void setRiskNotes(Set<GenericText> newVal) {
        riskNotes = newVal;
    }

    /**
     * Sets the specification info notes.
     *
     * @param  newVal  the specification info notes
     *
     * @see    #getSpecificationInfo()
     */
    public void setSpecificationInfo(Set<GenericText> newVal) {
        specificationInfo = newVal;
    }

    /**
     * Sets the storage conditions notes.
     *
     * @param  newVal  the storage conditions notes
     *
     * @see    #getStorageConditions()
     */
    public void setStorageConditions(Set<GenericText> newVal) {
        storageConditions = newVal;
    }

    /**
     * Sets the collection of subpopulations that are applicable to this
     * monograph. These are used in the electronic forms through the web
     * services.
     *
     * @param  newVal
     */
    public void setSubPopulations(Set<SubPopulation> newVal) {
        subPopulations = newVal;
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subslasses should override this and add
     * their values to the end.
     */
    @Override
    public String getValuesAsString(){
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;
        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", publishedDate: ");
        buffer.append(getPublishedDate());
        buffer.append(", monographGroup: ");
        if (getMonographGroup() != null){
            buffer.append(getMonographGroup().getNameE());
        } else {
            buffer.append("null");
        }
        buffer.append(", specificationInfo: ");
        buffer.append(getSpecificationInfo());
        buffer.append(", storageConditions: ");
        buffer.append(getStorageConditions());
        buffer.append(", generalNotes: ");
        buffer.append(getGeneralNotes());
        buffer.append(", nmiNotes: ");
        buffer.append(getNmiNotes());
        buffer.append(", riskNotes: ");
        buffer.append(getRiskNotes());
        buffer.append(", appendixE: ");
        buffer.append(getAppendixE());
        buffer.append(", monographBibliography: ");
        if (getMonographBibliography() != null){
            isFirstItem = true;
            buffer.append("[");
            for (Citation citation : getMonographBibliography()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(citation.getClass().getSimpleName());
                buffer.append(": ");
                buffer.append(citation.getValueInBibliographicalFormat());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }
        
        /*
        buffer.append(", sourceOrganismParts: ");
        if (getSourceOrganismParts() != null){
            isFirstItem = true;
            buffer.append("[");
            for (OrganismPart part : getSourceOrganismParts()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(part.getName());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }
        */
        
        buffer.append(", subPopulations: ");
        if (getSubPopulations() != null){
            isFirstItem = true;
            buffer.append("[");
            for (SubPopulation subPopulation : getSubPopulations()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(subPopulation.getName());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }
        buffer.append(",\n doseNotes: ");
        buffer.append(getDoseNotes());
        buffer.append(",\n monographDurations: ");
        buffer.append(getMonographDurations());
        buffer.append(",\n monographRiskStatements: ");
        buffer.append(getMonographRiskStatements());
        buffer.append(",\n monographRouteOfAdministration: ");
        buffer.append(getMonographRouteOfAdministration());
        buffer.append(",\n monographUses: ");
        //buffer.append(getMonographUses());
        buffer.append(", monographEntries: ");
        buffer.append(getMonographEntries());

        return buffer.toString();
    }

    //~ private ----------------------------------------------------------------

    /**
     * Used by the comparator, removes the numbers and dashes from the Monograph
     * Name so that the sorting occures only on the monograph alfa characters.
     * This was created to support the "5-HTP" monograph and have it sorted on
     * the "HTP".
     *
     * @param   inputString  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    static private String adjustForSorting(String inputString) {

        Matcher matcher = GeneratedMonograph.patternRemoveNumbersDash.matcher(inputString);

        String output = matcher.replaceAll(GeneratedMonograph.EMPTY_STRING);

        return output;
    }

    private void addToMonographCitationJoin(Set<Citation> citations,
                                            Long relationship) {
        if ((citations == null) || (relationship == null)) {
            //log.debug("aborting here...");
            return;
        }

        if (monographCitationJoins == null) {
            monographCitationJoins = new TreeSet<MonographCitationJoin>();
        }

        for (Citation citation : citations) {
            MonographCitationJoin monographCitationJoin =
                new MonographCitationJoin(this, citation, relationship);
            monographCitationJoins.add(monographCitationJoin);
            
            //log.debug("monographCitationJoins size = " + monographCitationJoins.size());
        }

    }

    /**
     * Given an object subclassed from the DependantObject returns the citations
     * as a set.
     *
     * @param   objects  subclassed from the DependantObject
     *
     * @return  a set of citations
     */
    private Set<Citation> getDependantObjectCitations(
                          Set<? extends DependantObject> objects) {
        TreeSet<Citation> cits = new TreeSet<Citation>();

        if (objects != null) {
            for (DependantObject depObj : objects) {
                cits.addAll(depObj.getCitations());
        }
            }
        return cits;
    }

    /**
     * Getter that returns a set of Citations based on the relationship being
     * requested. In order to reduce the number of join tables between the
     * Monograph and Citations, we have created a join table in the object of
     * the MonographCitationJoin that allows us to specify the nature of the
     * relationship. This functions is called by the getters within this class
     * and returns the objects that have the appropriate relationship.
     *
     * @param   relationship  Long value that defines the relationship between
     *                        the Monograph and the relevant Citation. For a
     *                        list of the possible relationship, please
     *                        reference the MonographCitationJoin object.
     *
     * @return  a Set of Citations depending on the selected relationship.
     */
    private Set<Citation> getFromMonographCitationJoin(Long relationship) {

        if (relationship == null) {
            return null;
        }

        TreeSet<Citation> citations = new TreeSet<Citation>(
                                          new Citation.NameComparator());

        if (getMonographCitationJoins()  != null) {
            for (MonographCitationJoin monographCitationJoin
                 : getMonographCitationJoins()) {
            if (monographCitationJoin.getRelationship().equals(relationship)) {
                citations.add(monographCitationJoin.getCitation());
            }
        }
        }

        return citations;
    }

    /**
     * Method used for testing purposes.
     * 
     * @param monoSourceMat
     */
    private void spewQaCitations(TextMonographSourceMaterial monoSourceMat) {
        
        QualifiedAssertion qa = monoSourceMat.getQualifiedAssertion();
        
        if (qa == null) {
            log ("Null QualifiedAssertion");
            return;
        }
        
        log ("QA Assertion: " + qa.getValuesAsString());
        if (qa.getCitationsE() == null && qa.getCitationsF() == null) {
            log ("QA Null Citations...");
        }
        else {
            log("Citations sizes: " + qa.getCitationsE().size() + "," 
                    +  qa.getCitationsE().size());
    
            for (Citation cit: qa.getCitationsE()) {
                log("Cit:" + cit.getAbridgedTitle());
            }
        }
     
    }
    
    
    /*
     * Aggregates all the MonographSourceMaterials from the contained set of 
     * MonographEntries.  
     */
    private void populateMonographSourceMaterials() {
        //don't think I need the Comparators... just add compareTos to 
    	//the 3 classes which implement MonographSourceMaterial.   
    	//Comparator englishComparator = new DisplayStringEnglishComparator();
        //Comparator frenchComparator = new DisplayStringFrenchComparator();
    	
    	log ("populateMonographSourceMaterials");
        monographSourceMaterialsE = new TreeSet<MonographSourceMaterial>(); 
        monographSourceMaterialsF = new TreeSet<MonographSourceMaterial>(); 
     
        monographEntriesWithSourceNotes = new TreeSet<MonographEntry>();
        if (getMonographEntries() == null) {
            return;
        }

        for (MonographEntry monographEntry : getMonographEntries()) {
            if (monographEntry.getSourceNotes() != null) {
                monographEntriesWithSourceNotes.add(monographEntry);
            }
            
            //add Source Organism Parts from MonographEntry
            if (monographEntry.getMonographSourceOrganismParts() != null) {
            	  
            	for (MonographSourceOrganismPart sourceOrgPart 
                     : monographEntry.getMonographSourceOrganismParts()) {
            		monographSourceMaterialsF.add(sourceOrgPart);
            		monographSourceMaterialsE.add(sourceOrgPart);
            	}	
            	/*	TODO - need to implement bilingual sorting
            		if (sourceOrgPart.getSourceMaterialNameE() != null) {
                		monographSourceMaterialsE.add(sourceOrgPart);
                	}
                	if (sourceOrgPart.getSourceMaterialNameF() != null) {
                		monographSourceMaterialsF.add(sourceOrgPart);
                	}
                }
                */
            }
            
            //OtherMonographSources moved up from ChemicalMonographEntry to 
            //MonographEntry Dec 16, 2009
            if (monographEntry.getOtherMonographSources() != null) {
            	log("Checkpoint 2: otherSources size: " + monographEntry.getOtherMonographSources().size());
            	for (TextMonographSourceMaterial textMonoSource 
                     : monographEntry.getOtherMonographSources()) {
                    if (textMonoSource.getSourceMaterialNameE() != null) {
                    	monographSourceMaterialsE.add(textMonoSource);
                    }
                    if (textMonoSource.getSourceMaterialNameF() != null) {
                    	monographSourceMaterialsF.add(textMonoSource);
                    }
                }
            }
            
            //add MonographSourceIngredients and Other Sources from 
            //ChemicalMonographEntry
            if (monographEntry instanceof ChemicalMonographEntry) {
                ChemicalMonographEntry chemicalMonographEntry =
                    (ChemicalMonographEntry) monographEntry;
                //try the quick and dirty "addAll" method...
                
                if (chemicalMonographEntry.getMonographSourceIngredients() != null) {
                	monographSourceMaterialsE.addAll
                	 	(chemicalMonographEntry.getMonographSourceIngredients());
                	monographSourceMaterialsF.addAll
             	 		(chemicalMonographEntry.getMonographSourceIngredients()); 
                }
             
                
                /*
                if (chemicalMonographEntry.getMonographSourceIngredients() != null) {
                    for (MonographSourceIngredient monoSourceIngredient 
                         : chemicalMonographEntry.getMonographSourceIngredients()) {
                    	monographSourceMaterialsE.add(monoSourceIngredient);
                    	monographSourceMaterialsF.add(monoSourceIngredient);
                    }
                    
                    /*
                    	//we need to downcast to a concrete Ingredient type here because not 
                    	//all subclasses of Ingredient implement bilingual "getAuthorizedName"
                    	//method pairs.
                    	Ingredient ingredient = monoSourceIngredient.getIngredient();
                    	if (ingredient instanceof ChemicalSubstance) {
                            ChemicalSubstance chemicalSubstance = (ChemicalSubstance) ingredient;
                            
                            monographSourceMaterialsE.add(chemicalSubstance
                                                  .getAuthorizedNameE());
                            monographSourceMaterialsF.add(chemicalSubstance
                                                  .getAuthorizedNameF());
                        }
                      
                        if (ingredient instanceof DefinedOrganismSubstance) {
                            DefinedOrganismSubstance definedOrganismSubstance =
                                (DefinedOrganismSubstance) ingredient;
                            monographSourceMaterialsE.add(definedOrganismSubstance
                                                  .getAuthorizedNameE());
                            monographSourceMaterialsF.add(definedOrganismSubstance
                                                  .getAuthorizedNameF());
                        }
                        
                    }
                }
                */
                
               
            }
        }
    }
  
    
    
   

    // +++ Inner Classes
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /*******************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    static public class EnglishComparator implements Comparator {

        /**
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         *
         * @param   o1  the first object to be compared.
         * @param   o2  the second object to be compared.
         *
         * @return  DOCUMENT ME!
         *
         * @throws  ClassCastException  DOCUMENT ME!
         */
        public int compare(Object o1, Object o2) throws ClassCastException {

            if ((o1 == null) || (((GeneratedMonograph) o1).getNameE() == null)) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }

            // Return the different names if they dont match.
            if (!((GeneratedMonograph) o1).getNameE().equals(((GeneratedMonograph) o2).getNameE())
                    && (adjustForSorting(((GeneratedMonograph) o1).getNameE())).equals(
                        adjustForSorting(((GeneratedMonograph) o2).getName()))) {
                return ((GeneratedMonograph) o1).getNameE().compareTo(((GeneratedMonograph) o2)
                        .getNameE());
            }

            // Return the alfa compare.
            return (adjustForSorting(((GeneratedMonograph) o1).getNameE()).compareTo(
                        adjustForSorting(((GeneratedMonograph) o2).getNameE())));
        }
    }

    private static void log(String msg) {
        log.debug(msg);
    }
    
    
}

