package org.openmeetings.app.quartz.scheduler;

//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;

import org.slf4j.LoggerFactory; 
import org.apache.log4j.Logger;

import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.openmeetings.app.data.basic.Sessionmanagement;
 
public class SessionClearJob {

	private static Logger log = Logger.getLogger(SessionClearJob.class);
	
	//Spring loaded Beans
	private Sessionmanagement sessionmanagement;
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	public SessionClearJob() {
		
	}

	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			this.sessionmanagement.clearSessionTable();
			//System.out.println("ISchedulingService do Clear Session"+this);
			//log.debug("ISchedulingService do Clear Session",this);
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}