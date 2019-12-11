package ca.gc.hc.nhpd.model;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * This class represents an entry in a Monograph. There is one for each
 * Ingredient in the Monograph. It is used to define Ingredient - specific
 * characteristics that appear in the Monograph.
 */
public abstract class MonographEntry extends PersistentObject 
implements Comparable {
    
	private static final Log log = LogFactory.getLog(MonographEntry.class);
	
    private Set<QualifiedSynonym> commonNames;
	private Ingredient ingredient;
	private Monograph monograph;
	private Set<MonographDoseCombination> monographDoseCombinations;
	private String sourceNotesE;
	private String sourceNotesF;
	private Set<QualifiedSynonym> properNames;
	private Set<MonographSourceOrganismPart> monographSourceOrganismParts;
	private transient Set<MonographDoseCombination>
		visibleMonographDoseCombinations;
	//moved up from ChemicalMonographEntry Dec 16,2009 
	private Set<TextMonographSourceMaterial> otherMonographSources;   //persisted
	
	
	/**
	 * Monograph Entry common names represents a collection of 
	 * qualified synonym names used to represent this monograph entry.  This
	 * set of names is displayed in the monograph interface and 
	 * represents a subset of the common names given to an ingredient.
	 * At loading time, if a common name is "extra" or a common name has a 
	 * different "French" version, a warning is issued.
	 * 
	 * The monograph display object has an accessor that combines 
	 * all the common names from all the monograph entries into a single accessor.
	 * 
	 * @return a set of QualifiedSynonyms for this monographEntry.
	 * @see setQualifiedSynonyms()
	 */
	public Set<QualifiedSynonym> getCommonNames() {
		return commonNames;
	}

	//filters out the common names that are only available in other languages.  
	public Set<QualifiedSynonym> getSingleLanguageCommonNames() {
		Set<QualifiedSynonym> qualifiedSynonyms = new TreeSet<QualifiedSynonym>();
		for (QualifiedSynonym qualifiedSynonym: commonNames) {
			if (qualifiedSynonym!=null) {  
				if (isLanguageFrench()) {
					if(qualifiedSynonym.getSynonym()!=null &&
							qualifiedSynonym.getSynonym().getNameF()!=null &&
							qualifiedSynonym.getSynonym().getNameF().length()!=0 &&
							!qualifiedSynonym.getSynonym().getNameF().equals(Synonym.EMPTY_EQUIVALENCE)) {
						qualifiedSynonyms.add(qualifiedSynonym);     
					}
				}
				else {
					if(qualifiedSynonym.getSynonym()!=null &&
							qualifiedSynonym.getSynonym().getNameE()!=null &&
							qualifiedSynonym.getSynonym().getNameE().length()!=0 &&
							!qualifiedSynonym.getSynonym().getNameE().equals(Synonym.EMPTY_EQUIVALENCE)) {
						qualifiedSynonyms.add(qualifiedSynonym);     
					}
				}
			}
		}

		return qualifiedSynonyms;
	}

	/**
	 * Sets the list of qualified synonym names associated with this monograph
	 * entry.
	 * @param newVal represents a list of qualified synonyms.  (Must be a subset
	 *               of the ingredient qualified synonym, validated by loader)
	 * @see getQualifiedSynonyms()
	 */
	public void setCommonNames(Set<QualifiedSynonym> newVal) {
		commonNames = newVal;
	}

	/***************************************************************************
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}

	/***************************************************************************
	 * @param newVal
	 */
	public void setIngredient(Ingredient newVal) {
		ingredient = newVal;
	}

	/***************************************************************************
	 * Only used in the loading of Monographs.
	 */
	public Monograph getMonograph() {
		return monograph;
	}

    /***************************************************************************
     * Used by the jsp when displaying the monograph.
     */
    public GeneratedMonograph getGeneratedMonograph() {
        if (monograph != null &&
                monograph instanceof ca.gc.hc.nhpd.model.GeneratedMonograph) {
            return (GeneratedMonograph) getMonograph();
        }
        return null;
    }
    
    public boolean isGenerated() {
        if (monograph != null &&
                monograph instanceof ca.gc.hc.nhpd.model.GeneratedMonograph) {
            return true;
        }
        return false;
    }
    
    
	/***************************************************************************
	 * Only used in the loading of Monographs.
	 */
	public void setMonograph(Monograph newVal) {
		monograph = newVal;
	}

	/**
	 * Monograph Entry proper names is a list of synonyms (not qualified by
	 * references) and  is returned instead of the ingredient name.  When empty,
	 * the ingredient name should be displayed.
	 * @return alternate Proper name display.
	 * @see setProperNames()
	 */
	public Set<QualifiedSynonym> getProperNames() {
		return properNames;
	}
	/**
	 * Sets the list of proper names associated with this monograph entry.
	 * @param newVal represents a list of Proper Names. (Synonym class)
	 * @see getProperNames()
	 */
	public void setProperNames(Set<QualifiedSynonym> newVal) {
		properNames = newVal;
	}

	/***************************************************************************
	 * Gets the source notes in the language appropriate for the Locale.
	 * @return the locale-specific source notes.
	 */
	public String getSourceNotes() {
		if (isLanguageFrench()) {
			return getSourceNotesF();
		}
		return getSourceNotesE();
	}

	/***************************************************************************
	 * Gets the source notes in English.
	 * @return the source notes in English.
	 * @see #setSourceNotesE()
	 */
	public String getSourceNotesE() {
		return sourceNotesE;
	}

	/***************************************************************************
	 * Gets the source notes in French.
	 * @return the source notes in French.
	 * @see #setSourceNotesF()
	 */
	public String getSourceNotesF() {
		return sourceNotesF;
	}

	/***************************************************************************
	 * Sets the source notes in English.
	 * @see #setSourceNotesE()
	 */
	public void setSourceNotesE(String newVal) {
		sourceNotesE = newVal;
	}

	/***************************************************************************
	 * Sets the source notes in French.
	 * @see #getSourceNotesF()
	 */
	public void setSourceNotesF(String newVal) {
		sourceNotesF = newVal;
	}

	/**
	 * Getter for the set of Source Materials. These are OrganismParts that this
	 * ingredient could be retrieved from within the context of a particular
	 * monograph.
	 *
	 * @return  a set of OrganismPart objects comprising the source material for
	 *          this monograph entry.
	 *
	 * @see     setSourceMaterials()
	 */
	public Set<MonographSourceOrganismPart> getMonographSourceOrganismParts() {
		return monographSourceOrganismParts;
	}

	/**
	 * Setter for the set of Source Materials. These are OrganismParts that this
	 * ingredient could be retrieved from within the context of a particular
	 * monograph.
	 *
	 * @param value  a set of OrganismParts
	 *
	 * @see getSourceMaterials()
	 */
	public void setMonographSourceOrganismParts(Set<MonographSourceOrganismPart> value) {
		monographSourceOrganismParts = value;
	}


	/***************************************************************************
	 * Gets the display name in the language appropriate for the Locale.
	 * @return the locale-specific display name.
	 */
	public String getDisplayableString(){
		if(getIngredient()!=null){
			return getIngredient().getAuthorizedName();
		}
		return getMonograph().getName();
	}



	public Set<MonographDoseCombination> getVisibleMonographDoseCombinations() {

		if (visibleMonographDoseCombinations == null) {
			visibleMonographDoseCombinations = 
				new TreeSet<MonographDoseCombination>();

			if (getMonographDoseCombinations() != null) {
				for (MonographDoseCombination monoDoseCombination 
						: getMonographDoseCombinations()) {
					if (monoDoseCombination.isVisible()) {
						visibleMonographDoseCombinations.add(monoDoseCombination);
					}
				}
			}
		}

		return visibleMonographDoseCombinations;

	}


	/* To eliminate redundancies in the display, only display the Use 
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
	public void populateDirectionsForUseUseDependencies() {

		Set<MonographDoseCombination> monoDoseCombos = 
			getVisibleMonographDoseCombinations();
		Set<MonographDose> monoDoses = null;
		Set<MonographUse> allDependentUses = new TreeSet<MonographUse>();

		//get all the Use dependencies for all MDCs in this MonoEntry
		for (MonographDoseCombination monoDoseCombo: monoDoseCombos) {
			monoDoses = monoDoseCombo.getMonographDoses();
			if (monoDoses != null) {
				for (MonographDose monoDose: monoDoses) {
					Set<MonographUse> monoUses = monoDose.getMonographUses();
					if (monoUses != null && monoUses.size() > 0) { 
						allDependentUses.addAll(monoDose.getMonographUses());
					}
				}
			}
		}

		//build the use dependency string for each Directions for use.
		Set<MonographDirectionForUse> monoDirs = null;
		for (MonographDoseCombination monoDoseCombo: monoDoseCombos) {
			monoDoses = monoDoseCombo.getMonographDoses();
			if (monoDoses != null) {
				for (MonographDose monoDose: monoDoses) {
					monoDirs = monoDose.getMonographDirectionsForUse();
					if (monoDirs != null) {
						for (MonographDirectionForUse mdfu: monoDirs) {
							buildUseDependencyTitle(mdfu,allDependentUses);
						}
					}	
				}
			}
		}
	}


	private void buildUseDependencyTitle(MonographDirectionForUse dirForUse,
			Set<MonographUse> dependentMonoUses) {

		StringBuffer buff = new StringBuffer();
		//testing code - remove
		if (dependentMonoUses != null && dependentMonoUses.size() > 0) {
			log ("Have " + dependentMonoUses.size() + " dependent MonoUses:");
			for (MonographUse md: dependentMonoUses) {
				log ("Dependent Use: " + md.getId() + ":" + md.getCode() +  
						" " + md.getShortStatement());	
			}
		}


		/*
		 * Get the Use dependencies from the MonographDirectionsForUse.
		 * Compare those with the use dependencies of all doses.  
		 * Append the use dependency if there are either zero use 
		 * dependencies for the dose, or no matching ones. 
		 */
		Set<MonographUse> dirUses = dirForUse.getMonographUses();
		if (dirUses == null || dirUses.size() == 0) {
			return;
		}
		boolean first = true;

		for (MonographUse dirUse: dirUses) {
			log ("Checking for same DirUse: " + dirUse.getId() + ":" + 
					dirUse.getCode() + " " + dirUse.getShortStatement());
			if (dependentMonoUses == null || dependentMonoUses.size() == 0) {
				log ("Have no doseUses");
				if (!first) {
					buff.append(";");
				}
				buff.append(dirUse.getShortStatement());
				first = false;
			}
			else {	
				if (!dependentMonoUses.contains(dirUse)) {
					if (!first) {
						buff.append(";");
					}
					buff.append(dirUse.getShortStatement());
					log ("Not contained: appended: " + dirUse.getShortStatement());  
					first = false;
				}
			}

			log("Setting MonoDir: " + dirForUse.getCode() + 
					"Use dependency title: " + buff.toString());	
		}
		dirForUse.setUseDependenciesTitle(buff.toString());
	}

	
	/***************************************************************************
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less than,
	 * equal to, or greater than the specified object.
	 * @param o the object to compare this to.
	 */
	public int compareTo(Object o) throws ClassCastException {
		if (o == null) {
			return -1;
		}
		if (getMonograph() == null
				|| ((MonographEntry) o).getMonograph() == null) {
			//Should never happen
			return 1;
		}

		//First sort is on Monograph:
		int monographCompare =
			getMonograph().compareTo(((MonographEntry) o).getMonograph());
		if (monographCompare != 0) {
			return monographCompare;
		}
		//Second sort in on Ingredient:
		if (getIngredient() == null
				|| ((MonographEntry) o).getIngredient() == null) {
			//Should never happen
			return 1;
		}
		return getIngredient().compareTo(((MonographEntry) o).getIngredient());
	}

	/***************************************************************************
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 */
	@Override
	public String getValuesAsString() {
		StringBuilder buffer = new StringBuilder();
		boolean isFirstItem;

		buffer.append(super.getValuesAsString());
		buffer.append(", ingredient: ");
		if (getIngredient() != null) {
			buffer.append(getIngredient().getAuthorizedName());
		} else {
			buffer.append("null");
		}
		buffer.append(", properNames: ");
		if (getProperNames() != null){
			isFirstItem = true;
			buffer.append("[");
			for (QualifiedSynonym synonym : getProperNames()) {
				if (isFirstItem) {
					isFirstItem = false;
				} else {
					buffer.append(", ");
				}
				buffer.append(synonym.getName());
			}
			buffer.append("]");
		} else {
			buffer.append("null");
		}

		buffer.append(", commonNames: ");
		if (getCommonNames() != null){
			isFirstItem = true;
			buffer.append("[");
			for (QualifiedSynonym synonym : getCommonNames()) {
				if (isFirstItem) {
					isFirstItem = false;
				} else {
					buffer.append(", ");
				}
				buffer.append(synonym.getName());
			}
			buffer.append("]");
		} else {
			buffer.append("null");
		}
		buffer.append(", sourceOrganismParts: ");
		if (getMonographSourceOrganismParts() != null){
			isFirstItem = true;
			buffer.append("[");
			for (MonographSourceOrganismPart part : getMonographSourceOrganismParts()) {
				if (isFirstItem) {
					isFirstItem = false;
				} else {
					buffer.append(", ");
				}
				buffer.append(part.getSourceMaterialName());
			}
			buffer.append("]");
		} else {
			buffer.append("null");
		}
		
		if (otherMonographSources != null){
	            isFirstItem = true;
	            buffer.append("[");
	            for (TextMonographSourceMaterial monoSourceMat : otherMonographSources) {
	                if (isFirstItem) {
	                    isFirstItem = false;
	                } else {
	                    buffer.append(", ");
	                }
	                buffer.append(monoSourceMat.getSourceMaterialName());
	            }
	            buffer.append("]");
	        } else {
	            buffer.append("null");
	        }
		buffer.append(", sourceNotesE: ");
		buffer.append(getSourceNotesE());
		buffer.append(",\n monographDoseCombinations: ");
		//buffer.append(getMonographDoseCombinations());

		return buffer.toString();
	}

	public Set<MonographDoseCombination> getMonographDoseCombinations() {
		return monographDoseCombinations;
	}

	public void setMonographDoseCombinations(
			Set<MonographDoseCombination> monographDoseCombinations) {
		this.monographDoseCombinations = monographDoseCombinations;
		populateDirectionsForUseUseDependencies();
	}

	//order the MDCs by the Use Title
	/*
	public List<MonographDoseCombination> getMonographDoseCombinationsByUse() {

		ArrayList<MonographDoseCombination> mdcArray = 
			new ArrayList<MonographDoseCombination>();
		for (MonographDoseCombination mdc: monographDoseCombinations) {
			mdcArray.add(mdc);
		}
		Collections.sort(mdcArray,
						 new MonographDoseCombination.MonographDoseCombinationComparator());

		return mdcArray;
	}
	 */

	private void log(String msg) {
		log.debug(msg);
	}

	public Set<TextMonographSourceMaterial> getOtherMonographSources() {
		return otherMonographSources;
	}
	
	/**
     * Sets other Sources. These are descriptions of sources that are not
     * ingredients or organism parts that are in the system, or can only be
     * described with text.
     *
     * @param value a set of text descriptions of sources in addition to the
     *        ingredients or organism parts already captured in those collections.
     *
     * @see    getOtherSourceE()
     */
	public void setOtherMonographSources(
			Set<TextMonographSourceMaterial> otherMonographSources) {
		this.otherMonographSources = otherMonographSources;
	}

}