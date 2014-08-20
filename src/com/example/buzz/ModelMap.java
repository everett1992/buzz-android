package com.example.buzz;

import com.example.buzz.results.Result;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ModelMap<T extends Result> extends HashMap<Integer, T> {
  public ModelMap(T[] results, ModelCollection m) {
    for (T r : results) {
      r.setModelCollection(m);
      put(r.getId(), r);
    }
  }
}
