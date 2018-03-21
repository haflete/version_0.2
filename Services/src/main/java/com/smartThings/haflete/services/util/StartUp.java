package com.smartThings.haflete.services.util;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.smartThings.haflete.dao.util.HibernateUtil;

@Singleton(mappedName = "StartUp")
@Startup
public class StartUp {
	
	public static final String ROOT_PATH = File.listRoots()[0].getAbsolutePath() + "\\7aflete\\\\medias";
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
