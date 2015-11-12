package com.axisdesktop.picasatoblog.config;

import javax.annotation.Resource;
import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan( "com.axisdesktop.picasatoblog" )
@PropertySource( "classpath:application.properties" )
public class AppConfig extends WebMvcConfigurerAdapter {

	@Resource( mappedName = "java:/mail/gmail" )
	private Session mailSession;

	@Bean
	JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setSession( mailSession );

		return sender;
	}
}
