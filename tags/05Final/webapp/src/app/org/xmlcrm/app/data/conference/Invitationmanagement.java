package org.xmlcrm.app.data.conference;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xmlcrm.app.templates.InvitationTemplate;
import org.xmlcrm.app.data.basic.AuthLevelmanagement;
import org.xmlcrm.app.data.conference.Roommanagement;
import org.xmlcrm.app.data.user.Usermanagement;
import org.xmlcrm.app.data.basic.Configurationmanagement;
import org.xmlcrm.app.hibernate.beans.user.Users;
import org.xmlcrm.app.hibernate.beans.invitation.Invitations;
import org.xmlcrm.app.hibernate.utils.HibernateUtil;
import org.xmlcrm.utils.crypt.MD5;
import org.xmlcrm.utils.crypt.ManageCryptStyle;
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
	
	public String addInvitationLink(Long user_level, String username, String message,
			String baseurl, String email, String subject, Long rooms_id, String conferencedomain,
			Boolean isPasswordProtected, String invitationpass, Integer valid,
			Date validFrom, Date validTo, Long createdBy
			){
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
				
				Invitations invitation = new Invitations();
				invitation.setIsPasswordProtected(isPasswordProtected);
				if (isPasswordProtected){
					invitation.setInvitationpass(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(invitationpass));
				}
				
				invitation.setInvitationWasUsed(false);
				
				//valid period of Invitation
				if (valid == 1) {
					//endless
					invitation.setIsValidByTime(false);
					invitation.setCanBeUsedOnlyOneTime(false);
				} else if (valid == 2){
					//period
					invitation.setIsValidByTime(true);
					invitation.setCanBeUsedOnlyOneTime(false);
					invitation.setValidFrom(validFrom);
					invitation.setValidTo(validTo);					
				} else {
					//one-time
					invitation.setIsValidByTime(false);
					invitation.setCanBeUsedOnlyOneTime(true);
					invitation.setInvitationWasUsed(false);
				}
				
				invitation.setDeleted("false");
				
				Users us = Usermanagement.getInstance().getUser(createdBy);
				String hashRaw = us.getLogin()+us.getUser_id()+(new Date());
				invitation.setHash(MD5.do_checksum(hashRaw));
				
				invitation.setInvitedBy(us);
				invitation.setInvitedname(username);
				invitation.setInvitedEMail(email);
				invitation.setRoom(Roommanagement.getInstance().getRoomById(rooms_id));
				invitation.setConferencedomain(conferencedomain);
				invitation.setStarttime(new Date());
				

				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				long invitationId = (Long) session.save(invitation);
				tx.commit();
				HibernateUtil.closeSession(idf);
				
				if (invitationId>0){
					return this.sendInvitionLink(username, message, baseurl, email, subject, invitation.getHash());
				} else {
					return "Sys - Error";
				}
			}
		} catch (HibernateException ex) {
			log.error("[addInvitationLink] "+ex);
		} catch (Exception err){
			log.error("addInvitationLink",err);
		}
		return null;
	}	
	
	private String sendInvitionLink(String username, String message, 
			String baseurl, String email, String subject, String invitationsHash){
		try {
				
			String invitation_link = baseurl+"?lzr=swf8&lzt=swf&invitationHash="+invitationsHash;
			
			Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
	        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			String template = InvitationTemplate.getInstance().getRegisterInvitationTemplate(username, message, invitation_link, default_lang_id);
		
			return MailHandler.sendMail(email, subject, template);

		} catch (Exception err){
			log.error("sendInvitationLink",err);
		}
		return null;
	}	
	
	public String sendInvitionLink(Long user_level, String username, String message, String domain, String room, 
			String roomtype, String baseurl, String email, String subject, Long room_id){
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
				
				String invitation_link = baseurl+"?lzr=swf8&lzt=swf&domain="+domain+"&room="+room+"&roomtype="+roomtype+"&email="+email+"&roomid="+room_id;
				
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				String template = InvitationTemplate.getInstance().getRegisterInvitationTemplate(username, message, invitation_link, default_lang_id);
			
				return MailHandler.sendMail(email, subject, template);

			}
		} catch (Exception err){
			log.error("sendInvitationLink",err);
		}
		return null;
	}
	
	public Object getInvitationByHashCode(String hashCode, boolean hidePass) {
		try {
			String hql = "select c from Invitations as c " +
					"where c.hash = :hashCode " +
					"AND c.deleted = :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("hashCode", hashCode);
			query.setString("deleted", "false");
			Invitations invitation = (Invitations) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			if (invitation == null) {
				//already deleted or does not exist
				return new Long(-31);
			} else {
				if (invitation.getCanBeUsedOnlyOneTime()){
					
					//do this only if the user tries to get the Invitation, not while checking the PWD
					if (hidePass) {
						//one-time invitation
						if (invitation.getInvitationWasUsed()){
							//Invitation is of type *only-one-time* and was already used
							return new Long(-32);
						} else {
							//set to true if this is the first time / a normal getInvitation-Query
							invitation.setInvitationWasUsed(true);
							this.updateInvitation(invitation);
							if (hidePass) invitation.setInvitationpass(null);
							return invitation;
						}
					} else {
						return invitation;
					}

				} else if (invitation.getIsValidByTime()){
					Date today = new Date();
					if (invitation.getValidFrom().getTime() <= today.getTime() 
							&& invitation.getValidTo().getTime() >= today.getTime()) {
						this.updateInvitation(invitation);
						if (hidePass) invitation.setInvitationpass(null);
						return invitation;
					} else {
						//Invitation is of type *period* and is not valid anymore
						return new Long(-33);
					}
				} else {
					//Invitation is not limited, neither time nor single-usage
					this.updateInvitation(invitation);
					if (hidePass) invitation.setInvitationpass(null);
					return invitation;
				}
			}
			
		} catch (HibernateException ex) {
			log.error("[getInvitationByHashCode] "+ex);
		} catch (Exception err) {
			log.error("[getInvitationByHashCode]",err);
		}
		return new Long(-1);
	}
	
	private void updateInvitation(Invitations invitation){
		try {
			invitation.setUpdatetime(new Date());
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(invitation);
			tx.commit();
			HibernateUtil.closeSession(idf);		
		} catch (HibernateException ex) {
			log.error("[selectMaxFromUsers] "+ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromUsers] "+ex2);
		}
	}
	
	public Object checkInvitationPass(String hashCode, String pass){
		try {
			Object obj = this.getInvitationByHashCode(hashCode, false);
			if (obj instanceof Invitations){
				Invitations invitation = (Invitations) obj;
				if (ManageCryptStyle.getInstance().getInstanceOfCrypt().verifyPassword(pass, invitation.getInvitationpass())){
					return new Long(1);
				} else {
					return new Long(-34);
				}
			} else {
				return obj;
			}
		} catch (Exception ex2) {
			log.error("[checkInvitationPass] "+ex2);
		}
		return new Long(-1);
	}
}
