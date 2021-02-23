package com.example.tfrohvolleyex.net_utils;

import android.graphics.Bitmap;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import android.util.LruCache;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }
    
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }
    
    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
