/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalmessageQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String title;
/*     */   private boolean titleLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Date date;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private String fromid;
/*     */   private boolean fromidLike;
/*     */   private String toid;
/*     */   private boolean toidLike;
/*     */   private String ip;
/*     */   private boolean ipLike;
/*     */   private String toname;
/*     */   private boolean tonameLike;
/*     */   private String fromname;
/*     */   private boolean fromnameLike;
/*     */   private String delin;
/*     */   private boolean delinLike;
/*     */   private String delout;
/*     */   private boolean deloutLike;
/*     */   private String fromPos;
/*     */   private boolean fromPosLike;
/*     */   private String toPos;
/*     */   private boolean toPosLike;
/* 219 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalmessageQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/*  24 */     return this.title;
/*     */   }
/*     */   public PortalmessageQuery setTitle(String title) {
/*  27 */     this.title = title;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setTitleLike(boolean isLike) {
/*  32 */     this.titleLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalmessageQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getDate() {
/*  50 */     return this.date;
/*     */   }
/*     */   public PortalmessageQuery setDate(Date date) {
/*  53 */     this.date = date;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  58 */     return this.state;
/*     */   }
/*     */   public PortalmessageQuery setState(String state) {
/*  61 */     this.state = state;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setStateLike(boolean isLike) {
/*  66 */     this.stateLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public String getFromid() {
/*  71 */     return this.fromid;
/*     */   }
/*     */   public PortalmessageQuery setFromid(String fromid) {
/*  74 */     this.fromid = fromid;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setFromidLike(boolean isLike) {
/*  79 */     this.fromidLike = isLike;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public String getToid() {
/*  84 */     return this.toid;
/*     */   }
/*     */   public PortalmessageQuery setToid(String toid) {
/*  87 */     this.toid = toid;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setToidLike(boolean isLike) {
/*  92 */     this.toidLike = isLike;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  97 */     return this.ip;
/*     */   }
/*     */   public PortalmessageQuery setIp(String ip) {
/* 100 */     this.ip = ip;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setIpLike(boolean isLike) {
/* 105 */     this.ipLike = isLike;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public String getToname() {
/* 110 */     return this.toname;
/*     */   }
/*     */   public PortalmessageQuery setToname(String toname) {
/* 113 */     this.toname = toname;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setTonameLike(boolean isLike) {
/* 118 */     this.tonameLike = isLike;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public String getFromname() {
/* 123 */     return this.fromname;
/*     */   }
/*     */   public PortalmessageQuery setFromname(String fromname) {
/* 126 */     this.fromname = fromname;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setFromnameLike(boolean isLike) {
/* 131 */     this.fromnameLike = isLike;
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDelin() {
/* 136 */     return this.delin;
/*     */   }
/*     */   public PortalmessageQuery setDelin(String delin) {
/* 139 */     this.delin = delin;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setDelinLike(boolean isLike) {
/* 144 */     this.delinLike = isLike;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDelout() {
/* 149 */     return this.delout;
/*     */   }
/*     */   public PortalmessageQuery setDelout(String delout) {
/* 152 */     this.delout = delout;
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setDeloutLike(boolean isLike) {
/* 157 */     this.deloutLike = isLike;
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public String getFromPos() {
/* 162 */     return this.fromPos;
/*     */   }
/*     */   public PortalmessageQuery setFromPos(String fromPos) {
/* 165 */     this.fromPos = fromPos;
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setFromPosLike(boolean isLike) {
/* 170 */     this.fromPosLike = isLike;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public String getToPos() {
/* 175 */     return this.toPos;
/*     */   }
/*     */   public PortalmessageQuery setToPos(String toPos) {
/* 178 */     this.toPos = toPos;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery setToPosLike(boolean isLike) {
/* 183 */     this.toPosLike = isLike;
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyId(boolean isAsc)
/*     */   {
/* 227 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyTitle(boolean isAsc)
/*     */   {
/* 237 */     this.orderFields.add(new OrderField("title", isAsc ? "ASC" : "DESC"));
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 247 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyDate(boolean isAsc)
/*     */   {
/* 257 */     this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyState(boolean isAsc)
/*     */   {
/* 267 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyFromid(boolean isAsc)
/*     */   {
/* 277 */     this.orderFields.add(new OrderField("fromid", isAsc ? "ASC" : "DESC"));
/* 278 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyToid(boolean isAsc)
/*     */   {
/* 287 */     this.orderFields.add(new OrderField("toid", isAsc ? "ASC" : "DESC"));
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyIp(boolean isAsc)
/*     */   {
/* 297 */     this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyToname(boolean isAsc)
/*     */   {
/* 307 */     this.orderFields.add(new OrderField("toname", isAsc ? "ASC" : "DESC"));
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyFromname(boolean isAsc)
/*     */   {
/* 317 */     this.orderFields.add(new OrderField("fromname", isAsc ? "ASC" : "DESC"));
/* 318 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyDelin(boolean isAsc)
/*     */   {
/* 327 */     this.orderFields.add(new OrderField("delin", isAsc ? "ASC" : "DESC"));
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyDelout(boolean isAsc)
/*     */   {
/* 337 */     this.orderFields.add(new OrderField("delout", isAsc ? "ASC" : "DESC"));
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyFromPos(boolean isAsc)
/*     */   {
/* 347 */     this.orderFields.add(new OrderField("fromPos", isAsc ? "ASC" : "DESC"));
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalmessageQuery orderbyToPos(boolean isAsc)
/*     */   {
/* 357 */     this.orderFields.add(new OrderField("toPos", isAsc ? "ASC" : "DESC"));
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 367 */     if (fieldMap == null) {
/* 368 */       fieldMap = new HashMap();
/* 369 */       fieldMap.put("id", "id");
/* 370 */       fieldMap.put("title", "title");
/* 371 */       fieldMap.put("description", "description");
/* 372 */       fieldMap.put("date", "date");
/* 373 */       fieldMap.put("state", "state");
/* 374 */       fieldMap.put("fromid", "fromid");
/* 375 */       fieldMap.put("toid", "toid");
/* 376 */       fieldMap.put("ip", "ip");
/* 377 */       fieldMap.put("toname", "toname");
/* 378 */       fieldMap.put("fromname", "fromname");
/* 379 */       fieldMap.put("delin", "delin");
/* 380 */       fieldMap.put("delout", "delout");
/* 381 */       fieldMap.put("fromPos", "fromPos");
/* 382 */       fieldMap.put("toPos", "toPos");
/*     */     }
/* 384 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 388 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 391 */     if (fields == null)
/* 392 */       return;
/* 393 */     String[] array = fields.split(",");
/* 394 */     StringBuffer buffer = new StringBuffer();
/* 395 */     for (String field : array) {
/* 396 */       if (getFieldSet().containsKey(field)) {
/* 397 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 398 */           .append(field).append(" ,");
/*     */       }
/* 400 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 401 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 402 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 405 */     if (buffer.length() != 0)
/* 406 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 408 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 193 */       this.fieldName = fieldName;
/* 194 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 200 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 203 */       this.fieldName = fieldName;
/* 204 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 207 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 210 */       this.order = order;
/* 211 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalmessageQuery
 * JD-Core Version:    0.6.2
 */