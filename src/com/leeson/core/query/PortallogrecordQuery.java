/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortallogrecordQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String info;
/*     */   private boolean infoLike;
/*     */   private Date recDate;
/*  76 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortallogrecordQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getInfo() {
/*  24 */     return this.info;
/*     */   }
/*     */   public PortallogrecordQuery setInfo(String info) {
/*  27 */     this.info = info;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallogrecordQuery setInfoLike(boolean isLike) {
/*  32 */     this.infoLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getRecDate() {
/*  37 */     return this.recDate;
/*     */   }
/*     */   public PortallogrecordQuery setRecDate(Date recDate) {
/*  40 */     this.recDate = recDate;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallogrecordQuery orderbyId(boolean isAsc)
/*     */   {
/*  84 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallogrecordQuery orderbyInfo(boolean isAsc)
/*     */   {
/*  94 */     this.orderFields.add(new OrderField("info", isAsc ? "ASC" : "DESC"));
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallogrecordQuery orderbyRecDate(boolean isAsc)
/*     */   {
/* 104 */     this.orderFields.add(new OrderField("rec_date", isAsc ? "ASC" : "DESC"));
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 114 */     if (fieldMap == null) {
/* 115 */       fieldMap = new HashMap();
/* 116 */       fieldMap.put("id", "id");
/* 117 */       fieldMap.put("info", "info");
/* 118 */       fieldMap.put("recDate", "rec_date");
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
 * Qualified Name:     com.leeson.core.query.PortallogrecordQuery
 * JD-Core Version:    0.6.2
 */