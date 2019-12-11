package ca.gc.hc.nhpd.exception;
/*
* This class provides a global Runtime exception for the NHPD   
* Ingredients application.  It should be the only exception required,
* in adherence to the "single runtime exception" pattern.  
* See http://www.mindview.net/Etc/Discussions/CheckedExceptions
* 
* @author: M. Rabe
* @since: May 2007
*
*/

public class IngredientsException extends RuntimeException 
{
    public static final String EXCEPTION_UNSUPPORTED_OBJECT = "AbstractDao.findByIdBase() - Unsupported Object: ";
	
    public static final long serialVersionUID = 1234098790182348283L;
	private int severity;
	private long errorCode;   //TBD
	private boolean toBeLogged;
	private Throwable wrappedException;
	private String stringStackTrace;
	
	public IngredientsException(String message)
	{
		super(message);
	}
	public IngredientsException(Throwable exception)
	{
		this.wrappedException = exception;
	}	
	public IngredientsException(String message, Throwable exception)
	{
		super(message);
		this.wrappedException = exception;
	}
	
		
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	public Throwable getWrappedException() {
		return wrappedException;
	}
	public void setWrappedException(Throwable wrappedException) {
		this.wrappedException = wrappedException;
	}
	public boolean isToBeLogged() {
		return toBeLogged;
	}
	public void setToBeLogged(boolean toBeLogged) {
		this.toBeLogged = toBeLogged;
	}
	public String getStringStackTrace() {
		return stringStackTrace;
	}
	public void setStringStackTrace(String stringStackTrace) {
		this.stringStackTrace = stringStackTrace;
	}
	
	
	/**
	 * @return
	 */
	public long getErrorCode() {
		return errorCode;
	}

	/**
	 * @param l
	 */
	public void setErrorCode(long l) {
		errorCode = l;
	}

}
