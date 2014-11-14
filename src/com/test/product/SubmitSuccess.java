package com.test.product;

import com.test.R;
import com.test.MenuBottom;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;
import com.test.base.Title;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubmitSuccess extends Activity implements OnClickListener {

	private Title title;// 设置标题栏
	private Button backHome;
	private Button viewOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_success);
		title = (Title) findViewById(R.id.title);
		title.setModule(4);
		title.setTitleTxt("确认订单");
		backHome = (Button) findViewById(R.id.submit_view_home);
		viewOrder = (Button) findViewById(R.id.submit_view_order);
		backHome.setOnClickListener(this);
		viewOrder.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.submit_view_home:
			intent=new Intent().setClass(this, MenuBottom.class);
			 MenuBottom.tabHost.setCurrentTab(0);
			 MenuBottom.radioGroup.check(R.id.main_tab_home);
			break;
		case R.id.submit_view_order:

			break;
		}
		startActivity(intent);
		this.finish();
	}
}
