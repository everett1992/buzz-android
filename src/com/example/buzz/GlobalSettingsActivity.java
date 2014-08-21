package com.example.buzz;

import com.example.buzz.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class GlobalSettingsActivity extends PreferenceActivity {
@SuppressWarnings("deprecation")
@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
  }
}
