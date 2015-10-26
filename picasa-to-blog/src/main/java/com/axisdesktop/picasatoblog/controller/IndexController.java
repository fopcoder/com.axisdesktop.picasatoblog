package com.axisdesktop.picasatoblog.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

/**
 * @author 1111
 *
 */
@Controller
public class IndexController {

	@Autowired
	private Environment environment;

	private static final int COOKIE_MAX_AGE = 3600 * 24 * 365 * 10;
	private static final String COOKIE_PATH = "/";

	private static final Logger logger = LoggerFactory.getLogger( IndexController.class );

	// TODO javadoc
	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, HttpServletResponse response, HttpServletRequest request ) {

		logger.debug( "====>" );
		logger.error( "------>" );
		logger.info( "+++++>" );

		// TODO fix session auto create in code or wildfly
		// in case that session was not created
		request.getSession( true );

		Map<String, String> cookies = getCookies( request.getCookies() );

		if( !cookies.containsKey( "visitor" ) ) {
			Cookie c = new Cookie( "visitor", UUID.randomUUID().toString() );
			c.setMaxAge( COOKIE_MAX_AGE );
			c.setPath( COOKIE_PATH );

			response.addCookie( c );
		}

		if( cookies.containsKey( "w" ) ) {
			picasaForm.setWidth( Integer.valueOf( cookies.get( "w" ) ) );
		}

		if( cookies.containsKey( "h" ) ) {
			picasaForm.setHeight( Integer.valueOf( cookies.get( "h" ) ) );
		}

		return "index";
	}

	// TODO javadoc
	@RequestMapping( value = "/getrss", method = RequestMethod.POST )
	public String getRss( @Valid PicasaForm picasaForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttr, HttpServletResponse response, HttpServletRequest request ) {
		if( bindingResult.hasErrors() ) {
			return "index";
		}

		redirectAttr.addFlashAttribute( "picasaForm", picasaForm );

		try {
			List<BlogImage> images = urlToImageList( picasaForm );
			redirectAttr.addFlashAttribute( "images", images );

			Cookie w = new Cookie( "w", Integer.toString( picasaForm.getWidth() ) );
			w.setMaxAge( COOKIE_MAX_AGE );
			w.setPath( COOKIE_PATH );

			Cookie h = new Cookie( "h", Integer.toString( picasaForm.getHeight() ) );
			h.setMaxAge( COOKIE_MAX_AGE );
			h.setPath( COOKIE_PATH );

			response.addCookie( w );
			response.addCookie( h );
		}
		catch( MalformedURLException e ) {
			model.addAttribute( "globalError", "MalformedURLException: " + picasaForm.getUrl() );
			return "index";
		}
		catch( JAXBException e ) {
			model.addAttribute( "globalError", "JAXBException: " + e );
			return "index";
		}

		// TODO fix session auto create in wildfly

		return "redirect:" + composeIndexRedirectUrl( request );
	}

	// TODO javadoc
	private List<BlogImage> urlToImageList( PicasaForm picasaForm ) throws JAXBException, MalformedURLException {
		List<BlogImage> images = new ArrayList<>();
		URL url = new URL( picasaForm.getUrl() );

		JAXBContext jaxbCtx = JAXBContext.newInstance( RssNode.class );
		Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
		RssNode xmlData = (RssNode)unmarshaller.unmarshal( url );

		for( ItemNode i : xmlData.getChannel().getItems() ) {
			String newUrl;
			int newWidth, newHeight;

			ContentNode node = i.getGroup().getContent();
			double k = 1.0 * node.getWidth() / node.getHeight();

			if( node.getWidth() > node.getHeight() ) {
				newUrl = "s" + picasaForm.getWidth();
				newWidth = picasaForm.getWidth();
				newHeight = (int)( picasaForm.getWidth() / k );
			}
			else {
				newUrl = "h" + picasaForm.getHeight();
				newHeight = picasaForm.getHeight();
				newWidth = (int)( picasaForm.getHeight() * k );
			}

			int idx = node.getUrl().lastIndexOf( "/" );
			newUrl = node.getUrl().substring( 0, idx + 1 ) + newUrl + node.getUrl().substring( idx );

			images.add( new BlogImage( newWidth, newHeight, newUrl, picasaForm.getAlt() ) );
		}

		return images;
	}

	/**
	 * Converts HTTP Cookies to HashMap<String, String>
	 * 
	 * @param cookie
	 *            array of HttpServletRequest Cookies
	 * @return HashMap<Key, Value>
	 */
	private Map<String, String> getCookies( Cookie[] cookie ) {
		Map<String, String> cm = new HashMap<>();

		if( cookie != null ) {
			for( Cookie c : cookie ) {
				cm.put( c.getName(), c.getValue() );
			}
		}

		return cm;
	}

	/**
	 * Composes String url for redirect after form submit
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return url as string
	 */
	private String composeIndexRedirectUrl( HttpServletRequest request ) {
		String url = null;

		if( Arrays.asList( environment.getActiveProfiles() ).contains( "development" ) ) {
			url = "/";
		}
		else {
			url = request.getScheme() + "://" + request.getServerName();
		}

		return url;
	}
}
