package com.test.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.test.base.MyAdapter;
import com.test.base.MyApplication;
import com.test.base.NormalActivity;
import com.test.base.Title;
import com.test.base.Url;
import com.test.model.Comment;
import com.test.utils.ConnectServer;
import com.test.utils.ErrorMsg;
import com.test.utils.NetworkAction;
import com.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CommentList extends NormalActivity implements OnClickListener{

	private Title title;// 设置标题栏
	private ListView listView;
	private ArrayList<Object> data;
	private MyAdapter adapter;
	private String productID;
	HashMap<String, String> paramter;
	private int page = 1; // 当前页码
	private String pageSize = "10"; // 每页显示的数据条数
	private int totalPage = 0; // 总页码
	private LinearLayout getMore;
	private TextView numTxt;//评论总数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commentlist);
		initView();
		initData();
	}

	private void initView() {
		data = new ArrayList<Object>();
		title = (Title) findViewById(R.id.title);
		listView = (ListView) findViewById(R.id.comment_listview);
		adapter = new MyAdapter(this, NetworkAction.评论列表, data);
		listView.setAdapter(adapter);
		listView.setDivider(null);
//		MyApplication.setListViewHeight(listView);
		getMore= (LinearLayout) findViewById(R.id.getMore);
		getMore.setOnClickListener(this);
		numTxt=(TextView) findViewById(R.id.comment_num);
	}

	private void initData() {
		Intent intent = getIntent();
		productID = intent.getStringExtra("productID");
		title.setModule(7);
		title.setTitleTxt("商品评价");
		paramter=new HashMap<String, String>();
		paramter.put("act", "comments_list");
		paramter.put("sid", MyApplication.sid);
		paramter.put("ProductID", productID);
		paramter.put("level", "0");
		paramter.put("page", String.valueOf(page));
		paramter.put("pagesize", pageSize);
		ConnectServer.getResualt(this, paramter, NetworkAction.评论列表,
				Url.URL_ORDER);
	}

	public void sendData(final NetworkAction request) {
		MyApplication.progressShow(this, request.toString());
		String url = "";
		HashMap<String, String> paramter = new HashMap<String, String>();
		if (request.equals(NetworkAction.评论列表)) {
			url = Url.URL_ORDER;
			paramter.put("act", "comments_list");
			paramter.put("sid", MyApplication.sid);
			paramter.put("ProductID", productID);
			paramter.put("level", "0");
			paramter.put("page", "1");
			paramter.put("pagesize", "100000");
		}
		Log.i(MyApplication.TAG, request + MyApplication.getUrl(paramter, url));
		MyApplication.client.postWithURL(url, paramter,
				new Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						try {
							Log.i(MyApplication.TAG, request + "response-->"
									+ response.toString());
							int code = response.getInt("code");
							if (response.getInt("code") == 1) {
								data.clear();

								JSONArray commentArray = response
										.getJSONArray("list");
								if (commentArray.length() > 0) {
									for (int i = 0; i < commentArray.length(); i++) {
										JSONObject commentObject = commentArray
												.getJSONObject(i);
										Comment comment = new Comment();
										comment.setComment_id(commentObject
												.getString("comment_id"));
										comment.setProduct_id(commentObject
												.getString("product_id"));
										comment.setUid(commentObject
												.getString("uid"));
										comment.setUsername(commentObject
												.getString("username"));
										comment.setComment_subject(commentObject
												.getString("comment_subject"));
										comment.setComment_content(commentObject
												.getString("comment_content"));
										comment.setComment_star(commentObject
												.getString("comment_star"));
										comment.setComment_evaluation(commentObject
												.getString("comment_evaluation"));
										comment.setCreatetime(commentObject
												.getString("createtime"));
										comment.setOutDeadLine(commentObject
												.getString("OutDeadLine"));
										data.add(comment);
									}
								}
								adapter.notifyDataSetChanged();
							} else {
								Toast.makeText(
										CommentList.this,
										request
												+ ErrorMsg.getErrorMsg(request,
														code), 2000).show();
							}
						} catch (JSONException e) {
							Log.i(MyApplication.TAG, request
									+ "JSONException-->" + e.getMessage());
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(MyApplication.TAG, request + "onErrorResponse-->"
								+ error.getMessage());
					}
				});
	}

	@Override
	public void showResualt(JSONObject response, NetworkAction request)
			throws JSONException {
		page++;// 当前页加一
		totalPage = Integer.valueOf(response.getString("totalpage"));// 获取总页码

		// 判断是否还要加载数据
		if (page <= totalPage )
			getMore.setVisibility(View.VISIBLE);
		else
			getMore.setVisibility(View.GONE);
		numTxt.setText("（"+response.getString("totalRecord")+"）");
		JSONArray commentArray = response
				.getJSONArray("list");
		if (commentArray.length() > 0) {
			for (int i = 0; i < commentArray.length(); i++) {
				JSONObject commentObject = commentArray
						.getJSONObject(i);
				Comment comment = new Comment();
				comment.setComment_id(commentObject
						.getString("comment_id"));
				comment.setProduct_id(commentObject
						.getString("product_id"));
				comment.setUid(commentObject
						.getString("uid"));
				comment.setUsername(commentObject
						.getString("username"));
				comment.setComment_subject(commentObject
						.getString("comment_subject"));
				comment.setComment_content(commentObject
						.getString("comment_content"));
				comment.setComment_star(commentObject
						.getString("comment_star"));
				comment.setComment_evaluation(commentObject
						.getString("comment_evaluation"));
				comment.setCreatetime(commentObject
						.getString("createtime"));
				comment.setOutDeadLine(commentObject
						.getString("OutDeadLine"));
				data.add(comment);
			}
		}
		adapter.notifyDataSetChanged();
		MyApplication.setListViewHeight(listView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getMore:
			paramter.put("page", String.valueOf(page));
			Log.i(MyApplication.TAG, "page->"+page);
			Log.i(MyApplication.TAG, "nowpage->"+paramter.get("nowpage"));
			ConnectServer.getResualt(this, paramter, NetworkAction.评论列表,
					Url.URL_ORDER);
			break;

		}
		
	}
}
