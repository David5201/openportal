/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalurlparameterQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String basname;
/*     */   private boolean basnameLike;
/*     */   private String userip;
/*     */   private boolean useripLike;
/*     */   private String usermac;
/*     */   private boolean usermacLike;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private String basip;
/*     */   private boolean basipLike;
/*     */   private String ssid;
/*     */   private boolean ssidLike;
/*     */   private String apmac;
/*     */   private boolean apmacLike;
/* 146 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalurlparameterQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasname() {
/*  24 */     return this.basname;
/*     */   }
/*     */   public PortalurlparameterQuery setBasname(String basname) {
/*  27 */     this.basname = basname;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setBasnameLike(boolean isLike) {
/*  32 */     this.basnameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUserip() {
/*  37 */     return this.userip;
/*     */   }
/*     */   public PortalurlparameterQuery setUserip(String userip) {
/*  40 */     this.userip = userip;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setUseripLike(boolean isLike) {
/*  45 */     this.useripLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUsermac() {
/*  50 */     return this.usermac;
/*     */   }
/*     */   public PortalurlparameterQuery setUsermac(String usermac) {
/*  53 */     this.usermac = usermac;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setUsermacLike(boolean isLike) {
/*  58 */     this.usermacLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  63 */     return this.url;
/*     */   }
/*     */   public PortalurlparameterQuery setUrl(String url) {
/*  66 */     this.url = url;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setUrlLike(boolean isLike) {
/*  71 */     this.urlLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/*  76 */     return this.basip;
/*     */   }
/*     */   public PortalurlparameterQuery setBasip(String basip) {
/*  79 */     this.basip = basip;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setBasipLike(boolean isLike) {
/*  84 */     this.basipLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSsid() {
/*  89 */     return this.ssid;
/*     */   }
/*     */   public PortalurlparameterQuery setSsid(String ssid) {
/*  92 */     this.ssid = ssid;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setSsidLike(boolean isLike) {
/*  97 */     this.ssidLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getApmac() {
/* 102 */     return this.apmac;
/*     */   }
/*     */   public PortalurlparameterQuery setApmac(String apmac) {
/* 105 */     this.apmac = apmac;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery setApmacLike(boolean isLike) {
/* 110 */     this.apmacLike = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyId(boolean isAsc)
/*     */   {
/* 154 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyBasname(boolean isAsc)
/*     */   {
/* 164 */     this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyUserip(boolean isAsc)
/*     */   {
/* 174 */     this.orderFields.add(new OrderField("userip", isAsc ? "ASC" : "DESC"));
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyUsermac(boolean isAsc)
/*     */   {
/* 184 */     this.orderFields.add(new OrderField("usermac", isAsc ? "ASC" : "DESC"));
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 194 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 204 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbySsid(boolean isAsc)
/*     */   {
/* 214 */     this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalurlparameterQuery orderbyApmac(boolean isAsc)
/*     */   {
/* 224 */     this.orderFields.add(new OrderField("apmac", isAsc ? "ASC" : "DESC"));
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 234 */     if (fieldMap == null) {
/* 235 */       fieldMap = new HashMap();
/* 236 */       fieldMap.put("id", "id");
/* 237 */       fieldMap.put("basname", "basname");
/* 238 */       fieldMap.put("userip", "userip");
/* 239 */       fieldMap.put("usermac", "usermac");
/* 240 */       fieldMap.put("url", "url");
/* 241 */       fieldMap.put("basip", "basip");
/* 242 */       fieldMap.put("ssid", "ssid");
/* 243 */       fieldMap.put("apmac", "apmac");
/*     */     }
/* 245 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 249 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 252 */     if (fields == null)
/* 253 */       return;
/* 254 */     String[] array = fields.split(",");
/* 255 */     StringBuffer buffer = new StringBuffer();
/* 256 */     for (String field : array) {
/* 257 */       if (getFieldSet().containsKey(field)) {
/* 258 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 259 */           .append(field).append(" ,");
/*     */       }
/* 261 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 262 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 263 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 266 */     if (buffer.length() != 0)
/* 267 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 269 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 120 */       this.fieldName = fieldName;
/* 121 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 127 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 130 */       this.fieldName = fieldName;
/* 131 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 134 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 137 */       this.order = order;
/* 138 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalurlparameterQuery
 * JD-Core Version:    0.6.2
 */