package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.ArrayList;
import java.util.Set;
import java.util.Comparator;
import org.apache.log4j.Logger;

/*******************************************************************************
 * This describes claims (health benefits) that can be associated with the
 * ingredients according to a Monograph.
 */
public class MonographUse extends DependantObject implements Comparable {
    private static final Logger LOG = Logger.getLogger(MonographUse.class);
    private static final String SEMI_SPACE = "; ";
    private static final int COMBO_DOSE = 0;
    private static final int SINGLE_DOSE = 1;
    
    //~ Instance fields --------------------------------------------------------

    private String code;
   
    private String longStatementE;
    private String longStatementF;
    private String shortStatementE;
    private String shortStatementF;
    
    //parent Monographs - only used for loading
    private Set<Monograph> monographs;
	private UseType useType;
    private RecommendedDietaryAllowance recommendedDietaryAllowance;
	private Long sortOrder;
	
	/**
     * The dependency level is used by the web service to calculate and control
     * recursive Monograph Use dependencies.  By keeping tabs on this number
     * we are able to display the required Monograph Use without letting the 
     * code run away into an infinit loop.  The dependency number used in the 
     * Monograph Dependency object allows for 2 levels of Monograph Use 
     * dependencies to be displayed. 
	 */
    private int dependencyLevel = 0;
	
	//~ public -----------------------------------------------------------------

    /**
     * Returns the current dependency level plus 1.  Each time we are going 
     * down into a new dependency level, we use this accessor to get the 
     * current level.
     */
    public int getDependencyLevel() {
        return dependencyLevel + 1;
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

	/***************************************************************************
     * Over-ridden since this attribute is not used by this subclass.
     * Removed - think these methods were being called by Hibernate
     * for dirty set checks. 
     */
    /*
    @Override
    public Set<MonographUse> getMonographUses() {
        LOG.error("MonographUse.getMonographUses() should never be called.");
        return null;
    }

    /***************************************************************************
     * Over-ridden since this attribute is not used by this subclass.
     *
    @Override
    public void setMonographUses(Set<MonographUse> newVal) {
        LOG.error("MonographUse.setMonographUses() should never be called.");
    }
    */

    /***************************************************************************
	* Gets the sort order used to display the Monograph Uses.  Monograph
	* uses get loaded with the ones most commonly used being loaded first.
	* @return the sort order.
	* @see #setSortOrder()
	*/
	public Long getSortOrder(){
	   return sortOrder;
	}

	/***************************************************************************
	* Sets the sort order.
	* @see #getSortOrder()
	*/
	public void setSortOrder(Long newVal){
		sortOrder = newVal;
	}

	/***************************************************************************
	 * Gets the use type.
	 * @return the use type.
	 * @see #setUseType()
	 */
	public UseType getUseType(){
		return useType;
	}

	/***************************************************************************
	 * Sets the use type.
	 * @see #getUseType()
	 */
	public void setUseType(UseType newVal){
		useType = newVal;
	}
	
	public boolean isHasDoseDependency() {
		return getMonographDoseCombination() != null;
	}
	
	public boolean isHasSubIngredientDoseDependency() {
		//log("isHasSubIngredientDoseDependency");
		MonographDoseCombination monoDoseCombo = getMonographDoseCombination();
		if (monoDoseCombo == null) return false;
		return monoDoseCombo.hasMonographSubingredientDoseCombinations();
	}
	
	
	/*
	 * Introduced for Fish Oil. 
	 */
	public String getSubIngredientDoseDependencyTitle() {
		
		StringBuffer sb = new StringBuffer();
		
		//	dependency on Dose, particulary SubIngredientDose
		MonographDoseCombination monoDoseCombo = getMonographDoseCombination();
		
		//Need to "AND" the doses found in the MonographDoseCombination. 
		
		if (monoDoseCombo != null) {
			Set<MonographDose> monoDoses = monoDoseCombo.getMonographDoses();
			boolean first = true;
			//log("MonographUse Id: " + getId() + " " + " displaying MDC dependency... ");
			
			if (monoDoseCombo.hasMonographSubingredientDoseCombinations()) {
			
				if (isLanguageFrench()) {
					sb.append(MonographDose.PRODUCTS_PROVIDING_F);
				}
				else {
					sb.append(MonographDose.PRODUCTS_PROVIDING_E);
				}	
			    
			   /* 
			    * Sort the doses such that doses with MSIDs containing only a 
				* min or max dose appear at the end of the line of text, prefixed
				* with "including at least", etc.  
	    		* TODO - probably a better way with a comparator impl.
	    		*/
	    		ArrayList<MonographDose> sortedDoses = new ArrayList<MonographDose>();
	    		orderDoses(sortedDoses,monoDoses,COMBO_DOSE);
	    		orderDoses(sortedDoses,monoDoses,SINGLE_DOSE);
	    		
	    		/*
	    		for (MonographDose md: sortedDoses) {
					log ("Sorted MDC Code: " + md.getCode());
				}
				*/
	    		/*
	    		log ("--------------------------------------");
	    		log ("Processing sorted msids...");
	    		for (MonographDose msid2: sortedDoses) {
	    			log("MSID: " + msid2.getCode() + 
	    				" MinDose/MaxDose:  " + msid2.getDoseMinimum() + "/" + msid2.getDoseMaximum()); 	
	    		}
				*/
				
	    		for (MonographDose monoDose: sortedDoses) {
	    			/*
	    			log("MonographUse: TEST16 MonoDose Id/Code: " + 
	    					monoDose.getId() + "/" + monoDose.getCode());
	    			log ("MD: FrequencyForDisplay: " + monoDose.getFrequencyForDisplay());
	    			*/
	    			/*
	    			if (!first) { 
	    				if (isLanguageFrench())
	    					sb.append(" et ");
	    				else
	    					sb.append(" and ");
	    			}
	    			*/
	    			
	    			if (monoDose.isHasSubIngredientDoseCombinations()) {
	    				if (!first) { 
		    				sb.append(",");
		    			}
	    				sb.append(monoDose.getSubIngredientDoseCombinationsForDisplay());
	    				//log("    Appended: " + monoDose.getSubIngredientDoseCombinationsForDisplay());
	    			}
	    			
	    			/*
	    			sb.append(monoDose.formatDose(monoDose.getDoseMinimum(),
	    										  monoDose.getDoseMaximum()));
	    			*/
	    			first = false;
	    		}
	    		
	    		//TODO - need to validate daily vs. single dose.
	    		//TBD - need to model the dose type at the Combo level?  
	    		if (isLanguageFrench())
	    			sb.append ( ", par jour: " );
				else
					sb.append ( ", per day: " );
				
			}
			else {
				//TODO - no MSIDCs code
			}
				
		}
		return sb.toString();
	}
	
    /**
     * Returns a title string describing the Dependency for display purposes.
     * Overrides the default provided by the DependantObject.
     * 
     * @return  a String representing this class for grouping purposes
     */
    @Override
    public String getDependencyTitleAsString() {
        
    	StringBuffer sb = new StringBuffer();
    	
    	
    	//dependency on Dose
    	MonographDoseCombination mdc = getMonographDoseCombination();
    	if (mdc != null) {
    		//log ("Use " + code + " has dependency on Dose: " + mdc.getCode());
    		if (isLanguageFrench()) {
				sb.append(MonographDose.PRODUCTS_PROVIDING_F);
			}
			else {
				sb.append(MonographDose.PRODUCTS_PROVIDING_E);
			}	
    		sb.append(mdc.getQuantitiesForDisplay());
    		return sb.toString();
    	}	
    	
    	//dependency on Use
    	if (getMonographUses() != null && getMonographUses().size() != 0) {
            
            for (MonographUse monographUse : getMonographUses()) {
                if ( sb.length() != 0 ) {
                    sb.append( SEMI_SPACE );
                }
                sb.append( monographUse.getShortStatement() );              
            }
            return sb.toString();
        }
    	
    	//dependency on Preparation
        if (getMonographPreparation() != null) {
            if (getMonographPreparation().getPreparationType() != null) {
                return getMonographPreparation().getPreparationType().getName();
            }
            if (getMonographPreparation().getPreparationTypeGroup() != null) {
            	return getMonographPreparation().getPreparationTypeGroup().getName();
            }
        }
          
        //dependency on Source Organism Part(s)
        if (getMonographSourceOrganismParts() != null && 
            getMonographSourceOrganismParts().size() != 0) {
            for (MonographSourceOrganismPart organismPart : getMonographSourceOrganismParts()) {
				if ( sb.length() != 0 ) {
					sb.append( SEMI_SPACE );
				}
				sb.append( organismPart.getOrganismPart().getType().getName() );              
			}
			return sb.toString();
		}
		
        // Not a dependency... but we do display as category.
        if ( getUseType() != null ) {
            return getUseType().getName();    
        }
      
        return null;
    }
      
    //TODO - works for single contained MonographSubIngredientDoseCombinations only
    //added to prototype Fish Oil
    private void orderDoses(ArrayList<MonographDose> orderedDoses,
    						Set<MonographDose> monoDoses,
    						int type) {
    	
    	for (MonographDose monoDose:monoDoses) {
    		if (monoDose.getMonographSubIngredientDoseCombinations() != null &&
    			monoDose.getMonographSubIngredientDoseCombinations().size() > 0) {
    			MonographSubIngredientDoseCombination msidc = 
    				monoDose.getMonographSubIngredientDoseCombinations().iterator().next();
    			Set<MonographSubIngredientDose> msids = msidc.getMonographSubIngredientDoses();
    			if (msids != null) {
    				MonographSubIngredientDose msid = msids.iterator().next();
		    		Float minDose = msid.getDoseMinimum();
		    		Float maxDose = msid.getDoseMaximum();
		    		if (type == COMBO_DOSE) {
		    			if (minDose != null && minDose.floatValue() > 0f &&
		    					maxDose != null && maxDose.floatValue() > 0f) 
		    				orderedDoses.add(monoDose);
		    		}
		    		if (type == SINGLE_DOSE) {
		    			if ((minDose == null || minDose.floatValue() == 0f) ||
		    					(maxDose == null || maxDose.floatValue() == 0f)) 
		    				orderedDoses.add(monoDose);
		    		}
    			}
    		}
    	}

    }
    	
    	
    	
    /**
     * Helper method that returns this Dependency class as a single string. This
     * is helpful as it reduces the number of formulas required in the JSP and
     * helps in the grouping of values.
     *
     * @return  a String representing this class for grouping purposes
     * 
     * TODO - needs to be broken out into type specific dependencies.
     */
    @Override
    public String getDependencyAsString() {
        
    	if (getDosageFormGroups() != null &&
    		getDosageFormGroups().size() > 0) {
    		//log("Have " + getDosageFormGroups().size() + " DosageForm Groups");
    		StringBuffer sb = new StringBuffer();
        	
        	//just testing code for now...
    		if (isLanguageFrench()) {
    			sb.append("Pour les formes posologiques de(s) type(s): " );
    		}
    		else {
    			sb.append("For dosage forms of type(s): " );
    		}
    		
    			for (DosageFormGroup dfg: getDosageFormGroups()) {
        		sb.append(dfg.getName() + ",");
        	}
        	//replace the last comma with ":"
        	sb.replace(sb.length()-1,sb.length()+1,":");
        
        	return sb.toString();
        }
    	
    	if ( getOtherDependency() != null ) {
            return getOtherDependency();
        }

        if ( getSubPopulation() != null ) {
            return getSubPopulation().getName();
        }
        
        return null;
    }

	/**
     * Accessors for Hibernate. Only used in the loading of Monographs.
	 * @return
	 */
    public Set<Monograph> getMonographs() {
		return monographs;
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
     * Gets the longStatement in the language appropriate for the Locale.
     *
     * @return  the locale-specific longStatement.
     */
    public String getLongStatement() {
        if (isLanguageFrench()) {
            return getLongStatementF();
        }
        return getLongStatementE();
    }

    public String getLongStatementE() {
        return longStatementE;
    }

    public String getLongStatementF() {
        return longStatementF;
    }

    /**
     * Gets the shortStatement in the language appropriate for the Locale.
     *
     * @return  the locale-specific shortStatement.
     */
    public String getShortStatement() {
        if (isLanguageFrench()) {
            return getShortStatementF();
        }
        return getShortStatementE();
    }

    public String getShortStatementE() {
        return shortStatementE;
    }

    public String getShortStatementF() {
        return shortStatementF;
    }
    
    public RecommendedDietaryAllowance getRecommendedDietaryAllowance() {
        return recommendedDietaryAllowance;
    }
    

    public void setRecommendedDietaryAllowance(RecommendedDietaryAllowance recommendedDietaryAllowance) {
        this.recommendedDietaryAllowance = recommendedDietaryAllowance;
    }
    
    public void setMonographs(Set<Monograph> monographs) {
		this.monographs = monographs;
	}
    
    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setLongStatementE(String newVal) {
        longStatementE = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setLongStatementF(String newVal) {
        longStatementF = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setShortStatementE(String newVal) {
        shortStatementE = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setShortStatementF(String newVal) {
        shortStatementF = newVal;
    }

    /*
     * equality check added for(non-Javadoc)
     * @see ca.gc.hc.nhpd.model.PersistentObject#equals(java.lang.Object)
     *
    public boolean equals(Object obj) {
    	MonographUse passedUse = (MonographUse)obj;
    	return this.getId().equals(passedUse.getId());
    }
    */
    
    /**
     * Compares this object with the specified object for equality and for
     * order. Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     *
     * <p>1. Move all Monograph Uses with to bottom of list when comparing.
     * <p>2. Then sort on the use name.
     *
     * @param   o  the object to compare this to.
     *
     * @return  a comparator flag qualifying equality and order
     *
     * @throws  ClassCastException  when the object provided is of the wrong
     *                              type
     */
   public int compareTo(Object o) throws ClassCastException {

        // Drop out if either side is null or not available.
        if (this.equals(o)) {
            return 0;
        }

        if (getId() == null) {
            return 1;
        }

        if ((o == null) || (((MonographUse) o).getId() == null)) {
            return -1;
        }

        
        if ((getDependencyAsString() != null) 
            && (((MonographUse) o).getDependencyAsString() == null)) {
                return 1;
            }
        if ((getDependencyAsString() == null) 
            && (((MonographUse) o).getDependencyAsString() != null)) {
                return -1;
            }
        
       /* 
        * Special case for sorting:  If the Dependency is a Dose, it will be prefixed by 
        * "For products providing n-n (units)... etc, where n-n are the min-max doses. 
        * We need to sort by the min-max doses.  TBD - this might be better achieved
        * by ordering by the contained MonographDoseCombination dependancy
        */
        String dependencyTitle = getDependencyTitleAsString();
        String otherDependencyTitle = ((MonographUse) o).getDependencyTitleAsString();
        if (hasDoseDependency(dependencyTitle,otherDependencyTitle)) {
        	int result = compareDoseDependencies(dependencyTitle,otherDependencyTitle);
        	if (result != 0) return result;
        }
        
        
        // Sort on the DependentObject.getDependencyTitleAsString.  (ROA or MonoUses)
        if ((this.getDependencyTitleAsString() != null)
                && (((MonographUse) o).getDependencyTitleAsString() != null)
                && !this.getDependencyTitleAsString().equals(
                    ((MonographUse) o).getDependencyTitleAsString())) {
            //return this.getDependencyTitleAsString().compareTo(((MonographUse) o)
            //        .getDependencyTitleAsString());
            return StringComparator.compare(getDependencyTitleAsString(),
                    ((MonographUse) o).getDependencyTitleAsString());
        }            
            
        // Sort on the DependentObject.getDependencyAsString.  (ROA or MonoUses)
        if ((this.getDependencyAsString() != null)
                && (((MonographUse) o).getDependencyAsString() != null)
                && !this.getDependencyAsString().equals(
                    ((MonographUse) o).getDependencyAsString())) {
            //return this.getDependencyAsString().compareTo(((DependantObject) o)
            //        .getDependencyAsString());
            return StringComparator.compare(getDependencyTitleAsString(),
                    ((DependantObject) o).getDependencyTitleAsString());
        }

        // Sort on the supplied sort order.
        if ((this.getSortOrder() != null)
            && (((MonographUse) o).getSortOrder() != null)
            && !this.getSortOrder().equals(((MonographUse)o).getSortOrder())) {
                return this.getSortOrder().compareTo(((MonographUse)o).getSortOrder());
        }
            
        // Sort on the Long Statement.
        //return getLongStatement().compareTo(((MonographUse) o).getLongStatement());
        return StringComparator.compare(getLongStatement(),
                ((MonographUse)o).getLongStatement());
          
    }
   
    private boolean hasDoseDependency(String dependencyTitle,
    								  String otherDependencyTitle) {
    	
    	//log("hasDoseDependency: ");
    	//log("       Title: " + dependencyTitle);
    	//log("  OtherTitle: " + otherDependencyTitle);
    	
    	if (dependencyTitle != null &&
    	    (dependencyTitle.startsWith(MonographDose.INCLUDING_E) ||
    		 dependencyTitle.startsWith(MonographDose.INCLUDING_F))) {
    		return false;
    	}	
    	if (otherDependencyTitle != null &&
    	    (otherDependencyTitle.startsWith(MonographDose.INCLUDING_E) ||
    		 otherDependencyTitle.startsWith(MonographDose.INCLUDING_F))) {
        	return false;
    	}
    	return (dependencyTitle != null && otherDependencyTitle != null	&&
    			(dependencyTitle.startsWith(MonographDose.PRODUCTS_PROVIDING_E) ||
    			 dependencyTitle.startsWith(MonographDose.PRODUCTS_PROVIDING_F)) &&
    			(otherDependencyTitle.startsWith(MonographDose.PRODUCTS_PROVIDING_E) ||
    	    	 otherDependencyTitle.startsWith(MonographDose.PRODUCTS_PROVIDING_F)));
    }
    	
      
    /*
     * 1. Sort by first dose 
     * 2. if second dose appears, sort by that also.
     * 
     * 100
     * 100-200
     * 100-300
     * 200
     * 200-1000
     */
    private int compareDoseDependencies(String doseDependency, 
    									String otherDoseDependency) {
    	String thisTitle = null;
    	String otherTitle = null;
    	if (isLanguageFrench()) {
    		thisTitle = doseDependency.replaceFirst(MonographDose.PRODUCTS_PROVIDING_F,"");
    		otherTitle = otherDoseDependency.replaceFirst(MonographDose.PRODUCTS_PROVIDING_F,"");
    	}
    	else {
    		thisTitle = doseDependency.replaceFirst(MonographDose.PRODUCTS_PROVIDING_E,"");
    		otherTitle = otherDoseDependency.replaceFirst(MonographDose.PRODUCTS_PROVIDING_E,"");
    	}
    	
    	//log("CompareDDs: ThisTitle: " + thisTitle );
    	//log("CompareDDs: OtherTitle " + otherTitle );
    	
    	
    	long thisMinDose = 0l;
    	long thisMaxDose = 0l;  
    	long otherMinDose = 0l;
    	long otherMaxDose = 0l;
    	String dosesString = null;
    	String[] doses = null; 
    	
    	int thisFirstSpace = thisTitle.indexOf(" ");
    	if (thisFirstSpace != -1) {
    		dosesString = thisTitle.substring(0, thisFirstSpace);
    		//log("CompareDDs: This dosesString: " + dosesString); 
    		if (dosesString.indexOf("-") != -1) {   
    			doses = dosesString.split("-");
    			thisMinDose = new Long(doses[0]).longValue(); 
    			thisMaxDose = new Long(doses[1]).longValue();
    		}
    		else {
    			if (dosesString.length() > 0) {
    				thisMinDose = new Long(dosesString).longValue();
    			}
    		}
    	}
    	
    	int otherFirstSpace = otherTitle.indexOf(" ");
    	if (otherFirstSpace != -1) {
    		dosesString = otherTitle.substring(0, otherFirstSpace);
    		//log("CompareDDs: Other dosesString: " + dosesString); 
    		if (dosesString.indexOf("-") != -1) {
    			doses = dosesString.split("-");
    			otherMinDose = new Long(doses[0]).longValue(); 
    			otherMaxDose = new Long(doses[1]).longValue(); 
    		}
    		else
    			if (dosesString.length() > 0) {
    				otherMinDose = new Long(dosesString).longValue();
    			}
    	}
    	
    	//-1 = less than
    	if (thisMinDose < otherMinDose)
    		return -1;
    	if (thisMinDose > otherMinDose)
    		return 1;
    	
    	//min doses are equal, so check max doses
    	if (thisMaxDose < otherMaxDose)
    		return -1;
    	if (thisMaxDose > otherMaxDose)
    		return 1;
    	
    	return 0;
    	
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
       
        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", use: ");
        buffer.append(", useType: ");
        if (getUseType() != null){
            buffer.append(getUseType().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", shortStatementE: ");
        buffer.append(getShortStatementE());
        buffer.append(", longStatementE: ");
        buffer.append(getLongStatementE());
        buffer.append(", sortOrder: ");
        buffer.append(getSortOrder());
          
        return buffer.toString();
    }

    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using the sort id.  This is used to sort the Monograph Duration information
     * by the Sort Order defined in the Monograph Use.
     */
    public static class SortOrderComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null 
                || ((MonographUse)o1).getSortOrder() == null) {
                return 1;
            }
            if (o2 == null
                || ((MonographUse)o2).getSortOrder() == null) {
                return -1;
            }
            
            // Return the compare.
            return ((MonographUse)o1).getSortOrder().compareTo(
                    ((MonographUse)o2).getSortOrder());
        }
    }
    
    
    private void log (String msg) {
    	LOG.debug(msg);
    }
    
}
