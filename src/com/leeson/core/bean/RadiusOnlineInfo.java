/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RadiusOnlineInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1466788889133138606L;
/*     */   Integer id;
/*     */   String nasIP;
/*     */   String ip;
/*     */   String userIP;
/*     */   String callingStationId;
/*     */   String name;
/*     */   String sharedSecret;
/*     */   String sessionTime;
/*     */   String octets;
/*     */   String clientType;
/*     */   String startDate;
/*     */   String costTime;
/*     */   String inS;
/*     */   String outS;
/*     */   String costOctets;
/*     */   String updateDate;
/*     */   String acctSessionId;
/*     */   String state;
/*     */   String nasname;
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  52 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  56 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getNasIP() {
/*  60 */     return this.nasIP;
/*     */   }
/*     */ 
/*     */   public void setNasIP(String nasIP) {
/*  64 */     this.nasIP = nasIP;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  68 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/*  72 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public String getUserIP() {
/*  76 */     return this.userIP;
/*     */   }
/*     */ 
/*     */   public void setUserIP(String userIP) {
/*  80 */     this.userIP = userIP;
/*     */   }
/*     */ 
/*     */   public String getCallingStationId() {
/*  84 */     return this.callingStationId;
/*     */   }
/*     */ 
/*     */   public void setCallingStationId(String callingStationId) {
/*  88 */     this.callingStationId = callingStationId;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  92 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  96 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getSharedSecret() {
/* 100 */     return this.sharedSecret;
/*     */   }
/*     */ 
/*     */   public void setSharedSecret(String sharedSecret) {
/* 104 */     this.sharedSecret = sharedSecret;
/*     */   }
/*     */ 
/*     */   public String getSessionTime() {
/* 108 */     return this.sessionTime;
/*     */   }
/*     */ 
/*     */   public void setSessionTime(String sessionTime) {
/* 112 */     this.sessionTime = sessionTime;
/*     */   }
/*     */ 
/*     */   public String getOctets() {
/* 116 */     return this.octets;
/*     */   }
/*     */ 
/*     */   public void setOctets(String octets) {
/* 120 */     this.octets = octets;
/*     */   }
/*     */ 
/*     */   public String getClientType() {
/* 124 */     return this.clientType;
/*     */   }
/*     */ 
/*     */   public void setClientType(String clientType) {
/* 128 */     this.clientType = clientType;
/*     */   }
/*     */ 
/*     */   public String getStartDate() {
/* 132 */     return this.startDate;
/*     */   }
/*     */ 
/*     */   public void setStartDate(String startDate) {
/* 136 */     this.startDate = startDate;
/*     */   }
/*     */ 
/*     */   public String getCostTime() {
/* 140 */     return this.costTime;
/*     */   }
/*     */ 
/*     */   public void setCostTime(String costTime) {
/* 144 */     this.costTime = costTime;
/*     */   }
/*     */ 
/*     */   public String getInS() {
/* 148 */     return this.inS;
/*     */   }
/*     */ 
/*     */   public void setInS(String inS) {
/* 152 */     this.inS = inS;
/*     */   }
/*     */ 
/*     */   public String getOutS() {
/* 156 */     return this.outS;
/*     */   }
/*     */ 
/*     */   public void setOutS(String outS) {
/* 160 */     this.outS = outS;
/*     */   }
/*     */ 
/*     */   public String getCostOctets() {
/* 164 */     return this.costOctets;
/*     */   }
/*     */ 
/*     */   public void setCostOctets(String costOctets) {
/* 168 */     this.costOctets = costOctets;
/*     */   }
/*     */ 
/*     */   public String getUpdateDate() {
/* 172 */     return this.updateDate;
/*     */   }
/*     */ 
/*     */   public void setUpdateDate(String updateDate) {
/* 176 */     this.updateDate = updateDate;
/*     */   }
/*     */ 
/*     */   public String getAcctSessionId() {
/* 180 */     return this.acctSessionId;
/*     */   }
/*     */ 
/*     */   public void setAcctSessionId(String acctSessionId) {
/* 184 */     this.acctSessionId = acctSessionId;
/*     */   }
/*     */ 
/*     */   public String getState() {
/* 188 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(String state) {
/* 192 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public String getNasname() {
/* 196 */     return this.nasname;
/*     */   }
/*     */ 
/*     */   public void setNasname(String nasname) {
/* 200 */     this.nasname = nasname;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 205 */     return "RadiusOnlineInfo [id=" + this.id + ", nasIP=" + this.nasIP + ", ip=" + this.ip + 
/* 206 */       ", userIP=" + this.userIP + ", callingStationId=" + 
/* 207 */       this.callingStationId + ", name=" + this.name + ", sharedSecret=" + 
/* 208 */       this.sharedSecret + ", sessionTime=" + this.sessionTime + ", octets=" + 
/* 209 */       this.octets + ", clientType=" + this.clientType + ", startDate=" + 
/* 210 */       this.startDate + ", costTime=" + this.costTime + ", inS=" + this.inS + 
/* 211 */       ", outS=" + this.outS + ", costOctets=" + this.costOctets + 
/* 212 */       ", updateDate=" + this.updateDate + ", acctSessionId=" + 
/* 213 */       this.acctSessionId + ", state=" + this.state + ", nasName=" + this.nasname + 
/* 214 */       "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.RadiusOnlineInfo
 * JD-Core Version:    0.6.2
 */