package com.test.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.test.base.MyApplication;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: jimmy Date: 8/9/13 Time: 1:52 PM To change
 * this template use File | Settings | File Templates.
 */
public class MyRequest extends Request<JSONObject> {
	private final Response.Listener<JSONObject> mListener;
	private final Map<String, String> mParams;

	public MyRequest(int method, String url, Map<String, String> params,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mParams = params;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}

	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			if(MyApplication.mypDialog!=null)
				MyApplication.progressClose();
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
		finally
		{
			if(MyApplication.mypDialog!=null)
			MyApplication.progressClose();
		}
	}

	@Override
	public void setTag(Object tag) {
		// TODO Auto-generated method stub
		super.setTag(tag);
	}

}
