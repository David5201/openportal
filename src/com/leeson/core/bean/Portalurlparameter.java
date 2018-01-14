package com.leeson.core.bean;

import java.io.Serializable;

public class Portalurlparameter
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String basname;
  private String userip;
  private String usermac;
  private String url;
  private String basip;
  private String ssid;
  private String apmac;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getBasname() {
    return this.basname;
  }
  public void setBasname(String basname) {
    this.basname = basname;
  }
  public String getUserip() {
    return this.userip;
  }
  public void setUserip(String userip) {
    this.userip = userip;
  }
  public String getUsermac() {
    return this.usermac;
  }
  public void setUsermac(String usermac) {
    this.usermac = usermac;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getBasip() {
    return this.basip;
  }
  public void setBasip(String basip) {
    this.basip = basip;
  }
  public String getSsid() {
    return this.ssid;
  }
  public void setSsid(String ssid) {
    this.ssid = ssid;
  }
  public String getApmac() {
    return this.apmac;
  }
  public void setApmac(String apmac) {
    this.apmac = apmac;
  }
  public String toString() {
    return "Portalurlparameter [id=" + this.id + ",basname=" + this.basname + ",userip=" + this.userip + ",usermac=" + this.usermac + ",url=" + this.url + ",basip=" + this.basip + ",ssid=" + this.ssid + ",apmac=" + this.apmac + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalurlparameter
 * JD-Core Version:    0.6.2
 */