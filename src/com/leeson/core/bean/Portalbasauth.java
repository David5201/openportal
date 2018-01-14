/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalbasauth
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private Long basid;
/*    */   private Integer type;
/*    */   private String username;
/*    */   private String password;
/*    */   private String basip;
/*    */   private String url;
/*    */   private Long sessiontime;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 26 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 29 */     this.id = id;
/*    */   }
/*    */   public Long getBasid() {
/* 32 */     return this.basid;
/*    */   }
/*    */   public void setBasid(Long basid) {
/* 35 */     this.basid = basid;
/*    */   }
/*    */   public Integer getType() {
/* 38 */     return this.type;
/*    */   }
/*    */   public void setType(Integer type) {
/* 41 */     this.type = type;
/*    */   }
/*    */   public String getUsername() {
/* 44 */     return this.username;
/*    */   }
/*    */   public void setUsername(String username) {
/* 47 */     this.username = username;
/*    */   }
/*    */   public String getPassword() {
/* 50 */     return this.password;
/*    */   }
/*    */   public void setPassword(String password) {
/* 53 */     this.password = password;
/*    */   }
/*    */   public String getBasip() {
/* 56 */     return this.basip;
/*    */   }
/*    */   public void setBasip(String basip) {
/* 59 */     this.basip = basip;
/*    */   }
/*    */   public String getUrl() {
/* 62 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 65 */     this.url = url;
/*    */   }
/*    */   public Long getSessiontime() {
/* 68 */     return this.sessiontime;
/*    */   }
/*    */   public void setSessiontime(Long sessiontime) {
/* 71 */     this.sessiontime = sessiontime;
/*    */   }
/*    */   public String toString() {
/* 74 */     return "Portalbasauth [id=" + this.id + ",basid=" + this.basid + ",type=" + this.type + ",username=" + this.username + ",password=" + this.password + ",basip=" + this.basip + ",url=" + this.url + ",sessiontime=" + this.sessiontime + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalbasauth
 * JD-Core Version:    0.6.2
 */