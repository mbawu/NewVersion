package com.test.person;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.test.R;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.utils.NetworkAction;

public class Person extends NormalActivity implements OnClickListener {

	private Title title;// 设置标题栏
	private FrameLayout order;// 订单查询
	private FrameLayout message;// 我的消息
	private FrameLayout address;// 地址管理
	private FrameLayout sec;// 账户安全
	private FrameLayout sugguest;// 意见反馈
	private FrameLayout coupon;// 我的优惠券
	private TextView loginBtn;// 跳转到登录页面的按钮
	private NetworkImageView img;// 登录以后显示的个人头像
	private FrameLayout loginout; // 没有登录时的模块
	private FrameLayout login; // 登录时的模块
	private TextView nameTxt;// 登录以后显示的名称

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initView();
		initData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 如果是从其他页面返回该页面并且登录的情况下
		if (!isFirstResume && MyApplication.loginStat) {
			loginout.setVisibility(View.GONE);
			login.setVisibility(View.VISIBLE);
			// 先取昵称，没有的话就取用户名
			String name = MyApplication.sp.getString("nickname","");
			if(name.equals(""))
				name=MyApplication.sp.getString("username", "");
			nameTxt.setText(name);
			// 设置用户头像，如果没有则显示未登录时候的头像
			String photo = MyApplication.sp.getString("photo", "");
			if (!photo.equals(""))
				MyApplication.client.getImageForNetImageView(photo, img,
						R.drawable.loginout_img);
		}
		// 如果是从其他页面返回该页面并且已退出登录的情况下
		else if (!isFirstResume && !MyApplication.loginStat) {
			loginout.setVisibility(View.VISIBLE);
			login.setVisibility(View.GONE);
		}
		super.onResume();

	}

	private void initView() {
		loginout = (FrameLayout) findViewById(R.id.loginout_layout);
		login = (FrameLayout) findViewById(R.id.login_layout);
		title = (Title) findViewById(R.id.title);
		title.setModule(6);
		title.setTitleTxt("我的");
		order = (FrameLayout) findViewById(R.id.person_order);
		message = (FrameLayout) findViewById(R.id.person_message);
		address = (FrameLayout) findViewById(R.id.person_address);
		sec = (FrameLayout) findViewById(R.id.person_sec);
		sugguest = (FrameLayout) findViewById(R.id.person_sugguest);
		coupon = (FrameLayout) findViewById(R.id.person_coupon);
		loginBtn = (TextView) findViewById(R.id.goto_login_btn);
		img = (NetworkImageView) findViewById(R.id.person_img);
		nameTxt = (TextView) findViewById(R.id.person_name);
	}

	private void initData() {
		order.setOnClickListener(this);
		message.setOnClickListener(this);
		address.setOnClickListener(this);
		sec.setOnClickListener(this);
		sugguest.setOnClickListener(this);
		coupon.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		img.setOnClickListener(this);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		// 订单查询
		case R.id.person_order:

			break;
		// 我的消息
		case R.id.person_message:

			break;
		// 地址管理
		case R.id.person_address:

			break;
		// 账户安全
		case R.id.person_sec:

			break;
		// 意见反馈
		case R.id.person_sugguest:

			break;
		// 我的优惠券
		case R.id.person_coupon:

			break;
		// 跳转到登录页面的按钮
		case R.id.goto_login_btn:
			intent = new Intent().setClass(this, Login.class);
			break;
		// 登录以后显示的个人头像，点击以后弹出菜单
		case R.id.person_img:

			break;
		}
		if (intent != null)
			startActivity(intent);

	}

}
