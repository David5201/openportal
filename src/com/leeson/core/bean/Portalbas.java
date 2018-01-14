package com.leeson.core.bean;

import java.io.Serializable;

public class Portalbas
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String bas;
  private String basname;
  private String basIp;
  private String basPort;
  private String portalVer;
  private String authType;
  private String sharedSecret;
  private String basUser;
  private String basPwd;
  private String timeoutSec;
  private String isPortalCheck;
  private String isOut;
  private String authInterface;
  private String isComputer;
  private Long web;
  private String isdebug;
  private Integer lateAuth;
  private Long lateAuthTime;
  private Integer isNtf;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getBas() {
    return this.bas;
  }
  public void setBas(String bas) {
    this.bas = bas;
  }
  public String getBasname() {
    return this.basname;
  }
  public void setBasname(String basname) {
    this.basname = basname;
  }
  public String getBasIp() {
    return this.basIp;
  }
  public void setBasIp(String basIp) {
    this.basIp = basIp;
  }
  public String getBasPort() {
    return this.basPort;
  }
  public void setBasPort(String basPort) {
    this.basPort = basPort;
  }
  public String getPortalVer() {
    return this.portalVer;
  }
  public void setPortalVer(String portalVer) {
    this.portalVer = portalVer;
  }
  public String getAuthType() {
    return this.authType;
  }
  public void setAuthType(String authType) {
    this.authType = authType;
  }
  public String getSharedSecret() {
    return this.sharedSecret;
  }
  public void setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
  }
  public String getBasUser() {
    return this.basUser;
  }
  public void setBasUser(String basUser) {
    this.basUser = basUser;
  }
  public String getBasPwd() {
    return this.basPwd;
  }
  public void setBasPwd(String basPwd) {
    this.basPwd = basPwd;
  }
  public String getTimeoutSec() {
    return this.timeoutSec;
  }
  public void setTimeoutSec(String timeoutSec) {
    this.timeoutSec = timeoutSec;
  }
  public String getIsPortalCheck() {
    return this.isPortalCheck;
  }
  public void setIsPortalCheck(String isPortalCheck) {
    this.isPortalCheck = isPortalCheck;
  }
  public String getIsOut() {
    return this.isOut;
  }
  public void setIsOut(String isOut) {
    this.isOut = isOut;
  }
  public String getAuthInterface() {
    return this.authInterface;
  }
  public void setAuthInterface(String authInterface) {
    this.authInterface = authInterface;
  }
  public String getIsComputer() {
    return this.isComputer;
  }
  public void setIsComputer(String isComputer) {
    this.isComputer = isComputer;
  }
  public Long getWeb() {
    return this.web;
  }
  public void setWeb(Long web) {
    this.web = web;
  }
  public String getIsdebug() {
    return this.isdebug;
  }
  public void setIsdebug(String isdebug) {
    this.isdebug = isdebug;
  }
  public Integer getLateAuth() {
    return this.lateAuth;
  }
  public void setLateAuth(Integer lateAuth) {
    this.lateAuth = lateAuth;
  }
  public Long getLateAuthTime() {
    return this.lateAuthTime;
  }
  public void setLateAuthTime(Long lateAuthTime) {
    this.lateAuthTime = lateAuthTime;
  }
  public Integer getIsNtf() {
    return this.isNtf;
  }
  public void setIsNtf(Integer isNtf) {
    this.isNtf = isNtf;
  }
  public String toString() {
    return "Portalbas [id=" + this.id + ",bas=" + this.bas + ",basname=" + this.basname + ",basIp=" + this.basIp + ",basPort=" + this.basPort + ",portalVer=" + this.portalVer + ",authType=" + this.authType + ",sharedSecret=" + this.sharedSecret + ",basUser=" + this.basUser + ",basPwd=" + this.basPwd + ",timeoutSec=" + this.timeoutSec + ",isPortalCheck=" + this.isPortalCheck + ",isOut=" + this.isOut + ",authInterface=" + this.authInterface + ",isComputer=" + this.isComputer + ",web=" + this.web + ",isdebug=" + this.isdebug + ",lateAuth=" + this.lateAuth + ",lateAuthTime=" + this.lateAuthTime + ",isNtf=" + this.isNtf + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalbas
 * JD-Core Version:    0.6.2
 */