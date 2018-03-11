package com.smartThings.haflete.portal.backBean.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class SuperBackBean implements Serializable {
	
	private static final long serialVersionUID = 1891627234784738276L;
	private static final String GENERAL_ERROR_MSG = "GENERAL_ERROR";
	
	public void addErrorMessage(String messageBundle) {
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		String message = bundleUtil.findMsg(messageBundle);
		FacesContext.getCurrentInstance().addMessage("globalError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "حطأ", message));
	}
	
	public void addGeneralError() {
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		String message = bundleUtil.findMsg("GENERAL_ERROR");
		FacesContext.getCurrentInstance().addMessage("globalError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "حطأ", message));
	}
	
	public void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			addGeneralError();
			e.printStackTrace();
		}
	}
}
