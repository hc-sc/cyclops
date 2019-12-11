package ca.gc.hc.nhpd.util;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;

/*
 * String comparator which implements Locale specific, case insensitive 
 * comparisons of 2 Strings.  Provides proper sorting of strings containing 
 * accented French characters, which is not provided by default Java
 * string comparison.  Works for both French and English comparisons.
 */
public class StringComparator {

	private static RuleBasedCollator genericCollator = 
		(RuleBasedCollator)Collator.getInstance(Locale.CANADA_FRENCH);
	
	public static int compare(String string1, String string2) {
		
		if (string1 == null) return 1;
		if (string2 == null) return -1; 
		
		return genericCollator.compare(
			string1.toLowerCase(Locale.CANADA_FRENCH),
			string2.toLowerCase(Locale.CANADA_FRENCH));
		
	}
		
}
