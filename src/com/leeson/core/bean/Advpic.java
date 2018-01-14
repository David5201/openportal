/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Advpic
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
/*    */   private String imgW;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 29 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 32 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 35 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 38 */     this.name = name;
/*    */   }
/*    */   public Long getUid() {
/* 41 */     return this.uid;
/*    */   }
/*    */   public void setUid(Long uid) {
/* 44 */     this.uid = uid;
/*    */   }
/*    */   public Long getSid() {
/* 47 */     return this.sid;
/*    */   }
/*    */   public void setSid(Long sid) {
/* 50 */     this.sid = sid;
/*    */   }
/*    */   public Long getAid() {
/* 53 */     return this.aid;
/*    */   }
/*    */   public void setAid(Long aid) {
/* 56 */     this.aid = aid;
/*    */   }
/*    */   public Long getPos() {
/* 59 */     return this.pos;
/*    */   }
/*    */   public void setPos(Long pos) {
/* 62 */     this.pos = pos;
/*    */   }
/*    */   public String getImg() {
/* 65 */     return this.img;
/*    */   }
/*    */   public void setImg(String img) {
/* 68 */     this.img = img;
/*    */   }
/*    */   public String getUrl() {
/* 71 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 74 */     this.url = url;
/*    */   }
/*    */   public Long getShowCount() {
/* 77 */     return this.showCount;
/*    */   }
/*    */   public void setShowCount(Long showCount) {
/* 80 */     this.showCount = showCount;
/*    */   }
/*    */   public Long getClickCount() {
/* 83 */     return this.clickCount;
/*    */   }
/*    */   public void setClickCount(Long clickCount) {
/* 86 */     this.clickCount = clickCount;
/*    */   }
/*    */   public String getImgW() {
/* 89 */     return this.imgW;
/*    */   }
/*    */   public void setImgW(String imgW) {
/* 92 */     this.imgW = imgW;
/*    */   }
/*    */   public String toString() {
/* 95 */     return "Advpic [id=" + this.id + ",name=" + this.name + ",uid=" + this.uid + ",sid=" + this.sid + ",aid=" + this.aid + ",pos=" + this.pos + ",img=" + this.img + ",url=" + this.url + ",showCount=" + this.showCount + ",clickCount=" + this.clickCount + ",imgW=" + this.imgW + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advpic
 * JD-Core Version:    0.6.2
 */