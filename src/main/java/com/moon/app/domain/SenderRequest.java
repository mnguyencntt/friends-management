package com.moon.app.domain;

import java.io.Serializable;

public class SenderRequest implements Serializable {

  private static final long serialVersionUID = -2231964655598360424L;

  private String sender;
  private String text;

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
