/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalroleprivilege
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long privilegeId;
/*    */   private Long roleId;
/*    */ 
/*    */   public Long getPrivilegeId()
/*    */   {
/* 20 */     return this.privilegeId;
/*    */   }
/*    */   public void setPrivilegeId(Long privilegeId) {
/* 23 */     this.privilegeId = privilegeId;
/*    */   }
/*    */   public Long getRoleId() {
/* 26 */     return this.roleId;
/*    */   }
/*    */   public void setRoleId(Long roleId) {
/* 29 */     this.roleId = roleId;
/*    */   }
/*    */   public String toString() {
/* 32 */     return "Portalroleprivilege [privilegeId=" + this.privilegeId + ",roleId=" + this.roleId + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalroleprivilege
 * JD-Core Version:    0.6.2
 */