package org.openmeetings.app.data.user;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openmeetings.app.hibernate.beans.adresses.Adresses;
import org.openmeetings.app.hibernate.beans.adresses.States;
import org.openmeetings.app.hibernate.utils.HibernateUtil;

public class Addressmanagement {

	private static final Logger log = LoggerFactory.getLogger(Addressmanagement.class);

	private static Addressmanagement instance = null;

	public static synchronized Addressmanagement getInstance() {
		if (instance == null) {
			instance = new Addressmanagement();
		}
		return instance;
	}

	/**
	 * adds a new record to the adress table
	 * @param street
	 * @param zip
	 * @param town
	 * @param states_id
	 * @param additionalname
	 * @param comment
	 * @param fax
	 * @param phone
	 * @param email
	 * @return id of generated Adress-Object or NULL
	 */
	public Long saveAddress(String street, String zip, String town,
			long states_id, String additionalname, String comment, String fax, String phone, String email) {
		try {
			States st = Statemanagement.getInstance().getStateById(states_id);

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Adresses adr = new Adresses();
			adr.setAdditionalname(additionalname);
			adr.setComment(comment);
			adr.setStarttime(new Date());
			adr.setFax(fax);
			adr.setStreet(street);
			adr.setTown(town);
			adr.setZip(zip);
			adr.setStates(st);
			adr.setPhone(phone);
			adr.setEmail(email);

			Long id = (Long) session.save(adr);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("added id " + id);

			return id;
		} catch (HibernateException ex) {
			log.error("saveAddress",ex);
		} catch (Exception ex2) {
			log.error("saveAddress",ex2);
		}
		return null;
	}

	/**
	 * gets an adress by its id
	 * @param adresses_id
	 * @return Adress-Object or NULL
	 */
	public Adresses getAdressbyId(long adresses_id) {
		try {
			String hql = "select c from Adresses as c where c.adresses_id = :adresses_id";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("adresses_id", adresses_id);
			Adresses addr = (Adresses) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return addr;
		} catch (HibernateException ex) {
			log.error("getAdressbyId",ex);
		} catch (Exception ex2) {
			log.error("getAdressbyId",ex2);
		}
		return null;
	}
	
	/**
	 * @author o.becherer
	 * @param email
	 * @return
	 */
	public Adresses retrieveAddressByEmail(String email) throws Exception{
		log.debug("retrieveAddressByEmail : " + email);
		
		String hql = "select c from Adresses as c where c.email = :email and c.deleted = :deleted";
		Object idf = HibernateUtil.createSession();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("email", email);
		query.setString("deleted", "false");
		
		Adresses addr = (Adresses) query.uniqueResult();
		tx.commit();
		HibernateUtil.closeSession(idf);
	
		return addr;
	}
	
	/**
	 * updates an Adress-Record by its given Id
	 * @param adresses_id
	 * @param street
	 * @param zip
	 * @param town
	 * @param states_id
	 * @param additionalname
	 * @param comment
	 * @param fax
	 * @return the updated Adress-Object or null
	 */
	public Adresses updateAdress(long adresses_id, String street, String zip, String town,
			long states_id, String additionalname, String comment, String fax, String email, String phone) {
		try {
			States st = Statemanagement.getInstance().getStateById(states_id);
			
			Adresses adr = this.getAdressbyId(adresses_id);

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			adr.setAdditionalname(additionalname);
			adr.setComment(comment);
			adr.setUpdatetime(new Date());
			adr.setFax(fax);
			adr.setStreet(street);
			adr.setTown(town);
			adr.setZip(zip);
			adr.setStates(st);
			adr.setPhone(phone);
			adr.setEmail(email);

			session.update(adr);

			tx.commit();
			HibernateUtil.closeSession(idf);

			return adr;
		} catch (HibernateException ex) {
			log.error("updateAdress",ex);
		} catch (Exception ex2) {
			log.error("updateAdress",ex2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param addr
	 * @return
	 */
	public Adresses updateAdress(Adresses addr) {
		log.debug("updateAddress");
		
		try {
				
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(addr);
			
			tx.commit();
				
			HibernateUtil.closeSession(idf);

			return addr;
		} catch (HibernateException ex) {
			log.error("updateAdress",ex);
		} catch (Exception ex2) {
			log.error("updateAdress",ex2);
		}
		return null;
	}	

}
