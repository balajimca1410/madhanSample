package com.balaji.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="books")
public class BookModel {
	@Id
	private int userId;
	private String query; 
	private long request;
	private String title;
	private String summary;
	private String publisher;
	
	
	
	
	
	public BookModel(int userId, String query, long request, String title, String summary, String publisher) {
		super();
		this.userId = userId;
		this.query = query;
		this.request = request;
		this.title = title;
		this.summary = summary;
		this.publisher = publisher;
	}
	public BookModel() {
		// TODO Auto-generated constructor stub
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public long getRequest() {
		return request;
	}
	public void setRequest(long request) {
		this.request = request;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Override
	public String toString() {
		return "BookModel [userId=" + userId + ", query=" + query + ", request=" + request + ", title=" + title
				+ ", summary=" + summary + ", publisher=" + publisher + "]";
	}
	

}
