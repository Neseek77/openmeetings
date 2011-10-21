package org.openmeetings.app.installation;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.basic.ErrorManagement;
import org.openmeetings.app.data.basic.FieldLanguageDaoImpl;
import org.openmeetings.app.data.basic.Fieldmanagment;
import org.openmeetings.app.data.basic.Navimanagement;
import org.openmeetings.app.data.basic.dao.OmTimeZoneDaoImpl;
import org.openmeetings.app.data.calendar.daos.AppointmentCategoryDaoImpl;
import org.openmeetings.app.data.calendar.daos.AppointmentReminderTypDaoImpl;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.data.user.Organisationmanagement;
import org.openmeetings.app.data.user.Salutationmanagement;
import org.openmeetings.app.data.user.Statemanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.persistence.beans.basic.OmTimeZone;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.utils.ImportHelper;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportInitvalues {

	private static final Logger log = Red5LoggerFactory.getLogger(
			ImportInitvalues.class, ScopeApplicationAdapter.webAppRootKey);

	public static final String languageFolderName = "languages/";

	private static final String nameOfLanguageFile = "languages.xml";

	private static final String nameOfCountriesFile = "countries.xml";

	private static final String nameOfTimeZoneFile = "timezones.xml";

	private static final String nameOfErrorFile = "errorvalues.xml";

	@Autowired
	private Configurationmanagement cfgManagement;
	@Autowired
	private Usermanagement userManagement;
	@Autowired
	private Fieldmanagment fieldmanagment;
	@Autowired
	private FieldLanguageDaoImpl fieldLanguageDaoImpl;
	@Autowired
	private Statemanagement statemanagement;
	@Autowired
	private OmTimeZoneDaoImpl omTimeZoneDaoImpl;
	@Autowired
	private Navimanagement navimanagement;
	@Autowired
	private ErrorManagement errorManagement;
	@Autowired
	private Salutationmanagement salutationmanagement;
	@Autowired
	private Organisationmanagement organisationmanagement;
	@Autowired
	private Roommanagement roommanagement;
	@Autowired
	private AppointmentCategoryDaoImpl appointmentCategoryDaoImpl;
	@Autowired
	private AppointmentReminderTypDaoImpl appointmentReminderTypDaoImpl;

	public void loadMainMenu() {

		userManagement.addUserLevel("User", 1);
		userManagement.addUserLevel("Moderator", 2);
		userManagement.addUserLevel("Admin", 3);
		userManagement.addUserLevel("Web-Service (only access via SOAP)", 4);

		/*
		 * ######################## Dashboard Menu Points
		 */
		navimanagement.addGlobalStructure("home", 1, 124, false, true, 1,
				"home", "false", 582L);
		navimanagement.addMainStructure("dashboardModuleStartScreen", 1, 290,
				true, false, 1, "Dashboard Module", 1, "false");
		navimanagement.addMainStructure("dashboardModuleCalendar", 2, 291,
				true, false, 1, "Module Calendar", 1, "false");

		/*
		 * ######################## Recording Menu Points - disabled by default
		 */

		navimanagement.addGlobalStructure("record", 2, 395, false, true, 1,
				"record", "false", 583L);
		navimanagement.addMainStructure("recordModule", 1, 395, true, false, 1,
				"Module Recording Player", 2, "false");

		/*
		 * String action, int naviorder, long fieldvalues_id, boolean isleaf,
		 * boolean isopen, long level_id, String name, long global_id
		 */

		/*
		 * ######################## Conference Menu Points
		 */

		navimanagement.addGlobalStructure("meetings", 3, 792, false, true, 1,
				"List of Conference Rooms Global Navi", "false", 793L);

		navimanagement.addMainStructure("conferenceModuleRoomList", 1, 792,
				true, false, 1, "List of Conference Rooms Main Navi", 3,
				"false");

		/*
		 * ######################## Event Menu Points
		 */

		navimanagement.addGlobalStructure("events", 4, 3, false, true, 1,
				"List of Event Rooms Global Navi", "true", 585L);

		navimanagement.addMainStructure("eventModuleRoomList", 1, 3, true,
				false, 1, "List of Event Rooms Main Navi", 4, "false");

		/*
		 * ######################## Moderation Menu Points - disabled by default
		 */
		navimanagement.addGlobalStructure("moderate", 5, 649L, false, true, 2,
				"Moderation Menu", "true", 660L);

		navimanagement.addMainStructure("moderatorModuleUser", 1, 650, true,
				false, 1, "Moderate Users", 5, "false");

		navimanagement.addMainStructure("moderatorModuleRoom", 1, 651, true,
				false, 1, "Moderate Rooms", 5, "false");

		/*
		 * ######################## Administration Menu Points
		 */

		navimanagement.addGlobalStructure("admin", 6, 6, false, true, 3,
				"Administration Menu", "false", 586L);

		navimanagement.addMainStructure("adminModuleUser", 14, 125, true,
				false, 2, "Administration of Users", 6, "false");

		navimanagement.addMainStructure("adminModuleConnections", 15, 597,
				true, false, 3, "Aministration of Connections", 6, "false");

		navimanagement.addMainStructure("adminModuleOrg", 16, 127, true, false,
				3, "Administration of Organizations", 6, "false");

		navimanagement.addMainStructure("adminModuleRoom", 17, 186, true,
				false, 3, "Administration of Rooms", 6, "false");

		navimanagement.addMainStructure("adminModuleConfiguration", 18, 263,
				true, false, 3, "Administration of Configuration", 6, "false");

		navimanagement.addMainStructure("adminModuleLanguages", 19, 348, true,
				false, 3, "Administration of Languages", 6, "false");

		navimanagement.addMainStructure("adminModuleLDAP", 20, 1103, true,
				false, 3, "Administration of LDAP Configs", 6, "false");

		navimanagement.addMainStructure("adminModuleBackup", 21, 367, true,
				false, 3, "Administration of Backups", 6, "false");

		errorManagement.addErrorType(new Long(1), new Long(322));
		errorManagement.addErrorType(new Long(2), new Long(323));

	}

	public void loadErrorMappingsFromXML(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfErrorFile);

		Element root = document.getRootElement();

		for (@SuppressWarnings("unchecked")
		Iterator<Element> it = root.elementIterator("row"); it.hasNext();) {

			Element row = it.next();

			Long errorvalues_id = null;
			Long fieldvalues_id = null;
			Long errortype_id = null;

			for (@SuppressWarnings("unchecked")
			Iterator<Element> itSub = row.elementIterator("field"); itSub
					.hasNext();) {

				Element field = itSub.next();

				String name = field.attributeValue("name");
				String text = field.getText();
				// System.out.println("NAME | TEXT "+name+" | "+text);
				if (name.equals("errorvalues_id"))
					errorvalues_id = Long.valueOf(text).longValue();
				if (name.equals("fieldvalues_id"))
					fieldvalues_id = Long.valueOf(text).longValue();
				if (name.equals("errortype_id"))
					errortype_id = Long.valueOf(text).longValue();
			}

			errorManagement.addErrorValues(errorvalues_id, errortype_id,
					fieldvalues_id);
		}
		log.error("ErrorMappings ADDED");
	}

	public void loadSalutations() {

		salutationmanagement.addUserSalutation("Mr", 261);
		salutationmanagement.addUserSalutation("Ms", 262);
		salutationmanagement.addUserSalutation("Mrs", 841);
		salutationmanagement.addUserSalutation("Dr", 842);

	}

	public void loadConfiguration(String crypt_ClassName,
			String allowfrontendRegister, String smtpServer, String smtpPort,
			String referer, String mailauthname, String mailauthpass,
			String mailusetls, String default_lang_id, String swf_path,
			String im_path, String url_feed, String url_feed2,
			String sendEmailAtRegister, String sendEmailWithVerficationCode,
			String default_export_font, String screen_viewer,
			String ffmpeg_path, String sox_path, String sip_enable,
			String sip_realm, String sip_port, String sip_proxyname,
			String sip_tunnel, String sip_codebase, String sip_forcetunnel,
			String sip_openxg_enable, String openxg_wrapper_url,
			String openxg_client_id, String openxg_client_secret,
			String openxg_client_domain, String openxg_community_code,
			String openxg_language_code, String openxg_adminid,
			String sip_language_phonecode, String sip_phonerange_start,
			String sip_phonerange) {

		cfgManagement
				.addConfByKey(
						3,
						"crypt_ClassName",
						crypt_ClassName,
						null,
						"This Class is used for Authentification-Crypting. Be carefull what you do here! If you change it while running previous Pass of users will not be workign anymore! for more Information see http://code.google.com/p/openmeetings/wiki/CustomCryptMechanism");

		cfgManagement.addConfByKey(3, "screen_viewer", screen_viewer, null,
				"ScreenViewer Type(0==standard, 1== jrdesktop)");

		cfgManagement.addConfByKey(3, "allow_frontend_register",
				allowfrontendRegister, null, "");

		cfgManagement.addConfByKey(3, "default_group_id", "1", null, "");

		// this domain_id is the Organisation of users who register through the
		// frontend
		cfgManagement.addConfByKey(3, "default_domain_id", "1", null, "");

		// "smtp.xmlcrm.org"
		cfgManagement.addConfByKey(3, "smtp_server", smtpServer, null,
				"this is the smtp server to send messages");
		// 25
		cfgManagement.addConfByKey(3, "smtp_port", smtpPort, null,
				"this is the smtp server port normally 25");
		// "openmeetings@xmlcrm.org"
		cfgManagement.addConfByKey(3, "system_email_addr", referer, null,
				"all send EMails by the system will have this address");
		// "openmeetings@xmlcrm.org"
		cfgManagement.addConfByKey(3, "email_username", mailauthname, null,
				"System auth email username");
		//
		cfgManagement.addConfByKey(3, "email_userpass", mailauthpass, null,
				"System auth email password");

		cfgManagement.addConfByKey(3, "mail.smtp.starttls.enable", mailusetls,
				null, "Enable TLS 1=true, 0=false");

		cfgManagement.addConfByKey(3, "application.name", "OpenMeetings", null,
				"Name of the Browser Title window");

		// "1" == "EN"
		cfgManagement.addConfByKey(3, "default_lang_id", default_lang_id, null,
				"Default System Language ID see language.xml");

		cfgManagement.addConfByKey(3, "swftools_path", swf_path, null,
				"Path To SWF-Tools");

		cfgManagement.addConfByKey(3, "imagemagick_path", im_path, null,
				"Path to ImageMagick tools");

		cfgManagement.addConfByKey(3, "sox_path", sox_path, null,
				"Path To SoX-Tools");

		cfgManagement.addConfByKey(3, "ffmpeg_path", ffmpeg_path, null,
				"Path To FFMPEG");

		cfgManagement.addConfByKey(3, "rss_feed1", url_feed, null, "Feed URL");

		cfgManagement.addConfByKey(3, "rss_feed2", url_feed2, null,
				"Feed URL 2");

		cfgManagement
				.addConfByKey(3, "sendEmailAtRegister", sendEmailAtRegister,
						null,
						"User get a EMail with their Account data. Values: 0(No) or 1(Yes)");

		cfgManagement
				.addConfByKey(
						3,
						"sendEmailWithVerficationCode",
						sendEmailWithVerficationCode,
						null,
						"User must activate their account by clicking on the "
								+ "activation-link in the registering Email. Values: 0(No) or 1(Yes) "
								+ "It makes no sense to make this(sendEmailWithVerficationCode) 1(Yes) while "
								+ "sendEmailAtRegister is 0(No) cause you need"
								+ "to send a EMail.");
		cfgManagement
				.addConfByKey(
						3,
						"default_export_font",
						default_export_font,
						null,
						"The Name of the Font used for exporting/render Images from Whiteboard"
								+ "The Font has to exist on the Server which runs Red5");

		cfgManagement.addConfByKey(3, "default.rpc.userid", "" + 1, null,
				"The User-Id of the Control User in OpenMeetings");

		// ***************************************
		// ***************************************
		// SIP Applet Configuration Values
		// ***************************************

		cfgManagement.addConfByKey(3, "sip.enable", sip_enable, null,
				"Enable to load the SIP Applet in the Client and "
						+ "call the SIP Applet whenever you enter a Room");

		cfgManagement.addConfByKey(3, "sip.realm", sip_realm, null,
				"So called *Domain of the SIP Provider*");

		cfgManagement.addConfByKey(3, "sip.port", sip_port, null, "SIP Port");

		cfgManagement.addConfByKey(3, "sip.proxyname", sip_proxyname, null,
				"SIP Proxy name (this is the outbound proxy)");

		cfgManagement
				.addConfByKey(3, "sip.tunnel", sip_tunnel, null,
						"SIP Tunnel IP + Port, format domain:port, for example 10.0.0.0:443");

		cfgManagement.addConfByKey(3, "sip.codebase", sip_codebase, null,
				"The Base-URL to load the Ringtone from");

		cfgManagement.addConfByKey(3, "sip.forcetunnel", sip_forcetunnel, null,
				"Force usage of the tunnel");

		// ***************************************
		// ***************************************
		// OpenXG Configuration Values
		// ***************************************

		cfgManagement.addConfByKey(3, "sip.openxg.enable", sip_openxg_enable,
				null,
				"Enable the OpenXG XML-RPC Gateway through the Wrapper URL");

		cfgManagement
				.addConfByKey(
						3,
						"openxg.wrapper.url",
						openxg_wrapper_url,
						null,
						"openxg_wrapper_url, null, OpenXG XML-RPC Wrapper URL, the Wrapper can only "
								+ "be located on 127.0.01 by default, "
								+ "for example http://127.0.0.1:5080/rpc_client/rpc_gateway_wrapper.php");

		cfgManagement.addConfByKey(3, "openxg.client.id", openxg_client_id,
				null, "OpenXG XML-RPC Client ID");

		cfgManagement.addConfByKey(3, "openxg.client.secret",
				openxg_client_secret, null, "OpenXG XML-RPC Client Secret");

		cfgManagement.addConfByKey(3, "openxg.client.domain",
				openxg_client_domain, null, "OpenXG Domain");

		cfgManagement.addConfByKey(3, "openxg.community.code",
				openxg_community_code, null, "OpenXG Community Code");

		cfgManagement.addConfByKey(3, "openxg.language.code",
				openxg_language_code, null, "OpenXG Language Code");

		cfgManagement.addConfByKey(3, "openxg.adminid", openxg_adminid, null,
				"OpenXG Admin ID");

		// ***************************************
		// ***************************************
		// Phone Range Configuration Values
		// ***************************************

		cfgManagement.addConfByKey(3, "sip.language.phonecode",
				sip_language_phonecode, null, "For example +358 for Finland");

		cfgManagement
				.addConfByKey(3, "sip.phonerange.start", sip_phonerange_start,
						null,
						"The first number in the Range of Phone Numbers in national format");

		cfgManagement.addConfByKey(3, "sip.phonerange", sip_phonerange, null,
				"Amount of numbers in the Phone Range available");

		cfgManagement.addConfByKey(3, "sip.phonerange.currentindex", "" + 0,
				null, "Number of used Phone Numbers in the sip.phonerange");

		cfgManagement
				.addConfByKey(3, "sip.phonerange.conference.currentindex",
						"" + 0, null,
						"Number of used Phone Numbers in the sip.phonerange for the conferences");

		// ***************************************
		// ***************************************
		// Timezone settings
		// ***************************************

		Calendar cal = Calendar.getInstance();
		int offset = cal.get(Calendar.ZONE_OFFSET);

		offset = offset / 1000 / 60 / 60;

		String timeZoneJavaFormat = "Etc/GMT";

		if (offset > 0) {
			timeZoneJavaFormat += "+" + offset;
		} else {
			timeZoneJavaFormat += "-" + offset;
		}

		cfgManagement.addConfByKey(3, "default.timezone", timeZoneJavaFormat,
				null, "This is the default timezone if nothing is specified");

		// ***************************************
		// ***************************************
		// additional settings
		// ***************************************

		cfgManagement.addConfByKey(3, "show.facebook.login", "" + 0, null,
				"Show Facebook Login");

		cfgManagement.addConfByKey(3, "default.quality.screensharing", "0",
				null, "Default selection in ScreenSharing Quality");

		cfgManagement.addConfByKey(3, "default.dashboard.tab", "0", null,
				"Default selection in Dashboard tabs as tab-index-id");

		cfgManagement.addConfByKey(3, "dashboard.show.myrooms", "1", null,
				"Show My Rooms Tab");

		cfgManagement.addConfByKey(3, "dashboard.show.chat", "1", null,
				"Show Chat Tab");

		cfgManagement.addConfByKey(3, "dashboard.show.rssfeed", "0", null,
				"Show RSS Tab");

		cfgManagement
				.addConfByKey(
						3,
						"show.whiteboard.draw.status",
						"0",
						null,
						"Display name of the user who draw the current object (User Name auto-disapper after 3 seconds.");

		cfgManagement.addConfByKey(3, "max_upload_size", new Integer(ImportHelper.DEFAULT_MAX_UPLOAD_SIZE).toString(), null,
				"Maximum size of upload file (bytes)"); //defaults to 1GB

	}

	
	public void loadDefaultRooms(boolean createRooms) {

		long conference_Id = roommanagement.addRoomType("conference");
		log.debug("conference_Id: " + conference_Id);
		long audience_Id = roommanagement.addRoomType("audience");
		log.debug("audience_Id: " + audience_Id);

		long restricted_Id = roommanagement.addRoomType("restricted");
		log.debug("restricted_Id: " + restricted_Id);

		long interview_Id = roommanagement.addRoomType("interview");
		log.debug("interview_Id: " + interview_Id);

		long custom_Id = roommanagement.addRoomType("custom");
		log.debug("custom_Id: " + custom_Id);
		
		if (createRooms) {
			roommanagement.addRoom(3, "public Interview Room", interview_Id, "",
					new Long(16), true, null, false, false, null, false, null,
					true, false, false, "", "", "", null, null, null, false);
	
			roommanagement.addRoom(3, "public Conference Room", conference_Id, "",
					new Long(32), true, null, false, false, null, false, null,
					true, false, false, "", "", "", null, null, null, false);
	
			roommanagement.addRoom(3, "public Video Only Room", conference_Id, "",
					new Long(32), true, null, false, false, null, false, null,
					true, false, false, "", "", "", null, null, null, false);
	
			roommanagement.addRoom(3, "public Video And Whiteboard Room",
					conference_Id, "", new Long(32), true, null, false, false,
					null, false, null, true, false, false, "", "", "", null, null,
					null, false);
	
			roommanagement.addRoom(3, "public Restricted Room", restricted_Id, "",
					new Long(100), true, null, false, false, null, false, null,
					true, false, false, "", "", "", null, null, null, false);
	
			long room2 = roommanagement.addRoom(3, "private Conference Room",
					conference_Id, "", new Long(32), false, null, false, false,
					null, false, null, true, false, false, "", "", "", null, null,
					null, false);
	
			roommanagement.addRoomToOrganisation(3, room2, 1);
	
			roommanagement.addRoom(3, "public Audience Room", audience_Id, "",
					new Long(32), true, null, false, false, null, false, null,
					true, false, false, "", "", "", null, null, null, false);
	
			long room4 = roommanagement.addRoom(3, "private Audience Room",
					audience_Id, "", new Long(32), false, null, false, false, null,
					false, null, true, false, false, "", "", "", null, null, null,
					false);
	
			roommanagement.addRoomToOrganisation(3, room4, 1);
		}
	}

	public void loadInitUserAndOrganisation(String username, String userpass,
			String email, String defaultOrganisationName, String timeZone, String configdefaultLang) {
		// Add user
		try {
			
			Long default_lang_id = Long.parseLong(configdefaultLang);
			if (default_lang_id == null) default_lang_id = 1L;
			
			// BaseUrl as param is empty as we do not send an EMAIL here
			Long user_id = userManagement.registerUserInit(new Long(3), 3, 1,
					1, username, userpass, "lastname", "firstname", email,
					new java.util.Date(), "street", "no", "fax", "zip", 1,
					"town", default_lang_id, false, null, "phone", "", false, "", "", "",
					false, timeZone, false, "", "", false, true);

			log.debug("Installation - User Added user-Id " + user_id);

			if (user_id < 0) {
				throw new Exception(
						"Could not add user user returns a negative error message: "
								+ user_id);
			}

			// Add default group
			Long organisation_id = organisationmanagement.addOrganisation(
					defaultOrganisationName, user_id);

			// Add user to default group
			organisationmanagement.addUserToOrganisation(user_id,
					organisation_id, null, "");
		} catch (Exception e) {
			log.error("[loadInitUserAndOrganisation] ", e);
		}
	}

	/**
	 * import all language Names from the xml file
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	private void loadCountriesFiles(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfCountriesFile);

		Element root = document.getRootElement();

		for (@SuppressWarnings("rawtypes")
		Iterator it = root.elementIterator("country"); it.hasNext();) {

			Element item = (Element) it.next();
			String country = item.attributeValue("name");

			statemanagement.addState(country);

		}
	}

	private void loadTimeZoneFiles(String filePath) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfTimeZoneFile);

		Element root = document.getRootElement();

		for (@SuppressWarnings("rawtypes")
		Iterator it = root.elementIterator("timezone"); it.hasNext();) {

			Element item = (Element) it.next();
			String timeZoneName = item.attributeValue("name");
			String timeZoneLabel = item.attributeValue("label");
			String iCal = item.attributeValue("iCal");
			Integer orderId = Integer.valueOf(item.attributeValue("orderId"));

			omTimeZoneDaoImpl.addOmTimeZone(timeZoneName, timeZoneLabel, iCal,
					orderId);

		}
	}

	public List<OmTimeZone> getTimeZones(String filePath) throws Exception {

		log.debug(":: getTimeZones ::");

		List<OmTimeZone> omTimeZones = new LinkedList<OmTimeZone>();

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfTimeZoneFile);

		Element root = document.getRootElement();

		for (@SuppressWarnings("rawtypes")
		Iterator it = root.elementIterator("timezone"); it.hasNext();) {

			Element item = (Element) it.next();
			String timeZoneName = item.attributeValue("name");
			String timeZoneLabel = item.attributeValue("label");
			Integer orderId = Integer.valueOf(item.attributeValue("orderId"));

			OmTimeZone omTimeZone = new OmTimeZone();
			omTimeZone.setJname(timeZoneName);
			omTimeZone.setLabel(timeZoneLabel);
			omTimeZone.setOrderId(orderId);

			omTimeZones.add(omTimeZone);

		}

		return omTimeZones;

	}

	/**
	 * load all availible languages File names and language name's from the
	 * config file
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<Integer, LinkedHashMap<String, Object>> getLanguageFiles(
			String filePath) throws Exception {

		LinkedHashMap<Integer, LinkedHashMap<String, Object>> languages = new LinkedHashMap<Integer, LinkedHashMap<String, Object>>();

		SAXReader reader = new SAXReader();
		Document document = reader.read(filePath
				+ ImportInitvalues.nameOfLanguageFile);

		Element root = document.getRootElement();

		for (@SuppressWarnings("unchecked")
		Iterator<Element> it = root.elementIterator("lang"); it.hasNext();) {

			Element item = it.next();
			String country = item.getText();
			Integer id = Integer.valueOf(item.attribute("id").getValue())
					.intValue();

			String rtl = item.attribute("rightToLeft").getValue();

			LinkedHashMap<String, Object> lang = new LinkedHashMap<String, Object>();
			lang.put("id", id);
			lang.put("name", country);
			lang.put("rtl", rtl);
			// log.error("getLanguageFiles "+country);
			languages.put(id, lang);

		}
		log.debug("Countries ADDED ");
		return languages;

	}

	/**
	 * @author o.becherer initial fillment of Appointmentcategories
	 */
	// ------------------------------------------------------------------------------
	public void loadInitAppointmentCategories() {
		log.debug("ImportInitValues.loadInitAppointmentCategories");

		try {
			appointmentCategoryDaoImpl.addAppointmentCategory(new Long(-1),
					"default", "default");
		} catch (Exception e) {
			log.error("Could not create AppointMentcategories");
			return;
		}

	}

	// ------------------------------------------------------------------------------

	/**
	 * @author o.becherer initial fillment of AppointMentReminderTypes
	 */
	// ------------------------------------------------------------------------------
	public void loadInitAppointmentReminderTypes() {

		log.debug("ImportInitValues.loadInitAppointmentReminderTypes");

		try {
			appointmentReminderTypDaoImpl.addAppointmentReminderTyps(-1L,
					"none", "no reminder");
			appointmentReminderTypDaoImpl.addAppointmentReminderTyps(-1L,
					"simple email", "simple email notification");
			appointmentReminderTypDaoImpl.addAppointmentReminderTyps(-1L,
					"iCal email", "Ical notification");

		} catch (Exception e) {
			log.error("Could not create ReminderType");
			return;
		}
	}

	// ------------------------------------------------------------------------------

	/**
	 * Loading initial Language from xml Files into database
	 */
	// ------------------------------------------------------------------------------
	public void loadInitLanguages(String filePath) throws Exception {

		this.loadCountriesFiles(filePath);

		this.loadTimeZoneFiles(filePath);

		LinkedHashMap<Integer, LinkedHashMap<String, Object>> listlanguages = this
				.getLanguageFiles(filePath);

		boolean langFieldIdIsInited = false;

		/** Read all languages files */
		for (Iterator<Integer> itLang = listlanguages.keySet().iterator(); itLang
				.hasNext();) {
			Integer langId = itLang.next();
			LinkedHashMap<String, Object> lang = listlanguages.get(langId);
			log.debug("loadInitLanguages lang: " + lang);

			String langName = (String) lang.get("name");
			String rtl = (String) lang.get("rtl");

			System.out.println("loadInitLanguages rtl from xml: " + rtl);

			Boolean langRtl = false;

			if (rtl != null && rtl.equals("true"))
				langRtl = true;

			Long languages_id = fieldLanguageDaoImpl.addLanguage(langName,
					langRtl);

			SAXReader reader = new SAXReader();
			Document document = reader.read(filePath + langName + ".xml");

			Element root = document.getRootElement();

			for (@SuppressWarnings("rawtypes")
			Iterator it = root.elementIterator("string"); it.hasNext();) {
				Element item = (Element) it.next();
				// log.error(item.getName());

				Long id = Long.valueOf(item.attributeValue("id")).longValue();
				String name = item.attributeValue("name");
				String value = "";

				for (@SuppressWarnings("rawtypes")
				Iterator t2 = item.elementIterator("value"); t2.hasNext();) {
					Element val = (Element) t2.next();
					value = val.getText();
				}

				// log.error("result: "+langFieldIdIsInited+" "+id+" "+name+" "+value);

				// Only do that for the first field-set
				if (!langFieldIdIsInited) {
					fieldmanagment.addField(name);
				}

				fieldmanagment.addFieldValueByFieldAndLanguage(id,
						languages_id, value);

			}
			log.debug("Lang ADDED: " + lang);
			if (!langFieldIdIsInited)
				langFieldIdIsInited = true;
		}

	}
	// ------------------------------------------------------------------------------

}
