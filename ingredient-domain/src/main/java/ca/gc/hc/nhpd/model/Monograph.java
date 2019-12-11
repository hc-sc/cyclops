package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
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
public abstract class Monograph extends PersistentObject implements Comparable, NamedObject {
    
	public static final String LANG_EN = Locale.ENGLISH.getISO3Language(); // ISO 639.2
    public static final String LANG_FR = Locale.CANADA_FRENCH.getISO3Language(); // ISO 639.2
    
    static public final String MONOGRAPH_STATUS_GENERATED = "G";
    static public final String MONOGRAPH_STATUS_EXTERNAL = "E";
    
    static public final String MONOGRAPH_STATUS_GENERATED_NAME = "GeneratedMonograph";
    static public final String MONOGRAPH_STATUS_EXTERNAL_NAME = "ExternalMonograph";
    
    private static final String EMPTY_STRING = "";
    private static final Logger log = Logger.getLogger(Monograph.class);
    private static final String NAME_CLEAN_PATTERN = "[0-9\\-]";
    private static final Pattern patternRemoveNumbersDash = Pattern.compile(NAME_CLEAN_PATTERN);
 
    //~ Instance fields --------------------------------------------------------
    private String code;
    private MonographGroup monographGroup;
    private String nameE;
    private String nameF;
    private Set<MonographType> monographTypes;
    private String urlE;
    private String urlF;
    private Set<MonographEntry> monographEntries;
    
    //~ public -----------------------------------------------------------------
    //This smells bad - this behaviour should reside in each class rather than
    //having all these instanceOf operations here.  TODO - replace with SourceMaterial
    //implementation.
    static public String getDisplayString(Object o, String language) {
        
        if (o == null) {
            log ("Null Object passed to getDisplayString");
            return "";
        }
        
        if (o instanceof String) {
            return (String)o;
        }

        if (language.equals(LANG_EN)) {
            if (o instanceof TextMonographSourceMaterial) {
                log ("Case 1");
                TextMonographSourceMaterial tsm = (TextMonographSourceMaterial) o;
                if (tsm.getQualifiedAssertion() == null)
                    log ("NULL QA in TSM!");
                else {
                    String bla = ((TextMonographSourceMaterial) o).getSourceMaterialName();
                    log ("returning: " + bla);
                    return bla;
                }
            }

            if (o instanceof ChemicalSubstance) {
                return ((ChemicalSubstance) o).getAuthorizedNameE();
            }

            if (o instanceof DefinedOrganismSubstance) {
                return ((DefinedOrganismSubstance) o).getAuthorizedNameE();
            }

            if (o instanceof Ingredient) {
                return ((Ingredient) o).getAuthorizedName();
            }

            if (o instanceof OrganismPart) {
                OrganismPart organismPart = (OrganismPart) o;

                return organismPart.getType().getNameE();
            }

        } else if (language.equals(LANG_FR)) {
            if (o instanceof TextMonographSourceMaterial) {
                log ("Case 2");
                return ((TextMonographSourceMaterial) o).getSourceMaterialName();
            }

            if (o instanceof ChemicalSubstance) {
                return ((ChemicalSubstance) o).getAuthorizedNameF();
            }

            if (o instanceof DefinedOrganismSubstance) {
                return ((DefinedOrganismSubstance) o).getAuthorizedNameF();
            }

            if (o instanceof Ingredient) {
                return ((Ingredient) o).getAuthorizedName();
            }

            if (o instanceof OrganismPart) {
                OrganismPart organismPart = (OrganismPart) o;

                return organismPart.getType().getNameF();
            }
        }

        log.debug("Could not make out part: " + o.getClass().getName());
        log ("Case X");
        return null;
    }


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
     * Gets the code.  Used in the loading of Monographs.
     *
     * @return  the code.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Navigates the relationship between a monograph and the monograph entries
     * to return all the ingredients directly related to the monograph as a set.
     * If a problem is encountered, returns a null.
     *
     * @return  a set of Ingredients related to this monograph.
     */
    public Set<Ingredient> getIngredients() {

        if (getMonographEntries() == null) {
            return null;
        }

        TreeSet<Ingredient> ingredients = new TreeSet<Ingredient>();

        for (MonographEntry monographEntry : getMonographEntries()) {
            Ingredient ingredient = monographEntry.getIngredient();
            ingredients.add(ingredient);
        }

        return ingredients;
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
    
    /**
     * Gets the set of MonographEntries that belong to this Monograph.
     *
     * @return  the set of MonographEntries associated to this Monograph.
     *
     * @see     setMonographEntries()
     */
    public Set<MonographEntry> getMonographEntries() {
        return monographEntries;
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
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
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
     * Gets the type associated to this Monograph.
     *
     * @return  the type.
     *
     * @see     #setType()
     */
    public Set<MonographType> getMonographTypes() {
        return monographTypes;
    }

    /**
     * Gets the English URL associated to this Monograph.
     *
     * @return  the English URL.
     *
     * @see     #setUrlE()
     */
    public String getUrlE() {
        return urlE;
    }

    /**
     * Gets the French URL associated to this Monograph.
     *
     * @return  the French URL.
     *
     * @see     #setUrlF()
     */
    public String getUrlF() {
        return urlF;
    }
    
    /**
     * Gets the url in the language appropriate for the Locale.
     *
     * @return  the locale-specific url.
     */
    public String getUrl() {
        if (isLanguageFrench()) {
            return getUrlF();
        }
        return getUrlE();
    }
    
    /**
     * Sets the code.  Used in the loading of Monographs.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setMonographEntries(Set<MonographEntry> newVal) {
        monographEntries = newVal;
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
     * Sets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #getNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }

    /**
     * Sets the type.
     *
     * @param  newVal  DOCUMENT ME!
     *
     * @see    #setType()
     */
    public void setMonographTypes(Set<MonographType> monographTypes) {
    	this.monographTypes = monographTypes;
    }

    /**
     * Sets the URL in English.
     *
     * @param  newVal  the URL in English
     *
     * @see    #getUrlE()
     */
    public void setUrlE(String newVal) {
        this.urlE = newVal;
    }

    /**
     * Sets the URL in French.
     *
     * @param  newVal  the URL in French
     *
     * @see    #getUrlF()
     */
    public void setUrlF(String newVal) {
        this.urlF = newVal;
    }

	/**
	 * Returns true when this monograph is of type AbLS.
	 * 
	 * @return true when this is an AbLS monograph.
	 */
    public boolean isAbls() {
    	if (getMonographTypes() != null 
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns true when this monograph is of type Internal AbLS.
     * 
     * @return true when this is an Internal AbLS monograph.
     */
    public boolean isAblsInternal() {
        if (getMonographTypes() != null
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS_INTERNAL)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true when this monograph is a product
     * monograph.
     * 
     * @return true when this is a product monograph.
     */
    public boolean isProduct() {
    	if (getMonographTypes() != null 
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_PRODUCT)
    			&& !getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)) {
    		return true;
    	}
    	return false;
    }

    /**
     * Returns true when this monograph is a product AbLS
     * monograph.
     * 
     * @return true when this is a product monograph.
     */
    public boolean isProductAbLS() {
    	if (getMonographTypes() != null 
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_PRODUCT)
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Returns true when this monograph is a compendial
     * monograph.
     * 
     * @return true when this is a compendial monograph.
     */
    public boolean isCompedial() {
    	if (getMonographTypes() != null 
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_COMPENDIAL)) {
    		return true;
    	}
    	return false;
    }

    /**
     * Returns true when this monograph is a TPDCAT4
     * monograph.
     * 
     * @return true when this is a TPDCAT4 monograph.
     */
    public boolean isTpdCat4() {
    	if (getMonographTypes() != null 
    			&& getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_TPDCAT4)) {
    		return true;
    	}
    	return false;
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
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", urlE: ");
        buffer.append(getUrlE());
        buffer.append(", urlF: ");
        buffer.append(getUrlF());
        buffer.append(", monographGroup: ");
        if (getMonographGroup() != null){
            buffer.append(getMonographGroup().getNameE());
        } else {
            buffer.append("null");
        }
        buffer.append(", monographType: ");
        if (getMonographTypes() != null){
        	isFirstItem = true;
        	for (MonographType monographType : getMonographTypes()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(monographType.getName());
        	}
        } else {
            buffer.append("null");
        }
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

        Matcher matcher = Monograph.patternRemoveNumbersDash.matcher(inputString);

        String output = matcher.replaceAll(Monograph.EMPTY_STRING);

        return output;
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

            if ((o1 == null) || (((Monograph) o1).getNameE() == null)) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }

            // Return the different names if they dont match.
            if (!((Monograph) o1).getNameE().equals(((Monograph) o2).getNameE())
                    && (adjustForSorting(((Monograph) o1).getNameE())).equals(
                        adjustForSorting(((Monograph) o2).getName()))) {
                return ((Monograph) o1).getNameE().compareTo(((Monograph) o2)
                        .getNameE());
            }

            // Return the alfa compare.
            return (adjustForSorting(((Monograph) o1).getNameE()).compareTo(
                        adjustForSorting(((Monograph) o2).getNameE())));
        }
    }

    private static void log(String msg) {
        log.debug(msg);
    }
    
}

