package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portalmessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String title;
  private String description;
  private Date date;
  private String state;
  private String fromid;
  private String toid;
  private String ip;
  private String toname;
  private String fromname;
  private String delin;
  private String delout;
  private String fromPos;
  private String toPos;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getDate() {
    return this.date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getFromid() {
    return this.fromid;
  }
  public void setFromid(String fromid) {
    this.fromid = fromid;
  }
  public String getToid() {
    return this.toid;
  }
  public void setToid(String toid) {
    this.toid = toid;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getToname() {
    return this.toname;
  }
  public void setToname(String toname) {
    this.toname = toname;
  }
  public String getFromname() {
    return this.fromname;
  }
  public void setFromname(String fromname) {
    this.fromname = fromname;
  }
  public String getDelin() {
    return this.delin;
  }
  public void setDelin(String delin) {
    this.delin = delin;
  }
  public String getDelout() {
    return this.delout;
  }
  public void setDelout(String delout) {
    this.delout = delout;
  }
  public String getFromPos() {
    return this.fromPos;
  }
  public void setFromPos(String fromPos) {
    this.fromPos = fromPos;
  }
  public String getToPos() {
    return this.toPos;
  }
  public void setToPos(String toPos) {
    this.toPos = toPos;
  }
  public String toString() {
    return "Portalmessage [id=" + this.id + ",title=" + this.title + ",description=" + this.description + ",date=" + this.date + ",state=" + this.state + ",fromid=" + this.fromid + ",toid=" + this.toid + ",ip=" + this.ip + ",toname=" + this.toname + ",fromname=" + this.fromname + ",delin=" + this.delin + ",delout=" + this.delout + ",fromPos=" + this.fromPos + ",toPos=" + this.toPos + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalmessage
 * JD-Core Version:    0.6.2
 */