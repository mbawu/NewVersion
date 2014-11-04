package com.test.person;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.test.R;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initView();
		initData();
	}

	private void initView() {
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

			break;
		// 登录以后显示的个人头像，点击以后弹出菜单
		case R.id.person_img:

			break;
		}

	}

}
