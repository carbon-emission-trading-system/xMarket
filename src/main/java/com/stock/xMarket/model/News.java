package com.stock.xMarket.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//公告展示端：标题、日期、内容

@Entity
@Table(name="news")
public class News {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="news_id")
	private int newsId;
	
	@Column(name = "news_type")
	private String newsType;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "date")
	private Date date;

	@Column(name = "news_content")
	private String newsContent;

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsType() {
		return newsType;
	}
	
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	
}