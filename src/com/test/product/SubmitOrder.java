package com.test.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.test.R;
import com.test.MenuBottom;
import com.test.base.CustomDialog;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.base.Url;
import com.test.model.Address;
import com.test.model.Coupon;
import com.test.utils.ConnectServer;
import com.test.utils.ErrorMsg;
import com.test.utils.NetworkAction;
import com.test.model.Product;
import com.test.product.SubmitSuccess;
import com.test.pay.PayMethod;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SubmitOrder extends NormalActivity implements OnClickListener,
		android.widget.CompoundButton.OnCheckedChangeListener {

	private Title title;// 设置标题栏
	private LinearLayout addressBigLayout;// 收货地址模块
	private TextView addressMsg;// 没有收货地址时候的提示
	private LinearLayout addressLayout;// 有收货地址时候显示的内容模块
	private TextView addressName;// 收货地址姓名
	private TextView addressPhone;// 收货地址电话
	private TextView addressStreet;// 收货地址街道
	private RadioButton online; // 在线付款按钮
	private RadioButton receive; // 货到付款按钮
	private EditText buyerMsg;//买家留言
	
	private TextView submitBtn;// 提交按钮
	// private LinearLayout defaultLayout; // 默认地址栏
	private LinearLayout couponLayout;// 优惠券模块
	// private TextView defaultNamePhone;// 默认显示姓名和手机号的文本框
	// private TextView defaultAddressTxt;// 默认显示的地址
	// private TextView changeAddressBtn;// 修改地址按钮
	private TextView noCouponTxt;// 暂无优惠券文本框
	private boolean editAddress = false;// 是否是修改地址的状态模式
	private ListView productListView;// 商品展示列表
	private MyAdapter productAdapter;
	private ArrayList<Object> products;// 商品集合
	private RadioGroup paywayGroup;
	private String payway = "1";// 付款方式：1在线支付，2货到付款
	private Address defaultAddress = null;
	private HashMap<String, Coupon> couponList;// 优惠券集合
	private String productID;// 商品ID
	private TextView totalPriceTxt;// 商品总价
	private TextView freightPriceTxt;// 运费总价
	private TextView couponPriceTxt;// 优惠的价格
	private TextView realPriceTxt;// 实付款
	private double oldRealPrice;

	private HashMap<String, String> paramterAddress;// 收货地址参数集合
	private HashMap<String, String> paramterFreight;
	private HashMap<String, String> paramtersubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		initView();
		initData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 如果是从修改地址的界面返回来的话则重新获取并刷新一下默认地址
		// if (editAddress) {
		// defaultAddress = null;
		// sendData(NetworkAction.获取收货地址列表, null);
		// editAddress = false;
		// }
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(4);
		title.setTitleTxt("确认订单");
		addressBigLayout = (LinearLayout) findViewById(R.id.address_big_layout);
		addressMsg = (TextView) findViewById(R.id.address_msg);
		addressLayout = (LinearLayout) findViewById(R.id.address_layout);
		addressName = (TextView) findViewById(R.id.submit_address_name);
		addressPhone = (TextView) findViewById(R.id.submit_address_phone);
		addressStreet = (TextView) findViewById(R.id.submit_address_street);
		buyerMsg= (EditText) findViewById(R.id.buyer_msg);
		
		totalPriceTxt = (TextView) findViewById(R.id.submit_totalprice);// 商品总价
		freightPriceTxt = (TextView) findViewById(R.id.submit_freightprice);// 运费总价
		// couponPriceTxt = (TextView) findViewById(R.id.submit_couponprice);//
		// 优惠的价格
		realPriceTxt = (TextView) findViewById(R.id.submit_realprice);// 实付款
		couponLayout = (LinearLayout) findViewById(R.id.submit_coupon_layout);// 优惠券模块
		noCouponTxt = (TextView) findViewById(R.id.submit_nocoupon_txt);// 暂无优惠券文本框
		couponList = new HashMap<String, Coupon>();
		// defaultLayout = (LinearLayout)
		// findViewById(R.id.submit_default_layout); // 默认地址栏
		// defaultNamePhone = (TextView)
		// findViewById(R.id.submit_default_name_phone);// 默认显示姓名和手机号的文本框
		// defaultAddressTxt = (TextView)
		// findViewById(R.id.submit_default_address);// 默认显示的地址
		// changeAddressBtn = (TextView)
		// findViewById(R.id.submit_change_address_btn);// 修改地址按钮
		// changeAddressBtn.setOnClickListener(this);
		submitBtn = (TextView) findViewById(R.id.submit_btn);
		submitBtn.setOnClickListener(this);
		productListView = (ListView) findViewById(R.id.submit_product_listview);
		paywayGroup = (RadioGroup) findViewById(R.id.submit_radiogroup);
		online = (RadioButton) findViewById(R.id.submit_payway_online);
		receive = (RadioButton) findViewById(R.id.submit_payway_receive);
		online.setOnCheckedChangeListener(this);
		receive.setOnCheckedChangeListener(this);
		online.setChecked(true);
	}

	private void initData() {
		Intent intent = getIntent();
		// 获取从产品详情页面传过来的单个商品或者从购物车传过来的多个商品都用ArrayList<Object>接收
		products = (ArrayList<Object>) intent.getSerializableExtra("products");
		// 获取收货地址
		paramterAddress = new HashMap<String, String>();
		paramterAddress.put("act", "addrlist");
		paramterAddress.put("sessionid", MyApplication.seskey);
		paramterAddress.put("uid", MyApplication.uid);
		paramterAddress.put("sid", MyApplication.sid);
		ConnectServer.getResualt(this, paramterAddress, NetworkAction.获取收货地址列表,
				Url.URL_USERS);
		productAdapter = new MyAdapter(this, NetworkAction.提交订单, products);
		productListView.setDivider(null);
		productListView.setAdapter(productAdapter);
		refreshListViewHeight();
		// 获取运费
		paramterFreight = new HashMap<String, String>();
		paramterFreight.put("act", "freight");
		paramterFreight.put("sid", MyApplication.sid);
		// 获取所有商品价格
		getTotalPrice();
		paramtersubmit = new HashMap<String, String>();

		// // 获取所有商品对应的有效的优惠券
		// getCouponList();
	}

	// 获取所有商品的价格
	private void getTotalPrice() {
		double tatoalPrice = 0;
		// double freightPrice = 0;
		double realPrice = 0;
		for (int i = 0; i < products.size(); i++) {
			double tempTatoalPrice = Double.valueOf(((Product) products.get(i))
					.getTotalPrice());
			// double tempFreightPrice = Double
			// .valueOf(((Product) products.get(i)).getFreight());
			tatoalPrice = tatoalPrice + tempTatoalPrice;
			// freightPrice = freightPrice + tempFreightPrice;
		}
		// realPrice = tatoalPrice + freightPrice;
		realPrice = tatoalPrice;
		totalPriceTxt.setText("￥" + String.valueOf(tatoalPrice));
		freightPriceTxt.setText("￥" + String.valueOf("0.00"));
		realPriceTxt.setText("￥" + String.valueOf(realPrice));
		paramterFreight.put("order_price", String.valueOf(tatoalPrice));
		ConnectServer.getResualt(this, paramterFreight, NetworkAction.获取运费,
				Url.URL_ORDER);
	}

	/**
	 * 获取所有商品对应的有效的优惠券
	 */
	private void getCouponList() {
		// 如果商品的数量大于0查询所有的商品对应的优惠券
		if (products.size() > 0) {
			noCouponTxt.setVisibility(View.GONE);
			for (int i = 0; i < products.size(); i++) {
				// productID=((Product)products.get(i)).getId();
				sendData(NetworkAction.查询购买商品的优惠券, products.get(i));
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.address_big_layout:// 修改地址按钮
			editAddress = true;
			// Intent intent = new Intent().setClass(this,
			// PersonAddressManager.class);
			// startActivity(intent);
			break;
		case R.id.submit_btn:// 提交按钮
			// 提交订单的时候判断一下是否有有效的收货地址了
			if (defaultAddress == null) {
				Toast.makeText(this, "还没有收货地址，请添加收货地址以后再提交", 2000).show();
				return;
			}
			paramtersubmit.put("act", "CartConfirm");
			paramtersubmit.put("sessionid", MyApplication.seskey);
			paramtersubmit.put("uid", MyApplication.uid);
			paramtersubmit.put("sid", MyApplication.sid);
			paramtersubmit.put("username", MyApplication.sp.getString("username", ""));
			paramtersubmit.put("cart_num", "" + products.size());
			paramtersubmit.put("totalMoney", ""
					+ realPriceTxt.getText().toString().substring(1));
			paramtersubmit.put("realname", defaultAddress.getRealname());
			paramtersubmit.put("mobile", defaultAddress.getMobile());
			paramtersubmit.put("address", defaultAddress.getStreet());
			paramtersubmit.put("provincesid", defaultAddress.getProvince_id());
			paramtersubmit.put("citysid", defaultAddress.getCity_id());
			paramtersubmit.put("countysid", defaultAddress.getCounty_id());
			paramtersubmit.put("payway", payway);
			paramtersubmit.put("timeid", "0");
			paramtersubmit.put("form", "2");
			paramtersubmit.put("note", buyerMsg.getText().toString());
			paramtersubmit.put("Freight", freightPriceTxt.getText().toString()
					.substring(1));
			paramtersubmit.put("PayType", "4");
			paramtersubmit.put("cartlist", orderJSON());
			ConnectServer.getResualt(this, paramtersubmit, NetworkAction.提交订单,
					Url.URL_ORDER);
			break;
		}

	}

	public void sendData(final NetworkAction request, Object object) {
		String url = null;
		HashMap<String, String> paramter = new HashMap<String, String>();
		if (request.equals(NetworkAction.获取收货地址列表)) {
			url = Url.URL_USERS;
			paramter.put("act", "addrlist");
			paramter.put("sessionid", MyApplication.seskey);
			paramter.put("uid", MyApplication.uid);
			paramter.put("sid", MyApplication.sid);
		} else if (request.equals(NetworkAction.查询购买商品的优惠券)) {
			url = Url.URL_MEMBER;
			paramter.put("act", "GetCouponByProductID");
			paramter.put("sessionid", MyApplication.seskey);
			paramter.put("uid", MyApplication.uid);
			paramter.put("sid", MyApplication.sid);
			paramter.put("product_id", ((Product) object).getId());
		} else if (request.equals(NetworkAction.提交订单)) {
			url = Url.URL_ORDER;
			paramter.put("act", "CartConfirm");
			paramter.put("sessionid", MyApplication.seskey);
			paramter.put("uid", MyApplication.uid);
			paramter.put("sid", MyApplication.sid);
			paramter.put("username", MyApplication.sp.getString("username", ""));
			paramter.put("cart_num", "" + products.size());
			// double totalMoney=0;
			// for (int i = 0; i < products.size(); i++) {
			// totalMoney=totalMoney+Double.valueOf(((Product)products.get(i)).getTotalPrice());
			// }

			// 提交订单的时候判断一下是否有有效的收货地址了
			if (defaultAddress == null) {
				Toast.makeText(this, "还没有收货地址，请添加收货地址以后再提交", 2000).show();
				return;
			}
			paramter.put("totalMoney", ""
					+ realPriceTxt.getText().toString().substring(1));
			paramter.put("realname", defaultAddress.getRealname());
			paramter.put("mobile", defaultAddress.getMobile());
			paramter.put("address", defaultAddress.getStreet());
			paramter.put("provincesid", defaultAddress.getProvince_id());
			paramter.put("citysid", defaultAddress.getCity_id());
			paramter.put("countysid", defaultAddress.getCounty_id());
			paramter.put("payway", payway);
			paramter.put("timeid", "0");
			paramter.put("form", "2");
			paramter.put("note", "");
			paramter.put("Freight", freightPriceTxt.getText().toString()
					.substring(1));
			paramter.put("PayType", "4");
			paramter.put("cartlist", orderJSON());
		} else if (request.equals(NetworkAction.获取运费)) {
			url = Url.URL_ORDER;
			paramter.put("act", "freight");
			paramter.put("sid", MyApplication.sid);
			paramter.put("order_price", object.toString());
		}
		Log.i(MyApplication.TAG, request + MyApplication.getUrl(paramter, url));
		MyApplication.client.postWithURL(url, paramter,
				new Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						try {
							Log.i(MyApplication.TAG, request + "response-->"
									+ response.toString());
							int code = response.getInt("code");
							if (response.getInt("code") == 1) {
								if (request.equals(NetworkAction.获取收货地址列表)) {

								} else if (request
										.equals(NetworkAction.查询购买商品的优惠券)) {
									JSONArray coupons = response
											.getJSONArray("list");
									if (coupons.length() > 0) {
										Log.i(MyApplication.TAG,
												"coupons-->start");
										couponLayout
												.setVisibility(View.VISIBLE);
										LinearLayout layout = new LinearLayout(
												couponLayout.getContext());
										layout.setOrientation(1);
										LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
												LayoutParams.MATCH_PARENT,
												LayoutParams.WRAP_CONTENT);
										layoutParams.setMargins(0, 6, 0, 0);
										TextView productName = new TextView(
												layout.getContext());
										productName
												.setTextColor(MyApplication.resources
														.getColor(R.color.gray));
										// productName.setPadding(0, 0, 20, 0);
										RadioGroup couponGroup = new RadioGroup(
												layout.getContext());
										couponGroup.setOrientation(1);// horizontal
																		// 0
																		// vertical
																		// 1
										for (int i = 0; i < coupons.length(); i++) {
											JSONObject couponObject = coupons
													.getJSONObject(i);
											Coupon coupon = new Coupon();
											coupon.setCouponID(couponObject
													.getString("CouponID"));
											coupon.setProductID(couponObject
													.getString("ProductID"));
											coupon.setProductName(couponObject
													.getString("ProductName"));
											coupon.setPriceLine(couponObject
													.getString("PriceLine"));
											coupon.setPrice(couponObject
													.getString("Price"));
											productName.setText(coupon
													.getProductName());
											RadioGroup.LayoutParams layoutParams1 = new RadioGroup.LayoutParams(
													LayoutParams.WRAP_CONTENT,
													LayoutParams.WRAP_CONTENT);
											layoutParams1.setMargins(30, 7, 0,
													0);
											RadioButton couponRadio = (RadioButton) LayoutInflater
													.from(couponGroup
															.getContext())
													.inflate(
															R.layout.radio_item,
															null);
											// couponRadio.setBackgroundDrawable(MyApplication.resources.getDrawable(R.drawable.radio_item));
											// couponRadio.setButtonDrawable(null);
											couponRadio.setTag(coupon);
											couponRadio.setId(i);
											couponRadio.setTextSize(14);
											couponRadio.setText("满"
													+ coupon.getPriceLine()
													+ "元返" + coupon.getPrice()
													+ "元优惠券");
											couponRadio
													.setTextColor(MyApplication.resources
															.getColor(R.color.gray));
											couponGroup.addView(couponRadio,
													layoutParams1);
											// couponList.put(coupon.getProductID(),
											// coupon);
											// couponList.add(coupon);
										}
										couponGroup
												.setOnCheckedChangeListener(new OnCheckedChangeListener() {

													@Override
													public void onCheckedChanged(
															RadioGroup group,
															int checkedId) {
														RadioButton couponRadio = (RadioButton) group
																.getChildAt(checkedId);
														Coupon coupon = (Coupon) couponRadio
																.getTag();
														boolean exist = false;
														// 检查优惠券列表中是否有选择该商品对应的有优惠券
														Iterator iter = couponList
																.entrySet()
																.iterator();
														while (iter.hasNext()) {
															Map.Entry entry = (Map.Entry) iter
																	.next();
															Object key = entry
																	.getKey();
															if (coupon
																	.getProductID()
																	.equals(key
																			.toString()))
																exist = true;
															// Object val =
															// entry.getValue();
														}
														// 如果是第一次选择该商品的优惠券
														if (!exist) {
															// 设置修改优惠券价格之前的优惠券优惠的价格
															coupon.setOldCouponPrice(couponPriceTxt
																	.getText()
																	.toString()
																	.substring(
																			1));
															// 设置修改实付款之前的实付款价格
															coupon.setOldRealPrice(realPriceTxt
																	.getText()
																	.toString()
																	.substring(
																			1));
															// 更新页面上优惠券优惠的最新价格
															double couponPrice = Double
																	.valueOf(couponPriceTxt
																			.getText()
																			.toString()
																			.substring(
																					1));
															couponPrice = couponPrice
																	+ Double.valueOf(coupon
																			.getPrice());
															couponPriceTxt.setText("￥"
																	+ String.valueOf(couponPrice));
															// 更新页面上实付款的价格
															double realPrice = Double
																	.valueOf(realPriceTxt
																			.getText()
																			.toString()
																			.substring(
																					1));
															realPrice = realPrice
																	- Double.valueOf(coupon
																			.getPrice());
															realPriceTxt.setText("￥"
																	+ String.valueOf(realPrice));
															// 加入到优惠券集合中
															couponList.put(
																	coupon.getProductID(),
																	coupon);

														} else {

															Coupon oldCoupon = couponList.get(coupon
																	.getProductID());
															// Toast.makeText(SubmitOrder.this,
															// "oldCoupon==>"+oldCoupon.getPrice(),
															// 2000).show();
															double couponPrice = Double
																	.valueOf(oldCoupon
																			.getOldCouponPrice());
															couponPrice = couponPrice
																	+ Double.valueOf(coupon
																			.getPrice());
															couponPriceTxt.setText("￥"
																	+ String.valueOf(couponPrice));

															double realPrice = Double
																	.valueOf(oldCoupon
																			.getOldRealPrice());
															realPrice = realPrice
																	- Double.valueOf(coupon
																			.getPrice());
															realPriceTxt.setText("￥"
																	+ String.valueOf(realPrice));
														}
													}
												});
										layout.addView(productName);
										layout.addView(couponGroup);
										couponLayout.addView(layout);
									}
								} else if (request.equals(NetworkAction.提交订单)) {
									// 删除购物车中提交成功的商品
									Log.i(MyApplication.TAG, "start");
									for (int i = 0; i < MyApplication.shopCartList
											.size(); i++) {
										Product product = (Product) MyApplication.shopCartList
												.get(i);
										if (product.isChecked()) {
											MyApplication.shopCartList
													.remove(i);

											if (MyApplication.shopCartList
													.size() == 1)
												MyApplication.shopCartList
														.clear();
										}
									}
									Log.i(MyApplication.TAG, "sub suc->"
											+ MyApplication.shopCartList.size());
									try {
										MyApplication.shopCartManager
												.saveProducts(MyApplication.shopCartList);
									} catch (Exception e) {
										// TODO: handle exception
									}
									// 如果是在线支付的话跳转到支付页面
									Intent intent = new Intent();
									if (payway.equals("1")) {
										Toast.makeText(SubmitOrder.this,
												"提交订单成功", 2000).show();
										intent.setClass(SubmitOrder.this,
												PayMethod.class);
										intent.putExtra("subject",
												response.getString("subject"));
										intent.putExtra("price", realPriceTxt
												.getText().toString()
												.substring(1));
										intent.putExtra("oid",
												response.getString("NEWID"));
									}
									// 如果是货到付款的话跳转到提交订单成功页面
									else if (payway.equals("2")) {
										intent.setClass(SubmitOrder.this,
												SubmitSuccess.class);
									}
									startActivity(intent);
									finish();
									// showResult();
								}
							} else {
								Toast.makeText(
										SubmitOrder.this,
										request
												+ ErrorMsg.getErrorMsg(request,
														code), 2000).show();
							}
						} catch (JSONException e) {
							Log.i(MyApplication.TAG, request
									+ "JSONException-->" + e.getMessage());
						}
					}

				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(MyApplication.TAG, request + "onErrorResponse-->"
								+ error.getMessage());
					}
				});
	}

	/**
	 * 
	 */
	public String orderJSON() {
		StringBuffer json = new StringBuffer();
		// 信息集合，购买的商品信息
		json.append("[");
		for (int i = 0; i < products.size(); i++) {
			Product product = (Product) products.get(i);
			if (i > 0)
				json.append(",");
			json.append("{");
			json.append("\"product_id\":\"").append(product.getId())
					.append("\"");
			if (product.isHaveGift())
				json.append(",\"gift_name\":\"")
						.append(product.getGift().getName()).append("\"");
			json.append(",\"product_num\":\"").append(product.getNum())
					.append("\"");
			json.append(",\"store_price\":\"").append(product.getStorePrice())
					.append("\"");
			json.append(",\"reference_price\":\"")
					.append(product.getReferencePrice()).append("\"");
			json.append(",\"product_photo\":\"").append(product.getOrderImg())
					.append("\"");
			json.append(",\"totalPrice\":\"").append(product.getTotalPrice())
					.append("\"");
			json.append(",\"product_name\":\"").append(product.getName())
					.append("\"");
			json.append(",\"date\":\"").append(product.getDate()).append("\"");
			json.append(",\"Nature\":\"").append(product.getNature())
					.append("\"");
			if (product.isHaveAttribute())
				json.append(",\"attribute\":\"")
						.append(product.getAttribute().getName()).append("\"");
			else
				json.append(",\"attribute\":\"").append("").append("\"");
			json.append(",\"date\":\"").append(product.getDate()).append("\"");
			json.append(",\"Freight\":\"").append(product.getFreight())
					.append("\"");
			json.append(",\"Note\":\"").append(buyerMsg.getText().toString()).append("\"");
			// 使用的优惠券
			Coupon coupon = couponList.get(product.getId());
			if (coupon != null)
				json.append(",\"CouponID\":\"").append(coupon.getCouponID())
						.append("\"");
			else
				json.append(",\"CouponID\":\"").append("").append("\"");
			// 赠送的优惠券，根据商品金额计算出商品总金额超过的priceline并获取到该priceline对应的优惠券
			ArrayList<Coupon> coupons = product.getCoupons();
			double totalPrice = Double.valueOf(product.getTotalPrice());
			Coupon giftCoupon = null;
			// 用商品的总额和每一个priceline进行比较，取出超过了的所有priceline的索引位置
			ArrayList<Integer> index = new ArrayList<Integer>();
			for (int j = 0; j < coupons.size(); j++) {
				Coupon couponItem = coupons.get(j);
				if (!couponItem.getPriceLine().equals("")) {
					double priceLine = Double
							.valueOf(couponItem.getPriceLine());
					if (totalPrice > priceLine)
						index.add(j);
				}
			}
			Log.i(MyApplication.TAG, "index.size()->" + index.size());
			Log.i(MyApplication.TAG, "totalPrice->" + totalPrice);
			// 如果有超过的优惠券记录下来所有超过了的优惠券并比较计算出超过的最大的金额的优惠券金额
			if (index.size() > 0) {
				for (int j = 0; j < index.size(); j++) {
					Coupon couponItem = coupons.get(index.get(j));
					if (j == 0)
						giftCoupon = couponItem;
					else {
						double newLine = Double.valueOf(couponItem
								.getPriceLine());
						double oldLine = Double.valueOf(giftCoupon
								.getPriceLine());
						Log.i(MyApplication.TAG, "newLine->" + newLine
								+ " oldLine-->" + oldLine);
						if (newLine > oldLine)
							giftCoupon = couponItem;
					}
				}
			} else// 如果商品总价没有超过任何一张优惠券则全部设置为空
			{
				Coupon emptyCoupon = new Coupon();
				emptyCoupon.setCouponID("0");
				emptyCoupon.setStoreID("");
				emptyCoupon.setProductID("");
				emptyCoupon.setProductName("");
				emptyCoupon.setPriceLine("");
				emptyCoupon.setPrice("");
				giftCoupon = emptyCoupon;
			}
			Log.i(MyApplication.TAG,
					"giftCoupon.getPrice()->" + giftCoupon.getPrice());
			// 得到的优惠券信息
			json.append(",\"CouponsID\":\"").append(giftCoupon.getCouponID())
					.append("\"");

			json.append(",\"PriceID\":\"").append(product.getPriceID())
					.append("\"");
			json.append("}");

		}
		json.append("]");
		return json.toString();
	}

	// private void showResult() {
	// // 提交订单成功以后刷新购物车
	// try {
	// MyApplication.shopCartManager
	// .saveProducts(MyApplication.shopCartList);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// CustomDialog.Builder builder = new CustomDialog.Builder(this);
	// Log.i(MyApplication.TAG, "payway-->" + payway);
	// // 如果是在线付款显示查看订单和去付款
	// if (payway.equals("1")) {
	// builder.setMessage("订单提交成功")
	// .setPositiveButton("查看订单",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// goToPage(2);
	// dialog.cancel();
	// }
	// })
	// .setNegativeButton("去付款",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// // 打开付款页面
	// dialog.cancel();
	// }
	// });
	// CustomDialog alert = builder.create();
	// alert.show();
	// }
	// // 如果是货到付款则不显示去付款
	// else if (payway.equals("2")) {
	// builder.setMessage("订单提交成功")
	// .setPositiveButton("查看订单",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// goToPage(2);
	// dialog.cancel();
	// }
	// })
	// .setNegativeButton("再逛逛",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// goToPage(1);
	// dialog.cancel();
	// }
	// });
	// CustomDialog alert = builder.create();
	// alert.show();
	// }
	//
	// }
	//
	// // 参数1代表前往首页逛逛，参数2代表去订单页面
	// private void goToPage(int page) {
	//
	// MyApplication.goToOrder = true;
	//
	// // 参数1代表前往首页逛逛，参数2代表去订单页面
	// if (page == 1) {
	// MenuBottom.tabHost.setCurrentTab(0);
	// MenuBottom.radioGroup.check(R.id.main_tab_home);
	// } else if (page == 2) {
	// MenuBottom.tabHost.setCurrentTab(4);
	// MenuBottom.radioGroup.check(R.id.main_tab_personcenter);
	// }
	//
	// SubmitOrder.this.finish();
	// }

	// 刷新listview的高度
	public void refreshListViewHeight() {
		int totalHeight = 0;
		for (int index = 0, len = productAdapter.getCount(); index < len; index++) {

			View listViewItem = productAdapter.getView(index, null,
					productListView);

			// 计算子项View 的宽高

			listViewItem.measure(0, 0);

			// 计算所有子项的高度和

			totalHeight += listViewItem.getMeasuredHeight() + 5;

		}
		ViewGroup.LayoutParams params = productListView.getLayoutParams();

		// listView.getDividerHeight()获取子项间分隔符的高度

		// params.height设置ListView完全显示需要的高度

		params.height = totalHeight
				+ (productListView.getDividerHeight() * (productAdapter
						.getCount() - 1));

		productListView.setLayoutParams(params);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		if (request.equals(NetworkAction.获取收货地址列表)) {
			JSONArray lists = response.getJSONArray("adr_info");
			// 如果没有收货地址的话设置文本框内容
			if (lists.length() < 1) {
				addressMsg.setVisibility(View.VISIBLE);
				addressLayout.setVisibility(View.GONE);
				return;
			} else {
				addressMsg.setVisibility(View.GONE);
				addressLayout.setVisibility(View.VISIBLE);
			}
			ArrayList<Address> addressList = new ArrayList<Address>();
			for (int i = 0; i < lists.length(); i++) {
				JSONObject items = lists.getJSONObject(i);
				Address address = new Address();
				address.setAddressID(items.getString("address_id"));
				address.setProvince_id(items.getString("province_id"));
				address.setProvince_name(items.getString("province_name"));
				address.setCity_id(items.getString("city_id"));
				address.setCity_name(items.getString("city_name"));
				address.setCounty_id(items.getString("county_id"));
				address.setCounty_name(items.getString("county_name"));
				address.setRealname(items.getString("realname"));
				address.setMobile(items.getString("mobile"));
				address.setAddress(items.getString("address"));
				address.setTag(items.getString("tag"));
				if (address.getTag().equals("1")) {
					defaultAddress = address;
				}
				addressList.add(address);
			}
			// 如果没有设置默认地址则默认显示第一条地址
			if (defaultAddress == null) {
				defaultAddress = addressList.get(0);
			}
			// 设置默认地址的姓名和电话
			addressName.setText(defaultAddress.getRealname());
			addressPhone.setText(defaultAddress.getMobile());
			// 设置地址
			addressStreet.setText(defaultAddress.getStreet());
		} else if (request.equals(NetworkAction.获取运费)) {
			double freight = response.getDouble("freight");
			double realPrice = Double.valueOf(realPriceTxt.getText().toString()
					.substring(1));
			realPrice = realPrice + freight;
			freightPriceTxt.setText("￥" + String.valueOf(freight));
			realPriceTxt.setText("￥" + String.valueOf(realPrice));
		}
		else if (request.equals(NetworkAction.提交订单)) {
			// 删除购物车中提交成功的商品
			Log.i(MyApplication.TAG, "start");
			for (int i = 0; i < MyApplication.shopCartList
					.size(); i++) {
				Product product = (Product) MyApplication.shopCartList
						.get(i);
				if (product.isChecked()) {
					MyApplication.shopCartList
							.remove(i);

					if (MyApplication.shopCartList
							.size() == 1)
						MyApplication.shopCartList
								.clear();
				}
			}
			Log.i(MyApplication.TAG, "sub suc->"
					+ MyApplication.shopCartList.size());
			try {
				MyApplication.shopCartManager
						.saveProducts(MyApplication.shopCartList);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 如果是在线支付的话跳转到支付页面
			Intent intent = new Intent();
			if (payway.equals("1")) {
				Toast.makeText(SubmitOrder.this,
						"提交订单成功", 2000).show();
				intent.setClass(SubmitOrder.this,
						PayMethod.class);
				intent.putExtra("subject",
						response.getString("subject"));
				intent.putExtra("price", realPriceTxt
						.getText().toString()
						.substring(1));
				intent.putExtra("oid",
						response.getString("NEWID"));
			}
			// 如果是货到付款的话跳转到提交订单成功页面
			else if (payway.equals("2")) {
				intent.setClass(SubmitOrder.this,
						SubmitSuccess.class);
			}
			startActivity(intent);
			finish();
			// showResult();
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			buttonView.setBackgroundDrawable(MyApplication.resources
					.getDrawable(R.drawable.style_btn_bg));
			buttonView.setTextColor(MyApplication.resources
					.getColor(R.color.white));
			switch (buttonView.getId()) {
			case R.id.submit_payway_online:// 在线付款的选择状态
				payway = "1";
				break;
			case R.id.submit_payway_receive:// 货到付款的选择状态
				payway = "2";
				break;

			}
		} else {
			buttonView.setBackgroundDrawable(MyApplication.resources
					.getDrawable(R.drawable.style_bolder_bg));
			buttonView.setTextColor(MyApplication.resources
					.getColor(R.color.style_color));
		}
		buttonView.setPadding(10, 5, 10, 5);
		
	}
}
