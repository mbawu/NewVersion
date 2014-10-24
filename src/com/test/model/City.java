package com.test.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import com.test.base.MyApplication;
import com.test.base.Url;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * 城市列表管理类
 * 
 * @author Administrator
 * 
 */
public class City {
	public static HashMap<String, String> proviceData = new HashMap<String, String>();// 省级列表
	public static HashMap<String, String> cityData = new HashMap<String, String>();// 市级列表
	public static HashMap<String, String> areaData = new HashMap<String, String>();// 区域列表
	
	
	/**
	 * 获取省级列表
	 * 
	 * @return
	 */
	public static ArrayList getProviceList() {
		
		
		ArrayList list = new ArrayList<String>();
		
		Object[] values=proviceData.values().toArray();
		for (int i = 0; i < values.length; i++) {
			Log.i(MyApplication.TAG, "i-->"+values[i].toString());
			list.add(values[i].toString());
		}
//		Log.i(MyApplication.TAG, "proviceData-->" + proviceData.size());
//		ArrayList list = new ArrayList<String>();
//		Iterator iterator = proviceData.keySet().iterator();
//		while (iterator.hasNext()) {
//			// value = hashmap.get(iterator.next());
//			
//			list.add(proviceData.get(iterator.next()));
//		}
		return list;
	}

	/**
	 * 根据需要获取的列表名称以及城市名称列出对应的城市ID
	 * @param listName  需要获取的是市级列表还是区域列表
	 * @param cityName  需要的城市或区域的名称
	 * @return
	 */
	public static String getIdFromList(String listName, String cityName) {
		String cityId = null;
		Iterator iter = null;
		if (listName.equals("province")) {
			iter = proviceData.entrySet().iterator();
		}
		else if(listName.equals("city"))
			iter = cityData.entrySet().iterator();
		else if(listName.equals("area"))
			iter = areaData.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			if (val.equals(cityName)) {
				cityId=key.toString();
			}
//			Log.i(MyApplication.TAG, "val-->"+val.toString()+"  key-->"+key.toString());
//			Log.i(MyApplication.TAG, "equals-->"+cityName.equals(val)+" cityId--> "+cityId);
		}
		return cityId;
	}

	/**
	 * 获取市级列表
	 * 
	 * @return
	 */
	public static ArrayList getCityList() {
		ArrayList list = new ArrayList<String>();
		Iterator iterator = cityData.keySet().iterator();
		while (iterator.hasNext()) {
			// value = hashmap.get(iterator.next());
			list.add(cityData.get(iterator.next()));
		}
		return list;
	}

	
	/**
	 * 获取区域列表
	 * 
	 * @return
	 */
	public static ArrayList getAreaList() {
		ArrayList list = new ArrayList<String>();
		Iterator iterator = areaData.keySet().iterator();
		while (iterator.hasNext()) {
			// value = hashmap.get(iterator.next());
			list.add(areaData.get(iterator.next()));
		}
		return list;
	}
}
