package com.example.buzz;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Query {
  private ConnectivityManager connMgr;
  private RequestQueue requestQueue;

  public Query(ConnectivityManager connMgr, RequestQueue requestQueue) {
    this.connMgr = connMgr;
    this.requestQueue = requestQueue;
  };

  public void execute(QueryUrl queryUrl, final Callbacks callbacks) {
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, queryUrl.toString(),
          null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              // Success callback
              callbacks.onSuccess(response);
            }
          }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              // Error callback
              callbacks.onError(error);
            }
          });
      // Add the request to the RequestQueue.
      requestQueue.add(jsonObjectRequest);
    } else {
      // No network callback
      callbacks.onNoNetwork(networkInfo);
    }
  }

  public interface Callbacks {
    public void onSuccess(JSONObject j);
    public void onError(VolleyError e);
    public void onNoNetwork(NetworkInfo n);
  }
}
