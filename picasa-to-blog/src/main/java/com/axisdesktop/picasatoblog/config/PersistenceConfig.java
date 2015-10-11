package com.axisdesktop.picasatoblog.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiTemplate;

// TODO split mysql & H2 datasources and libraries at compile time
@Configuration
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
}
