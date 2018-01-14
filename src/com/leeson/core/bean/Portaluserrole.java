/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portaluserrole
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long roleId;
/*    */   private Long userId;
/*    */ 
/*    */   public Long getRoleId()
/*    */   {
/* 20 */     return this.roleId;
/*    */   }
/*    */   public void setRoleId(Long roleId) {
/* 23 */     this.roleId = roleId;
/*    */   }
/*    */   public Long getUserId() {
/* 26 */     return this.userId;
/*    */   }
/*    */   public void setUserId(Long userId) {
/* 29 */     this.userId = userId;
/*    */   }
/*    */   public String toString() {
/* 32 */     return "Portaluserrole [roleId=" + this.roleId + ",userId=" + this.userId + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaluserrole
 * JD-Core Version:    0.6.2
 */