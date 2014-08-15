package com.example.buzz;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class GlobalSettingsActivity extends PreferenceActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
  }
}
