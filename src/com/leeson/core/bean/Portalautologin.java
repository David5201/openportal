package com.leeson.core.bean;

import java.io.Serializable;

public class Portalautologin
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long time;
  private Integer type;
  private Integer state;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getTime() {
    return this.time;
  }
  public void setTime(Long time) {
    this.time = time;
  }
  public Integer getType() {
    return this.type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public Integer getState() {
    return this.state;
  }
  public void setState(Integer state) {
    this.state = state;
  }
  public String toString() {
    return "Portalautologin [id=" + this.id + ",time=" + this.time + ",type=" + this.type + ",state=" + this.state + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalautologin
 * JD-Core Version:    0.6.2
 */