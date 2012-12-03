package com.epam.news.database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
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
    private static HibernateUtil hibernateUtil;
    private static final String NEWS_DATE_COLUMN = "date";
    private static final String DELETE_MANY_NEWS_QUERY = "DELETE FROM News WHERE id IN(";
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
	session.beginTransaction();
	List<News> list = new ArrayList<News>();
	Criteria criteria = session.createCriteria(News.class);
	criteria = criteria.addOrder(Order.desc(NEWS_DATE_COLUMN));
	list = (List<News>)criteria.list();
	return list;
    }

    @Override
    public News getById(int id) {
	Session session = sessions.getCurrentSession();
	session.beginTransaction();
	News news = new News();
	news = (News) session.get(News.class, id);
	return news;
    }

    @Override
    public int addNews(News news) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	session.save(news);
	transaction.commit();
	int id = news.getId();
	return id;
    }

    @Override
    public int updateNews(News news) {
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	news = (News) session.merge(news);
	session.update(news);
	transaction.commit();
	return 1;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	String deleteQuery = NewsDAO.createDeleteManyNewsQuery(
		DELETE_MANY_NEWS_QUERY, ids);
	Session session = sessions.getCurrentSession();
	Transaction transaction = session.beginTransaction();
	Query query = session.createQuery(deleteQuery);
	int result = query.executeUpdate();
	transaction.commit();
	return result;
    }

}
