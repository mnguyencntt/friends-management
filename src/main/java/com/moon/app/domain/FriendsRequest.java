package com.moon.app.domain;

import java.io.Serializable;
import java.util.List;

public class FriendsRequest implements Serializable {

  private static final long serialVersionUID = -2231964655598360424L;

  private List<String> friends;

  public List<String> getFriends() {
    return friends;
  }

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }

}
