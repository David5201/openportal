package com.leeson.core.bean;

import java.io.Serializable;

public class RadiusOnlineInfo
  implements Serializable
{
  private static final long serialVersionUID = 1466788889133138606L;
  Integer id;
  String nasIP;
  String ip;
  String userIP;
  String callingStationId;
  String name;
  String sharedSecret;
  String sessionTime;
  String octets;
  String clientType;
  String startDate;
  String costTime;
  String inS;
  String outS;
  String costOctets;
  String updateDate;
  String acctSessionId;
  String state;
  String nasname;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNasIP() {
    return this.nasIP;
  }

  public void setNasIP(String nasIP) {
    this.nasIP = nasIP;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUserIP() {
    return this.userIP;
  }

  public void setUserIP(String userIP) {
    this.userIP = userIP;
  }

  public String getCallingStationId() {
    return this.callingStationId;
  }

  public void setCallingStationId(String callingStationId) {
    this.callingStationId = callingStationId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSharedSecret() {
    return this.sharedSecret;
  }

  public void setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
  }

  public String getSessionTime() {
    return this.sessionTime;
  }

  public void setSessionTime(String sessionTime) {
    this.sessionTime = sessionTime;
  }

  public String getOctets() {
    return this.octets;
  }

  public void setOctets(String octets) {
    this.octets = octets;
  }

  public String getClientType() {
    return this.clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getCostTime() {
    return this.costTime;
  }

  public void setCostTime(String costTime) {
    this.costTime = costTime;
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

  public String getCostOctets() {
    return this.costOctets;
  }

  public void setCostOctets(String costOctets) {
    this.costOctets = costOctets;
  }

  public String getUpdateDate() {
    return this.updateDate;
  }

  public void setUpdateDate(String updateDate) {
    this.updateDate = updateDate;
  }

  public String getAcctSessionId() {
    return this.acctSessionId;
  }

  public void setAcctSessionId(String acctSessionId) {
    this.acctSessionId = acctSessionId;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getNasname() {
    return this.nasname;
  }

  public void setNasname(String nasname) {
    this.nasname = nasname;
  }

  public String toString()
  {
    return "RadiusOnlineInfo [id=" + this.id + ", nasIP=" + this.nasIP + ", ip=" + this.ip + 
      ", userIP=" + this.userIP + ", callingStationId=" + 
      this.callingStationId + ", name=" + this.name + ", sharedSecret=" + 
      this.sharedSecret + ", sessionTime=" + this.sessionTime + ", octets=" + 
      this.octets + ", clientType=" + this.clientType + ", startDate=" + 
      this.startDate + ", costTime=" + this.costTime + ", inS=" + this.inS + 
      ", outS=" + this.outS + ", costOctets=" + this.costOctets + 
      ", updateDate=" + this.updateDate + ", acctSessionId=" + 
      this.acctSessionId + ", state=" + this.state + ", nasName=" + this.nasname + 
      "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.RadiusOnlineInfo
 * JD-Core Version:    0.6.2
 */