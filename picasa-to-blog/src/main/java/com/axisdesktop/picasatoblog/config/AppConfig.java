package com.axisdesktop.picasatoblog.config;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan( "com.axisdesktop.picasatoblog" )
@PropertySource( "classpath:application.properties" )
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean
	JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();

		try {
			Context initCtx = new InitialContext();
			Session mailSession = (Session)initCtx.lookup( env.getRequiredProperty( "mail.smtp.source" ) );
			sender.setSession( mailSession );
		}
		catch( NamingException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sender;
	}
}
