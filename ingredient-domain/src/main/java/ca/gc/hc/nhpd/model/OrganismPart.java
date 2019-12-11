package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


/*******************************************************************************
 * An object that represents the type of a specific part of a particular
 * organism (e.g. a rose stem) or OrganismGroup (e.g. a Fish fin).
 */
public class OrganismPart extends PersistentObject 
                         implements Comparable {

	public static final String SPACE = " ";
	public static final String COMMA = ",";
    public static final String DASH = "-";
    public static final String OPEN_ROUND_BRACKET = "(";
    public static final String CLOSE_ROUND_BRACKET = ")";
    
	//~ Instance fields --------------------------------------------------------

	private Set<DefinedOrganismSubstance> ingredients;
	private String name;
	private Organism organism;
	private NamedOrganismGroup organismGroup;
	private Set<SubIngredient> subIngredients;
	private OrganismPartType type;
    private String shortDescriptionE;
    private String shortDescriptionF;

	//~ public -----------------------------------------------------------------
    public OrganismPart(){};
    
	/**
	 * Constructor - used by the DefinedOrganismSubstance to create
	 * a complete list of organism parts. This is to fix issue 8379.
	 * It will be a cartesian product of the taxonomy node names +
	 * the existing organism part list. We only care about the
	 * constituents (subingredients), the other members are not
	 * applicable in this case.
	 * 
	 * @param orgPartName a string representing the name of the 
	 * taxonomy node used in the new part.
	 * @param ptype = The part type
	 * @param subIngSet = The set of constituents
	 */
    public OrganismPart(String orgPartName, OrganismPartType ptype, Set<SubIngredient> subIngSet,
    		UserAccount lastUpdateAccount) {
    	name = orgPartName;
    	
    	//To avoid a duplicate Set reference Hibernate error
    	subIngredients = new TreeSet<SubIngredient>();
    	
    	if (subIngSet != null) {
    		for (SubIngredient subIng:subIngSet) {
    			subIngredients.add(subIng);
    		}
    	}
    	
    	type = ptype;
    	Date d = new Date();
    	this.setLastUpdateDate(d);
    	this.setCreationDate(d);
    	this.setLastUpdateAccount(lastUpdateAccount);
    }

	/**
	 * For sorting purposes, we will display the name of the Organism associated with
	 * this OrganismPart object, followed by the locale specific part name
	 */
	public String getDisplayableString() {
		StringBuffer sb = new StringBuffer();
		if(getOrganism()!=null){
			sb.append(getOrganism().getName());
		}
		if(getOrganismGroup()!=null){
			sb.append(getOrganismGroup().getName());
		}
		sb.append(SPACE);
        sb.append(getType());
		
		return sb.toString();
	}
	
	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * @param   o  the object to compare this to.
	 *
	 * @return  integer value depicting equality.
	 *
	 * @throws  ClassCastException  returned when the object being compared is
	 *                              not an OrganismPart
	 */
	public int compareTo(Object o) throws ClassCastException {
		
		if (o == null) return -1; 
	    return StringComparator.compare(getName(),((OrganismPart)o).getName());
	}

	/**
	 * Gets the Set of Ingredients.
	 *
	 * @return  The Set of Ingredients
	 *
	 * @see     #setIngredients()
	 */
	public Set<DefinedOrganismSubstance> getIngredients() {
		return ingredients;
	}

	/**
	 * Gets the Name.
	 * @return  The Name of this OrganismPart
	 */
	public String getName() {
        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
	}

    /**
     * Gets the English name.
     * @return  The English name of this OrganismPart
     * @see     #getName()
     */
    public String getNameE() {
		StringBuffer buffer = new StringBuffer();

		if ((name != null) && (name.length() > 0)) {
			buffer.append(name);
			buffer.append(" - ");
		} else {
			if (getOrganism() != null) {
				buffer.append(getOrganism().getName());
				buffer.append(" - ");
			} else if (getOrganismGroup() != null) {
	            buffer.append(getOrganismGroup().getNameE());
	            buffer.append(" - ");
	        } else {
	            buffer.append("null - ");
	        }
		}
		
        if(getType() != null && getType().getNameE() != null ) {
            buffer.append(getType().getNameE());         
        } else {
            buffer.append("null");
        }
        return buffer.toString();
    }

    /**
     * Gets the French name.
     * @return  The French name of this OrganismPart
     * @see     #getName()
     */
    public String getNameF() {
        StringBuffer buffer = new StringBuffer();

		if ((name != null) && (name.length() > 0)) {
			buffer.append(name);
			buffer.append(" - ");
		} else {
	        if (getOrganism() != null) {
	            buffer.append(getOrganism().getName());
	            buffer.append(" - ");
	        } else if (getOrganismGroup() != null) {
	            buffer.append(getOrganismGroup().getNameF());
				buffer.append(" - ");
			} else {
				buffer.append("null - ");
			}
		}
		
        if(getType() != null && getType().getNameF() != null ) {
            buffer.append(getType().getNameF());         
		} else {
			buffer.append("null");
		}
		return buffer.toString();
	}

    public String getSourceMaterialName() {
        return getName();
    }
    
	/**
	 * Gets the Organism that this is a part of. Null if this is part of an
	 * Organism Group.
	 *
	 * @return  The Organism
	 *
	 * @see     #setOrganism()
	 */
	public Organism getOrganism() {
		return organism;
	}

	/**
	 * Gets the OrganismGroup that this is a part of. Null if this is part of an
	 * Organism.
	 *
	 * @return  The OrganismGroup
	 *
	 * @see     #setOrganismGroup()
	 */
	public NamedOrganismGroup getOrganismGroup() {
		return organismGroup;
	}

	/**
	 * Gets the set of SubIngredients.
	 *
	 * @return  The set of SubIngredients
	 *
	 * @see     #setSubIngredients()
	 */
	public Set<SubIngredient> getSubIngredients() {
		return subIngredients;
	}

	/**
	 * Gets the OrganismPartType.
	 *
	 * @return  The OrganismPartType
	 *
	 * @see     #setType()
	 */
	public OrganismPartType getType() {
		return type;
	}

	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 *
	 * @return  This object as a string
	 */
	public String getValuesAsString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(super.getValuesAsString());

		if (getOrganism() != null) {
			buffer.append(", organism: ");
			buffer.append(getOrganism().getName());
		}

		if (getOrganismGroup() != null) {
			buffer.append(", organismGroup: ");
			buffer.append(getOrganismGroup().getName());
		}

		buffer.append(", type: ");
		buffer.append(getType());
		buffer.append(", subIngredients: ");
		buffer.append(getSubIngredients());

		return buffer.toString();
	}

	/**
	 * Returns a true / false depending on this organismPart having
	 * subIngredients. Initially checks subIngredients for null and size and
	 * returns a false if either are true.
	 *
	 * <p>In use by the OrganismPart by the getOrganismPartsWithConstituents()
	 * opperation.
	 *
	 * @return  boolean flag qualifying this OrganismPart has having one or more
	 *          SubIngredients.
	 *
	 * @see     #getSubIngredients()
	 */
	public boolean hasSubIngredients() {

		if ((getSubIngredients() == null)
				|| (getSubIngredients().size() == 0)) {
			return false;
		}

		return true;
	}

	/**
	 * Sets the Set of Ingredients.
	 *
	 * @param  newVal
	 *
	 * @see    #getIngredients()
	 */
	public void setIngredients(Set<DefinedOrganismSubstance> newVal) {
		ingredients = newVal;
	}

	/**
	 * Sets the Organism that this is a part of. Null if this is part of an
	 * Organism Group.
	 *
	 * @param  newVal  the Organism that this is a part of.
	 *
	 * @see    #getOrganism()
	 */
	public void setOrganism(Organism newVal) {
		organism = newVal;
	}

	/**
	 * Sets the OrganismGroup that this is a part of. Null if this is part of an
	 * Organism.
	 *
	 * @param  newVal  the OrganismGroup that this is a part of.
	 *
	 * @see    #getOrganismGroup()
	 */
	public void setOrganismGroup(NamedOrganismGroup newVal) {
		organismGroup = newVal;
	}

	/**
	 * Set the set of SubIngredients.
	 *
	 * @param  newVal
	 *
	 * @see    #getSubIngredients
	 */
	public void setSubIngredients(Set<SubIngredient> newVal) {
		subIngredients = newVal;
	}

	/**
	 * Sets the OrganismPartType.
	 *
	 * @param  newVal
	 *
	 * @see    #getType()
	 */
	public void setType(OrganismPartType newVal) {
		type = newVal;
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
        if (getType() != null && getType().getNameE() != null) {
            sbE.append(getType().getNameE());
        } else {
            sbE.append("null");
        }
        
        // French Synonym Name
        if (getType() != null && getType().getNameF() != null) {
            sbF.append(getType().getNameE());
        } else {
            sbF.append("null");
        }
        
        if (getOrganism() != null) {
            sbE.append(SPACE + DASH + SPACE + OPEN_ROUND_BRACKET + getOrganism().getName() + CLOSE_ROUND_BRACKET);
            sbF.append(SPACE + DASH + SPACE + OPEN_ROUND_BRACKET + getOrganism().getName() + CLOSE_ROUND_BRACKET);
        }
        /*
        sbE.append(SPACE + OPEN_ROUND_BRACKET);
        sbF.append(SPACE + OPEN_ROUND_BRACKET);
        
        // Iterate through all the references.
        if (getSubIngredients() != null && getSubIngredients().size() != 0) {
            int count = 0;
            sbE.append(OPEN_ROUND_BRACKET);
            sbF.append(OPEN_ROUND_BRACKET);
            Iterator i = getSubIngredients().iterator();
            while (i.hasNext()) {
                SubIngredient subIngredient = (SubIngredient) i.next();
                String subIngredientName = subIngredient.getName();
                if (count != 0) {
                    sbE.append(COMMA + SPACE);
                    sbF.append(COMMA + SPACE);
                }
                sbE.append(subIngredient.getIngredient().get.getNameE());
                sbF.append(subIngredientName);
                
            }
            sbE.append(CLOSE__BRACKET);
            sbF.append(CLOSE_SQUARE_BRACKET);
            
        }
        */
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

    
    
}
