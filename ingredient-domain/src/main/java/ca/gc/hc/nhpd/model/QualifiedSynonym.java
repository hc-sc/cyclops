package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringUtils;
import java.util.Iterator;
import java.util.Set;

/*******************************************************************************
 * This is a wrapper around the Synonym object so that the synonym can be used
 * by the Ingredient and other objects and be qualified by an appropriate
 * reference.
 * 
 * Notes:
 * <li> Ingredient will use this object for 3 different types of names
 * including: AuthorizedName, CommonName, QualifiedSynonym.
 * <li> Each of the ingredient names will be connected using different join
 * tables. (This allows for easier addition of future audit functionality and
 * reduces caching of names in the ingredient object.)
 * <li> This structure decouples the QualifiedSynonym from the Ingredient table
 * making it more flexible and reusable by other classes. Example: Organism.
 * <li> We are going to release the unique key constraint on Synonym English
 * Name + Class Name and Synonym French Name + Class Name. The unique key
 * constraint will now be on the English + French name combination. This will
 * allow Denis to support relationships where multiple English words are
 * equivalent to multiple French words.
 * <li> The Materialized Views will be modified to navigate through all 3 join
 * tables to the Qualified Synonyms table.
 */
public class QualifiedSynonym extends PersistentObject implements Comparable {

    private final static String OPEN_SQUARE_BRACKET = "[";
    private final static String CLOSE_SQUARE_BRACKET = "]";
    private final static String SPACE = " ";
    private final static String COMMA = ",";
    
	private Synonym synonym;

	private Set<Reference> references;

    private String shortDescriptionE;
    
    private String shortDescriptionF;
    
	/**
	 * Returns the synonym name (English / French depending on language settings)
	 * related to this QualifiedSynonym.
	 * 
	 * @return the synonym name.
	 */
	public String getName() {
		if (getSynonym() != null
				&& getSynonym().getName() != null) {
			return getSynonym().getName();
		}
		return null;
	}
	
    /**
     * The shortDescriptionBuilder attempts to build a short description
     * with some elements of logic.  In this case, the synonym name followed
     * by the comma reference codes in square brackets.
     */
    private void shortDescriptionBuilder() {
        StringBuilder sbE = new StringBuilder();
        StringBuilder sbF = new StringBuilder();
        
        // English Synonym Name
        if (getSynonym() != null && getSynonym().getNameE() != null) {
            sbE.append(getSynonym().getNameE());
        } else {
            sbE.append("null");
        }
        
        // French Synonym Name
        if (getSynonym() != null && getSynonym().getNameF() != null) {
            sbF.append(getSynonym().getNameE());
        } else {
            sbF.append("null");
        }
        
        sbE.append(SPACE);
        sbF.append(SPACE);
        
        // Iterate through all the references.
        if (getReferences() != null  && getReferences().size() != 0) {
            int count = 0;
            sbE.append(OPEN_SQUARE_BRACKET);
            sbF.append(OPEN_SQUARE_BRACKET);
            Iterator i = getReferences().iterator();
            while (i.hasNext()) {
                Reference reference = (Reference) i.next();
                String code = reference.getCode();
                if (count != 0) {
                    sbE.append(COMMA + SPACE);
                    sbF.append(COMMA + SPACE);
                }
                sbE.append(code);
                sbF.append(code);
                
            }
            sbE.append(CLOSE_SQUARE_BRACKET);
            sbF.append(CLOSE_SQUARE_BRACKET);
            
        }
        shortDescriptionE = sbE.toString();
        shortDescriptionF = sbF.toString();
    }
    
    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        if (shortDescriptionE == null) {
            shortDescriptionBuilder();
        }
        return shortDescriptionE;
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        if (shortDescriptionF == null) {
            shortDescriptionBuilder();
        }
        return shortDescriptionF;
    }

	/**
	 * Returns the synonym's display name.  The display name is the name
	 * that is formatted with the appropriate rules for display in English
	 * and French.
	 * 
	 * @return the synonym display name.
	 */
	public String getDisplayName() {
		String displaySynonym = null;
		
		if (getSynonym() != null) {
			displaySynonym = getSynonym().getDisplayName();
			if ((displaySynonym != null) && (displaySynonym.length() > 40)) {
				displaySynonym = StringUtils.breakText(displaySynonym, 40);		
			}
		}	
		return displaySynonym;
	}

	public Synonym getSynonym() {
		return synonym;
	}

	public void setSynonym(Synonym newValue) {
		synonym = newValue;
	}

	public Set<Reference> getReferences() {
		return references;
	}

	public void setReferences(Set<Reference> newValue) {
		references = newValue;
	}

	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 * 
	 * @return A string representation of this object.
	 */
	public String getValuesAsString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append(super.getValuesAsString());
		if (getSynonym() != null) {
			buffer.append(", Synonym: ");
			buffer.append(getSynonym());
		}
		if (getReferences() != null) {
			buffer.append(", References: ");
			for (Reference ref: references) {
				buffer.append(ref.getValuesAsString());
			}
		}
		return buffer.toString();
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * 
	 * @param o
	 *            the object to compare this to.
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws ClassCastException
	 *             DOCUMENT ME!
	 */
	public int compareTo(Object o) throws ClassCastException {

        
        if (getId() != null 
                && ((QualifiedSynonym) o).getId() != null
                && getId().equals(((QualifiedSynonym) o).getId())) {
            return 0;
        }
        
		if (getSynonym() == null) {
			return 1;
		}

		if ((o == null) || (((QualifiedSynonym) o).getSynonym() == null)) {
			return -1;
		}

		int i = getSynonym().compareTo(((QualifiedSynonym) o).getSynonym());
        
        // If the synonyms are different, then base the compare on the synonym only.
        if (i != 0) {
            return i;
        }
        
        // If both reference lists are null, then compare is equal.
        if (getReferences() == null
                && ((QualifiedSynonym) o).getReferences() == null ) {
            return 0;
        }
        
        // If this reference is null then compare is 1.
        if (getReferences() == null) {
            return 1;
        }
        
        // If o reference is null then compare is -1.
        if (((QualifiedSynonym) o).getReferences() == null) {
            return -1;
        }
        
        // Extract the contents of both reference lists.
        Set<Reference> ref1 = getReferences();
        Set<Reference> ref2 = ((QualifiedSynonym) o).getReferences();
        
        // If both lists are contained within each other, then 
        // consider both lists equal.
        if (ref1.equals(ref2)) {
            return 0; 
        // If ref1 contains ref2, but ref2 has more values then return -1.
        } else if (ref1.containsAll(ref2)) {
            return -1;
        }
        // Drop down and return a 1.
        return 1;
        
        /*
		// Are the synonyms the same?
        int synonymCompare = getSynonym().compareTo(((QualifiedSynonym) o).getSynonym());
		
        //references may not be populated.  
        boolean referencesCompare = false;
        if (getReferences() == null && ((QualifiedSynonym)o).getReferences() == null)
            referencesCompare = true;
        //else
        //    if (getReferences() != null && ((QualifiedSynonym)o).getReferences() != null)
		
        //TODO - this statement is causing Hibernate exception:
        //org.hibernate.LazyInitializationException: illegal access to loading collection    	
        //referencesCompare = getReferences().equals(((QualifiedSynonym) o).getReferences());
		
		// If both the synonym and references match, then return 0.
		if(synonymCompare == 0 && referencesCompare){
			return 0;
		} else if (synonymCompare != 0) {
			return synonymCompare;
		} else {
			return -1;
		}
        */
		
	}
    
    // Returns a hashcode to uniquely identify this object.
    /**
     * Returns a hashCode to uniquely identify this object.  The hashCode
     * is based on the synonym hashCode and references hashCodes.
     */
    public int hashCode() {
        int output = getSynonym().hashCode() * 10000;
        if (references != null) {
        	for(Reference reference : references) {
        		output = output + reference.hashCode();
        	}
        }
        return output;
    }
    
    /***************************************************************************
     * An equality implementation that uses database persisted information (id) 
     * and the object name to determin equality.  If unavailable, then it 
     * defaults to the standard equality operator.
     */
    public boolean equals(Object o) {
        
        if( o != null 
            && o instanceof QualifiedSynonym 
            && getSynonym() != null) {
                // Compare using the simpleClassName and id.
                if ( getSynonym().equals(((QualifiedSynonym)o).getSynonym())) {
                    return true;        
                } 
        }
        
        // Drop to the default compare.
        return super.equals(o);     
    }

    

}
