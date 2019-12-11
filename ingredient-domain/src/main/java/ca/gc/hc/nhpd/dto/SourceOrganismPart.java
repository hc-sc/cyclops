package ca.gc.hc.nhpd.dto;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * This object models the content of the Source Organism Parts on behalf of
 * the the Ingredient Web Service.  It Originally, 
 * the code was taking almost 60 seconds to return the Vitamin C 
 * ingredient.
 *  
 * This object was modified to return the Constituent dto object instead of the
 * constituent String object.  This is in response to Red Mine issue 2355.
 * 
 * @author mrabe
 */

public class SourceOrganismPart implements Comparable {

    static final private String SPACER = " - "; 
    static final private String TILDA = "~";
    
    private static final String nameSeparator = "~";
    private static final String ignoreValue = "NA";
    
    private boolean french;
    private Long ingredientId;
	private String name;
    private Long organismPartId;
    private List<Constituent> constituents;
    private String organism;
    private List<String> organismSynonymsEnglish;
    private List<String> organismSynonymsFrench;
    private String partTypeCode;
    private String partTypeNameEnglish;
    private String partTypeNameFrench;
    private List<String> partTypeSynonymsEnglish;
    private List<String> partTypeSynonymsFrench;
    private String groupNameEnglish;
    private String groupNameFrench;
  
    public SourceOrganismPart() {}
    
    /* 
     * constructor which creates an IngredientSearchResult instance from
     * a single row of the database search result
     */
    public SourceOrganismPart(Object[] searchResult, boolean isFrench) {
        
        setFrench(isFrench);
        ingredientId = ((BigDecimal)searchResult[0]).longValue();
        organismPartId = ((BigDecimal)searchResult[1]).longValue();

        // Modified by GB in response to IMSD 2355.
        String constituentsConstructsEnglish = (String) searchResult[2];
        String constituentsConstructsFrench = (String) searchResult[3];
        constituents = extractConstituent(constituentsConstructsEnglish, constituentsConstructsFrench);

        organism = (String)searchResult[4];
        groupNameEnglish = (String)searchResult[5];
        groupNameFrench = (String)searchResult[6];
        organismSynonymsEnglish = extractNames((String) searchResult[7]);
        organismSynonymsFrench = extractNames((String) searchResult[8]);
        partTypeCode = (String)searchResult[9];
        partTypeNameEnglish = (String)searchResult[10];
        partTypeNameFrench = (String)searchResult[11];
        partTypeSynonymsEnglish = extractNames((String) searchResult[12]);
        partTypeSynonymsFrench = extractNames((String) searchResult[13]);
        
    }
    
	/**
	 * Constructor - used by the ChemicalSubstance to create
	 * a complete list of organism parts. This is to fix issue 8379.
	 * It will be a cartesian product of the taxonomy node names +
	 * the existing source organism part list. We only care about the
	 * constituents (subingredients), the other members are not
	 * applicable in this case.
	 * 
	 * @param orgPartName a string representing the name of the 
	 * taxonomy node used in the new part.
	 * @param part = The existing part used to create a new part for
	 * the taxonomy node in question.
	 */
    public SourceOrganismPart(String orgPartName, SourceOrganismPart part) {
    	name = orgPartName;
    	partTypeCode = part.getPartTypeCode();
    	partTypeNameEnglish = part.getPartTypeNameEnglish();
    	partTypeNameFrench = part.getPartTypeNameFrench();
    	constituents = part.getConstituents();
    	setFrench(part.isFrench());
    }
    
    public Long getIngredientId() {
        return ingredientId;
    }
    
    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
    
    public String getOrganism() {
        return organism;
    }
    
    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getGroupNameEnglish() {
        return groupNameEnglish;
    }

    public void setGroupNameEnglish(String groupNameEnglish) {
        this.groupNameEnglish = groupNameEnglish;
    }

    public String getGroupNameFrench() {
        return groupNameFrench;
    }
    
    public void setGroupNameFrench(String groupNameFrench) {
        this.groupNameFrench = groupNameFrench;
    }

    public Long getOrganismPartId() {
        return organismPartId;
    }
    
    public void setOrganismPartId(Long organismPartId) {
        this.organismPartId = organismPartId;
    }
    
    public boolean isFrench() {
        return french;
    }
    
    public void setFrench(boolean french) {
        this.french = french;
    }
    
    public List<Constituent> getConstituents() {
        return constituents;
    }
    
    public void setConstituents(List<Constituent> constituents) {
        this.constituents = constituents;
    }

    public String getName() {
        // Note:  In some cases organism part is associated with
        // a organism group and not an organism.  IE: Calcium.
    	if ((name != null) && (name.length() > 0)) {
    	    if (isFrench()) {
    	    	return name + SPACER + partTypeNameFrench;
    	    } else {
    	    	return name + SPACER + partTypeNameEnglish;
    	    }
		}
    	
        if (organism == null) {
            if (isFrench()) {
                return groupNameFrench + SPACER + partTypeNameFrench;
            } else {
                return groupNameEnglish + SPACER + partTypeNameEnglish;
            }
        }
        if (isFrench()) {
            return organism + SPACER + partTypeNameFrench;
        }
        return organism + SPACER + partTypeNameEnglish;
    }
    
    public void setName(String name) {
        // do nothing!!
    }
    
    public List<String> getOrganismSynonymsEnglish() {
        return organismSynonymsEnglish;
    }
    
    public void setOrganismSynonymsEnglish(List<String> organismSynonymsEnglish) {
        this.organismSynonymsEnglish = organismSynonymsEnglish;
    }

    public List<String> getOrganismSynonymsFrench() {
        return organismSynonymsFrench;
    }
    
    public void setOrganismSynonymsFrench(List<String> organismSynonymsFrench) {
        this.organismSynonymsFrench = organismSynonymsFrench;
    }
    
    public List<String> getOrganismSynonyms() {
        if (isFrench()) {
            return organismSynonymsFrench;
        }
        return organismSynonymsEnglish;
    }

    public String getPartTypeCode() {
        return partTypeCode;
    }
    
    public void setPartTypeCode(String partTypeCode) {
        this.partTypeCode = partTypeCode;
    }
    
    public String getPartTypeNameEnglish() {
        return partTypeNameEnglish;
    }
    
    public void setPartTypeNameEnglish(String partTypeNameEnglish) {
        this.partTypeNameEnglish = partTypeNameEnglish;
    }

    public String getPartTypeNameFrench() {
        return partTypeNameFrench;
    }
    
    public void setPartTypeNameFrench(String partTypeNameFrench) {
        this.partTypeNameFrench = partTypeNameFrench;
    }

    public String setPartTypeName() {
        if (isFrench()) {
            return partTypeNameFrench;
        }
        return partTypeNameEnglish;
    }

    public List<String> getPartTypeSynonymsEnglish() {
        return partTypeSynonymsEnglish;
    }
    
    public void setPartTypeSynonymsEnglish(List<String> partTypeSynonymsEnglish) {
        this.partTypeSynonymsEnglish = partTypeSynonymsEnglish;
    }

    public List<String> getPartTypeSynonymsFrench() {
        return partTypeSynonymsFrench;
    }
    
    public void setPartTypeSynonymsFrench(List<String> partTypeSynonymsFrench) {
        this.partTypeSynonymsFrench = partTypeSynonymsFrench;
    }

    public List<String> getPartTypeSynonyms() {
        if (isFrench()) {
            return partTypeSynonymsFrench;
        }
        return partTypeSynonymsEnglish;
    }
    
    /*
     * Sort by delegate the compareTo call to the wrapped object TODO - if the web
     * service exposes Lists rather than Sets, we could get rid of this
     * Comparable stuff. Note that order is preserved when transforming a Set to
     * an ArrayList...
     */
    public int compareTo(Object o) throws ClassCastException {
    	SourceOrganismPart passedSop = (SourceOrganismPart) o;
        return StringComparator.compare(getName(), passedSop.getName()); 
    }
    

    /*
     * Use StringUtils to extract the names from the delimited name list columns.
     */
    private List<String> extractNames(String names) {
        return StringUtils.extractDelimitedNames(names,nameSeparator,ignoreValue);
    }
    
    /**
     * Given the constituent column information, this method breaks the string along
     * the TILDA seperated values and constructs individual constituents with the 
     * information.  These constituents are then returned as a list.
     *  
     * @param constituentsEnglish English String containing constituent information.
     * @param constituentsFrench French String containing constituent information.
     * 
     * @return a List of constituents.
     */
    private List<Constituent> extractConstituent(String constituentsEnglish, String constituentsFrench) {
        
        if (constituentsEnglish == null || constituentsFrench == null) {
            return null;
        }
        
        String[] constituentEnglishList = constituentsEnglish.split(TILDA); 
        String[] constituentFrenchList = constituentsFrench.split(TILDA);
        List<Constituent> constituentList = new ArrayList<Constituent>();
        for (int i = 0; i < constituentEnglishList.length; i++) {
            Constituent constituent = new Constituent(constituentEnglishList[i], constituentFrenchList[i]);
            constituentList.add(constituent);
        }
        return constituentList;
    }
    
    // Inner Classes
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /*******************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    static public class SourceOrganismPartComparator implements 
    	Comparator<SourceOrganismPart> {

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
        public int compare(SourceOrganismPart o1, SourceOrganismPart o2) 
        	throws ClassCastException {

            if (o1 == null) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }
            
            return StringComparator.compare(o1.getName(), o2.getName());
        }
    }
    

}
