package org.xmlcrm.app.templates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;

/**
 * 
 * @author swagner
 *
 */

public class VelocityLoader {
	
	private static final Log log = LogFactory.getLog(VelocityLoader.class);
	
	/**
	 * Loads the Path from the Red5-Scope
	 *
	 */
	public VelocityLoader(){
		try {
			IScope scope = Red5.getConnectionLocal().getScope().getParent();			
			String current_dir = scope.getResource("WEB-INF/").getFile().getAbsolutePath();	
            Velocity.init(current_dir+"/velocity.properties");
        } catch(Exception e) {
        	log.error("Problem initializing Velocity : " + e );
            System.out.println("Problem initializing Velocity : " + e );
        }
	}
	
	/**
	 * Loads the path by a given string, this is necessary cause if invoked
	 * by Servlet there is no Red5-Scope availible
	 * @param path
	 */
	public VelocityLoader(String path){
		try {
            Velocity.init(path+"WEB-INF/velocity.properties");
        } catch(Exception e) {
        	log.error("Problem initializing Velocity : " + e );
            System.out.println("Problem initializing Velocity : " + e );
        }
	}

}
