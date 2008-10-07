package org.openmeetings.app.data.record;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openmeetings.app.data.conference.Roommanagement;
import org.openmeetings.app.hibernate.beans.recording.Recording;
import org.openmeetings.app.hibernate.beans.rooms.Rooms;
import org.openmeetings.app.hibernate.beans.user.Users;
import org.openmeetings.app.hibernate.utils.HibernateUtil;

public class Recordingmanagement {

	private static final Logger log = LoggerFactory.getLogger(Recordingmanagement.class);

	private static Recordingmanagement instance;

	private Recordingmanagement() {}

	public static synchronized Recordingmanagement getInstance() {
		if (instance == null) {
			instance = new Recordingmanagement();
		}
		return instance;
	}
	
	public Long addRecording(String name, Long duration, String xmlString, Long rooms_id, Users recordedby, String  comment) throws Exception{
		Recording recording = new Recording();
		recording.setDeleted("false");
		recording.setDuration(duration);
		recording.setComment(comment);
		recording.setRecordedby(recordedby);
		recording.setName(name);
		recording.setXmlString(xmlString);
		recording.setRooms(Roommanagement.getInstance().getRoomById(rooms_id));
		recording.setStarttime(new java.util.Date());
		return this.addRecording(recording);
	}
	
	public Long addRecording(Recording recording) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long recording_id = (Long) session.save(recording);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return recording_id;
		} catch (HibernateException ex) {
			log.error("[addRecording] ",ex);
		} catch (Exception ex2) {
			log.error("[addRecording] ",ex2);
		}
		return new Long(-1);
	}
	
	public List<Recording> getRecordings(){
		try {
			String hql = "select c from Recording as c where c.deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			List<Recording> ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return ll;
		} catch (HibernateException ex) {
			log.error("getRecordings",ex);
		} catch (Exception ex2) {
			log.error("getRecordings",ex2);
		}
		return null;
	}
	
	public List<Recording> getRecordingsByRoom(Long rooms_id){
		try {
			String hql = "select c from Recording as c where c.rooms.rooms_id = :rooms_id AND c.deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("rooms_id", rooms_id);
			query.setString("deleted", "true");
			List<Recording> ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return ll;
		} catch (HibernateException ex) {
			log.error("getRecordingsByRoom",ex);
		} catch (Exception ex2) {
			log.error("getRecordingsByRoom",ex2);
		}
		return null;
	}
	
	public List<Recording> getRecordingsByWhereClause(String where){
		try {
			String hql = "select c from Recording as c where " + where + " c.deleted != :deleted";
			log.error("getRecordingsByWhereClause: "+hql);
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			List<Recording> ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return ll;
		} catch (HibernateException ex) {
			log.error("getRecordingsByWhereClause",ex);
		} catch (Exception ex2) {
			log.error("getRecordingsByWhereClause",ex2);
		}
		return null;
	}
	
	
	public Recording getRecordingById(Long recording_id) {
		try {
			String hql = "select c from Recording as c where c.recording_id = :recording_id AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("recording_id", recording_id);
			query.setString("deleted", "true");
			Recording rec = (Recording) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return rec;
		} catch (HibernateException ex) {
			log.error("getRecordingById",ex);
		} catch (Exception ex2) {
			log.error("getRecordingById",ex2);
		}
		return null;
	}	
	
	public void updateRecording(Recording rec){
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(rec);
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("updateRecording",ex);
		} catch (Exception ex2) {
			log.error("updateRecording",ex2);
		}
	}
	
	
}
