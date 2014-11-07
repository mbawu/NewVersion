package com.test.product;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.NetworkImageView;
import com.test.base.ChangeTime;
import com.test.model.Product;
import com.test.base.MyAdapter;
import com.test.base.MyGridView;
import com.test.base.MyApplication;
import com.test.base.MyViewFlipper;
import com.test.base.Url;
import com.test.R;
import com.test.base.MenuActivity;
import com.test.base.Title;
import com.test.utils.ConnectServer;
import com.test.utils.ErrorMsg;
import com.test.utils.NetworkAction;

public class Home extends MenuActivity implements OnGestureListener,
		OnItemClickListener, OnClickListener {

	private Title title;// 设置标题栏

	private MyViewFlipper flipper;// 广告容器
	private boolean getHeight = false;// 是否有获取到广告的高度
	private GestureDetector gesture;
	private RadioGroup flipperTxt;// 存放首页广告图片右下角跟随移动的单选框容器
	private int imgLocation = 0;// 记录首页广告图片位置

	private LinearLayout hotMoreBtn; // 热门商品更多按钮
	private LinearLayout secKillMoreBtn; // 秒杀商品更多按钮
	private MyGridView hotGridView;// 热卖商品
	private MyGridView secKillGridView;// 热卖商品
	private ArrayList<Object> hotProduct; // 数据集合
	private ArrayList<Object> secKillProduct; // 数据集合
	private MyAdapter adapterHot; // 热门商品适配器
	private MyAdapter adapterSecKill; // 秒杀商品适配器
	private ScrollView srollView; // 滚动条
	private FrameLayout hotTopModule;// 热门商品上面显示更多和文本的容器
	private FrameLayout secKillTopModule;// 秒杀商品上面显示更多和文本的容器
	private LinearLayout secKillLayout;// 秒杀模块内容
	private LinearLayout hotLayout;// 热门商品模块内容
	private boolean hotModule = false;// 热门商品模块标示
	private boolean secKillModule = false;// 秒杀商品模块标示
	public static Handler homeHandler;
	private int newHeight = 0;
	private boolean load;
	private int page = 1; // 当前页码
	private String pageSize = "4"; // 每页显示的数据条数
	private int totalPage = 0; // 总页码
	public ChangeTime changeTime;

	HashMap<String, String> paramter;
	HashMap<String, String> paramterHot;
	HashMap<String, String> paramterSeckill;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		// 开启倒计时功能
		startChangeTime();
		initView();
		initData();
	}

	// 开启倒计时功能
	private void startChangeTime() {
		// 如果退出程序以后该标识符为true，再次进入的时候需要重置该标识符才可启动倒计时功能
		if (!ChangeTime.exit) {
			ChangeTime.exit = true;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 如果点击广告图片离开app以后返回该页面继续播放广告图片
		// flipper.startFlipping();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 该页面被销毁以后监测刷新秒杀倒计时的线程也将随之结束
		ChangeTime.exit = false;
	}

	private void initView() {
		changeTime = new ChangeTime();
		Thread thread = new Thread(changeTime);
		thread.start();
		flipper = (MyViewFlipper) findViewById(R.id.home_viewFlipper);
		flipperTxt = (RadioGroup) findViewById(R.id.home_txt_layout);
		title = (Title) findViewById(R.id.title);
		title.setModule(1);
		gesture = new GestureDetector(this);
		flipper.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gesture.onTouchEvent(event);
			}

		});
		secKillGridView = (MyGridView) findViewById(R.id.home_seckill_gridview);
		hotGridView = (MyGridView) findViewById(R.id.home_hot_gridview);
		hotGridView.setOnItemClickListener(this);
		secKillGridView.setOnItemClickListener(this);
		secKillLayout = (LinearLayout) findViewById(R.id.home_seckill_layout);// 秒杀模块
		hotLayout = (LinearLayout) findViewById(R.id.home_hot_layout);// 热门商品模块
		hotMoreBtn = (LinearLayout) findViewById(R.id.home_hot_more_btn);// 热门商品更多按钮
		hotMoreBtn.setOnClickListener(this);
		secKillMoreBtn = (LinearLayout) findViewById(R.id.home_seckill_more_btn); // 秒杀商品更多按钮
		secKillMoreBtn.setOnClickListener(this);
		srollView = (ScrollView) findViewById(R.id.home_scroll);
		// srollView.setOnTouchListener(new TouchListenerImpl());
		hotProduct = new ArrayList<Object>();
		secKillProduct = new ArrayList<Object>();
		adapterHot = new MyAdapter(this, NetworkAction.热门商品, hotProduct);
		adapterSecKill = new MyAdapter(this, NetworkAction.秒杀商品, secKillProduct);
		hotGridView.setAdapter(adapterHot);
		secKillGridView.setAdapter(adapterSecKill);

		// 设置滚动条初始位置
//		 srollView.smoothScrollTo(0, 0);
	}

	private void initData() {
		// 每秒刷新秒杀商品时间的handler
		homeHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				// 取出最新的时间
				long time = bundle.getLong("time");
				// 取出索引，根据该索引查询对应的时间和textview
				int index = bundle.getInt("index");
//				Log.i("test", ""+index);
				// 从子线程返回的计算好了的新的时间字符串
				String timeString = bundle.getString("timeString");
				TextView txt = (TextView) ((View) ChangeTime.txtViewList.get(
						index).getTag())
						.findViewById(R.id.home_seckill_outtime);
				// 如果秒杀还没有结束的话，刷新最新的时间，否则重新获取一遍秒杀商品
				// if(time>0)
				// ChangeTime.txtViewList.get(index).setText(timeString);
				if (time > 0)
					txt.setText(timeString);
				else {
					ChangeTime.txtViewList.clear();
					ChangeTime.timeList.clear();
					secKillProduct.clear();
					ConnectServer.getResualt(Home.this, paramterSeckill,
							NetworkAction.秒杀商品, Url.URL_SECKILL);
				}
			}
		};
		// 首页广告
		paramter = new HashMap<String, String>();
		paramter.put("act", "img1");
		paramter.put("nowpage", "1");
		paramter.put("pagesize", "100");
		paramter.put("sid", MyApplication.sid);
		ConnectServer.getResualt(this, paramter, NetworkAction.首页广告,
				Url.URL_INDEX);
		// 热门商品
		paramterHot = new HashMap<String, String>();
		paramterHot.put("act", "hotsale");
		paramterHot.put("CacheID", "");
		paramterHot.put("CacheID1", "0");
		paramterHot.put("brans", "0");
		paramterHot.put("cates", "0");
		paramterHot.put("clears", "0");
		paramterHot.put("keyname", "");
		paramterHot.put("keyname1", "");
		paramterHot.put("nowpage", String.valueOf(page));
		paramterHot.put("pagesize", pageSize);
		paramterHot.put("sort_type", "0");
		paramterHot.put("sid", MyApplication.sid);
		ConnectServer.getResualt(this, paramterHot, NetworkAction.热门商品,
				Url.URL_HOTSALE);
		// 秒杀商品
		paramterSeckill = new HashMap<String, String>();
		paramterSeckill.put("act", "list");
		paramterSeckill.put("sid", MyApplication.sid);
		paramterSeckill.put("nowpage", String.valueOf(page));
		paramterSeckill.put("pagesize", pageSize);
		ConnectServer.getResualt(this, paramterSeckill, NetworkAction.秒杀商品,
				Url.URL_SECKILL);
	}

	// 处理返回数据结果
	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		// Log.i(MyApplication.TAG, "showResualt-->"+response.toString());
		if (request.equals(NetworkAction.首页广告)) {
			final JSONArray lists = response.getJSONArray("list");
			Log.i(MyApplication.TAG, "lists.length()-->" + lists.length());
			// 如果没有首页图片直接返回
			if (lists.length() == 0)
				return;
			// 如果有首页广告图片并且没有获取到实际网络图片的宽高比例的时候先获取其相对高度
			if (lists.length() > 0 && !getHeight) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						JSONObject item;
						try {
							item = lists.getJSONObject(0);
							String path = Url.URL_IMGPATH
									+ item.getString("attachments_path");
							URL url = new URL(path);
							String responseCode = url.openConnection()
									.getHeaderField(0);
							Bitmap map = BitmapFactory.decodeStream(url
									.openStream());
							int height = map.getHeight();
							int width = map.getWidth();
							 Log.i(MyApplication.TAG, "height-->"+height);
							newHeight = (int) (MyApplication.width * ((double) height / width));
							getHeight = true;
							 Log.i(MyApplication.TAG, "newHeight-->"+newHeight);
							ConnectServer.getResualt(Home.this, paramter,
									NetworkAction.首页广告, Url.URL_INDEX);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				thread.start();
				return;
			}

			// 如果有首页图片则循环显示
			for (int i = 0; i < lists.length(); i++) {
				JSONObject item = lists.getJSONObject(i);
				NetworkImageView netView = new NetworkImageView(Home.this);
				String path = Url.URL_IMGPATH
						+ item.getString("attachments_path");
				Log.i(MyApplication.TAG, "path-->" + path);
				netView.setAdjustViewBounds(false);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, newHeight);
				netView.setLayoutParams(layoutParams);
				MyApplication.client.getImageForNetImageView(path, netView,
						R.drawable.ic_launcher);
				flipper.addView(netView);
				flipper.setInAnimation(Home.this, R.anim.view_in_from_right);
				flipper.setOutAnimation(Home.this, R.anim.view_out_to_left);
				RadioGroup.LayoutParams rbParams = new RadioGroup.LayoutParams(
						20, 5);
				rbParams.setMargins(0, 0, 10, 0);
				// 如果图片的数量大于1才开始循环播放
				RadioButton rb = new RadioButton(flipperTxt.getContext());
				rb.setBackgroundDrawable(MyApplication.resources
						.getDrawable(R.drawable.home_img_bg));
				rb.setId(i);
				// 设置透明色来去掉radiobutton原来的button按钮
				rb.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
				if (i == 0)
					rb.setChecked(true);
				netView.setTag(rb);
				flipperTxt.addView(rb, rbParams);
			}
			if (lists.length() > 1) {
				flipper.setFlipInterval(3000);
				flipper.startFlipping();
			}
		} else if (request.equals(NetworkAction.热门商品)) {
			page++;// 当前页加一
			totalPage = Integer.valueOf(response.getString("totalpage"));// 获取总页码

			// 判断是否还要加载数据
			if (page > totalPage)
				load = false;
			else
				load = true;
			JSONArray lists = response.getJSONArray("list");// 获取数据集
			for (int i = 0; i < lists.length(); i++) {
				JSONObject product = lists.getJSONObject(i);
				Product newProduct = new Product();
				newProduct.setId(product.getString("product_id"));
				newProduct.setName(product.getString("product_name"));
				newProduct.setStorePrice(product.getString("store_price"));
				newProduct.setImgPath(product.getString("product_photo"));
				hotProduct.add(newProduct);// 新获取到的数据添加到数据集合中
			}
			adapterHot.notifyDataSetChanged();// 通知适配器数据发生变化了
		} else if (request.equals(NetworkAction.秒杀商品)) {// 获取秒杀商品
			secKillProduct.clear();
			page++;// 当前页加一
			totalPage = Integer.valueOf(response.getString("totalpage"));// 获取总页码

			// 判断是否还要加载数据
			if (page > totalPage)
				load = false;
			else
				load = true;
			String time = response.getString("time");
			JSONArray lists = response.getJSONArray("list");// 获取数据集
			for (int i = 0; i < lists.length(); i++) {
				JSONObject product = lists.getJSONObject(i);
				Product newProduct = new Product();
				newProduct.setSkID(product.getString("SKID"));
				newProduct.setId(product.getString("ProductID"));
				newProduct.setName(product.getString("SKName"));
				newProduct.setSKPrice(product.getString("SKPrice"));
				newProduct.setOutEndTime(product.getString("OutEndTime"));
				newProduct.setImgPath(product.getString("Images"));
				newProduct.setTime(time);
				secKillProduct.add(newProduct);// 新获取到的数据添加到数据集合中
			}
			adapterSecKill.notifyDataSetChanged();// 通知适配器数据发生变化了

		}
	}

	// 滚动条点击和滑动事件监听器
	// private class TouchListenerImpl implements OnTouchListener {
	// @Override
	// public boolean onTouch(View view, MotionEvent motionEvent) {
	// switch (motionEvent.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	//
	// break;
	// case MotionEvent.ACTION_MOVE:
	// int scrollY = view.getScrollY();
	// int height = view.getHeight();
	// int scrollViewMeasuredHeight = srollView.getChildAt(0)
	// .getMeasuredHeight();
	// // 如果滑动到顶端的事件
	// if (scrollY == 0) {
	//
	// }
	// // 如果滑动到底部的事件
	// if ((scrollY + height) == scrollViewMeasuredHeight) {
	// // 如果还需要加载数据的话按照不同的模块获取不同的数据
	// if (load) {
	// load = false;
	//
	// // 热门商品显示模式
	// if (hotModule) {
	// // MyApplication.progressShow(Home.this, "数据");
	// // sendDataToServer(NetworkAction.热门商品);// 获取热门商品
	// } else if (secKillModule) {
	// MyApplication.progressShow(Home.this, "数据");
	// // sendDataToServer(NetworkAction.秒杀商品);// 获取秒杀商品
	// }
	// }
	//
	// }
	// break;
	//
	// default:
	// break;
	// }
	// return false;
	// }
	//
	// };
	@Override
	public boolean onDown(MotionEvent e) {
		Log.i("test", "onDown");
		flipper.stopFlipping();
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// ((RadioButton)flipper.getCurrentView().getTag()).setChecked(true);
		if (e1.getX() > e2.getX()) {
			flipper.setInAnimation(Home.this, R.anim.view_in_from_right);
			flipper.setOutAnimation(Home.this, R.anim.view_out_to_left);
			flipper.showNext();
		} else {

			flipper.setInAnimation(Home.this, R.anim.view_in_from_left);
			flipper.setOutAnimation(Home.this, R.anim.view_out_to_right);
			flipper.showPrevious();
		}

		flipper.startFlipping();
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Toast.makeText(this, "点击广告图", 2000).show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long productId) {

		Intent intent = new Intent().setClass(this, ProductDetail.class);
		
		if (parent.getId() == R.id.home_seckill_gridview) {
			Product product = (Product) secKillProduct.get(position);
			intent.putExtra("productId",product.getId());
			intent.putExtra("skid",product.getSkID());
//			Log.i(MyApplication.TAG,"productId-->"+product.getId()+"  skid-->"+product.getSkID());
		}
		else if(parent.getId() == R.id.home_hot_gridview)
		{
			intent.putExtra("productId", String.valueOf(productId));
		}
//		Toast.makeText(this, String.valueOf(productId), 2000).show();
		 startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		// 秒杀更多按钮
		case R.id.home_seckill_more_btn:
			intent.setClass(this, SeckillProduct.class);
			MyApplication.seckillModule=true;
			break;
		// 热门商品更多按钮
		case R.id.home_hot_more_btn:
			intent.setClass(this, HotProduct.class);
			break;
		}

		startActivity(intent);
	}

}
