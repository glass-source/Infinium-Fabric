package com.infinium.api.utils;

import lombok.Getter;

public class Instantiable<T> {
  protected @Getter final T instance;

  public Instantiable(T clazz) {
    instance = clazz;
  }
}
