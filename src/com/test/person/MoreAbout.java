package com.test.person;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.test.R;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.utils.NetworkAction;

public class MoreAbout extends NormalActivity {

	private Title title;// 设置标题栏

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_about);
		initView();

	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(4);
		title.setTitleTxt("关于我们");

	}


	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		// TODO Auto-generated method stub

	}

}
