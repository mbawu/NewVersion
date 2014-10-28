package com.test.base;

import java.util.ArrayList;

import com.test.product.Home;
import com.test.product.SeckillProduct;
//import com.bdh.activity.product.ProductDetail;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;


//每秒刷新秒杀商品的倒计时时间
public class ChangeTime  implements Runnable{

	
	public static  ArrayList<Long> timeList;//首页的秒杀商品列表的文本框和时间集合
	public static ArrayList<TextView> txtViewList;
	public static  ArrayList<Long> sectimeList;//点击首页的更多进入到秒杀专区显示的新的文本框和时间集合
	public static ArrayList<TextView> sectxtViewList;
	public static boolean exit=true;
	public static  long secKillTime=-1;
	
	public ChangeTime()
	{
		timeList=new ArrayList<Long>();
		txtViewList=new ArrayList<TextView>();
		sectimeList=new ArrayList<Long>();
		sectxtViewList=new ArrayList<TextView>();
	}
	
	
	@Override
	public void run() {
		while(exit)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//秒杀商品详情页面倒计时文本框刷新
//			if(secKillTime!=-1)
//			{
//				Bundle bundle=new Bundle();
//				secKillTime--;
//				String day = String.valueOf(secKillTime / 60 / 60 / 24);
//				String hour = String.valueOf(secKillTime / 60 / 60 % 24);
//				String min = String.valueOf(secKillTime / 60 % 60);
//				String sec = String.valueOf(secKillTime % 60);
//				String timeString = day + "天" + hour + "时" + min + "分" + sec + "秒";
//				bundle.putLong("time", secKillTime);
//				bundle.putString("timeString", timeString);
//				if(ProductDetail.secKillHandler!=null)
//				{
//					Message msg=new Message();
//					msg.setData(bundle);
//					ProductDetail.secKillHandler.sendMessage(msg);
//				}
//			}
			
			//秒杀商品列表倒计时刷新
			if(timeList.size()>0&&!MyApplication.exit)
			{
//				Log.i(MyApplication.TAG, "timeList-->"+timeList.size());
//				Log.i(MyApplication.TAG, "txtViewList-->"+txtViewList.size());
				for (int i = 0; i < timeList.size(); i++) {
					long time=timeList.get(i);
					Bundle bundle=new Bundle();
					if(time>0)
					{
						time--;
						String day = String.valueOf(time / 60 / 60 / 24);
						String hour = String.valueOf(time / 60 / 60 % 24);
						String min = String.valueOf(time / 60 % 60);
						String sec = String.valueOf(time % 60);
						String timeString = day + "天" + hour + "时" + min + "分" + sec + "秒";
						bundle.putLong("time", time);
						bundle.putString("timeString", timeString);
						bundle.putInt("index", i);
						timeList.remove(i);
						timeList.add(i, time);
						if(Home.homeHandler!=null)
						{
							Message msg=new Message();
							msg.setData(bundle);
							Home.homeHandler.sendMessage(msg);
						}
					}
				}
			}
			
			//秒杀专区列表倒计时刷新
			if(sectimeList.size()>0&&!MyApplication.exit)
			{
//				Log.i(MyApplication.TAG, "sectimeList-->"+sectimeList.size());
//				Log.i(MyApplication.TAG, "txtViewList-->"+txtViewList.size());
				for (int i = 0; i < sectimeList.size(); i++) {
					long time=sectimeList.get(i);
					Bundle bundle=new Bundle();
					if(time>0)
					{
						time--;
						String day = String.valueOf(time / 60 / 60 / 24);
						String hour = String.valueOf(time / 60 / 60 % 24);
						String min = String.valueOf(time / 60 % 60);
						String sec = String.valueOf(time % 60);
						String timeString = day + "天" + hour + "时" + min + "分" + sec + "秒";
						bundle.putLong("time", time);
						bundle.putString("timeString", timeString);
						bundle.putInt("index", i);
						sectimeList.remove(i);
						sectimeList.add(i, time);
						if(SeckillProduct.secHandler!=null)
						{
							Message msg=new Message();
							msg.setData(bundle);
							SeckillProduct.secHandler.sendMessage(msg);
						}
					}
				}
			}
			
		}
		
	}
	
	
	
}
