package com.example.buzz;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.preference.PreferenceManager;

public class MainActivity extends BaseActivity {
  public final static String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set default values
      PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

      setContentView(R.layout.main);
  }

  public void sendMessage(View view) {
    // Create new intent, intents are used to create and pass information
    // to new contexts.
    Intent intent = new Intent(this, DisplayMessageActivity.class);

    // Get the text from the EditText in the layout.
    EditText editText = (EditText) findViewById(R.id.edit_message);
    String message = editText.getText().toString();

    // Add the message to the intent as a key value pair.
    intent.putExtra(EXTRA_MESSAGE, message);

    startActivity(intent);
  }
}
