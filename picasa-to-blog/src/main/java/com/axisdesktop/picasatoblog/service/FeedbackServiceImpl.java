package com.axisdesktop.picasatoblog.service;

import javax.annotation.Resource;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Resource( mappedName = "java:/mail/gmail" )
	private Session mailSession;

	@Override
	public void sendFeedback() {
		// templateEngine.
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void persistFeedback() {
		// TODO Auto-generated method stub

	}

}
