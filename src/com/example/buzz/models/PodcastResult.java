package com.example.buzz.models;

import java.net.URI;

import com.google.gson.annotations.Expose;

public class PodcastResult extends Result {
  @Expose
  public int id;
  @Expose
  public int subscription_id;

  public String title;
  @Expose
  public String description;
  @Expose
  public URI feed_url;
  @Expose
  public URI image_url;
  @Expose
  public URI link_url;

  @Expose
  public String[] categories;
  @Expose
  public int subscriptions_count;

  public int getId() { return id; }
}
