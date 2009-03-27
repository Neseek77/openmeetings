
package org.openmeetings.app.data.calendar.management;

import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;
import org.openmeetings.app.data.calendar.daos.AppointmentDaoImpl;
import org.openmeetings.app.data.calendar.daos.MeetingMemberDaoImpl;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.calendar.Appointment;
import org.openmeetings.app.hibernate.beans.calendar.MeetingMember;
import org.openmeetings.app.hibernate.beans.rooms.Rooms;
import org.openmeetings.app.hibernate.beans.user.Users;


public class AppointmentLogic {
	
	private static final Logger log = Red5LoggerFactory.getLogger(AppointmentLogic.class, "openmeetings");
	private static AppointmentLogic instance = null;

	public static synchronized AppointmentLogic getInstance() {
		if (instance == null) {
			instance = new AppointmentLogic();
		}

		return instance;
	}
	
	public List<Appointment> getAppointmentByRange(Long userId ,Date starttime, Date endtime){
		try {	
			return AppointmentDaoImpl.getInstance().getAppointmentsByRange(userId, starttime, endtime);
		}catch(Exception err){
			log.error("[getAppointmentByRange]",err);
		}
		return null;
	}
	
	
	public List<Appointment> getTodaysAppointmentsForUser(Long userId){
		log.debug("getTodaysAppointmentsForUser");
		
		
		List<Appointment> points = AppointmentDaoImpl.getInstance().getTodaysAppoitmentsbyRangeAndMember(userId); 
		
		log.debug("Count Appointments for Today : " + points.size());
		
		return points;
		
	}
	
	/**
	 * @author o.becherer
	 * @param room_id
	 * @return
	 */
	//--------------------------------------------------------------------------------------------
	public Appointment getAppointmentByRoom(Long room_id) throws Exception{
		log.debug("getAppointmentByRoom");
		
		Rooms room = Roommanagement.getInstance().getRoomById(room_id);
		
		if(room == null)
			throw new Exception("Room does not exist in database!");
		
		if(!room.getAppointment())
			throw new Exception("Room " + room.getName() + " isnt part of an appointed meeting");
		
		return AppointmentDaoImpl.getInstance().getAppointmentByRoom(room_id);
	}
	//--------------------------------------------------------------------------------------------
	
	
	//next appointment to current date
	public Appointment getNextAppointment(){
		try{
		return AppointmentDaoImpl.getInstance().getNextAppointment(new Date());
		}catch(Exception err){
			log.error("[getNextAppointmentById]",err);
		}	
		return null;
	}
	
	public List<Appointment> searchAppointmentByName(String appointmentName){
		try{
		return AppointmentDaoImpl.getInstance().searchAppointmentsByName(appointmentName) ;
		}catch(Exception err){
			log.error("[searchAppointmentByName]",err);	
		}
		return null;
	}
	
	public Long saveAppointment(String appointmentName, Long userId, String appointmentLocation,String appointmentDescription, 
			Date appointmentstart, Date appointmentend, 
			Boolean isDaily, Boolean isWeekly, Boolean isMonthly, Boolean isYearly, Long categoryId, Long remind, Long roomType, String baseUrl){
		
		log.debug("Appointmentlogic.saveAppointment");
		
		// create a Room
		Long room_id = Roommanagement.getInstance().addRoom(
				3,					// Userlevel
				appointmentName,	// name	
				roomType,					// RoomType	
				"",					// Comment
				new Long(8),		// Number of participants
				true,				// public
				null,				// Organisations
				270,				// Video Width
				280,				// Video height
				2,					// Video X
				2,					// Video Y
				400,				// Modeartionpanel X
				true,				// Whiteboard
				276,				// Whiteboard x
				2,					// Whiteboard y
				592,				// WB height
				660,				// WB width
				true,				// Show Files Panel
				2,					// Files X
				284,				// Files Y
				310,				// Files height
				270,				// Files width
				true);				// Appointment
		
		log.debug("Appointmentlogic.saveAppointment : Room - " + room_id);
		log.debug("Appointmentlogic.saveAppointment : Reminder - " + remind);
		
		Rooms room = Roommanagement.getInstance().getRoomById(room_id);
		
		if(room == null)
			log.error("Room " + room_id + " could not be found!");
		else
			log.debug("Room " + room_id + " ok!");
		
		try{
			Long id =  AppointmentDaoImpl.getInstance().addAppointment(appointmentName, userId, appointmentLocation, appointmentDescription,
				appointmentstart, appointmentend, isDaily, isWeekly, isMonthly, isYearly, categoryId, remind, room);
		
			
			// Adding Invitor as Meetingmember
			Users user = Usermanagement.getInstance().getUserById(userId); 
			
			MeetingMemberLogic.getInstance().addMeetingMember(user.getFirstname(), user.getLastname(), "", "", id, userId, user.getAdresses().getEmail(), baseUrl, userId, true);
			
			
			return id;
		}catch(Exception err){
			log.error("[saveAppointment]",err);
		}
		return null;
	}
	
	/**
	 * 
	 * @param appointmentId
	 * @return
	 */
	//-------------------------------------------------------------------------------------
	public Long deleteAppointment(Long appointmentId, Long users_id){
		log.debug("deleteAppointment : " + appointmentId);
		
		try{
			
			Appointment point = getAppointMentById(appointmentId);
			
			if(point == null){
				log.error("No appointment found for ID " + appointmentId);
				return null;
			}
			
			Rooms room = point.getRoom();
			
			
		
			
			// Deleting/Notifing Meetingmembers
			List<MeetingMember> members = MeetingMemberDaoImpl.getInstance().getMeetingMemberByAppointmentId(appointmentId);
		    
			if(members == null)
				log.debug("Appointment " + point.getAppointmentName() + " has no meeting members");
			
			if(members != null){
				for(int i = 0; i < members.size(); i++){
					log.debug("deleting member " + members.get(i).getEmail());
					MeetingMemberLogic.getInstance().deleteMeetingMember(members.get(i).getMeetingMemberId(), users_id);
				}
			}
			
			// Deleting Appointment itself
			AppointmentDaoImpl.getInstance().deleteAppointement(appointmentId);
		
			// Deleting Room
			Roommanagement.getInstance().deleteRoom(room);
			
		return appointmentId;
		
		}catch(Exception err){
			log.error("[deleteAppointment]",err);	
		}
		
		return null;
		
	}
	//-------------------------------------------------------------------------------------
	
	/**
	 * Retrieving Appointment by ID
	 */
	//----------------------------------------------------------------------------------------------
	public Appointment getAppointMentById(Long appointment){
		log.debug("getAppointMentById");
		
		return AppointmentDaoImpl.getInstance().getAppointmentById(appointment);
	}
	//----------------------------------------------------------------------------------------------
	
	
	
	/**
	 * 
	 * @param appointmentId
	 * @param appointmentName
	 * @param appointmentDescription
	 * @param appointmentstart
	 * @param appointmentend
	 * @param isDaily
	 * @param isWeekly
	 * @param isMonthly
	 * @param isYearly
	 * @param categoryId
	 * @param remind
	 * @param mmClient
	 * @return
	 */
	public Long updateAppointment(Long appointmentId, String appointmentName, String appointmentDescription, 
			Date appointmentstart, Date appointmentend,
			Boolean isDaily, Boolean isWeekly, Boolean isMonthly, Boolean isYearly, Long categoryId, Long remind, List mmClient , Long user_id, String baseUrl){
		
		try {
			return AppointmentDaoImpl.getInstance().updateAppointment(appointmentId, 
					appointmentName, appointmentDescription, appointmentstart, 
					appointmentend, isDaily, isWeekly, isMonthly, isYearly, categoryId, remind, 
					mmClient, user_id, baseUrl);
		} catch (Exception err) {
			log.error("[updateAppointment]",err);
		}
		return null;
	}
	
	
	
	/**
	 *Updating AppointMent object 
	 */
	//----------------------------------------------------------------------------------------------
	public Long updateAppointMent(Appointment point){
		log.debug("AppointmentLogic.updateAppointment");
	
		return AppointmentDaoImpl.getInstance().updateAppointment(point);
	}
	//----------------------------------------------------------------------------------------------
	
}
