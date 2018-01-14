/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ZsqhdapiQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private String publicurl;
/*     */   private boolean publicurlLike;
/*     */   private String autourl;
/*     */   private boolean autourlLike;
/*     */   private Integer state;
/*     */   private Integer publicstate;
/*     */   private Integer autostate;
/* 118 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public ZsqhdapiQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  24 */     return this.url;
/*     */   }
/*     */   public ZsqhdapiQuery setUrl(String url) {
/*  27 */     this.url = url;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery setUrlLike(boolean isLike) {
/*  32 */     this.urlLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPublicurl() {
/*  37 */     return this.publicurl;
/*     */   }
/*     */   public ZsqhdapiQuery setPublicurl(String publicurl) {
/*  40 */     this.publicurl = publicurl;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery setPublicurlLike(boolean isLike) {
/*  45 */     this.publicurlLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAutourl() {
/*  50 */     return this.autourl;
/*     */   }
/*     */   public ZsqhdapiQuery setAutourl(String autourl) {
/*  53 */     this.autourl = autourl;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery setAutourlLike(boolean isLike) {
/*  58 */     this.autourlLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/*  63 */     return this.state;
/*     */   }
/*     */   public ZsqhdapiQuery setState(Integer state) {
/*  66 */     this.state = state;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getPublicstate() {
/*  71 */     return this.publicstate;
/*     */   }
/*     */   public ZsqhdapiQuery setPublicstate(Integer publicstate) {
/*  74 */     this.publicstate = publicstate;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutostate() {
/*  79 */     return this.autostate;
/*     */   }
/*     */   public ZsqhdapiQuery setAutostate(Integer autostate) {
/*  82 */     this.autostate = autostate;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyId(boolean isAsc)
/*     */   {
/* 126 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 136 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyPublicurl(boolean isAsc)
/*     */   {
/* 146 */     this.orderFields.add(new OrderField("publicurl", isAsc ? "ASC" : "DESC"));
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyAutourl(boolean isAsc)
/*     */   {
/* 156 */     this.orderFields.add(new OrderField("autourl", isAsc ? "ASC" : "DESC"));
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyState(boolean isAsc)
/*     */   {
/* 166 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyPublicstate(boolean isAsc)
/*     */   {
/* 176 */     this.orderFields.add(new OrderField("publicstate", isAsc ? "ASC" : "DESC"));
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public ZsqhdapiQuery orderbyAutostate(boolean isAsc)
/*     */   {
/* 186 */     this.orderFields.add(new OrderField("autostate", isAsc ? "ASC" : "DESC"));
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 196 */     if (fieldMap == null) {
/* 197 */       fieldMap = new HashMap();
/* 198 */       fieldMap.put("id", "id");
/* 199 */       fieldMap.put("url", "url");
/* 200 */       fieldMap.put("publicurl", "publicurl");
/* 201 */       fieldMap.put("autourl", "autourl");
/* 202 */       fieldMap.put("state", "state");
/* 203 */       fieldMap.put("publicstate", "publicstate");
/* 204 */       fieldMap.put("autostate", "autostate");
/*     */     }
/* 206 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 210 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 213 */     if (fields == null)
/* 214 */       return;
/* 215 */     String[] array = fields.split(",");
/* 216 */     StringBuffer buffer = new StringBuffer();
/* 217 */     for (String field : array) {
/* 218 */       if (getFieldSet().containsKey(field)) {
/* 219 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 220 */           .append(field).append(" ,");
/*     */       }
/* 222 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 223 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 224 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 227 */     if (buffer.length() != 0)
/* 228 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 230 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/*  92 */       this.fieldName = fieldName;
/*  93 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/*  99 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 102 */       this.fieldName = fieldName;
/* 103 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 106 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 109 */       this.order = order;
/* 110 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.ZsqhdapiQuery
 * JD-Core Version:    0.6.2
 */