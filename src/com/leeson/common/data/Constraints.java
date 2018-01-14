/*    */ package com.leeson.common.data;
/*    */ 
/*    */ public class Constraints
/*    */ {
/*    */   private String name;
/*    */   private String tableName;
/*    */   private String columnName;
/*    */   private String referencedTableName;
/*    */   private String referencedColumnName;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 12 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getTableName() {
/* 20 */     return this.tableName;
/*    */   }
/*    */ 
/*    */   public void setTableName(String tableName) {
/* 24 */     this.tableName = tableName;
/*    */   }
/*    */ 
/*    */   public String getColumnName() {
/* 28 */     return this.columnName;
/*    */   }
/*    */ 
/*    */   public void setColumnName(String columnName) {
/* 32 */     this.columnName = columnName;
/*    */   }
/*    */ 
/*    */   public String getReferencedTableName() {
/* 36 */     return this.referencedTableName;
/*    */   }
/*    */ 
/*    */   public void setReferencedTableName(String referencedTableName) {
/* 40 */     this.referencedTableName = referencedTableName;
/*    */   }
/*    */ 
/*    */   public String getReferencedColumnName() {
/* 44 */     return this.referencedColumnName;
/*    */   }
/*    */ 
/*    */   public void setReferencedColumnName(String referencedColumnName) {
/* 48 */     this.referencedColumnName = referencedColumnName;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 53 */     return "Constraints [name=" + this.name + ", tableName=" + this.tableName + 
/* 54 */       ", columnName=" + this.columnName + ", referencedTableName=" + 
/* 55 */       this.referencedTableName + ", referencedColumnName=" + 
/* 56 */       this.referencedColumnName + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Constraints
 * JD-Core Version:    0.6.2
 */