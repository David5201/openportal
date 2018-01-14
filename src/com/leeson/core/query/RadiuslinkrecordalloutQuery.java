/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RadiuslinkrecordalloutQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private Date creatDate;
/*     */   private String url;
/*     */   private boolean urlLike;
/*  89 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public RadiuslinkrecordalloutQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public RadiuslinkrecordalloutQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getCreatDate() {
/*  37 */     return this.creatDate;
/*     */   }
/*     */   public RadiuslinkrecordalloutQuery setCreatDate(Date creatDate) {
/*  40 */     this.creatDate = creatDate;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  45 */     return this.url;
/*     */   }
/*     */   public RadiuslinkrecordalloutQuery setUrl(String url) {
/*  48 */     this.url = url;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery setUrlLike(boolean isLike) {
/*  53 */     this.urlLike = isLike;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery orderbyId(boolean isAsc)
/*     */   {
/*  97 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery orderbyName(boolean isAsc)
/*     */   {
/* 107 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery orderbyCreatDate(boolean isAsc)
/*     */   {
/* 117 */     this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordalloutQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 127 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 137 */     if (fieldMap == null) {
/* 138 */       fieldMap = new HashMap();
/* 139 */       fieldMap.put("id", "id");
/* 140 */       fieldMap.put("name", "name");
/* 141 */       fieldMap.put("creatDate", "creatDate");
/* 142 */       fieldMap.put("url", "url");
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
 * Qualified Name:     com.leeson.core.query.RadiuslinkrecordalloutQuery
 * JD-Core Version:    0.6.2
 */