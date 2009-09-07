package org.openmeetings.app.rtp;

import java.util.HashMap;
import java.util.Iterator;

import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.recording.RoomClient;
import org.openmeetings.app.hibernate.beans.rooms.Rooms;
import org.openmeetings.app.hibernate.beans.user.Users;
import org.openmeetings.app.remote.red5.ClientListManager;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.servlet.outputhandler.ScreenRequestHandler;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

/**
 * 
 * @author o.becherer
 *
 */
public class RTPStreamingHandler {
	
		private static final Logger log = Red5LoggerFactory.getLogger(RTPStreamingHandler.class, "openmeetings");
	
		/** Contains all RTPSessions*/
		public static HashMap<Rooms, RTPScreenSharingSession> rtpSessions = new HashMap<Rooms, RTPScreenSharingSession>();

		/** Minimal Limit for valid RTP Ports */
		public static final int minimalRTPPort = 22224;
		
		/** Maximum for valid RTP Ports */
		public static final int maximalRTPPort = 24000;
		
		/** Define The Port a Sharer should send his RTPStream on */
		//---------------------------------------------------------------------------------------------
		public static int getNextFreeRTPPort(){
			log.debug("getNextFreeRTPPort");
			
			Iterator<Rooms> riter = rtpSessions.keySet().iterator();
			
			int currentPort = minimalRTPPort;
			
			if(rtpSessions.size() < 1){
				log.debug("getNextFreeRTPPort : " + currentPort);
				return currentPort;
			}

			// TODO also use maximum RTP Port to give admins a chance to administrate a range 
			// of ports that should be open ;-)
			
			boolean portBlocked = true;
			
			while(portBlocked){
			
				while(riter.hasNext()){
					Rooms r = riter.next();
					RTPScreenSharingSession session = rtpSessions.get(r);
					
					log.debug("trying Port " + currentPort);
					
					if(session.getIncomingRTPPort() == currentPort){
						portBlocked = true;
						currentPort= currentPort +2;
					}
					else
						portBlocked = false;
				}
				
				// Checked all
				portBlocked = false;
					
			}
			
			log.debug("getNextFreeRTPPort : " + currentPort);
			return currentPort;
			
		}
		//---------------------------------------------------------------------------------------------
		
		
		/**
		 * Retrieving Session data for Room 
		 */
		//---------------------------------------------------------------------------------------------
		public static RTPScreenSharingSession getSessionForRoom(String room, String sid) throws Exception{
			log.debug("getSessionForRoom");
			
			if(room == null || room.length() <1)
				throw new Exception("InputVal room not valid");
			
			Long users_id = Sessionmanagement.getInstance().checkSession(sid);
			Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);

			Rooms myRoom= Roommanagement.getInstance().getRoomById(user_level, Long.parseLong(room));
		
			if(myRoom == null)
				throw new Exception("no room available for ID " + room);
						
			Iterator<Rooms> miter = rtpSessions.keySet().iterator();
			
			RTPScreenSharingSession session = null;
			
			while(miter.hasNext()){
				Rooms rooms = miter.next();
				
				if(rooms.getRooms_id().intValue() == myRoom.getRooms_id().intValue()){
					session = rtpSessions.get(rooms);
					
				}
				
			}
			
			if(session == null)
				throw new Exception("no RTPSession for Room " + room);
			
			return session;
		}
		//---------------------------------------------------------------------------------------------
		
		
		/**
		 * Store Session for Room
		 */
		//---------------------------------------------------------------------------------------------
		public static RTPScreenSharingSession storeSessionForRoom(String room, Long sharing_user_id, String publicSID, String hostIP) throws Exception{
			log.debug("storeSessionForRoom : Room = " + room + ", publicSID : " + publicSID + ", hostIP" + hostIP);
			
			// Defining The IP of the Sharer (Moderator)
			// Should be retrieved via Clientlist to receive the "extern" IP, seen by red5
			RoomClient rcl = ClientListManager.getInstance().getClientByPublicSID(publicSID);
			
			
			
			if(rcl ==null)
				throw new Exception("Could not retrieve RoomClient for publicSID");
			
			
			RTPScreenSharingSession session = new RTPScreenSharingSession();
			
			if(room == null || room.length() <1)
				throw new Exception("InputVal room not valid");
			
			Long user_level = Usermanagement.getInstance().getUserLevelByID(sharing_user_id);
			Rooms myRoom= Roommanagement.getInstance().getRoomById(user_level, Long.parseLong(room));
			
			if(myRoom == null)
				throw new Exception("no Room for ID " + room);
			
			
			// Define Room
			session.setRoom(myRoom);
			
			// Define User
			Users user = Usermanagement.getInstance().getUserById(sharing_user_id);
			
			if(user == null)
				throw new Exception("No User for id " + sharing_user_id);
			
			log.debug("storeSessionForRoom : User = " + user.getLogin());
			
			session.setSharingUser(user);
			
			// Define Sharers IP
			session.setSharingIpAddress(rcl.getUserip());
			log.debug("storeSessionForRoom : Sharers IP = " + rcl.getUserip());
			
			// Define RTP Port for Sharing User
			int port = getNextFreeRTPPort();
			
			log.debug("storeSessionForRoom : Incoming RTP Port = " + port);
			
			session.setIncomingRTPPort(port);
			
			// Pre-Define Viewers
			HashMap<String, RoomClient> clientsForRoom = ClientListManager.getInstance().getClientListByRoom(Long.parseLong(room));
			
			Iterator<String> siter = clientsForRoom.keySet().iterator();
			
			HashMap<RoomClient, Integer> viewers = new HashMap<RoomClient, Integer>();
			
			while(siter.hasNext()){
				String key = siter.next();
				RoomClient client = clientsForRoom.get(key);
				
				int viewerPort = getNextFreeRTPPort();
				
				viewers.put(client, viewerPort);
				
			}
			
			session.setViewers(viewers);
			log.debug("storeSessionForRoom : Added " + viewers.size() + " Viewers to session");
			
			
			// RED5Host IP
			session.setRed5Host(hostIP);
			
			rtpSessions.put(myRoom, session);
			
			log.debug("storeSessionForRoom : sessionData stored");
			
			return session;
			
		}
		//---------------------------------------------------------------------------------------------
		
		
		
		/**
		 * Remove Session
		 */
		//---------------------------------------------------------------------------------------------
		public static void removeSessionForRoom(String room, String sid) throws Exception{
			log.debug("removeSessionForRoom : " + room);
			
		
			if(room == null || room.length() <1)
				throw new Exception("InputVal room not valid");
			
			Long users_id = Sessionmanagement.getInstance().checkSession(sid);
			Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
			Rooms myRoom= Roommanagement.getInstance().getRoomById(user_level, Long.parseLong(room));
			
			if(myRoom == null)
				throw new Exception("no Room for ID " + room);
			
			
			
			Iterator<Rooms> miter = rtpSessions.keySet().iterator();
			
			
			while(miter.hasNext()){
				Rooms rooms = miter.next();
				
				if(rooms.getRooms_id().intValue() == myRoom.getRooms_id().intValue()){
					rtpSessions.remove(rooms);
					break;
				}
				
			}
			
		}
		//---------------------------------------------------------------------------------------------
		
}
