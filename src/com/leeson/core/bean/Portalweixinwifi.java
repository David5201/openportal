package com.leeson.core.bean;

import java.io.Serializable;

public class Portalweixinwifi
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String appId;
  private String shopId;
  private String ssid;
  private String domain;
  private String bssid;
  private String secretKey;
  private Long outTime;
  private String basip;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getAppId() {
    return this.appId;
  }
  public void setAppId(String appId) {
    this.appId = appId;
  }
  public String getShopId() {
    return this.shopId;
  }
  public void setShopId(String shopId) {
    this.shopId = shopId;
  }
  public String getSsid() {
    return this.ssid;
  }
  public void setSsid(String ssid) {
    this.ssid = ssid;
  }
  public String getDomain() {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public String getBssid() {
    return this.bssid;
  }
  public void setBssid(String bssid) {
    this.bssid = bssid;
  }
  public String getSecretKey() {
    return this.secretKey;
  }
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
  public Long getOutTime() {
    return this.outTime;
  }
  public void setOutTime(Long outTime) {
    this.outTime = outTime;
  }
  public String getBasip() {
    return this.basip;
  }
  public void setBasip(String basip) {
    this.basip = basip;
  }
  public String toString() {
    return "Portalweixinwifi [id=" + this.id + ",appId=" + this.appId + ",shopId=" + this.shopId + ",ssid=" + this.ssid + ",domain=" + this.domain + ",bssid=" + this.bssid + ",secretKey=" + this.secretKey + ",outTime=" + this.outTime + ",basip=" + this.basip + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalweixinwifi
 * JD-Core Version:    0.6.2
 */