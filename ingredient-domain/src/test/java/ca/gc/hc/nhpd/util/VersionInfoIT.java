package ca.gc.hc.nhpd.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.JndiSetup;
import ca.gc.hc.nhpd.dao.AbstractDao;

public class VersionInfoIT extends ConfigurationIT {

	private static final Logger log = Logger.getLogger(VersionInfoIT.class);
    
	@Test
	public void getVersionMap() {

		log.debug("getVersionMap()");
		
		HibernateUtil.initialize(false);
		
		VersionInfo versionInfo = new VersionInfo();
		HashMap<String, String> versionMap = versionInfo.getVersionMap();
		
		assertNotNull(versionMap);
		assertTrue(versionMap.size() != 0);
		
		Set<String> keys = versionMap.keySet();
		
		assertTrue(keys.contains("loader.ready.for.pre.load"));
		assertTrue(keys.contains("hibernate.version"));
		assertTrue(keys.contains("loader.load_date"));
		assertTrue(keys.contains("loader.ingredients_loaded"));

		for (String key : keys) {
			log.debug(key + " " + versionMap.get(key));
		}
		
	}
	
}
