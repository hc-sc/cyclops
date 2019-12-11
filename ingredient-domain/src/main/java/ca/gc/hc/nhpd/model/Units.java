package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

/*******************************************************************************
 * An object that represents a measurement unit (e.g. kg, mL, etc.).
 */
public class Units extends PersistentObject 
	implements ControlledVocabularyObject, Comparable {
    
    public static final String UNIT_CODE_YEAR = "yr";
    public static final String UNIT_CODE_MONTH = "mnths";
    
    private String code;
    private Boolean equivalent;
    private String nameE;
    private String nameF;
    private Boolean preferred;
    private Double ratioToBase;
    private UnitsType type;
    
    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return false;
	}

    /**
     * Gets whether this Units can be converted to another one (i.e. supports
     * amount conversion).
     * 
     * @return a Boolean -true if this Units can be converted to another one.
     * 
     * @see convertAmountToUnits()
     */
    public Boolean isConvertable() {
        if (ratioToBase == null) {
            return false;
        }
        if (ratioToBase == -1) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Converts the passed amount from the passed Units to this Units. With the
     * exception of volume and mass, the passed Units must be the same type as
     * this one. Volume may be converted to mass and vice versa.
     * 
     * @param amount a Number that contains the amount expressed using the
     *        passed amountUnits.
     *        
     * @param amountUnits the Units used to  express the passed amount.
     * 
     * @return a Number that contains the equivalent amount expressed using
     *         this Units.
     * @throws an UnexpectedDataType Exception if amount or amountUnits is
     *         undefined, null, or not the type of object that was expected.
     * @throws an InvalidArgument Exception if the amount cannot be converted
     *         since the amountUnits type is incompatible.
     * @throws an UnsupportedOperation Exception if either of these Units doesn't
     *         support amount conversion.
     * @param amount
     * @param amountUnits
     * @return
     */
    public double convertAmount (double amount, Units amountUnits) {
        if (!isConvertable())
        {
            throw new IngredientsException("UnsupportedOperation - " + getName()
                  + " cannot be converted");
        }
        if (this.getType().equals(amountUnits.getType())
            || ((this.getType().getCode().equals(UnitsType.TYPE_MASS) 
                 || this.getType().getCode().equals(UnitsType.TYPE_VOLUME))
                && (amountUnits.getType().getCode().equals(UnitsType.TYPE_MASS) 
                    || amountUnits.getType().getCode().equals(UnitsType.TYPE_VOLUME))))
        {
            return convertAmountToBase(amount) / this.ratioToBase;
        }
        throw new IngredientsException("InvalidArgument - Cannot convert a " 
              + amountUnits.getCode() + " to a " + this.getType().getCode());
    }
    
    /**
     * Converts the passed amount from this Units to the "base" Units for this
     * type. This is an initial step when converting it to another Units.
     * 
     * @param amount a Number that contains the amount expressed using this
     *        Units.
     *        
     * @return a Number that contains the equivalent amount expressed using
     *         the base Units.
     *         
     * @throws an UnsupportedOperation Exception if this Units doesn't support
     *         amount conversion.
     */
    public double convertAmountToBase(double amount) {
        if (!isConvertable())
        {
            throw new IngredientsException("UnsupportedOperation - A " + this.getName()
                  + " cannot be converted");
        }
        return amount * this.ratioToBase;
    }
    
    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #setCode()
     */
    public String getCode(){
        return code;
    }

    /***************************************************************************
     * Boolean flag that indicates if this unit can be used as an alternate to
     * another unit.  This is sometimes used for labelling purpose.
     * 
     * @return equivalence flag
     * 
     * @see setEquivalent()
     */
    public Boolean isEquivalent() {
        return equivalent;
    }
    
    /***************************************************************************
     * Boolean flag that indicates if this unit can be used as an alternate to
     * another unit.  This is sometimes used for labelling purpose.  
     * Hibernate Implementation.
     * 
     * @return equivalence flag
     * 
     * @see setEquivalentAsString()
     */
    public String getEquivalentAsString() {
        return StringUtils.booleanToString(equivalent);
    }
    
    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * 
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
     * Boolean flag that indicates if this unit is a preferred unit.  Preferred
     * units are sometimes emphasized to give a user an easier selection when
     * presenting the units list.  
     * 
     * @return preferred flag
     * 
     * @see setPreferred()
     */
    public Boolean isPreferred() {
        return preferred;
    }
    
    /***************************************************************************
     * Boolean flag that indicates if this unit is a preferred unit.  Preferred
     * units are sometimes emphasized to give a user an easier selection when
     * presenting the units list.  
     * Hibernate Implementation.
     * 
     * @return preferred flag
     * 
     * @see setPreferredAsString()
     */
    public String getPreferredAsString() {
        return StringUtils.booleanToString(preferred);
    }

    /**
     * Gets the ratio of this Units to a "base" Units of the same type. This is
     * used as a means of converting it to another Units (almost always of the
     * same type).
     * 
     * @return a Number that contains the ratio of this Units to the base Units
     *         for this type. -1 means that this Units cannot be converted.
     *         
     * @see setRatioToBase()
     */
    public Double getRatioToBase() {
        return ratioToBase;
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
	 * Gets the French short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the French specific short description.
	 */
	public String getShortDescriptionF() {
		return getNameF();
	}

    /***************************************************************************
     */
    public UnitsType getType(){
        return type;
    }

    /***************************************************************************
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getCode()
     */
    public void setCode(String newVal){
        code = newVal;
    }

    /***************************************************************************
     * Boolean flag that indicates if this unit can be used as an alternate to
     * another unit.  This is sometimes used for labelling purpose.
     * 
     * @param equivalent boolean flag indicating this object as an equivalent.
     * 
     * @see isEquivalent()
     */
    public void setEquivalent(Boolean equivalent) {
        this.equivalent = equivalent;
    }
    
    /***************************************************************************
    * Boolean flag that indicates if this unit can be used as an alternate to
    * another unit.  This is sometimes used for labelling purpose.
    * Hibernate Implementation.
    * 
    * @param equivalent String flag indicating this object as an equivalent.
    * 
    * @see getEquivalentAsString()
    */
    public void setEquivalentAsString(String equivalent) {
        this.equivalent = StringUtils.stringToBoolean(equivalent);
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
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
     * Boolean flag that indicates if this unit is a preferred unit.  Preferred
     * units are sometimes emphasized to give a user an easier selection when
     * presenting the units list.  
     * 
     * @param preferred flag
     * 
     * @see isPreferred()
     */
    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }
    
    /***************************************************************************
     * Boolean flag that indicates if this unit is a preferred unit.  Preferred
     * units are sometimes emphasized to give a user an easier selection when
     * presenting the units list.  
     * Hibernate Implementation.
     * 
     * @param preferred flag
     * 
     * @see getPreferredAsString()
     */
    public void setPreferredAsString(String preferred) {
        this.preferred = StringUtils.stringToBoolean(preferred);
    }
    
    /**
     * Sets the ratio of this Units to a "base" Units of the same type. This is
     * used as a means of converting it to another Units (almost always of the
     * same type).
     * 
     * @param aNumber a Number that contains the ratio of this Units to the
     *        base Units for this type. -1 means that this Units cannot be
     *        converted.
     * 
     * @see getRatioToBase()
     */
    public void setRatioToBase(Double ratioToBase) {
        this.ratioToBase = ratioToBase;
    }
    
    /***************************************************************************
     * @param newVal
     */
    public void setType(UnitsType newVal){
        type = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1;
    	return StringComparator.compare(getName(),((Units)o).getName());
         
    }

    public String getDescription() {
    	return getName();
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
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", type: ");
        if (getType() != null) {
            buffer.append(getType().getCode());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
}