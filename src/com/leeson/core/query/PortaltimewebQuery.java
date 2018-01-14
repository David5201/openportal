/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortaltimewebQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Date viewdate;
/*     */   private Integer viewweekday;
/*     */   private Integer viewdateday;
/*     */   private Integer viewmonth;
/*     */   private Long web;
/*     */   private Long count;
/*     */   private Long pos;
/* 137 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortaltimewebQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortaltimewebQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortaltimewebQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getViewdate() {
/*  50 */     return this.viewdate;
/*     */   }
/*     */   public PortaltimewebQuery setViewdate(Date viewdate) {
/*  53 */     this.viewdate = viewdate;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getViewweekday() {
/*  58 */     return this.viewweekday;
/*     */   }
/*     */   public PortaltimewebQuery setViewweekday(Integer viewweekday) {
/*  61 */     this.viewweekday = viewweekday;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getViewdateday() {
/*  66 */     return this.viewdateday;
/*     */   }
/*     */   public PortaltimewebQuery setViewdateday(Integer viewdateday) {
/*  69 */     this.viewdateday = viewdateday;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getViewmonth() {
/*  74 */     return this.viewmonth;
/*     */   }
/*     */   public PortaltimewebQuery setViewmonth(Integer viewmonth) {
/*  77 */     this.viewmonth = viewmonth;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getWeb() {
/*  82 */     return this.web;
/*     */   }
/*     */   public PortaltimewebQuery setWeb(Long web) {
/*  85 */     this.web = web;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCount() {
/*  90 */     return this.count;
/*     */   }
/*     */   public PortaltimewebQuery setCount(Long count) {
/*  93 */     this.count = count;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPos() {
/*  98 */     return this.pos;
/*     */   }
/*     */   public PortaltimewebQuery setPos(Long pos) {
/* 101 */     this.pos = pos;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyId(boolean isAsc)
/*     */   {
/* 145 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyName(boolean isAsc)
/*     */   {
/* 155 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 165 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyViewdate(boolean isAsc)
/*     */   {
/* 175 */     this.orderFields.add(new OrderField("viewdate", isAsc ? "ASC" : "DESC"));
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyViewweekday(boolean isAsc)
/*     */   {
/* 185 */     this.orderFields.add(new OrderField("viewweekday", isAsc ? "ASC" : "DESC"));
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyViewdateday(boolean isAsc)
/*     */   {
/* 195 */     this.orderFields.add(new OrderField("viewdateday", isAsc ? "ASC" : "DESC"));
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyViewmonth(boolean isAsc)
/*     */   {
/* 205 */     this.orderFields.add(new OrderField("viewmonth", isAsc ? "ASC" : "DESC"));
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyWeb(boolean isAsc)
/*     */   {
/* 215 */     this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyCount(boolean isAsc)
/*     */   {
/* 225 */     this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaltimewebQuery orderbyPos(boolean isAsc)
/*     */   {
/* 235 */     this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 245 */     if (fieldMap == null) {
/* 246 */       fieldMap = new HashMap();
/* 247 */       fieldMap.put("id", "id");
/* 248 */       fieldMap.put("name", "name");
/* 249 */       fieldMap.put("description", "description");
/* 250 */       fieldMap.put("viewdate", "viewdate");
/* 251 */       fieldMap.put("viewweekday", "viewweekday");
/* 252 */       fieldMap.put("viewdateday", "viewdateday");
/* 253 */       fieldMap.put("viewmonth", "viewmonth");
/* 254 */       fieldMap.put("web", "web");
/* 255 */       fieldMap.put("count", "count");
/* 256 */       fieldMap.put("pos", "pos");
/*     */     }
/* 258 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 262 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 265 */     if (fields == null)
/* 266 */       return;
/* 267 */     String[] array = fields.split(",");
/* 268 */     StringBuffer buffer = new StringBuffer();
/* 269 */     for (String field : array) {
/* 270 */       if (getFieldSet().containsKey(field)) {
/* 271 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 272 */           .append(field).append(" ,");
/*     */       }
/* 274 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 275 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 276 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 279 */     if (buffer.length() != 0)
/* 280 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 282 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 111 */       this.fieldName = fieldName;
/* 112 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 118 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 121 */       this.fieldName = fieldName;
/* 122 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 125 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 128 */       this.order = order;
/* 129 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortaltimewebQuery
 * JD-Core Version:    0.6.2
 */