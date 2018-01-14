/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Portalconfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private String bas;
/*     */   private String basname;
/*     */   private String basIp;
/*     */   private String basPort;
/*     */   private String portalVer;
/*     */   private String authType;
/*     */   private String sharedSecret;
/*     */   private String basUser;
/*     */   private String basPwd;
/*     */   private String timeoutSec;
/*     */   private String isPortalCheck;
/*     */   private String isOut;
/*     */   private String authInterface;
/*     */   private String isComputer;
/*     */   private Long web;
/*     */   private String isdebug;
/*     */   private Integer lateAuth;
/*     */   private Long lateAuthTime;
/*     */   private Integer isNtf;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  38 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  41 */     this.id = id;
/*     */   }
/*     */   public String getBas() {
/*  44 */     return this.bas;
/*     */   }
/*     */   public void setBas(String bas) {
/*  47 */     this.bas = bas;
/*     */   }
/*     */   public String getBasname() {
/*  50 */     return this.basname;
/*     */   }
/*     */   public void setBasname(String basname) {
/*  53 */     this.basname = basname;
/*     */   }
/*     */   public String getBasIp() {
/*  56 */     return this.basIp;
/*     */   }
/*     */   public void setBasIp(String basIp) {
/*  59 */     this.basIp = basIp;
/*     */   }
/*     */   public String getBasPort() {
/*  62 */     return this.basPort;
/*     */   }
/*     */   public void setBasPort(String basPort) {
/*  65 */     this.basPort = basPort;
/*     */   }
/*     */   public String getPortalVer() {
/*  68 */     return this.portalVer;
/*     */   }
/*     */   public void setPortalVer(String portalVer) {
/*  71 */     this.portalVer = portalVer;
/*     */   }
/*     */   public String getAuthType() {
/*  74 */     return this.authType;
/*     */   }
/*     */   public void setAuthType(String authType) {
/*  77 */     this.authType = authType;
/*     */   }
/*     */   public String getSharedSecret() {
/*  80 */     return this.sharedSecret;
/*     */   }
/*     */   public void setSharedSecret(String sharedSecret) {
/*  83 */     this.sharedSecret = sharedSecret;
/*     */   }
/*     */   public String getBasUser() {
/*  86 */     return this.basUser;
/*     */   }
/*     */   public void setBasUser(String basUser) {
/*  89 */     this.basUser = basUser;
/*     */   }
/*     */   public String getBasPwd() {
/*  92 */     return this.basPwd;
/*     */   }
/*     */   public void setBasPwd(String basPwd) {
/*  95 */     this.basPwd = basPwd;
/*     */   }
/*     */   public String getTimeoutSec() {
/*  98 */     return this.timeoutSec;
/*     */   }
/*     */   public void setTimeoutSec(String timeoutSec) {
/* 101 */     this.timeoutSec = timeoutSec;
/*     */   }
/*     */   public String getIsPortalCheck() {
/* 104 */     return this.isPortalCheck;
/*     */   }
/*     */   public void setIsPortalCheck(String isPortalCheck) {
/* 107 */     this.isPortalCheck = isPortalCheck;
/*     */   }
/*     */   public String getIsOut() {
/* 110 */     return this.isOut;
/*     */   }
/*     */   public void setIsOut(String isOut) {
/* 113 */     this.isOut = isOut;
/*     */   }
/*     */   public String getAuthInterface() {
/* 116 */     return this.authInterface;
/*     */   }
/*     */   public void setAuthInterface(String authInterface) {
/* 119 */     this.authInterface = authInterface;
/*     */   }
/*     */   public String getIsComputer() {
/* 122 */     return this.isComputer;
/*     */   }
/*     */   public void setIsComputer(String isComputer) {
/* 125 */     this.isComputer = isComputer;
/*     */   }
/*     */   public Long getWeb() {
/* 128 */     return this.web;
/*     */   }
/*     */   public void setWeb(Long web) {
/* 131 */     this.web = web;
/*     */   }
/*     */   public String getIsdebug() {
/* 134 */     return this.isdebug;
/*     */   }
/*     */   public void setIsdebug(String isdebug) {
/* 137 */     this.isdebug = isdebug;
/*     */   }
/*     */   public Integer getLateAuth() {
/* 140 */     return this.lateAuth;
/*     */   }
/*     */   public void setLateAuth(Integer lateAuth) {
/* 143 */     this.lateAuth = lateAuth;
/*     */   }
/*     */   public Long getLateAuthTime() {
/* 146 */     return this.lateAuthTime;
/*     */   }
/*     */   public void setLateAuthTime(Long lateAuthTime) {
/* 149 */     this.lateAuthTime = lateAuthTime;
/*     */   }
/*     */   public Integer getIsNtf() {
/* 152 */     return this.isNtf;
/*     */   }
/*     */   public void setIsNtf(Integer isNtf) {
/* 155 */     this.isNtf = isNtf;
/*     */   }
/*     */   public String toString() {
/* 158 */     return "Portalconfig [id=" + this.id + ",bas=" + this.bas + ",basname=" + this.basname + ",basIp=" + this.basIp + ",basPort=" + this.basPort + ",portalVer=" + this.portalVer + ",authType=" + this.authType + ",sharedSecret=" + this.sharedSecret + ",basUser=" + this.basUser + ",basPwd=" + this.basPwd + ",timeoutSec=" + this.timeoutSec + ",isPortalCheck=" + this.isPortalCheck + ",isOut=" + this.isOut + ",authInterface=" + this.authInterface + ",isComputer=" + this.isComputer + ",web=" + this.web + ",isdebug=" + this.isdebug + ",lateAuth=" + this.lateAuth + ",lateAuthTime=" + this.lateAuthTime + ",isNtf=" + this.isNtf + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalconfig
 * JD-Core Version:    0.6.2
 */