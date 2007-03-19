package org.xmlcrm.app.data.user;

import java.util.Iterator;
import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import org.xmlcrm.app.hibernate.beans.basic.Configuration;
import org.xmlcrm.app.hibernate.beans.contact.Contacts;
import org.xmlcrm.app.hibernate.beans.adresses.Emails;
import org.xmlcrm.app.hibernate.beans.user.*;
import org.xmlcrm.app.hibernate.utils.HibernateUtil;
import org.xmlcrm.app.remote.ResHandler;
import org.xmlcrm.app.data.basic.Configurationmanagement;
import org.xmlcrm.utils.mail.*;
import org.xmlcrm.utils.math.*;

import org.xmlcrm.app.data.basic.*;

public class Usermanagement {
	
	private static final Log log = LogFactory.getLog(Usermanagement.class);
	
	private static Usermanagement instance = null;
	
	private Usermanagement(){}
	
	public static synchronized Usermanagement getInstance(){
		if (instance == null){
			instance = new Usermanagement();
		}
		return instance;
	}

	
    private boolean checkUserLevel(long USER_LEVEL){
        if (USER_LEVEL>1){
            return true;
        } else {
            return false;
        }
    }   
    private boolean checkConfLevel(long USER_LEVEL){
        if (USER_LEVEL>2){
            return true;
        } else {
            return false;
        }
    }
	public Users getUser(Long user_id){
		Users users = new Users();
		if (user_id.longValue()>0){
		    try {
		    	Session session = HibernateUtil.currentSession();
		    	Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Users as c where c.user_id = :user_id");
				query.setLong("user_id", user_id.longValue());		
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users = (Users) it2.next();
				}
		    	tx.commit();
		    	HibernateUtil.closeSession();
                users.setUserlevel(getUserLevel(users.getLevel_id()));
                //TODO: Adresses Einbinden
                //users.setEmails(ResHandler.getEmailmanagement().getemails(users.getUser_id()));
                users.setUserdata(getUserdata(users.getUser_id()));    
                //TODO: Einbinden der Benutzergruppen
                //users.setUsergroups(ResHandler.getGroupmanagement().getUserGroups(user_id));
	        } catch( HibernateException ex ) {
	        	log.error(ex);
	        } catch ( Exception ex2 ){
	        	log.error(ex2);
	        }
		} else {
			users.setFirstname("Error: No USER_ID given");
		}
		return users;
	}
	
	public Users searchUserByID(Long user_id,String searchstring,String searchcriteria,int searchMax, int start){
		Users users = new Users();
		try {
	    	Session session = HibernateUtil.currentSession();
	    	Criteria crit = session.createCriteria(Users.class);  
	    	crit.add(Restrictions.ilike(searchcriteria, "%"+searchstring+"%"));
	    	crit.setMaxResults(searchMax);
	    	crit.add(Restrictions.eq("user_id", user_id ));
	    	crit.setFirstResult(start);
	    	List contactsZ = crit.list();
	    	int k = 0;
	        for(Iterator it = contactsZ.iterator();it.hasNext();){
	        	users = (Users) it.next();
	        	k++;
	        }
	    	HibernateUtil.closeSession();	  
    		users.setPassword("empty");    		
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return users;
	}
	
	public Users loginUser(String SID, String Username, String Userpass){
		Users usersA = new Users();
		Long UID = new Long(0);
		Long LEVEL_ID = new Long(1);
	    try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();    
	    	MD5Calc md5 = new MD5Calc("MD5");
	    	Query query = session.createQuery("select c from Users as c where c.login = :login");
			query.setString("login", Username);
			int count = query.list().size();
			log.debug("debug loginUser: "+Username);
			if (count!=1){
				usersA.setLevel_id(new Long(-1));
				usersA.setFirstname("Wrong - "+Username+" not Found");
				usersA.setUser_id(UID);
			} else {
				Users users = new Users();
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users = (Users) it2.next();
				}
				String chsum = md5.do_checksum(Userpass);
				if (chsum.equals(users.getPassword())){
					UID = users.getUser_id();
					LEVEL_ID = users.getLevel_id();
					usersA = users;
				} else {
					usersA.setLevel_id(new Long(-1));
					usersA.setFirstname("Wrong -Invalid Password for"+Username);
					usersA.setUser_id(UID);				
				}
			}
	    	tx.commit();
	    	HibernateUtil.closeSession();
	    	Sessionmanagement.getInstance().updateUser(SID,UID.longValue());
	    	usersA.setUserlevel(getUserLevel(LEVEL_ID));
	    	//TODO:Adressids nachpflegen
	    	//usersA.setEmails(ResHandler.getEmailmanagement().getemails(usersA.getUser_id()));
            usersA.setUserdata(getUserdata(usersA.getUser_id()));
            if (usersA.getLieferadressen()!=null){
            	usersA.setLieferadressen(getUserdataByKey(UID, "lieferaddr"));
            }
            if (usersA.getRechnungsaddressen()!=null){
            	usersA.setRechnungsaddressen(getUserdataByKey(UID, "rechnungsaddr"));
            }
            if (UID.longValue()!=0){
                updateLastLogin(usersA);
            }
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
		return usersA;	    
	}
	public String logout(String SID, int USER_ID){
		String result = "Fehler im logout";
		Sessionmanagement.getInstance().updateUser(SID,0);
		result = "Erfolgreich";
		return result;
	}
    private void updateLastLogin(Users us){
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();        
            session.update(us);
            tx.commit();
            HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
    }
	
	public Users[] searchUser(long USER_LEVEL,String searchstring){
		Users users[] = new Users[1];
		if (USER_LEVEL>1){
			try {
		    	Session session = HibernateUtil.currentSession();
		    	Criteria crit = session.createCriteria(Users.class);  
		    	crit.add(Restrictions.ilike("lastname", "%"+searchstring+"%"));
		    	crit.setMaxResults(10);
		    	List contactsZ = crit.list();
		    	int count = contactsZ.size();
		    	users = new Users[count];
		    	int k = 0;
		        for(Iterator it = contactsZ.iterator();it.hasNext();){
		        	users[k] = (Users) it.next();
		        	k++;
		        }
		    	HibernateUtil.closeSession();	  
		    	for (int vars=0;vars<users.length;vars++){
		    		users[vars].setPassword("empty");    		
		    	}
	        } catch( HibernateException ex ) {
	        	log.error(ex);
	        } catch ( Exception ex2 ){
	        	log.error(ex2);
	        }
        } else {
        	users[0] = new Users();
        	users[0].setFirstname("Error: No USER_ID given");
        }
        return users;
	}
	
    public Userdata[] getUserdata(Long user_id){
        Userdata userdata[] = new Userdata[1];
        if (user_id.longValue()>0){
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id");
                query.setLong("user_id", user_id.longValue());   
                int count = query.list().size();
                userdata = new Userdata[count];
                int k = 0;
                for (Iterator it2 = query.iterate(); it2.hasNext();) {
                    userdata[k] = (Userdata) it2.next();
                    k++;
                }
                tx.commit();
                HibernateUtil.closeSession();
            } catch( HibernateException ex ) {
            	log.error(ex);
            } catch ( Exception ex2 ){
            	log.error(ex2);
            }
        } else {
            userdata[0] = new Userdata();
            userdata[0].setComment("Error: No USER_ID given");
        }
        return userdata;
    }  
    private int getUserdataNoByKey(Long USER_ID, String DATA_KEY){
        int userdata = 0;
        if (USER_ID.longValue()>0){
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key");
                query.setLong("user_id", USER_ID.longValue());  
                query.setString("data_key",DATA_KEY);
                userdata = query.list().size();
                tx.commit();
                HibernateUtil.closeSession();
            } catch( HibernateException ex ) {
            	log.error(ex);
            } catch ( Exception ex2 ){
            	log.error(ex2);
            }
        } else {
        	System.out.println("Error: No USER_ID given");
        }
        return userdata;
    }
    public Userdata getUserdataByKey(Long user_id, String DATA_KEY){
        Userdata userdata = new Userdata();
        if (user_id.longValue()>0){
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("select c from Userdata as c where c.user_id = :user_id AND c.data_key = :data_key");
                query.setLong("user_id", user_id.longValue());  
                query.setString("data_key",DATA_KEY);                
                for (Iterator it2 = query.iterate(); it2.hasNext();) {
                    userdata = (Userdata) it2.next();
                }
                tx.commit();
                HibernateUtil.closeSession();
            } catch( HibernateException ex ) {
            	log.error(ex);
            } catch ( Exception ex2 ){
            	log.error(ex2);
            }
        } else {
            userdata.setComment("Error: No USER_ID given");
        }
        return userdata;
    }
    public String updateUser(long USER_LEVEL,Long user_id, Long level_id, String login, String password, String lastname, String firstname, int age, String adresse, String Zip, String state, String town, int rechnungsaddr, String raddresse, int lieferaddr, String laddresse,int availible, String telefon, String fax, String mobil,int EMailID,String email){
        String res = "Fehler beim Update";
        if (checkUserLevel(USER_LEVEL) && user_id!=0){
            try {
            	Users us = this.getUser(user_id);
            	if (this.checkUserData("login", login) || us.getLogin().equals(login)){
            		log.info("user_id "+user_id);
                	
                	
                	log.info("USER "+us.getLastname());
                    Session session = HibernateUtil.currentSession();
                    Transaction tx = session.beginTransaction();    
                    
                    us.setLastname(lastname);
                    us.setFirstname(firstname);
                    us.setAge(new Date());
                    us.setLogin(login);
                    us.setUpdatetime(new Date());
                    us.setAvailible(availible);
                    
                    if (level_id!=0) us.setLevel_id(new Long(level_id));
                    if (password.length()!=0) {
                    	MD5Calc md5 = new MD5Calc("MD5");
                    	us.setPassword(md5.do_checksum(password));
                    }
                    
                    session.update(us);
                    res = "Success";
                    tx.commit();
                    HibernateUtil.closeSession();
                    if (lieferaddr != 0){
                    	if (getUserdataNoByKey(user_id,"lieferaddr")==0){
                    		addUserdata(user_id.longValue(),"lieferaddr",laddresse,"");
                    	} else {
                    		updateUserdataByKey(user_id,"lieferaddr",laddresse,"");
                    	}
                    }
                    if (rechnungsaddr != 0){
                    	if (getUserdataNoByKey(user_id,"rechnungsaddr")==0){
                    		addUserdata(user_id.longValue(),"rechnungsaddr",raddresse,"");
                    	} else {
                    		updateUserdataByKey(user_id,"rechnungsaddr",raddresse,"");
                    	}
                    }
                    Emailmanagement.getInstance().updateUserEmail(EMailID,user_id,email);
            	} else {
            		res = "Error: Username not availible";
            	}
            } catch( HibernateException ex ) {
            	log.error(ex);
            } catch ( Exception ex2 ){
            	log.error(ex2);
            }
        } else {
            res = "Error: Permission denied";
        }            
        return res;
    }
    
    public String resetPassword(int USER_ID){
        String res = "Fehler beim Update";
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();    
            MD5Calc md5 = new MD5Calc("MD5");   
            String newPass = GenerateRandom.randomstring(5,10);
            String hqlUpdate = "update users set password = :password where USER_ID= :USER_ID";
            int updatedEntities = session.createQuery( hqlUpdate )
                                .setString("password",md5.do_checksum(newPass))
                                .setInteger( "USER_ID", USER_ID )
                                .executeUpdate();
            res = "Success"+updatedEntities;
            tx.commit();
            HibernateUtil.closeSession();  
            Emailmanagement.getInstance().sendNewPass(USER_ID,newPass);
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return res;        
    }
    
    public String updateUserdata(int DATA_ID, int USER_ID,String DATA_KEY,String DATA,String Comment){
        String res = "Fehler beim Update";
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();        
            String hqlUpdate = "update userdata set DATA_KEY= :DATA_KEY, USER_ID = :USER_ID, DATA = :DATA, updatetime = :updatetime, comment = :Comment where DATA_ID= :DATA_ID";
            int updatedEntities = session.createQuery( hqlUpdate )
                                .setString("DATA_KEY",DATA_KEY)
                                .setInteger( "USER_ID", USER_ID )
                                .setString("DATA",DATA)
                                .setLong( "updatetime", Calender.getInstance().getTimeStampMili() )
                                .setString( "Comment",Comment)
                                .setInteger( "DATA_ID", DATA_ID )
                                .executeUpdate();
            res = "Success"+updatedEntities;
            tx.commit();
            HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return res;
    }    
    public String updateUserdataByKey(Long USER_ID,String DATA_KEY,String DATA,String Comment){
        String res = "Fehler beim Update";
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();        
            String hqlUpdate = "update Userdata set data = :data, updatetime = :updatetime, " +
            					"comment = :comment where user_id= :user_id AND data_key = :data_key";
            int updatedEntities = session.createQuery( hqlUpdate )
                                .setString("data",DATA)
                                .setLong( "updatetime", Calender.getInstance().getTimeStampMili() )
                                .setString( "comment",Comment)
                                .setLong( "user_id", USER_ID.longValue() )
                                .setString("data_key",DATA_KEY)
                                .executeUpdate();
            res = "Success"+updatedEntities;
            tx.commit();
            HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return res;
    }
    public String deleteUserdata(int DATA_ID){
        String res = "Fehler beim deleteUserdata";
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();        
            String hqlUpdate = "delete userdata where DATA_ID= :DATA_ID";
            int updatedEntities = session.createQuery( hqlUpdate )
                                .setInteger( "DATA_ID", DATA_ID )
                                .executeUpdate();
            res = "Success"+updatedEntities;
            tx.commit();
            HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return res;
    } 
    public String deleteUserdataByUserAndKey(int User_ID, String DATA_KEY){
        String res = "Fehler beim deleteUserdataByUserAndKey";
        try {
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();        
            String hqlUpdate = "delete userdata where User_ID= :User_ID AND DATA_KEY = :DATA_KEY";
            int updatedEntities = session.createQuery( hqlUpdate )
                                .setInteger( "User_ID", User_ID )
                                .setString("DATA_KEY",DATA_KEY)
                                .executeUpdate();
            res = "Success"+updatedEntities;
            tx.commit();
            HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return res;
    }     
    public String addUserdata(long USER_ID,String DATA_KEY,String DATA,String Comment){
        String ret = "Fehler beim speichern der Userdata";
        long thistime = Calender.getInstance().getTimeStampMili();
        Userdata userdata = new Userdata();
        userdata.setData_key(DATA_KEY);
        userdata.setData(DATA);
        userdata.setStarttime(new Date());
        userdata.setUpdatetime(null);
        userdata.setComment(Comment);
        userdata.setUser_id(new Long(USER_ID));
        try {   
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();
            session.save(userdata);
            session.flush();   
            session.clear();
            session.refresh(userdata);
            tx.commit();
            HibernateUtil.closeSession();    
            ret = "success";
        } catch( HibernateException ex ) {
        	log.error(ex);
        } catch ( Exception ex2 ){
        	log.error(ex2);
        }
        return ret;
    }
	public String deleteUserID(long USER_ID){
		String result = "Fehler im deleteUserID";
	    try {
	    	if (USER_ID!=0){
	    		Users us = this.getUser(USER_ID);
	    		
	    		//result += Groupmanagement.getInstance().deleteUserFromAllGroups(new Long(USER_ID));
	    		
		    	Session session = HibernateUtil.currentSession();
		    	Transaction tx = session.beginTransaction();    	
		        session.delete(us);
		    	tx.commit();
		    	HibernateUtil.closeSession();
		    	result = "Erfolgreich";
//		    	result += ResHandler.getBestellmanagement().deleteWarenkorbByUserID(USER_ID);
//		    	result += ResHandler.getEmailmanagement().deleteEMailByUserID(USER_ID);
//		    	result += ResHandler.getContactmanagement().deleteContactUsergroups(USER_ID);
//		    	result += ResHandler.getContactmanagement().deleteUserContact(USER_ID);
		    	
	    	}
        } catch( HibernateException ex ) {
        	log.error("[deleteUserID]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[deleteUserID]"+ex2);
        }
		return result;
	}
	private Userlevel getUserLevel(Long level_id){
		Userlevel userlevel = new Userlevel();
	    try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();
			Query query = session.createQuery("select c from Userlevel as c where c.level_id = :level_id");
			query.setLong("level_id", level_id.longValue());		
			for (Iterator it2 = query.iterate(); it2.hasNext();) {
				userlevel = (Userlevel) it2.next();
			}
	    	tx.commit();
	    	HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error("[getUserLevel]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[getUserLevel]"+ex2);
        }
		return userlevel;
	}
	public long getUserLevelByID(long user_id){
		long UserLevel = 1;
		try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();    
	    	Query query = session.createQuery("select c from Users as c where c.user_id = :user_id");
			query.setLong("user_id", user_id);
			int count = query.list().size();
			if (count!=1){
				UserLevel = 1;
			} else {
				Users users = new Users();
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users = (Users) it2.next();
				}
				UserLevel = users.getLevel_id().longValue();
			}
	    	tx.commit();
	    	HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error("[getUserLevelByID]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[getUserLevelByID]"+ex2);
        }
		return UserLevel;
	}
	public String registerUser(long USER_LEVEL,String login, String Userpass, String lastname, String firstname, String email, int age, String adresse, String Zip, String state, String town, long language_id){
		String result = "Invalid";
		System.out.println("registerUser: "+result);
		if (Configurationmanagement.getInstance().getConfKey(3,"allow_frontend_register").getConf_value().equals("1") || checkConfLevel(USER_LEVEL)){
			if (!login.equals("") && !Userpass.equals("") && !email.equals("")){
				boolean checkName = checkUserData("lastname",login);
				boolean checkEmail = Emailmanagement.getInstance().checkUserEMail("email",email);

				if (checkName && checkEmail){
					Users users = new Users();
					users.setFirstname(firstname);
					users.setLogin(login);
	                users.setLastname(lastname);
					users.setAge(new Date());
					//TODO: Adressdaen geh�ren in die Adressdatentabellen
					//users.set(state);
					users.setAvailible(new Integer(1));
					users.setLastlogin(new Date());
					users.setLasttrans(new Long(0));
					users.setLevel_id(new Long(1));
					users.setStatus(1);
					users.setTitle_id(new Integer(1));
					users.setStarttime(new Date());
					users.setLanguage_id(new Long(language_id));
					users.setUpdatetime(new Date());
//					users.setTown(town);
//					users.setAdress(adresse);
//					users.setZip(Zip);
//					users.setUserlevel(2);
					//TODO: fehlende adress_id's f�r die rechnungs und lieferadresse in users
//					users.setLieferadresse(0);
//					users.setRechnungsaddresse(0);
//					users.setFax("");
//					users.setTelefon("");
//					users.setMobil("");
					MD5Calc md5 = new MD5Calc("MD5");
					users.setPassword(md5.do_checksum(Userpass));
					users.setRegdate(new Date());
			        try {   
			            Session session = HibernateUtil.currentSession();
			            Transaction tx = session.beginTransaction();
			            session.save(users);
			            session.flush();   
			            session.clear();
			            session.refresh(users);
			            tx.commit();
			            HibernateUtil.closeSession();
			            Long UserID = users.getUser_id();
			            
			            result = "ok";
			            //result = ResHandler.getEmailmanagement().registerEmail(email,UserID,Username,Userpass);  
			            Configuration configuration = Configurationmanagement.getInstance().getConfKey(3,"default_group_id");
			            //result = Groupmanagement.getInstance().addUserToGroup(new Long(3),new Long(Long.parseLong(configuration.getConf_value())),UserID,"new");
			           // configuration = Configurationmanagement.getInstance().getConfKey(3,"default_freigabe_folder_name");
			           // result = ResHandler.getContactmanagement().addContactGroup(3,UserID,configuration.getConf_value(),1,"Meine Freigaben - Benutzer in diesem Ordner sind im System vorl�ufig freigegeben","newUser");
			           // configuration = Configurationmanagement.getInstance().getConfKey(3,"default_folder_name");
			            //result = ResHandler.getContactmanagement().addContactGroup(3,UserID,configuration.getConf_value(),1,"Einfacher Ordner - Benutzer in diesem Ordner sind vorl�ufig nicht freigegeben","newUser");		            
			        } catch( HibernateException ex ) {
			        	log.error("[registerUser]"+ex);
			        } catch ( Exception ex2 ){
			        	log.error("[registerUser]"+ex2);
			        }
				} else {
					if (!checkName){
						result = "Dieser Name ist schon vergeben";
					}else if(!checkEmail){
						result = "Diese EMail ist schon registriert"; 
					}
				}
			} else {
				result = "Sie m�ssen mindestens einen Namen, ein Passwort und eine EMail-Adresse angeben"; 
			}
		} else {
			result = "Die Systemeinstellungen erlauben keinen Benutzerregistrierung";
		}
		return result;
	}

	public String registerUserInit(long USER_LEVEL,String login, String Userpass, String lastname, String firstname, String email, int age, String adresse, String Zip, String state, String town, long language_id){
		String result = "Invalid";
		System.out.println("registerUser: "+result);
			if (!login.equals("") && !Userpass.equals("") && !email.equals("")){
				boolean checkName = checkUserData("lastname",login);
				boolean checkEmail = Emailmanagement.getInstance().checkUserEMail("email",email);

				if (checkName && checkEmail){
					Users users = new Users();
					users.setFirstname(firstname);
					users.setLogin(login);
	                users.setLastname(lastname);
					users.setAge(new Date());
					//TODO: Adressdaen geh�ren in die Adressdatentabellen
					//users.set(state);
					users.setAvailible(new Integer(1));
					users.setLastlogin(new Date());
					users.setLasttrans(new Long(0));
					users.setLevel_id(new Long(1));
					users.setStatus(1);
					users.setTitle_id(new Integer(1));
					users.setStarttime(new Date());
					users.setUpdatetime(new Date());
					users.setLanguage_id(new Long(language_id));
					MD5Calc md5 = new MD5Calc("MD5");
					users.setPassword(md5.do_checksum(Userpass));
					users.setRegdate(new Date());
			        try {   
			            Session session = HibernateUtil.currentSession();
			            Transaction tx = session.beginTransaction();
			            session.save(users);
			            session.flush();   
			            session.clear();
			            session.refresh(users);
			            tx.commit();
			            HibernateUtil.closeSession();
			            Long UserID = users.getUser_id();
			            
			            result = "ok";
			            //result = ResHandler.getEmailmanagement().registerEmail(email,UserID,Username,Userpass);  
			        } catch( HibernateException ex ) {
			        	log.error("[registerUser]"+ex);
			        } catch ( Exception ex2 ){
			        	log.error("[registerUser]"+ex2);
			        }
				} else {
					if (!checkName){
						result = "Dieser Name ist schon vergeben";
					}else if(!checkEmail){
						result = "Diese EMail ist schon registriert"; 
					}
				}
			} else {
				result = "Sie m�ssen mindestens einen Namen, ein Passwort und eine EMail-Adresse angeben"; 
			}

		return result;
	}
	
	private boolean checkUserData(String DataName, String DataValue){
		boolean UserLevel = true;
		try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();    
	    	Query query = session.createQuery("select c from Users as c where c.login = :DataValue");
			query.setString("DataValue", DataValue);
			int count = query.list().size();
			if (count!=0){
				UserLevel = false;
			}
	    	tx.commit();
	    	HibernateUtil.closeSession();
        } catch( HibernateException ex ) {
        	log.error("[checkUserData]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[checkUserData]"+ex2);
        }
		return UserLevel;
	}
	
	public Users[] getAllFreeUser(long User_LEVEL,int maxRes,long user_id){
		Users users[] = new Users[1];
		if (User_LEVEL>0){
		    try {
		    	Session session = HibernateUtil.currentSession();
		    	Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Users as c where c.availible = :availible AND c.user_id != :user_id");
				query.setInteger("availible", 0);
				query.setLong("user_id",user_id);
				query.setMaxResults(maxRes);
				int count = query.list().size();
				users = new Users[count];
				int k=0;
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users[k] = (Users) it2.next();
					k++;
				}
		    	tx.commit();
		    	HibernateUtil.closeSession();
		    	for  (int vars=0;vars<users.length;vars++){
	                users[vars].setUserlevel(getUserLevel(users[vars].getLevel_id()));
	               // EMail management ist Adressen
	               // users[vars].setEmails(ResHandler.getEmailmanagement().getemails(users[vars].get()));
	                users[vars].setUserdata(getUserdata(users[vars].getLevel_id())); 
	                users[vars].setPassword("empty");
		    	}
	        } catch( HibernateException ex ) {
	        	log.error("[getAllFreeUser]"+ex);
	        } catch ( Exception ex2 ){
	        	log.error("[getAllFreeUser]"+ex2);
	        }
        } else {
        	users[0] = new Users();
        	users[0].setFirstname("Error: No USER_ID given");
        }
        return users;
	}	
	public Users getFreeUserByID(long User_LEVEL,long USER_ID,int myUser_ID){
		Users users = new Users();
		if (User_LEVEL>0){
		    try {
		    	Session session = HibernateUtil.currentSession();
		    	Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from Users as c where c.user_id = :user_id AND c.availible = :availible AND c.user_id != :myUser_ID");
				query.setInteger("availible", 0);
				query.setLong("user_id",USER_ID);
				query.setLong("myUser_ID",myUser_ID);
				int count = query.list().size();
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users = (Users) it2.next();
				}
		    	tx.commit();
		    	HibernateUtil.closeSession();
		    	users.setUserlevel(getUserLevel(users.getLevel_id()));
		    	//TODOD: EMailmanagement in Adressen
                //users.setEmails(ResHandler.getEmailmanagement().getemails(users.getUSER_ID()));
                users.setUserdata(getUserdata(users.getUser_id())); 
                users.setPassword("empty");
	        } catch( HibernateException ex ) {
	        	log.error("[getFreeUserByID]"+ex);
	        } catch ( Exception ex2 ){
	        	log.error("[getFreeUserByID]"+ex2);
	        }
        } else {
        	users.setFirstname("Error: No USER_ID given");
        }
        return users;
	}		
    
    
    
    
    public List getusersAdmin(long USER_LEVEL,int start,int maxRes){
        List users = null;
        if (checkConfLevel(USER_LEVEL)){
            try {
                Session session = HibernateUtil.currentSession();
                Transaction tx = session.beginTransaction();
                Query query = session.createQuery("from Users");
                query.setMaxResults(maxRes);
                query.setFirstResult(start);
                users = query.list();
                
                tx.commit();
                HibernateUtil.closeSession();
				
	        } catch( HibernateException ex ) {
	        	log.error("[getusersAdmin]"+ex);
	        } catch ( Exception ex2 ){
	        	log.error("[getusersAdmin]"+ex2);
	        }
        } else {
            log.debug("Error: Permission denied in getusersAdmin");
        }
        return users;
    }
    
	public Users getUserForGroup(int USER_ID){
		Users users = new Users();
		if (USER_ID>0){
		    try {
		    	Session session = HibernateUtil.currentSession();
		    	Transaction tx = session.beginTransaction();
				Query query = session.createQuery("select c from users as c where c.USER_ID = :USER_ID AND c.availible = :availible");
				query.setInteger("availible", 0);
				query.setInteger("USER_ID", USER_ID);		
				for (Iterator it2 = query.iterate(); it2.hasNext();) {
					users = (Users) it2.next();
				}
		    	tx.commit();
		    	HibernateUtil.closeSession();
                users.setUserlevel(getUserLevel(users.getLevel_id()));
                //TODO: EMAILmanagment in Adressen
                //users.setEmails(ResHandler.getEmailmanagement().getemails(users.getUSER_ID()));
                users.setUserdata(getUserdata(users.getUser_id()));    
	        } catch( HibernateException ex ) {
	        	log.error("[getUserForGroup]"+ex);
	        } catch ( Exception ex2 ){
	        	log.error("[getUserForGroup]"+ex2);
	        }
		} else {
			users.setFirstname("Error: No USER_ID given");
		}
		return users;
	}    
	
	public void addUserLevel(String description, int myStatus){
		try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();
	    	Userlevel uslevel = new Userlevel();
	    	uslevel.setStarttime(new Date());
	    	uslevel.setDescription(description);
	    	uslevel.setStatuscode(new Integer(myStatus));
	    	session.save(uslevel);
	    	tx.commit();
	    	HibernateUtil.closeSession();
		} catch( HibernateException ex ) {
        	log.error("[getUserForGroup]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[getUserForGroup]"+ex2);
        }		
	}
	
	public void addUserTitels(String titelname){
		try {
	    	Session session = HibernateUtil.currentSession();
	    	Transaction tx = session.beginTransaction();
	    	Titles ti = new Titles();
	    	ti.setName(titelname);
	    	ti.setStarttime(new Date());
	    	session.save(ti);
	    	tx.commit();
	    	HibernateUtil.closeSession();
		} catch( HibernateException ex ) {
        	log.error("[getUserForGroup]"+ex);
        } catch ( Exception ex2 ){
        	log.error("[getUserForGroup]"+ex2);
        }
	}
	
}

