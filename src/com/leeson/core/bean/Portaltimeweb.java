package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portaltimeweb
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Date viewdate;
  private Integer viewweekday;
  private Integer viewdateday;
  private Integer viewmonth;
  private Long web;
  private Long count;
  private Long pos;

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
  public Date getViewdate() {
    return this.viewdate;
  }
  public void setViewdate(Date viewdate) {
    this.viewdate = viewdate;
  }
  public Integer getViewweekday() {
    return this.viewweekday;
  }
  public void setViewweekday(Integer viewweekday) {
    this.viewweekday = viewweekday;
  }
  public Integer getViewdateday() {
    return this.viewdateday;
  }
  public void setViewdateday(Integer viewdateday) {
    this.viewdateday = viewdateday;
  }
  public Integer getViewmonth() {
    return this.viewmonth;
  }
  public void setViewmonth(Integer viewmonth) {
    this.viewmonth = viewmonth;
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
  public Long getPos() {
    return this.pos;
  }
  public void setPos(Long pos) {
    this.pos = pos;
  }
  public String toString() {
    return "Portaltimeweb [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",viewdate=" + this.viewdate + ",viewweekday=" + this.viewweekday + ",viewdateday=" + this.viewdateday + ",viewmonth=" + this.viewmonth + ",web=" + this.web + ",count=" + this.count + ",pos=" + this.pos + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaltimeweb
 * JD-Core Version:    0.6.2
 */