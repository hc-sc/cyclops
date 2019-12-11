package ca.gc.hc.nhpd.webservice.servlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.xml.ws.transport.http.servlet.WSServlet;

import ca.gc.hc.nhpd.servicepilot.ingredient.IngredientServicePropertyManager;
import ca.gc.hc.nhpd.util.HibernateUtil;

//import com.sun.xml.ws.transport.http.servlet.WSServlet;

/*
 * NHPD web services initialization servlet which initializes Hibernate.
 * The intent of this class is to eliminate the lazy intialization of
 * the Hibernate mappings which causes a considerable delay when 
 * issuing the first request to a service.  It also allows us to 
 * differentiate between Hibernate configurations, since the web app
 * now requires an instance of HibernateUtil which uses the EntityResolver
 * 
 * @author mrabe
 * 
 */
public class WebServiceInitServlet extends WSServlet {

	private static final long serialVersionUID = -2448853034001304722L;

	/** Application version loaded from MANIFEST.MF */
	private static String version;
	
	/** Application version loaded from MANIFEST.MF */ 
	private static String buildDate;
	
	private static final Log log = LogFactory.getLog(WebServiceInitServlet.class);
	
	@Override
	public void init() throws javax.servlet.ServletException {
		
        try {
        	// Load the environment properties override from Tomcat context.xml / web.xml
			log.debug("WebAppInitServlet: Loading environment configuration files");
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			IngredientServicePropertyManager.overrideProperties(envCtx);
		} catch (NamingException e) {
			log.warn("Could not load the Web Service settings environment overrides", e);
		}
		
		HibernateUtil.initialize(true);
		loadManifest();
	}
	
    /**
     * Load the war file manifest file. It contains the version and built date.
     * @param globals
     */
	private void loadManifest() {
		ServletContext application = getServletConfig().getServletContext(); 
		Manifest manifest = null;
		
		// Load the information from MANIFEST.MF
		InputStream manifestStream = application.getResourceAsStream("/META-INF/MANIFEST.MF"); 
		try {
			if(manifestStream != null) {
				manifest = new Manifest(manifestStream); 
			}
		} catch (IOException e) {
			log.error("Cannot load META-INF/MANIFEST.MF to get version information");
		} finally {
			try {
				if(manifestStream != null) manifestStream.close();
			} catch (IOException e) {
				log.warn("Could not close manifest stream properly", e);
			}
		}

		// Save the information into the Application Globals singleton
		if(manifest != null) {
			Attributes attributes = manifest.getMainAttributes();
	    	WebServiceInitServlet.version = attributes.getValue("Version");
	    	WebServiceInitServlet.buildDate = attributes.getValue("Built-On");

	    	log.info("Loaded " + version + ", build date " + buildDate);
	    	
		} else {
			log.info("Could not find or load the Manifest file");
		}
	}	
	
	/**
	 * Return the version loaded from the Manifest file.
	 * @return
	 */
	public static String getVersion() {
		return (version!=null?version:"not set") + " - " + (buildDate!=null?buildDate:"not set");
	}
}
