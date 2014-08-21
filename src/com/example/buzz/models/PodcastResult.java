package com.example.buzz.models;

import java.net.URI;

public class PodcastResult extends Result {
  public int id;
  public int subscription_id;

  public String title;
  public String description;
  public URI feed_url;
  public URI image_url;
  public URI link_url;

  public String[] categories;
  public int subscriptions_count;

  public int getId() { return id; }
}
