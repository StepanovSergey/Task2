package com.epam.news.bean;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class provides news bean
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
@NamedQueries({
@NamedQuery(name = "news.findAll", query = "FROM News n ORDER BY date DESC, id DESC"),
@NamedQuery(name="deleteManyNewsQuery", query="DELETE FROM News WHERE id IN(:deleteIds)")
})
@Entity
@Table(name = "NEWS")
public class News implements Serializable {
    private static final long serialVersionUID = 9153424252861600528L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "newsSequence")
    @SequenceGenerator(name = "newsSequence", sequenceName = "NEWS_SEQ")
    private int id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "news_date")
    private Date date;
    @Column(name = "BRIEF")
    private String brief;
    @Column(name = "CONTENT")
    private String content;

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the brief
     */
    public String getBrief() {
	return brief;
    }

    /**
     * @param brief
     *            the brief to set
     */
    public void setBrief(String brief) {
	this.brief = brief;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((brief == null) ? 0 : brief.hashCode());
	result = prime * result + ((content == null) ? 0 : content.hashCode());
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	result = prime * result + id;
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	News other = (News) obj;
	if (brief == null) {
	    if (other.brief != null) {
		return false;
	    }
	} else if (!brief.equals(other.brief)) {
	    return false;
	}
	if (content == null) {
	    if (other.content != null) {
		return false;
	    }
	} else if (!content.equals(other.content)) {
	    return false;
	}
	if (date == null) {
	    if (other.date != null) {
		return false;
	    }
	} else if (!date.equals(other.date)) {
	    return false;
	}
	if (id != other.id) {
	    return false;
	}
	if (title == null) {
	    if (other.title != null) {
		return false;
	    }
	} else if (!title.equals(other.title)) {
	    return false;
	}
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "News [id=" + id + ", title=" + title + ", date=" + date
		+ ", brief=" + brief + ", content=" + content + "]";
    }

}
