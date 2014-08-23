package com.example.buzz.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Provider {

  private static Provider provider;
  private RequestQueue requestQueue;
  private ImageLoader imageLoader;

  private Provider(Context context) {
    requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    imageLoader = new ImageLoader(requestQueue,
        new ImageLoader.ImageCache() {
          private final LruCache<String, Bitmap>
            cache = new LruCache<String, Bitmap>(20);

          @Override
          public Bitmap getBitmap(String url) {
            return cache.get(url);
          }

          @Override
          public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
          }
        });
  }

  public static synchronized Provider getInstance(Context context) {
    if (provider == null) {
      provider = new Provider(context);
    }
    return provider;
  }

  public RequestQueue getRequestQueue() {
    return this.requestQueue;
  }

  public ImageLoader getImageLoader() {
    return this.imageLoader;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    getRequestQueue().add(req);
  }
}
