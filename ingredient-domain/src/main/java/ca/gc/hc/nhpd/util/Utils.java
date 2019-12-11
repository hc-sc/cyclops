/*
 * Created on Jan 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ca.gc.hc.nhpd.util;

/**
 * @author Germain Brunet
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utils {

	public static String getShortName( String longName ) {
        
		if ( longName == null ) return null;
		if ( longName.indexOf( "." ) == -1 ) return longName;
        
		return longName.substring( longName.lastIndexOf( "." ) + 1, longName.length() );
	}
    
}
