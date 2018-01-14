package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portallinkrecord
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String ip;
  private String basip;
  private String loginName;
  private String state;
  private Date startDate;
  private Date endDate;
  private Long time;
  private Long ins;
  private Long outs;
  private Long octets;
  private Long uid;
  private Integer userDel;
  private Integer accountDel;
  private String mac;
  private String ex1;
  private String ex2;
  private String ex3;
  private String ex4;
  private String ex5;
  private String ex6;
  private String ex7;
  private String ex8;
  private String ex9;
  private String ex10;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getBasip() {
    return this.basip;
  }
  public void setBasip(String basip) {
    this.basip = basip;
  }
  public String getLoginName() {
    return this.loginName;
  }
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public Long getTime() {
    return this.time;
  }
  public void setTime(Long time) {
    this.time = time;
  }
  public Long getIns() {
    return this.ins;
  }
  public void setIns(Long ins) {
    this.ins = ins;
  }
  public Long getOuts() {
    return this.outs;
  }
  public void setOuts(Long outs) {
    this.outs = outs;
  }
  public Long getOctets() {
    return this.octets;
  }
  public void setOctets(Long octets) {
    this.octets = octets;
  }
  public Long getUid() {
    return this.uid;
  }
  public void setUid(Long uid) {
    this.uid = uid;
  }
  public Integer getUserDel() {
    return this.userDel;
  }
  public void setUserDel(Integer userDel) {
    this.userDel = userDel;
  }
  public Integer getAccountDel() {
    return this.accountDel;
  }
  public void setAccountDel(Integer accountDel) {
    this.accountDel = accountDel;
  }
  public String getMac() {
    return this.mac;
  }
  public void setMac(String mac) {
    this.mac = mac;
  }
  public String getEx1() {
    return this.ex1;
  }
  public void setEx1(String ex1) {
    this.ex1 = ex1;
  }
  public String getEx2() {
    return this.ex2;
  }
  public void setEx2(String ex2) {
    this.ex2 = ex2;
  }
  public String getEx3() {
    return this.ex3;
  }
  public void setEx3(String ex3) {
    this.ex3 = ex3;
  }
  public String getEx4() {
    return this.ex4;
  }
  public void setEx4(String ex4) {
    this.ex4 = ex4;
  }
  public String getEx5() {
    return this.ex5;
  }
  public void setEx5(String ex5) {
    this.ex5 = ex5;
  }
  public String getEx6() {
    return this.ex6;
  }
  public void setEx6(String ex6) {
    this.ex6 = ex6;
  }
  public String getEx7() {
    return this.ex7;
  }
  public void setEx7(String ex7) {
    this.ex7 = ex7;
  }
  public String getEx8() {
    return this.ex8;
  }
  public void setEx8(String ex8) {
    this.ex8 = ex8;
  }
  public String getEx9() {
    return this.ex9;
  }
  public void setEx9(String ex9) {
    this.ex9 = ex9;
  }
  public String getEx10() {
    return this.ex10;
  }
  public void setEx10(String ex10) {
    this.ex10 = ex10;
  }
  public String toString() {
    return "Portallinkrecord [id=" + this.id + ",ip=" + this.ip + ",basip=" + this.basip + ",loginName=" + this.loginName + ",state=" + this.state + ",startDate=" + this.startDate + ",endDate=" + this.endDate + ",time=" + this.time + ",ins=" + this.ins + ",outs=" + this.outs + ",octets=" + this.octets + ",uid=" + this.uid + ",userDel=" + this.userDel + ",accountDel=" + this.accountDel + ",mac=" + this.mac + ",ex1=" + this.ex1 + ",ex2=" + this.ex2 + ",ex3=" + this.ex3 + ",ex4=" + this.ex4 + ",ex5=" + this.ex5 + ",ex6=" + this.ex6 + ",ex7=" + this.ex7 + ",ex8=" + this.ex8 + ",ex9=" + this.ex9 + ",ex10=" + this.ex10 + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portallinkrecord
 * JD-Core Version:    0.6.2
 */