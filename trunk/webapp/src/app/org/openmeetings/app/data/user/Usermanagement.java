package org.openmeetings.app.data.user;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import org.openmeetings.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.openmeetings.app.hibernate.beans.user.*;
import org.openmeetings.app.hibernate.beans.adresses.Adresses_Emails;
import org.openmeetings.app.hibernate.beans.adresses.Emails;
import org.openmeetings.app.hibernate.utils.HibernateUtil;
import org.openmeetings.app.templates.ResetPasswordTemplate;
import org.openmeetings.app.conference.videobeans.RoomClient;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.basic.Fieldmanagment;
import org.openmeetings.app.data.beans.basic.SearchResult;
import org.openmeetings.app.data.user.Organisationmanagement;
import org.openmeetings.utils.mappings.CastMapToObject;
import org.openmeetings.utils.math.*;
import org.openmeetings.utils.mail.MailHandler;
import org.openmeetings.utils.crypt.*;

import org.red5.io.utils.ObjectMap;

import org.openmeetings.app.data.basic.*;

/**
 * 
 * @author swagner
 *
 */
public class Usermanagement {

	private static final Logger log = LoggerFactory.getLogger(Usermanagement.class);

	private static Usermanagement instance = null;

	private Usermanagement() {
	}

	public static synchronized Usermanagement getInstance() {
		if (instance == null) {
			instance = new Usermanagement();
		}
		return instance;
	}
	
	/**
	 * query for a list of users
	 * @param users_id
	 * @param user_level
	 * @param start
	 * @param max
	 * @param orderby
	 * @return
	 */
	public SearchResult getUsersList(long user_level, int start, int max, String orderby, boolean asc){
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)){
				SearchResult sresult = new SearchResult();
				sresult.setObjectName(Users.class.getName());
				sresult.setRecords(this.selectMaxFromUsers());
				//get all users
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Criteria crit = session.createCriteria(Users.class);
				crit.add(Restrictions.eq("deleted", "false"));
				if (asc) crit.addOrder(Order.asc(orderby));
				else crit.addOrder(Order.desc(orderby));
				crit.setMaxResults(max);
				crit.setFirstResult(start);
				sresult.setResult(crit.list());
				tx.commit();
				HibernateUtil.closeSession(idf);
				return sresult;				
			}
		} catch (HibernateException ex) {
			log.error("[getUsersList] "+ex);
		} catch (Exception ex2) {
			log.error("[getUsersList] "+ex2);
		}
		return null;
	}
	
	public List<Users> getAllUsers(){
		try {
			
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Users.class);
			crit.add(Restrictions.eq("deleted", "false"));

			List<Users> ll = crit.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return ll;				

		} catch (HibernateException ex) {
			log.error("[getAllUsers] "+ex);
		} catch (Exception ex2) {
			log.error("[getAllUsers] "+ex2);
		}
		return null;
	}	
	
	/**
	 * returns the maximum
	 * @return
	 */
	public Long selectMaxFromUsers(){
		try {
			//get all users
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select max(c.user_id) from Users c where c.deleted = 'false'"); 
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.info("selectMaxFromUsers",(Long)ll.get(0));
			return (Long)ll.get(0);				
		} catch (HibernateException ex) {
			log.error("[selectMaxFromUsers] "+ex);
		} catch (Exception ex2) {
			log.error("[selectMaxFromUsers] "+ex2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param user_level
	 * @param user_id
	 * @return
	 */
	public Users checkAdmingetUserById(long user_level, long user_id){
		if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
			return this.getUser(user_id);
		}
		return null;
	}
	
	public List getUserByMod(Long user_level, long user_id){
		return null;
	}

	/**
	 * 
	 * @param user_id
	 * @return
	 */
	public Users getUser(Long user_id) {
		if (user_id > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Users as c where c.user_id = :user_id");
				query.setLong("user_id", user_id);
				Users users = (Users) query.uniqueResult();
				tx.commit();
				HibernateUtil.closeSession(idf);
				return users;
				// TODO: Add Usergroups to user
				// users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
			} catch (HibernateException ex) {
				log.error("getUser",ex);
			} catch (Exception ex2) {
				log.error("getUser",ex2);
			}
		} else {
			log.error("[getUser] "+"Error: No USER_ID given");
		}
		return null;
	}
	
	public void updateUser(Users user) {
		if (user.getUser_id() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error("[updateUser] ",ex);
			} catch (Exception ex2) {
				log.error("[updateUser] ",ex2);
			}
		} else {
			log.error("[updateUser] "+"Error: No USER_ID given");
		}
	}

	/**
	 * login logic
	 * @param SID
	 * @param Username
	 * @param Userpass
	 * @return
	 */
	public Object loginUser(String SID, String username, String userpass, RoomClient currentClient) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Users.class);
			crit.add(Restrictions.eq("login", username));
			crit.add(Restrictions.eq("deleted", "false"));
			crit.add(Restrictions.eq("status", 1));
			List ll = crit.list();
			log.info("debug loginUser: " + username);
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.info("debug SIZE: " + ll.size());
			
			if (ll.size()==0) {
				return new Long(-10);
			} else {
				Users users = (Users) ll.get(0);
				if (ManageCryptStyle.getInstance().getInstanceOfCrypt().verifyPassword(userpass, users.getPassword())) {
					log.info("chsum OK: "+ users.getUser_id());
					Boolean bool = Sessionmanagement.getInstance().updateUser(SID, users.getUser_id());
					if (bool==null){
						//Exception
						return new Long(-1);
					} else if (!bool) {
						//invalid Session-Object
						return new Long(-35);
					}
					users.setUserlevel(getUserLevel(users.getLevel_id()));		
					updateLastLogin(users);
					//If invoked via SOAP this is NULL
					if (currentClient!=null){
						currentClient.setUser_id(users.getUser_id());
					}
					return users;
				} else {
					return new Long(-11);
				}
			}
		
		} catch (HibernateException ex) {
			log.error("[loginUser]: ",ex);
		} catch (Exception ex2) {
			log.error("[loginUser]: ",ex2);
		}
		return new Long(-1);
	}

	public Long logout(String SID, long USER_ID) {
		Sessionmanagement.getInstance().updateUser(SID, 0);
		return new Long(-12);
	}

	private void updateLastLogin(Users us) {
		try {
			us.setLastlogin(new Date());
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(us);
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("updateLastLogin",ex);
		} catch (Exception ex2) {
			log.error("updateLastLogin",ex2);
		}
	}

	/**
	 * suche eines Bentzers
	 * @param user_level
	 * @param searchstring
	 * @param max
	 * @param start
	 * @return
	 */
	public List searchUser(long user_level, String searchcriteria, String searchstring, int max, int start, String orderby, boolean asc) {
		if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Criteria crit = session.createCriteria(Users.class);
				crit.add(Restrictions.ilike(searchcriteria, "%" + searchstring + "%"));
				if (asc) crit.addOrder(Order.asc(orderby));
				else crit.addOrder(Order.desc(orderby));
				crit.add(Restrictions.ne("deleted", "true"));
				crit.setMaxResults(max);
				crit.setFirstResult(start);
				List contactsZ = crit.list();
				tx.commit();
				HibernateUtil.closeSession(idf);
				return contactsZ;
			} catch (HibernateException ex) {
				log.error("searchUser",ex);
			} catch (Exception ex2) {
				log.error("searchUser",ex2);
			}
		}
		return null;
	}

	public List getUserdataDashBoard(Long user_id) {
		if (user_id.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND deleted != :deleted");
				query.setLong("user_id", user_id.longValue());
				query.setString("deleted", "true");
				List ll = query.list();
				tx.commit();
				HibernateUtil.closeSession(idf);
				return ll;
			} catch (HibernateException ex) {
				log.error("getUserdataDashBoard",ex);
			} catch (Exception ex2) {
				log.error("getUserdataDashBoard",ex2);
			}
		}
		return null;
	}

	private int getUserdataNoByKey(Long USER_ID, String DATA_KEY) {
		int userdata = 0;
		if (USER_ID.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key AND deleted != :deleted");
				query.setLong("user_id", USER_ID.longValue());
				query.setString("data_key", DATA_KEY);
				query.setString("deleted", "true");
				userdata = query.list().size();
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error("getUserdataNoByKey",ex);
			} catch (Exception ex2) {
				log.error("getUserdataNoByKey",ex2);
			}
		} else {
			System.out.println("Error: No USER_ID given");
		}
		return userdata;
	}

	public Userdata getUserdataByKey(Long user_id, String DATA_KEY) {
		Userdata userdata = new Userdata();
		if (user_id.longValue() > 0) {
			try {
				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key AND deleted != :deleted");
				query.setLong("user_id", user_id.longValue());
				query.setString("data_key", DATA_KEY);
				query.setString("deleted", "true");
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					userdata = (Userdata) it2.next();
				}
				tx.commit();
				HibernateUtil.closeSession(idf);
			} catch (HibernateException ex) {
				log.error("getUserdataByKey",ex);
			} catch (Exception ex2) {
				log.error("getUserdataByKey",ex2);
			}
		} else {
			userdata.setComment("Error: No USER_ID given");
		}
		return userdata;
	}

	public Long updateUser(long user_level, Long user_id, Long level_id,
			String login, String password, String lastname, String firstname,
			Date age, String street, String additionalname, String zip, long states_id, String town,
			int availible, String telefon, String fax,
			String mobil, String email, String comment, int status, LinkedHashMap organisations,
			int title_id) {

		if (AuthLevelmanagement.getInstance().checkUserLevel(user_level) && user_id != 0) {
			try {
				Users us = this.getUser(user_id);
				// Check for duplicates
				boolean checkName = true;
				if (!login.equals(us.getLogin())){
					checkName = this.checkUserLogin(login);
				}
				boolean checkEmail = true;
				Adresses_Emails mail = null;
//				log.error("mail 1 update User: "+us.getAdresses().getAdresses_id());
//				log.error("mail 2 update User: "+us.getAdresses().getEmails().size());
				Iterator it = us.getAdresses().getEmails().iterator();
//				log.error("mail 3 update User: "+it);
				if (it.hasNext()){
//					log.error("mail 4 update User: has next");
					mail = (Adresses_Emails) it.next();
//					log.error("mail 5 update User naxt"+mail);
				}				
//				log.error("updateUser mail: "+mail);
//				log.error("updateUser email: "+email);
				if (!email.equals(mail.getMail().getEmail())){
					checkEmail = Emailmanagement.getInstance().checkUserEMail(email);
				}
				if (checkName && checkEmail) {
//					log.info("user_id " + user_id);
					
					us.setLastname(lastname);
					us.setFirstname(firstname);
					us.setAge(age);
					us.setLogin(login);
					us.setUpdatetime(new Date());
					us.setAvailible(availible);
					us.setStatus(status);
					us.setTitle_id(title_id);
					
					if (level_id != 0)
						us.setLevel_id(new Long(level_id));
					if (password.length() != 0) {
						if (password.length()>=4){
							us.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(password));
						} else {
							return new Long(-7);
						}
					}					
					
					//Todo implement Phone
					Addressmanagement.getInstance().updateAdress(us.getAdresses().getAdresses_id(), street, zip, town, states_id, additionalname, comment, fax);
					Emailmanagement.getInstance().updateUserEmail(mail.getMail().getMail_id(),user_id, email);
					
					//add or delete organisations from this user
					if (organisations!=null){
						Organisationmanagement.getInstance().updateUserOrganisationsByUser(us, organisations);
					}
					
//					log.info("USER " + us.getLastname());
					Object idf = HibernateUtil.createSession();
					Session session = HibernateUtil.getSession();
					Transaction tx = session.beginTransaction();

					session.update(us);
					
					tx.commit();
					HibernateUtil.closeSession(idf);
					
					return us.getUser_id();
					
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} catch (HibernateException ex) {
				log.error("[updateUser]",ex);
			} catch (Exception ex2) {
				log.error("[updateUser]",ex2);
			}
		} else {
			log.error("Error: Permission denied");
			return new Long(-1);
		}
		return new Long(-1);
	}

	public String updateUserdata(int DATA_ID, long USER_ID, String DATA_KEY,
			String DATA, String Comment) {
		String res = "Fehler beim Update";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "update userdata set DATA_KEY= :DATA_KEY, USER_ID = :USER_ID, DATA = :DATA, updatetime = :updatetime, comment = :Comment where DATA_ID= :DATA_ID";
			int updatedEntities = session.createQuery(hqlUpdate).setString(
					"DATA_KEY", DATA_KEY).setLong("USER_ID", USER_ID)
					.setString("DATA", DATA).setLong("updatetime",new Long(-1))
					.setString("Comment", Comment).setInteger("DATA_ID",
							DATA_ID).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("updateUserdata",ex);
		} catch (Exception ex2) {
			log.error("updateUserdata",ex2);
		}
		return res;
	}

	public String updateUserdataByKey(Long USER_ID, String DATA_KEY,
			String DATA, String Comment) {
		String res = "Fehler beim Update";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "update Userdata set data = :data, updatetime = :updatetime, "
					+ "comment = :comment where user_id= :user_id AND data_key = :data_key";
			int updatedEntities = session.createQuery(hqlUpdate).setString(
					"data", DATA).setLong("updatetime",
					new Long(-1)).setString(
					"comment", Comment).setLong("user_id", USER_ID.longValue())
					.setString("data_key", DATA_KEY).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("updateUserdataByKey",ex);
		} catch (Exception ex2) {
			log.error("updateUserdataByKey",ex2);
		}
		return res;
	}

	public String deleteUserdata(int DATA_ID) {
		String res = "Fehler beim deleteUserdata";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "delete userdata where DATA_ID= :DATA_ID";
			int updatedEntities = session.createQuery(hqlUpdate).setInteger("DATA_ID", DATA_ID).executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("deleteUserdata",ex);
		} catch (Exception ex2) {
			log.error("deleteUserdata",ex2);
		}
		return res;
	}

	public String deleteUserdataByUserAndKey(int users_id, String DATA_KEY) {
		String res = "Fehler beim deleteUserdataByUserAndKey";
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			String hqlUpdate = "delete userdata where users_id= :users_id AND DATA_KEY = :DATA_KEY";
			int updatedEntities = session.createQuery(hqlUpdate).setInteger(
					"users_id", users_id).setString("DATA_KEY", DATA_KEY)
					.executeUpdate();
			res = "Success" + updatedEntities;
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("deleteUserdataByUserAndKey",ex);
		} catch (Exception ex2) {
			log.error("deleteUserdataByUserAndKey",ex2);
		}
		return res;
	}

	public String addUserdata(long USER_ID, String DATA_KEY, String DATA,
			String Comment) {
		String ret = "Fehler beim speichern der Userdata";
		Userdata userdata = new Userdata();
		userdata.setData_key(DATA_KEY);
		userdata.setData(DATA);
		userdata.setStarttime(new Date());
		userdata.setUpdatetime(null);
		userdata.setComment(Comment);
		userdata.setUser_id(new Long(USER_ID));
		userdata.setDeleted("false");
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(userdata);
			session.flush();
			session.clear();
			session.refresh(userdata);
			tx.commit();
			HibernateUtil.closeSession(idf);
			ret = "success";
		} catch (HibernateException ex) {
			log.error("addUserdata",ex);
		} catch (Exception ex2) {
			log.error("addUserdata",ex2);
		}
		return ret;
	}

	public Long deleteUserID(long USER_ID) {
		try {
			if (USER_ID != 0) {
				Users us = this.getUser(USER_ID);
				us.setDeleted("true");
				us.setUpdatetime(new Date());
				// result +=
				// Groupmanagement.getInstance().deleteUserFromAllGroups(new
				// Long(USER_ID));

				Object idf = HibernateUtil.createSession();
				Session session = HibernateUtil.getSession();
				Transaction tx = session.beginTransaction();
				session.update(us);
				tx.commit();
				HibernateUtil.closeSession(idf);
				return us.getUser_id();
				// result +=
				// ResHandler.getBestellmanagement().deleteWarenkorbByUserID(USER_ID);
				// result +=
				// ResHandler.getEmailmanagement().deleteEMailByUserID(USER_ID);
				// result +=
				// ResHandler.getContactmanagement().deleteContactUsergroups(USER_ID);
				// result +=
				// ResHandler.getContactmanagement().deleteUserContact(USER_ID);

			}
		} catch (HibernateException ex) {
			log.error("[deleteUserID]" ,ex);
		} catch (Exception ex2) {
			log.error("[deleteUserID]" ,ex2);
		}
		return null;
	}

	private Userlevel getUserLevel(Long level_id) {
		Userlevel userlevel = new Userlevel();
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select c from Userlevel as c where c.level_id = :level_id AND deleted != :deleted");
			query.setLong("level_id", level_id.longValue());
			query.setString("deleted", "true");
			for (Iterator it2 = query.iterate(); it2.hasNext();) {
				userlevel = (Userlevel) it2.next();
			}
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("[getUserLevel]" ,ex);
		} catch (Exception ex2) {
			log.error("[getUserLevel]" ,ex2);
		}
		return userlevel;
	}

	/**
	 * get user-role
	 * 1 - user
	 * 2 - moderator
	 * 3 - admin
	 * @param user_id
	 * @return
	 */
	public Long getUserLevelByID(Long user_id) {
		
		try {
			if (user_id==null) return new Long(0);
			//For direct access of linked users
			if (user_id==-1){
				return new Long(1);
			}
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Query query = session.createQuery("select c from Users as c where c.user_id = :user_id AND deleted <> 'true'");
			query.setLong("user_id", user_id);
			Users us = (Users) query.uniqueResult();
			
			tx.commit();
			HibernateUtil.closeSession(idf);
			
			if (us!=null){
				return us.getLevel_id();
			}
		} catch (HibernateException ex) {
			log.error("[getUserLevelByID]" ,ex);
		} catch (Exception ex2) {
			log.error("[getUserLevelByID]" ,ex2);
		}
		return null;
	}

	/**
	 * Method to register a new User, User will automatically be added to the
	 * default user_level(1) new users will be automatically added to the
	 * Organisation with the id specified in the configuration value
	 * default_domain_id
	 * 
	 * @param user_level
	 * @param level_id
	 * @param availible
	 * @param status
	 * @param login
	 * @param Userpass
	 * @param lastname
	 * @param firstname
	 * @param email
	 * @param age
	 * @param street
	 * @param additionalname
	 * @param fax
	 * @param zip
	 * @param states_id
	 * @param town
	 * @param language_id
	 * @return
	 */
	public Long registerUser(String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id) {
		try {
			// Checks if FrontEndUsers can register
			if (Configurationmanagement.getInstance().getConfKey(3,"allow_frontend_register").getConf_value().equals("1")) {
				// TODO: add availible params sothat users have to verify their
				// login-data
				// TODO: add status from Configuration items
				Long user_id = this.registerUserInit(3, 1, 0, 1, login, Userpass,lastname, firstname, email, age, street, additionalname,fax, zip, states_id, town, language_id, true, new LinkedHashMap());
				// Get the default organisation_id of registered users
				if (user_id>0){
					long organisation_id = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3,"default_domain_id").getConf_value()).longValue();
					Organisationmanagement.getInstance().addUserToOrganisation(new Long(3), user_id,organisation_id, user_id, "");
				}
				return user_id;
			}
		} catch (Exception e) {
			log.error("[registerUser]",e);
		}
		return null;
	}

	/**
	 * Adds a user including his adress-data,auth-date,mail-data
	 * 
	 * @param user_level
	 * @param level_id
	 * @param availible
	 * @param status
	 * @param login
	 * @param Userpass
	 * @param lastname
	 * @param firstname
	 * @param email
	 * @param age
	 * @param street
	 * @param additionalname
	 * @param fax
	 * @param zip
	 * @param states_id
	 * @param town
	 * @param language_id
	 * @return new users_id OR null if an exception, -1 if an error, -4 if mail
	 *         already taken, -5 if username already taken, -3 if login or pass
	 *         or mail is empty
	 */
	public Long registerUserInit(long user_level, long level_id, int availible,
			int status, String login, String Userpass, String lastname,
			String firstname, String email, Date age, String street,
			String additionalname, String fax, String zip, long states_id,
			String town, long language_id, boolean sendWelcomeMessage, LinkedHashMap organisations) throws Exception {
		//TODO: make phone number persistent
		// User Level must be at least Admin
		// Moderators will get a temp update of there UserLevel to add Users to
		// their Group
		if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
			// Check for required data
			if (login.length()>=4 && Userpass.length()>=4) {
				// Check for duplicates
				boolean checkName = this.checkUserLogin(login);
				boolean checkEmail = Emailmanagement.getInstance().checkUserEMail(email);
				if (checkName && checkEmail) {
					
					if (sendWelcomeMessage && email.length()!=0) {
						String sendMail = Emailmanagement.getInstance().sendMail(login, Userpass, email);
						if (!sendMail.equals("success")) return new Long(-19);
					}						
					Long address_id = Addressmanagement.getInstance().saveAddress(street, zip, town, states_id, additionalname, "",fax);
					if (address_id==null) {
						return new Long(-22);
					}
					Long user_id = this.addUser(level_id, availible, status,firstname, login, lastname, language_id, Userpass,address_id, age);
					if (user_id==null) {
						return new Long(-111);
					}
					Long adress_emails_id = Emailmanagement.getInstance().registerEmail(email, address_id,"");
					if (adress_emails_id==null) {
						return new Long(-112);
					}					
					Organisationmanagement.getInstance().addUserOrganisationsByHashMap(user_id, organisations);
					
					if (address_id > 0 && user_id > 0 && adress_emails_id > 0) {
						return user_id;
					} else {
						return new Long(-16);
					}
				} else {
					if (!checkName) {
						return new Long(-15);
					} else if (!checkEmail) {
						return new Long(-17);
					}
				}
			} else {
				return new Long(-13);
			}
		}
		return new Long(-1);
	}

	/**
	 * @author swagner This Methdo adds a User to the User-Table
	 * @param level_id
	 *            The User Level, 1=User, 2=GroupAdmin/Moderator,
	 *            3=SystemAdmin/Admin
	 * @param availible
	 *            The user is activated
	 * @param status
	 *            The user is not blocked by System admins
	 * @param firstname
	 * @param login
	 *            Username for login
	 * @param lastname
	 * @param language_id
	 * @param Userpass
	 *            is MD5-crypted
	 * @param adress_id
	 * @return user_id or error null
	 */
	public Long addUser(long level_id, int availible, int status,
			String firstname, String login, String lastname, long language_id,
			String userpass, long adress_id, Date age) {
		try {
			Users users = new Users();
			users.setFirstname(firstname);
			users.setLogin(login);
			users.setLastname(lastname);
			users.setAge(age);
			users.setAdresses(Addressmanagement.getInstance().getAdressbyId(adress_id));
			users.setAvailible(availible);
			users.setLastlogin(new Date());
			users.setLasttrans(new Long(0));
			users.setLevel_id(level_id);
			users.setStatus(status);
			users.setTitle_id(new Integer(1));
			users.setStarttime(new Date());
			// this is needed cause the language is not a needed data at
			// registering
			if (language_id != 0) {
				users.setLanguage_id(new Long(language_id));
			} else {
				users.setLanguage_id(null);
			}
			users.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(userpass));
			users.setRegdate(new Date());
			users.setDeleted("false");

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			long user_id = (Long) session.save(users);
			tx.commit();
			HibernateUtil.closeSession(idf);

			return user_id;

		} catch (HibernateException ex) {
			log.error("[registerUser]" ,ex);
		} catch (Exception ex2) {
			log.error("[registerUser]" ,ex2);
		}
		return null;
	}

	public Long addUser(Users usr) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Long user_id = (Long) session.save(usr);
			tx.commit();
			HibernateUtil.closeSession(idf);
			return user_id;
		} catch (HibernateException ex) {
			log.error("[registerUser]" ,ex);
		} catch (Exception ex2) {
			log.error("[registerUser]" ,ex2);
		}
		return null;
	}
	
	/**
	 * check for duplicates
	 * @param DataValue
	 * @return
	 */
	public boolean checkUserLogin(String DataValue) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select c from Users as c where c.login = :DataValue AND deleted != :deleted");
			query.setString("DataValue", DataValue);
			query.setString("deleted", "true");
			int count = query.list().size();

			tx.commit();
			HibernateUtil.closeSession(idf);
			if (count != 0) {
				return false;
			}			
		} catch (HibernateException ex) {
			log.error("[checkUserData]" ,ex);
		} catch (Exception ex2) {
			log.error("[checkUserData]" ,ex2);
		}
		return true;
	}

	public void addUserLevel(String description, int myStatus) {
		try {
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Userlevel uslevel = new Userlevel();
			uslevel.setStarttime(new Date());
			uslevel.setDescription(description);
			uslevel.setStatuscode(new Integer(myStatus));
			uslevel.setDeleted("false");
			session.save(uslevel);
			tx.commit();
			HibernateUtil.closeSession(idf);
		} catch (HibernateException ex) {
			log.error("[addUserLevel]" ,ex);
		} catch (Exception ex2) {
			log.error("[addUserLevel]" ,ex2);
		}
	}
	
	
	
	/**
	 * Update User by Object
	 * @param user_level
	 * @param values
	 * @param users_id
	 * @return
	 */
	
	
	public Long saveOrUpdateUser(Long user_level,ObjectMap values, Long users_id){
		try {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				Long returnLong = null;
				Users user = (Users) CastMapToObject.getInstance().castByGivenObject(values, Users.class);
	
				if (user.getUser_id() != null && user.getUser_id()>0) {					
					
					returnLong = user.getUser_id();
					Users savedUser = this.getUser(user.getUser_id());
					savedUser.setAge(user.getAge());
					savedUser.setFirstname(user.getFirstname());
					savedUser.setLastname(user.getLastname());
					savedUser.setTitle_id(user.getTitle_id());
					if (user.getPassword().length()>3){
						savedUser.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(user.getPassword()));
					}
					
					
					String email = values.get("email").toString();
					
					Adresses_Emails mail = null;
					Iterator it = savedUser.getAdresses().getEmails().iterator();
					if (it.hasNext()){
						mail = (Adresses_Emails) it.next();
					}	
					
					if (!email.equals(mail.getMail().getEmail())){
						boolean checkEmail = Emailmanagement.getInstance().checkUserEMail(email);
						if (checkEmail) {
							Emailmanagement.getInstance().updateUserEmail(mail.getMail().getMail_id(),savedUser.getUser_id(), email);
						} else {
							returnLong = new Long(-11);
						}
					}					
					
					Addressmanagement.getInstance().updateAdress(user.getAdresses());
					savedUser.setAdresses(Addressmanagement.getInstance().getAdressbyId(user.getAdresses().getAdresses_id()));
					
					Object idf = HibernateUtil.createSession();
					Session session = HibernateUtil.getSession();
					Transaction tx = session.beginTransaction();

					session.update(savedUser);
					session.flush();
					
					tx.commit();
					HibernateUtil.closeSession(idf);
					
					return returnLong;
				}
				
			} else {
				log.error("[saveOrUpdateUser] invalid auth "+users_id+ " "+new Date());
			}
		} catch (Exception ex) {
			log.error("[saveOrUpdateUser]",ex);
		}
		
		return null;
	}
	
	/**
	 * reset a username by a given mail oder login by sending a mail to the registered EMail-Address
	 * @param email
	 * @param username
	 * @param appLink
	 * @return
	 */
	public Long resetUser(String email, String username, String appLink) {
		try {
			//check if Mail given
			if (email.length()>0){
				Adresses_Emails addr_e = (Adresses_Emails) Emailmanagement.getInstance().getAdresses_EmailsByMail(email);
				//log.debug("addr_e "+addr_e);
				if (addr_e!=null) {
					//log.debug("getAdresses_id "+addr_e.getAdresses_id());
					Users us = this.getUserByAdressesId(addr_e.getAdresses_id());
					if (us!=null) {
						this.sendHashByUser(us, appLink);
						return new Long(-4);
					} else {
						return new Long(-9);
					}
				} else {
					return new Long(-9);
				}
			//check if username given
			} else if (username.length()>0){
				Users us = this.getUserByName(username);
				if (us!=null) {
					this.sendHashByUser(us, appLink);
					return new Long(-4);
				} else {
					return new Long(-3);
				}
			}
		} catch (Exception e) {
			log.error("[resetUser]",e);
			return new Long(-1);
		}
		return new Long(-2);
	}
	
	private void sendHashByUser(Users us, String appLink) throws Exception {
		String loginData = us.getLogin()+new Date();
		log.debug("User: "+us.getLogin());
		us.setResethash(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(loginData));
		this.updateUser(us);
		String reset_link = appLink+"?hash="+us.getResethash();
		
		Adresses_Emails addrE = (Adresses_Emails) us.getAdresses().getEmails().iterator().next();
		
		Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
		
		String template = ResetPasswordTemplate.getInstance().getResetPasswordTemplate(reset_link, default_lang_id);
		
		Fieldlanguagesvalues labelid517 = Fieldmanagment.getInstance().getFieldByIdAndLanguage(new Long(517), default_lang_id);
    	
		MailHandler.sendMail(addrE.getMail().getEmail(), labelid517.getValue(), template);
	}
	
	private Users getUserByName(String login) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.login = :login" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("login", login);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return null;
	}
	
	private Users getUserByAdressesId(Long adresses_id) {
		try {
			String hql = "SELECT u FROM Users as u " +
					" where u.adresses.adresses_id = :adresses_id" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("adresses_id", adresses_id);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			return us;			
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return null;
	}
	
	public Object getUserByHash (String hash) {
		try {
			if (hash.length()==0) return new Long(-5);
			String hql = "SELECT u FROM Users as u " +
					" where u.resethash = :resethash" +
					" AND deleted != :deleted";
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("resethash", hash);
			query.setString("deleted", "true");
			Users us = (Users) query.uniqueResult();
			tx.commit();
			HibernateUtil.closeSession(idf);
			if (us!=null) {
				return us;		
			} else {
				return new Long(-5);
			}
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return new Long(-1);
	}
	
	public Object resetPassByHash (String hash, String pass) {
		try {
			Object u = this.getUserByHash(hash);
			if (u instanceof Users) {
				Users us = (Users) u;
				us.setPassword(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(pass));
				us.setResethash("");
				this.updateUser(us);
				return new Long(-8);
			} else {
				return u;
			}
		} catch (Exception e) {
			log.error("[getUserByAdressesId]",e);
		}
		return new Long(-1);
	}

}
