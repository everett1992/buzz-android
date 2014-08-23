package com.example.buzz;

import android.content.Context;
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
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.buzz.R;
import com.example.buzz.adapters.EpisodeArrayAdapter;
import com.example.buzz.models.EpisodeResult;
import com.example.buzz.models.ModelCollection;
import com.example.buzz.network.Provider;
import com.example.buzz.query.Query;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {
  private EpisodeResult[] episodes;
  private TextView messageView;
  private ListView listView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set default values
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    setContentView(R.layout.main);

    messageView = (TextView) findViewById(R.id.message);
    listView = (ListView) findViewById(R.id.names);

    if (savedInstanceState != null) {
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
    Provider provider = Provider.getInstance(getApplicationContext());

    ConnectivityManager connMgr = (ConnectivityManager)
      getSystemService(Context.CONNECTIVITY_SERVICE);
    Query query = new Query(connMgr, provider.getRequestQueue());

    query.execute(q("queued_episodes"), new Query.Callbacks() {
      @Override
      public void onSuccess(ModelCollection mc) {
        displayEpisodes(mc.episodes);
      }

      @Override
      public void onError(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null) {
          int status = networkResponse.statusCode;
          displayMessage(String.format("status: %d", status));
        } else {
          displayMessage(String.format("No response from %s", q("queued_episodes").toString()));
        }
      }
      @Override
      public void onNoNetwork(NetworkInfo info) {
        displayEpisodes("No network connection available.");
      }
    });
  }

  private void displayMessage(String message) {
    messageView.setText(message);
    messageView.setVisibility(View.VISIBLE);
  }

  private void displayEpisodes(String episodes) {
    Gson gson = new Gson();
    displayEpisodes(gson.fromJson(episodes, EpisodeResult[].class));
  }

  private void displayEpisodes(EpisodeResult[] episodes) {
    this.episodes = episodes;
    EpisodeArrayAdapter adapter = new EpisodeArrayAdapter(this, android.R.layout.simple_list_item_1, episodes);
    listView.setAdapter(adapter);
    messageView.setVisibility(View.GONE);
  }
}
