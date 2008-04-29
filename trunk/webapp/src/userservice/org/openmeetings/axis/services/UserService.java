package org.openmeetings.axis.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmeetings.app.remote.MainService;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.ErrorManagement;
import org.openmeetings.app.data.basic.Fieldmanagment;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.beans.basic.ErrorResult;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.hibernate.beans.basic.ErrorValues;
import org.openmeetings.app.hibernate.beans.basic.Sessiondata;
import org.openmeetings.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.openmeetings.app.hibernate.beans.basic.RemoteSessionObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class UserService {

	private static final Log log = LogFactory.getLog(UserService.class);
	
	/**
	 * load this session id before doing anything else
	 * @return Sessiondata-Object
	 */
    public Sessiondata getSession(){
    	return MainService.getInstance().getsessiondata();
    }   
    
    /**
     * auth function, use the SID you get by getSession
     * @param SID
     * @param Username
     * @param Userpass
     * @return positive means Loggedin, if negativ its an ErrorCode, you have to invoke the Method
     * getErrorByCode to get the Text-Description of that ErrorCode
     */  
    public Object loginUser(String SID, String username, String userpass){
    	try {
    		Object obj = Usermanagement.getInstance().loginUser(SID,username,userpass, null);
    		if (obj==null){
    			return new Long(-1);
    		}
    		String objName = obj.getClass().getName();
    		log.debug("objName: "+objName);
    		if (objName.equals("java.lang.Long")){
    			return obj;
    		} else {
    			return new Long(1);
    		}
    	} catch (Exception err) {
    		log.error("[loginUser]",err);
    	}
    	return new Long(-1);
    } 
    
    /**
     * Gets the Error-Object
     * @param SID
     * @param errorid
     * @param language_id
     * @return
     */
	public ErrorResult getErrorByCode(String SID, Long errorid, Long language_id){
        try {
            if (errorid<0){
            	ErrorValues eValues = ErrorManagement.getInstance().getErrorValuesById(errorid*(-1));
    	        if (eValues!=null){
    	        	Fieldlanguagesvalues errorValue = Fieldmanagment.getInstance().getFieldByIdAndLanguage(eValues.getFieldvalues().getFieldvalues_id(),language_id);
    	        	Fieldlanguagesvalues typeValue = Fieldmanagment.getInstance().getFieldByIdAndLanguage(eValues.getErrorType().getFieldvalues().getFieldvalues_id(),language_id);
    	        	if (errorValue!=null) {
    	        		return new ErrorResult(errorid,errorValue.getValue(),typeValue.getValue());
    	        	}
            	}
            } else {
            	return new ErrorResult(errorid,"Error ... please check your input","Error");
            }
        } catch (Exception err) {
        	log.error("[getErrorByCode] ",err);
        }
        return null;
	}
	
	/**
	 * 
	 * @param SID
	 * @param firstname
	 * @param lastname
	 * @param profilePictureUrl
	 * @param email
	 * @return
	 */
	public Long setUserObject(String SID, String username, String firstname, String lastname, 
			String profilePictureUrl, String email){
		try {
	    	Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	    	Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);			
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
				
				RemoteSessionObject remoteSessionObject = new RemoteSessionObject(username, firstname, lastname, 
						profilePictureUrl, email);
				
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.NO_REFERENCES);
				String xmlString = xStream.toXML(remoteSessionObject);
				
				Sessionmanagement.getInstance().updateUserRemoteSession(SID, xmlString);
				
				return new Long(1);
			} else {
				return new Long(-36);
			}
		} catch (Exception err){
			log.error("sendInvitationLink",err);
		}
		return new Long(-1);			
	}
	
	
}
