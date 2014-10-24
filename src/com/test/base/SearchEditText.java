package com.test.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class SearchEditText extends EditText {

	public SearchEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	 public SearchEditText(Context context, AttributeSet attrs) {
		 
         super(context, attrs);

      }
	 protected void onDraw(Canvas canvas) {
		 Log.i("test", "onDraw");
         Paint paint = new Paint();
//         this.setImeOptions(EditorInfo.IME_ACTION_DONE);
         paint.setStyle(Style.STROKE);

         paint.setStrokeWidth(2);

//         if(this.isFocused() == true)

             paint.setColor(Color.parseColor("#ffffff"));

//       else

//            paint.setColor(Color.rgb(0,173,173));

//       canvas.drawRoundRect(new RectF(2+this.getScrollX(), 2+this.getScrollY(), this.getWidth()-3+this.getScrollX(), this.getHeight()+ this.getScrollY()-1), 3,3, paint);
       canvas.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight(), paint);
       canvas.drawLine(0, this.getHeight(), 0, this.getHeight()-10, paint);
       canvas.drawLine(this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight()-10, paint);
       super.onDraw(canvas);

    }

}
