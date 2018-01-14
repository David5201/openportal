/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Advstores
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String description;
/*    */   private Date creatDate;
/*    */   private Long uid;
/*    */   private String address;
/*    */   private String phone;
/*    */   private String img;
/*    */   private Integer showInfo;
/*    */   private String x;
/*    */   private String y;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 29 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 32 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 35 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 38 */     this.name = name;
/*    */   }
/*    */   public String getDescription() {
/* 41 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 44 */     this.description = description;
/*    */   }
/*    */   public Date getCreatDate() {
/* 47 */     return this.creatDate;
/*    */   }
/*    */   public void setCreatDate(Date creatDate) {
/* 50 */     this.creatDate = creatDate;
/*    */   }
/*    */   public Long getUid() {
/* 53 */     return this.uid;
/*    */   }
/*    */   public void setUid(Long uid) {
/* 56 */     this.uid = uid;
/*    */   }
/*    */   public String getAddress() {
/* 59 */     return this.address;
/*    */   }
/*    */   public void setAddress(String address) {
/* 62 */     this.address = address;
/*    */   }
/*    */   public String getPhone() {
/* 65 */     return this.phone;
/*    */   }
/*    */   public void setPhone(String phone) {
/* 68 */     this.phone = phone;
/*    */   }
/*    */   public String getImg() {
/* 71 */     return this.img;
/*    */   }
/*    */   public void setImg(String img) {
/* 74 */     this.img = img;
/*    */   }
/*    */   public Integer getShowInfo() {
/* 77 */     return this.showInfo;
/*    */   }
/*    */   public void setShowInfo(Integer showInfo) {
/* 80 */     this.showInfo = showInfo;
/*    */   }
/*    */   public String getX() {
/* 83 */     return this.x;
/*    */   }
/*    */   public void setX(String x) {
/* 86 */     this.x = x;
/*    */   }
/*    */   public String getY() {
/* 89 */     return this.y;
/*    */   }
/*    */   public void setY(String y) {
/* 92 */     this.y = y;
/*    */   }
/*    */   public String toString() {
/* 95 */     return "Advstores [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",creatDate=" + this.creatDate + ",uid=" + this.uid + ",address=" + this.address + ",phone=" + this.phone + ",img=" + this.img + ",showInfo=" + this.showInfo + ",x=" + this.x + ",y=" + this.y + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advstores
 * JD-Core Version:    0.6.2
 */