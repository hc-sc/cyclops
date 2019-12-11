package ca.gc.hc.nhpd.model;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * MonographDoseCombination contains a collection of MonographDoses.  
 * When multiple MonographDoses are contained in this object they are used with
 * "logical and" semantics, which is to say that they must be used as a 
 * combination.  When the collection contains a single MonographDose,
 * "logical or" semantics are applied across the collection of 
 * MonographDoseCombination objects (contained by the MonographEntry); 
 * that is, any of the listed doses may be used in isolation. 
 */
public class MonographDoseCombination extends DependantObject
implements Comparable {
    
    private static final Log log = LogFactory.getLog(MonographDoseCombination.class);
    
	private String code;

	private boolean visible;

	private Set<MonographDose> monographDoses;
	//parent MonographEntries
	private Set<MonographEntry> monographEntries;

	public  static final String INCLUDING_E = " including at least ";
	public  static final String INCLUDING_F = " y compris au moins ";
	private static DecimalFormat twoPlaces = new DecimalFormat("0.####");

	private boolean directionsDependencyTitleSet = false;
	
	
	private boolean dependenciesLoaded = false;
	
    public boolean isDependenciesLoaded() {
		return dependenciesLoaded;
	}

	public void setDependenciesLoaded(boolean dependenciesLoaded) {
		this.dependenciesLoaded = dependenciesLoaded;
	}
	/**
	 * Gets the set of MonographDoses that belong to this combination.
	 * @return the set of MonographDoses in this combination.
	 * @see setMonographDoses()
	 */
	public Set<MonographDose> getMonographDoses() {
		return monographDoses;
	}

	/**
	 * Sets the set of MonographDoses that belong to this combination.
	 * @param newVal the set of MonographDoses in this combination.
	 * @see getMonographDoses()
	 */
	public void setMonographDoses(Set<MonographDose> newVal) {
		monographDoses = newVal;
	}

	/**
	 * Accessors for Hibernate. Only used in the loading of MonographEntries.
	 *
	 * @param  newValue
	 */
	public void setMonographEntries(Set<MonographEntry> newValue) {
		monographEntries = newValue;
	}

	/**
	 * Returns the parent (containing) monographEntries. Normally, this
	 * accessor should return a single parent monographEntry. In odd cases, some
	 * MonographEntry fields are shared. Only used in the loading of
	 * MonographEntries.
	 *
	 * @return  an accessor to the parent monographEntries.
	 */
	public Set<MonographEntry> getMonographEntries() {
		return monographEntries;
	}


	/**
	 * Overrides the method from DependantObject in order to accomodate cases where 
	 * the contained MonographDose (rather than this MDC) has a Use dependency title.
	 * This allows us to group both MDCs and MDs by Use
	 * @return
	 */
	@Override
	public String getUsesDependencyTitle() {

		if (getMonographUses() == null ||
				getMonographUses().size() == 0 ) {
			return getMonographDoseUseTitle();
		}

		String useTitle = null;
		StringBuffer sb = new StringBuffer();         
		for (MonographUse monoUse : getMonographUses()) {
			if ( sb.length() != 0 ) {
				sb.append( SEMI_SPACE );
			}
			useTitle = monoUse.getShortStatement();
			if (useTitle != null) {
				sb.append(useTitle);
				//log ("getUsesDepTitle CP 3: " + sb.toString());
			}
		} 

		return sb.toString();
	}

	/**
	 * Overrides the method from DependantObject in order to accomodate cases where 
	 * the contained MonographDose (rather than this MDC) has an MSOP dependency title.
	 * This allows us to group both MDCs and MDs by MSOP
	 * @return
	 */
	@Override
	public String getSourceOrgPartsDependencyTitle() {

		if (getMonographSourceOrganismParts() == null ||
				getMonographSourceOrganismParts().size() == 0 ) {
			return getMonographDoseSourceOrgPartTitle();
		}

		String sourceTitle = null;
		StringBuffer sb = new StringBuffer();         
		for (MonographSourceOrganismPart monoSrcOrgPart : getMonographSourceOrganismParts()) {
			if ( sb.length() != 0 ) {
				sb.append( SEMI_SPACE );
			}
			sourceTitle = monoSrcOrgPart.getSourceMaterialName();
			if (sourceTitle != null) {
				sb.append(sourceTitle);
				//log ("getUsesDepTitle CP 3: " + sb.toString());
			}
		} 

		return sb.toString();
	}

	public int getUsesDependencyTitleLength() {
		return getUsesDependencyTitle().length();
	}


	/* 
	 * To eliminate redundancies in the display, only display the Use 
	 * Dependencies of the DirectionForUse if they are not Use dependencies
	 * of the parent MonographDose 
	 * See Hops and 5-HTP monographs for examples:
	 * 1.  Hops: has a DirForUse with a distinct Use dependency, so display it
	 * 2.  5-HTP: has DirsForUse with Use dependencies which are the same as the 
	 *            parent MonographDose, so don't display them.
	 * 
	 * We do this here because MonographDirectionsForUse is no longer aware
	 * of its parent MonographDose               
	 */
	/*
    private void setDirectionsForUseUseDependencyTitles() {

    	log ("CHKP 1");
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		Set<MonographDirectionForUse> monoDirs = getMonographDirectionsForUse();
		if (monoDirs == null || monoDirs.size() == 0) {
			directionsDependencyTitleSet = true;
			return;
		}
		log ("CHKP 2");
		//initialize the use dependency title in each mdfu.  
		for (MonographDirectionForUse monoDir: monoDirs) {
			Set<MonographUse> dirUses = monoDir.getMonographUses();
			Set<MonographUse> doseUses = getMonographUses();  
			log ("CHKP 3");
			if (dirUses != null && dirUses.size() < 1) {
				for (MonographUse monoUse: dirUses) {
					if (doseUses != null && doseUses.size() < 1) {
					if (!doseUses.contains(monoUse)) 
						if (!first) {
							buff.append(";");
						}
						buff.append(monoUse.getShortDescription());
						first = false;
					}
				}
			}
			log("Setting MonoDir: " + monoDir.getCode() + 
				"Use dedendency title: " + buff.toString());	

			monoDir.setUseDependenciesTitle(buff.toString());
		}

		directionsDependencyTitleSet = true;
    }
	 */

	public String getMonographDoseUseTitle() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}

		MonographDose firstMonoDose = monographDoses.iterator().next();

		return firstMonoDose.getUsesDependencyTitle();

	}

	public String getMonographDoseSourceOrgPartTitle() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}

		MonographDose firstMonoDose = monographDoses.iterator().next();

		return firstMonoDose.getSourceOrgPartsDependencyTitle();

	}

	//eliminates duplicate Sets of Citations across the contained MonographDoses.
	//See Creatine monohydrate Monograph 
	public Set<Citation> getCommonCitations() {

		if (hasOnlyCommonCitations()) {
			MonographDose firstMonoDose = monographDoses.iterator().next();
			return firstMonoDose.getCitations();
		}
		else
			return null;
	}


	public Set<MonographDirectionForUse> getCommonMonographDirectionsForUse() {
		//log ("getCommonMonographDirectionsForUse");
		if (hasOnlyCommonMonographDirectionsForUse()) {
			MonographDose firstMonoDose = monographDoses.iterator().next();
			return firstMonoDose.getMonographDirectionsForUse();
		}
		else {
			//log ("returning null");
			return null;
		}
	}

	/*
	 * Retrieves the distinct set of MonographDirectionForUse for this
	 * MDC contained by all child MonographDose objects
	 */ 
	public Set<MonographDirectionForUse> getMonographDirectionsForUse() {

		Set<MonographDirectionForUse> retMonoDirsForUse = 
			new TreeSet<MonographDirectionForUse>();

		Set<MonographDirectionForUse> monoDirsForUse = null; 

		if (monographDoses != null) {
			for (MonographDose monoDose: monographDoses) {
				monoDirsForUse = monoDose.getMonographDirectionsForUse();
				if (monoDirsForUse != null && monoDirsForUse.size() > 0) {
					for (MonographDirectionForUse mdfu: monoDirsForUse) {
						//TreeSet and Comparable will filter out duplicates
						retMonoDirsForUse.add(mdfu);
					}
				}
			}
		}

		return retMonoDirsForUse;

	}

	/*
	 * Retrieves the distinct set of MonographDirectionForUse for this
	 * MDC contained by all child MonographDose objects.
	 * 
	 * To eliminate redundancies in the display, only display the Use 
	 * Dependencies of the DirectionForUse if they are not Use dependencies
	 * of the parent MonographDose 
	 * See Hops and 5-HTP monographs for examples:
	 * 1.  Hops: has a DirForUse with a distinct Use dependency, so display it
	 * 2.  5-HTP: has DirsForUse with Use dependencies which are the same as the 
	 *            parent MonographDose, so don't display them.
	 * 
	 * We need to do this here in MonographDoseCombination because 
	 * MonographDirectionsForUse is no longer aware of its parent MonographDose               
	 */
	/*
    public Set<MonographDirectionForUse> getMonographDirectionsForUse() {

    	//log("YYYYYY getMonographDirectionsForUse");
        //TODO: just retirve now, they have been populated.
    	StringBuffer buff = new StringBuffer();
    	Set<MonographDirectionForUse> retMonoDirsForUse = 
    		new TreeSet<MonographDirectionForUse>();

    	Set<MonographDirectionForUse> monoDirsForUse = null; 

    	//all Use dependencies of all MonographDoses 
    	Set<MonographUse> allDependentUses = new TreeSet<MonographUse>();
    	if (monographDoses != null) {
    		for (MonographDose monoDose: monographDoses) {
    			allDependentUses.addAll(monoDose.getMonographUses());
    		}
    		spewAllDependentUses(allDependentUses);

    		for (MonographDose monoDose: monographDoses) {
    			monoDirsForUse = monoDose.getMonographDirectionsForUse();
    			if (monoDirsForUse != null && monoDirsForUse.size() > 0) {
    				boolean first = true;
    				for (MonographDirectionForUse mdfu: monoDirsForUse) {
    					//TreeSet and Comparable will filter out duplicates
    					retMonoDirsForUse.add(mdfu);

    					Set<MonographUse> dirUses = mdfu.getMonographUses();
    					//Set<MonographUse> doseUses = monoDose.getMonographUses();  
    					if (allDependentUses != null && allDependentUses.size() > 0) {
							log ("Have " + allDependentUses.size() + " doseUses:");
						    for (MonographUse md: allDependentUses) {
						    	log ("DoseUse: " + md.getId() + ":" + md.getCode() +  
						    			" " + md.getShortStatement());	
						    }
    					}

    					if (dirUses != null && dirUses.size() > 0) {
    						log ("Have " + dirUses.size() + " dirUses");
    						for (MonographUse dirUse: dirUses) {
    						   /*
	 * append the use dependency if either there are no use 
	 * dependencies for the dose, or there are no matching ones. 
	 * 
    							log ("DirUse to look for: " + dirUse.getId() + ":" + 
    									dirUse.getCode() + " " + dirUse.getShortStatement());
    							if (allDependentUses == null || allDependentUses.size() == 0) {
    								log ("Have no doseUses");
    								if (!first) {
    									buff.append(";");
    								}
    								buff.append(dirUse.getShortStatement());
    								first = false;
    							}
    							else {	
	    							if (!allDependentUses.contains(dirUse)) {
	    								if (!first) {
	    									buff.append(";");
	    								}
	    								buff.append(dirUse.getShortStatement());
	    								log ("Not contained: appended: " + dirUse.getShortStatement());  
	    								first = false;
	    							}
    							}
    						}
    					}
    					log("Setting MonoDir: " + mdfu.getCode() + 
    						"Use dependency title: " + buff.toString());	

    					mdfu.setUseDependenciesTitle(buff.toString());
    				}

    			}
    		}
    	}

        return retMonoDirsForUse;

    }
	 */
	private void spewAllDependentUses(Set<MonographUse> allDependentUses) {
		if (allDependentUses == null || allDependentUses.size() == 0) {
			log.debug("NO DEPENDENT USES" );
			return;
		}

		log.debug(allDependentUses.size() + " Dependent Uses: "); 
		log.debug("----------------------------");
		for (MonographUse monoUse: allDependentUses) {
			log.debug("     Code: " + monoUse.getCode() + " Use: " + monoUse.getShortStatement());
		}
	}

	//returns the subpopulation name from the first contained MonographDose
	//These need to be common across all contained MonographDose objects
	//TODO - enforce that rule in the loader. 
	public String getSubPopulationName() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}
		MonographDose monoDose = (MonographDose)monographDoses.iterator().next();
		if (monoDose.getSubPopulation() != null) { 
			return monoDose.getSubPopulation().getName();
		}
		return null;
	}

	//returns the preparation type name from the first contained MonographDose
	//These need to be common across all contained MonographDose objects
	//TODO - enforce that rule in the loader. 
	public String getPreparationTypeName() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}
		
		MonographDose monoDose = (MonographDose)monographDoses.iterator().next();
        
        // Returns the name from the Monograph Preparation, if it has one.
        if (monoDose.getMonographPreparation().getName() != null) {
            return monoDose.getMonographPreparation().getName();
        }
        
		return monoDose.getPreparationTypeName();
	}

	//returns the preparation type group name from the first contained MonographDose
	//These need to be common across all contained MonographDose objects
	//TODO - enforce that rule in the loader. 
	public String getPreparationTypeGroupName() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}
		MonographDose monoDose = (MonographDose)monographDoses.iterator().next();
		return monoDose.getPreparationTypeGroupName();
	}

	//returns the preparation type id from the first contained MonographDose
	//These need to be common across all contained MonographDose objects
	//TODO - enforce that rule in the loader. 
	public Long getPreparationTypeId() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}
		MonographDose monoDose = (MonographDose)monographDoses.iterator().next();
		return monoDose.getMonographPreparation().getPreparationType().getId();
	}

	/*
	 * Returns the preparation type group id (DependantOject attribute) 
	 * from the first contained MonographDose.  
	 * These need to be common across all contained MonographDose objects
	 * TODO - enforce that rule in the loader. 
	 */
	public Long getPreparationTypeGroupId() {
		if (monographDoses == null || monographDoses.isEmpty()) {
			return null;
		}
		MonographDose monoDose = (MonographDose)monographDoses.iterator().next();
		return monoDose.getMonographPreparation().getPreparationTypeGroup().getId();
	}

	public boolean hasMonographSubingredientDoseCombinations() {
		if (monographDoses == null) return false;
		for (MonographDose monographDose: monographDoses) {
			if (monographDose.isHasSubIngredientDoseCombinations()) {
				return true; 
			}
		}
		return false;
	}
	
	
	/*
	 * Determines if this MonographSubingredientDoseCombination (MSIDC) should
	 * be rendered on a single line or multiple lines.  Single line rendering
	 * occurs for *real* MonographSubingredientDoseCombinations which contain > 1 
	 * MonographSubingredientDoses
	 * 
	 * a) if there are multiple subingredients in an MD which are not in a real
	 * MSIDC, separate lines for the subs
	 * b) if there are only single subingredients in a MD which are 
	 * not in a real MSIDC, single line for the sub
	 
	 *if MD has multiple MSIDCs, each with a single MSID:  multiple lines
if MD has single MSIDC with a single MSID and is part of an MDC: single line. 
	 *
	 *
	 *	
	public boolean isSingleLineSubingredients() {  
		logSubIngredients();
		
		/* 
		 * if this MD has multiple MSIDCs, and all MSIDCs are just wrappers,
		 * (they all have exactly one MSID), render on distinct lines
		 *
		Set<MonographSubIngredientDose> msids = null;
		if (monographSubIngredientDoseCombinations != null &&
			monographSubIngredientDoseCombinations.size() > 1) {
		
			for (MonographSubIngredientDoseCombination msidc: 
				monographSubIngredientDoseCombinations) {
				msids = msidc.getMonographSubIngredientDoses();
				if (msids != null && msids.size() != 1) {
					return true;
				}
			}
		}
		log ("isSingleLineSubingredients returning false for MD: " + code); 
		return false;
		
	}	    	
	

	public void logSubIngredients() {  

		if (monographSubIngredientDoseCombinations == null ||
				monographSubIngredientDoseCombinations.size() == 0) {
			log ("MD: " + code + " has NO MSIDCs");
			return;
		}

		log ("MD: " + code);
		Set<MonographSubIngredientDose> msids = null;
		for (MonographSubIngredientDoseCombination msidc: 
			monographSubIngredientDoseCombinations) {
			log ("    MSIDC: " + msidc.getCode());
			msids = msidc.getMonographSubIngredientDoses();
			if (msids != null) {
				for (MonographSubIngredientDose msid: msids) {
					log ("        MSID: " + msid.getCode());
				}
			}
		}

	}	    

	
	/*
	 * Determines if this MonographDoseCombination (MSIDC) should
	 * be rendered on a single line or multiple lines.  Single line rendering
	 * occurs for *real* MonographSubingredientDoseCombinations which contain > 1 
	 * MonographSubingredientDoses
	 * 
	 * a) if there are multiple subingredients in an MD which are not in a real
	 * MSIDC, separate lines for the subs
	 * b) if there are only single subingredients in a MD which are 
	 * not in a real MSIDC, single line for the sub
	 
	 *if MD has multiple MSIDCs, each with a single MSID:  multiple lines
if MD has single MSIDC with a single MSID and is part of an MDC: single line. 
	 *
	 *
	 */	
    public boolean isSingleLineSubingredients() {  
	    //logSubIngredients();
	   
	   
	    /*
		* if all contained MDs have single MSIDCs which have single MSIDs, 
		* render on a single line.
		*/
		Set<MonographSubIngredientDoseCombination> msidcs = null;
		Set<MonographSubIngredientDose> msids = null;
		for (MonographDose monoDose: monographDoses) {
			msidcs = monoDose.getMonographSubIngredientDoseCombinations();
			
			if (msidcs != null && msidcs.size() != 1) {
				log.debug("isSingleLineSubingredients returning false for MDC: " + code); 
				return false;
			}
			for (MonographSubIngredientDoseCombination msidc: msidcs) {
				msids = msidc.getMonographSubIngredientDoses();
				if (msids != null && msids.size() != 1) {
					log.debug("isSingleLineSubingredients returning false for MDC: " + code); 
					return false;
				}
			}
		}
			
		log.debug("isSingleLineSubingredients returning true for MDC: " + code); 
		return true;	
		

		
		/* 
		 * if this MDC has MDs with multiple MSIDCs, and all MSIDCs are just wrappers,
		 * (they all have exactly one MSID), render on distinct lines
		 *
		Set<MonographSubIngredientDoseCombination> msidcs = null;
		Set<MonographSubIngredientDose> msids = null;
		for (MonographDose monoDose: monographDoses) {
			msidcs = monoDose.getMonographSubIngredientDoseCombinations();
			
			if (msidcs != null && msidcs.size() > 1) {
				//more than 1 MSIDC
				for (MonographSubIngredientDoseCombination msidc: msidcs) {
					msids = msidc.getMonographSubIngredientDoses();
					if (msids != null && msids.size() > 1) {
						return true;
					}
				}
			}
			
		}
		log ("isSingleLineSubingredients returning false for MDC: " + code); 
		return false;
		*/
	}	    	
	

	/*
	 * Convenience accessor for JSPs - determines if this is a "real"
	 * MonographDoseCombination (MDC), eg. rather than just wrapping a single dose, 
	 * this MDC contains multiple doses.  
	 */
	public boolean isHasMultipleDoses() {
		if (monographDoses == null) return false;
		return  (monographDoses.size() > 1);
	}
	
	
	/*
	 * Convenience accessor for JSPs - determines if this MDC has any 
	 * MonographDose info for the ingredient, as opposed to subingredient 
	 * dose info.   
	 */
	public boolean isHasIngredientDoses() {
		if (monographDoses == null) return false;
		for (MonographDose monoDose: monographDoses) {
			if (monoDose.getQuantityForDisplay().length() > 0) {
				return true;
			}
		}
	
		return false;
	}
	
	public MonographDose getSingleDose() {
		if (monographDoses == null) return null;
		return (MonographDose)monographDoses.iterator().next();
	}

	public boolean hasOnlyCommonCitations() {
		if (monographDoses == null) return false;

		//get first MonographDose
		MonographDose firstMonoDose = monographDoses.iterator().next();
		Set<Citation> firstCitations = firstMonoDose.getCitations();
		//compare the rest with the first
		for (MonographDose monographDose: monographDoses) {
			if (!citationsMatch(firstCitations,
					monographDose.getCitations())) {
				return false;
			}
		}	
		return true;	 
	}



	public boolean hasOnlyCommonMonographDirectionsForUse() {
		if (monographDoses == null) return false;

		//get first MonographDirectionsForUse
		MonographDose firstMonoDose = monographDoses.iterator().next();
		Set<MonographDirectionForUse> firstDirections = firstMonoDose.getMonographDirectionsForUse();
		if (firstDirections == null) { 
			return false;
		}
		Set<MonographDirectionForUse> otherDirections = null;
		Iterator firstIterator = null;  
		Iterator otherIterator = null;
		MonographDirectionForUse firstDfu, otherDfu;
		//compare the rest with the first
		for (MonographDose monographDose: monographDoses) {

			otherDirections = monographDose.getMonographDirectionsForUse();
			if (otherDirections == null || 
					firstDirections.size() != otherDirections.size()) {
				return false;
			}
			firstIterator = firstDirections.iterator();
			otherIterator = otherDirections.iterator();
			while (firstIterator.hasNext()) {
				firstDfu = (MonographDirectionForUse)firstIterator.next();
				otherDfu = (MonographDirectionForUse)otherIterator.next();
				if (!firstDfu.equals(otherDfu)) {
					return false;
				}
			}
		} 
		return true;
	}


	private boolean citationsMatch(Set<Citation> set1,
			Set<Citation> set2) {

		if (set1 == null && set2 == null) 
			return true;

		if ((set1 == null && set2 != null) ||
				(set1 != null && set2 == null)) 
			return false;

		if (set1.size() != set2.size()) 
			return false;

		//compare them by code only.  I don't want to rely on a
		//polymorphic/fluctuating implementation of an equality operator for
		//the abstract citation class
		boolean found = false;
		for (Citation cit1: set1) {
			found = false;
			for (Citation cit2: set2) {
				//log ("Cit1: " + cit1.getCode() + "  Cit2: " + cit2.getCode());
				if (cit1.getCode().equals(cit2.getCode())) {
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}

		return true;

	}

	/* 
	 * returns text describing the set of doses.
	 * TODO - test this... 
	 */
	public String getQuantitiesForDisplay() {


		//log ("MDC: QFD..." ); 

		boolean first = true;

		StringBuffer quantities = new StringBuffer();
		for (MonographDose monoDose: getMonographDoses()) {
			if (!first) {
				quantities.append(",");
			}
			first = false;

			if (monoDose.isSingleDose()) {
				quantities.append(monoDose.getSingleDoseForDisplay());
			}
			else 
				if (monoDose.isDailyDose()) {
					quantities.append(monoDose.getQuantityForDisplay());
					if (isLanguageFrench())
						quantities.append(" par jour");
					else
						quantities.append(" per day");
				}
		}
		//log ("MDC: getQuantitiesForDisplay:" + quantities.toString());
		return quantities.toString();

	}


	/*
	 * Returns a textual representation of a MonographDoseCombination, which 
	 * traverses down the containment tree into MonographDoses, 
	 * MonographSubingredientDoseCombinations, MonographSubingredientDoses, and 
	 * MonographSubIngredientCombinations.
	 * The returned String is suitable for display as a Dependency title.  
	 * Introduced for the Fish Oil monograph, which has Uses which are dependant on 
	 * MonographSubingredientDose/MonographSubingredientCombinations   
	 */
	public String getTestMdcTitle() {

		Float minDose = 0f;
		Float maxDose = 0f;
		Float minRatio = 0f;
		Float maxRatio = 0f;
		StringBuffer buffer = new StringBuffer();


		for (MonographDose monoDose: monographDoses) {

			Set<MonographSubIngredientDoseCombination> monographSubIngredientDoseCombinations =
				monoDose.getMonographSubIngredientDoseCombinations();

			if (monographSubIngredientDoseCombinations == null || 
					monographSubIngredientDoseCombinations.size() <= 0) { 
				//log("MonographDose Id/Code:" + getId() + "/" + getCode() + " has no MSIDCs");
				return null;
			}



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
					//log("gSIDC4Disp CHP1");
					minDose = msid.getDoseMinimum();
					maxDose = msid.getDoseMaximum();

					/* 
					 * NB.  the dose and dose units might not be provided here if we 
					 * are dealing with a "real" MSIC which only provides a 
					 * subingredient ratio  
					 */
					if (minDose != null && maxDose != null) {
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

					//log("gSIDC4Disp CHP4");
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
						buffer.append(":1 ,");
					}
					//log("gSIDC4Disp CHP5");
					//else log ("No Ratios");
				}
			}

		}
		//log("Returned FO title: " + buffer.toString());
		return buffer.toString();

	}

	/*
	 * Builds a textual representation of a subingredient dose 
	 */
	private String formatDose(Float minDose, Float maxDose) {

		//log ("TEST 25 formatDose: " + minDose.floatValue() + "/" + maxDose.floatValue());
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
			//log("MonographDose: Min Dose Only: " + buffer.toString());
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

		return buffer.toString();
	}


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
					sb.append(", ");
				}
				//log("MD: getDependencyAsString appending: " + 
				//		organismPart.getOrganismPart().getType().getName());
				sb.append( organismPart.getOrganismPart().getType().getName() );              
			}
			return sb.toString();
		}
		else { 
			//log (" no organism part dependencies...");
		}
		return null;
	}

	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 *
	 * @return  this object as a string value
	 */
	@Override
	public String getValuesAsString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append(super.getValuesAsString());
		buffer.append(", monographDoses: ");
		buffer.append(getMonographDoses());

		return buffer.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
			return "Y";
		}
		return "N";
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
		visible = ("Y".equals(value));
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * Compares this object with the specified object for equality and for
	 * order. Returns a negative integer, zero, or a positive integer as this
	 * object is less than, equal to, or greater than the specified object.
	 * 
	 * NB.  This implementation ensures that the collection of MDCs are returned
	 * in the proper order for display purposes, such that the JSP can detect when
	 * to change dependency titles.  Dependency titles are grouped by SubPopulation,
	 * Use, and Preparation Type (in that order).
	 * 
	 * Special handling has been introduced to deal with cases where Use dependency
	 * titles need to be compared across MDCs and the MDs they contain.  An important
	 * assumption made in this logic is that all dependencies of the MDs contained 
	 * by an MDC will be the same. 
	 * 
	 * 
	 * NB. this design is questionable because it introduces display specific 
	 * functionality at the model tier.  It is recommended to revisit this design;
	 * perhaps subclassing the model objects with display specific objects which 
	 * provide this behaviour would be a better design. 
	 *
	 * @param   o  the object to compare this to.
	 * @return  a comparator flag qualifying equality and order
	 * @throws  ClassCastException  when the object provided is of the wrong
	 *                              type
	 */
	public int compareTo(Object obj) throws ClassCastException {

		try {

			// Drop out if either side is null or not available.
			if ((obj == null) || (((MonographDoseCombination) obj).getId() == null)) {
				return -1;
			}

			if (this.equals(obj)) {
				return 0;
			}

			if (getId() == null) {
				return 1;
			}

			MonographDoseCombination passedMonoDoseCombo = (MonographDoseCombination)obj; 

			/*
			 * Use the contents of the first contained MonographDose for sorting 
			 * purposes.  This implies that all MDs in a real MDC will have the 
			 * same dependencies.
			 * TODO - verify that in the Loader
			 * TODO - need to handle MSOP dependencies of the MDC.
			 */
			MonographDose thisMonoDose = null; 
			MonographDose passedMonoDose = null;

			if (monographDoses != null && monographDoses.size() > 0)
				thisMonoDose = (MonographDose)monographDoses.iterator().next();
			Set<MonographDose> passedDoses = passedMonoDoseCombo.getMonographDoses();
			if (passedDoses != null && passedDoses.size() > 0) 
				passedMonoDose = (MonographDose)passedDoses.iterator().next();	


			// Sort on the subPopulation First.
			if ( thisMonoDose.getSubPopulation() != null && 
					passedMonoDose.getSubPopulation() != null && 
					!thisMonoDose.getSubPopulation().equals(passedMonoDose.getSubPopulation())) {
				return thisMonoDose.getSubPopulation().compareTo(passedMonoDose.getSubPopulation());    
			}

			/* 
			 * Sort on the Uses.  NB. Real MDCs may have a UsesDependencyTitle,
			 * which needs to be compared against contained MD UsesDependencyTitle.
			 * Move empty getUsesDependencyTitle to the top of the lists 
			 */
			String thisUse = getUsesDependencyTitle();
			String thisChildUse = thisMonoDose.getUsesDependencyTitle();
			String passedUse = passedMonoDoseCombo.getUsesDependencyTitle();
			String passedChildUse = passedMonoDose.getUsesDependencyTitle();

			//cross parent/child checks
			if (thisUse == null) 
				if (passedUse == null && passedChildUse != null)
					return -1;
			if (thisUse != null) 
				if (passedUse == null && passedChildUse != null)
					return 1;

			if (thisUse != null && passedChildUse != null && 
					!thisUse.equals(passedChildUse)) {
				return StringComparator.compare(thisUse,passedChildUse);
			} 

			if (thisChildUse != null && passedUse != null && 
					!thisChildUse.equals(passedUse)) {
				return StringComparator.compare(thisChildUse,passedUse);
			} 


			//parent only checks
			if (thisUse == null && passedUse != null) {
				return -1;
			}
			if (thisUse != null && passedUse == null) {
				return 1; 
			}
			if (thisUse != null && passedUse != null) {
				if (!thisUse.equals(passedUse)) 
					return StringComparator.compare(thisUse,passedUse);
			}

			//child only checks
			if (thisChildUse == null && passedChildUse != null) {
				return -1;
			}
			if (thisChildUse != null && passedChildUse == null) {
				return 1; 
			}
			if (thisChildUse != null && passedChildUse != null) {
				if (!thisChildUse.equals(passedChildUse)) 
					return StringComparator.compare(thisChildUse,passedChildUse);
			}


			//need to sort on the preparation type, which may have been passed in 
			//via either a group or a single prep type.
			String thisPrep = null;
			String passedPrep = null;

			if (thisMonoDose.getPreparationTypeGroupName() != null)
				thisPrep = thisMonoDose.getPreparationTypeGroupName();
			else
				if (thisMonoDose.getPreparationTypeName() != null)
					thisPrep = thisMonoDose.getPreparationTypeName();	
			if (passedMonoDose.getPreparationTypeGroupName() != null)
				passedPrep = passedMonoDose.getPreparationTypeGroupName();
			else
				if (passedMonoDose.getPreparationTypeName() != null)
					passedPrep = passedMonoDose.getPreparationTypeName();	


			// Sort on the Prep Type (either type or group) 
			//log ("prep type: This: " 
					//		+ thisMonoDose.getPreparationTypeGroupName()
			//		+ " Passed: " + passedMonoDose.getPreparationTypeGroupName());
			if (thisPrep != null && passedPrep == null) {
				return 1;
			}
			if (thisPrep == null && passedPrep != null) {
				return -1;
			}
			if (thisPrep != null && passedPrep != null && 
					!thisPrep.equals(passedPrep)) {

				return StringComparator.compare(thisPrep, passedPrep);
			}


			/*
			 * Sort on MonographSourceOrganismPart dependencies.  See Angelica
			 * and Plantago* (Psyllium) monographs.  This sorts on the dependency title
			 * which can be built from several part names.  It also likely has to go
			 * into the contained monograph doses, in the same fashion as the uses.
			 */
			String passedDependTitle = passedMonoDoseCombo.getDependencyAsString();

			if (getDependencyAsString() != null && passedDependTitle == null) {
				return 1;
			}
			if (getDependencyAsString() == null && passedDependTitle != null) {
				return -1;
			}
			if (getDependencyAsString() != null && 
					passedDependTitle != null &&
					!getDependencyAsString().equals(passedDependTitle) ) {
				return StringComparator.compare(getDependencyAsString(), passedDependTitle);
			}

			//use first MD in the MDC to get dependency title
			String thisMonoDoseDepend = thisMonoDose.getDependencyAsString();
			String passedMonoDoseDepend = passedMonoDose.getDependencyAsString(); 

			if (thisMonoDoseDepend != null && passedMonoDoseDepend == null) {
				return 1;
			}
			if (thisMonoDoseDepend == null && passedMonoDoseDepend != null) {
				return -1;
			}
			if (thisMonoDoseDepend != null && 
					passedMonoDoseDepend != null &&
					!thisMonoDoseDepend.equals(passedMonoDoseDepend) ) {
				return StringComparator.compare(thisMonoDoseDepend, passedMonoDoseDepend);
			}

			//TODO - cross compare parent (MDC) and child (MD) dependency titles...


		} catch (Exception e) {
			e.printStackTrace();
		}

		// Sort on the id.
		return getId().compareTo(((MonographDoseCombination) obj).getId());

	}
    
    
    
}
