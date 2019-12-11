package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * The DependantObject object is a super class that gives context to its
 * subclasses. For example, if a MonographRiskStatement subclass has a
 * monographPreparation indicated, that means that the risk statement only 
 * applies to that monographPreparation. If it also has a monographUse indicated, 
 * then it applies to the combination of those two items.
 * Note that these also have Citations, which give authoritative sources for
 * these ingredients' attributes/qualities as defined in a Monograph.
 */
public abstract class DependantObject extends PersistentObject
                                      implements Comparable {
    private static final String COMMA_SPACE = ", ";
	protected final static String SEMI_SPACE = "; ";
    protected final static String EMPTY_STRING = "";
    
    private static final String START_TEXT_ENG = "For products providing ";
    private static final String MIDDLE_TEXT_ENG = " doses that are ";
    private static final String END_TEXT_ENG = " per day, the following statement is required: ";
    private static final String START_TEXT_FR = "Pour produits fournissant ";
    // private static final String MIDDLE_TEXT_FR = " pour plus de  ";
    private static final String END_TEXT_FR = " par jour, l'énoncé suivant est requis: ";
    
    private Set<Citation> citations;
    private Set<MonographUse> monographUses;
    private Set<MonographSourceOrganismPart> monographSourceOrganismParts;
    private String otherDependencyE;
    private String otherDependencyF;
    private MonographPreparation monographPreparation;
    private SubPopulation subPopulation;
    private MonographDoseCombination monographDoseCombination;
    private MonographSourceIngredient monographSourceIngredient;
    private Set<TextMonographSourceMaterial> otherMonographSources; 
    private Set<DosageFormGroup> dosageFormGroups;       //See FeverFew - recommended uses have dependencies on DosageFormGroups
    
    
    //~ public -----------------------------------------------------------------
    /**
     * Gets a string representation of the citations associated with this Object.
     * @return the citations for this Object.
     */
    public String getCitationsAsString() {        
        if ( getCitations() == null 
            || getCitations().size() == 0 ) {
                return null;
            }

        StringBuffer citationList = new StringBuffer();
        Iterator<Citation> i = getCitations().iterator();
        while ( i.hasNext() ) {
            Citation citation = i.next();
            if ( citation.getWorkAuthor() != null ) {
                if ( citationList.length() != 0 ) {
                    citationList.append( COMMA_SPACE );
                }
                citationList.append( citation.getAbridgedTitle() );
            }
        }
        return citationList.toString();
    }

    /**
     * Gets the citations associated with this Object.
     * @return the citations Set for this Object.
     */
    public Set<Citation> getCitations() {
        return citations;
    }

    /**
     * Gets the citations associated with this Object ordered by the citation
     * date.
     * @return the citations Set for this Object ordered by date.
     */
    public Set<Citation> getCitationsByDate() {
        Comparator<Citation> citationComparator = new Citation.CitationComparator();
        Set<Citation> citationsByDate = new TreeSet<Citation>(citationComparator);
        citationsByDate.addAll(getCitations());
        return citationsByDate;
    }
    
    /**
     * Helper method that returns this Dependency class as a title string
     * to be used by the display.
     * 
     * <P>Defined here as an empty string to be overwriten by the sub-classes
     * depending on the implementation and the grouping of the dependencies.
     * For example, in the Monograph Dose we have a top level grouping based
     * on the subPopulation object.
     *
     * @return  a String representing this class for grouping purposes
     */
    public String getDependencyTitleAsString() {
        return EMPTY_STRING;
    }

    /**
     * Helper method that returns this Dependency class as a single string. This
     * is helpful as it reduces the number of formulas required in the JSP and
     * helps in the grouping of values.
     *
     * TODO: Does this work in a multi dependency environment?  e.g. if the 
     * base class Dependant object contains 2 dependencies of different types,
     * does this design break?
     * 
     * @return  a String representing this class for grouping purposes
     */
    public String getDependencyAsString() {
        if (getMonographUses() != null && getMonographUses().size() != 0) {
            StringBuffer sb = new StringBuffer();
            for (MonographUse monographUse : getMonographUses()) {
                if ( sb.length() != 0 ) {
                    sb.append( SEMI_SPACE );
                }
                sb.append( monographUse.getShortStatement() );              
            }
            return sb.toString();
        }
        if (monographSourceOrganismParts != null && 
        	monographSourceOrganismParts.size() != 0) {
            StringBuffer sb = new StringBuffer();
            for (MonographSourceOrganismPart organismPart : monographSourceOrganismParts) {
	            if (sb.length() != 0) {
	                sb.append(SEMI_SPACE);
	            }
	            sb.append(organismPart.getOrganismPart().getName());
	        }
	        return sb.toString();
        }
        
        if (getMonographPreparation() != null) {
            if (getMonographPreparation().getPreparationType() != null) {
                return getMonographPreparation().getPreparationType().getName();
            }
            if (getMonographPreparation().getPreparationTypeGroup() != null) {
                return getMonographPreparation().getPreparationTypeGroup().getName();
            }
        }
        
        if ( getOtherDependency() != null ) {
            return getOtherDependency();
        }
		if (monographSourceOrganismParts != null && 
			monographSourceOrganismParts.size() != 0) {
			StringBuffer sb = new StringBuffer();
            for (MonographSourceOrganismPart organismPart : monographSourceOrganismParts) {
				if ( sb.length() != 0 ) {
					sb.append( SEMI_SPACE );
				}
				sb.append( organismPart.getOrganismPart().getName() );              
			}
			return sb.toString();
		}
        if ( getSubPopulation() != null ) {
            return getSubPopulation().getName();
        }
        if ( getSubPopulation() != null ) {
            return getSubPopulation().getName();
        }
        if ( getMonographDoseCombination() != null ) {
            return getMonographDoseCombination().getQuantitiesForDisplay();
        }
        if ( monographSourceIngredient != null ) {
            return monographSourceIngredient.getIngredient().getAuthorizedName();
        }
        if(getOtherSources() != null && getOtherSources().size() > 0) {
            StringBuffer genText = new StringBuffer();
            for (MonographSourceMaterial monoSourceMat : getOtherSources()) {
                 if(genText.length() != 0 ) {
                     genText.append(SEMI_SPACE);
                 }
                 genText.append(monoSourceMat.getSourceMaterialName());
            }
            return genText.toString();
        }
        return null;
    }


    /**
     * Display method, iterates through multiple uses of the Dependant Object and returns
     * them as a single concatenated string. 
     * @return
     */
    public String getUsesDependencyTitle() {
       
    	if (getMonographUses() == null ||
            getMonographUses().size() == 0 ) {
                return null;
            }
    	
        StringBuffer sb = new StringBuffer();         
        String useTitle = null;
        for (MonographUse monoUse : getMonographUses()) {
            if ( sb.length() != 0 ) {
                sb.append( SEMI_SPACE );
            }
            useTitle = monoUse.getShortStatement();
            if (useTitle != null) {
            	sb.append( useTitle );
            }
        } 
       
        return sb.toString();
    }
    
    /**
     * Display method, iterates through multiple MonographSourceOrganismParts
     * of the Dependant Object and returns them as a single concatenated string. 
     * @return
     */
    public String getSourceOrgPartDependencyTitle() {
       
    	if (getMonographSourceOrganismParts() == null ||
    		getMonographSourceOrganismParts().size() == 0 ) {
                return null;
            }
    	
        StringBuffer sb = new StringBuffer();         
        String msopTitle = null;
        for (MonographSourceOrganismPart monoSrcOrgPart : getMonographSourceOrganismParts()) {
            if ( sb.length() != 0 ) {
                sb.append( SEMI_SPACE );
            }
            msopTitle = monoSrcOrgPart.getSourceMaterialName();
            if (msopTitle != null) {
            	sb.append( msopTitle );
            }
        } 
       
        return sb.toString();
    }
    /**
     * Display method, iterates through multiple uses of the Dependant Object and returns
     * them as a single concatenated string. 
     * @return
     */
    public String getSourceOrgPartsDependencyTitle() {
       
    	if (getMonographSourceOrganismParts() == null ||
    			getMonographSourceOrganismParts().size() == 0 ) {
                return null;
            }
    	
        StringBuffer sb = new StringBuffer();         
        String msopTitle = null;
        for (MonographSourceOrganismPart monoSourceOrgPart : getMonographSourceOrganismParts()) {
            if ( sb.length() != 0 ) {
                sb.append( SEMI_SPACE );
            }
            msopTitle = monoSourceOrgPart.getSourceMaterialName();
            if (msopTitle != null) {
            	sb.append( msopTitle );
            }
        } 
       
        return sb.toString();
    }
    
    
    public Set<MonographUse> getMonographUses() {
        return monographUses;
    }

    public MonographDoseCombination getMonographDoseCombination() {
        return monographDoseCombination;
    }

	/**
     * The organism part and preparation type selection available in the
     * MonographDependency must be a subselection of the parts / preparation
     * available to the Monograph / Ingredient / DOS or COS parts and
     * preparation.
     *
     * @return  DOCUMENT ME!
     */
    public Set<MonographSourceOrganismPart> getMonographSourceOrganismParts() {
        return monographSourceOrganismParts;
    }

    /**
     * Gets the OtherDependency in the language appropriate for the Locale.
     *
     * @return  the locale-specific OtherDependency.
     */
    public String getOtherDependency() {
        if (isLanguageFrench()) {
            return getOtherDependencyF();
        }
        return getOtherDependencyE();
    }

    public String getOtherDependencyE() {
        return otherDependencyE;
    }

    public String getOtherDependencyF() {
        return otherDependencyF;
    }

    /**
     * Implemented to map the dependency relationship between a Monograph Risk
     * and an Other Sources (String) defined in the one of the Monograph Entries.
     * 
     * @return an OtherSource()
     */
    public Set<TextMonographSourceMaterial> getOtherSources() {
        return otherMonographSources;
    }

    public void setOtherSources(Set<TextMonographSourceMaterial> value) {
    	otherMonographSources = value;
    }

    public SubPopulation getSubPopulation() {
        return subPopulation;
    }

    /**
     * Sets the Citations for this Object.
     *
     * @param newVal the Set of Citations for this Object
     */
    public void setCitations(Set<Citation> newVal) {
        citations = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setMonographUses(Set<MonographUse> newVal) {
        monographUses = newVal;
    }

    public void setMonographDoseCombination(MonographDoseCombination newVal) {
        monographDoseCombination = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setMonographSourceOrganismParts(Set<MonographSourceOrganismPart> newVal) {
        monographSourceOrganismParts = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setOtherDependencyE(String newVal) {
        otherDependencyE = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setOtherDependencyF(String newVal) {
        otherDependencyF = newVal;
    }

	/**
	 * DOCUMENT ME!
	 *
	 * @param  newVal
	 */
    public void setSubPopulation(SubPopulation newVal) {
        subPopulation = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
    
        if (getDependencyAsString() == null) {
            return 1;
        }
        if (o == null) return -1;
        
        String dependString = ((DependantObject)o).getDependencyAsString();
        if (dependString == null) return -1;
    
        if (!getDependencyAsString().equals(dependString)) {
            return StringComparator.compare(getDependencyAsString(),dependString);
        }
        
        return getId().compareTo(((DependantObject)o).getId());
            
    }
    
	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 */
    /*
    @Override
	public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;

		buffer.append(super.getValuesAsString());
        buffer.append(", citations: ");
        if (getCitations() != null){
            isFirstItem = true;
            buffer.append("[");
            for (Citation citation : getCitations()) {
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
        buffer.append(", ingredientSource: ");
        if (getIngredientSource() != null){
            buffer.append(getIngredientSource().getAuthorizedName());
		} else {
			buffer.append("null");
		}
        buffer.append(", monographDoseCombination: ");
        buffer.append(getMonographDoseCombination());
        buffer.append(", monographUses: ");
        buffer.append(getMonographUses());
        buffer.append(", organismParts: ");
        buffer.append(getOrganismParts());
        buffer.append(", otherDependencyE: ");
        buffer.append(getOtherDependencyE());
        buffer.append(", otherSources: ");
        buffer.append(getOtherSources());
        buffer.append(", monographPreparation: ");
        buffer.append(getMonographPreparation());
		buffer.append(", subPopulation: ");
		if (getSubPopulation() != null){
			buffer.append(getSubPopulation().getName());
		} else {
			buffer.append("null");
		}
		
		return buffer.toString();
	}
	*/
    
   
    /**
     * Used to display a textual statement for MonographDirectionForUse and 
     * MonographDuration objects with OtherSource and Dose dependencies.  
     * See Niacin monograph.
     * @return
     */
    public String getForDoseDisplay() {
        if(getMonographDoseCombination() == null ) {
        	return null;
        }
        StringBuffer output = new StringBuffer();
        if(isLanguageFrench()){
            output.append(START_TEXT_FR);
        } else {
            output.append(START_TEXT_ENG);
        }
        if(getOtherSources() != null && getOtherSources().size() > 0) {
            StringBuffer otherSourcesText = new StringBuffer();
            for (MonographSourceMaterial monoSourceMat : getOtherSources()) {
                 if(otherSourcesText.length() != 0 ) {
                     otherSourcesText.append(SEMI_SPACE);
                 }
                 otherSourcesText.append(monoSourceMat.getSourceMaterialName());
            }
            output.append(otherSourcesText);
        } else if (monographSourceIngredient != null ) {
            output.append(monographSourceIngredient.getIngredient().getAuthorizedName());
        }
        
        if(!isLanguageFrench()){
            output.append(MIDDLE_TEXT_ENG);
        }
        
        output.append(getMonographDoseCombination().getMonographDoses().
        			      iterator().next().getUnitsDisplay(true));
        
        if(isLanguageFrench()){
            output.append(END_TEXT_FR);
        } else {
            output.append(END_TEXT_ENG);
        }
        
        return output.toString();
    }
    
    /**
	 * Implemented to map the dependency relationship between a Monograph Risk
	 * and an Ingredient Source defined in the one of the Monograph Entries.
	 */ 
	public MonographSourceIngredient getMonographSourceIngredient() {
		return monographSourceIngredient;
	}

	public void setMonographSourceIngredient(
			MonographSourceIngredient monographSourceIngredient) {
		this.monographSourceIngredient = monographSourceIngredient;
	}

	public Set<DosageFormGroup> getDosageFormGroups() {
		return dosageFormGroups;
	}

	public void setDosageFormGroups(Set<DosageFormGroup> dosageFormGroups) {
		this.dosageFormGroups = dosageFormGroups;
	}
	
	 /* convenience accessor for the preparation type */
    public String getPreparationTypeName() {
        if (getMonographPreparation() != null && 
            getMonographPreparation().getPreparationType() != null) {
            return getMonographPreparation().getPreparationType().getName();
        }
        return null;    
    }
    
    /* convenience accessor for the preparation type group */ 
    public String getPreparationTypeGroupName() {
        if (getMonographPreparation() != null && 
            getMonographPreparation().getPreparationTypeGroup() != null) {
            return getMonographPreparation().getPreparationTypeGroup().getName();
        }
        return null;    
    }
    
	public MonographPreparation getMonographPreparation() {
		return monographPreparation;
	}

	public void setMonographPreparation(MonographPreparation monographPreparation) {
		this.monographPreparation = monographPreparation;
	}

    
    
}
