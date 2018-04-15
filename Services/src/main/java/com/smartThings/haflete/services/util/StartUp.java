package com.smartThings.haflete.services.util;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.smartThings.haflete.dao.util.HibernateUtil;

@Singleton(mappedName = "StartUp")
@Startup
public class StartUp {
	
	public static final String ROOT_PATH = File.listRoots()[0].getAbsolutePath() + File.separator  + "7aflete" + File.separator + "media";
	public static final String DEFAULT_VIDEO_IMG_PATH = ROOT_PATH + File.separator + "defaults" +  File.separator + "defaultVideo.png";
	
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
