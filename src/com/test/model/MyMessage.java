package com.test.model;

import java.io.Serializable;

public class MyMessage implements Serializable{

	private String subject;
	private String content;
	private String creatTime;
	private String messStateID;
	private String storeID;
	private boolean showCheckBox;//是否显示可删除的复选框按钮
	private boolean checked;//是否被选中
	
	
	public boolean isShowCheckBox() {
		return showCheckBox;
	}
	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getMessStateID() {
		return messStateID;
	}
	public void setMessStateID(String messStateID) {
		this.messStateID = messStateID;
	}
	public String getStoreID() {
		return storeID;
	}
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	
}
