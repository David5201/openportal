/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalroleprivilegeQuery extends BaseQuery
/*     */ {
/*     */   private Long privilegeId;
/*     */   private Long roleId;
/*  63 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getPrivilegeId()
/*     */   {
/*  16 */     return this.privilegeId;
/*     */   }
/*     */   public PortalroleprivilegeQuery setPrivilegeId(Long privilegeId) {
/*  19 */     this.privilegeId = privilegeId;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getRoleId() {
/*  24 */     return this.roleId;
/*     */   }
/*     */   public PortalroleprivilegeQuery setRoleId(Long roleId) {
/*  27 */     this.roleId = roleId;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleprivilegeQuery orderbyPrivilegeId(boolean isAsc)
/*     */   {
/*  71 */     this.orderFields.add(new OrderField("privilegeId", isAsc ? "ASC" : "DESC"));
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleprivilegeQuery orderbyRoleId(boolean isAsc)
/*     */   {
/*  81 */     this.orderFields.add(new OrderField("roleId", isAsc ? "ASC" : "DESC"));
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/*  91 */     if (fieldMap == null) {
/*  92 */       fieldMap = new HashMap();
/*  93 */       fieldMap.put("privilegeId", "privilegeId");
/*  94 */       fieldMap.put("roleId", "roleId");
/*     */     }
/*  96 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 100 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 103 */     if (fields == null)
/* 104 */       return;
/* 105 */     String[] array = fields.split(",");
/* 106 */     StringBuffer buffer = new StringBuffer();
/* 107 */     for (String field : array) {
/* 108 */       if (getFieldSet().containsKey(field)) {
/* 109 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 110 */           .append(field).append(" ,");
/*     */       }
/* 112 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 113 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 114 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 117 */     if (buffer.length() != 0)
/* 118 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 120 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  37 */       this.fieldName = fieldName;
/*  38 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  44 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  47 */       this.fieldName = fieldName;
/*  48 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  51 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  54 */       this.order = order;
/*  55 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalroleprivilegeQuery
 * JD-Core Version:    0.6.2
 */