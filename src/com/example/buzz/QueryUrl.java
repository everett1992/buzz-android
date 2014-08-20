package com.example.buzz;

import java.util.HashMap;

public class QueryUrl {
  String hostname;
  String model;
  HashMap<String, String> params;

  public QueryUrl(String hostname) {
    this.hostname = hostname;
    this.params = new HashMap<String, String>();
  }

  public QueryUrl(String hostname, HashMap<String, String> params) {
    this.hostname = hostname;
    this.params = params;
  }

  public QueryUrl(String hostname, String model, HashMap<String, String> params) {
    this.hostname = hostname;
    this.model = model;
    this.params = params;
  }

  public QueryUrl(String hostname, String model) {
    this.hostname = hostname;
    this.params = new HashMap<String, String>();
  }

  // returns a new QueryUrl with the param added.
  public QueryUrl addParam(String key, String value) {
    @SuppressWarnings("unchecked")
	HashMap<String, String> new_params = (HashMap<String, String>) params.clone();
    new_params.put(key, value);
    return new QueryUrl(hostname, model, new_params);
  }

  private String baseName() {
    if (model == null) {
      return hostname;
    } else {
      return String.format("%s%s", hostname, model);
    }
  }

  private String url() {
    StringBuilder url = new StringBuilder(baseName());
    String sep = "?";
    for (String key : params.keySet()) {
      String value = params.get(key);
      url.append(String.format("%s%s=%s", sep, key, value));
      sep = "&";
    }
    return url.toString();
  }

  public String toString() {
    return url();
  }
}
