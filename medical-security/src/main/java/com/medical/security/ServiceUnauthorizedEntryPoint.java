package com.medical.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class ServiceUnauthorizedEntryPoint implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceUnauthorizedEntryPoint.class);

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// return 401 UNAUTHORIZED status code if the user is not authenticated
        logger.debug(" *** UnauthorizedEntryPoint.commence: " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
