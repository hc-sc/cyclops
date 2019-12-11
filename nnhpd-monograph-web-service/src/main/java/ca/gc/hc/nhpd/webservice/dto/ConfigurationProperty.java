package ca.gc.hc.nhpd.webservice.dto;

/*
 * WS Wrapper for configuration properties.  We expose a list of name value 
 * pairs, backed by a Properties implementation.  The reason for this is 
 * that we don't want to expose the details of the Java properties class. 
 */
public class ConfigurationProperty {
    private String propertyName;

    private String propertyValue;

    public ConfigurationProperty() {
    }

    public ConfigurationProperty(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

}