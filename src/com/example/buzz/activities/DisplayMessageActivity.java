package com.example.buzz;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;


public class DisplayMessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setMessage();
    }

    protected void setMessage() {
      Intent intent = getIntent();
      String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
      setContentView(R.layout.display_message);

      TextView textView = (TextView) findViewById(R.id.show_message);
      textView.setTextSize(40);
      textView.setText(message);
    }
}
