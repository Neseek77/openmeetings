package org.openmeetings.test.server;


import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openmeetings.client.gui.ClientViewerScreen;

/**
 * @author sebastianwagner
 *
 */
public class TestWebStartViewerGui extends TestCase {
	
	private static Logger log = Logger.getLogger(TestWebStartViewerGui.class);
	
	@Test
	public void testTestWebStartGui(){
		try {
			
			String url = "192.168.0.3";
			String SID = "ff27d722821cb1c9928764128cee2e85";
			String room = "1";
			String domain = "";
			String publicSID = "testw";
			String record = "no";
			
			ClientViewerScreen clientViewerScreen = new ClientViewerScreen(url,"4445", SID, room, domain, publicSID, record,"");
		   
			try {
	            Thread.sleep(100000000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	            throw new InterruptedException(e.getMessage());
	        }
		} catch (Exception err) {
			log.error("[TestSocket] ",err);
		}
		
	}

}
