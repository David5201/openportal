/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class OnlineInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 67654611140886748L;
/*     */   Integer id;
/*     */   String ip;
/*     */   String loginName;
/*     */   Date startDate;
/*     */   Long time;
/*     */   String state;
/*     */   String mac;
/*     */   String inS;
/*     */   String outS;
/*     */   String octetsS;
/*     */   String type;
/*     */   String basname;
/*     */   String ssid;
/*     */   String apmac;
/*     */   String auto;
/*     */   String agent;
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  27 */     return this.id;
/*     */   }
/*     */   public void setId(Integer id) {
/*  30 */     this.id = id;
/*     */   }
/*     */   public String getIp() {
/*  33 */     return this.ip;
/*     */   }
/*     */   public void setIp(String ip) {
/*  36 */     this.ip = ip;
/*     */   }
/*     */   public String getLoginName() {
/*  39 */     return this.loginName;
/*     */   }
/*     */   public void setLoginName(String loginName) {
/*  42 */     this.loginName = loginName;
/*     */   }
/*     */   public Date getStartDate() {
/*  45 */     return this.startDate;
/*     */   }
/*     */   public void setStartDate(Date startDate) {
/*  48 */     this.startDate = startDate;
/*     */   }
/*     */   public Long getTime() {
/*  51 */     return this.time;
/*     */   }
/*     */   public void setTime(Long time) {
/*  54 */     this.time = time;
/*     */   }
/*     */   public String getState() {
/*  57 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  60 */     this.state = state;
/*     */   }
/*     */   public String getMac() {
/*  63 */     return this.mac;
/*     */   }
/*     */   public void setMac(String mac) {
/*  66 */     this.mac = mac;
/*     */   }
/*     */   public String getInS() {
/*  69 */     return this.inS;
/*     */   }
/*     */   public void setInS(String inS) {
/*  72 */     this.inS = inS;
/*     */   }
/*     */   public String getOutS() {
/*  75 */     return this.outS;
/*     */   }
/*     */   public void setOutS(String outS) {
/*  78 */     this.outS = outS;
/*     */   }
/*     */   public String getOctetsS() {
/*  81 */     return this.octetsS;
/*     */   }
/*     */   public void setOctetsS(String octetsS) {
/*  84 */     this.octetsS = octetsS;
/*     */   }
/*     */   public String getType() {
/*  87 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/*  90 */     this.type = type;
/*     */   }
/*     */   public String getBasname() {
/*  93 */     return this.basname;
/*     */   }
/*     */   public void setBasname(String basname) {
/*  96 */     this.basname = basname;
/*     */   }
/*     */   public String getSsid() {
/*  99 */     return this.ssid;
/*     */   }
/*     */   public void setSsid(String ssid) {
/* 102 */     this.ssid = ssid;
/*     */   }
/*     */   public String getApmac() {
/* 105 */     return this.apmac;
/*     */   }
/*     */   public void setApmac(String apmac) {
/* 108 */     this.apmac = apmac;
/*     */   }
/*     */   public String getAuto() {
/* 111 */     return this.auto;
/*     */   }
/*     */   public void setAuto(String auto) {
/* 114 */     this.auto = auto;
/*     */   }
/*     */   public String getAgent() {
/* 117 */     return this.agent;
/*     */   }
/*     */   public void setAgent(String agent) {
/* 120 */     this.agent = agent;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 124 */     return "OnlineInfo [id=" + this.id + ", ip=" + this.ip + ", loginName=" + 
/* 125 */       this.loginName + ", startDate=" + this.startDate + ", time=" + this.time + 
/* 126 */       ", state=" + this.state + ", mac=" + this.mac + ", inS=" + this.inS + 
/* 127 */       ", outS=" + this.outS + ", octetsS=" + this.octetsS + ", type=" + 
/* 128 */       this.type + ", basname=" + this.basname + ", ssid=" + this.ssid + 
/* 129 */       ", apmac=" + this.apmac + ", auto=" + this.auto + ", agent=" + 
/* 130 */       this.agent + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.OnlineInfo
 * JD-Core Version:    0.6.2
 */