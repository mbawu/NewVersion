package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.test.model.Product;
import com.test.base.Url;
import com.test.R;
import com.test.base.MenuActivity;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.MyGridView;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.model.Category;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class ProductShow extends NormalActivity implements OnItemClickListener,
		OnClickListener, OnCheckedChangeListener {

	private Title title;// 设置标题栏
	private ArrayList<Object> products;
	private MyAdapter adapter;
	private MyGridView gridView;
	private HashMap<String, String> paramterCatagory;// 获取分类商品的请求参数
	private int page = 1; // 需要申请查看的数据的页码
	private String pageSize = "10";
//	private String sortType = "0"; // 排序方式 0销量，1价格升序，2价格降序，3人气，4上架时间，5好评
	private int totalpage = 0;
	private LinearLayout getMore;// 获取更多数据的按钮
	private RadioGroup radioGroup;
	private RadioButton showSort;// 综合排序
	private RadioButton popSort;// 综合排序弹出框选项
	private RadioButton popLow;// 综合排序弹出框选项
	private RadioButton popHigh;// 综合排序弹出框选项
	private RadioButton showSaleFirst;// 销量优先
	private RadioButton showFilter;// 筛选
	private PopupWindow sortPopWindow; // 综合排序PopupWindow
	private PopupWindow filterPopWindow; // 筛选PopupWindow

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_show);
		initView();
		initData();
	}

	private void initView() {
		title = (Title) findViewById(R.id.title);
		title.setModule(5);
		gridView = (MyGridView) findViewById(R.id.product_gridView);
		getMore = (LinearLayout) findViewById(R.id.getMore);
		getMore.setOnClickListener(this);
		radioGroup = (RadioGroup) findViewById(R.id.show_group);
		showSort = (RadioButton) findViewById(R.id.show_sort);
		showSort.setOnClickListener(this);
		showSaleFirst = (RadioButton) findViewById(R.id.show_salefirst);
		showFilter = (RadioButton) findViewById(R.id.show_filter);
		showSort.setOnCheckedChangeListener(this);
		showSaleFirst.setOnCheckedChangeListener(this);
		showFilter.setOnCheckedChangeListener(this);

		// radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// switch (checkedId) {
		// case R.id.show_sort:
		//
		// break;
		// case R.id.show_salefirst:
		//
		// break;
		// case R.id.show_filter:
		//
		// break;
		// }
		//
		// }
		// });
	}

	private void initData() {
		// 从分类列表跳转过来的情况
		Intent intent = getIntent();
		String Category_id = intent.getStringExtra("Category_id");
		String CacheID = intent.getStringExtra("CacheID");
		Log.i(MyApplication.TAG, "Category_id->" + Category_id);
		Log.i(MyApplication.TAG, "CacheID->" + CacheID);
		products = new ArrayList<Object>();
		adapter = new MyAdapter(this, NetworkAction.获取分类商品, products);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		paramterCatagory = new HashMap<String, String>();
		paramterCatagory.put("act", "product");
		paramterCatagory.put("sid", MyApplication.sid);
		paramterCatagory.put("store_id", MyApplication.sid);
		paramterCatagory.put("nowpage", String.valueOf(page));
		paramterCatagory.put("pagesize", pageSize);
		paramterCatagory.put("keyname", Category_id);
		paramterCatagory.put("CacheID", CacheID);
		paramterCatagory.put("keyname1", "0");
		paramterCatagory.put("CacheID1", "0");
		paramterCatagory.put("brans", "0");
		paramterCatagory.put("clears", "0");
		paramterCatagory.put("start_price", "0");
		paramterCatagory.put("end_price", "0");
		paramterCatagory.put("sort_type", "0");
		paramterCatagory.put("cates", "0");
		showSaleFirst.setChecked(true);
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		if (request.equals(NetworkAction.获取分类商品)) {
			page++;
			totalpage = response.getInt("totalpage");

			JSONArray productsArray = response.getJSONArray("list");
			// 判断是否还要加载数据
			if (page > totalpage)
				getMore.setVisibility(View.GONE);
			// 判断是否有商品数据
			if (productsArray.length() == 0)
				Toast.makeText(ProductShow.this,
						"该分类还没有商品" + productsArray.length(), 2000).show();
			else {
				for (int i = 0; i < productsArray.length(); i++) {

					JSONObject product = productsArray.getJSONObject(i);
					Product newProduct = new Product();
					newProduct.setId(product.getString("product_id"));
					newProduct.setName(product.getString("product_name"));
					newProduct.setStorePrice(product.getString("store_price"));
					newProduct.setImgPath(product.getString("product_photo"));
					products.add(newProduct);
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long parentId) {
		// Intent intent = new Intent().setClass(this, ProductListShow.class);
		// intent.putExtra("Category_id",
		// ((Category) listThird.get(position)).getCategory_id());
		// intent.putExtra("CacheID",
		// ((Category) listThird.get(position)).getCacheID());
		//
		// startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getMore:
			paramterCatagory.remove(paramterCatagory.get("nowpage"));
			paramterCatagory.put("nowpage", String.valueOf(page));
			ConnectServer.getResualt(this, paramterCatagory,
					NetworkAction.获取分类商品, Url.URL_INDEX);
			break;
		case R.id.show_sort:
			initSortPop(radioGroup);
			break;
		}

	}

	// 单选框的选中状态的更改事件
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			Drawable sortSelect = MyApplication.resources
					.getDrawable(R.drawable.sort_ico_select);
			sortSelect.setBounds(0, 0, sortSelect.getMinimumWidth(),
					sortSelect.getMinimumHeight());
			Drawable filterSelect = MyApplication.resources
					.getDrawable(R.drawable.filter_ico_select);
			filterSelect.setBounds(0, 0, filterSelect.getMinimumWidth(),
					filterSelect.getMinimumHeight());
			//综合排序被选中时的右边的对号的图片
			Drawable sortSelectBg = MyApplication.resources
					.getDrawable(R.drawable.sort_select_bg);
			sortSelectBg.setBounds(0, 0, sortSelectBg.getMinimumWidth(),
					sortSelectBg.getMinimumHeight());
			buttonView.setTextColor(MyApplication.resources
					.getColor(R.color.style_color));
			switch (buttonView.getId()) {
			case R.id.show_sort:
				buttonView.setCompoundDrawables(null, null, sortSelect, null); // 设置右图标
//				initSortPop(radioGroup);
				break;
			case R.id.show_salefirst:
				resetData(0);
				ConnectServer.getResualt(this, paramterCatagory,
						NetworkAction.获取分类商品, Url.URL_INDEX);
				break;
			case R.id.show_filter:
				buttonView.setCompoundDrawables(null, null, filterSelect, null); // 设置右图标
				break;

			case R.id.show_pop_sort:
				buttonView.setCompoundDrawables(null, null, sortSelectBg, null); // 设置右图标
				showSort.setText(buttonView.getText().toString());
				//添加综合排序的代码
				resetData(3);
				ConnectServer.getResualt(this, paramterCatagory,
						NetworkAction.获取分类商品, Url.URL_INDEX);
				sortPopWindow.dismiss();
				break;
			case R.id.show_pop_low:
				buttonView.setCompoundDrawables(null, null, sortSelectBg, null); // 设置右图标
				showSort.setText(buttonView.getText().toString());
				//添加价格从低到高的代码
				resetData(1);
				ConnectServer.getResualt(this, paramterCatagory,
						NetworkAction.获取分类商品, Url.URL_INDEX);
				sortPopWindow.dismiss();
				break;
			case R.id.show_pop_high:
				buttonView.setCompoundDrawables(null, null, sortSelectBg, null); // 设置右图标
				showSort.setText(buttonView.getText().toString());
				//添加价格从高到底的代码
				resetData(2);
				ConnectServer.getResualt(this, paramterCatagory,
						NetworkAction.获取分类商品, Url.URL_INDEX);
				sortPopWindow.dismiss();
				break;
			}
		} else {
			Drawable sortNoSelect = MyApplication.resources
					.getDrawable(R.drawable.sort_ico_noselect);
			sortNoSelect.setBounds(0, 0, sortNoSelect.getMinimumWidth(),
					sortNoSelect.getMinimumHeight());

			Drawable filterNoSelect = MyApplication.resources
					.getDrawable(R.drawable.filter_ico_noselect);
			filterNoSelect.setBounds(0, 0, filterNoSelect.getMinimumWidth(),
					filterNoSelect.getMinimumHeight());

			buttonView.setTextColor(MyApplication.resources
					.getColor(R.color.menu_noselect));
			switch (buttonView.getId()) {
			case R.id.show_sort:
				buttonView.setCompoundDrawables(null, null, sortNoSelect, null); // 设置右图标
				break;
			case R.id.show_filter:
				buttonView.setCompoundDrawables(null, null, filterNoSelect,
						null); // 设置右图标
				break;
			case R.id.show_pop_sort:
				buttonView.setCompoundDrawables(null, null, null, null); // 设置右图标
				break;
			case R.id.show_pop_low:
				buttonView.setCompoundDrawables(null, null, null, null); // 设置右图标
				break;
			case R.id.show_pop_high:
				buttonView.setCompoundDrawables(null, null, null, null); // 设置右图标
				break;
			}
		}

	}

	// 初始化综合排序弹出菜单
	private void initSortPop(View parent) {
		LayoutInflater layoutInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popuView = layoutInflater.inflate(R.layout.pop_sort, null);
		sortPopWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popSort=(RadioButton) popuView.findViewById(R.id.show_pop_sort);
		popLow=(RadioButton) popuView.findViewById(R.id.show_pop_low);
		popHigh=(RadioButton) popuView.findViewById(R.id.show_pop_high);
		popSort.setOnCheckedChangeListener(this);
		popLow.setOnCheckedChangeListener(this);
		popHigh.setOnCheckedChangeListener(this);
		if(showSort.getText().toString().equals(popSort.getText().toString()))
			popSort.setChecked(true);
		else if(showSort.getText().toString().equals(popLow.getText().toString()))
			popLow.setChecked(true);
		else if(showSort.getText().toString().equals(popHigh.getText().toString()))
			popHigh.setChecked(true);
		//加上下面两句代码点击其他地方该弹出框将消失
		ColorDrawable cd=new ColorDrawable(-0000);
		sortPopWindow.setBackgroundDrawable(cd);
		int height=title.getHeight()+radioGroup.getHeight();
		sortPopWindow.setFocusable(true);
		sortPopWindow.setOutsideTouchable(true);
		sortPopWindow.showAtLocation(parent, Gravity.TOP,10,height+50); 
		sortPopWindow.update();
	}
	
	//更新数据源的时候重置数据，根据排序方式而定
	public void resetData(int sortType)
	{
		products.clear();// 清空数据
		page = 1;// 重置页码
		getMore.setVisibility(View.VISIBLE);// 还原更多按钮的显示
		paramterCatagory.remove(paramterCatagory.get("nowpage"));// 重置页码参数
		paramterCatagory.put("nowpage", String.valueOf(page));
		paramterCatagory.remove(paramterCatagory.get("sort_type"));// 重置排序类型
		paramterCatagory.put("sort_type", String.valueOf(sortType));
	}
}
