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

import org.apache.log4j.Logger;

public class CacheControl implements Filter {
	private static final Logger LOGGER = Logger.getLogger("CapaServicio");

	public void destroy() {
	}

	public void doFilter(ServletRequest requests, ServletResponse responses,
			FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = Constants.getRequest(requests);
		final HttpServletResponse response = Constants.getResponse(responses);
		String requestPath = request.getRequestURI();
		if (requestPath != null) {
			if (requestPath.contains("/css/") || 
					requestPath.contains("/images/") || 
					requestPath.contains("/js/"))
			{
				if(requestPath.endsWith(".css.faces")
						|| requestPath.endsWith(".js.faces")
						|| requestPath.endsWith(".gif.faces"))
				{
					response.setHeader("Cache-Control", "max-age=31536000");
					LOGGER.debug("Entra control cache");
				}
			}
		}
		chain.doFilter(requests, responses);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}