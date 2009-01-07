package org.openmeetings.app.data.calendar.daos;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.hibernate.beans.calendar.Appointment;
import org.openmeetings.app.hibernate.beans.calendar.MeetingMember;
import org.openmeetings.app.hibernate.beans.lang.FieldLanguage;
import org.openmeetings.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.openmeetings.app.hibernate.beans.user.Users;
import org.openmeetings.app.hibernate.utils.HibernateUtil;

public class AppointmentDaoImpl {

	private static final Log log = LogFactory.getLog(AppointmentDaoImpl.class);

	private AppointmentDaoImpl() {
	}

	private static AppointmentDaoImpl instance = null;

	public static synchronized AppointmentDaoImpl getInstance() {
		if (instance == null) {
			instance = new AppointmentDaoImpl();
		}

		return instance;
	}
	
	/*
	 * insert, update, delete, select
	 */
	
	
	public Appointment getAppointmentById(Long appointmentId) {
		try {
			
			String hql = "select a from Appointment a " +
					"WHERE a.deleted != :deleted " +
					"AND a.appointmentId = :appointmentId ";
					
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setLong("appointmentId",appointmentId);
			
			
			Appointment appoint = (Appointment) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return appoint;
		} catch (HibernateException ex) {
			log.error("[getAppointmentById]: " + ex);
		} catch (Exception ex2) {
			log.error("[getAppointmentById]: " + ex2);
		}
		return null;
	}
	
	
	
	public Long addAppointment(String appointmentName, Long userId, String appointmentLocation,String appointmentDescription, 
			Date appointmentstart, Date appointmentend, 
			Boolean isDaily, Boolean isWeekly, Boolean isMonthly, Boolean isYearly, Long categoryId, Long remind) {
		try {
			
			Appointment ap = new Appointment();
			
			ap.setAppointmentName(appointmentName);
			ap.setAppointmentLocation(appointmentLocation);
			ap.setAppointmentStarttime(appointmentstart);
		 	ap.setAppointmentEndtime(appointmentend);
			ap.setAppointmentDescription(appointmentDescription);
			ap.setRemind(AppointmentReminderTypDaoImpl.getInstance().getAppointmentReminderTypById(remind));
			ap.setStarttime(new Date());
			ap.setDeleted("false");
			ap.setIsDaily(isDaily);
			ap.setIsWeekly(isWeekly);
			ap.setIsMonthly(isMonthly);
			ap.setIsYearly(isYearly);
			ap.setUserId(UsersDaoImpl.getInstance().getUser(userId));
			ap.setAppointmentCategory(AppointmentCategoryDaoImpl.getInstance().getAppointmentCategoryById(categoryId));
			
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Long appointment_id = (Long)session.save(ap);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return appointment_id;
		} catch (HibernateException ex) {
			log.error("[addAppointment]: ",ex);
		} catch (Exception ex2) {
			log.error("[addAppointment]: ",ex2);
		}
		return null;
	}
	
	public Long updateAppointment(Appointment appointment) {
		if (appointment.getAppointmentId() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.update(appointment);
				tx.commit();
				HibernateUtil.closeSession(idf);
				return appointment.getAppointmentId();
			} catch (HibernateException ex) {
				log.error("[updateAppointment] ",ex);
			} catch (Exception ex2) {
				log.error("[updateAppointment] ",ex2);
			}
		} else {
			log.error("[updateAppointment] "+"Error: No AppointmentId given");
		}
		return null;
	}
	
	public Long updateAppointment(Long appointmentId, String appointmentName, Long userId, String appointmentDescription, 
			Date appointmentstart, Date appointmentend,
			Boolean isDaily, Boolean isWeekly, Boolean isMonthly, Boolean isYearly, Long categoryId, Long remind, Map mmClient) {
		try {
			
			
			Appointment ap = this.getAppointmentById(appointmentId);
									
			ap.setAppointmentName(appointmentName);
			ap.setAppointmentStarttime(appointmentstart);
		 	ap.setAppointmentEndtime(appointmentend);
			ap.setAppointmentDescription(appointmentDescription);			
			ap.setUpdatetime(new Date());
			ap.setRemind(AppointmentReminderTypDaoImpl.getInstance().getAppointmentReminderTypById(remind));
			ap.setIsDaily(isDaily);
			ap.setIsWeekly(isWeekly);
			ap.setIsMonthly(isMonthly);
			ap.setIsYearly(isYearly);
			ap.setUserId(UsersDaoImpl.getInstance().getUser(userId));
			ap.setAppointmentCategory(AppointmentCategoryDaoImpl.getInstance().getAppointmentCategoryById(categoryId));
						
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			session.update(ap);

			tx.commit();
		    HibernateUtil.closeSession(idf);
		    
		    
		    List<MeetingMember> meetingsRemoteMembers = MeetingMemberDaoImpl.getInstance().getMeetingMemberByAppointmentId(ap.getAppointmentId());
		    
		    
		    //L�schObjekte ermitteln und entfernen
		    for (MeetingMember memberRemote : meetingsRemoteMembers) {
		    	
	    		boolean found = false;
		    	
		    	for (Iterator iter = mmClient.keySet().iterator();iter.hasNext();) {
		    		Map clientMemeber = (Map) mmClient.get(iter.next());
		    		Long meetingMemberId = Long.valueOf(clientMemeber.get("meetingMemberId").toString()).longValue();
		    	
		    		if (memberRemote.getMeetingMemberId().equals(meetingMemberId)) {
		    			found = true;
		    		}
		    		
		    	}
		    	
		    	if (!found) {
		    		
					//Not in List in client delete it
		    		MeetingMemberDaoImpl.getInstance().deleteMeetingMember(memberRemote.getMeetingMemberId());
		    	}
		    }
		    
		    //Items ermitteln die hinzugef�gt werden
		    for (Iterator iter = mmClient.keySet().iterator();iter.hasNext();) {
	    		Map clientMember = (Map) mmClient.get(iter.next());
	    		
	    		Long meetingMemberId = Long.valueOf(clientMember.get("meetingMemberId").toString()).longValue();
	    	
	    		boolean found = false;
	    		
	    		for (MeetingMember memberRemote : meetingsRemoteMembers) {
	    			if (memberRemote.getMeetingMemberId().equals(meetingMemberId)) {
		    			found = true;
		    		}
	    		}
	    		
	    		if (found == false && Long.valueOf(clientMember.get("userId").toString()).longValue()!=0) {
	    			
	    			//Not In Remote List available
	    			MeetingMemberDaoImpl.getInstance().addMeetingMember(clientMember.get("firstname").toString(), 
	    					clientMember.get("lastname").toString(), "0", "0", 
							appointmentId,Long.valueOf(clientMember.get("userId").toString()).longValue(), clientMember.get("email").toString());
	    			
	    		}
	    		else if (!found) {
	    			
	    			//Not In Remote List available extern members
	    			MeetingMemberDaoImpl.getInstance().addMeetingMember(clientMember.get("firstname").toString(), 
	    					clientMember.get("lastname").toString(), "0", "0", 
							appointmentId, null, clientMember.get("email").toString());
	    			
	    		}
	    	
    			
	    	}
		    
		    
		    return appointmentId;
		} catch (HibernateException ex) {
			log.error("[updateAppointment]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateAppointment]: ",ex2);
		}
		return null;
		
	}
	
	public Long deleteAppointement(Long appointmentId) {
		try {
			
			Appointment app = this.getAppointmentById(appointmentId);
			app.setUpdatetime(new Date());
			app.setDeleted("true");
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(app);
						
			tx.commit();
			HibernateUtil.closeSession(idf);
			return appointmentId;
		} catch (HibernateException ex) {
			log.error("[deleteAppointement]: " + ex);
		} catch (Exception ex2) {
			log.error("[deleteAppointement]: " + ex2);
		}
		return null;
	}
	
	public List<Appointment> getAppointmentsByRange(Long userId, Date starttime, Date endtime) {
		try {
			
		/*	String hql = "select a from Appointment a " +
					"WHERE a.deleted != :deleted "+
					"AND a.userId = :userId "+
			"AND a.starttime BETWEEN :starttime AND :endtime";
			*/
			
			String hql = "select a from Appointment a " +					
			"WHERE a.deleted != :deleted  " +
			"AND "+
			"( " +
					"(a.appointmentStarttime BETWEEN :starttime AND :endtime) "+
				"OR " +
					"(a.appointmentEndtime BETWEEN :starttime AND :endtime) "+
				"OR " +
					"(a.appointmentStarttime < :starttime AND a.appointmentEndtime > :endtime) " +
			") "+
			"AND " +
			"( " +
					"a.userId = :userId "+
			")";
			
			//"AND (a.terminstatus != 4 AND a.terminstatus != 5)";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setDate("starttime", starttime);
			query.setDate("endtime", endtime);
			query.setLong("userId",userId);
			
			List<Appointment> listAppoints = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			for (Appointment appointment : listAppoints) {
				log.debug(appointment);
				
				appointment.setMeetingMember(MeetingMemberDaoImpl.getInstance().getMeetingMemberByAppointmentId(appointment.getAppointmentId()));	
				
			}
			
			return listAppoints;
		} catch (HibernateException ex) {
			log.error("[getAppointmentsByRange]: " + ex);
		} catch (Exception ex2) {
			log.error("[getAppointmentsByRange]: " + ex2);
		}
		return null;
	}
	
	public List<Appointment> getAppointmentsByCat(Long categoryId) {
		try {
			
			String hql = "select a from Appointments a " +
					"WHERE a.deleted != :deleted " +
					"AND a.appointmentCategory.categoryId = :categoryId";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setLong("categoryId", categoryId);
			
			List<Appointment> listAppoints = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return listAppoints;
		} catch (HibernateException ex) {
			log.error("[getAppointements]: " + ex);
		} catch (Exception ex2) {
			log.error("[getAppointements]: " + ex2);
		}
		return null;
	}
	
	public List<Appointment> getAppointmentsByCritAndCat(Long cat_id) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Appointment.class);
			crit.add(Restrictions.eq("deleted", "false"));
			Criteria subcrit = crit.createCriteria("appointmentCategory");
			subcrit.add(Restrictions.eq("categoryId", cat_id));
			List<Appointment> listAppoints = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return listAppoints;
		} catch (HibernateException ex) {
			log.error("[getAppointements]: " + ex);
		} catch (Exception ex2) {
			log.error("[getAppointements]: " + ex2);
		}
		return null;
	}
	
	//next appointment to select date
	public Appointment getNextAppointment(Date appointmentStarttime) {
		try {
						
			String hql = "select a from Appointment a " +
					"WHERE a.deleted != :deleted " +					
					"AND a.appointmentStarttime > :appointmentStarttime ";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");			
			query.setDate("appointmentStarttime", appointmentStarttime);
			
			Appointment appoint = (Appointment) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return appoint;
		} catch (HibernateException ex) {
			log.error("[getNextAppointmentById]: " + ex);
		} catch (Exception ex2) {
			log.error("[getNextAppointmentById]: " + ex2);
		}
		return null;
	}
	
	public List<Appointment> searchAppointmentsByName(String name) {
		try {
			
			String hql = "select a from Appointment a " +
					"WHERE a.deleted != :deleted " +
					"AND a.appointmentName LIKE :appointmentName";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			query.setString("appointmentName", name );
			
			List<Appointment> listAppoints = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return listAppoints;
		} catch (HibernateException ex) {
			log.error("[searchAppointmentsByName]: " + ex);
		} catch (Exception ex2) {
			log.error("[searchAppointmentsByName]: " + ex2);
		}
		return null;
	}
	
}


