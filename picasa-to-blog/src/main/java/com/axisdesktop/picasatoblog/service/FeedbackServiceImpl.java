package com.axisdesktop.picasatoblog.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.axisdesktop.picasatoblog.model.FeedbackForm;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private Environment environment;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Override
	public void sendFeedback( FeedbackForm feedbackForm ) {
		try {

			Context ctx = new Context();
			ctx.setVariable( "data", feedbackForm );
			String htmlContent = this.templateEngine.process( "feedback", ctx );

			MimeMessage message = new MimeMessage( mailSender.getSession() );
			MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );

			helper.setSubject( "PicasaToBlog запрос" );
			helper.setFrom( environment.getRequiredProperty( "mail.from" ) );
			helper.setTo( environment.getRequiredProperty( "mail.to" ) );
			helper.setText( htmlContent, true ); // true = isHtml

			mailSender.send( message );
			System.out.println( "Mail sent!" );
		}
		catch( javax.mail.MessagingException e ) {
			e.printStackTrace();
			System.out.println( "Error in Sending Mail: " + e );
		}
	}

	@Override
	@Transactional
	public void persistFeedback() {
		// TODO Auto-generated method stub

	}

}
