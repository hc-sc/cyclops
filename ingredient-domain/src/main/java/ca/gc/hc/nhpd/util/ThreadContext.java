package ca.gc.hc.nhpd.util;

import ca.gc.hc.nhpd.exception.NhpdException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Locale;
/*
 * This class encapsulates common request specific infrastructure type
 * attributes such as Locale and user principal data.   
 * 
 * Added support for the version.
 */

public class ThreadContext implements LocaleSource {
	private static ThreadContext instance;
   
    private static ThreadLocal<Locale> userLocale = new ThreadLocal<Locale>();
    private static ThreadLocal<String> threadLocalUserId = new ThreadLocal<String>();
    private static ThreadLocal<String> userVersion = new ThreadLocal<String>();
    private static ThreadLocal<Boolean> displayVersion = new ThreadLocal<Boolean>();
   
    public static final String VERSION_1_4 = "1.4";
    public static final String VERSION_2_0 = "2.0";
    public static final String VERSION_VERSION = "version";
    
    //although we should be using the ISO 639-2 3 character values, these
    //values are also recognisable.
    private static String[] englishValues = {"e","en","eng"};
    private static String[] frenchValues = {"f","fr","fra","fre"};
    private static String[] versionValues = {VERSION_1_4, VERSION_2_0, VERSION_VERSION};
    
    public static final String LANG_EN = Locale.ENGLISH.getISO3Language(); //ISO 639.2
    public static final String LANG_FR = Locale.CANADA_FRENCH.getISO3Language(); //ISO 639.2
    public static final String VERSION_DEFAULT = "1.4";
    
    private static final Log log = LogFactory.getLog(ThreadContext.class);
    
    static {
        instance = new ThreadContext();
    }
    
    /***************************************************************************
     * Gets the single instance of this class.
     */
    public static ThreadContext getInstance() {
        return instance;
    } 
    
    /**
     * Sets the version based on the information provided in the header.
     */
    public void setVersion(String version) {
    
    	// Return the default version when no version is supplied.
    	if (version == null || version.length() == 0) {
			log.info("No version supplied.  Setting the version to default: " + VERSION_DEFAULT);
    		userVersion.set(VERSION_DEFAULT);
    	}

    	// Are we trying to display version related information?
    	if (VERSION_VERSION.equals(version)) {
    		displayVersion.set(new Boolean(true));
    		userVersion.set(VERSION_DEFAULT);
    	} else {
    		displayVersion.set(false);
    	}
    	
    	// Try to set the version if it's contained as an allowable version
    	// in the version values.
    	for (String versionValue : versionValues) {
    		if (versionValue.equals(version)) {
    			log.info("Version supplied.  Setting the version to: " + version);
    			userVersion.set(version);
    			return;
    		}
    	}
    	
    	// Set the default value.
		log.info("Version provided not in allowable version list (" + version + ").  Defaulting version to: " + VERSION_DEFAULT);
    	userVersion.set(VERSION_DEFAULT);
    	
    }
    
    /**
     * Return the version number for this web service call.
     * 
     * @return the version number for this web service call.
     */
    public String getVersion() {
    	String version = (String) userVersion.get();
    	if (version == null || version.length() == 0) {
    		return VERSION_DEFAULT;
    	}
    	return version; 
    }
    
    /*
     * Sets the user's Locale based on a character string which provides the language.  
     */
    public void setUserLanguage(String language) {
        
    	String lang = language.toLowerCase();
    	for (int x=0;x< frenchValues.length;x++)
    	{
    		if (lang.equals(frenchValues[x]))
    		{
    			userLocale.set(Locale.CANADA_FRENCH);
    			log.info("Setting user language to: " + LANG_FR);
    			return;
    		}
    	}
    	
    	for (int x=0;x< englishValues.length;x++)
    	{
    		if (lang.equals(englishValues[x]))
    		{
    			userLocale.set(Locale.ENGLISH);
    			log.info("Setting user language to: " + LANG_EN);
    			return;
    		}
    	}
    	
    	userLocale.set(Locale.ENGLISH);
    	log.warn("Unrecognized language: " + language 
    			+ "User Language set to default: " + LANG_EN);
    		
    } 
    
    /*
     * Returns the user's ISO 3 character language.  Defaults to English if the
     * Locale is not set. 
     */
    public String getUserLanguage()
    {
    	Locale locale = getUserLocale();  //inits Locale if it has not been set.
    	return locale.getISO3Language();	
    }
    
    public boolean isLanguageFrench() {
		return getUserLanguage().equals(LANG_FR);
	}
    
    /*
     * Gets the Locale of the user.  If it has not been set, defaults to English.
     * @return The locale previously set by a call to setUserLanguage().
     */
    public Locale getUserLocale() {
        Locale locale = (Locale)userLocale.get();

        if (locale == null) {
            log.warn("User Locale not set: initializing to default: Locale.ENGLISH");
            locale = Locale.ENGLISH; //Default value
            userLocale.set(locale);
        }

        return locale;
    }  
    
    /*
     * Gets the userId.  If not provided, throws an exception to that effect.
     * @return the User id for this thread. 
     */
    public String getUserId() {
        //String userId = (String)this.userId;
        if (threadLocalUserId == null) {
            log.error("User Id not provided: initializing to default: Locale.ENGLISH");
            throw new NhpdException("User Id not provided; request refused");
            
        }
        
        return threadLocalUserId.get();
    } 
    
    /*
     * Sets the user id for the current thread.  
     */
    public void setUserId(String userId) {
    	threadLocalUserId.set(userId);
    }  
    
    /*
     * Gets the display version flag.  This flag is true when a soap call is made
     * with the version flag equal to the word version.  This allows the web service
     * to return version specific information.
     */
    public Boolean getDisplayVersion() {
    	Boolean displayVersionFlag = (Boolean) displayVersion.get();
    	if (displayVersionFlag == null) {
    		return false;
    	}
    	
    	return displayVersionFlag;
    }
    
    /*
     * Sets the display version flag.
     */
    public void setDisplayVersion(Boolean displayVersionFlag) {
    	displayVersion.set(displayVersionFlag);
    }
    
}
