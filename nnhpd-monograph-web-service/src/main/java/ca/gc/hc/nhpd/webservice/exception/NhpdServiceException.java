package ca.gc.hc.nhpd.webservice.exception;
import ca.gc.hc.nhpd.exception.NhpdException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



/*
 * TBD:  look at web service exception handling patterns
 * not sure if we need web service specific exception  
 *
 * This exception class allows us to communicate a single error
 * back to a web service client.  When validating the contents of 
 * several fields, use a ValidationErrors return type class instead. 
 * 
 */
public class NhpdServiceException extends Exception {

	private String errorCode;
	private Properties properties; 
	private int severity;   //TBD
	
	
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public NhpdServiceException(String errorCode) {
		this.errorCode = errorCode;
	}
	
	//wrapper ctor
	public NhpdServiceException(Exception exception) {
		super(exception);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
}
