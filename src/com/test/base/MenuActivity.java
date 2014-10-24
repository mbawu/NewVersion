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
 * 主菜单上显示的activity继承的父类
 * @author Administrator
 *
 */
public abstract class MenuActivity extends Activity {


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
		if(MyApplication.mypDialog!=null)
			MyApplication.mypDialog.dismiss();
	}
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				
				CustomDialog.Builder builder = new CustomDialog.Builder(this);
				builder.setMessage("你确定退出吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										//退出前先保存购物车信息
										try {
											MyApplication.shopCartManager
													.saveProducts(MyApplication.shopCartList);
										} catch (IOException e) {

											e.printStackTrace();
										}
										finish();
										dialog.cancel();
										MyApplication.getInstance().exit();
									}
								})
						.setNegativeButton("返回",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				CustomDialog alert = builder.create();
				alert.show();
				return true;
			}

			return super.onKeyDown(keyCode, event);
		}
	 
	 public abstract void showResualt(JSONObject response,NetworkAction request) throws JSONException;
}
