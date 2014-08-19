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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
  private EpisodeModel[] episodes;
  private TextView messageView;
  private ListView listView;
  private RequestQueue requestQueue;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set default values
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    setContentView(R.layout.main);

    messageView = (TextView) findViewById(R.id.message);
    listView = (ListView) findViewById(R.id.names);
    requestQueue = Volley.newRequestQueue(this);

    if (savedInstanceState != null) {
      Gson gson = new Gson();
      displayMessage(savedInstanceState.getString("message"));
      displayEpisodes(savedInstanceState.getString("episodes"));
    }
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
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    // save state of your activity to outState
    Gson gson = new Gson();
    String message = messageView.getText().toString();

    outState.putString("episodes", gson.toJson(episodes));
    outState.putString("message", message);
  }

  public void refresh() {
    String url = q("queued_episodes").toString();
    displayMessage(url);

    ConnectivityManager connMgr = (ConnectivityManager)
      getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      displayMessage("refreshing...");
      // Request a string response from the provided URL.
      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
          null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              try {
                displayEpisodes(response.getJSONArray("episodes").toString());
              } catch (JSONException ex) {
                displayMessage("JSON parse error");
              }
            }
          }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              NetworkResponse networkResponse = error.networkResponse;
              if (networkResponse != null) {
                int status = networkResponse.statusCode;
                displayMessage(String.format("status: %d", status));
              } else {
                displayMessage("That didn't work!");
              }
            }
          });
      // Add the request to the RequestQueue.
      requestQueue.add(jsonObjectRequest);
    } else {
      messageView.setVisibility(View.VISIBLE);
      messageView.setText("No network connection available.");
    }
  }

  private void displayMessage(String message) {
    messageView.setText(message);
    messageView.setVisibility(View.VISIBLE);
  }

  private void displayEpisodes(String episodes) {
    Gson gson = new Gson();
    displayEpisodes(gson.fromJson(episodes, EpisodeModel[].class));
  }

  private void displayEpisodes(EpisodeModel[] episodes) {
    this.episodes = episodes;
    EpisodeArrayAdapter adapter = new EpisodeArrayAdapter(this, android.R.layout.simple_list_item_1, episodes);
    listView.setAdapter(adapter);
    messageView.setVisibility(View.GONE);
  }
}
