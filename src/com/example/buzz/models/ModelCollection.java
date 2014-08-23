package com.example.buzz.models;

import com.example.buzz.query.QueryResults;

public class ModelCollection {
  private QueryResults qr;

  private ModelMap<EpisodeResult> episodesMap;
  private ModelMap<PodcastResult> podcastsMap;
  private ModelMap<QueuedEpisodeResult> queuedEpisodesMap;

  public ModelCollection(QueryResults qr) {
    this.qr = qr;

    episodesMap = new ModelMap<EpisodeResult>(qr.episodes, this);
    podcastsMap = new ModelMap<PodcastResult>(qr.podcasts, this);
    queuedEpisodesMap = new ModelMap<QueuedEpisodeResult>(qr.queued_episodes, this);
  }

  public EpisodeResult getEpisodeByID(int id) {
    return episodesMap.get(id);
  }

  public PodcastResult getPodcastByID(int id) {
    return podcastsMap.get(id);
  }

  public QueuedEpisodeResult getQueuedEpisodeByID(int id) {
    return queuedEpisodesMap.get(id);
  }

  public EpisodeResult[] getEpisodes() {
    return qr.episodes;
  }

  public PodcastResult[] getPodcasts() {
    return qr.podcasts;
  }

  public QueuedEpisodeResult[] getQueuedEpisodes() {
    return qr.queued_episodes;
  }

  public QueryResults getQr() {
    return qr;
  }

}
