package ca.gc.hc.nhpd.util;

/**
 * This class keeps all generic methods and constants that can be used for
 * addresses.
 */
public class AddressUtils {

	/**
	 * See:
	 * http://wiki.answers.com/Q/What_is_the_lowest_ZIP_code_number_in_the_US
	 */
    public static final int LOWEST_ZIP_CODE = 210;
    
    //a-z, 0-9, [, ]   (ignore case)
    private static String domainRegex = "[\\p{Alnum}\\.\\-]*"; 
    private static String ipAddressRegex = "[\\p{Digit}\\.]*"; 
    
    /**
	 * Validates a Canadian postal code.  Strips spaces out.
	 */
    public static boolean isValidPostalCode(String postalCode) {
        
        if (postalCode == null || postalCode.length() < 6) {
            return false;
        }
        
        //remove spaces
        String strippedCode = postalCode.replaceAll(" ","");
        if (strippedCode.length() != 6) {
            return false;
        }
        
        if (!StringUtils.isAlpha  (strippedCode.substring(0,1))) return false;
        if (!StringUtils.isNumeric(strippedCode.substring(1,2))) return false;
        if (!StringUtils.isAlpha  (strippedCode.substring(2,3))) return false;
        if (!StringUtils.isNumeric(strippedCode.substring(3,4))) return false;
        if (!StringUtils.isAlpha  (strippedCode.substring(4,5))) return false;
        if (!StringUtils.isNumeric(strippedCode.substring(5,6))) return false;
        
        return true;
    
    }
    
    /**
     * Verifies US Zip codes.  These can be either in a simple format
     * (5 digits) or extended format (nnnnn-nnnn).
     * I'll allow 99999 as a valid zip code for potential future
     * consideration.  This is validated as the maximum number by
     * allowing a maximum string length of 5.
     */
    public static boolean isValidZipCode(String zipCode) {
        //log(" AddressUtils: isValidZipCode");
        if (zipCode == null) return false;
      
    	String tempZip = zipCode.trim(); 
    	
    	//plain (non-extended)
    	if (tempZip.length() == 5) {
    		return validatePlainZipCode(tempZip);
    	}
    	
    	//extended format
    	if (tempZip.length() != 10) {
    		return false;
    	}
    		
    	if (tempZip.charAt(5) != '-') {
    		return false;
    	}
   			
    	if (!validatePlainZipCode(tempZip.substring(0,5))) {
    		return false;
    	}
   		
    	try {
    		new Integer(tempZip.substring(6,10));
	    }
	    catch (NumberFormatException ex) {
	        return false;
	    }
		
        return true;
    
    }
    
    private static boolean validatePlainZipCode(String zipCode) {
    			
    	Integer zipCodeInt = null;
    	try {
	        zipCodeInt = new Integer(zipCode);
	    }
	    catch (NumberFormatException ex) {    
	    	return false;
	    }
	    if (zipCodeInt.intValue() < LOWEST_ZIP_CODE) {
	    	return false;
	    }
		
	    return true;
    }
    
    
    
   
    /**
	 * Simple validation for email address.  Implements most of the rules found here:
	 * 	http://en.wikipedia.org/wiki/Email_address#Syntax:
	 * 
	 * - everything before the last @ is the local part
	 * - only validation on the local part is that it can not contain 2 consecutive ..
	 * - everything after the last @ is the domain
	 * - domain can be either:
	 * 	 a) ip address format; e.g: [192.127.34.29]
	 *   b) regular format: letters, digits, hyphens and dots  
	 * 
	 * Alternatively, a large regex is an option:  
	 * 	http://haacked.com/archive/2007/08/21/i-knew-how-to-validate-an-email-address-until-i.aspx
	 * 
	 */
	public static boolean isValidEmail(String email) {
		
		if (StringUtils.isEmpty(email)) return false;
		
		String tempEmail = email.trim();
		
		//find last @
		int atIndex = tempEmail.lastIndexOf("@");
		if (atIndex == -1) return false;

		//2 consecutive periods: not allowed
		if (tempEmail.indexOf("..") != -1) {
			return false;
		}
		
		//check domain name
		String domain = tempEmail.substring(++atIndex,tempEmail.length());
		if (domain.length() < 1) {
			return false;
		}
		
		//IP address domain format
		if (domain.charAt(0) == '[') {
			//log ("Domain end: " + domain.charAt(domain.length() -1));
			if (domain.charAt(domain.length()-1) != ']')
				return false;
			else {
				String ipAddress = domain.substring(1,domain.length() -1);
				//log ("Ip address: " + ipAddress);
				if (ipAddress.length() < 7) return false;  //minimum 1.1.1.1
				return ipAddress.matches(ipAddressRegex); 
			}
		}
		
		return domain.matches(domainRegex); 
		
	}
	
	/*
	private static void log(String msg) {
		 System.out.println(msg);
		 System.out.flush();
	}
	
    public static void main(String[] args) {
    	log ("------------------------------------");	
    	log (" REGEX Expression: " + domainRegex);
    	log ("------------------------------------");	
    	
    	if (AddressUtils.isValidEmail("abc@[12.13.14.15]")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("abc@xyz.com")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("abc@xyz.com..")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("a.bc@xyz.com")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("ab..c@xyz.com")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("abc@xyz.com.com")) 
    		log ("OK"); else log("FAILED");
    	if (AddressUtils.isValidEmail("abc@xyz.com.#")) 
    		log ("OK"); else log("FAILED");
    }
    */
	   
    
}
