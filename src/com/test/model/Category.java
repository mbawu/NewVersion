package com.test.model;

public class Category {

	
	private String category_id;
	private String CacheID;
	private String parent_catid;
	private String category_level;
	private String category_name;
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCacheID() {
		return CacheID;
	}
	public void setCacheID(String cacheID) {
		CacheID = cacheID;
	}
	public String getParent_catid() {
		return parent_catid;
	}
	public void setParent_catid(String parent_catid) {
		this.parent_catid = parent_catid;
	}
	public String getCategory_level() {
		return category_level;
	}
	public void setCategory_level(String category_level) {
		this.category_level = category_level;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	
}
