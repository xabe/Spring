package com.indizen.cursoSpring.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginRedirectFilter implements Filter {
	
	public void destroy() {
	}


	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		if (isAuthenticated()) 
		{
			HttpServletRequest httpServletRequest = Constants.getRequest(request);
			HttpServletResponse httpServletResponse = Constants.getResponse(response);
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/commons/index.faces");
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isAuthenticated() {
		boolean result = false;
		SecurityContext context = SecurityContextHolder.getContext();
		
		Authentication authentication = context.getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			result = true;
		}
		return result;
	}


	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}