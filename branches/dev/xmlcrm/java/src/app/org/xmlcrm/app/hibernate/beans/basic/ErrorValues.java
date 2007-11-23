package org.xmlcrm.app.hibernate.beans.basic;

import java.util.Date;

import org.xmlcrm.app.hibernate.beans.lang.Fieldvalues;

/**
 * 
 * @hibernate.class table="errorvalues"
 *
 */
public class ErrorValues {
	
	private Long errorValuesId;
	private Fieldvalues fieldvalues;
	private Date starttime;
	private Date updatetime;
	private String deleted;
	
    /**
     * 
     * @hibernate.id
     *  column="errorValuesId"
     *  generator-class="increment"
     */ 
	public Long getErrorValuesId() {
		return errorValuesId;
	}
	public void setErrorValuesId(Long errorValuesId) {
		this.errorValuesId = errorValuesId;
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
     *  type="string"
     */	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

    /**
	 * @hibernate.many-to-one
	 * column = "fieldvalues_id"
	 * class = "org.xmlcrm.app.hibernate.beans.lang.Fieldvalues"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Fieldvalues getFieldvalues() {
		return fieldvalues;
	}
	public void setFieldvalues(Fieldvalues fieldvalues) {
		this.fieldvalues = fieldvalues;
	}
	
}
