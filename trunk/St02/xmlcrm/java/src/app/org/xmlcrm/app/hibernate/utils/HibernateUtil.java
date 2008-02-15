package org.xmlcrm.app.hibernate.utils;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.cfg.*;

import org.xmlcrm.app.remote.Application;

public class HibernateUtil {
	
	private static final Log log = LogFactory.getLog(HibernateUtil.class);

	/** Read the configuration, will share across threads**/
	  private static SessionFactory sessionFactory;
	  /** the per thread session **/
	  private static final ThreadLocal<Session> currentSession = new ThreadLocal<Session>();
	  /** The constants for describing the ownerships **/
	  private static final Owner trueOwner = new Owner(true);
	  private static final Owner fakeOwner = new Owner(false); 
	  
	  /** set this to false to test with JUnit **/
	  private static final boolean isLife = true;
	  /**
	   * get the hibernate session and set it on the thread local. Returns trueOwner if 
	   * it actually opens a session
	   */
	  public static Object createSession() throws Exception{
	    Session session = (Session)currentSession.get();  
	    //System.out.println(session);
	    if(session == null){
	      //System.out.println("No Session Found - Create and give the identity");
	      session = getSessionFactory().openSession(); 
	      currentSession.set(session);
	      return trueOwner;
	    }
	    //System.out.println("Session Found - Give a Fake identity");
	    return fakeOwner;
	  }
	  /**
	   * The method for closing a session. The close  and flush 
	   * will be executed only if the session is actually created
	   * by this owner.  
	   */
	  public static void closeSession(Object ownership) throws Exception{
	    if(((Owner)ownership).identity){
	      //System.out.println("Identity is accepted. Now closing the session");
	      Session session = (Session)currentSession.get();
	      session.flush();
	      session.close();
	      currentSession.set(null);
	    }else {
	       //System.out.println("Identity is rejected. Ignoring the request");
	    }
	  }  
	  /**
	   * returns the current session
	   */
	  public static Session getSession() throws HibernateException{
	    return (Session)currentSession.get();
	  } 
	  
	  /** 
	   * Creating a session factory , if not already loaded
	   */
	  private static SessionFactory getSessionFactory() {
		try {
			if (sessionFactory == null) {
				if (isLife){
					String current_dir = Application.webAppPath+File.separatorChar+Application.configDirName+File.separatorChar+"hibernate.cfg.xml";
					sessionFactory = new Configuration().configure(new File(current_dir)).buildSessionFactory();
				} else {
					sessionFactory = new Configuration().configure().buildSessionFactory();
				}
			}
			return sessionFactory;
		} catch (HibernateException e) {
			log.error("getSessionFactory",e);
		} catch (Exception err) {
			log.error("getSessionFactory",err);
		}
		return null;
	}  
	 
	  /**
		 * Internal class , for handling the identity. Hidden for the developers
		 */
	  private static class Owner {
	     public Owner(boolean identity){
	      this.identity = identity;
	     }
	     boolean identity = false;        
	  }  
}
