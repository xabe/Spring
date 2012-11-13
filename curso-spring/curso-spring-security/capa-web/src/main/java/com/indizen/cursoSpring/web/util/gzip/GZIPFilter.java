package com.indizen.cursoSpring.web.util.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GZIPFilter implements Filter {

	private FilterConfig filterConfig = null;

    public GZIPFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Throwable problem = null;
        try {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                String s = httpServletRequest.getHeader("accept-encoding");
                if (s != null && s.indexOf("gzip") != -1) {
                    GZIPResponseWrapper gzipresponsewrapper = new GZIPResponseWrapper(httpServletResponse);
                    chain.doFilter(request, gzipresponsewrapper);
                    gzipresponsewrapper.finishResponse();
                    return;
                } else if (s != null && s.indexOf("deflate") != -1) {
                    DeflaterResponseWrapper deflaterResponseWrapper = new DeflaterResponseWrapper(httpServletResponse);
                    chain.doFilter(request, deflaterResponseWrapper);
                    deflaterResponseWrapper.finishResponse();
                    return;
                }
                chain.doFilter(request, response);
            }

        } catch (Throwable t) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            t.printStackTrace();
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {

        this.filterConfig = filterConfig;
    }



    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

	public void destroy() {
		
	}
}
