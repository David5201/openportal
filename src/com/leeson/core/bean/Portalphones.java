/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalphones
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private Long did;
/*    */   private Long uid;
/*    */   private String name;
/*    */   private String phone;
/*    */   private String description;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 24 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 27 */     this.id = id;
/*    */   }
/*    */   public Long getDid() {
/* 30 */     return this.did;
/*    */   }
/*    */   public void setDid(Long did) {
/* 33 */     this.did = did;
/*    */   }
/*    */   public Long getUid() {
/* 36 */     return this.uid;
/*    */   }
/*    */   public void setUid(Long uid) {
/* 39 */     this.uid = uid;
/*    */   }
/*    */   public String getName() {
/* 42 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 45 */     this.name = name;
/*    */   }
/*    */   public String getPhone() {
/* 48 */     return this.phone;
/*    */   }
/*    */   public void setPhone(String phone) {
/* 51 */     this.phone = phone;
/*    */   }
/*    */   public String getDescription() {
/* 54 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 57 */     this.description = description;
/*    */   }
/*    */   public String toString() {
/* 60 */     return "Portalphones [id=" + this.id + ",did=" + this.did + ",uid=" + this.uid + ",name=" + this.name + ",phone=" + this.phone + ",description=" + this.description + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalphones
 * JD-Core Version:    0.6.2
 */