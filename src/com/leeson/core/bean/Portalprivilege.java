package com.leeson.core.bean;

import java.io.Serializable;

public class Portalprivilege
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String url;
  private Integer position;
  private Long parentId;

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
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public Integer getPosition() {
    return this.position;
  }
  public void setPosition(Integer position) {
    this.position = position;
  }
  public Long getParentId() {
    return this.parentId;
  }
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
  public String toString() {
    return "Portalprivilege [id=" + this.id + ",name=" + this.name + ",url=" + this.url + ",position=" + this.position + ",parentId=" + this.parentId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalprivilege
 * JD-Core Version:    0.6.2
 */