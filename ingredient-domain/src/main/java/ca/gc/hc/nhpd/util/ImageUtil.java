package ca.gc.hc.nhpd.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/*******************************************************************************
 * Utility methods to support getting images through Hibernate.
 */
public class ImageUtil {
    
    /***************************************************************************
     * Converts an SQL Blob to a byte array. This must be done to read the entire
     * byte array into memory at once. Note that this currently throws a runtime
     * exception if a problem occurs.
     * @param fromBlob the blob returned by Hibernate.
     * @return the byte array in the blob.
     * @throws IOException
     * @throws SQLException
     */
    public static byte[] toByteArray(Blob fromBlob) throws IOException,
                                                           SQLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4000];
        int data;
        InputStream inStream = null;

        try {
            inStream = fromBlob.getBinaryStream();
            data = inStream.read(buffer);
            while (data != -1) {
                baos.write(buffer, 0, data);
                data = inStream.read(buffer);
            }
            return baos.toByteArray();

        } catch (SQLException e1) {
            throw e1;
        } catch (IOException e2) {
            throw e2;

        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ex) {} //Nothing to do
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ex) {} //Nothing to do
            }
        }
    }
}
