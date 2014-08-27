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

  public EpisodeView(Context context, EpisodeResult episode) {
    super(context);
    this.episode = episode;

    setPadding(0, 0, 10, 10);

    addImage();
    addTitle();
    addSynced();
  }

  private void addTitle() {
    TextView title = new TextView(getContext());
    title.setText(this.episode.title);
    title.setTextSize(20);

    LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
    title.setLayoutParams(params);

    addView(title);
  }

  private void addImage() {
    ImageLoader imageLoader = Provider.getInstance(getContext()).getImageLoader();
    URI image_url = episode.podcast().image_url;
    if (image_url != null) {
      Log.v(episode.title, image_url.toString());
      NetworkImageView podcast_image = new NetworkImageView(getContext());
      podcast_image.setImageUrl(image_url.toString(), imageLoader);

      LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
      podcast_image.setLayoutParams(params);

      podcast_image.setAdjustViewBounds(true);
      podcast_image.setMaxWidth(100);

      addView(podcast_image);
    } else {
      Log.e(episode.title, "null");
    }
  }

  private void addSynced() {
    ImageView synced = new ImageView(getContext());
    if (episode.isStored()) {
      synced.setImageResource(R.drawable.ic_action_accept);
    }

    addView(synced);
  }
}

