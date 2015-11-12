package com.axisdesktop.picasatoblog.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axisdesktop.picasatoblog.model.FeedbackForm;
import com.axisdesktop.picasatoblog.service.FeedbackService;

@Controller
@RequestMapping( "/feedback" )
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping( path = { "", "/" } )
	public String index( FeedbackForm feedbackForm ) {
		return "feedback/index";
	}

	@RequestMapping( path = "/send", method = RequestMethod.POST )
	public String send( @Valid FeedbackForm feedbackForm, BindingResult bindingResult, RedirectAttributes redirectAttr,
			Locale locale ) {
		if( bindingResult.hasErrors() ) {
			return "feedback/index";
		}

		try {
			redirectAttr.addFlashAttribute( "feedbackForm", feedbackForm );
			feedbackService.sendFeedback( feedbackForm );
			// feedbackService.persistFeedback();
		}
		catch( Exception e ) {
			// TODO log this
			e.printStackTrace();
		}

		return "redirect:/feedback/confirm";
	}

	@RequestMapping( "/confirm" )
	public String sent( FeedbackForm feedbackForm ) {
		return "feedback/confirm";
	}

}
