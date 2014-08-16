package com.example.buzz;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EpisodeArrayAdapter extends ArrayAdapter<EpisodeModel> {

  public EpisodeArrayAdapter(Context context, int resource, EpisodeModel[] episodes) {
		super(context, resource, episodes);
		// TODO Auto-generated constructor stub
	}

@Override
  public View getView(int position, View convertView, ViewGroup parent) {
    EpisodeModel episode = getItem(position);

    return new EpisodeView(getContext(), episode);
  }
}

