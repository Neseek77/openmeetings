package org.openmeetings.test.dao;

import static junit.framework.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.openmeetings.app.data.calendar.daos.AppointmentDaoImpl;
import org.openmeetings.app.data.calendar.daos.MeetingMemberDaoImpl;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.persistence.beans.calendar.Appointment;
import org.openmeetings.app.persistence.beans.calendar.MeetingMember;
import org.openmeetings.app.persistence.beans.user.Users;
import org.openmeetings.test.AbstractOpenmeetingsSpringTest;
import org.springframework.beans.factory.annotation.Autowired;

public class TestMeetingMemberDaoImpl extends AbstractOpenmeetingsSpringTest {
	@Autowired
	private AppointmentDaoImpl appointmentDao;
    @Autowired
    private Usermanagement userManagement;
	@Autowired
	private MeetingMemberDaoImpl meetingMemberDao;
	@Autowired
	private UsersDaoImpl usersDao;
	
	@Test
	public void testMeetingMemberDaoImpl() throws Exception {
		
		Long userId = 1L;
		Users user = userManagement.getUserById(userId);
		assertNotNull("Cann't get default user", user);
		
		// add new appointment
		Appointment ap = new Appointment();
		
		ap.setAppointmentName("appointmentName");
		ap.setAppointmentLocation("appointmentLocation");
		
		Date appointmentstart = new Date();
		Date appointmentend = new Date();
		appointmentend.setTime(appointmentstart.getTime() + 3600);
		
		ap.setAppointmentStarttime(appointmentstart);
	 	ap.setAppointmentEndtime(appointmentend);
		ap.setAppointmentDescription("appointmentDescription");
		ap.setStarttime(new Date());
		ap.setDeleted("false");
		ap.setIsDaily(false);
		ap.setIsWeekly(false);
		ap.setIsMonthly(false);
		ap.setIsYearly(false);
		ap.setIsPasswordProtected(false);

		ap.setUserId(usersDao.getUser(userId));
		ap.setIsConnectedEvent(false);
		Long appointmentId = appointmentDao.addAppointmentObj(ap);
		assertNotNull("Cann't add appointment", appointmentId);
	
		String jNameMemberTimeZone = "";
		if (user.getOmTimeZone() != null) {
			jNameMemberTimeZone = user.getOmTimeZone().getJname();
		}
		
		Long mmId = meetingMemberDao.addMeetingMember(user.getFirstname(), user.getLastname(), "", "", appointmentId, userId, user.getAdresses().getEmail(), false, jNameMemberTimeZone, false);
		assertNotNull("Cann't add MeetingMember", mmId);
		
		MeetingMember mm = meetingMemberDao.getMeetingMemberById(mmId);
		assertNotNull("Cann't get MeetingMember", mm);
		
		mmId = meetingMemberDao.deleteMeetingMember(mmId);
		assertNotNull("Cann't delete MeetingMember", mmId);
		
		appointmentId = appointmentDao.deleteAppointement(appointmentId);
		assertNotNull("Cann't delete appointment", appointmentId);
	}
}
