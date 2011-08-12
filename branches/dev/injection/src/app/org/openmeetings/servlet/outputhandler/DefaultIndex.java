package org.openmeetings.servlet.outputhandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.persistence.beans.basic.Configuration;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class DefaultIndex extends VelocityViewServlet {
	private static final long serialVersionUID = 3043617619650666432L;
	private static final Logger log = Red5LoggerFactory.getLogger(DefaultIndex.class, ScopeApplicationAdapter.webAppRootKey);
	private Configurationmanagement cfgManagement;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cfgManagement = (Configurationmanagement)config.getServletContext().getAttribute("cfgManagement");
		log.error("in init: cfgManagement is null ? " + (null == cfgManagement) + " ; " + cfgManagement);
	}
	
	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {

		try {
			
			String template = "sip_template.vm";
			
			//Enable SIP Template or not 
			Configuration SIP_ENABLE = cfgManagement.getConfKey(3L, "sip.enable");
			
			//SIP_REALM
			Configuration application_name = cfgManagement.getConfKey(3L, "application.name");
			if (application_name == null) {
				ctx.put("APPLICATION_NAME", getServletContext().getServletContextName());
			} else {
				ctx.put("APPLICATION_NAME", application_name.getConf_value());
			}
			
			if (SIP_ENABLE == null || !SIP_ENABLE.getConf_value().equals("yes")) {
				
				template = "usual_template.vm";
				
			} else {
				
				//Set all the Params for the Applet Configuration
			
				//SIP_REALM
				Configuration SIP_REALM = cfgManagement.getConfKey(3L, "sip.realm");
				if (SIP_REALM == null) {
					ctx.put("SIP_REALM", "");
				} else {
					ctx.put("SIP_REALM", SIP_REALM.getConf_value());
				}
				
				//SIP_PORT
				Configuration SIP_PORT = cfgManagement.getConfKey(3L, "sip.port");
				if (SIP_PORT == null) {
					ctx.put("SIP_PORT", "");
				} else {
					ctx.put("SIP_PORT", SIP_PORT.getConf_value());
				}
				
				//SIP_PROXYNAME
				Configuration SIP_PROXYNAME = cfgManagement.getConfKey(3L, "sip.proxyname");
				if (SIP_PROXYNAME == null) {
					ctx.put("SIP_PROXYNAME", "");
				} else {
					ctx.put("SIP_PROXYNAME", SIP_PROXYNAME.getConf_value());
				}
				
				//SIP_TUNNEL
				Configuration SIP_TUNNEL = cfgManagement.getConfKey(3L, "sip.tunnel");
				if (SIP_TUNNEL == null) {
					ctx.put("SIP_TUNNEL", "");
				} else {
					ctx.put("SIP_TUNNEL", SIP_TUNNEL.getConf_value());
				}
				
				//SIP_CODEBASE
				Configuration SIP_CODEBASE = cfgManagement.getConfKey(3L, "sip.codebase");
				if (SIP_CODEBASE == null) {
					ctx.put("SIP_CODEBASE", "");
				} else {
					ctx.put("SIP_CODEBASE", SIP_CODEBASE.getConf_value());
				}
				
				//SIP_FORCETUNNEL
				Configuration SIP_FORCETUNNEL = cfgManagement.getConfKey(3L, "sip.forcetunnel");
				if (SIP_FORCETUNNEL == null) {
					ctx.put("SIP_FORCETUNNEL", "");
				} else {
					ctx.put("SIP_FORCETUNNEL", SIP_FORCETUNNEL.getConf_value());
				}
			}
			
			//Parse the Param for the SWF URL
			String swf = httpServletRequest.getParameter("swf");
			if (swf == null) {
				ctx.put("SWF_URL", "main.swf8.swf");
			} else {
				ctx.put("SWF_URL", swf);
			}
			
			String SWF_PARAMS = "";
			String SWF_FLASHVARS = "";
			
			//Load params from URL and set into wrapper code
			if (httpServletRequest.getParameterMap() != null) {
				for (Iterator<String> iter = httpServletRequest.getParameterMap().keySet().iterator();iter.hasNext();) {
					String paramKey = iter.next();
					SWF_FLASHVARS += paramKey+"="+httpServletRequest.getParameterMap().get(paramKey)+"&amp;";
					SWF_PARAMS += paramKey+"="+httpServletRequest.getParameterMap().get(paramKey)+"&amp;";
				}
			}
			
			HashMap<String,String> defaultValuesMap = new HashMap<String,String>();
			
			defaultValuesMap.put("lzt","swf");
			defaultValuesMap.put("lzproxied","solo");
			defaultValuesMap.put("lzr","swf8");
			defaultValuesMap.put("bgcolor","%23ffffff");
			defaultValuesMap.put("width","100%25");
			defaultValuesMap.put("height","100%25");
			//defaultValuesMap.put("__lzurl","main.lzx%3Flzt%3Dswf%26lzproxied%3Dsolo%26lzr%3Dswf8");
			defaultValuesMap.put("__lzminimumversion","8");
			defaultValuesMap.put("id","lzapp");
			
			for (Iterator<String> iter = defaultValuesMap.keySet().iterator();iter.hasNext();) {
				String paramKey = iter.next();
				SWF_PARAMS += paramKey+"="+defaultValuesMap.get(paramKey);
				SWF_FLASHVARS += paramKey+"="+defaultValuesMap.get(paramKey);
				if (iter.hasNext()) {
					SWF_PARAMS += "&";
					SWF_FLASHVARS += "&amp;";
				}
			}
			
			ctx.put("SWF_PARAMS", SWF_PARAMS);
			ctx.put("SWF_FLASHVARS", SWF_FLASHVARS);
			
			return getVelocityEngine().getTemplate(template);
			
		} catch (Exception er) {
			System.out.println("Error downloading: " + er);
			er.printStackTrace();
			log.error("[Calendar :: service]",er);
		}
		return null;
	}
}
