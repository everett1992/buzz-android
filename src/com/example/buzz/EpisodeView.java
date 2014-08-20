package com.example.buzz;

import java.net.URI;

import com.example.buzz.results.EpisodeResult;

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
    URI image_url = episode.podcast().image_url;
    if (image_url != null) {
      Log.v(episode.title, image_url.toString());
      UriImageView podcast_image = new UriImageView(getContext());
      podcast_image.setImageUri(image_url);

      addView(podcast_image);
    } else {
      Log.e(episode.title, "null");
    }
  }

  private void addSynced() {
    ImageView synced = new ImageView(getContext());
    synced.setImageResource(R.drawable.ic_action_accept);

    addView(synced);
  }
}

