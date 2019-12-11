package ca.gc.hc.nhpd.webservice.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.xml.ws.transport.http.servlet.WSServlet;

import ca.gc.hc.nhpd.servicepilot.monograph.MonographServicePropertyManager;

//import com.sun.xml.ws.transport.http.servlet.WSServlet;

/**
 * Reads configuration from the environment and injects it into the proper classes.
 * @author MDUGRE
 * @since 2.9.0
 */
public class MonographConfigServlet extends WSServlet {
	
	private static final long serialVersionUID = 6619986337782533258L;

	private static final Log log = LogFactory.getLog(MonographConfigServlet.class);
	
	/**
	 * Initialize the service configuration. 
	 */
	@Override
	public void init() throws javax.servlet.ServletException {
		
        try {
        	// Load the environment properties override from Tomcat context.xml / web.xml
			log.debug("WebAppInitServlet: Loading environment configuration files");
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			MonographServicePropertyManager.overrideProperties(envCtx);
		} catch (NamingException e) {
			log.warn("Could not load the Web Service settings environment overrides", e);
		}
	}
	
}
