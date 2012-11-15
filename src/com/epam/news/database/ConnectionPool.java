package com.epam.news.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.epam.news.utils.DataBaseParameters;

/**
 * This class provides connection pool
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private static DataBaseParameters dbParameters;
    private static Queue<Connection> occupiedConnections = new ConcurrentLinkedQueue<Connection>();
    private static Queue<Connection> freeConnections = new ConcurrentLinkedQueue<Connection>();
    private static Semaphore semaphore;

    /**
     * Initialize connection pool
     */
    public static void init() {
	semaphore = new Semaphore(dbParameters.getPoolSize(), true);
	try {
	    Class.forName(dbParameters.getDriverClass());
	    for (int i = 0; i < dbParameters.getPoolSize(); i++) {
		Connection connection = openConnection();
		freeConnections.add(connection);
	    }
	} catch (ClassNotFoundException e) {
	    log.error(e.getMessage(), e);
	}
    }

    /**
     * @return the dbParameters
     */
    public static DataBaseParameters getDbParameters() {
        return dbParameters;
    }

    /**
     * @param dbParameters the dbParameters to set
     */
    public static void setDbParameters(DataBaseParameters dbParameters) {
        ConnectionPool.dbParameters = dbParameters;
    }

    /**
     * Get connection from pool. If there is no connections available, thread
     * will wait for free connection
     * 
     * @return connection
     */
    public static Connection getConnection() {
	Connection connection = null;
	try {
	    semaphore.acquire();
	    connection = freeConnections.poll();
	    occupiedConnections.add(connection);
	} catch (InterruptedException e) {
	    log.error(e.getMessage(), e);
	}
	return connection;
    }

    private static Connection openConnection() {
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection(dbParameters.getURI(),
		    dbParameters.getUser(), dbParameters.getPassword());
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	}
	return connection;
    }

    /**
     * Releases used connection to pool. If released connection is closed, pool
     * create new connection.
     * 
     * @param connection
     *            connection to release
     */
    public static void releaseConnection(Connection connection) {
	occupiedConnections.remove(connection);
	try {
	    if (connection.isClosed()) {
		connection = openConnection();
	    }
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	} finally {
	    freeConnections.add(connection);
	    semaphore.release();
	}
    }

    /**
     * Close all connections of application
     */
    public static void closeAllConnections() {
	closeConnectionsOfQueue(freeConnections);
	closeConnectionsOfQueue(occupiedConnections);
    }

    private static void closeConnectionsOfQueue(Queue<Connection> queue) {
	Iterator<Connection> iterator = queue.iterator();
	while (iterator.hasNext()) {
	    Connection connection = iterator.next();
	    try {
		connection.close();
	    } catch (SQLException e) {
		log.error(e.getMessage(), e);
	    }
	}
    }
}
