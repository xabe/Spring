package com.indizen.cursoSpring.web.util.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {
	 protected HttpServletResponse servletResponse;
	    protected ServletOutputStream outputStrem;
	    protected PrintWriter printWriter;

	    public GZIPResponseWrapper(HttpServletResponse httpservletresponse) {
	        super(httpservletresponse);
	        servletResponse = null;
	        outputStrem = null;
	        printWriter = null;
	        servletResponse = httpservletresponse;
	    }

	    public ServletOutputStream createOutputStream() throws IOException {
	        return new GZIPResponseStream(servletResponse);
	    }

	    public void finishResponse() {
	        try {
	            if (printWriter != null) {
	                printWriter.close();
	            } else if (outputStrem != null) {
	                outputStrem.close();
	            }
	        } catch (IOException ioexception) {
	        }
	    }

	    @Override
	    public void flushBuffer() throws IOException {
	        outputStrem.flush();
	    }

	    @Override
	    public ServletOutputStream getOutputStream() throws IOException {
	        if (printWriter != null) {
	            throw new IllegalStateException("getWriter() has already been called!");
	        }
	        if (outputStrem == null) {
	            outputStrem = createOutputStream();
	        }
	        return outputStrem;
	    }

	    @Override
	    public PrintWriter getWriter() throws IOException {
	        if (printWriter != null) {
	            return printWriter;
	        }
	        if (outputStrem != null) {
	            throw new IllegalStateException("getOutputStream() has already been called!");
	        } else {
	            outputStrem = createOutputStream();
	            printWriter = new PrintWriter(new OutputStreamWriter(outputStrem, "UTF-8"));
	            return printWriter;
	        }
	    }

	    @Override
	    public void setContentLength(int i) {
	    }
}