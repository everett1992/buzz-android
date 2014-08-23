package com.example.buzz.models;

import com.example.buzz.models.ModelCollection;
import com.google.gson.annotations.Expose;

public abstract class Result {
  @Expose( serialize = false, deserialize = false )
  protected ModelCollection modelCollection;

  public void setModelCollection(ModelCollection m) {
    this.modelCollection = m;
  }

  public abstract int getId();
}
