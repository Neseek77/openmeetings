package org.openmeetings.app.data.conference;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.beans.basic.SearchResult;
import org.openmeetings.app.data.conference.dao.RoomModeratorsDaoImpl;
import org.openmeetings.app.data.user.Organisationmanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.persistence.beans.domain.Organisation_Users;
import org.openmeetings.app.persistence.beans.recording.RoomClient;
import org.openmeetings.app.persistence.beans.rooms.RoomTypes;
import org.openmeetings.app.persistence.beans.rooms.Rooms;
import org.openmeetings.app.persistence.beans.rooms.Rooms_Organisation;
import org.openmeetings.app.persistence.beans.sip.OpenXGReturnObject;
import org.openmeetings.app.persistence.beans.user.Users;
import org.openmeetings.app.remote.red5.ClientListManager;
import org.openmeetings.app.sip.xmlrpc.OpenXGHttpClient;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author swagner
 * 
 */
@Transactional
public class Roommanagement {

	private static final Logger log = Red5LoggerFactory
			.getLogger(Roommanagement.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Organisationmanagement organisationmanagement;
	@Autowired
	private OpenXGHttpClient openXGHttpClient;
	@Autowired
	private RoomModeratorsDaoImpl roomModeratorsDao;
	@Autowired
	private UsersDaoImpl usersDao;

	/**
	 * add a new Record to the table roomtypes
	 * 
	 * @param name
	 * @return ID of new created roomtype or null
	 */
	public Long addRoomType(String name) {
		try {
			RoomTypes rtype = new RoomTypes();
			rtype.setName(name);
			rtype.setStarttime(new Date());
			rtype.setDeleted("false");
			rtype = em.merge(rtype);
			long returnId = rtype.getRoomtypes_id();
			return returnId;
		} catch (Exception ex2) {
			log.error("[addRoomType] ", ex2);
		}
		return null;
	}

	public Long addRoom(Rooms room) {
		try {
			room.setStarttime(new Date());
			room = em.merge(room);
			long returnId = room.getRooms_id();
			return returnId;
		} catch (Exception ex2) {
			log.error("[addRoomType] ", ex2);
		}
		return null;
	}

	/**
	 * get all availible RoomTypes
	 * 
	 * @return List of RoomTypes
	 */
	@SuppressWarnings("unchecked")
	public List<RoomTypes> getAllRoomTypes(Long user_level) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				Query query = em
						.createQuery("select c from RoomTypes as c where c.deleted <> :deleted");
				query.setParameter("deleted", "true");
				List<RoomTypes> ll = query.getResultList();
				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getAllRoomTypes] ", ex2);
		}
		return null;
	}

	/**
	 * Get a RoomTypes-Object by its id
	 * 
	 * @param roomtypes_id
	 * @return RoomTypes-Object or NULL
	 */
	public RoomTypes getRoomTypesById(long roomtypes_id) {
		try {
			Query query = em
					.createQuery("select c from RoomTypes as c where c.roomtypes_id = :roomtypes_id AND c.deleted <> :deleted");
			query.setParameter("roomtypes_id", roomtypes_id);
			query.setParameter("deleted", "true");
			List<?> ll = query.getResultList();
			if (ll.size() > 0) {
				return (RoomTypes) ll.get(0);
			}
		} catch (Exception ex2) {
			log.error("[getRoomTypesById] ", ex2);
		}
		return null;
	}

	/**
	 * get a room object if user level
	 * 
	 * @param user_level
	 * @param rooms_id
	 * @return
	 */
	public Rooms getRoomById(long user_level, long rooms_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				return this.getRoomById(rooms_id);
			} else
				log.error("getRoombyId : Userlevel" + user_level
						+ " not allowed");
		} catch (Exception ex2) {
			log.error("[getRoomById] ", ex2);
		}
		return null;
	}

	public Rooms getRoomWithCurrentUsersById(long user_level, long rooms_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				Rooms room = this.getRoomById(rooms_id);

				if (room != null) {
					HashMap<String, RoomClient> map = ClientListManager
							.getInstance().getClientListByRoom(
									room.getRooms_id());

					room.setCurrentusers(new LinkedList<RoomClient>());

					for (Iterator<String> iter = map.keySet().iterator(); iter
							.hasNext();) {
						room.getCurrentusers().add(map.get(iter.next()));
					}

					return room;
				}
			}
		} catch (Exception ex2) {
			log.error("[getRoomWithCurrentUsersById] ", ex2);
		}
		return null;
	}

	/**
	 * Get a Rooms-Object or NULL
	 * 
	 * @param rooms_id
	 * @return Rooms-Object or NULL
	 */
	public Rooms getRoomById(long rooms_id) {
		log.debug("getRoombyId : " + rooms_id);
		try {

			if (rooms_id == 0) {
				return null;
			}

			String hql = "select c from Rooms as c where c.rooms_id = :rooms_id AND c.deleted <> :deleted";
			Rooms room = null;

			Query query = em.createQuery(hql);
			query.setParameter("rooms_id", rooms_id);
			query.setParameter("deleted", "true");
			List<?> ll = query.getResultList();
			if (ll.size() > 0) {
				room = (Rooms) ll.get(0);
			}

			if (room != null) {
				return room;
			} else {
				log.info("Could not find room " + rooms_id);
			}
		} catch (Exception ex2) {
			log.error("[getRoomById] ", ex2);
		}
		return null;
	}

	/**
	 * Get a Rooms-Object or NULL
	 * 
	 * @param externalRoomId
	 * @return Rooms-Object or NULL
	 */
	public Rooms getRoomByExternalId(Long externalRoomId,
			String externalRoomType, long roomtypes_id) {
		log.debug("getRoombyExternalId : " + externalRoomId + " - "
				+ externalRoomType + " - " + roomtypes_id);
		try {
			String hql = "select c from Rooms as c JOIN c.roomtype as rt "
					+ "where c.externalRoomId = :externalRoomId AND c.externalRoomType = :externalRoomType "
					+ "AND rt.roomtypes_id = :roomtypes_id AND c.deleted <> :deleted";
			Query query = em.createQuery(hql);
			query.setParameter("externalRoomId", externalRoomId);
			query.setParameter("externalRoomType", externalRoomType);
			query.setParameter("roomtypes_id", roomtypes_id);
			query.setParameter("deleted", "true");
			List<?> ll = query.getResultList();
			if (ll.size() > 0) {
				return (Rooms) ll.get(0);
			} else {
				log.error("Could not find room " + externalRoomId);
			}
		} catch (Exception ex2) {
			log.error("[getRoomByExternalId] ", ex2);
		}
		return null;
	}

	public Rooms getRoomByExternalId(long user_level, Long externalRoomId,
			String externalRoomType, long roomtypes_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				return this.getRoomByExternalId(externalRoomId,
						externalRoomType, roomtypes_id);
			} else
				log.error("getRoombyExternalId : Userlevel" + user_level
						+ " not allowed");
		} catch (Exception ex2) {
			log.error("[getRoomByExternalId] ", ex2);
		}
		return null;
	}

	public SearchResult getRooms(long user_level, int start, int max,
			String orderby, boolean asc, String search) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				SearchResult sResult = new SearchResult();
				sResult.setRecords(this.selectMaxFromRooms(search));
				sResult.setObjectName(Rooms.class.getName());
				sResult.setResult(this.getRoomsInternatlByHQL(start, max,
						orderby, asc, search));
				return sResult;
			}
		} catch (Exception ex2) {
			log.error("[getRooms] ", ex2);
		}
		return null;
	}

	public SearchResult getRoomsWithCurrentUsers(long user_level, int start,
			int max, String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				SearchResult sResult = new SearchResult();
				sResult.setRecords(this.selectMaxFromRooms(""));
				sResult.setObjectName(Rooms.class.getName());

				List<Rooms> rooms = this.getRoomsInternatl(start, max, orderby,
						asc);

				for (Rooms room : rooms) {

					HashMap<String, RoomClient> map = ClientListManager
							.getInstance().getClientListByRoom(
									room.getRooms_id());

					room.setCurrentusers(new LinkedList<RoomClient>());

					for (Iterator<String> iter = map.keySet().iterator(); iter
							.hasNext();) {
						room.getCurrentusers().add(map.get(iter.next()));
					}

				}

				sResult.setResult(rooms);
				return sResult;
			}
		} catch (Exception ex2) {
			log.error("[getRooms] ", ex2);
		}
		return null;
	}

	public List<Rooms> getRoomsWithCurrentUsersByList(long user_level,
			int start, int max, String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {

				List<Rooms> rooms = this.getRoomsInternatl(start, max, orderby,
						asc);

				for (Rooms room : rooms) {

					HashMap<String, RoomClient> map = ClientListManager
							.getInstance().getClientListByRoom(
									room.getRooms_id());

					room.setCurrentusers(new LinkedList<RoomClient>());

					for (Iterator<String> iter = map.keySet().iterator(); iter
							.hasNext();) {
						room.getCurrentusers().add(map.get(iter.next()));
					}

				}

				return rooms;

			}
		} catch (Exception ex2) {
			log.error("[getRooms] ", ex2);
		}
		return null;
	}

	public List<Rooms> getRoomsWithCurrentUsersByListAndType(long user_level,
			int start, int max, String orderby, boolean asc,
			String externalRoomType) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {

				List<Rooms> rooms = this.getRoomsInternatlbyType(start, max,
						orderby, asc, externalRoomType);

				for (Rooms room : rooms) {

					HashMap<String, RoomClient> map = ClientListManager
							.getInstance().getClientListByRoom(
									room.getRooms_id());

					room.setCurrentusers(new LinkedList<RoomClient>());

					for (Iterator<String> iter = map.keySet().iterator(); iter
							.hasNext();) {
						room.getCurrentusers().add(map.get(iter.next()));
					}

				}

				return rooms;

			}
		} catch (Exception ex2) {
			log.error("[getRooms] ", ex2);
		}
		return null;
	}

	public Long selectMaxFromRooms(String search) {
		try {
			String hql = "select count(c.rooms_id) from Rooms c "
					+ "where c.deleted <> 'true' " + "AND c.name LIKE :search ";

			if (search.length() == 0) {
				search = "%";
			} else {
				search = "%" + search + "%";
			}

			// get all users
			Query query = em.createQuery(hql);
			query.setParameter("search", search);
			List<?> ll = query.getResultList();
			log.debug("Number of records" + ll.get(0));
			return (Long) ll.get(0);
		} catch (Exception ex2) {
			log.error("[selectMaxFromRooms] ", ex2);
		}
		return null;
	}

	/**
	 * gets a list of all availible rooms
	 * 
	 * @param user_level
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public List<Rooms> getRoomsInternatl(int start, int max, String orderby,
			boolean asc) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
			Root<Rooms> c = cq.from(Rooms.class);
			Predicate condition = cb.equal(c.get("deleted"), "false");
			cq.where(condition);
			cq.distinct(asc);
			if (asc) {
				cq.orderBy(cb.asc(c.get(orderby)));
			} else {
				cq.orderBy(cb.desc(c.get(orderby)));
			}
			TypedQuery<Rooms> q = em.createQuery(cq);
			q.setFirstResult(start);
			q.setMaxResults(max);
			List<Rooms> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getRooms ] ", ex2);
		}
		return null;
	}

	/**
	 * gets a list of all availible rooms
	 * 
	 * @param user_level
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Rooms> getRoomsInternatlByHQL(int start, int max,
			String orderby, boolean asc, String search) {
		try {

			String hql = "select c from Rooms c "
					+ "where c.deleted <> 'true' " + "AND c.name LIKE :search ";

			if (search.length() == 0) {
				search = "%";
			} else {
				search = "%" + search + "%";
			}

			hql += " ORDER BY " + orderby;

			if (asc) {
				hql += " ASC";
			} else {
				hql += " DESC";
			}

			Query query = em.createQuery(hql);
			query.setParameter("search", search);
			query.setFirstResult(start);
			query.setMaxResults(max);

			return query.getResultList();

		} catch (Exception ex2) {
			log.error("[getRooms ] ", ex2);
		}
		return null;
	}

	public List<Rooms> getAllRooms() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
			Root<Rooms> c = cq.from(Rooms.class);
			Predicate condition = cb.equal(c.get("deleted"), "false");
			cq.where(condition);
			TypedQuery<Rooms> q = em.createQuery(cq);
			List<Rooms> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getAllRooms]", ex2);
		}
		return null;
	}

	public List<Rooms> getBackupRooms() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
			TypedQuery<Rooms> q = em.createQuery(cq);
			List<Rooms> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getBackupRooms]", ex2);
		}
		return null;
	}

	public List<Rooms> getRoomsInternatlbyType(int start, int max,
			String orderby, boolean asc, String externalRoomType) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
			Root<Rooms> c = cq.from(Rooms.class);
			Predicate condition = cb.equal(c.get("deleted"), "false");
			Predicate subCondition = cb.equal(c.get("externalRoomType"),
					externalRoomType);
			cq.where(condition, subCondition);
			cq.distinct(asc);
			if (asc) {
				cq.orderBy(cb.asc(c.get(orderby)));
			} else {
				cq.orderBy(cb.desc(c.get(orderby)));
			}
			TypedQuery<Rooms> q = em.createQuery(cq);
			q.setFirstResult(start);
			q.setMaxResults(max);
			List<Rooms> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getRooms ] ", ex2);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Rooms_Organisation> getOrganisationsByRoom(long user_level,
			long rooms_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				String hql = "select c from Rooms_Organisation as c "
						+ "where c.room.rooms_id = :rooms_id "
						+ "AND c.deleted <> :deleted";
				Query q = em.createQuery(hql);

				q.setParameter("rooms_id", rooms_id);
				q.setParameter("deleted", "true");
				List<Rooms_Organisation> ll = q.getResultList();
				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getOrganisationsByRoom] ", ex2);
		}
		return null;
	}

	/**
	 * get all rooms which are availible for public
	 * 
	 * @param user_level
	 * @param roomtypes_id
	 * @return
	 */
	public List<Rooms> getPublicRooms(long user_level, long roomtypes_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				String queryString = "SELECT r from Rooms r "
						+ "JOIN r.roomtype as rt "
						+ "WHERE "
						+ "r.ispublic=:ispublic and r.deleted=:deleted and rt.roomtypes_id=:roomtypes_id";
				Query q = em.createQuery(queryString);
				//
				q.setParameter("ispublic", true);
				q.setParameter("deleted", "false");
				q.setParameter("roomtypes_id", new Long(roomtypes_id));

				@SuppressWarnings("unchecked")
				List<Rooms> ll = q.getResultList();

				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	public List<Rooms> getRoomsByIds(List<Integer> roomIds) {
		try {
			if (roomIds == null || roomIds.size() == 0) {
				return new LinkedList<Rooms>();
			}

			String queryString = "SELECT r from Rooms r " + "WHERE ";

			queryString += "(";

			int i = 0;
			for (Integer room_id : roomIds) {
				if (i != 0) {
					queryString += " OR ";
				}
				queryString += " r.rooms_id = " + room_id;
				i++;
			}

			queryString += ")";

			Query q = em.createQuery(queryString);

			@SuppressWarnings("unchecked")
			List<Rooms> ll = q.getResultList();

			return ll;

		} catch (Exception ex2) {
			log.error("[getRoomsByIds] ", ex2);
		}
		return null;
	}

	public List<Rooms> getPublicRoomsWithoutType(long user_level) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {

				String queryString = "SELECT r from Rooms r " + "WHERE "
						+ "r.ispublic = :ispublic and r.deleted <> :deleted "
						+ "ORDER BY r.name ASC";

				Query q = em.createQuery(queryString);

				q.setParameter("ispublic", true);
				q.setParameter("deleted", "true");
				@SuppressWarnings("unchecked")
				List<Rooms> ll = q.getResultList();

				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getPublicRoomsWithoutType] ", ex2);
			ex2.printStackTrace();
		}
		return null;
	}

	/**
	 * Get Appointed Meetings
	 */
	// ---------------------------------------------------------------------------------------------
	public List<Rooms> getAppointedMeetings(Long userid, Long user_level) {
		log.debug("Roommanagement.getAppointedMeetings");

		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {

				String queryString = "SELECT r from Rooms r "
						+ "JOIN r.roomtype as rt " + "WHERE "
						+ "r.deleted=:deleted and r.appointment=:appointed";
				Query q = em.createQuery(queryString);
				//
				q.setParameter("appointed", true);
				q.setParameter("deleted", "false");

				@SuppressWarnings("unchecked")
				List<Rooms> ll = q.getResultList();

				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getAppointedMeetings] ", ex2);
		}
		return null;

	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * get all rooms which are availible for public
	 * 
	 * @param user_level
	 * @param roomtypes_id
	 * @return
	 */
	public List<Rooms> getPublicRooms(long user_level) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				// log.error("### getPublicRooms: create Query "+roomtypes_id);
				String queryString = "SELECT r from Rooms r "
						+ "JOIN r.roomtype as rt " + "WHERE "
						+ "r.ispublic=:ispublic and r.deleted=:deleted";
				Query q = em.createQuery(queryString);
				//
				q.setParameter("ispublic", true);
				q.setParameter("deleted", "false");

				@SuppressWarnings("unchecked")
				List<Rooms> ll = q.getResultList();
				return ll;
			}
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * adds a new Record to the table rooms
	 * 
	 * @param name
	 * @param roomtypes_id
	 * @param ispublic
	 * @return id of the newly created room or NULL
	 */
	public Long addRoom(long user_level, String name, long roomtypes_id,
			String comment, Long numberOfPartizipants, boolean ispublic,
			List<?> organisations, Boolean appointment, Boolean isDemoRoom,
			Integer demoTime, Boolean isModeratedRoom, List<?> roomModerators,
			Boolean allowUserQuestions, Boolean isAudioOnly, Boolean isClosed,
			String redirectURL, String sipNumber, String conferencePin,
			Long ownerId, Boolean waitForRecording, Boolean allowRecording,
			Boolean hideTopBar) {

		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {

				Rooms r = new Rooms();
				r.setName(name);
				r.setComment(comment);
				r.setStarttime(new Date());
				r.setNumberOfPartizipants(numberOfPartizipants);
				r.setRoomtype(this.getRoomTypesById(roomtypes_id));
				r.setIspublic(ispublic);
				r.setAllowUserQuestions(allowUserQuestions);
				r.setIsAudioOnly(isAudioOnly);

				r.setAppointment(appointment);

				r.setIsDemoRoom(isDemoRoom);
				r.setDemoTime(demoTime);

				r.setIsModeratedRoom(isModeratedRoom);
				r.setHideTopBar(hideTopBar);

				r.setDeleted("false");

				r.setIsClosed(isClosed);
				r.setRedirectURL(redirectURL);

				r.setSipNumber(sipNumber);
				r.setConferencePin(conferencePin);
				r.setOwnerId(ownerId);

				r.setWaitForRecording(waitForRecording);
				r.setAllowRecording(allowRecording);

				// handle SIP Issues
				OpenXGReturnObject openXGReturnObject = openXGHttpClient
						.openSIPgCreateConference();

				if (openXGReturnObject != null) {
					r.setSipNumber(openXGReturnObject.getConferenceNumber());
					r.setConferencePin(openXGReturnObject.getConferencePin());
				}

				r = em.merge(r);
				long returnId = r.getRooms_id();

				if (organisations != null) {
					Long t = this.updateRoomOrganisations(organisations, r);
					if (t == null)
						return null;
				}

				if (roomModerators != null) {
					roomModeratorsDao
							.addRoomModeratorByUserList(roomModerators,
									r.getRooms_id());
				}

				return returnId;
			}
		} catch (Exception ex2) {
			log.error("[addRoom] ", ex2);
		}
		return null;
	}

	public Long addRoomByMod(long user_level, String name, long roomtypes_id,
			String comment, Long numberOfPartizipants, boolean ispublic,
			Long organisation_id, Boolean appointment, Boolean isDemoRoom,
			Integer demoTime, Boolean isModeratedRoom, List<?> roomModerators,
			Boolean allowUserQuestions) {

		log.debug("addRoom");

		try {
			if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
				Rooms r = new Rooms();
				r.setName(name);
				r.setComment(comment);
				r.setStarttime(new Date());
				r.setNumberOfPartizipants(numberOfPartizipants);
				r.setRoomtype(this.getRoomTypesById(roomtypes_id));
				r.setIspublic(ispublic);

				r.setAllowUserQuestions(allowUserQuestions);
				r.setAppointment(appointment);

				r.setIsDemoRoom(isDemoRoom);
				r.setDemoTime(demoTime);

				r.setIsModeratedRoom(isModeratedRoom);

				r.setDeleted("false");
				r = em.merge(r);
				long returnId = r.getRooms_id();

				this.addRoomToOrganisation(3, returnId, organisation_id);

				if (roomModerators != null) {
					roomModeratorsDao
							.addRoomModeratorByUserList(roomModerators,
									r.getRooms_id());
				}

				return returnId;
			}
		} catch (Exception ex2) {
			log.error("[addRoom] ", ex2);
		}
		return null;
	}

	/**
	 * adds/check a new Record to the table rooms with external fields
	 * 
	 * @param name
	 * @param roomtypes_id
	 * @param ispublic
	 * @return id of (the newly created) room or NULL
	 */
	public Long addExternalRoom(String name, long roomtypes_id, String comment,
			Long numberOfPartizipants, boolean ispublic, List<?> organisations,
			Boolean appointment, Boolean isDemoRoom, Integer demoTime,
			Boolean isModeratedRoom, List<?> roomModerators,
			Long externalRoomId, String externalRoomType,
			Boolean allowUserQuestions, Boolean isAudioOnly, Boolean isClosed,
			String redirectURL, Boolean waitForRecording,
			Boolean allowRecording, Boolean hideTopBar) {

		log.debug("addExternalRoom");

		try {
			Rooms r = new Rooms();
			r.setName(name);
			r.setComment(comment);
			r.setStarttime(new Date());
			r.setNumberOfPartizipants(numberOfPartizipants);
			r.setRoomtype(this.getRoomTypesById(roomtypes_id));
			r.setIspublic(ispublic);

			r.setAllowUserQuestions(allowUserQuestions);
			r.setIsAudioOnly(isAudioOnly);

			r.setAppointment(appointment);

			r.setIsDemoRoom(isDemoRoom);
			r.setDemoTime(demoTime);

			r.setIsModeratedRoom(isModeratedRoom);

			r.setDeleted("false");

			r.setExternalRoomId(externalRoomId);
			r.setExternalRoomType(externalRoomType);

			r.setIsClosed(isClosed);
			r.setRedirectURL(redirectURL);

			r.setWaitForRecording(waitForRecording);
			r.setAllowRecording(allowRecording);

			r.setHideTopBar(hideTopBar);

			r = em.merge(r);

			long returnId = r.getRooms_id();

			if (organisations != null) {
				Long t = this.updateRoomOrganisations(organisations, r);
				if (t == null)
					return null;
			}

			if (roomModerators != null) {
				roomModeratorsDao.addRoomModeratorByUserList(
						roomModerators, r.getRooms_id());
			}

			return returnId;
		} catch (Exception ex2) {
			log.error("[addExternalRoom] ", ex2);
		}
		return null;
	}

	/**
	 * adds a new record to the table rooms_organisation
	 * 
	 * @param rooms_id
	 * @param organisation_id
	 * @return the id of the newly created Rooms_Organisation or NULL
	 */
	public Long addRoomToOrganisation(long user_level, long rooms_id,
			long organisation_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				Rooms_Organisation rOrganisation = new Rooms_Organisation();
				rOrganisation.setRoom(this.getRoomById(rooms_id));
				log.error("addRoomToOrganisation rooms "
						+ rOrganisation.getRoom().getName());
				rOrganisation.setStarttime(new Date());
				rOrganisation.setOrganisation(organisationmanagement
						.getOrganisationById(organisation_id));
				rOrganisation.setDeleted("false");

				rOrganisation = em.merge(rOrganisation);
				long returnId = rOrganisation.getRooms_organisation_id();
				return returnId;
			}
		} catch (Exception ex2) {
			log.error("[addRoomToOrganisation] ", ex2);
		}
		return null;
	}

	public Long addRoomOrganisation(Rooms_Organisation rOrganisation) {
		try {

			rOrganisation.setStarttime(new Date());

			rOrganisation = em.merge(rOrganisation);
			long returnId = rOrganisation.getRooms_organisation_id();
			return returnId;

		} catch (Exception ex2) {
			log.error("[addRoomOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param rooms_organisation_id
	 * @return
	 */
	public Rooms_Organisation getRoomsOrganisationById(
			long rooms_organisation_id) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms_Organisation> cq = cb
					.createQuery(Rooms_Organisation.class);
			Root<Rooms_Organisation> c = cq.from(Rooms_Organisation.class);
			Predicate condition = cb.equal(c.get("rooms_organisation_id"),
					rooms_organisation_id);
			cq.where(condition);
			TypedQuery<Rooms_Organisation> q = em.createQuery(cq);
			List<Rooms_Organisation> ll = q.getResultList();

			if (ll.size() > 0) {
				return ll.get(0);
			}
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * get List of Rooms_Organisation by organisation and roomtype
	 * 
	 * @param user_level
	 * @param organisation_id
	 * @param roomtypes_id
	 * @return
	 */
	public List<Rooms_Organisation> getRoomsOrganisationByOrganisationIdAndRoomType(
			long user_level, long organisation_id, long roomtypes_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				String hql = "select c from Rooms_Organisation as c "
						+ "where c.room.roomtypes_id = :roomtypes_id "
						+ "AND c.organisation.organisation_id = :organisation_id "
						+ "AND c.deleted <> :deleted";
				Query q = em.createQuery(hql);

				q.setParameter("roomtypes_id", roomtypes_id);
				q.setParameter("organisation_id", organisation_id);
				q.setParameter("deleted", "true");
				@SuppressWarnings("unchecked")
				List<Rooms_Organisation> ll = q.getResultList();

				return ll;
			} else {
				log.error("[notauthentificated] " + user_level);
			}
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * Gets all rooms by an organisation
	 * 
	 * @param organisation_id
	 * @return list of Rooms_Organisation with Rooms as Sub-Objects or null
	 */
	public List<Rooms_Organisation> getRoomsOrganisationByOrganisationId(
			long user_level, long organisation_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {

				String hql = "SELECT c FROM Rooms_Organisation c "
						+ "WHERE c.organisation.organisation_id = :organisation_id "
						+ "AND c.deleted <> :deleted AND c.room.deleted <> :deleted "
						+ "AND c.organisation.deleted <> :deleted "
						+ "ORDER BY c.room.name ASC";

				Query query = em.createQuery(hql);

				query.setParameter("organisation_id", organisation_id);
				query.setParameter("deleted", "true");

				@SuppressWarnings("unchecked")
				List<Rooms_Organisation> ll = query.getResultList();

				return ll;
			} else {
				log.error("[notauthentificated] " + user_level);
			}
		} catch (Exception ex2) {
			log.error("[getPublicRoomsWithoutType] ", ex2);
			ex2.printStackTrace();
		}
		return null;
	}

	public SearchResult getRoomsOrganisationByOrganisationId(long user_level,
			long organisation_id, int start, int max, String orderby,
			boolean asc) {
		try {
			if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {

				SearchResult sResult = new SearchResult();
				sResult.setObjectName(Rooms_Organisation.class.getName());
				sResult.setRecords(this.selectMaxFromRoomsByOrganisation(
						organisation_id).longValue());
				sResult.setResult(this.getRoomsOrganisationByOrganisationId(
						organisation_id, start, max, orderby, asc));
				return sResult;
			}
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	public Integer selectMaxFromRoomsByOrganisation(long organisation_id) {
		try {
			// get all users
			String hql = "select c from Rooms_Organisation as c "
					+ "where c.organisation.organisation_id = :organisation_id "
					+ "AND c.deleted <> :deleted";
			Query q = em.createQuery(hql);

			q.setParameter("organisation_id", organisation_id);
			q.setParameter("deleted", "true");
			@SuppressWarnings("unchecked")
			List<Rooms_Organisation> ll = q.getResultList();

			return ll.size();
		} catch (Exception ex2) {
			log.error("[selectMaxFromRooms] ", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param organisation_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	private List<Rooms_Organisation> getRoomsOrganisationByOrganisationId(
			long organisation_id, int start, int max, String orderby,
			boolean asc) {
		try {
			String hql = "select c from Rooms_Organisation as c "
					+ "where c.organisation.organisation_id = :organisation_id "
					+ "AND c.deleted <> :deleted";
			if (orderby.startsWith("c.")) {
				hql += "ORDER BY " + orderby;
			} else {
				hql += "ORDER BY " + "c." + orderby;
			}
			if (asc) {
				hql += " ASC";
			} else {
				hql += " DESC";
			}

			Query q = em.createQuery(hql);

			q.setParameter("organisation_id", organisation_id);
			q.setParameter("deleted", "true");
			q.setFirstResult(start);
			q.setMaxResults(max);
			@SuppressWarnings("unchecked")
			List<Rooms_Organisation> ll = q.getResultList();

			return ll;
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	private Rooms_Organisation getRoomsOrganisationByOrganisationIdAndRoomId(
			long organisation_id, long rooms_id) {
		try {
			String hql = "select c from Rooms_Organisation as c "
					+ "where c.room.rooms_id = :rooms_id "
					+ "AND c.organisation.organisation_id = :organisation_id "
					+ "AND c.deleted <> :deleted";
			Query q = em.createQuery(hql);

			q.setParameter("rooms_id", rooms_id);
			q.setParameter("organisation_id", organisation_id);
			q.setParameter("deleted", "true");
			@SuppressWarnings("unchecked")
			List<Rooms_Organisation> ll = q.getResultList();

			if (ll.size() > 0) {
				return ll.get(0);
			}
		} catch (Exception ex2) {
			log.error("[getRoomsOrganisationByOrganisationIdAndRoomId] ", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param organisation_id
	 * @return
	 */
	public List<Rooms_Organisation> getRoomsOrganisationByRoomsId(long rooms_id) {
		try {
			String hql = "select c from Rooms_Organisation as c "
					+ "where c.room.rooms_id = :rooms_id "
					+ "AND c.deleted <> :deleted";
			Query q = em.createQuery(hql);

			q.setParameter("rooms_id", rooms_id);
			q.setParameter("deleted", "true");
			@SuppressWarnings("unchecked")
			List<Rooms_Organisation> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	public List<Rooms_Organisation> getRoomsOrganisations() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Rooms_Organisation> cq = cb
					.createQuery(Rooms_Organisation.class);
			TypedQuery<Rooms_Organisation> q = em.createQuery(cq);
			List<Rooms_Organisation> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getRoomsByOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @param user_id
	 * @param rooms_id
	 * @return
	 */
	private boolean checkUserOrgRoom(long user_id, long rooms_id) {
		try {

			Users us = usersDao.getUser(user_id);
			List<Organisation_Users> s = us.getOrganisation_users();

			for (Iterator<Organisation_Users> it = s.iterator(); it.hasNext();) {
				Organisation_Users orgUsers = it.next();
				long organisation_id = orgUsers.getOrganisation()
						.getOrganisation_id();
				List<Rooms_Organisation> ll = this
						.getRoomsOrganisationByOrganisationId(3,
								organisation_id);
				for (Iterator<Rooms_Organisation> it2 = ll.iterator(); it2
						.hasNext();) {
					Rooms_Organisation roomOrg = it2.next();
					if (roomOrg.getRoom().getRooms_id() == rooms_id) {
						return true;
					}
				}
			}

		} catch (Exception ex2) {
			log.error("[checkUserOrgRoom] ", ex2);
		}
		return false;
	}

	/**
	 * 
	 * @param user_id
	 * @param user_level
	 * @param rooms_id
	 * @param roomtypes_id
	 * @param name
	 * @param ispublic
	 * @param comment
	 * @return
	 */
	public Rooms updateRoomsSelf(long user_id, long user_level, long rooms_id,
			long roomtypes_id, String name, boolean ispublic, String comment) {
		try {
			if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {

				if (this.checkUserOrgRoom(user_id, rooms_id)) {

					Rooms r = this.getRoomById(rooms_id);
					r.setComment(comment);
					r.setIspublic(ispublic);
					r.setName(name);
					r.setRoomtype(this.getRoomTypesById(roomtypes_id));
					r.setUpdatetime(new Date());

					if (r.getRooms_id() == null) {
						em.persist(r);
					} else {
						if (!em.contains(r)) {
							em.merge(r);
						}
					}
				}
			}
		} catch (Exception ex2) {
			log.error("[updateRoom] ", ex2);
		}
		return null;
	}

	/**
	 * Update a Record in the rooms table
	 * 
	 * @param rooms_id
	 * @param roomtypes_id
	 * @param name
	 * @param ispublic
	 * @param comment
	 * @return
	 */
	public Long updateRoom(long user_level, long rooms_id, long roomtypes_id,
			String name, boolean ispublic, String comment,
			Long numberOfPartizipants, List<?> organisations,
			Boolean appointment, Boolean isDemoRoom, Integer demoTime,
			Boolean isModeratedRoom, List<?> roomModerators,
			Boolean allowUserQuestions, Boolean isAudioOnly, Boolean isClosed,
			String redirectURL, String sipNumber, String conferencePin,
			Long ownerId, Boolean waitForRecording, Boolean allowRecording,
			Boolean hideTopBar) {
		try {

			log.debug("*** updateRoom numberOfPartizipants: "
					+ numberOfPartizipants);
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {

				return this.updateRoomInternal(rooms_id, roomtypes_id, name,
						ispublic, comment, numberOfPartizipants, organisations,
						appointment, isDemoRoom, demoTime, isModeratedRoom,
						roomModerators, allowUserQuestions, isAudioOnly,
						isClosed, redirectURL, sipNumber, conferencePin,
						ownerId, waitForRecording, allowRecording, hideTopBar);

			}

		} catch (Exception ex2) {
			log.error("[updateRoom] ", ex2);
		}
		return null;
	}

	public Long updateRoomInternal(long rooms_id, long roomtypes_id,
			String name, boolean ispublic, String comment,
			Long numberOfPartizipants, List<?> organisations,
			Boolean appointment, Boolean isDemoRoom, Integer demoTime,
			Boolean isModeratedRoom, List<?> roomModerators,
			Boolean allowUserQuestions, Boolean isAudioOnly, Boolean isClosed,
			String redirectURL, String sipNumber, String conferencePin,
			Long ownerId, Boolean waitForRecording, Boolean allowRecording,
			Boolean hideTopBar) {
		try {
			log.debug("*** updateRoom numberOfPartizipants: "
					+ numberOfPartizipants);
			Rooms r = this.getRoomById(rooms_id);
			r.setComment(comment);

			r.setIspublic(ispublic);
			r.setNumberOfPartizipants(numberOfPartizipants);
			r.setName(name);
			r.setRoomtype(this.getRoomTypesById(roomtypes_id));
			r.setUpdatetime(new Date());
			r.setAllowUserQuestions(allowUserQuestions);
			r.setIsAudioOnly(isAudioOnly);

			r.setIsDemoRoom(isDemoRoom);
			r.setDemoTime(demoTime);

			r.setAppointment(appointment);

			r.setIsModeratedRoom(isModeratedRoom);
			r.setHideTopBar(hideTopBar);

			r.setIsClosed(isClosed);
			r.setRedirectURL(redirectURL);

			r.setSipNumber(sipNumber);
			r.setConferencePin(conferencePin);
			r.setOwnerId(ownerId);

			r.setWaitForRecording(waitForRecording);
			r.setAllowRecording(allowRecording);

			if (r.getRooms_id() == null) {
				em.persist(r);
			} else {
				if (!em.contains(r)) {
					r = em.merge(r);
				}
			}

			if (organisations != null) {
				Long t = this.updateRoomOrganisations(organisations, r);
				if (t == null)
					return null;
			}
			if (roomModerators != null) {
				roomModeratorsDao
						.updateRoomModeratorByUserList(roomModerators,
								r.getRooms_id());
			}

			return r.getRooms_id();
		} catch (Exception ex2) {
			log.error("[updateRoom] ", ex2);
		}
		return null;
	}

	public Long updateRoomByMod(long user_level, long rooms_id,
			long roomtypes_id, String name, boolean ispublic, String comment,
			Long numberOfPartizipants, Long organisations, Boolean appointment,
			Boolean isDemoRoom, Integer demoTime, Boolean isModeratedRoom,
			List<?> roomModerators, Boolean allowUserQuestions) {
		try {
			log.debug("*** updateRoom numberOfPartizipants: "
					+ numberOfPartizipants);
			if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
				Rooms r = this.getRoomById(rooms_id);
				r.setComment(comment);

				r.setIspublic(ispublic);
				r.setNumberOfPartizipants(numberOfPartizipants);
				r.setName(name);
				r.setRoomtype(this.getRoomTypesById(roomtypes_id));
				r.setUpdatetime(new Date());
				r.setAllowUserQuestions(allowUserQuestions);

				r.setIsDemoRoom(isDemoRoom);
				r.setDemoTime(demoTime);

				r.setAppointment(appointment);

				r.setIsModeratedRoom(isModeratedRoom);

				if (r.getRooms_id() == null) {
					em.persist(r);
				} else {
					if (!em.contains(r)) {
						em.merge(r);
					}
				}

				// FIXME: Organizations will not be changed when you do an
				// update as Moderator

				if (roomModerators != null) {
					roomModeratorsDao
							.updateRoomModeratorByUserList(roomModerators,
									r.getRooms_id());
				}

				return r.getRooms_id();
			}
		} catch (Exception ex2) {
			log.error("[updateRoom] ", ex2);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private boolean checkRoomAlreadyInOrg(Long orgid, List organisations)
			throws Exception {
		for (Iterator it = organisations.iterator(); it.hasNext();) {
			Rooms_Organisation rOrganisation = (Rooms_Organisation) it.next();
			if (rOrganisation.getOrganisation().getOrganisation_id()
					.equals(orgid))
				return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private boolean checkRoomShouldByDeleted(long orgId, List organisations)
			throws Exception {
		for (Iterator it = organisations.iterator(); it.hasNext();) {
			Integer key = (Integer) it.next();
			Long storedOrgId = key.longValue();
			if (storedOrgId.equals(orgId))
				return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private Long updateRoomOrganisations(List organisations, Rooms room)
			throws Exception {
		List roomOrganisations = this.getOrganisationsByRoom(3,
				room.getRooms_id());

		List<Long> roomsToAdd = new LinkedList<Long>();
		List<Long> roomsToDel = new LinkedList<Long>();

		for (Iterator it = organisations.iterator(); it.hasNext();) {
			Integer key = (Integer) it.next();
			Long orgIdToAdd = key.longValue();
			if (!this.checkRoomAlreadyInOrg(orgIdToAdd, roomOrganisations))
				roomsToAdd.add(orgIdToAdd);
		}

		for (Iterator it = roomOrganisations.iterator(); it.hasNext();) {
			Rooms_Organisation rOrganisation = (Rooms_Organisation) it.next();
			Long orgIdToDel = rOrganisation.getOrganisation()
					.getOrganisation_id();
			if (!this.checkRoomShouldByDeleted(orgIdToDel, organisations))
				roomsToDel.add(orgIdToDel);
		}

		// log.error("updateRoomOrganisations roomsToAdd: "+roomsToAdd.size());
		// log.error("updateRoomOrganisations roomsToDel: "+roomsToDel.size());

		for (Iterator<Long> it = roomsToAdd.iterator(); it.hasNext();) {
			Long orgIdToAdd = it.next();
			this.addRoomToOrganisation(3, room.getRooms_id(), orgIdToAdd);
		}
		for (Iterator<Long> it = roomsToDel.iterator(); it.hasNext();) {
			Long orgToDel = it.next();
			this.deleteRoomFromOrganisationByRoomAndOrganisation(
					room.getRooms_id(), orgToDel);
		}

		return new Long(1);
	}

	/**
	 * delete all Rooms_Organisations and Room by a given room_id
	 * 
	 * @param rooms_id
	 */
	public Long deleteRoomById(long user_level, long rooms_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				this.deleteAllRoomsOrganisationOfRoom(rooms_id);
				return this.deleteRoom(this.getRoomById(rooms_id));
			}
		} catch (Exception ex2) {
			log.error("[deleteRoomById] ", ex2);
		}
		return null;
	}

	/**
	 * deletes a Room by given Room-Object
	 * 
	 * @param r
	 */
	public Long deleteRoom(Rooms r) {
		log.debug("deleteRoom");
		try {
			r.setDeleted("true");
			r.setUpdatetime(new Date());
			if (r.getRooms_id() == null) {
				em.persist(r);
			} else {
				if (!em.contains(r)) {
					em.merge(r);
				}
			}
			return r.getRooms_id();
		} catch (Exception ex2) {
			log.error("[deleteRoomsOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * delete all Rooms_Organisation by a rooms_id
	 * 
	 * @param rooms_id
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAllRoomsOrganisationOfRoom(long rooms_id) {
		try {
			List ll = this.getRoomsOrganisationByRoomsId(rooms_id);
			for (Iterator it = ll.iterator(); it.hasNext();) {
				Rooms_Organisation rOrg = (Rooms_Organisation) it.next();
				this.deleteRoomsOrganisation(rOrg);
			}
		} catch (Exception ex2) {
			log.error("[deleteAllRoomsOrganisationOfRoom] ", ex2);
		}
	}

	/**
	 * Delete all room of a given Organisation
	 * 
	 * @param organisation_id
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAllRoomsOrganisationOfOrganisation(long organisation_id) {
		try {
			List ll = this.getRoomsOrganisationByOrganisationId(3,
					organisation_id);
			for (Iterator it = ll.iterator(); it.hasNext();) {
				Rooms_Organisation rOrg = (Rooms_Organisation) it.next();
				this.deleteRoomsOrganisation(rOrg);
			}
		} catch (Exception ex2) {
			log.error("[deleteAllRoomsOfOrganisation] ", ex2);
		}
	}

	/**
	 * Delete a Rooms_Organisation by its id
	 * 
	 * @param rooms_organisation_id
	 */
	public Long deleteRoomsOrganisationByID(long rooms_organisation_id) {
		try {
			Rooms_Organisation rOrg = this
					.getRoomsOrganisationById(rooms_organisation_id);
			return this.deleteRoomsOrganisation(rOrg);
		} catch (Exception ex2) {
			log.error("[deleteRoomsOrganisationByID] ", ex2);
		}
		return null;
	}

	private Long deleteRoomFromOrganisationByRoomAndOrganisation(long rooms_id,
			long organisation_id) {
		try {
			Rooms_Organisation rOrganisation = this
					.getRoomsOrganisationByOrganisationIdAndRoomId(
							organisation_id, rooms_id);
			return this.deleteRoomsOrganisation(rOrganisation);
		} catch (Exception ex2) {
			log.error("[deleteRoomFromOrganisationByRoomAndOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * delete a Rooms_Organisation-Object
	 * 
	 * @param rOrg
	 */
	public Long deleteRoomsOrganisation(Rooms_Organisation rOrg) {
		try {
			rOrg.setDeleted("true");
			rOrg.setUpdatetime(new Date());
			if (rOrg.getRooms_organisation_id() == null) {
				em.persist(rOrg);
			} else {
				if (!em.contains(rOrg)) {
					em.merge(rOrg);
				}
			}
			return rOrg.getRooms_organisation_id();
		} catch (Exception ex2) {
			log.error("[deleteRoomsOrganisation] ", ex2);
		}
		return null;
	}

	/**
	 * Update Room Object
	 */
	// --------------------------------------------------------------------------------------------
	public void updateRoomObject(Rooms room) {
		log.debug("updateRoomObject " + room.getIsClosed());

		try {
			if (room.getRooms_id() == null) {
				em.persist(room);
			} else {
				if (!em.contains(room)) {
					em.merge(room);
				}
			}
		} catch (Exception e) {
			log.error("Error updateRoomObject : ", e);
		}
	}

	// --------------------------------------------------------------------------------------------

	public void closeRoom(Long rooms_id, Boolean status) {
		try {

			Rooms room = this.getRoomById(rooms_id);

			room.setIsClosed(status);

			this.updateRoomObject(room);

		} catch (Exception e) {
			log.error("Error updateRoomObject : ", e);
		}
	}

	/**
	 * Get a Rooms-Object or NULL
	 * 
	 * @param rooms_id
	 * @return Rooms-Object or NULL
	 */
	public Rooms getRoomByOwnerAndTypeId(Long ownerId, Long roomtypesId,
			String roomName) {
		try {

			if (roomtypesId == null || roomtypesId == 0) {
				return null;
			}
			log.debug("getRoomByOwnerAndTypeId : " + ownerId + " || "
					+ roomtypesId);

			String hql = "select c from Rooms as c "
					+ "where c.ownerId = :ownerId "
					+ "AND c.roomtype.roomtypes_id = :roomtypesId "
					+ "AND c.deleted <> :deleted";

			Rooms room = null;

			Query query = em.createQuery(hql);
			query.setParameter("ownerId", ownerId);
			query.setParameter("roomtypesId", roomtypesId);
			query.setParameter("deleted", "true");
			@SuppressWarnings("unchecked")
			List<Rooms> ll = query.getResultList();
			if (ll.size() > 0) {
				room = ll.get(0);
			}

			if (room != null) {
				return room;
			} else {
				log.debug("Could not find room " + ownerId + " || "
						+ roomtypesId);

				Long rooms_id = this.addRoom(3L, roomName, roomtypesId,
						"My Rooms of ownerId " + ownerId,
						(roomtypesId == 1) ? 25L : 150L, // numberOfPartizipants
						false, // ispublic
						null, // organisations
						false, // appointment
						false, // isDemoRoom
						null, // demoTime
						false, // isModeratedRoom
						null, // roomModerators
						true, // allowUserQuestions
						false, // isAudioOnly
						false, // isClosed
						"", // redirectURL
						"", // sipNumber
						"", // conferencePin
						ownerId, null, null, false);

				if (rooms_id != null) {
					return this.getRoomById(rooms_id);
				}
			}
		} catch (Exception ex2) {
			log.error("[getRoomByOwnerAndTypeId] ", ex2);
		}
		return null;
	}

}
