package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.util.HibernateUtil;

/**
 * Abstract DAO test that initiates a database session through a hibernate
 * transaction using the HibernateUtil class. This class needs the Generic type
 * of the Persisted Object for which the DAO is being tested. Once connected,
 * will test the database connectivity by getting a single object and a list of
 * objects.
 * 
 * This class needs to be extended and the custom test methods added to the child classes.
 */

abstract public class AbstractDaoIT<D extends PersistentObject> extends ConfigurationIT {

	protected AbstractDao dao;

	protected String LANG_FR = Locale.CANADA_FRENCH.getISO3Language();
	protected String LANG_EN = Locale.ENGLISH.getISO3Language();
	
	private Class<D> persistentClass;
	
	@Before
	public void startTransaction() {

		HibernateUtil.startHibernateTransaction(true);

		this.persistentClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		String className = persistentClass.getSimpleName();

		dao = AbstractDao.getInstanceOf(className);

		assertNotNull(dao);

	}

	@After
	public void endTransaction() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		HibernateUtil.commitHibernateTransaction(session);

	}

	@Test
	public void getByIdTest() {

		D object = (D) dao.findById(1l, true);

		assertNotNull(object);

	}

	@Test
	public void findAllTest() {

		List<Object> objects = dao.findAll();

		assertNotNull(objects);
		assertTrue(objects.size() > 0);

	}

}
