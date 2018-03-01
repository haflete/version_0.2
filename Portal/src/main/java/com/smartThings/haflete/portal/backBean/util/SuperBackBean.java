package com.smartThings.haflete.portal.backBean.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class SuperBackBean implements Serializable {
	
	private static final long serialVersionUID = 1891627234784738276L;
	
	public void addErrorMessage(String messageBundle) {
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		String message = bundleUtil.findMsg(messageBundle);
		FacesContext.getCurrentInstance().addMessage("globalError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "حطأ", message));
	}
}
