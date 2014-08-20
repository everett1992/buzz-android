package com.example.buzz;

import com.example.buzz.results.*;

public class QueryResults {
  EpisodeResult[] episodes;
  PodcastResult[] podcasts;
  QueuedEpisodeResult[] queuedEpisodes;

  public ModelCollection toModelCollection() {
    return new ModelCollection(this);
  }
}
