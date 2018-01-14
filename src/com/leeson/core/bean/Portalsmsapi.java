package com.leeson.core.bean;

import java.io.Serializable;

public class Portalsmsapi
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String url;
  private Long count;
  private String state;
  private String type;
  private String more;
  private Integer time;
  private String text;
  private String appkey;
  private String appsecret;
  private String smssign;
  private String smstemplate;
  private String company;

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
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public Long getCount() {
    return this.count;
  }
  public void setCount(Long count) {
    this.count = count;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getMore() {
    return this.more;
  }
  public void setMore(String more) {
    this.more = more;
  }
  public Integer getTime() {
    return this.time;
  }
  public void setTime(Integer time) {
    this.time = time;
  }
  public String getText() {
    return this.text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getAppkey() {
    return this.appkey;
  }
  public void setAppkey(String appkey) {
    this.appkey = appkey;
  }
  public String getAppsecret() {
    return this.appsecret;
  }
  public void setAppsecret(String appsecret) {
    this.appsecret = appsecret;
  }
  public String getSmssign() {
    return this.smssign;
  }
  public void setSmssign(String smssign) {
    this.smssign = smssign;
  }
  public String getSmstemplate() {
    return this.smstemplate;
  }
  public void setSmstemplate(String smstemplate) {
    this.smstemplate = smstemplate;
  }
  public String getCompany() {
    return this.company;
  }
  public void setCompany(String company) {
    this.company = company;
  }
  public String toString() {
    return "Portalsmsapi [id=" + this.id + ",name=" + this.name + ",url=" + this.url + ",count=" + this.count + ",state=" + this.state + ",type=" + this.type + ",more=" + this.more + ",time=" + this.time + ",text=" + this.text + ",appkey=" + this.appkey + ",appsecret=" + this.appsecret + ",smssign=" + this.smssign + ",smstemplate=" + this.smstemplate + ",company=" + this.company + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalsmsapi
 * JD-Core Version:    0.6.2
 */