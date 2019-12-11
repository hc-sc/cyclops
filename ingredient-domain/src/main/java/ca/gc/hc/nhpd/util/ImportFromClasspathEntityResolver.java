package ca.gc.hc.nhpd.util;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.internal.util.xml.DTDEntityResolver;
import org.xml.sax.InputSource;

/**
 * A class borrowed from the auction example at http://caveatemptor.hibernate.org
 * -Dwight Hubley, 2006-04-11
 * <br><br>
 * Extends Hibernate's default resolver with lookup of entities on classpath.
 * <p>
 * For example, the following can be resolved:
 * <pre>
 * <?xml version="1.0"?>
 * <!DOCTYPE hibernate-mapping SYSTEM "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd"
 * [
 * <!ENTITY usertypes SYSTEM "/org/hibernate/ce/auction/persistence/UserTypes.hbm.xml">
 * ]>
 *
 * <hibernate-mapping>
 *
 * &usertypes;
 * ...
 * </pre>
 * The file will be looked up in the classpath. Don't forget the leading slash!
 * Relative location is not supported.
 * 
 * Note:  This class was extended by Germain Brunet to allow DTD file loading from 
 * a project resource folder.
 *
 * @author christian.bauer@jboss.com
 */
public class ImportFromClasspathEntityResolver extends DTDEntityResolver {
    
    public static final long serialVersionUID = 1234028716728165623L;

    protected static final Log log = LogFactory.getLog(ImportFromClasspathEntityResolver.class);

    // This is the prefix of SYSTEM entity identifiers which are not URLs
    private static final String PREFIX = "file://";

    // Extension of DTD files.
    private static final String DTD_EXTENSION = ".dtd";
    static private final String SLASH = "/";
    private static final String RESOURCE_PATH = "";

    public InputSource resolveEntity(String publicId, String systemId) {
        // log.debug("Trying to resolve system id: " + systemId);
        
        String resource = null;
        
        // Lookup the file based on the PREFIX (Original Code)
        if (systemId != null && systemId.startsWith(PREFIX)) {
            // Remove the initial slash to look it up
            resource = systemId.substring(PREFIX.length());
        }
        
        // Lookup the file based on the extension (New Code)
        if (systemId != null && systemId.toLowerCase().endsWith(DTD_EXTENSION)) {
            // Extract the file name and add the path information to look it up.
            resource = RESOURCE_PATH + extractFileName(systemId);
        }
        
        if (resource != null) {
            // log.debug("Looking up entity on classpath: " + resource);

            InputStream stream = getClass().getResourceAsStream( resource );
            if ( stream == null ) {
                stream = getClass().getClassLoader()
                        .getResourceAsStream( resource );
            }
            if ( stream == null ) {
                stream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream( resource );
            }
            if ( stream == null ) {
                log.error("Couldn't find entity in classpath: " + resource);
            } else {
                // log.debug("Found entity on classpath: " + resource);
                InputSource source = new InputSource(stream);
                source.setPublicId(publicId);
                source.setSystemId(systemId);
                return source;
            }
        }

        return super.resolveEntity(publicId, systemId);
    }

    /**
     * This function extracts the file name from a given systemId.
     * 
     * @param uri containing a file we are referencing.
     * 
     * @return the file name portion of the systemId.
     */
    private String extractFileName(String systemId) {
        int lastSlash = systemId.lastIndexOf(SLASH);
        if (lastSlash == -1) {
            // not found;
            return systemId;
        }
        String outputString = systemId.substring(lastSlash + SLASH.length(), systemId.length()); 
        return outputString;
    }
    
}


