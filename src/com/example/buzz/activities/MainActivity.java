package com.example.buzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends BaseActivity {
  private TextView textView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set default values
      PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

      setContentView(R.layout.main);
      textView = (TextView) findViewById(R.id.refresh_result);
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

  public void refresh() {
    String stringUrl = "http://192.168.1.101:3000/test";

    ConnectivityManager connMgr = (ConnectivityManager)
      getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      textView.setText("refreshing...");
      new DownloadWebpageTask().execute(stringUrl);
    } else {
      textView.setText("No network connection available.");
    }
  }

  // Uses AsyncTask to create a task away from the main UI thread. This task takes a
  // URL string and uses it to create an HttpUrlConnection. Once the connection
  // has been established, the AsyncTask downloads the contents of the webpage as
  // an InputStream. Finally, the InputStream is converted into a string, which is
  // displayed in the UI by the AsyncTask's onPostExecute method.
  private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

      // params comes from the execute() call: params[0] is the url.
      try {
        return downloadUrl(urls[0]);
      } catch (IOException e) {
        return "Unable to retrieve web page. URL may be invalid.";
      }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
      textView.setText(result);
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
      InputStream is = null;
      // Only display the first 500 characters of the retrieved
      // web page content.
      int len = 500;

      try {
        URL url = new URL(myurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        is = conn.getInputStream();

        // Convert the InputStream into a string
        String contentAsString = readIt(is, len);
        return contentAsString;

        // Makes sure that the InputStream is closed after the app is
        // finished using it.
      } finally {
        if (is != null) {
          is.close();
        }
      }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
      Reader reader = null;
      reader = new InputStreamReader(stream, "UTF-8");
      char[] buffer = new char[len];
      reader.read(buffer);
      return new String(buffer);
    }
  }
}
