package com.example.buzz.models;

import com.example.buzz.query.QueryResults;

public class ModelCollection {
  public EpisodeResult[] episodes;
  public PodcastResult[] podcasts;
  public QueuedEpisodeResult[] queuedEpisodes;

  private ModelMap<EpisodeResult> episodesMap;
  private ModelMap<PodcastResult> podcastsMap;
  private ModelMap<QueuedEpisodeResult> queuedEpisodesMap;

  public ModelCollection(QueryResults qr) {
    this.episodes = qr.episodes;
    this.podcasts = qr.podcasts;
    this.queuedEpisodes = qr.queued_episodes;

    episodesMap = new ModelMap<EpisodeResult>(episodes, this);
    podcastsMap = new ModelMap<PodcastResult>(podcasts, this);
    queuedEpisodesMap = new ModelMap<QueuedEpisodeResult>(queuedEpisodes, this);
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
}
