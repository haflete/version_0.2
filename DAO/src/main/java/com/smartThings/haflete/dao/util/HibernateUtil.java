package com.smartThings.haflete.dao.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static void init() {
		try {

			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(ssrb.build());
			sessionFactory.openSession();
			sessionFactory.getCurrentSession();
			
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		System.out.println("Love it");
	}
 
	public static Session getSession() {
		Session session;
//		sessionFactory =  new Configuration().configure("hibernate.cfg.xml")
//				.buildSessionFactory();
//		if(sessionFactory == null)
//			init();
//		System.out.println(sessionFactory);
		try {
			session = sessionFactory.getCurrentSession();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return session;
	}

	public static StatelessSession getStatelessSession() {
		return sessionFactory.openStatelessSession();
	}
}
