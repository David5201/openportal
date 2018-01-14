/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalautologinQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long time;
/*     */   private Integer type;
/*     */   private Integer state;
/*  79 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalautologinQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/*  24 */     return this.time;
/*     */   }
/*     */   public PortalautologinQuery setTime(Long time) {
/*  27 */     this.time = time;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getType() {
/*  32 */     return this.type;
/*     */   }
/*     */   public PortalautologinQuery setType(Integer type) {
/*  35 */     this.type = type;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/*  40 */     return this.state;
/*     */   }
/*     */   public PortalautologinQuery setState(Integer state) {
/*  43 */     this.state = state;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalautologinQuery orderbyId(boolean isAsc)
/*     */   {
/*  87 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalautologinQuery orderbyTime(boolean isAsc)
/*     */   {
/*  97 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalautologinQuery orderbyType(boolean isAsc)
/*     */   {
/* 107 */     this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalautologinQuery orderbyState(boolean isAsc)
/*     */   {
/* 117 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 127 */     if (fieldMap == null) {
/* 128 */       fieldMap = new HashMap();
/* 129 */       fieldMap.put("id", "id");
/* 130 */       fieldMap.put("time", "time");
/* 131 */       fieldMap.put("type", "type");
/* 132 */       fieldMap.put("state", "state");
/*     */     }
/* 134 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 138 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 141 */     if (fields == null)
/* 142 */       return;
/* 143 */     String[] array = fields.split(",");
/* 144 */     StringBuffer buffer = new StringBuffer();
/* 145 */     for (String field : array) {
/* 146 */       if (getFieldSet().containsKey(field)) {
/* 147 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 148 */           .append(field).append(" ,");
/*     */       }
/* 150 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 151 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 152 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 155 */     if (buffer.length() != 0)
/* 156 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 158 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  53 */       this.fieldName = fieldName;
/*  54 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  60 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  63 */       this.fieldName = fieldName;
/*  64 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  67 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  70 */       this.order = order;
/*  71 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalautologinQuery
 * JD-Core Version:    0.6.2
 */