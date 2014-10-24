package com.test.base;

import com.test.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Title extends FrameLayout{
	
	private Context context;
	private LinearLayout backBtn;//标题栏的后退按钮
	private LinearLayout msgBtn;//消息按钮
	private FrameLayout searchLayout;//搜索模块
	private LinearLayout moreBtn;//更多模块
	private LinearLayout txtLayout;//标题模块
	private TextView titleTxt;//标题栏文字
	private SearchEditText searchTxt;
	
	public Title(Context context) {
        super(context,null);
    }
    
    public Title(final Context context,AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.title, this,true);
        backBtn=(LinearLayout) findViewById(R.id.backBtn);
        msgBtn=(LinearLayout) findViewById(R.id.msgBtn);
        titleTxt=(TextView) findViewById(R.id.title_txt);
        searchLayout=(FrameLayout) findViewById(R.id.searchLayout);
        moreBtn=(LinearLayout) findViewById(R.id.moreBtn);
        txtLayout=(LinearLayout) findViewById(R.id.txtLayout);
        backBtn.setOnClickListener(new OnclickListener());
        msgBtn.setOnClickListener(new OnclickListener());
        moreBtn.setOnClickListener(new OnclickListener());
        searchTxt=(SearchEditText) findViewById(R.id.searchTxt);
        searchTxt.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId ==EditorInfo.IME_ACTION_SEARCH){ 
					// 先隐藏键盘
					((InputMethodManager) searchTxt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(
					((Activity)context)
					.getCurrentFocus()
					.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
				
			}
				Toast.makeText(context, "你点了回车键", 2000).show();
				Toast.makeText(context, v.getText().toString(), 2000).show();
				return true;
			}
			
        	});
    }
    
    public class OnclickListener implements View.OnClickListener
    {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.backBtn:
				((Activity)context).finish();
				break;
			case R.id.msgBtn:
				//跳转到消息框
				break;
			case R.id.moreBtn:
				//更多按钮的弹出框
				break;
			default:
				break;
			}
			
		}
    }
    
    /**
     * 设置标题栏模式
     * 1. 消息按钮+搜索框+更多按钮
     * 2. 后退按钮+标题名称+更多按钮
     * @param module
     */
    public void setModule(int module)
    {
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
		default:
			break;
		}
    }
    
    //如果是有显示标题栏模块则设置该标题栏的文字内容
    public void setTitleTxt(String title)
    {
    	titleTxt.setText(title);
    }
}
