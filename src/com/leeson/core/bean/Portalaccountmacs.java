package com.leeson.core.bean;

import java.io.Serializable;

public class Portalaccountmacs
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long accountId;
  private String mac;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getAccountId() {
    return this.accountId;
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
  public String getMac() {
    return this.mac;
  }
  public void setMac(String mac) {
    this.mac = mac;
  }
  public String toString() {
    return "Portalaccountmacs [id=" + this.id + ",accountId=" + this.accountId + ",mac=" + this.mac + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalaccountmacs
 * JD-Core Version:    0.6.2
 */