package com.user.management.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandlerImpl extends SimpleUrlLogoutSuccessHandler implements LogoutHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutHandlerImpl.class);

	@Override
	public void logout(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) {
		LOGGER.info("logout(..): logging out the user : " + authentication.getName());
		try {
			super.onLogoutSuccess(request, response, authentication);
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (IOException | ServletException e) {
			
		}
	}

}