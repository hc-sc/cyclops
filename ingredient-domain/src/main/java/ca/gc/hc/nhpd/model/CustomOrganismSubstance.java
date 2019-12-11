package ca.gc.hc.nhpd.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An object that represents an organism substance ingredient, which is a part of
 * an Organism that has been prepared in a specific way.
 */
public class CustomOrganismSubstance extends Ingredient {
    private static final Log LOG = LogFactory.getLog(CustomOrganismSubstance.class);
    private static final String TYPE_E = "Custom Organism Substance";
    private static final String TYPE_F = "Substance dérivée d'un organisme sur mesure";
    private Organism organism;
    private OrganismPart part;
    private PreparationType preparation;

    /***************************************************************************
     * Gets the name, which is a concatenation of the organism, part, and
     * preparation names. Since these are all Latin, it is not Locale-specific.
     * @return the Latin name.
     */
    public String getAuthorizedName() {
        StringBuffer buffer = new StringBuffer();

        if (getOrganism() != null) {
            buffer.append(getOrganism().getName());
        } else {
            buffer.append("undefined organism");
        }
        buffer.append(" ");
        if (getPart() != null) {
            buffer.append(getPart().getName());
        } else {
            buffer.append("undefined part");
        }
        buffer.append(" ");
        if (getPreparation() != null) {
            buffer.append(getPreparation().getName());
        } else {
            buffer.append("undefined preparation");
        }

        return buffer.toString();
    }

    /***************************************************************************
     */
    public Organism getOrganism(){
        return organism;
    }

    /***************************************************************************
     */
    public OrganismPart getPart(){
        return part;
    }

    /***************************************************************************
     */
    public PreparationType getPreparation(){
        return preparation;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public String getType(){
        if (isLanguageFrench()) {
            return TYPE_F;
        }
        return TYPE_E;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setOrganism(Organism newVal){
        organism = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPart(OrganismPart newVal){
        part = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPreparation(PreparationType newVal){
		// Reset the allNames to ensure it pick up new name value.
		//super.allNames = null;
        preparation = newVal;
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
        buffer.append(", authorizedName: ");
        buffer.append(getAuthorizedName());
        
        return buffer.toString();
    }
}