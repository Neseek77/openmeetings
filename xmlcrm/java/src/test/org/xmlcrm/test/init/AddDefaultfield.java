package org.xmlcrm.test.init;

import junit.framework.TestCase;

import org.xmlcrm.app.data.basic.Fieldmanagment;

public class AddDefaultfield extends TestCase {
	
	public AddDefaultfield (String testname){
		super(testname);
	}
	
	public void testaddDefaultField(){
		
		Fieldmanagment.getInstance().addFourFieldValues("savewindow_btn", 163, "schlie�en", "close", "close", "close");
		
	}

}
