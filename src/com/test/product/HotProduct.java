package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.R;
import com.test.base.ChangeTime;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.MyGridView;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.base.Url;
import com.test.model.Product;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class HotProduct extends NormalActivity implements OnClickListener{

	private Title title;// 设置标题栏
	private MyGridView gridView;
	private ArrayList<Object> hotProduct; // 数据集合
	private MyAdapter adapterHot; // 秒杀商品适配器
	HashMap<String, String> paramterHot;
	private int newHeight = 0;
	private boolean load;
	private int page = 1; // 当前页码
	private String pageSize = "10"; // 每页显示的数据条数
	private int totalPage = 0; // 总页码
	private ImageView getMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot);
		initView();
		initData();
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		gridView = (MyGridView) findViewById(R.id.hot_gridview);
		hotProduct = new ArrayList<Object>();
		adapterHot = new MyAdapter(this, NetworkAction.热门商品, hotProduct);
		paramterHot = new HashMap<String, String>();
		gridView.setAdapter(adapterHot);
		getMore= (ImageView) findViewById(R.id.getMore);
		getMore.setOnClickListener(this);
	}

	private void initData() {
		title.setModule(2);
		title.setTitleTxt("热门商品");
		// 热门商品
		paramterHot.put("act", "hotsale");
		paramterHot.put("CacheID", "");
		paramterHot.put("CacheID1", "0");
		paramterHot.put("brans", "0");
		paramterHot.put("cates", "0");
		paramterHot.put("clears", "0");
		paramterHot.put("keyname", "");
		paramterHot.put("keyname1", "");
		
		paramterHot.put("pagesize", pageSize);
		paramterHot.put("sort_type", "0");
		paramterHot.put("sid", MyApplication.sid);
		ConnectServer.getResualt(this, paramterHot, NetworkAction.热门商品,
				Url.URL_HOTSALE);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		Log.i("test", "showResualt");
		page++;// 当前页加一
		totalPage = Integer.valueOf(response.getString("totalpage"));// 获取总页码

		// 判断是否还要加载数据
		if (page > totalPage)
			getMore.setVisibility(View.GONE);
		JSONArray lists = response.getJSONArray("list");// 获取数据集
		for (int i = 0; i < lists.length(); i++) {
			JSONObject product = lists.getJSONObject(i);
			Product newProduct = new Product();
			newProduct.setId(product.getString("product_id"));
			newProduct.setName(product.getString("product_name"));
			newProduct.setStorePrice(product.getString("store_price"));
			newProduct.setImgPath(product.getString("product_photo"));
			hotProduct.add(newProduct);// 新获取到的数据添加到数据集合中
		}
		adapterHot.notifyDataSetChanged();// 通知适配器数据发生变化了
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getMore:
			paramterHot.remove(paramterHot.get("nowpage"));
			paramterHot.put("nowpage", String.valueOf(page));
			ConnectServer.getResualt(this, paramterHot, NetworkAction.热门商品,
					Url.URL_HOTSALE);
			break;

		}
		
	}

}
