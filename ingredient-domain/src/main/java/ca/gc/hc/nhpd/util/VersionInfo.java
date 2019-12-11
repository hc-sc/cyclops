package ca.gc.hc.nhpd.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import ca.gc.hc.nhpd.conf.SpringContext;

/**
 * The version info class is responsible for collecting and returning 
 * application version information as a hashmap.  This is then used
 * by both the Ingredient Web Application and by the Ingredient and
 * Monograph web services to give out information pertaining the 
 * deployment.
 * 
 * @author GEBRUNET
 *
 */
public class VersionInfo {

    static private final String HIBERNATE = "hibernate";
    static private final String PERIOD = ".";
    static private final String HIBERNATE_OS_NAME = "os.name";
    static private final String HIBERNATE_JAVA_VERSION = "java.version";
    static private final String HIBERNATE_DIALECT = "hibernate.dialect";
    static private final String HIBERNATE_CONNECTION_DATASOURCE =
        "connection.datasource";
    static private final String HIBERNATE_VERSION = 
        "version";
    static public final String VERSION_LIST_SEPERATOR = ": ";

    private HashMap<String, String> versionMap = new HashMap<String, String>();

    /**
     * Returns a hash map of version related information.
     * 
     * @return a hash map of version related information.
     */
    public HashMap<String, String> getVersionMap() {
        oracleProperties();
        hibernateProperties();
        return versionMap;
    }
    
    /**
     * Return the hash map of version related information in a list format 
     * for easy digestion by the web services.
     * 
     * @return the version information in a list format.
     */
    public List<String> getVersionList() {
    	List<String> versionList = new ArrayList<String>();
    	for (String key : getVersionMap().keySet()) {
    		StringBuffer sb = new StringBuffer();
    		sb.append(key);
    		sb.append(VERSION_LIST_SEPERATOR);
    		sb.append(getVersionMap().get(key));
    		versionList.add(sb.toString());
    	}
    	return versionList;
    }
    
    /**
     * Extract and add to the properties object a number of common Oracle
     * properties. In addition, it accesses the oracle APPLICATION_PROPERTIES table
     * and adds the available values to the properties object.
     */
    private void oracleProperties() {
        
        // Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	Session session = SpringContext.getSession();
        
        Transaction tx = session.beginTransaction();

        try {

            // Get the banner.
            SQLQuery sq = session.createSQLQuery("select banner from sys.v_$version");
            List<String> results = sq.list();

            for (String result : results) {
                
                int keyBreak = result.indexOf("\t");

                if (keyBreak == -1) {
                    keyBreak = result.indexOf(" ");
                }

                if (keyBreak == -1) {
                    keyBreak = result.length();
                }

                String key = result.substring(0, keyBreak);
                versionMap.put("oracle.banner." + key, result);
            }

            /* user from global_name was removed for security reasons.
             * st = session.connection().createStatement(); rs =
             * st.executeQuery("select user from global_name");
             *
             * while (rs.next()) { String s = rs.getString("user");
             * properties.put("oracle.user", s); }
             */

            getDBValueForVersion(session, "oracle.global_name", "select global_name from global_name");
            getDBValueForVersion(session, "oracle.NLS_CHARACTERSET", "select value from nls_session_parameters where parameter = 'NLS_CHARACTERSET'");
            getDBValueForVersion(session, "oracle.NLS_SORT", "select value from nls_session_parameters where parameter = 'NLS_SORT'");
            
            
            Query query=session.createSQLQuery("select key,data,flag from APPLICATION_PROPERTIES");
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String,Object>> aliasToValueMapList = (List<Map<String,Object>>) query.list();
            
            for (Map<String,Object> aliasValue : aliasToValueMapList) {
            	
                String key = (String) aliasValue.get("KEY");
                String value = (String) aliasValue.get("DATA");
                BigDecimal flag = (BigDecimal) aliasValue.get("FLAG");
                if (flag != null && !flag.equals(new BigDecimal(0))) {
                	value = value + " (" + flag + ")";
                }
                versionMap.put(key, value);
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        tx.commit();

    }
    
    private void getDBValueForVersion(Session session, String key, String query) {
    	SQLQuery sq = session.createSQLQuery("select banner from sys.v_$version");
    	List<String> value = sq.list();
    	versionMap.put(key, value.get(0));
    }

    /**
     * Extract and add to the properties object a number of common Hibernate
     * properties. These include the os_name, the java_vm_version, the Hibernate
     * dialect and the connection_datasource.
     */
    private void hibernateProperties() {
    	
    	versionMap.put(
            HIBERNATE + PERIOD + HIBERNATE_OS_NAME,
            HibernateUtil.getConfiguration().getProperties().getProperty(
                HIBERNATE_OS_NAME));
    	versionMap.put(
            HIBERNATE + PERIOD + HIBERNATE_JAVA_VERSION,
            HibernateUtil.getConfiguration().getProperties().getProperty(
                HIBERNATE_JAVA_VERSION));
    	versionMap.put(
            HIBERNATE + PERIOD + HIBERNATE_DIALECT,
            HibernateUtil.getConfiguration().getProperties().getProperty(
                HIBERNATE_DIALECT));
    	versionMap.put(
            HIBERNATE + PERIOD + HIBERNATE_CONNECTION_DATASOURCE,
            HibernateUtil.getConfiguration().getProperties().getProperty(
                HIBERNATE_CONNECTION_DATASOURCE));
        versionMap.put(
                HIBERNATE + PERIOD + HIBERNATE_CONNECTION_DATASOURCE,
                HibernateUtil.getConfiguration().getProperties().getProperty(
                    HIBERNATE_CONNECTION_DATASOURCE));
        versionMap.put(
                HIBERNATE + PERIOD + HIBERNATE_VERSION,
                org.hibernate.Version.getVersionString());
    }
        
}