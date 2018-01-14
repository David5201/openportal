/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalautologin
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private Long time;
/*    */   private Integer type;
/*    */   private Integer state;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 22 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 25 */     this.id = id;
/*    */   }
/*    */   public Long getTime() {
/* 28 */     return this.time;
/*    */   }
/*    */   public void setTime(Long time) {
/* 31 */     this.time = time;
/*    */   }
/*    */   public Integer getType() {
/* 34 */     return this.type;
/*    */   }
/*    */   public void setType(Integer type) {
/* 37 */     this.type = type;
/*    */   }
/*    */   public Integer getState() {
/* 40 */     return this.state;
/*    */   }
/*    */   public void setState(Integer state) {
/* 43 */     this.state = state;
/*    */   }
/*    */   public String toString() {
/* 46 */     return "Portalautologin [id=" + this.id + ",time=" + this.time + ",type=" + this.type + ",state=" + this.state + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalautologin
 * JD-Core Version:    0.6.2
 */