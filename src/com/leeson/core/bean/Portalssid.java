package com.leeson.core.bean;

import java.io.Serializable;

public class Portalssid
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long basid;
  private String ip;
  private String ssid;
  private String address;
  private String basip;
  private String x;
  private String y;
  private String des;
  private String name;
  private Long web;
  private Long count;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getBasid() {
    return this.basid;
  }
  public void setBasid(Long basid) {
    this.basid = basid;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getSsid() {
    return this.ssid;
  }
  public void setSsid(String ssid) {
    this.ssid = ssid;
  }
  public String getAddress() {
    return this.address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getBasip() {
    return this.basip;
  }
  public void setBasip(String basip) {
    this.basip = basip;
  }
  public String getX() {
    return this.x;
  }
  public void setX(String x) {
    this.x = x;
  }
  public String getY() {
    return this.y;
  }
  public void setY(String y) {
    this.y = y;
  }
  public String getDes() {
    return this.des;
  }
  public void setDes(String des) {
    this.des = des;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Long getWeb() {
    return this.web;
  }
  public void setWeb(Long web) {
    this.web = web;
  }
  public Long getCount() {
    return this.count;
  }
  public void setCount(Long count) {
    this.count = count;
  }
  public String toString() {
    return "Portalssid [id=" + this.id + ",basid=" + this.basid + ",ip=" + this.ip + ",ssid=" + this.ssid + ",address=" + this.address + ",basip=" + this.basip + ",x=" + this.x + ",y=" + this.y + ",des=" + this.des + ",name=" + this.name + ",web=" + this.web + ",count=" + this.count + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalssid
 * JD-Core Version:    0.6.2
 */