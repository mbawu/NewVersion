package com.test.utils;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.test.base.MenuActivity;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;

public class ConnectServer {
	
	public static void getResualt(final Context context,HashMap<String, String> paramter,final NetworkAction request,String url)
	 {
	   Log.i(MyApplication.TAG, MyApplication.getUrl(paramter,url));
	   Log.i(MyApplication.TAG, url);
	   Log.i(MyApplication.TAG, paramter.toString());
	   MyApplication.client.postWithURL(url, paramter,
	     new Listener<JSONObject>() {
	      public void onResponse(JSONObject response) {
	       try {
	       Log.i(MyApplication.TAG, request+"response-->"+response.toString());
	       if(context instanceof MenuActivity)
	    	   ((MenuActivity)context).showResualt(response,request);
	       else
	    	   ((NormalActivity)context).showResualt(response,request);
//	        int code = response.getInt("code");
//	        if (response.getInt("code") == 1) {
	        	
//	        } else {
//	         Toast.makeText(context, ErrorMsg.getErrorMsg(request,code), 2000).show();
//	        }
	       } catch (JSONException e) {
	        Log.i(MyApplication.TAG, "JSONException-->"+e.getMessage());
	       }
	      }
	     }, new ErrorListener() {
	      @Override
	      public void onErrorResponse(VolleyError error) {
	    	  Toast.makeText(context, "访问服务器出错!", 2000).show();
	       Log.i(MyApplication.TAG, "onErrorResponse-->"+error.getMessage());
	      }
	     });

	 }
}
