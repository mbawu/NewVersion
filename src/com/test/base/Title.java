package com.test.base;

import com.test.R;
import com.test.person.More;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Title extends FrameLayout {

	private Context context;
	private LinearLayout backBtn;// 标题栏的后退按钮
	private LinearLayout msgBtn;// 消息按钮
	private FrameLayout searchLayout;// 搜索模块
	private LinearLayout moreBtn;// 更多模块
	private FrameLayout morePage;// 更多页面
	private LinearLayout txtLayout;// 标题模块
	private TextView titleTxt;// 标题栏文字
	private EditText searchTxt;

	public Title(Context context) {
		super(context, null);
	}

	public Title(final Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.title, this, true);
		backBtn = (LinearLayout) findViewById(R.id.backBtn);
		msgBtn = (LinearLayout) findViewById(R.id.msgBtn);
		titleTxt = (TextView) findViewById(R.id.title_txt);
		searchLayout = (FrameLayout) findViewById(R.id.searchLayout);
		moreBtn = (LinearLayout) findViewById(R.id.moreBtn);
		morePage = (FrameLayout) findViewById(R.id.morePage);
		txtLayout = (LinearLayout) findViewById(R.id.txtLayout);
		backBtn.setOnClickListener(new OnclickListener());
		msgBtn.setOnClickListener(new OnclickListener());
		moreBtn.setOnClickListener(new OnclickListener());
		morePage.setOnClickListener(new OnclickListener());
		searchTxt = (EditText) findViewById(R.id.searchTxt);
		searchTxt.setOnEditorActionListener(new MyApplication.OnEditorActionListener(context,searchTxt));
	}

	
	public class OnclickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent=null;
			switch (view.getId()) {
			case R.id.backBtn:
				((Activity) context).finish();
				break;
			case R.id.msgBtn:
				// 跳转到消息框
				break;
			case R.id.moreBtn:
				// 更多按钮的弹出框
				break;
			case R.id.morePage:
				// 跳转更多页面
				intent=new Intent().setClass(context, More.class);
				break;
			}
			if(intent!=null)
				((Activity)context).startActivity(intent);
		}
	}

	/**
	 * 设置标题栏模式 
	 * 1. 消息按钮+搜索框+更多按钮 
	 * 2. 后退按钮+标题名称+更多按钮 
	 * 3. 标题名称 
	 * 4. 后退按钮+标题名称 
	 * 5. 后退按钮+搜索框+更多按钮
	 * 6. 标题名称 +更多页面
	 * @param module
	 */
	public void setModule(int module) {
		switch (module) {
		case 1:
			msgBtn.setVisibility(View.VISIBLE);
			searchLayout.setVisibility(View.VISIBLE);
			moreBtn.setVisibility(View.VISIBLE);
			break;
		case 2:
			backBtn.setVisibility(View.VISIBLE);
			txtLayout.setVisibility(View.VISIBLE);
			moreBtn.setVisibility(View.VISIBLE);
			break;
		case 3:
			txtLayout.setVisibility(View.VISIBLE);
		case 4:
			backBtn.setVisibility(View.VISIBLE);
			txtLayout.setVisibility(View.VISIBLE);
			break;
		case 5:
			backBtn.setVisibility(View.VISIBLE);
			searchLayout.setVisibility(View.VISIBLE);
			moreBtn.setVisibility(View.VISIBLE);
			break;
		case 6:
			txtLayout.setVisibility(View.VISIBLE);
			morePage.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 设置后退按钮的可显示性
	 * @param visibility
	 */
	public void setBackBtnVisibility(boolean visibility) {
		if (visibility)
			backBtn.setVisibility(View.VISIBLE);
		else
			backBtn.setVisibility(View.GONE);
	}

	// 如果是有显示标题栏模块则设置该标题栏的文字内容
	public void setTitleTxt(String title) {
		titleTxt.setText(title);
	}
	
	//设置搜索框的文字内容
	public void setSearchTxt(String searchString) {
		searchTxt.setText(searchString);
	}
}
