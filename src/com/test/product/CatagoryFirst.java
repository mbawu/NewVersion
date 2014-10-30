package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.test.base.Url;
import com.test.R;
import com.test.base.MenuActivity;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.Title;
import com.test.model.Category;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class CatagoryFirst extends MenuActivity {

	private Title title;// 设置标题栏
	// private RadioGroup rg;
	private ListView listView;
	private ArrayList<Object> listFirst;
	private ArrayList<Object> listSecond;
	private ArrayList<Object> listThird;
	private ArrayList<Object> listSecondTemp;
	private MyAdapter adapterFirst;
	private MyAdapter adapterSecond;
	private GridView gridView;
	private HashMap<String, String> paramterFirst;
	private boolean isThird=false;//是否有三级分类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory);
		initView();
		initData();
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(3);
		title.setTitleTxt("列表");
		listView = (ListView) findViewById(R.id.catatgory_first);
		gridView = (GridView) findViewById(R.id.catatgory_second);
		listFirst = new ArrayList<Object>();
		listSecond = new ArrayList<Object>();
		listThird = new ArrayList<Object>();
		listSecondTemp=new ArrayList<Object>();
		adapterFirst = new MyAdapter(this, NetworkAction.一级分类, listFirst);
		adapterSecond=new MyAdapter(this, NetworkAction.二级分类, listSecondTemp);
		listView.setAdapter(adapterFirst);
		listView.setDivider(null);
		gridView.setAdapter(adapterSecond);
		paramterFirst = new HashMap<String, String>();
		
	}

	public void getCatagorySecond(String parentId)
	{
		listSecondTemp.clear();
		for (int i = 0; i < listSecond.size(); i++) {
			Category category=(Category) listSecond.get(i);
			if(parentId.equals(category.getParent_catid()))
			{
				listSecondTemp.add(category);
			}
		}
		Log.i("test", "parentid->"+parentId+"  size->"+listSecondTemp.size());
		adapterSecond.notifyDataSetChanged();
	}
	
	private void initData() {
		//获取分类列表
		paramterFirst.put("act", "category");
		paramterFirst.put("sid", MyApplication.sid);
		ConnectServer.getResualt(this, paramterFirst, NetworkAction.一级分类,
				Url.URL_INDEX);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		if (request.equals(NetworkAction.一级分类)) {
			JSONArray firstList = response.getJSONArray("catlist1");
			for (int i = 0; i < firstList.length(); i++) {
				Category category = new Category();
				JSONObject item = firstList.getJSONObject(i);
				category.setCategory_id(item.getString("category_id"));
				category.setCacheID(item.getString("CacheID"));
				category.setParent_catid(item.getString("parent_catid"));
				category.setCategory_level(item.getString("category_level"));

				category.setCategory_name(item.getString("category_name"));
				listFirst.add(i, category);
			}

			adapterFirst.notifyDataSetChanged();

			// 获取二级分类数据
			JSONArray secList = response.getJSONArray("catlist2");
			for (int i = 0; i < secList.length(); i++) {
				Category category = new Category();
				JSONObject item = secList.getJSONObject(i);
				category.setCategory_id(item.getString("category_id"));
				category.setCacheID(item.getString("CacheID"));
				category.setParent_catid(item.getString("parent_catid"));
				category.setCategory_level(item.getString("category_level"));

				category.setCategory_name(item.getString("category_name"));
				listSecond.add(i, category);

			}

			// 获取三级分类数据
			JSONArray thirdList = response
					.getJSONArray("catlist3");
			for (int i = 0; i < thirdList.length(); i++) {
				Category category = new Category();
				JSONObject item = thirdList
						.getJSONObject(i);
				category.setCategory_id(item
						.getString("category_id"));
				category.setCacheID(item
						.getString("CacheID"));
				category.setParent_catid(item
						.getString("parent_catid"));
				category.setCategory_level(item
						.getString("category_level"));
				category.setCategory_name(item
						.getString("category_name"));
				listThird.add(i, category);
			}
			
			//判断是否有三级分类
			if(listThird.size()>0)
				isThird=true;
		}
	}
}
