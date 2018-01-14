/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalbasauthQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long basid;
/*     */   private Integer type;
/*     */   private String username;
/*     */   private boolean usernameLike;
/*     */   private String password;
/*     */   private boolean passwordLike;
/*     */   private String basip;
/*     */   private boolean basipLike;
/*     */   private String url;
/*     */   private boolean urlLike;
/*     */   private Long sessiontime;
/* 131 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalbasauthQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getBasid() {
/*  24 */     return this.basid;
/*     */   }
/*     */   public PortalbasauthQuery setBasid(Long basid) {
/*  27 */     this.basid = basid;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getType() {
/*  32 */     return this.type;
/*     */   }
/*     */   public PortalbasauthQuery setType(Integer type) {
/*  35 */     this.type = type;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/*  40 */     return this.username;
/*     */   }
/*     */   public PortalbasauthQuery setUsername(String username) {
/*  43 */     this.username = username;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery setUsernameLike(boolean isLike) {
/*  48 */     this.usernameLike = isLike;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  53 */     return this.password;
/*     */   }
/*     */   public PortalbasauthQuery setPassword(String password) {
/*  56 */     this.password = password;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery setPasswordLike(boolean isLike) {
/*  61 */     this.passwordLike = isLike;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/*  66 */     return this.basip;
/*     */   }
/*     */   public PortalbasauthQuery setBasip(String basip) {
/*  69 */     this.basip = basip;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery setBasipLike(boolean isLike) {
/*  74 */     this.basipLike = isLike;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  79 */     return this.url;
/*     */   }
/*     */   public PortalbasauthQuery setUrl(String url) {
/*  82 */     this.url = url;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery setUrlLike(boolean isLike) {
/*  87 */     this.urlLike = isLike;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSessiontime() {
/*  92 */     return this.sessiontime;
/*     */   }
/*     */   public PortalbasauthQuery setSessiontime(Long sessiontime) {
/*  95 */     this.sessiontime = sessiontime;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyId(boolean isAsc)
/*     */   {
/* 139 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyBasid(boolean isAsc)
/*     */   {
/* 149 */     this.orderFields.add(new OrderField("basid", isAsc ? "ASC" : "DESC"));
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyType(boolean isAsc)
/*     */   {
/* 159 */     this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyUsername(boolean isAsc)
/*     */   {
/* 169 */     this.orderFields.add(new OrderField("username", isAsc ? "ASC" : "DESC"));
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyPassword(boolean isAsc)
/*     */   {
/* 179 */     this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 189 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbyUrl(boolean isAsc)
/*     */   {
/* 199 */     this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasauthQuery orderbySessiontime(boolean isAsc)
/*     */   {
/* 209 */     this.orderFields.add(new OrderField("sessiontime", isAsc ? "ASC" : "DESC"));
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 219 */     if (fieldMap == null) {
/* 220 */       fieldMap = new HashMap();
/* 221 */       fieldMap.put("id", "id");
/* 222 */       fieldMap.put("basid", "basid");
/* 223 */       fieldMap.put("type", "type");
/* 224 */       fieldMap.put("username", "username");
/* 225 */       fieldMap.put("password", "password");
/* 226 */       fieldMap.put("basip", "basip");
/* 227 */       fieldMap.put("url", "url");
/* 228 */       fieldMap.put("sessiontime", "sessiontime");
/*     */     }
/* 230 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 234 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 237 */     if (fields == null)
/* 238 */       return;
/* 239 */     String[] array = fields.split(",");
/* 240 */     StringBuffer buffer = new StringBuffer();
/* 241 */     for (String field : array) {
/* 242 */       if (getFieldSet().containsKey(field)) {
/* 243 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 244 */           .append(field).append(" ,");
/*     */       }
/* 246 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 247 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 248 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 251 */     if (buffer.length() != 0)
/* 252 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 254 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 105 */       this.fieldName = fieldName;
/* 106 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 112 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 115 */       this.fieldName = fieldName;
/* 116 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 119 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 122 */       this.order = order;
/* 123 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalbasauthQuery
 * JD-Core Version:    0.6.2
 */