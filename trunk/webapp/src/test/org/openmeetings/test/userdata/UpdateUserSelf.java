package org.openmeetings.test.userdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openmeetings.app.hibernate.beans.basic.Sessiondata;
import org.openmeetings.app.hibernate.beans.user.Users;
import org.openmeetings.app.remote.MainService;
import org.openmeetings.app.remote.UserService;

import junit.framework.TestCase;

public class UpdateUserSelf extends TestCase {
	
	private static final Logger log = LoggerFactory.getLogger(UpdateUserSelf.class);
	
	public UpdateUserSelf(String testname){
		super(testname);
	}
	
}
