package org.xmlcrm.app.hibernate.beans.basic;

import java.util.Date;

import org.xmlcrm.app.hibernate.beans.lang.Fieldlanguagesvalues;

/**
 * 
 * @hibernate.class table="navisub"
 *
 */
public class Navisub {
    
    private Long sub_id;
    private String name;
    private String icon;
    private Boolean isleaf;
    private Boolean isopen;
    private String action;
    private Date updatetime;
    private Date starttime;
    private String comment;
    private Integer naviorder;
    private Long level_id;
    private Long main_id;
    private String deleted;
    private Long fieldvalues_id;
    private Fieldlanguagesvalues label;
    
	public Fieldlanguagesvalues getLabel() {
		return label;
	}

	public void setLabel(Fieldlanguagesvalues label) {
		this.label = label;
	}

	public Navisub() {
	}

	/**
     * @hibernate.property
     *  column="main_id"
     *  type="long"
     */ 
	public Long getMain_id() {
		return main_id;
	}
	public void setMain_id(Long main_id) {
		this.main_id = main_id;
	}

	/**
     * @hibernate.property
     *  column="level_id"
     *  type="long"
     */ 
	public Long getLevel_id() {
		return level_id;
	}
	public void setLevel_id(Long level_id) {
		this.level_id = level_id;
	}

	/**
     * @hibernate.property
     *  column="action"
     *  type="string"
     */ 
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    
    /**
     * @hibernate.property
     *  column="comment"
     *  type="string"
     */ 
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * 
     * @hibernate.id
     *  column="sub_id"
     *  generator-class="increment"
     */ 
    public Long getSub_id() {
        return sub_id;
    }
    public void setSub_id(Long sub_id) {
        this.sub_id = sub_id;
    }
    
    /**
     * @hibernate.property
     *  column="icon"
     *  type="string"
     */ 
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    /**
     * @hibernate.property
     *  column="isleaf"
     *  type="boolean"
     */ 
    public Boolean getIsleaf() {
        return isleaf;
    }
    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }
    
    /**
     * @hibernate.property
     *  column="isopen"
     *  type="boolean"
     */ 
    public Boolean getIsopen() {
        return isopen;
    }
    public void setIsopen(Boolean isopen) {
        this.isopen = isopen;
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
     *  column="naviorder"
     *  type="int"
     */
    
    public Integer getNaviorder() {
		return naviorder;
	}
	public void setNaviorder(Integer naviorder) {
		this.naviorder = naviorder;
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
     * @hibernate.property
     *  column="fieldvalues_id"
     *  type="long"
     */
	public Long getFieldvalues_id() {
		return fieldvalues_id;
	}
	public void setFieldvalues_id(Long fieldvalues_id) {
		this.fieldvalues_id = fieldvalues_id;
	}	
}
