package com.example.buzz.results;

import com.example.buzz.ModelCollection;

public abstract class Result {
  protected ModelCollection modelCollection;

  public void setModelCollection(ModelCollection m) {
    this.modelCollection = m;
  }

  public abstract int getId();
}
