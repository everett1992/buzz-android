package com.example.buzz;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
      case R.id.action_search:
        //openSearch();
        return true;
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
}

