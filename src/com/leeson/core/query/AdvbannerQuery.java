/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AdvbannerQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private Long uid;
/*     */   private Long sid;
/*     */   private Long aid;
/*     */   private Long pos;
/*     */   private String img;
/*     */   private boolean imgLike;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private Long showCount;
/*     */   private Long clickCount;
/* 142 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public AdvbannerQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public AdvbannerQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/*  37 */     return this.uid;
/*     */   }
/*     */   public AdvbannerQuery setUid(Long uid) {
/*  40 */     this.uid = uid;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSid() {
/*  45 */     return this.sid;
/*     */   }
/*     */   public AdvbannerQuery setSid(Long sid) {
/*  48 */     this.sid = sid;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAid() {
/*  53 */     return this.aid;
/*     */   }
/*     */   public AdvbannerQuery setAid(Long aid) {
/*  56 */     this.aid = aid;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPos() {
/*  61 */     return this.pos;
/*     */   }
/*     */   public AdvbannerQuery setPos(Long pos) {
/*  64 */     this.pos = pos;
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/*  69 */     return this.img;
/*     */   }
/*     */   public AdvbannerQuery setImg(String img) {
/*  72 */     this.img = img;
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery setImgLike(boolean isLike) {
/*  77 */     this.imgLike = isLike;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  82 */     return this.url;
/*     */   }
/*     */   public AdvbannerQuery setUrl(String url) {
/*  85 */     this.url = url;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery setUrlLike(boolean isLike) {
/*  90 */     this.urlLike = isLike;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getShowCount() {
/*  95 */     return this.showCount;
/*     */   }
/*     */   public AdvbannerQuery setShowCount(Long showCount) {
/*  98 */     this.showCount = showCount;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getClickCount() {
/* 103 */     return this.clickCount;
/*     */   }
/*     */   public AdvbannerQuery setClickCount(Long clickCount) {
/* 106 */     this.clickCount = clickCount;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyId(boolean isAsc)
/*     */   {
/* 150 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyName(boolean isAsc)
/*     */   {
/* 160 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyUid(boolean isAsc)
/*     */   {
/* 170 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbySid(boolean isAsc)
/*     */   {
/* 180 */     this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyAid(boolean isAsc)
/*     */   {
/* 190 */     this.orderFields.add(new OrderField("aid", isAsc ? "ASC" : "DESC"));
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyPos(boolean isAsc)
/*     */   {
/* 200 */     this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyImg(boolean isAsc)
/*     */   {
/* 210 */     this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 220 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyShowCount(boolean isAsc)
/*     */   {
/* 230 */     this.orderFields.add(new OrderField("showCount", isAsc ? "ASC" : "DESC"));
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvbannerQuery orderbyClickCount(boolean isAsc)
/*     */   {
/* 240 */     this.orderFields.add(new OrderField("clickCount", isAsc ? "ASC" : "DESC"));
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 250 */     if (fieldMap == null) {
/* 251 */       fieldMap = new HashMap();
/* 252 */       fieldMap.put("id", "id");
/* 253 */       fieldMap.put("name", "name");
/* 254 */       fieldMap.put("uid", "uid");
/* 255 */       fieldMap.put("sid", "sid");
/* 256 */       fieldMap.put("aid", "aid");
/* 257 */       fieldMap.put("pos", "pos");
/* 258 */       fieldMap.put("img", "img");
/* 259 */       fieldMap.put("url", "url");
/* 260 */       fieldMap.put("showCount", "showCount");
/* 261 */       fieldMap.put("clickCount", "clickCount");
/*     */     }
/* 263 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 267 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 270 */     if (fields == null)
/* 271 */       return;
/* 272 */     String[] array = fields.split(",");
/* 273 */     StringBuffer buffer = new StringBuffer();
/* 274 */     for (String field : array) {
/* 275 */       if (getFieldSet().containsKey(field)) {
/* 276 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 277 */           .append(field).append(" ,");
/*     */       }
/* 279 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 280 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 281 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 284 */     if (buffer.length() != 0)
/* 285 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 287 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 116 */       this.fieldName = fieldName;
/* 117 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 123 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 126 */       this.fieldName = fieldName;
/* 127 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 130 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 133 */       this.order = order;
/* 134 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.AdvbannerQuery
 * JD-Core Version:    0.6.2
 */