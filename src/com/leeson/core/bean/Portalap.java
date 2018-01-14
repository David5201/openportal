/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Portalap
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private Long basid;
/*     */   private String ip;
/*     */   private String mac;
/*     */   private String address;
/*     */   private String basip;
/*     */   private String x;
/*     */   private String y;
/*     */   private String des;
/*     */   private String name;
/*     */   private Long web;
/*     */   private Long count;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  30 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  33 */     this.id = id;
/*     */   }
/*     */   public Long getBasid() {
/*  36 */     return this.basid;
/*     */   }
/*     */   public void setBasid(Long basid) {
/*  39 */     this.basid = basid;
/*     */   }
/*     */   public String getIp() {
/*  42 */     return this.ip;
/*     */   }
/*     */   public void setIp(String ip) {
/*  45 */     this.ip = ip;
/*     */   }
/*     */   public String getMac() {
/*  48 */     return this.mac;
/*     */   }
/*     */   public void setMac(String mac) {
/*  51 */     this.mac = mac;
/*     */   }
/*     */   public String getAddress() {
/*  54 */     return this.address;
/*     */   }
/*     */   public void setAddress(String address) {
/*  57 */     this.address = address;
/*     */   }
/*     */   public String getBasip() {
/*  60 */     return this.basip;
/*     */   }
/*     */   public void setBasip(String basip) {
/*  63 */     this.basip = basip;
/*     */   }
/*     */   public String getX() {
/*  66 */     return this.x;
/*     */   }
/*     */   public void setX(String x) {
/*  69 */     this.x = x;
/*     */   }
/*     */   public String getY() {
/*  72 */     return this.y;
/*     */   }
/*     */   public void setY(String y) {
/*  75 */     this.y = y;
/*     */   }
/*     */   public String getDes() {
/*  78 */     return this.des;
/*     */   }
/*     */   public void setDes(String des) {
/*  81 */     this.des = des;
/*     */   }
/*     */   public String getName() {
/*  84 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  87 */     this.name = name;
/*     */   }
/*     */   public Long getWeb() {
/*  90 */     return this.web;
/*     */   }
/*     */   public void setWeb(Long web) {
/*  93 */     this.web = web;
/*     */   }
/*     */   public Long getCount() {
/*  96 */     return this.count;
/*     */   }
/*     */   public void setCount(Long count) {
/*  99 */     this.count = count;
/*     */   }
/*     */   public String toString() {
/* 102 */     return "Portalap [id=" + this.id + ",basid=" + this.basid + ",ip=" + this.ip + ",mac=" + this.mac + ",address=" + this.address + ",basip=" + this.basip + ",x=" + this.x + ",y=" + this.y + ",des=" + this.des + ",name=" + this.name + ",web=" + this.web + ",count=" + this.count + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalap
 * JD-Core Version:    0.6.2
 */