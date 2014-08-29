package com.example.buzz.views;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.buzz.R;
import com.example.buzz.models.EpisodeResult;
import com.example.buzz.network.Provider;

import java.net.URI;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;

public class EpisodeView extends LinearLayout {
  private EpisodeResult episode;

  private NetworkImageView podcastImageView;
  private TextView titleView;
  private TextView progressView;
  private ImageView syncedView;

  public EpisodeView(Context context, EpisodeResult episode) {
    super(context);
    this.episode = episode;

    setPadding(0, 0, 10, 10);

    Log.e("Update", String.format("Should redraw this episode %s, %d", episode.title, episode.getCount()));


    // Create and configure title view.
    titleView = new TextView(getContext());
    titleView.setTextSize(20);
    LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
    titleView.setLayoutParams(params);

    // Create and configure podcast image view.
    podcastImageView = new NetworkImageView(getContext());
    LayoutParams podcastImageParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    podcastImageView.setLayoutParams(podcastImageParams);
    podcastImageView.setAdjustViewBounds(true);
    podcastImageView.setMaxWidth(100);

    // Create and configure synced view.
    syncedView = new ImageView(getContext());

    // Create and configure progress view.
    progressView = new TextView(getContext());
    LayoutParams progressParams = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
    progressView.setLayoutParams(progressParams);
    progressView.setTextSize(20);

    // Add all of the views.
    addView(podcastImageView);
    addView(titleView);
    addView(progressView);
    addView(syncedView);

    update();
  }

  public EpisodeView update() {
    // Set title
    titleView.setText(this.episode.title);

    // Set image
    ImageLoader imageLoader = Provider.getInstance(getContext()).getImageLoader();
    URI image_url = episode.podcast().image_url;
    if (image_url != null) {
      podcastImageView.setImageUrl(image_url.toString(), imageLoader);
    }

    // Set stored
    if (episode.isStored()) {
      syncedView.setImageResource(R.drawable.ic_action_accept);
    }

    // Set progress
    progressView.setText(this.episode.getCount().toString());

    return this;
  }

}

