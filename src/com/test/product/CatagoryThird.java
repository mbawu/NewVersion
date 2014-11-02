package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
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
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.model.Category;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class CatagoryThird extends NormalActivity implements OnItemClickListener {

	private Title title;// 设置标题栏
	private ArrayList<Object> listThird;
	private MyAdapter adapterThird;
	private GridView gridView;
	private EditText catagorySearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory_thrid);
		initView();
		initData();
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(4);
		title.setTitleTxt("列表");
		gridView = (GridView) findViewById(R.id.catatgory_third);
		gridView.setOnItemClickListener(this);
		catagorySearch=(EditText) findViewById(R.id.catagory_search);
		catagorySearch.setOnEditorActionListener(new MyApplication.OnEditorActionListener(this, catagorySearch));
	}

	private void initData() {
		Intent intent = getIntent();
		listThird = (ArrayList<Object>) intent.getSerializableExtra("catagory");
		adapterThird = new MyAdapter(this, NetworkAction.三级分类, listThird);
		gridView.setAdapter(adapterThird);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long parentId) {
		Intent intent = new Intent().setClass(this, ProductShow.class);
		intent.putExtra("Category_id",
				((Category) listThird.get(position)).getCategory_id());
		intent.putExtra("CacheID",
				((Category) listThird.get(position)).getCacheID());
		MyApplication.searchModule=1;
		startActivity(intent);

	}
}
