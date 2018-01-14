package com.leeson.core.bean;

import java.io.Serializable;

public class Zsqhdapi
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String url;
  private String publicurl;
  private String autourl;
  private Integer state;
  private Integer publicstate;
  private Integer autostate;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getPublicurl() {
    return this.publicurl;
  }
  public void setPublicurl(String publicurl) {
    this.publicurl = publicurl;
  }
  public String getAutourl() {
    return this.autourl;
  }
  public void setAutourl(String autourl) {
    this.autourl = autourl;
  }
  public Integer getState() {
    return this.state;
  }
  public void setState(Integer state) {
    this.state = state;
  }
  public Integer getPublicstate() {
    return this.publicstate;
  }
  public void setPublicstate(Integer publicstate) {
    this.publicstate = publicstate;
  }
  public Integer getAutostate() {
    return this.autostate;
  }
  public void setAutostate(Integer autostate) {
    this.autostate = autostate;
  }
  public String toString() {
    return "Zsqhdapi [id=" + this.id + ",url=" + this.url + ",publicurl=" + this.publicurl + ",autourl=" + this.autourl + ",state=" + this.state + ",publicstate=" + this.publicstate + ",autostate=" + this.autostate + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Zsqhdapi
 * JD-Core Version:    0.6.2
 */