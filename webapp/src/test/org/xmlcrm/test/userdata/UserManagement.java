package org.xmlcrm.test.userdata;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlcrm.app.hibernate.beans.basic.Sessiondata;
import org.xmlcrm.app.data.beans.basic.SearchResult;
import org.xmlcrm.app.hibernate.beans.user.Users;
import org.xmlcrm.app.remote.MainService;
import org.xmlcrm.app.remote.UserService;

import junit.framework.TestCase;

public class UserManagement extends TestCase {
	
	private static final Log log = LogFactory.getLog(UserManagement.class);	

	public UserManagement(String testname){
		super(testname);
	}
	
	public void testUsers(){
		
		MainService mService = new MainService();
		UserService uService = new UserService();
		Sessiondata sessionData = mService.getsessiondata();
		
		Users us = (Users) mService.loginUser(sessionData.getSession_id(), "swagner", "test");
		
		SearchResult users = uService.getUserList(sessionData.getSession_id(), 0, 100, "firstname", false);
		
		log.error("Number of Users 1: "+users.getResult().size());
		log.error("Number of Users 2: "+users.getRecords());
		
		Users users2 = (Users) users.getResult().get(5);
		
		System.out.println("User 2: "+users2.getLogin());
		
		Users user3 = mService.getUser(sessionData.getSession_id(), users2.getUser_id().intValue());
		
		System.out.println("user3: "+user3.getLogin());
		
		mService.logoutUser(sessionData.getSession_id());
		
	}
}
