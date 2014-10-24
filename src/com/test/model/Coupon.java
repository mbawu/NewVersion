package com.test.model;

import java.io.Serializable;

public class Coupon implements Serializable{

	private String CouponID;//优惠券ID
	private String StoreID;//商家ID
	private String ProductID;//商品ID
	private String ProductName;//商品名称
	private String PriceLine;//最低消费价格
	private String Price;//优惠券面值
	private String oldCouponPrice;//记录修改同一个商品的优惠券的原有优惠券价格
	private String oldRealPrice;//记录修改实付款之前的实付款价格
	private String start_time;//开始日期
	private String end_time;//结束日期
	private String flag;//状态
	private String validity;//是否过期1有效2过期
	private String orderID;//订单ID
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getOldCouponPrice() {
		return oldCouponPrice;
	}
	public void setOldCouponPrice(String oldCouponPrice) {
		this.oldCouponPrice = oldCouponPrice;
	}
	public String getOldRealPrice() {
		return oldRealPrice;
	}
	public void setOldRealPrice(String oldRealPrice) {
		this.oldRealPrice = oldRealPrice;
	}
	public String getCouponID() {
		return CouponID;
	}
	public void setCouponID(String couponID) {
		CouponID = couponID;
	}
	public String getStoreID() {
		return StoreID;
	}
	public void setStoreID(String storeID) {
		StoreID = storeID;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getPriceLine() {
		return PriceLine;
	}
	public void setPriceLine(String priceLine) {
		PriceLine = priceLine;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}

	
}
