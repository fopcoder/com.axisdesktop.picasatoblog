package com.axisdesktop.picasatoblog.service;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface HelperService {
	String composeRedirectURI( HttpServletRequest req, String path );

	String composeRedirectURI( HttpServletRequest req );

	String getIpAddress( HttpServletRequest request );

	Map<String, String> getCookies( Cookie[] cookie );
}
