/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalssidQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long basid;
/*     */   private String ip;
/*     */   private boolean ipLike;
/*     */   private String ssid;
/*     */   private boolean ssidLike;
/*     */   private String address;
/*     */   private boolean addressLike;
/*     */   private String basip;
/*     */   private boolean basipLike;
/*     */   private String x;
/*     */   private boolean xLike;
/*     */   private String y;
/*     */   private boolean yLike;
/*     */   private String des;
/*     */   private boolean desLike;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private Long web;
/*     */   private Long count;
/* 183 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalssidQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getBasid() {
/*  24 */     return this.basid;
/*     */   }
/*     */   public PortalssidQuery setBasid(Long basid) {
/*  27 */     this.basid = basid;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  32 */     return this.ip;
/*     */   }
/*     */   public PortalssidQuery setIp(String ip) {
/*  35 */     this.ip = ip;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setIpLike(boolean isLike) {
/*  40 */     this.ipLike = isLike;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSsid() {
/*  45 */     return this.ssid;
/*     */   }
/*     */   public PortalssidQuery setSsid(String ssid) {
/*  48 */     this.ssid = ssid;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setSsidLike(boolean isLike) {
/*  53 */     this.ssidLike = isLike;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/*  58 */     return this.address;
/*     */   }
/*     */   public PortalssidQuery setAddress(String address) {
/*  61 */     this.address = address;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setAddressLike(boolean isLike) {
/*  66 */     this.addressLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/*  71 */     return this.basip;
/*     */   }
/*     */   public PortalssidQuery setBasip(String basip) {
/*  74 */     this.basip = basip;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setBasipLike(boolean isLike) {
/*  79 */     this.basipLike = isLike;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public String getX() {
/*  84 */     return this.x;
/*     */   }
/*     */   public PortalssidQuery setX(String x) {
/*  87 */     this.x = x;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setXLike(boolean isLike) {
/*  92 */     this.xLike = isLike;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public String getY() {
/*  97 */     return this.y;
/*     */   }
/*     */   public PortalssidQuery setY(String y) {
/* 100 */     this.y = y;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setYLike(boolean isLike) {
/* 105 */     this.yLike = isLike;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDes() {
/* 110 */     return this.des;
/*     */   }
/*     */   public PortalssidQuery setDes(String des) {
/* 113 */     this.des = des;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setDesLike(boolean isLike) {
/* 118 */     this.desLike = isLike;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 123 */     return this.name;
/*     */   }
/*     */   public PortalssidQuery setName(String name) {
/* 126 */     this.name = name;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery setNameLike(boolean isLike) {
/* 131 */     this.nameLike = isLike;
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getWeb() {
/* 136 */     return this.web;
/*     */   }
/*     */   public PortalssidQuery setWeb(Long web) {
/* 139 */     this.web = web;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCount() {
/* 144 */     return this.count;
/*     */   }
/*     */   public PortalssidQuery setCount(Long count) {
/* 147 */     this.count = count;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyId(boolean isAsc)
/*     */   {
/* 191 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyBasid(boolean isAsc)
/*     */   {
/* 201 */     this.orderFields.add(new OrderField("basid", isAsc ? "ASC" : "DESC"));
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyIp(boolean isAsc)
/*     */   {
/* 211 */     this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbySsid(boolean isAsc)
/*     */   {
/* 221 */     this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyAddress(boolean isAsc)
/*     */   {
/* 231 */     this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 241 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyX(boolean isAsc)
/*     */   {
/* 251 */     this.orderFields.add(new OrderField("x", isAsc ? "ASC" : "DESC"));
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyY(boolean isAsc)
/*     */   {
/* 261 */     this.orderFields.add(new OrderField("y", isAsc ? "ASC" : "DESC"));
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyDes(boolean isAsc)
/*     */   {
/* 271 */     this.orderFields.add(new OrderField("des", isAsc ? "ASC" : "DESC"));
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyName(boolean isAsc)
/*     */   {
/* 281 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyWeb(boolean isAsc)
/*     */   {
/* 291 */     this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
/* 292 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalssidQuery orderbyCount(boolean isAsc)
/*     */   {
/* 301 */     this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 311 */     if (fieldMap == null) {
/* 312 */       fieldMap = new HashMap();
/* 313 */       fieldMap.put("id", "id");
/* 314 */       fieldMap.put("basid", "basid");
/* 315 */       fieldMap.put("ip", "ip");
/* 316 */       fieldMap.put("ssid", "ssid");
/* 317 */       fieldMap.put("address", "address");
/* 318 */       fieldMap.put("basip", "basip");
/* 319 */       fieldMap.put("x", "x");
/* 320 */       fieldMap.put("y", "y");
/* 321 */       fieldMap.put("des", "des");
/* 322 */       fieldMap.put("name", "name");
/* 323 */       fieldMap.put("web", "web");
/* 324 */       fieldMap.put("count", "count");
/*     */     }
/* 326 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 330 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 333 */     if (fields == null)
/* 334 */       return;
/* 335 */     String[] array = fields.split(",");
/* 336 */     StringBuffer buffer = new StringBuffer();
/* 337 */     for (String field : array) {
/* 338 */       if (getFieldSet().containsKey(field)) {
/* 339 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 340 */           .append(field).append(" ,");
/*     */       }
/* 342 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 343 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 344 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 347 */     if (buffer.length() != 0)
/* 348 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 350 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 157 */       this.fieldName = fieldName;
/* 158 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 164 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 167 */       this.fieldName = fieldName;
/* 168 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 171 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 174 */       this.order = order;
/* 175 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalssidQuery
 * JD-Core Version:    0.6.2
 */