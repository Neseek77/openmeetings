package org.openmeetings.app.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openmeetings.app.conference.videobeans.RoomClient;
import org.openmeetings.app.conference.whiteboard.WhiteboardManagement;
import org.openmeetings.app.conference.whiteboard.WhiteboardSyncLockObject;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;

/**
 * 
 * @author sebastianwagner
 *
 */
public class WhiteBoardService implements IPendingServiceCallback {
	
	private static final Logger log = LoggerFactory.getLogger(WhiteBoardService.class);	
	
	private WhiteBoardService() {}
	
	private static WhiteBoardService instance = null;
	
	public static synchronized WhiteBoardService getInstance() {
		if (instance == null) {
			instance = new WhiteBoardService();
		}
		return instance;
	}
	
	/**
	 * Loading the List of Objects on the whiteboard
	 * @return HashMap<String,Map>
	 */
	public LinkedList<Map> getRoomItems(){
		try {
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			Long room_id = currentClient.getRoom_id();
			
			log.debug("getRoomItems: "+room_id);
			HashMap<String,Map> roomItems = Application.getWhiteBoardObjectListByRoomId(room_id);
			
			LinkedList<Map> itemList = new LinkedList<Map>();
			for (Iterator<String> it = roomItems.keySet().iterator();it.hasNext();){
				itemList.add(roomItems.get(it.next()));
			}
			
			
			return itemList;
		} catch (Exception err) {
			log.error("[getRoomItems]",err);
		}
		return null;
	}
	
	public WhiteboardSyncLockObject startNewSyncprocess(){
		try {
			
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			Long room_id = currentClient.getRoom_id();
			
			WhiteboardSyncLockObject wSyncLockObject = new WhiteboardSyncLockObject();
			wSyncLockObject.setAddtime(new Date());
			wSyncLockObject.setRoomclient(currentClient);
			wSyncLockObject.setInitialLoaded(true);
			
			Map<String,WhiteboardSyncLockObject> syncListRoom = Application.getWhiteBoardSyncListByRoomid(room_id);
			

			wSyncLockObject.setCurrentLoadingItem(true);
			wSyncLockObject.setStarttime(new Date());
		
			syncListRoom.put(currentClient.getPublicSID(), wSyncLockObject);
			Application.setWhiteBoardSyncListByRoomid(room_id, syncListRoom);
			
			Iterator<IConnection> it = current.getScope().getConnections();
			while (it.hasNext()) {
				IConnection conn = it.next();
				if (conn instanceof IServiceCapableConnection) {
					RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
					if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
						((IServiceCapableConnection) conn).invoke("sendSyncFlag", new Object[] { wSyncLockObject },this);
					}
				}
			}	
			
			return wSyncLockObject;
			
			
		} catch (Exception err) {
			log.error("[startNewSyncprocess]",err);
		}
		return null;
	}
	
	public int sendCompletedSyncEvent() {
		try {
			
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			Long room_id = currentClient.getRoom_id();
			
			Map<String,WhiteboardSyncLockObject> syncListRoom = Application.getWhiteBoardSyncListByRoomid(room_id);

			WhiteboardSyncLockObject wSyncLockObject = syncListRoom.get(currentClient.getPublicSID());
			
			if (wSyncLockObject == null) {
				log.error("WhiteboardSyncLockObject not found for this Client "+syncListRoom);
				return -2;
			} else if (!wSyncLockObject.isCurrentLoadingItem()) {
				log.warn("WhiteboardSyncLockObject was not started yet "+syncListRoom);
				return -3;
			} else {
				syncListRoom.remove(currentClient.getPublicSID());
				Application.setWhiteBoardSyncListByRoomid(room_id, syncListRoom);
				
				int numberOfInitial = this.getNumberOfInitialLoaders(syncListRoom);
				
				if (numberOfInitial==0){
					int returnVal = 0;
					Iterator<IConnection> it = current.getScope().getConnections();
					while (it.hasNext()) {
						IConnection conn = it.next();
						if (conn instanceof IServiceCapableConnection) {
							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
							if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
								returnVal++;
								((IServiceCapableConnection) conn).invoke("sendSyncCompleteFlag", new Object[] { wSyncLockObject },this);
							}
						}
					}	
					return returnVal;
				} else {
					return -4;
				}
			}
			
			
		} catch (Exception err) {
			log.error("[sendCompletedSyncEvent]",err);
		}
		return -1;
	}
	
	private int getNumberOfInitialLoaders(Map<String,WhiteboardSyncLockObject> syncListRoom) throws Exception {
		int number = 0;
		for (Iterator<String> iter = syncListRoom.keySet().iterator();iter.hasNext();) {
			WhiteboardSyncLockObject lockObject = syncListRoom.get(iter.next());
			if (lockObject.isInitialLoaded()){
				number++;
			}
		}
		return number;
	}

	/*
	 * Image Sync Sequence
	 * 
	 */
	
	public WhiteboardSyncLockObject startNewObjectSyncProcess(String object_id){
		try {
			
			log.debug("startNewObjectSyncprocess: "+object_id);
			
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			Long room_id = currentClient.getRoom_id();
			
			WhiteboardSyncLockObject wSyncLockObject = new WhiteboardSyncLockObject();
			wSyncLockObject.setAddtime(new Date());
			wSyncLockObject.setRoomclient(currentClient);
			wSyncLockObject.setStarttime(new Date());
			
			Map<String,WhiteboardSyncLockObject> syncListImage = Application.getWhiteBoardObjectSyncListByRoomAndObjectId(room_id,object_id);
			syncListImage.put(currentClient.getPublicSID(), wSyncLockObject);
			Application.setWhiteBoardImagesSyncListByRoomAndObjectId(room_id, object_id, syncListImage);
			
			Iterator<IConnection> it = current.getScope().getConnections();
			while (it.hasNext()) {
				IConnection conn = it.next();
				if (conn instanceof IServiceCapableConnection) {
					RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
					log.debug("sending :"+rcl);
					if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
						log.debug("sendObjectSyncFlag :"+rcl);
						((IServiceCapableConnection) conn).invoke("sendObjectSyncFlag", new Object[] { wSyncLockObject },this);
					}
				}
			}	
			
			return wSyncLockObject;
			
			
		} catch (Exception err) {
			log.error("[startNewObjectSyncProcess]",err);
		}
		return null;
	}
	
	public int sendCompletedObjectSyncEvent(String object_id) {
		try {
			
			log.debug("sendCompletedObjectSyncEvent: "+object_id);
			
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			Long room_id = currentClient.getRoom_id();
			
			Map<String,WhiteboardSyncLockObject> syncListImage = Application.getWhiteBoardObjectSyncListByRoomAndObjectId(room_id,object_id);

			log.debug("sendCompletedObjectSyncEvent syncListImage: "+syncListImage);
			
			WhiteboardSyncLockObject wSyncLockObject = syncListImage.get(currentClient.getPublicSID());
			
			if (wSyncLockObject == null) {
				log.error("WhiteboardSyncLockObject not found for this Client "+currentClient.getPublicSID());
				log.error("WhiteboardSyncLockObject not found for this syncListImage "+syncListImage);
				return -2;
				
			} else {
				
				log.debug("sendCompletedImagesSyncEvent remove: "+currentClient.getPublicSID());
				
				syncListImage.remove(currentClient.getPublicSID());
				Application.setWhiteBoardImagesSyncListByRoomAndObjectId(room_id, object_id, syncListImage);
				
				int numberOfInitial = Application.getWhiteBoardObjectSyncListByRoomid(room_id).size();
				
				log.debug("sendCompletedImagesSyncEvent numberOfInitial: "+numberOfInitial);
				
				if (numberOfInitial==0){
					int returnVal = 0;
					Iterator<IConnection> it = current.getScope().getConnections();
					while (it.hasNext()) {
						IConnection conn = it.next();
						if (conn instanceof IServiceCapableConnection) {
							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
							if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
								returnVal++;
								((IServiceCapableConnection) conn).invoke("sendObjectSyncCompleteFlag", new Object[] { wSyncLockObject },this);
							}
						}
					}	
					return returnVal;
				} else {
					return -4;
				}
			}
			
			
		} catch (Exception err) {
			log.error("[sendCompletedObjectSyncEvent]",err);
		}
		return -1;
	}
	
	
//	private int getNumberOfImageLoaders(Map<String,WhiteboardSyncLockObject> syncListRoom) throws Exception {
//		int number = 0;
//		for (Iterator<String> iter = syncListRoom.keySet().iterator();iter.hasNext();) {
//			WhiteboardSyncLockObject lockObject = syncListRoom.get(iter.next());
//			if (lockObject.isImageLoader()){
//				number++;
//			}
//		}
//		return number;
//	}	
//	

	/*
	 * Image Sync Sequence
	 * 
	 */
	
//	public WhiteboardSyncLockObject startNewSWFSyncprocess(){
//		try {
//			
//			IConnection current = Red5.getConnectionLocal();
//			String streamid = current.getClient().getId();
//			RoomClient currentClient = Application.getClientList().get(streamid);
//			Long room_id = currentClient.getRoom_id();
//			
//			WhiteboardSyncLockObject wSyncLockObject = new WhiteboardSyncLockObject();
//			wSyncLockObject.setAddtime(new Date());
//			wSyncLockObject.setRoomclient(currentClient);
//			wSyncLockObject.setSWFLoader(true);
//			
//			Map<String,WhiteboardSyncLockObject> syncListRoom = Application.getWhiteBoardSWFSyncListByRoomid(room_id);
//			
//
//			wSyncLockObject.setCurrentLoadingItem(true);
//			wSyncLockObject.setStarttime(new Date());
//		
//			syncListRoom.put(currentClient.getPublicSID(), wSyncLockObject);
//			Application.setWhiteBoardSWFSyncListByRoomid(room_id, syncListRoom);
//			
//			Iterator<IConnection> it = current.getScope().getConnections();
//			while (it.hasNext()) {
//				IConnection conn = it.next();
//				if (conn instanceof IServiceCapableConnection) {
//					RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
//					if (room_id!=null && room_id == rcl.getRoom_id()) {
//						((IServiceCapableConnection) conn).invoke("sendSWFSyncFlag", new Object[] { wSyncLockObject },this);
//					}
//				}
//			}	
//			
//			return wSyncLockObject;
//			
//			
//		} catch (Exception err) {
//			log.error("[startNewSWFSyncprocess]",err);
//		}
//		return null;
//	}
//	
//	public int sendCompletedSWFSyncEvent() {
//		try {
//			
//			IConnection current = Red5.getConnectionLocal();
//			String streamid = current.getClient().getId();
//			RoomClient currentClient = Application.getClientList().get(streamid);
//			Long room_id = currentClient.getRoom_id();
//			
//			Map<String,WhiteboardSyncLockObject> syncListRoom = Application.getWhiteBoardSWFSyncListByRoomid(room_id);
//
//			WhiteboardSyncLockObject wSyncLockObject = syncListRoom.get(currentClient.getPublicSID());
//			
//			if (wSyncLockObject == null) {
//				log.error("WhiteboardSyncLockObject not found for this Client "+syncListRoom);
//				return -2;
//			} else if (!wSyncLockObject.isCurrentLoadingItem()) {
//				log.warn("WhiteboardSyncLockObject was not started yet "+syncListRoom);
//				return -3;
//			} else {
//				syncListRoom.remove(currentClient.getPublicSID());
//				Application.setWhiteBoardSWFSyncListByRoomid(room_id, syncListRoom);
//				
//				int numberOfInitial = this.getNumberOfSWFLoaders(syncListRoom);
//				
//				if (numberOfInitial==0){
//					int returnVal = 0;
//					Iterator<IConnection> it = current.getScope().getConnections();
//					while (it.hasNext()) {
//						IConnection conn = it.next();
//						if (conn instanceof IServiceCapableConnection) {
//							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
//							if (room_id!=null && room_id == rcl.getRoom_id()) {
//								returnVal++;
//								((IServiceCapableConnection) conn).invoke("sendSWFSyncCompleteFlag", new Object[] { wSyncLockObject },this);
//							}
//						}
//					}	
//					return returnVal;
//				} else {
//					return -4;
//				}
//			}
//			
//			
//		} catch (Exception err) {
//			log.error("[sendCompletedSWFSyncEvent]",err);
//		}
//		return -1;
//	}
//	
	
//	private int getNumberOfSWFLoaders(Map<String,WhiteboardSyncLockObject> syncListRoom) throws Exception {
//		int number = 0;
//		for (Iterator<String> iter = syncListRoom.keySet().iterator();iter.hasNext();) {
//			WhiteboardSyncLockObject lockObject = syncListRoom.get(iter.next());
//			if (lockObject.isSWFLoader()){
//				number++;
//			}
//		}
//		return number;
//	}	
//	
	public void removeUserFromAllLists(IConnection current, RoomClient currentClient){
		try {
			
			Long room_id = currentClient.getRoom_id();
			
			//TODO: Maybe we should also check all rooms, independent from the current
			//room_id if there is any user registered
			if (room_id != null) {
				
				
				//Check Initial Loaders
				Map<String,WhiteboardSyncLockObject> syncListRoom = Application.getWhiteBoardSyncListByRoomid(room_id);
				
				WhiteboardSyncLockObject wSyncLockObject = syncListRoom.get(currentClient.getPublicSID());
				
				if (wSyncLockObject != null) {
					syncListRoom.remove(currentClient.getPublicSID());
				}
				Application.setWhiteBoardSyncListByRoomid(room_id, syncListRoom);
				
				int numberOfInitial = this.getNumberOfInitialLoaders(syncListRoom);
				
				if (numberOfInitial==0){
					Iterator<IConnection> it = current.getScope().getConnections();
					while (it.hasNext()) {
						IConnection conn = it.next();
						if (conn instanceof IServiceCapableConnection) {
							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
							if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
								//do not send to current
								if (!rcl.getPublicSID().equals(currentClient.getPublicSID())) {
									((IServiceCapableConnection) conn).invoke("sendSyncCompleteFlag", new Object[] { wSyncLockObject },this);
								}
							}
						}
					}	
				}
				
				
				//Check Image Loaders
				Map<String,Map<String,WhiteboardSyncLockObject>> syncListRoomImages = Application.getWhiteBoardObjectSyncListByRoomid(room_id);
				
				for (Iterator<String> iter = syncListRoomImages.keySet().iterator();iter.hasNext();) {
					String object_id = iter.next();
					Map<String,WhiteboardSyncLockObject> syncListImages = syncListRoomImages.get(object_id);
					WhiteboardSyncLockObject wImagesSyncLockObject = syncListImages.get(currentClient.getPublicSID());
					if (wImagesSyncLockObject != null) {
						syncListImages.remove(currentClient.getPublicSID());
					}
					Application.setWhiteBoardImagesSyncListByRoomAndObjectId(room_id, object_id, syncListImages);
				}
				
				int numberOfImageLoaders = Application.getWhiteBoardObjectSyncListByRoomid(room_id).size();
				
				if (numberOfImageLoaders==0){
					Iterator<IConnection> it = current.getScope().getConnections();
					while (it.hasNext()) {
						IConnection conn = it.next();
						if (conn instanceof IServiceCapableConnection) {
							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
							if (room_id!=null && room_id.equals(rcl.getRoom_id())) {
								//do not send to current
								if (!rcl.getPublicSID().equals(currentClient.getPublicSID())) {
									((IServiceCapableConnection) conn).invoke("sendImagesSyncCompleteFlag", new Object[] { "remove" },this);
								} else {
									log.debug("IS current");
								}
							}
						}
					}	
				}
				
				
//				//TODO: Check SWF Loaders
//				Map<String,WhiteboardSyncLockObject> syncListSWF = Application.getWhiteBoardSWFSyncListByRoomid(room_id);
//				
//				WhiteboardSyncLockObject wSWFSyncLockObject = syncListImages.get(currentClient.getPublicSID());
//				
//				if (wSWFSyncLockObject != null) {
//					syncListSWF.remove(currentClient.getPublicSID());
//				}
//				Application.setWhiteBoardSWFSyncListByRoomid(room_id, syncListSWF);
//				
//				int numberOfSWFLoaders = this.getNumberOfSWFLoaders(syncListImages);
//				
//				if (numberOfSWFLoaders==0){
//					Iterator<IConnection> it = current.getScope().getConnections();
//					while (it.hasNext()) {
//						IConnection conn = it.next();
//						if (conn instanceof IServiceCapableConnection) {
//							RoomClient rcl = Application.getClientList().get(conn.getClient().getId());
//							if (room_id!=null && room_id == rcl.getRoom_id()) {
//								//do not send to current
//								if (!rcl.getPublicSID().equals(currentClient.getPublicSID())) {
//									((IServiceCapableConnection) conn).invoke("sendSWFSyncCompleteFlag", new Object[] { wSWFSyncLockObject },this);
//								}
//							}
//						}
//					}	
//				}
//				
//				
			}
			
		} catch (Exception err) {
			log.error("[removeUserFromAllLists]",err);
		}
	}
	
	public void resultReceived(IPendingServiceCall arg0) {
		// TODO Auto-generated method stub
		log.debug("resultReceived: "+arg0);
	}

}
