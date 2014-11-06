package com.test.person;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.base.MyApplication;
import com.test.R;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.base.Url;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class ForgetPwd extends NormalActivity implements OnClickListener {

	private Title title;// 设置标题栏
	private LinearLayout step1;
	private LinearLayout step2;
	private TextView step3;
	private int step = 1;
	private TextView nextStepBtn;// 下一步的按钮
	private EditText phoneNumTxt;// 手机号输入框
	private EditText codeTxt;// 验证码输入框
	private TextView getCodeTxt;// 点击获取验证码以后的提示信息
	private TextView getCode;// 获取验证码的按钮
	int seconds = 121;// 多少秒以后可用
	private CountSecond countNum;// 倒计时任务
	private HashMap<String, String> paramter;
	private String codeId;// 保存获取到的验证码ID
	private TextView newPwd;// 新的密码
	private int module;// 该页面的类型 1. 找回密码的页面 2.立即注册的页面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_pwd);
		initView();
		initData();
	}

	private void initView() {
		Intent intent = getIntent();
		module = Integer.valueOf(intent.getStringExtra("module"));
		title = (Title) findViewById(R.id.title);
		title.setModule(4);
		if (module == 1)
			title.setTitleTxt("找回密码");
		else if (module == 2)
			title.setTitleTxt("注册");
		step1 = (LinearLayout) findViewById(R.id.forget_step1);
		step2 = (LinearLayout) findViewById(R.id.forget_step2);
		step3 = (TextView) findViewById(R.id.forget_step3);
		nextStepBtn = (TextView) findViewById(R.id.forget_btn);
		phoneNumTxt = (EditText) findViewById(R.id.forget_phone);
		getCodeTxt = (TextView) findViewById(R.id.forget_sendTxt);
		getCode = (TextView) findViewById(R.id.forget_getcode_btn);
		getCode.setOnClickListener(this);
		codeTxt = (EditText) findViewById(R.id.forget_getcode_txt);
		countNum = new CountSecond();
		newPwd = (TextView) findViewById(R.id.forget_pwd_txt);
		paramter = new HashMap<String, String>();
	}

	private void initData() {

		nextStep();
		nextStepBtn.setOnClickListener(this);
	}

	private void nextStep() {
		if (step == 1) {
			step1.setVisibility(View.VISIBLE);
			step2.setVisibility(View.GONE);
			step3.setVisibility(View.GONE);
			nextStepBtn.setText("下一步");
		} else if (step == 2) {
			step1.setVisibility(View.GONE);
			step2.setVisibility(View.VISIBLE);
			step3.setVisibility(View.GONE);
			nextStepBtn.setText("提交");
		} else if (step == 3) {
			if (module == 2)
				step3.setText("恭喜您，注册账号已完成，请您牢记您的用户名和密码。");
			step1.setVisibility(View.GONE);
			step2.setVisibility(View.GONE);
			step3.setVisibility(View.VISIBLE);
			nextStepBtn.setText("完成");
		}
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		if (request.equals(NetworkAction.获取验证码)) {
			getCodeTxt.setVisibility(View.VISIBLE);
			codeId = response.getString("smsid");
			getCode.setOnClickListener(null);// 取消按钮的点击事件
			getCode.setBackgroundDrawable(ForgetPwd.this.getResources()
					.getDrawable(R.drawable.forget_getcode_select));// 让背景颜色变灰
			handler.post(countNum);// 执行倒计时任务

		} else if (request.equals(NetworkAction.验证验证码)) {
			// 如果验证码验证成功的话进入到第二步
			step = 2;
			nextStep();
		} else if (request.equals(NetworkAction.找回密码)) {
			//更新配置文件中的密码
			MyApplication.ed.putString("password", newPwd.getText().toString());
			MyApplication.registerSuc = true;
			MyApplication.ed.commit();
			// 如果修改密码成功的话进入到第三步
			step = 3;
			nextStep();
		}
		else if(request.equals(NetworkAction.用户注册))
		{
			try {
				MyApplication.shopCartManager
						.saveProducts(MyApplication.shopCartList);
				MyApplication.shopcart_refresh = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*
			 * 如果返回code1代表注册成功，
			 * 注册成功的时候将用户信息保存到本地SharedPreferences
			 */
			MyApplication.ed
					.putString("username", phoneNumTxt.getText().toString());
			MyApplication.ed.putString("password", newPwd.getText().toString());
			MyApplication.seskey = response
					.getString("sessionid");
			MyApplication.ed.putString("uid",
					response.getString("uid"));
			MyApplication.ed.putString("username",
					response.getString("username"));
			MyApplication.ed.putString("nickname",
					response.getString("nickname"));
			MyApplication.ed.putString("photo",
					response.getString("photo"));
			MyApplication.ed.putString("email",
					response.getString("email"));
			MyApplication.ed.putString("createtime",
					response.getString("createtime"));
			MyApplication.ed.putString("birthday",
					response.getString("birthday"));
			MyApplication.ed.putString("address",
					response.getString("address"));
			MyApplication.ed.putString("sex",
					response.getString("sex"));
			MyApplication.ed.putString("credit",
					response.getString("credit"));
			MyApplication.ed.putString("newfriends",
					response.getString("newfriends"));
			MyApplication.ed.putString("province_name",
					response.getString("province_name"));
			MyApplication.ed.putString("city_name",
					response.getString("city_name"));
			MyApplication.ed.putString("area_name",
					response.getString("area_name"));
			MyApplication.ed.putString("province_id",
					response.getString("province_id"));
			MyApplication.ed.putString("city_id",
					response.getString("city_id"));
			MyApplication.ed.putString("area_id",
					response.getString("area_id"));
			MyApplication.ed.putString("phone",
					phoneNumTxt.getText().toString());
			MyApplication.ed.commit();
			MyApplication.registerSuc = true;
			step = 3;
			nextStep();
		}
	}

	// 验证电话号码
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	@Override
	public void onClick(View v) {
		paramter.clear();
		String phoneNum = phoneNumTxt.getText().toString();
		String code = codeTxt.getText().toString();
		// 验证手机号码是否正确
		if (!isMobileNO(phoneNum)) {
			Toast.makeText(this, "请输入正确的手机号码！", 2000).show();
			return;
		}
		switch (v.getId()) {
		// 获取验证码的按钮
		case R.id.forget_getcode_btn:
			paramter.put("sid", MyApplication.sid);
			paramter.put("mobile", phoneNum);
			paramter.put("SmsContent", "找回密码验证码：");
			ConnectServer.getResualt(this, paramter, NetworkAction.获取验证码,
					Url.URL_VERIFICATION);
			break;
		// 下一步的按钮
		case R.id.forget_btn:
			if (step == 1) {

				if (!code.equals("")) {
					paramter.clear();
					paramter.put("act", "iscode");
					paramter.put("sid", MyApplication.sid);
					paramter.put("mobile", phoneNum);
					paramter.put("smskey", code);
					paramter.put("smsid", codeId);
					ConnectServer.getResualt(this, paramter,
							NetworkAction.验证验证码, Url.URL_MEMBER);
				} else
					Toast.makeText(this, "请输入收到的验证码！", 2000).show();
			} else if (step == 2) {
				String pwd = newPwd.getText().toString();
				if (pwd.length() < 6) {
					Toast.makeText(this, "请输入大于等于6位的密码！", 2000).show();
					return;
				}
				paramter.clear();
				if (module == 1) {
					paramter.put("sid", MyApplication.sid);
					paramter.put("act", "setpwd");
					paramter.put("smsid", codeId);
					paramter.put("mobile", phoneNum);
					paramter.put("password", pwd);
					paramter.put("smskey", code);
					paramter.put("username", phoneNum);
					ConnectServer.getResualt(this, paramter,
							NetworkAction.找回密码, Url.URL_MEMBER);
				}
				else if(module == 2)
				{
					paramter.put("act", "register");
					paramter.put("sid",MyApplication.sid);
					paramter.put("username", phoneNum);
					paramter.put("password", pwd);
					paramter.put("repassword", pwd);
					paramter.put("mobile", phoneNum);
					paramter.put("email", "");
					paramter.put("smsid", codeId);
					paramter.put("smskey", code);
					ConnectServer.getResualt(this, paramter,
							NetworkAction.用户注册, Url.URL_USERS);
				}
			} else if (step == 3) {
				finish();
			}
			break;

		}

	}

	Handler handler = new Handler() {
		int temp = seconds;

		public void handleMessage(Message msg) {
			temp--;
			getCode.setText(String.valueOf(temp));
			if (seconds == msg.arg1) {
				getCode.setOnClickListener(ForgetPwd.this);
				getCode.setBackgroundDrawable(ForgetPwd.this.getResources()
						.getDrawable(R.drawable.forget_getcode_noselect));
				getCode.setText("重新发送");
				getCodeTxt.setVisibility(View.GONE);
				temp = seconds;
			} else {
				handler.post(countNum);
			}
		};
	};

	// 倒计时任务，每次执行加1，然后发送消息
	public class CountSecond implements Runnable {
		int count = 0;

		@Override
		public void run() {
			// Log.i(MyApplication.TAG, "run-->"+count);
			count++;
			Message msg = new Message();
			msg.arg1 = count;
			handler.sendMessageDelayed(msg, 1000);

		}

	}

}
