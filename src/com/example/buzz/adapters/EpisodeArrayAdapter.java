package com.example.buzz.adapters;

import com.example.buzz.models.EpisodeResult;
import com.example.buzz.views.EpisodeView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EpisodeArrayAdapter extends ArrayAdapter<EpisodeResult> {

  public EpisodeArrayAdapter(Context context, int resource, EpisodeResult[] episodes) {
		super(context, resource, episodes);
		// TODO Auto-generated constructor stub
	}

@Override
  public View getView(int position, View convertView, ViewGroup parent) {
    EpisodeResult episode = getItem(position);

    if (convertView == null) {
      return new EpisodeView(getContext(), episode);
    } else {
      return ((EpisodeView) convertView).update();
    }
  }
}

