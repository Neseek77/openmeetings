package org.xmlcrm.test.init;

import junit.framework.TestCase;

import org.xmlcrm.app.data.user.Salutationmanagement;

public class AddTitles extends TestCase {
	
	public AddTitles(String testname){
		super(testname);
	}
	
	public void testaddTestTitles(){
		
		Salutationmanagement.getInstance().addUserSalutation("Herr");
		Salutationmanagement.getInstance().addUserSalutation("Frau");
		
	}

}
