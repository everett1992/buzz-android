package com.example.buzz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.example.buzz.query.QueryResults;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends BaseActivity {
  private ModelCollection modelCollection;
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
      Gson gson = new Gson();
      displayMessage(savedInstanceState.getString("message"));

      QueryResults qr =
        gson.fromJson(savedInstanceState.getString("query_result"), QueryResults.class);

      if (qr != null) {
        displayEpisodes(new ModelCollection(qr));
      }
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
      case R.id.action_download:
        download();
        return true;
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
    GsonBuilder builder = new GsonBuilder();
    builder.excludeFieldsWithoutExposeAnnotation();
    Gson gson = builder.create();
    String message = messageView.getText().toString();

    if (modelCollection != null) {
      outState.putString("query_result", gson.toJson(modelCollection.getQr()));
    }
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
        modelCollection = mc;
        displayEpisodes(mc);
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
        displayMessage("No network connection available.");
      }
    });
  }

  public void download() {
    // create a download task for every episode in the queue
    EpisodeStore<String> episodeStore = new EpisodeStore<String>() {
      @Override
      protected void onEpisodePreExecute(EpisodeResult episode) {
      }

      @Override
      protected void onEpisodeProgressUpdate(EpisodeResult episode, Integer... ignored) {
        episode.setCount(ignored[0]);

        int start = listView.getFirstVisiblePosition();
        for(int i=start, j=listView.getLastVisiblePosition(); i<=j; i++) {
          if(episode == listView.getItemAtPosition(i)) {
            View view = listView.getChildAt(i - start);
            listView.getAdapter().getView(i, view, listView);
            break;
          }
        }
      }
      @Override
      protected void onEpisodePostExecute(EpisodeResult episode, String ignored) {
        episode.setStored(true);

        listView.invalidateViews();
      }
    };

    for (EpisodeResult episode : modelCollection.getEpisodes()) {
      episodeStore.store(episode);
    }
  }

  private void displayMessage(String message) {
    messageView.setText(message);
    messageView.setVisibility(View.VISIBLE);
  }

  private void displayEpisodes(ModelCollection mc) {
    modelCollection = mc;
    EpisodeArrayAdapter adapter = new EpisodeArrayAdapter(this, android.R.layout.simple_list_item_1, modelCollection.getEpisodes());
    listView.setAdapter(adapter);
    messageView.setVisibility(View.GONE);
  }
}
