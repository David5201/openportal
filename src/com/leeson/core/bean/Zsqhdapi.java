/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Zsqhdapi
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String url;
/*    */   private String publicurl;
/*    */   private String autourl;
/*    */   private Integer state;
/*    */   private Integer publicstate;
/*    */   private Integer autostate;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 25 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 28 */     this.id = id;
/*    */   }
/*    */   public String getUrl() {
/* 31 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 34 */     this.url = url;
/*    */   }
/*    */   public String getPublicurl() {
/* 37 */     return this.publicurl;
/*    */   }
/*    */   public void setPublicurl(String publicurl) {
/* 40 */     this.publicurl = publicurl;
/*    */   }
/*    */   public String getAutourl() {
/* 43 */     return this.autourl;
/*    */   }
/*    */   public void setAutourl(String autourl) {
/* 46 */     this.autourl = autourl;
/*    */   }
/*    */   public Integer getState() {
/* 49 */     return this.state;
/*    */   }
/*    */   public void setState(Integer state) {
/* 52 */     this.state = state;
/*    */   }
/*    */   public Integer getPublicstate() {
/* 55 */     return this.publicstate;
/*    */   }
/*    */   public void setPublicstate(Integer publicstate) {
/* 58 */     this.publicstate = publicstate;
/*    */   }
/*    */   public Integer getAutostate() {
/* 61 */     return this.autostate;
/*    */   }
/*    */   public void setAutostate(Integer autostate) {
/* 64 */     this.autostate = autostate;
/*    */   }
/*    */   public String toString() {
/* 67 */     return "Zsqhdapi [id=" + this.id + ",url=" + this.url + ",publicurl=" + this.publicurl + ",autourl=" + this.autourl + ",state=" + this.state + ",publicstate=" + this.publicstate + ",autostate=" + this.autostate + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Zsqhdapi
 * JD-Core Version:    0.6.2
 */