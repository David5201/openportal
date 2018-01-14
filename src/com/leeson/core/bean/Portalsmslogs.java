/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Portalsmslogs
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String info;
/*    */   private Date sendDate;
/*    */   private String phone;
/*    */   private Long sid;
/*    */   private String type;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 24 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 27 */     this.id = id;
/*    */   }
/*    */   public String getInfo() {
/* 30 */     return this.info;
/*    */   }
/*    */   public void setInfo(String info) {
/* 33 */     this.info = info;
/*    */   }
/*    */   public Date getSendDate() {
/* 36 */     return this.sendDate;
/*    */   }
/*    */   public void setSendDate(Date sendDate) {
/* 39 */     this.sendDate = sendDate;
/*    */   }
/*    */   public String getPhone() {
/* 42 */     return this.phone;
/*    */   }
/*    */   public void setPhone(String phone) {
/* 45 */     this.phone = phone;
/*    */   }
/*    */   public Long getSid() {
/* 48 */     return this.sid;
/*    */   }
/*    */   public void setSid(Long sid) {
/* 51 */     this.sid = sid;
/*    */   }
/*    */   public String getType() {
/* 54 */     return this.type;
/*    */   }
/*    */   public void setType(String type) {
/* 57 */     this.type = type;
/*    */   }
/*    */   public String toString() {
/* 60 */     return "Portalsmslogs [id=" + this.id + ",info=" + this.info + ",sendDate=" + this.sendDate + ",phone=" + this.phone + ",sid=" + this.sid + ",type=" + this.type + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalsmslogs
 * JD-Core Version:    0.6.2
 */