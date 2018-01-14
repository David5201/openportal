package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portallinkrecordallout
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Date creatDate;
  private String url;

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
  public Date getCreatDate() {
    return this.creatDate;
  }
  public void setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String toString() {
    return "Portallinkrecordallout [id=" + this.id + ",name=" + this.name + ",creatDate=" + this.creatDate + ",url=" + this.url + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portallinkrecordallout
 * JD-Core Version:    0.6.2
 */