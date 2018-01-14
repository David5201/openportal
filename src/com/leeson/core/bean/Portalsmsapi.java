/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Portalsmsapi
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String url;
/*     */   private Long count;
/*     */   private String state;
/*     */   private String type;
/*     */   private String more;
/*     */   private Integer time;
/*     */   private String text;
/*     */   private String appkey;
/*     */   private String appsecret;
/*     */   private String smssign;
/*     */   private String smstemplate;
/*     */   private String company;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  32 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  35 */     this.id = id;
/*     */   }
/*     */   public String getName() {
/*  38 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  41 */     this.name = name;
/*     */   }
/*     */   public String getUrl() {
/*  44 */     return this.url;
/*     */   }
/*     */   public void setUrl(String url) {
/*  47 */     this.url = url;
/*     */   }
/*     */   public Long getCount() {
/*  50 */     return this.count;
/*     */   }
/*     */   public void setCount(Long count) {
/*  53 */     this.count = count;
/*     */   }
/*     */   public String getState() {
/*  56 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  59 */     this.state = state;
/*     */   }
/*     */   public String getType() {
/*  62 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/*  65 */     this.type = type;
/*     */   }
/*     */   public String getMore() {
/*  68 */     return this.more;
/*     */   }
/*     */   public void setMore(String more) {
/*  71 */     this.more = more;
/*     */   }
/*     */   public Integer getTime() {
/*  74 */     return this.time;
/*     */   }
/*     */   public void setTime(Integer time) {
/*  77 */     this.time = time;
/*     */   }
/*     */   public String getText() {
/*  80 */     return this.text;
/*     */   }
/*     */   public void setText(String text) {
/*  83 */     this.text = text;
/*     */   }
/*     */   public String getAppkey() {
/*  86 */     return this.appkey;
/*     */   }
/*     */   public void setAppkey(String appkey) {
/*  89 */     this.appkey = appkey;
/*     */   }
/*     */   public String getAppsecret() {
/*  92 */     return this.appsecret;
/*     */   }
/*     */   public void setAppsecret(String appsecret) {
/*  95 */     this.appsecret = appsecret;
/*     */   }
/*     */   public String getSmssign() {
/*  98 */     return this.smssign;
/*     */   }
/*     */   public void setSmssign(String smssign) {
/* 101 */     this.smssign = smssign;
/*     */   }
/*     */   public String getSmstemplate() {
/* 104 */     return this.smstemplate;
/*     */   }
/*     */   public void setSmstemplate(String smstemplate) {
/* 107 */     this.smstemplate = smstemplate;
/*     */   }
/*     */   public String getCompany() {
/* 110 */     return this.company;
/*     */   }
/*     */   public void setCompany(String company) {
/* 113 */     this.company = company;
/*     */   }
/*     */   public String toString() {
/* 116 */     return "Portalsmsapi [id=" + this.id + ",name=" + this.name + ",url=" + this.url + ",count=" + this.count + ",state=" + this.state + ",type=" + this.type + ",more=" + this.more + ",time=" + this.time + ",text=" + this.text + ",appkey=" + this.appkey + ",appsecret=" + this.appsecret + ",smssign=" + this.smssign + ",smstemplate=" + this.smstemplate + ",company=" + this.company + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalsmsapi
 * JD-Core Version:    0.6.2
 */