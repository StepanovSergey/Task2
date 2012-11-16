package com.epam.news.utils;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
	try {
	    sessionFactory = new Configuration().configure()
		    .buildSessionFactory();
	} catch (HibernateException ex) {
	    log.error(ex.getMessage(), ex);
	}
	return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
	return sessionFactory;
    }
}
