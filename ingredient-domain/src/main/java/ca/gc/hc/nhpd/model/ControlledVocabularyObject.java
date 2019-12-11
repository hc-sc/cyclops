package ca.gc.hc.nhpd.model;
import java.util.List;
/* 
 * Common interface defining Vocabulary objects.  These are mapped to 
 * "code table" type objects in the data model
 */ 
public interface ControlledVocabularyObject {

	public String getName();
	
	/* 
	 * Indicates whether this VocabularyObject has a Description attribute.
	 * This accessor, in conjunction with the getControlledVocabularyDescription
	 * accessor provide an abstract mechanism to retrieve description, since:
	 * a) description is captured in either comments or description attribute
	 * b) some model objects expose a description attribute, but there is
	 * no data populated in the database.  Method name must be JavaBean syntax
	 * compliant for boolean JSTL test  
	 */
	public boolean isHasControlledVocabularyDescription();
	
}
