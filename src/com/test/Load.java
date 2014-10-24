package com.test;

import cn.jpush.android.api.JPushInterface;

import com.test.base.MyApplication;
import com.test.product.Home;
import com.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

/*入口Activity 
 整个程序的主入口*/
public class Load extends Activity {
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		/* 程序初始化的时候获得屏幕宽高 */
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		MyApplication.width = metric.widthPixels;
		MyApplication.height = metric.heightPixels;
		
		handler = new Handler();
		handler.postDelayed(new startMainActivity(), 2000);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	class startMainActivity implements Runnable{

		@Override
		public void run() {
			//初始化JPUSH
			Intent intent = new Intent().setClass(Load.this, Home.class);
			startActivity(intent);
			//检查配置文件是否打开消息推送功能
			MyApplication.jPush=MyApplication.sp.getBoolean("push", true);
			//如果打开消息推送功能才初始化该功能
//			if(MyApplication.jPush){
//				JPushInterface.setDebugMode(true);
//		        JPushInterface.init(MyApplication.context);
//			}
			Load.this.finish();
		}
		
	}
}
