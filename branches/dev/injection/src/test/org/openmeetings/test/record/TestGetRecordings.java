package org.openmeetings.test.record;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.data.record.dao.RecordingDaoImpl;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.persistence.beans.domain.Organisation_Users;
import org.openmeetings.app.persistence.beans.recording.Recording;
import org.openmeetings.app.persistence.beans.rooms.Rooms;
import org.openmeetings.app.persistence.beans.rooms.Rooms_Organisation;
import org.openmeetings.app.persistence.beans.user.Users;
import org.openmeetings.test.AbstractOpenmeetingsSpringTest;
import org.openmeetings.utils.math.CalendarPatterns;
import org.springframework.beans.factory.annotation.Autowired;

public class TestGetRecordings extends AbstractOpenmeetingsSpringTest {

	private static final Logger log = Logger.getLogger(TestGetRecordings.class);
	@Autowired
	// FIXME
	private RecordingDaoImpl recordingDao;
	@Autowired
	private Roommanagement roommanagement;
	@Autowired
	private UsersDaoImpl usersDao;

	@Test
	public void testBatchConversion() {
		try {

			Long users_id = 1L;

			String whereClause = "";

			int i = 0;
			List<Rooms> rooms = roommanagement.getPublicRooms(3L);
			for (Iterator<Rooms> iter = rooms.iterator(); iter.hasNext();) {
				Rooms room = iter.next();
				if (i == 0)
					whereClause += " (";
				else
					whereClause += " OR";
				whereClause += " c.rooms.rooms_id = " + room.getRooms_id()
						+ " ";
				i++;
			}

			Users us = usersDao.getUser(users_id);

			for (Iterator<Organisation_Users> iter = us.getOrganisation_users()
					.iterator(); iter.hasNext();) {
				Organisation_Users orgUser = iter.next();
				Long organisation_id = orgUser.getOrganisation()
						.getOrganisation_id();

				List<Rooms_Organisation> rOrgList = roommanagement
						.getRoomsOrganisationByOrganisationId(3,
								organisation_id);
				for (Iterator<Rooms_Organisation> iterOrgList = rOrgList
						.iterator(); iterOrgList.hasNext();) {
					Rooms_Organisation rOrg = iterOrgList.next();
					if (i == 0)
						whereClause += " (";
					else
						whereClause += " OR";
					whereClause += " c.rooms.rooms_id = "
							+ rOrg.getRoom().getRooms_id() + " ";
					i++;
				}

			}
			if (whereClause.length() != 0)
				whereClause += ") AND ";
			List<Recording> rList = recordingDao
					.getRecordingsByWhereClause(whereClause);

			for (Iterator<Recording> iter = rList.iterator(); iter.hasNext();) {
				Recording rec = iter.next();
				log.debug("rec: " + rec.getStarttime());
				rec.setStarttimeAsString(CalendarPatterns
						.getDateWithTimeByMiliSeconds(rec.getStarttime()));
			}

		} catch (Exception err) {

			log.error("testBatchConversion", err);

		}

	}

}
