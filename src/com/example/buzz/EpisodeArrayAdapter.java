package com.example.buzz;

import com.example.buzz.results.EpisodeResult;

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

    return new EpisodeView(getContext(), episode);
  }
}

