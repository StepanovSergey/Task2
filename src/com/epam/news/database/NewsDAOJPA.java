package com.epam.news.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.epam.news.bean.News;
import com.epam.news.utils.EntityManagerFactoryWrapper;

/**
 * This class provides news dao with JPA
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class NewsDAOJPA implements INewsDao {
    private EntityManagerFactoryWrapper entityManagerWrapper;

    /**
     * @return the entityManager
     */
    public EntityManagerFactoryWrapper getEntityManagerWrapper() {
	return entityManagerWrapper;
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    public void setEntityManagerWrapper(EntityManagerFactoryWrapper entityManagerWrapper) {
	this.entityManagerWrapper = entityManagerWrapper;
    }

    @Override
    public List<News> getAll() {
	List<News> list = new ArrayList<News>();
	EntityManager em = entityManagerWrapper.getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	list = (List<News>)em.createNamedQuery("news.findAll").getResultList();
	return list;
    }

    @Override
    public News getById(int id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int addNews(News news) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int updateNews(News news) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	// TODO Auto-generated method stub
	return 0;
    }

}
