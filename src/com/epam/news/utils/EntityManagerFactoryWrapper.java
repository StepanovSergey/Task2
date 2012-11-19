package com.epam.news.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class provides jpa entity manager factory
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class EntityManagerFactoryWrapper {
    private static final String UNIT_NAME = "NewsManagement";
    private EntityManagerFactory emf;
    
    public void init() {
        emf = Persistence.createEntityManagerFactory(UNIT_NAME);
    }
   
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
