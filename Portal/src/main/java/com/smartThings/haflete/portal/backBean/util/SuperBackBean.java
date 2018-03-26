package com.smartThings.haflete.portal.backBean.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class SuperBackBean implements Serializable {
	
	private static final long serialVersionUID = 1891627234784738276L;
	private static final String GENERAL_ERROR_MSG = "GENERAL_ERROR";
	
	public void addErrorMessage(String messageBundle) {
		addErrorMessage("globalError", messageBundle);
	}
	
	public void addGeneralError() {
		addErrorMessage("globalError", GENERAL_ERROR_MSG);
	}
	
	public void addSuccMsg() {
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		String message = bundleUtil.findMsg("operationSuccess");
		FacesContext.getCurrentInstance().addMessage("globalError", new FacesMessage(FacesMessage.SEVERITY_INFO, "", message));
	}
	
	public void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			addGeneralError();
			e.printStackTrace();
		}
	}
	
	public void addErrorMessage(String id, String bundleName) {
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		String message = bundleUtil.findMsg(bundleName);
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حطأ", message));
	}
}
