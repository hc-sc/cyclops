package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * This object represents symptoms that a product is intended to treat / affect.
 */
public class Indication extends PersistentObject { //TODO New Class
	private MeddraPreferredTerm preferredTerm;
	private Set<RouteOfAdministration> routesOfAdministration;
	private IndicationType type;
	private Set uses;

    /***************************************************************************
	 * Gets the commonly used unique code for this object. In this case, is is a
	 * concatenation of the IndicationType's code and the PreferredTerm's code in the
	 * form "type:term".
	 */
	public String getCode(){
        StringBuffer buffer = new StringBuffer();
        
        if (getType() != null) {
            buffer.append(getType().getCode());
        }
        buffer.append(":");
        if (getPreferredTerm() != null) {
            buffer.append(getPreferredTerm().getCode());
        }
        
        return buffer.toString();
	}

    /***************************************************************************
     */
	public MeddraPreferredTerm getPreferredTerm(){
		return preferredTerm;
	}

    /***************************************************************************
     */
	public Set<RouteOfAdministration> getRoutesOfAdministration(){
		return routesOfAdministration;
	}

    /***************************************************************************
     */
	public IndicationType getType(){
		return type;
	}

    /***************************************************************************
     */
	public Set getUses(){
		return uses;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPreferredTerm(MeddraPreferredTerm newVal){
		preferredTerm = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setRoutesOfAdministration(Set<RouteOfAdministration> newVal){
		routesOfAdministration = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setType(IndicationType newVal){
		type = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setUses(Set newVal){
		uses = newVal;
	}

}