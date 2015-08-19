package com.axisdesktop.picasatoblog.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axisdesktop.picasatoblog.model.PicasaForm;

@Controller
public class IndexController {
	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, Model model ) {
		// model.addAttribute( "form", form );
		return "index";
	}

	@RequestMapping( value = "/getrss", method = RequestMethod.POST )
	public String getRss( @Valid PicasaForm picasaForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttr ) {
		if( bindingResult.hasErrors() ) {
			return "index";
		}

		redirectAttr.addFlashAttribute( "picasaForm", picasaForm );

		String content = null;

		try {
			StringBuilder res = new StringBuilder();
			URL url = new URL( picasaForm.getUrl() );

			try( BufferedReader br = new BufferedReader( new InputStreamReader( url.openStream() ) ) ) {
				String inputLine;

				while( ( inputLine = br.readLine() ) != null ) {
					res.append( inputLine );
				}

				content = res.toString();
			}
		}
		catch( MalformedURLException e ) {
			System.err.println( "MalformedURLException: " + picasaForm.getUrl() );
			model.addAttribute( "globalError", "MalformedURLException: " + picasaForm.getUrl() );
			return "index";
		}
		catch( IOException e ) {
			System.err.println( "IOException: " + e );
			model.addAttribute( "globalError", "IOException: " + e );
			return "index";
		}

		redirectAttr.addFlashAttribute( "urlContent", content );
		return "redirect:/";
	}
}
