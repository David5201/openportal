/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalweixinwifi
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String appId;
/*    */   private String shopId;
/*    */   private String ssid;
/*    */   private String domain;
/*    */   private String bssid;
/*    */   private String secretKey;
/*    */   private Long outTime;
/*    */   private String basip;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 27 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 30 */     this.id = id;
/*    */   }
/*    */   public String getAppId() {
/* 33 */     return this.appId;
/*    */   }
/*    */   public void setAppId(String appId) {
/* 36 */     this.appId = appId;
/*    */   }
/*    */   public String getShopId() {
/* 39 */     return this.shopId;
/*    */   }
/*    */   public void setShopId(String shopId) {
/* 42 */     this.shopId = shopId;
/*    */   }
/*    */   public String getSsid() {
/* 45 */     return this.ssid;
/*    */   }
/*    */   public void setSsid(String ssid) {
/* 48 */     this.ssid = ssid;
/*    */   }
/*    */   public String getDomain() {
/* 51 */     return this.domain;
/*    */   }
/*    */   public void setDomain(String domain) {
/* 54 */     this.domain = domain;
/*    */   }
/*    */   public String getBssid() {
/* 57 */     return this.bssid;
/*    */   }
/*    */   public void setBssid(String bssid) {
/* 60 */     this.bssid = bssid;
/*    */   }
/*    */   public String getSecretKey() {
/* 63 */     return this.secretKey;
/*    */   }
/*    */   public void setSecretKey(String secretKey) {
/* 66 */     this.secretKey = secretKey;
/*    */   }
/*    */   public Long getOutTime() {
/* 69 */     return this.outTime;
/*    */   }
/*    */   public void setOutTime(Long outTime) {
/* 72 */     this.outTime = outTime;
/*    */   }
/*    */   public String getBasip() {
/* 75 */     return this.basip;
/*    */   }
/*    */   public void setBasip(String basip) {
/* 78 */     this.basip = basip;
/*    */   }
/*    */   public String toString() {
/* 81 */     return "Portalweixinwifi [id=" + this.id + ",appId=" + this.appId + ",shopId=" + this.shopId + ",ssid=" + this.ssid + ",domain=" + this.domain + ",bssid=" + this.bssid + ",secretKey=" + this.secretKey + ",outTime=" + this.outTime + ",basip=" + this.basip + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalweixinwifi
 * JD-Core Version:    0.6.2
 */