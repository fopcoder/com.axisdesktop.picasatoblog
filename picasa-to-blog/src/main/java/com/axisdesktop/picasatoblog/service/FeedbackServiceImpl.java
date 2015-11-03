package com.axisdesktop.picasatoblog.service;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.axisdesktop.picasatoblog.model.FeedbackForm;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Resource( mappedName = "java:/mail/gmail" )
	private Session mailSession;

	@Override
	public void sendFeedback( FeedbackForm feedbackForm ) {
		try {

			MimeMessage m = new MimeMessage( mailSession );
			Address from = new InternetAddress( "picasatoblog@axisdesktop.com" );
			Address[] to = new InternetAddress[] { new InternetAddress( "barabass@gmail.com" ) };

			m.setFrom( from );
			m.setRecipients( Message.RecipientType.TO, to );
			m.setSubject( "PicasaBlog" );
			m.setSentDate( new java.util.Date() );
			m.setContent( "Mail sent from JBoss AS 7", "text/plain" );
			Transport.send( m );
			System.out.println( "Mail sent!" );
		}
		catch( javax.mail.MessagingException e ) {
			e.printStackTrace();
			System.out.println( "Error in Sending Mail: " + e );
		}

		// templateEngine.
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void persistFeedback() {
		// TODO Auto-generated method stub

	}

}
