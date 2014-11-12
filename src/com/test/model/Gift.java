package com.test.model;

import java.io.Serializable;

public class Gift  implements Serializable{

	private String name;//赠品名称
	private String ID;//该赠品ID
	private boolean isChecked;//是否被选择
	private String num;//赠品数量
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
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

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	
}
