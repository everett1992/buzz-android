package com.example.buzz.results;

public class QueuedEpisodeResult extends Result {
	public int id;
	public int episode_id;
	public int idx;

  public EpisodeResult episode() {
    return modelCollection.getEpisodeByID(episode_id);
  }

  public int getId() { return id; }
}
