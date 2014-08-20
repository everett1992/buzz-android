package com.example.buzz.results;

public class EpisodeResult extends Result {
  public int id;
  public String title;
  public String link_url;
  public String description;
  public String audio_url;
  public String publication_date;
  public int podcast_id;
  public String duration;
  public String episode_type;
  public String ed_is_played;
  public String ed_current_position;
  public String last_listened_at;

  public PodcastResult podcast() {
    return modelCollection.getPodcastByID(podcast_id);
  }

  public int getId() { return id; }
}
