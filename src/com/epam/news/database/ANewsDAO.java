package com.epam.news.database;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public abstract class ANewsDAO {
    
    /**
     * Create query for deleting many news by one query
     * 
     * @param ids
     *            ids of news for deleting
     * @return string query for deleting many news
     */
    protected static String createDeleteManyNewsQuery(String initQuery, Integer[] ids) {
	StringBuffer query = new StringBuffer(initQuery);
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
}
