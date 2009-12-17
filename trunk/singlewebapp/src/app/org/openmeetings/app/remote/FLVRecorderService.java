package org.openmeetings.app.remote;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.flvrecord.FlvRecordingDaoImpl;
import org.openmeetings.app.data.flvrecord.FlvRecordingMetaDataDaoImpl;
import org.openmeetings.app.data.flvrecord.beans.FLVRecorderObject;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.recording.RoomClient;
import org.openmeetings.app.remote.red5.ClientListManager;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.utils.math.CalendarPatterns;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;


public class FLVRecorderService implements IPendingServiceCallback {
	
	private static final Logger log = Red5LoggerFactory.getLogger(FLVRecorderService.class, "openmeetings");

	//Spring Beans
	private ClientListManager clientListManager = null;
	private FlvRecordingDaoImpl flvRecordingDaoImpl = null;
	private FlvRecordingMetaDataDaoImpl flvRecordingMetaDataDaoImpl = null;
	
	public void resultReceived(IPendingServiceCall arg0) {
		// TODO Auto-generated method stub
		
	}

	public ClientListManager getClientListManager() {
		return this.clientListManager;
	}
	public void setClientListManager(ClientListManager clientListManager) {
		this.clientListManager = clientListManager;
	}
	
	public FlvRecordingDaoImpl getFlvRecordingDaoImpl() {
		return flvRecordingDaoImpl;
	}
	public void setFlvRecordingDaoImpl(FlvRecordingDaoImpl flvRecordingDaoImpl) {
		this.flvRecordingDaoImpl = flvRecordingDaoImpl;
	}

	public FlvRecordingMetaDataDaoImpl getFlvRecordingMetaDataDaoImpl() {
		return flvRecordingMetaDataDaoImpl;
	}
	public void setFlvRecordingMetaDataDaoImpl(
			FlvRecordingMetaDataDaoImpl flvRecordingMetaDataDaoImpl) {
		this.flvRecordingMetaDataDaoImpl = flvRecordingMetaDataDaoImpl;
	}

	public RoomClient checkForRecording(){
		try {
			
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			
			log.debug("getCurrentRoomClient -2- "+streamid);
			
			RoomClient currentClient = this.clientListManager.getClientByStreamId(streamid);
			
			HashMap<String,RoomClient> roomClientList = this.clientListManager.getClientListByRoom(currentClient.getRoom_id());
			
			for (Iterator<String> iter = roomClientList.keySet().iterator();iter.hasNext();) {
				
				RoomClient rcl = roomClientList.get(iter.next());
				
				if (rcl.getIsRecording()) {
					
					return rcl;
					
				}
				
			}
			
			return null;
			
		} catch (Exception err) {
			err.printStackTrace();
			log.error("[checkForRecording]",err);
		}
		return null;
	}
	
	private static String generateFileName(String streamid) throws Exception{
		String dateString = CalendarPatterns.getTimeForStreamId(new java.util.Date());
		return streamid+"_"+dateString;
		
	}
	
	public String recordMeetingStream(String roomRecordingName, String comment){
		try {
			IConnection current = Red5.getConnectionLocal();
			RoomClient currentClient = this.clientListManager.getClientByStreamId(current.getClient().getId());
			Long room_id = currentClient.getRoom_id();

			Date now = new Date();
			
			//Receive flvRecordingId
			Long flvRecordingId = this.flvRecordingDaoImpl.addFlvRecording("", roomRecordingName, null, currentClient.getUser_id(),
									room_id, now, null, currentClient.getUser_id(), comment, currentClient.getStreamid());
			
			//Update Client and set Flag
			currentClient.setIsRecording(true);
			currentClient.setFlvRecordingId(flvRecordingId);
			this.clientListManager.updateClientByStreamId(current.getClient().getId(), currentClient);
			
			//get all stream and start recording them
			Collection<Set<IConnection>> conCollection = current.getScope().getConnections();
			for (Set<IConnection> conset : conCollection) {
				for (IConnection conn : conset) {
					if (conn != null) {
						if (conn instanceof IServiceCapableConnection) {
							RoomClient rcl = this.clientListManager.getClientByStreamId(conn.getClient().getId());
							
							log.debug("is this users still alive? :"+rcl);
							
							//FIXME: Check if this function is really in use at the moment	
							if (!rcl.getIsScreenClient()) {
								((IServiceCapableConnection) conn).invoke("startedRecording",new Object[] { currentClient }, this);
							}
							
							//If its the recording client we need another type of Meta Data
							if (rcl.getIsScreenClient()) {
							
								if (rcl.getFlvRecordingId() != null && rcl.getScreenPublishStarted() != null && rcl.getScreenPublishStarted()) {
									
									String streamName_Screen = generateFileName(rcl.getStreamPublishName().toString());
									
									//Start FLV Recording
									recordShow(conn, rcl.getStreamPublishName(), streamName_Screen);
									
									Long flvRecordingMetaDataId = this.flvRecordingMetaDataDaoImpl.addFlvRecordingMetaData(flvRecordingId, 
																			rcl.getFirstname()+" "+rcl.getLastname(), now, 
																						false, false, true, streamName_Screen);
									
									//Add Meta Data
									rcl.setFlvRecordingMetaDataId(flvRecordingMetaDataId);
									
									this.clientListManager.updateClientByStreamId(rcl.getStreamid(), rcl);
								
								}
								
							} else if 
							//if the user does publish av, a, v
							//But we only record av or a, video only is not interesting
							(rcl.getAvsettings().equals("av") || 
									rcl.getAvsettings().equals("a") || 
									rcl.getAvsettings().equals("v")){	
								
								String streamName = generateFileName(String.valueOf(rcl.getBroadCastID()).toString());
								
								//Start FLV recording
								recordShow(conn, String.valueOf(rcl.getBroadCastID()).toString(), streamName);
								
								//Add Meta Data
								boolean isAudioOnly = false;
								if (rcl.getAvsettings().equals("a")){
									isAudioOnly = true;
								}
								
								Long flvRecordingMetaDataId = this.flvRecordingMetaDataDaoImpl.addFlvRecordingMetaData(flvRecordingId, 
																	rcl.getFirstname()+" "+rcl.getLastname(), now, 
																				isAudioOnly, false, false, streamName);
								
								rcl.setFlvRecordingMetaDataId(flvRecordingMetaDataId);
								
								this.clientListManager.updateClientByStreamId(rcl.getStreamid(), rcl);
								
							} 
								
						}
					}
				}		
			}
			
			return roomRecordingName;
			
		} catch (Exception err) {
			log.error("[recordMeetingStream]",err);
		}
		return null;
	}
	
	/**
	 * Start recording the publishing stream for the specified
	 *
	 * @param conn
	 */
	private static void recordShow(IConnection conn, String broadcastid, String streamName) throws Exception {
		log.debug("Recording show for: " + conn.getScope().getContextPath());
		log.debug("Name of CLient and Stream to be recorded: "+broadcastid);		
		//log.debug("Application.getInstance()"+Application.getInstance());
		log.debug("Scope "+conn);
		log.debug("Scope "+conn.getScope());
		// Get a reference to the current broadcast stream.
		ClientBroadcastStream stream = (ClientBroadcastStream) ScopeApplicationAdapter.getInstance()
				.getBroadcastStream(conn.getScope(), broadcastid);
		try {
			// Save the stream to disk.
			stream.saveAs(streamName, false);
		} catch (Exception e) {
			log.error("Error while saving stream: " + streamName, e);
		}
	}	
	
	/**
	 * Stops recording the publishing stream for the specified
	 * IConnection.
	 *
	 * @param conn
	 */
	public void stopRecordingShow(IConnection conn, String broadcastId) {
		try {
			
			log.debug("** stopRecordingShow: "+conn);
			log.debug("### Stop recording show for broadcastId: "+ broadcastId + " || " + conn.getScope().getContextPath());
			
			Object streamToClose = ScopeApplicationAdapter.getInstance().
											getBroadcastStream(conn.getScope(), broadcastId);
			
			if (streamToClose == null) {
				log.debug("Could not aquire Stream, maybe already closed");
			}
			
			ClientBroadcastStream stream = (ClientBroadcastStream) streamToClose;
			// Stop recording.
			stream.stopRecording();
			
		} catch (Exception err) {
			log.error("[stopRecordingShow]",err);
		}
	}
	
	public Long stopRecordAndSave(IScope scope, String roomrecordingName, RoomClient currentClient){
		try {
			log.debug("stopRecordAndSave "+currentClient.getUsername()+","+currentClient.getUserip());
			
			
			//get all stream and stop recording them
			Collection<Set<IConnection>> conCollection = scope.getConnections();
			for (Set<IConnection> conset : conCollection) {
				for (IConnection conn : conset) {
					if (conn != null) {
						if (conn instanceof IServiceCapableConnection) {
							
							RoomClient rcl = ClientListManager.getInstance().getClientByStreamId(conn.getClient().getId());
							
							//FIXME: Check if this function is really in use at the moment	
//							if (!rcl.getIsScreenClient()) {
//								((IServiceCapableConnection) conn).invoke("stoppedRecording",new Object[] { currentClient }, this);
//							}
							
							log.debug("is this users still alive? :"+rcl);
							
							if (rcl.getIsScreenClient()) {
								
								if (rcl.getFlvRecordingId() != null && rcl.getScreenPublishStarted() != null && rcl.getScreenPublishStarted()) {
								
									//Stop FLV Recording
									stopRecordingShow(conn, rcl.getStreamPublishName());
									
									//Update Meta Data
									this.flvRecordingMetaDataDaoImpl.updateFlvRecordingMetaDataEndDate(rcl.getFlvRecordingMetaDataId(),new Date());
								}
								
							} else if (rcl.getAvsettings().equals("av") || 
									rcl.getAvsettings().equals("a") || 
									rcl.getAvsettings().equals("v")){	
								
								stopRecordingShow(conn, String.valueOf(rcl.getBroadCastID()).toString() );
								
								//Update Meta Data
								this.flvRecordingMetaDataDaoImpl.updateFlvRecordingMetaDataEndDate(rcl.getFlvRecordingMetaDataId(),new Date());
								
							}
							
						}
					}
				}
			}				
			
			//Store to database
			Long flvRecordingId = currentClient.getFlvRecordingId();
			
			this.flvRecordingDaoImpl.updateFlvRecordingEndTime(flvRecordingId, new Date());
			
			//Reset values
			currentClient.setFlvRecordingId(null);
			
			this.clientListManager.updateClientByStreamId(currentClient.getStreamid(), currentClient);
			
		} catch (Exception err) {
			log.error("[stopRecordAndSave]",err);
		}
		return new Long(-1);
	}
	
	
	public void stopRecordingShowForClient(IConnection conn, RoomClient rcl) {
		try {
			//this cannot be handled here, as to stop a stream and to leave a room is not
			//the same type of event.
			//StreamService.addRoomClientEnterEventFunc(rcl, roomrecordingName, rcl.getUserip(), false);
			log.error("### stopRecordingShowForClient: "+rcl.getIsRecording()+","+rcl.getUsername()+","+rcl.getUserip());
			
			if (rcl.getIsScreenClient()) {
				
				if (rcl.getFlvRecordingId() != null && rcl.getScreenPublishStarted() != null && rcl.getScreenPublishStarted()) {
				
					//Stop FLV Recording
					//FIXME: Is there really a need to stop it manually if the user just 
					//stops the stream?
					stopRecordingShow(conn, rcl.getStreamPublishName());
					
					//Update Meta Data
					this.flvRecordingMetaDataDaoImpl.updateFlvRecordingMetaDataEndDate(rcl.getFlvRecordingMetaDataId(),new Date());
				}
				
			} else if ((rcl.getAvsettings().equals("a") || rcl.getAvsettings().equals("v") 
					|| rcl.getAvsettings().equals("av"))){
				
				//FIXME: Is there really a need to stop it manually if the user just 
				//stops the stream?
				stopRecordingShow(conn,String.valueOf(rcl.getBroadCastID()).toString());
				
				//Update Meta Data
				this.flvRecordingMetaDataDaoImpl.updateFlvRecordingMetaDataEndDate(rcl.getFlvRecordingMetaDataId(),new Date());
			}
			
		} catch (Exception err) {
			log.error("[stopRecordingShowForClient]",err);
		}
	}
	
	public void addRecordingByStreamId(IConnection conn, String streamId, 
			RoomClient rcl, Long flvRecordingId) {
		try {
			
			Date now = new Date();
			
			//If its the recording client we need another type of Meta Data
			if (rcl.getIsScreenClient()) {
			
				if (rcl.getFlvRecordingId() != null && rcl.getScreenPublishStarted() != null && rcl.getScreenPublishStarted()) {
					
					String streamName_Screen = generateFileName(rcl.getStreamPublishName().toString());
					
					log.debug("##############  ADD SCREEN OF SHARER :: "+rcl.getStreamPublishName());
					
					//Start FLV Recording
					recordShow(conn, rcl.getStreamPublishName(), streamName_Screen);
					
					Long flvRecordingMetaDataId = this.flvRecordingMetaDataDaoImpl.addFlvRecordingMetaData(flvRecordingId, 
															rcl.getFirstname()+" "+rcl.getLastname(), now, 
																		false, false, true, streamName_Screen);
					
					//Add Meta Data
					rcl.setFlvRecordingMetaDataId(flvRecordingMetaDataId);
					
					this.clientListManager.updateClientByStreamId(rcl.getStreamid(), rcl);
				
				}
				
			} else if 
			//if the user does publish av, a, v
			//But we only record av or a, video only is not interesting
			(rcl.getAvsettings().equals("av") || 
					rcl.getAvsettings().equals("a") || 
					rcl.getAvsettings().equals("v")){	
				
				String streamName = generateFileName(String.valueOf(rcl.getBroadCastID()).toString());
				
				//Start FLV recording
				recordShow(conn, String.valueOf(rcl.getBroadCastID()).toString(), streamName);
				
				//Add Meta Data
				boolean isAudioOnly = false;
				if (rcl.getAvsettings().equals("a")){
					isAudioOnly = true;
				}
				
				Long flvRecordingMetaDataId = this.flvRecordingMetaDataDaoImpl.addFlvRecordingMetaData(flvRecordingId, 
													rcl.getFirstname()+" "+rcl.getLastname(), now, 
																isAudioOnly, false, false, streamName);
				
				rcl.setFlvRecordingMetaDataId(flvRecordingMetaDataId);
				
				this.clientListManager.updateClientByStreamId(rcl.getStreamid(), rcl);
				
			} 
				
		} catch (Exception err) {
			log.error("[addRecordingByStreamId]",err);
		}	
	}
	
	
	public FLVRecorderObject getFLVExplorerByRoom(String SID) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){	
	        	
	        	FLVRecorderObject fileExplorerObject = new FLVRecorderObject();
	        	
	        	fileExplorerObject.setUserHome(this.flvRecordingDaoImpl.getFlvRecordingRootByOwner(users_id));
	        	
	        	fileExplorerObject.setRoomHome(this.flvRecordingDaoImpl.getInstance().getFlvRecordingRootByPublic());
	        	
	        	return fileExplorerObject;
	        	
	        }
		} catch (Exception err){
			log.error("[getFileExplorerByRoom] "+err);
		}
		return null;
	}
	
	
}
