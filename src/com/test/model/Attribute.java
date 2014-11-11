package com.test.model;

import java.io.Serializable;

public class Attribute  implements Serializable{

	private String name;//属性名称
	private String price;//该属性对应的价格
	private String ID;//该属性ID
	private boolean isChecked;//是否被选择
	
	
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	
}
