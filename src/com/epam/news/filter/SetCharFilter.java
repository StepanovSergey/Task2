package com.epam.news.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter changes encoding to UTF-8
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class SetCharFilter implements Filter {
    private static final boolean debug = true;
    private String encoding = "UTF-8";
    private FilterConfig filterConfig = null;

    public SetCharFilter() {
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void doBeforeProcessing(ServletRequest request,
	    ServletResponse response) throws IOException, ServletException {
	if (debug) {
	    log("EncodingFilter:DoBeforeProcessing");
	}
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void doAfterProcessing(ServletRequest request,
	    ServletResponse response) throws IOException, ServletException {
	if (debug) {
	    log("EncodingFilter:DoAfterProcessing");
	}
    }

    /**
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	if (debug) {
	    log("EncodingFilter:doFilter()");
	}
	doBeforeProcessing(request, response);
	if (request.getCharacterEncoding() == null) {
	    if (encoding != null) {
		request.setCharacterEncoding(encoding);
	    }
	}
	Throwable problem = null;
	try {
	    chain.doFilter(request, response);
	} catch (Throwable t) {
	    problem = t;
	}
	doAfterProcessing(request, response);
	if (problem != null) {
	    if (problem instanceof ServletException) {
		throw (ServletException) problem;
	    }
	    if (problem instanceof IOException) {
		throw (IOException) problem;
	    }
	    sendProcessingError(problem, response);
	}
    }

    /**
     * 
     * @return
     */
    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }

    /**
     * 
     * @param filterConfig
     */
    public void setFilterConfig(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    /**
	     *
	     */
    @Override
    public void destroy() {
    }

    /**
     * 
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
	if (filterConfig != null) {

	    System.out.println("encoding: " + encoding);
	    if (debug) {
		log("EncodingFilter:Initializing filter");
	    }
	}
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
	if (filterConfig == null) {
	    return ("EncodingFilter()");
	}
	StringBuffer sb = new StringBuffer("EncodingFilter(");
	sb.append(filterConfig);
	sb.append(")");
	return (sb.toString());
    }

    /**
     * 
     * @param t
     * @param response
     */
    private void sendProcessingError(Throwable t, ServletResponse response) {
	String stackTrace = getStackTrace(t);

	if (stackTrace != null && !stackTrace.equals("")) {
	    try {
		response.setContentType("text/html");
		PrintStream ps = new PrintStream(response.getOutputStream());
		PrintWriter pw = new PrintWriter(ps);
		pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N
		pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
		pw.print(stackTrace);
		pw.print("</pre></body>\n</html>"); // NOI18N
		pw.close();
		ps.close();
		response.getOutputStream().close();
	    } catch (Exception ex) {
	    }
	} else {
	    try {
		PrintStream ps = new PrintStream(response.getOutputStream());
		t.printStackTrace(ps);
		ps.close();
		response.getOutputStream().close();
	    } catch (Exception ex) {
	    }
	}
    }

    /**
     * 
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
	String stackTrace = null;
	try {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    pw.close();
	    sw.close();
	    stackTrace = sw.getBuffer().toString();
	} catch (Exception ex) {
	}
	return stackTrace;
    }

    /**
     * 
     * @param msg
     */
    public void log(String msg) {
	filterConfig.getServletContext().log(msg);
    }
}
