package org.openmeetings.app.hibernate.beans.basic;

/**
 * This Class is marshaled as an XML-Object and stored as a String in the DB to make
 * it more easy to extend it
 * 
 * @author sebastianwagner
 *
 */
public class RemoteSessionObject {
	
	private String username;
	private String firstname;
	private String lastname;
	private String pictureUrl;
	private String email;
	
	public RemoteSessionObject(String username, String firstname, String lastname,
			String pictureUrl, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.pictureUrl = pictureUrl;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
