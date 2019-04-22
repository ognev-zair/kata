package com.abercrombie.codetest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Buttons content
 */
public class Navigation {
  @SerializedName("target")
  private String target;

  @SerializedName("title")
  private String title;

  public void setTarget(String target) {
    this.target = target;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTarget() {
    return target;
  }

  public String getTitle() {
    return title;
  }
}
