/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalsmslogsQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String info;
/*     */   private boolean infoLike;
/*     */   private Date sendDate;
/*     */   private String phone;
/*     */   private boolean phoneLike;
/*     */   private Long sid;
/*     */   private String type;
/*     */   private boolean typeLike;
/* 110 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalsmslogsQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getInfo() {
/*  24 */     return this.info;
/*     */   }
/*     */   public PortalsmslogsQuery setInfo(String info) {
/*  27 */     this.info = info;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery setInfoLike(boolean isLike) {
/*  32 */     this.infoLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getSendDate() {
/*  37 */     return this.sendDate;
/*     */   }
/*     */   public PortalsmslogsQuery setSendDate(Date sendDate) {
/*  40 */     this.sendDate = sendDate;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/*  45 */     return this.phone;
/*     */   }
/*     */   public PortalsmslogsQuery setPhone(String phone) {
/*  48 */     this.phone = phone;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery setPhoneLike(boolean isLike) {
/*  53 */     this.phoneLike = isLike;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSid() {
/*  58 */     return this.sid;
/*     */   }
/*     */   public PortalsmslogsQuery setSid(Long sid) {
/*  61 */     this.sid = sid;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  66 */     return this.type;
/*     */   }
/*     */   public PortalsmslogsQuery setType(String type) {
/*  69 */     this.type = type;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery setTypeLike(boolean isLike) {
/*  74 */     this.typeLike = isLike;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbyId(boolean isAsc)
/*     */   {
/* 118 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbyInfo(boolean isAsc)
/*     */   {
/* 128 */     this.orderFields.add(new OrderField("info", isAsc ? "ASC" : "DESC"));
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbySendDate(boolean isAsc)
/*     */   {
/* 138 */     this.orderFields.add(new OrderField("sendDate", isAsc ? "ASC" : "DESC"));
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbyPhone(boolean isAsc)
/*     */   {
/* 148 */     this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbySid(boolean isAsc)
/*     */   {
/* 158 */     this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmslogsQuery orderbyType(boolean isAsc)
/*     */   {
/* 168 */     this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 178 */     if (fieldMap == null) {
/* 179 */       fieldMap = new HashMap();
/* 180 */       fieldMap.put("id", "id");
/* 181 */       fieldMap.put("info", "info");
/* 182 */       fieldMap.put("sendDate", "sendDate");
/* 183 */       fieldMap.put("phone", "phone");
/* 184 */       fieldMap.put("sid", "sid");
/* 185 */       fieldMap.put("type", "type");
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
 * Qualified Name:     com.leeson.core.query.PortalsmslogsQuery
 * JD-Core Version:    0.6.2
 */