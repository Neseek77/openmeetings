package org.xmlcrm.app.data.conference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlcrm.app.templates.InvitationTemplate;
import org.xmlcrm.app.data.basic.AuthLevelmanagement;
import org.xmlcrm.utils.mail.MailHandler;

/**
 * 
 * @author swagner
 *
 */
public class Invitationmanagement {

	private static final Log log = LogFactory.getLog(Invitationmanagement.class);

	private static Invitationmanagement instance;

	private Invitationmanagement() {}

	public static synchronized Invitationmanagement getInstance() {
		if (instance == null) {
			instance = new Invitationmanagement();
		}
		return instance;
	}
	
	public String sendInvitionLink(long USER_LEVEL, String username, String message, String domain, String room, String roomtype, String baseurl, String email, String subject){
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(USER_LEVEL)){
				
				String invitation_link = baseurl+"?lzr=swf8&lzt=swf&domain="+domain+"&room="+room+"&roomtype="+roomtype;
				
				String template = InvitationTemplate.getInstance().getRegisterInvitationTemplate(username, message, invitation_link);
			
				return MailHandler.sendMail(email, subject, template);

			}
		} catch (Exception err){
			log.error("sendInvitationLink",err);
		}
		return null;
	}
	
}
