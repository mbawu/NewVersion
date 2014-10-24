package com.test.model;

public class Address {
	
	private String addressID;
	private String province_id;
	private String city_id;
	private String county_id;
	private String province_name;
	private String city_name;
	private String county_name;
	private String uid;
	private String realname;//收货人姓名
	private String mobile; //手机号
	private String address; //详细地址
	private String tag;//是否为默认地址
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 返回所有城市名加详细地址的全称
	 * @return
	 */
	public String getStreet()
	{
		return province_name+city_name+county_name+address;
	}
	
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCounty_name() {
		return county_name;
	}
	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
