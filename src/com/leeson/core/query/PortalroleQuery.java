/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalroleQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*  81 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalroleQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalroleQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalroleQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleQuery orderbyId(boolean isAsc)
/*     */   {
/*  89 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleQuery orderbyName(boolean isAsc)
/*     */   {
/*  99 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalroleQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 109 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 119 */     if (fieldMap == null) {
/* 120 */       fieldMap = new HashMap();
/* 121 */       fieldMap.put("id", "id");
/* 122 */       fieldMap.put("name", "name");
/* 123 */       fieldMap.put("description", "description");
/*     */     }
/* 125 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 129 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 132 */     if (fields == null)
/* 133 */       return;
/* 134 */     String[] array = fields.split(",");
/* 135 */     StringBuffer buffer = new StringBuffer();
/* 136 */     for (String field : array) {
/* 137 */       if (getFieldSet().containsKey(field)) {
/* 138 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 139 */           .append(field).append(" ,");
/*     */       }
/* 141 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 142 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 143 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 146 */     if (buffer.length() != 0)
/* 147 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 149 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  55 */       this.fieldName = fieldName;
/*  56 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  62 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  65 */       this.fieldName = fieldName;
/*  66 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  69 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  72 */       this.order = order;
/*  73 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalroleQuery
 * JD-Core Version:    0.6.2
 */