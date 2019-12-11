package ca.gc.hc.nhpd.format;

import ca.gc.hc.nhpd.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple utility class for massaging names
 */
public class NameDisplayUtils {
	
	//TODO - could get from properties file...
	private static final int BREAK_LENGTH = 40;
	
	public static List<String> breakNames(List<String> names) {
	
		List<String> retNames = new ArrayList<String>();
		if (names != null) {
			for (String name: names) {
				//gets rid of unbreakable sequences
				if (name.length() > BREAK_LENGTH)
					retNames.add(StringUtils.breakText(name,BREAK_LENGTH));
				else
					retNames.add(name);
			}
		}
		return retNames;
	}
	
}
