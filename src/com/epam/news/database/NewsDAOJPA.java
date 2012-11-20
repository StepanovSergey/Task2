package com.epam.news.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
    private static final String DELETE_QUERY = "DELETE FROM News WHERE id IN(";

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
    public void setEntityManagerWrapper(
	    EntityManagerFactoryWrapper entityManagerWrapper) {
	this.entityManagerWrapper = entityManagerWrapper;
    }

    @Override
    public List<News> getAll() {
	List<News> list = new ArrayList<News>();
	EntityManager em = entityManagerWrapper.getEntityManager();
	list = (List<News>) em.createNamedQuery("news.findAll").getResultList();
	em.close();
	return list;
    }

    @Override
    public News getById(int id) {
	EntityManager em = entityManagerWrapper.getEntityManager();
	News news = em.find(News.class, id);
	em.close();
	return news;
    }

    @Override
    public int addNews(News news) {
	EntityManager em = entityManagerWrapper.getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	em.persist(news);
	em.flush();
	transaction.commit();
	em.close();
	int id = news.getId();
	id++;
	news.setId(id);
	return id;
    }

    @Override
    public int updateNews(News news) {
	EntityManager em = entityManagerWrapper.getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	em.merge(news);
	transaction.commit();
	em.close();
	return 1;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	EntityManager em = entityManagerWrapper.getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	String deleteQuery = NewsDAO.createDeleteManyNewsQuery(DELETE_QUERY,
		ids);
	Query query = em.createQuery(deleteQuery);
	int result = query.executeUpdate();
	transaction.commit();
	em.close();
	return result;
    }

}
