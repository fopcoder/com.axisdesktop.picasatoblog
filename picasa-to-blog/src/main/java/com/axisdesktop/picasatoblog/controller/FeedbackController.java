package com.axisdesktop.picasatoblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axisdesktop.picasatoblog.model.FeedbackForm;
import com.axisdesktop.picasatoblog.service.FeedbackService;
import com.axisdesktop.picasatoblog.service.HelperService;

@Controller
@RequestMapping( "/feedback" )
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
	public String send( @Valid FeedbackForm feedbackForm, BindingResult bindingResult, RedirectAttributes redirectAttr ) {
		if( bindingResult.hasErrors() ) {
			return "/feedback/index";
		}

		redirectAttr.addFlashAttribute( "feedbackForm", feedbackForm );

		// don't wait when feedback will sent
		new Thread( new Runnable() {
			@Override
			public void run() {
				feedbackService.sendFeedback( feedbackForm );
				// feedbackService.persistFeedback();
			}
		} ).start();

		return "redirect:/feedback/confirm";
	}

	@RequestMapping( "/confirm" )
	public String sent( Model model ) {
		// don't show empty confirmation page
		if( !model.containsAttribute( "feedbackForm" ) ) {
			return "redirect:/feedback";
		}

		return "/feedback/confirm";
	}

}
