package com.example.buzz;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;

public class EpisodeView extends LinearLayout {
  private EpisodeModel episode;

  public EpisodeView(Context context, EpisodeModel episode) {
    super(context);
    this.episode = episode;

    setPadding(0, 0, 10, 10);

    TextView title = new TextView(context);
    title.setText(this.episode.title);
    title.setTextSize(20);

    LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
    title.setLayoutParams(params);

    ImageView synced = new ImageView(context);
    synced.setImageResource(R.drawable.ic_action_accept);

    addView(title);
    addView(synced);
  }
}

