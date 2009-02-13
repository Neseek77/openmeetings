package org.openmeetings.app.ldap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.openmeetings.app.data.user.Usermanagement;
import org.slf4j.Logger;
import org.red5.logging.Red5LoggerFactory;


/**
 * 
 * @author o.becherer
 * 
 * BaseClass for optional LDAP Authentification
 *
 */
public class LdapAuthBase {
	/** LdapConnectionUrl */
	private String ldap_connection_url = "";
	
	/** LdapServer Loginname */
	private String ldap_admin = "";
	
	/** LdapServer Passwd */
	private String ldap_passwd = "";
	
	/** LdapServer LoginBase */
	private String ldap_login_base = "";
	
	/** Security Authentification Type */
	private String ldap_auth_type = "simple";
	
	/** Directory Context */
	private DirContext authContext = null;
	
	/** ContextFactory */
	private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	
	public static final String LDAP_AUTH_TYPE_SIMPLE = "simple";
	public static final String LDAP_AUTH_TYPE_NONE = "none";
	
	private static final Logger log = Red5LoggerFactory.getLogger(Usermanagement.class, "openmeetings");

	
	/**
	 * Configuring LdapConnection
	 * @param connectionUrl
	 * @param admin
	 * @param passwd
	 * @param base
	 * @param authType
	 */
	//------------------------------------------------------------------------------------------------------
	public LdapAuthBase(String connectionUrl, String admin, String passwd, String base, String authType){
		log.debug("LdapAuthBase");
		
		this.ldap_connection_url = connectionUrl;
		this.ldap_admin = admin;
		this.ldap_passwd = passwd;
		this.ldap_login_base = base;
		this.ldap_auth_type = authType;
	}
	//------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Autentificate User
	 * @param username
	 * @param passwd
	 */
	//-------------------------------------------------------------------------------------------------------
	public boolean authenticateUser(String username, String passwd){
		log.debug("authenticateUser");
		
		Properties ldapAuthenticateProperties = new Properties();
	    ldapAuthenticateProperties.put(Context.PROVIDER_URL, ldap_connection_url);
	    ldapAuthenticateProperties.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
	    ldapAuthenticateProperties.put(Context.REFERRAL, "follow");
	    ldapAuthenticateProperties.put(Context.SECURITY_AUTHENTICATION, ldap_auth_type);
	    ldapAuthenticateProperties.put(Context.SECURITY_PRINCIPAL, username);
	    ldapAuthenticateProperties.put(Context.SECURITY_CREDENTIALS, passwd);
	    ldapAuthenticateProperties.put("java.naming.ldap.referral.bind", "true");
		
	    if(!ldap_auth_type.equals(LDAP_AUTH_TYPE_NONE) && (ldap_admin !=null || ldap_admin.length() < 1)){
	    	log.debug("Authentification to LDAP - Server start");
	    	try {
	    		loginToLdapServer();
	    	
	    		authContext = new InitialDirContext(ldapAuthenticateProperties);
	    	} catch (Exception ae){
	    		log.error("Authentification on LDAP Server failed : " + ae.getMessage());
	    		return false;
	    	}
	    }
	    
	    
	    else{
	    	log.debug("Connection to LDAP - Server start (without Server login)");
	    	try{
	    		authContext = new InitialDirContext(ldapAuthenticateProperties);
	    	}catch(Exception e){
	    		log.error("Connection to LDAP Server failed : " + e.getMessage());
	    		return false;
	    	}
	    }
	    
	    return true;
		
	}
	//-------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Login to LdapServer
	 */
	//-------------------------------------------------------------------------------------------------------
	private void loginToLdapServer() throws Exception{
		log.debug("loginToLdapServer");
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		
		// Build Security Principal
		String dn = "CN=" + ldap_admin + "," + ldap_login_base;
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, ldap_connection_url);
		env.put(Context.SECURITY_AUTHENTICATION, ldap_auth_type);
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, ldap_passwd);
		
		authContext = new InitialDirContext(env);
		
	}
	//-------------------------------------------------------------------------------------------------------
	
	
	 /**
	  * @param searchScope LDAP Url to search within
	  * @param filter LDAP Filter
	  * @param attributes Attributes to extract from LDAP Search
	  */
	//-------------------------------------------------------------------------------------------------------
	public Vector<HashMap<String, String>> getData(String searchScope, String filter, List<String> attributes){
		log.debug("getData");
		
		// Searchparams
		SearchControls constraints = new SearchControls();
		 
		// Recursive Search
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

		// Result
		Vector<HashMap<String, String>>  result = new Vector<HashMap<String, String>>() ;
		
		try{
			// search
			NamingEnumeration results = authContext.search(searchScope, filter, constraints);
			
			int y = 0;
			
			// Stepping through the data
			while (results != null && results.hasMore()) {
			     HashMap<String, String> innerMap = new HashMap<String, String>();
			     
				 y++;
				 
				 // Next result
			     SearchResult si = (SearchResult) results.next();
			     
			     if(si == null){
			    	 continue;
			     }
			     
			     // Attribute
			     javax.naming.directory.Attributes attrs = si.getAttributes();
			     
			     if(attrs != null){
			    	 for(int i = 0; i < attributes.size(); i++){
			    		 
			    		 String key = attributes.get(i);
			    		 String val = "";
			    		
			    		 Attribute but = attrs.get(key);
			    		 
			    		 if(but!= null){
			    		 	 Object obj = but.get();
				    		 
				    		 if(obj != null)
				    			 val = String.valueOf(obj);
			    		 }
			    		 
			    		 innerMap.put(key,val );
			    	 }
			     }
			     
			     result.add(innerMap);
			     
			    
			  }
		}catch(Exception e){
			log.error("Error occured on LDAP Search : " + e.getMessage());
		}
		
		return result;
	}
	
	//-------------------------------------------------------------------------------------------------------
}
