package com.test.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{

	private String OrderID;//主订单ID
	private String UserName;//提交订单的用户名
	private String OrderSubject;//主题
	private String ProductNum;//商品数量
	private String TotalPrice;//总价
	private String OrderCode;//订单号
	private String RealName;//收货人姓名
	private String Mobile;//收货人手机号
	private String Address;//收货人详细地址
	private String Flag;//订单状态。1已下单，2已取消，3派送中，4货物拒收，5交易完成
	private String OutCreateTime;//下单时间
	private String isPay;//是否支付0未支付1已支付
	private String comments;//评论状态 1已评论，0未评论
	private String totalRecord;//总条数
	private String orderType;//要查看的订单类型：1.待付款，2.待发货，3.待收货，4.已完成
	private String payWay;//payway		付款方式：1在线支付，2货到付款
	private ArrayList<Object> products;
	private String Freight;//订单运费

	public String getFreight() {
		return Freight;
	}
	public void setFreight(String freight) {
		Freight = freight;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getOrderSubject() {
		return OrderSubject;
	}
	public void setOrderSubject(String orderSubject) {
		OrderSubject = orderSubject;
	}
	public String getProductNum() {
		return ProductNum;
	}
	public void setProductNum(String productNum) {
		ProductNum = productNum;
	}
	public String getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getOrderCode() {
		return OrderCode;
	}
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public String getOutCreateTime() {
		return OutCreateTime;
	}
	public void setOutCreateTime(String outCreateTime) {
		OutCreateTime = outCreateTime;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}
	public ArrayList<Object> getProducts() {
		return products;
	}
	public void addProduct(Product product) {
		products.add(product);
	}
	
	public Order()
	{
		products=new ArrayList<Object>();
	}
}
