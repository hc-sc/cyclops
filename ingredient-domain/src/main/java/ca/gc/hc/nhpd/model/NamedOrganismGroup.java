package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * This object contains a group of Organisms. It does so by having a collection of
 * included TaxonomyNodes as well as a collection of excluded TaxonomyNodes. All
 * of the Organisms within the included collection minus those in the excluded
 * collection make up the group. This group is shared by any number of substances,
 * so it has a name and reference.
 */
public class NamedOrganismGroup extends SimpleOrganismGroup implements Comparable {
    private static final Log LOG = LogFactory.getLog(NamedOrganismGroup.class);
    private Set excludedCache = new TreeSet();
    private Set<Family> excludedFamilies = new TreeSet<Family>();
    private Set<Genus> excludedGenera = new TreeSet<Genus>();
    private Set<Organism> excludedOrganisms = new TreeSet<Organism>();
    private Set<Species> excludedSpecies = new TreeSet<Species>();
    private Set<SubTaxa> excludedSubTaxa = new TreeSet<SubTaxa>();
    
    private Set includedCache = new TreeSet();
    private Set<Family> includedFamilies = new TreeSet<Family>();
    private Set<Genus> includedGenera = new TreeSet<Genus>();
    private Set<Species> includedSpecies = new TreeSet<Species>();
    private Set<SubTaxa> includedSubTaxa = new TreeSet<SubTaxa>();
    
    private boolean flushExcludedCache;
    private boolean flushIncludedCache;
    private String nameE;
    private String nameF;
   
    private Reference reference;

    /***************************************************************************
     * For Hibernate use only. Others should all use getExcludedTaxonomyNodes.
     * @see #getExcludedTaxonomyNodes()
     */
    public Set<Family> getExcludedFamilies(){
        return excludedFamilies;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getExcludedTaxonomyNodes.
     * @see #getExcludedTaxonomyNodes()
     */
    public Set<Genus> getExcludedGenera(){
        return excludedGenera;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getExcludedTaxonomyNodes.
     * @see #getExcludedTaxonomyNodes()
     */
    public Set<Organism> getExcludedOrganisms(){
        return excludedOrganisms;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getExcludedTaxonomyNodes.
     * @see #getExcludedTaxonomyNodes()
     */
    public Set<Species> getExcludedSpecies(){
        return excludedSpecies;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getExcludedTaxonomyNodes.
     * @see #getExcludedTaxonomyNodes()
     */
    public Set<SubTaxa> getExcludedSubTaxa(){
        return excludedSubTaxa;
    }

    /***************************************************************************
     */
    public Set<TaxonomyNode> getExcludedTaxonomyNodes(){
        if (flushExcludedCache) {
            excludedCache.clear();
            excludedCache.addAll(getExcludedFamilies());
            excludedCache.addAll(getExcludedGenera());
            excludedCache.addAll(getExcludedSpecies());
            excludedCache.addAll(getExcludedSubTaxa());
            excludedCache.addAll(getExcludedOrganisms());
            flushExcludedCache = false;
        }

        return excludedCache;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getIncludedTaxonomyNodes.
     * @see #getIncludedTaxonomyNodes()
     */
    public Set<Family> getIncludedFamilies(){
        return includedFamilies;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getIncludedTaxonomyNodes.
     * @see #getIncludedTaxonomyNodes()
     */
    public Set<Genus> getIncludedGenera(){
        return includedGenera;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getIncludedTaxonomyNodes.
     * @see #getIncludedTaxonomyNodes()
     */
    public Set<Species> getIncludedSpecies(){
        return includedSpecies;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use getIncludedTaxonomyNodes.
     * @see #getIncludedTaxonomyNodes()
     */
    public Set<SubTaxa> getIncludedSubTaxa(){
        return includedSubTaxa;
    }

    /***************************************************************************
     */
    public Set getIncludedTaxonomyNodes(){
        if (flushIncludedCache) {
            includedCache.clear();
            includedCache.addAll(getIncludedFamilies());
            includedCache.addAll(getIncludedGenera());
            includedCache.addAll(getIncludedSpecies());
            includedCache.addAll(getIncludedSubTaxa());
            includedCache.addAll(getIncludedOrganisms());
            flushIncludedCache = false;
        }

        return includedCache;
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF()
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     */
    /*public Set getParts(){
        return parts;
    }*/

    /***************************************************************************
     */
    public Reference getReference(){
        return reference;
    }

    /***************************************************************************
     * Gets whether this group only contains a single Organism. Over-ridden to
     * account for the additional data structures.
     * @return true if this group only contains a single Organism.
     */
    public boolean isSingleOrganism(){
        return (getExcludedTaxonomyNodes().isEmpty()
                && getIncludedTaxonomyNodes().size() == 1
                && getIncludedTaxonomyNodes().iterator().next() instanceof Organism);
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setExcludedTaxonomyNodes.
     * @see #setExcludedTaxonomyNodes()
     * @param newVal
     */
    public void setExcludedFamilies(Set newVal){
        if (newVal != null) {
            excludedFamilies = newVal;
        } else {
            excludedFamilies.clear();
        }
        flushExcludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setExcludedTaxonomyNodes.
     * @see #setExcludedTaxonomyNodes()
     * @param newVal
     */
    public void setExcludedGenera(Set newVal){
        if (newVal != null) {
            excludedGenera = newVal;
        } else {
            excludedGenera.clear();
        }
        flushExcludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setExcludedTaxonomyNodes.
     * @see #setExcludedTaxonomyNodes()
     * @param newVal
     */
    public void setExcludedOrganisms(Set<Organism> newVal){
        if (newVal != null) {
            excludedOrganisms = newVal;
        } else {
            excludedOrganisms.clear();
        }
        flushExcludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setExcludedTaxonomyNodes.
     * @see #setExcludedTaxonomyNodes()
     * @param newVal
     */
    public void setExcludedSpecies(Set newVal){
        if (newVal != null) {
            excludedSpecies = newVal;
        } else {
            excludedSpecies.clear();
        }
        flushExcludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setExcludedTaxonomyNodes.
     * @see #setExcludedTaxonomyNodes()
     * @param newVal
     */
    public void setExcludedSubTaxa(Set newVal){
        if (newVal != null) {
            excludedSubTaxa = newVal;
        } else {
            excludedSubTaxa.clear();
        }
        flushExcludedCache = true;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setExcludedTaxonomyNodes(Set newVal){
        Object node;
        
        clearExcludedTaxonomyNodes();
        if (newVal != null) {
            for (Iterator it=newVal.iterator(); it.hasNext(); ) {
                node = it.next();
                if (node instanceof TaxonomyNode) {
                    addExcludedTaxonomyNode((TaxonomyNode)node);
                } else {
                    LOG.error("Item not a TaxonomyNode: " +
                              node.getClass().getName());
                }
            }
        }
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setIncludedTaxonomyNodes.
     * @see #setIncludedTaxonomyNodes()
     * @param newVal
     */
    public void setIncludedFamilies(Set newVal){
        if (newVal != null) {
            includedFamilies = newVal;
        } else {
            includedFamilies.clear();
        }
        flushIncludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setIncludedTaxonomyNodes.
     * @see #setIncludedTaxonomyNodes()
     * @param newVal
     */
    public void setIncludedGenera(Set newVal){
        if (newVal != null) {
            includedGenera = newVal;
        } else {
            includedGenera.clear();
        }
        flushIncludedCache = true;
    }

    /***************************************************************************
     * Over-ridden for Hibernate use only. Others should all use
     * setIncludedTaxonomyNodes.
     * @see #setIncludedTaxonomyNodes()
     * @param newVal
     */
    public void setIncludedOrganisms(Set<Organism> newVal){
        super.setIncludedOrganisms(newVal);
        flushIncludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setIncludedTaxonomyNodes.
     * @see #setIncludedTaxonomyNodes()
     * @param newVal
     */
    public void setIncludedSpecies(Set newVal){
        if (newVal != null) {
            includedSpecies = newVal;
        } else {
            includedSpecies.clear();
        }
        flushIncludedCache = true;
    }

    /***************************************************************************
     * For Hibernate use only. Others should all use setIncludedTaxonomyNodes.
     * @see #setIncludedTaxonomyNodes()
     * @param newVal
     */
    public void setIncludedSubTaxa(Set newVal){
        if (newVal != null) {
            includedSubTaxa = newVal;
        } else {
            includedSubTaxa.clear();
        }
        flushIncludedCache = true;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setIncludedTaxonomyNodes(Set newVal){
        Object node;
        
        clearIncludedTaxonomyNodes();
        if (newVal != null) {
            for (Iterator it=newVal.iterator(); it.hasNext(); ) {
                node = it.next();
                if (node instanceof TaxonomyNode) {
                    addIncludedTaxonomyNode((TaxonomyNode)node);
                } else {
                    LOG.error("Item not a TaxonomyNode: " +
                              node.getClass().getName());
                }
            }
        }
    }

    /***************************************************************************
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    /*public void setParts(Set newVal){
        parts = newVal;
    }*/

    /***************************************************************************
     * @param newVal
     */
    public void setReference(Reference newVal){
        reference = newVal;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }
    
    /***************************************************************************
     * Adds the passed TaxonomyNode to the appropriate set of excluded nodes
     * that exist in this OrganismGroup.
     * @param node the TaxonomyNode to be added
     * @see #setExcludedTaxonomyNodes()
     */
    public void addExcludedTaxonomyNode(TaxonomyNode node){
        if (node instanceof Family) {
            getExcludedFamilies().add((Family)node);
        } else if (node instanceof Genus) {
            getExcludedGenera().add((Genus)node);
        } else if (node instanceof Species) {
            getExcludedSpecies().add((Species)node);
        } else if (node instanceof SubTaxa) {
            getExcludedSubTaxa().add((SubTaxa)node);
        } else { //Must be an Organism
            getExcludedOrganisms().add((Organism)node);
        }
    }

    /***************************************************************************
     * Adds the passed TaxonomyNode to the appropriate set of included nodes
     * that exist in this OrganismGroup.
     * @param node the TaxonomyNode to be added
     * @see #setIncludedTaxonomyNodes()
     */
    public void addIncludedTaxonomyNode(TaxonomyNode node){
        if (node instanceof Family) {
            getIncludedFamilies().add((Family)node);
        } else if (node instanceof Genus) {
            getIncludedGenera().add((Genus)node);
        } else if (node instanceof Species) {
            getIncludedSpecies().add((Species)node);
        } else if (node instanceof SubTaxa) {
            getIncludedSubTaxa().add((SubTaxa)node);
        } else { //Must be an Organism
            getIncludedOrganisms().add((Organism)node);
        }
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),
    								    ((NamedOrganismGroup)o).getName());
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        Set excluded = getExcludedTaxonomyNodes();
        Set included = getIncludedTaxonomyNodes();
        Iterator iterator = null;
        TaxonomyNode node;

        
        buffer.append(super.getValuesAsString());
        if (included != null && !included.isEmpty()) {
            iterator = included.iterator();
            node = (TaxonomyNode)iterator.next(); //Has to be one, since it isn't empty
            buffer.append(", included nodes: [");
            buffer.append(node.getTaxonomyType());
            buffer.append(":");
            buffer.append(node.getName());
            while (iterator.hasNext()) {
                buffer.append(", ");
                node = (TaxonomyNode)iterator.next();
                buffer.append(node.getTaxonomyType());
                buffer.append(":");
                buffer.append(node.getName());
            }
            buffer.append("]");
        }
        if (excluded != null && !excluded.isEmpty()) {
            iterator = excluded.iterator();
            node = (TaxonomyNode)iterator.next(); //Has to be one, since it isn't empty
            buffer.append(", excluded nodes: [");
            buffer.append(node.getTaxonomyType());
            buffer.append(":");
            buffer.append(node.getName());
            while (iterator.hasNext()) {
                buffer.append(", ");
                node = (TaxonomyNode)iterator.next();
                buffer.append(node.getTaxonomyType());
                buffer.append(":");
                buffer.append(node.getName());
            }
            buffer.append("]");
        }
        buffer.append(", name: ");
        buffer.append(getName());
        buffer.append(", reference: ");
        buffer.append(getReference());
        
        return buffer.toString();
    }

    /***************************************************************************
     * Called to prepare the taxonomy nodes for update. This makes sure that they
     * are empty and also sets flushExcludedCache to true.
     * @see #setExcludedTaxonomyNodes()
     */
    private void clearExcludedTaxonomyNodes(){
        getExcludedFamilies().clear();
        getExcludedGenera().clear();
        getExcludedSpecies().clear();
        getExcludedSubTaxa().clear();
        getExcludedOrganisms().clear();
        flushExcludedCache = true;
    }

    /***************************************************************************
     * Called to prepare the taxonomy nodes for update. This makes sure that they
     * are empty and also sets flushIncludedCache to true.
     * @see #setIncludedTaxonomyNodes()
     */
    private void clearIncludedTaxonomyNodes(){
        getIncludedFamilies().clear();
        getIncludedGenera().clear();
        getIncludedSpecies().clear();
        getIncludedSubTaxa().clear();
        getIncludedOrganisms().clear();
        flushIncludedCache = true;
    }
}