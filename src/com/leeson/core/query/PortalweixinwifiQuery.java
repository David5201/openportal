/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalweixinwifiQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String appId;
/*     */   private boolean appIdLike;
/*     */   private String shopId;
/*     */   private boolean shopIdLike;
/*     */   private String ssid;
/*     */   private boolean ssidLike;
/*     */   private String domain;
/*     */   private boolean domainLike;
/*     */   private String bssid;
/*     */   private boolean bssidLike;
/*     */   private String secretKey;
/*     */   private boolean secretKeyLike;
/*     */   private Long outTime;
/*     */   private String basip;
/*     */   private boolean basipLike;
/* 154 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalweixinwifiQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAppId() {
/*  24 */     return this.appId;
/*     */   }
/*     */   public PortalweixinwifiQuery setAppId(String appId) {
/*  27 */     this.appId = appId;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setAppIdLike(boolean isLike) {
/*  32 */     this.appIdLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getShopId() {
/*  37 */     return this.shopId;
/*     */   }
/*     */   public PortalweixinwifiQuery setShopId(String shopId) {
/*  40 */     this.shopId = shopId;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setShopIdLike(boolean isLike) {
/*  45 */     this.shopIdLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSsid() {
/*  50 */     return this.ssid;
/*     */   }
/*     */   public PortalweixinwifiQuery setSsid(String ssid) {
/*  53 */     this.ssid = ssid;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setSsidLike(boolean isLike) {
/*  58 */     this.ssidLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDomain() {
/*  63 */     return this.domain;
/*     */   }
/*     */   public PortalweixinwifiQuery setDomain(String domain) {
/*  66 */     this.domain = domain;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setDomainLike(boolean isLike) {
/*  71 */     this.domainLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBssid() {
/*  76 */     return this.bssid;
/*     */   }
/*     */   public PortalweixinwifiQuery setBssid(String bssid) {
/*  79 */     this.bssid = bssid;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setBssidLike(boolean isLike) {
/*  84 */     this.bssidLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSecretKey() {
/*  89 */     return this.secretKey;
/*     */   }
/*     */   public PortalweixinwifiQuery setSecretKey(String secretKey) {
/*  92 */     this.secretKey = secretKey;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setSecretKeyLike(boolean isLike) {
/*  97 */     this.secretKeyLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOutTime() {
/* 102 */     return this.outTime;
/*     */   }
/*     */   public PortalweixinwifiQuery setOutTime(Long outTime) {
/* 105 */     this.outTime = outTime;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/* 110 */     return this.basip;
/*     */   }
/*     */   public PortalweixinwifiQuery setBasip(String basip) {
/* 113 */     this.basip = basip;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery setBasipLike(boolean isLike) {
/* 118 */     this.basipLike = isLike;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyId(boolean isAsc)
/*     */   {
/* 162 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyAppId(boolean isAsc)
/*     */   {
/* 172 */     this.orderFields.add(new OrderField("appId", isAsc ? "ASC" : "DESC"));
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyShopId(boolean isAsc)
/*     */   {
/* 182 */     this.orderFields.add(new OrderField("shopId", isAsc ? "ASC" : "DESC"));
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbySsid(boolean isAsc)
/*     */   {
/* 192 */     this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyDomain(boolean isAsc)
/*     */   {
/* 202 */     this.orderFields.add(new OrderField("domain", isAsc ? "ASC" : "DESC"));
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyBssid(boolean isAsc)
/*     */   {
/* 212 */     this.orderFields.add(new OrderField("bssid", isAsc ? "ASC" : "DESC"));
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbySecretKey(boolean isAsc)
/*     */   {
/* 222 */     this.orderFields.add(new OrderField("secretKey", isAsc ? "ASC" : "DESC"));
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyOutTime(boolean isAsc)
/*     */   {
/* 232 */     this.orderFields.add(new OrderField("outTime", isAsc ? "ASC" : "DESC"));
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalweixinwifiQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 242 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 252 */     if (fieldMap == null) {
/* 253 */       fieldMap = new HashMap();
/* 254 */       fieldMap.put("id", "id");
/* 255 */       fieldMap.put("appId", "appId");
/* 256 */       fieldMap.put("shopId", "shopId");
/* 257 */       fieldMap.put("ssid", "ssid");
/* 258 */       fieldMap.put("domain", "domain");
/* 259 */       fieldMap.put("bssid", "bssid");
/* 260 */       fieldMap.put("secretKey", "secretKey");
/* 261 */       fieldMap.put("outTime", "outTime");
/* 262 */       fieldMap.put("basip", "basip");
/*     */     }
/* 264 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 268 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 271 */     if (fields == null)
/* 272 */       return;
/* 273 */     String[] array = fields.split(",");
/* 274 */     StringBuffer buffer = new StringBuffer();
/* 275 */     for (String field : array) {
/* 276 */       if (getFieldSet().containsKey(field)) {
/* 277 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 278 */           .append(field).append(" ,");
/*     */       }
/* 280 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 281 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 282 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 285 */     if (buffer.length() != 0)
/* 286 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 288 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 128 */       this.fieldName = fieldName;
/* 129 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 135 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 138 */       this.fieldName = fieldName;
/* 139 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 142 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 145 */       this.order = order;
/* 146 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalweixinwifiQuery
 * JD-Core Version:    0.6.2
 */