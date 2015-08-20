package com.axisdesktop.picasatoblog.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axisdesktop.picasatoblog.model.BlogImage;
import com.axisdesktop.picasatoblog.model.ContentNode;
import com.axisdesktop.picasatoblog.model.ItemNode;
import com.axisdesktop.picasatoblog.model.PicasaForm;
import com.axisdesktop.picasatoblog.model.RssNode;

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

			JAXBContext jaxbCtx = JAXBContext.newInstance( RssNode.class );
			Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
			RssNode xmlData = (RssNode)unmarshaller.unmarshal( new StringReader( content ) );

			List<BlogImage> images = new ArrayList<>();

			for( ItemNode i : xmlData.getChannel().getItems() ) {
				ContentNode node = i.getGroup().getContent();
				String newUrl;

				if( node.getWidth() > node.getHeight() ) {
					newUrl = "w" + picasaForm.getWidth();
				}
				else {
					newUrl = "h" + picasaForm.getHeight();
				}

				int idx = node.getUrl().lastIndexOf( "/" );
				newUrl = node.getUrl().substring( 0, idx + 1 ) + newUrl + node.getUrl().substring( idx );

				images.add( new BlogImage( node.getWidth(), node.getHeight(), newUrl, picasaForm.getAlt() ) );
			}

			redirectAttr.addFlashAttribute( "images", images );
		}
		catch( MalformedURLException e ) {
			System.err.println( "MalformedURLException: " + picasaForm.getUrl() );
			model.addAttribute( "globalError", "MalformedURLException: " + picasaForm.getUrl() );
			return "index";
		}
		catch( JAXBException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
