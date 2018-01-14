/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalweb
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String description;
/*    */   private Long countShow;
/*    */   private Long countAuth;
/*    */   private Long adv;
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
/*    */   public String getDescription() {
/* 36 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 39 */     this.description = description;
/*    */   }
/*    */   public Long getCountShow() {
/* 42 */     return this.countShow;
/*    */   }
/*    */   public void setCountShow(Long countShow) {
/* 45 */     this.countShow = countShow;
/*    */   }
/*    */   public Long getCountAuth() {
/* 48 */     return this.countAuth;
/*    */   }
/*    */   public void setCountAuth(Long countAuth) {
/* 51 */     this.countAuth = countAuth;
/*    */   }
/*    */   public Long getAdv() {
/* 54 */     return this.adv;
/*    */   }
/*    */   public void setAdv(Long adv) {
/* 57 */     this.adv = adv;
/*    */   }
/*    */   public String toString() {
/* 60 */     return "Portalweb [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",countShow=" + this.countShow + ",countAuth=" + this.countAuth + ",adv=" + this.adv + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalweb
 * JD-Core Version:    0.6.2
 */