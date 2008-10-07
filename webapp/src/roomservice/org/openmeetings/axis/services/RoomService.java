package org.openmeetings.axis.services;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openmeetings.app.conference.videobeans.RoomClient;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.basic.rooms.RoomsList;
import org.openmeetings.app.data.beans.basic.SearchResult;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.rooms.RoomTypes;
import org.openmeetings.app.hibernate.beans.rooms.Rooms;
import org.openmeetings.app.hibernate.beans.rooms.Rooms_Organisation;
import org.openmeetings.app.remote.ConferenceService;

public class RoomService {
	
	private static final Logger log = LoggerFactory.getLogger(RoomService.class);
	
	//TODO: Not implemented yet
//	public List<Rooms_Organisation> getRoomsByOrganisationAndType(String SID, long organisation_id, long roomtypes_id) {
//		return ConferenceService.getInstance().getRoomsByOrganisationAndType(SID, organisation_id, roomtypes_id);
//	}
	
	public RoomsList getRoomsPublic(String SID, Long roomtypes_id) throws AxisFault{
		try {
			List<Rooms> roomList = ConferenceService.getInstance().getRoomsPublic(SID, roomtypes_id);
			RoomsList roomsListObject = new RoomsList();
			//We need to re-marshal the Rooms object cause Axis2 cannot use our objects
			if (roomList!=null && roomList.size()!=0) {
				//roomsListObject.setRoomList(roomList);
				roomsListObject.setRoomList(new LinkedList<Rooms>());
				for (Iterator<Rooms>it = roomList.iterator();it.hasNext();){
					Rooms room = it.next();
					room.setCurrentusers(null);
					roomsListObject.getRoomList().add(room);
				}
			}
			log.debug("roomList SIZE: "+roomList.size());
			return roomsListObject;
		} catch (Exception err) {
			log.error("[getRoomsPublic] ",err);
			throw new AxisFault(err.getMessage());
		}
	}
	
	public List<RoomTypes> getRoomTypes(String SID){
		return ConferenceService.getInstance().getRoomTypes(SID);
	}
	
	public Rooms getRoomById(String SID, long rooms_id){
		return ConferenceService.getInstance().getRoomById(SID, rooms_id);
	}
	
	public SearchResult getRooms(String SID, int start, int max, String orderby, boolean asc){
		return ConferenceService.getInstance().getRooms(SID, start, max, orderby, asc);
	}
	
	//TODO: Add functions to get Users of a Room
	
//	public List<Rooms_Organisation> getOrganisationByRoom(String SID,long rooms_id){
//		return ConferenceService.getInstance().getOrganisationByRoom(SID, rooms_id);
//	}
//	
//	public List<RoomClient> getRoomClientsListByRoomId(Long room_id){
//		return ConferenceService.getInstance().getRoomClientsListByRoomId(room_id);
//	}
	
	/**
	 * TODO: Fix Organization Issue
	 * @param SID
	 * @param name
	 * @param roomtypes_id
	 * @param comment
	 * @param numberOfPartizipants
	 * @param ispublic
	 * @param videoPodWidth
	 * @param videoPodHeight
	 * @param videoPodXPosition
	 * @param videoPodYPosition
	 * @param moderationPanelXPosition
	 * @param showWhiteBoard
	 * @param whiteBoardPanelXPosition
	 * @param whiteBoardPanelYPosition
	 * @param whiteBoardPanelHeight
	 * @param whiteBoardPanelWidth
	 * @param showFilesPanel
	 * @param filesPanelXPosition
	 * @param filesPanelYPosition
	 * @param filesPanelHeight
	 * @param filesPanelWidth
	 * @return
	 */
	public Long addRoom(String SID, String name,
			Long roomtypes_id ,
			String comment, Long numberOfPartizipants,
			Boolean ispublic,
			Integer videoPodWidth,
			Integer videoPodHeight,
			Integer videoPodXPosition,
			Integer videoPodYPosition,
			Integer moderationPanelXPosition,
			Boolean showWhiteBoard,
			Integer whiteBoardPanelXPosition,
			Integer whiteBoardPanelYPosition,
			Integer whiteBoardPanelHeight,
			Integer whiteBoardPanelWidth,
			Boolean showFilesPanel,
			Integer filesPanelXPosition,
			Integer filesPanelYPosition,
			Integer filesPanelHeight,
			Integer filesPanelWidth) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
			return Roommanagement.getInstance().addRoom(user_level, name, roomtypes_id, comment, 
							numberOfPartizipants, ispublic, null, videoPodWidth, 
							videoPodHeight, videoPodXPosition, videoPodYPosition, moderationPanelXPosition, 
							showWhiteBoard, whiteBoardPanelXPosition, whiteBoardPanelYPosition, 
							whiteBoardPanelHeight, whiteBoardPanelWidth, showFilesPanel, 
							filesPanelXPosition, filesPanelYPosition, filesPanelHeight, filesPanelWidth);
		} catch (Exception err) {
			log.error("[addRoom] ",err);
		}
		return new Long (-1);
	}

	/**
	 * TODO: Fix Organization Issue
	 * @param SID
	 * @param rooms_id
	 * @param name
	 * @param roomtypes_id
	 * @param comment
	 * @param numberOfPartizipants
	 * @param ispublic
	 * @param videoPodWidth
	 * @param videoPodHeight
	 * @param videoPodXPosition
	 * @param videoPodYPosition
	 * @param moderationPanelXPosition
	 * @param showWhiteBoard
	 * @param whiteBoardPanelXPosition
	 * @param whiteBoardPanelYPosition
	 * @param whiteBoardPanelHeight
	 * @param whiteBoardPanelWidth
	 * @param showFilesPanel
	 * @param filesPanelXPosition
	 * @param filesPanelYPosition
	 * @param filesPanelHeight
	 * @param filesPanelWidth
	 * @return
	 */
	public Long updateRoom(String SID, Long rooms_id, String name,
			Long roomtypes_id ,
			String comment, Long numberOfPartizipants,
			Boolean ispublic,
			Integer videoPodWidth,
			Integer videoPodHeight,
			Integer videoPodXPosition,
			Integer videoPodYPosition,
			Integer moderationPanelXPosition,
			Boolean showWhiteBoard,
			Integer whiteBoardPanelXPosition,
			Integer whiteBoardPanelYPosition,
			Integer whiteBoardPanelHeight,
			Integer whiteBoardPanelWidth,
			Boolean showFilesPanel,
			Integer filesPanelXPosition,
			Integer filesPanelYPosition,
			Integer filesPanelHeight,
			Integer filesPanelWidth) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
			return Roommanagement.getInstance().updateRoom(user_level, rooms_id, roomtypes_id, name, ispublic, 
					comment, numberOfPartizipants, null, videoPodWidth, videoPodHeight, 
					videoPodXPosition, videoPodYPosition, moderationPanelXPosition, showWhiteBoard, 
					whiteBoardPanelXPosition, whiteBoardPanelYPosition, whiteBoardPanelHeight, whiteBoardPanelWidth, showFilesPanel, 
						filesPanelXPosition, filesPanelYPosition, filesPanelHeight, filesPanelWidth);
		} catch (Exception err) {
			log.error("[addRoom] ",err);
		}
		return new Long (-1);
	}
	
	public Long deleteRoom(String SID, long rooms_id){
		return ConferenceService.getInstance().deleteRoom(SID, rooms_id);
	}

}
