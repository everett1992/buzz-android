package com.example.buzz;

public class BuzzModelManager<T> {
  private Query q;

  public BuzzModelManager(Query q) {
    this.q = q;
  }

  //public T[] find_by_id(int id) {
  //  q.addParam("id", Integer.toString(id));
  //}
}
