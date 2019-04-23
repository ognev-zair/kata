package com.abercrombie.codetest.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Data {
  @SerializedName("title")
  private String title;

  @SerializedName("backgroundImage")
  private String backgroundImage;

  @SerializedName("promoMessage")
  private String promoMessage;

  @SerializedName("topDescription")
  private String topDescription;

  @SerializedName("bottomDescription")
  private String bottomDescription;

  @SerializedName("content")
  private List<Navigation> navigations;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  public void setPromoMessage(String promoMessage) {
    this.promoMessage = promoMessage;
  }

  public void setTopDescription(String topDescription) {
    this.topDescription = topDescription;
  }

  public void setBottomDescription(String bottomDescription) {
    this.bottomDescription = bottomDescription;
  }

  public void setNavigations(List<Navigation> navigations) {
    this.navigations = navigations;
  }

  public String getTitle() {
    return title;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public String getPromoMessage() {
    return promoMessage;
  }

  public String getTopDescription() {
    return topDescription;
  }

  public String getBottomDescription() {
    return bottomDescription;
  }

  public List<Navigation> getNavigations() {
    return navigations;
  }
}
