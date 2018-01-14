package com.leeson.core.bean;

import java.io.Serializable;

public class Config
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Integer portalPort;
  private Integer isDebug;
  private Integer radiusOn;
  private Long checkTime;
  private Integer accountAdd;
  private Integer shutdownKick;
  private String domain;
  private Long countShow;
  private Long countAuth;
  private Integer useDomain;
  private Integer authPort;
  private Integer acctPort;
  private Integer smsAuthList;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Integer getPortalPort() {
    return this.portalPort;
  }
  public void setPortalPort(Integer portalPort) {
    this.portalPort = portalPort;
  }
  public Integer getIsDebug() {
    return this.isDebug;
  }
  public void setIsDebug(Integer isDebug) {
    this.isDebug = isDebug;
  }
  public Integer getRadiusOn() {
    return this.radiusOn;
  }
  public void setRadiusOn(Integer radiusOn) {
    this.radiusOn = radiusOn;
  }
  public Long getCheckTime() {
    return this.checkTime;
  }
  public void setCheckTime(Long checkTime) {
    this.checkTime = checkTime;
  }
  public Integer getAccountAdd() {
    return this.accountAdd;
  }
  public void setAccountAdd(Integer accountAdd) {
    this.accountAdd = accountAdd;
  }
  public Integer getShutdownKick() {
    return this.shutdownKick;
  }
  public void setShutdownKick(Integer shutdownKick) {
    this.shutdownKick = shutdownKick;
  }
  public String getDomain() {
    return this.domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
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
  public Integer getUseDomain() {
    return this.useDomain;
  }
  public void setUseDomain(Integer useDomain) {
    this.useDomain = useDomain;
  }
  public Integer getAuthPort() {
    return this.authPort;
  }
  public void setAuthPort(Integer authPort) {
    this.authPort = authPort;
  }
  public Integer getAcctPort() {
    return this.acctPort;
  }
  public void setAcctPort(Integer acctPort) {
    this.acctPort = acctPort;
  }
  public Integer getSmsAuthList() {
    return this.smsAuthList;
  }
  public void setSmsAuthList(Integer smsAuthList) {
    this.smsAuthList = smsAuthList;
  }
  public String toString() {
    return "Config [id=" + this.id + ",portalPort=" + this.portalPort + ",isDebug=" + this.isDebug + ",radiusOn=" + this.radiusOn + ",checkTime=" + this.checkTime + ",accountAdd=" + this.accountAdd + ",shutdownKick=" + this.shutdownKick + ",domain=" + this.domain + ",countShow=" + this.countShow + ",countAuth=" + this.countAuth + ",useDomain=" + this.useDomain + ",authPort=" + this.authPort + ",acctPort=" + this.acctPort + ",smsAuthList=" + this.smsAuthList + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Config
 * JD-Core Version:    0.6.2
 */