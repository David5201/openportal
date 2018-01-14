/*    */ package com.leeson.common.data;
/*    */ 
/*    */ public class Table
/*    */ {
/*    */   private String name;
/*    */   private String comment;
/*    */   private String engine;
/*    */   private Integer rows;
/*    */   private Integer auto_increment;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 12 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getComment() {
/* 20 */     return this.comment;
/*    */   }
/*    */ 
/*    */   public void setComment(String comment) {
/* 24 */     this.comment = comment;
/*    */   }
/*    */ 
/*    */   public String getEngine() {
/* 28 */     return this.engine;
/*    */   }
/*    */ 
/*    */   public void setEngine(String engine) {
/* 32 */     this.engine = engine;
/*    */   }
/*    */ 
/*    */   public Integer getRows() {
/* 36 */     return this.rows;
/*    */   }
/*    */ 
/*    */   public void setRows(Integer rows) {
/* 40 */     this.rows = rows;
/*    */   }
/*    */ 
/*    */   public Integer getAuto_increment() {
/* 44 */     return this.auto_increment;
/*    */   }
/*    */ 
/*    */   public void setAuto_increment(Integer auto_increment) {
/* 48 */     this.auto_increment = auto_increment;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 53 */     return "Table [name=" + this.name + ", comment=" + this.comment + ", engine=" + 
/* 54 */       this.engine + ", rows=" + this.rows + ", auto_increment=" + 
/* 55 */       this.auto_increment + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Table
 * JD-Core Version:    0.6.2
 */