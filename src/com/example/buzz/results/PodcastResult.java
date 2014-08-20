package com.example.buzz.results;

public class PodcastResult extends Result {
  public int id;
  public int subscription_id;

  public String title;
  public String description;
  public String feed_url;
  public String image_url;
  public String link_url;

  public String[] categories;
  public int subscriptions_count;

  public int getId() { return id; }
}
