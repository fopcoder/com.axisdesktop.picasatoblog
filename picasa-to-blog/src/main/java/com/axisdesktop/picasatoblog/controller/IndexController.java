package com.axisdesktop.picasatoblog.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axisdesktop.picasatoblog.model.BlogImage;
import com.axisdesktop.picasatoblog.model.PicasaForm;
import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.picasarss.ContentNode;
import com.axisdesktop.picasatoblog.picasarss.ItemNode;
import com.axisdesktop.picasatoblog.picasarss.RssNode;

/**
 * @author 1111
 *
 */
@Controller
public class IndexController {

	@Autowired
	private Environment environment;

	@Autowired
	private DataSource dataSource;

	private static final int COOKIE_MAX_AGE = 3600 * 24 * 365 * 10;
	private static final String COOKIE_PATH = "/";

	// TODO javadoc
	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, HttpServletResponse response, HttpServletRequest request ) {

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
			List<BlogImage> images = picasaRssToImageList( picasaForm );
			redirectAttr.addFlashAttribute( "images", images );

			Cookie w = new Cookie( "w", Integer.toString( picasaForm.getWidth() ) );
			w.setMaxAge( COOKIE_MAX_AGE );
			w.setPath( COOKIE_PATH );

			Cookie h = new Cookie( "h", Integer.toString( picasaForm.getHeight() ) );
			h.setMaxAge( COOKIE_MAX_AGE );
			h.setPath( COOKIE_PATH );

			response.addCookie( w );
			response.addCookie( h );

			Record rec = new Record();
			picasaRssGetUrlParams( picasaForm.getUrl(), rec );
			rec.setIp( getIpAddress( request ) );
			rec.setAlt( picasaForm.getAlt() );

			Map<String, String> cookies = getCookies( request.getCookies() );
			rec.setVisitor( cookies.get( "visitor" ) );

			persistRequest( rec );

		}
		catch( MalformedURLException e ) {
			model.addAttribute( "globalError", "MalformedURLException: " + picasaForm.getUrl() );
			return "index";
		}
		catch( JAXBException e ) {
			model.addAttribute( "globalError", "JAXBException: " + e );
			return "index";
		}
		catch( Exception e ) {
			// TODO log all other exception
		}

		// TODO fix session auto create in wildfly

		return "redirect:" + composeIndexRedirectUrl( request );
	}

	/**
	 * Persists user data.
	 * 
	 * Method uses own thread in case of slow database connection.
	 * 
	 * @param rec
	 *            A filled Record object
	 * @see Record
	 */
	private void persistRequest( Record rec ) {
		if( dataSource != null ) {
			new Thread( new Runnable() {
				@Override
				public void run() {
					BeanPropertySqlParameterSource data = new BeanPropertySqlParameterSource( rec );
					Number newId = new SimpleJdbcInsert( dataSource ).withTableName( "record" ).usingGeneratedKeyColumns( "id", "created" ).executeAndReturnKey( data );

					rec.setId( (long)newId );
				}
			} ).start();
		}
	}

	/**
	 * @param rssUrl
	 *            String Picasa RSS string url
	 * @param rec
	 * @see Record
	 */
	private void picasaRssGetUrlParams( String rssUrl, Record rec ) {
		rec.setPicasaRss( rssUrl );

		try {
			String[] params = new URI( rssUrl ).getPath().split( "/" );

			for( int i = 0, n = params.length; i < n; i++ ) {
				if( params[i].equals( "user" ) ) {
					rec.setPicasaUser( params[i++ + 1] );
					continue;
				}

				if( params[i].equals( "albumid" ) ) {
					rec.setPicasaAlbum( params[i + 1] );
				}
			}
		}
		catch( ArrayIndexOutOfBoundsException | URISyntaxException e ) {
			// TODO log all exceptions
			e.printStackTrace();
		}
	}

	/**
	 * Gets content of Picasa Rss, parses and converts it to List<BlogImage>
	 * 
	 * @param picasaForm
	 *            form from site
	 * @return List<BlogImage> list of BlogImage objects
	 * @throws JAXBException
	 * @throws MalformedURLException
	 */
	private List<BlogImage> picasaRssToImageList( PicasaForm picasaForm ) throws JAXBException, MalformedURLException {
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
	 * Gets client IP address
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return String IP
	 */
	private String getIpAddress( HttpServletRequest request ) {
		String ip = request.getHeader( "X-FORWARDED-FOR" );

		if( ip == null ) {
			ip = request.getRemoteAddr();
		}

		return ip;
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
