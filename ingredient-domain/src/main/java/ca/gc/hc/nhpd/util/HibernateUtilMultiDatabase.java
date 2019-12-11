package ca.gc.hc.nhpd.util;

import ca.gc.hc.nhpd.exception.NhpdException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.Transaction;
// import org.hibernate.util.XMLHelper;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * A class borrowed from the auction example at http://caveatemptor.hibernate.org
 * -Dwight Hubley, 2006-04-11
 * <br><br>
 * Basic Hibernate helper class for Hibernate configuration and startup.
 * <p>
 * Uses a static initializer to read startup options and initialize
 * <tt>Configuration</tt> and <tt>SessionFactory</tt>.
 * <p>
 * This class also tries to figure out if JNDI binding of the <tt>SessionFactory</tt>
 * is used, otherwise it falls back to a global static variable (Singleton). If
 * you use this helper class to obtain a <tt>SessionFactory</tt> in your code,
 * you are shielded from these deployment differences.
 * <p>
 * Another advantage of this class is access to the <tt>Configuration</tt> object
 * that was used to build the current <tt>SessionFactory</tt>. You can access
 * mapping metadata programmatically with this API, and even change it and rebuild
 * the <tt>SessionFactory</tt>.
 * <p>
 * If you want to assign a global interceptor, set its fully qualified
 * class name with the system (or hibernate.properties/hibernate.cfg.xml) property
 * <tt>hibernate.util.interceptor_class</tt>. It will be loaded and instantiated
 * on static initialization of HibernateUtil; it has to have a
 * no-argument constructor. You can call <tt>HibernateUtil.getInterceptor()</tt> if
 * you need to provide settings before using the interceptor.
 * <p>
 * Note: This class supports annotations by default, hence needs JDK 5.0
 * and the Hibernate Annotations library on the classpath. Change the single
 * commented line in the source to make it compile and run on older JDKs with
 * XML mapping files only.
 * <p>
 * Note: This class supports only one data store. Support for several
 * <tt>SessionFactory</tt> instances can be easily added (through a static <tt>Map</tt>,
 * for example). You could then lookup a <tt>SessionFactory</tt> by its name.
 *
 * Refactored Mar 1, 2011 to support multiple concurrent named connections.
 *
 *
 * TODO: in hindsight, it may be easier to instantiate one instance of the 
 * original hibernateUtil per database.  For example, the TradingPartnerInitServlet
 * could instantiate distinct HibernateUtil instances in AppGlobals.
 *
 *
 * @author christian@hibernate.org
 */
public class HibernateUtilMultiDatabase {

    private static Log log = LogFactory.getLog(HibernateUtilMultiDatabase.class);
    
    // protected static transient XMLHelper xmlHelper;

    private static Hashtable<String,Configuration> configurations = 
    	new Hashtable<String,Configuration>();
    private static Hashtable<String,SessionFactory> sessionFactories =
    	new Hashtable<String,SessionFactory>();
    
    private static ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
    
    public static final String CONNECTION_USER = "hibernate.connection.username";
    public static final String CONNECTION_URL = "hibernate.connection.url";
    
    /*
     * Initializes Hibernate.  
     * Set useEntityResolver to true when using in a web app deployed to 
     * WebSphere to circumvent the "port 80 is closed" issue which caused problems
     * resolving XML files which point to external schemas. 
     *  
     * This initialize method is called by an initialization servlet for both
     * the Ingredients Web App and the Web Services.  It allows us to:
     * a) decide on whether or not to use the Entity Resolver
     * b) decrease the response time for the first web app/service request,
     *    since Hibernate is now initialized at deployment time. 
     * 
     */
    public static void initialize(String dbName, 
    							  String configFile,
    							  boolean useEntityResolver) {
        
    	if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(configFile)) {
        	throw new NhpdException
        		("HibernateUtilMultiDatabase.initialize: missing database name"); 
        }
        
       
        // Create the SessionFactory from the provided configuration file  
        try {
        	
            // Replace with Configuration() if you don't use annotations or JDK 5.0
            //configuration = new AnnotationConfiguration();
        	Configuration configuration = new Configuration();
        	SessionFactory sessionFactory = null;
                    
           /*
            * The entity resolver supports entity placeholders in XML mapping files
            * and tries to resolve them on the classpath as a resource.  This was 
            * introduced to resolve the "port 80 closed due to security vulnerability" 
            * issue.  Entity resolver is only used for the web app; web services were
            * not affected.  See WebServiceInitServlet in WebServicessCommon and 
            * WebAppInitServlet in IngredientDatabase.
            */
            if (useEntityResolver) {
                log.debug("HibernateUtilMultiDatabase.initialize: Using Entity Resolver");
                configuration.setEntityResolver(new ImportFromClasspathEntityResolver());
            }
            else {
            	log.debug("HibernateUtilMultiDatabase.initialize: NOT Using Entity Resolver");
            }
            
            //Configure with the specified hibernate.cfg.xml
            configuration.configure(configFile);
              
            // Set global interceptor from configuration
            //setInterceptor(configuration, null);
            //// setInterceptor(configuration, new AuditLogInterceptor()); // Good Line.
            
            if (configuration.getProperty(Environment.SESSION_FACTORY_NAME) != null) {
                // Let Hibernate bind the factory to JNDI
                configuration.buildSessionFactory();     
            } else {
                // or use static variable handling
                sessionFactory = configuration.buildSessionFactory();
                
            }
            sessionFactories.put(dbName,sessionFactory);
            configurations.put(dbName,configuration);
            
        } 
        catch (Throwable ex) {
            // We have to catch Throwable, otherwise we will miss
            // NoClassDefFoundError and other subclasses of Error
            log.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
        
        
        
    }
    
    /**
     * Returns the original Hibernate configuration.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration(String dbName) {
        return configurations.get(dbName);
    }
    
    /**
     * Returns selected current connection properties 
     *
     * @return Configuration
     */
    public static Properties getConnectionProperties(String dbName) {
        Properties connectionProperties = new Properties();
        connectionProperties.put(CONNECTION_URL,
		 		 configurations.get(dbName).getProperty(CONNECTION_URL));
        connectionProperties.put(CONNECTION_USER,
        		 configurations.get(dbName).getProperty(CONNECTION_USER));
    	return connectionProperties;
    }
    
    /**
     * Returns the global SessionFactory.  We typically do not provide
     * the session factory name, so JNDI lookup is being avoided.  
     * 
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory(String dbName) {
        
       /* 
        * We differ from the original HibernateUtil here in that we assume
        * that initialize has been called already.  
        */
        Configuration config = getConfiguration(dbName);
        SessionFactory sf = null;
        
        String sfName = config.getProperty(Environment.SESSION_FACTORY_NAME);
        if ( sfName != null) {
            log.debug("Looking up SessionFactory in JNDI.");
            try {
                sf = (SessionFactory) new InitialContext().lookup(sfName);
            } catch (NamingException ex) {
                //throw new RuntimeException(ex);
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            sf = sessionFactories.get(dbName);
        }
        if (sf == null)
            throw new IllegalStateException("SessionFactory not available.");
        return sf;
    }
	
	
    /**
     * Closes the current SessionFactory and releases all resources.
     * <p>
     * The only other method that can be called on HibernateUtilMultiDatabase
     * after this one is rebuildSessionFactory(Configuration).
     */
    public static void shutdown(String dbName) {
        log.debug("Shutting down Hibernate.");
        // Close caches and connection pools
        sessionFactories.get(dbName).close();

    }


    /**
     * Rebuild the SessionFactory with the static Configuration.
     * <p>
     * This method also closes the old SessionFactory before, if still open.
     * Note that this method should only be used with static SessionFactory
     * management, not with JNDI or any other external registry.
     */
     public static void rebuildSessionFactory(String dbName) {
        log.debug("Using current Configuration for rebuild.");
        rebuildSessionFactory(dbName,configurations.get(dbName));
     }

    /**
     * Rebuild the SessionFactory with the given Hibernate Configuration.
     * <p>
     * HibernateUtilMultiDatabase does not configure() the given Configuration object,
     * it directly calls buildSessionFactory(). This method also closes
     * the old SessionFactory before, if still open.
     *
     * @param cfg
     */
     public static void rebuildSessionFactory(String dbName,Configuration cfg) {
        log.debug("Rebuilding the SessionFactory from given Configuration.");
        
        synchronized(sessionFactories.get(dbName)) {
            if (sessionFactories.get(dbName) != null && 
            		!sessionFactories.get(dbName).isClosed())
            	sessionFactories.get(dbName).close();
            if (cfg.getProperty(Environment.SESSION_FACTORY_NAME) != null) {
                cfg.buildSessionFactory();
            }
            else {
            	sessionFactories.put(dbName,cfg.buildSessionFactory());
            }
            configurations.put(dbName,cfg);
        }
     }

    /**
     * Creates a Hibernate Session and starts a transaction on it.  Use this
     * method in conjunction with commitHibernateTransaction to adhere to 
     * recommendation in Hibernate 3 documentation:  
     *  
     * A typical transaction should use the following idiom: 
     *    Session sess = factory.openSession();
     *    Transaction tx;
     *    try {
     *        tx = sess.beginTransaction();
     *        //do some work...
     *        tx.commit();
     *    }
     *    catch (Exception e) {
     *        if (tx!=null) tx.rollback();
     *        throw e;
     *    }
     *    finally {
     *       sess.close();
     *    }
     *
     * @param   newSession if true; uses "openSession" rather than getCurrentSession
     *          to create a new Session.  "openSession" is currently used to circumvent
     *          intermittent "Session Closed" errors received via the IDB web app 
     *          (running in WebSphere), and "getCurrentSession" is used by web 
     *          services running in Tomcat.   
     *          NB.  Using "openSession" with the Tomcat web services causes frequent
     *          deadlocks with the Hibernate/C3P0 connection pool, so we have reverted
     *          to the "getCurrentSession" method for the web services.  This issue 
     *          should be investigated further, as it appears that the C3P0 connection
     *          pool does not successfully recycle connections when using "openSession"
     *          in a Tomcat environment.  (Mike Rabe - Nov 8, 2010)  
     * @param   nlsSortLanguage  Set to either BINARY_AI or CANADIAN FRENCH to enable
     *          proper DB sorting on the session. 
     * @return  Hibernate Session with an active transaction
     * 
     */
    public static Session startHibernateTransaction(String dbName,
    												boolean newSession) {
        
        Session session = null;
        try {
            log.debug ("Starting a database transaction via Hibernate...");
            if (newSession) {
                session = sessionFactories.get(dbName).openSession();    
            }
            else {
            	session = sessionFactories.get(dbName).getCurrentSession();
            }
            
            session.setFlushMode(FlushMode.COMMIT);
            session.beginTransaction();
        }
        catch (Throwable ex) {
            log.error("Exception occurred in startHibernateTransaction",ex);
            ex.printStackTrace();
            throw new NhpdException(ex);
        }
        
        log.debug ("Hibernate tx started OK.");
        threadSession.set(session);
        return session;
    }
	
    /**
     * Commit the Hibernate transaction.  All database access must be demarcated
     * with begin/commit transaction, even read only.  Sample usage of the methods
     * in this class:  
     * 
     *   Session mySession = HibernateUtilMultiDatabase.startHibernateTransaction(true);
     *   //do some database access, typically via a DAO 
     *   HibernateUtilMultiDatabase.commitHibernateTransaction(mySession);
     * 
     * @param session the session (typically previously returned by the
     * 		  startHibernateTransaction method
     */
	public static void commitHibernateTransaction(Session session) {

		if (session == null) {
			throw new NhpdException
			("null Session passed to MultiDatabaseHibernateUtil.commitHibernateTransaction");
		}
		
		Transaction tx = session.getTransaction();
		try {
			if (tx != null) {
				log.debug("Committing the database transaction via Hibernate...");
				session.getTransaction().commit();
			}
			else {
				log.warn("tx from session is null - can't commit");
			}		 
		}
		catch (Throwable ex) {
			log.error("Exception occurred in commitHibernateTransaction",ex);
			//rollback the tx if it is there
			try {
				if (tx != null) {
					tx.rollback();
				}
			}
			catch (Exception rollbackEx) {
				//ignore exceptions on rollback but log them
				log.warn ("Could not roll back failed Hibernate tx.");
			}
			ex.printStackTrace();
			throw new NhpdException(ex);
		}
		finally {
			try {
				session.close();
			}
			catch (Throwable t) {
			   /* 
			    * Ignore exceptions on closing session.  Although closing the 
			    * session in this fashion is recommended by Hibernate, the
			    * close typically fails with a "Session was already closed" 
			    * error in WebSphere/Linux envs.  
				*/	
			}
		}
		
		log.debug ("Hibernate tx committed OK.");
	}
	
	/**
	 * Gets the current Hibernate Session.  Typically used in cases where a 
	 * client has started a transaction and then delegates some work to another 
	 * object that needs the Session (e.g. to do multiple actions within the 
	 * transaction)    
	 */
	public static Session getCurrentSession(String dbName) {
        return HibernateUtilMultiDatabase.getSessionFactory(dbName).getCurrentSession();
    }
	
	/**
	 * retrieves the Session for the current thread.  This is prototype code
	 * for CCMS; likely getCurrentSession does the same thing..
	 * MR Nov 17. 2010
	 
	public static Session getThreadSession( ) {
        return threadSession.get();
        
    }
    */
	
}
