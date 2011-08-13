package org.openmeetings.test.domain;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.openmeetings.app.data.user.Organisationmanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.persistence.beans.user.Users;
import org.springframework.beans.factory.annotation.Autowired;

public class TestAddGroup extends TestCase {

	@Autowired
	private Organisationmanagement organisationmanagement;

	private static final Logger log = Logger.getLogger(TestAddGroup.class);

	public TestAddGroup(String testname) {
		super(testname);
	}

	public void testAddingGroup() {

		long organisation_id = organisationmanagement.addOrganisation(
				"default", 1);

		log.error("new organisation: " + organisation_id);

		long organisation_usersid = organisationmanagement
				.addUserToOrganisation(new Long(1), organisation_id,
						new Long(1), "");

		log.error("new organisation_user: " + organisation_usersid);

		Users us = UsersDaoImpl.getInstance().getUser(new Long(1));

		log.error(us.getLastname());
		log.error(us.getAdresses().getTown());

		/*
		 * for (Iterator it = us.getAdresses().getEmails().iterator();
		 * it.hasNext();){ Adresses_Emails addrMails = (Adresses_Emails)
		 * it.next(); log.error(addrMails.getMail().getEmail()); }
		 * log.error("size of domains: "+us.getOrganisation_users().size()); for
		 * (Iterator it2 = us.getOrganisation_users().iterator();
		 * it2.hasNext();){ Organisation_Users orgUsers = (Organisation_Users)
		 * it2.next(); log.error(orgUsers.getOrganisation().getName()); }
		 */
	}

}
