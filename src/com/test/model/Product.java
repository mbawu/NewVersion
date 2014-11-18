package com.test.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.test.base.MyApplication;
import com.test.base.Url;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7060210544600464481L;
	private String skID;// 秒杀ID
	private String id; // 商品id
	private String code;// 商品编号
	private String shortName;// 商品短名称
	private String name; // 商品名称
	private String SKName;// 秒杀名称
	private String referencePrice;// 参考价
	private String storePrice; // 商品实际价格
	private String imgPath; // 商品图片路径
	private String totalPrice;// 该商品的总价
	// private String imgOriginalPath;//商品图片原始路径
	private String orderImg;// 显示在订单上的该产品的图片地址
	private ArrayList<String> imgs;
	private String subProductName;// 商品子标题
	private String discountCash;// 优惠了多少钱
	private String discount;// 折扣
	private Attribute attribute;// 商品属性
	private String time;// 秒杀商品当前时间戳
	private String outEndTime;// 秒杀商品结束时间戳
	private String SKPrice;// 秒杀价格
	private ArrayList<Object> attributes;// 商品属性集合
	private String store_id;
	private String num;// 商品数量
	private String date;// 购买商品的日期
	private String nature;// 商品性质：实物还是虚拟
	private String buy_type;// 购买类型：1正常购买，2秒杀
	private String freight;// 运费
	private String inventory;// 商品库存
	private String note;// 商品备注
	private boolean checked;// 该商品是否被选中
	private ArrayList<Coupon> coupons;
	private String OrderItemID;// 子订单ID
	private String priceID;// 属性ID
	// private String gift_name;//商品赠品
	private ArrayList<Object> gifts;// 赠品集合
	private Gift gift;// 该商品的所选赠品
	private boolean haveAttribute = true;// 是否有属性
	private boolean haveGift=true;//是否有赠品
	private boolean editNum=false;//是否编辑商品数量
	private boolean editFinished=false;

	

	public boolean isEditFinished() {
		return editFinished;
	}

	public void setEditFinished(boolean editFinished) {
		this.editFinished = editFinished;
	}

	public boolean isEditNum() {
		return editNum;
	}

	public void setEditNum(boolean editNum) {
		this.editNum = editNum;
	}

	public boolean isHaveAttribute() {
		return haveAttribute;
	}

	public void setHaveAttribute(boolean haveAttribute) {
		this.haveAttribute = haveAttribute;
	}

	public boolean isHaveGift() {
		return haveGift;
	}

	public void setHaveGift(boolean haveGift) {
		this.haveGift = haveGift;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	// public String getGift_name() {
	// return gift_name;
	// }
	//
	//
	// public void setGift_name(String gift_name) {
	// this.gift_name = gift_name;
	// }

	public String getPriceID() {
		return priceID;
	}

	public void setPriceID(String priceID) {
		this.priceID = priceID;
	}

	public String getOrderItemID() {
		return OrderItemID;
	}

	public void setOrderItemID(String orderItemID) {
		OrderItemID = orderItemID;
	}

	private String OrderCode;// 订单编码

	public String getOrderCode() {
		return OrderCode;
	}

	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}

	public ArrayList<Coupon> getCoupons() {
		if (coupons.size() == 0) {
			Coupon coupon = new Coupon();
			coupon.setCouponID("0");
			coupon.setStoreID("");
			coupon.setProductID("");
			coupon.setProductName("");
			coupon.setPriceLine("");
			coupon.setPrice("");
			coupons.add(coupon);
		}
		return coupons;
	}

	public void addCoupon(Coupon coupon) {
		coupons.add(coupon);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getShortName() {
		return MyApplication.limitString(name);
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getNote() {
		if (note == null)
			return "";
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStore_id() {
		return "12";
	}

	public String getSKName() {
		return SKName;
	}

	public void setSKName(String sKName) {
		SKName = sKName;
	}

	public String getSkID() {
		return skID;
	}

	public void setSkID(String skID) {
		this.skID = skID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSKPrice() {
		return SKPrice;
	}

	public void setSKPrice(String sKPrice) {
		SKPrice = sKPrice;
	}

	public String getOutEndTime() {
		return outEndTime;
	}

	public void setOutEndTime(String outEndTime) {
		this.outEndTime = outEndTime;
	}

	public String getDiscountCash() {
		return discountCash;
	}

	public void setDiscountCash(String discountCash) {
		this.discountCash = discountCash;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public Product() {
		imgs = new ArrayList<String>();
		attributes = new ArrayList<Object>();
		coupons = new ArrayList<Coupon>();
		gifts = new ArrayList<Object>();
	}

	public ArrayList<Object> getGifts() {
		return gifts;
	}

	public void setGifts(Gift gift) {
		gifts.add(gift);
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		for (int i = 0; i < gifts.size(); i++) {
			Gift temp = (Gift) gifts.get(i);
			if (temp.equals(gift))
				temp.setChecked(true);
			else
				temp.setChecked(false);
		}
		this.gift = gift;
	}

	public String getImgPath() {
		return imgPath;
	}

	public String getOrderImg() {
		return orderImg;
	}

	public void setImgPath(String imgPath) {
		if (orderImg == null)
			orderImg = imgPath;
		this.imgPath = Url.URL_IMGPATH + imgPath;
		imgs.add(this.imgPath);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(String referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(String storePrice) {
		this.storePrice = storePrice;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public ArrayList<String> getImgs() {
		return imgs;
	}

	public ArrayList<Object> getAttributes() {
		return attributes;
	}

	public ArrayList<Object> setAttributes(Attribute attribute) {
		attributes.add(attribute);
		return attributes;
	}

	public void setAttribute(Attribute attribute) {
		attribute.setChecked(true);
		this.attribute = attribute;
	}

	public void clearAttributeStat() {
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = (Attribute) attributes.get(i);
			attribute.setChecked(false);
		}
	}
}
