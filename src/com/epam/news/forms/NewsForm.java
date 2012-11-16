package com.epam.news.forms;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.epam.news.bean.News;
import com.epam.news.utils.DateConverter;

/**
 * This class provides news form
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class NewsForm extends ActionForm {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = 1216355468781937508L;
    private static final Logger log = Logger.getLogger(NewsForm.class);
    private static final String RESOURCES = "/com/epam/news/resources/ApplicationResource";
    private static final String datePattern = "date.pattern";
    private List<News> newsList = new ArrayList<News>();
    private News news = new News();
    private String lang;
    private Integer[] selectedItems;
    private String dateString;
    private Locale locale;

    /**
     * @return the dateString
     */
    public String getDateString() {
	return dateString;
    }

    /**
     * @param news
     *            the news to set
     */
    public void setNews(News news) {
	if (news.getDate() != null) {
	    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES, locale);
	    String pattern = bundle.getString(datePattern);
	    DateFormat dateFormat = new SimpleDateFormat(pattern);
	    dateString = dateFormat.format(news.getDate());
	}
	this.news = news;
    }

    /**
     * @param dateString
     *            the dateString to set
     */
    public void setDateString(String dateString) {
	if (dateString != null && !dateString.isEmpty()) {
	    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES, locale);
	    String pattern = bundle.getString(datePattern);
	    DateFormat dateFormat = new SimpleDateFormat(pattern);
	    try {
		java.util.Date date = dateFormat.parse(dateString);
		Date sqlDate = DateConverter.convertToDateSql(date);
		news.setDate(sqlDate);
	    } catch (ParseException e) {
		if (log.isDebugEnabled()) {
		    log.debug(e.getMessage(), e);
		}
	    }
	}
	this.dateString = dateString;
    }

    /**
     * @return the selectedItems
     */
    public Integer[] getSelectedItems() {
	return selectedItems;
    }

    /**
     * @param selectedItems
     *            the selectedItems to set
     */
    public void setSelectedItems(Integer[] selectedItems) {
	this.selectedItems = selectedItems;
    }

    /**
     * @return the newsList
     */
    public List<News> getNewsList() {
	return newsList;
    }

    /**
     * @param newsList
     *            the newsList to set
     */
    public void setNewsList(List<News> newsList) {
	this.newsList = newsList;
    }

    /**
     * @return the news
     */
    public News getNews() {
	return news;
    }

    /**
     * @return the lang
     */
    public String getLang() {
	return lang;
    }

    /**
     * @param lang
     *            the lang to set
     */
    public void setLang(String lang) {
	this.lang = lang;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
	return locale;
    }

    /**
     * @param locale
     *            the locale to set
     */
    public void setLocale(Locale locale) {
	this.locale = locale;
    }
}
