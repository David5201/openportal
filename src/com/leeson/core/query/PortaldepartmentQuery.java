/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortaldepartmentQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Long parentId;
/*  89 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortaldepartmentQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortaldepartmentQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortaldepartmentQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getParentId() {
/*  50 */     return this.parentId;
/*     */   }
/*     */   public PortaldepartmentQuery setParentId(Long parentId) {
/*  53 */     this.parentId = parentId;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery orderbyId(boolean isAsc)
/*     */   {
/*  97 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery orderbyName(boolean isAsc)
/*     */   {
/* 107 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 117 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaldepartmentQuery orderbyParentId(boolean isAsc)
/*     */   {
/* 127 */     this.orderFields.add(new OrderField("parentId", isAsc ? "ASC" : "DESC"));
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 137 */     if (fieldMap == null) {
/* 138 */       fieldMap = new HashMap();
/* 139 */       fieldMap.put("id", "id");
/* 140 */       fieldMap.put("name", "name");
/* 141 */       fieldMap.put("description", "description");
/* 142 */       fieldMap.put("parentId", "parentId");
/*     */     }
/* 144 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 148 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 151 */     if (fields == null)
/* 152 */       return;
/* 153 */     String[] array = fields.split(",");
/* 154 */     StringBuffer buffer = new StringBuffer();
/* 155 */     for (String field : array) {
/* 156 */       if (getFieldSet().containsKey(field)) {
/* 157 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 158 */           .append(field).append(" ,");
/*     */       }
/* 160 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 161 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 162 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 165 */     if (buffer.length() != 0)
/* 166 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 168 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  63 */       this.fieldName = fieldName;
/*  64 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  70 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  73 */       this.fieldName = fieldName;
/*  74 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  77 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  80 */       this.order = order;
/*  81 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortaldepartmentQuery
 * JD-Core Version:    0.6.2
 */