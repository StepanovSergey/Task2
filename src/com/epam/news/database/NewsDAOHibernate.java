package com.epam.news.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.epam.news.bean.News;
import com.epam.news.utils.HibernateUtil;

/**
 * This class provides dao working with hibernate
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class NewsDAOHibernate extends ANewsDAO implements INewsDao {
    private static final Logger logger = Logger.getLogger(NewsDAOHibernate.class);
    private static HibernateUtil hibernateUtil;
    private static final String NEWS_DATE_COLUMN = "date";
    private static SessionFactory sessions = HibernateUtil.getSessionFactory();

    /**
     * @return the hibernateUtil
     */
    public static HibernateUtil getHibernateUtil() {
	return hibernateUtil;
    }

    /**
     * @param hibernateUtil
     *            the hibernateUtil to set
     */
    public static void setHibernateUtil(HibernateUtil hibernateUtil) {
	NewsDAOHibernate.hibernateUtil = hibernateUtil;
    }

    @Override
    public List<News> getAll() {
	Session session = sessions.getCurrentSession();
	List<News> list = new ArrayList<News>();
	Transaction transaction = session.beginTransaction();
	Criteria criteria = session.createCriteria(News.class);
	criteria = criteria.addOrder(Order.desc(NEWS_DATE_COLUMN));
	list = (List<News>) criteria.list();
	transaction.commit();
	return list;
    }

    @Override
    public News getById(int id) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	News news = new News();
	news = (News) session.get(News.class, id);
	transaction.commit();
	return news;
    }

    @Override
    public int addNews(News news) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	session.save(news);
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
	}
	int id = news.getId();
	return id;
    }

    @Override
    public int updateNews(News news) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	news = (News) session.merge(news);
	session.update(news);
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
	}
	return 1;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	Query query = session.getNamedQuery("deleteManyNewsQuery").setParameterList("deleteIds", ids);
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
	}
	return result;
    }

}
