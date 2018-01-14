package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Advadv
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Date creatDate;
  private Integer state;
  private Date showDate;
  private Date endDate;
  private Long uid;
  private Long sid;
  private Long pos;
  private String img;
  private Integer showName;
  private Integer showInfo;
  private Integer showImg;
  private Long showCount;
  private Long clickCount;
  private String url;
  private Long lockTime;

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
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getCreatDate() {
    return this.creatDate;
  }
  public void setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
  }
  public Integer getState() {
    return this.state;
  }
  public void setState(Integer state) {
    this.state = state;
  }
  public Date getShowDate() {
    return this.showDate;
  }
  public void setShowDate(Date showDate) {
    this.showDate = showDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
  public Integer getShowName() {
    return this.showName;
  }
  public void setShowName(Integer showName) {
    this.showName = showName;
  }
  public Integer getShowInfo() {
    return this.showInfo;
  }
  public void setShowInfo(Integer showInfo) {
    this.showInfo = showInfo;
  }
  public Integer getShowImg() {
    return this.showImg;
  }
  public void setShowImg(Integer showImg) {
    this.showImg = showImg;
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
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public Long getLockTime() {
    return this.lockTime;
  }
  public void setLockTime(Long lockTime) {
    this.lockTime = lockTime;
  }
  public String toString() {
    return "Advadv [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",creatDate=" + this.creatDate + ",state=" + this.state + ",showDate=" + this.showDate + ",endDate=" + this.endDate + ",uid=" + this.uid + ",sid=" + this.sid + ",pos=" + this.pos + ",img=" + this.img + ",showName=" + this.showName + ",showInfo=" + this.showInfo + ",showImg=" + this.showImg + ",showCount=" + this.showCount + ",clickCount=" + this.clickCount + ",url=" + this.url + ",lockTime=" + this.lockTime + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advadv
 * JD-Core Version:    0.6.2
 */