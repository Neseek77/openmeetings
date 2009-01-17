package org.openmeetings.app.remote.red5;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmeetings.app.hibernate.beans.recording.RoomClient;
import org.openmeetings.utils.crypt.ManageCryptStyle;

public class ClientListManager {
	
	private static HashMap<String,RoomClient> clientList = new HashMap<String,RoomClient>();

	private static final Log log = LogFactory.getLog(ClientListManager.class);

	private static ClientListManager instance = null;

	private ClientListManager() {
	}

	public static synchronized ClientListManager getInstance() {
		if (instance == null) {
			instance = new ClientListManager();
		}
		return instance;
	}
	
	public synchronized RoomClient addClientListItem(String streamId, String scopeName, 
			Integer remotePort, String remoteAddress, String swfUrl){
		try { 
			
			//Store the Connection into a bean and add it to the HashMap
			RoomClient rcm = new RoomClient();
			rcm.setConnectedSince(new Date());
			rcm.setStreamid(streamId);
			rcm.setScope(scopeName);
			long thistime = new Date().getTime();
			rcm.setPublicSID(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(
						String.valueOf(thistime).toString()));
			
			rcm.setUserport(remotePort);
			rcm.setUserip(remoteAddress);
			rcm.setSwfurl(swfUrl);			
			rcm.setIsMod(new Boolean(false));
			
			if (clientList.containsKey(streamId)){
				log.error("Tried to add an existing Client "+streamId);
				return null;
			}
			
			clientList.put(rcm.getStreamid(),rcm);
			
			return rcm;
		} catch (Exception err) {
			log.error("[addClientListItem]",err);
		}
		return null;
	}
	
	public synchronized HashMap<String,RoomClient> getClientList() {
		return clientList;
	}
	
	public synchronized RoomClient getClientByStreamId(String streamId) {
		try {
			if (!clientList.containsKey(streamId)){
				log.debug("Tried to get a non existing Client "+streamId);
				return null;
			}
			return clientList.get(streamId);
		} catch (Exception err) {
			log.error("[getClientByStreamId]",err);
		}
		return null;
	}
	
	public synchronized RoomClient getClientByPublicSID(String publicSID) {
		try {
			for (Iterator<String> iter = clientList.keySet().iterator();iter.hasNext();) {
				RoomClient rcl = clientList.get(iter.next());
				if (rcl.getPublicSID().equals(publicSID)) {
					return rcl;
				}
			}
		} catch (Exception err) {
			log.error("[getClientByPublicSID]",err);
		}
		return null;
	}
	
	public synchronized Boolean updateClientByStreamId(String streamId, RoomClient rcm) {
		try {
			if (clientList.containsKey(streamId)){
				clientList.put(streamId,rcm);
				return true;
			} else {
				log.debug("Tried to update a non existing Client "+streamId);
				return false;
			}
		} catch (Exception err) {
			log.error("[updateClientByStreamId]",err);
		}
		return null;
	}
	
	public synchronized Boolean removeClient(String streamId) {
		try {
			if (clientList.containsKey(streamId)){
				clientList.remove(streamId);
				return true;
			} else {
				log.debug("Tried to remove a non existing Client "+streamId);
				return false;
			}
		} catch (Exception err) {
			log.error("[remoteClient]",err);
		}
		return null;
	}
	
	/**
	 * Get all ClientList Objects of that room and domain
	 * This Function is needed cause it is invoked internally AFTER the current user has been already removed
	 * from the ClientList to see if the Room is empty again and the PollList can be removed
	 * 
	 * @return
	 */
	public synchronized HashMap<String,RoomClient> getClientListByRoom(Long room_id){
		HashMap <String,RoomClient> roomClientList = new HashMap<String,RoomClient>();
		try {			
			for (Iterator<String> iter=clientList.keySet().iterator();iter.hasNext();) {
				String key = (String) iter.next();
				log.debug("getClientList key: "+key);
				RoomClient rcl = clientList.get(key);
				//same room, same domain
				if (room_id!=null && room_id.equals(rcl.getRoom_id())) roomClientList.put(key, rcl);
			}
		} catch (Exception err) {
			log.error("[getClientListByRoom]",err);
		}
		return roomClientList;
	}
	
	/**
	 * get the current Moderator in this room
	 * 
	 * @param roomname
	 * @return
	 */
	public synchronized RoomClient getCurrentModeratorByRoom(Long room_id) throws Exception{
		
		for (Iterator<String> iter=clientList.keySet().iterator();iter.hasNext();) {
			String key = (String) iter.next();
			RoomClient rcl = clientList.get(key);
//			
			log.debug("*..*unsetModerator ClientList key: "+rcl.getStreamid());
//			
			//Check if the Client is in the same room
			if(room_id!=null && room_id.equals(rcl.getRoom_id()) && rcl.getIsMod()){
				log.debug("found client who is the Moderator: "+rcl);
				return rcl;
			}				
		}
		
		return null;
	}
	
}
