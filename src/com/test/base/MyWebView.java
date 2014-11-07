package com.test.base;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class MyWebView extends WebView {

	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public boolean onTouch(View v, MotionEvent event) {
		
		float OldX1= 0;
		float OldY1= 0;
		float OldX2 = 0;
		float OldY2= 0;
		float NewX1 = 0;
		float NewY1= 0;
		float NewX2= 0;
		float NewY2= 0;
		             // TODO Auto-generated method stub
		             switch (event.getAction()) {
		               case MotionEvent.ACTION_POINTER_2_DOWN:
		                        if (event.getPointerCount() == 2) {
		                               for (int i = 0; i < event.getPointerCount(); i++) {
		                                       if (i == 0) {
		                                             OldX1 = event.getX(i);
	                                              OldY1 = event.getY(i);
		                                      } else if (i == 1) {
		                                              OldX2 = event.getX(i);
		                                              OldY2 = event.getY(i);
	                                      }
		                               }
		                        }
	                      break;
		                case MotionEvent.ACTION_MOVE:
	                        if (event.getPointerCount() == 2) {
		                                for (int i = 0; i < event.getPointerCount(); i++) {
		                                       if (i == 0) {
		                                               NewX1 = event.getX(i);
		                                               NewY1 = event.getY(i);
		                                      } else if (i == 1) {
		                                              NewX2 = event.getX(i);
	                                              NewY2 = event.getY(i);
		                                      }
	                               }
		                              float disOld = (float) Math.sqrt((Math.pow(OldX2 - OldX1, 2) + Math.pow(
		                                               OldY2 - OldY1, 2)));
		                              float disNew = (float) Math.sqrt((Math.pow(NewX2 - NewX1, 2) + Math.pow(
	                                              NewY2 - NewY1, 2)));
	                               Log.d("onTouch","disOld="+disOld+"|disNew="+disNew);
	                               if (disOld - disNew >= 25) {
		                                       // ËõÐ¡
                                      ((WebView) v).zoomOut();
		                                       
		                             } else if(disNew - disOld >= 25){
		                                      // ·Å´ó
	                                        ((WebView) v).zoomIn();
		                               }
		                              OldX1 = NewX1;
		                                 OldX2 = NewX2;
		                                 OldY1 = NewY1;
		                               OldY2 = NewY2;
		                         }
		                }
		
		                return false;
		        }

}
