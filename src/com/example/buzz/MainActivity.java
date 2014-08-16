package com.example.buzz;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
  private EpisodeModel[] episodes;
  private ArrayAdapter<EpisodeModel> adapter;
  private TextView textView;
  private ListView listView;
  private RequestQueue requestQueue;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set default values
      PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

      setContentView(R.layout.main);

      textView = (TextView) findViewById(R.id.refresh_result);
      listView = (ListView) findViewById(R.id.names);
      requestQueue = Volley.newRequestQueue(this);
  }

  public void sync(View view) {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_action_bar, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
      case R.id.action_refresh:
        refresh();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void downloadEpisodes() {
  }

  public void refresh() {
    final Context context = this;
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String server = preferences.getString("pref_server", "");
    String user_id_hash = preferences.getString("pref_id_hash", "");

    String url = String.format("http://%s/api/v1/queued_episodes?user_id_hash=%s", server, user_id_hash);

    ConnectivityManager connMgr = (ConnectivityManager)
      getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      textView.setText("refreshing...");
      // Request a string response from the provided URL.
      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
          null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              try {
                Gson gson = new Gson();
                episodes = gson.fromJson(response.getJSONArray("episodes").toString(), EpisodeModel[].class);
                adapter = new EpisodeArrayAdapter(context, android.R.layout.simple_list_item_1, episodes);
                listView.setAdapter(adapter);
                textView.setText("Done");
              } catch (JSONException ex) {
                textView.setText("JSON parse error");
              }
            }
          }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              textView.setText("That didn't work!");
            }
          });
      // Add the request to the RequestQueue.
      requestQueue.add(jsonObjectRequest);
    } else {
      textView.setText("No network connection available.");
    }
  }
}
