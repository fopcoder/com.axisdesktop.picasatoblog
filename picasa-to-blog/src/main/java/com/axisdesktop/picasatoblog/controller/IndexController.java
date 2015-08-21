package com.axisdesktop.picasatoblog.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ServletContext servletContext;

	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, Model model ) {
		getVersion();

		return "index";
	}

	@RequestMapping( value = "/getrss", method = RequestMethod.POST )
	public String getRss( @Valid PicasaForm picasaForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttr ) {
		if( bindingResult.hasErrors() ) {
			return "index";
		}

		redirectAttr.addFlashAttribute( "picasaForm", picasaForm );

		try {
			List<BlogImage> images = urlToImageList( picasaForm );
			redirectAttr.addFlashAttribute( "images", images );
		}
		catch( MalformedURLException e ) {
			System.err
					.println( "MalformedURLException: " + picasaForm.getUrl() );
			model.addAttribute( "globalError", "MalformedURLException: "
					+ picasaForm.getUrl() );
			return "index";
		}
		catch( JAXBException e ) {
			model.addAttribute( "globalError", "JAXBException: " + e );
			return "index";
		}

		return "redirect:/";
	}

	private List<BlogImage> urlToImageList( PicasaForm picasaForm )
			throws JAXBException, MalformedURLException {
		List<BlogImage> images = new ArrayList<>();
		URL url = new URL( picasaForm.getUrl() );

		JAXBContext jaxbCtx = JAXBContext.newInstance( RssNode.class );
		Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
		RssNode xmlData = (RssNode)unmarshaller.unmarshal( url );

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
			newUrl = node.getUrl().substring( 0, idx + 1 ) + newUrl
					+ node.getUrl().substring( idx );

			images.add( new BlogImage( node.getWidth(), node.getHeight(),
					newUrl, picasaForm.getAlt() ) );
		}

		return images;
	}

	private String getVersion() {
		String version = getClass().getPackage().getImplementationVersion();

		if( version == null ) {
			Properties prop = new Properties();
			try {
				prop.load( servletContext
						.getResourceAsStream( "/META-INF/MANIFEST.MF" ) );
				version = prop.getProperty( "Implementation-Version" );
			}
			catch( IOException e ) {

			}
		}

		System.err.println( "======>  " + version );

		return version;
	}

}
