/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalsmsapiQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private Long count;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private String type;
/*     */   private boolean typeLike;
/*     */   private String more;
/*     */   private boolean moreLike;
/*     */   private Integer time;
/*     */   private String text;
/*     */   private boolean textLike;
/*     */   private String appkey;
/*     */   private boolean appkeyLike;
/*     */   private String appsecret;
/*     */   private boolean appsecretLike;
/*     */   private String smssign;
/*     */   private boolean smssignLike;
/*     */   private String smstemplate;
/*     */   private boolean smstemplateLike;
/*     */   private String company;
/*     */   private boolean companyLike;
/* 214 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalsmsapiQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalsmsapiQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  37 */     return this.url;
/*     */   }
/*     */   public PortalsmsapiQuery setUrl(String url) {
/*  40 */     this.url = url;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setUrlLike(boolean isLike) {
/*  45 */     this.urlLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCount() {
/*  50 */     return this.count;
/*     */   }
/*     */   public PortalsmsapiQuery setCount(Long count) {
/*  53 */     this.count = count;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  58 */     return this.state;
/*     */   }
/*     */   public PortalsmsapiQuery setState(String state) {
/*  61 */     this.state = state;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setStateLike(boolean isLike) {
/*  66 */     this.stateLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  71 */     return this.type;
/*     */   }
/*     */   public PortalsmsapiQuery setType(String type) {
/*  74 */     this.type = type;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setTypeLike(boolean isLike) {
/*  79 */     this.typeLike = isLike;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public String getMore() {
/*  84 */     return this.more;
/*     */   }
/*     */   public PortalsmsapiQuery setMore(String more) {
/*  87 */     this.more = more;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setMoreLike(boolean isLike) {
/*  92 */     this.moreLike = isLike;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getTime() {
/*  97 */     return this.time;
/*     */   }
/*     */   public PortalsmsapiQuery setTime(Integer time) {
/* 100 */     this.time = time;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public String getText() {
/* 105 */     return this.text;
/*     */   }
/*     */   public PortalsmsapiQuery setText(String text) {
/* 108 */     this.text = text;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setTextLike(boolean isLike) {
/* 113 */     this.textLike = isLike;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAppkey() {
/* 118 */     return this.appkey;
/*     */   }
/*     */   public PortalsmsapiQuery setAppkey(String appkey) {
/* 121 */     this.appkey = appkey;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setAppkeyLike(boolean isLike) {
/* 126 */     this.appkeyLike = isLike;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAppsecret() {
/* 131 */     return this.appsecret;
/*     */   }
/*     */   public PortalsmsapiQuery setAppsecret(String appsecret) {
/* 134 */     this.appsecret = appsecret;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setAppsecretLike(boolean isLike) {
/* 139 */     this.appsecretLike = isLike;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSmssign() {
/* 144 */     return this.smssign;
/*     */   }
/*     */   public PortalsmsapiQuery setSmssign(String smssign) {
/* 147 */     this.smssign = smssign;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setSmssignLike(boolean isLike) {
/* 152 */     this.smssignLike = isLike;
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSmstemplate() {
/* 157 */     return this.smstemplate;
/*     */   }
/*     */   public PortalsmsapiQuery setSmstemplate(String smstemplate) {
/* 160 */     this.smstemplate = smstemplate;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setSmstemplateLike(boolean isLike) {
/* 165 */     this.smstemplateLike = isLike;
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCompany() {
/* 170 */     return this.company;
/*     */   }
/*     */   public PortalsmsapiQuery setCompany(String company) {
/* 173 */     this.company = company;
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery setCompanyLike(boolean isLike) {
/* 178 */     this.companyLike = isLike;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyId(boolean isAsc)
/*     */   {
/* 222 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyName(boolean isAsc)
/*     */   {
/* 232 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 242 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyCount(boolean isAsc)
/*     */   {
/* 252 */     this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyState(boolean isAsc)
/*     */   {
/* 262 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyType(boolean isAsc)
/*     */   {
/* 272 */     this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyMore(boolean isAsc)
/*     */   {
/* 282 */     this.orderFields.add(new OrderField("more", isAsc ? "ASC" : "DESC"));
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyTime(boolean isAsc)
/*     */   {
/* 292 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 293 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyText(boolean isAsc)
/*     */   {
/* 302 */     this.orderFields.add(new OrderField("text", isAsc ? "ASC" : "DESC"));
/* 303 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyAppkey(boolean isAsc)
/*     */   {
/* 312 */     this.orderFields.add(new OrderField("appkey", isAsc ? "ASC" : "DESC"));
/* 313 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyAppsecret(boolean isAsc)
/*     */   {
/* 322 */     this.orderFields.add(new OrderField("appsecret", isAsc ? "ASC" : "DESC"));
/* 323 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbySmssign(boolean isAsc)
/*     */   {
/* 332 */     this.orderFields.add(new OrderField("smssign", isAsc ? "ASC" : "DESC"));
/* 333 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbySmstemplate(boolean isAsc)
/*     */   {
/* 342 */     this.orderFields.add(new OrderField("smstemplate", isAsc ? "ASC" : "DESC"));
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalsmsapiQuery orderbyCompany(boolean isAsc)
/*     */   {
/* 352 */     this.orderFields.add(new OrderField("company", isAsc ? "ASC" : "DESC"));
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 362 */     if (fieldMap == null) {
/* 363 */       fieldMap = new HashMap();
/* 364 */       fieldMap.put("id", "id");
/* 365 */       fieldMap.put("name", "name");
/* 366 */       fieldMap.put("url", "url");
/* 367 */       fieldMap.put("count", "count");
/* 368 */       fieldMap.put("state", "state");
/* 369 */       fieldMap.put("type", "type");
/* 370 */       fieldMap.put("more", "more");
/* 371 */       fieldMap.put("time", "time");
/* 372 */       fieldMap.put("text", "text");
/* 373 */       fieldMap.put("appkey", "appkey");
/* 374 */       fieldMap.put("appsecret", "appsecret");
/* 375 */       fieldMap.put("smssign", "smssign");
/* 376 */       fieldMap.put("smstemplate", "smstemplate");
/* 377 */       fieldMap.put("company", "company");
/*     */     }
/* 379 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 383 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 386 */     if (fields == null)
/* 387 */       return;
/* 388 */     String[] array = fields.split(",");
/* 389 */     StringBuffer buffer = new StringBuffer();
/* 390 */     for (String field : array) {
/* 391 */       if (getFieldSet().containsKey(field)) {
/* 392 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 393 */           .append(field).append(" ,");
/*     */       }
/* 395 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 396 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 397 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 400 */     if (buffer.length() != 0)
/* 401 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 403 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 188 */       this.fieldName = fieldName;
/* 189 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 195 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 198 */       this.fieldName = fieldName;
/* 199 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 202 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 205 */       this.order = order;
/* 206 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalsmsapiQuery
 * JD-Core Version:    0.6.2
 */