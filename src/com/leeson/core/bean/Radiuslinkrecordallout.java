/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Radiuslinkrecordallout
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private Date creatDate;
/*    */   private String url;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 22 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 25 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */   public Date getCreatDate() {
/* 34 */     return this.creatDate;
/*    */   }
/*    */   public void setCreatDate(Date creatDate) {
/* 37 */     this.creatDate = creatDate;
/*    */   }
/*    */   public String getUrl() {
/* 40 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 43 */     this.url = url;
/*    */   }
/*    */   public String toString() {
/* 46 */     return "Radiuslinkrecordallout [id=" + this.id + ",name=" + this.name + ",creatDate=" + this.creatDate + ",url=" + this.url + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Radiuslinkrecordallout
 * JD-Core Version:    0.6.2
 */