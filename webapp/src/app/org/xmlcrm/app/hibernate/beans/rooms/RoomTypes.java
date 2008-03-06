package org.xmlcrm.app.hibernate.beans.rooms;

import java.util.Date;

/**
 * 
 * @hibernate.class table="roomtypes"
 * lazy="false"
 *
 */
public class RoomTypes {

	private Long roomtypes_id;
	private Date starttime;
	private Date updatetime;
	private String name;
	private String deleted;

    
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
     * 
     * @hibernate.id
     *  column="roomtypes_id"
     *  generator-class="increment"
     */  	
	public Long getRoomtypes_id() {
		return roomtypes_id;
	}
	public void setRoomtypes_id(Long roomtypes_id) {
		this.roomtypes_id = roomtypes_id;
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
}
