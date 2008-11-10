package org.openmeetings.app.remote;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.calendar.management.AppointmentLogic;
import org.openmeetings.app.data.calendar.management.MeetingMemberLogic;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.calendar.Appointment;
import org.openmeetings.app.hibernate.beans.calendar.AppointmentReminderTyps;

public class MeetingMemberService {
	
	private static final Log log = LogFactory.getLog(MeetingMemberService.class);
	
	private static MeetingMemberService instance = null;

	public static synchronized MeetingMemberService getInstance() {
		if (instance == null) {
			instance = new MeetingMemberService();
		}

		return instance;
	}

	
	public Long addMeetingMember(String SID, String firstname, String lastname, String memberStatus,
			String appointmentStatus, Long appointmentId, Long userid, String email){
		
		try{
			
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
					        	
	        return	 MeetingMemberLogic.getInstance().addMeetingMember( firstname,  lastname,  memberStatus,
	    			 appointmentStatus,  appointmentId,  userid,  email);
	        }
		} catch (Exception err) {
			log.error("[addMeetingMember]",err);
		}
		return null;
	
	}
	
	public Long updateMeetingMember(String SID,Long meetingMemberId, String firstname, String lastname, String memberStatus,
			String appointmentStatus, Long appointmentId, Long userid, String email){
		
		try{
			
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
					        	
	        return	 MeetingMemberLogic.getInstance().updateMeetingMember(meetingMemberId, firstname, 
	        		lastname, memberStatus, appointmentStatus, appointmentId, userid, email);
	        }
		} catch (Exception err) {
			log.error("[updateMeetingMember]",err);
		}
		return null;
	
	}
}
