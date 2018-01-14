/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalwebQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Long countShow;
/*     */   private Long countAuth;
/*     */   private Long adv;
/* 105 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalwebQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalwebQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalwebQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCountShow() {
/*  50 */     return this.countShow;
/*     */   }
/*     */   public PortalwebQuery setCountShow(Long countShow) {
/*  53 */     this.countShow = countShow;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCountAuth() {
/*  58 */     return this.countAuth;
/*     */   }
/*     */   public PortalwebQuery setCountAuth(Long countAuth) {
/*  61 */     this.countAuth = countAuth;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAdv() {
/*  66 */     return this.adv;
/*     */   }
/*     */   public PortalwebQuery setAdv(Long adv) {
/*  69 */     this.adv = adv;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyId(boolean isAsc)
/*     */   {
/* 113 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyName(boolean isAsc)
/*     */   {
/* 123 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 133 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyCountShow(boolean isAsc)
/*     */   {
/* 143 */     this.orderFields.add(new OrderField("countShow", isAsc ? "ASC" : "DESC"));
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyCountAuth(boolean isAsc)
/*     */   {
/* 153 */     this.orderFields.add(new OrderField("countAuth", isAsc ? "ASC" : "DESC"));
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalwebQuery orderbyAdv(boolean isAsc)
/*     */   {
/* 163 */     this.orderFields.add(new OrderField("adv", isAsc ? "ASC" : "DESC"));
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 173 */     if (fieldMap == null) {
/* 174 */       fieldMap = new HashMap();
/* 175 */       fieldMap.put("id", "id");
/* 176 */       fieldMap.put("name", "name");
/* 177 */       fieldMap.put("description", "description");
/* 178 */       fieldMap.put("countShow", "countShow");
/* 179 */       fieldMap.put("countAuth", "countAuth");
/* 180 */       fieldMap.put("adv", "adv");
/*     */     }
/* 182 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 186 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 189 */     if (fields == null)
/* 190 */       return;
/* 191 */     String[] array = fields.split(",");
/* 192 */     StringBuffer buffer = new StringBuffer();
/* 193 */     for (String field : array) {
/* 194 */       if (getFieldSet().containsKey(field)) {
/* 195 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 196 */           .append(field).append(" ,");
/*     */       }
/* 198 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 199 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 200 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 203 */     if (buffer.length() != 0)
/* 204 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 206 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  79 */       this.fieldName = fieldName;
/*  80 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  86 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  89 */       this.fieldName = fieldName;
/*  90 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  93 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  96 */       this.order = order;
/*  97 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalwebQuery
 * JD-Core Version:    0.6.2
 */