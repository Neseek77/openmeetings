package org.openmeetings.test.basic;

import junit.framework.TestCase;

import org.openmeetings.app.data.basic.Navimanagement;
import org.openmeetings.app.data.user.Organisationmanagement;
import org.openmeetings.app.data.user.Salutationmanagement;
import org.openmeetings.app.data.user.Statemanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.springframework.beans.factory.annotation.Autowired;

public class StartUpData extends TestCase {
	@Autowired
	private Usermanagement userManagement;
	@Autowired
	private Statemanagement statemanagement;
	@Autowired
	private Navimanagement navimanagement;

	public StartUpData(String testname) {
		super(testname);
	}

	public void testGenerateBasicNavi() throws Exception {

		userManagement.addUserLevel("User", 1);
		userManagement.addUserLevel("Moderator", 2);
		userManagement.addUserLevel("Admin", 3);

		navimanagement.addGlobalStructure("home", 1, 124, true, true, 1,
				"home", "false", 586L);

		navimanagement.addGlobalStructure("conf", 2, 1, false, true, 1,
				"conference", "false", 586L);
		navimanagement.addMainStructure("conf1", 1, 2, true, true, 1,
				"meeting", 2, "false");
		navimanagement.addMainStructure("conf2", 2, 3, true, true, 1,
				"classroom", 2, "false");

		navimanagement.addGlobalStructure("settings", 3, 4, false, true, 1,
				"setings", "false", 586L);
		navimanagement.addMainStructure("userself", 1, 5, true, false, 1,
				"userself", 3, "false");
		navimanagement.addMainStructure("roomconfiguremod", 2, 192, true,
				false, 1, "roomconfiguremod", 3, "false");

		navimanagement.addGlobalStructure("admin", 4, 6, false, true, 2,
				"admin", "false", 586L);
		navimanagement.addMainStructure("useradmin", 1, 125, true, false, 2,
				"useradmin", 4, "false");
		navimanagement.addMainStructure("groupadmin", 1, 126, true, false, 2,
				"groupadmin", 4, "false");
		navimanagement.addMainStructure("orgadmin", 1, 127, true, false, 3,
				"orgadmin", 4, "false");
		navimanagement.addMainStructure("roomadmin", 1, 186, true, false, 3,
				"roomadmin", 4, "false");

		Salutationmanagement.getInstance().addUserSalutation("Herr", 261);
		Salutationmanagement.getInstance().addUserSalutation("Frau", 262);

		// TODO: Load States from seperate XML-File
		statemanagement.addState("Deutschland");
		statemanagement.addState("France");
		statemanagement.addState("Italy");
		statemanagement.addState("Spain");
		statemanagement.addState("USA");
		statemanagement.addState("United Kingdom");
		statemanagement.addState("Ireland");
		statemanagement.addState("Danemark");

		// Add user
		Long user_id = userManagement.registerUserInit(new Long(3), 3, 1, 1,
				"swagner", "test", "lastname", "firstname",
				"seba.wagner@gmail.com", new java.util.Date(), "street", "no",
				"fax", "zip", 1, "town", 0, false, null, "phone", "", false,
				"", "", "", false, "", false, "", "", false, true);

		// Add default group
		Long organisation_id = Organisationmanagement.getInstance()
				.addOrganisation("default", user_id);

		// Add user to default group
		Long organisation_usersid = Organisationmanagement.getInstance()
				.addUserToOrganisation(new Long(1), organisation_id,
						new Long(1), "");

		// Configurationmanagement.getInstance().addConfByKey(3,
		// "allow_frontend_register", "1", 1, "");
		//
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "default_group_id", "1", 1, "");
		//
		// //this domain_id is the Organisation of users who register through
		// the frontend
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "default_domain_id", "1", 1, "");
		//
		// Configurationmanagement.getInstance().addConfByKey(3, "smtp_server",
		// "smtp.xmlcrm.org", 1, "this is the smtp server to send messages");
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "system_email_addr", "openmeetings@xmlcrm.org", 1,
		// "all send EMails by the system will have this address");
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "email_username", "openmeetings@xmlcrm.org", 1,
		// "System auth email username");
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "email_userpass", "tony123", 1, "System auth email password");
		//
		// Configurationmanagement.getInstance().addConfByKey(3, "default_lang",
		// "EN", 1, "Default System Language for tamplates");
		// Configurationmanagement.getInstance().addConfByKey(3,
		// "register_mail_subject", "SignUp", 1,
		// "The Subject for Mails sended at registration");

		// Todo: Load default language ID from Database

	}
}
