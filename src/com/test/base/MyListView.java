package com.test.base;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	  /** 
     * ÉèÖÃ²»¹ö¶¯ 
*/  
public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
       int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
            MeasureSpec.AT_MOST);  
       super.onMeasure(widthMeasureSpec, expandSpec);  
  
}  
}
