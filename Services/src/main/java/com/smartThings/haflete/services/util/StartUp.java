package com.smartThings.haflete.services.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.smartThings.haflete.dao.util.HibernateUtil;

@Singleton(mappedName = "StartUp")
@Startup
public class StartUp {

	@PostConstruct
	public void ini() { 
		try {
			HibernateUtil.init();
			HibernateUtil.getSession();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
