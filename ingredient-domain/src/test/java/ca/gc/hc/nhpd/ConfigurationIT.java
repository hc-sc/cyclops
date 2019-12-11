package ca.gc.hc.nhpd;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ca.gc.hc.nhpd.conf.DataSourceConfiguration;

/**
 * Configuration for Spring Integration Tests. Loads the JNDI references via the
 * test <code>jndi.xml</code> file and references the Configuration class. All
 * Spring Integration tests should subclass this class.
 */

@ContextConfiguration(classes = { DataSourceConfiguration.class })
public class ConfigurationIT extends AbstractJUnit4SpringContextTests {

	private static final Logger logger = Logger.getLogger(ConfigurationIT.class);

	@SuppressWarnings("resource")
	@BeforeClass
	public static void initJndi() throws Exception {

		logger.info("ConfigurationIT - initJndi");

		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

		ApplicationContext appContext = new ClassPathXmlApplicationContext("jndi.xml");
		InitialContext builder = new InitialContext();

		builder.createSubcontext("java:");
		builder.createSubcontext("java:comp");
		builder.createSubcontext("java:comp/env");
		builder.createSubcontext("java:comp/env/jdbc");
		// Database
		builder.bind("java:comp/env/jdbc/IngredientWebApp_dsn", appContext.getBean("dataSource"));

	}

}
