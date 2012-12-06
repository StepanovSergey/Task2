package com.epam.news.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.epam.news.bean.News;
import com.epam.news.utils.EntityManagerFactoryWrapper;

/**
 * This class provides news dao with JPA
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class NewsDAOJPA extends ANewsDAO implements INewsDao {
    private static final Logger logger = Logger.getLogger(NewsDAOJPA.class);
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
	Integer id;
	try {
	    transaction.commit();
	    id = news.getId();
	} catch (Exception e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	    try {
		transaction.rollback();
	    } catch (Exception ex) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(ex.getMessage(), ex);
		}
	    }
	    return 0;
	} finally {
	    em.close();
	}
	return ++id;
    }

    @Override
    public int updateNews(News news) {
	EntityManager em = entityManagerWrapper.getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	em.merge(news);
	try {
	    transaction.commit();
	} catch (HibernateException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	    try {
		transaction.rollback();
	    } catch (Exception ex) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(ex.getMessage(), ex);
		}
	    }
	    return 0;
	} finally {
	    em.close();
	}
	return 1;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	List<Integer> idsList = new ArrayList<Integer>();
	boolean isAdded = idsList.addAll(Arrays.asList(ids));
	if (isAdded) {
	    EntityManager em = entityManagerWrapper.getEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    transaction.begin();
	    Query query = em.createNamedQuery("deleteManyNewsQuery")
		    .setParameter("deleteIds", idsList);
	    int result = query.executeUpdate();
	    try {
		transaction.commit();
	    } catch (HibernateException e) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(e.getMessage(), e);
		}
		try {
		    transaction.rollback();
		} catch (Exception ex) {
		    if (logger.isEnabledFor(Level.ERROR)) {
			logger.error(ex.getMessage(), ex);
		    }
		}
		return 0;
	    } finally {
		em.close();
	    }
	    return result;

	} else {
	    return 0;
	}
    }
}
