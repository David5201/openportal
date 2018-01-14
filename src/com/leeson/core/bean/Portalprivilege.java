/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Portalprivilege
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String name;
/*    */   private String url;
/*    */   private Integer position;
/*    */   private Long parentId;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 23 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 26 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 32 */     this.name = name;
/*    */   }
/*    */   public String getUrl() {
/* 35 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 38 */     this.url = url;
/*    */   }
/*    */   public Integer getPosition() {
/* 41 */     return this.position;
/*    */   }
/*    */   public void setPosition(Integer position) {
/* 44 */     this.position = position;
/*    */   }
/*    */   public Long getParentId() {
/* 47 */     return this.parentId;
/*    */   }
/*    */   public void setParentId(Long parentId) {
/* 50 */     this.parentId = parentId;
/*    */   }
/*    */   public String toString() {
/* 53 */     return "Portalprivilege [id=" + this.id + ",name=" + this.name + ",url=" + this.url + ",position=" + this.position + ",parentId=" + this.parentId + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalprivilege
 * JD-Core Version:    0.6.2
 */