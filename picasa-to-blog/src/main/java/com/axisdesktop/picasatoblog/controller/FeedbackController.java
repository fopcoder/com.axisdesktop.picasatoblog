package com.axisdesktop.picasatoblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.axisdesktop.picasatoblog.service.FeedbackService;

@Controller
@RequestMapping( "/feedback" )
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping( value = { "", "/" } )
	public String index() {
		return "feedback/index";
	}

	@RequestMapping( "/send" )
	public String send() {
		// feedbackService.sendFeedback();
		// feedbackService.persistFeedback();
		return "redirect:feedback/sent";
	}

	public String error() {
		return "feedback/error";
	}

}
