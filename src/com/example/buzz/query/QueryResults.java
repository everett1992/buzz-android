package com.example.buzz.query;

import com.example.buzz.models.*;
import com.google.gson.annotations.Expose;

public class QueryResults {
  @Expose
  public EpisodeResult[] episodes;
  @Expose
  public PodcastResult[] podcasts;
  @Expose
  public QueuedEpisodeResult[] queued_episodes;

  public ModelCollection toModelCollection() {
    return new ModelCollection(this);
  }
}
