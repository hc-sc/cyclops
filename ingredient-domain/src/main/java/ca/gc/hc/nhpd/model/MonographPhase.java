package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * The MonographPhase defines a usage phase of a Monograph. Seldom required, 
 * these are used when the dose and duration of use vary depending on the phase
 * or stage of consumption. Example phases are "Loading" and "Maintenance".
 * Note that this object's monographDoseCombinations must already exist in a
 * distinct relationship to one of its Monograph's monographEntries.
 */
public class MonographPhase extends PersistentObject  implements Comparable {
    private String code;
	private Set<MonographDoseCombination> monographDoseCombinations;
    private Set<MonographDuration> monographDurations;
    private String nameE;
    private String nameF;
    private int order;

    /**
     * Gets the set of MonographDoseCombinations that belong to this phase. This
     * is a subset of those for the Monograph that has this phase. Note that
     * these must also be in a composite (distinct) relationship to the
     * MonographEntry.
     * @return the set of MonographDoseCombinations in this phase.
     * @see #setMonographDoseCombinations()
     */
    public Set<MonographDoseCombination> getMonographDoseCombinations() {
        return monographDoseCombinations;
    }

    /**
     * Gets the set of MonographDurations that belong to this phase. This is a
     * subset of those for the Monograph that has this phase.
     * @return the set of MonographDurations associated to this phase.
     * @see #setMonographDurations()
     */
    public Set<MonographDuration> getMonographDurations() {
        return monographDurations;
    }

    /**
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    /**
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF()
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * Gets the sort order of this phase in relation to the other phases of the
     * Monograph that has this phase.
     * @return the sort order of this phase.
     * @see #setOrder()
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the set of MonographDoseCombinations that belong to this phase. This
     * is a subset of those for the Monograph that has this phase. Note that
     * these must also be in a composite (distinct) relationship to the
     * MonographEntry.
     * @param newVal the set of MonographDoseCombinations in this phase.
     * @see #getMonographDoseCombinations()
     */
    public void setMonographDoseCombinations(Set<MonographDoseCombination> newVal) {
        monographDoseCombinations = newVal;
    }

    /**
     * Sets the set of MonographDurations that belong to this phase. This is a
     * subset of those for the Monograph that has this phase.
     * @param newVal the set of MonographDurations associated to this phase.
     * @see #getMonographDurations()
     */
    public void setMonographDurations(Set<MonographDuration> newVal) {
        monographDurations = newVal;
    }

    /**
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }

    /**
     * Sets the sort order of this phase in relation to the other phases of the
     * Monograph that has this phase.
     * @param newVal the sort order of this phase.
     * @see #setOrder()
     */
    public void setOrder(int newVal) {
        order = newVal;
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
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", order: ");
        buffer.append(getOrder());
        buffer.append(", monographDoseCombinations: ");
        buffer.append(getMonographDoseCombinations());
        buffer.append(", monographDurations: ");
        buffer.append(getMonographDurations());

        return buffer.toString();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public int compareTo(Object obj) throws ClassCastException {
		
		if (obj == null) 
			return -1;
		
		if (this.equals(obj)) 
			return 0;
		
		int passedOrder = ((MonographPhase)obj).getOrder();
		if (passedOrder == order) 
		    return 0;
		if (passedOrder > order) 
		    return -1;
		return 1;
		
	}
}
