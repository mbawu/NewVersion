package com.test.product;


import com.test.R;
import com.test.MenuBottom;
import com.test.base.Title;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubmitSuccess extends Activity {
	
	private Title title;// 设置标题栏
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.submit_success);
	title = (Title) findViewById(R.id.title);
	title.setModule(4);
	title.setTitleTxt("结算成功");
	Button viewBtn=(Button) findViewById(R.id.submit_view_btn);
	viewBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	});
}
}
