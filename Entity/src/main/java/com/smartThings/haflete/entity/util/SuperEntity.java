package com.smartThings.haflete.entity.util;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class SuperEntity implements Serializable {
	
	private static final long serialVersionUID = 4012397696031142201L;
	
	@Column(name="LAST_UPDATE_DATE") 
	@UpdateTimestamp
	private Calendar lastUpdateDate;
	
	@Column
	private Boolean active;
	
	public abstract Long getId();

	public abstract void setId(Long id);
	
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
