package org.openmeetings.app.remote;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.beans.basic.SearchResult;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.data.calendar.management.AppointmentLogic;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.hibernate.beans.calendar.Appointment;
import org.openmeetings.app.hibernate.beans.recording.RoomClient;
import org.openmeetings.app.hibernate.beans.rooms.Rooms;
import org.openmeetings.app.hibernate.beans.rooms.RoomTypes;
import org.openmeetings.app.hibernate.beans.rooms.Rooms_Organisation;
import org.openmeetings.app.remote.red5.ClientListManager;

/**
 * 
 * @author swagner
 *
 */
public class ConferenceService {
	
	private static final Logger log = Red5LoggerFactory.getLogger(ConferenceService.class, "openmeetings");
	private static ConferenceService instance;
	
	//beans, see chaservice.service.xml
	private ClientListManager clientListManager = null;

	public static synchronized ConferenceService getInstance() {
		if (instance == null) {
			instance = new ConferenceService();
		}
		return instance;
	}	
	
	public ClientListManager getClientListManager() {
		return clientListManager;
	}
	public void setClientListManager(ClientListManager clientListManager) {
		this.clientListManager = clientListManager;
	}

	/**(
	 * get a List of all availible Rooms of this organisation  (non-appointments)
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public List<Rooms_Organisation> getRoomsByOrganisationAndType(String SID, long organisation_id, long roomtypes_id){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        
	        log.debug("getRoomsByOrganisationAndType");
	        
	        if (User_level == null) {
	        	return null;
	        }
	        List<Rooms_Organisation> roomOrgsList = Roommanagement.getInstance().getRoomsOrganisationByOrganisationIdAndRoomType(User_level, organisation_id, roomtypes_id);
	        
	        List<Rooms_Organisation> filtered = new ArrayList<Rooms_Organisation>();
	        
	        for (Iterator<Rooms_Organisation> iter = roomOrgsList.iterator();iter.hasNext();) {
	        	Rooms_Organisation orgRoom = iter.next();
	        	
	        	if(!orgRoom.getRoom().getAppointment()){
	        		orgRoom.getRoom().setCurrentusers(this.getRoomClientsListByRoomId(orgRoom.getRoom().getRooms_id()));
	        		filtered.add(orgRoom);
	        	}
	        }
	        return filtered;     
		} catch (Exception err) {
			log.error("[getRoomsByOrganisationAndType]",err);
		}
		return null;
	}
	
	/**
	 * gets all rooms of an organisation
	 * TODO:check if the requesting user is also member of that organisation
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public SearchResult getRoomsByOrganisation(String SID, long organisation_id, int start, int max, String orderby, boolean asc){
        
		log.debug("getRoomsByOrganisation");
		
		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getRoomsOrganisationByOrganisationId(User_level, organisation_id, start, max, orderby, asc);
	}	
	
	/**
	 * get a List of all public availible rooms (non-appointments)
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public List<Rooms> getRoomsPublic(String SID, Long roomtypes_id){
		try {
			log.debug("getRoomsPublic");
			
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        log.error("getRoomsPublic user_level: "+User_level);
	        
	        
	        List<Rooms> roomList = Roommanagement.getInstance().getPublicRooms(User_level, roomtypes_id);
	       
	        // Filter : no appointed meetings
	        List<Rooms> filtered = new ArrayList<Rooms>();
	        
	        for (Iterator<Rooms> iter = roomList.iterator();iter.hasNext();) {
	        	Rooms rooms = iter.next();
	        	
	        	if(!rooms.getAppointment()){
	        		rooms.setCurrentusers(this.getRoomClientsListByRoomId(rooms.getRooms_id()));
	        		filtered.add(rooms);
	        	}
	        }
	        
	        
	        return filtered;
		} catch (Exception err) {
			log.error("[getRoomsByOrganisationAndType]",err);
		}
		return null;
	}
	
	
	/**
	 * retrieving ServerTime
	 * @return
	 */
	//--------------------------------------------------------------------------------------------
	public Date getServerTime(){
		log.debug("getServerTime");
	
		return new Date(System.currentTimeMillis());
	
	}
	//--------------------------------------------------------------------------------------------
	
	
	/**
	 * 
	 * retrieving Appointment for Room
	 * @author o.becherer
	 *
	 */
	//--------------------------------------------------------------------------------------------
	public Appointment getAppointMentDataForRoom(Long room_id){
		log.debug("getAppointMentDataForRoom");
		
		Rooms room = Roommanagement.getInstance().getRoomById(room_id);
		
		if(room.getAppointment() == false)
			return null;
		
		try{
			Appointment ment = AppointmentLogic.getInstance().getAppointmentByRoom(room_id);
			
			
			return ment;
		}catch(Exception e){
			log.error("getAppointMentDataForRoom " + e.getMessage());
			return null;
		}
		
	}
	//--------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	//--------------------------------------------------------------------------------------------
	public List<Rooms> getAppointedMeetings(String SID, Long room_types_id){
		log.debug("ConferenceService.getAppointedMeetings");
		
		 Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	     Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	     
	     List<Appointment> points = AppointmentLogic.getInstance().getTodaysAppointmentsForUser(users_id);
	     List<Rooms> result = new ArrayList<Rooms>(); 
	     
	     if(points != null){
	    	for(int i = 0; i < points.size(); i++){
	    		Appointment ment = points.get(i);
	    		
	    		Long rooms_id = ment.getRoom().getRooms_id();
	    		Rooms rooom = Roommanagement.getInstance().getRoomById(rooms_id);
	    		
	    		if(!rooom.getRoomtype().getRoomtypes_id().equals(room_types_id))
	    			continue;
	    		
	    		rooom.setCurrentusers(this.getRoomClientsListByRoomId(rooom.getRooms_id()));
	    		result.add(rooom);
	    	}
	     }
	     
	     log.debug("Found " + result.size() + " rooms");
	     return result;
	     
	}
	//--------------------------------------------------------------------------------------------
	
	
	/**
	 * 
	 * @param SID
	 * @return
	 */
	public List<RoomTypes> getRoomTypes(String SID){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        
        log.debug("users_id "+users_id);
        
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        
        return Roommanagement.getInstance().getAllRoomTypes(user_level);
	}
	
	/**
	 * 
	 * @param SID
	 * @param rooms_id
	 * @return
	 */
	public Rooms getRoomById(String SID, long rooms_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getRoomById(user_level,rooms_id);
	}
	
	public Rooms getRoomWithCurrentUsersById(String SID, long rooms_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getRoomById(user_level,rooms_id);
	}
	
	/**
	 * gets a list of all availible rooms
	 * @param SID
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public SearchResult getRooms(String SID, int start, int max, String orderby, boolean asc){
		log.debug("getRooms");
		
		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getRooms(user_level, start, max, orderby, asc);
	}
	
	public SearchResult getRoomsWithCurrentUsers(String SID, int start, int max, String orderby, boolean asc){
		log.debug("getRooms");
		
		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getRoomsWithCurrentUsers(user_level, start, max, orderby, asc);
	}
	
	/**
	 * get all Organisations of a room
	 * @param SID
	 * @param rooms_id
	 * @return
	 */
	public List<Rooms_Organisation> getOrganisationByRoom(String SID,long rooms_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().getOrganisationsByRoom(user_level, rooms_id);
	}

	
	/**
	 * 
	 * @param SID
	 * @param name
	 * @param roomtypes_id
	 * @deprecated
	 * @return
	 */
//	public Long addRoomPublic(String SID, String name, long roomtypes_id){
//        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
//        Long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
//        return Roommanagement.getInstance().addRoom(User_level, name, roomtypes_id,"", new Long(4), true,null,
//				290, 280, 2, 2,
//				400,
//				true, 296, 2, 592, 660,
//				true, 2, 284, 310, 290, false, false, null);
//	}
	
	/**
	 * 
	 * @param SID
	 * @param organisation_id
	 * @param name
	 * @param roomtypes_id
	 * @param ispublic
	 * @deprecated
	 * @return
	 */
//	public Long addRoomOrganisation(String SID, long organisation_id, String name, long roomtypes_id, boolean ispublic){
//        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
//        long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
//        Long rooms_id = Roommanagement.getInstance().addRoom(User_level, name, roomtypes_id,"", new Long(4), ispublic, null,
//				290, 280, 2, 2,
//				400,
//				true, 296, 2, 592, 660,
//				true, 2, 284, 310, 290, false, false, null);
//        return Roommanagement.getInstance().addRoomToOrganisation(User_level, rooms_id, organisation_id);
//	}
	
	/**
	 * 
	 * @param SID
	 * @param argObject
	 * @return
	 */
	public Long saveOrUpdateRoom(String SID, Object argObject){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        long User_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        log.debug("argObject: 1 - "+argObject.getClass().getName());
	        LinkedHashMap argObjectMap = (LinkedHashMap) argObject;
	        log.debug("argObject: 2 - "+argObjectMap.get("organisations").getClass().getName());
	        List organisations = (List) argObjectMap.get("organisations");
	        Long rooms_id = Long.valueOf(argObjectMap.get("rooms_id").toString()).longValue();
	        log.debug("rooms_id "+rooms_id);
	        
	        if (rooms_id==0){
	        	return Roommanagement.getInstance().addRoom(User_level, argObjectMap.get("name").toString(), 
	        			Long.valueOf(argObjectMap.get("roomtypes_id").toString()).longValue(),
	        			argObjectMap.get("comment").toString(), Long.valueOf(argObjectMap.get("numberOfPartizipants").toString()).longValue(),
	        			Boolean.valueOf(argObjectMap.get("ispublic").toString()),organisations,
	        			Boolean.valueOf(argObjectMap.get("appointment").toString()),
	        			Boolean.valueOf(argObjectMap.get("isDemoRoom").toString()),
	        			Integer.valueOf(argObjectMap.get("demoTime").toString()),
	        			Boolean.valueOf(argObjectMap.get("isModeratedRoom").toString()));
	        } else if (rooms_id>0){
	        	return Roommanagement.getInstance().updateRoom(User_level, rooms_id, 
	        			Long.valueOf(argObjectMap.get("roomtypes_id").toString()).longValue(), 
	        			argObjectMap.get("name").toString(), Boolean.valueOf(argObjectMap.get("ispublic").toString()),
	        			argObjectMap.get("comment").toString(),
	        			Long.valueOf(argObjectMap.get("numberOfPartizipants").toString()).longValue(),organisations,
	        			Boolean.valueOf(argObjectMap.get("appointment").toString()),
	        			Boolean.valueOf(argObjectMap.get("isDemoRoom").toString()),
	        			Integer.valueOf(argObjectMap.get("demoTime").toString()),
	        			Boolean.valueOf(argObjectMap.get("isModeratedRoom").toString()));
	        }
		} catch (Exception e){
			log.error("saveOrUpdateRoom",e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @param rooms_id
	 * @return
	 */
	public Long deleteRoom(String SID, long rooms_id){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        return Roommanagement.getInstance().deleteRoomById(user_level, rooms_id);
	}
	
	/**
	 * return all participants of a room
	 * @param room_id
	 * @return
	 */
	public List<RoomClient> getRoomClientsListByRoomId(Long room_id) {
		log.debug("getRoomClientsListByRoomId");
		try {
			//log.error("getRoomClientsListByRoomId: "+room_id);
			LinkedList<RoomClient> clients = new LinkedList<RoomClient>();
			HashMap<String,RoomClient> clientList =this.clientListManager.getClientListByRoom(room_id);
			for (Iterator<String> iter = clientList.keySet().iterator();iter.hasNext();) {
				RoomClient rcl = clientList.get(iter.next());
				//log.error("COMPARE: "+rcl.getRoom_id()+" || "+room_id);
				clients.add(rcl);
			}
			return clients;
		} catch (Exception err) {
			log.error("[getRoomClientsListByRoomId]",err);
		}
		return null;
	}
	
	public HashMap<String,RoomClient> getRoomClientsMapByRoomId(Long room_id) {
		try {
			//log.error("getRoomClientsListByRoomId: "+room_id);
			return this.clientListManager.getClientListByRoom(room_id);
		} catch (Exception err) {
			log.error("[getRoomClientsMapByRoomId]",err);
		}
		return null;
	}
	
	public SearchResult getRoomClientsMap(String SID, int start ,int max, String orderby, boolean asc){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
	        	return this.clientListManager.getListByStartAndMax(start, max, orderby, asc);
	        }
		} catch (Exception err) {
			log.error("[getRoomClientsMap]",err);
		}
		return null;
	}
	
}
