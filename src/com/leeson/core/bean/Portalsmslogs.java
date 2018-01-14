package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portalsmslogs
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String info;
  private Date sendDate;
  private String phone;
  private Long sid;
  private String type;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getInfo() {
    return this.info;
  }
  public void setInfo(String info) {
    this.info = info;
  }
  public Date getSendDate() {
    return this.sendDate;
  }
  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public Long getSid() {
    return this.sid;
  }
  public void setSid(Long sid) {
    this.sid = sid;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String toString() {
    return "Portalsmslogs [id=" + this.id + ",info=" + this.info + ",sendDate=" + this.sendDate + ",phone=" + this.phone + ",sid=" + this.sid + ",type=" + this.type + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalsmslogs
 * JD-Core Version:    0.6.2
 */