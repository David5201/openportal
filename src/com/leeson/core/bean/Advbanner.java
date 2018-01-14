/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Advbanner
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private Long uid;
/*    */   private Long sid;
/*    */   private Long aid;
/*    */   private Long pos;
/*    */   private String img;
/*    */   private String url;
/*    */   private Long showCount;
/*    */   private Long clickCount;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 28 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 31 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 37 */     this.name = name;
/*    */   }
/*    */   public Long getUid() {
/* 40 */     return this.uid;
/*    */   }
/*    */   public void setUid(Long uid) {
/* 43 */     this.uid = uid;
/*    */   }
/*    */   public Long getSid() {
/* 46 */     return this.sid;
/*    */   }
/*    */   public void setSid(Long sid) {
/* 49 */     this.sid = sid;
/*    */   }
/*    */   public Long getAid() {
/* 52 */     return this.aid;
/*    */   }
/*    */   public void setAid(Long aid) {
/* 55 */     this.aid = aid;
/*    */   }
/*    */   public Long getPos() {
/* 58 */     return this.pos;
/*    */   }
/*    */   public void setPos(Long pos) {
/* 61 */     this.pos = pos;
/*    */   }
/*    */   public String getImg() {
/* 64 */     return this.img;
/*    */   }
/*    */   public void setImg(String img) {
/* 67 */     this.img = img;
/*    */   }
/*    */   public String getUrl() {
/* 70 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 73 */     this.url = url;
/*    */   }
/*    */   public Long getShowCount() {
/* 76 */     return this.showCount;
/*    */   }
/*    */   public void setShowCount(Long showCount) {
/* 79 */     this.showCount = showCount;
/*    */   }
/*    */   public Long getClickCount() {
/* 82 */     return this.clickCount;
/*    */   }
/*    */   public void setClickCount(Long clickCount) {
/* 85 */     this.clickCount = clickCount;
/*    */   }
/*    */   public String toString() {
/* 88 */     return "Advbanner [id=" + this.id + ",name=" + this.name + ",uid=" + this.uid + ",sid=" + this.sid + ",aid=" + this.aid + ",pos=" + this.pos + ",img=" + this.img + ",url=" + this.url + ",showCount=" + this.showCount + ",clickCount=" + this.clickCount + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advbanner
 * JD-Core Version:    0.6.2
 */