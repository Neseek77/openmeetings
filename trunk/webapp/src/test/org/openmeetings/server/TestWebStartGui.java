package org.openmeetings.server;

import org.apache.log4j.Logger;
import org.openmeetings.client.gui.ClientStartScreen;

import junit.framework.TestCase;

/**
 * @author sebastianwagner
 *
 */
public class TestWebStartGui extends TestCase {
	
	private static Logger log = Logger.getLogger(TestWebStartGui.class);
	
	public TestWebStartGui(String testname){
		super(testname);
	}
	
	public void testTestWebStartGui(){
		try {
			
			String url = "192.168.0.3";
			String SID = "ff27d722821cb1c9928764128cee2e85";
			String room = "1";
			String domain = "";
			String publicSID = "testw";
			String record = "no";
			
			ClientStartScreen clientStartScreen = new ClientStartScreen(url,"4445", SID, room, domain, publicSID, record,"");
		   
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
