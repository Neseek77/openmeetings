package org.openmeetings.app.remote;

import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.openmeetings.app.hibernate.beans.basic.Configuration;
import org.openmeetings.app.hibernate.beans.basic.Sessiondata;

import org.openmeetings.app.hibernate.beans.recording.RoomClient;
import org.openmeetings.app.hibernate.beans.user.Users;
import org.openmeetings.app.hibernate.beans.user.Userdata;

import org.openmeetings.app.data.basic.*;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.data.user.Statemanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.hibernate.beans.basic.RemoteSessionObject;

import org.openmeetings.app.data.conference.Invitationmanagement;
import org.openmeetings.app.data.conference.Feedbackmanagement;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.rss.LoadAtomRssFeed;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author swagner
 *
 */ 
public class MainService implements IPendingServiceCallback {
	
	private static final Logger log = LoggerFactory.getLogger(MainService.class);
	private static MainService instance;

	public static synchronized MainService getInstance() {
		if (instance == null) {
			instance = new MainService();
		}
		return instance;
	}	
   
	/**
	 * get Navigation
	 * @param SID
	 * @param language_id
	 * @return
	 */
	public List getNavi(String SID, long language_id){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        //log.error("getNavi 1: "+users_id);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
	        //log.error("getNavi 2: "+user_level);
			return Navimanagement.getInstance().getMainMenu(user_level,users_id, language_id);
		} catch (Exception err){
			log.error("[getNavi] ", err);
		}
		return null;
	}
  
	/**
	 * gets a user by its SID
	 * @param SID
	 * @param USER_ID
	 * @return
	 */
	public Users getUser(String SID,int USER_ID){
		Users users = new Users();
		Long users_id = Sessionmanagement.getInstance().checkSession(SID);
		long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if(user_level>2){
    		users = UsersDaoImpl.getInstance().getUser(new Long(USER_ID));
    	} else {
    		users.setFirstname("No rights to do this");
    	}
		return users;
	}
	
	
	public RoomClient getCurrentRoomClient(String SID){
		try {
			IConnection current = Red5.getConnectionLocal();
			String streamid = current.getClient().getId();
			RoomClient currentClient = Application.getClientList().get(streamid);
			return currentClient;
		} catch (Exception err){
			log.error("[getCurrentRoomClient]",err);
		}
		return null;
	}	
	
	/**
	 * This Method is jsut for testing
	 * you can find the corresponding
	 * CLietn Function in
	 * xmlcrm/auth/checkLoginData.lzx
	 * @param myObject2
	 * @return
	 */
	public int testObject(Object myObject2){
		try {
			LinkedHashMap myObject = (LinkedHashMap) myObject2;
			log.debug("testObject "+myObject.size());
			log.debug("testObject "+myObject.get(1));
			log.debug("testObject "+myObject.get("stringObj"));
			return myObject.size();
		} catch (Exception e){
			log.error("ex: ", e);
		}
		return -1;
	}

	 
	/**
	 * load this session id before doing anything else
	 * @return a unique session identifier
	 */
    public Sessiondata getsessiondata(){
        return Sessionmanagement.getInstance().startsession();
    }   
       
    /**
     * auth function, use the SID you get by getsessiondata
     * @param SID
     * @param Username
     * @param Userpass
     * @return a valid user account or an empty user with an error message and level -1
     */ 
    public Object loginUser(String SID, String Username, String Userpass){
    	try {
        	log.debug("loginUser 111: "+SID+" "+Username);
        	IConnection current = Red5.getConnectionLocal();
        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
            Object obj = Usermanagement.getInstance().loginUser(SID,Username,Userpass, currentClient);
            
            if (currentClient.getUser_id()!=null && currentClient.getUser_id()>0) {
            	Users us = (Users) obj;
            	currentClient.setFirstname(us.getFirstname());
            	currentClient.setLastname(us.getLastname());
    			Iterator<IConnection> it = current.getScope().getConnections();
    			while (it.hasNext()) {
    				//log.error("hasNext == true");
    				IConnection cons = it.next();
    				//log.error("cons Host: "+cons);
    				if (cons instanceof IServiceCapableConnection) {
    					if (!cons.equals(current)){
    						//log.error("sending roomDisconnect to " + cons);
    						RoomClient rcl = Application.getClientList().get(cons.getClient().getId());
    						//Send to all connected users
							((IServiceCapableConnection) cons).invoke("roomConnect",new Object[] { currentClient }, this);
							//log.error("sending roomDisconnect to " + cons);
    					}
    				}
    			} 
            }

			return obj;
    	} catch (Exception err) {
    		log.error("loginUser",err);
    	}
    	return null;
    } 
    
    /**
     * Attention! This SID is NOT the default session id! its the Session id retrieved in the call
     * from the SOAP Gateway!
     * @param SID
     * @return
     */
    public Long loginUserByRemote(String SID){
    	try {
        	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
        	if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
        		
        		Sessiondata sd = Sessionmanagement.getInstance().getSessionByHash(SID);
        		if (sd == null || sd.getSessionXml() == null) {
        			return new Long(-37);
        		} else {
        			
        			XStream xStream = new XStream(new XppDriver());
        			xStream.setMode(XStream.NO_REFERENCES);
        			
        			String xmlString = sd.getSessionXml();
        			RemoteSessionObject userObject = (RemoteSessionObject) xStream.fromXML(xmlString);
        			
        			IConnection current = Red5.getConnectionLocal();
        			String streamId = current.getClient().getId();
        			RoomClient currentClient = Application.getClientList().get(streamId);	
        			currentClient.setUserObject(userObject.getUsername(), userObject.getFirstname(), userObject.getLastname());
        			currentClient.setPicture_uri(userObject.getPictureUrl());
        			currentClient.setMail(userObject.getEmail());
        			Application.getClientList().put(streamId, currentClient);
        			
        			return new Long(1);
        		}
        	}
    	} catch (Exception err) {
    		log.error("[loginUserByRemote] ",err);
    	}
    	return new Long(-1);
    }
    
    /**
     * this function logs a user into if he enteres the app directly into a room
     * @param SID
     */
    public void markSessionAsLogedIn(String SID){
    	Sessionmanagement.getInstance().updateUser(SID, -1);
    }
    
    /**
     * clear this session id
     * @param SID
     * @return string value if completed
     */
    public Long logoutUser(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	IConnection current = Red5.getConnectionLocal();
		RoomClient currentClient = Application.getClientList().get(current.getClient().getId());	
		currentClient.setUserObject(null, null, null, null);
    	return Usermanagement.getInstance().logout(SID,users_id);
    }
    
    /**
     * get a list of all states, needs no authentification to load
     * @return List of State-Objects or null
     */
    public List getStates(){
    	return Statemanagement.getInstance().getStates();
    }

    /**
     * Load if the users can register itself by using the form without logging in, 
     * needs no authentification to load
     * @param SID
     * @return
     */
    public Configuration allowFrontendRegister(String SID){
    	return Configurationmanagement.getInstance().getConfKey(3, "allow_frontend_register");
    }
    
    /**
     * Add a user register by an Object
     * see [registerUser] for the index of the Object
     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
     * otherwise the user will get an error code
     * @param regObject
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
    public Long registerUserByObject(Object regObjectObj){
    	try {
    		LinkedHashMap regObject = (LinkedHashMap) regObjectObj;
        	return Usermanagement.getInstance().registerUser(regObject.get("Username").toString(), regObject.get("Userpass").toString(), 
        			regObject.get("lastname").toString(), regObject.get("firstname").toString(), regObject.get("email").toString(), 
        			new Date(), regObject.get("street").toString(), regObject.get("additionalname").toString(), 
        			regObject.get("fax").toString(), regObject.get("zip").toString(), 
        			Long.valueOf(regObject.get("states_id").toString()).longValue(), regObject.get("town").toString(), 
        			Long.valueOf(regObject.get("language_id").toString()).longValue());
    	} catch (Exception ex) {
    		log.error("registerUserByObject",ex);
    	}
    	return new Long(-1);
    }
    
    /**
     * Register a new User
     * To allow the registering the config_key *allow_frontend_register* has to be the value 1
     * otherwise the user will get an error code
     * @deprecated use registerUserByObject instead
     * @param SID
     * @param Username
     * @param Userpass
     * @param lastname
     * @param firstname
     * @param email
     * @param age
     * @param street
     * @param additionalname
     * @param fax
     * @param zip
     * @param states_id
     * @param town
     * @param language_id
     * @return new users_id OR null if an exception, -1 if an error, -4 if mail already taken, -5 if username already taken, -3 if login or pass or mail is empty 
     */
	public Long registerUser(String SID, String Username, String Userpass, String lastname, 
				String firstname, String email, Date age, String street, String additionalname, 
				String fax, String zip, long states_id, String town, long language_id){
    	return Usermanagement.getInstance().registerUser(Username, Userpass, lastname, firstname, email, 
    			age, street, additionalname, fax, zip, states_id, town, language_id);
	}	
	
	/**
	 * logs a user out and deletes his account
	 * @param SID
	 * @return
	 */
    public Long deleteUserIDSelf(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if(user_level>=1){
    		Usermanagement.getInstance().logout(SID,users_id);
    		return UsersDaoImpl.getInstance().deleteUserID(users_id);
    	} else {
    		return new Long(-10);
    	}
    }
    
    /**
     * send an invitation to another user by Mail
     * @deprecated
     * @param SID
     * @param username
     * @param message
     * @param domain
     * @param room
     * @param roomtype
     * @param baseurl
     * @param email
     * @param subject
     * @param room_id
     * @return
     */
    public String sendInvitation(String SID, String username, String message, String domain, 
    		String room, String roomtype, String baseurl, String email, String subject, Long room_id){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	return Invitationmanagement.getInstance().sendInvitionLink(user_level, username, message, domain, room, roomtype, baseurl, email, subject, room_id);
    }

    /**
     * send some feedback, this will only work for the online demo-version
     * @param SID
     * @param username
     * @param message
     * @param email
     * @return
     */
    public String sendFeedback(String SID, String username, String message, String email){
    	return Feedbackmanagement.getInstance().sendFeedback(username, email, message);
    }
    
    public List<Userdata> getUserdata(String SID){
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return Usermanagement.getInstance().getUserdataDashBoard(users_id);
    	}
    	return null;
    }
    
    
    /**
     * @deprecated
     * @param SID
     * @return
     */
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>>> getRssFeeds(String SID) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	return LoadAtomRssFeed.getInstance().getRssFeeds(user_level);
    }
    
    
    
    /**
     * 
     * @param SID
     * @param urlEndPoint
     * @return
     */
    public LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,Object>>> getRssFeedByURL(String SID, String urlEndPoint) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		return LoadAtomRssFeed.getInstance().parseRssFeed(urlEndPoint);
    	} else {
    		return null;
    	}
    }
    
    /**
     * TODO: Is this function in usage?
     * @deprecated
     * @param SID
     * @param domain
     * @return
     */
	public LinkedHashMap<Integer,RoomClient> getUsersByDomain(String SID, String domain) {
    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);
    	if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
    		LinkedHashMap<Integer,RoomClient> lMap = new LinkedHashMap<Integer,RoomClient>();
    		Integer counter = 0;
    		for (Iterator<String> it = Application.getClientList().keySet().iterator();it.hasNext();) {
    			RoomClient rc = Application.getClientList().get(it.next());
    			//if (rc.getDomain().equals(domain)) {
    				lMap.put(counter, rc);
    				counter++;
    			//}
    		}
    		return lMap;
    	} else {
    		return null;
    	}
	}

	public void resultReceived(IPendingServiceCall arg0) {
		// TODO Auto-generated method stub
		log.debug("[resultReceived]"+arg0);
	}

    
    /*
     * Shopsystem
     
    public zahlungsarten[] getZahlungsarten(String SID){
    	return ResHandler.getZahlungsarten(SID);
    }
    public lieferarten[] getLieferarten(String SID){
    	return ResHandler.getLieferarten(SID);
    }     
	public products[] getProductsByCat(String SID){
		return ResHandler.getProductByCat(SID);
	}	
	public products[] searchProduct(String SID,String searchstring){
		return ResHandler.searchProduct(SID,searchstring);
	}    
	public products[] getProductsByCatID(String SID,String cat, int start){
		return ResHandler.getProductByCat(SID,start,cat);
	}
	public products[] getAllProductByCat(String SID,String cat){
		return ResHandler.getAllProductByCat(SID,cat);
	}
	public products getProductByID(String SID, int artnumber){
		return ResHandler.getProductByID(SID,artnumber);
	}	
	public Userwaren[] getUserwaren(String SID){
		return ResHandler.getUserwaren(SID);
	}
	public Userwaren getUserwarenByID(String SID,int WAREN_ID){
		return ResHandler.getUserwarenByID(SID,WAREN_ID);
	}	
	public String addWarenkorb(String SID, int ARTICLE_ID, int amount){
		return ResHandler.addWarenkorb(SID,ARTICLE_ID,amount);
	}
    public String updateWarenkorb(String SID, int WAREN_ID, int status, int ZAHLUNGS_ID, int LIEFER_ID, int amount, String  comment){
    	return ResHandler.updateWarenkorb(SID, WAREN_ID, status, ZAHLUNGS_ID, LIEFER_ID, amount, comment);
    }
    public String deleteWarenkorb(String SID, int WAREN_ID){
    	return ResHandler.deleteWarenkorb(SID,WAREN_ID);
    }
    */

    
/*
 * UserGroup Management Handlers
  
    public Usergroups[] getAllGroups(String SID){
        return ResHandler.getAllGroups(SID);
    }
    public List getAllUsers(String SID,int start, int max){
        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
        long user_level = Usermanagement.getInstance().getUserLevelByID(users_id); 
        return Usermanagement.getInstance().getusersAdmin(user_level,start,max);
    }    
    public Users_Usergroups getSingleGroup(String SID,int GROUP_ID){
        return ResHandler.getSingleGroup(SID, GROUP_ID);
    }      
    public Users_Usergroups getGroupUsers(String SID,int GROUP_ID){
        return ResHandler.getGroupUsers(SID,GROUP_ID);
    }
    public String addUserToGroup(String SID,int GROUP_ID,int USER_ID,String comment){
        return ResHandler.addUserToGroup(SID,GROUP_ID,USER_ID,comment);
    }
    public String updateUserGroup(String SID,int UID,int GROUP_ID,int USER_ID,String comment){
        return ResHandler.updateUserGroup(SID,UID,GROUP_ID,USER_ID,comment);
    }
    public String deleteUserGroupByID(String SID,int UID){
        return ResHandler.deleteUserGroupByID(SID,UID);
    }
    public String addGroup(String SID,String name,int freigabe,String description,String comment){
        return ResHandler.addGroup(SID,name,freigabe,description,comment);
    }
    public String updateGroup(String SID,int GROUP_ID,int freigabe, String name, String description, String comment){
        return ResHandler.updateGroup(SID,GROUP_ID,freigabe, name, description, comment);
    }
    public String deleteGroup(String SID,int GROUP_ID){
        return ResHandler.deleteGroup(SID,GROUP_ID);
    } 
 */    
    
}
