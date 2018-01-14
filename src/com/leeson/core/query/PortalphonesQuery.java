/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalphonesQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long did;
/*     */   private Long uid;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String phone;
/*     */   private boolean phoneLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/* 110 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalphonesQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getDid() {
/*  24 */     return this.did;
/*     */   }
/*     */   public PortalphonesQuery setDid(Long did) {
/*  27 */     this.did = did;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/*  32 */     return this.uid;
/*     */   }
/*     */   public PortalphonesQuery setUid(Long uid) {
/*  35 */     this.uid = uid;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  40 */     return this.name;
/*     */   }
/*     */   public PortalphonesQuery setName(String name) {
/*  43 */     this.name = name;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery setNameLike(boolean isLike) {
/*  48 */     this.nameLike = isLike;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/*  53 */     return this.phone;
/*     */   }
/*     */   public PortalphonesQuery setPhone(String phone) {
/*  56 */     this.phone = phone;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery setPhoneLike(boolean isLike) {
/*  61 */     this.phoneLike = isLike;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  66 */     return this.description;
/*     */   }
/*     */   public PortalphonesQuery setDescription(String description) {
/*  69 */     this.description = description;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery setDescriptionLike(boolean isLike) {
/*  74 */     this.descriptionLike = isLike;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyId(boolean isAsc)
/*     */   {
/* 118 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyDid(boolean isAsc)
/*     */   {
/* 128 */     this.orderFields.add(new OrderField("did", isAsc ? "ASC" : "DESC"));
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyUid(boolean isAsc)
/*     */   {
/* 138 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyName(boolean isAsc)
/*     */   {
/* 148 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyPhone(boolean isAsc)
/*     */   {
/* 158 */     this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalphonesQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 168 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 178 */     if (fieldMap == null) {
/* 179 */       fieldMap = new HashMap();
/* 180 */       fieldMap.put("id", "id");
/* 181 */       fieldMap.put("did", "did");
/* 182 */       fieldMap.put("uid", "uid");
/* 183 */       fieldMap.put("name", "name");
/* 184 */       fieldMap.put("phone", "phone");
/* 185 */       fieldMap.put("description", "description");
/*     */     }
/* 187 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 191 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 194 */     if (fields == null)
/* 195 */       return;
/* 196 */     String[] array = fields.split(",");
/* 197 */     StringBuffer buffer = new StringBuffer();
/* 198 */     for (String field : array) {
/* 199 */       if (getFieldSet().containsKey(field)) {
/* 200 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 201 */           .append(field).append(" ,");
/*     */       }
/* 203 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 204 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 205 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 208 */     if (buffer.length() != 0)
/* 209 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 211 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  84 */       this.fieldName = fieldName;
/*  85 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  91 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  94 */       this.fieldName = fieldName;
/*  95 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  98 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 101 */       this.order = order;
/* 102 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalphonesQuery
 * JD-Core Version:    0.6.2
 */