/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalipQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private String start;
/*     */   private boolean startLike;
/*     */   private String end;
/*     */   private boolean endLike;
/*     */   private Long web;
/*     */   private Long count;
/* 123 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalipQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalipQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalipQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getStart() {
/*  50 */     return this.start;
/*     */   }
/*     */   public PortalipQuery setStart(String start) {
/*  53 */     this.start = start;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery setStartLike(boolean isLike) {
/*  58 */     this.startLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEnd() {
/*  63 */     return this.end;
/*     */   }
/*     */   public PortalipQuery setEnd(String end) {
/*  66 */     this.end = end;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery setEndLike(boolean isLike) {
/*  71 */     this.endLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getWeb() {
/*  76 */     return this.web;
/*     */   }
/*     */   public PortalipQuery setWeb(Long web) {
/*  79 */     this.web = web;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCount() {
/*  84 */     return this.count;
/*     */   }
/*     */   public PortalipQuery setCount(Long count) {
/*  87 */     this.count = count;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyId(boolean isAsc)
/*     */   {
/* 131 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyName(boolean isAsc)
/*     */   {
/* 141 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 151 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyStart(boolean isAsc)
/*     */   {
/* 161 */     this.orderFields.add(new OrderField("start", isAsc ? "ASC" : "DESC"));
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyEnd(boolean isAsc)
/*     */   {
/* 171 */     this.orderFields.add(new OrderField("end", isAsc ? "ASC" : "DESC"));
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyWeb(boolean isAsc)
/*     */   {
/* 181 */     this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalipQuery orderbyCount(boolean isAsc)
/*     */   {
/* 191 */     this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 201 */     if (fieldMap == null) {
/* 202 */       fieldMap = new HashMap();
/* 203 */       fieldMap.put("id", "id");
/* 204 */       fieldMap.put("name", "name");
/* 205 */       fieldMap.put("description", "description");
/* 206 */       fieldMap.put("start", "start");
/* 207 */       fieldMap.put("end", "end");
/* 208 */       fieldMap.put("web", "web");
/* 209 */       fieldMap.put("count", "count");
/*     */     }
/* 211 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 215 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 218 */     if (fields == null)
/* 219 */       return;
/* 220 */     String[] array = fields.split(",");
/* 221 */     StringBuffer buffer = new StringBuffer();
/* 222 */     for (String field : array) {
/* 223 */       if (getFieldSet().containsKey(field)) {
/* 224 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 225 */           .append(field).append(" ,");
/*     */       }
/* 227 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 228 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 229 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 232 */     if (buffer.length() != 0)
/* 233 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 235 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  97 */       this.fieldName = fieldName;
/*  98 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 104 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 107 */       this.fieldName = fieldName;
/* 108 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 111 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 114 */       this.order = order;
/* 115 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalipQuery
 * JD-Core Version:    0.6.2
 */