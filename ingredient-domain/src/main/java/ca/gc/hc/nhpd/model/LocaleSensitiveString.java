package ca.gc.hc.nhpd.model;
import ca.gc.hc.nhpd.util.StringComparator;
/*
 * Simple wrapper class used for sorting Strings.  This class was developed
 * to simplify the sorting of String objects in a locale specific way.
 * Too bad we can't subclass String - its final!
 */
public class LocaleSensitiveString implements Comparable {
	private String string;

	public LocaleSensitiveString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public int compareTo(Object obj) throws ClassCastException {
		if (obj == null) return -1;
		return StringComparator.compare(string, ((LocaleSensitiveString)obj).getString());
	}

}
