package com.axisdesktop.picasatoblog.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.model.BlogImage;
import com.axisdesktop.picasatoblog.model.PicasaForm;
import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.picasarss.ContentNode;
import com.axisdesktop.picasatoblog.picasarss.ItemNode;
import com.axisdesktop.picasatoblog.picasarss.RssNode;
import com.axisdesktop.picasatoblog.service.AlbumService;
import com.axisdesktop.picasatoblog.service.HelperService;
import com.axisdesktop.picasatoblog.service.PersistLogService;
import com.axisdesktop.picasatoblog.service.VisitorService;

@Controller
public class IndexController {

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private PersistLogService dblogService;

	@Autowired
	private HelperService helperService;

	private static final int COOKIE_MAX_AGE = 3600 * 24 * 365 * 10;
	private static final String COOKIE_PATH = "/";

	// private static final Logger logger = LoggerFactory.getLogger( IndexController.class );

	@Autowired
	private ServletContext appContext;

	// TODO javadoc
	@RequestMapping( "/" )
	public String index( PicasaForm picasaForm, HttpServletResponse response, HttpServletRequest request ) {

		Map<String, String> cookies = helperService.getCookies( request.getCookies() );

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

		return "/index";
	}

	// TODO javadoc
	@RequestMapping( value = "/getrss", method = RequestMethod.POST )
	public String getRss( @Valid PicasaForm picasaForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttr, HttpServletResponse response, HttpServletRequest request ) {
		if( bindingResult.hasErrors() ) {
			return "/index";
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
			rec.setIp( helperService.getIpAddress( request ) );
			rec.setAlt( picasaForm.getAlt() );
			rec.setExternalName( picasaForm.getTitle() );

			Map<String, String> cookies = helperService.getCookies( request.getCookies() );
			rec.setVisitor( cookies.get( "visitor" ) );

			persistRequest( rec );

		}
		catch( MalformedURLException e ) {
			model.addAttribute( "globalError", "MalformedURLException: " + picasaForm.getUrl() );
			return "/index";
		}
		catch( JAXBException e ) {
			model.addAttribute( "globalError", "JAXBException: " + e );
			return "/index";
		}
		catch( Exception e ) {
			e.printStackTrace();
			// TODO log all other exception
		}

		return "redirect:/";

	}

	/**
	 * Persists user data.
	 * 
	 * Method uses own thread in case of slow database connection.
	 * 
	 * @param rec
	 *        A filled Record object
	 * @see Record
	 */
	private void persistRequest( Record rec ) {
		new Thread( new Runnable() {
			@Override
			public void run() {
				Visitor visitor = visitorService.saveVisitor( rec );
				Album album = albumService.saveAlbum( visitor, rec );
				dblogService.savePersistLog( visitor, album, rec.getIp() );
			}
		} ).start();
	}

	/**
	 * @param rssUrl
	 *        String Picasa RSS string url
	 * @param rec
	 * @see Record
	 */
	private void picasaRssGetUrlParams( String rssUrl, Record rec ) {
		rec.setExternalRss( rssUrl );

		try {
			String[] params = new URI( rssUrl ).getPath().split( "/" );

			for( int i = 0, n = params.length; i < n; i++ ) {
				if( params[i].equals( "user" ) ) {
					rec.setExternalUser( params[i++ + 1] );
					continue;
				}

				if( params[i].equals( "albumid" ) ) {
					rec.setExternalAlbum( params[i + 1] );
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
	 *        form from site
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

		picasaForm.setTitle( xmlData.getChannel().getTitle() );

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
}
