package org.openmeetings.servlet.outputhandler;

import java.io.*;
import java.net.URLDecoder;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;

import org.slf4j.Logger;			// FIXME
import org.red5.logging.Red5LoggerFactory; // FIXME


public class HttpServletRequestEx extends HttpServletRequestWrapper {
	private HttpServletRequest request_ = null;
	private Map<String, String> requestParams_ = null;
	private String uriEncoding;

	private static final Logger log = Red5LoggerFactory.getLogger(DownloadHandler.class, "openmeetings"); // FIXME

	public HttpServletRequestEx(HttpServletRequest req, String uriEncoding)	throws IOException {
		super(req);

		log.debug("CTOR"); // FIXME
		this.uriEncoding = uriEncoding;

		String requestEncoding = req.getCharacterEncoding(); 		// FIXME
		if (requestEncoding == null)								// FIXME
			requestEncoding = "<null>" ;							// FIXME
		log.debug("req.getCharacterEncoding()" + requestEncoding);	// FIXME

		request_ = req;
		requestParams_ = new HashMap<String, String>();
		
		String queryString = request_.getQueryString();
		
		if ( queryString == null )
		{
			return ;
		}
		
		queryString = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");

		String[] params = queryString.split("&");
		for (String param : params)	{
			String[] nameValue = param.split("=");
			requestParams_.put(nameValue[0], 2 == nameValue.length? nameValue[1] : null);
		}
	}

	// Methods to replace HSR methods
	public String getParameter(String name) {
		try 
		{
			if (requestParams_.get(name) != null )
			{
				return URLDecoder.decode(requestParams_.get(name),uriEncoding);
			}
			else
			{
				return null;
			}
		}
		catch(UnsupportedEncodingException e)
		{
			return null;
		}
	}

	public Map getParameterMap() {		
		return new HashMap<String, String>(requestParams_);
	}

	public Enumeration getParameterNames() {
		return Collections.enumeration(requestParams_.keySet());
	}

	public String[] getParameterValues(String name) {
		ArrayList<String> values = new ArrayList<String>(requestParams_.values());
		return values.toArray(new String[0]);
	}
}

