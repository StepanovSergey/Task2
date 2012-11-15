package com.epam.news.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.news.bean.News;

/**
 * This class provides DAO implementation
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class NewsDAO implements INewsDao {
    private static final Logger log = Logger.getLogger(NewsDAO.class);
    private static final String getAllQuery = "SELECT * FROM news ORDER BY news_date desc";
    private static final String getByIdQuery = "SELECT * FROM news WHERE id=?";
    private static final String addNewsQuery = "INSERT INTO news(title,news_date,brief,content) VALUES (?,?,?,?)";
    private static final String updateNewsQuery = "UPDATE news SET title=?, news_date=?, brief=?, content=? WHERE id=?";
    private static final String deleteManyNewsQuery = "DELETE FROM news WHERE id IN (";
    private static final String getByTitleNewsQuery = "SELECT title FROM news WHERE title = ?";

    @Override
    public List<News> getAll() {
	List<News> allNews = new ArrayList<News>();
	Connection connection = ConnectionPool.getConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(getAllQuery);
	    while (resultSet.next()) {
		News news = setParameters(resultSet);
		allNews.add(news);
	    }
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(statement, null, resultSet);
	    ConnectionPool.releaseConnection(connection);
	}
	return allNews;
    }

    @Override
    public News getById(int id) {
	News news = new News();
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    preparedStatement = connection.prepareStatement(getByIdQuery);
	    preparedStatement.setInt(1, id);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		news = setParameters(resultSet);
	    }
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(null, preparedStatement, resultSet);
	    ConnectionPool.releaseConnection(connection);
	}
	return news;
    }

    @Override
    public int addNews(News news) {
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet set = null;
	int result = 0;
	try {
	    preparedStatement = connection.prepareStatement(addNewsQuery,
		    new String[] { "ID" });
	    preparedStatement.setString(1, news.getTitle());
	    preparedStatement.setDate(2, news.getDate());
	    preparedStatement.setString(3, news.getBrief());
	    preparedStatement.setString(4, news.getContent());
	    result = preparedStatement.executeUpdate();
	    if (result > 0) {
		set = preparedStatement.getGeneratedKeys();
		if (set.next()) {
		    return set.getInt(1);
		} else {
		    throw new SQLException("Id is not generated");
		}
	    } else {
		throw new SQLException("No news added");
	    }
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(null, preparedStatement, set);
	    ConnectionPool.releaseConnection(connection);
	}
	return result;
    }

    @Override
    public int updateNews(News news) {
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	int result = 0;
	try {
	    preparedStatement = connection.prepareStatement(updateNewsQuery);
	    preparedStatement.setString(1, news.getTitle());
	    preparedStatement.setDate(2, news.getDate());
	    preparedStatement.setString(3, news.getBrief());
	    preparedStatement.setString(4, news.getContent());
	    preparedStatement.setInt(5, news.getId());
	    result = preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(null, preparedStatement, null);
	    ConnectionPool.releaseConnection(connection);
	}
	return result;
    }

    @Override
    public int deleteManyNews(Integer[] ids) {
	Connection connection = ConnectionPool.getConnection();
	Statement statement = null;
	int result = 0;
	try {
	    statement = connection.createStatement();
	    String deleteManyNewsQuery = createDeleteManyNewsQuery(ids);
	    result = statement.executeUpdate(deleteManyNewsQuery);
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(statement, null, null);
	    ConnectionPool.releaseConnection(connection);
	}
	return result;
    }

    @Override
    public int getByTitle(String newsTitle) {
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	int result = 0;
	try {
	    preparedStatement = connection
		    .prepareStatement(getByTitleNewsQuery);
	    preparedStatement.setString(1, newsTitle);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		result++;
	    }
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    releaseResources(null, preparedStatement, null);
	    ConnectionPool.releaseConnection(connection);
	}
	return result;
    }

    /**
     * Create query for deleting many news by one query
     * 
     * @param ids
     *            ids of news for deleting
     * @return string query for deleting many news
     */
    private String createDeleteManyNewsQuery(Integer[] ids) {
	StringBuffer query = new StringBuffer(deleteManyNewsQuery);
	Integer lastId = ids[ids.length - 1];
	for (Integer id : ids) {
	    query.append(id);
	    if (lastId.equals(id)) {
		query.append(")");
	    } else {
		query.append(",");
	    }
	}
	return query.toString();
    }

    /**
     * Creates new news entity with parameters in result set
     * 
     * @param resultSet
     *            set of parameters from database
     * @return news entity with parameters
     */
    private News setParameters(ResultSet resultSet) {
	News news = new News();
	try {
	    news.setId(resultSet.getInt("id"));
	    news.setTitle(resultSet.getString("title"));
	    news.setDate(resultSet.getDate("news_date"));
	    news.setBrief(resultSet.getString("brief"));
	    news.setContent(resultSet.getString("content"));
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	}
	return news;
    }

    /**
     * Close result set, statement and prepared statement
     * 
     * @param statement
     *            statement to close
     * @param preparedStatement
     *            prepared statement to close
     * @param resultSet
     *            result set to close
     */
    private void releaseResources(Statement statement,
	    PreparedStatement preparedStatement, ResultSet resultSet) {
	if (statement != null) {
	    try {
		statement.close();
	    } catch (SQLException e) {
		log.error(e.getMessage(), e);
	    }
	}
	if (resultSet != null) {
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		log.error(e.getMessage(), e);
	    }
	}
	if (preparedStatement != null) {
	    try {
		preparedStatement.close();
	    } catch (SQLException e) {
		log.error(e.getMessage(), e);
	    }
	}
    }

}
