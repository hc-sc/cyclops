package ca.gc.hc.nhpd.exception;
/*
* This class provides a generic runtime exception for any NHPD   
* application.  It assists in implementing the "single runtime exception"
* pattern.  See http://www.mindview.net/Etc/Discussions/CheckedExceptions
* 
* @author: M. Rabe
* @since: Mar 2010
*
*/

public class NhpdException extends RuntimeException {
    public static final long serialVersionUID = 1234098713261234098L;
    private int severity;
	private long errorCode;   //TBD
	private boolean toBeLogged;
	private Throwable wrappedException;
	private String stringStackTrace;
	
	public NhpdException() {
		super();
	}
	public NhpdException(String message) {
		super(message);
	}
	
	public NhpdException(Throwable exception) {
		this.wrappedException = exception;
	}	
	
	public NhpdException(String message, Throwable exception) {
		super(message);
		this.wrappedException = exception;
	}
	
    public int getSeverity() {
		return severity;
	}
    
    public Throwable getWrappedException() {
        return wrappedException;
    }
    
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	/*
	public Throwable getWrappedException() {
		return wrappedException;
	}
	public void setWrappedException(Throwable wrappedException) {
		this.wrappedException = wrappedException;
	}
	*/
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
