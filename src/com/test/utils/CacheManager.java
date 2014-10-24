package com.test.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class CacheManager {
	public static final String NO_MEDIA =".nomedia";
	public static final String DIR_FILES = "tempfiles";
	public String mTempDir;
	public Context context;
	
	public CacheManager(Context context) {
		this.context = context;
	}

	public void initalizeDir() {
		File dir = Environment.getExternalStorageDirectory();
		if (dir == null) {
			mTempDir = null;
			return;
		}

		File dataDir = new File(dir, "Android/data/" + context.getPackageName());
		if (dataDir.exists()) {
			dataDir.delete();
		}
		
		dataDir.mkdirs();
		makeNomediaDir(dataDir);
		mTempDir = createDir(dataDir, DIR_FILES);
    }
	
	public String getCacheDirPath() {
		return mTempDir;
	}
	
	private boolean makeNomediaDir(File dir) {
	    boolean result = false;
	    if (dir.isDirectory()) {
	        File nomedia = new File(dir, NO_MEDIA);
	        result = nomedia.mkdirs();
	    }

	    return result;
	}
	
	private String createDir(File dirPath, String dirName) {
		File cacheDirFile = new File(dirPath, dirName);
		if (!cacheDirFile.exists()) {
			cacheDirFile.mkdirs();
		}
		return cacheDirFile.getAbsolutePath();
	}
	
	
	public void clearCache() {
		if (mTempDir != null) {
			File temDir = new File(mTempDir);
			if (temDir.exists()) {
				temDir.delete();
			}
		}
	}
}
