package com.test.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.ViewFlipper;

public class MyViewFlipper  extends ViewFlipper {

	public MyViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		
		//获取当前view的索引
		int index=((RadioButton)getCurrentView().getTag()).getId();
		//如果是最后一个view的话
		if(index==getChildCount()-1)
			//让第一个单选框被选中
			((RadioButton)getChildAt(0).getTag()).setChecked(true);
		else
			//如果不是最后一个则让下一个的单选框被选中
			((RadioButton)getChildAt(index+1).getTag()).setChecked(true);
		super.showNext();
	}
	
	@Override
	public void showPrevious() {
		
		int index=((RadioButton)getCurrentView().getTag()).getId();
		if(index==0)
			((RadioButton)getChildAt(getChildCount()-1).getTag()).setChecked(true);
		else
			((RadioButton)getChildAt(index-1).getTag()).setChecked(true);
		super.showPrevious();
	}
}
