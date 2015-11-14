package com.axisdesktop.picasatoblog.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class HelperServiceImpl implements HelperService {
	@Autowired
	private Environment env;

	/**
	 * Composes String url for redirect after form submit
	 * 
	 * @param req
	 *        HttpServletRequest
	 * @param path
	 *        relative redirect path
	 * @return uri as string
	 * @throws IllegalArgumentException
	 *         if request or environment are null
	 */
	@Override
	public String composeRedirectURI( HttpServletRequest req, String path ) {
		if( req == null || env == null ) throw new IllegalArgumentException(
				"request and environment must be not null, [" + req + ", " + env + "]" );

		if( path == null ) path = "/";

		if( !Arrays.asList( env.getActiveProfiles() ).contains( "development" ) ) {
			path = ServletUriComponentsBuilder.newInstance().scheme( req.getScheme() ).host( req.getServerName() )
					.path( path ).build().toString();
		}

		return path;
	}

	@Override
	public String composeRedirectURI( HttpServletRequest req ) {
		return composeRedirectURI( req, null );
	}

	/**
	 * Gets client IP address
	 * 
	 * @param request
	 *        HttpServletRequest
	 * @return String IP
	 */
	@Override
	public String getIpAddress( HttpServletRequest request ) {
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
	 *        array of HttpServletRequest Cookies
	 * @return HashMap<Key, Value>
	 */
	@Override
	public Map<String, String> getCookies( Cookie[] cookie ) {
		Map<String, String> cm = new HashMap<>();

		if( cookie != null ) {
			for( Cookie c : cookie ) {
				cm.put( c.getName(), c.getValue() );
			}
		}

		return cm;
	}
}
