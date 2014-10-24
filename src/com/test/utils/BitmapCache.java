package com.test.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created with IntelliJ IDEA.
 * User: jimmy
 * Date: 8/12/13
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}