package com.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;


import com.test.model.Product;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ShopCartManager {
	private File file = new File(getPath() + File.separator + "shopcart.txt");
	private Context context;

	public ShopCartManager(Context context) {
		this.context = context;
	}

	public void saveProducts(ArrayList<Object> products) throws IOException {
		checkFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream);
		objectOutputStream.writeObject(products);
		objectOutputStream.close();
	}

	public void dele() {
		file.delete();
	}

	public void add(Product product) {
		if (MyApplication.shopCartList.size() > 19) {
			Toast.makeText(context, "购物车数量不能超过20条哦!", 2000).show();
		} else {
			if (MyApplication.shopCartList.size() == 0) {
				MyApplication.shopCartList.add(product);
			} else {
				for (int i = 0; i < MyApplication.shopCartList.size(); i++) {
					Product oldProduct = (Product)MyApplication.shopCartList.get(i);
					if (product.getId().equals(oldProduct.getId())) {
						int oldNum=Integer.valueOf(oldProduct.getNum() );
						int newNum=Integer.valueOf(product.getNum() );
						oldProduct.setNum(String.valueOf(oldNum+newNum));
						return;
					}
				}
				MyApplication.shopCartList.add(product);
			}
		}

	}

	public void refresh() {
		for (int i = 0, length = MyApplication.shopCartList.size(); i < length; i++) {
			if (((Product)MyApplication.shopCartList.get(i)).isChecked()) {
				MyApplication.shopCartList.remove(MyApplication.shopCartList
						.get(i));
				i--;
				length--;
			}
		}

	}

	public void checkFile() throws IOException
	{
		if (!file.exists()) {
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					fileOutputStream);
			objectOutputStream.writeObject(new ArrayList<Object>());
			objectOutputStream.close();
		}
	}
	
	public ArrayList<Object> readShopCart() throws StreamCorruptedException,
			IOException, ClassNotFoundException {
		ArrayList<Object> products = null;
		checkFile();
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				fileInputStream);
		products = (ArrayList<Object>) objectInputStream.readObject();
		objectInputStream.close();
		return products;
	}

	public String getPath() {
		File file = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "shopcart");
			if (!file.exists()) {
				file.mkdir();
			}
			return file.toString();
		} else {
			return null;
		}

	}

	public void remove(int position) {
		MyApplication.shopCartList.remove(position);
	}
}
