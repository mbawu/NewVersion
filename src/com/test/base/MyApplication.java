package com.test.base;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



import cn.jpush.android.api.JPushInterface;

import com.test.utils.CacheManager;
import com.test.utils.MyHttpClient;
import com.test.model.City;
import com.test.model.Product;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;

public class MyApplication extends Application {
	public static boolean loginStat = false; // 登录状态
	public static boolean jPush = false; // 是否接受消息推送
	public static boolean goToOrder=false;//是否要跳转到查看订单的页面
	public static ProgressDialog mypDialog; // 全局进度条
	public static int width; // 屏幕宽
	public static int height; // 屏幕高
	public static LayoutInflater Inflater; // 布局填充器
	public static String seskey = ""; // 登录注册返回的身份秘钥
	public static String uid = ""; // 登录注册返回的用户id
	private static MyApplication instance; // Myapplication对象
	private List<Activity> mList = new LinkedList<Activity>(); // 本地集合存放Activity引用
	public static SharedPreferences sp; // 本地存储SharedPreferences
	public static Editor ed; // 本地存储编辑器Editor
	public CacheManager mcCacheManager; // 缓存管理器
	public static boolean registerSuc = false; // 注册成功以后通知登录页面是否需要刷新并登录
	public static Resources resources;
	public static MyHttpClient client;
	public static String TAG = "Beidahuang";// 测试用的TAG
	public static int subStringLength=20;//如果产品名称过长则保留字符串的长度
	public static ArrayList<Object> shopCartList;//放置到购物车的商品的集合
	public static ShopCartManager shopCartManager;//购物车管理类
	public static Boolean shopcart_refresh = false;//购物车数据发生变化时是否需要刷新购物车数据
	public static String sid="15";//商家ID
	public static boolean exit=false;
	public static Context context;
	public static boolean comment=false;//是否在评论订单的状态


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context=getApplicationContext();
		shopCartManager=new ShopCartManager(getApplicationContext());
		shopCartList=new ArrayList<Object>();
		try {
			shopCartList = shopCartManager.readShopCart();
		} catch (StreamCorruptedException e) {
			shopCartManager.dele();
			e.printStackTrace();
		} catch (IOException e) {
			shopCartManager.dele();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			shopCartManager.dele();
			e.printStackTrace();
		}
		/*
		 * 初始化SharedPreferences
		 */
		sp = getSharedPreferences("hrht", MODE_PRIVATE);
		/*
		 * 初始化LayoutInflater
		 */
		Inflater = LayoutInflater.from(getApplicationContext());
		ed = sp.edit();

		resources = getResources();
		/*
		 * 初始化Volley框架的Http工具类
		 */
		client = MyHttpClient.getInstance(MyApplication.this
				.getApplicationContext());
		/*
		 * 实例化缓存管理器
		 */
		mcCacheManager = new CacheManager(getApplicationContext());
		// city=new City();
	}

	/**
	 * 获取加载的类名
	 * 
	 * @param activity
	 * @return
	 */
	public String getClassName(Class myclass) {
		String fullName = myclass.getName();
		fullName = fullName.substring(fullName.lastIndexOf(".") + 1);
		return fullName;
	}

	/*
	 * 获取缓存管理器
	 */
	public CacheManager getCacheManager() {
		if (mcCacheManager == null) {
			mcCacheManager = new CacheManager(getApplicationContext());
		}

		return mcCacheManager;
	}

	/*
	 * 同步安全方式获取MyApplication单例实例
	 */
	public synchronized static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	// 打印需要打印的信息
	public static void printLog(String className, String msg) {
		Log.i(MyApplication.TAG, className + "--->" + msg);
	}

	/*
	 * 将Activity加入到管理器中
	 */
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void removeActivity(Activity finishActivity)
	{
		for (Activity activity : mList) {
			if (activity.equals(finishActivity))
				activity.finish();
		}
	}
	
	/*
	 * 整个应用程序退出，循环遍历集合内没有销毁的Activity，全都销毁以后再退出，保证应用程序正常关闭退出
	 */
	public void exit() {
		try {
			printLog("application", "exit" + mList.size());
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
			printLog("application", "exit" + mList.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!exit)
				System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	
	/**
	 * 全局进度条，在任何一个activity中都可以调用该进度条，传入当前context和需要显示的文字即可
	 * 在需要出现进度条的模块调用该方法，在执行完网络请求以后无论何种情况都会先结束掉该进度条
	 * @param context  上下文对象
	 * @param title  标题
	 * @param msg  进度条显示的内容
	 */
	public static void progressShow(Context context, String msg) {
		
		mypDialog = new ProgressDialog(context);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置进度条风格，风格为长形，有刻度的
			mypDialog.setTitle("加载");
		// 设置ProgressDialog 标题
		mypDialog.setMessage(msg+"加载中...");
		// 设置ProgressDialog 提示信息
		// mypDialog.setIcon(R.drawable.android);
		// //设置ProgressDialog 标题图标
		mypDialog.setProgress(40);
		// 设置ProgressDialog 进度条进度
		// mypDialog.setButton("地狱曙光",this);
		// //设置ProgressDialog 的一个Button
		mypDialog.setCancelable(true);
		// 设置ProgressDialog 是否可以按退回按键取消
		mypDialog.show();
		// 让ProgressDialog显示
	}

	// 关闭进度条
	public static void progressClose() {
		mypDialog.dismiss();
	}

	// 获取拼接出来的请求字符串
	public static String getUrl(HashMap<String, String> paramter, String url) {
		Iterator iter = paramter.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (count == 0)
				url = url + "?" + key + "=" + val;
			else
				url = url + "&" + key + "=" + val;
			count++;
		}
		return url;
	}

	public static String limitString(String name)
	{
		if(name.length()>subStringLength)
		{
			name=name.substring(0, subStringLength)+"...";
		}
		return name;
	}
}
