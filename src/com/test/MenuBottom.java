package com.test;




import com.test.base.MyApplication;
import com.test.person.Person;
import com.test.product.CatagoryFirst;
import com.test.product.Home;
import com.test.product.ShopCart;
import com.test.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

public class MenuBottom extends TabActivity {
	/** Called when the activity is first created. */
	public static TabHost tabHost; // 底部菜单栏
	public static RadioGroup radioGroup;
	private Resources resources; //获取资源文件

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		resources = getResources();
		initData();
	}

	
	private void initData() {
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		// 首页菜单
		intent = new Intent().setClass(this, Home.class);
		spec = tabHost.newTabSpec(resources.getString(R.string.main_menu_home))
				.setIndicator(resources.getString(R.string.main_menu_home))
				.setContent(intent);
		tabHost.addTab(spec);
		// 活动菜单
		intent = new Intent().setClass(this, CatagoryFirst.class);
		spec = tabHost
				.newTabSpec(resources.getString(R.string.main_menu_search))
				.setIndicator(resources.getString(R.string.main_menu_search))
				.setContent(intent);
		tabHost.addTab(spec);

		// 列表菜单
		intent = new Intent().setClass(this, CatagoryFirst.class);
		spec = tabHost.newTabSpec(resources.getString(R.string.main_menu_list))
				.setIndicator(resources.getString(R.string.main_menu_list))
				.setContent(intent);
		tabHost.addTab(spec);

		// 购物车菜单
		intent = new Intent().setClass(this, ShopCart.class);
		spec = tabHost
				.newTabSpec(resources.getString(R.string.main_menu_shopcart))
				.setIndicator(resources.getString(R.string.main_menu_shopcart))
				.setContent(intent);
		tabHost.addTab(spec);

		// 个人中心菜单
		intent = new Intent().setClass(this, Person.class);
		spec = tabHost
				.newTabSpec(
						resources.getString(R.string.main_menu_personcenter))
				.setIndicator(
						resources.getString(R.string.main_menu_personcenter))
				.setContent(intent);
		tabHost.addTab(spec);

//		// 登录界面
//					intent = new Intent().setClass(this, PersonLogin.class);
//					spec = tabHost
//							.newTabSpec(resources.getString(R.string.person_login))
//							.setIndicator(resources.getString(R.string.person_login))
//							.setContent(intent);
//					tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		radioGroup = (RadioGroup) this
				.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.main_tab_home:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_home));
					break;
				case R.id.main_tab_search:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_search));
					break;
				case R.id.main_tab_list:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_list));
					break;
				case R.id.main_tab_shopcart:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_shopcart));
					break;
				case R.id.main_tab_personcenter:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_personcenter));
					break;
				default:
					break;
				}
			}
		});
	}


}