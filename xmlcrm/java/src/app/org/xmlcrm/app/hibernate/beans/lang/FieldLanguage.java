package org.xmlcrm.app.hibernate.beans.lang;

import java.util.Date;

/**
 * 
 * @hibernate.class table="fieldlanguage"
 *
 */
public class FieldLanguage {

	private Long language_id;
	private String name;
	private Date starttime;
	private Date updatetime;
	private Boolean deleted;
	
    public FieldLanguage() {
		super();
	}

    /**
     * 
     * @hibernate.id
     *  column="language_id"
     *  generator-class="increment"
     */ 
	public Long getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
	}

    /**
     * @hibernate.property
     *  column="name"
     *  type="string"
     */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
     * @hibernate.property
     *  column="starttime"
     *  type="java.util.Date"
     */  	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
    
    /**
     * @hibernate.property
     *  column="updatetime"
     *  type="java.util.Date"
     */  	
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
    /**
     * @hibernate.property
     *  column="deleted"
     *  type="boolean"
     */	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}	
}
