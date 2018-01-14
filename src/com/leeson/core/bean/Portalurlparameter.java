/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalurlparameter
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String basname;
/*    */   private String userip;
/*    */   private String usermac;
/*    */   private String url;
/*    */   private String basip;
/*    */   private String ssid;
/*    */   private String apmac;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 26 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 29 */     this.id = id;
/*    */   }
/*    */   public String getBasname() {
/* 32 */     return this.basname;
/*    */   }
/*    */   public void setBasname(String basname) {
/* 35 */     this.basname = basname;
/*    */   }
/*    */   public String getUserip() {
/* 38 */     return this.userip;
/*    */   }
/*    */   public void setUserip(String userip) {
/* 41 */     this.userip = userip;
/*    */   }
/*    */   public String getUsermac() {
/* 44 */     return this.usermac;
/*    */   }
/*    */   public void setUsermac(String usermac) {
/* 47 */     this.usermac = usermac;
/*    */   }
/*    */   public String getUrl() {
/* 50 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 53 */     this.url = url;
/*    */   }
/*    */   public String getBasip() {
/* 56 */     return this.basip;
/*    */   }
/*    */   public void setBasip(String basip) {
/* 59 */     this.basip = basip;
/*    */   }
/*    */   public String getSsid() {
/* 62 */     return this.ssid;
/*    */   }
/*    */   public void setSsid(String ssid) {
/* 65 */     this.ssid = ssid;
/*    */   }
/*    */   public String getApmac() {
/* 68 */     return this.apmac;
/*    */   }
/*    */   public void setApmac(String apmac) {
/* 71 */     this.apmac = apmac;
/*    */   }
/*    */   public String toString() {
/* 74 */     return "Portalurlparameter [id=" + this.id + ",basname=" + this.basname + ",userip=" + this.userip + ",usermac=" + this.usermac + ",url=" + this.url + ",basip=" + this.basip + ",ssid=" + this.ssid + ",apmac=" + this.apmac + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalurlparameter
 * JD-Core Version:    0.6.2
 */