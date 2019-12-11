package ca.gc.hc.nhpd.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.dao.AbstractDao;
import ca.gc.hc.nhpd.dao.UnitsDao;
import ca.gc.hc.nhpd.model.Units;

/**
 * Manual DAO Test that verifies database transaction configuration without
 * using the HibernateUtil class. This test is used to get a single and list of
 * Units.
 */

public class ManualDaoIT extends ConfigurationIT {

	private static String CLASS_NAME = AbstractDao.UNITS_MODELCLASSNAME;

	private UnitsDao unitsDao;

	@Before
	public void startTransaction() {
		SessionFactory sessionFact = (SessionFactory) applicationContext.getBean("hibernateSessionFactory");

		Session session = sessionFact.getCurrentSession();

		Transaction tx = session.beginTransaction();

		unitsDao = (UnitsDao) AbstractDao.getInstanceOf(CLASS_NAME);

	}

	@After
	public void endTransaction() {

		SessionFactory sessionFact = (SessionFactory) applicationContext.getBean("hibernateSessionFactory");

		Session session = sessionFact.getCurrentSession();

		Transaction tx = session.getTransaction();

		tx.commit();

	}

	@Test
	public void getUnitsByIdTest() {

		Units units = (Units) unitsDao.findById(1l, true);

		assertNotNull(units);

	}

	@Test
	public void getAllUnitsTest() {

		List<Units> units = unitsDao.findAll();

		assertNotNull(units);
		assertTrue(units.size() > 0);

	}

}
