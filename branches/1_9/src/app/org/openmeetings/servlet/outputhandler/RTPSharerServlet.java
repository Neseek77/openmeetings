package org.openmeetings.servlet.outputhandler;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.app.rtp.RTPScreenSharingSession;
import org.openmeetings.app.rtp.RTPStreamingHandler;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author o.becherer
 * 
 *         This servlet should be called from a conference participant who has
 *         been notified of an existing RTP Screensharing session The servlet
 *         should return a velocity template to start the RTPPlayer Applet
 */
public class RTPSharerServlet extends VelocityViewServlet {
	private static final long serialVersionUID = -3803050458625713769L;
	private static final Logger log = Red5LoggerFactory.getLogger(
			RTPSharerServlet.class, ScopeApplicationAdapter.webAppRootKey);

	public Sessionmanagement getSessionManagement() {
		try {
			if (ScopeApplicationAdapter.initComplete) {
				ApplicationContext context = WebApplicationContextUtils
						.getWebApplicationContext(getServletContext());
				return (Sessionmanagement) context.getBean("sessionManagement");
			}
		} catch (Exception err) {
			log.error("[getSessionManagement]", err);
		}
		return null;
	}

	public Usermanagement getUserManagement() {
		try {
			if (ScopeApplicationAdapter.initComplete) {
				ApplicationContext context = WebApplicationContextUtils
						.getWebApplicationContext(getServletContext());
				return (Usermanagement) context.getBean("userManagement");
			}
		} catch (Exception err) {
			log.error("[getUserManagement]", err);
		}
		return null;
	}

	public RTPStreamingHandler getRtpStreamingHandler() {
		try {
			if (ScopeApplicationAdapter.initComplete) {
				ApplicationContext context = WebApplicationContextUtils
						.getWebApplicationContext(getServletContext());
				return (RTPStreamingHandler) context
						.getBean("rtpStreamingHandler");
			}
		} catch (Exception err) {
			log.error("[getRtpStreamingHandler]", err);
		}
		return null;
	}

	private Configurationmanagement getConfigurationmanagement() {
		try {
			if (!ScopeApplicationAdapter.initComplete) {
				return null;
			}
			ApplicationContext context = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
			return (Configurationmanagement) context.getBean("cfgManagement");
		} catch (Exception err) {
			log.error("[getConfigurationmanagement]", err);
		}
		return null;
	}

	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) {

		try {

			if (getUserManagement() == null || getRtpStreamingHandler() == null
					|| getSessionManagement() == null) {
				return getVelocityView().getVelocityEngine().getTemplate(
						"booting.vm");
			}

			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}

			log.debug("sid: " + sid);

			Long users_id = getSessionManagement().checkSession(sid);
			Long user_level = getUserManagement().getUserLevelByID(users_id);

			if (user_level > 0) {

				String publicSID = httpServletRequest.getParameter("publicSID");
				if (publicSID == null) {
					log.error("publicSID is empty: " + publicSID);
					return null;
				}

				String room = httpServletRequest.getParameter("room_id");

				if (room == null || room.length() < 1) {
					log.error("room_id is emtpy!");
					return null;
				}

				// Generate Unique Name to prevent browser from caching file
				Date t = new Date();

				String requestedFile = room + "_" + t.getTime() + ".jnlp";
				httpServletResponse.setContentType("text/html");
				httpServletResponse.setHeader("Content-Disposition",
						"Inline; filename=\"" + requestedFile + "\"");

				String template = "rtp_player_applet.vm";

				// Retrieve Data from RTPmanager
				RTPScreenSharingSession rsss = getRtpStreamingHandler()
						.getSessionForRoom(room, sid, publicSID);

				if (rsss == null) {
					log.error("no RTPSharingSession available for room " + room);
					return null;
				}

				log.debug("Trying to connect on Stream (origin : "
						+ rsss.getSharingIpAddress() + ")");

				// Defining Port for Viewer...
				HashMap<String, Integer> preDefindedUsers = rsss.getViewers();

				if (preDefindedUsers.size() < 1)
					throw new Exception(
							"No predefined viewers available in RTPSharingSession!!");

				Iterator<String> citer = preDefindedUsers.keySet().iterator();

				Integer myPort = null;

				log.debug("Trying to resolve publicSID for sharerApplet : "
						+ publicSID);

				log.debug("Dumping Viewers MAP : ");
				log.debug("-------------------------------------");

				Iterator<String> testiter = preDefindedUsers.keySet()
						.iterator();

				while (testiter.hasNext()) {
					String clientPublicSid = testiter.next();
					Integer port = preDefindedUsers.get(clientPublicSid);

					log.debug("Viewer : " + clientPublicSid + " : " + port);

				}

				log.debug("-------------------------------------");

				while (citer.hasNext()) {
					String myClientSID = citer.next();
					Integer port = preDefindedUsers.get(myClientSID);

					log.debug("Trying Client with publicSID " + myClientSID);

					if (myClientSID.equals(publicSID)) {
						log.debug("HIT!!!");
						myPort = port;

						break;
					}
				}

				// TODO : this would be a valid entrypoint to add a new viewer,
				// if he is part of
				// the conference (check via ClientList per room) and came late
				// ;-)
				if (myPort == null)
					throw new Exception(
							"Predefindes Viewer List does not contain publicSID("
									+ publicSID + ") !");

				ctx.put("APP_NAME", getConfigurationmanagement().getAppName());
				ctx.put("HOST", InetAddress.getLocalHost().getHostAddress());
				ctx.put("PORT", myPort);
				ctx.put("HEIGHT", rsss.getStreamHeight());
				ctx.put("WIDTH", rsss.getStreamWidth());

				log.debug("Put Variables to Velocity context : HOST="
						+ ctx.get("HOST") + ", PORT=" + ctx.get("PORT"));

				log.debug("Received PubliSID : " + publicSID);
				// RoomClient rcl =
				// ClientListManager.getInstance().getClientByPublicSID(publicSID);
				// String ip = rcl.getUserip();

				// rsss.addNewViewer(ip, port);

				return getVelocityView().getVelocityEngine().getTemplate(
						template);

			}

			return null;

		} catch (Exception er) {
			log.error("[RTPSharerServlet]", er);
			System.out.println("Error downloading: " + er);
		}
		return null;
	}

}
