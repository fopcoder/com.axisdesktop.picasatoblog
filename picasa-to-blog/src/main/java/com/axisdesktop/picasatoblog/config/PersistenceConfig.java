package com.axisdesktop.picasatoblog.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO split mysql & H2 datasources and libraries at compile time
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "com.axisdesktop.picasatoblog.repository" )
public class PersistenceConfig {

	@Autowired
	private Environment environment;

	@Profile( "development" )
	@Bean( name = "dataSource" )
	DataSource dataSourceDev() {
		EmbeddedDatabase db = new EmbeddedDatabaseBuilder().setType( EmbeddedDatabaseType.H2 )
				.setScriptEncoding( "UTF-8" ).addScript( "sql/schema.sql" ).build();

		return db;
	}

	@Profile( "development" )
	@Bean( initMethod = "start", destroyMethod = "stop" )
	public Server startDBManager() throws SQLException {
		return Server.createWebServer();
	}

	@Profile( "production" )
	@Bean( name = "dataSource" )
	DataSource dataSource() {
		DataSource ds = null;

		try {
			ds = (DataSource)new JndiTemplate().lookup( environment.getRequiredProperty( "db.jndi" ) );
		}
		catch( Exception e /* IllegalStateException | NamingException e */) {
			// TODO log all exceptions
			e.printStackTrace();
		}

		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl( Boolean.FALSE );
		vendorAdapter.setShowSql( Boolean.TRUE );
		vendorAdapter.setDatabasePlatform( "org.hibernate.dialect.MySQL5Dialect" );
		vendorAdapter.setDatabase( Database.MYSQL );

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter( vendorAdapter );
		factory.setPackagesToScan( "com.axisdesktop.picasatoblog.entity" );
		factory.setDataSource( dataSource() );
		factory.setPersistenceUnitName( "picasablog-jndi" );

		Properties jpaProperties = new Properties();
		// jpaProperties.put( "hibernate.format_sql", environment.getProperty( "hibernate.format_sql" ) );
		factory.setJpaProperties( jpaProperties );
		factory.afterPropertiesSet();

		return factory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
		transactionManager.setJpaDialect( new HibernateJpaDialect() );
		return transactionManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}
