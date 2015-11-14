package com.axisdesktop.picasatoblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.axisdesktop.picasatoblog.model.FeedbackForm;
import com.axisdesktop.picasatoblog.service.FeedbackService;
import com.axisdesktop.picasatoblog.service.HelperService;

@Controller
@RequestMapping( "/feedback" )
@SessionAttributes( "feedbackForm" )
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private HelperService helperService;

	@RequestMapping( path = { "", "/" } )
	public String index( FeedbackForm feedbackForm ) {
		return "/feedback/index";
	}

	@RequestMapping( path = "/send", method = RequestMethod.POST )
	public String send( @Valid FeedbackForm feedbackForm, BindingResult bindingResult, HttpServletRequest request ) {
		if( bindingResult.hasErrors() ) {
			return "/feedback/index";
		}

		// don't wait when feedback will sent
		new Thread( new Runnable() {
			@Override
			public void run() {
				feedbackService.sendFeedback( feedbackForm );
				// feedbackService.persistFeedback();
			}
		} ).start();

		return "redirect:" + helperService.composeRedirectURI( request, "/feedback/confirm" );
	}

	@RequestMapping( "/confirm" )
	public String sent( SessionStatus status, Model model, HttpServletRequest req ) {
		// do not show empty confirmation page
		if( !model.containsAttribute( "feedbackForm" ) ) {
			return "redirect:" + helperService.composeRedirectURI( req, "/feedback" );
		}

		status.setComplete(); // clean session attributes

		return "/feedback/confirm";
	}

}
