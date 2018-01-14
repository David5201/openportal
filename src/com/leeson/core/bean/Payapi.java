package com.leeson.core.bean;

import java.io.Serializable;

public class Payapi
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String alipayPartner;
  private String alipayKey;
  private String weixinAppId;
  private String weixinAppSecret;
  private String weixinPartner;
  private String weixinKey;
  private String weixinPartnerExd;
  private Integer alipay;
  private Integer weixin;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getAlipayPartner() {
    return this.alipayPartner;
  }
  public void setAlipayPartner(String alipayPartner) {
    this.alipayPartner = alipayPartner;
  }
  public String getAlipayKey() {
    return this.alipayKey;
  }
  public void setAlipayKey(String alipayKey) {
    this.alipayKey = alipayKey;
  }
  public String getWeixinAppId() {
    return this.weixinAppId;
  }
  public void setWeixinAppId(String weixinAppId) {
    this.weixinAppId = weixinAppId;
  }
  public String getWeixinAppSecret() {
    return this.weixinAppSecret;
  }
  public void setWeixinAppSecret(String weixinAppSecret) {
    this.weixinAppSecret = weixinAppSecret;
  }
  public String getWeixinPartner() {
    return this.weixinPartner;
  }
  public void setWeixinPartner(String weixinPartner) {
    this.weixinPartner = weixinPartner;
  }
  public String getWeixinKey() {
    return this.weixinKey;
  }
  public void setWeixinKey(String weixinKey) {
    this.weixinKey = weixinKey;
  }
  public String getWeixinPartnerExd() {
    return this.weixinPartnerExd;
  }
  public void setWeixinPartnerExd(String weixinPartnerExd) {
    this.weixinPartnerExd = weixinPartnerExd;
  }
  public Integer getAlipay() {
    return this.alipay;
  }
  public void setAlipay(Integer alipay) {
    this.alipay = alipay;
  }
  public Integer getWeixin() {
    return this.weixin;
  }
  public void setWeixin(Integer weixin) {
    this.weixin = weixin;
  }
  public String toString() {
    return "Payapi [id=" + this.id + ",alipayPartner=" + this.alipayPartner + ",alipayKey=" + this.alipayKey + ",weixinAppId=" + this.weixinAppId + ",weixinAppSecret=" + this.weixinAppSecret + ",weixinPartner=" + this.weixinPartner + ",weixinKey=" + this.weixinKey + ",weixinPartnerExd=" + this.weixinPartnerExd + ",alipay=" + this.alipay + ",weixin=" + this.weixin + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Payapi
 * JD-Core Version:    0.6.2
 */