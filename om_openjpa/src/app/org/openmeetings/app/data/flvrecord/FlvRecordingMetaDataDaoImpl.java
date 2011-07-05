package org.openmeetings.app.data.flvrecord;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openmeetings.app.hibernate.beans.flvrecord.FlvRecordingMetaData;
import org.openmeetings.app.hibernate.utils.HibernateUtil;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class FlvRecordingMetaDataDaoImpl {

	private static final Logger log = Red5LoggerFactory.getLogger(FlvRecordingMetaDataDaoImpl.class);

	private static FlvRecordingMetaDataDaoImpl instance;

	private FlvRecordingMetaDataDaoImpl() {}

	public static synchronized FlvRecordingMetaDataDaoImpl getInstance() {
		if (instance == null) {
			instance = new FlvRecordingMetaDataDaoImpl();
		}
		return instance;
	}
	
	public FlvRecordingMetaData getFlvRecordingMetaDataById(Long flvRecordingMetaDataId) {
		try { 
			
			String hql = "SELECT c FROM FlvRecordingMetaData c " +
					"WHERE c.flvRecordingMetaDataId = :flvRecordingMetaDataId";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("flvRecordingMetaDataId", flvRecordingMetaDataId);
			
			FlvRecordingMetaData flvRecordingMetaData = (FlvRecordingMetaData) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return flvRecordingMetaData;
			
		} catch (HibernateException ex) {
			log.error("[getFlvRecordingMetaDataById]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFlvRecordingMetaDataById]: ",ex2);
		}
		return null;
	}
	
	public List<FlvRecordingMetaData> getFlvRecordingMetaDataByRecording(Long flvRecordingId) {
		try { 
			
			String hql = "SELECT c FROM FlvRecordingMetaData c " +
					"WHERE c.flvRecording.flvRecordingId = :flvRecordingId " +
					"AND c.deleted != :deleted ";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("flvRecordingId", flvRecordingId);
			query.setString("deleted", "true");
			
			List<FlvRecordingMetaData> flvRecordingMetaDatas = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return flvRecordingMetaDatas;
			
		} catch (HibernateException ex) {
			log.error("[getFlvRecordingMetaDataByRecording]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFlvRecordingMetaDataByRecording]: ",ex2);
		}
		return null;
	}
	
	public List<FlvRecordingMetaData> getFlvRecordingMetaDataAudioFlvsByRecording(Long flvRecordingId) {
		try { 
			
			String hql = "SELECT c FROM FlvRecordingMetaData c " +
					"WHERE c.flvRecording.flvRecordingId = :flvRecordingId " +
					"AND (" +
						"(c.isScreenData = false) " +
							" AND " +
						"(c.isAudioOnly = true OR (c.isAudioOnly = false AND c.isVideoOnly = false))" +
					")";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("flvRecordingId", flvRecordingId);
			
			List<FlvRecordingMetaData> flvRecordingMetaDatas = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return flvRecordingMetaDatas;
			
		} catch (HibernateException ex) {
			log.error("[getFlvRecordingMetaDataAudioFlvsByRecording]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFlvRecordingMetaDataAudioFlvsByRecording]: ",ex2);
		}
		return null;
	}
	
	public FlvRecordingMetaData getFlvRecordingMetaDataScreenFlvByRecording(Long flvRecordingId) {
		try { 
			
			String hql = "SELECT c FROM FlvRecordingMetaData c " +
					"WHERE c.flvRecording.flvRecordingId = :flvRecordingId " +
					"AND c.isScreenData = true";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("flvRecordingId", flvRecordingId);
			
			List<FlvRecordingMetaData> flvRecordingMetaDatas = query.list();
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			if (flvRecordingMetaDatas.size() > 0) {
				return flvRecordingMetaDatas.get(0);
			}
			
		} catch (HibernateException ex) {
			log.error("[getFlvRecordingMetaDataScreenFlvByRecording]: ",ex);
		} catch (Exception ex2) {
			log.error("[getFlvRecordingMetaDataScreenFlvByRecording]: ",ex2);
		}
		return null;
	}
	
	public Long addFlvRecordingMetaData(Long flvRecordingId, String freeTextUserName, 
					Date recordStart, Boolean isAudioOnly, Boolean isVideoOnly, 
					Boolean isScreenData, String streamName, Integer interiewPodId) {
		try { 
			
			FlvRecordingMetaData flvRecordingMetaData = new FlvRecordingMetaData();
			
			flvRecordingMetaData.setDeleted("false");
			
			flvRecordingMetaData.setFlvRecording(FlvRecordingDaoImpl.getInstance().getFlvRecordingById(flvRecordingId));
			flvRecordingMetaData.setFreeTextUserName(freeTextUserName);
			flvRecordingMetaData.setInserted(new Date());
			
			flvRecordingMetaData.setRecordStart(recordStart);
			
			flvRecordingMetaData.setIsAudioOnly(isAudioOnly);
			flvRecordingMetaData.setIsVideoOnly(isVideoOnly);
			flvRecordingMetaData.setIsScreenData(isScreenData);
			
			flvRecordingMetaData.setStreamName(streamName);
			
			flvRecordingMetaData.setInteriewPodId(interiewPodId);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Long flvRecordingMetaDataId = (Long) session.save(flvRecordingMetaData);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return flvRecordingMetaDataId;
			
		} catch (HibernateException ex) {
			log.error("[addFlvRecordingMetaData]: ",ex);
		} catch (Exception ex2) {
			log.error("[addFlvRecordingMetaData]: ",ex2);
		}
		return null;
	}
	
	public Long addFlvRecordingMetaDataObj(FlvRecordingMetaData flvRecordingMetaData) {
		try {

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long flvRecordingMetaDataId = (Long) session.save(flvRecordingMetaData);

			tx.commit();
			HibernateUtil.closeSession(idf);

			return flvRecordingMetaDataId;

		} catch (HibernateException ex) {
			log.error("[addFlvRecordingMetaDataObj]: ", ex);
		} catch (Exception ex2) {
			log.error("[addFlvRecordingMetaDataObj]: ", ex2);
		}
		return null;
	}

	public Long updateFlvRecordingMetaDataEndDate(Long flvRecordingMetaDataId, 
										Date recordEnd) {
		try { 
			
			FlvRecordingMetaData flvRecordingMetaData = this.getFlvRecordingMetaDataById(flvRecordingMetaDataId);
			
			flvRecordingMetaData.setRecordEnd(recordEnd);
			
			log.debug("updateFlvRecordingMetaDataEndDate :: Start Date :"+flvRecordingMetaData.getRecordStart());
			log.debug("updateFlvRecordingMetaDataEndDate :: End Date :"+flvRecordingMetaData.getRecordEnd());
			
			this.updateFlvRecordingMetaData(flvRecordingMetaData);
			
			return flvRecordingMetaDataId;
			
		} catch (HibernateException ex) {
			log.error("[updateFlvRecordingMetaDataEndDate]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFlvRecordingMetaDataEndDate]: ",ex2);
		}
		return null;
	}

	public Long updateFlvRecordingMetaDataInitialGap(Long flvRecordingMetaDataId, 
										long initalGap) {
		try { 
			
			FlvRecordingMetaData flvRecordingMetaData = this.getFlvRecordingMetaDataById(flvRecordingMetaDataId);
			
			flvRecordingMetaData.setInitialGapSeconds(Long.valueOf(initalGap).intValue());
			
			this.updateFlvRecordingMetaData(flvRecordingMetaData);
			
			return flvRecordingMetaDataId;
			
		} catch (HibernateException ex) {
			log.error("[updateFlvRecordingMetaDataEndDate]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFlvRecordingMetaDataEndDate]: ",ex2);
		}
		return null;
	}

	public Long updateFlvRecordingMetaData(FlvRecordingMetaData flvRecordingMetaData) {
		try { 
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			session.update(flvRecordingMetaData);
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
		} catch (HibernateException ex) {
			log.error("[updateFlvRecordingMetaData]: ",ex);
		} catch (Exception ex2) {
			log.error("[updateFlvRecordingMetaData]: ",ex2);
		}
		return null;
	}
	
}
