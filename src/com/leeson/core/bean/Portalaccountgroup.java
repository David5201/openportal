package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portalaccountgroup
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Date date;
  private Long time;
  private Long octets;
  private String state;
  private Long speed;
  private Integer maclimit;
  private Integer maclimitcount;
  private Integer autologin;
  private Integer autoKick;
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
  private String isp;
  private String radius;
  private Date creatDate;
  private Integer unlockMac;
  private Integer clearHaveAll;
  private Integer clearHaveLimit;
  private Long octetsLimit;
  private Long timeLimit;

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
  public Date getDate() {
    return this.date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public Long getTime() {
    return this.time;
  }
  public void setTime(Long time) {
    this.time = time;
  }
  public Long getOctets() {
    return this.octets;
  }
  public void setOctets(Long octets) {
    this.octets = octets;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public Long getSpeed() {
    return this.speed;
  }
  public void setSpeed(Long speed) {
    this.speed = speed;
  }
  public Integer getMaclimit() {
    return this.maclimit;
  }
  public void setMaclimit(Integer maclimit) {
    this.maclimit = maclimit;
  }
  public Integer getMaclimitcount() {
    return this.maclimitcount;
  }
  public void setMaclimitcount(Integer maclimitcount) {
    this.maclimitcount = maclimitcount;
  }
  public Integer getAutologin() {
    return this.autologin;
  }
  public void setAutologin(Integer autologin) {
    this.autologin = autologin;
  }
  public Integer getAutoKick() {
    return this.autoKick;
  }
  public void setAutoKick(Integer autoKick) {
    this.autoKick = autoKick;
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
  public String getIsp() {
    return this.isp;
  }
  public void setIsp(String isp) {
    this.isp = isp;
  }
  public String getRadius() {
    return this.radius;
  }
  public void setRadius(String radius) {
    this.radius = radius;
  }
  public Date getCreatDate() {
    return this.creatDate;
  }
  public void setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
  }
  public Integer getUnlockMac() {
    return this.unlockMac;
  }
  public void setUnlockMac(Integer unlockMac) {
    this.unlockMac = unlockMac;
  }
  public Integer getClearHaveAll() {
    return this.clearHaveAll;
  }
  public void setClearHaveAll(Integer clearHaveAll) {
    this.clearHaveAll = clearHaveAll;
  }
  public Integer getClearHaveLimit() {
    return this.clearHaveLimit;
  }
  public void setClearHaveLimit(Integer clearHaveLimit) {
    this.clearHaveLimit = clearHaveLimit;
  }
  public Long getOctetsLimit() {
    return this.octetsLimit;
  }
  public void setOctetsLimit(Long octetsLimit) {
    this.octetsLimit = octetsLimit;
  }
  public Long getTimeLimit() {
    return this.timeLimit;
  }
  public void setTimeLimit(Long timeLimit) {
    this.timeLimit = timeLimit;
  }
  public String toString() {
    return "Portalaccountgroup [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",date=" + this.date + ",time=" + this.time + ",octets=" + this.octets + ",state=" + this.state + ",speed=" + this.speed + ",maclimit=" + this.maclimit + ",maclimitcount=" + this.maclimitcount + ",autologin=" + this.autologin + ",autoKick=" + this.autoKick + ",ex1=" + this.ex1 + ",ex2=" + this.ex2 + ",ex3=" + this.ex3 + ",ex4=" + this.ex4 + ",ex5=" + this.ex5 + ",ex6=" + this.ex6 + ",ex7=" + this.ex7 + ",ex8=" + this.ex8 + ",ex9=" + this.ex9 + ",ex10=" + this.ex10 + ",isp=" + this.isp + ",radius=" + this.radius + ",creatDate=" + this.creatDate + ",unlockMac=" + this.unlockMac + ",clearHaveAll=" + this.clearHaveAll + ",clearHaveLimit=" + this.clearHaveLimit + ",octetsLimit=" + this.octetsLimit + ",timeLimit=" + this.timeLimit + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalaccountgroup
 * JD-Core Version:    0.6.2
 */