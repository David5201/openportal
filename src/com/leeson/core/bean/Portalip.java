/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalip
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String description;
/*    */   private String start;
/*    */   private String end;
/*    */   private Long web;
/*    */   private Long count;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 25 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 28 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 34 */     this.name = name;
/*    */   }
/*    */   public String getDescription() {
/* 37 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 40 */     this.description = description;
/*    */   }
/*    */   public String getStart() {
/* 43 */     return this.start;
/*    */   }
/*    */   public void setStart(String start) {
/* 46 */     this.start = start;
/*    */   }
/*    */   public String getEnd() {
/* 49 */     return this.end;
/*    */   }
/*    */   public void setEnd(String end) {
/* 52 */     this.end = end;
/*    */   }
/*    */   public Long getWeb() {
/* 55 */     return this.web;
/*    */   }
/*    */   public void setWeb(Long web) {
/* 58 */     this.web = web;
/*    */   }
/*    */   public Long getCount() {
/* 61 */     return this.count;
/*    */   }
/*    */   public void setCount(Long count) {
/* 64 */     this.count = count;
/*    */   }
/*    */   public String toString() {
/* 67 */     return "Portalip [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",start=" + this.start + ",end=" + this.end + ",web=" + this.web + ",count=" + this.count + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalip
 * JD-Core Version:    0.6.2
 */