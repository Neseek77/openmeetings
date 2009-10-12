package org.openmeetings.app.quartz.scheduler;

//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;

import org.slf4j.LoggerFactory; 
import org.apache.log4j.Logger;

import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
 
public class QuartzZombieJob implements IScheduledJob {

	private static Logger log = Logger.getLogger(QuartzZombieJob.class);

	public void execute(ISchedulingService service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// FIXME: Check for Zombies Issue 583
			//
			//ScopeApplicationAdapter.getInstance().clearZombiesFromAllConnection();

		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
