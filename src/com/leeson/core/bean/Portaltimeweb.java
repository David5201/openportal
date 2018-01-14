/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Portaltimeweb
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String description;
/*    */   private Date viewdate;
/*    */   private Integer viewweekday;
/*    */   private Integer viewdateday;
/*    */   private Integer viewmonth;
/*    */   private Long web;
/*    */   private Long count;
/*    */   private Long pos;
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
/*    */   public String getDescription() {
/* 40 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 43 */     this.description = description;
/*    */   }
/*    */   public Date getViewdate() {
/* 46 */     return this.viewdate;
/*    */   }
/*    */   public void setViewdate(Date viewdate) {
/* 49 */     this.viewdate = viewdate;
/*    */   }
/*    */   public Integer getViewweekday() {
/* 52 */     return this.viewweekday;
/*    */   }
/*    */   public void setViewweekday(Integer viewweekday) {
/* 55 */     this.viewweekday = viewweekday;
/*    */   }
/*    */   public Integer getViewdateday() {
/* 58 */     return this.viewdateday;
/*    */   }
/*    */   public void setViewdateday(Integer viewdateday) {
/* 61 */     this.viewdateday = viewdateday;
/*    */   }
/*    */   public Integer getViewmonth() {
/* 64 */     return this.viewmonth;
/*    */   }
/*    */   public void setViewmonth(Integer viewmonth) {
/* 67 */     this.viewmonth = viewmonth;
/*    */   }
/*    */   public Long getWeb() {
/* 70 */     return this.web;
/*    */   }
/*    */   public void setWeb(Long web) {
/* 73 */     this.web = web;
/*    */   }
/*    */   public Long getCount() {
/* 76 */     return this.count;
/*    */   }
/*    */   public void setCount(Long count) {
/* 79 */     this.count = count;
/*    */   }
/*    */   public Long getPos() {
/* 82 */     return this.pos;
/*    */   }
/*    */   public void setPos(Long pos) {
/* 85 */     this.pos = pos;
/*    */   }
/*    */   public String toString() {
/* 88 */     return "Portaltimeweb [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",viewdate=" + this.viewdate + ",viewweekday=" + this.viewweekday + ",viewdateday=" + this.viewdateday + ",viewmonth=" + this.viewmonth + ",web=" + this.web + ",count=" + this.count + ",pos=" + this.pos + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaltimeweb
 * JD-Core Version:    0.6.2
 */