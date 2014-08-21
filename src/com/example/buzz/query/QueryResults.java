package com.example.buzz.query;

import com.example.buzz.models.*;

public class QueryResults {
  public EpisodeResult[] episodes;
  public PodcastResult[] podcasts;
  public QueuedEpisodeResult[] queued_episodes;

  public ModelCollection toModelCollection() {
    return new ModelCollection(this);
  }
}
