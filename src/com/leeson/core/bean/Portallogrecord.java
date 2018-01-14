package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portallogrecord
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String info;
  private Date recDate;

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
  public Date getRecDate() {
    return this.recDate;
  }
  public void setRecDate(Date recDate) {
    this.recDate = recDate;
  }
  public String toString() {
    return "Portallogrecord [id=" + this.id + ",info=" + this.info + ",recDate=" + this.recDate + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portallogrecord
 * JD-Core Version:    0.6.2
 */