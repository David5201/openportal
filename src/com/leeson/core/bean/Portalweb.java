package com.leeson.core.bean;

import java.io.Serializable;

public class Portalweb
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Long countShow;
  private Long countAuth;
  private Long adv;

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
  public Long getCountShow() {
    return this.countShow;
  }
  public void setCountShow(Long countShow) {
    this.countShow = countShow;
  }
  public Long getCountAuth() {
    return this.countAuth;
  }
  public void setCountAuth(Long countAuth) {
    this.countAuth = countAuth;
  }
  public Long getAdv() {
    return this.adv;
  }
  public void setAdv(Long adv) {
    this.adv = adv;
  }
  public String toString() {
    return "Portalweb [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",countShow=" + this.countShow + ",countAuth=" + this.countAuth + ",adv=" + this.adv + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalweb
 * JD-Core Version:    0.6.2
 */