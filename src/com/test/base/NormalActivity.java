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
import android.view.KeyEvent;

/**
 * 普通的activity继承的父类
 * @author Administrator
 *
 */
public abstract class NormalActivity extends Activity implements showResualtI{

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		MyApplication.getInstance().addActivity(this);
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
