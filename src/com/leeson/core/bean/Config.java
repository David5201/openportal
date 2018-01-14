/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Config
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private Integer portalPort;
/*     */   private Integer isDebug;
/*     */   private Integer radiusOn;
/*     */   private Long checkTime;
/*     */   private Integer accountAdd;
/*     */   private Integer shutdownKick;
/*     */   private String domain;
/*     */   private Long countShow;
/*     */   private Long countAuth;
/*     */   private Integer useDomain;
/*     */   private Integer authPort;
/*     */   private Integer acctPort;
/*     */   private Integer smsAuthList;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  32 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  35 */     this.id = id;
/*     */   }
/*     */   public Integer getPortalPort() {
/*  38 */     return this.portalPort;
/*     */   }
/*     */   public void setPortalPort(Integer portalPort) {
/*  41 */     this.portalPort = portalPort;
/*     */   }
/*     */   public Integer getIsDebug() {
/*  44 */     return this.isDebug;
/*     */   }
/*     */   public void setIsDebug(Integer isDebug) {
/*  47 */     this.isDebug = isDebug;
/*     */   }
/*     */   public Integer getRadiusOn() {
/*  50 */     return this.radiusOn;
/*     */   }
/*     */   public void setRadiusOn(Integer radiusOn) {
/*  53 */     this.radiusOn = radiusOn;
/*     */   }
/*     */   public Long getCheckTime() {
/*  56 */     return this.checkTime;
/*     */   }
/*     */   public void setCheckTime(Long checkTime) {
/*  59 */     this.checkTime = checkTime;
/*     */   }
/*     */   public Integer getAccountAdd() {
/*  62 */     return this.accountAdd;
/*     */   }
/*     */   public void setAccountAdd(Integer accountAdd) {
/*  65 */     this.accountAdd = accountAdd;
/*     */   }
/*     */   public Integer getShutdownKick() {
/*  68 */     return this.shutdownKick;
/*     */   }
/*     */   public void setShutdownKick(Integer shutdownKick) {
/*  71 */     this.shutdownKick = shutdownKick;
/*     */   }
/*     */   public String getDomain() {
/*  74 */     return this.domain;
/*     */   }
/*     */   public void setDomain(String domain) {
/*  77 */     this.domain = domain;
/*     */   }
/*     */   public Long getCountShow() {
/*  80 */     return this.countShow;
/*     */   }
/*     */   public void setCountShow(Long countShow) {
/*  83 */     this.countShow = countShow;
/*     */   }
/*     */   public Long getCountAuth() {
/*  86 */     return this.countAuth;
/*     */   }
/*     */   public void setCountAuth(Long countAuth) {
/*  89 */     this.countAuth = countAuth;
/*     */   }
/*     */   public Integer getUseDomain() {
/*  92 */     return this.useDomain;
/*     */   }
/*     */   public void setUseDomain(Integer useDomain) {
/*  95 */     this.useDomain = useDomain;
/*     */   }
/*     */   public Integer getAuthPort() {
/*  98 */     return this.authPort;
/*     */   }
/*     */   public void setAuthPort(Integer authPort) {
/* 101 */     this.authPort = authPort;
/*     */   }
/*     */   public Integer getAcctPort() {
/* 104 */     return this.acctPort;
/*     */   }
/*     */   public void setAcctPort(Integer acctPort) {
/* 107 */     this.acctPort = acctPort;
/*     */   }
/*     */   public Integer getSmsAuthList() {
/* 110 */     return this.smsAuthList;
/*     */   }
/*     */   public void setSmsAuthList(Integer smsAuthList) {
/* 113 */     this.smsAuthList = smsAuthList;
/*     */   }
/*     */   public String toString() {
/* 116 */     return "Config [id=" + this.id + ",portalPort=" + this.portalPort + ",isDebug=" + this.isDebug + ",radiusOn=" + this.radiusOn + ",checkTime=" + this.checkTime + ",accountAdd=" + this.accountAdd + ",shutdownKick=" + this.shutdownKick + ",domain=" + this.domain + ",countShow=" + this.countShow + ",countAuth=" + this.countAuth + ",useDomain=" + this.useDomain + ",authPort=" + this.authPort + ",acctPort=" + this.acctPort + ",smsAuthList=" + this.smsAuthList + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Config
 * JD-Core Version:    0.6.2
 */