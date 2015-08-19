package com.axisdesktop.picasatoblog.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.axisdeskop.picasatoblog.model.PicasaForm;

@Controller
public class IndexController {
	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, Model model ) {
		// model.addAttribute( "form", form );
		return "index";
	}

	@RequestMapping( value = "/getrss", method = RequestMethod.POST )
	public String getRss( @Valid PicasaForm picasaForm, BindingResult bindingResult ) {
		if( bindingResult.hasErrors() ) {
			return "index";
		}
		// URL url new URL();

		return "redirect:/";
	}

}
