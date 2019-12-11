package ca.gc.hc.nhpd.conf;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class created to support legacy code accessing the spring context.  This class is 
 * instantiated by spring through the <code>DataSourceConfiguration</code>.
 *
 * https://objectpartners.com/2010/08/23/gaining-access-to-the-spring-context-in-non-spring-managed-classes/
 */
public class SpringContext implements ApplicationContextAware {

	static ApplicationContext context;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static Session getSession() {
		SessionFactory sessionFactory = (SessionFactory) SpringContext.getApplicationContext().getBean("hibernateSessionFactory");
		return sessionFactory.getCurrentSession();
	}

}
