package com.axisdesktop.picasatoblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan( "com.axisdesktop.picasatoblog" )
@PropertySource( "classpath:application.properties" )
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix( "/WEB-INF/views/" );
		templateResolver.setSuffix( ".html" );
		templateResolver.setCharacterEncoding( "UTF-8" );
		templateResolver.setTemplateMode( "HTML5" );

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver( templateResolver() );

		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setCharacterEncoding( "UTF-8" );
		viewResolver.setTemplateEngine( templateEngine() );

		return viewResolver;
	}

	@Override
	public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
		registry.addResourceHandler( "/resources/**" ).addResourceLocations( "/resources/" );
	}
}
