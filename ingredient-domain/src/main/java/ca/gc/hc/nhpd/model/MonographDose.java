package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/*******************************************************************************
 * The MonographDose defines the quantity and frequency of an ingredient 
 * according to the Monograph. Sometimes it may have a related 
 * MonographSubIngredientDose, when the dose of that subIngredient is also
 * specifically defined in the Monograph.
 */

public class MonographDose extends DependantObject implements Comparable {
	private static final String CHAR_DASH = "-";
	private static final String COMMA_SPACE = ", ";
	private static final String CHAR_SPACE = " ";
	private static final String FREQUENCY_QUALIFIER = "x/";
	private static final String CHAR_Y = "Y";
	private static final String CHAR_N = "N";
	private static final String SEMI_SPACE = "; ";
	private static final String WORD_TIME = "time";
	private static final String WORD_TIMES = "times";
	private static final String WORD_FOIS = "fois";
	private static final String WORD_PAR = "par";
	private static final String WORD_PER = "per";
	private static final String WORD_UNE = "une";
	private static final String NUMBER_ONE = "1";
	private static final String SINGLE_SPACE = " ";
	private static final String EMPTY_STRING = "";
	public  static final String PRODUCTS_PROVIDING_E = "For products providing ";
	public  static final String PRODUCTS_PROVIDING_F = "Pour les produits fournissant ";
	public  static final String INCLUDING_E = " including at least ";
	public  static final String INCLUDING_F = " y compris au moins ";

	private static final Logger LOG = Logger.getLogger(MonographDose.class);
	private static DecimalFormat twoPlaces = new DecimalFormat("0.####");

	private String code;
	private String additionalDetailsE;
	private String additionalDetailsF;
	private Float doseMaximum;
	private Float doseMinimum;
	private Units doseUnits;
	private int frequencyMaximum;
	private int frequencyMinimum;
	private Units frequencyUnits;
	private Set<MonographDirectionForUse> monographDirectionsForUse;
	private MonographDoseCombination monographDoseCombination;   //parent MDC
	private Set<MonographSubIngredientDoseCombination> monographSubIngredientDoseCombinations;    
	private RestrictionType restrictionType;
	private SubPopulation subPopulation;
	private boolean visible;

    private Units defaultDoseUnits;
    
	//~ public -----------------------------------------------------------------

	/**
	 * Gets the code.  Used in the loading of Monographs.
	 * @return  the code.
	 *
	 * @see     #setCode()
	 */
	public String getCode() {
		return code;
	}

	/***************************************************************************
	 * Over-ridden since this attribute is not used by this subclass.
	 */
	/*
    @Override
    public MonographDose getMonographDose() {
        //LOG.error("MonographDose.getMonographDose() should never be called.");
        return null;
    }
	 */
	/***************************************************************************
	 * Over-ridden since this attribute is not used by this subclass.
	 */
	/*
    @Override
    public void setMonographDose(MonographDose newVal) {
        //LOG.error("MonographDose.getMonographDose() should never be called.");
    }
	 */
	/**
	 * Sets the code.  Used in the loading of Monographs.
	 *
	 * @param  newValue  code.
	 */
	public void setCode(String newValue) {
		code = newValue;
	}

	/**
	 * Compares this object with the specified object for equality and for
	 * order. Returns a negative integer, zero, or a positive integer as this
	 * object is less than, equal to, or greater than the specified object.
	 *
	 * @param   o  the object to compare this to.
	 *
	 * @return  a comparator flag qualifying equality and order
	 *
	 * @throws  ClassCastException  when the object provided is of the wrong
	 *                              type
	 */
	public int compareTo(Object o) throws ClassCastException {

		try {

			// Drop out if either side is null or not available.
			if (this.equals(o)) {
				return 0;
			}

			if (getId() == null) {
				return 1;
			}

			if ((o == null) || (((MonographDose) o).getId() == null)) {
				return -1;
			}

			// Sort on the subPopulation First.
			if ((this.getSubPopulation() != null)
					&& (((MonographDose) o).getSubPopulation() != null)
					&& !this.getSubPopulation().equals(
							((MonographDose) o).getSubPopulation())) {
				return this.getSubPopulation().compareTo(
						((MonographDose) o).getSubPopulation());
			}

			// Sort on the DependentObject.getDependencyTitleAsString.  (ROA or MonoUses)
			// Move empty getDependencyTitleAsString to the top of the lists.
			if (this.getDependencyTitleAsString() != null  &&
					(((MonographDose) o).getDependencyTitleAsString()) == null) {
				return 1;
			}

			if (this.getDependencyTitleAsString() == null  &&
					(((MonographDose) o).getDependencyTitleAsString()) != null) {
				return -1;
			}

			if ( (this.getDependencyTitleAsString() != null) &&
					(((MonographDose) o).getDependencyTitleAsString() != null) &&
					!this.getDependencyTitleAsString().equals(
							((MonographDose) o).getDependencyTitleAsString())) {
				//return this.getDependencyTitleAsString().compareTo(
						//    ((MonographDose) o).getDependencyTitleAsString());
				return StringComparator.compare(getDependencyTitleAsString(),
						((MonographDose) o).getDependencyTitleAsString());
			}

			// Sort on the DependentObject.getDependencyAsString.  (ROA or MonoUses)
			if ((this.getDependencyAsString() != null) &&
					(((MonographDose) o).getDependencyAsString() != null)  &&
					!this.getDependencyAsString().equals(
							((MonographDose) o).getDependencyAsString())) {
				//return this.getDependencyAsString().compareTo(
				//    ((MonographDose) o).getDependencyAsString());
				return StringComparator.compare(getDependencyAsString(),
						((MonographDose) o).getDependencyAsString());
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		// Sort on the id.
		return getId().compareTo(((MonographDose) o).getId());

	}

	/**
	 * Gets the AdditionalDetails in the language appropriate for the Locale.
	 *
	 * <p>Additional Details helps qualify non-standard dosage information. For
	 * example: 3 on the first day, 2 on the second day and 1 on the third day.
	 *
	 * @return  the locale-specific AdditionalDetails.
	 */
	public String getAdditionalDetails() {

		if (isLanguageFrench()) {
			return getAdditionalDetailsF();
		}

		return getAdditionalDetailsE();
	}

	/**
	 * Getter for the additional English details we may want to add to qualify
	 * this MonographDose object.
	 *
	 * @return  the additional details in English.
	 *
	 * @see     #setAdditonalDetailsE()
	 */
	public String getAdditionalDetailsE() {
		return additionalDetailsE;
	}

	/**
	 * Getter for the additional French details we may want to add to qualify
	 * this MonographDose object.
	 *
	 * @return  the additional details in French.
	 *
	 * @see     #setAdditonalDetailsF()
	 */
	public String getAdditionalDetailsF() {
		return additionalDetailsF;
	}

	/**
	 * Returns the MonographDirectionsForUse
	 *
	 * @return  a set of MonographDirectionsForUse
	 */
	public Set<MonographDirectionForUse> getMonographDirectionsForUse() {
		return monographDirectionsForUse; 
	}

	/**
	 * Helper method that returns this MonographDose class as a single string.
	 * This is helpfull as it reduces the number of formulas required in the JSP
	 * and helps in the grouping of values.
	 *
	 * @return  a String representing this class for grouping purposes
	 * TODO - clean up: this does not work in a multi-dependency world.
	 */
	public String getDependencyAsString() {

		if (getOtherDependency() != null) {
			//log("MD: returning *other* dependency");
			return getOtherDependency();
		}

		if (getMonographSourceOrganismParts() != null && 
				getMonographSourceOrganismParts().size() != 0) {
			StringBuffer sb = new StringBuffer();
			for (MonographSourceOrganismPart organismPart : getMonographSourceOrganismParts()) {
				if ( sb.length() != 0 ) {
					sb.append( COMMA_SPACE );
				}
				//log("MD: getDependencyAsString appending: " + 
				//		organismPart.getOrganismPart().getType().getName());
				sb.append( organismPart.getOrganismPart().getType().getName() );              
			}
			return sb.toString();
		}

		return null;
	}

	/**
	 * Helper method that returns this Dependency class as a title string to be
	 * used by the display. Overrides the default provided by the
	 * DependantObject.
	 *
	 * @return  a String representing this class for grouping purposes
	 */
	public String getDependencyTitleAsString() {
		StringBuffer monographUsesStringBuffer = new StringBuffer();

		if (getMonographUses() != null && getMonographUses().size() != 0) {
			if (isHasSubIngredientCombinations()) {
				monographUsesStringBuffer.append(getDosesPrefix());
			}

			for (MonographUse monographUse : getMonographUses()) {
				if (monographUsesStringBuffer.length() != 0) {
					monographUsesStringBuffer.append(SEMI_SPACE);
				}
				//MR Added for Fish Oil	    
				if (isHasSubIngredientCombinations()) {
					monographUsesStringBuffer.append
					(getSubIngredientDoseCombinationsForDisplay());
				}
				else
					monographUsesStringBuffer.append(
							monographUse.getShortStatement());
			}
		}

		if (monographUsesStringBuffer.length() != 0) {
			return monographUsesStringBuffer.toString();
		}

		return "";
	}

	/*
	 * replaces getDependencyTitleAsString.  This is in DependentObject now...
	 */
	/*
    public String getUseDependencyTitle() {
        StringBuffer monographUsesStringBuffer = new StringBuffer();

        if (getMonographUses() != null && getMonographUses().size() != 0) {  	
            for (MonographUse monographUse : getMonographUses()) {
                log("getUseDependencyTitle: monographUse: " + monographUse.getCode());
            	if (monographUsesStringBuffer.length() != 0) {
                    monographUsesStringBuffer.append(SEMI_SPACE);
            	}
                monographUsesStringBuffer.append(monographUse.getShortStatement());
            }
	        if (monographUsesStringBuffer.length() != 0) {
	        	 log("monographUsesStringBuffer: " + monographUsesStringBuffer.toString());
	        	return monographUsesStringBuffer.toString();
	        }
        }

        return "";
    }
	 */

	/**
	 * For Display Purposes, returns the appropriate categorization title. This
	 * field provides the second level of categorization for the Monograph Dose.
	 * <br/>
	 * If there are MonographUses, it calls getDependencyAsString(). Otherwise
	 * it returns null.
	 *
	 * @return  Second level categorization title.
	 */
	public String getDisplayTitle() {
		if (getMonographUses() != null) {
			return getDependencyAsString();
		}

		return null;
	}

	/**
	 * Getter for the maximum dose defined by this MonographDose object. The
	 * maximum dose should be less than the maximum dose defined for the
	 * ingredient in this Monograph.
	 *
	 * @return  the maximum dose defined by this MonographDose object.
	 *
	 * @see     #setDoseMaximum()
	 */
	public Float getDoseMaximum() {
		return doseMaximum;
	}

	/**
	 * Getter for the minimum dose defined by this MonographDose object. The
	 * minimum dose should be less than the maximum dose defined for the
	 * ingredient in this Monograph.
	 *
	 * @return  the minimum dose defined by this MonographDose object.
	 *
	 * @see     #setDoseMinimum()
	 */
	public Float getDoseMinimum() {
		return doseMinimum;
	}

	/**
	 * Getter for the units defining the minimum and maximum dose in this
	 * MonographDose object. By default, the dosage units should perhaps be
	 * fetched from the Ingredient restriction.
	 *
	 * @return  the dose units qualifying the minimum and maximum dose values
	 *          found in this MonographDose object.
	 *
	 * @see     #setDoseUnits()
	 */
	public Units getDoseUnits() {
		return doseUnits;
	}
    
    /**
     * Getter that returns the default dose units.  This dose unit is returned
     * by the web services when the normal getDoseUnits() returns a null value.
     * This is in response to the IMSD #2055.  Based on e-mails from Denis, 
     * the default dose unit is Grams.
     * 
     * TODO - This needs to be refactored as we have more information about 
     * these default units.  It's a temporary measure until we can better
     * understand and implement this information at the database tier.
     * 
     * TODO - After a discussion with Dwight, it was determined that this is
     * a temporary measure until we can have access to modify the data model in
     * a future version of this application.  Also, based on a conversation with
     * Denis, we may be able to persist this information in the existing 
     * DoseUnit column.  More testing is required to determin the impact of this
     * change on the web application and web services.  (Future Version)
     * 
     * @return the Default Dose Units used when the getDoseUnits returns null. 
     */
    public Units getDefaultDoseUnits() {
        
        if (defaultDoseUnits == null) {
            instantiateDefaultDoseUnits();
        }
        return defaultDoseUnits;
    }

    
    /**
     * Used by the getDefaultDoseUnits, this method instantiates the 
     * default dose units so that this information can be returned.
     * Since the unit id is not exposed throught the web services,
     * and since the unit id can change from one load to another,
     * this information was not hard coded with the unit information.
     * 
     * Also, the unit comparator works on the name so this units should
     * work well in existing lists.
     */
    private void instantiateDefaultDoseUnits() {
     
        if (defaultDoseUnits == null) {
            UnitsType unitsType = new UnitsType();
            unitsType.setNameE("Mass");
            unitsType.setNameF("Masse");
            defaultDoseUnits = new Units();
            defaultDoseUnits.setCode("g");
            defaultDoseUnits.setNameE("Grams");
            defaultDoseUnits.setNameF("Grammes");
            defaultDoseUnits.setType(unitsType);
        }
        
    }
    
	/*
    public boolean restrictionsOnly() {
    	return (frequencyMinimum == 0 && 
    			frequencyMaximum == 0 && 
    			(restrictionType != null && restrictionType.getCode() != null));
    }
	 */

	public boolean isSingleDose() {
		return (restrictionType != null && 
				restrictionType.getCode().equals(RestrictionType.SINGLE_DOSE));
	}

	public boolean isDailyDose() {
		return (restrictionType != null && 
				restrictionType.getCode().equals(RestrictionType.DAILY_DOSE));
	}


	public String getRestrictionForDisplay() {

		if  (restrictionType.getCode().equals(RestrictionType.SINGLE_DOSE)) {
			if (isLanguageFrench())
				return RestrictionType.SINGLE_DOSE_TEXT_F;
			return RestrictionType.SINGLE_DOSE_TEXT_E;
		}
		if  (restrictionType.getCode().equals(RestrictionType.DAILY_DOSE)) { 
			if (isLanguageFrench())
				return RestrictionType.DAILY_DOSE_TEXT_F;
			return RestrictionType.DAILY_DOSE_TEXT_E;
		}

		return "";

	}



	/**
	 * Builds the display portion of the Frequency.  On the fence as to where to put
	 * it since we are building a sentence, and I dont want to create a new class 
	 * for this purpose only. 
	 * @return frequency as a formated text string.
	 */
	//TODO This should be moved out to another class
	public String getFrequencyForDisplay() {

		// return if we have no frequency.
		if ((getFrequencyMinimum() == 0) && (getFrequencyMaximum() == 0)) {
			return null;
		}

		// determine the Frequency string.
		String frequency = EMPTY_STRING;
		if ((frequencyMinimum != 0)
				&& (frequencyMaximum != 0)
				&& (frequencyMinimum != frequencyMaximum)) {
			frequency = frequencyMinimum + CHAR_DASH + frequencyMaximum;
		} else if (
				(frequencyMinimum != 0)
				&& (frequencyMinimum != frequencyMaximum) ) {
			frequency = new Integer(frequencyMinimum).toString();
		} else if (frequencyMaximum != 0) {
			frequency = new Integer(frequencyMaximum).toString();
		}

		StringBuffer output = new StringBuffer();

		if ((restrictionType != null) && 
				(restrictionType.getCode() != null) &&
				(restrictionType.getCode().equals(RestrictionType.SINGLE_DOSE))) {
			// Single Dose display.
			// output.append("***");

			// When displaying in French and displaying the number one, display the text.
			if (isLanguageFrench() && NUMBER_ONE.equals(frequency)) {
				output.append(WORD_UNE);
			} else {
				output.append(frequency);
			}

			// Add a space.
			output.append(SINGLE_SPACE);

			// When displaying in French use "fois".  When in English and frequency is one
			// use the singular time, otherwise use times.
			if (isLanguageFrench()) {
				output.append(WORD_FOIS);
			} else if (NUMBER_ONE.equals(frequency)) {
				output.append(WORD_TIME);
			} else {
				output.append(WORD_TIMES);
			}

			// Add a space
			output.append(SINGLE_SPACE);

			// In French use par, in English use per.
			if (isLanguageFrench()) {
				output.append(WORD_PAR);
			} else {
				output.append(WORD_PER);
			}

			// Add a space
			output.append(SINGLE_SPACE);

			// Add the frequency
			output.append(frequencyUnits.getName().toLowerCase());

		} else {
			// All other display.

			// Display the frequency        
			output.append(frequency);

			// Display the frequency Units

			if (getFrequencyUnits() != null) {
				output.append(
						FREQUENCY_QUALIFIER
						+ frequencyUnits.getName().toLowerCase());
			}

		}

		return output.toString();

	}

	/**
	 * Getter for the maximum frequency defined by this MonographDose object.
	 * The maximum frequency helps qualify the period in which the dose can be
	 * taken.
	 *
	 * <p>For example, dose X can be taken from 3 to 5 times per day. In this
	 * case, the 5 would represent the maximum frequency.
	 *
	 * @return  the maximum frequency defined by this MonographDose object.
	 *
	 * @see     #setFrequencyMaximum()
	 */
	public int getFrequencyMaximum() {
		return frequencyMaximum;
	}

	/**
	 * Getter for the minimum frequency defined by this MonographDose object.
	 * The minimum frequency helps qualify the period in which the dose can be
	 * taken.
	 *
	 * <p>For example, dose X can be taken from 3 to 5 times per day. In this
	 * case, the 3 would represent the minimum frequency.
	 *
	 * @return  the minimum frequency defined by this MonographDose object.
	 *
	 * @see     #setFrequencyMinimum()
	 */
	public int getFrequencyMinimum() {
		return frequencyMinimum;
	}

	/**
	 * Getter for the frequency units defining the minimum and maximum frequency
	 * in this MonographDose object. In the future, we should perhaps implement
	 * a validation that would ensure that the frequency and dose information
	 * would fall within the restriction available for any given ingredient.
	 *
	 * <p>For example, dose X can be taken from 3 to 5 times per day. In this
	 * case, the "day" would represent the qualifying units.
	 *
	 * @return  the frequency units qualifying the minimum and maximum frequency
	 *          values found in this MonographDose object.
	 *
	 * @see     #setFrequencyUnits()
	 */
	public Units getFrequencyUnits() {
		return frequencyUnits;
	}

	/**
	 * Gets the dose combination that this is in (if any). Unless part of a
	 * dependency, these are collected into MonographDoseCombinations. This
	 * is for Hibernate and private use.
	 * @return the dose combination that this is in (if any).
	 * @see #setMonographDoseCombination()
	 */
	public MonographDoseCombination getMonographDoseCombination() {
		return monographDoseCombination;
	}

	/**
	 * Gets the MonographSubIngredientDose.  These doses add additional 
	 * qualifiers to the Monograph Dose object by detailing specific
	 * dose information available for each SubIngredient associated to this
	 * Monograph Ingredient.
	 *
	 * @return  the set of MonographSubIngredientDose for this Monograph Dose
	 *          object.
	 *
	 * @see     #setMonographSharedSubIngredientDoses()
	 */
	public Set<MonographSubIngredientDoseCombination> getMonographSubIngredientDoseCombinations() {
		return monographSubIngredientDoseCombinations;
	}

	//used by monographPhases JSP
	public String getDoseForDisplay() {

		if (restrictionType != null && 
				restrictionType.getCode() != null &&
				restrictionType.getCode().equals(RestrictionType.SINGLE_DOSE))
			return getSingleDoseForDisplay(); 

		if (isLanguageFrench()) 
			return getQuantityForDisplay() + "/jour";

		return getQuantityForDisplay() + "/day";   

	}

	public boolean isHasSubIngredientDoseCombinations() {	
		/*
    	if (monographSubIngredientDoseCombinations != null) {
			log ("MonographDose with Code: " + code + " has " + 
					monographSubIngredientDoseCombinations.size() + " MSIDCs");
    	}
		 */
		return (monographSubIngredientDoseCombinations != null &&
				monographSubIngredientDoseCombinations.size() > 0);
	}

	public boolean isHasSubIngredientDoses() {
		//log("isHasSubIngredientDoses:    MonographDose Code: " + code );

		if (monographSubIngredientDoseCombinations != null) {
			//log(" have " + monographSubIngredientDoseCombinations.size() + " MSIDCs ");
			for (MonographSubIngredientDoseCombination msidc: monographSubIngredientDoseCombinations) {
				if ( msidc.getMonographSubIngredientDoses() != null &&
						msidc.getMonographSubIngredientDoses().size() > 0) {
					//log(" have " + msdic.getMonographSubIngredientDoses().size() + " MSIDs ");
					return true;
				}
			}
		}
		return false;

	}

	//this goes down into the MSICs which are wrapped by the MSIDs which 
	//are wrapped by the MSIDCs
	public boolean isHasSubIngredientCombinations() {

		//log("isHasSubIngredientCombinations:    MonographDose Code: " + code );

		if (monographSubIngredientDoseCombinations != null && 
				monographSubIngredientDoseCombinations.size() > 0) {
			//log(" have MSIDCs"); 
			for (MonographSubIngredientDoseCombination msdic: 
				monographSubIngredientDoseCombinations) {
				//log("    MSIDC code: " + msdic.getCode()); 
				if ( msdic.getMonographSubIngredientDoses() != null &&
						msdic.getMonographSubIngredientDoses().size() > 0) {
					//log("       have MSIDs"); 
					for (MonographSubIngredientDose msid: msdic.getMonographSubIngredientDoses() ) {
						//log("        MSID code: " + msid.getCode());
						if (msid.getSubIngredientCombination() != null) {
							//log("          have an MSIC!"); 
							return true;
						}	
					}
				}
			}
		}
		return false;
	}

	


	/*
	 * Called by callers of getSubIngredientDoseCombinationsForDisplay before
	 * iterating thru a collection of MSIDCs
	 */ 
	private String getDosesPrefix() {
		if (isLanguageFrench()) { 
			return PRODUCTS_PROVIDING_F;
		}
		else {	
			return PRODUCTS_PROVIDING_E;
		}
	}


	/*
	 * Returns a textual representation of a Set of 
	 * MonographSubingredientDoseCombination objects, including their contained
	 * MonographSubIngredientCombinations.
	 * These are suitable for display as Dependency titles.  Introduced for the
	 * Fish Oil monograph, which has Uses which are dependant on 
	 * MonographSubingredientDose/MonographSubingredientCombinations   
	 */
	public String getSubIngredientDoseCombinationsForDisplay() {
		//log("getSubIngredientDoseCombinationsForDisplay - MD Code: " + code );
		if (monographSubIngredientDoseCombinations == null || 
				monographSubIngredientDoseCombinations.size() <= 0) { 
			//log("MonographDose Id/Code:" + getId() + "/" + getCode() + " has no MSIDCs");
			return null;
		}

		//TODO - any requirement to provide abbreviations as in legacy Fish Oil monograph?
		// eg.  EPA DHA
		Float minDose = 0f;
		Float maxDose = 0f;
		Float minRatio = 0f;
		Float maxRatio = 0f;
		StringBuffer buffer = new StringBuffer();

		boolean firstSubDose = true;
		Set<MonographSubIngredientDose> msids = null;

		//only have single msidc for now...
		for (MonographSubIngredientDoseCombination msidc: monographSubIngredientDoseCombinations) {
			msids = msidc.getMonographSubIngredientDoses();
			if (msids == null || msids.size() <= 0) {
				//log ("TEST22:  Have no MSIDS");
				return null; 
			}

			//log ("TEST23:  Have " + msids.size() + " MSIDS");
			for (MonographSubIngredientDose msid: msids) {
				MonographSubIngredientCombination  msic = 
					msid.getSubIngredientCombination();
				//log("MSID: " + msid.getCode() + " MinDose: " + msid.getDoseMinimum() +
				//	" MaxDose: " + msid.getDoseMaximum()); 
				if (!firstSubDose) {
					if (isLanguageFrench()) 
						buffer.append(" et ");
					else
						buffer.append(" and ");
					firstSubDose = false;
				}

				minDose = msid.getDoseMinimum();
				maxDose = msid.getDoseMaximum();

				/* 
				 * NB.  the dose and dose units might not be provided here if we 
				 * are dealing with a "real" MSIC which only provides a 
				 * subingredient ratio  
				 */
				if (minDose != null || maxDose != null) {
					buffer.append(formatDose(msid.getDoseMinimum(),
							msid.getDoseMaximum()));
					Units doseUnits = msid.getDoseUnits();
					//log(" doseUnits.getName: " + doseUnits.getName());
					if (doseUnits != null && 
							doseUnits.getName() != null &&
							!doseUnits.getName().equals("")) {
						buffer.append(" " + msid.getDoseUnits().getName() + " ");
					}
					/*
					 * A MonographSubingredientDose should always have a 
					 * MonographSubIngredientCombination, which would contain the 
					 * SubIngredient even if its a single one.
					 */
					if (msic == null) {
						//log ("TEST22:  Null MSIC");
						return null;
					}

					//log ("TEST22: MSIC Id: " + msic.getId());
					//subingredient names - use the ingredient authorized name, 
					//since the subingredient name also contains org part.  
					buffer.append(msic.getPrimarySubIngredient().getIngredient().getAuthorizedName());
					if (msic.getSecondarySubIngredient() != null && 
							msic.getSecondarySubIngredient().getIngredient() != null && 
							msic.getSecondarySubIngredient().getIngredient().getAuthorizedName() != null) {
						buffer.append(" + ");
						buffer.append(msic.getSecondarySubIngredient().getIngredient().getAuthorizedName());
					}
				}

				//log ("Buffer: "  + buffer.toString());
				//Format min/max ratios, if provided
				minRatio = msic.getMinimumRatio();
				maxRatio = msic.getMaximumRatio();
				if ((minRatio != null && maxRatio != null) && 
						(minRatio.floatValue() != 0 || (maxRatio.floatValue() != 0))) {
					if (isLanguageFrench()) 
						buffer.append(" et contenant un ratio   ");
					else
						buffer.append(" and containing a ratio of ");

					buffer.append(msic.getPrimarySubIngredient().getIngredient().getAuthorizedName());
					buffer.append(":");
					buffer.append(msic.getSecondarySubIngredient().getIngredient().getAuthorizedName());

					if (isLanguageFrench()) 
						buffer.append(" entre ");
					else
						buffer.append(" between ");

					buffer.append(twoPlaces.format(minRatio));
					buffer.append(":1");
					if (isLanguageFrench()) 
						buffer.append(" et ");
					else
						buffer.append(" and ");
					buffer.append(twoPlaces.format(maxRatio));
					buffer.append(":1 ");
				}
				//else log ("No Ratios");
			}
		}

		return buffer.toString();

	}




	/*
	 * Builds a textual representation of a dose/subingredient dose 
	 */
	public String formatDose(Float minDose, Float maxDose) {

		if ((minDose == null || minDose.floatValue() == 0f) &&
				(maxDose == null || maxDose.floatValue() == 0f)) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		//both doses provided
		if ((minDose != null && minDose.floatValue() > 0f) &&
				(maxDose != null && maxDose.floatValue() > 0f)) {
			buffer.append(twoPlaces.format(minDose));
			buffer.append("-");
			buffer.append(twoPlaces.format(maxDose));
			//log("MonographDose: Both Doses: " + buffer.toString());
			return buffer.toString();
		}

		//only minimum dose provided = "including at least"
		if ((minDose != null && minDose.floatValue() > 0f) &&
				(maxDose == null || maxDose.floatValue() == 0f)) {
			if (isLanguageFrench()) 
				buffer.append(INCLUDING_F);
			else
				buffer.append(INCLUDING_E);
			buffer.append(twoPlaces.format(minDose));
			return buffer.toString();
		}


		//only maximum dose provided = "not to exceed"
		if (minDose == null || minDose.floatValue() == 0f &&
				(maxDose != null && maxDose >= 0f)) {
			if (isLanguageFrench()) 
				buffer.append(" ne pas dépasser ");
			else
				buffer.append(" not to exceed ");
			buffer.append(twoPlaces.format(maxDose));
			//log("MonographDose: Max Dose Only: " + buffer.toString());
		}

		//log("MonographDose: At END: " + buffer.toString());
		return buffer.toString();
	}


	/*
	 * formats the doses.  
	 */
	public String getQuantityForDisplay() {

		// Units must be present to display any value.  If not available, then
		// return a null.	
		if (getDoseUnits() == null) {
			//log("MonographDose Units is null!");
			return "";
		}

		StringBuffer output = new StringBuffer();

		if ((getDoseMinimum() != null)
				&& (getDoseMaximum() != null)
				&& getDoseMinimum().equals(getDoseMaximum())) {
			output.append(twoPlaces.format(getDoseMinimum()));
		} else if ((getDoseMinimum() != null) && (getDoseMaximum() != null)) {
			output.append(
					twoPlaces.format(getDoseMinimum())
					+ CHAR_SPACE + CHAR_DASH + CHAR_SPACE
					+ twoPlaces.format(getDoseMaximum()));
		} else if (getDoseMinimum() != null) {
			if (isLanguageFrench()) {
				output.append("Doses égales ou supérieures à ");
			}
			else {
				output.append("Doses greater than or equal to " );
			}
			output.append(twoPlaces.format(getDoseMinimum()));
		} else if (getDoseMaximum() != null) {
			if (isLanguageFrench()) {
				output.append(" ne pas dépasser ");
			}
			else {
				output.append(" not to exceed ");
			}
			output.append(twoPlaces.format(getDoseMaximum()));
		}

		// Dose Units
		if (getDoseUnits() != null) {
			output.append(" " + getDoseUnits().getName());
		}

		return output.toString();
	}


	//Used for Creatine monohydrate, caffeine, etc.
	public String getSingleDoseForDisplay() {

		StringBuffer output = new StringBuffer();

		if ((doseMinimum != null) && (doseMaximum != null) && 
				doseMinimum.equals(doseMaximum)) {
			if (isLanguageFrench())
				output.append(" Dose unique: " + twoPlaces.format(doseMinimum));
			else	
				output.append(" Single dose: " + twoPlaces.format(doseMinimum));
		}
		else
			if ((doseMinimum != null) && (doseMaximum != null)) {
				if (isLanguageFrench()) 
					output.append(" Dose unique: " + twoPlaces.format(doseMinimum)
							+ CHAR_DASH + twoPlaces.format(doseMaximum));
				else	
					output.append(" Single dose: " + twoPlaces.format(doseMinimum)
							+ CHAR_DASH + twoPlaces.format(doseMaximum));
			}
			else
				if ((doseMinimum == null || doseMinimum.floatValue() == 0f) && (doseMaximum != null)) {
					if (isLanguageFrench())
						output.append(" dose unique ne doit pas dépasser " + twoPlaces.format(doseMaximum));
					else
						output.append(" single dose not to exceed " + twoPlaces.format(doseMaximum));

				}
				else
					if (doseMinimum != null) {
						if (isLanguageFrench())
							output.append("Dose unique ne peut être moins que " + twoPlaces.format(doseMinimum));
						else
							output.append("Minimum Single dose " + twoPlaces.format(doseMinimum));
					}

		output.append(" " + doseUnits.getName());  //e.g. "Grams"

		return output.toString(); 

	}



	/**
	 * Getter that returns the RestrictionType information for this
	 * MonographDose. The RestrictionType is essentially a code table containing
	 * 4 records normalizing the restriction information.
	 *
	 * @return  the set of monograph dose type for this Monograph Dose.
	 *
	 * @see     #setRestrictionType()
	 */
	public RestrictionType getRestrictionType() {
		return restrictionType;
	}

	/**
	 * Getter that returns the target sub-population associated with this dose.
	 * The default value should be an 'adult'.
	 *
	 * @return  the set of SubPopulation for this Monograph Dose object.
	 *
	 * @see     #setSubPopulation()
	 */
	public SubPopulation getSubPopulation() {
		return subPopulation;
	}

	//TODO Refactor out
	public String getUnitsDisplay(boolean displayGreaterThanSign) {
		StringBuffer output = new StringBuffer();


		if (getDoseMinimum() != null
				&& getDoseMaximum() != null
				&& getDoseMinimum().equals(twoPlaces.format(getDoseMaximum()))) {
			output.append(twoPlaces.format(getDoseMinimum()));
		} else if ((getDoseMinimum() != null) && (getDoseMaximum() != null)) {
			output.append(twoPlaces.format(getDoseMinimum()) + CHAR_DASH + twoPlaces.format(getDoseMaximum()));
		} else if (twoPlaces.format(getDoseMinimum()) != null) {
			if(displayGreaterThanSign) {
				output.append("&#8805;" + CHAR_SPACE);
			}
			output.append(twoPlaces.format(getDoseMinimum()));
		} else if (getDoseMaximum() != null) {
			output.append(getDoseMaximum());
		}

		// Dose Units
		if (getDoseUnits() != null) {
			output.append(CHAR_SPACE + getDoseUnits().getCode());
		}

		return output.toString();
	}


	/**
	 * Display function that formats the appearance of the Minimum, Maximum and
	 * Units for the Monograph Dose.
	 *
	 * @return  the Monograph Dose Min, Max, and Units in a formatted string.
	 */
	//TODO Refactor out
	public String getValueForMonographDisplay() {
		StringBuffer output = new StringBuffer();

		// Minimum / Maximum Dose
		output.append(getUnitsDisplay(false));

		// Append a comma space.
		output.append(COMMA_SPACE);

		// Minimum / Maximum Frequency
		if ((getFrequencyMinimum() != 0)
				&& (getFrequencyMaximum() != 0)
				&& (getFrequencyMinimum() != getFrequencyMaximum())) {
			output.append(
					getFrequencyMinimum() + CHAR_DASH + getFrequencyMaximum());
		} else if (
				(getFrequencyMinimum() != 0)
				&& (getFrequencyMinimum() != getFrequencyMaximum())) {
			output.append(getFrequencyMinimum());
		} else if (getFrequencyMaximum() != 0) {
			output.append(getFrequencyMaximum());
		}

		// Frequency Units
		if (getFrequencyUnits() != null) {
			output.append(
					FREQUENCY_QUALIFIER
					+ getFrequencyUnits().getName().toLowerCase());
		}

		return output.toString();
	}

	/**
	 * Accessor for Hibernate use only! Returns a true (Y) false (N) value
	 * depending on the visibility factor for this MonographDose.
	 *
	 * @return  Hibarnate Only, returns a Y/N depending on the visibility of
	 *          this Monograph.
	 */
	public String getVisibleString() {
		if (visible) {
			return CHAR_Y;
		}
		return CHAR_N;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the additional English details for this Monograph Dose.
	 *
	 * @param  newVal  the additional English detail for this Monograph Dose.
	 *
	 * @see    #getAdditionalDetailsE()
	 */
	public void setAdditionalDetailsE(String newVal) {
		additionalDetailsE = newVal;
	}

	/**
	 * Sets the additional French details for this Monograph Dose.
	 *
	 * @param  newVal  the additional French detail for this Monograph Dose.
	 *
	 * @see    #getAdditionalDetailsF()
	 */
	public void setAdditionalDetailsF(String newVal) {
		additionalDetailsF = newVal;
	}

	/**
	 * Sets the maximum dose for this Monograph Dose.
	 *
	 * @param  newVal  the maximum dose for this Monograph Dose.
	 *
	 * @see    #getDoseMaximum()
	 */
	public void setDoseMaximum(Float newVal) {
		doseMaximum = newVal;
	}

	/**
	 * Sets the minimum dose for this Monograph Dose.
	 *
	 * @param  newVal  the minimum dose for this Monograph Dose.
	 *
	 * @see    #getDoseMinimum()
	 */
	public void setDoseMinimum(Float newVal) {
		doseMinimum = newVal;
	}

	/**
	 * Sets the dosage units qualifying the minimum and maximum dose for this
	 * Monograph Dose.
	 *
	 * @param  newVal  the dosage units that qualify the minimum and maximum
	 *                 dose.
	 *
	 * @see    #getDoseUnits()
	 */
	public void setDoseUnits(Units newVal) {
		doseUnits = newVal;
	}

	/**
	 * Sets the maximum frequency for this Monograph Dose.
	 *
	 * @param  newVal  the maximum frequency for this Monograph Dose.
	 *
	 * @see    #getFrequencyMaximum()
	 */
	public void setFrequencyMaximum(int newVal) {
		frequencyMaximum = newVal;
	}

	/**
	 * Sets the minimum frequency for this Monograph Dose.
	 *
	 * @param  newVal  the minimum frequency for this Monograph Dose.
	 *
	 * @see    #getFrequencyMinimum()
	 */
	public void setFrequencyMinimum(int newVal) {
		frequencyMinimum = newVal;
	}

	/**
	 * Sets the frequency units qualifying the minimum and maximum dose for this
	 * Monograph Dose.
	 *
	 * @param  newVal  the frequency units that qualify the minimum and maximum
	 *                 dose.
	 *
	 * @see    #getFrequencyUnits()
	 */
	public void setFrequencyUnits(Units newVal) {
		frequencyUnits = newVal;
	}

	/**
	 * Sets the set of MonographDirectionForUse for this Monograph dose.
	 *
	 * @param  newVal  the set of MonographDirectionForUse for this
	 *                 monograph dose type.
	 *
	 * @see    #getMonographDirectionsForUse()
	 */
	public void setMonographDirectionsForUse(Set<MonographDirectionForUse> newVal) {
		monographDirectionsForUse = newVal;
	}

	/**
	 * Sets the dose combination that this is in (if any). Unless part of a
	 * dependency, these are collected into MonographDoseCombinations. This
	 * is for Hibernate use during loading only.
	 * @param newVal the dose combination that this is in (if any).
	 * @see #getMonographDoseCombination()
	 */
	public void setMonographDoseCombination(MonographDoseCombination newVal) {
		monographDoseCombination = newVal;
	}

	/**
	 * Sets the set of "Distinct" monographSubIngredientDose for this Monograph dose.
	 *
	 * @param  newVal  the set of monograph sub-ingredient doses.
	 *
	 * @see    #getMonographDistinctSubIngredientDoses()
	 */
	public void setMonographSubIngredientDoseCombinations
	(Set<MonographSubIngredientDoseCombination> newVal) {
		monographSubIngredientDoseCombinations = newVal;
	}

	/**
	 * Sets the restrictionType for this Monograph dose.
	 *
	 * @param  newVal  the restriction type.
	 *
	 * @see    #getRestrictionType()
	 */
	public void setRestrictionType(RestrictionType newVal) {
		restrictionType = newVal;
	}

	/**
	 * Sets the sub-population information for this Monograph dose.
	 *
	 * @param  newVal  the sub-population being targetted by this dose.
	 *
	 * @see    #getSubPopulation()
	 */
	public void setSubPopulation(SubPopulation newVal) {
		subPopulation = newVal;
	}

	/**
	 * Setter to use in indicating the visibility of this MonographDose.
	 *
	 * @param  value  indicating if this MonographDose should be visible.
	 *
	 * @see    isVisible(), getVisibleString(), setVisibleString()
	 */
	public void setVisible(boolean value) {
		visible = value;
	}

	/**
	 * Accessor for Hibernate use only.
	 *
	 * @param  value  'Y'/'N' indicating if this Monograph Dose should be
	 *                visible.
	 *
	 * @see    isVisible(), setVisible(), getVisibleString()
	 */
	public void setVisibleString(String value) {
		visible = (CHAR_Y.equals(value));
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
		buffer.append(", doseMaximum: ");
		buffer.append(getDoseMaximum());
		buffer.append(", doseMinimum: ");
		buffer.append(getDoseMinimum());
		buffer.append(", doseUnits: ");
		if (getDoseUnits() != null){
			buffer.append(getDoseUnits().getName());
		} else {
			buffer.append("null");
		}
		buffer.append(", frequencyMaximum: ");
		buffer.append(getFrequencyMaximum());
		buffer.append(", frequencyMinimum: ");
		buffer.append(getFrequencyMinimum());
		buffer.append(", frequencyUnits: ");
		if (getFrequencyUnits() != null){
			buffer.append(getFrequencyUnits().getName());
		} else {
			buffer.append("null");
		}
		buffer.append(", restrictionType: ");
		if (getRestrictionType() != null){
			buffer.append(getRestrictionType().getName());
		} else {
			buffer.append("null");
		}
		buffer.append(", subPopulation: ");
		if (getSubPopulation() != null){
			buffer.append(getSubPopulation().getName());
		} else {
			buffer.append("null");
		}
		buffer.append(", additionalDetailsE: ");
		buffer.append(getAdditionalDetailsE());
		buffer.append(", visible: ");
		buffer.append(isVisible());
		buffer.append(", monographDirectionsForUse: ");
		buffer.append(getMonographDirectionsForUse()); 
		buffer.append(", monographSubIngredientDoseCombinations: ");
		//buffer.append(getMonographSubIngredientDoseCombinations());

		return buffer.toString();
	}

}
