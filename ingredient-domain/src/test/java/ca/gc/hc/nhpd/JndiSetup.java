package ca.gc.hc.nhpd;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.junit.Before;

import oracle.jdbc.pool.OracleDataSource;

/**
 * This class programmatically sets the JNDI reference for use by Hibernate
 * without the use of Spring. Any class that wants to access the database should
 * extend this class.
 * 
 * GB - Consider removing in favor of the ConfigurationIT.
 * 
 * Ref: https://developer.jboss.org/thread/163604?_sscc=t
 */

public class JndiSetup {

	private static final Logger log = Logger.getLogger(JndiSetup.class);

	// JNDI reference from the hibernate.cfg.xml file.
	private final static String jndiname = "java:comp/env/jdbc/IngredientWebApp_dsn";

	// Connection Details to the Ingredient Database.
	private final static String url = "jdbc:oracle:thin:@oratqa01.hc-sc.gc.ca:1521:SDV101";
	private final static String username = "NHPDWEB_USER";
	private final static String password = "tqanhwbusr";

	// Code that setups the JNDI reference.
	@Before
	public void setUp() {

		log.info("Setting up the JNDI configuration.");

		try {

			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			InitialContext ic = new InitialContext();

			// Must populate the jndi reference one section at a time.
			String[] cxts = jndiname.split("/");
			String inCxt = cxts[0];
			createSubcontext(ic, inCxt);
			for (int i = 1; i < cxts.length - 1; i++) {
				inCxt = inCxt + "/" + cxts[i];
				createSubcontext(ic, inCxt);
			}

			OracleDataSource ds = new OracleDataSource();
			ds.setURL(url);
			ds.setUser(username);
			ds.setPassword(password);

			ic.bind(jndiname, ds);

		} catch (NamingException | SQLException ex) {
			log.error("Could not create the JNDI reference.", ex);
			ex.printStackTrace();
		}

	}

	/**
	 * Creates the different contexts required to save a JNDI reference.
	 * 
	 * @param ctx
	 * @param cxtName
	 * @return
	 * @throws NamingException
	 */
	private static Context createSubcontext(Context ctx, String cxtName) throws NamingException {
		Context subctx = ctx;
		Name name = ctx.getNameParser("").parse(cxtName);
		for (int pos = 0; pos < name.size(); pos++) {
			String ctxName = name.get(pos);
			try {
				subctx = (Context) ctx.lookup(ctxName);
			} catch (NameNotFoundException e) {
				subctx = ctx.createSubcontext(ctxName);
			}
			ctx = subctx;
		}
		return subctx;
	}

}
