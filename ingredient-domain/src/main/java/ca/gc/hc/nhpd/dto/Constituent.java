package ca.gc.hc.nhpd.dto;

import ca.gc.hc.nhpd.model.PersistentObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This object was created in response to IMSD Issue 2355 in which the constituents need to be expanded to display
 * Approved Names, Common Names, and Proper Names.  To accomodate this change, the SOURCEORGANISMPARTS_MV was 
 * modified to return a parameterized version of the CONSTITUENTS_ENG and CONSTITUENTS_FR that also included the
 * other names.  
 * 
 * This object takes this HTML(ish) formated string and breaks it down into appropriate attributes for digestion
 * by the web services.  An example of this format is (broken to 80 characters):
 * 
 * <ap>Zeaxanthin</ap><cn>Zeaxanthin</cn><pn>(3R,3'R)-Dihydroxy-beta-carotene<b
 * r/>all-trans-beta-Carotene-3,3'-diol * </pn>~<ap>Lutein(USP)</ap><cn>Lutein<
 * /cn><pn>(3R,3´R,6´R)-beta,epsilon-Carotene-3,3´-diol</pn>~<ap>Lutein(USP)</a
 * p><cn>Lutein</cn><pn>(3R,3´R,6´R)-beta,epsilon-Carotene-3,3´-diol</pn>~<ap>L
 * utein</ap><cn>Lutein</cn><pn>(3R,3´R,6´R)-beta,epsilon-Carotene-3,3´-diol</p
 * n>
 * 
 * Individual Components are TILDA (~) seperated.  Inside a component, you find the Approved Name (<ap></ap>), the
 * common name(s) (<cn></cn>) and proper names(s) (<pn></pn>).
 *
 * Note:  This object extends the persistent object to leverage the isLanguageFrench method.  This object
 * is never saved in Hibernate since it's a product of a Materialized View. 
 * 
 * @author GEBRUNET
 *
 */

public class Constituent extends PersistentObject {

    private final static String AUTHORIZED_NAME_START_TAG = "<ap>";

    private final static String AUTHORIZED_NAME_END_TAG = "</ap>";

    private final static String COMMON_NAME_START_TAG = "<cn>";

    private final static String COMMON_NAME_END_TAG = "</cn>";

    private final static String PROPER_NAME_START_TAG = "<pn>";

    private final static String PROPER_NAME_END_TAG = "</pn>";

    private final static String BREAK_STRING = "<br/>";

    private String authorizedNameEnglish;

    private String authorizedNameFrench;

    private List<String> commonNamesEnglish;

    private List<String> commonNamesFrench;

    private List<String> properNamesEnglish;

    private List<String> properNamesFrench;

    /*----------------------------------------------------------------
     * Constructors
     *----------------------------------------------------------------*/
    
    // Generic Constructor used to initialize this object.
    public Constituent() {}
    
    /* Constructor used when this object is being constructed using the information
     * from the materialized view.
     */
    public Constituent(String constituentConstructEnglish,
            String constituentConstructFrench) {

        authorizedNameEnglish = extractAuthorizedName(constituentConstructEnglish);
        authorizedNameFrench = extractAuthorizedName(constituentConstructFrench);
        commonNamesEnglish = extractCommonNames(constituentConstructEnglish);
        commonNamesFrench = extractCommonNames(constituentConstructFrench);
        properNamesEnglish = extractProperNames(constituentConstructEnglish);
        properNamesFrench = extractProperNames(constituentConstructFrench);

    }

    /*----------------------------------------------------------------
     * Standard Accessors
     *----------------------------------------------------------------*/
    
    public String getAuthorizedNameEnglish() {
        return authorizedNameEnglish;
    }

    public String getAuthorizedNameFrench() {
        return authorizedNameFrench;
    }

    public String getAuthorizedName() {
        if (isLanguageFrench()) {
            return authorizedNameFrench;
        } else {
            return authorizedNameEnglish;
        }
    }
    
    public List<String> getCommonNamesEnglish() {
        return commonNamesEnglish;
    }

    public List<String> getCommonNamesFrench() {
        return commonNamesFrench;
    }

    public List<String> getCommonNames() {
        if (isLanguageFrench()) {
            return getCommonNamesFrench();
        } else {
            return getCommonNamesEnglish();
        }
    }
    
    public List<String> getProperNamesEnglish() {
        return properNamesEnglish;
    }

    public List<String> getProperNamesFrench() {
        return properNamesFrench;
    }

    public List<String> getProperNames() {
        if (isLanguageFrench()) {
            return getProperNamesFrench();
        } else {
            return getProperNamesEnglish();
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Authorized Name English: " + authorizedNameEnglish + ", ");
        sb.append("Authorized Name French: " + authorizedNameFrench + ", ");
        sb.append("Common Name English: " + commonNamesEnglish + ", ");
        sb.append("Common Name French: " + commonNamesFrench + ", ");
        sb.append("Proper Name English: " + properNamesEnglish + ", ");
        sb.append("Proper Name French: " + properNamesFrench);
        return sb.toString();
    }

    /*----------------------------------------------------------------
     * Private Methods
     *----------------------------------------------------------------*/
    
    /**
     * Extracts the authorized name from the textString. 
     */
    static private String extractAuthorizedName(String textString) {
        return extractText(textString, AUTHORIZED_NAME_START_TAG,
                AUTHORIZED_NAME_END_TAG);
    }

    /**
     * Extracts the common name(s) from the textString. 
     */
    static private List<String> extractCommonNames(String textString) {
        return breakText(extractText(textString, COMMON_NAME_START_TAG,
                COMMON_NAME_END_TAG));
    }

    /**
     * Extracts the proper name(s) from the textString. 
     */
    static private List<String> extractProperNames(String textString) {
        return breakText(extractText(textString, PROPER_NAME_START_TAG,
                PROPER_NAME_END_TAG));
    }

    /**
     * Breaks a textString into a list using the BREAK_STRING (in this case the "<br/>") as seperator.  
     */
    static private List<String> breakText(String textString) {
        if (textString == null) {
            return null;
        }
        String[] list = textString.split(BREAK_STRING);
        List<String> breakText = new ArrayList<String>();
        for (String item : list) {
            breakText.add(item);
        }
        return breakText;
    }

    /**
     * Given a string of text, start and end tags, this function returns a list
     * of string values that correspond to the extracted text string.
     * 
     * @param textString
     *            being extracted
     * @param startTag
     *            defining the start of the text
     * @param endTag
     *            defining the end of the text
     * @return a list of text string extracted from the textString
     */
    static private String extractText(String textString, String startTag,
            String endTag) {

        if (textString == null || (startTag == null) || (endTag == null)) {
            return null;
        }

        int startIndex = -1;
        int endIndex = -1;

        String extractText = null;

        boolean found = true;
        while (found) {
            startIndex = textString.indexOf(startTag, startIndex);
            endIndex = textString.indexOf(endTag, startIndex);
            if (startIndex == -1 || endIndex == -1) {
                found = false;
            } else {
                extractText = textString.substring(startIndex
                        + startTag.length(), endIndex);
                startIndex = startIndex + 1;
            }
        }
        return extractText;
    }
}
