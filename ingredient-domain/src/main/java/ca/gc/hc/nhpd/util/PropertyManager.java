package ca.gc.hc.nhpd.util;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * This class is used as a helper class for properties file based configurations.
 * Those classes typically expose type-specific accessors for their properties
 * and use an instance of this class to access their values.
 * This currently supports Strings, Integers, and Y/N values as booleans.
 */
public class PropertyManager {
    private static final Log LOG = LogFactory.getLog(PropertyManager.class);
    private PropertyResourceBundle properties = null;
    
    /***************************************************************************
     * Constructs a PropertyManager for the named properties file. Logs a
     * warning and uses default values if there are problems encountered.
     * @param fileName the path / file name of the properties file.
     */
    public PropertyManager(String fileName) {
        InputStream stream = PropertyManager.class.getResourceAsStream(fileName);
        try {
            properties = new PropertyResourceBundle(stream);
        } catch (Exception ex) {
            LOG.warn("Could not open " + fileName
                     + ". Default configuration values will be used.", ex);
        }
    }
    
    public PropertyManager(String fileName, Class clazz) {
        InputStream stream = clazz.getResourceAsStream(fileName);
        try {
            properties = new PropertyResourceBundle(stream);
        } catch (Exception ex) {
            LOG.warn("Could not open " + fileName
                     + ". Default configuration values will be used.", ex);
        }
    }
    
    /***************************************************************************
     * Gets an integer property from the file. Logs a warning and returns the
     * default value if there are problems encountered.
     * @param propertyName the key of the property in the file.
     * @param defaultValue the value to return if there was a problem.
     * @return the property's value.
     */
    public int getIntegerProperty(String propertyName, int defaultValue) {
        if (properties != null) {
            String stringValue = null;
            
            try {
                stringValue = properties.getString(propertyName);
                return Integer.valueOf(stringValue).intValue();
                
            } catch (NumberFormatException nfe) {
                LOG.warn("Configuration property " + propertyName 
                         + " is not an integer. The default value will be used.");

            } catch (Throwable ex) {
                LOG.warn("Configuration property " + propertyName 
                         + " is missing. The default value will be used.");
            }
        }

        return defaultValue;
    }

    /***************************************************************************
     * Gets a String property from the file. Logs a warning and returns the
     * default value if there are problems encountered.
     * @param propertyName the key of the property in the file.
     * @param defaultValue the value to return if there was a problem.
     * @return the property's value.
     */
    public String getStringProperty(String propertyName, String defaultValue) {
        if (properties != null) {
            try {
                return properties.getString(propertyName);
                
            } catch (Throwable ex) {
                LOG.warn("Configuration property " + propertyName 
                         + " is missing. The default value will be used.");
            }
        }

        return defaultValue;
    }
    
    
    /***************************************************************************
     * Gets a boolean property from the file. Logs a warning and returns the
     * default value if there are problems encountered.  This method transforms
     * yes/no and true/false text values into a boolean value
     *  
     * @param propertyName the key of the property in the file.
     * @param defaultValue the value to return if there was a problem.
     * @return the property's value.
     */
    public boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        
    	String value = null;
    	
        if (properties != null) {	
        	try {
                value = properties.getString(propertyName);    
                return (value.toLowerCase().startsWith("t") ||
                	    value.toLowerCase().startsWith("y")); 
        	} 
        	catch (Throwable ex) {
                LOG.warn("Configuration property " + propertyName 
                         + " is missing. The default value will be used.");
            }
        	
        }
        return defaultValue;
        
    }
    
}
