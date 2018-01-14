/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalrole
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String description;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 21 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 24 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 30 */     this.name = name;
/*    */   }
/*    */   public String getDescription() {
/* 33 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 36 */     this.description = description;
/*    */   }
/*    */   public String toString() {
/* 39 */     return "Portalrole [id=" + this.id + ",name=" + this.name + ",description=" + this.description + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalrole
 * JD-Core Version:    0.6.2
 */