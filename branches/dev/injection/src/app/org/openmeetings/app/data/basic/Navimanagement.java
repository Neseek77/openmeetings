package org.openmeetings.app.data.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.openmeetings.app.persistence.beans.basic.Naviglobal;
import org.openmeetings.app.persistence.beans.basic.Navimain;
import org.openmeetings.app.persistence.beans.basic.Navisub;
import org.openmeetings.app.persistence.utils.PersistenceSessionUtil;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Navimanagement {

	private static final Logger log = Red5LoggerFactory.getLogger(
			Navimanagement.class, ScopeApplicationAdapter.webAppRootKey);

	@Autowired
	private Fieldmanagment fieldmanagment;

	private static Navimanagement instance;

	private Navimanagement() {
	};

	public static synchronized Navimanagement getInstance() {
		if (instance == null) {
			instance = new Navimanagement();
		}
		return instance;
	}

	public List getMainMenu(long user_level, long USER_ID, long language_id) {
		List<Naviglobal> ll = this.getMainMenu(user_level, USER_ID);
		for (Iterator<Naviglobal> it2 = ll.iterator(); it2.hasNext();) {
			Naviglobal navigl = it2.next();
			navigl.setLabel(fieldmanagment.getFieldByIdAndLanguageByNavi(
					navigl.getFieldvalues_id(), language_id));
			navigl.setTooltip(fieldmanagment.getFieldByIdAndLanguageByNavi(
					navigl.getTooltip_fieldvalues_id(), language_id));
			List<Navimain> s = navigl.getMainnavi();
			for (Iterator<Navimain> it3 = s.iterator(); it3.hasNext();) {
				Navimain navim = it3.next();
				navim.setLabel(fieldmanagment.getFieldByIdAndLanguageByNavi(
						navim.getFieldvalues_id(), language_id));
				navim.setTooltip(fieldmanagment.getFieldByIdAndLanguageByNavi(
						navim.getTooltip_fieldvalues_id(), language_id));
				if (navim.getSubnavi() != null) {
					for (Iterator<Navisub> it4 = navim.getSubnavi().iterator(); it4
							.hasNext();) {
						Navisub navis = it4.next();
						navis.setLabel(fieldmanagment
								.getFieldByIdAndLanguageByNavi(
										navis.getFieldvalues_id(), language_id));
						navis.setTooltip(fieldmanagment
								.getFieldByIdAndLanguageByNavi(
										navis.getTooltip_fieldvalues_id(),
										language_id));
					}
				}

			}
		}
		return ll;
	}

	public List<Naviglobal> getMainMenu(long user_level, long USER_ID) {
		try {

			Object idf = PersistenceSessionUtil.createSession();
			EntityManager session = PersistenceSessionUtil.getSession();
			EntityTransaction tx = session.getTransaction();
			tx.begin();
			// CriteriaBuilder crit = session.getCriteriaBuilder();
			Query query = session.createQuery("select c from Naviglobal as c "
					+ "where c.level_id <= :level_id AND "
					+ "c.deleted LIKE 'false' " + "order by c.naviorder");
			query.setParameter("level_id", user_level);
			List<Naviglobal> navi = query.getResultList();

			tx.commit();

			log.debug("getMainMenu " + navi.size());

			PersistenceSessionUtil.closeSession(idf);

			return navi;
		} catch (Exception ex2) {
			log.error("getMainMenu", ex2);
		}
		return null;
	}

	public void addGlobalStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name, String deleted, Long tooltip_fieldvalues_id) {
		try {
			Naviglobal ng = new Naviglobal();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setNaviorder(naviorder);
			ng.setFieldvalues_id(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setIsopen(isopen);
			ng.setDeleted(deleted);
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setStarttime(new Date());
			ng.setTooltip_fieldvalues_id(tooltip_fieldvalues_id);

			Object idf = PersistenceSessionUtil.createSession();
			EntityManager session = PersistenceSessionUtil.getSession();
			EntityTransaction tx = session.getTransaction();
			tx.begin();
			// CriteriaBuilder crit = session.getCriteriaBuilder();

			session.merge(ng);

			tx.commit();
			PersistenceSessionUtil.closeSession(idf);

		} catch (Exception ex2) {
			log.error("addGlobalStructure", ex2);
		}
	}

	public void addMainStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name, long global_id, String deleted) {
		try {
			Navimain ng = new Navimain();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setFieldvalues_id(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setNaviorder(naviorder);
			ng.setIsopen(isopen);
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setDeleted(deleted);
			ng.setGlobal_id(global_id);
			ng.setStarttime(new Date());

			Object idf = PersistenceSessionUtil.createSession();
			EntityManager session = PersistenceSessionUtil.getSession();
			EntityTransaction tx = session.getTransaction();
			tx.begin();
			// CriteriaBuilder crit = session.getCriteriaBuilder();

			session.merge(ng);

			tx.commit();
			PersistenceSessionUtil.closeSession(idf);

		} catch (Exception ex2) {
			log.error("addMainStructure", ex2);
		}
	}

	public void addSubStructure(String action, int naviorder,
			long fieldvalues_id, boolean isleaf, boolean isopen, long level_id,
			String name, long main_id) {
		try {
			Navisub ng = new Navisub();
			ng.setAction(action);
			ng.setComment("");
			ng.setIcon("");
			ng.setNaviorder(naviorder);
			ng.setFieldvalues_id(fieldvalues_id);
			ng.setIsleaf(isleaf);
			ng.setIsopen(isopen);
			ng.setLevel_id(level_id);
			ng.setName(name);
			ng.setDeleted("false");
			ng.setMain_id(main_id);
			ng.setStarttime(new Date());

			Object idf = PersistenceSessionUtil.createSession();
			EntityManager session = PersistenceSessionUtil.getSession();
			EntityTransaction tx = session.getTransaction();
			tx.begin();
			// CriteriaBuilder crit = session.getCriteriaBuilder();

			session.merge(ng);

			tx.commit();
			PersistenceSessionUtil.closeSession(idf);

		} catch (Exception ex2) {
			log.error("addSubStructure", ex2);
		}
	}
}
