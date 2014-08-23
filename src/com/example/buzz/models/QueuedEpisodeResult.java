package com.example.buzz.models;

import com.google.gson.annotations.Expose;


public class QueuedEpisodeResult extends Result {
  @Expose
  public int id;
  @Expose
  public int episode_id;
  @Expose
  public int idx;

  public EpisodeResult episode() {
    return modelCollection.getEpisodeByID(episode_id);
  }

  public int getId() { return id; }
}
