/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AdvpicQuery extends BaseQuery
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
/*     */   private String imgW;
/*     */   private boolean imgWLike;
/* 155 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public AdvpicQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public AdvpicQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/*  37 */     return this.uid;
/*     */   }
/*     */   public AdvpicQuery setUid(Long uid) {
/*  40 */     this.uid = uid;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSid() {
/*  45 */     return this.sid;
/*     */   }
/*     */   public AdvpicQuery setSid(Long sid) {
/*  48 */     this.sid = sid;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAid() {
/*  53 */     return this.aid;
/*     */   }
/*     */   public AdvpicQuery setAid(Long aid) {
/*  56 */     this.aid = aid;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPos() {
/*  61 */     return this.pos;
/*     */   }
/*     */   public AdvpicQuery setPos(Long pos) {
/*  64 */     this.pos = pos;
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/*  69 */     return this.img;
/*     */   }
/*     */   public AdvpicQuery setImg(String img) {
/*  72 */     this.img = img;
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery setImgLike(boolean isLike) {
/*  77 */     this.imgLike = isLike;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  82 */     return this.url;
/*     */   }
/*     */   public AdvpicQuery setUrl(String url) {
/*  85 */     this.url = url;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery setUrlLike(boolean isLike) {
/*  90 */     this.urlLike = isLike;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getShowCount() {
/*  95 */     return this.showCount;
/*     */   }
/*     */   public AdvpicQuery setShowCount(Long showCount) {
/*  98 */     this.showCount = showCount;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getClickCount() {
/* 103 */     return this.clickCount;
/*     */   }
/*     */   public AdvpicQuery setClickCount(Long clickCount) {
/* 106 */     this.clickCount = clickCount;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public String getImgW() {
/* 111 */     return this.imgW;
/*     */   }
/*     */   public AdvpicQuery setImgW(String imgW) {
/* 114 */     this.imgW = imgW;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery setImgWLike(boolean isLike) {
/* 119 */     this.imgWLike = isLike;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyId(boolean isAsc)
/*     */   {
/* 163 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyName(boolean isAsc)
/*     */   {
/* 173 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyUid(boolean isAsc)
/*     */   {
/* 183 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbySid(boolean isAsc)
/*     */   {
/* 193 */     this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyAid(boolean isAsc)
/*     */   {
/* 203 */     this.orderFields.add(new OrderField("aid", isAsc ? "ASC" : "DESC"));
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyPos(boolean isAsc)
/*     */   {
/* 213 */     this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyImg(boolean isAsc)
/*     */   {
/* 223 */     this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 233 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyShowCount(boolean isAsc)
/*     */   {
/* 243 */     this.orderFields.add(new OrderField("showCount", isAsc ? "ASC" : "DESC"));
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyClickCount(boolean isAsc)
/*     */   {
/* 253 */     this.orderFields.add(new OrderField("clickCount", isAsc ? "ASC" : "DESC"));
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvpicQuery orderbyImgW(boolean isAsc)
/*     */   {
/* 263 */     this.orderFields.add(new OrderField("imgW", isAsc ? "ASC" : "DESC"));
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 273 */     if (fieldMap == null) {
/* 274 */       fieldMap = new HashMap();
/* 275 */       fieldMap.put("id", "id");
/* 276 */       fieldMap.put("name", "name");
/* 277 */       fieldMap.put("uid", "uid");
/* 278 */       fieldMap.put("sid", "sid");
/* 279 */       fieldMap.put("aid", "aid");
/* 280 */       fieldMap.put("pos", "pos");
/* 281 */       fieldMap.put("img", "img");
/* 282 */       fieldMap.put("url", "url");
/* 283 */       fieldMap.put("showCount", "showCount");
/* 284 */       fieldMap.put("clickCount", "clickCount");
/* 285 */       fieldMap.put("imgW", "imgW");
/*     */     }
/* 287 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 291 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 294 */     if (fields == null)
/* 295 */       return;
/* 296 */     String[] array = fields.split(",");
/* 297 */     StringBuffer buffer = new StringBuffer();
/* 298 */     for (String field : array) {
/* 299 */       if (getFieldSet().containsKey(field)) {
/* 300 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 301 */           .append(field).append(" ,");
/*     */       }
/* 303 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 304 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 305 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 308 */     if (buffer.length() != 0)
/* 309 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 311 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 129 */       this.fieldName = fieldName;
/* 130 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 136 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 139 */       this.fieldName = fieldName;
/* 140 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 143 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 146 */       this.order = order;
/* 147 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.AdvpicQuery
 * JD-Core Version:    0.6.2
 */