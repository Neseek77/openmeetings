package org.openmeetings.app.remote;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.basic.dao.OmTimeZoneDaoImpl;
import org.openmeetings.app.data.conference.Invitationmanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.persistence.beans.basic.Configuration;
import org.openmeetings.app.persistence.beans.basic.OmTimeZone;
import org.openmeetings.app.persistence.beans.invitation.Invitations;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class InvitationService implements IPendingServiceCallback {

	private static final Logger log = Red5LoggerFactory.getLogger(
			InvitationService.class, ScopeApplicationAdapter.webAppRootKey);
	@Autowired
	private Sessionmanagement sessionManagement;
	@Autowired
	private Configurationmanagement cfgManagement;
	@Autowired
	private Usermanagement userManagement;
	@Autowired
	private OmTimeZoneDaoImpl omTimeZoneDaoImpl;
	@Autowired
	private Invitationmanagement invitationManagement;

	public void resultReceived(IPendingServiceCall arg0) {
		// TODO Auto-generated method stub
		log.debug("InvitationService resultReceived" + arg0);
	}

	/**
	 * send an invitation to another user by Mail
	 * 
	 * @param SID
	 * @param username
	 * @param message
	 * @param baseurl
	 * @param email
	 * @param subject
	 * @param room_id
	 * @param conferencedomain
	 * @param isPasswordProtected
	 * @param invitationpass
	 * @param valid
	 * @param validFromDate
	 * @param validFromTime
	 * @param validToDate
	 * @param validToTime
	 * @return
	 */
	public String sendInvitationHash(String SID, String username,
			String message, String baseurl, String email, String subject,
			Long room_id, String conferencedomain, Boolean isPasswordProtected,
			String invitationpass, Integer valid, Date validFromDate,
			String validFromTime, Date validToDate, String validToTime,
			Long language_id, String jNameTimeZone) {

		try {
			log.debug("sendInvitationHash: ");

			Integer validFromHour = Integer.valueOf(
					validFromTime.substring(0, 2)).intValue();
			Integer validFromMinute = Integer.valueOf(
					validFromTime.substring(3, 5)).intValue();

			Integer validToHour = Integer.valueOf(validToTime.substring(0, 2))
					.intValue();
			Integer validToMinute = Integer
					.valueOf(validToTime.substring(3, 5)).intValue();

			log.info("validFromHour: " + validFromHour);
			log.info("validFromMinute: " + validFromMinute);

			Calendar calFrom = Calendar.getInstance();
			calFrom.setTime(validFromDate);
			calFrom.set(Calendar.HOUR_OF_DAY, validFromHour);
			calFrom.set(Calendar.MINUTE, validFromMinute);
			calFrom.set(Calendar.SECOND, 0);

			Calendar calTo = Calendar.getInstance();
			calTo.setTime(validToDate);
			calTo.set(Calendar.HOUR_OF_DAY, validToHour);
			calTo.set(Calendar.MINUTE, validToMinute);
			calTo.set(Calendar.SECOND, 0);

			Date dFrom = calFrom.getTime();
			Date dTo = calTo.getTime();

			Long users_id = sessionManagement.checkSession(SID);
			Long user_level = userManagement.getUserLevelByID(users_id);

			OmTimeZone omTimeZone = omTimeZoneDaoImpl
					.getOmTimeZone(jNameTimeZone);

			// If everything fails
			if (omTimeZone == null) {
				Configuration conf = cfgManagement.getConfKey(3L,
						"default.timezone");
				if (conf != null) {
					jNameTimeZone = conf.getConf_value();
				}
				omTimeZone = omTimeZoneDaoImpl.getOmTimeZone(jNameTimeZone);
			}

			String timeZoneName = omTimeZone.getIcal();

			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone(timeZoneName));
			int offset = cal.get(Calendar.ZONE_OFFSET)
					+ cal.get(Calendar.DST_OFFSET);

			log.debug("addAppointment offset :: " + offset);

			Date gmtTimeStart = new Date(dFrom.getTime() - offset);
			Date gmtTimeEnd = new Date(dTo.getTime() - offset);

			Invitations invitation = invitationManagement
					.addInvitationLink(user_level, username, message, baseurl,
							email, subject, room_id, conferencedomain,
							isPasswordProtected, invitationpass, valid, dFrom,
							dTo, users_id, baseurl, language_id, true,
							gmtTimeStart, gmtTimeEnd, null);

			if (invitation != null) {
				return "success";
			} else {
				return "Sys - Error";
			}
		} catch (Exception err) {
			log.error("[sendInvitationHash]", err);
		}

		return null;

		// return
		// invitationManagement.sendInvitionLink(user_level,
		// username, message, domain, room, roomtype, baseurl, email, subject,
		// room_id);
	}

	public Object getInvitationByHash(String hashCode) {
		return invitationManagement.getInvitationByHashCode(
				hashCode, true);
	}

	public Object checkInvitationPass(String hashCode, String pass) {
		return invitationManagement.checkInvitationPass(hashCode,
				pass);
	}
}
