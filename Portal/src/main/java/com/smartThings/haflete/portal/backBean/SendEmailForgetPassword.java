package com.smartThings.haflete.portal.backBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;

@ManagedBean
@ReferencedBean
public class SendEmailForgetPassword {
	
	private String email;
	
	public String sendEmail() {
		return "";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
