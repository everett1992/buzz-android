package com.example.buzz.models;

import com.example.buzz.models.Result;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ModelMap<T extends Result> extends HashMap<Integer, T> {

  public ModelMap(T[] results, ModelCollection m) {
    if (results != null) {
      for (T r : results) {
        r.setModelCollection(m);
        put(r.getId(), r);
      }
    }
  }
}
