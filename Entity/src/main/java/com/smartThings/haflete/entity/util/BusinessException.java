package com.smartThings.haflete.entity.util;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends Exception {
	
	String bundleName;

	public BusinessException(String bundleName) {
		super();
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
}
