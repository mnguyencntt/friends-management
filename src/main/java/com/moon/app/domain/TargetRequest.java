package com.moon.app.domain;

import java.io.Serializable;

public class TargetRequest implements Serializable {

  private static final long serialVersionUID = -2231964655598360424L;

  private String requestor;
  private String target;

  public String getRequestor() {
    return requestor;
  }

  public void setRequestor(String requestor) {
    this.requestor = requestor;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

}
