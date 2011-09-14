package org.openmeetings.app.data.basic;

import org.slf4j.Logger;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;

public class AuthLevelmanagement {
	 
	private static final Logger log = Red5LoggerFactory.getLogger(AuthLevelmanagement.class, ScopeApplicationAdapter.webAppRootKey);

	public boolean checkUserLevel(Long user_level) {
		if (user_level == 1 || user_level == 2 || user_level == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkModLevel(Long user_level) {
		if (user_level == 2 || user_level == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkAdminLevel(Long user_level) {
		if (user_level == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkWebServiceLevel(Long user_level) {
		if (user_level == 3 || user_level == 4) {
			return true;
		} else {
			return false;
		}
	}

}
