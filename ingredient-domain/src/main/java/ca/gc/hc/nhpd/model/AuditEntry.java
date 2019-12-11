package ca.gc.hc.nhpd.model;

import java.util.Date;
/*************************************************************************
 * A class representing an Audit Trail entry tracking any changes made to
 * any of the model classes.
 * 
 * @author BDempsey
 *
 */
public class AuditEntry extends PersistentObject {
	private String nameOfObject;
	private Long idOfObject;
	private String originalValue;
	private String newValue;
	private String fieldName;
	private String transType;
	
	public AuditEntry() {};
	
	public AuditEntry(UserAccount acct, String objName, Long objId, Date entryDate, String origValue, String newVal, String fldName, String trType)
	{
		nameOfObject = objName;
		idOfObject = objId;
		originalValue = origValue;
		newValue = newVal;
		fieldName = fldName;
		transType = trType;
		super.setLastUpdateDate(entryDate);
		super.setCreationDate(entryDate);
		super.setLastUpdateAccount(acct);
		
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String newVal) {
		fieldName = newVal;
	}
	public Long getIdOfObject() {
		return idOfObject;
	}
	public void setIdOfObject(Long newVal) {
		idOfObject = newVal;
	}
	public String getNameOfObject() {
		return nameOfObject;
	}
	public void setNameOfObject(String newVal) {
		nameOfObject = newVal;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newVal) {
		newValue = newVal;
	}
	public String getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(String newVal) {
		originalValue = newVal;
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
        buffer.append(", object name: ");
        buffer.append(getNameOfObject());
        buffer.append(", object id: ");
        buffer.append(getIdOfObject());
        buffer.append(", original value: ");
        buffer.append(getOriginalValue());
        buffer.append(", new value: ");
        buffer.append(getNewValue());
        buffer.append(", field name: ");
        buffer.append(getFieldName());
        
        return buffer.toString();
    }

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
}
