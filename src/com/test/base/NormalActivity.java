package com.test.base;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.test.base.CustomDialog;
import com.test.base.MyApplication;
import com.test.utils.NetworkAction;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 普通的activity继承的父类
 * @author Administrator
 *
 */
public abstract class NormalActivity extends Activity implements showResualtI{

	/*
	 * 根据该参数在onresume的super()方法之前判断是否是第一次进入该页面
	*	如果为真的话就是第一次进入该页面
	*	如果为假的话则是从其他页面返回到该页面的情况
	 */
	public boolean isFirstResume=true;
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MyApplication.getInstance().addActivity(this);
		Log.i(MyApplication.TAG,"NormalActivity->onStart" );
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isFirstResume=false;
		Log.i(MyApplication.TAG,"NormalActivity->onResume" );
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
		if(MyApplication.mypDialog!=null)
			MyApplication.mypDialog.dismiss();
	}
	
//	 public abstract void showResualt(JSONObject response,NetworkAction request) throws JSONException;
}

interface showResualtI{
	void showResualt(JSONObject response,NetworkAction request) throws JSONException;
}
