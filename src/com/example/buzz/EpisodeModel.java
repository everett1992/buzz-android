package com.example.buzz;

public class EpisodeModel {
  int id;
  String title;
  String link_url;
  String description;
  String audio_url;
  String publication_date;
  int podcast_id;
  String duration;
  String episode_type;
  String ed_is_played;
  String ed_current_position;
  String last_listened_at;

  StoredEpisodeManager storedEpisodeManager = new StoredEpisodeManager();

  public String toString() {
    return title;
  }

  public boolean isStored() {
    return storedEpisodeManager.isStored(this);
  }
}
