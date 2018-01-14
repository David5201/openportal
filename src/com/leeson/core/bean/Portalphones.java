package com.leeson.core.bean;

import java.io.Serializable;

public class Portalphones
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long did;
  private Long uid;
  private String name;
  private String phone;
  private String description;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getDid() {
    return this.did;
  }
  public void setDid(Long did) {
    this.did = did;
  }
  public Long getUid() {
    return this.uid;
  }
  public void setUid(Long uid) {
    this.uid = uid;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String toString() {
    return "Portalphones [id=" + this.id + ",did=" + this.did + ",uid=" + this.uid + ",name=" + this.name + ",phone=" + this.phone + ",description=" + this.description + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalphones
 * JD-Core Version:    0.6.2
 */