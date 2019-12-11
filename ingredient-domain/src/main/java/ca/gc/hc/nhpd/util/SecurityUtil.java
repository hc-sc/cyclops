package ca.gc.hc.nhpd.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * Utility methods to support security.
 */
public class SecurityUtil {
    private static final Log LOG = LogFactory.getLog(SecurityUtil.class);
    
    /***************************************************************************
     * Gets a hexadecimal representation of an MD5 hash of the passed String.
     * Note that, for convenience, this absorbs an exception that should never
     * happen.
     * @param aString the String to be hashed.
     * @return the resulting hexadecimal MD5 hash.
     */
    public static String getMD5Hash(String aString) {
        byte[] hash;
        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(aString.getBytes());
            hash = digest.digest();
            for (int i=0; i<hash.length; i++) {
                hexString.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).
                                 substring(1,3));
            }
            return hexString.toString();
            
        }catch(NoSuchAlgorithmException nsae){ // Should never happen!
            LOG.error("Fatal error when trying to MD5 hash secrets.");
            return aString;
        }        
    }

}
