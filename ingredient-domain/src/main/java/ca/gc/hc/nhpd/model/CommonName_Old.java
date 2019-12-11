package ca.gc.hc.nhpd.model;

import java.util.Iterator;
import java.util.Set;

/*******************************************************************************
 * An object that represents a name that may be in common use for an item, where
 * the name comes from an authoritative source.
 */
public class CommonName_Old extends Synonym {
    
    private final static String COMMA_SPACE = ", ";
    private final static String EMPTY_STRING = "";
    
	private Set references;

    /**
     * Gets the citation associated with this QualifiedObject.
     *
     * @return  the citations Set for this QualifiedObject.
     */
    public String getCitationsAsString() {
        
        if ( getReferences() == null 
            || getReferences().size() == 0 ) {
                return null;
            }

        StringBuffer citationList = new StringBuffer();
        Iterator i = getReferences().iterator();
        while ( i.hasNext() ) {
            Reference reference = (Reference) i.next();
            if ( reference.getCitation() != null ) {
                Citation citation = reference.getCitation();
                // Use the abridged title if possible...
                if ( citationList.length() != 0 ) {
                    citationList.append( COMMA_SPACE );
                }
                if ( citation.getAbridgedTitle() != null 
                       && !citation.getAbridgedTitle().equals(EMPTY_STRING)) {
                    citationList.append( citation.getAbridgedTitle() );                    
                } else if ( reference.getCode() != null 
                        && !reference.getCode().equals(EMPTY_STRING)) {
                    citationList.append( reference.getCode() );                    
                }
            }
        }
        return citationList.toString();
    }

    /***************************************************************************
     */
    public Set getReferences(){
        return references;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReferences(Set newVal){
        references = newVal;
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
        buffer.append(", references: ");
        buffer.append(getReferences());
        
        return buffer.toString();
    }
}
