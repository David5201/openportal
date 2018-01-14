/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portaluser
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String loginName;
/*    */   private String password;
/*    */   private String name;
/*    */   private String gender;
/*    */   private String phoneNumber;
/*    */   private String email;
/*    */   private String description;
/*    */   private Long departmentId;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 27 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 30 */     this.id = id;
/*    */   }
/*    */   public String getLoginName() {
/* 33 */     return this.loginName;
/*    */   }
/*    */   public void setLoginName(String loginName) {
/* 36 */     this.loginName = loginName;
/*    */   }
/*    */   public String getPassword() {
/* 39 */     return this.password;
/*    */   }
/*    */   public void setPassword(String password) {
/* 42 */     this.password = password;
/*    */   }
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 48 */     this.name = name;
/*    */   }
/*    */   public String getGender() {
/* 51 */     return this.gender;
/*    */   }
/*    */   public void setGender(String gender) {
/* 54 */     this.gender = gender;
/*    */   }
/*    */   public String getPhoneNumber() {
/* 57 */     return this.phoneNumber;
/*    */   }
/*    */   public void setPhoneNumber(String phoneNumber) {
/* 60 */     this.phoneNumber = phoneNumber;
/*    */   }
/*    */   public String getEmail() {
/* 63 */     return this.email;
/*    */   }
/*    */   public void setEmail(String email) {
/* 66 */     this.email = email;
/*    */   }
/*    */   public String getDescription() {
/* 69 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 72 */     this.description = description;
/*    */   }
/*    */   public Long getDepartmentId() {
/* 75 */     return this.departmentId;
/*    */   }
/*    */   public void setDepartmentId(Long departmentId) {
/* 78 */     this.departmentId = departmentId;
/*    */   }
/*    */   public String toString() {
/* 81 */     return "Portaluser [id=" + this.id + ",loginName=" + this.loginName + ",password=" + this.password + ",name=" + this.name + ",gender=" + this.gender + ",phoneNumber=" + this.phoneNumber + ",email=" + this.email + ",description=" + this.description + ",departmentId=" + this.departmentId + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaluser
 * JD-Core Version:    0.6.2
 */