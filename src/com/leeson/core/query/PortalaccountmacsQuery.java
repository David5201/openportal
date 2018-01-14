/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalaccountmacsQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long accountId;
/*     */   private String mac;
/*     */   private boolean macLike;
/*  76 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalaccountmacsQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAccountId() {
/*  24 */     return this.accountId;
/*     */   }
/*     */   public PortalaccountmacsQuery setAccountId(Long accountId) {
/*  27 */     this.accountId = accountId;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public String getMac() {
/*  32 */     return this.mac;
/*     */   }
/*     */   public PortalaccountmacsQuery setMac(String mac) {
/*  35 */     this.mac = mac;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountmacsQuery setMacLike(boolean isLike) {
/*  40 */     this.macLike = isLike;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountmacsQuery orderbyId(boolean isAsc)
/*     */   {
/*  84 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountmacsQuery orderbyAccountId(boolean isAsc)
/*     */   {
/*  94 */     this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountmacsQuery orderbyMac(boolean isAsc)
/*     */   {
/* 104 */     this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 114 */     if (fieldMap == null) {
/* 115 */       fieldMap = new HashMap();
/* 116 */       fieldMap.put("id", "id");
/* 117 */       fieldMap.put("accountId", "accountId");
/* 118 */       fieldMap.put("mac", "mac");
/*     */     }
/* 120 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 124 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 127 */     if (fields == null)
/* 128 */       return;
/* 129 */     String[] array = fields.split(",");
/* 130 */     StringBuffer buffer = new StringBuffer();
/* 131 */     for (String field : array) {
/* 132 */       if (getFieldSet().containsKey(field)) {
/* 133 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 134 */           .append(field).append(" ,");
/*     */       }
/* 136 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 137 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 138 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 141 */     if (buffer.length() != 0)
/* 142 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 144 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  50 */       this.fieldName = fieldName;
/*  51 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  57 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  60 */       this.fieldName = fieldName;
/*  61 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  64 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  67 */       this.order = order;
/*  68 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalaccountmacsQuery
 * JD-Core Version:    0.6.2
 */