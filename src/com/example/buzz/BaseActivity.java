package com.example.buzz;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity {

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.action_bar, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
      case R.id.action_settings:
        openSettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void openSettings() {
    Intent intent = new Intent(this, GlobalSettingsActivity.class);
    startActivity(intent);
  }

  protected QueryUrl q(String model) {

    SharedPreferences preferences =
      PreferenceManager.getDefaultSharedPreferences(this);
    String hostname = preferences.getString("pref_server", "");
    String user_id_hash = preferences.getString("pref_id_hash", "");

    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id_hash", user_id_hash);

    return new QueryUrl(hostname, model, params);

  }
}
