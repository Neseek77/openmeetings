package org.openmeetings.app.quartz.scheduler;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import org.slf4j.LoggerFactory; 
import org.slf4j.Logger;

import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.openmeetings.app.data.basic.Sessionmanagement;
 
public class QuartzSessionClear implements IScheduledJob {

	private static Logger log = LoggerFactory.getLogger(QuartzSessionClear.class.getName());

	public void execute(ISchedulingService service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			//Sessionmanagement.getInstance().clearSessionTable();
			System.out.println("ISchedulingService do Clear Session"+this);
			log.debug("ISchedulingService do Clear Session",this);
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
