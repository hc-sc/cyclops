package ca.gc.hc.nhpd.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;

/**
 * Spring Configuration class used for the IngredientDomain data source
 * configuration.
 */

@Configuration
@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan({ "ca.gc.hc.nhpd.*" })
public class DataSourceConfiguration {

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * Defines the transaction manager by identifying it as the Hibernate
	 * Transaction Manager and wrapping the session factory. Skipping over the
	 * Spring managed beans for now until all the objects get properly annotated.
	 * 
	 * @param sessionFactory
	 * 
	 * @return
	 */
	@Bean
	HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	/**
	 * Defines hibernate session factory and brings in all the object definitions
	 * from the hibernate.cfg.xml file. Connects the hibernate session factory to
	 * the data source.
	 * 
	 * @param dataSource
	 * 
	 * @return
	 */
	@Bean
	public LocalSessionFactoryBean hibernateSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		Resource resource = applicationContext.getResource("classpath:hibernate.cfg.xml");
		sessionFactory.setConfigLocation(resource);
		sessionFactory.setHibernateProperties(additionalProperties());
		return sessionFactory;
	}

	/**
	 * Defines the property values to use with the hibernate configuration. These
	 * values were taken from the hibernate.cfg.xml file.
	 * 
	 * @return
	 */
	private Properties additionalProperties() {
		Properties properties = new Properties();

		// Enabled by default, consider removing.
		// Allows the caching of objects identified with the <cache> mapping.
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");

		// Enables queries to be cached. Must be set on the individual query.
		properties.setProperty("hibernate.cache.use_query_cache", "true");

		// Binds hibernate to ehcache. Required for 2nd level cache to work.
		properties.setProperty("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.EhCacheRegionFactory");

		// Allows hibernate to converse with Oracle.
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");

		// Will output statistics on queries being executed.
		properties.setProperty("hibernate.generate_statistics", "false");

		// Displays the SQL.
		properties.setProperty("hibernate.show_sql", "false");
		// properties.setProperty("hibernate.show_sql", "true");

		// Allows for batch updates by hibernate. 0 disables this functionality.
		properties.setProperty("jdbc.batch_size", "0");

		// Trust the row counts from Oracle.
		properties.setProperty("jdbc.batch_versioned_data", "true");

		// Use streams when reading/writing binary files.
		properties.setProperty("jdbc.use_streams_for_binary", "true");

		// Enable the fetching of data when outer joins are identified.
		properties.setProperty("max_fetch_depth", "1");

		// Substitutes values when generating the SQL.
		properties.setProperty("query.substitutions", "true 1, false 0, yes 'Y', no 'N'");

		// Allows hibernate to generate comments inside the SQL for easier debugging.
		properties.setProperty("use_sql_comments", "false");

		return properties;
	}

	/**
	 * Defines the data source using a JNDI reference.
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource("java:comp/env/jdbc/IngredientWebApp_dsn");
	}

	/**
	 * Wrapper for the EhCacheManaggerFactory bean.
	 * 
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	/**
	 * Defines the EhCache Manager using the settings in the ehcache.xml file.
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}

	/**
	 * Instantiates a bean that exposes the spring context to legacy code.
	 * 
	 * @return
	 */
	@Bean
	public SpringContext springContext() {
		return new SpringContext();
	}

}
