package com.infinium.global.config.data.adapters;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureInstanceCreator implements InstanceCreator<ScheduledFuture<?>> {

  @Override
  public ScheduledFuture<?> createInstance(Type type) {
    return null;
  }

}
