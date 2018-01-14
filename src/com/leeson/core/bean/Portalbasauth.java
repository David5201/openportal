package com.leeson.core.bean;

import java.io.Serializable;

public class Portalbasauth
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long basid;
  private Integer type;
  private String username;
  private String password;
  private String basip;
  private String url;
  private Long sessiontime;

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
  public Integer getType() {
    return this.type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getBasip() {
    return this.basip;
  }
  public void setBasip(String basip) {
    this.basip = basip;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public Long getSessiontime() {
    return this.sessiontime;
  }
  public void setSessiontime(Long sessiontime) {
    this.sessiontime = sessiontime;
  }
  public String toString() {
    return "Portalbasauth [id=" + this.id + ",basid=" + this.basid + ",type=" + this.type + ",username=" + this.username + ",password=" + this.password + ",basip=" + this.basip + ",url=" + this.url + ",sessiontime=" + this.sessiontime + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalbasauth
 * JD-Core Version:    0.6.2
 */