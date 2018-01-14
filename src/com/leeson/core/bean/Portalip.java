package com.leeson.core.bean;

import java.io.Serializable;

public class Portalip
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private String start;
  private String end;
  private Long web;
  private Long count;

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
  public String getStart() {
    return this.start;
  }
  public void setStart(String start) {
    this.start = start;
  }
  public String getEnd() {
    return this.end;
  }
  public void setEnd(String end) {
    this.end = end;
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
    return "Portalip [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",start=" + this.start + ",end=" + this.end + ",web=" + this.web + ",count=" + this.count + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalip
 * JD-Core Version:    0.6.2
 */