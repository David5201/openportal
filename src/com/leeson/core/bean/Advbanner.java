package com.leeson.core.bean;

import java.io.Serializable;

public class Advbanner
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Long uid;
  private Long sid;
  private Long aid;
  private Long pos;
  private String img;
  private String url;
  private Long showCount;
  private Long clickCount;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Long getUid() {
    return this.uid;
  }
  public void setUid(Long uid) {
    this.uid = uid;
  }
  public Long getSid() {
    return this.sid;
  }
  public void setSid(Long sid) {
    this.sid = sid;
  }
  public Long getAid() {
    return this.aid;
  }
  public void setAid(Long aid) {
    this.aid = aid;
  }
  public Long getPos() {
    return this.pos;
  }
  public void setPos(Long pos) {
    this.pos = pos;
  }
  public String getImg() {
    return this.img;
  }
  public void setImg(String img) {
    this.img = img;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public Long getShowCount() {
    return this.showCount;
  }
  public void setShowCount(Long showCount) {
    this.showCount = showCount;
  }
  public Long getClickCount() {
    return this.clickCount;
  }
  public void setClickCount(Long clickCount) {
    this.clickCount = clickCount;
  }
  public String toString() {
    return "Advbanner [id=" + this.id + ",name=" + this.name + ",uid=" + this.uid + ",sid=" + this.sid + ",aid=" + this.aid + ",pos=" + this.pos + ",img=" + this.img + ",url=" + this.url + ",showCount=" + this.showCount + ",clickCount=" + this.clickCount + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advbanner
 * JD-Core Version:    0.6.2
 */