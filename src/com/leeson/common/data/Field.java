/*    */ package com.leeson.common.data;
/*    */ 
/*    */ public class Field
/*    */ {
/*    */   private String name;
/*    */   private String fieldType;
/*    */   private String fieldDefault;
/*    */   private String fieldProperty;
/*    */   private String comment;
/*    */   private String nullable;
/*    */   private String extra;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 14 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 18 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getFieldType() {
/* 22 */     return this.fieldType;
/*    */   }
/*    */ 
/*    */   public void setFieldType(String fieldType) {
/* 26 */     this.fieldType = fieldType;
/*    */   }
/*    */ 
/*    */   public String getFieldDefault() {
/* 30 */     return this.fieldDefault;
/*    */   }
/*    */ 
/*    */   public void setFieldDefault(String fieldDefault) {
/* 34 */     this.fieldDefault = fieldDefault;
/*    */   }
/*    */ 
/*    */   public String getFieldProperty() {
/* 38 */     return this.fieldProperty;
/*    */   }
/*    */ 
/*    */   public void setFieldProperty(String fieldProperty) {
/* 42 */     this.fieldProperty = fieldProperty;
/*    */   }
/*    */ 
/*    */   public String getComment() {
/* 46 */     return this.comment;
/*    */   }
/*    */ 
/*    */   public void setComment(String comment) {
/* 50 */     this.comment = comment;
/*    */   }
/*    */ 
/*    */   public String getNullable() {
/* 54 */     return this.nullable;
/*    */   }
/*    */ 
/*    */   public void setNullable(String nullable) {
/* 58 */     this.nullable = nullable;
/*    */   }
/*    */ 
/*    */   public String getExtra() {
/* 62 */     return this.extra;
/*    */   }
/*    */ 
/*    */   public void setExtra(String extra) {
/* 66 */     this.extra = extra;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     return "Field [name=" + this.name + ", fieldType=" + this.fieldType + 
/* 72 */       ", fieldDefault=" + this.fieldDefault + ", fieldProperty=" + 
/* 73 */       this.fieldProperty + ", comment=" + this.comment + ", nullable=" + 
/* 74 */       this.nullable + ", extra=" + this.extra + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Field
 * JD-Core Version:    0.6.2
 */