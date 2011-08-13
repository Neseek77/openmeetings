package org.openmeetings.app.data.basic;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.openmeetings.app.data.beans.basic.SearchResult;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.persistence.beans.basic.Configuration;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.utils.mappings.CastMapToObject;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Configurationmanagement {

	private static final Logger log = Red5LoggerFactory.getLogger(
			Configurationmanagement.class,
			ScopeApplicationAdapter.webAppRootKey);

	@PersistenceContext
	private EntityManager em;

	public Configuration getConfKey(long user_level, String CONF_KEY) {
		try {
			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				Configuration configuration = null;
				Query query = em
						.createQuery("select c from Configuration as c where c.conf_key = :conf_key and c.deleted = :deleted");
				query.setParameter("conf_key", CONF_KEY);
				query.setParameter("deleted", "false");

				List<Configuration> configs = query.getResultList();

				if (configs != null && configs.size() > 0) {
					configuration = configs.get(0);
				}
				return configuration;
			} else {
				log.error("[getAllConf] Permission denied " + user_level);
			}
		} catch (Exception ex2) {
			log.error("[getConfKey]: ", ex2);
		}
		return null;
	}

	public Configuration getConfByConfigurationId(long user_level,
			long configuration_id) {
		try {
			log.debug("getConfByConfigurationId1: user_level " + user_level);
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				Configuration configuration = null;
				Query query = em
						.createQuery("select c from Configuration as c where c.configuration_id = :configuration_id");
				query.setParameter("configuration_id", configuration_id);
				query.setMaxResults(1);
				try {
					configuration = (Configuration) query.getSingleResult();
				} catch (NoResultException e) {
				}
				log.debug("getConfByConfigurationId4: " + configuration);

				if (configuration != null && configuration.getUser_id() != null) {
					configuration.setUsers(UsersDaoImpl.getInstance().getUser(
							configuration.getUser_id()));
				}
				return configuration;
			} else {
				log.error("[getConfByConfigurationId] Permission denied "
						+ user_level);
			}
		} catch (Exception ex2) {
			log.error("[getConfByConfigurationId]: ", ex2);
		}
		return null;
	}

	public SearchResult getAllConf(long user_level, int start, int max,
			String orderby, boolean asc) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				SearchResult sresult = new SearchResult();
				sresult.setRecords(this.selectMaxFromConfigurations());
				sresult.setResult(this.getConfigurations(start, max, orderby,
						asc));
				sresult.setObjectName(Configuration.class.getName());
				return sresult;
			} else {
				log.error("[getAllConf] Permission denied " + user_level);
			}
		} catch (Exception ex2) {
			log.error("[getAllConf]: ", ex2);
		}
		return null;
	}

	public List<Configuration> getConfigurations(int start, int max,
			String orderby, boolean asc) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Configuration> cq = cb
					.createQuery(Configuration.class);
			Root<Configuration> c = cq.from(Configuration.class);
			Predicate condition = cb.equal(c.get("deleted"), "false");
			cq.where(condition);
			cq.distinct(asc);
			if (asc) {
				cq.orderBy(cb.asc(c.get(orderby)));
			} else {
				cq.orderBy(cb.desc(c.get(orderby)));
			}
			TypedQuery<Configuration> q = em.createQuery(cq);
			q.setFirstResult(start);
			q.setMaxResults(max);
			List<Configuration> ll = q.getResultList();
			return ll;
		} catch (Exception ex2) {
			log.error("[getConfigurations]", ex2);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	private Long selectMaxFromConfigurations() {
		try {
			log.debug("selectMaxFromConfigurations ");
			// get all users
			Query query = em
					.createQuery("select count(c.configuration_id) from Configuration c where c.deleted = 'false'");
			List ll = query.getResultList();
			log.debug("selectMaxFromConfigurations" + ll.get(0));
			return (Long) ll.get(0);
		} catch (Exception ex2) {
			log.error("[selectMaxFromConfigurations] ", ex2);
		}
		return null;
	}

	public String addConfByKey(long user_level, String CONF_KEY,
			String CONF_VALUE, Long USER_ID, String comment) {
		String ret = "Add Configuration";
		if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
			Configuration configuration = new Configuration();
			configuration.setConf_key(CONF_KEY);
			configuration.setConf_value(CONF_VALUE);
			configuration.setStarttime(new Date());
			configuration.setDeleted("false");
			configuration.setComment(comment);
			if (USER_ID != null)
				configuration.setUser_id(USER_ID);
			try {
				configuration = em.merge(configuration);
				ret = "Erfolgreich";
			} catch (Exception ex2) {
				log.error("[addConfByKey]: ", ex2);
			}
		} else {
			ret = "Error: Permission denied";
		}
		return ret;
	}

	public Long saveOrUpdateConfiguration(long user_level,
			LinkedHashMap values, Long users_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				Configuration conf = (Configuration) CastMapToObject
						.getInstance().castByGivenObject(values,
								Configuration.class);
				if (conf.getConfiguration_id().equals(null)
						|| conf.getConfiguration_id() == 0) {
					log.info("add new Configuration");
					conf.setConfiguration_id(null);
					conf.setStarttime(new Date());
					conf.setDeleted("false");
					return this.addConfig(conf);
				} else {
					log.info("update Configuration ID: "
							+ conf.getConfiguration_id());
					Configuration conf2 = this.getConfByConfigurationId(3L,
							conf.getConfiguration_id());
					conf2.setComment(conf.getComment());
					conf2.setConf_key(conf.getConf_key());
					conf2.setConf_value(conf.getConf_value());
					conf2.setUser_id(users_id);
					conf2.setDeleted("false");
					conf2.setUpdatetime(new Date());
					return this.updateConfig(conf2);
				}
			} else {
				log.error("[saveOrUpdateConfByConfigurationId] Error: Permission denied");
				return new Long(-100);
			}
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: ", ex2);
		}
		return new Long(-1);
	}

	public Long addConfig(Configuration conf) {
		try {
			conf = em.merge(conf);
			Long configuration_id = conf.getConfiguration_id();
			return configuration_id;
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: ", ex2);
		}
		return new Long(-1);
	}

	public Long updateConfig(Configuration conf) {
		try {
			if (conf.getConfiguration_id() == null) {
				em.persist(conf);
			} else {
				if (!em.contains(conf)) {
					conf = em.merge(conf);
				}
			}
			return conf.getConfiguration_id();
		} catch (Exception ex2) {
			log.error("[updateConfByUID]: ", ex2);
		}
		return new Long(-1);
	}

	public Long deleteConfByConfiguration(long user_level,
			LinkedHashMap values, Long users_id) {
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				Configuration conf = (Configuration) CastMapToObject
						.getInstance().castByGivenObject(values,
								Configuration.class);
				conf.setUsers(UsersDaoImpl.getInstance().getUser(users_id));
				conf.setUpdatetime(new Date());
				conf.setDeleted("true");

				Configuration conf2 = this.getConfByConfigurationId(3L,
						conf.getConfiguration_id());
				conf2.setComment(conf.getComment());
				conf2.setConf_key(conf.getConf_key());
				conf2.setConf_value(conf.getConf_value());
				conf2.setUser_id(users_id);
				conf2.setDeleted("true");
				conf2.setUpdatetime(new Date());

				this.updateConfig(conf2);
				return new Long(1);
			} else {
				log.error("Error: Permission denied");
				return new Long(-100);
			}
		} catch (Exception ex2) {
			log.error("[deleteConfByUID]: ", ex2);
		}
		return new Long(-1);
	}
}
