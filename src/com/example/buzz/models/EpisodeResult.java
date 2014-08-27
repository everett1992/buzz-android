package com.example.buzz.models;

import java.net.URI;

import com.google.gson.annotations.Expose;

public class EpisodeResult extends Result {
  @Expose
  public int id;
  @Expose
  public String title;
  @Expose
  public String description;
  @Expose
  public URI link_url;
  @Expose
  public URI audio_url;
  @Expose
  public String publication_date;
  @Expose
  public int podcast_id;
  @Expose
  public String duration;
  @Expose
  public String episode_type;
  @Expose
  public String ed_is_played;
  @Expose
  public String ed_current_position;
  @Expose
  public String last_listened_at;

  public PodcastResult podcast() {
    return modelCollection.getPodcastByID(podcast_id);
  }

  public boolean isStored() {
    return true;
  }

  public int getId() { return id; }
}
