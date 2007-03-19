package org.xmlcrm.test.userdata;

import org.xmlcrm.app.hibernate.beans.basic.Sessiondata;
import org.xmlcrm.app.hibernate.beans.user.Users;
import org.xmlcrm.app.remote.MainService;

import junit.framework.TestCase;

public class DeleteUser extends TestCase {

	public DeleteUser(String testname){
		super(testname);
	}
	
	public void testdeleteUsers(){
		
		MainService mService = new MainService();
		Sessiondata sessionData = mService.getsessiondata();
		
		Users us = mService.loginUser(sessionData.getSession_id(), "swagner6", "678101");
		
		System.out.println("us: "+us.getFirstname());
		
		String delete = mService.deleteUserIDSelf(sessionData.getSession_id());
		
		System.out.println("deleteSelf "+delete);
	}
	/*
	public void testdeleteUsersAdmin(){
		
		MainService mService = new MainService();
		Sessiondata sessionData = mService.getsessiondata();
		
		Users us = mService.loginUser(sessionData.getSession_id(), "swagner", "67810");
		
		System.out.println("us: "+us.getFirstname());
		
		String delete = mService.deleteUserAdmin(sessionData.getSession_id(),9);
		
		System.out.println("deleteAdmin "+delete);
		
		mService.logoutUser(sessionData.getSession_id());
	}
	*/

}
