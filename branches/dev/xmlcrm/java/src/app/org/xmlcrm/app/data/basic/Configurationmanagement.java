package org.xmlcrm.app.data.basic;

import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.xmlcrm.app.data.basic.AuthLevelmanagement;
import org.xmlcrm.app.data.user.Usermanagement;
import org.xmlcrm.app.data.beans.basic.SearchResult;
import org.xmlcrm.app.data.user.Usermanagement;
import org.xmlcrm.app.hibernate.beans.basic.Configuration;
import org.xmlcrm.app.hibernate.utils.HibernateUtil;
import org.xmlcrm.utils.math.Calender;
import org.xmlcrm.utils.mappings.CastHashMapToObject;

public class Configurationmanagement {

	private static final Log log = LogFactory.getLog(Configurationmanagement.class);

	private Configurationmanagement() {
	}

	private static Configurationmanagement instance = null;

	public static synchronized Configurationmanagement getInstance() {
		if (instance == null) {
			instance = new Configurationmanagement();
		}

		return instance;
	}

	public Configuration getConfKey(long USER_LEVEL, String CONF_KEY) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
				Configuration configuration = null;
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Configuration as c where c.conf_key = :conf_key");
				query.setString("conf_key", CONF_KEY);
				for (Iterator it = query.iterate(); it.hasNext();) {
					configuration = (Configuration) it.next();
				}
				tx.commit();
				HibernateUtil.closeSession(idf);
				return configuration;
			} else {
				log.error("[getAllConf] Permission denied "+USER_LEVEL);
			}
		} catch (HibernateException ex) {
			log.error("[getConfKey]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getConfKey]: " ,ex2);
		}		
		return null;
	}
	
	public Configuration getConfByConfigurationId(long USER_LEVEL, long configuration_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
				Configuration configuration = null;
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Configuration as c where c.configuration_id = :configuration_id");
				query.setLong("configuration_id", configuration_id);
				for (Iterator it = query.iterate(); it.hasNext();) {
					configuration = (Configuration) it.next();
				}
				tx.commit();
				HibernateUtil.closeSession(idf);
				return configuration;
			} else {
				log.error("[getConfByConfigurationId] Permission denied "+USER_LEVEL);
			}
		} catch (HibernateException ex) {
			log.error("[getConfByConfigurationId]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getConfByConfigurationId]: " ,ex2);
		}		
		return null;
	}

	public SearchResult getAllConf(long USER_LEVEL, int start ,int max, String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
				SearchResult sresult = new SearchResult();
				sresult.setRecords(this.selectMaxFromConfigurations());
				sresult.setResult(this.getConfigurations(start, max, orderby, asc));
				sresult.setObjectName(Configuration.class.getName());
				return sresult;
			} else {
				log.error("[getAllConf] Permission denied "+USER_LEVEL);
			}
		} catch (HibernateException ex) {
			log.error("[getAllConf]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getAllConf]: " ,ex2);
		}		
		return null;
	}
	
	public List getConfigurations(int start, int max, String orderby, boolean asc) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Configuration.class);
			crit.add(Restrictions.eq("deleted", "false"));
			crit.setFirstResult(start);
			crit.setMaxResults(max);
			if (asc) crit.addOrder(Order.asc(orderby));
			else crit.addOrder(Order.desc(orderby));
			List ll = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);		
			return ll;
		} catch (HibernateException ex) {
			log.error("[getConfigurations]" ,ex);
		} catch (Exception ex2) {
			log.error("[getConfigurations]" ,ex2);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private Long selectMaxFromConfigurations(){
		try {
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select max(c.configuration_id) from Configuration c where c.deleted = 'false'"); 
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[selectMaxFromConfigurations] ",ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromConfigurations] ",ex2);
		}
		return null;
	}		

	public String addConfByKey(long USER_LEVEL, String CONF_KEY,
			String CONF_VALUE, Long USER_ID, String comment) {
		String ret = "Add Configuration";
		if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
			Configuration configuration = new Configuration();
			configuration.setConf_key(CONF_KEY);
			configuration.setConf_value(CONF_VALUE);
			configuration.setStarttime(new Date());
			configuration.setDeleted("false");
			configuration.setComment(comment);
			if (USER_ID!=null) configuration.setUsers(Usermanagement.getInstance().getUser(new Long(USER_ID)));

			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.save(configuration);
				session.flush();
				session.clear();
				session.refresh(configuration);
				tx.commit();
				HibernateUtil.closeSession(idf);
				ret = "Erfolgreich";
			} catch (HibernateException ex) {
				log.error("[addConfByKey]: " ,ex);
			} catch (Exception ex2) {
				log.error("[addConfByKey]: " ,ex2);
			}
		} else {
			ret = "Error: Permission denied";
		}
		return ret;
	}
	
	public Long saveOrUpdateConfiguration(long USER_LEVEL, LinkedHashMap values, Long users_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
				Configuration conf = (Configuration) CastHashMapToObject.getInstance().castByGivenObject(values, Configuration.class);
				if (conf.getConfiguration_id().equals(null) || conf.getConfiguration_id() == 0 ){
					log.info("add new Configuration");
					conf.setStarttime(new Date());
					conf.setDeleted("false");
					return this.addConfig(conf);
				} else {
					log.info("update Configuration ID: "+conf.getConfiguration_id());
					conf.setUsers(Usermanagement.getInstance().getUser(users_id));
					conf.setDeleted("false");
					conf.setUpdatetime(new Date());
					return this.updateConfig(conf);
				}
			} else {
				log.error("[saveOrUpdateConfByConfigurationId] Error: Permission denied");
				return new Long(-100);
			}				
		} catch (HibernateException ex) {
			log.error("[updateConfByUID]: " ,ex);
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: " ,ex2);
		}
		return new Long(-1);
	}

	public Long addConfig(Configuration conf){
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long configuration_id = (Long) session.save(conf);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return configuration_id;
		} catch (HibernateException ex) {
			log.error("[updateConfByUID]: " ,ex);
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: " ,ex2);
		}
		return new Long(-1);		
	}
	
	public Long updateConfig(Configuration conf){
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(conf);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return conf.getConfiguration_id();
		} catch (HibernateException ex) {
			log.error("[updateConfByUID]: " ,ex);
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: " ,ex2);
		}
		return new Long(-1);		
	}	

	public Long deleteConfByConfiguration(long USER_LEVEL, LinkedHashMap values, Long users_id) {
		try {	
			if (AuthLevelmanagement.getInstance().checkAdminLevel(USER_LEVEL)) {
				Configuration conf = (Configuration) CastHashMapToObject.getInstance().castByGivenObject(values, Configuration.class);
				conf.setUsers(Usermanagement.getInstance().getUser(users_id));
				conf.setUpdatetime(new Date());
				conf.setDeleted("true");
				this.updateConfig(conf);
				return new Long(1);
			} else {
				log.error("Error: Permission denied");
				return new Long(-100);
			}
		} catch (HibernateException ex) {
			log.error("[deleteConfByUID]: " ,ex);
		} catch (Exception ex2) {
			log.error("[deleteConfByUID]: " ,ex2);
		}		
		return new Long(-1);
	}
}
