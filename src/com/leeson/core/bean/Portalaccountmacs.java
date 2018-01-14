/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalaccountmacs
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private Long accountId;
/*    */   private String mac;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 21 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 24 */     this.id = id;
/*    */   }
/*    */   public Long getAccountId() {
/* 27 */     return this.accountId;
/*    */   }
/*    */   public void setAccountId(Long accountId) {
/* 30 */     this.accountId = accountId;
/*    */   }
/*    */   public String getMac() {
/* 33 */     return this.mac;
/*    */   }
/*    */   public void setMac(String mac) {
/* 36 */     this.mac = mac;
/*    */   }
/*    */   public String toString() {
/* 39 */     return "Portalaccountmacs [id=" + this.id + ",accountId=" + this.accountId + ",mac=" + this.mac + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalaccountmacs
 * JD-Core Version:    0.6.2
 */