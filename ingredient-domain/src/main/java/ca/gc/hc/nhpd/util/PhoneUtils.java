/*
 * Created on Jan 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ca.gc.hc.nhpd.util;

/**
 * @author Mike Rabe
 *
 * Simple class to validate phone numbers 
 */
public class PhoneUtils {
	//TBD - rules on max length?
	private static final int MIN_LEN = 7;
	
	private static char[] phoneCharsToRemove = {'(', ' ', '-', ')','#'}; 
	
	public static boolean validate( String phoneNumber ) {
		if (StringUtils.isEmpty(phoneNumber)) return false;
		String strippedNumber =  
			StringUtils.removeCharacters(phoneNumber, phoneCharsToRemove);
		if (strippedNumber.length() < MIN_LEN) {
			return false;
		}
		return StringUtils.isNumeric(strippedNumber);	
	}
    
}
