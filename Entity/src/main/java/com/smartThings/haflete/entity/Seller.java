package com.smartThings.haflete.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import com.smartThings.haflete.entity.util.SuperEntity;

@Entity
@Table(name = "Seller")
public class Seller extends SuperEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String email;
	
	@Column
	private String mobileNum;
	
	@Column
	private String username;
	
	@Column
	private String password;

	@OneToOne(mappedBy = "seller", fetch=FetchType.EAGER)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	@Where(clause="active=TRUE")
	private Store store;

	@Column
	private String keepMeLoginCookie;
	
	public String getKeepMeLoginCookie() {
		return keepMeLoginCookie;
	}

	public void setKeepMeLoginCookie(String keepMeLoginCookie) {
		this.keepMeLoginCookie = keepMeLoginCookie;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
