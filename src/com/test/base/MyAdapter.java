package com.test.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
//import com.test.activity.person.Person;
//import com.test.activity.person.PersonAddress;
//import com.test.activity.person.PersonCoupon;
//import com.test.activity.person.PersonOrder;
//import com.test.activity.product.OrderEvaluate;
//import com.test.activity.product.ProductDetail;
//import com.test.activity.product.ProductList;
//import com.test.activity.product.ProductListShow;
//import com.test.activity.product.ShopCart;
import com.test.model.Address;
import com.test.model.Category;
import com.test.model.Comment;
import com.test.model.Coupon;
import com.test.model.MyMessage;
import com.test.utils.NetworkAction;
import com.test.model.Order;
import com.test.model.Product;
import com.test.R;
//import com.test.pay.PayMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter implements
		android.view.View.OnClickListener, OnCheckedChangeListener {

	private Object object;
	private ArrayList<Object> data;
	private NetworkAction request;
	private int orderTypeTemp;

	/**
	 * 
	 * @param object
	 *            需要显示的activity
	 * @param request
	 *            需要显示的数据类型
	 * @param data
	 *            需要显示的数据集合
	 */
	public MyAdapter(Object object, NetworkAction request,
			ArrayList<Object> data) {
		// MyApplication.printLog("MyAdapter-->classname", module);
		this.object = object;
		this.data = data;
		this.request = request;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 拿出每个product的ID赋值给每个item
		if (request.equals(NetworkAction.热门商品)
				|| request.equals(NetworkAction.获取分类商品)
				|| request.equals(NetworkAction.搜索商品)
				|| request.equals(NetworkAction.秒杀商品)) {
			Product product = (Product) data.get(position);
			long productId = Long.valueOf(product.getId());
			return productId;
		} else if (request.equals(NetworkAction.一级分类)) {
			Category category = (Category) data.get(position);
			long parentId = Long.valueOf(category.getCategory_id());
			return parentId;
		} else if (request.equals(NetworkAction.二级分类)) {
			Category category = (Category) data.get(position);
			long parentId = Long.valueOf(category.getCategory_id());
			return parentId;
		}

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (request.equals(NetworkAction.热门商品)
					|| request.equals(NetworkAction.秒杀商品))
				convertView = MyApplication.Inflater.inflate(
						R.layout.home_hot_item, null);
		}else if (convertView != null && request.equals(NetworkAction.秒杀商品)) {
			return convertView;
		}
		
		
		if (request.equals(NetworkAction.热门商品))
		{
			Product product = (Product) data.get(position);
			NetworkImageView img = (NetworkImageView) convertView
					.findViewById(R.id.home_hot_img);
			TextView nameTxt = (TextView) convertView
					.findViewById(R.id.home_hot_name);
			TextView priceTxt = (TextView) convertView
					.findViewById(R.id.home_hot_price);
			nameTxt.setText(product.getName());
			priceTxt.setText("￥ " + product.getStorePrice());
			// Log.i(MyApplication.TAG,"getImgPath-->"+ product.getImgPath());
			MyApplication.client.getImageForNetImageView(product.getImgPath(),
					img, R.drawable.ic_launcher);
		}else if (request.equals(NetworkAction.秒杀商品)) {
			Product product = (Product) data.get(position);
			NetworkImageView img = (NetworkImageView) convertView
					.findViewById(R.id.home_hot_img);
			TextView nameTxt = (TextView) convertView
					.findViewById(R.id.home_hot_name);
			TextView priceTxt = (TextView) convertView
					.findViewById(R.id.home_hot_price);
			LinearLayout timeLayout= (LinearLayout) convertView
					.findViewById(R.id.home_seckill_timelayout);
			final TextView outTimeTxt = (TextView) convertView
					.findViewById(R.id.home_seckill_outtime);
			timeLayout.setVisibility(View.VISIBLE);

			long outTime = Integer.valueOf(product.getOutEndTime());
			long time = Integer.valueOf(product.getTime());
			long endTime = outTime - time;
			String day = String.valueOf(endTime / 60 / 60 / 24);
			String hour = String.valueOf(endTime / 60 / 60 % 24);
			String min = String.valueOf(endTime / 60 % 60);
			String sec = String.valueOf(endTime % 60);
			String timeString = day + "天" + hour + "时" + min + "分" + sec + "秒";
			outTimeTxt.setText(timeString);
			outTimeTxt.setTag(convertView);
			ChangeTime.timeList.add(endTime);
			ChangeTime.txtViewList.add(outTimeTxt);
			nameTxt.setText(product.getName());
			priceTxt.setText("￥ " + product.getSKPrice());
			Log.i(MyApplication.TAG, "getView-->adapter");
			MyApplication.client.getImageForNetImageView(product.getImgPath(),
					img, R.drawable.ic_launcher);
		} 
	return convertView;
	}
//			 if(module.equals("home_hot"))
//			 convertView=MyApplication.Inflater.inflate(R.layout.home_hot_item,
//			 null);
//			if (request.equals(NetworkAction.获取收货地址列表))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.person_address_item, null);
//			else if (request.equals(NetworkAction.热门商品)
//					|| request.equals(NetworkAction.获取分类商品)
//					|| request.equals(NetworkAction.搜索商品)
//					|| request.equals(NetworkAction.秒杀商品))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.home_hot_item, null);
//			else if (request.equals(NetworkAction.我的消息))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.person_more_message_item, null);
//			else if (request.equals(NetworkAction.一级分类))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.productlist_first_item, null);
//			else if (request.equals(NetworkAction.二级分类))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.productlist_second_item, null);
//			else if (request.equals(NetworkAction.三级分类))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.productlist_third_item, null);
//			else if (request.equals(NetworkAction.提交订单)
//					|| request.equals(NetworkAction.订单详情))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.submit_product_item, null);
//			else if (request.equals(NetworkAction.购物车))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.shopcart_item, null);
//			else if (request.equals(NetworkAction.我的优惠券))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.coupon_item, null);
//			else if (request.equals(NetworkAction.评论列表))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.comment_item, null);
//			else if (request.equals(NetworkAction.订单列表))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.person_order_item, null);
//			else if (request.equals(NetworkAction.评论订单))
//				convertView = MyApplication.Inflater.inflate(
//						R.layout.orderevaluate_item, null);
//
//		}
//		// 如果是秒杀商品并且已经有view了则不再重绘直接返回当前view，为了不让倒计时线程重复添加view
//		else if (convertView != null && request.equals(NetworkAction.秒杀商品)) {
//			return convertView;
//		}
//
//		if (request.equals(NetworkAction.获取收货地址列表))// 设置收货地址item界面
//		{
//			TextView name = (TextView) convertView
//					.findViewById(R.id.person_address_item_name);
//			TextView phone = (TextView) convertView
//					.findViewById(R.id.person_address_item_phone);
//			TextView street = (TextView) convertView
//					.findViewById(R.id.person_address_item_street);
//			TextView edite = (TextView) convertView
//					.findViewById(R.id.person_address_item_edite);
//			edite.setOnClickListener(this);
//			edite.setTag(data.get(position));
//			TextView delete = (TextView) convertView
//					.findViewById(R.id.person_address_item_delete);
//			delete.setOnClickListener(this);
//			Address address = (Address) data.get(position);
//			name.setText(address.getRealname());
//			phone.setText("(" + address.getMobile() + ")");
//			street.setText(address.getStreet());
//			delete.setTag(address.getAddressID());
//
//			// 判断是否为默认地址并做相应的调整
//			FrameLayout defaultLayout = (FrameLayout) convertView
//					.findViewById(R.id.person_address_item_default_layout);// 设为默认地址的容器
//			defaultLayout.setTag(address);
//			defaultLayout.setOnClickListener(this);// 注册设为默认地址的点击事件
//
//			ImageView defaultImg = (ImageView) convertView
//					.findViewById(R.id.person_address_item_default_img);// 设为默认地址的图片
//			TextView defaultTxt = (TextView) convertView
//					.findViewById(R.id.person_address_item_default_txt);// 设为默认地址的文字
//			if (address.getTag().equals("1"))// 如果是默认地址的情况
//			{
//				defaultImg.setVisibility(View.VISIBLE);// 显示图标
//				defaultTxt.setTextColor(MyApplication.resources
//						.getColor(R.color.gray));
//				defaultTxt.setText("默认地址");
//				defaultLayout.setOnClickListener(null);
//			} else// 如果不是默认地址的情况
//			{
//				defaultImg.setVisibility(View.GONE);// 隐藏图标
//				defaultTxt.setTextColor(MyApplication.resources
//						.getColor(R.color.blue));
//				defaultTxt.setText("设为默认地址");
//			}
//
//		} else if (request.equals(NetworkAction.热门商品)
//				|| request.equals(NetworkAction.获取分类商品)
//				|| request.equals(NetworkAction.搜索商品)) {
//			Product product = (Product) data.get(position);
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.home_hot_img);
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.home_hot_name);
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.home_hot_price);
//			nameTxt.setText(product.getName());
//			priceTxt.setText("￥ " + product.getStorePrice());
//			// Log.i(MyApplication.TAG,"getImgPath-->"+ product.getImgPath());
//			MyApplication.client.getImageForNetImageView(product.getImgPath(),
//					img, R.drawable.ic_launcher);
//		} else if (request.equals(NetworkAction.我的消息)) {
//			TextView subjectTxt = (TextView) convertView
//					.findViewById(R.id.person_msg_subject);
//			TextView creatTimeTxt = (TextView) convertView
//					.findViewById(R.id.person_msg_creattime);
//			TextView contentTxt = (TextView) convertView
//					.findViewById(R.id.person_msg_content);
//			CheckBox deleteBox = (CheckBox) convertView
//					.findViewById(R.id.message_delete);
//
//			MyMessage msg = (MyMessage) data.get(position);
//			subjectTxt.setText(msg.getSubject());
//			creatTimeTxt.setText(msg.getCreatTime());
//			contentTxt.setText(msg.getContent());
//			deleteBox.setTag(msg);
//			deleteBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView,
//						boolean isChecked) {
//					MyMessage msg = (MyMessage) buttonView.getTag();
//					msg.setChecked(isChecked);
//				}
//			});
//			if (msg.isShowCheckBox()) {
//				deleteBox.setVisibility(View.VISIBLE);
//				if (msg.isChecked())
//					deleteBox.setChecked(true);
//				else
//					deleteBox.setChecked(false);
//			} else
//				deleteBox.setVisibility(View.GONE);
//		} else if (request.equals(NetworkAction.一级分类)) {
//			Category category = (Category) data.get(position);
//			TextView firstTxt = (TextView) convertView
//					.findViewById(R.id.productlist_firsttxt);
//			firstTxt.setText(category.getCategory_name());
//		} else if (request.equals(NetworkAction.二级分类)) {
//			Category category = (Category) data.get(position);
//			TextView secTxt = (TextView) convertView
//					.findViewById(R.id.productlist_sectxt);// 二级分类文本框
//			secTxt.setText(category.getCategory_name());
//
//			// 点击二级分类事件
//			RelativeLayout secondItem = (RelativeLayout) convertView
//					.findViewById(R.id.productlist_second_itemlayout);
//			secondItem.setOnClickListener(this);
//			// 找到三级分类列表
//			ListView thirdListView = (ListView) convertView
//					.findViewById(R.id.productlist_third_listview);// 3级列表listview
//			// 获取所有的三级分类集合
//			ArrayList<Object> thridList = ((ProductList) object).thirdLevel;
//			// 临时存放该二级分类ID所对应的三级分类的集合
//			ArrayList<Object> temp = new ArrayList<Object>();
//			for (int i = 0; i < thridList.size(); i++) {
//				Category thirdCategory = (Category) thridList.get(i);
//				if (thirdCategory.getParent_catid().equals(
//						category.getCategory_id()))
//					temp.add(thirdCategory);
//			}
//			//如果没有三级分类更改图片
//			if(temp.size()==0)
//			{
//				ImageView secondImg=(ImageView) convertView
//						.findViewById(R.id.productlist_second_img);
//				secondImg.setBackgroundDrawable(MyApplication.resources
//						.getDrawable(R.drawable.first_img));
//			}
//			// 把获取到的该二级分类下的三级分类装载到适配器里面
//			MyAdapter adapter = new MyAdapter(object, NetworkAction.三级分类, temp);
//			thirdListView.setAdapter(adapter);
//			// 设置列表高度，全部显示三级分类，不要滚动条
//			setListViewHeight(thirdListView);
//			secondItem.setTag(thirdListView);
//			secondItem.setTag(R.id.tag_first, temp);
//			secondItem.setTag(R.id.tag_second, category);
//		} else if (request.equals(NetworkAction.三级分类)) {
//			Category category = (Category) data.get(position);
//			TextView thirdTxt = (TextView) convertView
//					.findViewById(R.id.productlist_thirdtxt);
//			thirdTxt.setText(category.getCategory_name());
//			// 三级分类条目
//			RelativeLayout thirdLayout = (RelativeLayout) convertView
//					.findViewById(R.id.productlist_third_itemlayout);
//			thirdLayout.setOnClickListener(this);
//			thirdLayout.setTag(category);
//		} else if (request.equals(NetworkAction.秒杀商品)) {
//			Product product = (Product) data.get(position);
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.home_hot_img);
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.home_hot_name);
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.home_hot_price);
//			final TextView outTimeTxt = (TextView) convertView
//					.findViewById(R.id.home_seckill_outtime);
//			outTimeTxt.setVisibility(View.VISIBLE);
//
//			long outTime = Integer.valueOf(product.getOutEndTime());
//			long time = Integer.valueOf(product.getTime());
//			long endTime = outTime - time;
//			String day = String.valueOf(endTime / 60 / 60 / 24);
//			String hour = String.valueOf(endTime / 60 / 60 % 24);
//			String min = String.valueOf(endTime / 60 % 60);
//			String sec = String.valueOf(endTime % 60);
//			String timeString = day + "天" + hour + "时" + min + "分" + sec + "秒";
//			outTimeTxt.setText(timeString);
//			outTimeTxt.setTag(convertView);
//			ChangeTime.timeList.add(endTime);
//			ChangeTime.txtViewList.add(outTimeTxt);
//			nameTxt.setText(product.getName());
//			priceTxt.setText("￥ " + product.getSKPrice());
//			Log.i(MyApplication.TAG, "getView-->adapter");
//			MyApplication.client.getImageForNetImageView(product.getImgPath(),
//					img, R.drawable.ic_launcher);
//		} else if (request.equals(NetworkAction.提交订单)
//				|| request.equals(NetworkAction.订单详情)) {
//			Product product = (Product) data.get(position);
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.submit_product_photo);
//
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.submit_product_name);
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.submit_product_price);
//			TextView numTxt = (TextView) convertView
//					.findViewById(R.id.submit_product_num);
//			// 先判断是秒杀商品还是正常商品，根据不同的商品显示不同的信息
//			// 购买类型：1正常购买，2秒杀
//			if (product.getBuy_type().equals("1")) {
//				nameTxt.setText(product.getName());
//				priceTxt.setText("￥" + product.getStorePrice());
//			} else {
//				nameTxt.setText(product.getSKName());
//				priceTxt.setText("￥" + product.getSKPrice());
//			}
//			numTxt.setText(product.getNum());
//			MyApplication.client.getImageForNetImageView(
//					product.getImgs().get(0), img, R.drawable.ic_launcher);
//		} else if (request.equals(NetworkAction.购物车)) {
//			Product product = (Product) data.get(position);
//			// setListViewHeight(((ShopCart)object).listView);
//			// Toast.makeText((Context) object, product.getName(), 2000).show();
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.shopcart_product_photo);
//			MyApplication.client.getImageForNetImageView(
//					product.getImgs().get(0), img, R.drawable.ic_launcher);
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.shopcart_product_name);
//			if (product.getBuy_type().equals("2"))
//				nameTxt.setText(product.getSKName());
//			else
//				nameTxt.setText(product.getName());
//			TextView priceNameTxt = (TextView) convertView
//					.findViewById(R.id.shopcart_product_pricename);
//			if (product.getBuy_type().equals("2"))
//				priceNameTxt.setText("秒杀价：");
//			else
//				priceNameTxt.setText("促销价：");
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.shopcart_product_price);
//			priceTxt.setText(product.getStorePrice());
//			TextView numTxt = (TextView) convertView
//					.findViewById(R.id.shopcart_num_txt);
//			numTxt.setText(product.getNum());
//			numTxt.setTag(product);
//			ImageView subImg = (ImageView) convertView
//					.findViewById(R.id.shopcart_num_sub);
//			ImageView addImg = (ImageView) convertView
//					.findViewById(R.id.shopcart_num_add);
//			subImg.setTag(numTxt);
//			addImg.setTag(numTxt);
//			subImg.setOnClickListener(this);
//			addImg.setOnClickListener(this);
//			CheckBox checkBox = (CheckBox) convertView
//					.findViewById(R.id.shopcart_delete);
//			checkBox.setTag(product);
//			checkBox.setChecked(product.isChecked());
//			checkBox.setOnCheckedChangeListener(this);
//		} else if (request.equals(NetworkAction.我的优惠券)) {
//			Coupon coupon = (Coupon) data.get(position);
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.coupon_item_price);
//			String price = coupon.getPrice();
//			int end = price.indexOf(".");
//			price = price.substring(0, end);
//			priceTxt.setText(price + "元优惠券");
//			if ((position + 1) % 3 == 1)
//				priceTxt.setBackgroundDrawable(MyApplication.resources
//						.getDrawable(R.drawable.coupon_bg_blue));
//			else if ((position + 1) % 3 == 2)
//				priceTxt.setBackgroundDrawable(MyApplication.resources
//						.getDrawable(R.drawable.coupon_bg_green));
//			else if ((position + 1) % 3 == 0)
//				priceTxt.setBackgroundDrawable(MyApplication.resources
//						.getDrawable(R.drawable.coupon_bg_yellow));
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.coupon_item_name);
//			nameTxt.setText(coupon.getProductName());
//			TextView priceLineTxt = (TextView) convertView
//					.findViewById(R.id.coupon_item_priceline);
//			priceLineTxt.setText("订单满￥" + coupon.getPriceLine() + "(不含运费)");
//			TextView dateTxt = (TextView) convertView
//					.findViewById(R.id.coupon_item_date);
//			dateTxt.setText("有效期：" + coupon.getEnd_time());
//
//			TextView useBtn = (TextView) convertView
//					.findViewById(R.id.coupon_item_use);
//			useBtn.setTag(coupon);
//			if (coupon.getValidity().equals("1")) {
//				useBtn.setText("立即使用");
//				useBtn.setOnClickListener(this);
//			} else if (coupon.getValidity().equals("2")) {
//				useBtn.setText("已过期");
//				useBtn.setBackgroundColor(MyApplication.resources
//						.getColor(R.color.darkgray));
//			}
//		} else if (request.equals(NetworkAction.评论列表)) {
//			Comment comment = (Comment) data.get(position);
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.comment_name);
//			RatingBar star = (RatingBar) convertView
//					.findViewById(R.id.comment_stars);
//			TextView contentTxt = (TextView) convertView
//					.findViewById(R.id.comment_content);
//			TextView dateTxt = (TextView) convertView
//					.findViewById(R.id.comment_date);
//			nameTxt.setText(comment.getUsername());
//			star.setRating(Float.valueOf(comment.getComment_star()));
//			contentTxt.setText(comment.getComment_content());
//			dateTxt.setText("发布时间 " + comment.getCreatetime());
//		} else if (request.equals(NetworkAction.订单列表)) {
//			Order order = (Order) data.get(position);
//			TextView orderCodeTxt = (TextView) convertView
//					.findViewById(R.id.order_code);
//			TextView orderDateTxt = (TextView) convertView
//					.findViewById(R.id.order_date);
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.order_photo);
//			TextView orderSubjectTxt = (TextView) convertView
//					.findViewById(R.id.order_subject);
//			TextView orderNumTxt = (TextView) convertView
//					.findViewById(R.id.order_num);
//			TextView orderTotalpriceTxt = (TextView) convertView
//					.findViewById(R.id.order_totalprice);
//			// //查看订单按钮
//			// TextView orderViewTxt= (TextView) convertView
//			// .findViewById(R.id.order_view);
//			// //取消订单按钮
//			// TextView orderCancelTxt= (TextView) convertView
//			// .findViewById(R.id.order_cancel);
//
//			// 左边的按钮
//			TextView leftBtn = (TextView) convertView
//					.findViewById(R.id.order_left_btn);
//			// 右边的按钮
//			TextView rightBtn = (TextView) convertView
//					.findViewById(R.id.order_right_btn);
//
//			// 按钮模块
//			FrameLayout btnLayout = (FrameLayout) convertView
//					.findViewById(R.id.order_btn_layout);
//			// 订单号
//			orderCodeTxt.setText("订单号：" + order.getOrderCode());
//			// 下单时间
//			orderDateTxt.setText("创建时间：" + order.getOutCreateTime());
//			// 显示第一个产品的第一张图片
//			ArrayList<Object> products = order.getProducts();
//			MyApplication.client.getImageForNetImageView(
//					((Product) products.get(0)).getImgs().get(0), img,
//					R.drawable.ic_launcher);
//			orderSubjectTxt.setText(order.getOrderSubject());
//			// 商品数量
//			orderNumTxt.setText(order.getTotalRecord());
//			// 订单金额
//			orderTotalpriceTxt.setText("￥" + order.getTotalPrice());
//			// leftBtn.setTag(R.id.tag_first, convertView);
//			leftBtn.setTag(order.getOrderID());
//			// rightBtn.setTag(order.getOrderID());
//			rightBtn.setTag(order);
//			// 要查看的订单类型：1.待付款，2.待发货，3.待收货，4.已完成
//			int orderType = Integer.valueOf(order.getOrderType());
//			orderTypeTemp = orderType;
//			int orderComment = Integer.valueOf(order.getComments());
//			// leftBtn.setTag(R.id.tag_three, orderType);
//			leftBtn.setOnClickListener(this);
//			rightBtn.setOnClickListener(this);
//			switch (orderType) {
//			case 1:
//				if (order.getIsPay().equals("0")
//						&& Integer.valueOf(order.getFlag()) < 3) {
//					btnLayout.setVisibility(View.VISIBLE);
//					leftBtn.setVisibility(View.VISIBLE);
//					leftBtn.setText("取消订单");
//					rightBtn.setVisibility(View.VISIBLE);
//					rightBtn.setText("  付     款  ");
//				} else {
//					btnLayout.setVisibility(View.GONE);
//					leftBtn.setVisibility(View.GONE);
//					rightBtn.setVisibility(View.GONE);
//				}
//
//				break;
//			case 2:
//				if (order.getIsPay().equals("0")
//						&& Integer.valueOf(order.getFlag()) < 3) {
//					btnLayout.setVisibility(View.VISIBLE);
//					leftBtn.setVisibility(View.VISIBLE);
//					leftBtn.setText("取消订单");
//				} else {
//					btnLayout.setVisibility(View.GONE);
//					leftBtn.setVisibility(View.GONE);
//
//				}
//				rightBtn.setVisibility(View.GONE);
//				break;
//			case 3:
//				btnLayout.setVisibility(View.VISIBLE);
//				leftBtn.setVisibility(View.GONE);
//				rightBtn.setVisibility(View.VISIBLE);
//				rightBtn.setText("确认收货 ");
//				break;
//			case 4:
//				btnLayout.setVisibility(View.VISIBLE);
//				leftBtn.setVisibility(View.GONE);
//				rightBtn.setVisibility(View.VISIBLE);
//				// 评论状态 1已评论，0未评论
//				if (orderComment == 0)
//					rightBtn.setText("  评     价  ");
//				else
//					rightBtn.setText("  晒     单  ");
//				break;
//			}
//
//		} else if (request.equals(NetworkAction.评论订单)) {
//			Product product = (Product) data.get(position);
//			NetworkImageView img = (NetworkImageView) convertView
//					.findViewById(R.id.order_evaluate_photo);
//
//			TextView nameTxt = (TextView) convertView
//					.findViewById(R.id.order_evaluate_name);
//			TextView priceTxt = (TextView) convertView
//					.findViewById(R.id.order_evaluate_price);
//
//			final RatingBar stars = (RatingBar) convertView
//					.findViewById(R.id.starBtn);
//
//			Button addComment = (Button) convertView
//					.findViewById(R.id.addComment);
//			final EditText comments = (EditText) convertView
//					.findViewById(R.id.addContent);
//			stars.setTag(product);
//
//			addComment.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					if (comments.getText().toString().equals("")) {
//						Toast.makeText((Context) object, "请填写评论！", 2000).show();
//						return;
//					}
//					stars.setTag(R.id.tag_first, comments.getText().toString());
//					MyApplication.comment = false;
//					((OrderEvaluate) object).publishComment(stars);
//				}
//			});
//			// 先判断是秒杀商品还是正常商品，根据不同的商品显示不同的信息
//			// 购买类型：1正常购买，2秒杀
//			if (product.getBuy_type().equals("1")) {
//				nameTxt.setText(product.getName());
//				priceTxt.setText("￥" + product.getStorePrice());
//			} else {
//				nameTxt.setText(product.getSKName());
//				priceTxt.setText("￥" + product.getSKPrice());
//			}
//			MyApplication.client.getImageForNetImageView(
//					product.getImgs().get(0), img, R.drawable.ic_launcher);


	@Override
	public void onClick(View v) {
		// switch (v.getId()) {
		// case R.id.person_address_item_edite:// 收货列表编辑按钮
		// Address address = (Address) v.getTag();
		// ((PersonAddress) object).editAddress(address);
		// break;
		// case R.id.person_address_item_delete:// 收货列表删除按钮
		// String addressID = (String) v.getTag();
		// Log.i(MyApplication.TAG, "addressID-->" + addressID);
		// ((PersonAddress) object).deleteAddress(addressID);
		// break;
		// case R.id.person_address_item_default_layout:// 点击设为默认地址的事件
		// ((PersonAddress) object).defaultAddress((Address) v.getTag());
		// break;
		//
		// case R.id.productlist_second_itemlayout:// 二级列表分类点击事件
		// ListView thirdListView = (ListView) v.getTag();
		// ArrayList<Object> temp = (ArrayList<Object>) v
		// .getTag(R.id.tag_first);
		// ImageView img = (ImageView) v
		// .findViewById(R.id.productlist_second_img);
		// //如果二级分类下有三级分类的情况
		// if (temp.size() > 0) {
		// // 二级分类列表底下的三级分类显示的时候
		// if (thirdListView.getVisibility() == View.VISIBLE) {
		// img.setBackgroundDrawable(MyApplication.resources
		// .getDrawable(R.drawable.productlist_second_close));
		// thirdListView.setVisibility(View.GONE);
		// } else// 二级分类列表底下的三级分类没有显示的时候
		// {
		// img.setBackgroundDrawable(MyApplication.resources
		// .getDrawable(R.drawable.productlist_second_open));
		// thirdListView.setVisibility(View.VISIBLE);
		// }
		// }
		// //如果没有三级分类的情况
		// else
		// {
		// img.setBackgroundDrawable(MyApplication.resources
		// .getDrawable(R.drawable.first_img));
		// thirdListView.setVisibility(View.GONE);
		// // 获取该分类的信息
		// Category category = (Category) v.getTag(R.id.tag_second);
		// // 从商品分类页面跳转到商品展示页面
		// Intent intent = new Intent().setClass((Context) object,
		// ProductListShow.class);
		// intent.putExtra("Category_id", category.getCategory_id());
		// intent.putExtra("CacheID", category.getCacheID());
		// ((ProductList) object).startActivity(intent);
		// }
		// break;
		// case R.id.productlist_third_itemlayout:// 点击三级分类事件
		// // 获取该分类的信息
		// Category category = (Category) v.getTag();
		// // 从商品分类页面跳转到商品展示页面
		// Intent intent = new Intent().setClass((Context) object,
		// ProductListShow.class);
		// intent.putExtra("Category_id", category.getCategory_id());
		// intent.putExtra("CacheID", category.getCacheID());
		// ((ProductList) object).startActivity(intent);
		// // Toast.makeText((Context) object, category.getCacheID(), 2000)
		// // .show();
		// break;
		// case R.id.shopcart_num_sub:// 购物车的减少按钮
		// changeShopCartNum(v, "sub");
		// break;
		// case R.id.shopcart_num_add:// 购物车的增加按钮
		// changeShopCartNum(v, "add");
		// break;
		// case R.id.coupon_item_use:// 优惠券的使用按钮
		// Coupon coupon = (Coupon) v.getTag();
		// Intent product_detail_intent = new Intent();
		// product_detail_intent.setClass((Context) object,
		// ProductDetail.class);
		// product_detail_intent.putExtra("productId", coupon.getProductID());
		// ((Person) object).startActivity(product_detail_intent);
		// break;
		// case R.id.order_left_btn:// 订单列表左边按钮
		// String orderID = (String) v.getTag();
		// ((PersonOrder) object).cancelOrder(orderID);
		// break;
		// case R.id.order_right_btn:// 订单列表右边按钮
		// Order order = (Order) v.getTag();
		// switch (orderTypeTemp) {
		// // 如果是待付款状态该按钮为付款的功能
		// case 1:
		// Intent intentPay = new Intent();
		// intentPay.setClass(((PersonOrder) object).getActivity(),
		// PayMethod.class);
		// intentPay.putExtra("subject", order.getOrderSubject());
		// intentPay.putExtra("price", order.getTotalPrice());
		// intentPay.putExtra("oid", order.getOrderID());
		// ((PersonOrder) object).startActivity(intentPay);
		// break;
		// // 如果是待收货状态该按钮为确认收货的功能
		// case 3:
		// ((PersonOrder) object).confirmReceive(order.getOrderID());
		// break;
		// // 如果是已完成状态该按钮为评价订单的功能
		// case 4:
		// // 如果是未评价的状态才执行评价操作
		// if (order.getComments().equals("0")) {
		// Intent intent2 = new Intent();
		// intent2.setClass(((PersonOrder) object).getActivity(),
		// OrderEvaluate.class);
		// intent2.putExtra("order", order);
		// ((PersonOrder) object).startActivity(intent2);
		// }
		// break;
		// }
		//
		// break;
		// }

	}

	// 修改购物车某个商品的数量，更新全局购物车集合并刷新显示
	public void changeShopCartNum(View v, String operation) {
		// TextView numTxt = (TextView) v.getTag();
		// Product product = (Product) numTxt.getTag();
		// int num = Integer.valueOf(numTxt.getText().toString());
		// if (operation.equals("sub")) {
		// // 如果数量为1则不能够再减少该商品
		// if (num == 1)
		// return;
		// else
		// num--;
		// } else
		// num++;
		// numTxt.setText(String.valueOf(num));
		// product.setNum(String.valueOf(num));
		// ((ShopCart) object).recalculatePrice();
	}

	/**
	 * 获取并设置ListView内容的高度
	 * 
	 * @param lv
	 */
	private void setListViewHeight(ListView lv) {
		ListAdapter la = lv.getAdapter();
		if (null == la) {
			return;
		}
		// calculate height of all items.
		int h = 0;
		final int cnt = la.getCount();
		for (int i = 0; i < cnt; i++) {
			View item = la.getView(i, null, lv);
			item.measure(0, 0);
			h += item.getMeasuredHeight();
		}
		// reset ListView height
		ViewGroup.LayoutParams lp = lv.getLayoutParams();
		lp.height = h + (lv.getDividerHeight() * (cnt - 1));
		lv.setLayoutParams(lp);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Product product = (Product) buttonView.getTag();
		Log.i(MyApplication.TAG, "product name-->" + product.getName());
		product.setChecked(isChecked);
		Log.i(MyApplication.TAG, "product checked-->" + product.isChecked());
	}

}
