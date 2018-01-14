/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AdvadvQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Date creatDate;
/*     */   private Integer state;
/*     */   private Date showDate;
/*     */   private Date endDate;
/*     */   private Long uid;
/*     */   private Long sid;
/*     */   private Long pos;
/*     */   private String img;
/*     */   private boolean imgLike;
/*     */   private Integer showName;
/*     */   private Integer showInfo;
/*     */   private Integer showImg;
/*     */   private Long showCount;
/*     */   private Long clickCount;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private Long lockTime;
/* 211 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public AdvadvQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public AdvadvQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public AdvadvQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getCreatDate() {
/*  50 */     return this.creatDate;
/*     */   }
/*     */   public AdvadvQuery setCreatDate(Date creatDate) {
/*  53 */     this.creatDate = creatDate;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/*  58 */     return this.state;
/*     */   }
/*     */   public AdvadvQuery setState(Integer state) {
/*  61 */     this.state = state;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getShowDate() {
/*  66 */     return this.showDate;
/*     */   }
/*     */   public AdvadvQuery setShowDate(Date showDate) {
/*  69 */     this.showDate = showDate;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getEndDate() {
/*  74 */     return this.endDate;
/*     */   }
/*     */   public AdvadvQuery setEndDate(Date endDate) {
/*  77 */     this.endDate = endDate;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/*  82 */     return this.uid;
/*     */   }
/*     */   public AdvadvQuery setUid(Long uid) {
/*  85 */     this.uid = uid;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSid() {
/*  90 */     return this.sid;
/*     */   }
/*     */   public AdvadvQuery setSid(Long sid) {
/*  93 */     this.sid = sid;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPos() {
/*  98 */     return this.pos;
/*     */   }
/*     */   public AdvadvQuery setPos(Long pos) {
/* 101 */     this.pos = pos;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/* 106 */     return this.img;
/*     */   }
/*     */   public AdvadvQuery setImg(String img) {
/* 109 */     this.img = img;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery setImgLike(boolean isLike) {
/* 114 */     this.imgLike = isLike;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getShowName() {
/* 119 */     return this.showName;
/*     */   }
/*     */   public AdvadvQuery setShowName(Integer showName) {
/* 122 */     this.showName = showName;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getShowInfo() {
/* 127 */     return this.showInfo;
/*     */   }
/*     */   public AdvadvQuery setShowInfo(Integer showInfo) {
/* 130 */     this.showInfo = showInfo;
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getShowImg() {
/* 135 */     return this.showImg;
/*     */   }
/*     */   public AdvadvQuery setShowImg(Integer showImg) {
/* 138 */     this.showImg = showImg;
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getShowCount() {
/* 143 */     return this.showCount;
/*     */   }
/*     */   public AdvadvQuery setShowCount(Long showCount) {
/* 146 */     this.showCount = showCount;
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getClickCount() {
/* 151 */     return this.clickCount;
/*     */   }
/*     */   public AdvadvQuery setClickCount(Long clickCount) {
/* 154 */     this.clickCount = clickCount;
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 159 */     return this.url;
/*     */   }
/*     */   public AdvadvQuery setUrl(String url) {
/* 162 */     this.url = url;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery setUrlLike(boolean isLike) {
/* 167 */     this.urlLike = isLike;
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getLockTime() {
/* 172 */     return this.lockTime;
/*     */   }
/*     */   public AdvadvQuery setLockTime(Long lockTime) {
/* 175 */     this.lockTime = lockTime;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyId(boolean isAsc)
/*     */   {
/* 219 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyName(boolean isAsc)
/*     */   {
/* 229 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 239 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyCreatDate(boolean isAsc)
/*     */   {
/* 249 */     this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyState(boolean isAsc)
/*     */   {
/* 259 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyShowDate(boolean isAsc)
/*     */   {
/* 269 */     this.orderFields.add(new OrderField("showDate", isAsc ? "ASC" : "DESC"));
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyEndDate(boolean isAsc)
/*     */   {
/* 279 */     this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyUid(boolean isAsc)
/*     */   {
/* 289 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbySid(boolean isAsc)
/*     */   {
/* 299 */     this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
/* 300 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyPos(boolean isAsc)
/*     */   {
/* 309 */     this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyImg(boolean isAsc)
/*     */   {
/* 319 */     this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
/* 320 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyShowName(boolean isAsc)
/*     */   {
/* 329 */     this.orderFields.add(new OrderField("showName", isAsc ? "ASC" : "DESC"));
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyShowInfo(boolean isAsc)
/*     */   {
/* 339 */     this.orderFields.add(new OrderField("showInfo", isAsc ? "ASC" : "DESC"));
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyShowImg(boolean isAsc)
/*     */   {
/* 349 */     this.orderFields.add(new OrderField("showImg", isAsc ? "ASC" : "DESC"));
/* 350 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyShowCount(boolean isAsc)
/*     */   {
/* 359 */     this.orderFields.add(new OrderField("showCount", isAsc ? "ASC" : "DESC"));
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyClickCount(boolean isAsc)
/*     */   {
/* 369 */     this.orderFields.add(new OrderField("clickCount", isAsc ? "ASC" : "DESC"));
/* 370 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 379 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 380 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvadvQuery orderbyLockTime(boolean isAsc)
/*     */   {
/* 389 */     this.orderFields.add(new OrderField("lockTime", isAsc ? "ASC" : "DESC"));
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 399 */     if (fieldMap == null) {
/* 400 */       fieldMap = new HashMap();
/* 401 */       fieldMap.put("id", "id");
/* 402 */       fieldMap.put("name", "name");
/* 403 */       fieldMap.put("description", "description");
/* 404 */       fieldMap.put("creatDate", "creatDate");
/* 405 */       fieldMap.put("state", "state");
/* 406 */       fieldMap.put("showDate", "showDate");
/* 407 */       fieldMap.put("endDate", "endDate");
/* 408 */       fieldMap.put("uid", "uid");
/* 409 */       fieldMap.put("sid", "sid");
/* 410 */       fieldMap.put("pos", "pos");
/* 411 */       fieldMap.put("img", "img");
/* 412 */       fieldMap.put("showName", "showName");
/* 413 */       fieldMap.put("showInfo", "showInfo");
/* 414 */       fieldMap.put("showImg", "showImg");
/* 415 */       fieldMap.put("showCount", "showCount");
/* 416 */       fieldMap.put("clickCount", "clickCount");
/* 417 */       fieldMap.put("url", "url");
/* 418 */       fieldMap.put("lockTime", "lockTime");
/*     */     }
/* 420 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 424 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 427 */     if (fields == null)
/* 428 */       return;
/* 429 */     String[] array = fields.split(",");
/* 430 */     StringBuffer buffer = new StringBuffer();
/* 431 */     for (String field : array) {
/* 432 */       if (getFieldSet().containsKey(field)) {
/* 433 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 434 */           .append(field).append(" ,");
/*     */       }
/* 436 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 437 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 438 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 441 */     if (buffer.length() != 0)
/* 442 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 444 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 185 */       this.fieldName = fieldName;
/* 186 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 192 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 195 */       this.fieldName = fieldName;
/* 196 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 199 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 202 */       this.order = order;
/* 203 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.AdvadvQuery
 * JD-Core Version:    0.6.2
 */