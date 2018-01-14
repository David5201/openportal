/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PayapiQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String alipayPartner;
/*     */   private boolean alipayPartnerLike;
/*     */   private String alipayKey;
/*     */   private boolean alipayKeyLike;
/*     */   private String weixinAppId;
/*     */   private boolean weixinAppIdLike;
/*     */   private String weixinAppSecret;
/*     */   private boolean weixinAppSecretLike;
/*     */   private String weixinPartner;
/*     */   private boolean weixinPartnerLike;
/*     */   private String weixinKey;
/*     */   private boolean weixinKeyLike;
/*     */   private String weixinPartnerExd;
/*     */   private boolean weixinPartnerExdLike;
/*     */   private Integer alipay;
/*     */   private Integer weixin;
/* 162 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PayapiQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAlipayPartner() {
/*  24 */     return this.alipayPartner;
/*     */   }
/*     */   public PayapiQuery setAlipayPartner(String alipayPartner) {
/*  27 */     this.alipayPartner = alipayPartner;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setAlipayPartnerLike(boolean isLike) {
/*  32 */     this.alipayPartnerLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAlipayKey() {
/*  37 */     return this.alipayKey;
/*     */   }
/*     */   public PayapiQuery setAlipayKey(String alipayKey) {
/*  40 */     this.alipayKey = alipayKey;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setAlipayKeyLike(boolean isLike) {
/*  45 */     this.alipayKeyLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getWeixinAppId() {
/*  50 */     return this.weixinAppId;
/*     */   }
/*     */   public PayapiQuery setWeixinAppId(String weixinAppId) {
/*  53 */     this.weixinAppId = weixinAppId;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setWeixinAppIdLike(boolean isLike) {
/*  58 */     this.weixinAppIdLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getWeixinAppSecret() {
/*  63 */     return this.weixinAppSecret;
/*     */   }
/*     */   public PayapiQuery setWeixinAppSecret(String weixinAppSecret) {
/*  66 */     this.weixinAppSecret = weixinAppSecret;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setWeixinAppSecretLike(boolean isLike) {
/*  71 */     this.weixinAppSecretLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getWeixinPartner() {
/*  76 */     return this.weixinPartner;
/*     */   }
/*     */   public PayapiQuery setWeixinPartner(String weixinPartner) {
/*  79 */     this.weixinPartner = weixinPartner;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setWeixinPartnerLike(boolean isLike) {
/*  84 */     this.weixinPartnerLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getWeixinKey() {
/*  89 */     return this.weixinKey;
/*     */   }
/*     */   public PayapiQuery setWeixinKey(String weixinKey) {
/*  92 */     this.weixinKey = weixinKey;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setWeixinKeyLike(boolean isLike) {
/*  97 */     this.weixinKeyLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getWeixinPartnerExd() {
/* 102 */     return this.weixinPartnerExd;
/*     */   }
/*     */   public PayapiQuery setWeixinPartnerExd(String weixinPartnerExd) {
/* 105 */     this.weixinPartnerExd = weixinPartnerExd;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery setWeixinPartnerExdLike(boolean isLike) {
/* 110 */     this.weixinPartnerExdLike = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAlipay() {
/* 115 */     return this.alipay;
/*     */   }
/*     */   public PayapiQuery setAlipay(Integer alipay) {
/* 118 */     this.alipay = alipay;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getWeixin() {
/* 123 */     return this.weixin;
/*     */   }
/*     */   public PayapiQuery setWeixin(Integer weixin) {
/* 126 */     this.weixin = weixin;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyId(boolean isAsc)
/*     */   {
/* 170 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyAlipayPartner(boolean isAsc)
/*     */   {
/* 180 */     this.orderFields.add(new OrderField("alipayPartner", isAsc ? "ASC" : "DESC"));
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyAlipayKey(boolean isAsc)
/*     */   {
/* 190 */     this.orderFields.add(new OrderField("alipayKey", isAsc ? "ASC" : "DESC"));
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixinAppId(boolean isAsc)
/*     */   {
/* 200 */     this.orderFields.add(new OrderField("weixinAppId", isAsc ? "ASC" : "DESC"));
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixinAppSecret(boolean isAsc)
/*     */   {
/* 210 */     this.orderFields.add(new OrderField("weixinAppSecret", isAsc ? "ASC" : "DESC"));
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixinPartner(boolean isAsc)
/*     */   {
/* 220 */     this.orderFields.add(new OrderField("weixinPartner", isAsc ? "ASC" : "DESC"));
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixinKey(boolean isAsc)
/*     */   {
/* 230 */     this.orderFields.add(new OrderField("weixinKey", isAsc ? "ASC" : "DESC"));
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixinPartnerExd(boolean isAsc)
/*     */   {
/* 240 */     this.orderFields.add(new OrderField("weixinPartnerExd", isAsc ? "ASC" : "DESC"));
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyAlipay(boolean isAsc)
/*     */   {
/* 250 */     this.orderFields.add(new OrderField("alipay", isAsc ? "ASC" : "DESC"));
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */   public PayapiQuery orderbyWeixin(boolean isAsc)
/*     */   {
/* 260 */     this.orderFields.add(new OrderField("weixin", isAsc ? "ASC" : "DESC"));
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 270 */     if (fieldMap == null) {
/* 271 */       fieldMap = new HashMap();
/* 272 */       fieldMap.put("id", "id");
/* 273 */       fieldMap.put("alipayPartner", "alipayPartner");
/* 274 */       fieldMap.put("alipayKey", "alipayKey");
/* 275 */       fieldMap.put("weixinAppId", "weixinAppId");
/* 276 */       fieldMap.put("weixinAppSecret", "weixinAppSecret");
/* 277 */       fieldMap.put("weixinPartner", "weixinPartner");
/* 278 */       fieldMap.put("weixinKey", "weixinKey");
/* 279 */       fieldMap.put("weixinPartnerExd", "weixinPartnerExd");
/* 280 */       fieldMap.put("alipay", "alipay");
/* 281 */       fieldMap.put("weixin", "weixin");
/*     */     }
/* 283 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 287 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 290 */     if (fields == null)
/* 291 */       return;
/* 292 */     String[] array = fields.split(",");
/* 293 */     StringBuffer buffer = new StringBuffer();
/* 294 */     for (String field : array) {
/* 295 */       if (getFieldSet().containsKey(field)) {
/* 296 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 297 */           .append(field).append(" ,");
/*     */       }
/* 299 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 300 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 301 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 304 */     if (buffer.length() != 0)
/* 305 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 307 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 136 */       this.fieldName = fieldName;
/* 137 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 143 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 146 */       this.fieldName = fieldName;
/* 147 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 150 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 153 */       this.order = order;
/* 154 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PayapiQuery
 * JD-Core Version:    0.6.2
 */