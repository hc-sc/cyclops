package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * The ApplicationProperty object is used to access and update information in
 * the Application_Properties oracle table.  This object / table are independent
 * of other ingredient database related objects and it does not extend the
 * standard PersistentObject.
 * 
 * One of the objectives of this object is to control the key flag and triggers
 * in order to cause the truncation of the ingredient database before and
 * end-to-end load.
 * </p>
 *
 * @version  1.0
 */
public class ApplicationProperty implements Comparable<ApplicationProperty> {

    public static final String KEY_LOADER_INGREDIENTS_LOADED = "loader.ingredients_loaded";
    public static final String KEY_LOADER_LOAD_DATE = "loader.load_date";
    public static final String KEY_LOADER_READY_TO_PUBLISH = "loader.ready.to.publish";    
    public static final String KEY_LOADER_VERSION = "loader.version";
    public static final String KEY_SCHEMA_DATE = "schema.date";
    public static final String KEY_SCHEMA_VERSION = "schema.version";
    public static final String KEY_TRUNCATE_INTERNAL_DATA = "truncate.internal.data";
    
    public static final Double FLAG_TRUCATE_INTERNAL_DATA = new Double(3); // testing use 2, production use 3!
    public static final Double FLAG_LOADER_READY_TO_PUBLISH = new Double(1);
    
    public static final String DATA_LOADER_READY_TO_PUBLISH = "1";
    
    private String data;
    private Double flag;
    private String key;
    
    /**
     * Getter for the data.  The data is a string value that is associated
     * with the key.  In our case, we use the data to stamp the version 
     * number of the loader, the number in ingredients that were loaded as
     * well as the load date.
     * 
     * @return
     */
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
    /**
     * Getter for the flag.  The flag is a numeric piece of information that 
     * is associated with the key.  In our case, the flag is sometimes used
     * to trigger various database events.  Like the purging of information.
     * 
     * @return
     */
    public Double getFlag() {
        return flag;
    }
    /**
     * Setter for the flag.
     * 
     * @param flag
     */
    public void setFlag(Double flag) {
        this.flag = flag;
    }
    
    /**
     * Getter for the key.  The key is a unique identifier that can be used to
     * retrieve the data or the flag.  Think property file key.
     * 
     * @return the key.
     */
    public String getKey() {
        return key;
    }
    /**
     * Setter for the key.
     * 
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Returns a string implementation of this object.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Key: ");
        sb.append(getKey());
        sb.append(", Data: ");
        sb.append(getData());
        sb.append(", Flag: ");
        sb.append(getFlag());
        return sb.toString();
    }
    
    /**
     * Allows this object to be compared.  The key is used for comparison.
     */
    public int compareTo(ApplicationProperty obj) throws ClassCastException {
        if (obj == null) 
            return -1;
        
        return getKey().compareTo(obj.getKey());
    }
    
}
