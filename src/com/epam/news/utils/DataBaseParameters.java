package com.epam.news.utils;

/**
 * This class provides data base parameters for establishing connections.
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class DataBaseParameters {
    private String driverClass;
    private String URI;
    private String user;
    private String password;
    private int poolSize;

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
	return driverClass;
    }

    /**
     * @param driverClass
     *            the driverClass to set
     */
    public void setDriverClass(String driverClass) {
	this.driverClass = driverClass;
    }

    /**
     * @return the uRI
     */
    public String getURI() {
	return URI;
    }

    /**
     * @param uRI
     *            the uRI to set
     */
    public void setURI(String uRI) {
	URI = uRI;
    }

    /**
     * @return the user
     */
    public String getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
	this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the poolSize
     */
    public int getPoolSize() {
	return poolSize;
    }

    /**
     * @param poolSize
     *            the poolSize to set
     */
    public void setPoolSize(int poolSize) {
	this.poolSize = poolSize;
    }

}
