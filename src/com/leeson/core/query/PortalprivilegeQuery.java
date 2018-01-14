/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalprivilegeQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private Integer position;
/*     */   private Long parentId;
/*  97 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalprivilegeQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalprivilegeQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  37 */     return this.url;
/*     */   }
/*     */   public PortalprivilegeQuery setUrl(String url) {
/*  40 */     this.url = url;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery setUrlLike(boolean isLike) {
/*  45 */     this.urlLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getPosition() {
/*  50 */     return this.position;
/*     */   }
/*     */   public PortalprivilegeQuery setPosition(Integer position) {
/*  53 */     this.position = position;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getParentId() {
/*  58 */     return this.parentId;
/*     */   }
/*     */   public PortalprivilegeQuery setParentId(Long parentId) {
/*  61 */     this.parentId = parentId;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery orderbyId(boolean isAsc)
/*     */   {
/* 105 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery orderbyName(boolean isAsc)
/*     */   {
/* 115 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 125 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery orderbyPosition(boolean isAsc)
/*     */   {
/* 135 */     this.orderFields.add(new OrderField("position", isAsc ? "ASC" : "DESC"));
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalprivilegeQuery orderbyParentId(boolean isAsc)
/*     */   {
/* 145 */     this.orderFields.add(new OrderField("parentId", isAsc ? "ASC" : "DESC"));
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 155 */     if (fieldMap == null) {
/* 156 */       fieldMap = new HashMap();
/* 157 */       fieldMap.put("id", "id");
/* 158 */       fieldMap.put("name", "name");
/* 159 */       fieldMap.put("url", "url");
/* 160 */       fieldMap.put("position", "position");
/* 161 */       fieldMap.put("parentId", "parentId");
/*     */     }
/* 163 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 167 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 170 */     if (fields == null)
/* 171 */       return;
/* 172 */     String[] array = fields.split(",");
/* 173 */     StringBuffer buffer = new StringBuffer();
/* 174 */     for (String field : array) {
/* 175 */       if (getFieldSet().containsKey(field)) {
/* 176 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 177 */           .append(field).append(" ,");
/*     */       }
/* 179 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 180 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 181 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 184 */     if (buffer.length() != 0)
/* 185 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 187 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  71 */       this.fieldName = fieldName;
/*  72 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  78 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  81 */       this.fieldName = fieldName;
/*  82 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  85 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  88 */       this.order = order;
/*  89 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalprivilegeQuery
 * JD-Core Version:    0.6.2
 */