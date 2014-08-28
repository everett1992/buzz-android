package com.example.buzz;
import java.util.HashSet;
import java.util.Set;
import java.net.URI;

import android.os.AsyncTask;

import com.example.buzz.models.EpisodeResult;


public abstract class EpisodeStore<Result> {
  private Set<EpisodeResult> episodes;
  public EpisodeStore() {
    episodes = new HashSet<EpisodeResult>();
  }

  public boolean store(EpisodeResult episode) {
    if (episodes.add(episode)) {
      EpisodeDownloadTask downloadTask = new EpisodeDownloadTask(episode);
      downloadTask.execute(episode.audio_url);
      return true;
    } else {
      return false;
    }
  }

  protected abstract void onEpisodePreExecute(EpisodeResult episode);
  protected abstract void onEpisodeProgressUpdate(EpisodeResult episode, Integer... progress);
  protected abstract void onEpisodePostExecute(EpisodeResult episode, Result result);

  private class EpisodeDownloadTask extends AsyncTask<URI, Integer, Result> {
    private EpisodeResult episode;
    private int count;

    public EpisodeDownloadTask(EpisodeResult episode) {
      this.episode = episode;
      count = 0;
    }

    @Override
    protected void onPreExecute() {
      onEpisodePreExecute(episode);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        count += 1;
        onEpisodeProgressUpdate(episode, count);
    }

    @Override
    protected void onPostExecute(Result result) {
      onEpisodePostExecute(episode, result);
    }


    @Override
    protected Result doInBackground(URI... arg0) {
      return null;
    }
  }
}
