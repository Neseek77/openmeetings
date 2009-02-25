package org.openmeetings.test.rooms;

import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;

import junit.framework.TestCase;

import org.openmeetings.app.data.conference.Roommanagement;

public class RoomTest extends TestCase {
	
	private static final Logger log = Red5LoggerFactory.getLogger(RoomTest.class, "openmeetings");
	
	public RoomTest(String testname){
		super(testname);
	}
	
	public void testRoomTest(){
		//Public Rooms
		long room1 = Roommanagement.getInstance().addRoom(3, "room1", 1, "", new Long(4), true, null,
				290, 280, 2, 2,
				400,
				true, 296, 2, 592, 660,
				true, 2, 284, 310, 290, false);
		log.error("room1: "+room1);
		long room2 = Roommanagement.getInstance().addRoom(3, "room1", 2, "", new Long(4), true, null,
				290, 280, 2, 2,
				400,
				true, 296, 2, 592, 660,
				true, 2, 284, 310, 290, true);
		log.error("room2: "+room2);
		
	}

}
