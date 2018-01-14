/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalspeed
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private Long up;
/*    */   private Long down;
/*    */   private Long mup;
/*    */   private Long mdown;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 24 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 27 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 30 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 33 */     this.name = name;
/*    */   }
/*    */   public Long getUp() {
/* 36 */     return this.up;
/*    */   }
/*    */   public void setUp(Long up) {
/* 39 */     this.up = up;
/*    */   }
/*    */   public Long getDown() {
/* 42 */     return this.down;
/*    */   }
/*    */   public void setDown(Long down) {
/* 45 */     this.down = down;
/*    */   }
/*    */   public Long getMup() {
/* 48 */     return this.mup;
/*    */   }
/*    */   public void setMup(Long mup) {
/* 51 */     this.mup = mup;
/*    */   }
/*    */   public Long getMdown() {
/* 54 */     return this.mdown;
/*    */   }
/*    */   public void setMdown(Long mdown) {
/* 57 */     this.mdown = mdown;
/*    */   }
/*    */   public String toString() {
/* 60 */     return "Portalspeed [id=" + this.id + ",name=" + this.name + ",up=" + this.up + ",down=" + this.down + ",mup=" + this.mup + ",mdown=" + this.mdown + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalspeed
 * JD-Core Version:    0.6.2
 */