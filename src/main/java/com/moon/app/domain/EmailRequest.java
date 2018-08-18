package com.moon.app.domain;

import java.io.Serializable;

public class EmailRequest implements Serializable {

  private static final long serialVersionUID = -2231964655598360424L;

  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
