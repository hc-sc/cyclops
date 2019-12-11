package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ImageUtil;
import java.sql.Blob;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

/*******************************************************************************
 * An object that represents the manufacturing process used to create an
 * Ingredient. It is described by an image, description, or Citation (or any
 * combination of these). This process may add impurities that have to be tested
 * for in the purity testing.
 */
public class ManufacturingProcess extends PersistentObject implements Comparable {
    private static final Log LOG = LogFactory.getLog(ManufacturingProcess.class);

   /* 
    * TODO - test these generics.  I am using the most specific classes
    * here for the next 2 collections, whereas the the Hibernate mapping 
    * file uses the ImpuritySubstance and Ingredient super classes.  
    * MR 2009/02/12
    */
    private Set<ChemicalImpuritySubstance> chemicalImpuritySubstances = 
    	new TreeSet<ChemicalImpuritySubstance>();
    private Set<ChemicalSubstance> chemicalSubstances = 
    	new TreeSet<ChemicalSubstance>();
	
    
	private Citation citation;
	private String descriptionE;
	private String descriptionF;
	private Attachment image;
	private Set<MicrobiologicalImpurity> microbiologicalImpurities;
    private String nameE;
    private String nameF;
    private boolean synthesis;

    /***************************************************************************
     * Gets the ChemicalImpurities that are produced by this manufacturing
     * process. Note that this set is immutable, since it is the composite
     * of entries from 2 other sets. Use the add, remove, and set methods to
     * make changes to it.
     * @return the ChemicalImpurities that are produced by this
     *         manufacturing process
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     * @see #setChemicalImpurities()
     */
	public Set<ChemicalImpuritySubstance> getChemicalImpurities(){
        Set chemicalImpurities = new TreeSet(getChemicalImpuritySubstances());
        
        chemicalImpurities.addAll(getChemicalSubstances());
		return Collections.unmodifiableSet(chemicalImpurities);
	}

    /***************************************************************************
     * Gets the ChemicalImpuritySubstances that are produced by this
     * manufacturing process (these are impurities that are not otherwise stored
     * in the system as Ingredients). Normally chemical impurities are dealt
     * with in an abstract sense, however Hibernate requires the two different
     * types be dealt with separately since they are stored in different parts
     * of the persistent store. This method is typically only used by Hibernate.
     * @return the ChemicalImpuritySubstances that are produced by this
     *         manufacturing process
     * @see #getChemicalImpurities()
     * @see #setChemicalImpuritySubstances()
     */
    public Set<ChemicalImpuritySubstance> getChemicalImpuritySubstances(){
        return chemicalImpuritySubstances;
    }

    /***************************************************************************
     * Gets the ChemicalSubstance impurities that are produced by this
     * manufacturing process. Normally chemical impurities are dealt with in an
     * abstract sense, however Hibernate requires the two different types be
     * dealt with separately since they are stored in different parts of the
     * persistent store. This method is typically only used by Hibernate.
     * @return the ChemicalSubstance impurities that are produced by this
     *         manufacturing process
     * @see #getChemicalImpurities()
     * @see #setChemicalSubstances()
     */
    public Set getChemicalSubstances(){
        return chemicalSubstances;
    }

    /***************************************************************************
     */
	public Citation getCitation(){
		return citation;
	}

    /***************************************************************************
	 * Gets the description in the language appropriate for the Locale.
	 * @return the locale-specific description.
	 */
	public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
	}

    /***************************************************************************
     */
	public String getDescriptionE(){
		return descriptionE;
	}

    /***************************************************************************
     */
	public String getDescriptionF(){
		return descriptionF;
	}

    /***************************************************************************
     */
	public Attachment getImage(){
		return image;
	}

    /***************************************************************************
     */
    public boolean isIsolation(){
        return !synthesis;
    }

    /***************************************************************************
     */
	public Set<MicrobiologicalImpurity> getMicrobiologicalImpurities(){
		return microbiologicalImpurities;
	}

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name of the country.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /***************************************************************************
     * Gets the English name.
     * @return the English name.
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the French name.
     * @return the French name.
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     */
    public boolean isSynthesis(){
        return synthesis;
    }

    /***************************************************************************
     * Sets the ChemicalImpurities that are produced by this manufacturing
     * process.
     * @param newVal the ChemicalImpurities that are produced by this
     *         manufacturing process
     * @see #getChemicalImpurities()
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     */
	public void setChemicalImpurities(Set newVal){
        Object impurity;
        
        getChemicalImpuritySubstances().clear();
        getChemicalSubstances().clear();
        if (newVal != null) {
            for (Iterator it=newVal.iterator(); it.hasNext(); ) {
                impurity = it.next();
                if (impurity instanceof ChemicalImpurity) {
                    addChemicalImpurity((ChemicalImpurity)impurity);
                } else {
                    LOG.error("Item not a ChemicalImpurity: " +
                              impurity.getClass().getName());
                }
            }
        }
	}

    /***************************************************************************
     * Sets the ChemicalImpuritySubstances that are produced by this
     * manufacturing process (these are impurities that are not otherwise stored
     * in the system as Ingredients). Normally chemical impurities are dealt
     * with in an abstract sense, however Hibernate requires the two different
     * types be dealt with separately since they are stored in different parts
     * of the persistent store. This method is typically only used by Hibernate.
     * @param newVal the ChemicalImpuritySubstances that are produced by this
     *         manufacturing process
     * @see #setChemicalImpurities()
     * @see #getChemicalImpuritySubstances()
     */
    public void setChemicalImpuritySubstances(Set<ChemicalImpuritySubstance> newVal){
        if (newVal != null) {
            chemicalImpuritySubstances = newVal;
        } else {
            chemicalImpuritySubstances.clear();
        }
    }

    /***************************************************************************
     * Sets the ChemicalSubstance impurities that are produced by this
     * manufacturing process. Normally chemical impurities are dealt with in an
     * abstract sense, however Hibernate requires the two different types be
     * dealt with separately since they are stored in different parts of the
     * persistent store. This method is typically only used by Hibernate.
     * @param newVal the ChemicalSubstance impurities that are produced by this
     *        manufacturing process
     * @see #setChemicalImpurities()
     * @see #getChemicalSubstances()
     */
    public void setChemicalSubstances(Set newVal){
        if (newVal != null) {
            chemicalSubstances = newVal;
        } else {
            chemicalSubstances.clear();
        }
    }

    /***************************************************************************
	 * @param newVal
	 */
	public void setCitation(Citation newVal){
		citation = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDescriptionE(String newVal){
		descriptionE = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDescriptionF(String newVal){
		descriptionF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setImage(Attachment newVal){
		image = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMicrobiologicalImpurities(Set<MicrobiologicalImpurity> newVal){
		microbiologicalImpurities = newVal;
	}

    /***************************************************************************
     * Sets the English name.
     * @param newVal the English name.
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the French name.
     * @param newVal the French name.
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSynthesis(boolean newVal){
        synthesis = newVal;
    }

    /***************************************************************************
     * Adds the passed ChemicalImpurity to the set of impurities that are
     * produced by this manufacturing process.
     * @param impurity the ChemicalImpurity to be added
     * @see #getChemicalImpurities()
     * @see #setChemicalImpurities()
     * @see #removeChemicalImpurity()
     */
    public void addChemicalImpurity(ChemicalImpurity impurity){
        if (impurity instanceof ChemicalSubstance) { //Must be a ChemicalSubstance
            getChemicalSubstances().add((ChemicalSubstance)impurity);
        } else { //Must be a ChemicalImpuritySubstance
            getChemicalImpuritySubstances().add((ChemicalImpuritySubstance)impurity);
        }
    }

    /***************************************************************************
     * Removes the passed ChemicalImpurity from the set of impurities that are
     * produced by this manufacturing process.
     * @param impurity the ChemicalImpurity to be removed
     * @see #getChemicalImpurities()
     * @see #setChemicalImpurities()
     * @see #addChemicalImpurity()
     */
    public void removeChemicalImpurity(ChemicalImpurity impurity){
        if (impurity instanceof Ingredient) { //Must be a ChemicalSubstance
            getChemicalSubstances().remove(impurity);
        } else { //Must be a ChemicalImpuritySubstance
            getChemicalImpuritySubstances().remove(impurity);
        }
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),
    			((ManufacturingProcess)o).getName());
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());
        buffer.append(", synthesis: ");
        buffer.append(isSynthesis());
        buffer.append(", chemicalImpurities: ");
        buffer.append(getChemicalImpurities());
        buffer.append(", microbiologicalImpurities: ");
        buffer.append(getMicrobiologicalImpurities());
        buffer.append(", citation: ");
        buffer.append(getCitation());
        
        return buffer.toString();
    }
}