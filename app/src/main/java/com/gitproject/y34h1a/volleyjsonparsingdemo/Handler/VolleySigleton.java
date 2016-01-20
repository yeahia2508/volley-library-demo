package com.gitproject.y34h1a.volleyjsonparsingdemo.Handler;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Y34H1A on 6/23/2015.
 */
public class VolleySigleton {
    public static VolleySigleton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;
    private VolleySigleton(){
        mRequestQueue = Volley.newRequestQueue(CustomApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache(){
            private LruCache<String,Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });

    }
    public static VolleySigleton getInstance(){
        if(sInstance == null) {
            sInstance = new VolleySigleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
