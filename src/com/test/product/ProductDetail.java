package com.test.product;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.NetworkImageView;
import com.test.MenuBottom;
import com.test.person.Login;
import com.test.base.ChangeTime;
import com.test.base.CustomDialog;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.MyViewFlipper;
import com.test.base.MyWebView;
import com.test.base.Title;
import com.test.base.Url;
import com.test.model.Attribute;
import com.test.model.Coupon;
import com.test.model.Gift;
import com.test.utils.ConnectServer;
import com.test.utils.ErrorMsg;
import com.test.utils.NetworkAction;
import com.test.model.Product;
import com.test.R;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ProductDetail extends Activity implements OnClickListener,
		OnGestureListener {

	private Resources resources;
	private TextView secKillTime;// 秒杀商品时间
	private TextView commentNumTxt;// 评论总数
	private String num = "1";// 记录商品数量
	private ProgressDialog progressBar;// 加载商品详情时候的进度条
	private AlertDialog alertDialog;// 加载商品详情出错时候的提示框
	private WebView webView;// 显示商品详情的webView
	String productUrl = "http://api2.xinlingmingdeng.com/products/"; // 商品详情网址
	private String productId;// 传过来的商品ID
	private String skid;// 秒杀商品ID
	private TextView name;// 商品名称
	private TextView referencePrice;// 商品参考价格
	public TextView storePrice;// 商品价格

	private String discount;// 商品折扣
	private TextView cashBack;// 满返
	public Product product;// 商品实体类
	public MyAdapter adapter;
	public TextView priceTxt;// 规格价格

	private FrameLayout call;// 联系客服按钮
	private FrameLayout comment;// 查看评价按钮
	private Button buyNow;// 立即购买按钮
	private Button addShopcart;// 加入购物车按钮
	private int viewWidth;
	private int viewHeight;
	public static Handler secKillHandler;// 秒杀倒计时handler

	private GestureDetector gesture;
	private int photopage = 1;
	private int photototalpage;
	private LinearLayout.LayoutParams layoutParams;
	private String buyType = "1"; // Buy_type 购买类型：1正常购买，2秒杀
	private String freight;// 运费

	// 新版修改的参数
	private Title title;// 设置标题栏
	private RadioGroup flipperTxt;// 存放首页广告图片右下角跟随移动的单选框容器
	private MyViewFlipper flipper;
	private boolean getHeight = false;// 是否有获取到图片的高度
	private int newHeight = 0;
	private LinearLayout timeLayout;// 显示倒计时的容器
	private LinearLayout discountLayout;// 促销信息头模块
	private LinearLayout discountModuleLayout;// 促销信息折扣和满返的模块容器
	private TextView discountModuleTxt;// 促销信息折扣的标签
	private TextView cashbackModuleTxt;// 促销信息满返的标签
	private ImageView discountImg;// 促销信息模块图片
	private LinearLayout discountContentLayout;// 促销信息内容模块
	private LinearLayout dContentLayout;// 折扣内容模块
	private LinearLayout cContentLayout;// 满返内容模块
	private TextView discountTxt;// 折扣文本框
	private TextView cashbackTxt;// 满返文本框
	private FrameLayout attributeLayout;// 商品属性
	public TextView attributeTxt;// 属性文本框
	public TextView numTxt;// 属性的数量文本框
	private TextView inventoryTxt;// 库存文本框
	private ListView listView;// 属性和赠品集合
	private TextView attributeNumTxt;// 规格上面的数量框
	private FrameLayout giftLayout;// 赠品模块
	public TextView giftTxt;// 库存赠品文本框
	private TextView giftAttributeTxt;// 显示规格还是赠品的文本框
	private LinearLayout commentLayout;// 评论内容模块
	private NetworkImageView commentImg;// 评论头像
	private TextView nameTxt;// 评论用户昵称
	private TextView contentTxt;// 评论内容
	private TextView dateTxt;// 评论日期
	private int commentNum = 0;// 评论数
	private ScrollView scrollView;
	private boolean detailModule=false;//图文详情模式
	private boolean detailFinished=false;//图文详情是否加载完毕

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		initView();
		initData();
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(7);
		title.setTitleTxt("商品详情");
		flipperTxt = (RadioGroup) findViewById(R.id.product_txt_layout);
		timeLayout = (LinearLayout) findViewById(R.id.product_seckill_outtime_layout);
		discountContentLayout = (LinearLayout) findViewById(R.id.product_content_layout);
		discountModuleLayout = (LinearLayout) findViewById(R.id.product_discount_title);
		discountModuleTxt = (TextView) findViewById(R.id.product_discount_txt);
		cashbackModuleTxt = (TextView) findViewById(R.id.product_cashback_txt);
		discountImg = (ImageView) findViewById(R.id.product_discount_img);
		dContentLayout = (LinearLayout) findViewById(R.id.product_dcontent_layout);
		cContentLayout = (LinearLayout) findViewById(R.id.product_ccontent_layout);
		discountTxt = (TextView) findViewById(R.id.product_discount);
		cashbackTxt = (TextView) findViewById(R.id.product_cashback);
		attributeTxt = (TextView) findViewById(R.id.product_attribute_txt);
		numTxt = (TextView) findViewById(R.id.product_attribute_num);
		inventoryTxt = (TextView) findViewById(R.id.product_attribute_invetory);
		giftTxt = (TextView) findViewById(R.id.product_gift_txt);
		scrollView = (ScrollView) findViewById(R.id.product_detail_scrollview);
		scrollView.setOnTouchListener(new TouchListenerImpl());

		// TODO Auto-generated method stub
		commentNumTxt = (TextView) findViewById(R.id.product_comment_num);
		discountLayout = (LinearLayout) findViewById(R.id.product_discount_layout);// 促销信息模块
		discountLayout.setOnClickListener(this);
		giftLayout = (FrameLayout) findViewById(R.id.product_gift_layout);// 赠品模块
		giftLayout.setOnClickListener(this);
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		gesture = new GestureDetector(this);
		flipper = (MyViewFlipper) findViewById(R.id.product_viewFlipper);
		flipper.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gesture.onTouchEvent(event);
			}
		});
		secKillTime = (TextView) findViewById(R.id.product_seckill_outtime);
		viewWidth = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		viewHeight = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		progressBar = new ProgressDialog(this);// 初始化进度条
		webView = (WebView) findViewById(R.id.product_webview);// 初始化商品详情网页内容

		name = (TextView) findViewById(R.id.product_name);// 商品名称
		referencePrice = (TextView) findViewById(R.id.product_reference_price);// 商品价格
		referencePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中间加横线
		storePrice = (TextView) findViewById(R.id.product_store_price);// 商品促销价格
		attributeLayout = (FrameLayout) findViewById(R.id.product_attribute_layout);// 商品属性
		attributeLayout.setOnClickListener(this);
		call = (FrameLayout) findViewById(R.id.product_call);// 联系客服按钮
		call.setOnClickListener(this);
		comment = (FrameLayout) findViewById(R.id.product_comment);// 查看评价按钮
		comment.setOnClickListener(this);
		buyNow = (Button) findViewById(R.id.product_buynow);// 立即购买按钮
		buyNow.setOnClickListener(this);
		addShopcart = (Button) findViewById(R.id.product_add_shopcart);// 加入购物车按钮
		addShopcart.setOnClickListener(this);
		resources = ProductDetail.this.getResources();

		secKillHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				// 取出最新的时间
				long time = bundle.getLong("time");
				// 从子线程返回的计算好了的新的时间字符串
				String timeString = bundle.getString("timeString");
				if (time > 0)
					secKillTime.setText("倒计时：" + timeString);
				else {
					// 重置秒杀商品详情页面的倒计时时间
					ChangeTime.secKillTime = -1;
					secKillTime.setText("秒杀已结束");
				}
			}
		};
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 重置秒杀商品详情页面的倒计时时间
		ChangeTime.secKillTime = -1;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (MyApplication.goToOrder) {
			MyApplication.goToOrder = false;
			this.finish();
		}
	}

	private void initData() {
		// 获取传过来的商品ID，如果不为空则获取商品的详细信息
		Intent intent = getIntent();
		productId = intent.getStringExtra("productId");
		skid = intent.getStringExtra("skid");
		Log.i(MyApplication.TAG, "skid-->" + skid);
		if (skid == null || skid.equals("null"))
			sendData(NetworkAction.商品详情);
		else {
			buyType = "2";
			sendData(NetworkAction.秒杀商品详情);
		}

		sendData(NetworkAction.评论列表);
		// 获取商品详情网页的网址
		productUrl = productUrl + "?act=detail&sid=" + MyApplication.sid
				+ "&product_id=" + productId;
		// Log.i(MyApplication.TAG, "url-->"+productUrl);
		// 加载商品详情
		 initWebView();
	}

	private void sendData(final NetworkAction request) {

		String url = "";
		HashMap<String, String> paramter = new HashMap<String, String>();
		if (request.equals(NetworkAction.商品详情)) {
			url = Url.URL_DETAILS;
			paramter.put("act", "ProductInto");
			paramter.put("product_id", productId);
			paramter.put("sid", MyApplication.sid);
		} else if (request.equals(NetworkAction.秒杀商品详情)) {
			url = Url.URL_SECKILL;
			paramter.put("act", "seckill");
			paramter.put("product_id", productId);
			paramter.put("sid", MyApplication.sid);
			paramter.put("SKID", skid);
		} else if (request.equals(NetworkAction.评论列表)) {
			url = Url.URL_ORDER;
			paramter.put("act", "comments_list");
			paramter.put("sid", MyApplication.sid);
			paramter.put("ProductID", productId);
			paramter.put("level", "0");
			paramter.put("page", "1");
			paramter.put("pagesize", "100000");
		}
		Log.i(MyApplication.TAG, request + MyApplication.getUrl(paramter, url));
		/*
		 * 向服务器发送请求
		 */
		MyApplication.client.postWithURL(url, paramter,
				new Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						try {
							Log.i(MyApplication.TAG, request + "response-->"
									+ response.toString());
							int code = response.getInt("code");

							if (response.getInt("code") == 1) {

								if (request.equals(NetworkAction.秒杀商品详情)
										|| request.equals(NetworkAction.商品详情)) {
									product = new Product();
									JSONObject normalInfo = response
											.getJSONObject("info");
									JSONArray imgList = null;
									if (request.equals(NetworkAction.秒杀商品详情)) {

										buyType = "2";
										// 获取特价信息集合
										long startTime = response
												.getLong("date");// 获取开始时间戳
										Log.i(MyApplication.TAG, request
												+ " startTime-->" + startTime);
										JSONObject info = response
												.getJSONObject("SecondKill");
										// 获取结束时间戳
										long outTime = info
												.getLong("OutEndTime");
										// 得到离秒杀商品结束剩余的时间
										long time = outTime - startTime;
										// 显示秒杀商品倒计时文本框
										timeLayout.setVisibility(View.VISIBLE);
										secKillTime.setTextSize(15);
										// 开始刷新倒计时文本框
										ChangeTime.secKillTime = time;
										name.setText(info.getString("SKName"));
										if (!normalInfo.getString(
												"ReferencePrice")
												.equals("null"))
											referencePrice.setText("原价：￥"
													+ normalInfo
															.getString("ReferencePrice"));
										else
											referencePrice
													.setVisibility(View.GONE);
										Log.i(MyApplication.TAG,
												"getStorePrice()->"
														+ info.getString("SKPrice"));
										storePrice.setText("￥"
												+ info.getString("SKPrice"));

										// 初始化图片集合
										imgList = info.getJSONArray("Images");

										// --------------下面为需要传递到下个页面的数据集合，上面为页面显示数据集合-------------

										product.setStorePrice(info
												.getString("SKPrice"));
										product.setSkID(skid);
										product.setInventory(info
												.getString("SKLeftNum"));// 设置库存数
										product.setSKName(info
												.getString("SKName"));
										product.setSKPrice(info
												.getString("SKPrice"));
										// 添加秒杀商品的属性ID
										try {
											String priceID = info
													.getString("PriceID");
											if (priceID.equals("null"))
												product.setPriceID("0");
											else
												product.setPriceID(priceID);
										} catch (Exception e) {
											product.setPriceID("0");
										}
									} else if (request
											.equals(NetworkAction.商品详情)) {
										buyType = "1";
										String tempname = normalInfo
												.getString("SubProductName");
										if (tempname.equals(""))
											name.setText(normalInfo
													.getString("ProductName"));
										else
											name.setText(tempname);
										// 没有原价信息隐藏该模块
										if (!normalInfo.getString(
												"ReferencePrice")
												.equals("null"))
											referencePrice.setText("原价：￥"
													+ normalInfo
															.getString("ReferencePrice"));
										else
											referencePrice
													.setVisibility(View.GONE);
										// 设置商品价格
										product.setStorePrice(normalInfo
												.getString("StorePrice"));
										// 设置商品编码
										product.setCode(normalInfo
												.getString("ProductCode"));
										// 初始化图片集合
										imgList = response
												.getJSONArray("ImgInfo");

										// 商品属性
										// JSONArray attributes = response
										// .getJSONArray("PriceList");
										// Log.i(MyApplication.TAG,
										// "attributes-->" + attributes);
										// if (attributes.length() >= 1) {
										// attributeLayout
										// .setVisibility(View.VISIBLE);
										// LinearLayout layout = new
										// LinearLayout(
										// attributeLayout
										// .getContext());
										// RadioGroup attributeGroup = new
										// RadioGroup(
										// layout.getContext());
										// attributeGroup.setOrientation(1);
										//
										// for (int i = 0; i < attributes
										// .length(); i++) {
										// RadioGroup.LayoutParams layoutParams1
										// = new RadioGroup.LayoutParams(
										// LayoutParams.WRAP_CONTENT,
										// LayoutParams.WRAP_CONTENT);
										// layoutParams1.setMargins(0, 7,
										// 0, 0);
										//
										// // 设置属性单选按钮
										// // RadioButton attribute = new
										// // RadioButton(
										// // attributeGroup.getContext());
										// RadioButton attribute = (RadioButton)
										// LayoutInflater
										// .from(attributeGroup
										// .getContext())
										// .inflate(
										// R.layout.radio_item2,
										// null);
										// // 显示属性名称
										// JSONObject item = attributes
										// .getJSONObject(i);
										// // 获取该属性的商品数量
										// int num = item
										// .getInt("PriceNum");
										// // 如果该商品数量不足，则不让选择
										// if (num <= 0)
										// attribute.setEnabled(false);
										// Log.i(MyApplication.TAG,
										// "num-->" + num);
										// attribute.setText(item
										// .getString("PriceName"));
										// attribute.setTextColor(resources
										// .getColor(R.color.gray));
										// attribute.setId(i);
										// attribute.setTextSize(14);
										// attribute.setTag(
										// R.id.tag_first,
										// item.getString("Price"));
										// attribute.setTag(
										// R.id.tag_second,
										// item.getString("PriceNum"));
										// attribute.setTag(
										// R.id.tag_three,
										// item.getString("PriceID"));
										// attributeGroup.addView(
										// attribute,
										// layoutParams1);
										// if (i == 0) {
										// // 添加商品的priceID
										// product.setPriceID(item
										// .getString("PriceID"));
										// // 定义标题文本框
										// TextView attributeTxt = new TextView(
										// layout.getContext());
										// attributeTxt
										// .setText("商品属性：  ");
										// attributeTxt
										// .setTextColor(resources
										// .getColor(R.color.gray));
										// attributeTxt.setPadding(0,
										// 15, 0, 0);
										// layout.addView(attributeTxt);
										// // 默认选中商品属性的第一个属性，并设置该属性的价格和折扣信息
										// if (buyType.equals("1")
										// && discount != null) {
										// attribute
										// .setChecked(true);
										// // product.setAttribute(attribute
										// // .getText()
										// // .toString());
										// String Price = attribute
										// .getTag(R.id.tag_first)
										// .toString();
										// String newPrice =
										// String.valueOf(Double
										// .valueOf(Price)
										// * (Double
										// .valueOf(discount) / 100));
										// DecimalFormat df = new DecimalFormat(
										// ".00");
										// String lessPrice =
										// String.valueOf(df.format(Double
										// .valueOf(Price)
										// - Double.valueOf(newPrice)));
										// discountTxt
										// .setText(discount
										// + "折   抵￥"
										// + lessPrice);
										// storePrice.setText("￥"
										// + newPrice);
										// // 设置该产品的相关属性价格
										// product.setStorePrice(newPrice);
										// product.setInventory(item
										// .getString("PriceNum"));
										// }
										// }
										//
										// }
										// attributeGroup
										// .setOnCheckedChangeListener(new
										// OnCheckedChangeListener() {
										//
										// @Override
										// public void onCheckedChanged(
										// RadioGroup group,
										// int checkedId) {
										// RadioButton btn = (RadioButton) group
										// .getChildAt(checkedId);
										// String priceID = btn
										// .getTag(R.id.tag_three)
										// .toString();
										// product.setPriceID(priceID);
										// //
										// 购买普通商品的时候并且该商品有折扣时使用新属性的价格和折扣计算最新的价格和优惠价
										// if (buyType
										// .equals("1")
										// && discount != null) {
										// String Price = btn
										// .getTag(R.id.tag_first)
										// .toString();
										// String newPrice = String
										// .valueOf(Double
										// .valueOf(Price)
										// * (Double
										// .valueOf(discount) / 100));
										// DecimalFormat df = new DecimalFormat(
										// ".00");
										// String lessPrice = String
										// .valueOf(df
										// .format(Double
										// .valueOf(Price)
										// - Double.valueOf(newPrice)));
										// discountTxt
										// .setText(discount
										// + "折   抵￥"
										// + lessPrice);
										// storePrice
										// .setText("￥"
										// + newPrice);
										// // product.setAttribute(btn
										// // .getText()
										// // .toString());
										// product.setStorePrice(newPrice);
										// product.setInventory(btn
										// .getTag(R.id.tag_second)
										// .toString());
										// }
										// //
										// }
										// });
										// layout.addView(attributeGroup);
										// attributeLayout.addView(layout);
										// } else
										// product.setPriceID("0");

										// --------------------------普通商品的商品信息打包-----------------------------------
										product.setInventory(normalInfo
												.getString("ProductLeft"));// 库存数
										product.setStorePrice(normalInfo
												.getString("StorePrice"));

									}

									// --------------------------普通商品和秒杀商品相同属性的信息打包---------------------------------
									product.setId(normalInfo
											.getString("ProductID"));// 商品ID
									product.setFreight(normalInfo
											.getString("Freight"));// 商品运费
									product.setName(normalInfo
											.getString("ProductName"));// 商品名称
									product.setBuy_type(buyType);
									product.setReferencePrice(normalInfo
											.getString("ReferencePrice"));
									product.setDate(String.valueOf(new Date()
											.getTime()));// 记录加入购物车的时间
									product.setNature(normalInfo
											.getString("Nature"));
									// ----------------------共同区域----------------------------------

									// 获取图片缩放比例后的高度
									final JSONObject itemTemp;
									itemTemp = imgList.getJSONObject(0);
									// 如果有图片并且没有获取到实际网络图片的宽高比例的时候先获取其相对高度
									if (imgList.length() > 0 && !getHeight) {
										Thread thread = new Thread(
												new Runnable() {

													@Override
													public void run() {

														try {
															String path = Url.URL_IMGPATH
																	+ itemTemp
																			.getString("FilePath");
															URL url = new URL(
																	path);
															String responseCode = url
																	.openConnection()
																	.getHeaderField(
																			0);
															Bitmap map = BitmapFactory
																	.decodeStream(url
																			.openStream());
															int height = map
																	.getHeight();
															int width = map
																	.getWidth();
															newHeight = (int) (MyApplication.width * ((double) height / width));
															getHeight = true;
															sendData(request);
														} catch (Exception e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}

													}
												});
										thread.start();
										return;
									}
									// 图片集合
									for (int i = 0; i < imgList.length(); i++) {
										// 记录图片总数
										photopage = imgList.length();
										String m = Url.URL_IMGPATH
												+ ((JSONObject) imgList.get(i))
														.getString("FilePath");
										String imgPath = ((JSONObject) imgList
												.get(i)).getString("FilePath");
										product.setImgPath(imgPath);
										Log.i(MyApplication.TAG, "imgList->"
												+ imgList.length());
										NetworkImageView netView = new NetworkImageView(
												ProductDetail.this);
										netView.setAdjustViewBounds(false);
										LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
												LayoutParams.MATCH_PARENT,
												newHeight);
										netView.setLayoutParams(layoutParams);
										Log.i(MyApplication.TAG, "m->" + m);
										MyApplication.client
												.getImageForNetImageView(m,
														netView,
														R.drawable.ic_launcher);
										flipper.addView(netView);
										flipper.setInAnimation(
												ProductDetail.this,
												R.anim.view_in_from_right);
										flipper.setOutAnimation(
												ProductDetail.this,
												R.anim.view_out_to_left);
										RadioGroup.LayoutParams rbParams = new RadioGroup.LayoutParams(
												15, 15);
										rbParams.setMargins(0, 0, 10, 0);
										RadioButton rb = new RadioButton(
												flipperTxt.getContext());
										rb.setBackgroundDrawable(MyApplication.resources
												.getDrawable(R.drawable.product_img_bg));
										rb.setId(i);
										// 设置透明色来去掉radiobutton原来的button按钮
										rb.setButtonDrawable(new ColorDrawable(
												Color.TRANSPARENT));
										if (i == 0)
											rb.setChecked(true);
										netView.setTag(rb);
										flipperTxt.addView(rb, rbParams);
									}
									photototalpage = imgList.length();

									// 商品详情的促销信息模块,只是针对非秒杀商品的
									if (buyType.equals("1")) {

										// 折扣信息集合
										JSONArray discounts = response
												.getJSONArray("discount");
										// 满返信息集合
										JSONArray coupons = response
												.getJSONArray("coupons");
										if (discounts.length() > 0
												|| coupons.length() > 0)
											// 显示促销信息模块
											discountLayout
													.setVisibility(View.VISIBLE);
										Log.i(MyApplication.TAG,
												"discount length->"
														+ discounts.length());
										// 有折扣信息的时候
										if (discounts.length() > 0) {
											// 有折扣信息的时候显示折扣标签和内容
											discountModuleTxt
													.setVisibility(View.VISIBLE);
											dContentLayout
													.setVisibility(View.VISIBLE);
											for (int i = 0; i < discounts
													.length(); i++) {
												JSONObject discountObject = discounts
														.getJSONObject(i);
												discount = discountObject
														.getString("Per");

												product.setDiscount(discountObject
														.getString("Per"));
												product.setDiscountCash(discountObject
														.getString("OffsetPrice"));
												discountTxt.setText(product
														.getDiscount()
														+ "折   抵￥"
														+ product
																.getDiscountCash());

											}
										}

										if (coupons.length() > 0) {
											cashbackModuleTxt
													.setVisibility(View.VISIBLE);
											cContentLayout
													.setVisibility(View.VISIBLE);
											// 生成满返信息
											for (int i = 0; i < coupons
													.length(); i++) {
												JSONObject couponObject = coupons
														.getJSONObject(i);
												Coupon coupon = new Coupon();
												coupon.setCouponID(couponObject
														.getString("CouponID"));
												coupon.setStoreID(couponObject
														.getString("StoreID"));
												coupon.setProductID(couponObject
														.getString("ProductID"));
												coupon.setPrice(couponObject
														.getString("Price"));
												coupon.setStart_time(couponObject
														.getString("StartTime"));
												coupon.setEnd_time(couponObject
														.getString("EndTime"));
												coupon.setPriceLine(couponObject
														.getString("PriceLine"));
												product.addCoupon(coupon);
												// 满返内容
												String cashbackContent = "凡购买商品满￥"
														+ couponObject
																.getString("PriceLine")
														+ "元立返￥"
														+ couponObject
																.getString("Price")
														+ "元优惠券";
												// 满返时间
												String time = couponObject
														.getString("StartTime")
														.substring(5, 7)
														+ "月"
														+ couponObject
																.getString(
																		"StartTime")
																.substring(8,
																		10)
														+ "日 ~ "
														+ couponObject
																.getString(
																		"EndTime")
																.substring(5, 7)
														+ "月"
														+ couponObject
																.getString(
																		"EndTime")
																.substring(8,
																		10)
														+ "日";
												if (i == 0) {
													cashbackTxt.setText(time
															+ "      "
															+ cashbackContent);
												} else {
													cashbackTxt
															.setText(cashbackTxt
																	.getText()
																	.toString()
																	+ "，"
																	+ cashbackContent);
												}
											}
										}
									}
									// 初始化商品数量
									product.setNum("1");
									// 初始化商品属性信息
									JSONArray attributes = response
											.getJSONArray("PriceList");
									if (attributes.length() > 0) {
										attributeLayout
												.setVisibility(View.VISIBLE);
										for (int j = 0; j < attributes.length(); j++) {
											JSONObject item = attributes
													.getJSONObject(j);
											Attribute attribute = new Attribute();
											attribute.setID(item
													.getString("PriceID"));
											attribute.setName(item
													.getString("PriceName"));
											attribute.setPrice(item
													.getString("Price"));
											product.setAttributes(attribute);
											// 默认属性选择第一个
											if (j == 0) {
												product.setAttribute(attribute);
												attributeTxt.setText(product
														.getAttribute()
														.getName());
												numTxt.setText("、"
														+ product.getNum()
														+ "件 ");
												inventoryTxt.setText("（库存"
														+ product
																.getInventory()
														+ "）");
											}
										}
										storePrice.setText("￥"
												+ product.getAttribute()
														.getPrice());
									} else {
										product.setHaveAttribute(false);
										storePrice.setText("￥"
												+ product.getStorePrice());
									}

									// 赠品
									JSONArray gifts = response
											.getJSONArray("GiftList");
									if (gifts.length() >= 1) {
										giftLayout.setVisibility(View.VISIBLE);
										for (int j = 0; j < gifts.length(); j++) {
											JSONObject item = gifts
													.getJSONObject(j);
											Gift gift = new Gift();
											gift.setID(item.getString("GiftID"));
											gift.setName(item
													.getString("GiftName"));
											gift.setNum(item
													.getString("GiftTotal"));
											product.setGifts(gift);
											if (j == 0) {
												product.setGift(gift);
												giftTxt.setText(product
														.getGift().getName());
											}
										}
									} else
										product.setHaveGift(false);

								} else if (request.equals(NetworkAction.评论列表)) {
									commentNumTxt.setText("("
											+ response
													.getString("total_number")
											+ ")");
									int num = response.getInt("total_number");
									if (num > 0) {
										commentNum = num;
										commentLayout = (LinearLayout) findViewById(R.id.product_comment_layout);
										commentImg = (NetworkImageView) findViewById(R.id.product_detail_comment_img);
										nameTxt = (TextView) findViewById(R.id.product_detail_comment_name);
										contentTxt = (TextView) findViewById(R.id.product_detail_comment_content);
										dateTxt = (TextView) findViewById(R.id.product_detail_comment_date);
										commentLayout
												.setVisibility(View.VISIBLE);
										MyApplication.client
												.getImageForNetImageView("",
														commentImg,
														R.drawable.loginout_img);
										JSONObject item = response
												.getJSONArray("list")
												.getJSONObject(0);
										nameTxt.setText(item
												.getString("username"));
										contentTxt.setText(item
												.getString("comment_content"));
										dateTxt.setText(item
												.getString("createtime"));
									}
								}

							} else {
								Toast.makeText(
										ProductDetail.this,
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

	/*
	 * 初始化webview
	 */
	protected void initWebView() {
//		// 设计进度条
//		progressBar = ProgressDialog.show(ProductDetail.this, null,
//				"正在加载商品详情，请稍后…");
		// 获得WebView组件
		webView.getSettings().setJavaScriptEnabled(true);
		WebSettings settings = webView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// webView.getSettings().setSupportZoom(true);
		// webView.getSettings().setBuiltInZoomControls(true);
		// Log.i(MyApplication.TAG, "url-->"+url);
		webView.loadUrl(productUrl);
		alertDialog = new AlertDialog.Builder(this).create();
		// 设置视图客户端
		webView.setWebViewClient(new MyWebViewClient());
	}

	/*
	 * 设置加载网页时显示进度条，无法加载时弹出错误提示
	 */
	class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			progressBar.show();
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			detailFinished=true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Toast.makeText(ProductDetail.this, "网页加载出错！", Toast.LENGTH_LONG);
			alertDialog.setTitle("ERROR");
			alertDialog.setMessage(description);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			alertDialog.show();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;

		switch (v.getId()) {

		case R.id.product_call:// 联系客服按钮
			CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setMessage("\t\t确定拨打客服电话?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 点击“确认”后的操作
									Intent intent = new Intent(
											Intent.ACTION_CALL, Uri
													.parse("tel:"
															+ "4000838310"));
									ProductDetail.this.startActivity(intent);
									dialog.cancel();
								}
							})
					.setNegativeButton("返回",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
			CustomDialog alert = builder.create();
			alert.show();

			break;
		case R.id.product_comment:// 查看评价按钮
			Intent commentIntent = new Intent();
			commentIntent.setClass(this, CommentList.class);
			commentIntent.putExtra("productID", product.getId());
			startActivity(commentIntent);
			break;
		case R.id.product_buynow:// 立即购买按钮
			 product.setBuy_type(buyType);// 记录购买的商品类型
			 double pNumSub = Double.valueOf(product.getNum());
			 double priceSub = Double.valueOf(product.getStorePrice());
			 product.setTotalPrice(String.valueOf(pNumSub * priceSub));//
			 //设置该商品的总价
			  if (MyApplication.loginStat) {
			  intent = new Intent(this, SubmitOrder.class);
			  ArrayList<Object> plist = new ArrayList<Object>();
			  plist.add(product);
			  intent.putExtra("products", plist);
			  } else {
			  intent = new Intent(this, Login.class);
			  }
			 startActivity(intent);
			break;
		case R.id.product_add_shopcart:// 加入购物车按钮
			double p1NumSub = Double.valueOf(product.getNum());
			double p1riceSub = Double.valueOf(product.getStorePrice());
			product.setTotalPrice(String.valueOf(p1NumSub * p1riceSub));// 设置该商品的总价
			if (MyApplication.loginStat) {
				MyApplication.shopCartManager.add(product);

				CustomDialog.Builder builder1 = new CustomDialog.Builder(this);
				builder1.setMessage("成功加入购物车！")
						.setPositiveButton("再逛逛",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								})
						.setNegativeButton("去购物车",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									
										Intent intent=new Intent().setClass(ProductDetail.this, MenuBottom.class);
										startActivity(intent);
										MenuBottom.tabHost.setCurrentTab(3);
										MenuBottom.radioGroup
												.check(R.id.main_tab_shopcart);
										ProductDetail.this.finish();
										dialog.cancel();
									}
								});
				CustomDialog alert1 = builder1.create();
				alert1.show();
				ArrayList<Object> products = null;
				try {
					products = MyApplication.shopCartManager.readShopCart();
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				 intent = new Intent(this, Login.class);
				startActivity(intent);
			}

			break;
		case 0:// 动态添加的商品属性按钮的点击事件
				// Toast.makeText(this, v.getTag().toString(), 2000).show();
			break;
		case R.id.product_discount_layout:// 促销信息模块
			// visible 0 gone 8
			if (discountContentLayout.getVisibility() == 8) {
				discountContentLayout.setVisibility(View.VISIBLE);
				discountImg.setBackgroundDrawable(MyApplication.resources
						.getDrawable(R.drawable.product_discount_open));
				discountModuleLayout.setVisibility(View.GONE);
			} else {
				discountContentLayout.setVisibility(View.GONE);
				discountImg.setBackgroundDrawable(MyApplication.resources
						.getDrawable(R.drawable.product_discount_close));
				discountModuleLayout.setVisibility(View.VISIBLE);
			}
			break;
		// 商品规格模块
		case R.id.product_attribute_layout:
		case R.id.product_gift_layout:
			AlertDialog congratulateDialog = new AlertDialog.Builder(this)
					.create();
			Window window = congratulateDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			window.setGravity(Gravity.LEFT | Gravity.TOP);
			lp.x = MyApplication.width - 250; // 新位置X坐标
			lp.y = title.getHeight(); // 新位置Y坐标
			window.setAttributes(lp);
			congratulateDialog.show();
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.dialog_attribute, null);
			congratulateDialog.getWindow().setContentView(layout);
			congratulateDialog.getWindow().setWindowAnimations(
					R.style.dialogWindowAnim);
			// 小图片
			NetworkImageView img = (NetworkImageView) layout
					.findViewById(R.id.attribute_img);
			MyApplication.client.getImageForNetImageView(product.getImgPath(),
					img, R.drawable.ic_launcher);
			// 价格
			priceTxt = (TextView) layout.findViewById(R.id.attribute_price);
			priceTxt.setText("￥" + product.getAttribute().getPrice());
			// 编码
			TextView codeTxt = (TextView) layout
					.findViewById(R.id.attribute_code);
			codeTxt.setText("商品编号：" + product.getId());
			giftAttributeTxt = (TextView) layout.findViewById(R.id.gift_txt);
			if (v.getId() == R.id.product_attribute_layout) {
				giftAttributeTxt.setText("规格");
				// 属性集合
				listView = (ListView) layout
						.findViewById(R.id.attribute_listview);
				ArrayList<Object> data = product.getAttributes();
				adapter = new MyAdapter(this, NetworkAction.商品属性, data);
				// 显示数量模块
				LinearLayout numLayout = (LinearLayout) layout
						.findViewById(R.id.product_num_layout);
				View line = (View) layout.findViewById(R.id.product_num_line);
				numLayout.setVisibility(View.VISIBLE);
				line.setVisibility(View.VISIBLE);
				attributeNumTxt = (TextView) layout
						.findViewById(R.id.product_num);
				attributeNumTxt.setText(product.getNum());
				ImageView jian = (ImageView) layout
						.findViewById(R.id.product_jian);
				ImageView jia = (ImageView) layout
						.findViewById(R.id.product_jia);
				jian.setOnClickListener(this);
				jia.setOnClickListener(this);
			} else if (v.getId() == R.id.product_gift_layout) {
				giftAttributeTxt.setText("赠品");
				listView = (ListView) layout
						.findViewById(R.id.attribute_listview);
				ArrayList<Object> data = product.getGifts();
				adapter = new MyAdapter(this, NetworkAction.赠品, data);
			}
			listView.setDivider(null);
			listView.setAdapter(adapter);
			Button buyBtn = (Button) layout.findViewById(R.id.product_buynow);
			Button addBtn = (Button) layout
					.findViewById(R.id.product_add_shopcart);
			buyBtn.setOnClickListener(this);
			addBtn.setOnClickListener(this);
			break;
		case R.id.product_jian:// 规格页面的减号的点击事件
			int numjian = Integer.valueOf(attributeNumTxt.getText().toString());
			if (numjian > 1) {
				numjian--;
				attributeNumTxt.setText(String.valueOf(numjian));
				numTxt.setText(String.valueOf(numjian) + "件");
				product.setNum(String.valueOf(numjian));
			}
			break;
		case R.id.product_jia:// 规格页面的加号的点击事件
			int numjia = Integer.valueOf(attributeNumTxt.getText().toString());
			numjia++;
			if (numjia <= Integer.valueOf(product.getInventory())) {
				attributeNumTxt.setText(String.valueOf(numjia));
				numTxt.setText(String.valueOf(numjia) + "件");
				product.setNum(String.valueOf(numjia));
			} else
				Toast.makeText(this, "无法再添加，没有库存了！", 2000).show();
			break;
		}

	}

	// 滚动条点击和滑动事件监听器
	private class TouchListenerImpl implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			int count=0;
			int scrollY = view.getScrollY();
			int height = view.getHeight();
			int scrollViewMeasuredHeight = scrollView.getChildAt(0)
					.getMeasuredHeight();
			switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if((scrollY + height) == scrollViewMeasuredHeight)
					detailModule=true;
				else
					detailModule=false;
//				Toast.makeText(ProductDetail.this, detailModule+"", 2000).show();
				break;
			case MotionEvent.ACTION_UP:

				break;
			case MotionEvent.ACTION_MOVE:

				if(detailModule && detailFinished)
					webView.setVisibility(View.VISIBLE);
				else if(detailModule&& !detailFinished && count==0)
				{
					Toast.makeText(ProductDetail.this, "图文详情还未加载完毕，请稍后！", 2000).show();
					count++;
				}
				
//				// 如果滑动到顶端的事件
//				if (scrollY == 0) {
//
//				}
//				Log.i(MyApplication.TAG, " height->" + height);
//				Log.i(MyApplication.TAG, "scrollY ->" + scrollY);
//				Log.i(MyApplication.TAG, "scrollViewMeasuredHeight->"
//						+ scrollViewMeasuredHeight);
//				// 如果滑动到底部的事件
//				if ((scrollY + height) > scrollViewMeasuredHeight) {
//					Toast.makeText(ProductDetail.this, "滑到底端了", 2000).show();
//				}
				break;

			default:
				break;
			}
			return false;
		}

	};

	// 滑动图片的方法
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (photopage <= 1)
			return false;

		if (e1.getX() > e2.getX()) {
			flipper.setInAnimation(ProductDetail.this,
					R.anim.view_in_from_right);
			flipper.setOutAnimation(ProductDetail.this, R.anim.view_out_to_left);
			flipper.showNext();
		} else {

			flipper.setInAnimation(ProductDetail.this, R.anim.view_in_from_left);
			flipper.setOutAnimation(ProductDetail.this,
					R.anim.view_out_to_right);
			flipper.showPrevious();
		}

		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
