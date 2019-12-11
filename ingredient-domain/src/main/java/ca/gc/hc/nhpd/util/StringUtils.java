package ca.gc.hc.nhpd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************************
 * Utility methods to support dealing with SQL. //TODO - add middle dot and
 * degree char? /* IE breaks: after a hyphen - after a percent sign % after a
 * degree sign ï¿½ after a middle dot ï¿½ before a left parenthesis ( after a right
 * parenthesis ) before a left bracket [ after a right bracket ] before a left
 * brace { after a right brace } before a left guillemet ï¿½ after a right
 * guillemet ï¿½ after an exclamation mark ! after a question mark ?
 */
public class StringUtils {
    
    private static final String BOOLEAN_YES = "y";
    private static final String BOOLEAN_NO = "n";
    private static final String BOOLEAN_TRUE = "t";
    private static final String BOOLEAN_FALSE = "f";
    
    private static final String BOOLEAN_EMPTY = "e";
    
    public static final String OPEN_BRACE = "[";
    public static final String CLOSE_BRACE = "]";
    public static final String OPEN_PARENTHESES = "{";
    public static final String CLOSE_PARENTHESES = "}";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String PERIOD = ".";

    private static final String NUMBERS_DASH_PATTERN = "[0-9\\-]";
    private static final Pattern numbersDashPattern = Pattern
            .compile(NUMBERS_DASH_PATTERN);

    public static final String EMPTY_STRING = "";
    
    public static final char[] regexSpecialChars = 
		{'[', '\\', '^', '$', '.', '|', '?', '*', '+', '(',')'};
    
    private static final String PLAIN_ASCII = "AaEeIiOoUu" // grave
            + "AaEeIiOoUuYy" // acute
            + "AaEeIiOoUuYy" // circumflex
            + "AaOoNn" // tilde
            + "AaEeIiOoUuYy" // umlaut
            + "Aa" // ring
            + "Cc" // cedilla
            + "OoUu" // double acute
    ;

    private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
            + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
            + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
            + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
            + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
            + "\u00C5\u00E5" + "\u00C7\u00E7" + "\u0150\u0151\u0170\u0171";

    public static String breakText(String text, int maxSize) {
        // just in case the caller has not checked.
        if (text.length() < maxSize)
            return text;

        String htmlBreak = "<br/>";

        // int minSegmentSize = 30;
        String retText = new String();

        char[] ieBreakChars = { ' ', '-', '%', '(', ')', '[', ']', '{', '}',
        		'«','»', '!', '?' };

        int index = 0; // position
        int prevBreakPos = 0; // where I want to start searching from
        int nextBreakPos = 0; // the closest break char of all possible matches

        while (prevBreakPos < text.length()) {
            nextBreakPos = text.length(); // initialize to high value
            // find next IE break character
            for (int x = 0; x < ieBreakChars.length; x++) {
                index = text.indexOf(ieBreakChars[x], prevBreakPos);
                // log ("Index Of: " + ieBreakChars[x] + "," + prevBreakPos +
                // " is: " + index );
                if (index > -1) // if break was found for character x
                {
                    nextBreakPos = Math.min(nextBreakPos, index);
                    // log("nextBreakPos set to: " + nextBreakPos);
                }
            }

            if (nextBreakPos < text.length()) {
                // log ("*************** Break at: " + nextBreakPos + "  Char: "
                // + text.charAt(nextBreakPos));

                if ((nextBreakPos - prevBreakPos) >= maxSize) {
                    // log ("New BP: " + prevBreakPos + ":" + nextBreakPos);
                    retText = retText.concat(breakString(text.substring(
                            prevBreakPos, nextBreakPos + 1), htmlBreak,
                            maxSize, ',')); // break after comma if possible
                } else {
                    retText = retText.concat(text.substring(prevBreakPos,
                            nextBreakPos + 1));
                }
            } else {
                // concat the rest
                retText = retText.concat(text.substring(prevBreakPos, text
                        .length()));
            }

            // advance to pass next break
            prevBreakPos = nextBreakPos + 1;

        }
        /*
         * log ("Exec time (nanoseconds):" + (System.nanoTime() - startTime));
         * log("\n\n"); log("Initial Text: " + text); log(" Broken Text: " +
         * retText);
         */
        return retText;
    }

    /*
     * alternateBreakChar character after which insertion of breaks is preferred
     * NB. could pass in multiple alternate break characters in a char[] later
     */
    private static String breakString(String unbrokenString,
            String breakString, int maxSize, char alternateBreakChar) {

        String retString = new String(breakString); // insert break at start
        int start = 0;
        int scanBackLength = maxSize / 4;
        int breakInsertPos = 0;
        /*
         * log ("Inserting " + numberOfBreaks + " breaks in unbrokenString: \n"
         * + unbrokenString); log ("----------------");
         */
        while (start < (unbrokenString.length() - maxSize)) {
            if (scanBackLength > 0) {
                breakInsertPos = unbrokenString.lastIndexOf(alternateBreakChar,
                        start + maxSize);
            }

            // break char found, but too far back
            if (breakInsertPos < (start + maxSize) - scanBackLength) {
                // log ("Concat: TFB: " + unbrokenString.substring(start, start
                // + maxSize));
                retString = retString.concat(unbrokenString.substring(start,
                        start + maxSize));
                retString = retString.concat(breakString);
                start += maxSize;
            } else
            // insert break after alternate break char
            if (breakInsertPos <= (start + maxSize)) {
                // log (" Concat: AC: " + unbrokenString.substring(start,
                // breakInsertPos+1));
                retString = retString.concat(unbrokenString.substring(start,
                        breakInsertPos + 1));
                retString = retString.concat(breakString);
                start = breakInsertPos + 1;
            }
        }
        // concat remaining unbreakable
        /*
         * log ("Concat remaining:  Start: " + start); log ("UB String length: "
         * + unbrokenString.length() +
         * unbrokenString.substring(start,unbrokenString.length()));
         */
        return retString.concat(unbrokenString.substring(start, unbrokenString
                .length()));

    }

    /*
     * Removes numbers and dashes from a text string
     */
    public static String removeNumbersAndDashes(String text) {
        Matcher matcher = numbersDashPattern.matcher(text);
        return matcher.replaceAll(EMPTY_STRING);

    }

    /*
     * Removes enclosing braces from a text string.
     */
    public static String removeEnclosingBraces(String text) {

        // if null or no open brace
        if (text == null || text.indexOf(OPEN_BRACE) == -1) {
            return text;
        }

        String retText = null;
        if (text.indexOf(OPEN_BRACE) != -1 && text.indexOf(CLOSE_BRACE) != -1) {
            retText = text.substring(text.lastIndexOf(OPEN_BRACE) + 1,
                    text.lastIndexOf(CLOSE_BRACE)).trim();
        }

        return retText;

    }

    /*
     * Extracts strings from multiple enclosing braces. e.g. given [23][24]
     * returns 23 24 in a String array. Useful for cases when dealing with multi
     * indexed objects.
     */
    public static String[] extractStringsFromMultipleBraces(String text) {

        String workText = new String(text);
        ArrayList<String> results = new ArrayList<String>();
        int startPos = 0;
        int endPos = 0;

        while (true) {
            startPos = workText.indexOf("[");
            endPos = workText.indexOf("]");
            if (startPos == -1) { // if not found get outta here.
                break;
            }
            results.add(workText.substring(startPos + 1, endPos));
            workText = workText.substring(endPos + 1);
        }

        String[] retArray = new String[results.size()];
        for (int x = 0; x < results.size(); x++) {
            retArray[x] = results.get(x);
        }

        return retArray;

    }

    /*
     * extracts terms from a space separated string
     */
    public static List<String> extractKeywords(String text) {
        if (text == null)
            return null;
        String[] splitText = text.split(" ");
        List<String> keywords = new ArrayList<String>();
        for (String keyword : splitText) {
            if (keyword.trim().length() > 0)
                keywords.add(keyword);
        }
        return keywords;
    }

    /*
     * Replaces all non-alpha and non-numeric characters with the provided
     * character.
     */
    public static String replaceSpecialCharacters(String text, char replaceChar) {
        if (text == null)
            return null;
        StringBuffer buff = new StringBuffer();

        for (int x = 0; x < text.length(); x++) {
            if (Character.isLetterOrDigit(text.charAt(x)))
                buff.append(text.charAt(x));
            else
                buff.append(replaceChar);
        }
        return buff.toString();

    }
    
    
    /*
	 * Removes specified characters from the provided text. 
	 *   
	 */  
	public static String removeCharacters(String text, 
									 	  char[] charactersToRemove) {
									 	 
		String removeString = null;
		String retString = new String(text);
		for (char character: charactersToRemove) {
			removeString = checkSpecialRegex(character);
			retString = retString.replaceAll(removeString,"");
		}
		return retString;
	}
    
	private static String checkSpecialRegex(char character) {
		for (int x=0;x<regexSpecialChars.length; x++) {
			if (regexSpecialChars[x] == character) {
				return new String("\\" + character);
			}
		}
		//stupid String class does not have a constructor taking a char...
		return new String("" + character);
		
	}
	
    
    /*
	 * Removes all non-alpha and non-numeric characters from the provided 
	 * text.  
	 */  
	public static String removeSpecialCharacters(String text) {
		if (text == null) return null;
		StringBuffer buff = new StringBuffer();

		for (int x=0;x<text.length();x++) {
			if ( Character.isLetterOrDigit(text.charAt(x))) 
				buff.append(text.charAt(x));
		}
		return buff.toString();	 

	}
	
	
	
	
	
    /*
     * Removes all terms from the provided text, replacing them with a single
     * space or null string if the term appears at the beginning or end of the
     * text. The provided termsToRemove should not contain any characters that
     * have special meaning to regular expressions. TODO - refactor for regex
     * handling.
     */
    public static String removeTerms(String text, List<String> termsToRemove,
            boolean ignoreCase) {
        // add leading and trailing space to simplify the algorithm
        String workText = " " + text + " ";
        if (ignoreCase) {
            workText = workText.toLowerCase();
        }
        for (String term : termsToRemove) {
            term = " " + term + " ";
            workText = workText.replaceAll(ignoreCase ? term.toLowerCase()
                    : term, " ");
        }

        // log("StringUtil.removeTerms returning: " + workText);
        return workText;
    }

    /*
     * Extracts strings from a string of quoted, comma separated strings and
     * places them into an array.
     */
    public static String[] extractStringArray(String stringValues) {
        if (stringValues == null)
            return null;

        String[] splitValues = stringValues.split(",");
        if (splitValues == null || splitValues.length < 1)
            return null;
        for (String value : splitValues) {
            value = value.replaceAll("\"", "").trim();
            // log ("ESA: " + value);
        }

        return splitValues;
    }

    /**
     * This method serves as a "toString()" like method for a byte array. The
     * byte array is converted into a textual representation of a string. This
     * method is necessary since the byte array toString method does not display
     * all the individual elements of the array. Currently, this method is going
     * to be housed within StringUtils. However, if enough byte related
     * utilities arise, a ByteUtils class would be a better place to house this
     * method. The textual representation of the byte array is delimited by a
     * ',' comma.
     * @param bytesForTextToInsert The byte array that will be represented as a
     *        string.
     * @return A string which represents the elements of a byte array. For
     *         example, if the byte array is {-8, 5, 3, 2} then the textual
     *         output would be "-8, 5, 3, 2".
     */
    public static String convertToString(byte[] bytes) {
        StringBuffer textualRepresentationOfByte = new StringBuffer();
        int i = 0;
        if (bytes != null) {
            for (byte aByte : bytes) {
                textualRepresentationOfByte.append(aByte);
                if (i != (bytes.length - 1)) {
                    textualRepresentationOfByte.append(",");
                }
                i++;
            }
        }
        return textualRepresentationOfByte.toString();
    }

    // replaces accented characters with ascii equivalents
    public static String convertNonAscii(String s) {
        if (s == null)
            return null;
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int pos = UNICODE.indexOf(c);
            if (pos > -1) {
                sb.append(PLAIN_ASCII.charAt(pos));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Extracts names from a delimited list.
     * 
     * @param names the list of names delimited by the delimiter
     * @param delimiter the string used as the delimiter
     * @param ignoreValue any name matching this value will not be placed in the
     *        result list
     * @return Sorted List of extracted names
     */
    public static List<String> extractDelimitedNames(String names,
            String delimiter, String ignoreValue) {

        TreeSet<LocaleSensitiveString> sortedNames = new TreeSet<LocaleSensitiveString>();
        ArrayList<String> retNames = new ArrayList<String>();
        String[] tempNames = null;

        if (names != null && names.trim().length() > 0) {
            tempNames = names.split(delimiter);
            for (int x = 0; x < tempNames.length; x++) {
                if (ignoreValue == null
                        || !tempNames[x].equalsIgnoreCase(ignoreValue)) {
                    sortedNames.add(new LocaleSensitiveString(tempNames[x]));
                }
            }
        }

        // translate into String List,which maintains order.
        for (LocaleSensitiveString sortedName : sortedNames) {
            retNames.add(sortedName.getString());
        }
        return retNames;

    }

    /* 
     * counts number of occurences of the specified character within the provided String
     * 
     */
    public static int countCharOccurences(String text, char character) {
        if (text == null || text.length() == 0) return 0;
        int count = 0;
        for (int x=0;x<text.length();x++) {
            if (text.charAt(x) == character) {
                count++; 
            }
        }
        return count;
    
    }
    
    public static boolean isEmpty (String text) {
        return (text == null || text.length() < 1);
    }
    
    public static boolean isAlpha (String text) {
        if (text == null || text.trim().length() == 0) return false;
        return (text.trim().matches("\\p{Alpha}+"));   //regex syntactic crud...
    }
    
    public static boolean isNumeric (String text) {
        if (text == null || text.trim().length() == 0) return false;
        return (text.trim().matches("\\p{Digit}+"));   //regex syntactic crud...
    }
    
    public static boolean isAlphaNumeric (String text) {
        if (text == null || text.trim().length() == 0) return false;
        return (text.trim().matches("\\p{Alnum}+"));   //regex syntactic crud...
    }
    public static String encloseWithBraces(int number) {
        return "[" + new Integer(number).toString() + "]";
    }
    
    /**
     * This private method is used to convert the Boolean value to an 
     * appropriate string for Hibernate purposes.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * @return string equivalent value for Hibernate purposes.
     */
    public static String booleanToString(Boolean value) {
        if (value == null) {
            return BOOLEAN_EMPTY;
        }
        if (value.equals(true)) {
            return BOOLEAN_YES;
        }
        if (value.equals(false)) {
            return BOOLEAN_NO;
        }
        return BOOLEAN_EMPTY;
    }
    
    /**
     * This private method is used to convert the String value to an 
     * appropriate Boolean for the object.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * 
     * @return Boolean equivalent value for the object to use.
     */
    public static Boolean stringToBoolean(String value) {
        if (value == null) {
            return null;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_YES)) {
            return true;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_NO)) {
            return false;
        }
        return null;
    }
        
    /**
     * This method attempts to convert the string into an appropriate
     * Boolean value.  This is new functionality added on 2009-10-13
     * to support entries that have no values.  For this reason, we
     * are now supporting null values.
     * 
     * <p>By default, any string that starts with a "y" is a true, 
     * any string that starts with a "n" is a false, and all other
     * cases are left as Boolean null values.
     * 
     * @param value being evaludated for conversion into a Boolean
     *              value.
     * 
     * @return True / False / Null depending on value string.
     */
    public static Boolean evaluateBooleanValue(String value) {
        if (value == null) {
            return null;
        } else if (value.toLowerCase().startsWith(BOOLEAN_YES)
                || value.toLowerCase().startsWith(BOOLEAN_TRUE)) {
            return true;
        } else if (value.toLowerCase().startsWith(BOOLEAN_NO)
                || value.toLowerCase().startsWith(BOOLEAN_FALSE)) {
            return false;
        } else {
            return null;    
        }
    }
    
    
}
