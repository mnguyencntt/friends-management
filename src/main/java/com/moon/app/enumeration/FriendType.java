package com.moon.app.enumeration;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum FriendType {

  FRIEND("Friend"), BLOCK("Block"), SUBSCRIBE("Subscribe");

  private String type;

  private FriendType(final String type) {
    this.setType(type);
  }

  public static FriendType getFriendType(final String name) {
    Predicate<FriendType> predicate = e -> e.getType().equalsIgnoreCase(name);
    Supplier<IllegalStateException> exceptionSupplier = () -> new IllegalStateException(String.format("Unsupported type %s.", name));
    return Arrays.stream(FriendType.values()).filter(predicate).findFirst().orElseThrow(exceptionSupplier);
  }

  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

}
