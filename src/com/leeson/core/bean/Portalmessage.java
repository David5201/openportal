/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Portalmessage
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private String title;
/*     */   private String description;
/*     */   private Date date;
/*     */   private String state;
/*     */   private String fromid;
/*     */   private String toid;
/*     */   private String ip;
/*     */   private String toname;
/*     */   private String fromname;
/*     */   private String delin;
/*     */   private String delout;
/*     */   private String fromPos;
/*     */   private String toPos;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  32 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  35 */     this.id = id;
/*     */   }
/*     */   public String getTitle() {
/*  38 */     return this.title;
/*     */   }
/*     */   public void setTitle(String title) {
/*  41 */     this.title = title;
/*     */   }
/*     */   public String getDescription() {
/*  44 */     return this.description;
/*     */   }
/*     */   public void setDescription(String description) {
/*  47 */     this.description = description;
/*     */   }
/*     */   public Date getDate() {
/*  50 */     return this.date;
/*     */   }
/*     */   public void setDate(Date date) {
/*  53 */     this.date = date;
/*     */   }
/*     */   public String getState() {
/*  56 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  59 */     this.state = state;
/*     */   }
/*     */   public String getFromid() {
/*  62 */     return this.fromid;
/*     */   }
/*     */   public void setFromid(String fromid) {
/*  65 */     this.fromid = fromid;
/*     */   }
/*     */   public String getToid() {
/*  68 */     return this.toid;
/*     */   }
/*     */   public void setToid(String toid) {
/*  71 */     this.toid = toid;
/*     */   }
/*     */   public String getIp() {
/*  74 */     return this.ip;
/*     */   }
/*     */   public void setIp(String ip) {
/*  77 */     this.ip = ip;
/*     */   }
/*     */   public String getToname() {
/*  80 */     return this.toname;
/*     */   }
/*     */   public void setToname(String toname) {
/*  83 */     this.toname = toname;
/*     */   }
/*     */   public String getFromname() {
/*  86 */     return this.fromname;
/*     */   }
/*     */   public void setFromname(String fromname) {
/*  89 */     this.fromname = fromname;
/*     */   }
/*     */   public String getDelin() {
/*  92 */     return this.delin;
/*     */   }
/*     */   public void setDelin(String delin) {
/*  95 */     this.delin = delin;
/*     */   }
/*     */   public String getDelout() {
/*  98 */     return this.delout;
/*     */   }
/*     */   public void setDelout(String delout) {
/* 101 */     this.delout = delout;
/*     */   }
/*     */   public String getFromPos() {
/* 104 */     return this.fromPos;
/*     */   }
/*     */   public void setFromPos(String fromPos) {
/* 107 */     this.fromPos = fromPos;
/*     */   }
/*     */   public String getToPos() {
/* 110 */     return this.toPos;
/*     */   }
/*     */   public void setToPos(String toPos) {
/* 113 */     this.toPos = toPos;
/*     */   }
/*     */   public String toString() {
/* 116 */     return "Portalmessage [id=" + this.id + ",title=" + this.title + ",description=" + this.description + ",date=" + this.date + ",state=" + this.state + ",fromid=" + this.fromid + ",toid=" + this.toid + ",ip=" + this.ip + ",toname=" + this.toname + ",fromname=" + this.fromname + ",delin=" + this.delin + ",delout=" + this.delout + ",fromPos=" + this.fromPos + ",toPos=" + this.toPos + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalmessage
 * JD-Core Version:    0.6.2
 */