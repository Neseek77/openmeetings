package org.openmeetings.test.rooms;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;
import org.openmeetings.app.data.conference.Roommanagement;


public class AddOrgRoom extends TestCase {
	
	private static final Logger log = Red5LoggerFactory.getLogger(AddOrgRoom.class, "openmeetings");
	
	public AddOrgRoom(String testname){
		super(testname);
	}
	
	public void testAddOrgRoom(){
		
		long room = Roommanagement.getInstance().addRoom(3,"roomOrg", 1, "", new Long(4), true,null,
				290, 280, 2, 2,
				400,
				true, 296, 2, 592, 660,
				true, 2, 284, 310, 290, false, false, null);
		Roommanagement.getInstance().addRoomToOrganisation(3,room, 1);
		
	}
	

}
