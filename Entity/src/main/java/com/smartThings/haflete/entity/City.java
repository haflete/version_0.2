package com.smartThings.haflete.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smartThings.haflete.entity.util.SuperEntity;

@Entity
@Table(name = "City")
public class City extends SuperEntity {
	
	private static final long serialVersionUID = 3440982568078906522L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String arabicName;
	
	@Column
	private String englishName;

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", arabicName=" + arabicName + ", englishName=" + englishName + "]";
	}
}
