package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.test.R;
import com.test.base.ChangeTime;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.base.Url;
import com.test.model.Product;
import com.test.utils.ConnectServer;
import com.test.utils.NetworkAction;

public class SeckillProduct extends NormalActivity {

	private Title title;// 设置标题栏
	private GridView gridView;
	private ArrayList<Object> secKillProduct; // 数据集合
	private MyAdapter adapterSecKill; // 秒杀商品适配器
	HashMap<String, String> paramterSeckill;
	public static Handler secHandler;
	private int newHeight = 0;
	private boolean load;
	private int page = 1; // 当前页码
	private String pageSize = "10"; // 每页显示的数据条数
	private int totalPage = 0; // 总页码
	public ChangeTime changeTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seckill);
		initView();
		initData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ChangeTime.sectimeList.clear();
		ChangeTime.sectxtViewList.clear();
	}
	private void initView() {
		title = (Title) findViewById(R.id.title);
		gridView = (GridView) findViewById(R.id.seckill_gridview);
		secKillProduct = new ArrayList<Object>();
		adapterSecKill = new MyAdapter(this, NetworkAction.秒杀商品, secKillProduct);
		paramterSeckill = new HashMap<String, String>();
		gridView.setAdapter(adapterSecKill);
	}

	private void initData() {
		title.setModule(2);
		title.setTitleTxt("限时秒杀");
		// 秒杀商品
		paramterSeckill.put("act", "list");
		paramterSeckill.put("sid", MyApplication.sid);
		paramterSeckill.put("nowpage", String.valueOf(page));
		paramterSeckill.put("pagesize", pageSize);
		ConnectServer.getResualt(this, paramterSeckill, NetworkAction.秒杀商品,
				Url.URL_SECKILL);
		// 每秒刷新秒杀商品时间的handler
				secHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						Bundle bundle = msg.getData();
						// 取出最新的时间
						long time = bundle.getLong("time");
						// 取出索引，根据该索引查询对应的时间和textview
						int index = bundle.getInt("index");
//						Log.i("test", ""+index);
						// 从子线程返回的计算好了的新的时间字符串
						String timeString = bundle.getString("timeString");
						TextView txt = (TextView) ((View) ChangeTime.sectxtViewList.get(
								index).getTag())
								.findViewById(R.id.home_seckill_outtime);
						// 如果秒杀还没有结束的话，刷新最新的时间，否则重新获取一遍秒杀商品
						// if(time>0)
						// ChangeTime.txtViewList.get(index).setText(timeString);
						if (time > 0)
							txt.setText(timeString);
						else {
							ChangeTime.sectxtViewList.clear();
							ChangeTime.sectimeList.clear();
							secKillProduct.clear();
							ConnectServer.getResualt(SeckillProduct.this, paramterSeckill,
									NetworkAction.秒杀商品, Url.URL_SECKILL);
						}
					}
				};
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		page++;// 当前页加一
		totalPage = Integer.valueOf(response.getString("totalpage"));// 获取总页码

		// 判断是否还要加载数据
		if (page > totalPage)
			load = false;
		else
			load = true;
		String time = response.getString("time");
		JSONArray lists = response.getJSONArray("list");// 获取数据集
		for (int i = 0; i < lists.length(); i++) {
			JSONObject product = lists.getJSONObject(i);
			Product newProduct = new Product();
			newProduct.setSkID(product.getString("SKID"));
			newProduct.setId(product.getString("ProductID"));
			newProduct.setName(product.getString("SKName"));
			newProduct.setSKPrice(product.getString("SKPrice"));
			newProduct.setOutEndTime(product.getString("OutEndTime"));
			newProduct.setImgPath(product.getString("Images"));
			newProduct.setTime(time);
			secKillProduct.add(newProduct);// 新获取到的数据添加到数据集合中
		}
		adapterSecKill.notifyDataSetChanged();// 通知适配器数据发生变化了

	}

}
