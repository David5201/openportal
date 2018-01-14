package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class OnlineInfo
  implements Serializable
{
  private static final long serialVersionUID = 67654611140886748L;
  Integer id;
  String ip;
  String loginName;
  Date startDate;
  Long time;
  String state;
  String mac;
  String inS;
  String outS;
  String octetsS;
  String type;
  String basname;
  String ssid;
  String apmac;
  String auto;
  String agent;

  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getLoginName() {
    return this.loginName;
  }
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Long getTime() {
    return this.time;
  }
  public void setTime(Long time) {
    this.time = time;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getMac() {
    return this.mac;
  }
  public void setMac(String mac) {
    this.mac = mac;
  }
  public String getInS() {
    return this.inS;
  }
  public void setInS(String inS) {
    this.inS = inS;
  }
  public String getOutS() {
    return this.outS;
  }
  public void setOutS(String outS) {
    this.outS = outS;
  }
  public String getOctetsS() {
    return this.octetsS;
  }
  public void setOctetsS(String octetsS) {
    this.octetsS = octetsS;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getBasname() {
    return this.basname;
  }
  public void setBasname(String basname) {
    this.basname = basname;
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
  public String getAuto() {
    return this.auto;
  }
  public void setAuto(String auto) {
    this.auto = auto;
  }
  public String getAgent() {
    return this.agent;
  }
  public void setAgent(String agent) {
    this.agent = agent;
  }

  public String toString() {
    return "OnlineInfo [id=" + this.id + ", ip=" + this.ip + ", loginName=" + 
      this.loginName + ", startDate=" + this.startDate + ", time=" + this.time + 
      ", state=" + this.state + ", mac=" + this.mac + ", inS=" + this.inS + 
      ", outS=" + this.outS + ", octetsS=" + this.octetsS + ", type=" + 
      this.type + ", basname=" + this.basname + ", ssid=" + this.ssid + 
      ", apmac=" + this.apmac + ", auto=" + this.auto + ", agent=" + 
      this.agent + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.OnlineInfo
 * JD-Core Version:    0.6.2
 */