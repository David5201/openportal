/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalspeedQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private Long up;
/*     */   private Long down;
/*     */   private Long mup;
/*     */   private Long mdown;
/* 100 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalspeedQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalspeedQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUp() {
/*  37 */     return this.up;
/*     */   }
/*     */   public PortalspeedQuery setUp(Long up) {
/*  40 */     this.up = up;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getDown() {
/*  45 */     return this.down;
/*     */   }
/*     */   public PortalspeedQuery setDown(Long down) {
/*  48 */     this.down = down;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getMup() {
/*  53 */     return this.mup;
/*     */   }
/*     */   public PortalspeedQuery setMup(Long mup) {
/*  56 */     this.mup = mup;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getMdown() {
/*  61 */     return this.mdown;
/*     */   }
/*     */   public PortalspeedQuery setMdown(Long mdown) {
/*  64 */     this.mdown = mdown;
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyId(boolean isAsc)
/*     */   {
/* 108 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyName(boolean isAsc)
/*     */   {
/* 118 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyUp(boolean isAsc)
/*     */   {
/* 128 */     this.orderFields.add(new OrderField("up", isAsc ? "ASC" : "DESC"));
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyDown(boolean isAsc)
/*     */   {
/* 138 */     this.orderFields.add(new OrderField("down", isAsc ? "ASC" : "DESC"));
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyMup(boolean isAsc)
/*     */   {
/* 148 */     this.orderFields.add(new OrderField("mup", isAsc ? "ASC" : "DESC"));
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalspeedQuery orderbyMdown(boolean isAsc)
/*     */   {
/* 158 */     this.orderFields.add(new OrderField("mdown", isAsc ? "ASC" : "DESC"));
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 168 */     if (fieldMap == null) {
/* 169 */       fieldMap = new HashMap();
/* 170 */       fieldMap.put("id", "id");
/* 171 */       fieldMap.put("name", "name");
/* 172 */       fieldMap.put("up", "up");
/* 173 */       fieldMap.put("down", "down");
/* 174 */       fieldMap.put("mup", "mup");
/* 175 */       fieldMap.put("mdown", "mdown");
/*     */     }
/* 177 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 181 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 184 */     if (fields == null)
/* 185 */       return;
/* 186 */     String[] array = fields.split(",");
/* 187 */     StringBuffer buffer = new StringBuffer();
/* 188 */     for (String field : array) {
/* 189 */       if (getFieldSet().containsKey(field)) {
/* 190 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 191 */           .append(field).append(" ,");
/*     */       }
/* 193 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 194 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 195 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 198 */     if (buffer.length() != 0)
/* 199 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 201 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  74 */       this.fieldName = fieldName;
/*  75 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  81 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/*  84 */       this.fieldName = fieldName;
/*  85 */       return this;
/*     */     }
/*     */     public String getOrder() {
/*  88 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/*  91 */       this.order = order;
/*  92 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalspeedQuery
 * JD-Core Version:    0.6.2
 */