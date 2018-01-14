/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ConfigQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Integer portalPort;
/*     */   private Integer isDebug;
/*     */   private Integer radiusOn;
/*     */   private Long checkTime;
/*     */   private Integer accountAdd;
/*     */   private Integer shutdownKick;
/*     */   private String domain;
/*     */   private boolean domainLike;
/*     */   private Long countShow;
/*     */   private Long countAuth;
/*     */   private Integer useDomain;
/*     */   private Integer authPort;
/*     */   private Integer acctPort;
/*     */   private Integer smsAuthList;
/* 164 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public ConfigQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getPortalPort() {
/*  24 */     return this.portalPort;
/*     */   }
/*     */   public ConfigQuery setPortalPort(Integer portalPort) {
/*  27 */     this.portalPort = portalPort;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getIsDebug() {
/*  32 */     return this.isDebug;
/*     */   }
/*     */   public ConfigQuery setIsDebug(Integer isDebug) {
/*  35 */     this.isDebug = isDebug;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRadiusOn() {
/*  40 */     return this.radiusOn;
/*     */   }
/*     */   public ConfigQuery setRadiusOn(Integer radiusOn) {
/*  43 */     this.radiusOn = radiusOn;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCheckTime() {
/*  48 */     return this.checkTime;
/*     */   }
/*     */   public ConfigQuery setCheckTime(Long checkTime) {
/*  51 */     this.checkTime = checkTime;
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAccountAdd() {
/*  56 */     return this.accountAdd;
/*     */   }
/*     */   public ConfigQuery setAccountAdd(Integer accountAdd) {
/*  59 */     this.accountAdd = accountAdd;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getShutdownKick() {
/*  64 */     return this.shutdownKick;
/*     */   }
/*     */   public ConfigQuery setShutdownKick(Integer shutdownKick) {
/*  67 */     this.shutdownKick = shutdownKick;
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDomain() {
/*  72 */     return this.domain;
/*     */   }
/*     */   public ConfigQuery setDomain(String domain) {
/*  75 */     this.domain = domain;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery setDomainLike(boolean isLike) {
/*  80 */     this.domainLike = isLike;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCountShow() {
/*  85 */     return this.countShow;
/*     */   }
/*     */   public ConfigQuery setCountShow(Long countShow) {
/*  88 */     this.countShow = countShow;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCountAuth() {
/*  93 */     return this.countAuth;
/*     */   }
/*     */   public ConfigQuery setCountAuth(Long countAuth) {
/*  96 */     this.countAuth = countAuth;
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getUseDomain() {
/* 101 */     return this.useDomain;
/*     */   }
/*     */   public ConfigQuery setUseDomain(Integer useDomain) {
/* 104 */     this.useDomain = useDomain;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAuthPort() {
/* 109 */     return this.authPort;
/*     */   }
/*     */   public ConfigQuery setAuthPort(Integer authPort) {
/* 112 */     this.authPort = authPort;
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAcctPort() {
/* 117 */     return this.acctPort;
/*     */   }
/*     */   public ConfigQuery setAcctPort(Integer acctPort) {
/* 120 */     this.acctPort = acctPort;
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getSmsAuthList() {
/* 125 */     return this.smsAuthList;
/*     */   }
/*     */   public ConfigQuery setSmsAuthList(Integer smsAuthList) {
/* 128 */     this.smsAuthList = smsAuthList;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyId(boolean isAsc)
/*     */   {
/* 172 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyPortalPort(boolean isAsc)
/*     */   {
/* 182 */     this.orderFields.add(new OrderField("portalPort", isAsc ? "ASC" : "DESC"));
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyIsDebug(boolean isAsc)
/*     */   {
/* 192 */     this.orderFields.add(new OrderField("isDebug", isAsc ? "ASC" : "DESC"));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyRadiusOn(boolean isAsc)
/*     */   {
/* 202 */     this.orderFields.add(new OrderField("radiusOn", isAsc ? "ASC" : "DESC"));
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyCheckTime(boolean isAsc)
/*     */   {
/* 212 */     this.orderFields.add(new OrderField("checkTime", isAsc ? "ASC" : "DESC"));
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyAccountAdd(boolean isAsc)
/*     */   {
/* 222 */     this.orderFields.add(new OrderField("accountAdd", isAsc ? "ASC" : "DESC"));
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyShutdownKick(boolean isAsc)
/*     */   {
/* 232 */     this.orderFields.add(new OrderField("shutdownKick", isAsc ? "ASC" : "DESC"));
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyDomain(boolean isAsc)
/*     */   {
/* 242 */     this.orderFields.add(new OrderField("domain", isAsc ? "ASC" : "DESC"));
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyCountShow(boolean isAsc)
/*     */   {
/* 252 */     this.orderFields.add(new OrderField("countShow", isAsc ? "ASC" : "DESC"));
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyCountAuth(boolean isAsc)
/*     */   {
/* 262 */     this.orderFields.add(new OrderField("countAuth", isAsc ? "ASC" : "DESC"));
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyUseDomain(boolean isAsc)
/*     */   {
/* 272 */     this.orderFields.add(new OrderField("useDomain", isAsc ? "ASC" : "DESC"));
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyAuthPort(boolean isAsc)
/*     */   {
/* 282 */     this.orderFields.add(new OrderField("authPort", isAsc ? "ASC" : "DESC"));
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbyAcctPort(boolean isAsc)
/*     */   {
/* 292 */     this.orderFields.add(new OrderField("acctPort", isAsc ? "ASC" : "DESC"));
/* 293 */     return this;
/*     */   }
/*     */ 
/*     */   public ConfigQuery orderbySmsAuthList(boolean isAsc)
/*     */   {
/* 302 */     this.orderFields.add(new OrderField("smsAuthList", isAsc ? "ASC" : "DESC"));
/* 303 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 312 */     if (fieldMap == null) {
/* 313 */       fieldMap = new HashMap();
/* 314 */       fieldMap.put("id", "id");
/* 315 */       fieldMap.put("portalPort", "portalPort");
/* 316 */       fieldMap.put("isDebug", "isDebug");
/* 317 */       fieldMap.put("radiusOn", "radiusOn");
/* 318 */       fieldMap.put("checkTime", "checkTime");
/* 319 */       fieldMap.put("accountAdd", "accountAdd");
/* 320 */       fieldMap.put("shutdownKick", "shutdownKick");
/* 321 */       fieldMap.put("domain", "domain");
/* 322 */       fieldMap.put("countShow", "countShow");
/* 323 */       fieldMap.put("countAuth", "countAuth");
/* 324 */       fieldMap.put("useDomain", "useDomain");
/* 325 */       fieldMap.put("authPort", "authPort");
/* 326 */       fieldMap.put("acctPort", "acctPort");
/* 327 */       fieldMap.put("smsAuthList", "smsAuthList");
/*     */     }
/* 329 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 333 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 336 */     if (fields == null)
/* 337 */       return;
/* 338 */     String[] array = fields.split(",");
/* 339 */     StringBuffer buffer = new StringBuffer();
/* 340 */     for (String field : array) {
/* 341 */       if (getFieldSet().containsKey(field)) {
/* 342 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 343 */           .append(field).append(" ,");
/*     */       }
/* 345 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 346 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 347 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 350 */     if (buffer.length() != 0)
/* 351 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 353 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 138 */       this.fieldName = fieldName;
/* 139 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 145 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 148 */       this.fieldName = fieldName;
/* 149 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 152 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 155 */       this.order = order;
/* 156 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.ConfigQuery
 * JD-Core Version:    0.6.2
 */